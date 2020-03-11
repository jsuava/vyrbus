package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.model.dao.MotivocortesiaDAO;

@SuppressWarnings("unchecked")
public class MotivocortesiaDAOImpl extends GenericDAOImpl implements MotivocortesiaDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<MotivoCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<MotivoCortesia>) super.findByEstadoRegistro(MotivoCortesia.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<MotivoCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<MotivoCortesia>) super.findByX(MotivoCortesia.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public MotivoCortesia buscarPorId(Long id) {
		return (MotivoCortesia) super.findById(MotivoCortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#guardar(com.tepsa.sisvyr.model.bean.MotivoCortesia)
	 */
	@Override
	public void guardar(MotivoCortesia motivoCortesia) {
		super.save(motivoCortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#actualizar(com.tepsa.sisvyr.model.bean.MotivoCortesia)
	 */
	@Override
	public void actualizar(MotivoCortesia motivoCortesia) {
		super.update(motivoCortesia);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MotivocorteciaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(MotivoCortesia.class, id);
		
	}
	

}
