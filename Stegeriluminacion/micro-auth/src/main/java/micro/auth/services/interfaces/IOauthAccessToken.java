package micro.auth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.auth.oauthaccesstoken.FiltroOauthAccessTokenDTO;
import modelo.auth.oauth2.OauthAccessToken;

public interface IOauthAccessToken {

	Respuesta<Page<OauthAccessToken>> filtrar(Pageable pageable, FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO);
	 
	
}
