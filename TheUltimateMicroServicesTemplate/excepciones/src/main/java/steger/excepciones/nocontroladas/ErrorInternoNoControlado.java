package steger.excepciones.nocontroladas;

import dto.main.Respuesta;
import steger.excepciones.CustomError;
import steger.excepciones._config.languaje.Translator;

public class ErrorInternoNoControlado extends CustomError {

	private static final long serialVersionUID = 1L;
	public static int EXCEPCION_DESCONOCIDA = 550;
	public static int INTEGRIDAD_DATOS = 551;
	public static int ERROR_VALIDAR_MODELO = 552;
	public static int RECURSO_NO_ENCONTRADO = 404;
 
	public static <T> Respuesta<T> excepcionDesconocida(T cuerpo) {
		return error(EXCEPCION_DESCONOCIDA, EXCEPCION_DESCONOCIDA, cuerpo, Translator.toLocale("excepcion.desconocida"), false);
	}

	public static <T> Respuesta<T> integridadDeDatos(T cuerpo) {
		return error(INTEGRIDAD_DATOS, INTEGRIDAD_DATOS, cuerpo, Translator.toLocale("integridad.datos"), false);
	}

	public static <T> Respuesta<T> errorValidacionModelo(T cuerpo) {
		return error(ERROR_VALIDAR_MODELO, ERROR_VALIDAR_MODELO, cuerpo, Translator.toLocale("error.validar.modelo"), false);
	}
	
	public static <T> Respuesta<T> recursoNoEncontrado(T cuerpo) {
		return error(RECURSO_NO_ENCONTRADO, RECURSO_NO_ENCONTRADO, cuerpo, Translator.toLocale("recurso.noencontrado"), false);
	}
	
	
}
