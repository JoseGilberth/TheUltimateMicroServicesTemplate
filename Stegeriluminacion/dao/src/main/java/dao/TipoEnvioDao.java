package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.solicitud.compra.TipoEnvio;

public interface TipoEnvioDao extends JpaRepository<TipoEnvio, Long> {

	@Query("SELECT obj FROM TipoEnvio obj ORDER BY obj.etiqueta desc")
	List<TipoEnvio> listAll();

}
