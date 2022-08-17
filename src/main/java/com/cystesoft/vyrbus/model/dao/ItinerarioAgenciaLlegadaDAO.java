package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;

public interface ItinerarioAgenciaLlegadaDAO extends GenericDAO {
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<ItinerarioAgenciaLlegada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public ItinerarioAgenciaLlegada buscarPorId(Long id);

	/**
	 * Elimina los terminales de llegada del Itinerario segun la localidad.
	 * @param idItinerario
	 * @param idLocalidad
	 * @throws Exception
	 */
	public void delete (Long idItinerario, Integer idLocalidad) throws Exception;
	/**
	 * Busca las agencias de partida por las localidades enviadas
	 * @param idItinerario	: Identificador del itinerario
	 * @param estado		: Estado de los registros
	 * @param strLocalidad	: Identificadores de localidad
	 * @return
	 * @throws Exception
	 */
	public List<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado, String strLocalidad)throws Exception;
}
