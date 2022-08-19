/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 14/05/2013
 */
package com.cystesoft.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.dao.ParametrosDAO;
import com.cystesoft.vyrbus.service.business.ParametrosManager;

/**
 * @author JABANTO
 *
 */
public class ParametrosManajerImpl implements ParametrosManager{

	private ParametrosDAO parametrosDAO;

	/**
	 * @return the parametrosDAO
	 */
	public ParametrosDAO getParametrosDAO() {
		return parametrosDAO;
	}

	/**
	 * @param parametrosDAO the parametrosDAO to set
	 */
	public void setParametrosDAO(ParametrosDAO parametrosDAO) {
		this.parametrosDAO = parametrosDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ParametrosManager#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public Parametros buscarPorEstadoRegistro(String estado) {
		return getParametrosDAO().buscarPorEstadoRegistro(estado);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ParametrosManager#guardar(com.tepsa.sisvyr.model.bean.Parametros)
	 */
	@Override
	@Transactional
	public void guardar(Parametros parametros) throws Exception {
		// TODO Auto-generated method stub
		getParametrosDAO().guardar(parametros);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ParametrosManager#actualizar(com.tepsa.sisvyr.model.bean.Parametros)
	 */
	@Override
	@Transactional
	public void actualizar(Parametros parametros) throws Exception {
		// TODO Auto-generated method stub
		getParametrosDAO().actualizar(parametros);
	}

}
