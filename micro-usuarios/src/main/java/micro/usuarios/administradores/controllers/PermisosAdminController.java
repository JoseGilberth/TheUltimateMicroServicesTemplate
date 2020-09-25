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

import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.usuarios.administradores.services.PermisosAdminService;
import model.auth.usuarios.administradores.PermisoAdministrador;

@RestController
@RequestMapping(path = "/permisos/admin")
public class PermisosAdminController extends ACrudEndPoints<PermisosAdminController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PermisosAdminService permisosAdminService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<List<PermisoAdministrador>>> listAll() {
		Respuesta<List<PermisoAdministrador>> respuesta = permisosAdminService.obtenerTodos();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
