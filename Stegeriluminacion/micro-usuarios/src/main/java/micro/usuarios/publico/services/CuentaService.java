package micro.usuarios.publico.services;

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

import dao.auth.usuarios.publicos.ResetTokenPublicoDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.micro.usuarios.UsuarioPublicoCambiarClaveDTO;
import  steger.excepciones.controladas.ErrorInternoControlado;
import micro.usuarios._config.languaje.Translator;
import micro.usuarios.publico.services.interfaces.ICuentaService;
import modelo.auth.usuarios.publicos.ResetTokenPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;

@Service
public class CuentaService implements ICuentaService {

	Logger logger = LoggerFactory.getLogger(CuentaService.class);

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private ResetTokenPublicoDao resetTokenPublicoDao;

	@Value("${correo.registro}")
	String stegeriluminacionRegistro;

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<Boolean> cambiarPassword(UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		try {
			UsuarioPublico usuarioPublico = usuariosPublicoDao.buscarPorCorreo(usuarioPublicoCambiarClaveDTO.getCorreo());
			if (usuarioPublico == null) {
				return ErrorInternoControlado.usuarioDuplicado( false );
			}
			
			String passwordTemp = randomAlphaNumeric(5);
			usuarioPublico.setPassword(bcrypt.encode(passwordTemp));
			usuariosPublicoDao.saveAndFlush(usuarioPublico);
			
			emailService.cambiarPassword(usuarioPublicoCambiarClaveDTO.getCorreo(), "CAMBIO DE CONTRASEÑA", usuarioPublico, passwordTemp );
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(null);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("password.solicitud.cambio"));
			return respuesta;

		} catch (Exception ex) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	@Deprecated
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<Boolean> restablecerPassword(String token, String password) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		try {
			ResetTokenPublico rt = resetTokenPublicoDao.findByToken(token);
			if (rt != null) {
				if (!rt.isExpirado()) {

					UsuarioPublico usuarioPublico = usuariosPublicoDao.buscarPorUsuario(rt.getUsuarioPublico().getUsername());
					if (!usuarioPublico.isEnabled()) {
						return ErrorInternoControlado.usuarioNoActivo(false);
					}

					String claveHash = bcrypt.encode(password);
					
					usuariosPublicoDao.cambiarPassword(claveHash, rt.getUsuarioPublico().getUsername());
					
					resetTokenPublicoDao.delete(rt);

					respuesta.setCodigo(200);
					respuesta.setCodigoHttp(200);
					respuesta.setCuerpo(true);
					respuesta.setEstado(true);
					respuesta.setMensaje(Translator.toLocale("password.cambiado.exito"));
					return respuesta;

				} else {
					return ErrorInternoControlado.tokenExpirado(false);
				}
			} else {
				return ErrorInternoControlado.tokenNoExiste(false);
			}
		} catch (Exception ex) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

}
