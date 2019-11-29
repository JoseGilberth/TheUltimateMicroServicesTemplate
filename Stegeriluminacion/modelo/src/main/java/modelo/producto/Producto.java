package modelo.producto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID uuid;

	@NotEmpty(message = "Debe indicar un nombre del producto.")
	@Length(min = 3, max = 255, message = "El nombre debe contener al menos 3 caracteres y menos 255 caracteres.")
	@Column(name = "etiqueta", unique = false, nullable = false, length = 255)
	private String etiqueta;

	@Column(name = "costo", nullable = false)
	private double costo;

	@NotEmpty(message = "Debe indicar una descripción del producto.")
	@Length(min = 3, max = 5000, message = "La descripción debe contener al menos 3 caracteres.")
	@Column(name = "descripcion", unique = false, nullable = false, length = 5000)
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Imagen> imagenes = new HashSet<Imagen>();

	@NotNull(message = "Debe indicar una unidad de medida del producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private UnidadMedida unidadMedida;

	@NotNull(message = "Debe indicar una unidad de venta del producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private UnidadVenta unidadVenta;

	@NotNull(message = "Debe indicar una categoría del producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Categoria categoria;

	@NotNull(message = "Debe indicar una marca del producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Marca marca;

	@NotNull(message = "Debe indicar una valor de estatus de producto.")  
	@Column(name = "activo", nullable = false)
	private boolean activo;

	@NotNull(message = "Debe indicar una valor de estatus de actividad para clientes.")  
	@Column(name = "activoPublico", nullable = false)
	private boolean activoPublico;
	 
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;

	@PrePersist
	public void prePersist() {
		this.activo = true;
		UUID randomUUID = UUID.randomUUID(); 
		this.uuid = randomUUID;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Set<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public UnidadVenta getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(UnidadVenta unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isActivoPublico() {
		return activoPublico;
	}

	public void setActivoPublico(boolean activoPublico) {
		this.activoPublico = activoPublico;
	}

	
	
}