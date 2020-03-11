/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 14:47:19
 */
package com.cystesoft.vyrbus.model.dao;

import com.cystesoft.vyrbus.model.bean.MTCDireccionTerminal;

/**
 * @author JABANTO
 *
 */
public interface MTCDireccionTerminalDAO extends GenericDAO {
	/**
	 * Busca direccion del terminal registrado en el MTC por el Identificador de la Agencia registrada en el Sisvyr
	 * @param idAgencia	: Identificador de la Agencia
	 * @return	DireccionTerminal
	 * @throws Exception
	 */
	public MTCDireccionTerminal buscarPorIdAgencia(Integer idAgencia)throws Exception;

}
