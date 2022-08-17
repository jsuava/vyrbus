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

import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.dao.RutaDAO;
import com.cystesoft.vyrbus.service.business.RutaManager;
import com.cystesoft.vyrbus.service.exceptions.RutaDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class RutaManagerImpl implements RutaManager {
	private RutaDAO rutaDAO;

	/**
	 * @return the rutaDAO
	 */
	public RutaDAO getRutaDAO() {
		return rutaDAO;
	}
	/**
	 * @param rutaDAO the rutaDAO to set
	 */
	public void setRutaDAO(RutaDAO rutaDAO) {
		this.rutaDAO = rutaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Ruta> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getRutaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Ruta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getRutaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Ruta buscarPorId(Long id) throws Exception {
		return getRutaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#guardar(com.tepsa.sisvyr.model.bean.Ruta)
	 */
	@Override
	@Transactional
	public void guardar(Ruta ruta) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("localidadOrigen", ruta.getLocalidadOrigen());
			criteriosBusqueda.put("localidadDestino", ruta.getLocalidadDestino());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultRutaDuplicada = getRutaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de una Ruta*/
			if(resultRutaDuplicada.size()>0)
				throw new RutaDuplicadaException();

			getRutaDAO().guardar(ruta);

		}catch (RutaDuplicadaException rdex){
			throw new RutaDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#actualizar(com.tepsa.sisvyr.model.bean.Ruta)
	 */
	@Override
	@Transactional
	public void actualizar(Ruta ruta) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("localidadOrigen", ruta.getLocalidadOrigen());
			criteriosBusqueda.put("localidadDestino", ruta.getLocalidadDestino());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> result = getRutaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Ruc*/
			for (Object element : result) {
				Ruta oruta = (Ruta) element;
					if (!(oruta.getId().intValue() == ruta.getId().intValue()))
						throw new RutaDuplicadaException();
				}

			getRutaDAO().actualizar(ruta);

		}catch (RutaDuplicadaException rsdex){
			throw new RutaDuplicadaException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getRutaDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RutaManager#buscarRutasByPromocion(java.lang.String, java.lang.String[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Ruta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return getRutaDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}

}
