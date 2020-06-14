package micro.usuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import micro.usuarios.services.externos.IWebSocketExternalService;
import micro.usuarios.services.interfaces.IWebSocket;

@Service
public class WebSocketService implements IWebSocket {

	@Autowired
	IWebSocketExternalService iWebSocketExternalService;

	@Override
	public Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket) {
		return iWebSocketExternalService.sendMessage(messageWebsocket);
	}

}
