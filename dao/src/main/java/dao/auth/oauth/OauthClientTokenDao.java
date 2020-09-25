package dao.auth.oauth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dto.auth.FiltroOauthClientTokenDTO;
import model.auth.oauth2.OauthClientToken;

public interface OauthClientTokenDao extends JpaRepository<OauthClientToken, String> {

	@Query("FROM OauthClientToken oauthCT "
			+ " WHERE (:#{#filtroOauthClientTokenDTO.authentication_id} is null or oauthCT.authentication_id =:#{#filtroOauthClientTokenDTO.authentication_id})"
			+ " AND (:#{#filtroOauthClientTokenDTO.user_name} is null or oauthCT.user_name = :#{#filtroOauthClientTokenDTO.user_name})"
			+ " AND (:#{#filtroOauthClientTokenDTO.client_id} is null or oauthCT.client_id = :#{#filtroOauthClientTokenDTO.client_id})"
			+ " AND (:#{#filtroOauthClientTokenDTO.token_id} is null or oauthCT.token_id = :#{#filtroOauthClientTokenDTO.token_id})"
			+ " ORDER BY oauthCT.user_name asc")
	Page<OauthClientToken> filtro(Pageable pageable, FiltroOauthClientTokenDTO filtroOauthClientTokenDTO);

	@Query("FROM OauthClientToken oauthAT WHERE oauthAT.token_id =:token_id")
	OauthClientToken findByTokenId(@Param("token_id") String token_id);

	@Query("FROM OauthClientToken oauthAT WHERE oauthAT.user_name =:user_name")
	List<OauthClientToken> findByUsername(@Param("user_name") String user_name);

}
