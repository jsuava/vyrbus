/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 10/11/2014
 * Hora			: 14:21:54
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class VentaTramo extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Itinerario itinerario;
	private Ruta ruta;
	private Integer despuesHoraSalida;
	private Integer tiempo;
	private Date fechaInicio;
	private Date fechaFin;
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
	 * @return the itinerario
	 */
	public Itinerario getItinerario() {
		return itinerario;
	}
	/**
	 * @param itinerario the itinerario to set
	 */
	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}
	/**
	 * @return the despuesHoraSalida
	 */
	public Integer getDespuesHoraSalida() {
		return despuesHoraSalida;
	}
	/**
	 * @param despuesHoraSalida the despuesHoraSalida to set
	 */
	public void setDespuesHoraSalida(Integer despuesHoraSalida) {
		this.despuesHoraSalida = despuesHoraSalida;
	}
	/**
	 * @return the ruta
	 */
	public Ruta getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the tiempo
	 */
	public Integer getTiempo() {
		return tiempo;
	}
	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}



}
