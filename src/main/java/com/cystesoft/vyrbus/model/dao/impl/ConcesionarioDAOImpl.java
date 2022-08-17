/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 23/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.dao.ConcesionarioDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ConcesionarioDAOImpl extends GenericDAOImpl implements ConcesionarioDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Concesionario> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Concesionario>) super.findByEstadoRegistro(Concesionario.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#buscarPorX(java.util.TreeMap)
	 */

	@Override
		public ArrayList<Concesionario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Concesionario>) super.findByX(Concesionario.class, criteriosBusqueda, criteriosOrdenar);
	}




	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Concesionario buscarPorId(Long id) {
		return (Concesionario) super.findById(Concesionario.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#guardar(com.tepsa.sisvyr.domain.Concesionario)
	 */
	@Override
	public void guardar(Concesionario concesionario) {
		super.save(concesionario);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#actualizar(com.tepsa.sisvyr.domain.Concesionario)
	 */
	@Override
	public void actualizar(Concesionario concesionario) {
		super.update(concesionario);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ConcesionarioDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Concesionario.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ConcesionarioDAO#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Concesionario> buscarPorX(String campo, Object[] criterios,List<String> criteriosOrdenar, String estadoRegistro)throws Exception {
		// TODO Auto-generated method stub
		return (List<Concesionario>) super.findByX(Concesionario.class, campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}