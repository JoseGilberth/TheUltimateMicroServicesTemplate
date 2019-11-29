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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String etiqueta;

	@Column(length = 100)
	private String callePrincipal;

	@ManyToOne( optional = false , cascade = { CascadeType.MERGE  } )
	private TipoVialidad tipoVialidadPrincipal;

	@Column(length = 100)
	private String calle1;

	@ManyToOne( optional = false  , cascade = {   CascadeType.MERGE  })
	private TipoVialidad tipoVialidad1;

	@Column(length = 100)
	private String calle2;

	@ManyToOne( optional = false  , cascade = { CascadeType.MERGE  })
	private TipoVialidad tipoVialidad2;

	@Column(length = 100)
	private String calle3;

	@ManyToOne( optional = false , cascade = {   CascadeType.MERGE  } )
	private TipoVialidad tipoVialidad3;

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

	@Column(length = 100)
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