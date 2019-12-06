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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
public class Imagen implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

 	@NotBlank(message = "{imagen.etiqueta.notblank}") 
	@Length(min = 1, max = 255, message = "{imagen.etiqueta.length}")
	@Column(nullable = false)
	private String etiqueta;

 	@NotBlank(message = "{imagen.imagen.notblank}")  
	@Lob
	@Column(nullable = false, length = 5000000 ) // 5 MEGAS
	private byte[] imagen;

 	@NotBlank(message = "{imagen.mimeType.notblank}") 
	@Length(min = 1, max = 255, message = "{imagen.mimeType.length}")
	@Column(nullable = false)
	private String mimeType;

 	@NotBlank(message = "{imagen.tamano.notblank}")
	@Column(nullable = false)
	private int tamano;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime fechaActualizacion;
 
}