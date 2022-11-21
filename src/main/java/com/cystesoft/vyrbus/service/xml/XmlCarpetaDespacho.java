/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:46:31
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlCarpetaDespacho implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sericio;
	private String origen;
	private String destino;
	private String fechaHoraSalida;
	private Integer totalAsientos;
	private Integer totalPasajeros;
	private Integer totalFrecuentes;
	private String asientosFrecuentes;
	private XmlDetalleCarpetaDespacho xmlDetalleCarpetaDespacho;
	/**
	 * @return the sericio
	 */
	public String getSericio() {
		return sericio;
	}
	/**
	 * @param sericio the sericio to set
	 */
	public void setSericio(String sericio) {
		this.sericio = sericio;
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the fechaHoraSalida
	 */
	public String getFechaHoraSalida() {
		return fechaHoraSalida;
	}
	/**
	 * @param fechaHoraSalida the fechaHoraSalida to set
	 */
	public void setFechaHoraSalida(String fechaHoraSalida) {
		this.fechaHoraSalida = fechaHoraSalida;
	}
	/**
	 * @return the totalAsientos
	 */
	public Integer getTotalAsientos() {
		return totalAsientos;
	}
	/**
	 * @param totalAsientos the totalAsientos to set
	 */
	public void setTotalAsientos(Integer totalAsientos) {
		this.totalAsientos = totalAsientos;
	}
	/**
	 * @return the totalPasajeros
	 */
	public Integer getTotalPasajeros() {
		return totalPasajeros;
	}
	/**
	 * @param totalPasajeros the totalPasajeros to set
	 */
	public void setTotalPasajeros(Integer totalPasajeros) {
		this.totalPasajeros = totalPasajeros;
	}
	/**
	 * @return the totalFrecuentes
	 */
	public Integer getTotalFrecuentes() {
		return totalFrecuentes;
	}
	/**
	 * @param totalFrecuentes the totalFrecuentes to set
	 */
	public void setTotalFrecuentes(Integer totalFrecuentes) {
		this.totalFrecuentes = totalFrecuentes;
	}
	/**
	 * @return the asientosFrecuentes
	 */
	public String getAsientosFrecuentes() {
		return asientosFrecuentes;
	}
	/**
	 * @param asientosFrecuentes the asientosFrecuentes to set
	 */
	public void setAsientosFrecuentes(String asientosFrecuentes) {
		this.asientosFrecuentes = asientosFrecuentes;
	}
	/**
	 * @return the xmlDetalleCarpetaDespacho
	 */
	public XmlDetalleCarpetaDespacho getXmlDetalleCarpetaDespacho() {
		return xmlDetalleCarpetaDespacho;
	}
	/**
	 * @param xmlDetalleCarpetaDespacho the xmlDetalleCarpetaDespacho to set
	 */
	public void setXmlDetalleCarpetaDespacho(XmlDetalleCarpetaDespacho xmlDetalleCarpetaDespacho) {
		this.xmlDetalleCarpetaDespacho = xmlDetalleCarpetaDespacho;
	}
}
