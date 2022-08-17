/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 20/11/2013
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.SerieEspecieValorada;
import com.cystesoft.vyrbus.model.dao.SerieEspecieValoradaDAO;
import com.cystesoft.vyrbus.service.business.SerieEspecieValoradaManager;

/**
 * @author JABANTO
 *
 */
public class SerieEspecieValoradaManagerImpl implements SerieEspecieValoradaManager {
	private SerieEspecieValoradaDAO serieEspecieValoradaDAO;

	/**
	 * @return the serieEspecieValoradaDAO
	 */
	public SerieEspecieValoradaDAO getSerieEspecieValoradaDAO() {
		return serieEspecieValoradaDAO;
	}

	/**
	 * @param serieEspecieValoradaDAO the serieEspecieValoradaDAO to set
	 */
	public void setSerieEspecieValoradaDAO(SerieEspecieValoradaDAO serieEspecieValoradaDAO) {
		this.serieEspecieValoradaDAO = serieEspecieValoradaDAO;
	}


	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SerieEspecieValoradaManager#buscarPorID(java.lang.String, java.lang.Integer)
	 */
	@Override
	public SerieEspecieValorada buscarPorID(String numeroSerie,Integer idTipoComprobante) throws Exception {
		// TODO Auto-generated method stub
		return getSerieEspecieValoradaDAO().buscarPorID(numeroSerie, idTipoComprobante);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.SerieEspecieValoradaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<SerieEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getSerieEspecieValoradaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}





}
