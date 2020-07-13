/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 12 jul. 2020
 * Hora			: 23:41:08
 */
package com.cystesoft.vyrbus.model.dao.impl;

import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.dao.TarifaDAO;

/**
 * @author Marco
 *
 */
public class TarifaDAOImpl extends GenericDAOImpl implements TarifaDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#guardar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	public void guardar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		super.save(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#actualizar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	public void actualizar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		super.update(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.TarifaDAO#inactivate(java.lang.Long)
	 */
	@Override
	public void inactivate(Long id) throws Exception {
		// TODO Auto-generated method stub
		super.inactivate(Tarifa.class, id);
	}
	

}
