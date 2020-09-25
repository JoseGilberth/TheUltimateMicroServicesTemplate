package micro.usuarios.publicos.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import dto.usuarios.FiltroUsuarioPublicoDTO;
import dto.usuarios.PatchUsuarioPublicoDTO;
import interfaces.interfaces.ICrud;
import model.auth.usuarios.fingerprint.FingerPrintAuthentication;
import model.auth.usuarios.publicos.UsuarioPublico;

public interface IUsuarioService extends ICrud<UsuarioPublico, Long> {

	Respuesta<UsuarioPublico> actualizar(PatchUsuarioPublicoDTO usuarioPublico, OAuth2Authentication auth);

	Respuesta<Boolean> actualizarHuellas(FingerPrintAuthentication fingerPrintAuthentication);

	Respuesta<UsuarioPublico> obtenerPorToken(OAuth2Authentication auth);

	Respuesta<Page<UsuarioPublico>> filtrar(Pageable pageable, FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO);
}
