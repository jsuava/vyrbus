/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:00:32
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author Marco
 *
 */
public class TarifaCliente extends GenericBean{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Cliente cliente;
	private Ruta ruta;
	private Servicio servicio;
	private Date fechaActivacion;
	private Date fechaCaducidad;
	private Date fechaSuspension;
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
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	 * @return the servicio
	 */
	public Servicio getServicio() {
		return servicio;
	}
	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	/**
	 * @return the fechaActivacion
	 */
	public Date getFechaActivacion() {
		return fechaActivacion;
	}
	/**
	 * @param fechaActivacion the fechaActivacion to set
	 */
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	/**
	 * @return the fechaCaducidad
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	/**
	 * @param fechaCaducidad the fechaCaducidad to set
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	/**
	 * @return the fechaSuspension
	 */
	public Date getFechaSuspension() {
		return fechaSuspension;
	}
	/**
	 * @param fechaSuspension the fechaSuspension to set
	 */
	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}
	
	
	
}
