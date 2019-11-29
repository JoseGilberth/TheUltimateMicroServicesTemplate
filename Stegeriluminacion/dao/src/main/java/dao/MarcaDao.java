package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.producto.Marca;

public interface MarcaDao extends JpaRepository<Marca, Long> {

	@Query("SELECT marca FROM Marca marca ORDER BY marca.etiqueta desc")
	List<Marca> listAll();
}
