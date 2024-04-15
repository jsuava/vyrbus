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
public class MovimientoPasajes {
	
	  private Long id;
	  private Long idVentaPasaje;
	  private String operacion;
	  private String fechaOperacion;
	  private String ruta;
	  private String fechaEmbarque;
	  private String horaEmbarque;
	  private int numeroPiso;
	  private int numeroAsiento;
	  private Double importePagado;
	  private String medioPago;
	  
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
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
	public int getNumeroPiso() {
		return numeroPiso;
	}
	public void setNumeroPiso(int piso) {
		this.numeroPiso = piso;
	}
	public int getNumeroAsiento() {
		return numeroAsiento;
	}
	public void setNumeroAsiento(int asiento) {
		this.numeroAsiento = asiento;
	}
	public Double getImportePagado() {
		return importePagado;
	}
	public void setImportePagado(Double importePagado) {
		this.importePagado = importePagado;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	  
	  

}
