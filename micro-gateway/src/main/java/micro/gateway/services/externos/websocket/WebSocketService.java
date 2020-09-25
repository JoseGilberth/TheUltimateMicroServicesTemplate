package micro.gateway.services.externos.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import micro.gateway.services.externos.websocket.interfaces.IWebSocketService;

@Service
public class WebSocketService implements IWebSocketService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IWebSocketExternalService iWebSocketExternalService;

	@Override
	public Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(200, true, "");
		try {
			respuesta = iWebSocketExternalService.sendMessage(messageWebsocket);
		} catch (Exception ex) {
			logger.error("Error: " + ex.getMessage());
		}
		return respuesta;
	}

}
