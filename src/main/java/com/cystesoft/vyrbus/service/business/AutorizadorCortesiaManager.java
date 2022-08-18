package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;

/**
 *
 * @author JABANTO
 *
 */
public interface AutorizadorCortesiaManager {
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
	public void guardar(AutorizadorCortesia autorizadorCortesia) throws Exception;
	/**
	 *
	 * @param autorizadorCortesia
	 */
	public void actualizar(AutorizadorCortesia autorizadorCortesia) throws Exception;
	/**
	 *
	 * @param id : Identificador del Autorizador para la cortesia.
	 */
	public void inactivar(long id);

}
