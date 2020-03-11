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

import com.cystesoft.vyrbus.model.bean.NumeroFlota;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface NumeroFlotaDAO extends GenericDAO {
	public ArrayList<NumeroFlota> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<NumeroFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public NumeroFlota buscarPorId(Long id);
	public void guardar(NumeroFlota numeroFlota);
	public void actualizar(NumeroFlota numeroFlota);
	public void inactivar(Long id);
}