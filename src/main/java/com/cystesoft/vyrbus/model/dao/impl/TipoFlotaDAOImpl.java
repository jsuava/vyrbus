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

import com.cystesoft.vyrbus.model.bean.TipoFlota;
import com.cystesoft.vyrbus.model.dao.TipoFlotaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoFlotaDAOImpl extends GenericDAOImpl implements TipoFlotaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoFlota> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoFlota>) super.findByEstadoRegistro(TipoFlota.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoFlota>) super.findByX(TipoFlota.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoFlota buscarPorId(Long id) {
		return (TipoFlota) super.findById(TipoFlota.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#guardar(com.tepsa.sisvyr.domain.TipoFlota)
	 */
	@Override
	public void guardar(TipoFlota tipoFlota) {
		super.save(tipoFlota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#actualizar(com.tepsa.sisvyr.domain.TipoFlota)
	 */
	@Override
	public void actualizar(TipoFlota tipoFlota) {
		super.update(tipoFlota);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFlotaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoFlota.class, id);
	}
}