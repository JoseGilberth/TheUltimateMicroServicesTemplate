package modelo.auth.usuarios.publicos.cuentas;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data; 

@Data
@Entity
@Table( )
public class PayPal extends Cuenta implements Serializable{
 
	private static final long serialVersionUID = 1L;
 

}
