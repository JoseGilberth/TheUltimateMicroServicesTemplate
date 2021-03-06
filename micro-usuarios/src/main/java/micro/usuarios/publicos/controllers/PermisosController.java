package micro.usuarios.publicos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.usuarios.publicos.services.PermisosPublicosService;
import model.auth.usuarios.publicos.PermisoPublico;

@RestController
@RequestMapping(path = "/permisos/publico")
public class PermisosController extends ACrudEndPoints<PermisosController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PermisosPublicosService permisosPublicosService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<List<PermisoPublico>>> listAll() {
		Respuesta<List<PermisoPublico>> respuesta = permisosPublicosService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
