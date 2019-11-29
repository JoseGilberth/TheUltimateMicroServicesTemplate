package micro.usuarios.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioPublicoDTO;
import modelo.auth.usuarios.publicos.UsuarioPublico;

public interface IUsuarioService {
	 
	Respuesta<UsuarioPublico> actualizar(UsuarioPublico usuarioPublico, OAuth2Authentication auth);
	Respuesta<Page<UsuarioPublico>> filtrar(Pageable pageable, FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO);
	 
	
}
