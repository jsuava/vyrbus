/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 01/08/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import pe.itsb.vyrbus.model.bean.OcupacionAsientosDesbloqueados;
import pe.itsb.vyrbus.model.dao.OcupacionAsientosDesbloqueadosDAO;

/**
 * @author JABANTO
 *
 */
public class OcupacionAsientosDesbloqueadosDAOImpl extends GenericDAOImpl implements OcupacionAsientosDesbloqueadosDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OcupacionAsientosDesbloqueadosDAO#guardar(com.tepsa.sisvyr.model.bean.OcupacionAsientosDesbloqueados)
	 */
	@Override
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados)throws Exception {
		// TODO Auto-generated method stub
		super.save(ocupacionAsientosDesbloqueados);
	}


}
