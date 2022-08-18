/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 04/05/2016
 * Hora			: 12:12:07
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Session;
import com.cystesoft.vyrbus.model.dao.SessionDAO;
import com.cystesoft.vyrbus.service.business.SessionManager;

/**
 * @author jabanto
 *
 */
public class SessionManagerImpl implements SessionManager{
	private SessionDAO sessionDAO;
	/**
	 * @return the sessionDAO
	 */
	public SessionDAO getSessionDAO() {
		return sessionDAO;
	}

	/**
	 * @param sessionDAO the sessionDAO to set
	 */
	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SessionManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Session> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return getSessionDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SessionManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Session> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getSessionDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SessionManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Session buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return getSessionDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SessionManager#guardar(com.tepsa.sisvyr.model.bean.Session)
	 */
	@Transactional
	@Override
	public void guardar(Session session) {
		// TODO Auto-generated method stub
		getSessionDAO().guardar(session);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SessionManager#actualizar(com.tepsa.sisvyr.model.bean.Session)
	 */
	@Transactional
	@Override
	public void actualizar(Session session) {
		// TODO Auto-generated method stub
		getSessionDAO().actualizar(session);
	}



}
