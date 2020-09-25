package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceDerecho;

public interface FingerPrintFmdIndiceDerechoDao extends JpaRepository<FingerPrintFmdIndiceDerecho, Long> {

	@Query("SELECT fp FROM FingerPrintFmdIndiceDerecho fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdIndiceDerecho> findByFingerPrintAuthentication(@Param("id") Long id);

}
