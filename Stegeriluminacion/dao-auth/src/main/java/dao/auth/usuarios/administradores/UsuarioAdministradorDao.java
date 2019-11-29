package dao.auth.usuarios.administradores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import modelo.auth.usuarios.administradores.UsuarioAdministrador;

public interface UsuarioAdministradorDao extends JpaRepository<UsuarioAdministrador, Long> {
 
	@Query("SELECT usuario"
			+ " FROM UsuarioAdministrador usuario"
			+ " WHERE usuario.username = :usuario "
	)
	UsuarioAdministrador buscarPorUsuario( @Param("usuario") String usuario);

	
	
}
