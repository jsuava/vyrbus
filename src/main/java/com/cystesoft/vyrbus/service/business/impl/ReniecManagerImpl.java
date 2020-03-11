/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/09/2013
 */
package com.cystesoft.vyrbus.service.business.impl;

import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Reniec;
import com.cystesoft.vyrbus.model.dao.ReniecDAO;
import com.cystesoft.vyrbus.service.business.ReniecManager;

/**
 * @author JABANTO
 *
 */
public class ReniecManagerImpl implements ReniecManager {
	private ReniecDAO reniecDAO;

	/**
	 * @return the reniecDAO
	 */
	public ReniecDAO getReniecDAO() {
		return reniecDAO;
	}

	/**
	 * @param reniecDAO the reniecDAO to set
	 */
	public void setReniecDAO(ReniecDAO reniecDAO) {
		this.reniecDAO = reniecDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReniecManager#buscarPax(java.lang.String)
	 */
	@Override
	public Reniec buscarPax(String numeroDocumento) throws Exception {
		// TODO Auto-generated method stub
		return getReniecDAO().buscarPax(numeroDocumento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReniecManager#validarPaxConReniec(com.tepsa.sisvyr.model.bean.Pasajero)
	 */
	@Override
	public Pasajero validarPaxConReniec(Pasajero oPasajero) throws Exception {
		// TODO Auto-generated method stub
		return getReniecDAO().validarPaxConReniec(oPasajero);
	}
	
}
