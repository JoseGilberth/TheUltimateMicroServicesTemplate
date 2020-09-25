package micro.usuarios.administradores.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.usuarios.FiltroUsuarioAdminDTO;
import interfaces.interfaces.ICrud;
import model.auth.usuarios.administradores.UsuarioAdministrador;
import model.auth.usuarios.fingerprint.FingerPrintAuthentication;

public interface IUsuarioAdminService extends ICrud<UsuarioAdministrador, Long> {

	Respuesta<Page<UsuarioAdministrador>> filtrar(Pageable pageable, FiltroUsuarioAdminDTO filtroUsuarioAdminDTO);

	Respuesta<Boolean> actualizarHuellas(FingerPrintAuthentication fingerPrintAuthentication);
}
