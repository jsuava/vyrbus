/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 06/09/2012
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
public class FechaExpedicionNullException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	final public static int FECHA_EXPIRACION_MAYOR=0;
	final public static int FECHA_EXPIRACION_MENOR=1;

	private int tipo;


	public FechaExpedicionNullException(){
		super();
	}

	public FechaExpedicionNullException(Integer tipo){
		this.setTipo(tipo);
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}


}
