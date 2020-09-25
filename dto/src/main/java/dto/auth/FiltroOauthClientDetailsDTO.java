package dto.auth;

public class FiltroOauthClientDetailsDTO {

	private String id;
	private String resource_ids;
	private String client_secret;
	private String scope;
	private String authorized_grant_types;
	private String web_server_redirect_uri;
	private String authorities;
	private int access_token_validity;
	private int refresh_token_validity;
	private String autoapprove;
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the resource_ids
	 */
	public String getResource_ids() {
		return resource_ids;
	}
	/**
	 * @param resource_ids the resource_ids to set
	 */
	public void setResource_ids(String resource_ids) {
		this.resource_ids = resource_ids;
	}
	/**
	 * @return the client_secret
	 */
	public String getClient_secret() {
		return client_secret;
	}
	/**
	 * @param client_secret the client_secret to set
	 */
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	/**
	 * @return the authorized_grant_types
	 */
	public String getAuthorized_grant_types() {
		return authorized_grant_types;
	}
	/**
	 * @param authorized_grant_types the authorized_grant_types to set
	 */
	public void setAuthorized_grant_types(String authorized_grant_types) {
		this.authorized_grant_types = authorized_grant_types;
	}
	/**
	 * @return the web_server_redirect_uri
	 */
	public String getWeb_server_redirect_uri() {
		return web_server_redirect_uri;
	}
	/**
	 * @param web_server_redirect_uri the web_server_redirect_uri to set
	 */
	public void setWeb_server_redirect_uri(String web_server_redirect_uri) {
		this.web_server_redirect_uri = web_server_redirect_uri;
	}
	/**
	 * @return the authorities
	 */
	public String getAuthorities() {
		return authorities;
	}
	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	/**
	 * @return the access_token_validity
	 */
	public int getAccess_token_validity() {
		return access_token_validity;
	}
	/**
	 * @param access_token_validity the access_token_validity to set
	 */
	public void setAccess_token_validity(int access_token_validity) {
		this.access_token_validity = access_token_validity;
	}
	/**
	 * @return the refresh_token_validity
	 */
	public int getRefresh_token_validity() {
		return refresh_token_validity;
	}
	/**
	 * @param refresh_token_validity the refresh_token_validity to set
	 */
	public void setRefresh_token_validity(int refresh_token_validity) {
		this.refresh_token_validity = refresh_token_validity;
	}
	/**
	 * @return the autoapprove
	 */
	public String getAutoapprove() {
		return autoapprove;
	}
	/**
	 * @param autoapprove the autoapprove to set
	 */
	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}

	
}
