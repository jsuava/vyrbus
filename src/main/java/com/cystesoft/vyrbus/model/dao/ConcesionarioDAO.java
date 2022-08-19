/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 23/05/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Concesionario;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface ConcesionarioDAO extends GenericDAO {
	public ArrayList<Concesionario> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Concesionario> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Concesionario buscarPorId(Long id);
	public void guardar(Concesionario concesionario);
	public void actualizar(Concesionario concesionario);
	public void inactivar(Long id);

	public List<Concesionario> buscarPorX(String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) throws Exception;
}