/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 feb. 2024
 * Hora			: 12:52:26
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.MovimientoPasajes;

/**
 * @author Marco
 *
 */
public interface MovimientoPasajesManager {

	public int guardar(MovimientoPasajes movimientoPasajes) throws Exception;
}
