package dto.micro.auth;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class OauthAccessTokenDTO {
	
	private int expiresIn;
	private String tokenType;
	private String usuario;
	private String clientId;
	private String token;
	private Date expiration;
	private Set<String> scope;
	private Map<String, Object> additionalInformation;
	
}
