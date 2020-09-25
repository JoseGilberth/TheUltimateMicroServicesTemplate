package excepciones;

import dto.main.Respuesta;

public abstract class CustomError extends Exception {

	private static final long serialVersionUID = 1L;

	public static <T> Respuesta<T> error(int codigoHttp, T cuerpo, String mensaje) {
		return new Respuesta<T>(codigoHttp, cuerpo, mensaje);
	}
}
