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

import com.cystesoft.vyrbus.model.bean.DescuentoRecargo;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface DescuentoRecargoDAO extends GenericDAO {
	public ArrayList<DescuentoRecargo> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<DescuentoRecargo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public DescuentoRecargo buscarPorId(Long id);
	public void guardar(DescuentoRecargo descuentoRecargo);
	public void actualizar(DescuentoRecargo descuentoRecargo);
	public void inactivar(Long id);
}