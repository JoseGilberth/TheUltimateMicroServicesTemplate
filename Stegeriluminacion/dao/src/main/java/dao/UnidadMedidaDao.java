package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.producto.UnidadMedida;

public interface UnidadMedidaDao extends JpaRepository<UnidadMedida, Long> {

	@Query("SELECT obj FROM UnidadMedida obj ORDER BY obj.etiqueta desc")
	List<UnidadMedida> listAll();
}
