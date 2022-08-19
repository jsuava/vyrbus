/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Interfaz donde definimos las reglas del negocio para la tabla Centro de Costo VRMCENCOS.
 * Autor		: José Sullo Avalos
 * Fecha		: 16/09/2013
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.CentroCosto;

/**
 * @author Jose
 *
 */
public interface CentroCostoManager {
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<CentroCosto> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception;

	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda.
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos.
	 * @return : Array de Centros de Costo.
	 */
	public ArrayList<CentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) throws Exception;

	/**
	 * Busqueda por Id del Centro de Costo.
	 * @param id : Identificador del Centro de Costo.
	 * @return Lista de Centro de Costo.
	 */
	public CentroCosto buscarPorId(Long id) throws Exception;

	/**
	 * Guarda el Centro de Costo.
	 * @param centroCosto : Objeto que se guardara.
	 */
	public int guardar(CentroCosto centroCosto) throws Exception;

	/**
	 * Actualiza Tipo de Gasto.
	 * @param centroCosto : Objeto que se actualizara.
	 */
	public int actualizar(CentroCosto centroCosto) throws Exception;

	/**
	 * Inactivar
	 * @param id : Identificador del Centro de Costo
	 */
	public void inactivar(Long id) throws Exception;
}
