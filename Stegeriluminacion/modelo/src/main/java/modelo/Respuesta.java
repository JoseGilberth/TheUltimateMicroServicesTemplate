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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import modelo.auth.usuarios.administradores.UsuarioAdministrador;
import modelo.auth.usuarios.publicos.UsuarioPublico;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Respuesta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Debe agregar una respuesta.")
	@Length(min = 10, max = 500 , message = "La respuesta debe contener al menos 3 caracteres y menos de 500 caracteres.")
	@Column(name = "respuesta", unique = false, nullable = false, length = 255)
	private String respuesta;
 
	@NotNull(message = "Debe indicar un producto.") 
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pregunta pregunta;

	@ManyToOne
	@JoinColumn(nullable = true)
	private UsuarioPublico usuarioPublico;

	@ManyToOne
	@JoinColumn(nullable = true)
	private UsuarioAdministrador usuarioAdministrador;

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

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public UsuarioPublico getUsuarioPublico() {
		return usuarioPublico;
	}

	public void setUsuarioPublico(UsuarioPublico usuarioPublico) {
		this.usuarioPublico = usuarioPublico;
	}

	public UsuarioAdministrador getUsuarioAdministrador() {
		return usuarioAdministrador;
	}

	public void setUsuarioAdministrador(UsuarioAdministrador usuarioAdministrador) {
		this.usuarioAdministrador = usuarioAdministrador;
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
