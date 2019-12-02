package modelo.auth.usuarios.administradores;

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

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@PrePersist
	public void prePersist(){
		this.fechaAlta = new Date();
	}
	 
	
}
