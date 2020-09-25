package model.auth.usuarios.administradores;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.auth.usuarios.Usuario;

@Entity
@Table(indexes = { @Index(columnList = "username", name = "index_ua_usuario"),
		@Index(columnList = "correo", name = "index_ua_correo") })
@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioAdministrador extends Usuario implements Serializable {

	private static final long serialVersionUID = -7055491564629324679L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_administrador_permiso", joinColumns = @JoinColumn(name = "usuario_administrador_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permisos_id", referencedColumnName = "id"))
	private Set<PermisoAdministrador> permisos = new HashSet<PermisoAdministrador>();

	@OneToMany(fetch = FetchType.LAZY)
	private Set<MotivoAltaBaja> motivosAltaBaja = new HashSet<MotivoAltaBaja>();

	public UsuarioAdministrador() {

	}

}
