/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26/10/2016
 * Hora			: 15:30:14
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author jabanto
 *
 */
public class TipoNota extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String movimiento;
	private String equivalenteSunat;
	private String codigoEquivalenteSunat;
	private Integer tipoNota;
	private Double gastoAdminEfectivo;
	private Double gastoAdminTarjeta;
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
	 * @return the movimiento
	 */
	public String getMovimiento() {
		return movimiento;
	}
	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	/**
	 * @return the equivalenteSunat
	 */
	public String getEquivalenteSunat() {
		return equivalenteSunat;
	}
	/**
	 * @param equivalenteSunat the equivalenteSunat to set
	 */
	public void setEquivalenteSunat(String equivalenteSunat) {
		this.equivalenteSunat = equivalenteSunat;
	}
	/**
	 * @return the codigoEquivalenteSunat
	 */
	public String getCodigoEquivalenteSunat() {
		return codigoEquivalenteSunat;
	}
	/**
	 * @param codigoEquivalenteSunat the codigoEquivalenteSunat to set
	 */
	public void setCodigoEquivalenteSunat(String codigoEquivalenteSunat) {
		this.codigoEquivalenteSunat = codigoEquivalenteSunat;
	}
	/**
	 * @return the tipoNota
	 */
	public Integer getTipoNota() {
		return tipoNota;
	}
	/**
	 * @param tipoNota the tipoNota to set
	 */
	public void setTipoNota(Integer tipoNota) {
		this.tipoNota = tipoNota;
	}
	/**
	 * @return the gastoAdminEfectivo
	 */
	public Double getGastoAdminEfectivo() {
		return gastoAdminEfectivo;
	}
	/**
	 * @param gastoAdminEfectivo the gastoAdminEfectivo to set
	 */
	public void setGastoAdminEfectivo(Double gastoAdminEfectivo) {
		this.gastoAdminEfectivo = gastoAdminEfectivo;
	}
	/**
	 * @return the gastoAdminTarjeta
	 */
	public Double getGastoAdminTarjeta() {
		return gastoAdminTarjeta;
	}
	/**
	 * @param gastoAdminTarjeta the gastoAdminTarjeta to set
	 */
	public void setGastoAdminTarjeta(Double gastoAdminTarjeta) {
		this.gastoAdminTarjeta = gastoAdminTarjeta;
	}
	

}
