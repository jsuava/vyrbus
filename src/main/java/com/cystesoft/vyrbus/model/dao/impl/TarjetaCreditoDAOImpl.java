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

import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.dao.TarjetaCreditoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TarjetaCreditoDAOImpl extends GenericDAOImpl implements TarjetaCreditoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TarjetaCredito>) super.findByEstadoRegistro(TarjetaCredito.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TarjetaCredito>) super.findByX(TarjetaCredito.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarjetaCredito buscarPorId(Long id) {
		return (TarjetaCredito) super.findById(TarjetaCredito.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#guardar(com.tepsa.sisvyr.domain.TarjetaCredito)
	 */
	@Override
	public void guardar(TarjetaCredito tarjetaCredito) {
		super.save(tarjetaCredito);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#actualizar(com.tepsa.sisvyr.domain.TarjetaCredito)
	 */
	@Override
	public void actualizar(TarjetaCredito tarjetaCredito) {
		super.update(tarjetaCredito);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TarjetaCreditoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TarjetaCredito.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarjetaCreditoDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<TarjetaCredito> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return (List<TarjetaCredito>) super.findByX(TarjetaCredito.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}