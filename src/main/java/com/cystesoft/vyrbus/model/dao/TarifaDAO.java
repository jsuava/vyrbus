/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Oscco
 * Fecha		: 12 jul. 2020
 * Hora			: 23:34:42
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.Tarifa;


/**
 * @author Marco
 *
 */
public interface TarifaDAO extends GenericDAO{
	
	/**
	 * Guarda una nueva cabecera de tarifa 
	 * @param tarifaFechaAbierta	
	 * @throws Exception
	 */
	public void guardar(Tarifa tarifa)throws Exception;
	
	/**
	 * Actualiza una cabecera de tarifa 
	 * @param tarifaFechaAbierta
	 * @throws Exception
	 */
	public void actualizar(Tarifa tarifa)throws Exception;
	
	/**
	 * Inactiva el registro
	 * @param id	: Identificador de la cabecera de tarifa 
	 * @throws Exception
	 */
	public void inactivate(Long id)throws Exception;
	
	public List<Tarifa> buscarTarifa(Integer canalVentaID, 
							Integer servicioID, 
							Integer localidadOrigenID, 
							Integer localidadDestinoID, 
							Integer piso, 
							Integer zona) throws Exception;

}
