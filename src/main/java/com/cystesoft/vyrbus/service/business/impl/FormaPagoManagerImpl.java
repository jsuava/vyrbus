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

import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.dao.FormaPagoDAO;
import com.cystesoft.vyrbus.service.business.FormaPagoManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class FormaPagoManagerImpl implements FormaPagoManager {
	private FormaPagoDAO formaPagoDAO;
	
	/**
	 * @return the formaPagoDAO
	 */
	public FormaPagoDAO getFormaPagoDAO() {
		return formaPagoDAO;
	}
	/**
	 * @param formaPagoDAO the formaPagoDAO to set
	 */
	public void setFormaPagoDAO(FormaPagoDAO formaPagoDAO) {
		this.formaPagoDAO = formaPagoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<FormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getFormaPagoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<FormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getFormaPagoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public FormaPago buscarPorId(Long id) throws Exception {
		return getFormaPagoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#guardar(com.tepsa.sisvyr.model.bean.FormaPago)
	 */
	@Override
	@Transactional
	public void guardar(FormaPago formaPago) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", formaPago.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getFormaPagoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
			
			getFormaPagoDAO().guardar(formaPago);
			
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#actualizar(com.tepsa.sisvyr.model.bean.FormaPago)
	 */
	@Override
	@Transactional
	public void actualizar(FormaPago formaPago) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", formaPago.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getFormaPagoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				FormaPago oformFormaPago = (FormaPago) resultDenominacion.get(r);
					if (!(oformFormaPago.getId() == formaPago.getId()))
						throw new DenominacionDuplicadaException();
				}
			
			getFormaPagoDAO().actualizar(formaPago);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.FormaPagoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getFormaPagoDAO().inactivar(id);
	}

}
