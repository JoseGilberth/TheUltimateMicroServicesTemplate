package micro.eureka.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import de.codecentric.boot.admin.server.domain.entities.Instance;


import feign.RequestInterceptor;

/*
 * Another little footnote: in a Spring boot application, 
 * your @Configuration class should not contain the @EnableWebMvc annotation (source). 
 * It may prevent other things from working, such as the springfox-swagger-ui html page. 
 * – Paulo Merson May 30 at 17:34 
*/
@Configuration
//@EnableWebMvc
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
