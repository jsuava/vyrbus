/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: JA
 * Fecha		: 13/08/2012
 */
package pe.itsb.vyrbus.model.dao.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Flag;
import pe.itsb.vyrbus.model.dao.FlagDAO;


/**
 *
 * @author JA
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class FlagDAOImpl extends GenericDAOImpl implements FlagDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Flag> buscarPorEstadoRegistro (String estado, String criterioOrden){
		return (ArrayList<Flag>) super.findByEstadoRegistro(Flag.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorX(java.util.TreeMap, java.lang.String)
	 */
	@Override
	public ArrayList<Flag> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Flag>) super.findByX(Flag.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Flag buscarPorId(Long id) {
		return (Flag) super.findById(Flag.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#guardar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void guardar(Flag flag) {
		super.save(flag);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.BusDAO#actualizar(com.tepsa.sisvyr.domain.Bus)
	 */
	@Override
	public void actualizar(Flag flag) {
		super.update(flag);
	}


}
