package micro.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import modelo.Pregunta;

public interface IPregunta {


	Respuesta<Page<Pregunta>> obtenerTodos(Pageable pageable);
	
	Respuesta<Boolean> borrarByUser(int idUsuario, Long idPregunta);
}
