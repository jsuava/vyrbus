/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 15:05:30
 */
package com.cystesoft.vyrbus.service.business.impl;

import com.cystesoft.vyrbus.model.bean.MTCDireccionTerminal;
import com.cystesoft.vyrbus.model.dao.MTCDireccionTerminalDAO;
import com.cystesoft.vyrbus.service.business.MTCDireccionTerminalManager;

/**
 * @author JABANTO
 *
 */
public class MTCDireccionTerminalManagerImpl implements MTCDireccionTerminalManager{
	private MTCDireccionTerminalDAO mtcDireccionTerminalDAO;

	/**
	 * @return the mtcDireccionTerminalDAO
	 */
	public MTCDireccionTerminalDAO getMtcDireccionTerminalDAO() {
		return mtcDireccionTerminalDAO;
	}

	/**
	 * @param mtcDireccionTerminalDAO the mtcDireccionTerminalDAO to set
	 */
	public void setMtcDireccionTerminalDAO(MTCDireccionTerminalDAO mtcDireccionTerminalDAO) {
		this.mtcDireccionTerminalDAO = mtcDireccionTerminalDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCDireccionTerminalManager#buscarPorIdAgencia(java.lang.Integer)
	 */
	@Override
	public MTCDireccionTerminal buscarPorIdAgencia(Integer idAgencia)throws Exception {
		return getMtcDireccionTerminalDAO().buscarPorIdAgencia(idAgencia);
	}
	

}
