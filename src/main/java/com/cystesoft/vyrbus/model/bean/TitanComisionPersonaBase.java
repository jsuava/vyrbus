/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 31/07/2014
 */
package com.cystesoft.vyrbus.model.bean;

/**
 * @author JABANTO
 *
 */
public class TitanComisionPersonaBase {
	private TitanPersona persona;
	private Double montoBase;
	private Integer estadoRegistroID; //Representa el monto de la base historica.
	/**
	 * @return the persona
	 */
	public TitanPersona getPersona() {
		return persona;
	}
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(TitanPersona persona) {
		this.persona = persona;
	}
	/**
	 * @return the montoBase
	 */
	public Double getMontoBase() {
		return montoBase;
	}
	/**
	 * @param montoBase the montoBase to set
	 */
	public void setMontoBase(Double montoBase) {
		this.montoBase = montoBase;
	}
	/**
	 * @return the estadoRegistroID
	 */
	public Integer getEstadoRegistroID() {
		return estadoRegistroID;
	}
	/**
	 * @param estadoRegistroID the estadoRegistroID to set
	 */
	public void setEstadoRegistroID(Integer estadoRegistroID) {
		this.estadoRegistroID = estadoRegistroID;
	}




}
