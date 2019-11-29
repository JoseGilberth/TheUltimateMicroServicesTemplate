package dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.Favoritos;

public interface FavoritosDao extends JpaRepository<Favoritos, Long>{
	
	@Query("SELECT fav FROM Favoritos fav ORDER BY fav.fechaAlta desc")
	Page<Favoritos> obtenerTodosPorPaginacion(Pageable pageable);
	
	@Query("SELECT prod FROM Producto prod  ORDER BY prod.fechaAlta desc")
	Page<Favoritos> obtenerTodosPersonalizadoPorPaginacion(Pageable pageable);
	
	//@Query("DELETE FROM Favoritos fav INNER JOIN fav.usuarioPublico up WHERE fav.id =:idFavoritos AND up.id =:idUsuario")
	//Boolean borrarByUsuario(@Param("idUsuario") int idUsuario,@Param("idFavoritos")  Long idFavoritos);
}
