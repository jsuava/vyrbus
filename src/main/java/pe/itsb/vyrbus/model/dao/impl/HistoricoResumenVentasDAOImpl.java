/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 14:40:42
 */
package pe.itsb.vyrbus.model.dao.impl;

import pe.itsb.vyrbus.model.bean.HistoricoResumenVentas;
import pe.itsb.vyrbus.model.dao.HistoricoResumenVentasDAO;

/**
 * @author Marco
 *
 */
@SuppressWarnings("unchecked")
public class HistoricoResumenVentasDAOImpl extends GenericDAOImpl implements HistoricoResumenVentasDAO{

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.HistoricoResumenVentasDAO#guardar(pe.itsb.vyrbus.model.bean.HistoricoResumenVentas)
	 */
	@Override
	public void guardar(HistoricoResumenVentas historicoResumenVentas) {
		// TODO Auto-generated method stub
		super.save(historicoResumenVentas);
	}

}
