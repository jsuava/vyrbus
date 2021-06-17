package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaPartidaDAO;
import com.cystesoft.vyrbus.service.business.ItinerarioAgenciaPartidaManager;

public class ItinerarioAgenciaPartidaManagerImpl implements ItinerarioAgenciaPartidaManager {
	private ItinerarioAgenciaPartidaDAO itinerarioAgenciaPartidaDAO;
	
	/**
	 * @return the itinerarioAgenciaPartidaDAO
	 */
	public ItinerarioAgenciaPartidaDAO getItinerarioAgenciaPartidaDAO() {
		return itinerarioAgenciaPartidaDAO;
	}
	/**
	 * @param itinerarioAgenciaPartidaDAO the itinerarioAgenciaPartidaDAO to set
	 */
	public void setItinerarioAgenciaPartidaDAO (ItinerarioAgenciaPartidaDAO itinerarioAgenciaPartidaDAO) {
		this.itinerarioAgenciaPartidaDAO = itinerarioAgenciaPartidaDAO;
	}

	
	@Override
	@Transactional
	public void guardar(ItinerarioAgenciaPartida itinerarioAgenciaPartida)throws Exception {
		getItinerarioAgenciaPartidaDAO().save(itinerarioAgenciaPartida);
	}

	@Override
	@Transactional
	public void actualizar(ItinerarioAgenciaPartida itinerarioAgenciaPartida)throws Exception {
		getItinerarioAgenciaPartidaDAO().update(itinerarioAgenciaPartida);
		
	}

	@Override
	@Transactional
	public void inactivar(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<ItinerarioAgenciaPartida> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		return getItinerarioAgenciaPartidaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}
	@Override
	public ItinerarioAgenciaPartida buscarPorId(Long id) {
		return getItinerarioAgenciaPartidaDAO().buscarPorId(id);
	}
	@Override
	@Transactional
	public void delete(Long idItinerario, Integer idLocalidad) throws Exception {
		getItinerarioAgenciaPartidaDAO().delete(idItinerario, idLocalidad);
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.ItinerarioAgenciaPartidaManager#buscarAgenciasPartida(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ItinerarioAgenciaPartida> buscarAgenciasPartida(Long idItinerario, String estado, String strLocalidad)throws Exception{
		return getItinerarioAgenciaPartidaDAO().buscarAgenciasPartida(idItinerario, estado, strLocalidad);
	}

}
