package micro.usuarios.administradores.services.interfaces;

import dto.micro.usuarios.FiltroUsuarioAdminDTO;
import interfaces.ICRUD;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;

public interface IUsuarioAdminService extends ICRUD<UsuarioAdministrador, FiltroUsuarioAdminDTO, Long> {

}
