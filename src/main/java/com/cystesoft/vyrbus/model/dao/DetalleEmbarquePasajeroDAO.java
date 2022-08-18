/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:49:30
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleEmbarquePasajero;

/**
 * @author Jose Abanto
 *
 */
public interface DetalleEmbarquePasajeroDAO extends GenericDAO{
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<DetalleEmbarquePasajero> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<DetalleEmbarquePasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public DetalleEmbarquePasajero buscarPorId(Long id);
	/**
	 *
	 * @param detalleEmbarquePasajero
	 */
	public void guardar(DetalleEmbarquePasajero detalleEmbarquePasajero);
	/**
	 *
	 * @param detalleEmbarquePasajero
	 */
	public void actualizar(DetalleEmbarquePasajero detalleEmbarquePasajero);
}
