
package micro.correos.services.interfaces;

import javax.mail.MessagingException;

import dto.main.Respuesta;
import dto.micro.correo.Mail;

public interface IEmailService {

	Respuesta<Boolean> sendEmail(Mail mail, String template) throws MessagingException;

}
