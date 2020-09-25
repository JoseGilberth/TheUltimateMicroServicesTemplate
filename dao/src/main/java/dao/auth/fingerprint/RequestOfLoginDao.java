package dao.auth.fingerprint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.auth.usuarios.fingerprint.RequestOfLogin;

public interface RequestOfLoginDao extends JpaRepository<RequestOfLogin, Long> {

	@Query("SELECT rol FROM RequestOfLogin rol WHERE rol.uuid = :uuid ")
	RequestOfLogin findByUuid(@Param("uuid") String uuid);
}
