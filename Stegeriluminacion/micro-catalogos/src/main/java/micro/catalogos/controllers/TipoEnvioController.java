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
import micro.catalogos.services.TipoEnvioService;
import modelo.solicitud.compra.TipoEnvio;

@RestController
@RequestMapping(path = "/tipoenvio")
public class TipoEnvioController extends ACRUDEndPoints<TipoEnvio> implements ICRUDEndPoints<TipoEnvio, Long> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TipoEnvioService tipoEnvioService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoEnvio>> obtener(Long id) {
		Respuesta<TipoEnvio> respuesta = null;
		respuesta = tipoEnvioService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<TipoEnvio>>> obtenerTodos() {
		Respuesta<List<TipoEnvio>> respuesta = null;
		respuesta = tipoEnvioService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoEnvio>> crear(@RequestBody TipoEnvio t) {
		Respuesta<TipoEnvio> respuesta = null;
		respuesta = tipoEnvioService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<modelo.solicitud.compra.TipoEnvio>> actualizar(@RequestBody TipoEnvio t) {
		Respuesta<TipoEnvio> respuesta = null;
		respuesta = tipoEnvioService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = tipoEnvioService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
