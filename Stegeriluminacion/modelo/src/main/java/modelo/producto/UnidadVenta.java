package modelo.producto;

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
import modelo.auth.usuarios.publicos.MotivoAltaBajaPublico;
import modelo.auth.usuarios.publicos.PermisoPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.auth.usuarios.publicos.ubicacion.DireccionEntrega;
import modelo.auth.usuarios.publicos.ubicacion.DireccionFacturacion;
import modelo.auth.usuarios.publicos.ubicacion.DireccionVivienda;

@Data
@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class UnidadVenta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

 	@NotBlank(message = "{unidadventa.etiqueta.notblank}")
	@Length(min = 3, max = 255, message = "{unidadventa.etiqueta.length}")
	@Column(name = "etiqueta", unique = true, nullable = false, length = 255)
	private String etiqueta;

 	@NotBlank(message = "{unidadventa.etiquetaCorta.notblank}")
	@Length(min = 3, max = 255, message = "{unidadventa.etiquetaCorta.length}")
	@Column(name = "etiquetaCorta", unique = true, nullable = false, length = 255)
	private String etiquetaCorta;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;

 

}
