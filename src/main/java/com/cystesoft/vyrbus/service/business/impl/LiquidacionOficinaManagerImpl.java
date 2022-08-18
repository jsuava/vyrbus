package com.cystesoft.vyrbus.service.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;
import com.cystesoft.vyrbus.model.dao.LiquidacionOficinaDAO;
import com.cystesoft.vyrbus.service.business.LiquidacionOficinaManager;

public class LiquidacionOficinaManagerImpl implements LiquidacionOficinaManager{
	private LiquidacionOficinaDAO liquidacionOficinaDAO;


	public LiquidacionOficinaDAO getLiquidacionOficinaDAO(){
		return liquidacionOficinaDAO;
	}

	public void setLiquidacionOficinaDAO(LiquidacionOficinaDAO liquidacionOficinaDAO){
		this.liquidacionOficinaDAO=liquidacionOficinaDAO;
	}



	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionOficinaManager#buscarLiquidacionPendiente(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionPendiente(String fecha,Integer idAgencia) throws Exception {
		return getLiquidacionOficinaDAO().buscarLiquidacionPendiente(fecha, idAgencia);
	}

//	/*
//	 * (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.LiquidacionOficinaManager#buscarLiquidacionOficiana(java.lang.Integer, java.lang.String, java.lang.Integer)
//	 */
//	@Override
//	public List<LiquidacionOficina> buscarLiquidacionOficiana(Integer idUsuario, String fecha, Integer idAgencia) {
//		return getLiquidacionOficinaDAO().buscarLiquidacionOficiana(idUsuario, fecha, idAgencia);
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionOficinaManager#buscarLiquidacionLiquidadas(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacionLiquidadas(String fecha,Integer idAgencia) {
		return getLiquidacionOficinaDAO().buscarLiquidacionLiquidadas(fecha, idAgencia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionOficinaManager#guradar(com.tepsa.sisvyr.model.bean.LiquidacionOficina)
	 */
	@Transactional
	@Override
	public void guardar(LiquidacionOficina liquidacionOficina) throws Exception {
		getLiquidacionOficinaDAO().guardar(liquidacionOficina);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionOficinaManager#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		getLiquidacionOficinaDAO().inactivar(id);
	}



}
