package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

public class LineaContadoCliente extends GenericBean {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private CarteraCliente carteraCliente;
	private SolicitudCartera solicitudCartera;
	private Double descuentoAlta;
	private Double descuentoBaja;
	private Date fechaActivacion;
	private Date fechaCaducidad;
	private Date fechaSuspension;
	private String estadoLineaContado;
	private String esComisionable;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CarteraCliente getCarteraCliente() {
		return carteraCliente;
	}
	public void setCarteraCliente(CarteraCliente carteraCliente) {
		this.carteraCliente = carteraCliente;
	}
	public Double getDescuentoAlta() {
		return descuentoAlta;
	}
	public void setDescuentoAlta(Double descuentoAlta) {
		this.descuentoAlta = descuentoAlta;
	}
	public Double getDescuentoBaja() {
		return descuentoBaja;
	}
	public void setDescuentoBaja(Double descuentoBaja) {
		this.descuentoBaja = descuentoBaja;
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
	public Date getFechaSuspension() {
		return fechaSuspension;
	}
	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}
	public String getEstadoLineaContado() {
		return estadoLineaContado;
	}
	public void setEstadoLineaContado(String estadoLineaContado) {
		this.estadoLineaContado = estadoLineaContado;
	}
	public String getEsComisionable() {
		return esComisionable;
	}
	public void setEsComisionable(String esComisionable) {
		this.esComisionable = esComisionable;
	}
	public SolicitudCartera getSolicitudCartera() {
		return solicitudCartera;
	}
	public void setSolicitudCartera(SolicitudCartera solicitudCartera) {
		this.solicitudCartera = solicitudCartera;
	}

}
