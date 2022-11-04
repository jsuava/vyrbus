/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 14:40:42
 */
package com.cystesoft.vyrbus.model.dao.impl;

import com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas;
import com.cystesoft.vyrbus.model.dao.HistoricoResumenVentasDAO;

/**
 * @author Marco
 *
 */
@SuppressWarnings("unchecked")
public class HistoricoResumenVentasDAOImpl extends GenericDAOImpl implements HistoricoResumenVentasDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.HistoricoResumenVentasDAO#guardar(com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas)
	 */
	@Override
	public void guardar(HistoricoResumenVentas historicoResumenVentas) {
		// TODO Auto-generated method stub
		super.save(historicoResumenVentas);
	}

}
