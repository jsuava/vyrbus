package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.LiquidacionBus;

/**
 * 
 * @author JABANTO
 *
 */
public interface LiquidacionBusDAO extends GenericDAO{
	
	/**
	 * Busqueda por estado registro
	 * @param estado		: estado 
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<LiquidacionBus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda : 
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<LiquidacionBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * Busqueda por id.
	 * @param id : identificador de la liquidacion
	 * @return
	 */
	public LiquidacionBus buscarXId(Long id);

	
	/**
	 * Guarda 
	 * @param liquidacionbus
	 */
	public void guardar(LiquidacionBus liquidacionbus);
	
	/***
	 * Actualiza
	 * @param liquidacionBus
	 */
	public void actualizar(LiquidacionBus liquidacionBus);
	
	/**
	 * Inactiva liquidacion
	 * @param 
	 * @param 
	 */
	public void inactivar(Long id);
}
