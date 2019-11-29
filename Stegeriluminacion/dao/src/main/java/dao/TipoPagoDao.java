package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.solicitud.compra.TipoPago;

public interface TipoPagoDao extends JpaRepository<TipoPago, Long> {

	@Query("SELECT obj FROM TipoPago obj ORDER BY obj.etiqueta desc")
	List<TipoPago> listAll();

}
