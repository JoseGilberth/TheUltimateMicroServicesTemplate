package dto.main;

public class Respuesta<T> {
	
	private int codigoHttp;
	private int codigo;
	private T cuerpo;
	private String mensaje;
	private boolean estado;
	
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
