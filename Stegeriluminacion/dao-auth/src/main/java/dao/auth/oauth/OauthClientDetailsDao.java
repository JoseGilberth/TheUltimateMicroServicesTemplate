package dao.auth.oauth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.micro.auth.FiltroOauthClientDetailsDTO;
import modelo.auth.oauth2.OauthClientDetails; 
 
public interface OauthClientDetailsDao extends JpaRepository<OauthClientDetails, String>{

	@Query("FROM OauthClientDetails oauthCD "
			+ " WHERE (:#{#filtroOauthClientDetailsDTO.id} is null or oauthCD.id =:#{#filtroOauthClientTokenDTO.id})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.resource_ids} is null or oauthCD.resource_ids = :#{#filtroOauthClientTokenDTO.resource_ids})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.client_secret} is null or oauthCD.client_secret = :#{#filtroOauthClientTokenDTO.client_secret})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.scope} is null or oauthCD.scope = :#{#filtroOauthClientTokenDTO.scope})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.authorized_grant_types} is null or oauthCD.authorized_grant_types = :#{#filtroOauthClientTokenDTO.authorized_grant_types})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.web_server_redirect_uri} is null or oauthCD.web_server_redirect_uri = :#{#filtroOauthClientTokenDTO.web_server_redirect_uri})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.authorities} is null or oauthCD.authorities = :#{#filtroOauthClientTokenDTO.authorities})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.access_token_validity} is null or oauthCD.access_token_validity = :#{#filtroOauthClientTokenDTO.access_token_validity})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.refresh_token_validity} is null or oauthCD.refresh_token_validity = :#{#filtroOauthClientTokenDTO.refresh_token_validity})"
			+ " AND (:#{#filtroOauthClientDetailsDTO.autoapprove} is null or oauthCD.autoapprove = :#{#filtroOauthClientTokenDTO.autoapprove})"
			+ " ORDER BY oauthCD.id asc")
	Page<OauthClientDetails> obtenerTodosPorPaginacion(Pageable pageable , FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO);
	  
	@Query( "FROM OauthClientDetails oauthCD WHERE oauthCD.id =:client_id" )
	OauthClientDetails findByClientId( @Param("client_id")  String client_id);

	@Query( "FROM OauthClientDetails oauthCD WHERE oauthCD.resource_ids =:resource_ids" )
	List<OauthClientDetails> findByResourceId(@Param("resource_ids") String resource_ids);
 

	
}
