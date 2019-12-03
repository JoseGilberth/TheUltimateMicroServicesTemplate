
package micro.correos.services.interfaces;

import javax.mail.MessagingException;

import dto.main.Respuesta;
import dto.micro.correo.Mail;

public interface ICorreoService {

	public void registro(Mail mail);
	public Respuesta<Boolean> changePassword(Mail mail) throws MessagingException ;

}
