package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author JABANTO
 *
 */
public class CarteraCliente extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Usuario usuario;
	private Cliente cliente;
	private SolicitudCartera solicitudCartera;
	private double baseHistorica;
	private Date fechaAsignacion;
	private Date fechaSuspension; 
	private String estadoCartera;
	
	private LineaCreditoCliente lineaCreditoCliente; //No mapeado
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public SolicitudCartera getSolicitudCartera() {
		return solicitudCartera;
	}
	public void setSolicitudCartera(SolicitudCartera solicitudCartera) {
		this.solicitudCartera = solicitudCartera;
	}
	public double getBaseHistorica() {
		return baseHistorica;
	}
	public void setBaseHistorica(double baseHistorica) {
		this.baseHistorica = baseHistorica;
	}
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public Date getFechaSuspension() {
		return fechaSuspension;
	}
	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}
	public String getEstadoCartera() {
		return estadoCartera;
	}
	public void setEstadoCartera(String estadoCartera) {
		this.estadoCartera = estadoCartera;
	}
	/**
	 * @return the lineaCreditoCliente
	 */
	public LineaCreditoCliente getLineaCreditoCliente() {
		return lineaCreditoCliente;
	}
	/**
	 * @param lineaCreditoCliente the lineaCreditoCliente to set
	 */
	public void setLineaCreditoCliente(LineaCreditoCliente lineaCreditoCliente) {
		this.lineaCreditoCliente = lineaCreditoCliente;
	}
	
	

}
