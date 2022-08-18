package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JABANTO
 *
 */
public class SolicitudClienteCredito extends GenericBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private UsuarioAprobador usuarioAprobador;
	private SolicitudCartera solicitudCartera;
	private TipoCobranza tipoCobranza;
	private double lineaCreditoSolicitada;
	private String numeroControl;
	private double lineaCreditoAprobada;
	private double sobregiro;
	private Integer nivelAprobacion;
	private Date fechaSolicitud;
	private Date fechaAprobacion;
	private Date fechaAnulacion;
	private String estadoSolicitud;
	private String esCanje;
	private String esComisionable;
	private String esAmpliacion;
	private String observaciones;

	//NO MAPEADO
	private String estadoSolicitudAprobadoXFinanzas;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UsuarioAprobador getUsuarioAprobador() {
		return usuarioAprobador;
	}

	public void setUsuarioAprobador(UsuarioAprobador usuarioAprobador) {
		this.usuarioAprobador = usuarioAprobador;
	}
	public SolicitudCartera getSolicitudCartera() {
		return solicitudCartera;
	}

	public void setSolicitudCartera(SolicitudCartera solicitudCartera) {
		this.solicitudCartera = solicitudCartera;
	}
	public TipoCobranza getTipoCobranza() {
		return tipoCobranza;
	}

	public void setTipoCobranza(TipoCobranza tipoCobranza) {
		this.tipoCobranza = tipoCobranza;
	}
	public double getLineaCreditoSolicitada() {
		return lineaCreditoSolicitada;
	}

	public void setLineaCreditoSolicitada(double lineaCreditoSolicitada) {
		this.lineaCreditoSolicitada = lineaCreditoSolicitada;
	}
	public String getNumeroControl() {
		return numeroControl;
	}

	public void setNumeroControl(String numeroControl) {
		this.numeroControl = numeroControl;
	}
	public double getLineaCreditoAprobada() {
		return lineaCreditoAprobada;
	}

	public void setLineaCreditoAprobada(double lineaCreditoAprobada) {
		this.lineaCreditoAprobada = lineaCreditoAprobada;
	}
	public double getSobregiro() {
		return sobregiro;
	}

	public void setSobregiro(double sobregiro) {
		this.sobregiro = sobregiro;
	}
	public Integer getNivelAprobacion() {
		return nivelAprobacion;
	}

	public void setNivelAprobacion(Integer nivelAprobacion) {
		this.nivelAprobacion = nivelAprobacion;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	public String getEsCanje() {
		return esCanje;
	}

	public void setEsCanje(String esCanje) {
		this.esCanje = esCanje;
	}
	public String getEsAmpliacion() {
		return esAmpliacion;
	}

	public void setEsAmpliacion(String esAmpliacion) {
		this.esAmpliacion = esAmpliacion;
	}
	public String getEsComisionable() {
		return esComisionable;
	}

	public void setEsComisionable(String esComisionable) {
		this.esComisionable = esComisionable;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/*NO MAPEADO*/
	/**
	 * Se utiliza solamente cuando el UGA solicita reconsiderar el credito al area de finanzas
	 * @return (Acti) si la solicitud ya fue aprobada por finanzas, (null) la solicitud esta pendiente por aprobar
	 */
	public String getEstadoSolicitudAprobadoXFinanzas() {
		return estadoSolicitudAprobadoXFinanzas;
	}
	/**
	 * Se utiliza solamente cuando el UGA solicita reconsiderar el credito al area de finanzas
	 * @param estadoSolicitudAprobadoXFinanzas: (Acti) si la solicitud ya fue aprobada por finanzas, (null) la solicitud esta pendiente por aprobar
	 */
	public void setEstadoSolicitudAprobadoXFinanzas(String estadoSolicitudAprobadoXFinanzas) {
		this.estadoSolicitudAprobadoXFinanzas = estadoSolicitudAprobadoXFinanzas;
	}
}
