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

import com.cystesoft.vyrbus.model.bean.TipoAgencia;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoAgenciaDAO extends GenericDAO {
	public ArrayList<TipoAgencia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoAgencia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoAgencia buscarPorId(Long id);
	public void guardar(TipoAgencia tipoAgencia);
	public void actualizar(TipoAgencia tipoAgencia);
	public void inactivar(Long id);
}