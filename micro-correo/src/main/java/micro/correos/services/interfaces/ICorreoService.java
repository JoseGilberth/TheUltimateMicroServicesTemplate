
package micro.correos.services.interfaces;

import javax.mail.MessagingException;

import dto.main.Mail;
import dto.main.Respuesta;

public interface ICorreoService {

	Respuesta<Boolean> registro(Mail mail) throws MessagingException;

	Respuesta<Boolean> cambiarPassword(Mail mail) throws MessagingException;

}
