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

import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.dao.OperadorTarjetaCreditoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class OperadorTarjetaCreditoDAOImpl extends GenericDAOImpl implements OperadorTarjetaCreditoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<OperadorTarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<OperadorTarjetaCredito>) super.findByEstadoRegistro(OperadorTarjetaCredito.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<OperadorTarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<OperadorTarjetaCredito>) super.findByX(OperadorTarjetaCredito.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public OperadorTarjetaCredito buscarPorId(Long id) {
		return (OperadorTarjetaCredito) super.findById(OperadorTarjetaCredito.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#guardar(com.tepsa.sisvyr.domain.OperadorTarjetaCredito)
	 */
	@Override
	public void guardar(OperadorTarjetaCredito operadorTarjetaCredito) {
		super.save(operadorTarjetaCredito);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#actualizar(com.tepsa.sisvyr.domain.OperadorTarjetaCredito)
	 */
	@Override
	public void actualizar(OperadorTarjetaCredito operadorTarjetaCredito) {
		super.update(operadorTarjetaCredito);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.OperadorTarjetaCreditoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(OperadorTarjetaCredito.class, id);
	}
}