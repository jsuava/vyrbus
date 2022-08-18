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

import com.cystesoft.vyrbus.model.bean.GrupoMantenimiento;
import com.cystesoft.vyrbus.model.dao.GrupoMantenimientoDAO;
import com.cystesoft.vyrbus.service.business.GrupoMantenimientoManager;
import com.cystesoft.vyrbus.service.exceptions.CodigoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class GrupoMantenimientoManagerImpl implements GrupoMantenimientoManager {
	private GrupoMantenimientoDAO grupoMantenimientoDAO;

	/**
	 * @return the grupoMantenimientoDAO
	 */
	public GrupoMantenimientoDAO getGrupoMantenimientoDAO() {
		return grupoMantenimientoDAO;
	}
	/**
	 * @param grupoMantenimientoDAO the grupoMantenimientoDAO to set
	 */
	public void setGrupoMantenimientoDAO(GrupoMantenimientoDAO grupoMantenimientoDAO) {
		this.grupoMantenimientoDAO = grupoMantenimientoDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<GrupoMantenimiento> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getGrupoMantenimientoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	@Transactional(readOnly=true)
	public ArrayList<GrupoMantenimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public GrupoMantenimiento buscarPorId(Long id) throws Exception {
		return getGrupoMantenimientoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#guardar(com.tepsa.sisvyr.model.bean.GrupoMantenimiento)
	 */
	@Override
	@Transactional
	public void guardar(GrupoMantenimiento grupoMantenimiento) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", grupoMantenimiento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", grupoMantenimiento.getNombreCorto());
			List<?> resultNombreCorto = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();

			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("codigo", grupoMantenimiento.getCodigo());
			List<?> resultCodigo = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Código*/
			if (resultCodigo.size()>0)
				throw new CodigoDuplicadoException();

			getGrupoMantenimientoDAO().guardar(grupoMantenimiento);

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
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#actualizar(com.tepsa.sisvyr.model.bean.GrupoMantenimiento)
	 */
	@Override
	@Transactional
	public void actualizar(GrupoMantenimiento grupoMantenimiento)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("denominacion", grupoMantenimiento.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for (Object element : resultDenominacion) {
				GrupoMantenimiento ogrupoMan = (GrupoMantenimiento) element;
				if (!(ogrupoMan.getId() == grupoMantenimiento.getId()))
					throw new DenominacionDuplicadaException();
			}

			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", grupoMantenimiento.getNombreCorto());
			List<?> resultNombreCorto = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			for (Object element : resultNombreCorto) {
				GrupoMantenimiento ogrupoMan = (GrupoMantenimiento) element;
				if (!(ogrupoMan.getId() == grupoMantenimiento.getId()))
					throw new NombreCortoDuplicadoException();
			}

			criteriosBusqueda.remove("denominacion");criteriosBusqueda.remove("nombreCorto");
			criteriosBusqueda.put("codigo", grupoMantenimiento.getCodigo());
			List<?> resultCodigo = getGrupoMantenimientoDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Código*/
			for (Object element : resultCodigo) {
				GrupoMantenimiento ogrupoMan = (GrupoMantenimiento) element;
				if (!(ogrupoMan.getId() == grupoMantenimiento.getId()))
					throw new CodigoDuplicadoException();
			}

			getGrupoMantenimientoDAO().actualizar(grupoMantenimiento);

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
	 * @see com.tepsa.sisvyr.service.business.GrupoMantenimientoManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getGrupoMantenimientoDAO().inactivar(id);
	}

}
