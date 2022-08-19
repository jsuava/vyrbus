/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: Marc Oscco
 * Fecha		: 26 oct. 2019
 * Hora			: 15:58:00
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.dao.TarifaRegularDAO;
import com.cystesoft.vyrbus.service.business.TarifaRegularManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Marco
 *
 */
public class TarifaRegularManagerImpl implements TarifaRegularManager{

	private TarifaRegularDAO tarifaRegularDAO;

	public TarifaRegularDAO getTarifaRegularDAO() {
		return tarifaRegularDAO;
	}

	public void setTarifaRegularDAO(TarifaRegularDAO tarifaRegularDAO) {
		this.tarifaRegularDAO = tarifaRegularDAO;
	}

	@Override
	@Transactional
	public int guardar(TarifaRegular tarifaRegular) throws Exception {
		int result = Constantes.FAILURE;
		try{
			this.tarifaRegularDAO.save(tarifaRegular);

			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaRegularManager#buscarTarifaPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
//	@Override
//	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID,
//			String fechaTarifa) throws Exception {
//		// TODO Auto-generated method stub
//		return getTarifaRegularDAO().buscarTarifaPorServicio(canalVentaID, servicioID, rutaID, fechaTarifa);
//	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaRegularManager#buscarTarifaPorServicio(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TarifaRegular> buscarTarifaPorServicio(
			Integer canalVentaID,
			Integer servicioID,
			Integer rutaID,
			String fechaTarifa,
			String horaPartida,
			Integer piso,
			Integer zona) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaRegularDAO().buscarTarifaPorServicio(canalVentaID, servicioID, rutaID, fechaTarifa, horaPartida, piso, zona);
	}

	@Override
	public List<TarifaRegular> listarTarifasPorServicios(Integer canalVentaID,
			Integer servicioID,
			Integer origenID,
			Integer destinoID,
			Integer tipoItinerarioID,
			String fechaInicio,
			String fechaFin,
			String horaPartida,
			Integer con_o_sin_tarifa) throws Exception {
		return getTarifaRegularDAO().listarTarifasPorServicios(canalVentaID, servicioID, origenID,
																destinoID, tipoItinerarioID, fechaInicio,
																fechaFin, horaPartida, con_o_sin_tarifa);
	}

	@Override
	public String buscarCantidadTarifasAReemplazar(Integer canalVentaID,
			Integer servicioID,
			Integer origenID,
			Integer destinoID,
			Integer piso,
			Integer zona,
			String fechaInicio,
			String fechaFin,
			String horaPartida,
			Integer PorServicio)throws Exception {
		return getTarifaRegularDAO().buscarCantidadTarifasAReemplazar(canalVentaID, servicioID, origenID, destinoID,
																		piso, zona, fechaInicio,
																		fechaFin, horaPartida, PorServicio);

	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaRegularManager#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	public void delete(Long idTarifaRegular) throws Exception {
		// TODO Auto-generated method stub
		getTarifaRegularDAO().delete(idTarifaRegular);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaRegularManager#buscarTarifasAReemplazar(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TarifaRegular> buscarTarifasAReemplazar(Integer canalVentaID, Integer servicioID, Integer origenID,
			Integer destinoID, Integer piso, Integer zona, String fechaInicio, String fechaFin, String horaPartida,
			Integer PorServicio) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaRegularDAO().buscarTarifasAReemplazar(canalVentaID, servicioID, origenID, destinoID,
									piso, zona, fechaInicio, fechaFin, horaPartida, PorServicio);
	}


}
