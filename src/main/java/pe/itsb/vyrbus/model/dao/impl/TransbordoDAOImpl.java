package pe.itsb.vyrbus.model.dao.impl;

import pe.itsb.vyrbus.model.bean.Transbordo;
import pe.itsb.vyrbus.model.dao.TransbordoDAO;

/**
 *
 * @author José Abanto
 *
 */
public class TransbordoDAOImpl extends GenericDAOImpl implements TransbordoDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TransbordoDAO#guardar(com.tepsa.sisvyr.model.bean.Transbordo)
	 */
	@Override
	public void guardar(Transbordo transbordo) throws Exception {
		super.save(transbordo);
	}


}
