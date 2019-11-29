package modelo.auth.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class OauthRefreshToken {

	@Id
	@Column(name = "token_id")
	private String token_id;

	@Lob 
	@Column(name = "token" , length= 16777215  ) //MEDIUM BLOB
	private String token;

	@Lob 
	@Column(name = "authentication" , length= 16777215  ) //MEDIUM BLOB
	private String authentication;

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}


}
