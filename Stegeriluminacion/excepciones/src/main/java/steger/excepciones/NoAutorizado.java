package steger.excepciones;

import java.util.HashMap;
import java.util.Map;
 
public class NoAutorizado<T> {
  
	public static Map<String, Object> noAutorizadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put("codigoHttp",  401 );
		mapException.put("codigo", 401 );
		mapException.put("cuerpo", "Recurso no autorizado");
		mapException.put("mensaje",  "Recurso no autorizado");
		mapException.put("estado", false);
		return mapException;
	}

	public static Map<String, Object> accedoDenegadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put("codigoHttp",  403 );
		mapException.put("codigo", 403 );
		mapException.put("cuerpo", "Recurso no autorizado");
		mapException.put("mensaje",  "Recurso no autorizado");
		mapException.put("estado", false);
		return mapException;
	}

}
