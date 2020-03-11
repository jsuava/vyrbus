/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Excepcion para la duplicidad de asientos.
 * Autor		: José Sullo Avalos
 * Fecha		: 09/10/2012
 */
package com.cystesoft.vyrbus.service.exceptions;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class DuplicateSeatException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
}
