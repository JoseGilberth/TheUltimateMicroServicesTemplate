package model.general;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{file.etiqueta.notblank}")
	@Length(min = 1, max = 255, message = "{file.etiqueta.length}")
	@Column(nullable = false)
	private String etiqueta;

	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] archivo;

	@NotBlank(message = "{file.mimeType.notblank}")
	@Length(min = 1, max = 255, message = "{file.mimeType.length}")
	@Column(nullable = false)
	private String mimeType;

	@Min(value = 1, message = "{file.tamano.min}")
	@Column(nullable = false)
	private int tamano;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime fechaActualizacion;

	@PrePersist
	private void prePersist() {
		this.tamano = archivo.length;
	}

}