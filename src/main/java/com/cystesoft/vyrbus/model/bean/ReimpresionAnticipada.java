/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/07/2016
 * Hora			: 14:43:48
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jabanto
 *
 */
public class ReimpresionAnticipada  extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Agencia agencia;
	private CanalVenta canalVenta;
	private Date fechaEmision;
	private Integer tiempoReimpresion;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the canalVenta
	 */
	public CanalVenta getCanalVenta() {
		return canalVenta;
	}
	/**
	 * @param canalVenta the canalVenta to set
	 */
	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
	}
	
	/**
	 * @return the tiempoReimpresion
	 */
	public Integer getTiempoReimpresion() {
		return tiempoReimpresion;
	}
	/**
	 * @param tiempoReimpresion the tiempoReimpresion to set
	 */
	public void setTiempoReimpresion(Integer tiempoReimpresion) {
		this.tiempoReimpresion = tiempoReimpresion;
	}
	/**
	 * @return the fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	

}
