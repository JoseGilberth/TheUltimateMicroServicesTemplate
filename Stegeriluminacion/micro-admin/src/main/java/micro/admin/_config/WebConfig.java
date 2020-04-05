package micro.admin._config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Another little footnote: in a Spring boot application, 
 * your @Configuration class should not contain the @EnableWebMvc annotation (source). 
 * It may prevent other things from working, such as the springfox-swagger-ui html page. 
 * – Paulo Merson May 30 at 17:34 
*/
@Configuration
public class WebConfig implements WebMvcConfigurer, WebMvcRegistrations {

	Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
		configurer.favorPathExtension(true);
		configurer.favorParameter(true);
		configurer.favorPathExtension(true);
	}

}
