package steger.excepciones.nocontroladas;

import dto.main.Respuesta;
import steger.excepciones.CustomError;

public class ErrorInternoNoControlado extends CustomError {

	private static final long serialVersionUID = 1L;
	public static int[] RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO = { 512, 512 };
 
	public static <T> Respuesta<T> excepcionDesconocida(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> integridadDeDatos(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> errorValidacionModelo(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}
	
	public static <T> Respuesta<T> recursoNoEncontrado(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}
	
	
}
