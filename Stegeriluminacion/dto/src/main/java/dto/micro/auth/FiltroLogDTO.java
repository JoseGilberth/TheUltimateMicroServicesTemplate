package dto.micro.auth;

import java.util.Date;

public class FiltroLogDTO {

	private Long id;
	private String usuario;
	private String tipoUsuario;
	private String apartado;
	private String accion;
	private Date fechaAltaInicial;
	private Date fechaAltaFinal;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the tipoUsuario
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario the tipoUsuario to set
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * @return the apartado
	 */
	public String getApartado() {
		return apartado;
	}

	/**
	 * @param apartado the apartado to set
	 */
	public void setApartado(String apartado) {
		this.apartado = apartado;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return the fechaAltaInicial
	 */
	public Date getFechaAltaInicial() {
		return fechaAltaInicial;
	}

	/**
	 * @param fechaAltaInicial the fechaAltaInicial to set
	 */
	public void setFechaAltaInicial(Date fechaAltaInicial) {
		this.fechaAltaInicial = fechaAltaInicial;
	}

	/**
	 * @return the fechaAltaFinal
	 */
	public Date getFechaAltaFinal() {
		return fechaAltaFinal;
	}

	/**
	 * @param fechaAltaFinal the fechaAltaFinal to set
	 */
	public void setFechaAltaFinal(Date fechaAltaFinal) {
		this.fechaAltaFinal = fechaAltaFinal;
	}

	

}
