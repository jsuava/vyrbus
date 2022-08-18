/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 22/07/2015
 * Hora			: 09:32:29
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCentroCosto;
import com.cystesoft.vyrbus.model.dao.TipoCentroCostoDAO;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class TipoCentroCostoDAOImpl extends GenericDAOImpl implements TipoCentroCostoDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCentroCostoDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */

	@Override
	public ArrayList<TipoCentroCosto> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TipoCentroCosto>) super.findByEstadoRegistro(TipoCentroCosto.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCentroCostoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TipoCentroCosto>) super.findByX(TipoCentroCosto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCentroCostoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCentroCosto buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TipoCentroCosto) super.findById(TipoCentroCosto.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCentroCostoDAO#guardar(com.tepsa.sisvyr.model.bean.TipoCentroCosto)
	 */
	@Override
	public void guardar(TipoCentroCosto tipoCentroCosto) {
		// TODO Auto-generated method stub
		super.save(tipoCentroCosto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCentroCostoDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoCentroCosto)
	 */
	@Override
	public void actualizar(TipoCentroCosto tipoCentroCosto) {
		// TODO Auto-generated method stub
		super.update(tipoCentroCosto);
	}

}
