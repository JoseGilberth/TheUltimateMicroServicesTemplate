package micro.websocket.channels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;

import dto.main.MessageWebsocket;
import micro.websocket._config.StaticVariables;

public class AdminChannel {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@SendTo(StaticVariables.TOPIC_ADMIN) // PARA ENVIAR
	public MessageWebsocket send2(MessageWebsocket message) throws Exception {
		return message;
	}

	/*
	 * @MessageMapping("/hello") // PARA RECIBIR
	 * 
	 * @SendTo("/topic/greetings") // PARA ENVIAR public Respuesta<MessageWebsocket>
	 * send(MessageWebsocket message) throws Exception { Respuesta<MessageWebsocket>
	 * respuesta = new Respuesta<MessageWebsocket>(); respuesta.setCodigo(200);
	 * respuesta.setCodigoHttp(200); respuesta.setCuerpo(message);
	 * respuesta.setEstado(true); respuesta.setMensaje("OK"); MessageWebsocket
	 * messageWebsocket = new MessageWebsocket();
	 * messageWebsocket.setAccion("Accion"); messageWebsocket.setMensaje("Test");
	 * messageWebsocket.setUsuario("Usuario");
	 * template.convertAndSendToUser("gilbertoAdmin", "/topic/admin",
	 * messageWebsocket); return respuesta; }
	 */

}
