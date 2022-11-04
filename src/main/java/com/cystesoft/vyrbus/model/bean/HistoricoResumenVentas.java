/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 14:27:56
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Marco
 *
 */
public class HistoricoResumenVentas extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer rubro;
	private Integer idCanalVenta;
	private String nombreCanal;
	private Integer idAgencia;
	private String nombreAgencia;
	private Integer idTipoComprobante;
	private String comprobante;
	private Integer cantidad;
	private Double total;
	private Date fechaVenta;
	private String anio;
	private String mes;
	private String dia;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRubro() {
		return rubro;
	}
	public void setRubro(Integer rubro) {
		this.rubro = rubro;
	}
	public Integer getIdCanalVenta() {
		return idCanalVenta;
	}
	public void setIdCanalVenta(Integer idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}
	public String getNombreCanal() {
		return nombreCanal;
	}
	public void setNombreCanal(String nombreCanal) {
		this.nombreCanal = nombreCanal;
	}
	public Integer getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}
	public String getNombreAgencia() {
		return nombreAgencia;
	}
	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}
	public Integer getIdTipoComprobante() {
		return idTipoComprobante;
	}
	public void setIdTipoComprobante(Integer idTipoComprobante) {
		this.idTipoComprobante = idTipoComprobante;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	
	

}
