/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 19/09/2012
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 *
 * @author José Avalos
 * @since JDK1.6
 */
public class PreferenciaAlimentariaException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int ALIMENTACION_IDA_NULL=1;
	public static final int ALIMENTACION_RETORNO_NULL=2;

	private Integer tipo;

	/**
	 *
	 */
	public PreferenciaAlimentariaException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param tipo
	 */
	public PreferenciaAlimentariaException(Integer tipo) {
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
