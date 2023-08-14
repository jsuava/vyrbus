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

import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.dao.CanalVentaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class CanalVentaDAOImpl extends GenericDAOImpl implements CanalVentaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<CanalVenta> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<CanalVenta>) super.findByEstadoRegistro(CanalVenta.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<CanalVenta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<CanalVenta>) super.findByX(CanalVenta.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public CanalVenta buscarPorId(Long id) {
		return (CanalVenta) super.findById(CanalVenta.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#guardar(com.tepsa.sisvyr.domain.CanalVenta)
	 */
	@Override
	public void guardar(CanalVenta canalVenta) {
		super.save(canalVenta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#actualizar(com.tepsa.sisvyr.domain.CanalVenta)
	 */
	@Override
	public void actualizar(CanalVenta canalVenta) {
		super.update(canalVenta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.CanalVentaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(CanalVenta.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CanalVentaDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<CanalVenta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return (List<CanalVenta>) super.findByX(CanalVenta.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}