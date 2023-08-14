package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.LiquidacionBus;
import pe.itsb.vyrbus.model.dao.LiquidacionBusDAO;
import pe.itsb.vyrbus.service.business.LiquidacionBusManager;

/**
 *
 * @author JABANTO
 *
 */
public class LiquidacionBusManagerImpl implements LiquidacionBusManager{
	private LiquidacionBusDAO liquidacionBusDAO;

	public void setLiquidacionBusDAO (LiquidacionBusDAO liquidacionBusDAO){
		this.liquidacionBusDAO=liquidacionBusDAO;
	}

	public LiquidacionBusDAO getLiquidacionBusDAO(){
		return liquidacionBusDAO;
	}


	@Override
	public ArrayList<LiquidacionBus> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getLiquidacionBusDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	@Override
	public ArrayList<LiquidacionBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getLiquidacionBusDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public LiquidacionBus buscarXId(Long id) {
		return getLiquidacionBusDAO().buscarXId(id);
	}

	@Transactional
	@Override
	public void guardar(LiquidacionBus liquidacionbus) {
		getLiquidacionBusDAO().guardar(liquidacionbus);
	}

	@Transactional
	@Override
	public void actualizar(LiquidacionBus liquidacionBus) {
		getLiquidacionBusDAO().actualizar(liquidacionBus);
	}

	@Transactional
	@Override
	public void inactivar(Long id) {
		getLiquidacionBusDAO().inactivar(id);
	}

}
