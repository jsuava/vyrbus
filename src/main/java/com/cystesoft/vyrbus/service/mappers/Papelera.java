/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase para manejar las imagenes enviadas a la papelera.
 * Autor		: José Sullo Avalos
 * Fecha		: 23/10/2012
 */
package com.cystesoft.vyrbus.service.mappers;

import java.io.Serializable;

/**
 * @author Jose
 *
 */
public class Papelera extends ElementoBus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public Papelera() {
		super("resources/mapa/bus_papelera.png");
	}

	/**
	 * @param src
	 */
	public Papelera(String src) {
		super(src);
	}
}
