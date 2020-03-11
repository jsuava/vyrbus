package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.LiquidacionBus;
import com.cystesoft.vyrbus.model.dao.LiquidacionBusDAO;

/**
 * 
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class LiquidacionBusDAOImpl extends GenericDAOImpl implements LiquidacionBusDAO {

	
	
	@Override
	public ArrayList<LiquidacionBus> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<LiquidacionBus>) super.findByEstadoRegistro(LiquidacionBus.class, estado, criterioOrden);
	}

	@Override
	public ArrayList<LiquidacionBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<LiquidacionBus>) super.findByX(LiquidacionBus.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public LiquidacionBus buscarXId(Long id) {
		return (LiquidacionBus) super.findById(LiquidacionBus.class, id);
	}

	@Override
	public void guardar(LiquidacionBus liquidacionbus) {
		super.save(liquidacionbus);
		
	}

	@Override
	public void actualizar(LiquidacionBus liquidacionBus) {
		super.update(liquidacionBus);
	}

	@Override
	public void inactivar(Long id) {
		super.inactivate(LiquidacionBus.class, id);		
	}

}
