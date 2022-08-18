/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 14:56:58
 */
package com.cystesoft.vyrbus.model.dao.impl;


import java.util.List;

import com.cystesoft.vyrbus.model.bean.MTCRuta;
import com.cystesoft.vyrbus.model.dao.MTCRutaDAO;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class MTCRutaDAOImpl extends GenericDAOImpl implements MTCRutaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MTCRutaDAO#buscarPorEstado(java.lang.String)
	 */
	@Override
	public List<MTCRuta> buscarPorEstado(String estado) throws Exception {
		return (List<MTCRuta>) findByEstadoRegistro(MTCRuta.class, estado, "denominacion");
	}



}
