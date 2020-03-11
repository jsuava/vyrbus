/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 25/05/2013
 */
package com.cystesoft.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.HistoricoMembresia;
import com.cystesoft.vyrbus.model.dao.HistoricoMembresiaDAO;
import com.cystesoft.vyrbus.service.business.HistoricoMembresiaManager;

/**
 * @author JABANTO
 *
 */
public  class HistoricoMembresiaManagerImpl implements HistoricoMembresiaManager {
	private HistoricoMembresiaDAO historicoMembresiaDAO;
	
	/**
	 * @return the historicoMembresiaDAO
	 */
	public HistoricoMembresiaDAO getHistoricoMembresiaDAO() {
		return historicoMembresiaDAO;
	}



	/**
	 * @param historicoMembresiaDAO the historicoMembresiaDAO to set
	 */
	public void setHistoricoMembresiaDAO(HistoricoMembresiaDAO historicoMembresiaDAO) {
		this.historicoMembresiaDAO = historicoMembresiaDAO;
	}

	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.HistoricoMembresiaManager#guardar(com.tepsa.sisvyr.model.bean.HistoricoMembresia)
	 */
	@Override
	@Transactional
	public void guardar(HistoricoMembresia historicoMembresia) throws Exception {
		// TODO Auto-generated method stub
		getHistoricoMembresiaDAO().guardar(historicoMembresia);
	}



	
}
