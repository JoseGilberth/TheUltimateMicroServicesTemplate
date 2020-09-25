package micro.auth.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.auth.FiltroOauthAccessTokenDTO;
import dto.auth.OauthAccessTokenDTO;
import dto.main.Respuesta;

public interface IOauthAccessToken {

	Respuesta<Page<OauthAccessTokenDTO>> filtrar(Pageable pageable, FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO);

	Respuesta<Boolean> cerrarSesion(OAuth2Authentication auth);

	Respuesta<Boolean> revocar(String token);

}
