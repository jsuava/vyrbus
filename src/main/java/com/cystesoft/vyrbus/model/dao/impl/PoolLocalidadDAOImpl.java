/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 01/09/2016
 * Hora			: 18:20:06
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.PoolLocalidad;
import com.cystesoft.vyrbus.model.dao.PoolLocalidadDAO;



/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class PoolLocalidadDAOImpl extends GenericDAOImpl implements PoolLocalidadDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<PoolLocalidad> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<PoolLocalidad>) findByEstadoRegistro(PoolLocalidad.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<PoolLocalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<PoolLocalidad>) findByX(PoolLocalidad.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public PoolLocalidad buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (PoolLocalidad) findById(PoolLocalidad.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#guardar(com.tepsa.sisvyr.model.bean.LocalidadIntegracion)
	 */
	@Override
	public void guardar(PoolLocalidad localidadIntegracion) {
		// TODO Auto-generated method stub
		save(localidadIntegracion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#actualizar(com.tepsa.sisvyr.model.bean.LocalidadIntegracion)
	 */
	@Override
	public void actualizar(PoolLocalidad localidadIntegracion) {
		// TODO Auto-generated method stub
		update(localidadIntegracion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LocalidadIntegracionDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub
		
	}


	
}
