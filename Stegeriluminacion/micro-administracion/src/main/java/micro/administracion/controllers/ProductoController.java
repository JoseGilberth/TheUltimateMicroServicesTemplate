package micro.administracion.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.micro.administracion.producto.CrearProductoDTO;
import dto.micro.administracion.producto.FiltroProductoDTO;
import interfaces.ACRUDEndPoints;
import interfaces.administracion.ICRUDEndPoints;
import interfaces.administracion.IFiltroEndPoints;
import micro.administracion.services.ProductoService;
import modelo.producto.Producto;

@RestController
@RequestMapping(path = "/producto")
public class ProductoController extends ACRUDEndPoints<Producto> implements ICRUDEndPoints<Producto, Long>, IFiltroEndPoints<Producto, FiltroProductoDTO> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductoService productoService;

	@Override
	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Producto>>> filtrar(Pageable pageable,	@RequestBody FiltroProductoDTO filtroProductoDTO) { // size=10 page=1
		Respuesta<Page<Producto>> respuesta = null;
		respuesta = productoService.filtrar(pageable, filtroProductoDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);

	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Producto>> obtener(Long id) {
		Respuesta<Producto> respuesta = null; 
		respuesta = productoService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<Producto>>> obtenerTodos() {
		Respuesta<List<Producto>> respuesta = null; 
		respuesta = productoService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Producto>> crear(@RequestBody Producto t) {
		Respuesta<Producto> respuesta = null; 
		respuesta = productoService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}
	
	@PostMapping(value = "/crear" , 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Producto>> crearProductoInventario(@RequestBody CrearProductoDTO t) {
		Respuesta<Producto> respuesta = null; 
		respuesta = productoService.crearProductoInventario(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}
	

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Producto>> actualizar(@RequestBody Producto t) {
		Respuesta<Producto> respuesta = null; 
		respuesta = productoService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null; 
		respuesta = productoService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta); 
	}

}
