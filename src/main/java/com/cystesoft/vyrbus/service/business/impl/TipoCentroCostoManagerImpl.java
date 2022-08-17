/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 22/07/2015
 * Hora			: 09:30:21
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoCentroCosto;
import com.cystesoft.vyrbus.model.dao.TipoCentroCostoDAO;
import com.cystesoft.vyrbus.service.business.TipoCentroCostoManager;

/**
 * @author jabanto
 *
 */
public class TipoCentroCostoManagerImpl implements TipoCentroCostoManager{
	private TipoCentroCostoDAO tipoCentroCostoDAO;

	/**
	 * @return the tipoCentroCostoDAO
	 */
	public TipoCentroCostoDAO getTipoCentroCostoDAO() {
		return tipoCentroCostoDAO;
	}

	/**
	 * @param tipoCentroCostoDAO the tipoCentroCostoDAO to set
	 */
	public void setTipoCentroCostoDAO(TipoCentroCostoDAO tipoCentroCostoDAO) {
		this.tipoCentroCostoDAO = tipoCentroCostoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCentroCostoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoCentroCosto> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return getTipoCentroCostoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCentroCostoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTipoCentroCostoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCentroCostoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCentroCosto buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTipoCentroCostoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCentroCostoManager#guardar(com.tepsa.sisvyr.model.bean.TipoCentroCosto)
	 */
	@Override
	@Transactional
	public void guardar(TipoCentroCosto tipoCentroCosto) {
		// TODO Auto-generated method stub
		getTipoCentroCostoDAO().guardar(tipoCentroCosto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoCentroCostoManager#actualizar(com.tepsa.sisvyr.model.bean.TipoCentroCosto)
	 */
	@Override
	@Transactional
	public void actualizar(TipoCentroCosto tipoCentroCosto) {
		// TODO Auto-generated method stub
		getTipoCentroCostoDAO().actualizar(tipoCentroCosto);
	}


}
