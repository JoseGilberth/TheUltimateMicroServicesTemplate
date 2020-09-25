package micro.websocket.channels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;

import dto.main.MessageWebsocket;
import utils.StaticVariables;
 
public class AdminChannel {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@SendTo(StaticVariables.TOPIC_ADMIN)
	public MessageWebsocket send2(MessageWebsocket message) throws Exception {
		return message;
	}
	
}
