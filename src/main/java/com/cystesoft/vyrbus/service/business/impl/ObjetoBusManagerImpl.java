/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ObjetoBus;
import com.cystesoft.vyrbus.model.dao.ObjetoBusDAO;
import com.cystesoft.vyrbus.service.business.ObjetoBusManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class ObjetoBusManagerImpl implements ObjetoBusManager {
	private ObjetoBusDAO objetoBusDAO;
	
	/**
	 * @return the objetoBusDAO
	 */
	public ObjetoBusDAO getObjetoBusDAO() {
		return objetoBusDAO;
	}
	/**
	 * @param objetoBusDAO the objetoBusDAO to set
	 */
	public void setObjetoBusDAO(ObjetoBusDAO objetoBusDAO) {
		this.objetoBusDAO = objetoBusDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<ObjetoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getObjetoBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<ObjetoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getObjetoBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public ObjetoBus buscarPorId(Long id) throws Exception {
		return getObjetoBusDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#guardar(com.tepsa.sisvyr.model.bean.ObjetoBus)
	 */
	@Override
	@Transactional
	public void guardar(ObjetoBus objetoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", objetoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getObjetoBusDAO().buscarPorX(criteriosBusqueda, null);
			
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
			
			getObjetoBusDAO().guardar(objetoBus);
			
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#actualizar(com.tepsa.sisvyr.model.bean.ObjetoBus)
	 */
	@Override
	@Transactional
	public void actualizar(ObjetoBus objetoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", objetoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getObjetoBusDAO().buscarPorX(criteriosBusqueda, null);
			
			/*Valida duplicidad de la denominacion*/
			for(int r = 0; r < result.size(); r ++) {
				ObjetoBus oobjetoBus = (ObjetoBus) result.get(r);
				if (!(oobjetoBus.getId() == objetoBus.getId()))
					throw new DenominacionDuplicadaException();
			}
		
			getObjetoBusDAO().actualizar(objetoBus);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ObjetoBusManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getObjetoBusDAO().inactivar(id);
	}

}
