package model.auth.usuarios.fingerprint;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class RequestOfLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 800)
	private String uuid;

	@Column(length = 500)
	private String usuario;

    @Lob
	private String access_token; 

    @Lob
	private String refresh_token;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;

	@LastModifiedDate
	LocalDateTime fechaActualizacion;

	@PrePersist
	public void prePersist() {
		this.uuid = UUID.randomUUID().toString();
	}

	public RequestOfLogin() {
	}

	public RequestOfLogin(@NotNull(message = "{RequestOfLogin.usuario.notnull}") String usuario) {
		super();
		this.usuario = usuario;
	}
}
