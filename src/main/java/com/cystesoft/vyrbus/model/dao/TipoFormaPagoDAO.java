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

import com.cystesoft.vyrbus.model.bean.TipoFormaPago;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoFormaPagoDAO extends GenericDAO {
	public ArrayList<TipoFormaPago> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoFormaPago> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoFormaPago buscarPorId(Long id);
	public void guardar(TipoFormaPago tipoFormaPago);
	public void actualizar(TipoFormaPago tipoFormaPago);
	public void inactivar(Long id);
}