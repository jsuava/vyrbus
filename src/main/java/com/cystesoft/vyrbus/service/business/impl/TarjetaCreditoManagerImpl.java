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

import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.dao.TarjetaCreditoDAO;
import com.cystesoft.vyrbus.service.business.TarjetaCreditoManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TarjetaCreditoManagerImpl implements TarjetaCreditoManager {
	private TarjetaCreditoDAO tarjetaCreditoDAO;

	/**
	 * @return the tarjetaCreditoDAO
	 */
	public TarjetaCreditoDAO getTarjetaCreditoDAO() {
		return tarjetaCreditoDAO;
	}
	/**
	 * @param tarjetaCreditoDAO the tarjetaCreditoDAO to set
	 */
	public void setTarjetaCreditoDAO(TarjetaCreditoDAO tarjetaCreditoDAO) {
		this.tarjetaCreditoDAO = tarjetaCreditoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTarjetaCreditoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarjetaCredito buscarPorId(Long id) throws Exception {
		return getTarjetaCreditoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#guardar(com.tepsa.sisvyr.model.bean.TarjetaCredito)
	 */
	@Override
	@Transactional
	public void guardar(TarjetaCredito tarjetaCredito) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tarjetaCredito.getDenominacion());
			criteriosBusqueda.put("operadorTarjetaCredito", tarjetaCredito.getOperadorTarjetaCredito());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, null);

			if(result.size()>0)
				throw new DenominacionDuplicadaException();

			getTarjetaCreditoDAO().guardar(tarjetaCredito);

		}catch(DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#actualizar(com.tepsa.sisvyr.model.bean.TarjetaCredito)
	 */
	@Override
	@Transactional
	public void actualizar(TarjetaCredito tarjetaCredito) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tarjetaCredito.getDenominacion());
			criteriosBusqueda.put("operadorTarjetaCredito", tarjetaCredito.getOperadorTarjetaCredito());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getTarjetaCreditoDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad del Ruc*/
			for (Object element : result) {
				TarjetaCredito otarjetaCredito = (TarjetaCredito) element;
					if (!(otarjetaCredito.getId() == tarjetaCredito.getId()))
						throw new DenominacionDuplicadaException();
				}

			getTarjetaCreditoDAO().actualizar(tarjetaCredito);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTarjetaCreditoDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarjetaCreditoManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<TarjetaCredito> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return getTarjetaCreditoDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}
}
