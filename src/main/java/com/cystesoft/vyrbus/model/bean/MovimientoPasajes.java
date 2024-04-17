/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 feb. 2024
 * Hora			: 11:04:49
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author Marco
 *
 */
public class MovimientoPasajes extends GenericBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	  private VentaPasaje ventaPasaje;
	  private Servicio servicio;
	  private Ruta ruta;
	  private TipoFormaPago tipoFormaPago;
	  private Agencia agenciaEmbarque;
	  private String operacion;
	  private String fechaOperacion;
	  private String fechaEmbarque;
	  private String horaEmbarque;
	  private Integer numeroPiso;
	  private Integer numeroAsiento;
	  private Double importePagado;
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public VentaPasaje getVentaPasaje() {
		return ventaPasaje;
	}
	public void setVentaPasaje(VentaPasaje ventaPasaje) {
		this.ventaPasaje = ventaPasaje;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public Ruta getRuta() {
		return ruta;
	}
	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}
	public String getFechaEmbarque() {
		return fechaEmbarque;
	}
	public void setFechaEmbarque(String fechaEmbarque) {
		this.fechaEmbarque = fechaEmbarque;
	}
	public String getHoraEmbarque() {
		return horaEmbarque;
	}
	public void setHoraEmbarque(String horaEmbarque) {
		this.horaEmbarque = horaEmbarque;
	}
	public Integer getNumeroPiso() {
		return numeroPiso;
	}
	public void setNumeroPiso(Integer piso) {
		this.numeroPiso = piso;
	}
	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}
	public void setNumeroAsiento(Integer asiento) {
		this.numeroAsiento = asiento;
	}
	public Double getImportePagado() {
		return importePagado;
	}
	public void setImportePagado(Double importePagado) {
		this.importePagado = importePagado;
	}
	public TipoFormaPago getTipoFormaPago() {
		return tipoFormaPago;
	}
	public void setTipoFormaPago(TipoFormaPago medioPago) {
		this.tipoFormaPago = medioPago;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Agencia getAgenciaEmbarque() {
		return agenciaEmbarque;
	}
	public void setAgenciaEmbarque(Agencia agenciaEmbarque) {
		this.agenciaEmbarque = agenciaEmbarque;
	}
	  
	

}
