package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoGasto;
import com.cystesoft.vyrbus.model.dao.TipoGastoDAO;

/**
 *
 * @author JABANTO
 *
 */

@SuppressWarnings("unchecked")
public class TipoGastoDAOImpl extends GenericDAOImpl implements TipoGastoDAO{


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoGasto> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<TipoGasto>) super.findByEstadoRegistro(TipoGasto.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoGasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<TipoGasto>) super.findByX(TipoGasto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoGasto buscarPorId(Long id) {
		return (TipoGasto) super.findById(TipoGasto.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#guardar(com.tepsa.sisvyr.model.bean.TipoGasto)
	 */
	@Override
	public void guardar(TipoGasto tipoGasto) {
		super.save(tipoGasto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoGasto)
	 */
	@Override
	public void actualizar(TipoGasto tipoGasto) {
		super.update(tipoGasto);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoGastoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoGasto.class, id);
	}

}
