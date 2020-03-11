/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 25/05/2013
 */
package com.cystesoft.vyrbus.model.dao.impl;

import com.cystesoft.vyrbus.model.bean.HistoricoMembresia;
import com.cystesoft.vyrbus.model.dao.HistoricoMembresiaDAO;

/**
 * @author JABANTO
 *
 */
public class HistoricoMembresiaDAOImpl extends GenericDAOImpl implements HistoricoMembresiaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HistoricoMembresiaDAO#guardar(com.tepsa.sisvyr.model.bean.HistoricoMembresia)
	 */
	@Override
	public void guardar(HistoricoMembresia historicoMembresia) throws Exception {
		// TODO Auto-generated method stub
		super.save(historicoMembresia);
	}

}
