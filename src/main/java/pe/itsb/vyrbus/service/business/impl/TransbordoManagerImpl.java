package pe.itsb.vyrbus.service.business.impl;


import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Transbordo;
import pe.itsb.vyrbus.model.dao.TransbordoDAO;
import pe.itsb.vyrbus.service.business.TransbordoManager;

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
