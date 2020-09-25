import { Injectable, OnDestroy } from '@angular/core';
import { Client, CompatClient, Stomp, StompSubscription } from '@stomp/stompjs';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter, first, switchMap, map } from 'rxjs/operators';
import * as SockJS from 'sockjs-client';
import { configuraciones } from '../../environments/configuraciones';
import { Token } from '../_dto/login/Token.Dto';
import { MessageWebSocket } from '../_dto/_main/MessageWebSocket.Dto';
import { UtilComponent } from '../_shared/util.component';
import { environment } from '../../environments/environment';


export enum SocketClientState {
  ATTEMPTING, CONNECTED
}
@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI implements OnDestroy {

  private webSocketEndPoint: string = environment.websocket + "/micro-websocket/websocket";
  private adminTopic: string = "/user/notify/admin";
  private loggerTopic: string = "/user/notify/logger";
  private client: CompatClient;
  public state: BehaviorSubject<SocketClientState>;

  constructor(private utilComponent: UtilComponent) {
    this.initConection();
  }

  initConection() {
    const _this = this;
    let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
    this.client = Stomp.over(new SockJS(this.webSocketEndPoint));
    this.client.reconnect_delay = 5000;
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);

    this.client.connect({ 'Auth-Token': token.access_token }, function (frame) { });

    this.client.onDisconnect = (data) => {
      _this.state.next(SocketClientState.ATTEMPTING);
      //_this.initConection();
      console.log("onDisconnect: " + JSON.stringify(data));
    }

    this.client.onWebSocketClose = (data) => {
      //_this.initConection();
      _this.state.next(SocketClientState.ATTEMPTING);
      console.log("onWebSocketClose: " + JSON.stringify(data));
    }

    this.client.onWebSocketError = (data) => {
      console.log("onWebSocketError: " + JSON.stringify(data));
    }

    this.client.onConnect = (data) => { 
      _this.state.next(SocketClientState.CONNECTED);
      _this.adminTopicChannel()
        .subscribe((messageWebSocket: MessageWebSocket) => {
          if (messageWebSocket.accion != "LogRequest") {
            _this.utilComponent.presentToasterInfo("(" + messageWebSocket.accion + ") " + messageWebSocket.mensaje);
          }
        });
    }

    this.client.onStompError = (data) => {
      console.log("onStompError: " + JSON.stringify(data));
    }

  }


  connect(): Observable<CompatClient> {
    let _this = this;
    return new Observable<CompatClient>(observer => {
      _this.state.pipe(filter(state => state === SocketClientState.CONNECTED)).subscribe(() => {
        observer.next(_this.client);
      });
    });
  }

  onMessage(topic: string): Observable<any> {
    return this.connect().pipe(first(), switchMap((client: CompatClient) => {
      return new Observable<any>(observer => {
        const subscription: StompSubscription = client.subscribe(topic, message => {
          observer.next(JSON.parse(message.body));
        });
        return () => client.unsubscribe(subscription.id);
      });
    }));
  }


  onAlwaysMessage(topic: string): Observable<any> {
    let _this = this;
    return this.connect().pipe(first(), switchMap((client: CompatClient) => {
      return new Observable<any>(observer => {
        let ids: string[] = _this.unSubscribeAll(client, topic);
        const subscription: StompSubscription = client.subscribe(topic, message => {
          observer.next(JSON.parse(message.body));
          ids.push(subscription.id);
          localStorage.setItem(topic, JSON.stringify(ids));
        });
      });
    }));
  }

  unSubscribeAll(client: CompatClient, topic: string) {
    let ids: string[] = <string[]>JSON.parse(localStorage.getItem(topic));
    if (ids == null) {
      ids = [];
    }
    ids.forEach(id => {
      client.unsubscribe(id);
    });
    return ids;
  }


  _disconnect() {
    const _this = this;
    if (this.client !== undefined) {
      if (this.client !== null) {
        this.connect().pipe(first()).subscribe(client => client.disconnect(
          respuesta => {
            _this.state.next(SocketClientState.ATTEMPTING);
          }));
      }
    }
  }

  ngOnDestroy() {
    this._disconnect();
  }

  /**
   * CANALES
   */
  adminTopicChannel() {
    //return this.onMessage(this.adminTopic).pipe(first()); 
    return this.onAlwaysMessage(this.adminTopic);
  }

  loggerTopicChannel() {
    return this.onAlwaysMessage(this.loggerTopic);
  }



  /**
   * METODOS VIEJOS
   */
  connectDeprecated() {
    const _this = this;
    let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
    this.client = Stomp.over(new SockJS(this.webSocketEndPoint));
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    this.client.reconnect_delay = 5000;
    this.client.connect({ 'Auth-Token': token.access_token }, function (frame) {
      _this.state.next(SocketClientState.CONNECTED);
      _this.client.subscribe(_this.adminTopic, function (sdkEvent) {
        _this.onMessageReceivedDeprecated(sdkEvent);
      });
    });
  }

  onMessageReceivedDeprecated(message) {
    let messageWebSocket: MessageWebSocket = <MessageWebSocket>JSON.parse(message.body);
    if (messageWebSocket.accion != "LogRequest") {
      this.utilComponent.presentToasterInfo("(" + messageWebSocket.accion + ") " + messageWebSocket.mensaje);
    }
  }


}
