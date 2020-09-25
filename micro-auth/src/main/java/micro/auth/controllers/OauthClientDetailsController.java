package micro.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.auth.FiltroOauthClientDetailsDTO;
import dto.main.Respuesta;
import micro.auth.services.OauthClientDetailsService;
import model.auth.oauth2.OauthClientDetails;

@RestController
@RequestMapping(path = "/cliente/detalle")
public class OauthClientDetailsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientDetailsService oauthClientTokenService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<OauthClientDetails>>> filtrar(Pageable pageable,
			@RequestBody FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO) { // size=10 page=1
		Respuesta<Page<OauthClientDetails>> respuesta = oauthClientTokenService.filtrar(pageable,
				filtroOauthClientDetailsDTO);
		respuesta = oauthClientTokenService.filtrar(pageable, filtroOauthClientDetailsDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
