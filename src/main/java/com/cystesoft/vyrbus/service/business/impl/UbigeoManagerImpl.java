/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.dao.UbigeoDAO;
import com.cystesoft.vyrbus.service.business.UbigeoManager;

/**
 * @author Jose
 *
 */
public class UbigeoManagerImpl implements UbigeoManager {
	private UbigeoDAO ubigeoDAO;
	
	/**
	 * @return the ubigeoDAO
	 */
	public UbigeoDAO getUbigeoDAO() {
		return ubigeoDAO;
	}
	/**
	 * @param ubigeoDAO the ubigeoDAO to set
	 */
	public void setUbigeoDAO(UbigeoDAO ubigeoDAO) {
		this.ubigeoDAO = ubigeoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Ubigeo> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getUbigeoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Ubigeo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getUbigeoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#buscarPorId(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Ubigeo buscarPorId(String id) throws Exception {
		return getUbigeoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#guardar(com.tepsa.sisvyr.model.bean.Ubigeo)
	 */
	@Override
	@Transactional
	public void guardar(Ubigeo ubigeo) throws Exception {
		getUbigeoDAO().guardar(ubigeo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#actualizar(com.tepsa.sisvyr.model.bean.Ubigeo)
	 */
	@Override
	@Transactional
	public void actualizar(Ubigeo ubigeo) throws Exception {
		getUbigeoDAO().actualizar(ubigeo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#ubicacionGeografica(com.tepsa.sisvyr.model.bean.Ubigeo)
	 */
	@Override
	public String ubicacionGeografica(Ubigeo ubigeo) throws Exception {
		return getUbigeoDAO().ubicacionGeografica(ubigeo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#ubicacionGeografica(java.lang.String)
	 */
	@Override
	public String ubicacionGeografica(String id) throws Exception {
		return getUbigeoDAO().ubicacionGeografica(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.UbigeoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getUbigeoDAO().inactivar(id);
	}

}
