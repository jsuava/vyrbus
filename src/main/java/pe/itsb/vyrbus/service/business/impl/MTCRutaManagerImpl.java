/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 15:04:25
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.List;

import pe.itsb.vyrbus.model.bean.MTCRuta;
import pe.itsb.vyrbus.model.dao.MTCRutaDAO;
import pe.itsb.vyrbus.service.business.MTCRutaManager;

/**
 * @author JABANTO
 *
 */
public class MTCRutaManagerImpl implements MTCRutaManager {
	private MTCRutaDAO mtcRutaDAO;

	/**
	 * @return the mtcRutaDAO
	 */
	public MTCRutaDAO getMtcRutaDAO() {
		return mtcRutaDAO;
	}

	/**
	 * @param mtcRutaDAO the mtcRutaDAO to set
	 */
	public void setMtcRutaDAO(MTCRutaDAO mtcRutaDAO) {
		this.mtcRutaDAO = mtcRutaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MTCRutaManager#buscarPorEstado(java.lang.String)
	 */
	@Override
	public List<MTCRuta> buscarPorEstado(String estado) throws Exception {
		return getMtcRutaDAO().buscarPorEstado(estado);
	}



}
