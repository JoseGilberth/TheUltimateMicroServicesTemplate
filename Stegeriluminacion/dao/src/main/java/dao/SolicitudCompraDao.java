package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.solicitud.compra.SolicitudDeCompra;

public interface SolicitudCompraDao extends JpaRepository<SolicitudDeCompra, Long>  {
	
	@Query("SELECT prod " + " FROM Producto prod" + " ORDER BY prod.fechaAlta desc")
	Page<SolicitudDeCompra> obtenerTodosPorPaginacion(Pageable pageable);
	
	@Query("SELECT prod " + " FROM Producto prod" + " ORDER BY prod.fechaAlta desc")
	Page<SolicitudDeCompra> obtenerTodosPersonalizadoPorPaginacion(Pageable pageable);
	
	@Query("SELECT com FROM Comentarios com ORDER BY com.fechaAlta desc")
	Boolean borrarByUsuario(int idUsuario, Long idSolicitud);
}
