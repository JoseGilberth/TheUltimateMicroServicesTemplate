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
import micro.catalogos.services.TipoMunicipioService;
import modelo.auth.usuarios.publicos.ubicacion.TipoMunicipio; 

@RestController
@RequestMapping(path = "/tipomunicipio")
public class TipoMunicipioController extends ACRUDEndPoints<TipoMunicipio> implements ICRUDEndPoints<TipoMunicipio, Long> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TipoMunicipioService tipoMunicipioService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoMunicipio>> obtener(Long id) {
		Respuesta<TipoMunicipio> respuesta = null;
		respuesta = tipoMunicipioService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<List<TipoMunicipio>>> obtenerTodos() {
		Respuesta<List<TipoMunicipio>> respuesta = null;
		respuesta = tipoMunicipioService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoMunicipio>> crear(@RequestBody TipoMunicipio t) {
		Respuesta<TipoMunicipio> respuesta = null;
		respuesta = tipoMunicipioService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<TipoMunicipio>> actualizar(@RequestBody TipoMunicipio t) {
		Respuesta<TipoMunicipio> respuesta = null;
		respuesta = tipoMunicipioService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Override
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = tipoMunicipioService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
}
