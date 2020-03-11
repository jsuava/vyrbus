/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2019
 * Hora			: 15:57:04
 */
package com.cystesoft.vyrbus.service.business;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.TarifaRegular;

/**
 * @author Marco
 *
 */
public interface TarifaRegularManager {

//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID, 
//							   						   String fechaTarifa) throws Exception;

	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID, 
			   String fechaTarifa, Integer piso, Integer zona) throws Exception;
}
