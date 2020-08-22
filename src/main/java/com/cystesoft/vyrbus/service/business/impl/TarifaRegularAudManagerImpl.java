/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 20 jul. 2020
 * Hora			: 11:46:43
 */
package com.cystesoft.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TarifaRegularAud;
import com.cystesoft.vyrbus.model.dao.TarifaRegularAudDAO;
import com.cystesoft.vyrbus.service.business.TarifaRegularAudManager;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author Marco
 *
 */
public class TarifaRegularAudManagerImpl implements TarifaRegularAudManager {

	private TarifaRegularAudDAO tarifaRegularAudDAO;
	
	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.TarifaRegularAudManager#guardar(com.cystesoft.vyrbus.model.bean.TarifaRegularAud)
	 */
	/**
	 * @return the tarifaRegularAudDAO
	 */
	public TarifaRegularAudDAO getTarifaRegularAudDAO() {
		return tarifaRegularAudDAO;
	}

	/**
	 * @param tarifaRegularAudDAO the tarifaRegularAudDAO to set
	 */
	public void setTarifaRegularAudDAO(TarifaRegularAudDAO tarifaRegularAudDAO) {
		this.tarifaRegularAudDAO = tarifaRegularAudDAO;
	}

	@Override
	@Transactional
	public int guardar(TarifaRegularAud tarifaRegularAud) throws Exception {
		// TODO Auto-generated method stub
		int result = Constantes.FAILURE;
		try{
			this.getTarifaRegularAudDAO().save(tarifaRegularAud);
			result = Constantes.CORRECT;
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

}
