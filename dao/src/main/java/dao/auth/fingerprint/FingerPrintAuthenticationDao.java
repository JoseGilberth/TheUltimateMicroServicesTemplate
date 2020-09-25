package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintAuthentication;

public interface FingerPrintAuthenticationDao extends JpaRepository<FingerPrintAuthentication, Long> {
   
	@Query("FROM LogRequest log WHERE log.usuario =:usuario")
	List<FingerPrintAuthentication> findByUserName(@Param("usuario") String usuario);

}
