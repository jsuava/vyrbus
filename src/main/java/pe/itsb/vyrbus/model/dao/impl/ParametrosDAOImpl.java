/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 14/05/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import pe.itsb.vyrbus.model.bean.Parametros;
import pe.itsb.vyrbus.model.dao.ParametrosDAO;

/**
 * @author JABANTO
 *
 */
public class ParametrosDAOImpl extends GenericDAOImpl implements ParametrosDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ParametrosDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public Parametros buscarPorEstadoRegistro(String estado) {
		Class<?> oClass=Parametros.class;
		String hql="FROM "+oClass.getSimpleName()+" WHERE estadoRegistro='"+estado+"'";

		log.info(hql);

		return (Parametros) getSession().createQuery(hql).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ParametrosDAO#guardar(com.tepsa.sisvyr.model.bean.Parametros)
	 */
	@Override
	public void guardar(Parametros parametros) throws Exception {
		super.save(parametros);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ParametrosDAO#actualizar(com.tepsa.sisvyr.model.bean.Parametros)
	 */
	@Override
	public void actualizar(Parametros parametros) throws Exception {
		// TODO Auto-generated method stub
		super.update(parametros);
	}


}
