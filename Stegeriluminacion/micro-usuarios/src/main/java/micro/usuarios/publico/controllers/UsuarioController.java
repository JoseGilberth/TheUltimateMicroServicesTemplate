package micro.usuarios.publico.controllers;

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
import dto.micro.usuarios.FiltroUsuarioPublicoDTO;
import micro.usuarios.publico.services.UsuarioService;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@RestController
@RequestMapping(path = "/usuarios/publico")
public class UsuarioController {

	Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	UsuarioService usuarioService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<Page<UsuarioPublico>>> filtrar(Pageable pageable, @RequestBody FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO) { // size=10 page=1
		Respuesta<Page<UsuarioPublico>> respuesta = usuarioService.filtrar(pageable, filtroUsuarioPublicoDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> obtenerPorToken(OAuth2Authentication auth) {
		Respuesta<UsuarioPublico> respuesta = null;
		try {
			respuesta = usuarioService.obtenerPorToken(auth);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> actualizar(@RequestBody UsuarioPublico usuarioPublico,
			OAuth2Authentication auth) {

		Respuesta<UsuarioPublico> respuesta = null;
		try {
			respuesta = usuarioService.actualizar(usuarioPublico, auth);
			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error(ex.getMessage()));
		}
	}

}
