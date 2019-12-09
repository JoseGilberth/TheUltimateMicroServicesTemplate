package modelo.auth.usuarios.publicos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import modelo.auth.usuarios.Usuario;

@Entity
@Table(indexes = { @Index(columnList = "username", name = "index_usuario"),
		@Index(columnList = "correo", name = "index_correo") },
	uniqueConstraints={
		@UniqueConstraint(columnNames={"username"} , name = "unique_username"),
		@UniqueConstraint(columnNames={"correo"} , name = "unique_correo"),
		@UniqueConstraint(columnNames={"telefonoCelular"} , name = "unique_telefonoCelular")
	}
)
@Data
public class UsuarioPublico extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "{usuario.publico.telefonoCelular.notnull}")
	@Length(min = 10, max = 10, message = "{usuario.publico.telefonoCelular.lenght}")	
	@Column(name = "telefonoCelular", unique = true, nullable = false, length = 10)
	private String telefonoCelular;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_publico_permiso_publico", joinColumns = @JoinColumn(name = "usuario_publico_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permisos_id", referencedColumnName = "id"))
	private Set<PermisoPublico> permisos = new HashSet<PermisoPublico>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<MotivoAltaBajaPublico> motivosAltaBaja = new HashSet<MotivoAltaBajaPublico>();

	@NotNull(message = "{usuario.publico.limitRequest.notnull}") 
	@Column(name = "limitRequest", nullable = false)
	private Integer limitRequest;

	@NotNull(message = "{usuario.publico.timeUnit.notnull}") 
	@Column(name = "timeUnit", nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeUnit timeUnit;

	@NotNull(message = "{usuario.publico.tokenExpiration.notnull}") 
	@Column(name = "tokenExpiration", nullable = false)
	private Integer tokenExpiration;

	public UsuarioPublico() {

	} 
}
