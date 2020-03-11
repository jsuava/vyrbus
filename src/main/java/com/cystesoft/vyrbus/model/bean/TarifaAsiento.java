/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25 oct. 2019
 * Hora			: 13:36:45
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author Marco
 *
 */
public class TarifaAsiento extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Tarifa tarifa;
	private Itinerario itinerario;
	private Integer numeroAsiento;
	private Double monto;
	private String dias;
	private Date fechaActivacion;
	private Date fechaCaducidad;
	private String horaPartida;	  
	private Date fechaSuspension;
	  
	public TarifaAsiento(){
		  
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getHoraPartida() {
		return horaPartida;
	}

	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}

	public Date getFechaSuspension() {
		return fechaSuspension;
	}

	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}


}
