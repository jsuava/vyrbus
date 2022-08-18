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

import com.cystesoft.vyrbus.model.bean.TipoComprobante;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoComprobanteDAO extends GenericDAO {
	public ArrayList<TipoComprobante> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoComprobante> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoComprobante buscarPorId(Long id);
	public void guardar(TipoComprobante tipoComprobante);
	public void actualizar(TipoComprobante tipoComprobante);
	public void inactivar(Long id);
}