package micro.usuarios.administradores.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.usuarios.FiltroUsuarioAdminDTO;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;

public interface IUsuarioAdminService {

	Respuesta<Page<UsuarioAdministrador>> filtrar(Pageable pageable, FiltroUsuarioAdminDTO filtroUsuarioAdminDTO);

	Respuesta<UsuarioAdministrador> crear(UsuarioAdministrador usuario);

	Respuesta<UsuarioAdministrador> actualizar(Long id, UsuarioAdministrador usuario);

}
