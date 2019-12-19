package modelo.auth.log;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

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
