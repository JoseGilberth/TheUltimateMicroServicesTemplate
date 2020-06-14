package micro.auth.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthAccessTokenDTO;
import dto.micro.auth.OauthAccessTokenDTO;
import micro.auth.services.OauthAccessTokenService;
import modelo.auth.oauth2.OauthAccessToken;

@RestController
@RequestMapping(path = "/sesiones")
public class OauthAccessTokenController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenService oauthAccessTokenService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthAccessTokenDTO>>> filtrar(Pageable pageable,
			@RequestBody FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) { // size=10 page=1
		Respuesta<Page<OauthAccessTokenDTO>> respuesta = oauthAccessTokenService.filtrar(pageable,
				filtroOauthAccessTokenDTO);
		respuesta = oauthAccessTokenService.filtrar(pageable, filtroOauthAccessTokenDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(value = "/cerrar", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<Boolean>> revocarToken(OAuth2Authentication auth) {
		Respuesta<Boolean> respuesta = oauthAccessTokenService.revocar(auth);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(value = "/token", consumes = { MediaType.TEXT_PLAIN_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<Boolean>> revocarToken(@RequestBody String token) {
		Respuesta<Boolean> respuesta = oauthAccessTokenService.revocar(token);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<List<OauthAccessToken>>> obtenerTodos() {
		Respuesta<List<OauthAccessToken>> respuesta = oauthAccessTokenService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthAccessToken>> crear(@RequestBody OauthAccessToken t) {
		Respuesta<OauthAccessToken> respuesta = oauthAccessTokenService.crear(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<OauthAccessToken>> actualizar(@RequestBody OauthAccessToken t) {
		Respuesta<OauthAccessToken> respuesta = oauthAccessTokenService.actualizar(t);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
