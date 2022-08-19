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

import com.cystesoft.vyrbus.model.bean.EstadoCivil;
import com.cystesoft.vyrbus.model.dao.EstadoCivilDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class EstadoCivilDAOImpl extends GenericDAOImpl implements EstadoCivilDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<EstadoCivil> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<EstadoCivil>) super.findByEstadoRegistro(EstadoCivil.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<EstadoCivil> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<EstadoCivil>) super.findByX(EstadoCivil.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public EstadoCivil buscarPorId(Long id) {
		return (EstadoCivil) super.findById(EstadoCivil.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#guardar(com.tepsa.sisvyr.domain.EstadoCivil)
	 */
	@Override
	public void guardar(EstadoCivil estadoCivil) {
		super.save(estadoCivil);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#actualizar(com.tepsa.sisvyr.domain.EstadoCivil)
	 */
	@Override
	public void actualizar(EstadoCivil estadoCivil) {
		super.update(estadoCivil);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.EstadoCivilDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(EstadoCivil.class, id);
	}
}