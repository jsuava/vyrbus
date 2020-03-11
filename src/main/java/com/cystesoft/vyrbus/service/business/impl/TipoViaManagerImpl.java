/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoVia;
import com.cystesoft.vyrbus.model.dao.TipoViaDAO;
import com.cystesoft.vyrbus.service.business.TipoViaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoViaManagerImpl implements TipoViaManager {
	private TipoViaDAO tipoViaDAO;
	
	/**
	 * @return the tipoViaDAO
	 */
	public TipoViaDAO getTipoViaDAO() {
		return tipoViaDAO;
	}
	/**
	 * @param tipoViaDAO the tipoViaDAO to set
	 */
	public void setTipoViaDAO(TipoViaDAO tipoViaDAO) {
		this.tipoViaDAO = tipoViaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoVia> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoViaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoVia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoViaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoVia buscarPorId(Long id) throws Exception {
		return getTipoViaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#guardar(com.tepsa.sisvyr.model.bean.TipoVia)
	 */
	@Override
	@Transactional
	public void guardar(TipoVia tipoVia) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoVia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoViaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoVia.getAbreviatura());
			List<?> resultNombreCorto = getTipoViaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();
			
			getTipoViaDAO().guardar(tipoVia);
			
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoVia)
	 */
	@Override
	@Transactional
	public void actualizar(TipoVia tipoVia) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoVia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoViaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				TipoVia otipoVia = (TipoVia) resultDenominacion.get(r);
					if (!(otipoVia.getId() == tipoVia.getId()))
						throw new DenominacionDuplicadaException();
				}
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoVia.getAbreviatura());
			List<?> resultnombreCorto = getTipoViaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for(int r = 0; r < resultnombreCorto.size(); r ++) {
				TipoVia otipovia= (TipoVia) resultnombreCorto.get(r);
					if (!(otipovia.getId() == tipoVia.getId()))
						throw new NombreCortoDuplicadoException();
				}		
		
			getTipoViaDAO().actualizar(tipoVia);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoViaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoViaDAO().inactivar(id);
	}

}
