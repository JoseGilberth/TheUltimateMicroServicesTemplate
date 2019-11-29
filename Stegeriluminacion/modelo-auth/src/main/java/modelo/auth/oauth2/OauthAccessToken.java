package modelo.auth.oauth2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity 
@Table(
indexes = {
		@Index(columnList = "authentication_id", name = "index_authentication_id" , unique = true )
	}
)
public class OauthAccessToken implements Serializable{
 
	private static final long serialVersionUID = -4449925385733893733L;

	@Id
	@Column(name = "token_id")
	private String token_id;
	
	@Lob 
	@Column(name = "token" , length= 16777215  ) //MEDIUM BLOB, length= 16777215 
	private byte[] token;

	@Column(name = "authentication_id")
	private String authentication_id;

	@Column(name = "user_name")
	private String user_name;

	@Column(name = "client_id")
	private String client_id;

	@Lob 
	@Column(name = "authentication" , length= 16777215 )//MEDIUM BLOB, length= 16777215
	private byte[] authentication;

	@Column(name = "refresh_token")
	private String refresh_token;

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}



	public String getAuthentication_id() {
		return authentication_id;
	}

	public void setAuthentication_id(String authentication_id) {
		this.authentication_id = authentication_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	

}
