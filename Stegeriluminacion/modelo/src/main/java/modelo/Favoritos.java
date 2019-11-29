package modelo;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import modelo.auth.usuarios.publicos.UsuarioPublico;
import modelo.producto.Producto;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Favoritos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Debe indicar una cantidad de estrellas al producto.")
	@Min(message = "El minímo para estrellas debe ser 0", value = 0)
	@Max(message = "El máximo para estrellas debe ser 0", value = 5)
	@Column(name = "estrellas", unique = false, nullable = false)
	private int estrellas;
	 
	@NotNull(message = "Debe indicar un producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

	@NotNull(message = "Debe indicar un usuario publico.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioPublico usuarioPublico;
	 
	
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

	public int getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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
	
	

}
