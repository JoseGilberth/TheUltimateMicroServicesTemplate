package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import modelo.Pregunta;

public interface PreguntaDao extends JpaRepository<Pregunta, Long> {
	
	
	@Query("SELECT preg FROM Pregunta preg ORDER BY preg.fechaAlta desc")
	Page<Pregunta> obtenerTodosPorPaginacion(Pageable pageable);
	

	//@Query("DELETE FROM Pregunta preg INNER JOIN preg.usuarioPublico up  WHERE pregunta.id=:idPregunta AND up.id =:idUsuario")
	//Boolean borrarByUsuario(int idUsuario, Long idPregunta);
}
