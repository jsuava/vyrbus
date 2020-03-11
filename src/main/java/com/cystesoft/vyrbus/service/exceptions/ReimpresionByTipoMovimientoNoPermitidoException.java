/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que controla las excepciones lanzadas por Reimpresiones no permitidas por el tipo de movimiento.
 * Autor		: José Sullo Avalos
 * Fecha		: 07/02/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class ReimpresionByTipoMovimientoNoPermitidoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tipoMovimiento;
	
	public ReimpresionByTipoMovimientoNoPermitidoException(Integer tipoMovimiento) {
		super();
		this.tipoMovimiento = tipoMovimiento;
	}
	
	/**
	 * @return the tipoMovimiento
	 */
	public Integer getTipoMovimiento() {
		return tipoMovimiento;
	}
	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(Integer tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
}
