/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Para las excepciones de cambio de capacidad del servicio.
 * Autor		: José Sullo Avalos
 * Fecha		: 09/10/2012
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class CapacityExceedsException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	/**
	 * 
	 */
	public CapacityExceedsException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param message
	 */
	public CapacityExceedsException(String message) {
		super();
		this.message = message;
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
}
