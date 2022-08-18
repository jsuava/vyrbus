/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 21:26:32
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jose
 *
 */
public class VentaServicioEspecial extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private TipoComprobante tipoComprobante;
	private String numeroComprobante;
	private Date fechaEmision;
	private Cliente cliente;
	private Pasajero pasajero;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String nombreCliente;
	private String direccion;
	private FormaPago formaPago;
	private TipoCobranza tipoCobranza;
	private Double importe;
	private Double igv;
	private Double total;
	private String glosa;
	private TipoNota tipoNota;
	private Integer enviadoSFE;
	private Date fechaEnvioSFE;
	private Agencia agencia;
	private UsuarioHardware usuarioHardware;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the tipoComprobante
	 */
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	/**
	 * @param tipoComprobante the tipoComprobante to set
	 */
	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	/**
	 * @return the numeroComprobante
	 */
	public String getNumeroComprobante() {
		return numeroComprobante;
	}
	/**
	 * @param numeroComprobante the numeroComprobante to set
	 */
	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}
	/**
	 * @return the fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the pasajero
	 */
	public Pasajero getPasajero() {
		return pasajero;
	}
	/**
	 * @param pasajero the pasajero to set
	 */
	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	/**
	 * @return the tipoDocumento
	 */
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	/**
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the formaPago
	 */
	public FormaPago getFormaPago() {
		return formaPago;
	}
	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	/**
	 * @return the tipoCobranza
	 */
	public TipoCobranza getTipoCobranza() {
		return tipoCobranza;
	}
	/**
	 * @param tipoCobranza the tipoCobranza to set
	 */
	public void setTipoCobranza(TipoCobranza tipoCobranza) {
		this.tipoCobranza = tipoCobranza;
	}
	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
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
	 * @return the glosa
	 */
	public String getGlosa() {
		return glosa;
	}
	/**
	 * @param glosa the glosa to set
	 */
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	/**
	 * @return the tipoNota
	 */
	public TipoNota getTipoNota() {
		return tipoNota;
	}
	/**
	 * @param tipoNota the tipoNota to set
	 */
	public void setTipoNota(TipoNota tipoNota) {
		this.tipoNota = tipoNota;
	}
	/**
	 * @return the enviadoSFE
	 */
	public Integer getEnviadoSFE() {
		return enviadoSFE;
	}
	/**
	 * @param enviadoSFE the enviadoSFE to set
	 */
	public void setEnviadoSFE(Integer enviadoSFE) {
		this.enviadoSFE = enviadoSFE;
	}
	/**
	 * @return the fechaEnvioSFE
	 */
	public Date getFechaEnvioSFE() {
		return fechaEnvioSFE;
	}
	/**
	 * @param fechaEnvioSFE the fechaEnvioSFE to set
	 */
	public void setFechaEnvioSFE(Date fechaEnvioSFE) {
		this.fechaEnvioSFE = fechaEnvioSFE;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the usuarioHardware
	 */
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}
}
