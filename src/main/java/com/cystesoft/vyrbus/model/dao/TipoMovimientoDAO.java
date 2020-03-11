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

import com.cystesoft.vyrbus.model.bean.TipoMovimiento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoMovimientoDAO extends GenericDAO {
	public ArrayList<TipoMovimiento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoMovimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoMovimiento buscarPorId(Long id);
	public void guardar(TipoMovimiento condicionVenta);
	public void actualizar(TipoMovimiento condicionVenta);
	public void inactivar(Long id);
	
	
	/**
	 * Realiza la busqueda de lostipo de movimiento de acuerdo a lso criterios enviados
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las agencias separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de tipos de movimiento.
	 */
	public List<TipoMovimiento> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}