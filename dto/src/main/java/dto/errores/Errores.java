package dto.errores;
 
import java.util.LinkedHashMap;

import lombok.Data;

@Data
public class Errores {

	private String mensaje;
	private String general;
	private String modelo;
	private LinkedHashMap<String, String> lista;

	public Errores() {
	}

	public Errores(String general) {
		this.general = general;
	}
	public Errores(LinkedHashMap<String, String> lista) {
		this.lista = lista;
	}
	public Errores(String general, String modelo) {
		this.general = general;
		this.modelo = modelo;
	}

	public Errores(String general, LinkedHashMap<String, String> lista) {
		this.general = general;
		this.lista = lista;
	} 

	
	
}
