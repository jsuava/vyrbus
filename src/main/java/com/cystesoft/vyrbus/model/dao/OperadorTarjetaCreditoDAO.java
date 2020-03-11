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

import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface OperadorTarjetaCreditoDAO extends GenericDAO {
	public ArrayList<OperadorTarjetaCredito> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<OperadorTarjetaCredito> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public OperadorTarjetaCredito buscarPorId(Long id);
	public void guardar(OperadorTarjetaCredito operadorTarjetaCredito);
	public void actualizar(OperadorTarjetaCredito operadorTarjetaCredito);
	public void inactivar(Long id);
}