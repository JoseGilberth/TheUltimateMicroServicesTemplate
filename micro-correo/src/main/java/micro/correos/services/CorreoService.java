package micro.correos.services;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dto.main.Mail;
import dto.main.Respuesta;
import micro.correos.services.interfaces.ICorreoService;
import micro.correos.services.interfaces.IEmailService;

@Service
public class CorreoService implements ICorreoService {

	Logger logger = LoggerFactory.getLogger(CorreoService.class);

	@Autowired
	IEmailService iEmailService;

	@Async
	public Respuesta<Boolean> registro(Mail mail) throws MessagingException {
		try {
			return iEmailService.sendEmail(mail, "email/registro");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Async
	public Respuesta<Boolean> cambiarPassword(Mail mail) throws MessagingException {
		try {
			return iEmailService.sendEmail(mail, "email/cambio_password");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
