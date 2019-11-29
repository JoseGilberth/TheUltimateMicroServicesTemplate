package modelo.auth.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(indexes = { @Index(columnList = "usuario", name = "index_usuario") })
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "El usuario no debe ser nulo")
	@Column(nullable = false, length = 255)
	private String usuario;

	@NotNull(message = "El tipo de usuario no debe ser nulo")
	@Column(nullable = false, length = 255)
	private String tipoUsuario;

	@NotNull(message = "El apartado no debe ser nulo")
	@Column(nullable = false, length = 255)
	private String apartado;

	@NotNull(message = "La acción no debe ser nula")
	@Column(nullable = false, length = 255)
	private String accion;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@PrePersist
	public void prePersist() {
		this.fechaAlta = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getApartado() {
		return apartado;
	}

	public void setApartado(String apartado) {
		this.apartado = apartado;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
