package modelo.auth.usuarios.publicos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table
public class PermisoPublico {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull( message="{permiso.publico.etiqueta.notnull}" )
	@Column( length = 255, nullable = false, unique = true )
	private String etiqueta;
	
	@NotNull( message="{permiso.publico.descripcion.notnull}" )
	@Length( min = 10 , max = 500 , message = "{permiso.publico.descripcion.length}" )
	@Column(length = 500 , nullable = false, unique = false)
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
