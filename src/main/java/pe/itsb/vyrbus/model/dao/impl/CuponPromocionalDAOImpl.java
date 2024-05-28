/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 23/04/2019
 * Hora			: 19:43:03
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.CuponPromocional;
import pe.itsb.vyrbus.model.dao.CuponPromocionalDAO;


/**
 * @author abant
 *
 */
@SuppressWarnings("unchecked")
public class CuponPromocionalDAOImpl extends GenericDAOImpl implements CuponPromocionalDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CodigoPromocionalDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CuponPromocional> buscarPorEstadoRegistro(String estado,
			String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<CuponPromocional>) findByEstadoRegistro(CuponPromocional.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CodigoPromocionalDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */	
	@Override
	public ArrayList<CuponPromocional> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<CuponPromocional>) findByX(CuponPromocional.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CodigoPromocionalDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public CuponPromocional buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return (CuponPromocional) findById(CuponPromocional.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CodigoPromocionalDAO#guardar(com.tepsa.sisvyr.model.bean.CodigoPromocional)
	 */
	@Override
	public void guardar(CuponPromocional codigoPromocional) throws Exception {
		// TODO Auto-generated method stub
		save(codigoPromocional);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CodigoPromocionalDAO#actualizar(com.tepsa.sisvyr.model.bean.CodigoPromocional)
	 */
	@Override
	public void actualizar(CuponPromocional codigoPromocional)
			throws Exception {
		// TODO Auto-generated method stub
		update(codigoPromocional);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CuponPromocionalDAO#buscar(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<CuponPromocional> buscar(String fechaInicio, String fechaFinal,String denominacion, String codigo) throws Exception {
		String hql="FROM CuponPromocional WHERE estadoRegistro='A' ";
		if(fechaInicio!=null && fechaFinal!=null)
			hql += "AND fechaInicial >= '"+fechaInicio+"' AND fechaFinal <= '"+fechaFinal+"' ";
		if(denominacion!=null)
			hql += "AND denominacion LIKE '%"+denominacion+"%' ";
		if(codigo!=null)
			hql += "AND codigo LIKE '%"+codigo+"%' ";
		hql += "ORDER BY  fechaInicial ";
		
		List<CuponPromocional>result=getSession().createQuery(hql).list();
		
		
		return result;
	}

}
