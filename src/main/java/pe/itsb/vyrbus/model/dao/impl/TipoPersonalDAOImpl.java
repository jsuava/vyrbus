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

import pe.itsb.vyrbus.model.bean.TipoPersonal;
import pe.itsb.vyrbus.model.dao.TipoPersonalDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class TipoPersonalDAOImpl extends GenericDAOImpl implements TipoPersonalDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TipoPersonal> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<TipoPersonal>) super.findByEstadoRegistro(TipoPersonal.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<TipoPersonal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<TipoPersonal>) super.findByX(TipoPersonal.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoPersonal buscarPorId(Long id) {
		return (TipoPersonal) super.findById(TipoPersonal.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#guardar(com.tepsa.sisvyr.domain.TipoPersonal)
	 */
	@Override
	public void guardar(TipoPersonal tipoPersonal) {
		super.save(tipoPersonal);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#actualizar(com.tepsa.sisvyr.domain.TipoPersonal)
	 */
	@Override
	public void actualizar(TipoPersonal tipoPersonal) {
		super.update(tipoPersonal);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.TipoPersonalDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoPersonal.class, id);
	}
}