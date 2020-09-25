package micro.usuarios.publicos.services;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.publicos.PermisoPublicoDao;
import dao.auth.usuarios.publicos.ResetTokenPublicoDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import excepciones.controladas.ErrorInternoControlado;
import micro.usuarios.publicos.interfaces.IEmailService;
import micro.usuarios.publicos.interfaces.IRegistroService;
import model.auth.usuarios.publicos.PermisoPublico;
import model.auth.usuarios.publicos.ResetTokenPublico;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils._config.language.Translator;

@Service
public class RegistroService implements IRegistroService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsuarioPublicoDao usuariosPublicoDao;

	@Autowired
	PermisoPublicoDao permisoPublicoDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	IEmailService emailService;

	@Autowired
	private ResetTokenPublicoDao resetTokenPublicoDao;

	@Value("${correo.registro}")
	String registro;

	@Override
	public Respuesta<UsuarioPublico> crear(UsuarioPublico usuarioPublico) {

		UsuarioPublico usuario = usuariosPublicoDao.findByUsernameOrEmail(usuarioPublico.getUsername(),
				usuarioPublico.getCorreo());
		if (usuario != null) {// EXISTE EN LA BASE DE DATOS
			return ErrorInternoControlado.usuarioDuplicado(usuarioPublico.getUsername());
		}
		usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
		usuarioPublico.setPermisos(new HashSet<PermisoPublico>(permisoPublicoDao.findAll()));
		usuarioPublico.setLimitRequest(100);
		usuarioPublico.setTimeUnitRequest(TimeUnit.MINUTES);
		usuarioPublico.setTimeUnitToken(TimeUnit.MINUTES);
		usuarioPublico.setTokenExpiration(60);
		usuarioPublico = usuariosPublicoDao.saveAndFlush(usuarioPublico);

		emailService.registro(new String[] { usuarioPublico.getCorreo() }, new String[] {}, new String[] {},
				"Bienvenido", usuarioPublico, registro);

		return new Respuesta<UsuarioPublico>(200, usuarioPublico,
				Translator.toLocale("usuarios.publico.registroservice.crear"));
	}

	@Override
	public Respuesta<String> activarUsuario(String token) {
		ResetTokenPublico rt = resetTokenPublicoDao.findByToken(token);
		if (rt != null) {
			if (!rt.isExpirado()) {
				UsuarioPublico usuarioPublico = usuariosPublicoDao.findByUsernameOrEmail(
						rt.getUsuarioPublico().getUsername(), rt.getUsuarioPublico().getCorreo());
				usuarioPublico.setEnabled(true);
				usuarioPublico = usuariosPublicoDao.saveAndFlush(usuarioPublico);
				resetTokenPublicoDao.delete(rt);
				return new Respuesta<String>(200, "",
						Translator.toLocale("usuarios.publico.registroservice.activarusuario"));
			} else {
				return ErrorInternoControlado.tokenExpirado(null);
			}
		} else {
			return ErrorInternoControlado.tokenNoExiste(null);
		}
	}

}