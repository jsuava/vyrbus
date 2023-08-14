package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.MapaBus;

public interface MapaBusManager {
	/**
	 * Busca los objetos que conforman el mapa del bus de acuerdo al servicio enviado como parametro.
	 * @param idServicio	: Identificador del servicio.
	 * @param estado		: Estado de los registros a buscar Activos o Inactivos
	 * @return Lista del tipo MapaBus.
	 * @throws Exception
	 */
	public List<MapaBus> buscarMapaBus(Integer idServicio, String estado)throws Exception;
	/**
	 * Busca los servicios que ya tiene asignado un mapa de bus.
	 * @return Lista con los servicios.
	 * @throws Exception
	 */
	public List<MapaBus> buscarServiciosWithMapa()throws Exception;
	/**
	 * Guarda el mapa del bus
	 * @param lstMapaBus	: Lista con todos los objetos del mapa.
	 * @return -1=Fallo, 1=Exitoso
	 * @throws Exception
	 */
	public int guardarMapaBus(List<MapaBus> lstMapaBus)throws Exception;
	/**
	 * Busca los objetos que conforman el mapa del bus de acuerdo al servicio enviado como parametro.
	 * @param idServicio	: Identificador del servicio.
	 * @param estado		: Estado de los registros a buscar Activos o Inactivos
	 * @return Lista del tipo MapaBus.
	 * @throws Exception
	 */
	public List<MapaBus> buscarMapaBusHorizontal(Integer idServicio, String estado)throws Exception;

	public ArrayList<MapaBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
}
