package model.auth.usuarios.fingerprint;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
public class FingerPrintAuthentication implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private String usuario;
	
	/* MANO DERECHA */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdPulgarDerecho.class)
	private Set<FingerPrintFmdPulgarDerecho> fingerPrintFmdPulgarDerecho;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdIndiceDerecho.class)
	private Set<FingerPrintFmdIndiceDerecho> fingerPrintFmdIndiceDerecho;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdMedioDerecho.class)
	private Set<FingerPrintFmdMedioDerecho> fingerPrintFmdMedioDerecho;

	/* MANO IZQUIERDA */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdPulgarIzquierdo.class)
	private Set<FingerPrintFmdPulgarIzquierdo> fingerPrintFmdPulgarIzquierdo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdIndiceIzquierdo.class)
	private Set<FingerPrintFmdIndiceIzquierdo> fingerPrintFmdIndiceIzquierdo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fingerPrintAuthentication", targetEntity = FingerPrintFmdMedioIzquierdo.class)
	private Set<FingerPrintFmdMedioIzquierdo> fingerPrintFmdMedioIzquierdo;

}
