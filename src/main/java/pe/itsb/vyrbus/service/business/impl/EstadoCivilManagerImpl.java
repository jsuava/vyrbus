/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.EstadoCivil;
import pe.itsb.vyrbus.model.dao.EstadoCivilDAO;
import pe.itsb.vyrbus.service.business.EstadoCivilManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class EstadoCivilManagerImpl implements EstadoCivilManager {
	private EstadoCivilDAO estadoCivilDAO;

	/**
	 * @return the estadoCivilDAO
	 */
	public EstadoCivilDAO getEstadoCivilDAO() {
		return estadoCivilDAO;
	}
	/**
	 * @param estadoCivilDAO the estadoCivilDAO to set
	 */
	public void setEstadoCivilDAO(EstadoCivilDAO estadoCivilDAO) {
		this.estadoCivilDAO = estadoCivilDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoCivil> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getEstadoCivilDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoCivil> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getEstadoCivilDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public EstadoCivil buscarPorId(Long id) throws Exception {
		return getEstadoCivilDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#guardar(com.tepsa.sisvyr.model.bean.EstadoCivil)
	 */
	@Override
	@Transactional
	public void guardar(EstadoCivil estadoCivil) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", estadoCivil.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getEstadoCivilDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getEstadoCivilDAO().guardar(estadoCivil);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#actualizar(com.tepsa.sisvyr.model.bean.EstadoCivil)
	 */
	@Override
	@Transactional
	public void actualizar(EstadoCivil estadoCivil) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", estadoCivil.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getEstadoCivilDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				EstadoCivil oestadoCivil = (EstadoCivil) element;
					if (!(oestadoCivil.getId() == estadoCivil.getId()))
						throw new DenominacionDuplicadaException();
				}

			getEstadoCivilDAO().actualizar(estadoCivil);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoCivilManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getEstadoCivilDAO().inactivar(id);
	}

}
