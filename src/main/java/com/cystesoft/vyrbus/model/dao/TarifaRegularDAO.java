/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 25 oct. 2019
 * Hora			: 13:57:18
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.TarifaRegular;

/**
 * @author Marco
 *
 */
public interface TarifaRegularDAO extends GenericDAO {
	
//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, 
//													   Integer rutaID, String fechaTarifa) throws Exception;
	
	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, 
													   Integer rutaID, String fechaTarifa, String horaPartida, 
													   Integer piso, Integer zona) throws Exception;

	
	public List<TarifaRegular>listarTarifasPorServicios(Integer canalVentaID,
														Integer servicioID,
														Integer origenID,
														Integer destinoID,
														Integer tipoItinerarioID,
														String fechaInicio,
														String fechaFin,
														String horaPartida,
														Integer con_o_sin_tarifa) throws Exception;
	
	public String buscarCantidadTarifasAReemplazar(Integer canalVentaID,
			Integer servicioID,
			Integer origenID,
			Integer destinoID,
			Integer piso,
			Integer zona,
			String fechaInicio,
			String fechaFin,
			String horaPartida,
			Integer PorServicio)throws Exception;

	
	public void delete (Long idTarifaRegular) throws Exception;
	
	public List<TarifaRegular> buscarTarifasAReemplazar(Integer canalVentaID,
			Integer servicioID,
			Integer origenID,
			Integer destinoID,
			Integer piso,
			Integer zona,
			String fechaInicio,
			String fechaFin,
			String horaPartida,
			Integer PorServicio)throws Exception;

}
