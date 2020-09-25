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
@Table(indexes = { @Index(columnList = "client_id", name = "index_client_id") })
@Data
public class OauthClientDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "client_id")
	private String id;

	@Column(name = "resource_ids")
	private String resource_ids;

	@Column(name = "client_secret")
	private String client_secret;

	@Column(name = "scope")
	private String scope;

	@Column(name = "authorized_grant_types")
	private String authorized_grant_types;

	@Column(name = "web_server_redirect_uri")
	private String web_server_redirect_uri;

	@Column(name = "authorities")
	private String authorities;

	@Column(name = "access_token_validity")
	private int access_token_validity;

	@Column(name = "refresh_token_validity")
	private int refresh_token_validity;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] additional_information;

	@Column(name = "autoapprove")
	private String autoapprove;

}
