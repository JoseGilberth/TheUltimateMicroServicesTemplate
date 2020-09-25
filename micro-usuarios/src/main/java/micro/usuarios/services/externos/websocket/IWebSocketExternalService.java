package micro.usuarios.services.externos.websocket;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dto.main.MessageWebsocket;
import dto.main.Respuesta;

@FeignClient(name = "micro-websocket", url = "http://localhost:8606/micro-websocket", fallback = WebSocketService.class)
public interface IWebSocketExternalService {

	@PostMapping(value = "/websocket/admin/send/message")
	Respuesta<Boolean> sendMessage(@RequestBody MessageWebsocket messageWebsocket);

}
