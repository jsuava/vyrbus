/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 28/11/2016
 * Hora			: 09:49:21
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.EmbarquePasajero;


/**
 * @author Jose Abanto
 *
 */
public interface EmbarquePasajeroDAO extends GenericDAO{
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<EmbarquePasajero> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<EmbarquePasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public EmbarquePasajero buscarPorId(Long id);
	/**
	 *
	 * @param embarquePasajero
	 */
	public void guardar(EmbarquePasajero embarquePasajero);
	/**
	 *
	 * @param embarquePasajero
	 */
	public void actualizar(EmbarquePasajero embarquePasajero);
}
