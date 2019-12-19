package micro.auth._config.security.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import steger.excepciones.auth.AuthException;

@Configuration
@EnableResourceServer
@Order(4)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);

	@Value("${security.unprotected-paths}")
	private String[] unProtectedPaths;

	@Value("${security.jwt.resource-id}")
	private String resourceId;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling().authenticationEntryPoint(new AuthException()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.requestMatchers().and()
				.authorizeRequests()
				.antMatchers(unProtectedPaths).permitAll()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**").hasAuthority("web:administracion:mantenimiento:swagger")
				.antMatchers( HttpMethod.POST , "/sesiones/filtro").hasAuthority("web:administracion:sesiones:mostrar")  
				.antMatchers( HttpMethod.POST , "/log/filtro")	   .hasAuthority("web:administracion:log:mostrar") 
				.anyRequest().authenticated();
	}

}