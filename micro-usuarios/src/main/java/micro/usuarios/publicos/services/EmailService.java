package micro.usuarios.publicos.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.publicos.ResetTokenPublicoDao;
import dto.main.Mail;
import dto.main.Respuesta;
import micro.usuarios.publicos.interfaces.IEmailService;
import micro.usuarios.services.externos.email.IEmailExternalService;
import model.auth.usuarios.publicos.ResetTokenPublico;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils._config.language.Translator;

@Service
public class EmailService implements IEmailService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResetTokenPublicoDao resetTokenPublicoDao;

	@Value("${correo.correo}")
	String correo;

	@Value("${correo.soporte}")
	String soporteCorreo;

	@Autowired
	IEmailExternalService iEmailExternalService;

	@Override
	public Respuesta<Boolean> registro(String[] correos, String[] bcc, String[] cc, String subject,
			UsuarioPublico usuario, String urlToSend) {

		Mail mail = new Mail();
		mail.setFrom(correo);
		mail.setTo(correos);
		mail.setSubject(subject);

		/* PROCESO PARA VALIDAR SU USUARIO */
		ResetTokenPublico token = new ResetTokenPublico();
		token.setToken(usuario.getUsername() + UUID.randomUUID().toString());
		token.setUsuarioPublico(usuario);
		token.setExpiracionInMinutes(1440);
		resetTokenPublicoDao.save(token);

		Map<String, Object> variables = new HashMap<>();
		String url = urlToSend + token.getToken();
		variables.put("user", token.getUsuarioPublico().getUsername());
		variables.put("resetUrl", url);
		mail.setVariables(variables);

		iEmailExternalService.registro(mail);

		return new Respuesta<Boolean>(200, true, Translator.toLocale("usuarios.publico.emailservice.registro"));
	}

	@Override
	public Respuesta<Boolean> cambiarPassword(String[] correos, String[] bcc, String[] cc, String subject,
			UsuarioPublico usuario, String passwordTemp) {

		Mail mail = new Mail();
		mail.setFrom(soporteCorreo);
		mail.setTo(correos);
		mail.setBcc(bcc);
		mail.setCc(cc);
		mail.setSubject(subject);

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("user", usuario.getUsername());
		variables.put("passwordTemp", passwordTemp);
		mail.setVariables(variables);

		Respuesta<Boolean> respuesta = iEmailExternalService.cambiarPassword(mail);
		return respuesta;

	}

}
