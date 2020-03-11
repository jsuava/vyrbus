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

import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.dao.TipoAgenciaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoAgenciaDAOImpl extends GenericDAOImpl implements TipoAgenciaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoAgencia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoAgencia>) super.findByEstadoRegistro(TipoAgencia.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoAgencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoAgencia>) super.findByX(TipoAgencia.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoAgencia buscarPorId(Long id) {
		return (TipoAgencia) super.findById(TipoAgencia.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#guardar(com.tepsa.sisvyr.domain.TipoAgencia)
	 */
	@Override
	public void guardar(TipoAgencia tipoAgencia) {
		super.save(tipoAgencia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#actualizar(com.tepsa.sisvyr.domain.TipoAgencia)
	 */
	@Override
	public void actualizar(TipoAgencia tipoAgencia) {
		super.update(tipoAgencia);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoAgenciaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoAgencia.class, id);
	}
}