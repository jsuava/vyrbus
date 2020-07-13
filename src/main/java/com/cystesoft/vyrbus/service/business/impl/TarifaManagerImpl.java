/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Oscco
 * Fecha		: 12 jul. 2020
 * Hora			: 23:49:44
 */
package com.cystesoft.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.dao.TarifaDAO;
import com.cystesoft.vyrbus.service.business.TarifaManager;

/**
 * @author Marco
 *
 */
public class TarifaManagerImpl implements TarifaManager {

	private TarifaDAO tarifaDAO;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#guardar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	/**
	 * @return the tarifaDAO
	 */
	public TarifaDAO getTarifaDAO() {
		return tarifaDAO;
	}

	/**
	 * @param tarifaDAO the tarifaDAO to set
	 */
	public void setTarifaDAO(TarifaDAO tarifaDAO) {
		this.tarifaDAO = tarifaDAO;
	}

	@Override
	@Transactional
	public void guardar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		getTarifaDAO().guardar(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#actualizar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	@Transactional
	public void actualizar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		getTarifaDAO().actualizar(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#inactivate(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivate(Long id) throws Exception {
		// TODO Auto-generated method stub
		getTarifaDAO().inactivate(id);
	}	

}
