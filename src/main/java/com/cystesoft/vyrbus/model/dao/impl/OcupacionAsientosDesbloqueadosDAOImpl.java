/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 01/08/2013
 */
package com.cystesoft.vyrbus.model.dao.impl;

import com.cystesoft.vyrbus.model.bean.OcupacionAsientosDesbloqueados;
import com.cystesoft.vyrbus.model.dao.OcupacionAsientosDesbloqueadosDAO;

/**
 * @author JABANTO
 *
 */
public class OcupacionAsientosDesbloqueadosDAOImpl extends GenericDAOImpl implements OcupacionAsientosDesbloqueadosDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OcupacionAsientosDesbloqueadosDAO#guardar(com.tepsa.sisvyr.model.bean.OcupacionAsientosDesbloqueados)
	 */
	@Override
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados)throws Exception {
		// TODO Auto-generated method stub
		super.save(ocupacionAsientosDesbloqueados);
	}


}
