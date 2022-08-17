/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/08/2015
 * Hora			: 11:54:26
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCambio;


/**
 * @author jabanto
 *
 */
public interface TipoCambioDAO extends GenericDAO{
	/**
	 * Realiza la busqueda por el estado del registro
	 * @param estado : Estado del registro
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TipoCambio> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	/**
	 * Realiza la busqueda segun los criterios enviados
	 * @param criteriosBusqueda	: Criteros de busqueda.
	 * @param criteriosOrdenar	: Criterios para el orden de la informacion.
	 * @return
	 */
	public ArrayList<TipoCambio> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda por su identificador de la entidad.
	 * @param id : Identificador.
	 * @return
	 */
	public TipoCambio buscarPorId (Long id)throws Exception;
	/**
	 * Guarda una nueva instancia de un objeto
	 * @param tipoCambio
	 */
	public void guardar(TipoCambio tipoCambio)throws Exception;
	/**
	 *Actualiza una instancia de un objeto
	 * @param tipoCambio
	 */
	public void actualizar(TipoCambio tipoCambio)throws Exception;
	/**
	 * Realiza la busqueda del ultimo tipo de cambio ingresado.
	 * @param idTipoMoneda	: Identificador del tipo de moneda.
	 * @return TipoMoneda
	 * @throws Exception
	 */
	public TipoCambio buscarUltimoTipoCambio(Integer idTipoMoneda)throws Exception;
	/**
	 * Realiza la busqueda de los tipos de cambio, segun los parametros enviados
	 * @param fechaInicio	: Fecha inciio.
	 * @param fechaFin		: Fecha Fin.
	 * @param idTipoMoneda	: Obsional, Identificador del tipo de moneda.
	 * @return	Lista de resultados
	 * @throws Exception
	 */
	public List<TipoCambio>buscarTiposCambio(String fechaInicio, String fechaFin, Integer idTipoMoneda)throws Exception;
}
