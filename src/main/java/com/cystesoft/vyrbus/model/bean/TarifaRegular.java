/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25 oct. 2019
 * Hora			: 13:26:01
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author Marco
 *
 */
public class TarifaRegular extends GenericBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Tarifa tarifa;
	private Itinerario itinerario;
	private Date fechaTarifa;
	private String horaPartida;
	private Double monto;
	
	public TarifaRegular(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Date getFechaTarifa() {
		return fechaTarifa;
	}
	public void setFechaTarifa(Date fechaTarifa) {
		this.fechaTarifa = fechaTarifa;
	}
	public String getHoraPartida() {
		return horaPartida;
	}
	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	
}
