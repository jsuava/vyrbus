package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JABANTO
 *
 */
public class Transbordo extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private VentaPasaje ventaPasaje;
	private Itinerario itinerarioOrigen;
	private Servicio servicioOrigen;
	private Date fechaPartidaOrigen;
	private String horaPartidaOrigen;
	private Integer numeroAsientoOrigen;
	private Itinerario itinerarioDestino;
	private Servicio servicioDestino;
	private Date fechaPartidaDestino;
	private String horaPartidaDestino;
	private Integer numeroAsientoDestino;

	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return this.id;
	}

	public void setVentaPasaje(VentaPasaje ventaPasaje){
		this.ventaPasaje=ventaPasaje;
	}
	public VentaPasaje getVentaPasaje(){
		return this.ventaPasaje;
	}

	public void setItinerarioOrigen(Itinerario itinerarioOrigen){
		this.itinerarioOrigen=itinerarioOrigen;
	}
	public Itinerario getItinerarioOrigen(){
		return this.itinerarioOrigen;
	}

	public void setServicioOrigen(Servicio servicioOrigen){
		this.servicioOrigen=servicioOrigen;
	}
	public Servicio getServicioOrigen(){
		return this.servicioOrigen;
	}

	public void setFechaPartidaOrigen(Date fechaPartidaOrigen){
		this.fechaPartidaOrigen=fechaPartidaOrigen;
	}
	public Date getFechaPartidaOrigen(){
		return this.fechaPartidaOrigen;
	}

	public void setHoraPartidaOrigen(String horaPartidaOrigen){
		this.horaPartidaOrigen=horaPartidaOrigen;
	}
	public String getHoraPartidaOrigen(){
		return this.horaPartidaOrigen;
	}

	public void setNumeroAsientoOrigen(Integer numeroAsientoOrigen){
		this.numeroAsientoOrigen=numeroAsientoOrigen;
	}
	public Integer getNumeroAsientoOrigen(){
		return this.numeroAsientoOrigen;
	}

	public void setItinerarioDestino(Itinerario itinerarioDestino){
		this.itinerarioDestino=itinerarioDestino;
	}
	public Itinerario getItinerarioDestino(){
		return this.itinerarioDestino;
	}

	public void setServicioDestino(Servicio servicioDestino){
		this.servicioDestino=servicioDestino;
	}
	public Servicio getServicioDestino(){
		return this.servicioDestino;
	}

	public void setFechaPartidaDestino(Date fechaPartidaDestino){
		this.fechaPartidaDestino=fechaPartidaDestino;
	}
	public Date getFechaPartidaDestino(){
		return this.fechaPartidaDestino;
	}

	public void setHoraPartidaDestino(String horaPartidaDestino){
		this.horaPartidaDestino=horaPartidaDestino;
	}
	public String getHoraPartidaDestino(){
		return this.horaPartidaDestino;
	}

	public void setNumeroAsientoDestino(Integer numeroAsientoDestino){
		this.numeroAsientoDestino=numeroAsientoDestino;
	}
	public Integer getNumeroAsientoDestino(){
		return this.numeroAsientoDestino;
	}

}
