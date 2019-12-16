package micro.auth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthAccessTokenDTO;
import dto.micro.auth.OauthAccessTokenDTO;

public interface IOauthAccessToken {

	Respuesta<Page<OauthAccessTokenDTO>> filtrar(Pageable pageable, FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO);
	Respuesta<Boolean> revocar(OAuth2Authentication auth);
	Respuesta<Boolean> revocar(String token);

}
