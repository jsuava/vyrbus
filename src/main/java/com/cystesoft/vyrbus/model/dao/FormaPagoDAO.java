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

import com.cystesoft.vyrbus.model.bean.FormaPago;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface FormaPagoDAO extends GenericDAO {
	public ArrayList<FormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<FormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public FormaPago buscarPorId(Long id);
	public void guardar(FormaPago formaPago);
	public void actualizar(FormaPago formaPago);
	public void inactivar(Long id);
}