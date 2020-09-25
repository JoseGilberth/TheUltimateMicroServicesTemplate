package model.auth.usuarios.publicos;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.auth.usuarios.Usuario;

@Entity
@Table(indexes = { @Index(columnList = "username", name = "index_usuario"),
		@Index(columnList = "correo", name = "index_correo") }, uniqueConstraints = {
				@UniqueConstraint(columnNames = { "username" }, name = "unique_username"),
				@UniqueConstraint(columnNames = { "correo" }, name = "unique_correo") })
@Data
@EqualsAndHashCode(callSuper = false)
//@ToString(exclude = { "" })
public class UsuarioPublico extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_publico_permiso_publico", joinColumns = @JoinColumn(name = "usuario_publico_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permisos_id", referencedColumnName = "id"))
	private Set<PermisoPublico> permisos = new HashSet<PermisoPublico>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = true, name = "usuario_publico_id")
	private Set<MotivoAltaBajaPublico> motivosAltaBaja = new HashSet<MotivoAltaBajaPublico>();

	@Min(value = 1, message = "{usuario.publico.limitRequest.min}")
	@Column(nullable = false)
	private Integer limitRequest;

	@Min(value = 1, message = "{usuario.publico.periodRequest.min}")
	@Column(nullable = false)
	private int periodRequest;

	@NotNull(message = "{usuario.publico.timeUnitRequest.notnull}")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeUnit timeUnitRequest;

	@NotNull(message = "{usuario.publico.timeUnitToken.notnull}")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeUnit timeUnitToken;

	@Min(value = 1, message = "{usuario.publico.tokenExpiration.min}")
	@Column(nullable = false)
	private Integer tokenExpiration;

	public UsuarioPublico() {

	}

	@PrePersist
	private void prePersist() {
		// limitRequest = 100;
		// timeUnitRequest = TimeUnit.MINUTES;
		// timeUnitToken = TimeUnit.MINUTES;
		// tokenExpiration = 60;
	}

}
