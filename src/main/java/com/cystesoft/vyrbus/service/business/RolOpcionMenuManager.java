package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.RolOpcionMenu;
import com.cystesoft.vyrbus.model.bean.RolOpcionMenuID;

/**
 *
 * @author José Abanto
 *
 */
public interface RolOpcionMenuManager {

	/**
	 * Busqueda por estado registro
	 * @param estado		: estado
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<RolOpcionMenu> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda :
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<RolOpcionMenu> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);


	/**
	 * Guarda
	 * @param rolOpcionMenu : Class
	 */
	public void guardar(RolOpcionMenu rolOpcionMenu);

	/***
	 * Actualiza RolOpcionMenu
	 * @param rolOpcionMenu : Class
	 */
	public void actualizar(RolOpcionMenu rolOpcionMenu);

	/**
	 * Activa y/o deshactiva RolOpcionMenu
	 * @param rolOpcionMenuID	: identificador
	 * @param estado 			: Habilitado - Deshavilitado.
	 */
	public void inactivarActivar(RolOpcionMenuID rolOpcionMenuID, String estado);


}
