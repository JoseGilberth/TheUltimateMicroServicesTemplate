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
import  steger.excepciones.controladas.ErrorInternoControlado;
import micro.usuarios._config.languaje.Translator;
import micro.usuarios.publico.services.interfaces.IRegistroService;
import modelo.auth.usuarios.publicos.ResetTokenPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;


@Service
public class RegistroService implements IRegistroService {

	Logger logger = LoggerFactory.getLogger(RegistroService.class);

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	EmailService emailService;

	@Autowired
	private ResetTokenPublicoDao resetTokenPublicoDao;

	@Autowired
	private SpringTemplateEngine templateEngine;


    @Value("${correo.registro}")
	String stegeriluminacionRegistro;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<UsuarioPublico> crearRegistro(UsuarioPublico usuarioPublico) {
		try {
			Respuesta<UsuarioPublico> respuesta = new Respuesta<UsuarioPublico>();

			if (!usuarioPublico.getPassword().equals(usuarioPublico.getRepetirPassword())) {
				return ErrorInternoControlado.passwordsNoCoinciden(null);
			}

			UsuarioPublico usuario = usuariosPublicoDao.buscarPorUsuarioOCorreo(usuarioPublico.getUsername(), usuarioPublico.getCorreo());
			if (usuario != null) {// EXISTE EN LA BASE DE DATOS
				return ErrorInternoControlado.usuarioDuplicado(null);
			}
			usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));

			UsuarioPublico usuarioPublic = usuariosPublicoDao.saveAndFlush(usuarioPublico);

			Respuesta<Boolean> correo = emailService.registro(usuarioPublico.getCorreo(), "Bienvenido", usuarioPublico, stegeriluminacionRegistro );
			
			if (correo.getCodigoHttp() == 200) {
				respuesta.setCodigo(200);
				respuesta.setCodigoHttp(200);
				respuesta.setCuerpo(usuarioPublic);
				respuesta.setEstado(true);
				respuesta.setMensaje(Translator.toLocale("usuarios.creado"));
				return respuesta;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return ErrorInternoControlado.error(Translator.toLocale("error.correo.envio"));
			}
		} catch (Exception ex ) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			if(ex instanceof ConstraintViolationException) { 
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

 					
					UsuarioPublico usuarioPublico = usuariosPublicoDao.buscarPorUsuario(rt.getUsuario().getUsername());
				
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
					return ErrorInternoControlado.tokenExpirado(Translator.toLocale("token.expirado"));
				}
			} else {
				return ErrorInternoControlado.tokenNoExiste(Translator.toLocale("token.noexiste"));
			} 
		} catch (Exception ex) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

}