package model.auth.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
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

	 
}
