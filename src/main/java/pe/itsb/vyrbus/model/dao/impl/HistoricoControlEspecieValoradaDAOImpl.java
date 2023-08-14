/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto.
 * Fecha		: 09/09/2013
 */
package pe.itsb.vyrbus.model.dao.impl;
import pe.itsb.vyrbus.model.bean.HistoricoControlEspecieValorada;
import pe.itsb.vyrbus.model.dao.HistoricoControlEspecieValoradaDAO;

/**
 * @author JABANTO
 *
 */
public class HistoricoControlEspecieValoradaDAOImpl extends GenericDAOImpl implements HistoricoControlEspecieValoradaDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HistoricoControlEspecieValoradaDAO#guardar(com.tepsa.sisvyr.model.dao.HistoricoControlEspecieValoradaDAO)
	 */
	@Override
	public void guardar(HistoricoControlEspecieValorada historicoControlEspecieValorada)throws Exception {
		// TODO Auto-generated method stub
		super.save(historicoControlEspecieValorada);
	}


}
