package modelo.auth.usuarios.publicos.cuentas;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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

	@NotNull(message = "El número de tarjeta no debe estar vacio.")
	@Length(min = 16, max = 16, message = "La longitud del número de tarjeta debe ser de 16 dígitos")
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

	
	
}
