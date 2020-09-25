package model.auth.usuarios.fingerprint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import model.general.File;
 
@Entity
@XmlRootElement
@Table()
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class FingerPrintFmdMeniqueDerecho extends File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "finger_print_authentication_id")
	private FingerPrintAuthentication fingerPrintAuthentication;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FingerPrintFmdMeniqueDerecho other = (FingerPrintFmdMeniqueDerecho) obj;
		if (id == null) {
			if (other.getEtiqueta() != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	 
}