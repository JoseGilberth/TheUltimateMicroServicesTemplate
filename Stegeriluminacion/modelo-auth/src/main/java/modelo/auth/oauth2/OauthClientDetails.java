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
		@Index(columnList = "client_id", name = "index_client_id")
	}
)
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

	@Lob
	@Column(name = "additional_information", length = 21845)// TEXT
	private byte[] additional_information;

	@Column(name = "autoapprove")
	private String autoapprove;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResource_ids() {
		return resource_ids;
	}

	public void setResource_ids(String resource_ids) {
		this.resource_ids = resource_ids;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorized_grant_types() {
		return authorized_grant_types;
	}

	public void setAuthorized_grant_types(String authorized_grant_types) {
		this.authorized_grant_types = authorized_grant_types;
	}

	public String getWeb_server_redirect_uri() {
		return web_server_redirect_uri;
	}

	public void setWeb_server_redirect_uri(String web_server_redirect_uri) {
		this.web_server_redirect_uri = web_server_redirect_uri;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public int getAccess_token_validity() {
		return access_token_validity;
	}

	public void setAccess_token_validity(int access_token_validity) {
		this.access_token_validity = access_token_validity;
	}

	public int getRefresh_token_validity() {
		return refresh_token_validity;
	}

	public void setRefresh_token_validity(int refresh_token_validity) {
		this.refresh_token_validity = refresh_token_validity;
	}

	

	public byte[] getAdditional_information() {
		return additional_information;
	}

	public void setAdditional_information(byte[] additional_information) {
		this.additional_information = additional_information;
	}

	public String getAutoapprove() {
		return autoapprove;
	}

	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}

	


}
