package modelo.producto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class UnidadMedida implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Debe indicar un nombre a la unidad de medida.")
	@Length(min = 3, max = 255, message = "El nombre debe contener al menos 3 caracteres y menos 255 caracteres.")
	@Column(name = "etiqueta", unique = true, nullable = false, length = 40)
	private String etiqueta;

	@NotEmpty(message = "Debe indicar un nombre corto a la unidad de medida.")
	@Length(min = 3, max = 255, message = "El nombre debe contener al menos 3 caracteres y menos 255 caracteres.")
	@Column(name = "etiquetaCorta", unique = true, nullable = false, length = 40)
	private String etiquetaCorta;

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

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getEtiquetaCorta() {
		return etiquetaCorta;
	}

	public void setEtiquetaCorta(String etiquetaCorta) {
		this.etiquetaCorta = etiquetaCorta;
	}

}
