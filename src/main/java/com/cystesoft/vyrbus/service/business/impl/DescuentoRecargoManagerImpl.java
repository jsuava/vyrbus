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

import com.cystesoft.vyrbus.model.bean.DescuentoRecargo;
import com.cystesoft.vyrbus.model.dao.DescuentoRecargoDAO;
import com.cystesoft.vyrbus.service.business.DescuentoRecargoManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class DescuentoRecargoManagerImpl implements DescuentoRecargoManager {
	private DescuentoRecargoDAO descuentoRecargoDAO;

	/**
	 * @return the descuentoRecargoDAO
	 */
	public DescuentoRecargoDAO getDescuentoRecargoDAO() {
		return descuentoRecargoDAO;
	}
	/**
	 * @param descuentoRecargoDAO the descuentoRecargoDAO to set
	 */
	public void setDescuentoRecargoDAO(DescuentoRecargoDAO descuentoRecargoDAO) {
		this.descuentoRecargoDAO = descuentoRecargoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<DescuentoRecargo> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getDescuentoRecargoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<DescuentoRecargo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getDescuentoRecargoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public DescuentoRecargo buscarPorId(Long id) throws Exception {
		return getDescuentoRecargoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#guardar(com.tepsa.sisvyr.model.bean.DescuentoRecargo)
	 */
	@Override
	@Transactional
	public void guardar(DescuentoRecargo descuentoRecargo) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", descuentoRecargo.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultRutaDuplicada = getDescuentoRecargoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad Denominacion*/
			if(resultRutaDuplicada.size()>0)
				throw new DenominacionDuplicadaException();

			getDescuentoRecargoDAO().guardar(descuentoRecargo);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#actualizar(com.tepsa.sisvyr.model.bean.DescuentoRecargo)
	 */
	@Override
	@Transactional
	public void actualizar(DescuentoRecargo descuentoRecargo) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", descuentoRecargo.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getDescuentoRecargoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				DescuentoRecargo odescuentoRecargo = (DescuentoRecargo) element;
					if (!(odescuentoRecargo.getId() == descuentoRecargo.getId()))
						throw new DenominacionDuplicadaException();
			}

			getDescuentoRecargoDAO().actualizar(descuentoRecargo);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DescuentoRecargoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getDescuentoRecargoDAO().inactivar(id);
	}

}
