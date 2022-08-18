/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.dao.SexoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class SexoDAOImpl extends GenericDAOImpl implements SexoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Sexo> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Sexo>) super.findByEstadoRegistro(Sexo.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Sexo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Sexo>) super.findByX(Sexo.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Sexo buscarPorId(Long id) {
		return (Sexo) super.findById(Sexo.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#guardar(com.tepsa.sisvyr.domain.Sexo)
	 */
	@Override
	public void guardar(Sexo sexo) {
		super.save(sexo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#actualizar(com.tepsa.sisvyr.domain.Sexo)
	 */
	@Override
	public void actualizar(Sexo sexo) {
		super.update(sexo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.SexoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Sexo.class, id);
	}
}