package micro.usuarios.publico.services.interfaces;

import dto.main.Respuesta;
import modelo.auth.usuarios.publicos.UsuarioPublico;

public interface IRegistroService {

	Respuesta<UsuarioPublico> crear(UsuarioPublico usuarioPublico);

	Respuesta<String> activarUsuario(String token);
}
