package micro.correos.controllers;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import dto.main.Mail;
import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.correos.services.interfaces.ICorreoService;

@RestController
@RequestMapping(path = "/correo")
public class CorreoController extends ACrudEndPoints<CorreoController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ICorreoService iCorreoService;

	@PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<Boolean>> registro(@RequestBody Mail correo) throws MessagingException {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		respuesta = iCorreoService.registro(correo);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(value = "/cambiar/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta<Boolean>> cambiarContraseña(@RequestBody Mail correo) throws MessagingException {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		respuesta = iCorreoService.cambiarPassword(correo);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
