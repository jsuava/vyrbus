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

import pe.itsb.vyrbus.model.bean.TipoPersonal;
import pe.itsb.vyrbus.model.dao.TipoPersonalDAO;
import pe.itsb.vyrbus.service.business.TipoPersonalManager;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoPersonalManagerImpl implements TipoPersonalManager {
	private TipoPersonalDAO tipoPersonalDAO;

	/**
	 * @return the tipoPersonalDAO
	 */
	public TipoPersonalDAO getTipoPersonalDAO() {
		return tipoPersonalDAO;
	}
	/**
	 * @param tipoPersonalDAO the tipoPersonalDAO to set
	 */
	public void setTipoPersonalDAO(TipoPersonalDAO tipoPersonalDAO) {
		this.tipoPersonalDAO = tipoPersonalDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoPersonal> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoPersonalDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoPersonal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoPersonalDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoPersonal buscarPorId(Long id) throws Exception {
		return getTipoPersonalDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#guardar(com.tepsa.sisvyr.model.bean.TipoPersonal)
	 */
	@Override
	@Transactional
	public void guardar(TipoPersonal tipoPersonal) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoPersonal.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoPersonalDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			getTipoPersonalDAO().guardar(tipoPersonal);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#actualizar(com.tepsa.sisvyr.model.bean.TipoPersonal)
	 */
	@Override
	@Transactional
	public void actualizar(TipoPersonal tipoPersonal) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoPersonal.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoPersonalDAO().buscarPorX(criteriosBusqueda, null);

			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoPersonal otipoPersonal = (TipoPersonal) element;
					if (!(otipoPersonal.getId() == tipoPersonal.getId()))
						throw new DenominacionDuplicadaException();
				}

			getTipoPersonalDAO().actualizar(tipoPersonal);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoPersonalManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoPersonalDAO().inactivar(id);
	}

}
