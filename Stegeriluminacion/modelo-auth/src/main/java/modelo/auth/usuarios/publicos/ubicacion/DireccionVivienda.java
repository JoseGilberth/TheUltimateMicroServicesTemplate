package modelo.auth.usuarios.publicos.ubicacion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table()
public class DireccionVivienda extends Direccion  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
}