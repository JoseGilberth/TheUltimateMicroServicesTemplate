package dao.auth.oauth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.micro.auth.FiltroOauthAccessTokenDTO;
import modelo.auth.oauth2.OauthAccessToken; 
 
public interface OauthAccessTokenDao extends JpaRepository<OauthAccessToken, String>{

	@Query("FROM OauthAccessToken oauthAT "
 			+ " WHERE (:#{#filtroOauthAccessTokenDTO.user_name} is null or oauthAT.user_name like %:#{#filtroOauthAccessTokenDTO.user_name}%)"
			+ " AND (:#{#filtroOauthAccessTokenDTO.client_id} is null or oauthAT.client_id like %:#{#filtroOauthAccessTokenDTO.client_id}%)"
 			+ " ORDER BY oauthAT.user_name asc")
	Page<OauthAccessToken> obtenerTodosPorPaginacion(Pageable pageable , FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO);
	

	//from RestToken where token = :? 	
	@Query( "FROM OauthAccessToken oauthAT WHERE oauthAT.token_id =:token_id" )
	OauthAccessToken findByTokenId(@Param("token_id") String token_id);

	@Query( "FROM OauthAccessToken oauthAT WHERE oauthAT.user_name =:user_name" )
	List<OauthAccessToken> findByUserName(@Param("user_name") String user_name);

	

	
}
