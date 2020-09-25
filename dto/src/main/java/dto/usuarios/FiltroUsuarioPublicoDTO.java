package dto.usuarios;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import lombok.Data;

@Data
public class FiltroUsuarioPublicoDTO {

	private Long id;
	private String username;
	private String correo;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String telefonoCelular;
	private Integer limitRequestInicial;
	private Integer limitRequestFinal;
	private TimeUnit timeUnitRequest;
	private TimeUnit timeUnitToken;
	private Integer tokenExpirationInicial;
	private Integer tokenExpirationFinal;
	private LocalDateTime fechaAltaInicial;
	private LocalDateTime fechaAltaFinal;
	private Boolean enabled;

}