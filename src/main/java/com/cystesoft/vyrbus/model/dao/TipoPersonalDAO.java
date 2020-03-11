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

import com.cystesoft.vyrbus.model.bean.TipoPersonal;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoPersonalDAO extends GenericDAO {
	public ArrayList<TipoPersonal> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoPersonal> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoPersonal buscarPorId(Long id);
	public void guardar(TipoPersonal tipoPersonal);
	public void actualizar(TipoPersonal tipoPersonal);
	public void inactivar(Long id);
}