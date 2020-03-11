/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/12/2014
 * Hora			: 10:22:47
 */
package com.cystesoft.vyrbus.service.business.impl;

import com.cystesoft.vyrbus.model.bean.RangoSobregiro;
import com.cystesoft.vyrbus.model.dao.RangoSobregiroDAO;
import com.cystesoft.vyrbus.service.business.RangoSobregiroManager;

/**
 * @author JABANTO
 *
 */
public class RangoSobregiroManagerImpl implements RangoSobregiroManager {

	private RangoSobregiroDAO rangoSobregiroDAO;
	/**
	 * @return the rangoSobregiroDAO
	 */
	public RangoSobregiroDAO getRangoSobregiroDAO() {
		return rangoSobregiroDAO;
	}

	/**
	 * @param rangoSobregiroDAO the rangoSobregiroDAO to set
	 */
	public void setRangoSobregiroDAO(RangoSobregiroDAO rangoSobregiroDAO) {
		this.rangoSobregiroDAO = rangoSobregiroDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RangoSobregiroManager#buscarByLineaCredito(java.lang.Double)
	 */
	@Override
	public RangoSobregiro buscarByLineaCredito(Double lineaCredito)throws Exception {
		return getRangoSobregiroDAO().buscarByLineaCredito(lineaCredito);
	}

	

}
