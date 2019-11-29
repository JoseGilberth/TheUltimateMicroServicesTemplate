package micro.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.publico.favoritos.FiltroFavoritosDTO;
import modelo.Favoritos;

public interface IFavoritos {
	
	Respuesta<Page<Favoritos>> obtenerTodos(Pageable pageable);
	
	Respuesta<Page<Favoritos>> obtenerTodosPersonalizado(Pageable pageable,FiltroFavoritosDTO filtroFavoritosDTO);
	
	Respuesta<Boolean> borrarByUser(int idUsuario, Long idFavoritos);
}
