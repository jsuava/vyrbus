/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 31/08/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class VentaReservaException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int CONVERTIR_PASAJERO_PAXFRE = 1;
	public static final int TARIFA_IDA_CERO = 2;
	public static final int TARIFA_RETORNO_CERO = 3;
	public static final int LONGITUD_NUMERO_DOCUMENTO = 4;
	public static final int NO_SELECTION_TIPO_MONEDA = 5;

	private Integer tipo;
	/**
	 *
	 */
	public VentaReservaException() {
		super();
	}
	/**
	 * @param tipo
	 */
	public VentaReservaException(Integer tipo) {
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
