/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 11:22:47
 */
package com.cystesoft.vyrbus.model.bean;

import java.util.Date;

/**
 * @author JABANTO
 *
 */
public class DetalleFlotaHRE extends GenericBean{
	private static final long serialVersionUID = 1L;

	private Long id;
	private HRE hre;
	private String numeroDocumento;
	private String mtcTipoDocumentoID;
	private Personal personal;
	private TipoPersonal tipoPersonal;
	private Date fechaInicioConduccion;
	private String horaInicioConduccion;
	private Date fechaTerminoConcduccion;
	private String horaTerminoConduccion;



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
	 * @return the hre
	 */
	public HRE getHre() {
		return hre;
	}
	/**
	 * @param hre the hre to set
	 */
	public void setHre(HRE hre) {
		this.hre = hre;
	}
	/**
	 * @return the mTCTipoDocumentoID
	 */
	public String getMtcTipoDocumentoID() {
		return mtcTipoDocumentoID;
	}
	/**
	 * @param mTCTipoDocumentoID the mTCTipoDocumentoID to set
	 */
	public void setMtcTipoDocumentoID(String mtcTipoDocumentoID) {
		this.mtcTipoDocumentoID = mtcTipoDocumentoID;
	}
	/**
	 * @return the personal
	 */
	public Personal getPersonal() {
		return personal;
	}
	/**
	 * @param personal the personal to set
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	/**
	 * @return the tipoPersonal
	 */
	public TipoPersonal getTipoPersonal() {
		return tipoPersonal;
	}
	/**
	 * @param tipoPersonal the tipoPersonal to set
	 */
	public void setTipoPersonal(TipoPersonal tipoPersonal) {
		this.tipoPersonal = tipoPersonal;
	}
	/**
	 * @return the fechaInicioConduccion
	 */
	public Date getFechaInicioConduccion() {
		return fechaInicioConduccion;
	}
	/**
	 * @param fechaInicioConduccion the fechaInicioConduccion to set
	 */
	public void setFechaInicioConduccion(Date fechaInicioConduccion) {
		this.fechaInicioConduccion = fechaInicioConduccion;
	}
	/**
	 * @return the horaInicioConduccion
	 */
	public String getHoraInicioConduccion() {
		return horaInicioConduccion;
	}
	/**
	 * @param horaInicioConduccion the horaInicioConduccion to set
	 */
	public void setHoraInicioConduccion(String horaInicioConduccion) {
		this.horaInicioConduccion = horaInicioConduccion;
	}
	/**
	 * @return the fechaTerminoConcduccion
	 */
	public Date getFechaTerminoConcduccion() {
		return fechaTerminoConcduccion;
	}
	/**
	 * @param fechaTerminoConcduccion the fechaTerminoConcduccion to set
	 */
	public void setFechaTerminoConcduccion(Date fechaTerminoConcduccion) {
		this.fechaTerminoConcduccion = fechaTerminoConcduccion;
	}
	/**
	 * @return the horaTerminoConduccion
	 */
	public String getHoraTerminoConduccion() {
		return horaTerminoConduccion;
	}
	/**
	 * @param horaTerminoConduccion the horaTerminoConduccion to set
	 */
	public void setHoraTerminoConduccion(String horaTerminoConduccion) {
		this.horaTerminoConduccion = horaTerminoConduccion;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.personal.getApellidoPaterno()+" "+(this.personal.getApellidoMaterno()!=null?this.personal.getApellidoMaterno():"")+" "+personal.getNombre();
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

}
