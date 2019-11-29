package micro.publico.controllers;


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
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import micro.publico.services.RespuestaService;
import modelo.Respuesta;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;


@RestController
@RequestMapping(path = "/respuesta")
public class RespuestaController extends ACRUDEndPoints<Respuesta> implements ICRUDEndPoints<Respuesta,Long> {
	Logger logger = LoggerFactory.getLogger( RespuestaController.class );
	
	
	@Autowired
	RespuestaService IRespuesta;
	
	@Autowired
	UsuarioPublicoDao usuarioPublicoDao; 
	
	

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<dto.main.Respuesta<Respuesta>> obtener(Long id) {
		throw new UnsupportedOperationException();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<dto.main.Respuesta<Respuesta>> crear(OAuth2Authentication auth, Respuesta res) {
		dto.main.Respuesta<Respuesta> respuesta = null;  
		
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			res.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = IRespuesta.crear(res);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<dto.main.Respuesta<Respuesta>> actualizar(OAuth2Authentication auth, Respuesta res) {
		dto.main.Respuesta<Respuesta> respuesta = null;  
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			res.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = IRespuesta.actualizar(res);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<dto.main.Respuesta<Boolean>> borrar(OAuth2Authentication auth, Long idRespuesta) {
		dto.main.Respuesta<Boolean> respuesta = null; 
		
		String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
		
		respuesta = IRespuesta.borrarByUser(usuarioPublico.getId(), idRespuesta); //BORRA COMENTARIO BY USER
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
	
	/********************* PAGINABLES ***********************/
	
	
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<dto.main.Respuesta<Page<Respuesta>>> obtenerListaDeRespuestas( Pageable pageable ) { // size=10 page=1
		dto.main.Respuesta<Page<Respuesta>> respuesta = null;
		try {
			respuesta = IRespuesta.obtenerTodos( pageable );
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}

}
