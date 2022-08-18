/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 03/09/2012
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class SecuenciaTramo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer origen;
	private Integer destino;
	private Integer orden;
	/**
	 *
	 */
	public SecuenciaTramo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param origen
	 * @param destino
	 * @param orden
	 */
	public SecuenciaTramo(Integer origen, Integer destino, Integer orden) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.orden = orden;
	}

	/**
	 * @return Objeto origen.
	 */
	public Integer getOrigen() {
		return origen;
	}
	/**
	 * @param origen	: Setea el objeto origen.
	 */
	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	/**
	 * @return Objeto destino.
	 */
	public Integer getDestino() {
		return destino;
	}
	/**
	 * @param destino	: Setea el objeto destino.
	 */
	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	/**
	 * @return Objeto orden.
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden	: Setea el objeto orden.
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.origen.toString()+"-"+this.destino+"-"+this.orden;
	}
}
