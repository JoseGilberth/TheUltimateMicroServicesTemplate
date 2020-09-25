package micro.auth._config.security.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import excepciones.auth.AccessDeniedHandlerException;
import excepciones.auth.AuthException;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);

	@Value("${security.jwt.resource-id}")
	private String resourceIds;

	@Value("${security.oauth2.unprotected-paths}")
	private String[] unProtectedPaths;

	@Value("${security.oauth2.swagger-paths}")
	private String[] swagger;

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceIds).tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerException())
				.authenticationEntryPoint(new AuthException()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable().requestMatchers().and()
				.authorizeRequests()

				// PATHS NO PROTEGIDOS
				.antMatchers(unProtectedPaths).permitAll()

				// SWAGGER
				.antMatchers(swagger).hasAuthority("web:administracion:mantenimiento:auth:swagger")

				// SESIONES
				.antMatchers(HttpMethod.POST, "/sesiones/filtro").hasAuthority("web:administracion:sesiones:mostrar")
				.antMatchers(HttpMethod.POST, "/sesiones/token").hasAuthority("web:administracion:sesiones:cerrar")

				// LOG
				.antMatchers("/log/*").hasAuthority("web:administracion:log:todos")

				// CLIENTE DETALLE
				.antMatchers("/cliente/detalle/**").hasAuthority("web:administracion:cliente:detalle:todos")

				// CLIENTE TOKEN
				.antMatchers("/cliente/token/**").hasAuthority("web:administracion:cliente:token:todos")

				.anyRequest().authenticated();
	}

}