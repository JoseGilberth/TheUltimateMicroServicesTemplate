package model.auth.oauth2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type; 

import lombok.Data;

@Entity
@Table
@Data
public class OauthClientToken implements Serializable {

	private static final long serialVersionUID = 6251109141391960855L;

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

}
