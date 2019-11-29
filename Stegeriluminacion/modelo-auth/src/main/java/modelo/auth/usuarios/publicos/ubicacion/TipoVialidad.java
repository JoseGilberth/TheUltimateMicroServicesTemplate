package modelo.auth.usuarios.publicos.ubicacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement

@Table(
		indexes = {
		 	@Index(columnList = "etiqueta", name = "index_etiqueta" , unique = true )
		}
	)
public class TipoVialidad implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "version")
	private int version;

	@Column(nullable = false , unique = true )
	private String etiqueta;

	public Long getId() {
		return this.id; 
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TipoVialidad)) {
			return false;
		}
		TipoVialidad other = (TipoVialidad) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (etiqueta != null && !etiqueta.trim().isEmpty())
			result += "etiqueta: " + etiqueta;
		return result;
	}
}