/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: JA
 * Fecha		: 13/08/2012
 */
package pe.itsb.vyrbus.model.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Bus;

/**
 *
 * @author JA
 * @since JDK1.6
 */
public interface BusDAO {
	public ArrayList<Bus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<Bus> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public Bus buscarPorId (Long id);
	public void guardar(Bus bus);
	public void actualizar(Bus bus);
	public void inactivar(long id);


}
