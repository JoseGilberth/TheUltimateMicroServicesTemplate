package micro.usuarios.publico.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import interfaces.ACRUDEndPoints;
import micro.usuarios.publico.services.interfaces.IRegistroService;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import utils.validaciones.interfaces.OnCreate;

@RestController
@RequestMapping(path = "/usuarios/publico/registro")
public class RegistroController extends ACRUDEndPoints<RegistroController> {

	Logger logger = LoggerFactory.getLogger(RegistroController.class);

	@Autowired
	IRegistroService registroService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> registrar(
			@Validated(OnCreate.class) @RequestBody UsuarioPublico usuarioPublico) {
		usuarioPublico.setCorreo(usuarioPublico.getCorreo().replaceAll("\\s", ""));
		Respuesta<UsuarioPublico> respuesta = registroService.crearRegistro(usuarioPublico);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(value = "/activar/{token}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> activarUsuario(@PathVariable("token") String token) {
		Respuesta<String> respuesta = null;
		respuesta = registroService.activarUsuario(token);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta.getCuerpo());
	}

}
