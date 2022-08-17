/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:14:59
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.dao.EquipajeDAO;
import com.cystesoft.vyrbus.service.business.EquipajeManager;

/**
 * @author abant
 *
 */
public class EquipajeManagerImpl implements EquipajeManager{
	private EquipajeDAO equipajeDAO;

	/**
	 * @return the equipajeDAO
	 */
	public EquipajeDAO getEquipajeDAO() {
		return equipajeDAO;
	}

	/**
	 * @param equipajeDAO the equipajeDAO to set
	 */
	public void setEquipajeDAO(EquipajeDAO equipajeDAO) {
		this.equipajeDAO = equipajeDAO;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.EquipajeManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.EquipajeManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.EquipajeManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Equipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getEquipajeDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.EquipajeManager#guardar(com.cystesoft.vyrbus.model.bean.Equipaje)
	 */
	@Transactional
	@Override
	public void guardar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		getEquipajeDAO().guardar(equipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.EquipajeManager#actualizar(com.cystesoft.vyrbus.model.bean.Equipaje)
	 */
	@Transactional
	@Override
	public void actualizar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		getEquipajeDAO().actualizar(equipaje);
	}
}
