package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.producto.Categoria;

public interface CategoriaDao extends JpaRepository<Categoria, Long> {

	@Query("SELECT cate FROM Categoria cate ORDER BY cate.etiqueta desc")
	List<Categoria> listAll();

}
