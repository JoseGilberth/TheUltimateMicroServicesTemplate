package micro.auth.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.auth.FiltroOauthAccessTokenDTO;
import dto.auth.OauthAccessTokenDTO;
import dto.main.Respuesta;
import micro.auth.services.OauthAccessTokenService;

@RestController
@RequestMapping(path = "/sesiones")
public class OauthAccessTokenController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenService oauthAccessTokenService;

	// OBTENER TODOS
	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthAccessTokenDTO>>> filtrar(Pageable pageable,
			@RequestBody FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) { // size=10 page=1
		Respuesta<Page<OauthAccessTokenDTO>> respuesta = oauthAccessTokenService.filtrar(pageable,
				filtroOauthAccessTokenDTO);
		respuesta = oauthAccessTokenService.filtrar(pageable, filtroOauthAccessTokenDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	// CERRAR SESION
	@GetMapping(value = "/cerrar", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Boolean>> cerrarSesion(OAuth2Authentication auth) {
		Respuesta<Boolean> respuesta = oauthAccessTokenService.cerrarSesion(auth);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	// REVOCAR MANUAL
	@PostMapping(value = "/token", consumes = { MediaType.TEXT_PLAIN_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Boolean>> revocarToken(@RequestBody String token) {
		Respuesta<Boolean> respuesta = oauthAccessTokenService.revocar(token);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
