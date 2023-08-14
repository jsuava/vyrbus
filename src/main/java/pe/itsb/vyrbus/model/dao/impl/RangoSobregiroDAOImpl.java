/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 30/12/2014
 * Hora			: 10:14:42
 */
package pe.itsb.vyrbus.model.dao.impl;

import pe.itsb.vyrbus.model.bean.RangoSobregiro;
import pe.itsb.vyrbus.model.dao.RangoSobregiroDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class RangoSobregiroDAOImpl extends GenericDAOImpl implements RangoSobregiroDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.RangoSobregiroDAO#buscarByLineaCredito(java.lang.Double)
	 */
	@Override
	public RangoSobregiro buscarByLineaCredito(Double lineaCredito)throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM RangoSobregiro rs WHERE "+lineaCredito+" BETWEEN rs.lineaCreditoMinima AND rs.lineaCreditoMaxima AND rs.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";
		log.info(hql);
		RangoSobregiro rangoSobregiro=(RangoSobregiro)getSession().createQuery(hql).uniqueResult();

		return rangoSobregiro;
	}

}
