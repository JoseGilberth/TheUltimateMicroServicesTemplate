import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { MessageWebSocket } from '../_dto/_main/MessageWebSocket.Dto';
import { UtilComponent } from '../_shared/util.component';


@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI {
  webSocketEndPoint: string = 'http://localhost:8606/micro-websocket/ws';
  topic: string = "/topic/greetings";
  stompClient: any;

  constructor(private utilComponent: UtilComponent) {
  }

  _connect() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);

    const _this = this;
    _this.stompClient.connect({}, function (frame) {

      _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
        _this.onMessageReceived(sdkEvent);
      });

      console.log("Enviando mensaje 1: ");
      let messageWebSocket = new MessageWebSocket();
      messageWebSocket.from = "frommme";
      messageWebSocket.text = "roer";
      _this._send(messageWebSocket);

      console.log("Enviando mensaje 2: ");
      messageWebSocket.from = "frommme";
      messageWebSocket.text = "roer";
      _this._send(messageWebSocket);

      _this.stompClient.reconnect_delay = 5000;
    }, this.errorCallBack);
  };


  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message) { 
    this.stompClient.send("/app/hello", {}, JSON.stringify(message));
  }

  onMessageReceived(message) {
    this.utilComponent.presentToasterInfo(message.body);
    console.log("Message Recieved from Server :: " + message.body); 
  }
}
