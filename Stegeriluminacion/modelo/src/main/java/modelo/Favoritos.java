package modelo;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
import modelo.producto.Producto;

@Data
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
 
	@NotBlank( message="{favoritos.estrellas.notblank}" )
	@Min(message = "{favoritos.estrellas.min}", value = 0)
	@Max(message = "{favoritos.estrellas.max}", value = 5)
	@Column(name = "estrellas", unique = false, nullable = false)
	private int estrellas;
	 
	@NotNull(message = "{favoritos.producto.notnull}") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;
 
	@NotNull(message = "{favoritos.usuarioPublico.notnull}") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private UsuarioPublico usuarioPublico;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime fechaAlta;

	@LastModifiedDate
	private LocalDateTime fechaActualizacion;
 
}
