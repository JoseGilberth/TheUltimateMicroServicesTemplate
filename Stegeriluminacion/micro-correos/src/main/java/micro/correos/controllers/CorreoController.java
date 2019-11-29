package micro.correos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.micro.correo.Mail;
import  steger.excepciones.controladas.ErrorInternoControlado;
import micro.correos._config.languaje.Translator;
import micro.correos.services.CorreoService;

@RestController
@RequestMapping(path = "/correo")
public class CorreoController {

	Logger logger = LoggerFactory.getLogger(CorreoController.class);

	@Autowired
	private CorreoService mailService;

	@GetMapping(value = "/heartbit", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> heartBit() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Respuesta<Boolean>> registro(@RequestBody Mail correo) {
		
		logger.info("Entrando al correo");
		
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		try {

			mailService.registro(correo);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(true);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("correo.enviado"));

			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error( ex.getMessage()));
		}
	}

	@PostMapping(value = "/cambiar/password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Respuesta<Boolean>> cambiarContraseña(@RequestBody Mail correo) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		try {
			mailService.cambiarContraseña(correo);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(true);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("correo.enviado"));

			return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ErrorInternoControlado.error( ex.getMessage()));
		}
	}

}
