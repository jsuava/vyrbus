/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Objeto que controla la excepcion de las direcciones de correo.
 * Autor		: José Avalos
 * Fecha		: 01/04/2013
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class EmailNullException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tipoEmail;
	public static final int EMAIL_PERSONAL = 0;
	public static final int EMAIL_ENVIO_INFO = 1;
	public static final int EMAIL_INVALID=2;

	/**
	 *
	 */
	public EmailNullException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param tipoEmail
	 */
	public EmailNullException(Integer tipoEmail) {
		super();
		this.tipoEmail = tipoEmail;
	}
	/**
	 * @return the tipoEmail
	 */
	public Integer getTipoEmail() {
		return tipoEmail;
	}
	/**
	 * @param tipoEmail the tipoEmail to set
	 */
	public void setTipoEmail(Integer tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

}
