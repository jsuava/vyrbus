/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 03/04/2014
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.TarifaFechaAbierta;


/**
 * @author JABANTO
 *
 */
public interface TarifaFechaAbiertaDAO extends GenericDAO {
	/**
	 * Realiza la busqueda de la tarifa fecha abierta por ruta y servicio
	 * @param idRuta		: Identificador de la Ruta.
	 * @param idServicio	: Identificador del Servicio
	 * @return	TarifaFechaAbierta
	 * @throws Exception
	 */
	public Double buscarTarifa(Integer idRuta, Integer idServicio)throws Exception;
	
	/**
	 * Busca las tarifas a fecha abierta, según los parametros enviados.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @param idServicio	: Identificador del servicio.
	 * @return Lista de tarifas a fecha abierta.
	 * @throws Exception
	 */
	public List<TarifaFechaAbierta> buscarTarifas(Integer idOrigen,Integer idDestino, Integer idServicio)throws Exception;

	/**
	 * Busca las tarifas a fecha abierta, según los parametros enviados.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @param idServicio	: Identificador del servicio.
	 * @return Lista de tarifas a fecha abierta.
	 * @throws Exception
	 */
	public List<TarifaFechaAbierta> listarTarifasFA(Integer idOrigen,Integer idDestino, Integer idServicio)throws Exception;	
	
	/**
	 * Guarda una nueva tarifa a fecha abierta.
	 * @param tarifaFechaAbierta	
	 * @throws Exception
	 */
	public void guardar(TarifaFechaAbierta tarifaFechaAbierta)throws Exception;
	
	/**
	 * Actualiza una tarifa a fecha abierta
	 * @param tarifaFechaAbierta
	 * @throws Exception
	 */
	public void actualizar(TarifaFechaAbierta tarifaFechaAbierta)throws Exception;
	
	/**
	 * Inactiva el registro
	 * @param id	: Identificador de la tarifa a fecha abierta.
	 * @throws Exception
	 */
	public void inactivate(Long id)throws Exception;

}
