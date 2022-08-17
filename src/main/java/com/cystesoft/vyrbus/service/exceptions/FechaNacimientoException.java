/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 20/01/2014
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class FechaNacimientoException extends Exception implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3247199297101161510L;
	public static int DIA_NO_VALID=0;
	public static int MES_NO_VALID=1;
	public static int ANIO_NO_VALID=2;

	private Integer tipo;

	public FechaNacimientoException(){

	}

	public FechaNacimientoException(Integer tipo){
		this.tipo=tipo;
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
