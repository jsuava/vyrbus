/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 03:00:05
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlItemDetalleHRE implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String piloto;
	private String licencia;
	private String horaInicio;
	private String horaFin;
	private String turno;
	/**
	 * @return the piloto
	 */
	public String getPiloto() {
		return piloto;
	}
	/**
	 * @param piloto the piloto to set
	 */
	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}
	/**
	 * @return the licencia
	 */
	public String getLicencia() {
		return licencia;
	}
	/**
	 * @param licencia the licencia to set
	 */
	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}
	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}
	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}
	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	/**
	 * @return the turno
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * @param turno the turno to set
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}
}
