/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 27/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.dao.LocalidadDAO;
import pe.itsb.vyrbus.service.business.LocalidadManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class LocalidadManagerImpl implements LocalidadManager {
	private LocalidadDAO localidadDAO;

	/**
	 * @return the localidadDAO
	 */
	public LocalidadDAO getLocalidadDAO() {
		return localidadDAO;
	}
	/**
	 * @param localidadDAO the localidadDAO to set
	 */
	public void setLocalidadDAO(LocalidadDAO localidadDAO) {
		this.localidadDAO = localidadDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Localidad> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getLocalidadDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Localidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getLocalidadDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Localidad buscarPorId(Long id) throws Exception {
		return getLocalidadDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#guardar(com.tepsa.sisvyr.model.bean.Localidad)
	 */
	@Override
	@Transactional
	public void guardar(Localidad localidad) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", localidad.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getLocalidadDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de una Ruta*/
			if(result.size()>0)
				throw new DenominacionDuplicadaException();

			getLocalidadDAO().guardar(localidad);

		}catch (DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#actualizar(com.tepsa.sisvyr.model.bean.Localidad)
	 */
	@Override
	@Transactional
	public void actualizar(Localidad localidad) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", localidad.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getLocalidadDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : result) {
				Localidad olocaLocalidad = (Localidad) element;
					if (!(olocaLocalidad.getId() == localidad.getId()))
						throw new DenominacionDuplicadaException();
				}

			getLocalidadDAO().actualizar(localidad);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LocalidadManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getLocalidadDAO().inactivar(id);
	}

}
