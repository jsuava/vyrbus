/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 10/11/2014
 * Hora			: 14:28:17
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.VentaTramo;

/**
 * @author JABANTO
 *
 */
public interface VentaTramoDAO extends GenericDAO{

	/**
	 * Busca por el identificador de la ruta.
	 * @param idRuta	: Identificador de la ruta
	 * @param fechaPartida : Fecha de partida del Servicio.
	 * @return Lista de registros encontrados.
	 * @throws Exception
	 */
	public List<VentaTramo> buscarByRuta(Integer idRuta, String fechaPartida)throws Exception;
	/**
	 * Guarda una nueva instancia de la ventaTramo
	 * @param ventaTramo : ventaTramo
	 * @throws Exception
	 */
	public void guardar(VentaTramo ventaTramo)throws Exception;
	/**
	 * Realiza la busqueda por el itinerario y ruta.
	 * @param itinerario	: Instancia del itinerario
	 * @param ruta			: Instancia de la  ruta.
	 * @return
	 * @throws Exception
	 */
	public VentaTramo  buscarPorItinerarioRuta(Itinerario itinerario,Ruta ruta)throws Exception;
	/**
	 * Realiza la busqueda de los tramos programdos para la venta, segun los parametres enviados
	 * @param criteriosBusqueda	: Lista de criteriso para la busqueda.
	 * @param criteriosOrden	: Lista de Criterios para ordenar los resultados.
	 * @return	ListaVentaTramo
	 * @throws Exception
	 */
	public List<VentaTramo> buscarPor(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrden)throws Exception;
	/**
	 * Realiza la busqueda de los tramos programamos para la venta, segun los parametros de busqueda enviados.
	 * @param fechaInicial	: Fecha inical.
	 * @param fechaFinal	: Fecha final.
	 * @param origenId		: Identificador de la Localidad origen.
	 * @param destinoId		: Identificador de la Localidad destino.
	 * @return	Lista de tamos Programados
	 * @throws Exception
	 */
	public List<VentaTramo>buscarPor(String fechaInicial, String fechaFinal, Integer origenId,Integer destinoId)throws Exception;
	/**
	 * Anula el tramos programado
	 * @param id
	 * @throws Exception
	 */
	public void anular(Long id)throws Exception;

}
