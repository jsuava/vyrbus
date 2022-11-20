/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 nov. 2022
 * Hora			: 02:50:35
 */
package com.cystesoft.vyrbus.service.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * @author abant
 *
 */
@XmlType
public class XmlManifiesto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numeroManifiesto;
	private String usuario;
	private String agencia;
	private String itinerario;
	private String servicio;
	private String bus;
	private String origen;
	private String destino;
	private String Placa;
	private String tarjetaHabilitacion;	
	private String piloto;
	private String pilotoLicencia;
	private String copiloto;
	private String copilotoLicencia;
	private String copilotoAuxiliar;
	private String copilotoAuxiliarLicencia;
	private String tripulante;
	private String tripulanteDni;
	private String salida;
	private Integer totalAsientos;
	private Integer totalPasajeros;
	private String autorizacionSunat;
	private String rotulo;
	private XmlDetalleManifiesto detalleManifiesto;
//	private XmlConfigPrint configPrint;
//	private String z_rptManifiesto;
	/**
	 * @return the numeroManifiesto
	 */
	public String getNumeroManifiesto() {
		return numeroManifiesto;
	}
	/**
	 * @param numeroManifiesto the numeroManifiesto to set
	 */
	public void setNumeroManifiesto(String numeroManifiesto) {
		this.numeroManifiesto = numeroManifiesto;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the agencia
	 */
	public String getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the itinerario
	 */
	public String getItinerario() {
		return itinerario;
	}
	/**
	 * @param itinerario the itinerario to set
	 */
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
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
	 * @return the bus
	 */
	public String getBus() {
		return bus;
	}
	/**
	 * @param bus the bus to set
	 */
	public void setBus(String bus) {
		this.bus = bus;
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
	 * @return the placa
	 */
	public String getPlaca() {
		return Placa;
	}
	/**
	 * @param placa the placa to set
	 */
	public void setPlaca(String placa) {
		Placa = placa;
	}
	/**
	 * @return the tarjetaHabilitacion
	 */
	public String getTarjetaHabilitacion() {
		return tarjetaHabilitacion;
	}
	/**
	 * @param tarjetaHabilitacion the tarjetaHabilitacion to set
	 */
	public void setTarjetaHabilitacion(String tarjetaHabilitacion) {
		this.tarjetaHabilitacion = tarjetaHabilitacion;
	}	
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
	 * @return the pilotoLicencia
	 */
	public String getPilotoLicencia() {
		return pilotoLicencia;
	}
	/**
	 * @param pilotoLicencia the pilotoLicencia to set
	 */
	public void setPilotoLicencia(String pilotoLicencia) {
		this.pilotoLicencia = pilotoLicencia;
	}
	/**
	 * @return the copiloto
	 */
	public String getCopiloto() {
		return copiloto;
	}
	/**
	 * @param copiloto the copiloto to set
	 */
	public void setCopiloto(String copiloto) {
		this.copiloto = copiloto;
	}
	/**
	 * @return the copilotoLicencia
	 */
	public String getCopilotoLicencia() {
		return copilotoLicencia;
	}
	/**
	 * @param copilotoLicencia the copilotoLicencia to set
	 */
	public void setCopilotoLicencia(String copilotoLicencia) {
		this.copilotoLicencia = copilotoLicencia;
	}
	/**
	 * @return the copilotoAuxiliar
	 */
	public String getCopilotoAuxiliar() {
		return copilotoAuxiliar;
	}
	/**
	 * @param copilotoAuxiliar the copilotoAuxiliar to set
	 */
	public void setCopilotoAuxiliar(String copilotoAuxiliar) {
		this.copilotoAuxiliar = copilotoAuxiliar;
	}
	/**
	 * @return the copilotoAuxiliarLicencia
	 */
	public String getCopilotoAuxiliarLicencia() {
		return copilotoAuxiliarLicencia;
	}
	/**
	 * @param copilotoAuxiliarLicencia the copilotoAuxiliarLicencia to set
	 */
	public void setCopilotoAuxiliarLicencia(String copilotoAuxiliarLicencia) {
		this.copilotoAuxiliarLicencia = copilotoAuxiliarLicencia;
	}
	/**
	 * @return the tripulante
	 */
	public String getTripulante() {
		return tripulante;
	}
	/**
	 * @param tripulante the tripulante to set
	 */
	public void setTripulante(String tripulante) {
		this.tripulante = tripulante;
	}
	/**
	 * @return the tripulanteDni
	 */
	public String getTripulanteDni() {
		return tripulanteDni;
	}
	/**
	 * @param tripulanteDni the tripulanteDni to set
	 */
	public void setTripulanteDni(String tripulanteDni) {
		this.tripulanteDni = tripulanteDni;
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
	 * @return the autorizacionSunat
	 */
	public String getAutorizacionSunat() {
		return autorizacionSunat;
	}
	/**
	 * @param autorizacionSunat the autorizacionSunat to set
	 */
	public void setAutorizacionSunat(String autorizacionSunat) {
		this.autorizacionSunat = autorizacionSunat;
	}
	
	
	/**
	 * @return the detalleManifiesto
	 */
	public XmlDetalleManifiesto getDetalleManifiesto() {
		return detalleManifiesto;
	}
	/**
	 * @param detalleManifiesto the detalleManifiesto to set
	 */
	public void setDetalleManifiesto(XmlDetalleManifiesto detalleManifiesto) {
		this.detalleManifiesto = detalleManifiesto;
	}
	/**
	 * @return the salida
	 */
	public String getSalida() {
		return salida;
	}
	/**
	 * @param salida the salida to set
	 */
	public void setSalida(String salida) {
		this.salida = salida;
	}
	/**
	 * @return the rotulo
	 */
	public String getRotulo() {
		return rotulo;
	}
	/**
	 * @param rotulo the rotulo to set
	 */
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

}
