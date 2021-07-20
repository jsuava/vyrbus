/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:06:43
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Equipaje;
import com.cystesoft.vyrbus.model.dao.EquipajeDAO;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class EquipajeDAOImpl extends GenericDAOImpl implements EquipajeDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.EquipajeDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */	
	@Override
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<Equipaje>) super.findByEstadoRegistro(Equipaje.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.EquipajeDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<Equipaje>) super.findByX(Equipaje.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.EquipajeDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Equipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (Equipaje) findById(Equipaje.class, id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.EquipajeDAO#guardar(com.cystesoft.vyrbus.model.bean.Equipaje)
	 */
	@Override
	public void guardar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		super.save(equipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.EquipajeDAO#actualizar(com.cystesoft.vyrbus.model.bean.Equipaje)
	 */
	@Override
	public void actualizar(Equipaje equipaje) {
		// TODO Auto-generated method stub
		super.update(equipaje);
	}

}
