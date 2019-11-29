package dto.micro.administracion.producto;

import java.time.LocalDateTime;
import java.util.UUID;

import modelo.producto.Marca;
import modelo.producto.UnidadMedida;
import modelo.producto.UnidadVenta;
 
public class FiltroProductoDTO {

	private String etiqueta;
	private boolean activo;
	private boolean activoPublico;
	private UUID uuid;
	private double costo;
	private String descripcion;
	private UnidadMedida unidadMedida;
	private UnidadVenta unidadVenta;
	private Marca marca; 
	private LocalDateTime fechaAltaInicial;
	private LocalDateTime fechaAltaFinal;
	
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isActivoPublico() {
		return activoPublico;
	}
	public void setActivoPublico(boolean activoPublico) {
		this.activoPublico = activoPublico;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public UnidadVenta getUnidadVenta() {
		return unidadVenta;
	}
	public void setUnidadVenta(UnidadVenta unidadVenta) {
		this.unidadVenta = unidadVenta;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	public LocalDateTime getFechaAltaInicial() {
		return fechaAltaInicial;
	}
	public void setFechaAltaInicial(LocalDateTime fechaAltaInicial) {
		this.fechaAltaInicial = fechaAltaInicial;
	}
	public LocalDateTime getFechaAltaFinal() {
		return fechaAltaFinal;
	}
	public void setFechaAltaFinal(LocalDateTime fechaAltaFinal) {
		this.fechaAltaFinal = fechaAltaFinal;
	}
  
	
	
}
