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

import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.dao.TipoAgenciaDAO;
import com.cystesoft.vyrbus.service.business.TipoAgenciaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoAgenciaManagerImpl implements TipoAgenciaManager {
	private TipoAgenciaDAO tipoAgenciaDAO;
	
	/**
	 * @return the tipoAgenciaDAO
	 */
	public TipoAgenciaDAO getTipoAgenciaDAO() {
		return tipoAgenciaDAO;
	}
	/**
	 * @param tipoAgenciaDAO the tipoAgenciaDAO to set
	 */
	public void setTipoAgenciaDAO(TipoAgenciaDAO tipoAgenciaDAO) {
		this.tipoAgenciaDAO = tipoAgenciaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoAgencia> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoAgenciaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoAgencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoAgenciaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoAgencia buscarPorId(Long id) throws Exception {
		return getTipoAgenciaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#guardar(com.tepsa.sisvyr.model.bean.TipoAgencia)
	 */
	@Override
	@Transactional
	public void guardar(TipoAgencia tipoAgencia) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoAgencia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
						
			getTipoAgenciaDAO().guardar(tipoAgencia);
			
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoAgencia)
	 */
	@Override
	@Transactional
	public void actualizar(TipoAgencia tipoAgencia) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoAgencia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				TipoAgencia otipoAgencia = (TipoAgencia) resultDenominacion.get(r);
					if (!(otipoAgencia.getId() == tipoAgencia.getId()))
						throw new DenominacionDuplicadaException();
				}
			
			getTipoAgenciaDAO().actualizar(tipoAgencia);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoAgenciaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoAgenciaDAO().inactivar(id);
	}

}
