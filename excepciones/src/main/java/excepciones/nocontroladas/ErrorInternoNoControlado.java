package excepciones.nocontroladas;

import dto.main.Respuesta;
import excepciones.CustomError;
import utils._config.language.Translator;

public class ErrorInternoNoControlado extends CustomError {

	private static final long serialVersionUID = 1L;
	public static int EXCEPCION_DESCONOCIDA = 550;
	public static int INTEGRIDAD_DATOS = 551;
	public static int ERROR_VALIDAR_MODELO = 552;
	public static int RECURSO_NO_ENCONTRADO = 404;

	public static <T> Respuesta<T> excepcionDesconocida(T cuerpo) {
		return error(EXCEPCION_DESCONOCIDA, cuerpo, Translator.toLocale("excepcion.desconocida"));
	}

	public static <T> Respuesta<T> integridadDeDatos(T cuerpo) {
		return error(INTEGRIDAD_DATOS, cuerpo, Translator.toLocale("integridad.datos"));
	}

	public static <T> Respuesta<T> errorValidacionModelo(T cuerpo) {
		return error(ERROR_VALIDAR_MODELO, cuerpo, Translator.toLocale("error.validar.modelo"));
	}

	public static <T> Respuesta<T> recursoNoEncontrado(T cuerpo) {
		return error(RECURSO_NO_ENCONTRADO, cuerpo, Translator.toLocale("recurso.no.encontrado"));
	}

}
