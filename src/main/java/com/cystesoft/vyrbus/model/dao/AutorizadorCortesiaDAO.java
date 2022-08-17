package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;


/**
 *
 * @author JABANTO
 *
 */
public interface AutorizadorCortesiaDAO {
	/**
	 * Realiza la busqueda por el estado registro indicado
	 * @param estado	: estado a buscar
	 * @param criterioOrden	: criterios para el orden en la data que se mostrará al usuario.
	 * @return
	 */
	public ArrayList<AutorizadorCortesia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda: criterios para la busqueda
	 * @param criteriosOrdenar : criterios para el orden en la data que se mostrará al usuario.
	 * @return
	 */
	public ArrayList<AutorizadorCortesia> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id : Identificador del Autorizador para la cortesia.
	 * @return
	 */
	public AutorizadorCortesia buscarPorId (Long id);
	/**
	 *
	 * @param autorizadorCortesia
	 */
	public void guardar(AutorizadorCortesia autorizadorCortesia);
	/**
	 *
	 * @param autorizadorCortesia
	 */
	public void actualizar(AutorizadorCortesia autorizadorCortesia);
	/**
	 *
	 * @param id : Identificador del Autorizador para la cortesia.
	 */
	public void inactivar(long id);


}
