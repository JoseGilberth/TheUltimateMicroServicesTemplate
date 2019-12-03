package modelo.producto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
	 
	
}