package micro.usuarios.publico.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abstracts.ACRUDEndPoints;
import abstracts.ACrud;
import dto.main.Respuesta;
import micro.usuarios.publico.services.PermisosPublicosService;
import modelo.auth.usuarios.publicos.PermisoPublico;

@RestController
@RequestMapping(path = "/permisos/publico")
public class PermisosController extends ACRUDEndPoints<PermisosController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PermisosPublicosService permisosPublicosService;

	@Autowired
	ACrud<PermisoPublico, Long> aCrud;
	 
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<List<PermisoPublico>>> listAll() {
		Respuesta<List<PermisoPublico>> respuesta = null;
		respuesta = aCrud.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
