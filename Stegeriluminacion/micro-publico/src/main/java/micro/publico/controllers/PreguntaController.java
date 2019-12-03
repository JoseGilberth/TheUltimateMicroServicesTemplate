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
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import micro.publico.services.PreguntaService;
import modelo.Pregunta;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@RestController
@RequestMapping(path = "/pregunta")
public class PreguntaController extends ACRUDEndPoints<Pregunta> implements ICRUDEndPoints<Pregunta,Long>  {
	
	Logger logger = LoggerFactory.getLogger( PreguntaController.class );
	
	@Autowired
	PreguntaService IPregunta;
	
	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;

	

	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Pregunta>> obtener(Long id) {
		Respuesta<Pregunta> respuesta = null; 
		try {
			respuesta = IPregunta.obtener(id);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}



	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Pregunta>> crear(OAuth2Authentication auth, Pregunta pregunta) {
		Respuesta<Pregunta> respuesta = null;  
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			pregunta.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = IPregunta.crear(pregunta);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}



	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Pregunta>> actualizar(OAuth2Authentication auth, Pregunta pregunta) {
		Respuesta<Pregunta> respuesta = null;  
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			pregunta.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = IPregunta.actualizar(pregunta);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}



	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth, Long idPregunta) {
		Respuesta<Boolean> respuesta = null; 
		
		String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
		
		respuesta = IPregunta.borrarByUser(usuarioPublico.getId(), idPregunta); //BORRA COMENTARIO BY USER
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
	
	@Override
	public ResponseEntity<Respuesta<List<Pregunta>>> obtenerTodos() {
	    throw new UnsupportedOperationException();
	}
	
	/********************* PAGINABLES ***********************/
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<Respuesta<Page<Pregunta>>> obtenerListaDePreguntas( Pageable pageable ) { // size=10 page=1
		Respuesta<Page<Pregunta>> respuesta = null;
		try {
			respuesta = IPregunta.obtenerTodos(pageable);
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}



	
}
