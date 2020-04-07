package micro.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import abstracts.ACrud;
import dao.auth.oauth.OauthAccessTokenDao;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthAccessTokenDTO;
import dto.micro.auth.OauthAccessTokenDTO;
import micro.auth._config.languaje.Translator;
import micro.auth.services.interfaces.IOauthAccessToken;
import modelo.auth.oauth2.OauthAccessToken;
import utils.Token;

@Service
public class OauthAccessTokenService extends ACrud<OauthAccessToken, String> implements IOauthAccessToken {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenDao oauthAccessTokenDao;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	WebSocketService webSocketService;

	/*
	 * DefaultOAuth2AccessToken token =
	 * SerializationUtils.deserialize(sesiones.getContent().get(0).getToken());
	 * logger.info("TOKEN getExpiresIn : " + token.getExpiresIn());
	 * logger.info("TOKEN getTokenType : " + token.getTokenType());
	 * 
	 * OAuth2Authentication auth =
	 * tokenServices.loadAuthentication(token.getValue());
	 * logger.info("TOKEN getPrincipal : " + auth.getPrincipal().toString());
	 * 
	 * logger.info("TOKEN getValue : " + token.getValue());
	 * logger.info("TOKEN getExpiration : " + token.getExpiration());
	 * logger.info("TOKEN getScope : " + token.getScope());
	 * logger.info("TOKEN getAdditionalInformation : " +
	 * token.getAdditionalInformation());
	 */
	@Override
	public Respuesta<Page<OauthAccessTokenDTO>> filtrar(Pageable pageable,
			FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) {

		Respuesta<Page<OauthAccessTokenDTO>> respuesta = new Respuesta<Page<OauthAccessTokenDTO>>();
		Page<OauthAccessToken> sesiones = oauthAccessTokenDao.obtenerTodosPorPaginacion(pageable,
				filtroOauthAccessTokenDTO);

		List<OauthAccessTokenDTO> oauthAccessTokenDTOList = new ArrayList<OauthAccessTokenDTO>();
		for (OauthAccessToken oauthAccessToken : sesiones) {
			DefaultOAuth2AccessToken token = SerializationUtils.deserialize(oauthAccessToken.getToken());
			OauthAccessTokenDTO oauthAccessTokenDTO = new OauthAccessTokenDTO();
			oauthAccessTokenDTO.setAdditionalInformation(token.getAdditionalInformation());
			oauthAccessTokenDTO.setExpiration(token.getExpiration());
			oauthAccessTokenDTO.setExpiresIn(token.getExpiresIn());
			oauthAccessTokenDTO.setScope(token.getScope());
			oauthAccessTokenDTO.setToken(token.getValue());
			oauthAccessTokenDTO.setTokenType(token.getTokenType());
			oauthAccessTokenDTO.setUsuario(oauthAccessToken.getUser_name());
			oauthAccessTokenDTO.setClientId(oauthAccessToken.getClient_id());
			oauthAccessTokenDTOList.add(oauthAccessTokenDTO);
		}
		Page<OauthAccessTokenDTO> page = new PageImpl<OauthAccessTokenDTO>(oauthAccessTokenDTOList, pageable,
				oauthAccessTokenDTOList.size());
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(page);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("token.obtenido"));
		return respuesta;
	}

	@Override
	public Respuesta<Boolean> revocar(OAuth2Authentication auth) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		final String token = tokenStore.getAccessToken(auth).getValue();
		List<String> usuarioTipo = Token.getUsuarioYTipo(token);
		try {
			webSocketService.sendMessage(new MessageWebsocket(usuarioTipo.get(0),
					"El usuario " + usuarioTipo.get(0) + " ha cerrado sesión", "Login"));
		} catch (Exception e) {
		}
		tokenServices.revokeToken(token);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(true);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("token.removido"));
		return respuesta;
	}

	@Override
	public Respuesta<Boolean> revocar(String tokenId) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		tokenServices.revokeToken(tokenId);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(true);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("token.removido"));
		return respuesta;
	}

}
