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

import com.cystesoft.vyrbus.model.bean.Nacionalidad;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface NacionalidadDAO extends GenericDAO {
	public ArrayList<Nacionalidad> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Nacionalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Nacionalidad buscarPorId(Long id);
	public void guardar(Nacionalidad nacionalidad);
	public void actualizar(Nacionalidad nacionalidad);
	public void inactivar(Long id);
}