/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	: 
 * Autor		: Jos� Abanto
 * Fecha		: 9 feb. 2024
 * Hora			: 12:53:31
 */
package pe.itsb.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.MovimientoPasajes;
import pe.itsb.vyrbus.model.dao.MovimientoPasajesDAO;
import pe.itsb.vyrbus.service.business.MovimientoPasajesManager;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Marco
 *
 */
public class MovimientoPasajesManagerImpl implements MovimientoPasajesManager {

	private MovimientoPasajesDAO movimientoPasajesDAO;

	public MovimientoPasajesDAO getMovimientoPasajesDAO() {
		return movimientoPasajesDAO;
	}

	public void setMovimientoPasajesDAO(MovimientoPasajesDAO movimientoPasajesDAO) {
		this.movimientoPasajesDAO = movimientoPasajesDAO;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.MovimientoPasajesManager#guardar(com.cystesoft.vyrbus.model.bean.MovimientoPasajes)
	 */
	@Override
	@Transactional
	public int guardar(MovimientoPasajes movimientoPasajes) throws Exception {
		// TODO Auto-generated method stub
		int result = Constantes.FAILURE;
		try{
			this.movimientoPasajesDAO.save(movimientoPasajes);

			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
		
	}
	
	
}
