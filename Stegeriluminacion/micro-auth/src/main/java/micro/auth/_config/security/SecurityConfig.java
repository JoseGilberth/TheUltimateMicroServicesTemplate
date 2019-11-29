package micro.auth._config.security;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import micro.auth.usuarios.CustomDaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.security-realm}")
	private String securityRealm;

	@Resource(name = "usuariosAdministradores")
	private UserDetailsService usuariosAdministradores;

	@Resource(name = "usuariosPublicos")
	private UserDetailsService usuariosPublicos;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		logger.info(">>>>> [userDetailsService] Authentication enabled:  " + true);
		auth.userDetailsService(usuariosPublicos).passwordEncoder(bcrypt);
		auth.userDetailsService(usuariosAdministradores).passwordEncoder(bcrypt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.requestMatchers().and().authorizeRequests().antMatchers("/heartbit").permitAll().anyRequest()
				.permitAll();
	}

	@Bean
	protected CustomDaoAuthenticationProvider customDaoAuthenticationProvider() throws Exception {
		CustomDaoAuthenticationProvider customProvider = new CustomDaoAuthenticationProvider();
		return customProvider;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(encodingStrength);
		return bCryptPasswordEncoder;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(customDaoAuthenticationProvider()));
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(this.dataSource);
	}

}
