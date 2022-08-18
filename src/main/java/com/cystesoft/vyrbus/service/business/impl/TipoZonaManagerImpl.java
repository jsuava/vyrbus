/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de los métodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 28/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoZona;
import com.cystesoft.vyrbus.model.dao.TipoZonaDAO;
import com.cystesoft.vyrbus.service.business.TipoZonaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoZonaManagerImpl implements TipoZonaManager {
	private TipoZonaDAO tipoZonaDAO;

	/**
	 * @return the tipoZonaDAO
	 */
	public TipoZonaDAO getTipoZonaDAO() {
		return tipoZonaDAO;
	}
	/**
	 * @param tipoZonaDAO the tipoZonaDAO to set
	 */
	public void setTipoZonaDAO(TipoZonaDAO tipoZonaDAO) {
		this.tipoZonaDAO = tipoZonaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoZona> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoZonaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoZona> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoZonaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoZona buscarPorId(Long id) throws Exception {
		return getTipoZonaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#guardar(com.tepsa.sisvyr.model.bean.TipoZona)
	 */
	@Override
	@Transactional
	public void guardar(TipoZona tipoZona) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoZona.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoZonaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoZona.getAbreviatura());
			List<?> resultNombreCorto = getTipoZonaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();

			getTipoZonaDAO().guardar(tipoZona);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoZona)
	 */
	@Override
	@Transactional
	public void actualizar(TipoZona tipoZona) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoZona.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoZonaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoZona otipozona = (TipoZona) element;
				if (!(otipozona.getId().intValue() == tipoZona.getId().intValue()))
					throw new DenominacionDuplicadaException();
			}

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("abreviatura", tipoZona.getAbreviatura());
			List<?> resultnombreCorto = getTipoZonaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for (Object element : resultnombreCorto) {
				TipoZona oTipoZona= (TipoZona) element;
				if (!(oTipoZona.getId().intValue() == tipoZona.getId().intValue()))
					throw new NombreCortoDuplicadoException();
			}

			getTipoZonaDAO().actualizar(tipoZona);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoZonaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoZonaDAO().inactivar(id);
	}

}
