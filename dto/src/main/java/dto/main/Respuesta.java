package dto.main;

public class Respuesta<T> {

	private int codigoHttp;
	private T cuerpo;
	private String mensaje;

	public Respuesta(int codigoHttp,  T cuerpo, String mensaje) {
		super();
		this.codigoHttp = codigoHttp;
		this.cuerpo = cuerpo;
		this.mensaje = mensaje;
	}

	public Respuesta() {
		super();
	}

	public int getCodigoHttp() {
		return codigoHttp;
	}

	public void setCodigoHttp(int codigoHttp) {
		this.codigoHttp = codigoHttp;
	}

	public T getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(T cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
