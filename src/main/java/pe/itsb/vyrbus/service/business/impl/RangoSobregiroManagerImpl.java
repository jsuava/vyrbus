/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 30/12/2014
 * Hora			: 10:22:47
 */
package pe.itsb.vyrbus.service.business.impl;

import pe.itsb.vyrbus.model.bean.RangoSobregiro;
import pe.itsb.vyrbus.model.dao.RangoSobregiroDAO;
import pe.itsb.vyrbus.service.business.RangoSobregiroManager;

/**
 * @author JABANTO
 *
 */
public class RangoSobregiroManagerImpl implements RangoSobregiroManager {

	private RangoSobregiroDAO rangoSobregiroDAO;
	/**
	 * @return the rangoSobregiroDAO
	 */
	public RangoSobregiroDAO getRangoSobregiroDAO() {
		return rangoSobregiroDAO;
	}

	/**
	 * @param rangoSobregiroDAO the rangoSobregiroDAO to set
	 */
	public void setRangoSobregiroDAO(RangoSobregiroDAO rangoSobregiroDAO) {
		this.rangoSobregiroDAO = rangoSobregiroDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RangoSobregiroManager#buscarByLineaCredito(java.lang.Double)
	 */
	@Override
	public RangoSobregiro buscarByLineaCredito(Double lineaCredito)throws Exception {
		return getRangoSobregiroDAO().buscarByLineaCredito(lineaCredito);
	}



}
