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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import modelo.auth.usuarios.Usuario;
import modelo.auth.usuarios.publicos.ubicacion.DireccionEntrega;
import modelo.auth.usuarios.publicos.ubicacion.DireccionFacturacion;
import modelo.auth.usuarios.publicos.ubicacion.DireccionVivienda;

@Entity
@Table(indexes = { @Index(columnList = "username", name = "index_usuario"),
		@Index(columnList = "correo", name = "index_correo") },
	uniqueConstraints={
		@UniqueConstraint(columnNames={"username"} , name = "unique_username"),
		@UniqueConstraint(columnNames={"correo"} , name = "unique_correo"),
		@UniqueConstraint(columnNames={"telefonoCelular"} , name = "unique_telefonoCelular")
	} 
)
public class UsuarioPublico extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "{usuario.publico.telefonoCelular.notnull}")
	@Length(min = 10, max = 10, message = "{usuario.publico.telefonoCelular.lenght}")	
	@Column(name = "telefonoCelular", unique = true, nullable = false, length = 10)
	private String telefonoCelular;

	@OneToOne
	@JoinColumn(nullable = true)
	private DireccionVivienda direccionVivienda;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DireccionFacturacion> direccionFacturacion;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DireccionEntrega> direccionEntrega;

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

	@PrePersist
	private void prePersist() {
	}

	@PreUpdate
	private void preUpdate() {
	}

	/**
	 * @return the telefonoCelular
	 */
	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	/**
	 * @param telefonoCelular the telefonoCelular to set
	 */
	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	/**
	 * @return the direccionVivienda
	 */
	public DireccionVivienda getDireccionVivienda() {
		return direccionVivienda;
	}

	/**
	 * @param direccionVivienda the direccionVivienda to set
	 */
	public void setDireccionVivienda(DireccionVivienda direccionVivienda) {
		this.direccionVivienda = direccionVivienda;
	}

	/**
	 * @return the direccionFacturacion
	 */
	public Set<DireccionFacturacion> getDireccionFacturacion() {
		return direccionFacturacion;
	}

	/**
	 * @param direccionFacturacion the direccionFacturacion to set
	 */
	public void setDireccionFacturacion(Set<DireccionFacturacion> direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	/**
	 * @return the direccionEntrega
	 */
	public Set<DireccionEntrega> getDireccionEntrega() {
		return direccionEntrega;
	}

	/**
	 * @param direccionEntrega the direccionEntrega to set
	 */
	public void setDireccionEntrega(Set<DireccionEntrega> direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	/**
	 * @return the permisos
	 */
	public Set<PermisoPublico> getPermisos() {
		return permisos;
	}

	/**
	 * @param permisos the permisos to set
	 */
	public void setPermisos(Set<PermisoPublico> permisos) {
		this.permisos = permisos;
	}

	/**
	 * @return the motivosAltaBaja
	 */
	public Set<MotivoAltaBajaPublico> getMotivosAltaBaja() {
		return motivosAltaBaja;
	}

	/**
	 * @param motivosAltaBaja the motivosAltaBaja to set
	 */
	public void setMotivosAltaBaja(Set<MotivoAltaBajaPublico> motivosAltaBaja) {
		this.motivosAltaBaja = motivosAltaBaja;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getLimitRequest() {
		return limitRequest;
	}

	public void setLimitRequest(Integer limitRequest) {
		this.limitRequest = limitRequest;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(Integer tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	} 
}
