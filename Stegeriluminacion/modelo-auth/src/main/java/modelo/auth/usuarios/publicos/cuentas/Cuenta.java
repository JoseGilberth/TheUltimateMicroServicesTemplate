package modelo.auth.usuarios.publicos.cuentas;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@MappedSuperclass
public class Cuenta {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	@NotBlank(message = "{cuenta.numero.notblank}")
	@Length(min = 16, max = 16, message = "{cuenta.numero.lenght}")
	@Column(length = 16, nullable = false, unique = true)
	private String numero;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaExpiracion;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime fechaActualizacion; 

	@Column(nullable = false)
	private boolean enabled;

	@PrePersist
	private void prePersist() {
		this.enabled = false;
	}
	
	
	
}
