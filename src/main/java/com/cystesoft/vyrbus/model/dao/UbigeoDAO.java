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

import com.cystesoft.vyrbus.model.bean.Ubigeo;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface UbigeoDAO extends GenericDAO {
	public ArrayList<Ubigeo> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Ubigeo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Ubigeo buscarPorId(String id);
	public void guardar(Ubigeo ubigeo);
	public void actualizar(Ubigeo ubigeo);
	public String ubicacionGeografica(Ubigeo ubigeo);
	public String ubicacionGeografica(String id);
	public void inactivar(Long id);
}