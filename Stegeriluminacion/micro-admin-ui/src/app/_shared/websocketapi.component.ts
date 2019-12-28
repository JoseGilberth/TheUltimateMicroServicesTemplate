import { Injectable } from '@angular/core';
import { Stomp, StompConfig } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { MessageWebSocket } from '../_dto/_main/MessageWebSocket.Dto';
import { UtilComponent } from '../_shared/util.component';
import { configuraciones } from '../../environments/configuraciones';
import { Token } from '../_dto/login/Token.Dto';



@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI {

  webSocketEndPoint: string = 'http://localhost:8606/micro-websocket/ws';
  topic: string = "/user/topic/admin";
  stompClient: any = null;
  isConnected: boolean = (localStorage.getItem(configuraciones.static.isConnectedToWebSocket) == "true") ;

  constructor(private utilComponent: UtilComponent) {
  }

  _onDisconnect() {

  }

  _connect() {
    const _this = this;
    let tipoUsuario = this.utilComponent.obtenerTipoUsuario();

    if (tipoUsuario === "Administrador" && !this.isConnected || this.stompClient == null) {
       let ws = new SockJS(this.webSocketEndPoint);
      this.stompClient = Stomp.over(ws);

      let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));

      _this.stompClient.connect({ 'Auth-Token': token.access_token }, function (frame) {
        _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
          _this.onMessageReceived(sdkEvent);
        });
        _this.stompClient.reconnect_delay = 5000;
        _this.isConnected = true;
        localStorage.setItem(configuraciones.static.isConnectedToWebSocket,"true");
      }, error => {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
          this._connect();
        }, 5000);
      });


    }
  };


  _disconnect() {
    if (this.stompClient !== undefined) {
      if (this.stompClient !== null) {
        this.stompClient.disconnect();
      }
    }
    console.log("Disconnected");
  }


  /**
   * Send message to sever via web socket
   * @param {*} message
  _send(message) {
    let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
    this.stompClient.send("/app/hello", { 'Authorization': 'Bearer ' + token.access_token }, JSON.stringify(message));
  }
   */
  onMessageReceived(message) {
    let messageWebSocket: MessageWebSocket = <MessageWebSocket>JSON.parse(message.body);
    this.utilComponent.presentToasterInfo("(" + messageWebSocket.accion + ") " + messageWebSocket.mensaje);
  }

}
