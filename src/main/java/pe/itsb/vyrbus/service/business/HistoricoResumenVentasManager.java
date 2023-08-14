/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 17:58:45
 */
package pe.itsb.vyrbus.service.business;

import pe.itsb.vyrbus.model.bean.HistoricoResumenVentas;

/**
 * @author Marco
 *
 */
public interface HistoricoResumenVentasManager {

	/**
	 *
	 * @param ventaPasajeHistorial
	 */
	public void guardar(HistoricoResumenVentas historicoResumenVentas);

}
