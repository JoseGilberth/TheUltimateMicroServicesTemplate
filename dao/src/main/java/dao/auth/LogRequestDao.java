package dao.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.auth.FiltroLogDTO;
import model.auth.log.LogRequest;

public interface LogRequestDao extends JpaRepository<LogRequest, Long> {

	@Query("FROM LogRequest log "
			+ " WHERE (:#{#filtroLogDTO.usuario} is null or log.usuario like %:#{#filtroLogDTO.usuario}%)"
			+ " AND (:#{#filtroLogDTO.tipoUsuario} is null or log.tipoUsuario like %:#{#filtroLogDTO.tipoUsuario}%)"
			+ " AND (:#{#filtroLogDTO.accion} is null or log.accion like %:#{#filtroLogDTO.accion}%)"
			+ " AND (:#{#filtroLogDTO.fechaAltaInicial} is null or log.fechaAlta between :#{#filtroLogDTO.fechaAltaInicial} AND :#{#filtroLogDTO.fechaAltaFinal} )"
			+ " ORDER BY log.fechaAlta desc")
	Page<LogRequest> filtro(Pageable pageable, FiltroLogDTO filtroLogDTO);

	@Query("FROM LogRequest log WHERE log.usuario =:usuario")
	List<LogRequest> findByUserName(@Param("usuario") String usuario);

}
