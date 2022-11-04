/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 17:58:45
 */
package com.cystesoft.vyrbus.service.business;

import com.cystesoft.vyrbus.model.bean.HistoricoResumenVentas;

/**
 * @author Marco
 *
 */
public interface HistoricoResumenVentasManager {
	
	/**
	 * 
	 * @param ventaPasajeHistorial
	 */
	public void guardar(HistoricoResumenVentas historicoResumenVentas);

}
