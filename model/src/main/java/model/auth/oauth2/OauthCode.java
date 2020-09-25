package model.auth.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Table
@Data
public class OauthCode {

	@Id
	@Column(name = "code")
	private String code;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] authentication;

}
