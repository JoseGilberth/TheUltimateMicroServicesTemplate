package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.auth.usuarios.publicos.ubicacion.TipoAsentamiento;
 

public interface TipoAsentamientoDao extends JpaRepository<TipoAsentamiento, Long> {

	@Query("SELECT obj FROM TipoAsentamiento obj ORDER BY obj.etiqueta desc")
	List<TipoAsentamiento> listAll();
}
