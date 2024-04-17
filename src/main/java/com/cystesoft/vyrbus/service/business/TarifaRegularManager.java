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
import com.cystesoft.vyrbus.service.mappers.HistorialTarifa;

/**
 * @author Marco
 *
 */
public interface TarifaRegularManager {

//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID,
//
	public int guardar(TarifaRegular tarifaRegular) throws Exception;

	public void delete (Long idTarifaRegular) throws Exception;

	public List<TarifaRegular> buscarTarifaPorServicio(
			Integer canalVentaID,
			Integer servicioID,
			Integer rutaID,
			String fechaTarifa,
			String horaPartida,
			Integer piso,
			Integer zona) throws Exception;

	public List<TarifaRegular> listarTarifasPorServicios(Integer canalVentaID,
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

	public List<HistorialTarifa> buscarHistorialTarifa(Integer canalVentaID,
			Long tarifaID,
			String fechaTarifa,
			String horaPartida)throws Exception;

}
