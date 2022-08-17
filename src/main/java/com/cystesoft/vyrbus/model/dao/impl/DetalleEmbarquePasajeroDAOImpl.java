/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:51:07
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleEmbarquePasajero;
import com.cystesoft.vyrbus.model.dao.DetalleEmbarquePasajeroDAO;

/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class DetalleEmbarquePasajeroDAOImpl extends GenericDAOImpl implements DetalleEmbarquePasajeroDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleEmbarquePasajeroDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<DetalleEmbarquePasajero> buscarPorEstadoRegistro(
			String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEmbarquePasajero>) super.findByEstadoRegistro(DetalleEmbarquePasajero.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleEmbarquePasajeroDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleEmbarquePasajero> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleEmbarquePasajero>) super.findByX(DetalleEmbarquePasajero.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleEmbarquePasajeroDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleEmbarquePasajero buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (DetalleEmbarquePasajero) super.findById(DetalleEmbarquePasajero.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleEmbarquePasajeroDAO#guardar(com.tepsa.sisvyr.model.bean.DetalleEmbarquePasajero)
	 */
	@Override
	public void guardar(DetalleEmbarquePasajero detalleEmbarquePasajero) {
		// TODO Auto-generated method stub
		super.save(detalleEmbarquePasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleEmbarquePasajeroDAO#actualizar(com.tepsa.sisvyr.model.bean.DetalleEmbarquePasajero)
	 */
	@Override
	public void actualizar(DetalleEmbarquePasajero detalleEmbarquePasajero) {
		// TODO Auto-generated method stub
		super.update(detalleEmbarquePasajero);
	}

}
