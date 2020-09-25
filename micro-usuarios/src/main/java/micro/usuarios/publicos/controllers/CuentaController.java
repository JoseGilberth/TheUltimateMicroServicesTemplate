package micro.usuarios.publicos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.usuarios.UsuarioPublicoCambiarClaveDTO;
import interfaces.abstracts.ACrudEndPoints;
import micro.usuarios.publicos.interfaces.ICuentaService;

@RestController
@RequestMapping(path = "/usuarios/publico/cuenta")
public class CuentaController extends ACrudEndPoints<CuentaController> {

	Logger logger = LoggerFactory.getLogger(CuentaController.class);

	@Autowired
	ICuentaService cuentaService;

	@PostMapping(value = "/cambiar/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Boolean>> cambiarClave(
			@RequestBody UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO) {
		Respuesta<Boolean> respuesta = null;
		respuesta = cuentaService.cambiarPassword(usuarioPublicoCambiarClaveDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
