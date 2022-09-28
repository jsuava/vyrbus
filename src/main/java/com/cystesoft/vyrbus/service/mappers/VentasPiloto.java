/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 1 ago. 2021
 * Hora			: 19:09:47
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;
import java.util.Date;

import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;

/**
 * @author Jose
 *
 */
public class VentasPiloto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date fechaCompra;
	private String numeroBoleto;
	private String ruc;
	private String dni;
	private String nombres;
	private Double exonerado;
	private Double venta;
	private Double igv;
	private Double total;
	private Date fechaSalidaBus;
	private String horaVenta;
	private String origen;
	private String destino;
	private String asiento;
	private String tipo;
	private String horaSalidaBus;
	private String codigo;
	private String piloto;
	private String copiloto;
	private String tripulante;
	
	//Se reutiliza este objeto para el registro de ventas y se agregan estos campos
	private String tipoDocumentoSunat;
	private String serie;
	private String numero;
	private TipoComprobante tipoComprobante;
	private TipoMovimiento tipoMovimiento;
	
	
	public String getTipoDocumentoSunat() {
		return tipoDocumentoSunat;
	}
	public void setTipoDocumentoSunat(String tipoDocumentoSunat) {
		this.tipoDocumentoSunat = tipoDocumentoSunat;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	/**
	 * @return the fechaCompra
	 */
	public Date getFechaCompra() {
		return fechaCompra;
	}
	/**
	 * @param fechaCompra the fechaCompra to set
	 */
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	/**
	 * @return the numeroBoleto
	 */
	public String getNumeroBoleto() {
		return numeroBoleto;
	}
	/**
	 * @param numeroBoleto the numeroBoleto to set
	 */
	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}
	/**
	 * @return the ruc
	 */
	public String getRuc() {
		return ruc;
	}
	/**
	 * @param ruc the ruc to set
	 */
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}
	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the exonerado
	 */
	public Double getExonerado() {
		return exonerado;
	}
	/**
	 * @param exonerado the exonerado to set
	 */
	public void setExonerado(Double exonerado) {
		this.exonerado = exonerado;
	}
	/**
	 * @return the venta
	 */
	public Double getVenta() {
		return venta;
	}
	/**
	 * @param venta the venta to set
	 */
	public void setVenta(Double venta) {
		this.venta = venta;
	}
	/**
	 * @return the igv
	 */
	public Double getIgv() {
		return igv;
	}
	/**
	 * @param igv the igv to set
	 */
	public void setIgv(Double igv) {
		this.igv = igv;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the fechaSalidaBus
	 */
	public Date getFechaSalidaBus() {
		return fechaSalidaBus;
	}
	/**
	 * @param fechaSalidaBus the fechaSalidaBus to set
	 */
	public void setFechaSalidaBus(Date fechaSalidaBus) {
		this.fechaSalidaBus = fechaSalidaBus;
	}
	/**
	 * @return the horaVenta
	 */
	public String getHoraVenta() {
		return horaVenta;
	}
	/**
	 * @param horaVenta the horaVenta to set
	 */
	public void setHoraVenta(String horaVenta) {
		this.horaVenta = horaVenta;
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
	 * @return the asiento
	 */
	public String getAsiento() {
		return asiento;
	}
	/**
	 * @param asiento the asiento to set
	 */
	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the horaSalidaBus
	 */
	public String getHoraSalidaBus() {
		return horaSalidaBus;
	}
	/**
	 * @param horaSalidaBus the horaSalidaBus to set
	 */
	public void setHoraSalidaBus(String horaSalidaBus) {
		this.horaSalidaBus = horaSalidaBus;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
}
