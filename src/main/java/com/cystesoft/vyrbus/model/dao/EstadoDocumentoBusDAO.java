/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 25/08/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.EstadoDocumentoBus;

/**
 *
 * @author JA
 * @since JDK1.6
 */
public interface EstadoDocumentoBusDAO extends GenericDAO {
	public ArrayList<EstadoDocumentoBus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<EstadoDocumentoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	public EstadoDocumentoBus buscarPorId(Long id);
	public void guardar(EstadoDocumentoBus estadoDocumentoBus);
	public void actualizar(EstadoDocumentoBus estadoDocumentoBus);
	public void inactivar(Long id);

}
