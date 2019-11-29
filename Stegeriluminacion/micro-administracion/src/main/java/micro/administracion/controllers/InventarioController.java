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
import dto.micro.administracion.inventario.FiltroInventarioDTO;
import interfaces.ACRUDEndPoints;
import interfaces.administracion.ICRUDEndPoints;
import interfaces.administracion.IFiltroEndPoints;
import micro.administracion.services.InventarioService;
import modelo.inventario.Inventario;

@RestController
@RequestMapping(path = "/inventario")
public class InventarioController extends ACRUDEndPoints<Inventario>
		implements ICRUDEndPoints<Inventario, Long>, IFiltroEndPoints<Inventario, FiltroInventarioDTO> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InventarioService inventarioService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Inventario>>> filtrar(Pageable pageable, @RequestBody FiltroInventarioDTO filtroInventarioDTO) { // size=10 page=1
		Respuesta<Page<Inventario>> respuesta = null;
		respuesta = inventarioService.filtrar(pageable, filtroInventarioDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Inventario>> obtener( @PathVariable("id") Long id) {
		logger.info("ID: " + id);
		Respuesta<Inventario> respuesta = null;
		respuesta = inventarioService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(value = "/producto/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE }) 
	public ResponseEntity<Respuesta<Inventario>> obtenerPorIdProducto( @PathVariable("id") Long id) {
		Respuesta<Inventario> respuesta = null;
		respuesta = inventarioService.obtenerPorIdProducto(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
	

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<Inventario>>> obtenerTodos() {
		Respuesta<List<Inventario>> respuesta = null;
		respuesta = inventarioService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Inventario>> crear(@RequestBody Inventario t) {
		Respuesta<Inventario> respuesta = null;
		respuesta = inventarioService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Inventario>> actualizar(@RequestBody Inventario t) {
		Respuesta<Inventario> respuesta = null;
		respuesta = inventarioService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = inventarioService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
