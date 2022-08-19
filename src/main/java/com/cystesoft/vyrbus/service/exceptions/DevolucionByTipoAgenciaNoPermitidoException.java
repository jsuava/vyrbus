/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: clase para controlar las excepcionesde devolución ocasionadas por TipoAgencia.
 * Autor		: José Sullo Avalos
 * Fecha		: 11/02/2013
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class DevolucionByTipoAgenciaNoPermitidoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tipoAgencia;

	public DevolucionByTipoAgenciaNoPermitidoException(Integer tipoAgencia) {
		super();
		this.tipoAgencia = tipoAgencia;
	}

	/**
	 * @return the tipoAgencia
	 */
	public Integer getTipoAgencia() {
		return tipoAgencia;
	}
	/**
	 * @param tipoAgencia the tipoAgencia to set
	 */
	public void setTipoAgencia(Integer tipoAgencia) {
		this.tipoAgencia = tipoAgencia;
	}
}
