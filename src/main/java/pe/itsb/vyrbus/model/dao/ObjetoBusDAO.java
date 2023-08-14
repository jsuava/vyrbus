/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 21/06/2012
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.ObjetoBus;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface ObjetoBusDAO extends GenericDAO {

	public ArrayList<ObjetoBus> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<ObjetoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	public ObjetoBus buscarPorId(Long id);
	public void guardar(ObjetoBus objetoBus);
	public void actualizar(ObjetoBus objetoBus);
	public void inactivar(Long id);
}