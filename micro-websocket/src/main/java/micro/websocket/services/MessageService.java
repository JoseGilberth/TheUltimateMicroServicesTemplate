package micro.websocket.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import micro.websocket._config.websocket.Permission;
import micro.websocket.services.interfaces.IMessageService;
import model.auth.usuarios.administradores.UsuarioAdministrador;
import utils.StaticVariables;

@Service
public class MessageService implements IMessageService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	UsuarioAdministradorDao usuarioAdministradorDao;

	@Override
	public Respuesta<Boolean> sendMessage(MessageWebsocket messageWebsocket) {
		try { 
			List<UsuarioAdministrador> usuariosAdministradores = usuarioAdministradorDao.findAll();
			for (UsuarioAdministrador usuarioAdministrador : usuariosAdministradores) {
				logger.info("usuarioAdministrador  " + usuarioAdministrador.getUsername());
				if (Permission.hasPermission(usuarioAdministrador.getPermisos(),StaticVariables.PERMISO_NOTIFICACION_MASTER)) {
					template.convertAndSendToUser(usuarioAdministrador.getUsername(), messageWebsocket.getTopic(),messageWebsocket); 
				}
			}
		}catch(Exception ex) {
			logger.error("sendMessage: " + ex.getMessage());
		}
		logger.info("OK  ");
		return new Respuesta<Boolean>(200, true, "");
	}

}
