package micro.usuarios.publicos.interfaces;

import dto.main.Respuesta;
import dto.usuarios.UsuarioPublicoCambiarClaveDTO;

public interface ICuentaService {

	Respuesta<Boolean> cambiarPassword(UsuarioPublicoCambiarClaveDTO usuarioPublicoCambiarClaveDTO);

}
