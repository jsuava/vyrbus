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

import com.cystesoft.vyrbus.model.bean.TipoDocumento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoDocumentoDAO extends GenericDAO {
	public ArrayList<TipoDocumento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoDocumento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoDocumento buscarPorId(Long id);
	public void guardar(TipoDocumento tipoDocumento);
	public void actualizar(TipoDocumento tipoDocumento);
	public void inactivar(Long id);
}