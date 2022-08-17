package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.dao.AutorizadorCortesiaDAO;

/***

* @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class AutorizadorCortesiaDAOImpl extends GenericDAOImpl implements AutorizadorCortesiaDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<AutorizadorCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<AutorizadorCortesia>) super.findByEstadoRegistro(AutorizadorCortesia.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<AutorizadorCortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<AutorizadorCortesia>)super.findByX(AutorizadorCortesia.class,criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public AutorizadorCortesia buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (AutorizadorCortesia)super.findById(AutorizadorCortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#guardar(com.tepsa.sisvyr.model.bean.AutorizadorCortesia)
	 */
	@Override
	public void guardar(AutorizadorCortesia autorizadorCortesia) {
		// TODO Auto-generated method stub
		super.save(autorizadorCortesia);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#actualizar(com.tepsa.sisvyr.model.bean.AutorizadorCortesia)
	 */
	@Override
	public void actualizar(AutorizadorCortesia autorizadorCortesia) {
		// TODO Auto-generated method stub
		super.update(autorizadorCortesia);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.AutorizadorCortesiaDAO#inactivar(long)
	 */
	@Override
	public void inactivar(long id) {
		// TODO Auto-generated method stub
		super.inactivate(AutorizadorCortesia.class, id);
	}

}
