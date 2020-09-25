package micro.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.auth.interfaces.IRequestOfLoginService;
import model.auth.usuarios.fingerprint.RequestOfLogin;

@RestController
@RequestMapping(path = "/request/login/")
public class RequestOfLoginController extends ACrudEndPoints<RequestOfLogin> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IRequestOfLoginService iRequestOfLoginService;

	@GetMapping(value = "/{uuid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<RequestOfLogin>> getRequestLogin(@PathVariable("uuid") String uuid) {
		Respuesta<RequestOfLogin> respuesta = iRequestOfLoginService.findByUuid(uuid);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(value = "/{usuario}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<RequestOfLogin>> requestLogin(@PathVariable("usuario") String usuario) {
		Respuesta<RequestOfLogin> respuesta = iRequestOfLoginService.crear(new RequestOfLogin(usuario));
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
