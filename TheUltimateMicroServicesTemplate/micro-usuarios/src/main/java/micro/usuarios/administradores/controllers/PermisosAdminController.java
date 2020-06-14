package micro.usuarios.administradores.controllers;

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
import micro.usuarios.administradores.services.PermisosAdminService;
import modelo.auth.usuarios.administradores.PermisoAdministrador;

@RestController
@RequestMapping(path = "/permisos/admin")
public class PermisosAdminController extends ACRUDEndPoints<PermisosAdminController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PermisosAdminService permisosAdminService;

	@Autowired
	ACrud<PermisoAdministrador, Long> aCrud;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<List<PermisoAdministrador>>> listAll() {
		Respuesta<List<PermisoAdministrador>> respuesta = null;
		respuesta = aCrud.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
