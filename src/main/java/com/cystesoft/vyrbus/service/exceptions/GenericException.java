/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 11/04/2015
 * Hora			: 10:48:08
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author jabanto
 *
 */
public class GenericException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object objectFocus;

	public GenericException(){
		super();
	}
	
	public GenericException(String message){
		super(message);
	}
	
	public GenericException(String message,Object objectFocus){
		super(message);
		this.setObjectFocus(objectFocus);
	}

	/**
	 * @return the objectFocus
	 */
	public Object getObjectFocus() {
		return objectFocus;
	}

	/**
	 * @param objectFocus the objectFocus to set
	 */
	public void setObjectFocus(Object objectFocus) {
		this.objectFocus = objectFocus;
	}
	
}
