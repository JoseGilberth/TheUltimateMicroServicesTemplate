package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.auth.usuarios.publicos.ubicacion.TipoEntidad;
 

public interface TipoEntidadDao extends JpaRepository<TipoEntidad, Long> {

	@Query("SELECT obj FROM TipoEntidad obj ORDER BY obj.etiqueta desc")
	List<TipoEntidad> listAll();
	
}
