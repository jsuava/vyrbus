/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 01/09/2016
 * Hora			: 18:18:29
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.PoolLocalidad;

/**
 * @author jabanto
 *
 */
public interface PoolLocalidadManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<PoolLocalidad> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<PoolLocalidad> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public PoolLocalidad buscarPorId(Long id);
	/**
	 *
	 * @param localidadIntegracion
	 */
	public void guardar(PoolLocalidad localidadIntegracion);
	/**
	 *
	 * @param localidadIntegracion
	 */
	public void actualizar(PoolLocalidad localidadIntegracion);
	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);
	/**
	 *
	 * @param localidadIDTepsa
	 * @return
	 */
	public List<PoolLocalidad> buscarByLocalidadID(Integer localidadIDTepsa);
}
