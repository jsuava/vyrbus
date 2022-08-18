/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/09/2013
 */
package com.cystesoft.vyrbus.service.business.impl;

import com.cystesoft.vyrbus.model.bean.HistoricoControlEspecieValorada;
import com.cystesoft.vyrbus.model.dao.HistoricoControlEspecieValoradaDAO;
import com.cystesoft.vyrbus.service.business.HistoricoControlEspecieValoradaManager;

/**
 * @author JABANTO
 *
 */
public class HistoricoControlEspecieValoradaManagerImpl implements HistoricoControlEspecieValoradaManager {
	private HistoricoControlEspecieValoradaDAO historicoControlEspecieValoradaDAO;

	/**
	 * @return the controlEspecieValoradaDAO
	 */
	public HistoricoControlEspecieValoradaDAO getHistoricoControlEspecieValoradaDAO() {
		return historicoControlEspecieValoradaDAO;
	}

	/**
	 * @param controlEspecieValoradaDAO the controlEspecieValoradaDAO to set
	 */
	public void setHistoricoControlEspecieValoradaDAO(HistoricoControlEspecieValoradaDAO historicoControlEspecieValoradaDAO) {
		this.historicoControlEspecieValoradaDAO = historicoControlEspecieValoradaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.HistoricoControlEspecieValoradaManager#guardar(com.tepsa.sisvyr.model.dao.HistoricoControlEspecieValoradaDAO)
	 */
	@Override
	public void guardar(HistoricoControlEspecieValorada historicoControlEspecieValorada)throws Exception {
		// TODO Auto-generated method stub
		getHistoricoControlEspecieValoradaDAO().guardar(historicoControlEspecieValorada);
	}


}
