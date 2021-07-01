/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 16:42:10
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class TipoCobranzaNullException  extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	/**
	 * 
	 */
	public TipoCobranzaNullException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @param message
	 */
	public TipoCobranzaNullException(String message) {
		super();
		this.message = message;
	}
	
}
