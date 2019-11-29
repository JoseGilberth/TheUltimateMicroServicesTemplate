package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.Respuesta;



public interface RespuestaDao extends JpaRepository<Respuesta, Long> {

	@Query("SELECT com FROM Comentarios com ORDER BY com.fechaAlta desc")
	Page<Respuesta> obtenerTodosPorPaginacion(Pageable pageable);
	
	@Query("SELECT com FROM Comentarios com ORDER BY com.fechaAlta desc")
	Page<Respuesta> obtenerTodosPersonalizadoPorPaginacion(Pageable pageable);

	//@Query("DELETE FROM Respuesta INNER JOIN com.usuarioPublico up WHERE Respuesta.id =:idRespuesta AND up.id  =:idUsuario")
	//Boolean borrarByUsuario(@Param("idUsuario") int idUsuario,@Param("idRespuesta") Long idRespuesta);
}
