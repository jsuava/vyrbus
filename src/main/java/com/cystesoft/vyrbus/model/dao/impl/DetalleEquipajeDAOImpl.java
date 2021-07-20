/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:10:27
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO;

/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class DetalleEquipajeDAOImpl  extends GenericDAOImpl implements DetalleEquipajeDAO{

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */	
	@Override
	public ArrayList<DetalleEquipaje> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEquipaje>) super.findByEstadoRegistro(DetalleEquipaje.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleEquipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEquipaje>) super.findByX(DetalleEquipaje.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleEquipaje buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (DetalleEquipaje) super.findById(DetalleEquipaje.class, id);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#guardar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Override
	public void guardar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		super.save(detalleEquipaje);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.DetalleEquipajeDAO#actualizar(com.cystesoft.vyrbus.model.bean.DetalleEquipaje)
	 */
	@Override
	public void actualizar(DetalleEquipaje detalleEquipaje) {
		// TODO Auto-generated method stub
		super.update(detalleEquipaje);
	}

}
