package dao.auth.fingerprint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceIzquierdo;

public interface FingerPrintFmdIndiceIzquierdoDao extends JpaRepository<FingerPrintFmdIndiceIzquierdo, Long> {

	@Query("SELECT fp FROM FingerPrintFmdIndiceIzquierdo fp INNER JOIN fp.fingerPrintAuthentication fpa WHERE fpa.id =:id")
	List<FingerPrintFmdIndiceIzquierdo> findByFingerPrintAuthentication(@Param("id") Long id);

}
