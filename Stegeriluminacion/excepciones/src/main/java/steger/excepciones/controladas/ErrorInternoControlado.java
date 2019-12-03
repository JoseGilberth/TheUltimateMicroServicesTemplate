package steger.excepciones.controladas;

import dto.main.Respuesta;
import steger.excepciones.CustomError;
import steger.excepciones._config.languaje.Translator;

public class ErrorInternoControlado extends CustomError {

	private static final long serialVersionUID = 1L;
	public static int[] RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO = { 512, 512 };
	public static int[] CORREO_NO_ENVIADO = { 513, 513 }; 
	 
	public static <T> Respuesta<T> usuarioDuplicado(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, Translator.toLocale("usuarios.duplicado"), false);
	}

	public static <T> Respuesta<T> usuarioNoActivo(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> passwordsNoCoinciden(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> tokenExpirado(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> tokenNoExiste(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}
	
	public static <T> Respuesta<T> borradoComentarioNoAutorizado(T cuerpo) {
		return error(RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[0], RECURSO_DUPLICADO_USUARIO_ADMINISTRATIVO[1], cuerpo, "El usuario no existe", false);
	}

	public static <T> Respuesta<T> errorAlEnviarElCorreo( T cuerpo) {
		return error(CORREO_NO_ENVIADO[0], CORREO_NO_ENVIADO[1], cuerpo, Translator.toLocale("error.correo.envio") , false);
	}
	
	
	
}
