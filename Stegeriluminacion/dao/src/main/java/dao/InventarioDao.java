package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import modelo.inventario.Inventario;

public interface InventarioDao extends JpaRepository<Inventario, Long> {

	@Query("SELECT inv FROM Inventario inv ORDER BY inv.fechaAlta desc")
	Page<Inventario> obtenerTodosPorPaginacion(Pageable pageable);

	@Query("SELECT inv FROM Inventario inv INNER JOIN inv.producto product WHERE product.id =:idProducto ORDER BY inv.fechaAlta desc")
	Inventario obtenerPorIdProducto( @Param("idProducto") Long idProducto);

	
	
}
