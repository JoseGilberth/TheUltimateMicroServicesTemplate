package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;
import modelo.auth.usuarios.publicos.MotivoAltaBajaPublico;
import modelo.auth.usuarios.publicos.PermisoPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.auth.usuarios.publicos.ubicacion.DireccionEntrega;
import modelo.auth.usuarios.publicos.ubicacion.DireccionFacturacion;
import modelo.auth.usuarios.publicos.ubicacion.DireccionVivienda;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
@Data
public class Respuesta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@NotBlank( message="{respuesta.respuesta.notblank}" )
	@Length(min = 10, max = 500 , message = "{respuesta.respuesta.length}")
	@Column(name = "respuesta", length = 500, unique = false, nullable = false)
	private String respuesta;

	@NotNull( message="{respuesta.pregunta.notnull}" ) 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pregunta pregunta;

	@ManyToOne
	@JoinColumn(nullable = true)
	private UsuarioPublico usuarioPublico;

	@ManyToOne
	@JoinColumn(nullable = true)
	private UsuarioAdministrador usuarioAdministrador;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;
 
}
