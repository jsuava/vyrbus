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

import com.cystesoft.vyrbus.model.bean.GrupoMantenimiento;
import com.cystesoft.vyrbus.model.dao.GrupoMantenimientoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class GrupoMantenimientoDAOImpl extends GenericDAOImpl implements GrupoMantenimientoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<GrupoMantenimiento> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<GrupoMantenimiento>) super.findByEstadoRegistro(GrupoMantenimiento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<GrupoMantenimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<GrupoMantenimiento>) super.findByX(GrupoMantenimiento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public GrupoMantenimiento buscarPorId(Long id) {
		return (GrupoMantenimiento) super.findById(GrupoMantenimiento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#guardar(com.tepsa.sisvyr.domain.GrupoMantenimiento)
	 */
	@Override
	public void guardar(GrupoMantenimiento grupoMantenimiento) {
		super.save(grupoMantenimiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#actualizar(com.tepsa.sisvyr.domain.GrupoMantenimiento)
	 */
	@Override
	public void actualizar(GrupoMantenimiento grupoMantenimiento) {
		super.update(grupoMantenimiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GrupoMantenimientoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(GrupoMantenimiento.class, id);
	}
}