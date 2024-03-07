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

import pe.itsb.vyrbus.model.bean.AutorizadorDescuento;
import pe.itsb.vyrbus.model.dao.AutorizadorDescuentoDAO;

/**
 *
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class AutorizadorDescuentoDAOImpl extends GenericDAOImpl implements AutorizadorDescuentoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorDescuento> buscarPorEstadoRegistro (String estado, String criterioOrden){
		return (ArrayList<AutorizadorDescuento>) super.findByEstadoRegistro(AutorizadorDescuento.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorDescuento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<AutorizadorDescuento>) super.findByX(AutorizadorDescuento.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public AutorizadorDescuento buscarPorId(Long id) {
		return (AutorizadorDescuento) super.findById(AutorizadorDescuento.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#guardar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void guardar(AutorizadorDescuento bus) {
		super.save(bus);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#actualizar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void actualizar(AutorizadorDescuento bus) {
		super.update(bus);
	}

}
