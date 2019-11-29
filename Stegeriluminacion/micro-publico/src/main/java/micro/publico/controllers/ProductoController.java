package micro.publico.controllers;



import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.micro.publico.producto.FiltroProductoDTO;
import interfaces.ACRUDEndPoints;
import interfaces.publico.ICRUDEndPoints;
import interfaces.publico.IFiltroEndPoints;
import micro.publico.services.ProductoService;
import modelo.producto.Producto;
import  steger.excepciones.controladas.ErrorInternoControlado;

@RestController
@RequestMapping(path = "/producto")
public class ProductoController  extends ACRUDEndPoints<Producto> implements ICRUDEndPoints<Producto,UUID>, IFiltroEndPoints<Producto,FiltroProductoDTO> {

	Logger logger = LoggerFactory.getLogger( ProductoController.class );
	
	@Autowired
	ProductoService iProducto;
 

	@Override
	@GetMapping(value = "/{uuid}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Producto>> obtener( @PathVariable("uuid") UUID uuid) {
		Respuesta<Producto> respuesta = null; 
		try {
			respuesta = iProducto.obtener(uuid);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

	 
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth, UUID id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<Respuesta<Producto>> crear(OAuth2Authentication auth,Producto t) { 
		throw new UnsupportedOperationException();
	}


	@Override
	public ResponseEntity<Respuesta<Producto>> actualizar(OAuth2Authentication auth,Producto t) {
		throw new UnsupportedOperationException();
	}


	
	
	/********************* PAGINABLES ***********************/
	@Override
	@PostMapping( 
			value = "/filtro"
			, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
			,produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Producto>>> filtrar(Pageable pageable , @RequestBody FiltroProductoDTO filtroProductoDTO) { 
		Respuesta<Page<Producto>> respuesta = null;
		try {
			respuesta = iProducto.obtenerTodosPersonalizado(pageable, filtroProductoDTO);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}
	
	
	@GetMapping (produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<Respuesta<Page<Producto>>> obtenerListaDeProductos( Pageable pageable ) { // size=10 page=1
		Respuesta<Page<Producto>> respuesta = null;
		try {
			respuesta = iProducto.obtenerTodos( pageable );
		    return ResponseEntity.status( respuesta.getCodigoHttp() ).body(respuesta); 
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( ErrorInternoControlado.error(ex.getMessage()) ); 
		}
	}



 
}
