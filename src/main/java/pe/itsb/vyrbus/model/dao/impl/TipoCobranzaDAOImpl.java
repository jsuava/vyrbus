package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.dao.TipoCobranzaDAO;

/**
 *
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class TipoCobranzaDAOImpl extends GenericDAOImpl implements TipoCobranzaDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoCobranza> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<TipoCobranza>) super.findByEstadoRegistro(TipoCobranza.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoCobranza> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<TipoCobranza>)super.findByX(TipoCobranza.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoCobranza buscarPorId(Long id) {
		return (TipoCobranza)super.findById(TipoCobranza.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#guardar(com.tepsa.sisvyr.model.bean.TipoCobranza)
	 */
	@Override
	public void guardar(TipoCobranza tipoCobranza) {
		super.save(tipoCobranza);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoCobranza)
	 */
	@Override
	public void actualizar(TipoCobranza tipoCobranza) {
		super.update(tipoCobranza);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoCobranzaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(TipoCobranza.class, id);
	}


}
