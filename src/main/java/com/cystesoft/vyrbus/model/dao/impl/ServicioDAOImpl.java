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

import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.dao.ServicioDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ServicioDAOImpl extends GenericDAOImpl implements ServicioDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Servicio> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Servicio>) super.findByEstadoRegistro(Servicio.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Servicio> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Servicio>) super.findByX(Servicio.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Servicio buscarPorId(Long id) {
		return (Servicio) super.findById(Servicio.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#guardar(com.tepsa.sisvyr.domain.Servicio)
	 */
	@Override
	public void guardar(Servicio servicio) {
		super.save(servicio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#actualizar(com.tepsa.sisvyr.domain.Servicio)
	 */
	@Override
	public void actualizar(Servicio servicio) {
		super.getSession().merge(servicio);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ServicioDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Servicio.class, id);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ServicioDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Servicio> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception{
		return (List<Servicio>) super.findByX(Servicio.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}