/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 28/01/2013
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class PostergacionByTipoMovimientoNoPermitidoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tipoMovimiento;

	public PostergacionByTipoMovimientoNoPermitidoException(Integer tipoMovimiento) {
		super();
		this.setTipoMovimiento(tipoMovimiento);
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
