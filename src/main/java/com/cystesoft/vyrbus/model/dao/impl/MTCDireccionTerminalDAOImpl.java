/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 14:55:56
 */
package com.cystesoft.vyrbus.model.dao.impl;

import com.cystesoft.vyrbus.model.bean.MTCDireccionTerminal;
import com.cystesoft.vyrbus.model.dao.MTCDireccionTerminalDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class MTCDireccionTerminalDAOImpl extends GenericDAOImpl implements MTCDireccionTerminalDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MTCDireccionTerminalDAO#buscarPorIdAgencia(java.lang.Integer)
	 */
	@Override
	public MTCDireccionTerminal buscarPorIdAgencia(Integer idAgencia)throws Exception {
		String hql="FROM MTCDireccionTerminal dt WHERE dt.agencia.id="+idAgencia+" AND dt.estadoRegistro='"+Constantes.VALUE_ACTIVO+"' ";

		log.info(hql);
		MTCDireccionTerminal mtcDireccionTerminal=(MTCDireccionTerminal) getSession().createQuery(hql).uniqueResult();
		return mtcDireccionTerminal;
	}

}
