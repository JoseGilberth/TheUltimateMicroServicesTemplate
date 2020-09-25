package micro.auth.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.auth.FiltroOauthClientTokenDTO;
import dto.main.Respuesta;
import model.auth.oauth2.OauthClientToken;

public interface IOauthClientToken {

	Respuesta<Page<OauthClientToken>> filtrar(Pageable pageable, FiltroOauthClientTokenDTO filtroOauthClientTokenDTO);

}
