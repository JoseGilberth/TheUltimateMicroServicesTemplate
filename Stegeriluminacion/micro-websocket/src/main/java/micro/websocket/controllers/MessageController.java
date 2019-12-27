package micro.websocket.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import abstracts.ACRUDEndPoints;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;

@RestController
public class MessageController extends ACRUDEndPoints<MessageController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SimpMessagingTemplate template;

	int contar = 0; 

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public ResponseEntity<Respuesta<MessageWebsocket>> send(MessageWebsocket message) throws Exception {
		Respuesta<MessageWebsocket> respuesta = new Respuesta<MessageWebsocket>();
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(message);
		respuesta.setEstado(true);
		respuesta.setMensaje("OK");
		while (contar > 0) {
			try { 
                Thread.sleep(5*1000); 
				MessageWebsocket messageWebsocket = new MessageWebsocket();
				messageWebsocket.setFrom(String.valueOf(contar) + "");
				messageWebsocket.setText(String.valueOf(contar) + ": Textini");
		        template.convertAndSend("/topic/greetings", messageWebsocket); 
		        contar++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        contar++;
		
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
