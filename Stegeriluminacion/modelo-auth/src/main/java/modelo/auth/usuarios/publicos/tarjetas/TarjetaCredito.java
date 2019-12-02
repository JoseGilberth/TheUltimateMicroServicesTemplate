package modelo.auth.usuarios.publicos.tarjetas;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data; 

@Data
@Entity
@Table( )
public class TarjetaCredito extends Tarjeta implements Serializable{
 
	private static final long serialVersionUID = 1L;
 

}
