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
import dto.micro.administracion.proveedor.FiltroProveedorDTO;
import interfaces.ACRUDEndPoints;
import interfaces.administracion.ICRUDEndPoints;
import interfaces.administracion.IFiltroEndPoints;
import micro.administracion.services.ProveedorService;
import modelo.proveedor.Proveedor;

@RestController
@RequestMapping(path = "/proveedor")
public class ProveedorController extends ACRUDEndPoints<Proveedor>
		implements ICRUDEndPoints<Proveedor, Long>, IFiltroEndPoints<Proveedor, FiltroProveedorDTO> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProveedorService proveedorService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<Proveedor>>> filtrar(Pageable pageable,
			@RequestBody FiltroProveedorDTO filtroProveedorDTO) { // size=10 page=1
		Respuesta<Page<Proveedor>> respuesta = null;
		respuesta = proveedorService.filtrar(pageable, filtroProveedorDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Proveedor>> obtener(Long id) {
		Respuesta<Proveedor> respuesta = null;
		respuesta = proveedorService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<List<Proveedor>>> obtenerTodos() {
		Respuesta<List<Proveedor>> respuesta = null;
		respuesta = proveedorService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Proveedor>> crear(@RequestBody Proveedor t) {
		Respuesta<Proveedor> respuesta = null;
		respuesta = proveedorService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Proveedor>> actualizar(@RequestBody Proveedor t) {
		Respuesta<Proveedor> respuesta = null;
		respuesta = proveedorService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@Override
	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = proveedorService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
