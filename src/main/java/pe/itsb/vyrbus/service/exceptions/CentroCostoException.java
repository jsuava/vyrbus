/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 16/09/2013
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class CentroCostoException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int DENOMINACION_NULL = 1;
	public static final int CODIGO_NULL = 2;
	public static final int ESTADO_NULL = 3;
	public static final int CONCESIONARIO_NULL = 4;
	private Integer tipo;
	/**
	 *
	 */
	public CentroCostoException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tipo
	 */
	public CentroCostoException(Integer tipo) {
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
