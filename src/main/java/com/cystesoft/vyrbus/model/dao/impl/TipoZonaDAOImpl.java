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

import com.cystesoft.vyrbus.model.bean.TipoZona;
import com.cystesoft.vyrbus.model.dao.TipoZonaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoZonaDAOImpl extends GenericDAOImpl implements TipoZonaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoZona> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoZona>) super.findByEstadoRegistro(TipoZona.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoZona> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoZona>) super.findByX(TipoZona.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoZona buscarPorId(Long id) {
		return (TipoZona) super.findById(TipoZona.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#guardar(com.tepsa.sisvyr.domain.TipoZona)
	 */
	@Override
	public void guardar(TipoZona tipoZona) {
		super.save(tipoZona);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#actualizar(com.tepsa.sisvyr.domain.TipoZona)
	 */
	@Override
	public void actualizar(TipoZona tipoZona) {
		super.update(tipoZona);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoZonaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoZona.class, id);
	}
}