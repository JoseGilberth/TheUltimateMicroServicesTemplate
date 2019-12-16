package micro.usuarios.administradores.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioAdminDTO;
import interfaces.ACRUDEndPoints;
import interfaces.ACrud;
import micro.usuarios.administradores.services.interfaces.IUsuarioAdminService;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;
import utils.validaciones.interfaces.OnCreate;
import utils.validaciones.interfaces.OnUpdate;

@RestController
@RequestMapping(path = "/usuarios/admin")
public class UsuarioAdminController extends ACRUDEndPoints<UsuarioAdminController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IUsuarioAdminService usuarioService;

	@Autowired
	ACrud<UsuarioAdministrador, Long> aCrud;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<Page<UsuarioAdministrador>>> filtrar(Pageable pageable, @RequestBody FiltroUsuarioAdminDTO filtroUsuarioDTO) { // size=10 page=1
		Respuesta<Page<UsuarioAdministrador>> respuesta = usuarioService.filtrar(pageable, filtroUsuarioDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<UsuarioAdministrador>> crear( @Validated(OnCreate.class) @RequestBody UsuarioAdministrador usuario) {
		Respuesta<UsuarioAdministrador> respuesta = null;
		respuesta = usuarioService.crear(usuario);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<UsuarioAdministrador>> actualizar(@PathVariable("id") Long id, @Validated(OnUpdate.class) @RequestBody UsuarioAdministrador usuario) {
		Respuesta<UsuarioAdministrador> respuesta = null;
		respuesta = usuarioService.actualizar(id, usuario);  
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") Long id) {
		Respuesta<Boolean> respuesta = null;
		respuesta = aCrud.eliminar(id);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
