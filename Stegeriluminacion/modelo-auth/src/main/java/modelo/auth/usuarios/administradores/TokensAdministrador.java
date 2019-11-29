package modelo.auth.usuarios.administradores;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
//https://memorynotfound.com/spring-security-forgot-password-send-email-reset-password/
public class TokensAdministrador implements Serializable {

	private static final long serialVersionUID = 3756655220417479455L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{token.administrador.token.notnull}") 
	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = UsuarioAdministrador.class, fetch = FetchType.EAGER)
	private UsuarioAdministrador userAdmin;

	@Column(nullable = false)
	private Date expiracion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public UsuarioAdministrador getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(UsuarioAdministrador userAdmin) {
		this.userAdmin = userAdmin;
	}

	public Date getExpiracion() {
		return expiracion;
	}

	public void setExpiracion(Date expiracion) {
		this.expiracion = expiracion;
	}

	public void setExpiracionInMinutes(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiracion = now.getTime();
	}

	public boolean isExpirado() {
		return new Date().after(this.expiracion);
	}
}