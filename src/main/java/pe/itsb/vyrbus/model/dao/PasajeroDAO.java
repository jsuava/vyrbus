/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Pasajero;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface PasajeroDAO extends GenericDAO {
	public ArrayList<Pasajero> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Pasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Pasajero buscarPorId(Long id);
	public void guardar(Pasajero pasajero);
	public void actualizar(Pasajero pasajero);
	public void inactivar(Long id);
	/**
	 * Realiza la busqueda del pasajero con el indice FullText.
	 * @param nombres	: Array con los nombres y apellidos del pasajero a buscar.
	 * @return Lista de coincidencias.
	 * @throws Exception
	 */
	public ArrayList<Pasajero> buscarPorFullTextIndex(String[] nombres)throws Exception;
}