package steger.excepciones;

import java.util.HashMap;
import java.util.Map;

import steger.excepciones._config.languaje.Translator;
 
public class NoAutorizado<T> {
  
	public static Map<String, Object> noAutorizadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put("codigoHttp",  401 );
		mapException.put("codigo", 401 );
		mapException.put("cuerpo", Translator.toLocale("recurso.noautorizado"));
		mapException.put("mensaje",  Translator.toLocale("recurso.noautorizado"));
		mapException.put("estado", false);
		return mapException;
	}

	public static Map<String, Object> accesoDenegadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put("codigoHttp",  403 );
		mapException.put("codigo", 403 );
		mapException.put("cuerpo", Translator.toLocale("acceso.denegado"));
		mapException.put("mensaje", Translator.toLocale("acceso.denegado"));
		mapException.put("estado", false);
		return mapException;
	}

}
