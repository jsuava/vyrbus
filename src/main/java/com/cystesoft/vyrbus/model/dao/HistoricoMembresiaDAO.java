/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José
 * Fecha		: 25/05/2013
 */
package com.cystesoft.vyrbus.model.dao;

import com.cystesoft.vyrbus.model.bean.HistoricoMembresia;

/**
 * @author JABANTO
 *
 */
public interface HistoricoMembresiaDAO {

	/**
	 * Guarda historico membresia.
	 * @param historicoMembresia
	 * @throws Exception
	 */
	public void guardar(HistoricoMembresia historicoMembresia) throws Exception;
	
}
