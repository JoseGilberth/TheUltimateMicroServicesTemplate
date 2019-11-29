package micro.auth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthClientTokenDTO;
import modelo.auth.oauth2.OauthClientToken;

public interface IOauthClientToken {

	Respuesta<Page<OauthClientToken>> filtrar(Pageable pageable, FiltroOauthClientTokenDTO filtroOauthClientTokenDTO);
	 
	
}
