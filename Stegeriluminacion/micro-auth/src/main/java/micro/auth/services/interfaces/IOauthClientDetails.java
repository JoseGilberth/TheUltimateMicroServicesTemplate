package micro.auth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthClientDetailsDTO;
import modelo.auth.oauth2.OauthClientDetails;

public interface IOauthClientDetails {

	Respuesta<Page<OauthClientDetails>> filtrar(Pageable pageable, FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO);
	 
	
}
