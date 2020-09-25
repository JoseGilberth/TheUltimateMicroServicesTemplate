package dao.auth.usuarios.administradores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.usuarios.FiltroUsuarioAdminDTO;
import model.auth.usuarios.administradores.UsuarioAdministrador;

public interface UsuarioAdministradorDao extends JpaRepository<UsuarioAdministrador, Long> {

	@Query("FROM UsuarioAdministrador ua "
			+ " WHERE (:#{#filtroUsuarioAdminDTO.username} is null or ua.username like %:#{#filtroUsuarioAdminDTO.username}%)"
			+ " AND (:#{#filtroUsuarioAdminDTO.id} is null or ua.id = :#{#filtroUsuarioAdminDTO.id})"
			+ " AND (:#{#filtroUsuarioAdminDTO.correo} is null or ua.correo like %:#{#filtroUsuarioAdminDTO.correo}%)"
			+ " AND (:#{#filtroUsuarioAdminDTO.nombre} is null or ua.nombre like %:#{#filtroUsuarioAdminDTO.nombre}%)"
			+ " AND (:#{#filtroUsuarioAdminDTO.apellido1} is null or ua.apellido1 like %:#{#filtroUsuarioAdminDTO.apellido1}%)"
			+ " AND (:#{#filtroUsuarioAdminDTO.apellido2} is null or ua.apellido2 like %:#{#filtroUsuarioAdminDTO.apellido2}%)"
			+ " AND (:#{#filtroUsuarioAdminDTO.fechaAltaInicial} is null or ua.fechaAlta between :#{#filtroUsuarioAdminDTO.fechaAltaInicial} AND :#{#filtroUsuarioAdminDTO.fechaAltaFinal} )"
			+ " AND (:#{#filtroUsuarioAdminDTO.enabled} is null or ua.enabled = :#{#filtroUsuarioAdminDTO.enabled})"
			+ " ORDER BY ua.fechaAlta desc")
	Page<UsuarioAdministrador> filtro(Pageable pageable, FiltroUsuarioAdminDTO filtroUsuarioAdminDTO);

	@Query("SELECT usuario  FROM UsuarioAdministrador usuario WHERE usuario.username = :usuario ")
	UsuarioAdministrador findByUsername(@Param("usuario") String usuario);

}
