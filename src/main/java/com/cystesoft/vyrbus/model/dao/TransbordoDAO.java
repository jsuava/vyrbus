package com.cystesoft.vyrbus.model.dao;
import com.cystesoft.vyrbus.model.bean.Transbordo;

/**
 *
 * @author José Abanto
 *
 */
public interface TransbordoDAO extends GenericDAO {

	/**
	 * Guarda el Trnasbordo
	 * @param transbordo : Class transbordo.
	 * @throws Exception
	 */
	public void guardar(Transbordo transbordo) throws Exception;
}
