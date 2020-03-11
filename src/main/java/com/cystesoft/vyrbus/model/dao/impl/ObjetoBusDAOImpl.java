/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ObjetoBus;
import com.cystesoft.vyrbus.model.dao.ObjetoBusDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ObjetoBusDAOImpl extends GenericDAOImpl implements ObjetoBusDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<ObjetoBus> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<ObjetoBus>) super.findByEstadoRegistro(ObjetoBus.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<ObjetoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<ObjetoBus>) super.findByX(ObjetoBus.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public ObjetoBus buscarPorId(Long id) {
		return (ObjetoBus) super.findById(ObjetoBus.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#guardar(com.tepsa.sisvyr.domain.ObjetoBus)
	 */
	@Override
	public void guardar(ObjetoBus objetoBus) {
		super.save(objetoBus);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#actualizar(com.tepsa.sisvyr.domain.ObjetoBus)
	 */
	@Override
	public void actualizar(ObjetoBus objetoBus) {
		super.update(objetoBus);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ObjetoBusDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(ObjetoBus.class, id);
	}
}