package micro.usuarios.publico.controllers;

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
import dto.micro.usuarios.UsuarioPublicoCambiarClaveDTO;
import micro.usuarios.publico.services.CuentaService;

@RestController
@RequestMapping(path = "/usuarios/publico/cuenta")
public class CuentaController {

	Logger logger = LoggerFactory.getLogger(CuentaController.class);

	@Autowired
	CuentaService cuentaService;

	@GetMapping(value = "/heartbit", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> heartBit() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@PostMapping(value = "/cambiar/password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<Boolean>> cambiarClave(
			@RequestBody UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO) {
		Respuesta<Boolean> respuesta = null;
		respuesta = cuentaService.cambiarPassword(usuarioPublicoCambiarClaveDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
