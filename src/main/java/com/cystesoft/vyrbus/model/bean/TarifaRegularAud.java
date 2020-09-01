/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Oscco
 * Fecha		: 20 jul. 2020
 * Hora			: 11:20:53
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author Marco
 *
 */
public class TarifaRegularAud extends GenericBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idTarreg;
	private Long idTarifa;
	private Integer idCanven;
	private Integer idServicio;
	private Integer idRuta;
	private Integer pisoBus;
	private Integer zonaBus;
	private Date fechaTarifa;
	private String horaPartida;
	private Double monto;
	
	public TarifaRegularAud(){
		
	}
	
	/**
	 * @return the idTarreg
	 */
	public Long getIdTarreg() {
		return idTarreg;
	}

	/**
	 * @param idTarreg the idTarreg to set
	 */
	public void setIdTarreg(Long idTarreg) {
		this.idTarreg = idTarreg;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getidTarifa() {
		return idTarifa;
	}
	public void setidTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}
	
	/**
	 * @return the idCanven
	 */
	public Integer getIdCanven() {
		return idCanven;
	}

	/**
	 * @param idCanven the idCanven to set
	 */
	public void setIdCanven(Integer idCanven) {
		this.idCanven = idCanven;
	}

	/**
	 * @return the idServicio
	 */
	public Integer getIdServicio() {
		return idServicio;
	}

	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	/**
	 * @return the idRuta
	 */
	public Integer getIdRuta() {
		return idRuta;
	}

	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	/**
	 * @return the pisoBus
	 */
	public Integer getPisoBus() {
		return pisoBus;
	}

	/**
	 * @param pisoBus the pisoBus to set
	 */
	public void setPisoBus(Integer pisoBus) {
		this.pisoBus = pisoBus;
	}

	/**
	 * @return the zonaBus
	 */
	public Integer getZonaBus() {
		return zonaBus;
	}

	/**
	 * @param zonaBus the zonaBus to set
	 */
	public void setZonaBus(Integer zonaBus) {
		this.zonaBus = zonaBus;
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
