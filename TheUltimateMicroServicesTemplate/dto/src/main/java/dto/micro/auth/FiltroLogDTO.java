package dto.micro.auth;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FiltroLogDTO {

	private Long id;
	private String usuario;
	private String tipoUsuario;
	private String apartado;
	private String accion;
	private LocalDateTime fechaAltaInicial;
	private LocalDateTime fechaAltaFinal;
 

}
