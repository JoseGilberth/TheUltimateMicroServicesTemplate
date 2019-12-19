package micro.usuarios.publico.services.interfaces;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioPublicoDTO;
import interfaces.ICRUD;
import modelo.auth.usuarios.publicos.UsuarioPublico;

public interface IUsuarioService extends ICRUD<UsuarioPublico, FiltroUsuarioPublicoDTO, Long> {

	Respuesta<UsuarioPublico> actualizar(UsuarioPublico usuarioPublico, OAuth2Authentication auth);

	Respuesta<UsuarioPublico> obtenerPorToken(OAuth2Authentication auth);

}
