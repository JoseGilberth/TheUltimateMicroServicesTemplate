package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.auth.usuarios.publicos.ubicacion.TipoMunicipio;

 
public interface TipoMunicipioDao extends JpaRepository<TipoMunicipio, Long> {

	@Query("SELECT obj FROM TipoMunicipio obj ORDER BY obj.etiqueta desc")
	List<TipoMunicipio> listAll();
}
