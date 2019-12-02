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
import dto.micro.publico.solicitudes.FiltroSolicitudDTO;
import steger.excepciones.controladas.ErrorInternoControlado;
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import interfaces.publico.IFiltroEndPoints;
import micro.publico.services.SolicitudDeCompraService;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.solicitud.compra.SolicitudDeCompra;


@RestController
@RequestMapping(path = "/solicitudDeCompra")
public class SolicitudDeCompraController  extends ACRUDEndPoints<SolicitudDeCompra> implements ICRUDEndPoints<SolicitudDeCompra,Long>, IFiltroEndPoints<SolicitudDeCompra,FiltroSolicitudDTO> {

	Logger logger = LoggerFactory.getLogger( SolicitudDeCompraController.class );
	
	@Autowired
	SolicitudDeCompraService ISolicitud;

	@Autowired
	UsuarioPublicoDao usuarioPublicoDao; 

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<SolicitudDeCompra>> obtener(Long id) {
		Respuesta<SolicitudDeCompra> respuesta = null; 
		respuesta = ISolicitud.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<SolicitudDeCompra>> crear(OAuth2Authentication auth, SolicitudDeCompra sdc) { 
		Respuesta<SolicitudDeCompra> respuesta = null;  
		try { 
			
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			sdc.setUsuarioPublico(usuarioPublico); // SE REMPLAZA POR EL QUE GVENGA AQUI
			
			respuesta = ISolicitud.crear(sdc);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
		
		
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<SolicitudDeCompra>> actualizar(OAuth2Authentication auth,SolicitudDeCompra sdc) {
		throw new UnsupportedOperationException();
	
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth, Long idSolicitud) {
		Respuesta<Boolean> respuesta = null; 
		
		String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
		
		respuesta = ISolicitud.borrarByUser(usuarioPublico.getId(), idSolicitud); //BORRA Solicitud BY USER
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	public ResponseEntity<Respuesta<List<SolicitudDeCompra>>> obtenerTodos() {
	    throw new UnsupportedOperationException();
	}
	
	
	/********************* PAGINABLES ***********************/
	@Override
	public ResponseEntity<Respuesta<Page<SolicitudDeCompra>>> filtrar(Pageable pageable, FiltroSolicitudDTO filtroSolicitudDTO) {
		Respuesta<Page<SolicitudDeCompra>> respuesta = null;
		try {
			respuesta = ISolicitud.obtenerSolicitudesPersonalizada(pageable, filtroSolicitudDTO);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}
	
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<Respuesta<Page<SolicitudDeCompra>>> obtenerListaDeSolicitudes( Pageable pageable ) { // size=10 page=1
		Respuesta<Page<SolicitudDeCompra>> respuesta = null;
		try {
			respuesta = ISolicitud.obtenerSolicitudesTodas(pageable);
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}
	
}
