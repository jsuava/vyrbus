/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 09/09/2013
 */
package pe.itsb.vyrbus.service.business.impl;

import pe.itsb.vyrbus.model.bean.HistoricoControlEspecieValorada;
import pe.itsb.vyrbus.model.dao.HistoricoControlEspecieValoradaDAO;
import pe.itsb.vyrbus.service.business.HistoricoControlEspecieValoradaManager;

/**
 * @author JABANTO
 *
 */
public class HistoricoControlEspecieValoradaManagerImpl implements HistoricoControlEspecieValoradaManager {
	private HistoricoControlEspecieValoradaDAO historicoControlEspecieValoradaDAO;

	/**
	 * @return the controlEspecieValoradaDAO
	 */
	public HistoricoControlEspecieValoradaDAO getHistoricoControlEspecieValoradaDAO() {
		return historicoControlEspecieValoradaDAO;
	}

	/**
	 * @param controlEspecieValoradaDAO the controlEspecieValoradaDAO to set
	 */
	public void setHistoricoControlEspecieValoradaDAO(HistoricoControlEspecieValoradaDAO historicoControlEspecieValoradaDAO) {
		this.historicoControlEspecieValoradaDAO = historicoControlEspecieValoradaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.HistoricoControlEspecieValoradaManager#guardar(com.tepsa.sisvyr.model.dao.HistoricoControlEspecieValoradaDAO)
	 */
	@Override
	public void guardar(HistoricoControlEspecieValorada historicoControlEspecieValorada)throws Exception {
		// TODO Auto-generated method stub
		getHistoricoControlEspecieValoradaDAO().guardar(historicoControlEspecieValorada);
	}


}
