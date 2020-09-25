package micro.auth.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.auth.FiltroOauthClientDetailsDTO;
import dto.main.Respuesta;
import model.auth.oauth2.OauthClientDetails;

public interface IOauthClientDetails {

	Respuesta<Page<OauthClientDetails>> filtrar(Pageable pageable,
			FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO);

}
