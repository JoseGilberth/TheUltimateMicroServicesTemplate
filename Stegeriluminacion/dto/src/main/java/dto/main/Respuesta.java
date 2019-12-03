package dto.main;

public class Respuesta<T> {

	private int codigoHttp;
	private int codigo;
	private T cuerpo;
	private String mensaje;
	private boolean estado;

	public Respuesta(int codigoHttp, int codigo, T cuerpo, String mensaje, boolean estado) {
		super();
		this.codigoHttp = codigoHttp;
		this.codigo = codigo;
		this.cuerpo = cuerpo;
		this.mensaje = mensaje;
		this.estado = estado;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
