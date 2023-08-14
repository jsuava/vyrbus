/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementación de métodos que permiten el acceso al modelo.
 * Autor		: José Avalos
 * Fecha		: 28/09/2012
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.EstadoDocumentoBus;
import pe.itsb.vyrbus.model.dao.EstadoDocumentoBusDAO;
import pe.itsb.vyrbus.service.business.EstadoDocumentoBusManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class EstadoDocumentoBusManagerImpl implements EstadoDocumentoBusManager {
	private EstadoDocumentoBusDAO estadoDocumentoBusDAO;

	/**
	 * @return the estadoDocumentoBusDAO
	 */
	public EstadoDocumentoBusDAO getEstadoDocumentoBusDAO() {
		return estadoDocumentoBusDAO;
	}
	/**
	 * @param estadoDocumentoBusDAO the estadoDocumentoBusDAO to set
	 */
	public void setEstadoDocumentoBusDAO(EstadoDocumentoBusDAO estadoDocumentoBusDAO) {
		this.estadoDocumentoBusDAO = estadoDocumentoBusDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoDocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getEstadoDocumentoBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<EstadoDocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getEstadoDocumentoBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public EstadoDocumentoBus buscarPorId(Long id) throws Exception {
		return getEstadoDocumentoBusDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#guardar(com.tepsa.sisvyr.model.bean.EstadoDocumentoBus)
	 */
	@Override
	@Transactional
	public void guardar(EstadoDocumentoBus estadoDocumentoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", estadoDocumentoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getEstadoDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			if(result.size()>0)
				throw new DenominacionDuplicadaException();

			getEstadoDocumentoBusDAO().guardar(estadoDocumentoBus);

		}catch (DenominacionDuplicadaException ndex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#actualizar(com.tepsa.sisvyr.model.bean.EstadoDocumentoBus)
	 */
	@Override
	@Transactional
	public void actualizar(EstadoDocumentoBus estadoDocumentoBus) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", estadoDocumentoBus.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getEstadoDocumentoBusDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominacion*/
			for (Object element : result) {
				EstadoDocumentoBus oEstadoDocumentoBus = (EstadoDocumentoBus) element;
					if (!(oEstadoDocumentoBus.getId() == estadoDocumentoBus.getId()))
						throw new DenominacionDuplicadaException();
			}

			getEstadoDocumentoBusDAO().actualizar(estadoDocumentoBus);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.EstadoDocumentoBusManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getEstadoDocumentoBusDAO().inactivar(id);
	}

}
