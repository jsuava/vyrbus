package com.cystesoft.vyrbus.view.applet;

import java.io.Serializable;

public class Boleto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String razonSocial;
	private String ruc;
	private String pasajero;
	private String fechaNacimiento;
	private String tipoDocumento;
	private String numeroDocumento;
	private String origen;
	private String destino;
	private String fechaPartida;
	private String horaPartida;
	private String numeroAsiento;
	private String numeroPiso;
	private String numeroControl;
	private String importePagado;
	private String fechaCaducidad;
	private String fechaEmision;
	
	
	
	public Boleto(String razonSocial, String ruc, String pasajero,
			String fechaNacimiento, String tipoDocumento,
			String numeroDocumento, String origen, String destino,
			String fechaPartida, String horaPartida, String numeroAsiento,
			String numeroPiso, String numeroControl, String importePagado,
			String fechaCaducidad, String fechaEmision) {
		super();
		this.razonSocial = razonSocial;
		this.ruc = ruc;
		this.pasajero = pasajero;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.origen = origen;
		this.destino = destino;
		this.fechaPartida = fechaPartida;
		this.horaPartida = horaPartida;
		this.numeroAsiento = numeroAsiento;
		this.numeroPiso = numeroPiso;
		this.numeroControl = numeroControl;
		this.importePagado = importePagado;
		this.fechaCaducidad = fechaCaducidad;
		this.fechaEmision = fechaEmision;
	}
	
	public Boleto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getPasajero() {
		return pasajero;
	}
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public String getFechaPartida() {
		return fechaPartida;
	}
	public void setFechaPartida(String fechaPartida) {
		this.fechaPartida = fechaPartida;
	}
	
	public String getHoraPartida() {
		return horaPartida;
	}
	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}
	
	public String getNumeroAsiento() {
		return numeroAsiento;
	}
	public void setNumeroAsiento(String numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}
	
	public String getNumeroPiso() {
		return numeroPiso;
	}
	public void setNumeroPiso(String numeroPiso) {
		this.numeroPiso = numeroPiso;
	}
	
	public String getNumeroControl() {
		return numeroControl;
	}
	public void setNumeroControl(String numeroControl) {
		this.numeroControl = numeroControl;
	}
	
	public String getImportePagado() {
		return importePagado;
	}
	public void setImportePagado(String importePagado) {
		this.importePagado = importePagado;
	}
	
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
}
