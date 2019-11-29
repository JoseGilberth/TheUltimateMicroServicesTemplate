package dao.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.micro.auth.FiltroLogDTO;
import modelo.auth.log.Log;

public interface LogDao extends JpaRepository<Log, Long> {

	@Query("FROM Log log " + " WHERE (:#{#filtroLogDTO.usuario} is null or log.usuario =:#{#filtroLogDTO.usuario})"
			+ " AND (:#{#filtroLogDTO.tipoUsuario} is null or log.tipoUsuario = :#{#filtroLogDTO.tipoUsuario})"
			+ " AND (:#{#filtroLogDTO.apartado} is null or log.apartado = :#{#filtroLogDTO.apartado})"
			+ " AND (:#{#filtroLogDTO.accion} is null or log.accion = :#{#filtroLogDTO.accion})"
			+ " AND (:#{#filtroLogDTO.fechaAltaInicial} is null or log.fechaAlta between :#{#filtroLogDTO.fechaAltaInicial} AND :#{#filtroLogDTO.fechaAltaFinal} )"
			+ " ORDER BY log.fechaAlta desc")
	Page<Log> obtenerTodosPorPaginacion(Pageable pageable, FiltroLogDTO filtroLogDTO);

	@Query("FROM Log log WHERE log.usuario =:usuario")
	List<Log> findByUserName(@Param("usuario") String usuario);

}
