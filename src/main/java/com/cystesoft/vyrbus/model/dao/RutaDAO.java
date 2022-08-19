/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Ruta;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface RutaDAO extends GenericDAO {
	public ArrayList<Ruta> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Ruta> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Ruta buscarPorId(Long id);
	public void guardar(Ruta ruta);
	public void actualizar(Ruta ruta);
	public void inactivar(Long id);
	/**
	 * Realiza la busqueda de las Rutas que participan de la promocion.
	 * @param campo				: Campo que debera cumplir con los criterios enviados.
	 * @param criterios			: Identificadores de las rutas separados por comas.
	 * @param criteriosOrdenar	: Criterios a utilizar para ordenar la informacion.
	 * @param estadoRegistro	: Estado de los registros.
	 * @return Lista de rutas.
	 */
	public List<Ruta> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}