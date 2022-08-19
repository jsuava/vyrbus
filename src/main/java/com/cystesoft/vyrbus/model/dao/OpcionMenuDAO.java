package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;

/**
 *
 * @author José Abanto.
 *
 */

public interface OpcionMenuDAO extends GenericDAO {
	/**
	 * Busqueda por estado registro
	 * @param estado		: estado
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<OpcionMenu> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda :
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<OpcionMenu> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busqueda por id
	 * @param id : identificador del ObcionMenu
	 * @return
	 */
	public OpcionMenu buscarPorId(Long id);

	/**
	 * Guarda
	 * @param opcionMenu : Class
	 */
	public void guardar(OpcionMenu opcionMenu);

	/***
	 * Actualiza Rol
	 * @param opcionMenu : Class
	 */
	public void actualizar(OpcionMenu opcionMenu);

	/**
	 * Inactivar
	 * @param id : identificador
	 */
	public void inactivar(Long id);

	/**
	 * cosnulta menus padres
	 * @return
	 */
	public List<OpcionMenu> buscaMenusPadres();

	/**
	 *
	 * @param idMenu
	 * @param idMenuPadre
	 * @return
	 */
	public List<OpcionMenu> buscarMenus(Integer idMenuPadre, Integer idMenu);
	/**
	 * Realiza la busqueda de los menus habilitados
	 * @return Lista de Opciones de menu.
	 * @throws Exception
	 */
	public List<OpcionMenu> buscarOpcionesMenu() throws Exception;

}
