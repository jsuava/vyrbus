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

import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.dao.CanalVentaDAO;
import com.cystesoft.vyrbus.service.business.CanalVentaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class CanalVentaManagerImpl implements CanalVentaManager {
	private CanalVentaDAO canalVentaDAO;
	
	/**
	 * @return the canalVentaDAO
	 */
	public CanalVentaDAO getCanalVentaDAO() {
		return canalVentaDAO;
	}
	/**
	 * @param canalVentaDAO the canalVentaDAO to set
	 */
	public void setCanalVentaDAO(CanalVentaDAO canalVentaDAO) {
		this.canalVentaDAO = canalVentaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<CanalVenta> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		return getCanalVentaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<CanalVenta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception {
		return getCanalVentaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public CanalVenta buscarPorId(Long id) throws Exception {
		return getCanalVentaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#guardar(com.tepsa.sisvyr.model.bean.CanalVenta)
	 */
	@Override
	@Transactional
	public void guardar(CanalVenta canalVenta) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", canalVenta.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getCanalVentaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0)
				throw new DenominacionDuplicadaException();
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", canalVenta.getNombreCorto());
			List<?> resultNombreCorto = getCanalVentaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0)
				throw new NombreCortoDuplicadoException();
			
			getCanalVentaDAO().guardar(canalVenta);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#actualizar(com.tepsa.sisvyr.model.bean.CanalVenta)
	 */
	@Override
	@Transactional
	public void actualizar(CanalVenta canalVenta) throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", canalVenta.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getCanalVentaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				CanalVenta ocanalVenta = (CanalVenta) resultDenominacion.get(r);
					if (!(ocanalVenta.getId() == canalVenta.getId()))
						throw new DenominacionDuplicadaException();
				}
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", canalVenta.getNombreCorto());
			List<?> resultnombreCorto = getCanalVentaDAO().buscarPorX(criteriosBusqueda, null);
			
			/*Valida duplicidad del Nombre corto*/
			for(int r = 0; r < resultnombreCorto.size(); r ++) {
				CanalVenta ocanalVenta = (CanalVenta) resultnombreCorto.get(r);
					if (!(ocanalVenta.getId() == canalVenta.getId()))
						throw new NombreCortoDuplicadoException();
				}		
		
			getCanalVentaDAO().actualizar(canalVenta);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		getCanalVentaDAO().inactivar(id);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CanalVentaManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<CanalVenta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return getCanalVentaDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}
	
}
