package dao.auth.usuarios.publicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import modelo.auth.usuarios.publicos.ResetTokenPublico;
 
public interface ResetTokenPublicoDao extends JpaRepository<ResetTokenPublico, Long>{
	
	//from RestToken where token = :? 	
	@Query( "FROM ResetTokenPublico rtm WHERE rtm.token = :token" )
	ResetTokenPublico findByToken(String token);
}
