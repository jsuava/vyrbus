/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 01/09/2016
 * Hora			: 18:25:05
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.PoolLocalidad;
import com.cystesoft.vyrbus.model.dao.PoolLocalidadDAO;
import com.cystesoft.vyrbus.service.business.PoolLocalidadManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author jabanto
 *
 */
public class PoolLocalidadManagerImpl implements PoolLocalidadManager{
	private PoolLocalidadDAO poolLocalidadDAO;

	/**
	 * @return the poolLocalidadDAO
	 */
	public PoolLocalidadDAO getPoolLocalidadDAO() {
		return poolLocalidadDAO;
	}

	/**
	 * @param poolLocalidadDAO the poolLocalidadDAO to set
	 */
	public void setPoolLocalidadDAO(PoolLocalidadDAO poolLocalidadDAO) {
		this.poolLocalidadDAO = poolLocalidadDAO;
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<PoolLocalidad> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getPoolLocalidadDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<PoolLocalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getPoolLocalidadDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public PoolLocalidad buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getPoolLocalidadDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#guardar(com.tepsa.sisvyr.model.bean.LocalidadIntegracion)
	 */
	@Transactional
	@Override
	public void guardar(PoolLocalidad localidadIntegracion) {
		// TODO Auto-generated method stub
		getPoolLocalidadDAO().guardar(localidadIntegracion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#actualizar(com.tepsa.sisvyr.model.bean.LocalidadIntegracion)
	 */
	@Transactional
	@Override
	public void actualizar(PoolLocalidad localidadIntegracion) {
		// TODO Auto-generated method stub
		getPoolLocalidadDAO().update(localidadIntegracion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadIntegracionManager#buscarByLocalidadID(java.lang.Integer)
	 */
	@Override
	public List<PoolLocalidad> buscarByLocalidadID(Integer localidadIDTepsa) {
		TreeMap<String, Object> criteriosBusqueda= new TreeMap<>();
		criteriosBusqueda.put("localidadDestino", new Localidad(localidadIDTepsa));
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);

		List<PoolLocalidad> result= buscarPorX(criteriosBusqueda, null);

		return result;
	}



}
