package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.GastoBus;
import com.cystesoft.vyrbus.model.dao.GastoBusDAO;
import com.cystesoft.vyrbus.service.business.GastoBusManager;

/**
 * 
 * @author JABANTO
 *
 */
public class GastoBusManagerImpl implements GastoBusManager{
	private GastoBusDAO gastoBusDAO;
	
	
	public GastoBusDAO getGastoBusDAO(){
		return gastoBusDAO;
	}
	
	public void setGastoBusDAO (GastoBusDAO gastoBusDAO){
		this.gastoBusDAO=gastoBusDAO;
	}
	
	@Override
	public ArrayList<GastoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getGastoBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	@Transactional
	@Override
	public void guardar(GastoBus gastoBus) {
		getGastoBusDAO().guardar(gastoBus);
		
	}

	@Transactional
	@Override
	public void delete(Long idLiquidacionBus) {
		getGastoBusDAO().delete(idLiquidacionBus);
		
	}

	
	
	
	

}
