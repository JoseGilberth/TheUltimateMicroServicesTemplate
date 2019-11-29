package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.auth.usuarios.publicos.ubicacion.TipoVialidad;

 
public interface TipoVialidadDao extends JpaRepository<TipoVialidad, Long> {

	@Query("SELECT obj FROM TipoVialidad obj ORDER BY obj.etiqueta desc")
	List<TipoVialidad> listAll();
}
