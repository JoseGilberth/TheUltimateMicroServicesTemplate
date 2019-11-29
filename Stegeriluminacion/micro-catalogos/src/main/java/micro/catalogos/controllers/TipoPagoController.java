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
import micro.catalogos.services.TipoPagoService;
import modelo.solicitud.compra.TipoPago;

@RestController
@RequestMapping(path = "/tipopago")
public class TipoPagoController extends ACRUDEndPoints<TipoPago> implements ICRUDEndPoints<TipoPago, Long> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TipoPagoService tipoPagoService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoPago>> obtener(Long id) {
		Respuesta<TipoPago> respuesta = null;
		respuesta = tipoPagoService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<TipoPago>>> obtenerTodos() {
		Respuesta<List<TipoPago>> respuesta = null;
		respuesta = tipoPagoService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoPago>> crear(@RequestBody TipoPago t) {
		Respuesta<TipoPago> respuesta = null;
		respuesta = tipoPagoService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoPago>> actualizar(@RequestBody TipoPago t) {
		Respuesta<TipoPago> respuesta = null;
		respuesta = tipoPagoService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = tipoPagoService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
}
