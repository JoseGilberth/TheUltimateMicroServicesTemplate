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
import dto.micro.administracion.oauthaccesstoken.FiltroOauthAccessTokenDTO;
import micro.auth.services.OauthAccessTokenService;
import modelo.auth.oauth2.OauthAccessToken;

@RestController
@RequestMapping(path = "/sesiones")
public class OauthAccessTokenController  {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenService oauthAccessTokenService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthAccessToken>>> filtrar(Pageable pageable, @RequestBody FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) { // size=10 page=1
		Respuesta<Page<OauthAccessToken>> respuesta = oauthAccessTokenService.filtrar(pageable, filtroOauthAccessTokenDTO);
		respuesta = oauthAccessTokenService.filtrar(pageable, filtroOauthAccessTokenDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthAccessToken>> obtener(Long id) {
		Respuesta<OauthAccessToken> respuesta = oauthAccessTokenService.obtener(id);
		respuesta = oauthAccessTokenService.obtener(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<List<OauthAccessToken>>> obtenerTodos() {
		Respuesta<List<OauthAccessToken>> respuesta = oauthAccessTokenService.obtenerTodos();
		respuesta = oauthAccessTokenService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthAccessToken>> crear(@RequestBody OauthAccessToken t) {
		Respuesta<OauthAccessToken> respuesta = oauthAccessTokenService.crear(t);
		respuesta = oauthAccessTokenService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthAccessToken>> actualizar(@RequestBody OauthAccessToken t) {
		Respuesta<OauthAccessToken> respuesta = oauthAccessTokenService.actualizar(t);
		respuesta = oauthAccessTokenService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}
 
	@DeleteMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = oauthAccessTokenService.eliminar(id);
		respuesta = oauthAccessTokenService.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
