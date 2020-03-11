/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.dao.LocalidadDAO;


/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class LocalidadDAOImpl extends GenericDAOImpl implements LocalidadDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Localidad> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Localidad>) super.findByEstadoRegistro(Localidad.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Localidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Localidad>) super.findByX(Localidad.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Localidad buscarPorId(Long id) {
		return (Localidad) super.findById(Localidad.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#guardar(com.tepsa.sisvyr.domain.Localidad)
	 */
	@Override
	public void guardar(Localidad localidad) {
		super.save(localidad);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#actualizar(com.tepsa.sisvyr.domain.Localidad)
	 */
	@Override
	public void actualizar(Localidad localidad) {
		super.update(localidad);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.LocalidadDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Localidad.class, id);
	}
}