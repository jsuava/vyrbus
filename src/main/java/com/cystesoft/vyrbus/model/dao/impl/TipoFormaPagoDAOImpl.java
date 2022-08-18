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

import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.dao.TipoFormaPagoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoFormaPagoDAOImpl extends GenericDAOImpl implements TipoFormaPagoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoFormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoFormaPago>) super.findByEstadoRegistro(TipoFormaPago.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoFormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoFormaPago>) super.findByX(TipoFormaPago.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoFormaPago buscarPorId(Long id) {
		return (TipoFormaPago) super.findById(TipoFormaPago.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#guardar(com.tepsa.sisvyr.domain.TipoFormaPago)
	 */
	@Override
	public void guardar(TipoFormaPago tipoFormaPago) {
		super.save(tipoFormaPago);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#actualizar(com.tepsa.sisvyr.domain.TipoFormaPago)
	 */
	@Override
	public void actualizar(TipoFormaPago tipoFormaPago) {
		super.update(tipoFormaPago);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoFormaPagoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoFormaPago.class, id);
	}
}