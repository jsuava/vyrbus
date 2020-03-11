/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 11:56:40
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.HRE;

/**
 * @author JABANTO
 *
 */
public interface HREDAO extends GenericDAO {
	/**
	 * Guarda la hoja de ruta electronica.
	 * @param hre	: Nueva instancia del objeto
	 * @throws Exception
	 */
	public void guardar(HRE hre)throws Exception;
	/**
	 * Actualiza la hoja de ruta electronica
	 * @param hre	: Instancia a del objeto a actualizar
	 * @throws Exception
	 */
	public void actualizar(HRE hre)throws Exception;
	/**
	 * Busca hoja de ruta por el id
	 * @param nroHojaRuta	: Numero de Hoja de Ruta Electronica
	 * @return HRE
	 * @throws Exception
	 */
	public HRE buscarPorId(String nroHojaRuta)throws Exception;
	/**
	 * Busca HRE emintidas
	 * @param fechaInicial	: Fecha Inicial
	 * @param fechaFinal	: Fecha Final
	 * @param idOrigen		: Identificador de la localidad origen
	 * @param idDestino		: Identificador de la localidad destino	
	 * @return Lista de HRE
	 * @throws Exception
	 */
	public List<HRE>buscarHREEmitida(String fechaInicial, String fechaFinal,Integer idOrigen, Integer idDestino)throws Exception;
	/**
	 * Busca HRE Emitida
	 * @param iditinerario	: Identificador del itinerario
	 * @return HRE
	 * @throws Exception
	 */
	public HRE buscarHREEmitida(Long iditinerario)throws Exception;
//	public HRE buscarHREEmitida(String fechaPartida, String codigoBus)throws Exception;
	/**
	 * Busca HRE, segun criterios parametros enviados.
	 * @param criteriosBusqueda	: Criterios que se aplicaran para la busqueda.
	 * @param criteriosOrden 	: Criterios que se aplicara para ordenar los datos.
	 * @return Lista de resultados.
	 * @throws Exception
	 */
	public List<HRE> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String>criteriosOrden)throws Exception;
}
