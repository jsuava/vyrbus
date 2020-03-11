/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:56:00
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.EmbarquePasajero;
import com.cystesoft.vyrbus.model.dao.EmbarquePasajeroDAO;
import com.cystesoft.vyrbus.service.business.EmbarquePasajeroManager;

/**
 * @author Jose Abanto
 *
 */
public class EmbarquePasajeroManagerImpl implements EmbarquePasajeroManager{
	private EmbarquePasajeroDAO embarquePasajeroDAO;

	/**
	 * @return the embarquePasajeroDAO
	 */
	public EmbarquePasajeroDAO getEmbarquePasajeroDAO() {
		return embarquePasajeroDAO;
	}

	/**
	 * @param embarquePasajeroDAO the embarquePasajeroDAO to set
	 */
	public void setEmbarquePasajeroDAO(EmbarquePasajeroDAO embarquePasajeroDAO) {
		this.embarquePasajeroDAO = embarquePasajeroDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EmbarquePasajeroManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<EmbarquePasajero> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return getEmbarquePasajeroDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EmbarquePasajeroManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<EmbarquePasajero> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getEmbarquePasajeroDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EmbarquePasajeroManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public EmbarquePasajero buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getEmbarquePasajeroDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EmbarquePasajeroManager#guardar(com.tepsa.sisvyr.model.bean.EmbarquePasajero)
	 */
	@Transactional
	@Override
	public void guardar(EmbarquePasajero embarquePasajero) {
		// TODO Auto-generated method stub
		getEmbarquePasajeroDAO().guardar(embarquePasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EmbarquePasajeroManager#actualizar(com.tepsa.sisvyr.model.bean.EmbarquePasajero)
	 */
	@Transactional
	@Override
	public void actualizar(EmbarquePasajero embarquePasajero) {
		// TODO Auto-generated method stub
		getEmbarquePasajeroDAO().actualizar(embarquePasajero);
	}
}
