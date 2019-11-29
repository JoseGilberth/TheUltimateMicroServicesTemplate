package modelo.auth.usuarios;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  
public class Usuario {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "{usuario.username.notnull}")
	@Length(min = 5, max = 250, message = "{usuario.username.lenght}")
	@Column(length = 255, nullable = false, unique = true)
	private String username;
 
	@JsonIgnore
	@NotNull(message = "{usuario.password.notnull}")
	@Length(min = 5, max = 255, message = "{usuario.password.lenght}")
	@Column(length = 255, nullable = false)
	private String password;
	
	@Transient
	private String repetirPassword;

	@Pattern(regexp = ".+@.+\\..+", message = "{usuario.correo.pattern}")
	@NotNull(message = "{usuario.correo.notnull}")
	@Length(min = 3, max = 250, message = "{usuario.correo.length}")
	@Column(length = 255, nullable = false, unique = true)
	private String correo;

	@Length(min = 3, max = 255, message = "{usuario.nombre.length}")
	@Column(length = 255, nullable = true, unique = false)
	private String nombre;

	@Length(min = 3, max = 255, message = "{usuario.apellido1.length}")
	@Column(length = 255, nullable = true, unique = false)
	private String apellido1;

	@Length(min = 3, max = 255, message = "{usuario.apellido2.length}")
	@Column(length = 255, nullable = true, unique = false)
	private String apellido2;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime ultimaActualizacion;
	
	@Column(nullable = false)
	private boolean enabled;

	@PrePersist
	private void prePersist() {
		enabled = false;
	}
	
	public Usuario() {
	}
	
	@PreUpdate
	private void preUpdate() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepetirPassword() {
		return repetirPassword;
	}

	public void setRepetirPassword(String repetirPassword) {
		this.repetirPassword = repetirPassword;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the fechaAlta
	 */
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the ultimaActualizacion
	 */
	public LocalDateTime getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	/**
	 * @param ultimaActualizacion the ultimaActualizacion to set
	 */
	public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
