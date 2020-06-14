package micro.auth._config.security;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);

	@Autowired
	private TokenStore tokenStore;

	@Resource(name = "cliente")
	private ClientDetailsService clientes;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private WebResponseExceptionTranslator<OAuth2Exception> oauth2ResponseExceptionTranslator;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.withClientDetails(clientes);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore)
				.accessTokenConverter(accessTokenConverter).tokenEnhancer(enhancerChain)
				.exceptionTranslator(oauth2ResponseExceptionTranslator);
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		CustomTokenService defaultTokenServices = new CustomTokenService();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer());
		return defaultTokenServices;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	
	  //-- ELIMINA JKS DE CERTIFICADOS SI YA EXISTEN 
    
    //keytool -delete -alias stegerilumination -keystore "stegerilumination.jks" -storepass G1lb3rt0. -keypass gilberto 
    //keytool -delete -alias stegerilumination -v -trustcacerts -keystore cacerts.jks -storepass G1lb3rt0. -keypass gilberto
	  
	//-- GENERAR JKS 
    //keytool -genkey -alias stegerilumination -keyalg RSA -keypass gilberto -storepass G1lb3rt0. -keystore "stegerilumination.jks"
	  
	//-- PUBLIC - PEM , CER , TEXT 
    //keytool -export -alias stegerilumination -keystore "stegerilumination.jks" -file "stegerilumination-public-key.pem" -storepass G1lb3rt0. -keypass gilberto 
    //keytool -export -alias stegerilumination -keystore "stegerilumination.jks" -file "stegerilumination-public-key.cer" -storepass G1lb3rt0. -keypass gilberto 
    //keytool -list -alias stegerilumination -rfc -keystore "stegerilumination.jks" -storepass G1lb3rt0. -keypass gilberto
	  
	//-- IMPORT JKS A CERTIFICADOS DE CONFIANZA 
    //keytool -import -alias stegerilumination -v -trustcacerts -file "stegerilumination-public-key.cer" -keystore cacerts.jks -keypass gilberto -storepass G1lb3rt0.
	 

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		try {

			org.springframework.core.io.Resource resource = new ClassPathResource("/auth/stegerilumination.jks");
			char[] storepass = "G1lb3rt0.".toCharArray();
			char[] keypass = "gilberto".toCharArray();
			String alias = "stegerilumination";

			KeyStore store = KeyStore.getInstance("jks");
			store.load(resource.getInputStream(), storepass);
			RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(alias, keypass);
			RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
			converter.setKeyPair(new KeyPair(publicKey, key));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String publicKey = null;
		try {
			publicKey = IOUtils.toString(new ClassPathResource("/auth/public.txt").getInputStream());
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}

}
