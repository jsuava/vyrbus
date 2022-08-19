package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Rol;

/**
 *
 * @author JABANTO
 *
 */
public interface RolManager {
	/**
	 * Busqueda por estado registro
	 * @param estado		: estado rol
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<Rol> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda :
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<Rol> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Busqueda por id del Rol
	 * @param id : identificador del Rol
	 * @return
	 */
	public Rol buscarPorId(Long id);

	/**
	 * Guarda Rol
	 * @param rol : Class
	 * @throws Exception
	 */
	public void guardar(Rol rol) throws Exception;

	/***
	 * Actualiza Rol
	 * @param formaPago : Class
	 * @throws Exception
	 */
	public void actualizar(Rol rol) throws Exception;

	/**
	 * Inactiva Rol
	 * @param id
	 */
	public void inactivar(Long id);


}
