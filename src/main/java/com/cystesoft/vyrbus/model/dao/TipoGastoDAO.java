package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoGasto;

/**
 *
 * @author JABANTO
 *
 */

public interface TipoGastoDAO extends GenericDAO{
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<TipoGasto> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos
	 * @return : Array de Tipos de Gasto
	 */
	public ArrayList<TipoGasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busqueda por Id del Tipo de Gasto
	 * @param id : Identificador del Tipo de Gasto
	 * @return : Lista de tipos de Tipos de Gasto
	 */
	public TipoGasto buscarPorId(Long id);

	/**
	 * Guarda el Tipo de Gasto.
	 * @param tipoGasto :clase TipoGasto
	 */
	public void guardar(TipoGasto tipoGasto);

	/**
	 * Actualiza Tipo de Gasto.
	 * @param tipoItinerario : Clase tipoGasto
	 */
	public void actualizar(TipoGasto tipoGasto);

	/**
	 * Inactivar
	 * @param id : Identificador del TipoGasto
	 */
	public void inactivar(Long id);
}
