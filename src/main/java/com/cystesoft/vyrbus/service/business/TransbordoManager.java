package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.Transbordo;



/**
 *
 * @author José Abanto
 *
 */
public interface TransbordoManager {

	/**
	 * Guarda el Trnasbordo
	 * @param transbordo : Class transbordo.
	 * @throws Exception
	 */
	public void guardar(Transbordo transbordo) throws Exception;
}
