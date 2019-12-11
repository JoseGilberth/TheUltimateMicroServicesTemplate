package dao.auth.usuarios.publicos;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.micro.usuarios.FiltroUsuarioPublicoDTO;
import modelo.auth.usuarios.publicos.UsuarioPublico;


public interface UsuarioPublicoDao extends  JpaRepository<UsuarioPublico, Long >{

	
	@Query("FROM UsuarioPublico up "
	      + " WHERE (:#{#filtroUsuarioPublicoDTO.username} is null or up.username like %:#{#filtroUsuarioPublicoDTO.username}%)"
			+ " AND (:#{#filtroUsuarioPublicoDTO.id} is null or up.id = :#{#filtroUsuarioPublicoDTO.id})"
			+ " AND (:#{#filtroUsuarioPublicoDTO.correo} is null or up.correo like %:#{#filtroUsuarioPublicoDTO.correo}%)"
			+ " AND (:#{#filtroUsuarioPublicoDTO.nombre} is null or up.nombre like %:#{#filtroUsuarioPublicoDTO.nombre}%)"
			+ " AND (:#{#filtroUsuarioPublicoDTO.apellido1} is null or up.apellido1 like %:#{#filtroUsuarioPublicoDTO.apellido1}%)" 
			+ " AND (:#{#filtroUsuarioPublicoDTO.apellido2} is null or up.apellido2 like %:#{#filtroUsuarioPublicoDTO.apellido2}%)"
			+ " AND (:#{#filtroUsuarioPublicoDTO.telefonoCelular} is null or up.telefonoCelular like %:#{#filtroUsuarioPublicoDTO.telefonoCelular}%)"
			+ " AND (:#{#filtroUsuarioPublicoDTO.limitRequestInicial} is null or up.limitRequest between :#{#filtroUsuarioPublicoDTO.limitRequestInicial} AND :#{#filtroUsuarioPublicoDTO.limitRequestFinal} )"
			+ " AND (:#{#filtroUsuarioPublicoDTO.timeUnit} is null or up.timeUnit = :#{#filtroUsuarioPublicoDTO.timeUnit})"
			+ " AND (:#{#filtroUsuarioPublicoDTO.tokenExpirationInicial} is null or up.tokenExpiration between :#{#filtroUsuarioPublicoDTO.tokenExpirationInicial} AND :#{#filtroUsuarioPublicoDTO.tokenExpirationFinal} )"
			+ " AND (:#{#filtroUsuarioPublicoDTO.fechaAltaInicial} is null or up.fechaAlta between :#{#filtroUsuarioPublicoDTO.fechaAltaInicial} AND :#{#filtroUsuarioPublicoDTO.fechaAltaFinal} )"
			+ " AND (:#{#filtroUsuarioPublicoDTO.enabled} is null or up.enabled = :#{#filtroUsuarioPublicoDTO.enabled})"  
			+ " ORDER BY up.fechaAlta desc")
	Page<UsuarioPublico> obtenerTodosPorPaginacion(Pageable pageable, FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO);
 
	
	@Query("SELECT usuario"
			+ " FROM UsuarioPublico usuario"
			+ " WHERE usuario.username = :usuario "
	)
	UsuarioPublico buscarPorUsuario( @Param("usuario") String usuario);
	
	@Query("SELECT u "
			+ " FROM UsuarioPublico u"
			+ " WHERE u.username = :usuario OR u.correo = :correo"
	)
	UsuarioPublico buscarPorUsuarioOCorreo( @Param("usuario") String usuario,  @Param("correo") String correo);
	
	@Transactional
	@Modifying
	@Query("UPDATE UsuarioPublico us SET us.password = :password WHERE us.username = :usuario")
	void cambiarPassword(@Param("password") String password, @Param("usuario") String usuario) throws Exception;
	
	@Query("SELECT u "
			+ " FROM UsuarioPublico u"
			+ " WHERE u.correo = :correo "
	)
	UsuarioPublico buscarPorCorreo( @Param("correo") String correo);
}
