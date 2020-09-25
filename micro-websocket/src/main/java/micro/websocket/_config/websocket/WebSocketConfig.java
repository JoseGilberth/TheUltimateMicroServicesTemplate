package micro.websocket._config.websocket;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import utils.StaticVariables;
import utils.Token;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${cors.Access-Control-Allow-Origin}")
	private String allowOrigin;

	@Value("${websocket.stomp.endpoint}")
	private String[] stompEndpoint;

	@Resource(name = "usuariosAdministradores")
	private UserDetailsService adminUserDetailService;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/notify", "/topic");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(stompEndpoint).setAllowedOrigins(allowOrigin).withSockJS().setSessionCookieNeeded(false);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {

		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String jwtToken = accessor.getFirstNativeHeader(StaticVariables.AUTH_TOKEN);
					List<String> data = Token.getUsuarioYTipo(jwtToken);
					try {
						UserDetails userDetails = adminUserDetailService.loadUserByUsername(data.get(0));
						if (userDetails != null) {
							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							accessor.setUser(authentication);
						}
					} catch (Exception ex) {
						logger.error("configureClientInboundChannel: " + ex.getMessage());
					}
				} else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					if (Objects.nonNull(authentication))
						logger.info("Disconnected Auth : " + authentication.getName());
					else
						logger.info("Disconnected Sess : " + accessor.getSessionId());
				}
				return message;
			}

			@Override
			public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
				StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
				if (sha.getCommand() == null) {
					logger.warn("postSend null command");
					return;
				}
				String sessionId = sha.getSessionId();
				switch (sha.getCommand()) {
				case CONNECT:
					logger.info("STOMP Connect [sessionId: " + sessionId + "]");
					break;
				case CONNECTED:
					logger.info("STOMP Connected [sessionId: " + sessionId + "]");
					break;
				case DISCONNECT:
					logger.info("STOMP Disconnect [sessionId: " + sessionId + "]");
					break;
				default:
					break;
				}
			}
		});
	}

}
