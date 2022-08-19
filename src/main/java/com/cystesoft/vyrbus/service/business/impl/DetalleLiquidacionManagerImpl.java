package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.DetalleLiquidacion;
import com.cystesoft.vyrbus.model.dao.DetalleLiquidacionDAO;
import com.cystesoft.vyrbus.service.business.DetalleLiquidacionManager;

/**
 *
 * @author JABANTO
 *
 */
public class DetalleLiquidacionManagerImpl implements DetalleLiquidacionManager {
	private DetalleLiquidacionDAO  detalleLiquidacionDAO;


	public DetalleLiquidacionDAO getDetalleLiquidacionDAO (){
		return detalleLiquidacionDAO;
	}

	public void setDetalleLiquidacionDAO (DetalleLiquidacionDAO detalleLiquidacionDAO){
		this.detalleLiquidacionDAO=detalleLiquidacionDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#guarda(com.tepsa.sisvyr.model.bean.DetalleLiquidacion)
	 */
	@Transactional
	@Override
	public void guarda(DetalleLiquidacion detalleLiquidacion) throws Exception {
		getDetalleLiquidacionDAO().guarda(detalleLiquidacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#actualizar(com.tepsa.sisvyr.model.bean.DetalleLiquidacion)
	 */
	@Transactional
	@Override
	public void actualizar(DetalleLiquidacion detalleLiquidacion)throws Exception {
		getDetalleLiquidacionDAO().actualizar(detalleLiquidacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		getDetalleLiquidacionDAO().delete(id);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#buscarVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleLiquidacion> buscarVentasALiquidar(String fechaLquidacion,Integer idAgencia, Integer idUsuario) {
		return getDetalleLiquidacionDAO().buscarVentasALiquidar(fechaLquidacion, idAgencia, idUsuario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#actualizaFechaLiqVentas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void actualizaIdLiquidacionVentasLiquidadas(Long idLiquidacion, String fechaLiquidacion, Integer idAgencia,Integer idUsuario) {
		getDetalleLiquidacionDAO().actualizaIdLiquidacionVentasLiquidadas(idLiquidacion, fechaLiquidacion, idAgencia, idUsuario);

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.DetalleLiquidacionManager#deleteXidLiquidacion(java.lang.Long)
	 */
	@Override
	public void deleteXidLiquidacion(Long idLiquidacion) throws Exception {
		// TODO Auto-generated method stub
		getDetalleLiquidacionDAO().deleteXidLiquidacion(idLiquidacion);
	}



}
