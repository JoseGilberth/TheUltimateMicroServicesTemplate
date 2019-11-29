package micro.auth.controllers;

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
import dto.micro.auth.FiltroOauthClientDetailsDTO;
import micro.auth.services.OauthClientDetailsService;
import modelo.auth.oauth2.OauthClientDetails;

@RestController
@RequestMapping(path = "/cliente/detalle")
public class OauthClientDetailsController  {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientDetailsService oauthClientTokenService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthClientDetails>>> filtrar(Pageable pageable, @RequestBody FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO) { // size=10 page=1
		Respuesta<Page<OauthClientDetails>> respuesta = oauthClientTokenService.filtrar(pageable, filtroOauthClientDetailsDTO);
		respuesta = oauthClientTokenService.filtrar(pageable, filtroOauthClientDetailsDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientDetails>> obtener(Long id) {
		Respuesta<OauthClientDetails> respuesta = oauthClientTokenService.obtener(id);
		respuesta = oauthClientTokenService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<List<OauthClientDetails>>> obtenerTodos() {
		Respuesta<List<OauthClientDetails>> respuesta = oauthClientTokenService.obtenerTodos();
		respuesta = oauthClientTokenService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientDetails>> crear(@RequestBody OauthClientDetails t) {
		Respuesta<OauthClientDetails> respuesta = oauthClientTokenService.crear(t);
		respuesta = oauthClientTokenService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientDetails>> actualizar(@RequestBody OauthClientDetails t) {
		Respuesta<OauthClientDetails> respuesta = oauthClientTokenService.actualizar(t);
		respuesta = oauthClientTokenService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = oauthClientTokenService.eliminar(id);
		respuesta = oauthClientTokenService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
