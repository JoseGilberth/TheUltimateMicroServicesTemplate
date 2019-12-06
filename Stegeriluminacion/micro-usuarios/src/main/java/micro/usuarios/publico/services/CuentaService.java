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
import utils.Random;
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

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	@Override
	public Respuesta<Boolean> cambiarPassword(UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		try {
			
			UsuarioPublico usuarioPublico = usuariosPublicoDao.buscarPorCorreo(usuarioPublicoCambiarClaveDTO.getCorreo());
			if (usuarioPublico == null) {
				return ErrorInternoControlado.usuarioDuplicado( false );
			}
			String passwordTemp = Random.randomAlphaNumeric(5);
			usuarioPublico.setPassword(bcrypt.encode(passwordTemp));
			usuariosPublicoDao.saveAndFlush(usuarioPublico); 

			emailService.cambiarPassword(new String[]{usuarioPublicoCambiarClaveDTO.getCorreo()}, null ,null ,"CAMBIO DE CONTRASEÑA", usuarioPublico, passwordTemp );
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
 

}
