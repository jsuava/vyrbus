package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoGasto;

/**
 *
 * @author JABANTO
 *
 */

public interface TipoGastoManager {
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<TipoGasto> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception;

	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos
	 * @return : Array de Tipos de Gasto
	 */
	public ArrayList<TipoGasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception;

	/**
	 * Busqueda por Id del Tipo de Gasto
	 * @param id : Identificador del Tipo de Gasto
	 * @return : Lista de tipos de Tipos de Gasto
	 */
	public TipoGasto buscarPorId(Long id) throws Exception;

	/**
	 * Guarda el Tipo de Gasto.
	 * @param tipoGasto :clase TipoGasto
	 */
	public void guardar(TipoGasto tipoGasto) throws Exception;

	/**
	 * Actualiza Tipo de Gasto.
	 * @param tipoItinerario : Clase tipoGasto
	 */
	public void actualizar(TipoGasto tipoGasto) throws Exception;

	/**
	 * Inactivar
	 * @param id : Identificador del TipoGasto
	 */
	public void inactivar(Long id) throws Exception;
}
