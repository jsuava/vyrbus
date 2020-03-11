/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 04/05/2016
 * Hora			: 11:28:17
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author jabanto
 *
 */
public class Session extends GenericBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3160476508730765011L;
	private long id;
	private Usuario usuario;
	private UsuarioHardware usuarioHardware;
	private String nombrePc;
	private String mac;
	private String codigoAutorizacion;
	private Integer sessionIniciada;
	private Date fechaInicial;
	private Date fechaFin;
	private Date fechaAccesoSistema;
	private String agencia;
	private Integer estado;
	private String observaciones;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return the usuarioHardware
	 */
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}
	/**
	 * @return the nombrePc
	 */
	public String getNombrePc() {
		return nombrePc;
	}
	/**
	 * @param nombrePc the nombrePc to set
	 */
	public void setNombrePc(String nombrePc) {
		this.nombrePc = nombrePc;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the codigoAutorizacion
	 */
	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}
	/**
	 * @param codigoAutorizacion the codigoAutorizacion to set
	 */
	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}
	/**
	 * @return the sessionIniciada
	 */
	public Integer getSessionIniciada() {
		return sessionIniciada;
	}
	/**
	 * @param sessionIniciada the sessionIniciada to set
	 */
	public void setSessionIniciada(Integer sessionIniciada) {
		this.sessionIniciada = sessionIniciada;
	}
	/**
	 * @return the fechaInicialValidez
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}
	/**
	 * @param fechaInicial the fechaInicialValidez to set
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFinValidez to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the fechaAccesoSistema
	 */
	public Date getFechaAccesoSistema() {
		return fechaAccesoSistema;
	}
	/**
	 * @param fechaAccesoSistema the fechaAccesoSistema to set
	 */
	public void setFechaAccesoSistema(Date fechaAccesoSistema) {
		this.fechaAccesoSistema = fechaAccesoSistema;
	}
	/**
	 * @return the agencia
	 */
	public String getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
