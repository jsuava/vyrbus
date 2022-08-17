package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;

public interface ItinerarioAgenciaLlegadaManager {
	/**
	 *
	 * @param criteriosBusqueda	: Array de parametros para la busqueda.
	 * @param criteriosOrdenar	: lista de parametros para el orden.
	 * @return Array con el resultado de la busqueda
	 * @throws Exception
	 */
	public ArrayList<ItinerarioAgenciaLlegada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;

	/**
	 *
	 * @param id : Identidicador unico para la busqueda.
	 * @return Agencia(s) de partida del itinerario..
	 */
	public ItinerarioAgenciaLlegada buscarPorId(Long id);
	/**
	 *
	 * @param itinerarioAgenciaLlegada
	 * @throws Exception
	 */
	public void guardar(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada)throws Exception;
	/**
	 *
	 * @param itinerarioAgenciaLlegada
	 * @throws Exception
	 */
	public void actualizar(ItinerarioAgenciaLlegada itinerarioAgenciaLlegada)throws Exception;
	/**
	 *
	 * @param id
	 * @throws Exception
	 */
	public void inactivar(Long id)throws Exception;
	/**
	 * Elimina los terminales de Llegada de un itinerario segun la localidad
	 * @param idItinerario
	 * @param idLocalidad
	 * @throws Exception
	 */
	public void delete (Long idItinerario, Integer idLocalidad) throws Exception;
	/**
	 * Busca las agencias de llegada por las localidades enviadas
	 * @param idItinerario	: Identificador del itinerario
	 * @param estado		: Estado de los registros
	 * @param strLocalidad	: Identificadores de localidad
	 * @return
	 * @throws Exception
	 */
	public List<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado, String strLocalidad)throws Exception;

}
