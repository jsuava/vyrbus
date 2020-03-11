package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Cortesia;
import com.cystesoft.vyrbus.model.dao.CortesiaDAO;
import com.cystesoft.vyrbus.service.business.CortesiaManager;

public class CortesiaManagerImpl implements CortesiaManager {
	private CortesiaDAO cortesiaDAO;
	
	/**
	 * 
	 * @return
	 */
	public CortesiaDAO getCortesiaDAO(){
		return cortesiaDAO;
	}
	
	/**
	 * 
	 * @param corteciaDAO
	 */
	public void setCortesiaDAO (CortesiaDAO cortesiaDAO){
		this.cortesiaDAO=cortesiaDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Cortesia> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getCortesiaDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Cortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getCortesiaDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Cortesia buscarPorId(Long id) {
		return getCortesiaDAO().buscarPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#guardar(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	@Transactional
	public void guardar(Cortesia cortesia) {
		getCortesiaDAO().guardar(cortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#actualizar(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	@Transactional
	public void actualizar(Cortesia cortesia) {
		getCortesiaDAO().actualizar(cortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#inactivar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void inactivar(Long id) {
		getCortesiaDAO().inactivar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CorteciaManager#activar(java.lang.Long)
	 */
	@Override
	@Transactional
	public void activar(Long id) {
		getCortesiaDAO().activar(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CortesiaManager#BuscarCortesia(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	public List<Cortesia> BuscarCortesia(Cortesia cortesia) {
		return getCortesiaDAO().BuscarCortesia(cortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CortesiaManager#BuscarBolXcumpleaniosPaxFree(java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public ArrayList<Cortesia> buscarBoletosPaxFreXTipoFormaPago(Long idPasajero,Integer idMotivocortesia) throws Exception {
		return getCortesiaDAO().BuscarBolXcumpleaniosPaxFree(idPasajero, idMotivocortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CortesiaManager#confirmacionCortesia(java.lang.String)
	 */
	@Override
	public boolean cortesiaConfirmada(String numeroControl) throws Exception {
		return getCortesiaDAO().cortesiaConfirmada(numeroControl);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CortesiaManager#buscarXIDventa(java.lang.Long)
	 */
	@Override
	public Cortesia buscarXIDventa(Long id) {
		return getCortesiaDAO().buscarXIDventa(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.CortesiaManager#buscarVentasXCortesia(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ArrayList<Cortesia> buscarCortesia(String fecha, String numDocPax, Integer idTipoFormaPago) {
		// TODO Auto-generated method stub
		return getCortesiaDAO().buscarCortesia(fecha, numDocPax, idTipoFormaPago);
	}
}
