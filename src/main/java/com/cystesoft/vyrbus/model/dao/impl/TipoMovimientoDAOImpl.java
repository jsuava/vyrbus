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

import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.dao.TipoMovimientoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoMovimientoDAOImpl extends GenericDAOImpl implements TipoMovimientoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override	public ArrayList<TipoMovimiento> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoMovimiento>) super.findByEstadoRegistro(TipoMovimiento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoMovimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoMovimiento>) super.findByX(TipoMovimiento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoMovimiento buscarPorId(Long id) {
		return (TipoMovimiento) super.findById(TipoMovimiento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#guardar(com.tepsa.sisvyr.domain.CondicionVenta)
	 */
	@Override
	public void guardar(TipoMovimiento condicionVenta) {
		super.save(condicionVenta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#actualizar(com.tepsa.sisvyr.domain.CondicionVenta)
	 */
	@Override
	public void actualizar(TipoMovimiento condicionVenta) {
		super.update(condicionVenta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CondicionVentaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoMovimiento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMovimientoDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<TipoMovimiento> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return (List<TipoMovimiento>)super.findByX(TipoMovimiento.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}