/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 12:47:38
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.dao.TipoMonedaDAO;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class TipoMonedaDAOImpl extends GenericDAOImpl implements TipoMonedaDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMonedaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoMoneda> buscarPorEstadoRegistro(String estado,String criterioOrden)throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<TipoMoneda>) super.findByEstadoRegistro(TipoMoneda.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMonedaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoMoneda> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar)throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<TipoMoneda>) super.findByX(TipoMoneda.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMonedaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoMoneda buscarPorId(Long id)throws Exception {
		// TODO Auto-generated method stub
		return (TipoMoneda) super.findById(TipoMoneda.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMonedaDAO#guardar(com.tepsa.sisvyr.model.bean.TipoMoneda)
	 */
	@Override
	public void guardar(TipoMoneda tipoMoneda)throws Exception {
		// TODO Auto-generated method stub
		super.save(tipoMoneda);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TipoMonedaDAO#actualizar(com.tepsa.sisvyr.model.bean.TipoMoneda)
	 */
	@Override
	public void actualizar(TipoMoneda tipoMoneda)throws Exception {
		// TODO Auto-generated method stub
		super.update(tipoMoneda);
	}

}
