package com.cystesoft.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.cystesoft.vyrbus.model.bean.Gasto;
import com.cystesoft.vyrbus.model.dao.GastoDAO;
import com.cystesoft.vyrbus.service.business.GastoManager;
/**
 * 
 * @author José Abanto
 *
 */
public class GastoManagerImpl implements GastoManager {
	private GastoDAO gastoDAO;
	
	public void setGastoDAO (GastoDAO  gastoDAO){
		this.gastoDAO=gastoDAO;
	}
	
	public GastoDAO getGastoDAO(){
		return gastoDAO;
	}
	
	
	@Override
	public ArrayList<Gasto> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getGastoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	@Override
	public ArrayList<Gasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getGastoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	@Transactional
	@Override
	public void guardar(Gasto gasto) {
		getGastoDAO().guardar(gasto);
		
	}

	@Transactional
	@Override
	public void actualizar(Gasto gasto) {
		getGastoDAO().actualizar(gasto);
		
	}

	@Transactional
	@Override
	public void inactivar(Long id) {
		getGastoDAO().inactivar(id);
		
	}

	@Override
	public Gasto buscarXId(Long id) {
		return getGastoDAO().buscarXId(id);
	}

	@Override
	public List<Gasto> buscarGasto(String fechaGasto, Integer idTipoGasto,Integer idAgencia, Integer idUsuario) {
		return getGastoDAO().buscarGasto(fechaGasto, idTipoGasto, idAgencia, idUsuario);
	}

	@Override
	public List<Gasto> buscarGastoLiqOficina(String fechaLiquidacion,String usuario) {
		return getGastoDAO().buscarGastoLiqOficina(fechaLiquidacion, usuario);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.GastoManager#BuscarTotalGastos(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double BuscarTotalGastos(String fecha, Integer idUsuario,Integer idAgencia) throws Exception {
		// TODO Auto-generated method stub
		
		return getGastoDAO().BuscarTotalGastos(fecha, idUsuario, idAgencia);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.GastoManager#obtenerGastosByLiquidacion(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Gasto> obtenerGastosByLiquidacion(String fechaLiquidacion, Integer idAgencia, Integer idUsuario, Integer isIngreso, boolean groupByObs) {
		return getGastoDAO().obtenerGastosByLiquidacion(fechaLiquidacion, idAgencia, idUsuario, isIngreso, groupByObs);
	}

	/* (non-Javadoc)
	 * @see com.cystesoft.vyrbus.service.business.GastoManager#buscarGastosIngresos(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<Gasto> buscarGastosIngresos(String fechaInicio, String fechaFin, Integer tipoOperacion)
			throws Exception {
		// TODO Auto-generated method stub
		return getGastoDAO().buscarGastosIngresos(fechaInicio, fechaFin, tipoOperacion);
	}
}
