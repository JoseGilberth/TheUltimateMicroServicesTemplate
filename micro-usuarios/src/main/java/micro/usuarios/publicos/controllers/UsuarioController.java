package micro.usuarios.publicos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.usuarios.FiltroUsuarioPublicoDTO;
import dto.usuarios.PatchUsuarioPublicoDTO;
import interfaces.abstracts.ACrudEndPoints;
import micro.usuarios.publicos.interfaces.IUsuarioService;
import model.auth.usuarios.publicos.UsuarioPublico;
import model.validations.interfaces.OnCreate;
import model.validations.interfaces.OnUpdate;

@RestController
@RequestMapping(path = "/usuarios/publico")
public class UsuarioController extends ACrudEndPoints<UsuarioController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUsuarioService usuarioService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Page<UsuarioPublico>>> filtrar(Pageable pageable,
			@RequestBody FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO) { // size=10 page=1
		Respuesta<Page<UsuarioPublico>> respuesta = usuarioService.filtrar(pageable, filtroUsuarioPublicoDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	// PUBLICO
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> obtenerPorToken(OAuth2Authentication auth) {
		Respuesta<UsuarioPublico> respuesta = usuarioService.obtenerPorToken(auth);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	// PUBLICO
	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> actualizar(OAuth2Authentication auth,
			@RequestBody PatchUsuarioPublicoDTO patchUsuarioPublicoDTO) {
		Respuesta<UsuarioPublico> respuesta = usuarioService.actualizar(patchUsuarioPublicoDTO, auth);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> crearPorAdministrador(
			@Validated(OnCreate.class) @RequestBody UsuarioPublico usuarioPublico) {
		Respuesta<UsuarioPublico> respuesta = usuarioService.crear(usuarioPublico);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> actualizarPorAdministrador(@PathVariable("id") Long id,
			@Validated(OnUpdate.class) @RequestBody UsuarioPublico usuarioPublico) {
		Respuesta<UsuarioPublico> respuesta = usuarioService.actualizar(id, usuarioPublico);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Boolean>> borrarPorAdministrador(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = usuarioService.borrar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
