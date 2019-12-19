package micro.correos.services;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dto.main.Respuesta;
import dto.micro.correo.Mail;
import micro.correos.services.interfaces.ICorreoService;

@Service
public class CorreoService implements ICorreoService {

	Logger logger = LoggerFactory.getLogger(CorreoService.class);

	@Autowired
	SendEmailService sendEmailService;

	@Async
	public Respuesta<Boolean> registro(Mail mail) throws MessagingException {
		try {
			return sendEmailService.sendEmail(mail, "email/registro");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Async
	public Respuesta<Boolean> cambiarPassword(Mail mail) throws MessagingException {
		try {
			return sendEmailService.sendEmail(mail, "email/cambio_password");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
