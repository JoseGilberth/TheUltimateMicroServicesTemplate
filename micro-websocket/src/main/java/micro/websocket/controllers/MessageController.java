package micro.websocket.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.websocket.services.interfaces.IMessageService;

@RestController
@RequestMapping(value = "/websocket")
public class MessageController extends ACrudEndPoints<MessageController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IMessageService iMessageService;

	@PostMapping(value = "/admin/send/message", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Boolean>> filtrar(@RequestBody MessageWebsocket messageWebsocket) {
		logger.info("DATOS ENTRANTES: " + messageWebsocket);
		Respuesta<Boolean> respuesta = iMessageService.sendMessage(messageWebsocket);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
