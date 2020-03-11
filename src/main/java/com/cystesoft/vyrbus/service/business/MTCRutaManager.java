/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 15:03:33
 */
package com.cystesoft.vyrbus.service.business;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.MTCRuta;


/**
 * @author JABANTO
 *
 */
public interface MTCRutaManager {
	/**
	 * busca rutas del MTC por estado
	 * @param estado	: Estado por el que se desea buscar.
	 * @return lista de rutas.
	 * @throws Exception
	 */
	public List<MTCRuta> buscarPorEstado(String estado)throws Exception;
}
