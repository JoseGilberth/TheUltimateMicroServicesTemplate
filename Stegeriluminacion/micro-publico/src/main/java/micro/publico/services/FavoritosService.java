package micro.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.FavoritosDao;
import dto.main.Respuesta;
import dto.micro.publico.favoritos.FiltroFavoritosDTO;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.IFavoritos;
import modelo.Favoritos;
import steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class FavoritosService extends ACrud<Favoritos, Long> implements IFavoritos  {
	
	@Autowired
	FavoritosDao favoritDao;
	

	Logger logger = LoggerFactory.getLogger( FavoritosService.class );


	@Override
	public Respuesta<Page<Favoritos>> obtenerTodos(Pageable pageable) {
		Respuesta<Page<Favoritos>> respuesta = new Respuesta<Page<Favoritos>>();
		try {
			Page<Favoritos> favoritos = favoritDao.obtenerTodosPorPaginacion(pageable);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(favoritos);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("favoritos.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Page<Favoritos>> obtenerTodosPersonalizado(Pageable pageable,
			FiltroFavoritosDTO filtroFavoritosDTO) {
Respuesta<Page<Favoritos>> respuesta = new Respuesta<Page<Favoritos>>();
		
		try {
			Page<Favoritos> favoritos = favoritDao.obtenerTodosPersonalizadoPorPaginacion(pageable);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(favoritos);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("favoritos.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Boolean> borrarByUser(int idUsuario, Long idFavoritos) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		try {
			boolean favoritos = false;//favoritDao.borrarByUsuario(idUsuario, idFavoritos);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(favoritos);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("favoritos.borrado") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
	
	
 
}
