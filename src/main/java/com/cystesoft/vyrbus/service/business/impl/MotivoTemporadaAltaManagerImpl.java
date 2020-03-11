package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.MotivoTemporadaAlta;
import com.cystesoft.vyrbus.model.dao.MotivoTemporadaAltaDAO;
import com.cystesoft.vyrbus.service.business.MotivoTemporadaAltaManager;
import com.cystesoft.vyrbus.service.exceptions.DenominacionDuplicadaException;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * 
 * @author JABANTO
 *
 */
public class MotivoTemporadaAltaManagerImpl implements MotivoTemporadaAltaManager  {
	
	private MotivoTemporadaAltaDAO motivoTemporadaAltaDAO;
	/**
	 * @return the motivoTemporadaAltaDAO
	 */
	public MotivoTemporadaAltaDAO getMotivoTemporadaAltaDAO() {
		return motivoTemporadaAltaDAO;
	}

	/**
	 * @param motivoTemporadaAltaDAO the motivoTemporadaAltaDAO to set
	 */
	public void setMotivoTemporadaAltaDAO(MotivoTemporadaAltaDAO motivoTemporadaAltaDAO) {
		this.motivoTemporadaAltaDAO = motivoTemporadaAltaDAO;
	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return getMotivoTemporadaAltaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<MotivoTemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public MotivoTemporadaAlta buscarPorId(Long id) {
		return getMotivoTemporadaAltaDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#guardar(com.tepsa.sisvyr.model.bean.MotivoTemporadaAlta)
	 */
	@Transactional
	@Override
	public void guardar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception {
		try{
			/*Valida duplicidad de la denominación*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("nombreMotivo", motivoTemporadaAlta.getNombreMotivo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, null);
			if(resul.size()>0)
				throw new DenominacionDuplicadaException();
			
			getMotivoTemporadaAltaDAO().guardar(motivoTemporadaAlta);
			
		}catch (DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#actualizar(com.tepsa.sisvyr.model.bean.MotivoTemporadaAlta)
	 */
	@Transactional
	@Override
	public void actualizar(MotivoTemporadaAlta motivoTemporadaAlta) throws Exception {
		try{
			/*Valida duplicidad de la denominación*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("nombreMotivo", motivoTemporadaAlta.getNombreMotivo());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<?> resul = getMotivoTemporadaAltaDAO().buscarPorX(criteriosBusqueda, null);
			for(int r = 0; r < resul.size(); r ++) {
				MotivoTemporadaAlta oAlta = (MotivoTemporadaAlta) resul.get(r);
					if (!(oAlta.getId().equals(motivoTemporadaAlta.getId())) )
						throw new DenominacionDuplicadaException();
			}
			getMotivoTemporadaAltaDAO().actualizar(motivoTemporadaAlta);
			
		}catch(DenominacionDuplicadaException ddex){
			throw new DenominacionDuplicadaException();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.MotivoTemporadaAltaManager#inactivar(java.lang.Long)
	 */
	@Transactional
	@Override
	public void inactivar(Long id) {
		getMotivoTemporadaAltaDAO().inactivar(id);
	}

	
	

}
