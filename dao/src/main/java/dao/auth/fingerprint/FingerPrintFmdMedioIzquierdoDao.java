package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdMedioIzquierdo;

public interface FingerPrintFmdMedioIzquierdoDao extends JpaRepository<FingerPrintFmdMedioIzquierdo, Long> {

	@Query("SELECT fp FROM FingerPrintFmdMedioIzquierdo fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdMedioIzquierdo> findByFingerPrintAuthentication(@Param("id") Long id);
}
