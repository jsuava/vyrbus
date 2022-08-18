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

import com.cystesoft.vyrbus.model.bean.TipoItinerario;
import com.cystesoft.vyrbus.model.dao.TipoItinerarioDAO;
import com.cystesoft.vyrbus.service.business.TipoItinerarioManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoItinerarioManagerImpl implements TipoItinerarioManager {
	private TipoItinerarioDAO tipoItinerarioDAO;

	/**
	 * @return the tipoItinerarioDAO
	 */
	public TipoItinerarioDAO getTipoItinerarioDAO() {
		return tipoItinerarioDAO;
	}
	/**
	 * @param tipoItinerarioDAO the tipoItinerarioDAO to set
	 */
	public void setTipoItinerarioDAO(TipoItinerarioDAO tipoItinerarioDAO) {
		this.tipoItinerarioDAO = tipoItinerarioDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TipoItinerario> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoItinerarioDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TipoItinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoItinerarioDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TipoItinerario buscarPorId(Long id) throws Exception {
		return getTipoItinerarioDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#guardar(com.tepsa.sisvyr.model.bean.TipoItinerario)
	 */
	@Override
	@Transactional
	public void guardar(TipoItinerario tipoItinerario) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoItinerario.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getTipoItinerarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida la denominacion. Para evitar duplicados*/
			if (result.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("nombreCorto", tipoItinerario.getNombreCorto());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultNomcorto = getTipoItinerarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida la Nombre Corto. Para evitar duplicados*/
			if (resultNomcorto.size() > 0)
				throw new NombreCortoDuplicadoException();

			getTipoItinerarioDAO().guardar(tipoItinerario);

		}catch (DenominacionDuplicadaException ddnex){
			throw new DenominacionDuplicadaException();
		}catch (NombreCortoDuplicadoException ncdex){
			throw new NombreCortoDuplicadoException();
		}


	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#actualizar(com.tepsa.sisvyr.model.bean.TipoItinerario)
	 */
	@Override
	@Transactional
	public void actualizar(TipoItinerario tipoItinerario) throws Exception {
		try {
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoItinerario.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getTipoItinerarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida la denominacion. Para evitar duplicados*/
			for (Object element : result) {
				TipoItinerario oTipoItinerario = (TipoItinerario) element;
					if (!(oTipoItinerario.getId().equals(tipoItinerario.getId())))
						throw new DenominacionDuplicadaException();
			}

			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("nombreCorto", tipoItinerario.getNombreCorto());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultNomcorto = getTipoItinerarioDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida la Nombre Corto. Para evitar duplicados*/
			for (Object element : resultNomcorto) {
				TipoItinerario oTipoItinerario = (TipoItinerario) element;
					if (!(oTipoItinerario.getId().equals(tipoItinerario.getId())))
						throw new NombreCortoDuplicadoException();
			}


			getTipoItinerarioDAO().actualizar(tipoItinerario);

		}catch (DenominacionDuplicadaException dndex){
			throw new DenominacionDuplicadaException();
		}catch (NombreCortoDuplicadoException ncdex){
			throw new NombreCortoDuplicadoException();
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoItinerarioManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoItinerarioDAO().inactivar(id);
	}

}
