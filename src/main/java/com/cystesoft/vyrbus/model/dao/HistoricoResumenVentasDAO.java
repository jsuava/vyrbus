/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 14:37:10
 */
package com.cystesoft.vyrbus.model.dao;

import com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas;

/**
 * @author Marco
 *
 */
public interface HistoricoResumenVentasDAO extends GenericDAO {
	
	public void guardar(HistoricoResumenVentas historicoResumenVentas);
	
}
