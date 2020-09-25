package micro.usuarios.services.externos.websocket.interfaces;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;

public interface IWebSocketService {

	Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket);
	
}
