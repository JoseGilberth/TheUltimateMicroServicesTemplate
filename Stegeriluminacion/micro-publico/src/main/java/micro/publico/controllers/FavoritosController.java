package micro.publico.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.micro.publico.favoritos.FiltroFavoritosDTO;
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import interfaces.publico.IFiltroEndPoints;
import modelo.Comentarios;
import modelo.Favoritos;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;
import micro.publico.services.FavoritosService;

@RestController
@RequestMapping(path = "/favoritos")
public class FavoritosController extends ACRUDEndPoints<Favoritos> implements ICRUDEndPoints<Favoritos,Long>, IFiltroEndPoints<Favoritos,FiltroFavoritosDTO> {
	
	Logger logger = LoggerFactory.getLogger( FavoritosController.class );
	
	@Autowired
	FavoritosService Ifavoritos;
	
	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Favoritos>> obtener(Long id) {
		throw new UnsupportedOperationException();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Favoritos>> crear(OAuth2Authentication auth, Favoritos favorit) {
		Respuesta<Favoritos> respuesta = null;  
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			favorit.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = Ifavoritos.crear(favorit);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Favoritos>> actualizar(OAuth2Authentication auth, Favoritos t) {
		throw new UnsupportedOperationException();
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth, Long idFavoritos) {
			Respuesta<Boolean> respuesta = null; 
		
		String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
		
		respuesta = Ifavoritos.borrarByUser(usuarioPublico.getId(), idFavoritos); //BORRA COMENTARIO BY USER
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}


	@Override
	public ResponseEntity<Respuesta<List<Favoritos>>> obtenerTodos() {
	    throw new UnsupportedOperationException();
	}
	
	
	/********************* PAGINABLES ***********************/
	@Override
	@PostMapping( 
			value = "/filtro"
			, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
			,produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Favoritos>>> filtrar(Pageable pageable, FiltroFavoritosDTO FiltroFavoritosDTO) {
		Respuesta<Page<Favoritos>> respuesta = null;
		
		try {
			respuesta = Ifavoritos.obtenerTodosPersonalizado(pageable, FiltroFavoritosDTO);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}
	
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<Respuesta<Page<Favoritos>>> obtenerListaDeFavoritos( Pageable pageable ) { // size=10 page=1
		Respuesta<Page<Favoritos>> respuesta = null;
		try {
			respuesta = Ifavoritos.obtenerTodos( pageable );
			
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}

}
