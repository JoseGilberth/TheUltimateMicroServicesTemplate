package steger.excepciones;

import dto.main.Respuesta;

public abstract class CustomError extends Exception {
 
	private static final long serialVersionUID = 1L;

	public static <T> Respuesta<T> error(String mensaje) {
		Respuesta<T> respuesta = new Respuesta<T>();
		respuesta.setCodigo( 500 );
		respuesta.setCodigoHttp( 500 );
		respuesta.setCuerpo(null);
		respuesta.setEstado(false);
		respuesta.setMensaje(mensaje);
		return respuesta;
	}

	public static <T> Respuesta<T> error(int codigo, int codigoHttp, T cuerpo, String mensaje, boolean estado) {
		Respuesta<T> respuesta = new Respuesta<T>();
		respuesta.setCodigo(codigo);
		respuesta.setCodigoHttp(codigoHttp);
		respuesta.setCuerpo(cuerpo);
		respuesta.setEstado(estado);
		respuesta.setMensaje(mensaje);
		return respuesta;
	}
}
