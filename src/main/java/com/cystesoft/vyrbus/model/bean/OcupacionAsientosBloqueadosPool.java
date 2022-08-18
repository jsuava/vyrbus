/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 08/09/2016
 * Hora			: 11:58:36
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;


/**
 * @author jabanto
 *
 */
public class OcupacionAsientosBloqueadosPool extends GenericBean{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String operacion;
	private String ruta;
	private String codigoTransaccion;
	private Usuario usuario;
	private Agencia agencia;
	private String numeroAsiento;
	private Integer piso;
	private Date fechaPartida;
	private String horaPartida;
	private Date fechaExpiraBloqueo;
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
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}
	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the codigoTransaccion
	 */
	public String getCodigoTransaccion() {
		return codigoTransaccion;
	}
	/**
	 * @param codigoTransaccion the codigoTransaccion to set
	 */
	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the numeroAsiento
	 */
	public String getNumeroAsiento() {
		return numeroAsiento;
	}
	/**
	 * @param numeroAsiento the numeroAsiento to set
	 */
	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}
	/**
	 * @return the piso
	 */
	public Integer getPiso() {
		return piso;
	}
	/**
	 * @param piso the piso to set
	 */
	public void setPiso(Integer piso) {
		this.piso = piso;
	}
	/**
	 * @return the fechaPartida
	 */
	public Date getFechaPartida() {
		return fechaPartida;
	}
	/**
	 * @param fechaPartida the fechaPartida to set
	 */
	public void setFechaPartida(Date fechaPartida) {
		this.fechaPartida = fechaPartida;
	}
	/**
	 * @return the horaPartida
	 */
	public String getHoraPartida() {
		return horaPartida;
	}
	/**
	 * @param horaPartida the horaPartida to set
	 */
	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}
	/**
	 * @return the fechaExpiraBloqueo
	 */
	public Date getFechaExpiraBloqueo() {
		return fechaExpiraBloqueo;
	}
	/**
	 * @param fechaExpiraBloqueo the fechaExpiraBloqueo to set
	 */
	public void setFechaExpiraBloqueo(Date fechaExpiraBloqueo) {
		this.fechaExpiraBloqueo = fechaExpiraBloqueo;
	}


}
