package com.cystesoft.vyrbus.service.business.impl;


import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Transbordo;
import com.cystesoft.vyrbus.model.dao.TransbordoDAO;
import com.cystesoft.vyrbus.service.business.TransbordoManager;

public class TransbordoManagerImpl implements  TransbordoManager {
	private TransbordoDAO transbordoDAO;

	public void setTransbordoDAO(TransbordoDAO transbordoDAO){
		this.transbordoDAO=transbordoDAO;
	}

	public TransbordoDAO getTransbordoPasajerosDAO(){
		return transbordoDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TransbordoManager#guardar(com.tepsa.sisvyr.model.bean.Transbordo)
	 */
	@Override
	@Transactional
	public void guardar(Transbordo transbordo) throws Exception {
		getTransbordoPasajerosDAO().guardar(transbordo);

	}


}
