/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 25/08/2014
 * Hora			: 14:47:30
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.MTCRuta;


/**
 * @author JABANTO
 *
 */
public interface MTCRutaDAO extends GenericDAO {
	/**
	 * busca rutas del MTC por estado
	 * @param estado	: Estado por el que se desea buscar.
	 * @return lista de rutas.
	 * @throws Exception
	 */
	public List<MTCRuta> buscarPorEstado(String estado)throws Exception;
}
