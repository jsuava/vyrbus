/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/01/2017
 * Hora			: 15:35:25
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoPrecio;
import com.cystesoft.vyrbus.model.dao.TipoPrecioDAO;
import com.cystesoft.vyrbus.service.business.TipoPrecioManager;

/**
 * @author Jose Abanto
 *
 */
public class TipoPrecioManagerImpl implements TipoPrecioManager{
	private TipoPrecioDAO tipoPrecioDAO;

	/**
	 * @return the tipoPrecioDAO
	 */
	public TipoPrecioDAO getTipoPrecioDAO() {
		return tipoPrecioDAO;
	}

	/**
	 * @param tipoPrecioDAO the tipoPrecioDAO to set
	 */
	public void setTipoPrecioDAO(TipoPrecioDAO tipoPrecioDAO) {
		this.tipoPrecioDAO = tipoPrecioDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoPrecio> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return getTipoPrecioDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoPrecio> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTipoPrecioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoPrecio buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTipoPrecioDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#guardar(com.tepsa.sisvyr.model.bean.TipoPrecio)
	 */
	@Transactional
	@Override
	public void guardar(TipoPrecio tipoPrecio) {
		// TODO Auto-generated method stub
		getTipoPrecioDAO().guardar(tipoPrecio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#actualizar(com.tepsa.sisvyr.model.bean.TipoPrecio)
	 */
	@Transactional
	@Override
	public void actualizar(TipoPrecio tipoPrecio) {
		// TODO Auto-generated method stub
		getTipoPrecioDAO().actualizar(tipoPrecio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPrecioManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub
		getTipoPrecioDAO().inactivar(id);
	}
	
}
