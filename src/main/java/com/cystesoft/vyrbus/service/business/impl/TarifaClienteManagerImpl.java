/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:22:30
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TarifaCliente;
import com.cystesoft.vyrbus.model.dao.TarifaClienteDAO;
import com.cystesoft.vyrbus.service.business.TarifaClienteManager;

/**
 * @author jabanto
 *
 */
public class TarifaClienteManagerImpl implements TarifaClienteManager{
	private TarifaClienteDAO tarifaClienteDAO;

	/**
	 * @return the tarifaClienteDAO
	 */
	public TarifaClienteDAO getTarifaClienteDAO() {
		return tarifaClienteDAO;
	}

	/**
	 * @param tarifaClienteDAO the tarifaClienteDAO to set
	 */
	public void setTarifaClienteDAO(TarifaClienteDAO tarifaClienteDAO) {
		this.tarifaClienteDAO = tarifaClienteDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaCliente> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return getTarifaClienteDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getTarifaClienteDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaCliente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getTarifaClienteDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteManager#guardar(com.tepsa.sisvyr.model.bean.TarifaCliente)
	 */
	@Transactional
	@Override
	public void guardar(TarifaCliente tarifaCliente) {
		// TODO Auto-generated method stub
		getTarifaClienteDAO().guardar(tarifaCliente);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaClienteManager#actualizar(com.tepsa.sisvyr.model.bean.TarifaCliente)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaCliente tarifaCliente) {
		// TODO Auto-generated method stub
		getTarifaClienteDAO().actualizar(tarifaCliente);
	}

}
