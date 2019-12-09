package modelo.auth.oauth2;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import modelo.auth.usuarios.publicos.MotivoAltaBajaPublico;
import modelo.auth.usuarios.publicos.PermisoPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;

@Entity
@Table
@Data
public class OauthCode {

	@Id
	@Column(name = "code")
	private String code;

	@Lob 
	@Column(name = "authentication", length= 16777215  ) //MEDIUM BLOB
	private byte[] authentication;
 
	
}
