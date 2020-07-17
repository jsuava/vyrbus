package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaLlegadaDAO;
import com.cystesoft.vyrbus.service.business.ItinerarioAgenciaLlegadaManager;

public class ItinerarioAgenciaLlegadaManagerImpl implements ItinerarioAgenciaLlegadaManager {

	private ItinerarioAgenciaLlegadaDAO itinerarioAgenciaLlegadaDAO;
	
	/**
	 * @return the itinerarioAgenciaLlegadaDAO
	 */
	public ItinerarioAgenciaLlegadaDAO getItinerarioAgenciaLlegadaDAO() {
		return itinerarioAgenciaLlegadaDAO;
	}
	/**
	 * @param itinerarioAgenciaLlegadaDAO the itinerarioAgenciaLlegadaDAO to set
	 */
	public void setItinerarioAgenciaLlegadaDAO(ItinerarioAgenciaLlegadaDAO itinerarioAgenciaLlegadaDAO) {
		this.itinerarioAgenciaLlegadaDAO = itinerarioAgenciaLlegadaDAO;
	}
	
	@Override
	@Transactional
	public void guardar(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada)throws Exception {
		getItinerarioAgenciaLlegadaDAO().save(itinerarioAgenciaLlegada);
		
	}

	@Override
	@Transactional
	public void actualizar(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada)throws Exception {
		getItinerarioAgenciaLlegadaDAO().update(itinerarioAgenciaLlegada);
		
	}

	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<ItinerarioAgenciaLlegada> buscarPorX(
			TreeMap<String, Object> criteriosBusqueda,
			List<String> criteriosOrdenar) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItinerarioAgenciaLlegada buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Long idItinerario) throws Exception {
		getItinerarioAgenciaLlegadaDAO().delete(idItinerario);
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.ItinerarioAgenciaLlegadaManager#buscarAgenciasLlegada(java.lang.Long, java.lang.String, java.lang.String)
	 */
	public List<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado, String strLocalidad)throws Exception{
		return getItinerarioAgenciaLlegadaDAO().buscarAgenciasLlegada(idItinerario, estado, strLocalidad);
	}
}
