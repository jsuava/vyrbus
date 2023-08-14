/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 14:37:10
 */
package pe.itsb.vyrbus.model.dao;

import pe.itsb.vyrbus.model.bean.HistoricoResumenVentas;

/**
 * @author Marco
 *
 */
public interface HistoricoResumenVentasDAO extends GenericDAO {

	public void guardar(HistoricoResumenVentas historicoResumenVentas);

}
