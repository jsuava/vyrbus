/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 01/08/2013
 */
package pe.itsb.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.OcupacionAsientosDesbloqueados;
import pe.itsb.vyrbus.model.dao.OcupacionAsientosDesbloqueadosDAO;
import pe.itsb.vyrbus.service.business.OcupacionAsientosDesbloqueadosManager;

/**
 * @author JABANTO
 *
 */
public class OcupacionAsientosDesbloqueadosManagerImpl implements OcupacionAsientosDesbloqueadosManager {
	private OcupacionAsientosDesbloqueadosDAO ocupacionAsientosDesbloqueadosDAO;

	/**
	 * @return the ocupacionAsientosDesbloqueadosDAO
	 */
	public OcupacionAsientosDesbloqueadosDAO getOcupacionAsientosDesbloqueadosDAO() {
		return ocupacionAsientosDesbloqueadosDAO;
	}


	/**
	 * @param ocupacionAsientosDesbloqueadosDAO the ocupacionAsientosDesbloqueadosDAO to set
	 */
	public void setOcupacionAsientosDesbloqueadosDAO(
			OcupacionAsientosDesbloqueadosDAO ocupacionAsientosDesbloqueadosDAO) {
		this.ocupacionAsientosDesbloqueadosDAO = ocupacionAsientosDesbloqueadosDAO;
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.OcupacionAsientosDesbloqueadosManager#guardar(com.tepsa.sisvyr.model.bean.OcupacionAsientosDesbloqueados)
	 */
	@Transactional
	@Override
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados)throws Exception {
		// TODO Auto-generated method stub
		getOcupacionAsientosDesbloqueadosDAO().guardar(ocupacionAsientosDesbloqueados);
	}




}
