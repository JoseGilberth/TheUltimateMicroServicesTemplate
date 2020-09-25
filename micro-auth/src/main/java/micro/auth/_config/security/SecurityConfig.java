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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;

import micro.auth._config.security.custom.CustomDaoAuthenticationProvider;
import micro.auth._config.security.custom.CustomJdbcTokenStore;
 

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Resource(name = "AdminUserDetailService")
	private UserDetailsService usuariosAdministradores;

	@Resource(name = "PublicUserDetailService")
	private UserDetailsService usuariosPublicos;

	@Value("${security.oauth2.unprotected-paths}")
	private String[] unprotectedPaths;

	@Value("${security.oauth2.ignored-paths}")
	private String[] ignoredPaths;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuariosPublicos).passwordEncoder(bcrypt);
		auth.userDetailsService(usuariosAdministradores).passwordEncoder(bcrypt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.requestMatchers().and().authorizeRequests().antMatchers(unprotectedPaths).permitAll().anyRequest()
				.permitAll();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers(ignoredPaths);
	}

	@Bean
	protected CustomDaoAuthenticationProvider customDaoAuthenticationProvider() throws Exception {
		CustomDaoAuthenticationProvider customProvider = new CustomDaoAuthenticationProvider();
		return customProvider;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(customDaoAuthenticationProvider()));
	}

	@Bean
	public TokenStore tokenStore() {
		//return new JdbcTokenStore(this.dataSource);
		return new CustomJdbcTokenStore(this.dataSource);
	}

}
