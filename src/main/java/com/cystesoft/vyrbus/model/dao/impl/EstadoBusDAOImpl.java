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

import com.cystesoft.vyrbus.model.bean.EstadoBus;
import com.cystesoft.vyrbus.model.dao.EstadoBusDAO;


/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class EstadoBusDAOImpl extends GenericDAOImpl implements EstadoBusDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<EstadoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<EstadoBus>) super.findByEstadoRegistro(EstadoBus.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<EstadoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<EstadoBus>) super.findByX(EstadoBus.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public EstadoBus buscarPorId(Long id) {
		return (EstadoBus) super.findById(EstadoBus.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#guardar(com.tepsa.sisvyr.domain.EstadoBus)
	 */
	@Override
	public void guardar(EstadoBus estadoBus) {
		super.save(estadoBus);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#actualizar(com.tepsa.sisvyr.domain.EstadoBus)
	 */
	@Override
	public void actualizar(EstadoBus estadoBus) {
		super.update(estadoBus);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoBusDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(EstadoBus.class, id);
	}
}