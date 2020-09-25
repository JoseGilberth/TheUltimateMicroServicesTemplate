package micro.auth.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthAccessTokenDao;
import dto.auth.FiltroOauthAccessTokenDTO;
import dto.auth.OauthAccessTokenDTO;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import interfaces.interfaces.MainCrud;
import micro.auth.interfaces.IOauthAccessToken;
import micro.auth.services.externos.websocket.interfaces.IWebSocketService;
import model.auth.oauth2.OauthAccessToken;
import utils.Token;
import utils._config.language.Translator;

@Service
public class OauthAccessTokenService extends MainCrud<OauthAccessToken, Long> implements IOauthAccessToken {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenDao oauthAccessTokenDao;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	IWebSocketService iWebSocketService;

	@Value("${websocket.topics.admin}")
	private String topicAdmin;

	@Override
	public Respuesta<Page<OauthAccessTokenDTO>> filtrar(Pageable pageable,
			FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) {

		Page<OauthAccessToken> sesiones = oauthAccessTokenDao.filtro(pageable, filtroOauthAccessTokenDTO);

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

		return new Respuesta<Page<OauthAccessTokenDTO>>(200, page,
				Translator.toLocale("auth.oauthaccesstokenservice.filtrar"));
	}

	@Override
	public Respuesta<Boolean> cerrarSesion(OAuth2Authentication auth) {
		final String token = tokenStore.getAccessToken(auth).getValue();
		List<String> usuarioTipo = Token.getUsuarioYTipo(token);

		try {
			iWebSocketService.sendMessage(new MessageWebsocket(usuarioTipo.get(0),
					Translator.toLocale("notification.login.init.session", new String[] { usuarioTipo.get(0) }),
					"Login", topicAdmin));
		} catch (Exception ex) {
			logger.error("iWebSocketService: " + ex.getMessage());
		}

		return new Respuesta<Boolean>(200, true, Translator.toLocale("auth.oauthaccesstokenservice.revocar"));
	}

	@Override
	public Respuesta<Boolean> revocar(String tokenId) {
		tokenServices.revokeToken(tokenId);
		return new Respuesta<Boolean>(200, true, Translator.toLocale("auth.oauthaccesstokenservice.revocar"));
	}

}
