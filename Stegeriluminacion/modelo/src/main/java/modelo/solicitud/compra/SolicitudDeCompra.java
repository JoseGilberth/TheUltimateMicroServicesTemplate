package modelo.solicitud.compra;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
import modelo.producto.Producto;

@Data
@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class SolicitudDeCompra  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@NotNull( message="{solicitudcompra.tipoPago.notnull}" )  
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoPago tipoPago;
 
	@NotNull( message="{solicitudcompra.tipoEnvio.notnull}" )  
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoEnvio tipoEnvio;

	@NotNull( message="{solicitudcompra.direccionEntrega.notnull}" )   
	@ManyToOne
	@JoinColumn(nullable = false)
	private DireccionEntrega direccionEntrega;

	@NotNull( message="{solicitudcompra.usuarioPublico.notnull}" )
	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioPublico usuarioPublico;

	@ManyToMany
	@JoinTable(name = "solicitud_compra_producto", joinColumns = @JoinColumn(name = "solicitud_compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
	private Set<Producto> productos = new HashSet<Producto>();

	@Column(name = "costoTotal", nullable = false)
	private double costoTotal;

	@NotNull( message="{solicitudcompra.tipoProcesoSolicitud.notnull}" )
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoProcesoSolicitud tipoProcesoSolicitud;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;
 
}
