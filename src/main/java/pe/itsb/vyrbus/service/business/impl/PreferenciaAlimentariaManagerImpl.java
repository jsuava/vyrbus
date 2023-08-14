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

import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.dao.PreferenciaAlimentariaDAO;
import pe.itsb.vyrbus.service.business.PreferenciaAlimentariaManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class PreferenciaAlimentariaManagerImpl implements PreferenciaAlimentariaManager {
	private PreferenciaAlimentariaDAO preferenciaAlimentariaDAO;

	/**
	 * @return the preferenciaAlimentariaDAO
	 */
	public PreferenciaAlimentariaDAO getPreferenciaAlimentariaDAO() {
		return preferenciaAlimentariaDAO;
	}
	/**
	 * @param preferenciaAlimentariaDAO the preferenciaAlimentariaDAO to set
	 */
	public void setPreferenciaAlimentariaDAO(
			PreferenciaAlimentariaDAO preferenciaAlimentariaDAO) {
		this.preferenciaAlimentariaDAO = preferenciaAlimentariaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<PreferenciaAlimentaria> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getPreferenciaAlimentariaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<PreferenciaAlimentaria> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getPreferenciaAlimentariaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public PreferenciaAlimentaria buscarPorId(Long id) throws Exception {
		return getPreferenciaAlimentariaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#guardar(com.tepsa.sisvyr.model.bean.PreferenciaAlimentaria)
	 */
	@Override
	@Transactional
	public void guardar(PreferenciaAlimentaria preferenciaAlimentaria)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", preferenciaAlimentaria.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getPreferenciaAlimentariaDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				PreferenciaAlimentaria opreferenciaAlimentaria = (PreferenciaAlimentaria) element;
					if (!(opreferenciaAlimentaria.getId() == preferenciaAlimentaria.getId()))
						throw new DenominacionDuplicadaException();
			}
			getPreferenciaAlimentariaDAO().guardar(preferenciaAlimentaria);
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#actualizar(com.tepsa.sisvyr.model.bean.PreferenciaAlimentaria)
	 */
	@Override
	@Transactional
	public void actualizar(PreferenciaAlimentaria preferenciaAlimentaria)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", preferenciaAlimentaria.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getPreferenciaAlimentariaDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				PreferenciaAlimentaria opreferenciaAlimentaria = (PreferenciaAlimentaria) element;
					if (!(opreferenciaAlimentaria.getId() == preferenciaAlimentaria.getId()))
						throw new DenominacionDuplicadaException();
			}
			getPreferenciaAlimentariaDAO().actualizar(preferenciaAlimentaria);
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.PreferenciaAlimentariaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getPreferenciaAlimentariaDAO().inactivar(id);
	}

}
