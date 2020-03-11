/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 01/08/2013
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.OcupacionAsientosDesbloqueados;

/**
 * @author JABANTO
 *
 */
public interface OcupacionAsientosDesbloqueadosManager {
	
	/**
	 * Guarda la ocupacion de los asientos desbloqueados.
	 * @param ocupacionAsientosDesbloqueados : Objeto 
	 * @throws Exception
	 */
	public void guardar(OcupacionAsientosDesbloqueados ocupacionAsientosDesbloqueados) throws Exception;

}
