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

import com.cystesoft.vyrbus.model.bean.Sexo;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface SexoDAO extends GenericDAO {
	public ArrayList<Sexo> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Sexo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Sexo buscarPorId(Long id);
	public void guardar(Sexo sexo);
	public void actualizar(Sexo sexo);
	public void inactivar(Long id);
}