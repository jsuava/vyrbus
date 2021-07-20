/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Equipaje;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface EquipajeDAO extends GenericDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Equipaje buscarPorId(Long id);
	/**
	 * 
	 * @param equipaje
	 */
	public void guardar(Equipaje equipaje);
	/**
	 * 
	 * @param equipaje
	 */
	public void actualizar(Equipaje equipaje);
	
}