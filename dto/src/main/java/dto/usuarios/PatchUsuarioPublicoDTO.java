package dto.usuarios;
import lombok.Data;

@Data
public class PatchUsuarioPublicoDTO {

	private String nombre;

	private String apellido1;

	private String apellido2;

	private String telefonoCelular;

	private String password;

	private String repetirPassword;

}