/**
 * Proyecto		: MANTYBUS
 * Sistema		: Sistema de Mantenimiento de Buses
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 21/09/2011
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class SocketMessagingException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	/**
	 * @param message
	 */
	public SocketMessagingException(String message) {
		super();
		this.message = message;
	}
	public SocketMessagingException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the message
	 */
	@Override
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
