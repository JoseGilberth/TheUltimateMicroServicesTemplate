import { Injectable, OnDestroy } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { configuraciones } from '../../environments/configuraciones';
import { Token } from '../_dto/login/Token.Dto';
import { MessageWebSocket } from '../_dto/_main/MessageWebSocket.Dto';
import { UtilComponent } from '../_shared/util.component';


export enum SocketClientState {
  ATTEMPTING, CONNECTED
}
@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI implements OnDestroy {

  private webSocketEndPoint: string = 'http://localhost:8606/micro-websocket/ws';
  private topic: string = "/user/topic/admin";
  private client: CompatClient;
  public state: BehaviorSubject<SocketClientState>;

  constructor(private utilComponent: UtilComponent) {
    this.connect();
  }

  ngOnDestroy() {
    this._disconnect();
  }

  connect() {
    const _this = this;
    let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
    this.client = Stomp.over(new SockJS(this.webSocketEndPoint));
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    this.client.reconnect_delay = 5000;
    this.client.connect({ 'Auth-Token': token.access_token }, function (frame) {
      _this.state.next(SocketClientState.CONNECTED);
      _this.client.subscribe(_this.topic, function (sdkEvent) {
        _this.onMessageReceived(sdkEvent);
      });
    });
  }

  onMessageReceived(message) {
    let messageWebSocket: MessageWebSocket = <MessageWebSocket>JSON.parse(message.body);
    this.utilComponent.presentToasterInfo("(" + messageWebSocket.accion + ") " + messageWebSocket.mensaje);
  }

  _disconnect() {
    const _this = this;
    if (this.client !== undefined) {
      if (this.client !== null) {
        _this.client.disconnect(
          respuesta => {
            _this.state.next(SocketClientState.ATTEMPTING);
          });
      }
    }
  }

}
