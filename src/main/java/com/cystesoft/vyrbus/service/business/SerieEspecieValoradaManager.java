/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 20/11/2013
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.SerieEspecieValorada;

/**
 * @author JABANTO
 *
 */
public interface SerieEspecieValoradaManager {
	/**
	 * Realiza la busqueda por el Id (serie, tipoComprobante)
	 * @param numeroSerie		: Numero de Serie
	 * @param idTipoComprobante	: Identifiador del tipo de comprobante
	 * @return
	 * @throws Exception
	 */
	public SerieEspecieValorada buscarPorID(String numeroSerie, Integer idTipoComprobante) throws Exception;
	/***
	 * Realiza la buqueda segun los parametros enviados
	 * @param criteriosBusqueda	: Criterios de busqueda
	 * @param criteriosOrdenar	: Criterios para el orden de la informacion
	 * @return
	 */
	public ArrayList<SerieEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
}
