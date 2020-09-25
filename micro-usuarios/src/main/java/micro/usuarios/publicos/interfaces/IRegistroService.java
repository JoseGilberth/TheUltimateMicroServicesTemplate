package micro.usuarios.publicos.interfaces;

import dto.main.Respuesta;
import model.auth.usuarios.publicos.UsuarioPublico;

public interface IRegistroService {

	Respuesta<UsuarioPublico> crear(UsuarioPublico usuarioPublico);

	Respuesta<String> activarUsuario(String token);
}
