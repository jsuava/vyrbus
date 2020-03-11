package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.RolOpcionMenu;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenuID;
import com.cystesoft.vyrbus.model.dao.RolOpcionMenuDAO;
import com.cystesoft.vyrbus.service.business.RolOpcionMenuManager;

/**
 * 
 * @author José Abanto
 *
 */
public class RolOpcionMenuManagerImpl implements RolOpcionMenuManager{
	private RolOpcionMenuDAO rolOpcionMenuDAO;
	
	public RolOpcionMenuDAO getRolOpcionMenuDAO(){
		return rolOpcionMenuDAO;
	}
	
	public void setRolOpcionMenuDAO(RolOpcionMenuDAO rolOpcionMenuDAO){
		this.rolOpcionMenuDAO=rolOpcionMenuDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolOpcionMenuManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<RolOpcionMenu> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getRolOpcionMenuDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolOpcionMenuManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<RolOpcionMenu> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getRolOpcionMenuDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolOpcionMenuManager#guardar(com.tepsa.sisvyr.model.bean.RolOpcionMenu)
	 */
	@Override
	@Transactional
	public void guardar(RolOpcionMenu rolOpcionMenu) {
		getRolOpcionMenuDAO().guardar(rolOpcionMenu);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolOpcionMenuManager#actualizar(com.tepsa.sisvyr.model.bean.RolOpcionMenu)
	 */
	@Override
	@Transactional
	public void actualizar(RolOpcionMenu rolOpcionMenu) {
		getRolOpcionMenuDAO().actualizar(rolOpcionMenu);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.RolOpcionMenuManager#inactivar(com.tepsa.sisvyr.model.bean.RolOpcionMenuID, java.lang.String)
	 */
	@Override
	public void inactivarActivar(RolOpcionMenuID rolOpcionMenuID, String estado) {
		getRolOpcionMenuDAO().inactivarActivar(rolOpcionMenuID, estado);
	}
	

}
