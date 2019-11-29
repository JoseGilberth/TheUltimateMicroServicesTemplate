package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import modelo.producto.Producto;

public interface ProductoDao extends JpaRepository<Producto, Long> {
	
	@Query("SELECT prod FROM Producto prod WHERE prod.activo =:activo ORDER BY prod.fechaAlta desc")
	Page<Producto> obtenerTodosPorPaginacion(Pageable pageable, @Param("activo") boolean activo);

	@Query("SELECT prod " + " FROM Producto prod" + " ORDER BY prod.fechaAlta desc")
	Page<Producto> obtenerTodosPersonalizadoPorPaginacion(Pageable pageable);

}
