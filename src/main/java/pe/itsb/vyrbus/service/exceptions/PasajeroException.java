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
public class PasajeroException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int NUMERO_DOCUMENTO_NULL = 1;
	public static final int NOMBRE_NULL = 2;
	public static final int APELLIDO_PATERNO_NULL = 3;
	public static final int EDAD_NULL = 4;
	public static final int NUMERO_DOCUMENTO_INCORRECTO = 5;

	private Integer tipo;

	/**
	 *
	 */
	public PasajeroException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param tipo
	 */
	public PasajeroException(Integer tipo) {
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
