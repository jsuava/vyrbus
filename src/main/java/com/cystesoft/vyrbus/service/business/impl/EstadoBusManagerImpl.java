/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 27/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.EstadoBus;
import com.cystesoft.vyrbus.model.dao.EstadoBusDAO;
import com.cystesoft.vyrbus.service.business.EstadoBusManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class EstadoBusManagerImpl implements EstadoBusManager {
	private EstadoBusDAO estadoBusDAO;
	
	/**
	 * @return the estadoBusDAO
	 */
	public EstadoBusDAO getEstadoBusDAO() {
		return estadoBusDAO;
	}
	/**
	 * @param estadoBusDAO the estadoBusDAO to set
	 */
	public void setEstadoBusDAO(EstadoBusDAO estadoBusDAO) {
		this.estadoBusDAO = estadoBusDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getEstadoBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getEstadoBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public EstadoBus buscarPorId(Long id) throws Exception {
		return getEstadoBusDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#guardar(com.tepsa.sisvyr.model.bean.EstadoBus)
	 */
	@Override
	@Transactional
	public void guardar(EstadoBus estadoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", estadoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getEstadoBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
						
			getEstadoBusDAO().guardar(estadoBus);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#actualizar(com.tepsa.sisvyr.model.bean.EstadoBus)
	 */
	@Override
	@Transactional
	public void actualizar(EstadoBus estadoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", estadoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getEstadoBusDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				EstadoBus oestadoBus = (EstadoBus) resultDenominacion.get(r);
					if (!(oestadoBus.getId() == estadoBus.getId()))
						throw new DenominacionDuplicadaException();
				}
				
			getEstadoBusDAO().actualizar(estadoBus);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoBusManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getEstadoBusDAO().inactivar(id);
	}

}
