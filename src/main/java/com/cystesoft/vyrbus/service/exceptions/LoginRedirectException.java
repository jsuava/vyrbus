/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 19 mar. 2023
 * Hora			: 18:49:37
 */
package com.cystesoft.vyrbus.service.exceptions;

/**
 * @author Jose
 *
 */
public class LoginRedirectException extends Exception {
	private static final long serialVersionUID = 1L;
	private String mensaje;
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @param mensaje
	 */
	public LoginRedirectException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}	
}
