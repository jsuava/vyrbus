/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: Clase para manejar los errorex ocasionados por la localidad.
 * Autor		: Jos� Avalos
 * Fecha		: 28/08/2012
 */
package pe.itsb.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 *
 * @author Jose Avalos
 * @since JDK1.6
 */
public class LocalidadNullException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int ORIGEN = 1;
	public static final int DESTINO = 2;
	private Integer origenDestino;
	/**
	 * @param esOrigen
	 */
	public LocalidadNullException(Integer origenDestino) {
		super();
		this.origenDestino = origenDestino;
	}
	/**
	 *
	 */
	public LocalidadNullException() {
		super();
	}

	/**
	 * @return Objeto esOrigen.
	 */
	public Integer getOrigenDestino() {
		return origenDestino;
	}
	/**
	 * @param esOrigen	: Setea el objeto esOrigen.
	 */
	public void setOrigenDestino(Integer origenDestino) {
		this.origenDestino = origenDestino;
	}
}
