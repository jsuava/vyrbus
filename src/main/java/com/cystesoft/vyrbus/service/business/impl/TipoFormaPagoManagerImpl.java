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

import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.dao.TipoFormaPagoDAO;
import com.cystesoft.vyrbus.service.business.TipoFormaPagoManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoFormaPagoManagerImpl implements TipoFormaPagoManager {
	private TipoFormaPagoDAO tipoFormaPagoDAO;

	/**
	 * @return the tipoFormaPagoDAO
	 */
	public TipoFormaPagoDAO getTipoFormaPagoDAO() {
		return tipoFormaPagoDAO;
	}
	/**
	 * @param tipoFormaPagoDAO the tipoFormaPagoDAO to set
	 */
	public void setTipoFormaPagoDAO(TipoFormaPagoDAO tipoFormaPagoDAO) {
		this.tipoFormaPagoDAO = tipoFormaPagoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoFormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoFormaPagoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoFormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoFormaPagoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoFormaPago buscarPorId(Long id) throws Exception {
		return getTipoFormaPagoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#guardar(com.tepsa.sisvyr.model.bean.TipoFormaPago)
	 */
	@Override
	@Transactional
	public void guardar(TipoFormaPago tipoFormaPago) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoFormaPago.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoFormaPagoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoFormaPago.getNombreCorto());
			List<?> resultNombreCorto = getTipoFormaPagoDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();

			getTipoFormaPagoDAO().guardar(tipoFormaPago);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#actualizar(com.tepsa.sisvyr.model.bean.TipoFormaPago)
	 */
	@Override
	@Transactional
	public void actualizar(TipoFormaPago tipoFormaPago) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoFormaPago.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoFormaPagoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoFormaPago otipoFormaPago = (TipoFormaPago) element;
				if (!(otipoFormaPago.getId() == tipoFormaPago.getId()))
					throw new DenominacionDuplicadaException();
			}

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoFormaPago.getNombreCorto());
			List<?> resultnombreCorto = getTipoFormaPagoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for (Object element : resultnombreCorto) {
				TipoFormaPago otipoFormaPago= (TipoFormaPago) element;
				if (!(otipoFormaPago.getId() == tipoFormaPago.getId()))
					throw new NombreCortoDuplicadoException();
			}

			getTipoFormaPagoDAO().actualizar(tipoFormaPago);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFormaPagoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoFormaPagoDAO().inactivar(id);
	}

}
