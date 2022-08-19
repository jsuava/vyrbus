/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 27/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TipoFlota;
import com.cystesoft.vyrbus.model.dao.TipoFlotaDAO;
import com.cystesoft.vyrbus.service.business.TipoFlotaManager;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class TipoFlotaManagerImpl implements TipoFlotaManager {
	private TipoFlotaDAO tipoFlotaDAO;

	/**
	 * @return the tipoFlotaDAO
	 */
	public TipoFlotaDAO getTipoFlotaDAO() {
		return tipoFlotaDAO;
	}
	/**
	 * @param tipoFlotaDAO the tipoFlotaDAO to set
	 */
	public void setTipoFlotaDAO(TipoFlotaDAO tipoFlotaDAO) {
		this.tipoFlotaDAO = tipoFlotaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoFlota> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getTipoFlotaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getTipoFlotaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public TipoFlota buscarPorId(Long id) throws Exception {
		return getTipoFlotaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#guardar(com.tepsa.sisvyr.model.bean.TipoFlota)
	 */
	@Override
	@Transactional
	public void guardar(TipoFlota tipoFlota) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoFlota.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoFlota.getNombreCorto());
			List<?> resultnombreCorto = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultnombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();

			criteriosBusqueda.remove("denominacion"); criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("codigo", tipoFlota.getCodigo());
			List<?> resultcodigo = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultcodigo.size()>0)
				throw new CodigoDuplicadoException();

			getTipoFlotaDAO().guardar(tipoFlota);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(CodigoDuplicadoException cdex){
			throw new CodigoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#actualizar(com.tepsa.sisvyr.model.bean.TipoFlota)
	 */
	@Override
	@Transactional
	public void actualizar(TipoFlota tipoFlota) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", tipoFlota.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				TipoFlota otipoFlota = (TipoFlota) element;
					if (!(otipoFlota.getId() == tipoFlota.getId()))
						throw new DenominacionDuplicadaException();
				}

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", tipoFlota.getNombreCorto());
			List<?> resultnombreCorto = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for (Object element : resultnombreCorto) {
				TipoFlota oTipoFlota= (TipoFlota) element;
					if (!(oTipoFlota.getId() == tipoFlota.getId()))
						throw new NombreCortoDuplicadoException();
				}

			criteriosBusqueda.remove("denominacion"); criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("codigo", tipoFlota.getCodigo());
			List<?> resultcodigo = getTipoFlotaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Código*/
			for (Object element : resultcodigo) {
				TipoFlota oTipoFlota= (TipoFlota) element;
					if (!(oTipoFlota.getId() == tipoFlota.getId()))
						throw new CodigoDuplicadoException();
				}

			getTipoFlotaDAO().actualizar(tipoFlota);

		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(CodigoDuplicadoException cdex){
			throw new CodigoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TipoFlotaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getTipoFlotaDAO().inactivar(id);
	}

}
