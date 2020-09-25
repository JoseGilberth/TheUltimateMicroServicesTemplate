package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarIzquierdo;

public interface FingerPrintFmdPulgarIzquierdoDao extends JpaRepository<FingerPrintFmdPulgarIzquierdo, Long> {

	@Query("SELECT fp FROM FingerPrintFmdPulgarIzquierdo fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdPulgarIzquierdo> findByFingerPrintAuthentication(@Param("id") Long id);
}
