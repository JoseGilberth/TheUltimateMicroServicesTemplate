package dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import modelo.proveedor.Proveedor;
 

public interface ProveedorDao extends JpaRepository<Proveedor, Long> {
 
	@Query("SELECT prov FROM Proveedor prov WHERE prov.activo =:activo ORDER BY prov.fechaAlta desc")
	Page<Proveedor> obtenerTodosPorPaginacion(Pageable pageable, @Param("activo") boolean activo);

	
}
 