package modelo.solicitud.compra;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.auth.usuarios.publicos.ubicacion.DireccionEntrega;
import modelo.producto.Producto;

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

	@NotNull(message = "Debe indicar un tipo de pago.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoPago tipoPago;

	@NotNull(message = "Debe indicar un tipo de envio.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoEnvio TipoEnvio;

	@NotNull(message = "Debe indicar una dirección de entrega.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private DireccionEntrega direccionEntrega;
 
	@NotNull(message = "Debe indicar a que usuario le pertenece esta solicitud.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioPublico usuarioPublico;

	@ManyToMany
	@JoinTable(name = "solicitud_compra_producto", joinColumns = @JoinColumn(name = "solicitud_compra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
	private Set<Producto> productos = new HashSet<Producto>();

	@Column(name = "costoTotal", nullable = false)
	private double costoTotal;
	

	@NotNull(message = "Debe indicar un tipo de proceso para la solicitud.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoProcesoSolicitud tipoProcesoSolicitud;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public TipoEnvio getTipoEnvio() {
		return TipoEnvio;
	}

	public void setTipoEnvio(TipoEnvio tipoEnvio) {
		TipoEnvio = tipoEnvio;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public UsuarioPublico getUsuarioPublico() {
		return usuarioPublico;
	}

	public void setUsuarioPublico(UsuarioPublico usuarioPublico) {
		this.usuarioPublico = usuarioPublico;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the direccionEntrega
	 */
	public DireccionEntrega getDireccionEntrega() {
		return direccionEntrega;
	}

	/**
	 * @param direccionEntrega the direccionEntrega to set
	 */
	public void setDireccionEntrega(DireccionEntrega direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	/**
	 * @return the costoTotal
	 */
	public double getCostoTotal() {
		return costoTotal;
	}

	/**
	 * @param costoTotal the costoTotal to set
	 */
	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	/**
	 * @return the tipoProcesoSolicitud
	 */
	public TipoProcesoSolicitud getTipoProcesoSolicitud() {
		return tipoProcesoSolicitud;
	}

	/**
	 * @param tipoProcesoSolicitud the tipoProcesoSolicitud to set
	 */
	public void setTipoProcesoSolicitud(TipoProcesoSolicitud tipoProcesoSolicitud) {
		this.tipoProcesoSolicitud = tipoProcesoSolicitud;
	}

	
	
}
