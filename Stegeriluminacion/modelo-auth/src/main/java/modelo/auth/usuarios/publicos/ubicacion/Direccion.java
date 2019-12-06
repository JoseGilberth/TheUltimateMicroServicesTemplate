package modelo.auth.usuarios.publicos.ubicacion;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@MappedSuperclass
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{direccion.etiqueta.notblank}")
	@Length(min = 5, max = 250, message = "{direccion.etiqueta.lenght}")
	@Column(nullable = false)
	private String etiqueta;

	@NotBlank(message = "{direccion.callePrincipal.notblank}")
	@Length(min = 1, max = 100, message = "{direccion.callePrincipal.lenght}")
	@Column(length = 100)
	private String callePrincipal;

	@ManyToOne( optional = false , cascade = { CascadeType.MERGE  } )
	private TipoVialidad tipoVialidadPrincipal;

	@NotBlank(message = "{direccion.calle1.notblank}")
	@Length(min = 1, max = 100, message = "{direccion.calle1.lenght}")
	@Column(length = 100)
	private String calle1;

	@ManyToOne( optional = false  , cascade = {   CascadeType.MERGE  })
	private TipoVialidad tipoVialidad1;

	@NotBlank(message = "{direccion.calle2.notblank}")
	@Length(min = 1, max = 100, message = "{direccion.calle2.lenght}")
	@Column(length = 100)
	private String calle2;

	@ManyToOne( optional = false  , cascade = { CascadeType.MERGE  })
	private TipoVialidad tipoVialidad2;
 
	@Column(length = 100)
	private String calle3;

	@ManyToOne( optional = false , cascade = {   CascadeType.MERGE  } )
	private TipoVialidad tipoVialidad3;

	@NotBlank(message = "{direccion.numeroExterior.notblank}")
	@Length(min = 1, max = 100, message = "{direccion.numeroExterior.lenght}")
	@Column(length = 100)
	private String numeroExterior;

	@Column(length = 100)
	private String letraExterior;

	@Column(length = 100)
	private String edificio;

	@Column(length = 100)
	private String edificio2;

	@Column(length = 100)
	private String numeroInterior;

	@Column(length = 100)
	private String letraInterior;

	@ManyToOne( optional = false  )
	private TipoAsentamiento tipoAsentamiento;

	@NotBlank(message = "{direccion.codigoPostal.notblank}")
	@Length(min = 1, max = 8, message = "{direccion.codigoPostal.lenght}")
	@Column(length = 8)
	private String codigoPostal;

	@ManyToOne( optional = false , cascade = { CascadeType.MERGE  } )
	private TipoMunicipio tipoMunicipio;

	@Column(length = 100)
	private String ageb;

	@Column(length = 100)
	private String manzana;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime fechaActualizacion; 
	
}