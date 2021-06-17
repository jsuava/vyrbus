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

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.dao.AgenciaDAO;
import com.cystesoft.vyrbus.service.business.AgenciaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
 */
public class AgenciaManagerImpl implements AgenciaManager {
	private AgenciaDAO agenciaDAO;
	
	/**
	 * @return the agenciaDAO
	 */
	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}
	/**
	 * @param agenciaDAO the agenciaDAO to set
	 */
	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Agencia> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception {
		return getAgenciaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Agencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception {
		return getAgenciaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Agencia buscarPorId(Long id)throws Exception {
		return getAgenciaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#guardar(com.tepsa.sisvyr.model.bean.Agencia)
	 */
	@Override
	@Transactional
	public int guardar(Agencia agencia)throws Exception {
		int result = Constantes.FAILURE;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", agencia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad de la denominación*/
			if(resultDenominacion.size()>0){
				throw new DenominacionDuplicadaException();
			}
				
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("nombreCorto", agencia.getNombreCorto());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultNombreCorto = getAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			/*Valida duplicidad del Nombre corto*/
			if (resultNombreCorto.size()>0){
				throw new NombreCortoDuplicadoException();
			}
				
			
			getAgenciaDAO().guardar(agencia);
			result = Constantes.CORRECT;
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#actualizar(com.tepsa.sisvyr.model.bean.Agencia)
	 */
	@Override
	@Transactional
	public void actualizar(Agencia agencia)throws Exception {
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("denominacion", agencia.getDenominacion());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resultDenominacion = getAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			
			criteriosBusqueda.remove("denominacion");
			criteriosBusqueda.put("nombreCorto", agencia.getNombreCorto());
			List<?> resultnombreCorto = getAgenciaDAO().buscarPorX(criteriosBusqueda, null);
			
			/*Valida duplicidad de la denominación*/
			for(int r = 0; r < resultDenominacion.size(); r ++) {
				Agencia oagencia = (Agencia) resultDenominacion.get(r);
				if (!(oagencia.getId().equals(agencia.getId())))
					throw new DenominacionDuplicadaException();
			}
			
			/*Valida duplicidad del Nombre corto*/
			for(int r = 0; r < resultnombreCorto.size(); r ++) {
				Agencia oAgencia= (Agencia) resultnombreCorto.get(r);
				if (!(oAgencia.getId().equals(agencia.getId())))
					throw new NombreCortoDuplicadoException();
			}
		
			getAgenciaDAO().actualizar(agencia);
		
		}catch (DenominacionDuplicadaException rsdex){
			throw new DenominacionDuplicadaException();
		}catch(NombreCortoDuplicadoException rdnex){
			throw new NombreCortoDuplicadoException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getAgenciaDAO().inactivar(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarAgenciaByRucClienteCredito(java.lang.String)
	 */
	@Override
	public Agencia buscarAgenciaByRucClienteCredito(String ruc)throws Exception{
		return getAgenciaDAO().buscarAgenciaByRucClienteCredito(ruc);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorX(java.lang.String, java.lang.Object[], java.util.List, java.lang.String)
	 */
	@Override
	public List<Agencia> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception {
		return getAgenciaDAO().buscarPorX(campo, criterios, criteriosOrdenar, estadoRegistro);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarAgenciaComprobantesSinBoleto(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<Agencia> buscarAgenciaComprobantesSinBoleto(Integer idTipoComprobante, String fechaPartida)throws Exception{
		return getAgenciaDAO().buscarAgenciaComprobantesSinBoleto(idTipoComprobante, fechaPartida);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#bucarAgenciasByDetalleCorporativo()
	 */
	@Override
	public List<Agencia> buscarAgenciasByDetalleCorporativo() throws Exception {
		// TODO Auto-generated method stub
		return getAgenciaDAO().buscarAgenciasByDetalleCorporativo();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.AgenciaManager#buscarAgenciaByLocalidad(java.lang.String)
	 */
	@Override
	public List<Agencia>buscarAgenciaByLocalidad(String localidades)throws Exception{
		return getAgenciaDAO().buscarAgenciaByLocalidad(localidades);
	}
	
}
