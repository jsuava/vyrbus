/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: JA
 * Fecha		: 13/08/2012
 */
package pe.itsb.vyrbus.model.dao.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Descuento;
import pe.itsb.vyrbus.model.dao.DescuentoDAO;

/**
 *
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class DescuentoDAOImpl extends GenericDAOImpl implements DescuentoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Descuento> buscarPorEstadoRegistro (String estado, String criterioOrden){
		return (ArrayList<Descuento>) super.findByEstadoRegistro(Descuento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Descuento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Descuento>) super.findByX(Descuento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Descuento buscarPorId(Long id) {
		return (Descuento) super.findById(Descuento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#guardar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void guardar(Descuento bus) {
		super.save(bus);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#actualizar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void actualizar(Descuento bus) {
		super.update(bus);
	}

}
