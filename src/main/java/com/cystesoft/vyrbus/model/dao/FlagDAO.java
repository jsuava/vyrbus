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

import com.cystesoft.vyrbus.model.bean.Flag;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface FlagDAO extends GenericDAO {
	public ArrayList<Flag> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Flag> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Flag buscarPorId(Long id);
	public void guardar(Flag flag);
	public void actualizar(Flag flag);
	
}