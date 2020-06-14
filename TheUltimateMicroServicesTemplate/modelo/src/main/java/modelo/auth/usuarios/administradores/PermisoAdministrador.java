package modelo.auth.usuarios.administradores;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table
public class PermisoAdministrador implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@NotNull( message="{permiso.administrador.etiqueta.notnull}" )
	@Column( length = 255, nullable = false, unique = true )
	private String etiqueta;

	@NotNull( message="{permiso.administrador.descripcion.notnull}" ) 
	@Length( min = 10 , max = 500 , message = "{permiso.administrador.descripcion.length}" )
	@Column(length = 500 , nullable = false, unique = false)
	private String descripcion;
 

}
