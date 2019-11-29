package micro.usuarios.publico.services.interfaces;

import dto.main.Respuesta;
import modelo.auth.usuarios.publicos.UsuarioPublico;

public interface IEmailService {

    Respuesta<Boolean> registro(String correo, String contenido, UsuarioPublico usuario, String urlToSend);
    Respuesta<Boolean> cambiarPassword(String correo, String contenido, UsuarioPublico usuario, String urlToSend);
     
    
}
