/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 12:23:26
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoMoneda;

/**
 * @author jabanto
 *
 */
public interface TipoMonedaManager {
	/**
	 * Realiza la busqueda por el estado del registro
	 * @param estado : Estado del registro
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TipoMoneda> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * Realiza la busqueda segun los criterios enviados
	 * @param criteriosBusqueda	: Criteros de busqueda.
	 * @param criteriosOrdenar	: Criterios para el orden de la informacion.
	 * @return
	 */
	public ArrayList<TipoMoneda> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda por su identificador de la entidad.
	 * @param id : Identificador.
	 * @return
	 */
	public TipoMoneda buscarPorId (Long id)throws Exception;
	/**
	 * Guarda una nueva instancia de un objeto
	 * @param tipoMoneda
	 */
	public void guardar(TipoMoneda tipoMoneda)throws Exception;
	/**
	 *Actualiza una instancia de un objeto
	 * @param tipoMoneda
	 */
	public void actualizar(TipoMoneda tipoMoneda)throws Exception;

}
