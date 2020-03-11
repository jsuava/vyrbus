/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 11/07/2016
 * Hora			: 17:38:45
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoAsiento;
import com.cystesoft.vyrbus.model.dao.TipoAsientoDAO;
import com.cystesoft.vyrbus.service.business.TipoAsientoManager;

/**
 * @author jabanto
 *
 */
public class TipoAsientoManagerImpl implements TipoAsientoManager{
	private TipoAsientoDAO tipoAsientoDAO;

	/**
	 * @return the tipoAsientoDAO
	 */
	public TipoAsientoDAO getTipoAsientoDAO() {
		return tipoAsientoDAO;
	}

	/**
	 * @param tipoAsientoDAO the tipoAsientoDAO to set
	 */
	public void setTipoAsientoDAO(TipoAsientoDAO tipoAsientoDAO) {
		this.tipoAsientoDAO = tipoAsientoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAsientoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoAsiento> buscarPorEstadoRegistro(String estado,String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return getTipoAsientoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAsientoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoAsiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return getTipoAsientoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAsientoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoAsiento buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getTipoAsientoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAsientoManager#guardar(com.tepsa.sisvyr.model.bean.TipoAsiento)
	 */
	@Transactional
	@Override
	public void guardar(TipoAsiento tipoAsiento) throws Exception {
		// TODO Auto-generated method stub
		getTipoAsientoDAO().guardar(tipoAsiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAsientoManager#actualizar(com.tepsa.sisvyr.model.bean.TipoAsiento)
	 */
	@Transactional
	@Override
	public void actualizar(TipoAsiento tipoAsiento) throws Exception {
		// TODO Auto-generated method stub
		getTipoAsientoDAO().actualizar(tipoAsiento);
	}
	
}
