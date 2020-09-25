
package micro.correos.services.interfaces;

import javax.mail.MessagingException;

import dto.main.Mail;
import dto.main.Respuesta;

public interface IEmailService {

	Respuesta<Boolean> sendEmail(Mail mail, String template) throws MessagingException;

}
