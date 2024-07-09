/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: 
 * Autor		: Jos� Abanto
 * Fecha		: 9 feb. 2024
 * Hora			: 12:52:26
 */
package pe.itsb.vyrbus.service.business;

import pe.itsb.vyrbus.model.bean.MovimientoPasajes;

/**
 * @author Marco
 *
 */
public interface MovimientoPasajesManager {

	public int guardar(MovimientoPasajes movimientoPasajes) throws Exception;
}
