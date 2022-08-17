/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: JA
 * Fecha		: 13/08/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Bus;
import com.cystesoft.vyrbus.model.dao.BusDAO;

/**
 *
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class BusDAOImpl extends GenericDAOImpl implements BusDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Bus> buscarPorEstadoRegistro (String estado, String criterioOrden){
		return (ArrayList<Bus>) super.findByEstadoRegistro(Bus.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Bus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Bus>) super.findByX(Bus.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Bus buscarPorId(Long id) {
		return (Bus) super.findById(Bus.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#guardar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void guardar(Bus bus) {
		super.save(bus);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#actualizar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void actualizar(Bus bus) {
		super.update(bus);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#innactivar(long)
	 */
	@Override
	public void inactivar(long id) {
		super.inactivate(Bus.class, id);
	}

}
