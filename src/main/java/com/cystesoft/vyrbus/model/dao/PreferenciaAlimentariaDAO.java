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

import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface PreferenciaAlimentariaDAO extends GenericDAO {
	public ArrayList<PreferenciaAlimentaria> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<PreferenciaAlimentaria> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public PreferenciaAlimentaria buscarPorId(Long id);
	public void guardar(PreferenciaAlimentaria preferenciaAlimentaria);
	public void actualizar(PreferenciaAlimentaria preferenciaAlimentaria);
	public void inactivar(Long id);
}