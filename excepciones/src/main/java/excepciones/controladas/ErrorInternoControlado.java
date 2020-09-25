package excepciones.controladas;

import dto.main.Respuesta;
import excepciones.CustomError;
import utils._config.language.Translator;

public class ErrorInternoControlado extends CustomError {

	private static final long serialVersionUID = 1L;

	public static int USUARIO_DUPLICADO = 512;
	public static int USUARIO_O_CORREO_DUPLICADO = 513;
	public static int USUARIO_NO_ACTIVO = 514;
	public static int CORREO_NO_EXISTE = 514;
	public static int PASSWORD_NO_COINCIDEN = 515;
	public static int TOKEN_EXPIRADO = 516;
	public static int TOKEN_NO_EXISTE = 517;
	public static int PASSWORD_NO_VALIDO = 518;
	public static int CORREO_ENVIO_ERROR = 519;

	public static <T> Respuesta<T> usuarioDuplicado(String usuario) {
		return error(USUARIO_DUPLICADO, null, Translator.toLocale("usuarios.duplicado", new String[] { usuario }));
	}

	public static <T> Respuesta<T> usuarioOCorreoDuplicado(String usuario) {
		return error(USUARIO_O_CORREO_DUPLICADO, null,
				Translator.toLocale("usuarios.correo.duplicado", new String[] { usuario }));
	}

	public static <T> Respuesta<T> correoNoExiste(String usuario) {
		return error(CORREO_NO_EXISTE, null, Translator.toLocale("correo.noexiste", new String[] { usuario }));
	}

	public static <T> Respuesta<T> usuarioNoActivo(T cuerpo) {
		return error(USUARIO_NO_ACTIVO, cuerpo, Translator.toLocale("usuarios.noactivo"));
	}

	public static <T> Respuesta<T> passwordsNoCoinciden() {
		return error(PASSWORD_NO_COINCIDEN, null, Translator.toLocale("usuarios.password.nocoinciden"));
	}

	public static <T> Respuesta<T> passwordNoValido(String cuerpo) {
		return error(PASSWORD_NO_VALIDO, null, cuerpo.toString());
	}
	
	public static <T> Respuesta<T> tokenExpirado(T cuerpo) {
		return error(TOKEN_EXPIRADO, cuerpo, Translator.toLocale("token.expirado"));
	}

	public static <T> Respuesta<T> tokenNoExiste(T cuerpo) {
		return error(TOKEN_NO_EXISTE, cuerpo, Translator.toLocale("token.no.existe"));
	}

	public static <T> Respuesta<T> errorAlEnviarElCorreo(T cuerpo) {
		return error(CORREO_ENVIO_ERROR, cuerpo, Translator.toLocale("correo.envio.error"));
	}

}
