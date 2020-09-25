package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarDerecho;

public interface FingerPrintFmdPulgarDerechoDao extends JpaRepository<FingerPrintFmdPulgarDerecho, Long> {

	@Query("SELECT fp FROM FingerPrintFmdPulgarDerecho fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdPulgarDerecho> findByFingerPrintAuthentication(@Param("id") Long id);
}
