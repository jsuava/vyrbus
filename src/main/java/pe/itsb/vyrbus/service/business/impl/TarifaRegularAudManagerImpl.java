/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 20 jul. 2020
 * Hora			: 11:46:43
 */
package pe.itsb.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.TarifaRegularAud;
import pe.itsb.vyrbus.model.dao.TarifaRegularAudDAO;
import pe.itsb.vyrbus.service.business.TarifaRegularAudManager;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Marco
 *
 */
public class TarifaRegularAudManagerImpl implements TarifaRegularAudManager {

	private TarifaRegularAudDAO tarifaRegularAudDAO;

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.TarifaRegularAudManager#guardar(pe.itsb.vyrbus.model.bean.TarifaRegularAud)
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
