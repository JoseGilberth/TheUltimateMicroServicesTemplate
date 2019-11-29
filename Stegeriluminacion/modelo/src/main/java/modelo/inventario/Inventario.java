package modelo.inventario;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import modelo.producto.Producto;
import modelo.proveedor.Proveedor;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Inventario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "existencias", nullable = false)
	private double existencias;

	@Column(name = "invMinimo", nullable = false)
	private double invMinimo;

	@Column(name = "invMaximo", nullable = false)
	private double invMaximo;

	@Column(name = "costo", nullable = false)
	private double costo;

	@Column(name = "costoMayoreo", nullable = false)
	private double costoMayoreo;

	@NotNull(message = "Debe indicar un proveedor.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private Proveedor proveedor;

	@NotNull(message = "Debe indicar un producto.")
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

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

	public double getExistencias() {
		return existencias;
	}

	public void setExistencias(double existencias) {
		this.existencias = existencias;
	}

	public double getInvMinimo() {
		return invMinimo;
	}

	public void setInvMinimo(double invMinimo) {
		this.invMinimo = invMinimo;
	}

	public double getInvMaximo() {
		return invMaximo;
	}

	public void setInvMaximo(double invMaximo) {
		this.invMaximo = invMaximo;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getCostoMayoreo() {
		return costoMayoreo;
	}

	public void setCostoMayoreo(double costoMayoreo) {
		this.costoMayoreo = costoMayoreo;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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

}
