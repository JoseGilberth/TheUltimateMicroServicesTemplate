package micro.usuarios.publico.services.interfaces;

import dto.main.Respuesta;
import dto.micro.usuarios.UsuarioPublicoCambiarClaveDTO;

public interface ICuentaService {
	
	Respuesta<Boolean> cambiarPassword(UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO);
 
}
