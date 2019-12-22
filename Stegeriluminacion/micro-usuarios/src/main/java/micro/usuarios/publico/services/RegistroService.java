package micro.usuarios.publico.services;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import dao.auth.usuarios.publicos.ResetTokenPublicoDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import micro.usuarios._config.languaje.Translator;
import micro.usuarios.publico.services.interfaces.IEmailService;
import micro.usuarios.publico.services.interfaces.IRegistroService;
import modelo.auth.usuarios.publicos.ResetTokenPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class RegistroService implements IRegistroService {

	Logger logger = LoggerFactory.getLogger(RegistroService.class);

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	IEmailService emailService;

	@Autowired
	private ResetTokenPublicoDao resetTokenPublicoDao;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("${correo.registro}")
	String stegeriluminacionRegistro;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<UsuarioPublico> crear(UsuarioPublico usuarioPublico) {
		try {
			Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();

			UsuarioPublico usuario = usuariosPublicoDao.buscarPorUsuarioOCorreo(usuarioPublico.getUsername(), usuarioPublico.getCorreo());
			
			if (usuario != null) {// EXISTE EN LA BASE DE DATOS
				return ErrorInternoControlado.usuarioDuplicado(null);
			}
			usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));

			UsuarioPublico usuarioPublic = usuariosPublicoDao.saveAndFlush(usuarioPublico);

			Respuesta<Boolean> correo = emailService.registro(new String[] { usuarioPublico.getCorreo() }, null, null, "Bienvenido", usuarioPublico, stegeriluminacionRegistro);

			if (correo.getCodigoHttp() == 200) {
				respuesta.setCodigo(200);
				respuesta.setCodigoHttp(200);
				respuesta.setCuerpo(usuarioPublic);
				respuesta.setEstado(true);
				respuesta.setMensaje(Translator.toLocale("usuarios.creado"));
				return respuesta;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return ErrorInternoControlado.errorAlEnviarElCorreo(null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (ex instanceof ConstraintViolationException) {
				throw ex;
			}
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<String> activarUsuario(String token) {
		try {
			Respuesta<String> respuesta = new Respuesta<String>();
			ResetTokenPublico rt = resetTokenPublicoDao.findByToken(token);
			if (rt != null) {
				if (!rt.isExpirado()) {
					UsuarioPublico usuarioPublico = usuariosPublicoDao .buscarPorUsuario(rt.getUsuarioPublico().getUsername());
					usuarioPublico.setEnabled(true);
					usuarioPublico = usuariosPublicoDao.saveAndFlush(usuarioPublico);
					resetTokenPublicoDao.delete(rt);
					Context context = new Context();
					String html = templateEngine.process("usuarios/usuario_activado", context);
					respuesta.setCodigo(200);
					respuesta.setCodigoHttp(200);
					respuesta.setCuerpo(html);
					respuesta.setEstado(true);
					respuesta.setMensaje(Translator.toLocale("usuarios.activado"));
					return respuesta;
				} else {
					return ErrorInternoControlado.tokenExpirado(null);
				}
			} else {
				return ErrorInternoControlado.tokenNoExiste(null);
			}
		} catch (Exception ex) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

}