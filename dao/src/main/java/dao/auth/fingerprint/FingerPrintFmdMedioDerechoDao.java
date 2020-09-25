package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdMedioDerecho;

public interface FingerPrintFmdMedioDerechoDao extends JpaRepository<FingerPrintFmdMedioDerecho, Long> {

	@Query("SELECT fp FROM FingerPrintFmdMedioDerecho fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdMedioDerecho> findByFingerPrintAuthentication(@Param("id") Long id);
}
