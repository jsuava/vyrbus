/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/01/2017
 * Hora			: 15:33:15
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoPrecio;

/**
 * @author Jose Abanto
 *
 */
public interface TipoPrecioManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TipoPrecio> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TipoPrecio> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public TipoPrecio buscarPorId(Long id);

	/**
	 *
	 * @param tipoPrecio
	 */
	public void guardar(TipoPrecio tipoPrecio);

	/**
	 *
	 * @param tipoPrecio
	 */
	public void actualizar(TipoPrecio tipoPrecio);

	/**
	 *
	 * @param id
	 */
	public void inactivar(Long id);
}
