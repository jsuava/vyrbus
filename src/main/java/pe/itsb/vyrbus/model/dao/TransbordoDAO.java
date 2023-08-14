package pe.itsb.vyrbus.model.dao;
import pe.itsb.vyrbus.model.bean.Transbordo;

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
