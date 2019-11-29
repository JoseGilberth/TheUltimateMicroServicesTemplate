
package micro.correos.services.interfaces;

 import dto.micro.correo.Mail;

public interface ICorreoService {

	public void registro(Mail mail);
	public void cambiarContraseña(Mail mail);
	
}
