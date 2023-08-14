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

import pe.itsb.vyrbus.model.bean.Nacionalidad;
import pe.itsb.vyrbus.model.dao.NacionalidadDAO;
import pe.itsb.vyrbus.service.business.NacionalidadManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class NacionalidadManagerImpl implements NacionalidadManager {
	private NacionalidadDAO nacionalidadDAO;

	/**
	 * @return the nacionalidadDAO
	 */
	public NacionalidadDAO getNacionalidadDAO() {
		return nacionalidadDAO;
	}
	/**
	 * @param nacionalidadDAO the nacionalidadDAO to set
	 */
	public void setNacionalidadDAO(NacionalidadDAO nacionalidadDAO) {
		this.nacionalidadDAO = nacionalidadDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Nacionalidad> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getNacionalidadDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<Nacionalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getNacionalidadDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Nacionalidad buscarPorId(Long id) throws Exception {
		return getNacionalidadDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#guardar(com.tepsa.sisvyr.model.bean.Nacionalidad)
	 */
	@Override
	@Transactional
	public void guardar(Nacionalidad nacionalidad) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", nacionalidad.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getNacionalidadDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getNacionalidadDAO().guardar(nacionalidad);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#actualizar(com.tepsa.sisvyr.model.bean.Nacionalidad)
	 */
	@Override
	@Transactional
	public void actualizar(Nacionalidad nacionalidad) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", nacionalidad.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getNacionalidadDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				Nacionalidad onacionalidad = (Nacionalidad) element;
					if (!(onacionalidad.getId().equals(nacionalidad.getId())))
						throw new DenominacionDuplicadaException();
				}

			getNacionalidadDAO().actualizar(nacionalidad);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.NacionalidadManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getNacionalidadDAO().inactivar(id);
	}

}
