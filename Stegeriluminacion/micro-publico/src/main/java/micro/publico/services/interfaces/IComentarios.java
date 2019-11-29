package micro.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import dto.micro.publico.comentarios.FiltroComentarioDTO;
import modelo.Comentarios;

public interface IComentarios {
	
	Respuesta<Page<Comentarios>> obtenerTodos(Pageable pageable);
	
	Respuesta<Page<Comentarios>> obtenerTodosPersonalizado(Pageable pageable, FiltroComentarioDTO filtroComentarioDTO);
	
	Respuesta<Boolean> borrarByUser(OAuth2Authentication auth, Long idComentario);
	

}
