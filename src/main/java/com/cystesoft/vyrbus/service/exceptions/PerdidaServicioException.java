/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Avalos
 * Fecha		: 26 mar. 2022
 * Hora			: 07:41:56
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class PerdidaServicioException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tipo; 
	
	
	/**
	 * 
	 */
	public PerdidaServicioException() {
		super();
	}
	
	/**
	 * @param tipo
	 */
	public PerdidaServicioException(Integer tipo) {
		super();
		this.tipo = tipo;
	}
	
	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}
