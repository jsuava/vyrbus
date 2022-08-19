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

import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.dao.PreferenciaAlimentariaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class PreferenciaAlimentariaDAOImpl extends GenericDAOImpl implements PreferenciaAlimentariaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<PreferenciaAlimentaria> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<PreferenciaAlimentaria>) super.findByEstadoRegistro(PreferenciaAlimentaria.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<PreferenciaAlimentaria> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<PreferenciaAlimentaria>) super.findByX(PreferenciaAlimentaria.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public PreferenciaAlimentaria buscarPorId(Long id) {
		return (PreferenciaAlimentaria) super.findById(PreferenciaAlimentaria.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#guardar(com.tepsa.sisvyr.domain.PreferenciaAlimentaria)
	 */
	@Override
	public void guardar(PreferenciaAlimentaria preferenciaAlimentaria) {
		super.save(preferenciaAlimentaria);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#actualizar(com.tepsa.sisvyr.domain.PreferenciaAlimentaria)
	 */
	@Override
	public void actualizar(PreferenciaAlimentaria preferenciaAlimentaria) {
		super.update(preferenciaAlimentaria);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PreferenciaAlimentariaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(PreferenciaAlimentaria.class, id);
	}
}