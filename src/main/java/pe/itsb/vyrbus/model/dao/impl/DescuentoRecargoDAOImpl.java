/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DescuentoRecargo;
import pe.itsb.vyrbus.model.dao.DescuentoRecargoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class DescuentoRecargoDAOImpl extends GenericDAOImpl implements DescuentoRecargoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<DescuentoRecargo> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<DescuentoRecargo>) super.findByEstadoRegistro(DescuentoRecargo.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<DescuentoRecargo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<DescuentoRecargo>) super.findByX(DescuentoRecargo.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public DescuentoRecargo buscarPorId(Long id) {
		return (DescuentoRecargo) super.findById(DescuentoRecargo.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#guardar(com.tepsa.sisvyr.domain.DescuentoRecargo)
	 */
	@Override
	public void guardar(DescuentoRecargo descuentoRecargo) {
		super.save(descuentoRecargo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#actualizar(com.tepsa.sisvyr.domain.DescuentoRecargo)
	 */
	@Override
	public void actualizar(DescuentoRecargo descuentoRecargo) {
		super.update(descuentoRecargo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.DescuentoRecargoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(DescuentoRecargo.class, id);
	}
}