package modelo.auth.usuarios.publicos.ubicacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table(
		indexes = {
		 	@Index(columnList = "etiqueta", name = "index_etiqueta" , unique = true )
		}
	)
public class TipoAsentamiento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "version")
	private int version;

	@NotBlank(message = "{tipoasentamiento.etiqueta.notblank}") 
	@Length(min = 5, max = 250, message = "{tipoasentamiento.etiqueta.lenght}")
	@Column(nullable = false , unique = true )
	private String etiqueta;

 
}