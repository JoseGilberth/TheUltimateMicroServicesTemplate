package modelo.auth.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class OauthCode {

	@Id
	@Column(name = "code")
	private String code;

	@Lob 
	@Column(name = "authentication", length= 16777215  ) //MEDIUM BLOB
	private byte[] authentication;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}
	
	
}
