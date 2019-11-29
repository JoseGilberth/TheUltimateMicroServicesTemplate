package dto.errores;


import java.util.LinkedHashMap;

public class Errores {

	private String mensaje;
	private String general;
	private String modelo;
	private LinkedHashMap<String, String> lista;

	public Errores() {
	}

	public Errores(String general) {
		super();
		this.general = general;
	}
	public Errores(LinkedHashMap<String, String> lista) {
		super();
		this.lista = lista;
	}
	public Errores(String general, String modelo) {
		super();
		this.general = general;
		this.modelo = modelo;
	}

	public Errores(String general, LinkedHashMap<String, String> lista) {
		super();
		this.general = general;
		this.lista = lista;
	}



	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public LinkedHashMap<String, String> getLista() {
		return lista;
	}

	public void setLista(LinkedHashMap<String, String> lista) {
		this.lista = lista;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
}
