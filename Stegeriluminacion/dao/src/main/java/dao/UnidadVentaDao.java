package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.producto.UnidadVenta;

public interface UnidadVentaDao extends JpaRepository<UnidadVenta, Long> {

	@Query("SELECT obj FROM UnidadVenta obj ORDER BY obj.etiqueta desc")
	List<UnidadVenta> listAll();

}
