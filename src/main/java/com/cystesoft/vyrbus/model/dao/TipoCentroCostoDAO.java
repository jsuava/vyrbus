/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 22/07/2015
 * Hora			: 09:25:59
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCentroCosto;


/**
 * @author jabanto
 *
 */
public interface TipoCentroCostoDAO extends GenericDAO{
	/**
	 * Busca por el estado del registro.
	 * @param estado	: Estado por el cual se debe realizar la busqueda
	 * @param criterioOrden : Critero para establecer el oreden de los resultados
	 * @return
	 */
	public ArrayList<TipoCentroCosto> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Busca segun los parametros enviados
	 * @param criteriosBusqueda : Parametros de busqueda
	 * @param criteriosOrdenar	: Lista de Criteros para establecer el oreden de los resultados
	 * @return
	 */
	public ArrayList<TipoCentroCosto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Busac por su identificador
	 * @param id : Identificador de la entidad
	 * @return
	 */
	public TipoCentroCosto buscarPorId(Long id);
	/**
	 * Guarda una nueva instancia
	 * @param tipoCentroCosto
	 */
	public void guardar(TipoCentroCosto tipoCentroCosto);
	/**
	 * Actualia una instancia
	 * @param tipoCentroCosto
	 */
	public void actualizar(TipoCentroCosto tipoCentroCosto);
}
