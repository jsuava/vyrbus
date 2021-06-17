/**
 * Proyecto		: VYRBUS
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: Marco Oscco
 * Fecha		: 12 jul. 2020
 * Hora			: 23:49:44
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Tarifa;
import com.cystesoft.vyrbus.model.dao.TarifaDAO;
import com.cystesoft.vyrbus.service.business.TarifaManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Marco
 *
 */
public class TarifaManagerImpl implements TarifaManager {

	private TarifaDAO tarifaDAO;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#guardar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	/**
	 * @return the tarifaDAO
	 */
	public TarifaDAO getTarifaDAO() {
		return tarifaDAO;
	}

	/**
	 * @param tarifaDAO the tarifaDAO to set
	 */
	public void setTarifaDAO(TarifaDAO tarifaDAO) {
		this.tarifaDAO = tarifaDAO;
	}

	@Override
	@Transactional
	public int guardar(Tarifa tarifa) throws Exception {
		int result = Constantes.FAILURE;
		try{
			getTarifaDAO().save(tarifa);
			
			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#actualizar(com.cystesoft.vyrbus.model.bean.Tarifa)
	 */
	@Override
	@Transactional
	public void actualizar(Tarifa tarifa) throws Exception {
		// TODO Auto-generated method stub
		getTarifaDAO().actualizar(tarifa);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaManager#inactivate(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivate(Long id) throws Exception {
		// TODO Auto-generated method stub
		getTarifaDAO().inactivate(id);
	}	
	
	@Override
	@Transactional
	public List<Tarifa> buscarTarifa(Integer canalVentaID, 
			Integer servicioID, 
			Integer localidadOrigenID, 
			Integer localidadDestinoID, 
			Integer piso, 
			Integer zona) throws Exception{
		return getTarifaDAO().buscarTarifa(canalVentaID, servicioID, localidadOrigenID, localidadDestinoID, piso, zona);
	}

}
