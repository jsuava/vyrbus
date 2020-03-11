/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:50:50
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.EmbarquePasajero;
import com.cystesoft.vyrbus.model.dao.EmbarquePasajeroDAO;

/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class EmbarquePasajeroDAOImpl extends GenericDAOImpl implements EmbarquePasajeroDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EmbarquePasajeroDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<EmbarquePasajero> buscarPorEstadoRegistro(String estado,
			String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<EmbarquePasajero>) super.findByEstadoRegistro(EmbarquePasajero.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EmbarquePasajeroDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<EmbarquePasajero> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<EmbarquePasajero>) super.findByX(EmbarquePasajero.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EmbarquePasajeroDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public EmbarquePasajero buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (EmbarquePasajero) super.findById(EmbarquePasajero.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EmbarquePasajeroDAO#guardar(com.tepsa.sisvyr.model.bean.EmbarquePasajero)
	 */
	@Override
	public void guardar(EmbarquePasajero embarquePasajero) {
		// TODO Auto-generated method stub
		super.save(embarquePasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.EmbarquePasajeroDAO#actualizar(com.tepsa.sisvyr.model.bean.EmbarquePasajero)
	 */
	@Override
	public void actualizar(EmbarquePasajero embarquePasajero) {
		// TODO Auto-generated method stub
		super.update(embarquePasajero);
	}

}
