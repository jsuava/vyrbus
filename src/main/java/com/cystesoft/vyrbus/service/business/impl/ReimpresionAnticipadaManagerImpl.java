/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/07/2016
 * Hora			: 15:14:55
 */
package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.ReimpresionAnticipada;
import com.cystesoft.vyrbus.model.dao.ReimpresionAnticipadaDAO;
import com.cystesoft.vyrbus.service.business.ReimpresionAnticipadaManager;

/**
 * @author jabanto
 *
 */
public class ReimpresionAnticipadaManagerImpl implements ReimpresionAnticipadaManager{
	private ReimpresionAnticipadaDAO reimpresionAnticipadaDAO;

	/**
	 * @return the reimpresionAnticipadaDAO
	 */
	public ReimpresionAnticipadaDAO getReimpresionAnticipadaDAO() {
		return reimpresionAnticipadaDAO;
	}

	/**
	 * @param reimpresionAnticipadaDAO the reimpresionAnticipadaDAO to set
	 */
	public void setReimpresionAnticipadaDAO(ReimpresionAnticipadaDAO reimpresionAnticipadaDAO) {
		this.reimpresionAnticipadaDAO = reimpresionAnticipadaDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#buscarPorID(java.lang.Long)
	 */
	@Override
	public ReimpresionAnticipada buscarPorID(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getReimpresionAnticipadaDAO().buscarPorID(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<ReimpresionAnticipada> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception {
		// TODO Auto-generated method stub
		return getReimpresionAnticipadaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<ReimpresionAnticipada> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return getReimpresionAnticipadaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#guardar(com.tepsa.sisvyr.model.bean.ReimpresionAnticipada)
	 */
	@Transactional
	@Override
	public void guardar(ReimpresionAnticipada reimpresionAnticipada)throws Exception {
		// TODO Auto-generated method stub
		getReimpresionAnticipadaDAO().guardar(reimpresionAnticipada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#actualizar(com.tepsa.sisvyr.model.bean.ReimpresionAnticipada)
	 */
	@Transactional
	@Override
	public void actualizar(ReimpresionAnticipada reimpresionAnticipada)throws Exception {
		// TODO Auto-generated method stub
		getReimpresionAnticipadaDAO().actualizar(reimpresionAnticipada);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.ReimpresionAnticipadaManager#tieneAutorizacionReimpresion(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean tieneAutorizacionReimpresion(Integer agenciaID, Integer canalVentaID) throws Exception {
		// TODO Auto-generated method stub
		return getReimpresionAnticipadaDAO().tieneAutorizacionReimpresion(agenciaID, canalVentaID);
	}
}
