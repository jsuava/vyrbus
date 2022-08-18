/**
 * Proyecto		: MANTYBUS
 * Sistema		: Sistema de Mantenimiento de Buses
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 06/07/2011
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class AddressEmailInvalidException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	public AddressEmailInvalidException() {
		super();
	}
	public AddressEmailInvalidException(String message) {
		super(message);
		this.message=message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
