/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:18:58
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TarifaCliente;

/**
 * @author jabanto
 *
 */
public interface TarifaClienteManager {
	/**
	 * Realiza la busqueda segun el estado del registro
	 * @param estado	: estado del registro (A,I)
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaCliente> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Realiza la busqueda segun los criterios de busqueda y orden que se envien
	 * @param criteriosBusqueda	: criterios de busqueda
	 * @param criteriosOrdenar	: Criterios para ordenar el resultado de la busqueda
	 * @return
	 */
	public ArrayList<TarifaCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Realiza la busqueda por su identificador.
	 * @param id	: Identificador de la entidad
	 * @return
	 */
	public TarifaCliente buscarPorId(Long id);
	/**
	 * Guarda una nueva instacia.
	 * @param tarifaCliente
	 */
	public void guardar(TarifaCliente tarifaCliente);
	/**
	 * Actualiza un instancia
	 * @param tarifaCliente
	 */
	public void actualizar(TarifaCliente tarifaCliente);
}
