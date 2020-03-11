package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;

public interface ItinerarioAgenciaPartidaDAO extends GenericDAO {
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<ItinerarioAgenciaPartida> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ItinerarioAgenciaPartida buscarPorId(Long id);
	
	/**
	 * 
	 * @param idItinerario
	 * @throws Exception
	 */
	public void delete (Long idItinerario) throws Exception;

	
}
