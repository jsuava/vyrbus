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

import pe.itsb.vyrbus.model.bean.EstadoBus;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface EstadoBusDAO extends GenericDAO {
	public ArrayList<EstadoBus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<EstadoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public EstadoBus buscarPorId(Long id);
	public void guardar(EstadoBus estadoBus);
	public void actualizar(EstadoBus estadoBus);
	public void inactivar(Long id);
}