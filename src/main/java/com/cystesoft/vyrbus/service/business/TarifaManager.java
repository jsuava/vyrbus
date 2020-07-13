/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Oscco
 * Fecha		: 12 jul. 2020
 * Hora			: 23:46:36
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.Tarifa;


/**
 * @author Marco
 *
 */
public interface TarifaManager {
	/**
	 * Guarda una nueva tarifa a fecha abierta.
	 * @param tarifaFechaAbierta	
	 * @throws Exception
	 */
	public void guardar(Tarifa tarifa)throws Exception;
	/**
	 * Actualiza una tarifa a fecha abierta
	 * @param tarifaFechaAbierta
	 * @throws Exception
	 */
	public void actualizar(Tarifa tarifa)throws Exception;
	/**
	 * Inactiva el registro
	 * @param id	: Identificador de la tarifa a fecha abierta.
	 * @throws Exception
	 */
	public void inactivate(Long id)throws Exception;
}
