/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 15:05:30
 */
package pe.itsb.vyrbus.service.business.impl;

import pe.itsb.vyrbus.model.bean.MTCDireccionTerminal;
import pe.itsb.vyrbus.model.dao.MTCDireccionTerminalDAO;
import pe.itsb.vyrbus.service.business.MTCDireccionTerminalManager;

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
