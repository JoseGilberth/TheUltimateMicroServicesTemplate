package micro.usuarios.publicos.controllers;

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
import interfaces.abstracts.ACrudEndPoints;
import micro.usuarios.publicos.interfaces.IRegistroService;
import model.auth.usuarios.publicos.UsuarioPublico;
import model.validations.interfaces.OnCreate;

@RestController
@RequestMapping(path = "/usuarios/publico/registro")
public class RegistroController extends ACrudEndPoints<RegistroController> {

	Logger logger = LoggerFactory.getLogger(RegistroController.class);

	@Autowired
	IRegistroService registroService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<UsuarioPublico>> registrar(
			@Validated(OnCreate.class) @RequestBody UsuarioPublico usuarioPublico) {
		usuarioPublico.setCorreo(usuarioPublico.getCorreo().replaceAll("\\s", ""));
		Respuesta<UsuarioPublico> respuesta = registroService.crear(usuarioPublico);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@GetMapping(value = "/activar/{token}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> activarUsuario(@PathVariable("token") String token) {
		Respuesta<String> respuesta = registroService.activarUsuario(token);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta.getCuerpo());
	}

}
