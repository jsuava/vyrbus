/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 23/04/2019
 * Hora			: 19:43:31
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.CuponPromocional;
import pe.itsb.vyrbus.model.dao.CuponPromocionalDAO;
import pe.itsb.vyrbus.service.business.CuponPromocionalManager;


/**
 * @author abant
 *
 */
public class CuponPromocionalManagerImpl implements CuponPromocionalManager{
	private CuponPromocionalDAO cuponPromocionalDAO;

	/**
	 * @return the codigoPromocionalDAO
	 */
	public CuponPromocionalDAO getCuponPromocionalDAO() {
		return cuponPromocionalDAO;
	}

	/**
	 * @param cuponPromocionalDAO the codigoPromocionalDAO to set
	 */
	public void setCuponPromocionalDAO(CuponPromocionalDAO cuponPromocionalDAO) {
		this.cuponPromocionalDAO = cuponPromocionalDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CodigoPromocionalManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CuponPromocional> buscarPorEstadoRegistro(String estado,
			String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return getCuponPromocionalDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CodigoPromocionalManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<CuponPromocional> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return getCuponPromocionalDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CodigoPromocionalManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public CuponPromocional buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getCuponPromocionalDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CodigoPromocionalManager#guardar(com.tepsa.sisvyr.model.bean.CodigoPromocional)
	 */
	@Transactional
	@Override
	public void guardar(CuponPromocional codigoPromocional) throws Exception {
		// TODO Auto-generated method stub
		getCuponPromocionalDAO().guardar(codigoPromocional);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CodigoPromocionalManager#actualizar(com.tepsa.sisvyr.model.bean.CodigoPromocional)
	 */
	@Override
	@Transactional
	public void actualizar(CuponPromocional codigoPromocional)
			throws Exception {
		// TODO Auto-generated method stub
		getCuponPromocionalDAO().actualizar(codigoPromocional);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CuponPromocionalManager#buscar(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<CuponPromocional> buscar(String fechaInicio, String fechaFina, String denominacion, String codigo) throws Exception {
		// TODO Auto-generated method stub
		return getCuponPromocionalDAO().buscar(fechaInicio, fechaFina, denominacion, codigo);
	}
}
