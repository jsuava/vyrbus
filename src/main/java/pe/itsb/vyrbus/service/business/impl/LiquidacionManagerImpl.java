package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.dao.LiquidacionDAO;
import pe.itsb.vyrbus.service.business.LiquidacionManager;
import pe.itsb.vyrbus.service.mappers.ResumenComprobante;
import pe.itsb.vyrbus.view.tuentrada.LiquidacionTuentrada;

/**
 *
 * @author Jos� Abanto
 *
 */
public class LiquidacionManagerImpl implements LiquidacionManager {
	private LiquidacionDAO liquidacionDAO;

	public LiquidacionDAO getLiquidacionDAO(){
		return liquidacionDAO;
	}


	public void setLiquidacionDAO (LiquidacionDAO liquidacionDAO){
		this.liquidacionDAO=liquidacionDAO;
	}



	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#aperturaLiquidacion(com.tepsa.sisvyr.model.bean.Liquidacion)
	 */
	@Transactional
	@Override
	public void aperturarLiquidacion(Liquidacion liquidacion) {
		getLiquidacionDAO().aperturarLiquidacion(liquidacion);

	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Liquidacion> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getLiquidacionDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#buscarLiquidacion(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> buscarLiquidacion(String fechaInicial,String FechaFinal, Integer idAgencia, Integer idUsuario,Integer estadoLiquidacion) throws Exception {
		return getLiquidacionDAO().buscarLiquidacion(fechaInicial, FechaFinal, idAgencia, idUsuario, estadoLiquidacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#buscarUltimaLiquidacion(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Liquidacion buscarUltimaLiquidacion(Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion) throws Exception{
		return getLiquidacionDAO().buscarUltimaLiquidacion(idAgencia, idUsuario, estadoLiquidacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#actualizar(com.tepsa.sisvyr.model.bean.Liquidacion)
	 */
	@Transactional
	@Override
	public void actualizar(Liquidacion liquidacion) throws Exception {
		getLiquidacionDAO().actualizar(liquidacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#BuscarEspeciesValoradas(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Liquidacion> BuscarEspeciesValoradas(String fechaLiquidacion,Integer idAgencia, Integer idUsuario) {
		return getLiquidacionDAO().BuscarEspeciesValoradas(fechaLiquidacion, idAgencia, idUsuario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#buscarRptLiquidacionTurno(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Liquidacion buscarRptLiquidacionTurno(String fechaLiquidacion,Integer idAgencia, Integer idUsuario) {
		// TODO Auto-generated method stub
		return getLiquidacionDAO().buscarRptLiquidacionTurno(fechaLiquidacion, idAgencia, idUsuario);
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#liquidacionTuentrada(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<LiquidacionTuentrada> liquidacionTuentrada(Integer idAgencia, Integer idUsuario, String fechaliquidacion)throws Exception{
		return getLiquidacionDAO().liquidacionTuentrada(idAgencia, idUsuario, fechaliquidacion);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public Liquidacion buscarPorId(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getLiquidacionDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.LiquidacionManager#buscarResumenComprobantes(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String, ResumenComprobante> buscarResumenComprobantes(String fechaLiquidacion, Integer idAgencia, Integer idUsuario) {
		return getLiquidacionDAO().buscarResumenComprobantes(fechaLiquidacion, idAgencia, idUsuario);
	}


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.LiquidacionManager#buscarLiquidacionByUsuario(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Liquidacion buscarLiquidacionByUsuario(Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion, String fecha) throws Exception {
		return getLiquidacionDAO().buscarLiquidacionByUsuario(idAgencia, idUsuario, estadoLiquidacion, fecha);
	}




//	/* (non-Javadoc)
//	 * @see com.tepsa.sisvyr.service.business.LiquidacionManager#reaperturarLiquidacion(com.tepsa.sisvyr.model.bean.Liquidacion)
//	 */
//	@Override
//	public void reaperturarLiquidacion(Long idliquidacion)throws Exception {
//		// TODO Auto-generated method stub
//		getLiquidacionDAO().reaperturarLiquidacion(idliquidacion);
//	}

}
