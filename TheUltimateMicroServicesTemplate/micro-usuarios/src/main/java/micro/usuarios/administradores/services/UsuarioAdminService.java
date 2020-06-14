package micro.usuarios.administradores.services;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import abstracts.ACrud;
import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioAdminDTO;
import micro.usuarios._config.languaje.Translator;
import micro.usuarios.administradores.services.interfaces.IUsuarioAdminService;
import modelo.auth.usuarios.Usuario;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;
import steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class UsuarioAdminService extends ACrud<UsuarioAdministrador, Long> implements IUsuarioAdminService {

	Logger logger = LoggerFactory.getLogger(UsuarioAdminService.class);

	@Autowired
	UsuarioAdministradorDao usuarioAdministradorDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Value("${correo.registro}")
	String stegeriluminacionRegistro;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<UsuarioAdministrador> crear(UsuarioAdministrador usuarioNuevo) {
		try {
			Respuesta<UsuarioAdministrador> respuesta = new Respuesta<UsuarioAdministrador>();

			Usuario usuario = usuarioAdministradorDao.buscarPorUsuario(usuarioNuevo.getUsername());
			if (usuario != null) {
				return ErrorInternoControlado.usuarioOCorreoDuplicado(null);
			}

			usuarioNuevo.setPassword(bcrypt.encode(usuarioNuevo.getPassword()));
			UsuarioAdministrador usuarioAdmin = usuarioAdministradorDao.saveAndFlush(usuarioNuevo);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(usuarioAdmin);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("usuarios.creado"));
			return respuesta;

		} catch (Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (ex instanceof ConstraintViolationException) {
				throw ex;
			}
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<UsuarioAdministrador> actualizar(Long id, UsuarioAdministrador usuario) {
		try {
			Respuesta<UsuarioAdministrador> respuesta = new Respuesta<UsuarioAdministrador>();

			Usuario usuarioActual = usuarioAdministradorDao.findById(id).get();

			/* PASSWORD */
			if (usuario.getPassword().isEmpty()) {
				usuario.setPassword(usuarioActual.getPassword());
			} else {
				usuario.setPassword(bcrypt.encode(usuario.getPassword()));
			}
			UsuarioAdministrador usuarioAdmin = usuarioAdministradorDao.saveAndFlush(usuario);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(usuarioAdmin);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("usuarios.actualizado"));
			return respuesta;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Page<UsuarioAdministrador>> filtrar(Pageable pageable, FiltroUsuarioAdminDTO filtroUsuarioAdminDTO) {
		Respuesta<Page<UsuarioAdministrador>> respuesta = new Respuesta<Page<UsuarioAdministrador>>();
		Page<UsuarioAdministrador> datos = usuarioAdministradorDao.obtenerTodosPorPaginacion(pageable, filtroUsuarioAdminDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(datos);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("usuarios.obtenido"));
		return respuesta;
	}

	@Override
	public Respuesta<Boolean> borrar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
