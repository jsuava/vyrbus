/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14/10/2015
 * Hora			: 15:09:34
 */
package com.cystesoft.vyrbus.model.bean.report;

import java.io.Serializable;

/**
 * @author jabanto
 *
 */
public class RptAvanceBus implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String fechaPartida;
	private String horaPartida;
	private String servicio;
	private String ruta;
	private Integer bus;
	private Integer tipoRuta;

	/**
	 * @return the fechaPartida
	 */
	public String getFechaPartida() {
		return fechaPartida;
	}
	/**
	 * @param fechaPartida the fechaPartida to set
	 */
	public void setFechaPartida(String fechaPartida) {
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
	 * @return the servicio
	 */
	public String getServicio() {
		return servicio;
	}
	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(String servicio) {
		this.servicio = servicio;
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
	 * @return the bus
	 */
	public Integer getBus() {
		return bus;
	}
	/**
	 * @param bus the bus to set
	 */
	public void setBus(Integer bus) {
		this.bus = bus;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return tipoRuta.toString()+horaPartida+servicio+ruta+fechaPartida;//+""+bus;
	}
	/**
	 * @return the tipoRuta
	 */
	public Integer getTipoRuta() {
		return tipoRuta;
	}
	/**
	 * @param tipoRuta the tipoRuta to set
	 */
	public void setTipoRuta(Integer tipoRuta) {
		this.tipoRuta = tipoRuta;
	}

}
