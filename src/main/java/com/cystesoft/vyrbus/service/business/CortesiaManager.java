package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Cortesia;

/**
 * 
 * @author JABANTO
 *
 */
public interface CortesiaManager {
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<Cortesia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos
	 * @return : 
	 */
	public ArrayList<Cortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * Busqueda por Id del Tipo de Gasto
	 * @param id : Identificador Usuario
	 * @return : 
	 */
	public Cortesia buscarPorId(Long id);
	
	/**
	 * Guarda una nueva cortesia.
	 * @param 
	 */
	public void guardar(Cortesia cortesia);
	
	/**
	 * Actualiza la Cortesia.
	 * @param 
	 */
	public void actualizar(Cortesia cortesia);
	
	/**
	 * Inactiva una Cortesia
	 * @param id : Identificador de la Cortesia.
	 */
	public void inactivar(Long id);
	
	/**
	 * 
	 * @param id
	 */
	public void activar (Long id);
	
	/**
	 * Busca cortesias emitidas
	 * @param cortesia : Class cortesia
	 * @return
	 */
	public List<Cortesia> BuscarCortesia(Cortesia cortesia);
	
	/**
	 * Busca los Boletos emitidos por cumpleanios a un PAXFREE.
	 * @param idPasajero		: Identificador de Pasajero.
	 * @param idMotivocortesia	: Identificador del motivo de la cortesia.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Cortesia>buscarBoletosPaxFreXTipoFormaPago(Long idPasajero,Integer idMotivocortesia) throws Exception;
	
	/**
	 * Realiza la validación de la cortesia si fue o no confirmada
	 * @param numeroControl : Número de control de la costesia.
	 * @return (true) si la cortesia ya fue confirmada, (false) si la cortesia aún esta pendiente por confirmar.
	 * @throws Exception
	 */
	public boolean cortesiaConfirmada(String numeroControl) throws Exception;
	
	/**
	 * buscar costesia por el IDventa
	 * @param id	:Identificador de la Venta de Pasajes
	 * @return
	 */
	public Cortesia buscarXIDventa(Long id);
	
	/**
	 * Realiza la busqueda de las cortesias
	 * @param fecha				: fecha emision
	 * @param numDocPax			: numeroc documento del pasajero
	 * @param idTipoFormaPago	:Identificador del tipo de forma de pago
	 * @return lista de ventas
	 */
	public ArrayList<Cortesia>buscarCortesia(String fecha,String numDocPax, Integer idTipoFormaPago);
}
