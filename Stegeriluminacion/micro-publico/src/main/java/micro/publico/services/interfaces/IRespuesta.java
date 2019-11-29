package micro.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
//queda mal el de respuesta aqui
public interface IRespuesta {
	
    Respuesta<Page<modelo.Respuesta>> obtenerTodos(Pageable pageable);
	
	Respuesta<Boolean> borrarByUser(int idUsuario, Long idRespuesta);

}
