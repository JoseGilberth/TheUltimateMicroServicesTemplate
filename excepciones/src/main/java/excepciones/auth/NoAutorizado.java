package excepciones.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import utils.StaticVariables;
import utils._config.language.Translator;

public class NoAutorizado<T> {

	public static Map<String, Object> noAutorizadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put(StaticVariables.CODIGO_HTTP, HttpServletResponse.SC_UNAUTHORIZED);
		mapException.put(StaticVariables.CUERPO, Translator.toLocale("recurso.no.autorizado"));
		mapException.put(StaticVariables.MENSAJE, Translator.toLocale("recurso.no.autorizado"));
		return mapException;
	}

	public static Map<String, String> noAutorizadoMapString(String cuerpo) {
		final Map<String, String> mapException = new HashMap<>();
		mapException.put(StaticVariables.CODIGO_HTTP, String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
		mapException.put(StaticVariables.CUERPO, Translator.toLocale("recurso.no.autorizado"));
		mapException.put(StaticVariables.MENSAJE, Translator.toLocale("recurso.no.autorizado"));
		return mapException;
	}

	public static Map<String, Object> accesoDenegadoMap() {
		final Map<String, Object> mapException = new HashMap<>();
		mapException.put(StaticVariables.CODIGO_HTTP, HttpServletResponse.SC_FORBIDDEN);
		mapException.put(StaticVariables.CUERPO, Translator.toLocale("acceso.denegado"));
		mapException.put(StaticVariables.MENSAJE, Translator.toLocale("acceso.denegado"));
		return mapException;
	}

	public static Map<String, String> accesoDenegadoMapString() {
		final Map<String, String> mapException = new HashMap<>();
		mapException.put("codigoHttp", String.valueOf(HttpServletResponse.SC_FORBIDDEN));
		mapException.put("cuerpo", Translator.toLocale("acceso.denegado"));
		mapException.put("mensaje", Translator.toLocale("acceso.denegado"));
		return mapException;
	}

}
