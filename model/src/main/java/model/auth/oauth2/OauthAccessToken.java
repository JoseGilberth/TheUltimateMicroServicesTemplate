package model.auth.oauth2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Table(indexes = { @Index(columnList = "authentication_id", name = "index_authentication_id", unique = true) })
@Data
public class OauthAccessToken implements Serializable {

	private static final long serialVersionUID = -4449925385733893733L;

	@Id
	@Column(name = "token_id")
	private String token_id;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] token;

	@Column(name = "authentication_id")
	private String authentication_id;

	@Column(name = "user_name")
	private String user_name;

	@Column(name = "client_id")
	private String client_id;

	@Type(type = "org.hibernate.type.BinaryType") // Postgres

	private byte[] authentication;

	@Column(name = "refresh_token")
	private String refresh_token;


}
