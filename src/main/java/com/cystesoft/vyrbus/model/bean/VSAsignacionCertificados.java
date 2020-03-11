/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Avalos Sullo
 * Fecha		: 28/06/2014
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class VSAsignacionCertificados extends GenericBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer agenciaID;
	private Long correlativoInicial;
	private Long correlativoFinal;
	
	private Agencia agencia; //No mapeado
	
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the agenciaID
	 */
	public Integer getAgenciaID() {
		return agenciaID;
	}
	/**
	 * @param agenciaID the agenciaID to set
	 */
	public void setAgenciaID(Integer agenciaID) {
		this.agenciaID = agenciaID;
	}
	/**
	 * @return the correlativoFinal
	 */
	public Long getCorrelativoFinal() {
		return correlativoFinal;
	}
	/**
	 * @param correlativoFinal the correlativoFinal to set
	 */
	public void setCorrelativoFinal(Long correlativoFinal) {
		this.correlativoFinal = correlativoFinal;
	}
	/**
	 * @return the correlativoInicial
	 */
	public Long getCorrelativoInicial() {
		return correlativoInicial;
	}
	/**
	 * @param correlativoInicial the correlativoInicial to set
	 */
	public void setCorrelativoInicial(Long correlativoInicial) {
		this.correlativoInicial = correlativoInicial;
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
	
}
