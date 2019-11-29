package dto.micro.usuarios;

import java.time.LocalDateTime;

public class FiltroUsuarioPublicoDTO {

	private Long id;
	private String username;
	private String correo;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Boolean enabled;
	private LocalDateTime fechaAltaInicial;
	private Long fechaAltaFinal; 
	
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the fechaAltaInicial
	 */
	public LocalDateTime getFechaAltaInicial() {
		return fechaAltaInicial;
	}
	/**
	 * @param fechaAltaInicial the fechaAltaInicial to set
	 */
	public void setFechaAltaInicial(LocalDateTime fechaAltaInicial) {
		this.fechaAltaInicial = fechaAltaInicial;
	}
	/**
	 * @return the fechaAltaFinal
	 */
	public Long getFechaAltaFinal() {
		return fechaAltaFinal;
	}
	/**
	 * @param fechaAltaFinal the fechaAltaFinal to set
	 */
	public void setFechaAltaFinal(Long fechaAltaFinal) {
		this.fechaAltaFinal = fechaAltaFinal;
	} 
	
	
}