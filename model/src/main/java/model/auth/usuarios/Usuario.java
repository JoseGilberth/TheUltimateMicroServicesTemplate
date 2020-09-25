package model.auth.usuarios;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import lombok.Data;
import model.auth.usuarios.fingerprint.FingerPrintAuthentication;
import model.validations.interfaces.OnCreate;
import model.validations.interfaces.OnUpdate;
import model.validations.matchers.ValidPassword;
import model.validations.matchers.create.FieldMatch;
import model.validations.matchers.update.FieldMatchUpdate;

@Data
@MappedSuperclass
@FieldMatch(first = "password", second = "repetirPassword", message = "Los passwords deben coincidir.", groups = { OnCreate.class })
@FieldMatchUpdate(first = "password", second = "repetirPassword", message = "Los passwords deben coincidir.", groups = { OnUpdate.class })
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8409428002195898111L;

	@Expose
	@Length(min = 5, max = 100, message = "{usuario.username.lenght}")
	@Column(length = 100, nullable = false, unique = true)
	private String username;

	@JsonIgnore
	@Length(min = 5, max = 250, message = "{usuario.password.lenght}")
	@Column(length = 250, nullable = false)
	@ValidPassword(groups = { OnCreate.class })
	private String password;

	@JsonIgnore
	@Transient
	private String repetirPassword;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	private FingerPrintAuthentication fingerPrintAuthentication;

	@Expose
	@Pattern(regexp = ".+@.+\\..+", message = "{usuario.correo.pattern}")
	@Length(min = 5, max = 100, message = "{usuario.correo.length}")
	@Column(length = 100, nullable = false, unique = true)
	private String correo;

	@Expose
	@Length(min = 3, max = 100, message = "{usuario.nombre.length}", groups = { OnUpdate.class })
	@Column(length = 100, nullable = true, unique = false)
	private String nombre;

	@Length(min = 3, max = 100, message = "{usuario.apellido1.length}", groups = { OnUpdate.class })
	@Column(length = 100, nullable = true, unique = false)
	private String apellido1;

	@Length(min = 3, max = 100, message = "{usuario.apellido2.length}", groups = { OnUpdate.class })
	@Column(length = 100, nullable = true, unique = false)
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getRepetirPassword() {
		return repetirPassword;
	}

	@JsonProperty
	public void setRepetirPassword(String repetirPassword) {
		this.repetirPassword = repetirPassword;
	}

	@JsonIgnore
	public FingerPrintAuthentication getFingerPrintAuthentication() {
		return fingerPrintAuthentication;
	}

	@JsonProperty
	public void setFingerPrintAuthentication(FingerPrintAuthentication fingerPrintAuthentication) {
		this.fingerPrintAuthentication = fingerPrintAuthentication;
	}

}
