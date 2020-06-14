package dto.main;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data 
@RequiredArgsConstructor
public class MessageWebsocket {

	@NonNull private String usuario;
	@NonNull private String mensaje;
	@NonNull private String accion;
	
	
}
