package modelo.auth.usuarios.administradores;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import modelo.auth.usuarios.Usuario;

@Data
@Entity
@Table(indexes = { @Index(columnList = "username", name = "index_ua_usuario"),
		@Index(columnList = "correo", name = "index_ua_correo") })
public class UsuarioAdministrador extends Usuario {
 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_administrador_permiso", joinColumns = @JoinColumn(name = "usuario_administrador_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permisos_id", referencedColumnName = "id"))
	private Set<PermisoAdministrador> permisos = new HashSet<PermisoAdministrador>();

	@OneToMany(fetch = FetchType.LAZY)
	private Set<MotivoAltaBaja> motivosAltaBaja = new HashSet<MotivoAltaBaja>();

	public UsuarioAdministrador() {

	} 
 

}
