package dto.micro.publico.favoritos;

import java.time.LocalDateTime;

public class FiltroFavoritosDTO {


	private Double precioAlto;
	
	private Double precioBajo;
	
	private LocalDateTime fechaInicial;
	
	private LocalDateTime fechaFinal;
	
	
	public LocalDateTime getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(LocalDateTime fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public LocalDateTime getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDateTime fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Double getPrecioAlto() {
		return precioAlto;
	}

	public void setPrecioAlto(Double precioAlto) {
		this.precioAlto = precioAlto;
	}

	public Double getPrecioBajo() {
		return precioBajo;
	}

	public void setPrecioBajo(Double precioBajo) {
		this.precioBajo = precioBajo;
	}

	

}
