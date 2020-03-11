/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26/10/2016
 * Hora			: 15:41:27
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.dao.TipoNotaDAO;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class TipoNotaDAOImpl extends GenericDAOImpl implements TipoNotaDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */	
	@Override
	public ArrayList<TipoNota> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<TipoNota>) super.findByEstadoRegistro(TipoNota.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoNota> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<TipoNota>) super.findByX(TipoNota.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoNota buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (TipoNota) super.findById(TipoNota.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#guardar(com.tepsa.sisvyr.model.bean.TipoNota)
	 */
	@Override
	public void guardar(TipoNota tipoNota) {
		// TODO Auto-generated method stub
		super.save(tipoNota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoNota)
	 */
	@Override
	public void actualizar(TipoNota tipoNota) {
		// TODO Auto-generated method stub
		super.update(tipoNota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoNotaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub
		
	}

}
