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
import dto.micro.auth.FiltroOauthClientTokenDTO;
import micro.auth.services.OauthClientTokenService;
import modelo.auth.oauth2.OauthClientToken;

@RestController
@RequestMapping(path = "/cliente/token")
public class OauthClientTokenController  {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientTokenService oauthClientTokenService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthClientToken>>> filtrar(Pageable pageable, @RequestBody FiltroOauthClientTokenDTO filtroOauthClientTokenDTO) { // size=10 page=1
		Respuesta<Page<OauthClientToken>> respuesta = oauthClientTokenService.filtrar(pageable, filtroOauthClientTokenDTO);
		respuesta = oauthClientTokenService.filtrar(pageable, filtroOauthClientTokenDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientToken>> obtener(Long id) {
		Respuesta<OauthClientToken> respuesta = oauthClientTokenService.obtener(id);
		respuesta = oauthClientTokenService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<List<OauthClientToken>>> obtenerTodos() {
		Respuesta<List<OauthClientToken>> respuesta = oauthClientTokenService.obtenerTodos();
		respuesta = oauthClientTokenService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientToken>> crear(@RequestBody OauthClientToken t) {
		Respuesta<OauthClientToken> respuesta = oauthClientTokenService.crear(t);
		respuesta = oauthClientTokenService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthClientToken>> actualizar(@RequestBody OauthClientToken t) {
		Respuesta<OauthClientToken> respuesta = oauthClientTokenService.actualizar(t);
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
