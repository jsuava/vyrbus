/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 07/07/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.TipoAsiento;

/**
 *
 * @author Jos� Sullo Avalos
 * @since JDK1.6
 */
public interface MapaBusDAO extends GenericDAO {
	/**
	 * Busca los objetos que conforman el mapa del bus de acuerdo al servicio enviado como parametro.
	 * @param idServicio	: Identificador del servicio.
	 * @param estado		: Estado de los registros a buscar Activos o Inactivos
	 * @return Lista del tipo MapaBus.
	 * @throws Exception
	 */
	public List<MapaBus> buscarMapaBus(Integer idServicio, String estado)throws Exception;
	/**
	 * Busca los objetos que conforman el mapa del bus de acuerdo al servicio enviado como parametro.
	 * @param idServicio	: Identificador del servicio.
	 * @param estado		: Estado de los registros a buscar Activos o Inactivos
	 * @return Lista del tipo MapaBus.
	 * @throws Exception
	 */
	public List<MapaBus> buscarMapaBusHorizontal(Integer idServicio, String estado)throws Exception;
	/**
	 * Busca los servicios que ya tiene asignado un mapa de bus.
	 * @return Lista con los servicios.
	 * @throws Exception
	 */
	public List<MapaBus> buscarServiciosWithMapa()throws Exception;
	/**
	 * Elimina el mapa asociado a un servicio.
	 * @param idServicio	: Identificador del servicio
	 * @return -1=Fallo 1=Exito
	 * @throws Exception
	 */
	public int deleteMapaBus(Integer idServicio)throws Exception;
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 * @throws Exception
	 */
	public ArrayList<MapaBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda de los tipos de asientos del servicio
	 * @param servicioId : Identificador del servicio.
	 * @return Lista con los tipos de asientos del servicio
	 * @throws Exception
	 */
	public List<TipoAsiento> buscarTipoAsientoByServicio(Integer servicioId)throws Exception;
}
