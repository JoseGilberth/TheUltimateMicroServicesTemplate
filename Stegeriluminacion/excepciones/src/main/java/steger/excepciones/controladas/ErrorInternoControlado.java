package steger.excepciones.controladas;

import dto.main.Respuesta;
import steger.excepciones.CustomError;
import steger.excepciones._config.languaje.Translator;

public class ErrorInternoControlado extends CustomError {

	private static final long serialVersionUID = 1L;
	
	public static int USUARIO_DUPLICADO = 512;
	public static int USUARIO_O_CORREO_DUPLICADO = 513;
	public static int USUARIO_NO_ACTIVO = 514;
	public static int PASSWORD_NO_COINCIDEN = 515;
	public static int TOKEN_EXPIRADO = 516;
	public static int TOKEN_NO_EXISTE = 517;
	public static int COMENTARIO_BORRADO_NO_AUTORIZADO = 518;
	public static int CORREO_ENVIO_ERROR = 518;

	public static <T> Respuesta<T> usuarioDuplicado(T cuerpo) {
		return error(USUARIO_DUPLICADO, USUARIO_DUPLICADO, cuerpo, Translator.toLocale("usuarios.duplicado"), false);
	}

	public static <T> Respuesta<T> usuarioOCorreoDuplicado(T cuerpo) {
		return error(USUARIO_O_CORREO_DUPLICADO, USUARIO_O_CORREO_DUPLICADO, cuerpo,
				Translator.toLocale("usuarios.correo.duplicado"), false);
	}

	public static <T> Respuesta<T> usuarioNoActivo(T cuerpo) {
		return error(USUARIO_NO_ACTIVO, USUARIO_NO_ACTIVO, cuerpo, Translator.toLocale("usuarios.noactivo"), false);
	}

	public static <T> Respuesta<T> passwordsNoCoinciden(T cuerpo) {
		return error(PASSWORD_NO_COINCIDEN, PASSWORD_NO_COINCIDEN, cuerpo,
				Translator.toLocale("usuarios.password.nocoinciden"), false);
	}

	public static <T> Respuesta<T> tokenExpirado(T cuerpo) {
		return error(TOKEN_EXPIRADO, TOKEN_EXPIRADO, cuerpo, Translator.toLocale("token.expirado"), false);
	}

	public static <T> Respuesta<T> tokenNoExiste(T cuerpo) {
		return error(TOKEN_NO_EXISTE, TOKEN_NO_EXISTE, cuerpo, Translator.toLocale("token.noexiste"), false);
	}

	public static <T> Respuesta<T> borradoComentarioNoAutorizado(T cuerpo) {
		return error(COMENTARIO_BORRADO_NO_AUTORIZADO, COMENTARIO_BORRADO_NO_AUTORIZADO, cuerpo,
				Translator.toLocale("comentario.borrar.noautorizado"), false);
	}

	public static <T> Respuesta<T> errorAlEnviarElCorreo(T cuerpo) {
		return error(CORREO_ENVIO_ERROR, CORREO_ENVIO_ERROR, cuerpo, Translator.toLocale("correo.envio.error"), false);
	}

}
