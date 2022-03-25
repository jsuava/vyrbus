/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 dic. 2021
 * Hora			: 13:47:28
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class ControlAccesoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int EMPTY_CODIGO=1;
	public static final int EXPIRED_CODIGO=2;
	
	private Integer tipo;

	/**
	 * @param tipo
	 */
	public ControlAccesoException(Integer tipo) {
		super();
		this.tipo = tipo;
	}

	/**
	 * 
	 */
	public ControlAccesoException() {
		super();
		// TODO Auto-generated constructor stub
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
