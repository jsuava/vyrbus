/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 18:00:37
 */
package com.cystesoft.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas;
import com.cystesoft.vyrbus.model.dao.HistoricoResumenVentasDAO;
import com.cystesoft.vyrbus.service.business.HistoricoResumenVentasManager;

/**
 * @author Marco
 *
 */
public class HistoricoResumenVentasManagerImpl implements HistoricoResumenVentasManager{

	private HistoricoResumenVentasDAO historicoResumenVentasDAO;

	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.HistoricoResumenVentasManager#guardar(com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas)
	 */
	public HistoricoResumenVentasDAO getHistoricoResumenVentasDAO() {
		return historicoResumenVentasDAO;
	}


	public void setHistoricoResumenVentasDAO(HistoricoResumenVentasDAO historicoResumenVentasDAO) {
		this.historicoResumenVentasDAO = historicoResumenVentasDAO;
	}


	@Override
	@Transactional
	public void guardar(HistoricoResumenVentas historicoResumenVentas) {
		// TODO Auto-generated method stub
		getHistoricoResumenVentasDAO().guardar(historicoResumenVentas);
	}

}
