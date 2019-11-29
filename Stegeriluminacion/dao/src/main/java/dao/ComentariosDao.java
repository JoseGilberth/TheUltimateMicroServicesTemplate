package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dto.micro.publico.comentarios.FiltroComentarioDTO;
import modelo.Comentarios; 

public interface ComentariosDao extends JpaRepository<Comentarios, Long> {
	
	@Query("FROM Comentarios coment INNER JOIN coment.producto produc "
			+ " WHERE (:#{#filtroComentarioDTO.fechaInicial} is null or coment.fechaAlta =:#{#filtroComentarioDTO.fechaInicial})" 
			+ " AND (:#{#filtroComentarioDTO.idProducto} is null or produc.id =:#{#filtroComentarioDTO.idProducto})"
			+ " ORDER BY coment.fechaAlta desc")
	Page<Comentarios> obtenerTodosPorFiltro(Pageable pageable , FiltroComentarioDTO filtroComentarioDTO);
	  
	@Query("SELECT coment FROM Comentarios coment ORDER BY coment.fechaAlta desc")
	Page<Comentarios> obtenerTodosPorPaginacion(Pageable pageable);
	
	@Query("SELECT coment FROM Comentarios coment ORDER BY coment.fechaAlta desc")
	Page<Comentarios> obtenerTodosPersonalizadoPorPaginacion(Pageable pageable);

	@Query("SELECT coment FROM Comentarios coment  INNER JOIN coment.producto produc where produc.id =:idProducto ORDER BY coment.fechaAlta desc")
	Page<Comentarios> obtenerTodosPersonalizadoPorPaginacion2(Pageable pageable, Long idProducto);

}