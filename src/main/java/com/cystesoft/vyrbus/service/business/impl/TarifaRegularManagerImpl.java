/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2019
 * Hora			: 15:58:00
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.dao.TarifaRegularDAO;
import com.cystesoft.vyrbus.service.business.TarifaRegularManager;

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
	public List<TarifaRegular> buscarTarifaPorServicio(Integer canalVentaID, Integer servicioID, Integer rutaID,
			String fechaTarifa, Integer piso, Integer zona) throws Exception {
		// TODO Auto-generated method stub
		return getTarifaRegularDAO().buscarTarifaPorServicio(canalVentaID, servicioID, rutaID, fechaTarifa, piso, zona);
	}

}
