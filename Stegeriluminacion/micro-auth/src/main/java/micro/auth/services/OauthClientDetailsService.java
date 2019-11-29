package micro.auth.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthClientDetailsDao;
import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthClientDetailsDTO;
import micro.auth._config.languaje.Translator;
import micro.auth.services.interfaces.ACrud;
import micro.auth.services.interfaces.IOauthClientDetails;
import modelo.auth.oauth2.OauthClientDetails;

@Service("cliente")
public class OauthClientDetailsService extends ACrud<OauthClientDetails>
		implements IOauthClientDetails, ClientDetailsService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientDetailsDao oauthClientDetailsDao;

	@Override
	public Respuesta<Page<OauthClientDetails>> filtrar(Pageable pageable, FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO) {
		Respuesta<Page<OauthClientDetails>> respuesta = new Respuesta<Page<OauthClientDetails>>();
		Page<OauthClientDetails> sesiones = oauthClientDetailsDao.obtenerTodosPorPaginacion(pageable, filtroOauthClientDetailsDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(sesiones);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		logger.info("ClientDetailService - Clientes");
		OauthClientDetails client = oauthClientDetailsDao.findByClientId(clientId);
		if (client == null) {
			throw new ClientRegistrationException(String.format("Cliente ID no existe", clientId));
		}
		BaseClientDetails details = new BaseClientDetails();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
		details.setAccessTokenValiditySeconds(client.getAccess_token_validity());
		details.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorized_grant_types()));
		details.setClientId(client.getId());
		details.setClientSecret(client.getClient_secret());
		details.setRefreshTokenValiditySeconds(client.getRefresh_token_validity());
		details.setRegisteredRedirectUri(Collections.singleton(client.getWeb_server_redirect_uri()));
		details.setResourceIds(Arrays.asList(client.getResource_ids()));
		details.setScope(Arrays.asList(client.getScope()));
		logger.info("ClientDetailService - /Clientes");
		return details;
	}
	

}
