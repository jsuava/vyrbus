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

import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.dao.TipoComprobanteDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoComprobanteDAOImpl extends GenericDAOImpl implements TipoComprobanteDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoComprobante> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoComprobante>) super.findByEstadoRegistro(TipoComprobante.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoComprobante> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoComprobante>) super.findByX(TipoComprobante.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoComprobante buscarPorId(Long id) {
		return (TipoComprobante) super.findById(TipoComprobante.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#guardar(com.tepsa.sisvyr.domain.TipoComprobante)
	 */
	@Override
	public void guardar(TipoComprobante tipoComprobante) {
		super.save(tipoComprobante);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#actualizar(com.tepsa.sisvyr.domain.TipoComprobante)
	 */
	@Override
	public void actualizar(TipoComprobante tipoComprobante) {
		super.update(tipoComprobante);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoComprobanteDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoComprobante.class, id);
	}
}