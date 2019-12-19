package micro.eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import feign.RequestInterceptor;

/*
 * Another little footnote: in a Spring boot application, 
 * your @Configuration class should not contain the @EnableWebMvc annotation (source). 
 * It may prevent other things from working, such as the springfox-swagger-ui html page. 
 * – Paulo Merson May 30 at 17:34 
*/
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/*
	 * AYUDA A FEIGN PARA PONER EL JWT AUTOMATICAMENTE Y SE ENVIE ENTRE
	 * MICROSERVICIOS ES UN SIMPLE INTERCEPTOR QUE MANDA EL TOKEN EN LA PETICION
	 * FEIGN
	 */
	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				Object details = auth.getDetails();
				if (details instanceof OAuth2AuthenticationDetails) {
					OAuth2AuthenticationDetails holder = (OAuth2AuthenticationDetails) details;
					template.header(HttpHeaders.AUTHORIZATION, holder.getTokenType() + " " + holder.getTokenValue());
				}
			}
		};
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}

}
