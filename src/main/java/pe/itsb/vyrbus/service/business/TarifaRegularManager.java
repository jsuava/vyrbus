/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 26 oct. 2019
 * Hora			: 15:57:04
 */
package pe.itsb.vyrbus.service.business;

import java.util.List;

import pe.itsb.vyrbus.model.bean.TarifaRegular;

/**
 * @author Marco
 *
 */
public interface TarifaRegularManager {

//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID,
//
	public int guardar(TarifaRegular tarifaRegular) throws Exception;

	public void delete (Long idTarifaRegular) throws Exception;

	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID, String fechaTarifa, String horaPartida, Integer piso, Integer zona) throws Exception;

	public List<TarifaRegular> listarTarifasPorServicios(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer tipoItinerarioID, String fechaInicio, String fechaFin, String horaPartida, Integer con_o_sin_tarifa) throws Exception;

	public String buscarCantidadTarifasAReemplazar(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer piso, Integer zona, String fechaInicio, String fechaFin, String horaPartida, Integer PorServicio)throws Exception;

	public List<TarifaRegular> buscarTarifasAReemplazar(Integer empresaID, Integer canalVentaID, Integer servicioID, Integer origenID, Integer destinoID, Integer piso, Integer zona, String fechaInicio, String fechaFin, String horaPartida,Integer PorServicio)throws Exception;


}
