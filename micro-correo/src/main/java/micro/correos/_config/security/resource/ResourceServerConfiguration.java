package micro.correos._config.security.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import excepciones.auth.AccessDeniedHandlerException;
import excepciones.auth.AuthException;

 
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);

	@Value("${security.oauth2.unprotected-paths}")
	private String[] unProtectedPaths;
	
	@Value("${security.oauth2.swagger-paths}")
	private String[] swaggerPaths;

	@Value("${security.jwt.resource-id}")
	private String resource_id;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resource_id);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling()
				.accessDeniedHandler(new AccessDeniedHandlerException())
				.authenticationEntryPoint(new AuthException())
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.requestMatchers().and()
				.authorizeRequests()

				// PATHS NO PROTEGIDOS
				.antMatchers(unProtectedPaths).permitAll()
				// SWAGGER
				.antMatchers(swaggerPaths).hasAuthority("web:administracion:mantenimiento:gateway:swagger")
				
				.anyRequest().authenticated();
	}


}