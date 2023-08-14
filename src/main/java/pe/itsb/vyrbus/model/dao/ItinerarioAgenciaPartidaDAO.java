package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaPartida;

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
	 * Elimina los terminales de partida de un Itinerario segun su localidad.
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
	public List<ItinerarioAgenciaPartida> buscarAgenciasPartida(Long idItinerario, String estado, String strLocalidad)throws Exception;

}
