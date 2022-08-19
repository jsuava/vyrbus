/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 30/12/2014
 * Hora			: 10:12:26
 */
package com.cystesoft.vyrbus.model.dao;

import com.cystesoft.vyrbus.model.bean.RangoSobregiro;

/**
 * @author JABANTO
 *
 */
public interface RangoSobregiroDAO extends GenericDAO {

	/**
	 * Realiza la busqueda del rango correspondiente, de a cuerdo a la lina de credito.
	 * @param lineaCredito	: Linea de credito.
	 * @return	RangoSobregiro
	 * @throws Exception
	 */
	public RangoSobregiro buscarByLineaCredito(Double lineaCredito)throws Exception;
}
