package micro.usuarios.publico.services;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioPublicoDTO;
import micro.usuarios._config.languaje.Translator;
import micro.usuarios.publico.services.interfaces.ACrud;
import micro.usuarios.publico.services.interfaces.IUsuarioService;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class UsuarioService extends ACrud<UsuarioPublico> implements IUsuarioService {

	Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	EmailService emailService;

	@Value("${correo.registro}")
	String stegeriluminacionRegistro;

	@Override
	public Respuesta<UsuarioPublico> actualizar(UsuarioPublico usuarioPublico, OAuth2Authentication auth) {
		Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();
		try {
			UsuarioPublico usuarioActual = usuarioPublicoDao.buscarPorUsuario(auth.getPrincipal().toString());
			if (!usuarioPublico.getPassword().equals(usuarioPublico.getRepetirPassword())) {
				return ErrorInternoControlado.passwordsNoCoinciden(null);
			}
			/* PASSWORD */
			if (usuarioPublico.getPassword().isEmpty()) {
				usuarioPublico.setPassword(usuarioActual.getPassword());
			} else {
				usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
			}
			UsuarioPublico usuarioPublic = usuarioPublicoDao.saveAndFlush(usuarioPublico);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(usuarioPublic);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("usuarios.actualizado"));
			return respuesta;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<UsuarioPublico> crearUsuarioPorAdministracion(UsuarioPublico usuarioPublico) {
		try {
			Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();

			UsuarioPublico usuario = usuariosPublicoDao.buscarPorUsuarioOCorreo(usuarioPublico.getUsername(),
					usuarioPublico.getCorreo());
			if (usuario != null) {
				return ErrorInternoControlado.usuarioDuplicado(null);
			}
			usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
			UsuarioPublico usuarioPublic = usuariosPublicoDao.saveAndFlush(usuarioPublico);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(usuarioPublic);
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
	public Respuesta<UsuarioPublico> actualizarUsuarioPorAdministracion(Long id, UsuarioPublico usuarioPublico) {
		try {
			Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();

			UsuarioPublico usuarioActual = usuarioPublicoDao.findById(id).get();

			/* PASSWORD */
			if (usuarioPublico.getPassword().isEmpty()) {
				usuarioPublico.setPassword(usuarioActual.getPassword());
			} else {
				usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
			}
			UsuarioPublico usuarioPublic = usuarioPublicoDao.saveAndFlush(usuarioPublico);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(usuarioPublic);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("usuarios.actualizado"));
			return respuesta;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<UsuarioPublico> obtenerPorToken(OAuth2Authentication auth) {
		Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(auth.getPrincipal().toString());
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(usuarioPublico);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("usuarios.busqueda.satisfactoria"));
		return respuesta;
	}

	@Override
	public Respuesta<Page<UsuarioPublico>> filtrar(Pageable pageable, FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO) {
		Respuesta<Page<UsuarioPublico>> respuesta = new Respuesta<Page<UsuarioPublico>>();
		Page<UsuarioPublico> datos = usuarioPublicoDao.obtenerTodosPorPaginacion(pageable, filtroUsuarioPublicoDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(datos);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

}
