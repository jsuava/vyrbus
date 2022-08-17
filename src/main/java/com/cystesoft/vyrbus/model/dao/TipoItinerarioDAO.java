/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 12/09/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoItinerario;

/**
 *
 * @author JABANTO
 * @since JDK1.6
 */
public interface TipoItinerarioDAO extends GenericDAO {
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<TipoItinerario> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un array de criterios.
	 * @param criteriosBusqueda: Array de criterios de busqueda
	 * @param criteriosOrdenar : Lista de criterios para el Orden de los Datos
	 * @return : Array de Tipos de Itinerarios
	 */
	public ArrayList<TipoItinerario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busqueda por Id del Tipo de Itinerario
	 * @param id : Identificador del Tipo de Itinerario
	 * @return : Lista de tipos de itinerarios
	 */
	public TipoItinerario buscarPorId(Long id);

	/**
	 * Guarda el Tipo de Itinerario.
	 * @param tipoItinerario :clase TipoItinerario
	 */
	public void guardar(TipoItinerario tipoItinerario);

	/**
	 * Actualiza Tipo de Itinerario.
	 * @param tipoItinerario : Clase TipoItinerario
	 */
	public void actualizar(TipoItinerario tipoItinerario);

	/**
	 * Inactivar
	 * @param id : Identificador del TipoItinerario
	 */
	public void inactivar(Long id);
}
