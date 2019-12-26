package micro.websocket.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import abstracts.ACRUDEndPoints;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;

@RestController
public class MessageController extends ACRUDEndPoints<MessageController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public ResponseEntity<Respuesta<MessageWebsocket>> send(MessageWebsocket message) throws Exception {

		String time = new SimpleDateFormat("HH:mm").format(new Date());

		Respuesta<MessageWebsocket> respuesta = new Respuesta<MessageWebsocket>();
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(message);
		respuesta.setEstado(true);
		respuesta.setMensaje("OK");

		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
