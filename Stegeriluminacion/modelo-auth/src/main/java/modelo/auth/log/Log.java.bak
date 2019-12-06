package modelo.auth.log;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

import lombok.Data;
import modelo.auth.usuarios.publicos.MotivoAltaBajaPublico;
import modelo.auth.usuarios.publicos.PermisoPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.auth.usuarios.publicos.ubicacion.DireccionEntrega;
import modelo.auth.usuarios.publicos.ubicacion.DireccionFacturacion;
import modelo.auth.usuarios.publicos.ubicacion.DireccionVivienda;

@Data
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
 
}
