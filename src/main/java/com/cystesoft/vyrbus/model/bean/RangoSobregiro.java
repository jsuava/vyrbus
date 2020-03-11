/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/12/2014
 * Hora			: 09:56:25
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class RangoSobregiro extends GenericBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double lineaCreditoMinima;
	private Double lineaCreditoMaxima;
	private Double porcentajeSobregiroSugerida;
	private Double porcentajeSobregiroMinimo;
	private Double porcentajeSobregiroMaximo;
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
	 * @return the lineaCreditoMinima
	 */
	public Double getLineaCreditoMinima() {
		return lineaCreditoMinima;
	}
	/**
	 * @param lineaCreditoMinima the lineaCreditoMinima to set
	 */
	public void setLineaCreditoMinima(Double lineaCreditoMinima) {
		this.lineaCreditoMinima = lineaCreditoMinima;
	}
	/**
	 * @return the lineaCreditoMaxima
	 */
	public Double getLineaCreditoMaxima() {
		return lineaCreditoMaxima;
	}
	/**
	 * @param lineaCreditoMaxima the lineaCreditoMaxima to set
	 */
	public void setLineaCreditoMaxima(Double lineaCreditoMaxima) {
		this.lineaCreditoMaxima = lineaCreditoMaxima;
	}
	/**
	 * @return the porcentajeSobregiroSugerida
	 */
	public Double getPorcentajeSobregiroSugerida() {
		return porcentajeSobregiroSugerida;
	}
	/**
	 * @param porcentajeSobregiroSugerida the porcentajeSobregiroSugerida to set
	 */
	public void setPorcentajeSobregiroSugerida(
			Double porcentajeSobregiroSugerida) {
		this.porcentajeSobregiroSugerida = porcentajeSobregiroSugerida;
	}
	/**
	 * @return the porcentajeSobregiroMinimo
	 */
	public Double getPorcentajeSobregiroMinimo() {
		return porcentajeSobregiroMinimo;
	}
	/**
	 * @param porcentajeSobregiroMinimo the porcentajeSobregiroMinimo to set
	 */
	public void setPorcentajeSobregiroMinimo(Double porcentajeSobregiroMinimo) {
		this.porcentajeSobregiroMinimo = porcentajeSobregiroMinimo;
	}
	/**
	 * @return the porcentajeSobregiroMaximo
	 */
	public Double getPorcentajeSobregiroMaximo() {
		return porcentajeSobregiroMaximo;
	}
	/**
	 * @param porcentajeSobregiroMaximo the porcentajeSobregiroMaximo to set
	 */
	public void setPorcentajeSobregiroMaximo(Double porcentajeSobregiroMaximo) {
		this.porcentajeSobregiroMaximo = porcentajeSobregiroMaximo;
	}
	
	
		
	public boolean esEditableSobregiro(){
		if(this.porcentajeSobregiroSugerida.doubleValue()>=this.porcentajeSobregiroMaximo)
			return false;
		else
			return true;
	}

}
