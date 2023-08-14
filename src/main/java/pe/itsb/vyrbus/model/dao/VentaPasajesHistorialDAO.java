/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas.
 * Descripci�n	: Declaraci�n de los metodos relacionados con la Venta de Pasajes.
 * Autor		: Jos� Sullo Avalos
 * Fecha		: 05/07/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaPasajeHistorial;

/**
 *
 * @author
 * @since JDK1.6
 */
public interface VentaPasajesHistorialDAO extends GenericDAO {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorEstadoRegistro(String estado, String criterioOrden);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<VentaPasajeHistorial> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 *
	 * @param id
	 * @return
	 */
	public VentaPasajeHistorial buscarPorId(Long id);

	/**
	 *
	 * @param ventaPasajeHistorial
	 */
	public void guardar(VentaPasajeHistorial ventaPasajeHistorial);

	/**
	 *
	 * @param ventaPasajeHistorial
	 */
	public void actualizar(VentaPasajeHistorial ventaPasajeHistorial);
}
