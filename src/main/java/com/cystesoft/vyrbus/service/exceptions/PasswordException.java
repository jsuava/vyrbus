/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 14/05/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

/**
 * @author JABANTO
 *
 */
public class PasswordException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final int PASSWORD_INCOREC=1;
	public static final int PASSWORD_NULL=2;
	public static final int PASSWORD_CONFIRMATION_NULL=3;
	public static final int PASSWORD_DIFERENTES=4;
	public static final int PASSWORD_NUEVO_NULL=5;
	public static final int PASSWORD_ACTUAL_NULL=6;
	public static final int PASSWORD_ACTUAL_DIFERENTE=7;
	public static final int PASSWORD_IGUAL_LOGIN = 8;

	private Integer tipo;

	public PasswordException(){
		super();
	}

	public PasswordException(Integer tipo){
		super();
		this.setTipo(tipo);
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
