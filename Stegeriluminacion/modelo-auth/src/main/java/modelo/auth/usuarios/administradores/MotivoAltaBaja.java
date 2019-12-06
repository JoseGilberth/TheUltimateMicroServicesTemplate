package modelo.auth.usuarios.administradores;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table
public class MotivoAltaBaja {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull( message="{motivoAltaBaja.administrador.motivo.notnull}" )
	@Length( min = 10 , max = 500 , message = "{motivoAltaBaja.administrador.motivo.length}" )
	@Column(length = 255, nullable = false, unique = false)
	private String motivo;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;
}
