package micro.websocket.services;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import micro.websocket._config.StaticVariables;
import micro.websocket.services.interfaces.IMessageService;
import modelo.auth.usuarios.administradores.PermisoAdministrador;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;

@Service
public class MessageService implements IMessageService {

	Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	UsuarioAdministradorDao usuarioAdministradorDao;

 	
	@Override
	public Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(true);
		respuesta.setEstado(true);
		respuesta.setMensaje("OK"); 
		List<UsuarioAdministrador> usuariosAdministradores = usuarioAdministradorDao.findAll();
		for (UsuarioAdministrador usuarioAdministrador : usuariosAdministradores) {
			if (hasPermission(usuarioAdministrador.getPermisos(), StaticVariables.PERMISO_NOTIFICACION_MASTER)) {
				template.convertAndSendToUser(usuarioAdministrador.getUsername(), "/topic/admin", messageWebsocket);
			}
		}
		return respuesta;
	}

	private boolean hasPermission(Set<PermisoAdministrador> permisos, String permisoToFind) {
		return permisos.stream().anyMatch(permiso -> permiso.getEtiqueta().equals(permisoToFind));
	}

}
