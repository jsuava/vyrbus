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

import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.dao.RutaDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class RutaDAOImpl extends GenericDAOImpl implements RutaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Ruta> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Ruta>) super.findByEstadoRegistro(Ruta.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Ruta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Ruta>) super.findByX(Ruta.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Ruta buscarPorId(Long id) {
		return (Ruta) super.findById(Ruta.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#guardar(com.tepsa.sisvyr.domain.Ruta)
	 */
	@Override
	public void guardar(Ruta ruta) {
		super.save(ruta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#actualizar(com.tepsa.sisvyr.domain.Ruta)
	 */
	@Override
	public void actualizar(Ruta ruta) {
		super.update(ruta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.RutaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Ruta.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.RutaDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Ruta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return (List<Ruta>) super.findByX(Ruta.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}



}