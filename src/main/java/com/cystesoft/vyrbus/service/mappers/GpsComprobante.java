/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 7 mar. 2024
 * Hora			: 16:58:50
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Marco
 *
 */
public class GpsComprobante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idVentaPasaje;
	private Date fechaEmision;
	private String tipoPago;
	private String numComprobante;
	private Double importe;
	private String servicio;
	private String ruta;
	private String agenciaEmbarque;
	private String fechaViaje;
	private String horaViaje;
	private Integer piso;
	private Integer asiento;
	private String pasajero;
	private String docIdentidad;
	private Date fechaOperacion;
	private String usuario;
	private String estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdVentaPasaje() {
		return idVentaPasaje;
	}
	public void setIdVentaPasaje(Long idVentaPasaje) {
		this.idVentaPasaje = idVentaPasaje;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getNumComprobante() {
		return numComprobante;
	}
	public void setNumComprobante(String numComprobante) {
		this.numComprobante = numComprobante;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getAgenciaEmbarque() {
		return agenciaEmbarque;
	}
	public void setAgenciaEmbarque(String agenciaEmbarque) {
		this.agenciaEmbarque = agenciaEmbarque;
	}
	public String getFechaViaje() {
		return fechaViaje;
	}
	public void setFechaViaje(String fechaViaje) {
		this.fechaViaje = fechaViaje;
	}
	public String getHoraViaje() {
		return horaViaje;
	}
	public void setHoraViaje(String horaViaje) {
		this.horaViaje = horaViaje;
	}
	public Integer getPiso() {
		return piso;
	}
	public void setPiso(Integer piso) {
		this.piso = piso;
	}
	public Integer getAsiento() {
		return asiento;
	}
	public void setAsiento(Integer asiento) {
		this.asiento = asiento;
	}
	public String getPasajero() {
		return pasajero;
	}
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}
	public String getDocIdentidad() {
		return docIdentidad;
	}
	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
	}
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
