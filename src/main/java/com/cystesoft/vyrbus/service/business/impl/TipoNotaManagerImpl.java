/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 26/10/2016
 * Hora			: 15:41:55
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoNota;
import com.cystesoft.vyrbus.model.dao.TipoNotaDAO;
import com.cystesoft.vyrbus.service.business.TipoNotaManager;

/**
 * @author jabanto
 *
 */
public class TipoNotaManagerImpl implements TipoNotaManager{
	private TipoNotaDAO tipoNotaDAO;

	/**
	 * @return the tipoNotaDAO
	 */
	public TipoNotaDAO getTipoNotaDAO() {
		return tipoNotaDAO;
	}

	/**
	 * @param tipoNotaDAO the tipoNotaDAO to set
	 */
	public void setTipoNotaDAO(TipoNotaDAO tipoNotaDAO) {
		this.tipoNotaDAO = tipoNotaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoNota> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return getTipoNotaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoNota> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTipoNotaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoNota buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTipoNotaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#guardar(com.tepsa.sisvyr.model.bean.TipoNota)
	 */
	@Transactional
	@Override
	public void guardar(TipoNota tipoNota) {
		// TODO Auto-generated method stub
		getTipoNotaDAO().guardar(tipoNota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoNota)
	 */
	@Transactional
	@Override
	public void actualizar(TipoNota tipoNota) {
		// TODO Auto-generated method stub
		getTipoNotaDAO().actualizar(tipoNota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoNotaManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub
		getTipoNotaDAO().inactivar(id);
	}



}
