package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TipoCobranza;


/**
 * 
 * @author JABANTO
 *
 */
public interface TipoCobranzaDAO extends GenericDAO {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TipoCobranza> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Buscar tipo de cobranza a travez de un array de criterios.
	 * @param criteriosBusqueda	: array de criterios para la busqueda.
	 * @param criteriosOrdenar	: arrey de criterios para el ordenamiento de datos
	 * @return
	 */
	public ArrayList<TipoCobranza> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TipoCobranza buscarPorId(Long id);
	/**
	 * 
	 * @param tipoCobranza
	 */
	public void guardar(TipoCobranza tipoCobranza);
	/**
	 * 
	 * @param tipoCobranza
	 */
	public void actualizar(TipoCobranza tipoCobranza);
	/**
	 * 
	 * @param id
	 */
	public void inactivar(Long id);
}
