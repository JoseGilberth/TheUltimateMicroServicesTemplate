package micro.usuarios.publicos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.usuarios.UsuarioPublicoCambiarClaveDTO;
import excepciones.controladas.ErrorInternoControlado;
import micro.usuarios.publicos.interfaces.ICuentaService;
import micro.usuarios.publicos.interfaces.IEmailService;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils.Random;
import utils._config.language.Translator;

@Service
public class CuentaService implements ICuentaService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	IEmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Override
	public Respuesta<Boolean> cambiarPassword(UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO) {

		UsuarioPublico usuarioPublico = usuariosPublicoDao.findByUsernameOrEmail(
				usuarioPublicoCambiarClaveDTO.getCorreo(), usuarioPublicoCambiarClaveDTO.getCorreo());
		if (usuarioPublico == null) {
			return ErrorInternoControlado.correoNoExiste(usuarioPublicoCambiarClaveDTO.getCorreo());
		}

		String passwordTemp = Random.randomAlphaNumeric(5);
		usuarioPublico.setPassword(bcrypt.encode(passwordTemp));
		usuariosPublicoDao.saveAndFlush(usuarioPublico);

		emailService.cambiarPassword(new String[] { usuarioPublicoCambiarClaveDTO.getCorreo() }, null, null,
				"CAMBIO DE CONTRASEÑA", usuarioPublico, passwordTemp);

		return new Respuesta<Boolean>(200, null, Translator.toLocale("usuarios.publico.cuentaservice.cambiarpassword"));

	}

}
