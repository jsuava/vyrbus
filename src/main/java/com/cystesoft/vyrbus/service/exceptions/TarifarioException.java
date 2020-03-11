/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 29/05/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author JABANTO
 *
 */
public class TarifarioException extends Exception implements Serializable {
	public static final long serialVersionUID = 1L;
	public static final int NO_MONTO = 1;
	public static final int NO_SELECT_RUTAS = 2;
	public static final int NO_SELETC_TIPO_MODIFICACION_TARIFA = 3;
	
	private Integer tipo;

	public TarifarioException(){
		super();
	}
	
	public TarifarioException(Integer tipo) {
		super();
		this.tipo = tipo;
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
