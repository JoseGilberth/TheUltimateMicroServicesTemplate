package modelo.auth.usuarios.publicos;

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

@Entity
@Table
public class MotivoAltaBajaPublico {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	@NotNull( message="{motivoAltaBaja.publico.motivo.notnull}" )
	@Length( min = 10 , max = 500 , message = "{motivoAltaBaja.publico.motivo.length}" )
	@Column(length = 255, nullable = false, unique = false)
	private String motivo;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@PrePersist
	public void prePersist(){
		this.fechaAlta = new Date();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	
	
}
