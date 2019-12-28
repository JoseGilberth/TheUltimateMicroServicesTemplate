package micro.websocket.services.interfaces;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;

public interface IMessageService {

	Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket);
	
}
