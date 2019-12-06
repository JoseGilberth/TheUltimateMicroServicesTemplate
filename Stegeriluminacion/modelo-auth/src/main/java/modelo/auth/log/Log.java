package modelo.auth.log;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

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
 
	@NotBlank( message="{log.usuario.notblank}" )
	@Column(nullable = false, length = 255)
	private String usuario;
 
	@NotBlank( message="{log.tipoUsuario.notblank}" )
	@Column(nullable = false, length = 255)
	private String tipoUsuario;
 
	@NotBlank( message="{log.apartado.notblank}" )
	@Column(nullable = false, length = 255)
	private String apartado;

	@NotBlank( message="{log.accion.notblank}" ) 
	@Column(nullable = false, length = 255)
	private String accion;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;
 
}
