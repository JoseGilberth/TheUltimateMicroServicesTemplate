package micro.catalogos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import interfaces.ACRUDEndPoints;
import interfaces.administracion.ICRUDEndPoints;
import micro.catalogos.services.TipoAsentamientoService;
import modelo.auth.usuarios.publicos.ubicacion.TipoAsentamiento; 

@RestController
@RequestMapping(path = "/tipoasentamiento")
public class TipoAsentamientoController extends ACRUDEndPoints<TipoAsentamiento>
		implements ICRUDEndPoints<TipoAsentamiento, Long> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TipoAsentamientoService tipoAsentamientoService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoAsentamiento>> obtener(Long id) {
		Respuesta<TipoAsentamiento> respuesta = null;
		respuesta = tipoAsentamientoService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<TipoAsentamiento>>> obtenerTodos() {
		Respuesta<List<TipoAsentamiento>> respuesta = null;
		respuesta = tipoAsentamientoService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoAsentamiento>> crear(@RequestBody TipoAsentamiento t) {
		Respuesta<TipoAsentamiento> respuesta = null;
		respuesta = tipoAsentamientoService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoAsentamiento>> actualizar(@RequestBody TipoAsentamiento t) {
		Respuesta<TipoAsentamiento> respuesta = null;
		respuesta = tipoAsentamientoService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = tipoAsentamientoService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
