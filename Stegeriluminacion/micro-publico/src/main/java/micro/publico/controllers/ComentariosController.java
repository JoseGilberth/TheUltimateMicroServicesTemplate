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
import dto.micro.publico.comentarios.FiltroComentarioDTO;
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import interfaces.publico.IFiltroEndPoints;
import micro.publico.services.ComentariosService;
import modelo.Comentarios;
import steger.excepciones.controladas.ErrorInternoControlado;

@RestController
@RequestMapping(path = "/comentarios")
public class ComentariosController  extends ACRUDEndPoints<Comentarios> implements ICRUDEndPoints<Comentarios,Long>, IFiltroEndPoints<Comentarios,FiltroComentarioDTO> {
	
	Logger logger = LoggerFactory.getLogger( ComentariosController.class );
	
	@Autowired
	ComentariosService IComentario;
	
	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;
	

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Comentarios>> obtener(Long id) {
		throw new UnsupportedOperationException();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Comentarios>> crear(OAuth2Authentication auth, Comentarios com) {
		Respuesta<Comentarios> respuesta = null;   
		respuesta = IComentario.crearPorUsuario(auth , com); 
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Comentarios>> actualizar(OAuth2Authentication auth, Comentarios com) {
		Respuesta<Comentarios> respuesta = null;   
		respuesta = IComentario.actualizarPorUsuario(auth,com);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth, Long idComentario) {
		Respuesta<Boolean> respuesta = null;
		respuesta = IComentario.eliminarPorUsuario(auth, idComentario); //BORRA COMENTARIO BY USER
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	public ResponseEntity<Respuesta<List<Comentarios>>> obtenerTodos() {
	    throw new UnsupportedOperationException();
	}
	
	/********************* PAGINABLES ***********************/
	@Override
	@PostMapping( 
			value = "/filtro"
			, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
			,produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Comentarios>>> filtrar(Pageable pageable, FiltroComentarioDTO filtroComentarioDTO) {
		Respuesta<Page<Comentarios>> respuesta = null;
		
		try {
			respuesta = IComentario.obtenerTodosPersonalizado(pageable, filtroComentarioDTO);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}
	
	
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<Respuesta<Page<Comentarios>>> obtenerListaDeComentarios( Pageable pageable ) { // size=10 page=1
		Respuesta<Page<Comentarios>> respuesta = null;
		try {
			respuesta = IComentario.obtenerTodos( pageable );
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}



}
