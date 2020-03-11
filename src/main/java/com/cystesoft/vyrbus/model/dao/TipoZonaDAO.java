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

import com.cystesoft.vyrbus.model.bean.TipoZona;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoZonaDAO extends GenericDAO {
	public ArrayList<TipoZona> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoZona> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoZona buscarPorId(Long id);
	public void guardar(TipoZona tipoZona);
	public void actualizar(TipoZona tipoZona);
	public void inactivar(Long id);
}