package modelo.auth.usuarios;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import utils.validaciones.interfaces.OnCreate;
import utils.validaciones.interfaces.OnUpdate;
import utils.validaciones.matchers.create.FieldMatch;
import utils.validaciones.matchers.update.FieldMatchUpdate;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldMatch.List({
	@FieldMatch(first = "password", second = "repetirPassword", message = "Los passwords deben coincidir.", groups = { OnCreate.class }),
})
@FieldMatchUpdate(first = "password", second = "repetirPassword", message = "Los passwords deben coincidir.", groups = { OnUpdate.class })
public class Usuario {
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
