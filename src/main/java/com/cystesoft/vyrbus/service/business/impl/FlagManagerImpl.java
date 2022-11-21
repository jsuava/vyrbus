/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Implementacion de metodos que permiten el acceso al modelo.
 * Autor		: José Sullo Avalos
 * Fecha		: 27/09/2012
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Flag;
import com.cystesoft.vyrbus.model.dao.FlagDAO;
import com.cystesoft.vyrbus.service.business.FlagManager;

/**
 * @author Jose
 *
 */
public class FlagManagerImpl implements FlagManager {
	private FlagDAO flagDAO;

	/**
	 * @return the flagDAO
	 */
	public FlagDAO getFlaDAO() {
		return flagDAO;
	}
	/**
	 * @param flagDAO the flagDAO to set
	 */
	public void setFlagDAO(FlagDAO flagDAO) {
		this.flagDAO = flagDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Flag> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception {
		return getFlaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Flag> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception {
		return getFlaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Flag buscarPorId(Long id)throws Exception {
		return getFlaDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#guardar(com.tepsa.sisvyr.model.bean.Agencia)
	 */
	@Override
	@Transactional
	public void guardar(Flag flag)throws Exception {
		
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.AgenciaManager#actualizar(com.tepsa.sisvyr.model.bean.Agencia)
	 */
	@Override
	@Transactional
	public void actualizar(Flag flag)throws Exception {
		try{
			
			
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}


}
