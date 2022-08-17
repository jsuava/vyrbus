package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.TemporadaAlta;
import com.cystesoft.vyrbus.model.dao.TemporadaAltaDAO;
import com.cystesoft.vyrbus.service.business.TemporadaAltaManager;

/**
 *
 * @author JABANTO
 *
 */
public class TemporadaAltaManagerImpl implements TemporadaAltaManager {

	private TemporadaAltaDAO temporadaAltaDAO;

	/**
	 * @return the temporadaAltaDAO
	 */
	public TemporadaAltaDAO getTemporadaAltaDAO() {
		return temporadaAltaDAO;
	}

	/**
	 * @param temporadaAltaDAO the temporadaAltaDAO to set
	 */
	public void setTemporadaAltaDAO(TemporadaAltaDAO temporadaAltaDAO) {
		this.temporadaAltaDAO = temporadaAltaDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getTemporadaAltaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getTemporadaAltaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#guardar(com.tepsa.sisvyr.model.bean.TemporadaAlta)
	 */
	@Transactional
	@Override
	public void guardar(TemporadaAlta temporadaAlta) {
		getTemporadaAltaDAO().guardar(temporadaAlta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#actualizar(com.tepsa.sisvyr.model.bean.TemporadaAlta)
	 */
	@Transactional
	@Override
	public void actualizar(TemporadaAlta temporadaAlta) {
		getTemporadaAltaDAO().actualizar(temporadaAlta);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#buscarTemporadaAlta(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TemporadaAlta> buscarTemporadaAlta(String anio, String mes, String dia)throws Exception {
		return getTemporadaAltaDAO().buscarTemporadaAlta(anio, mes, dia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TemporadaAltaManager#deleteTemporadaAlta(java.lang.String)
	 */
	@Override
	public void anularTemporadaAlta(String anio,String mes, String dia) throws Exception {
		getTemporadaAltaDAO().anularTemporadaAlta(anio,mes, dia);
	}
}
