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

import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.dao.TipoDocumentoDAO;
import com.cystesoft.vyrbus.service.business.TipoDocumentoManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoDocumentoManagerImpl implements TipoDocumentoManager {
	private TipoDocumentoDAO tipoDocumentoDAO;
	
	/**
	 * @return the tipoDocumentoDAO
	 */
	public TipoDocumentoDAO getTipoDocumentoDAO() {
		return tipoDocumentoDAO;
	}
	/**
	 * @param tipoDocumentoDAO the tipoDocumentoDAO to set
	 */
	public void setTipoDocumentoDAO(TipoDocumentoDAO tipoDocumentoDAO) {
		this.tipoDocumentoDAO = tipoDocumentoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoDocumento> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoDocumentoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoDocumento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoDocumentoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoDocumento buscarPorId(Long id) throws Exception {
		return getTipoDocumentoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#guardar(com.tepsa.sisvyr.model.bean.TipoDocumento)
	 */
	@Override
	@Transactional
	public void guardar(TipoDocumento tipoDocumento) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoDocumento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoDocumentoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoDocumento.getNombreCorto());
			List<?> resultNombreCorto = getTipoDocumentoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();
			
			getTipoDocumentoDAO().guardar(tipoDocumento);		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#actualizar(com.tepsa.sisvyr.model.bean.TipoDocumento)
	 */
	@Override
	@Transactional
	public void actualizar(TipoDocumento tipoDocumento) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", tipoDocumento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoDocumentoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				TipoDocumento otipodocumento = (TipoDocumento) resultDenominacion.get(r);
					if (!(otipodocumento.getId() == tipoDocumento.getId()))
						throw new DenominacionDuplicadaException();
				}
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoDocumento.getNombreCorto());
			List<?> resultnombreCorto = getTipoDocumentoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for(int r = 0; r < resultnombreCorto.size(); r ++) {
				TipoDocumento otipoDocumentoN= (TipoDocumento) resultnombreCorto.get(r);
					if (!(otipoDocumentoN.getId() == tipoDocumento.getId()))
						throw new NombreCortoDuplicadoException();
				}		
		
			getTipoDocumentoDAO().actualizar(tipoDocumento);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoDocumentoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoDocumentoDAO().inactivar(id);
	}

}
