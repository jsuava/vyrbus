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

import pe.itsb.vyrbus.model.bean.EstadoCivil;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface EstadoCivilDAO extends GenericDAO {
	public ArrayList<EstadoCivil> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<EstadoCivil> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	public EstadoCivil buscarPorId(Long id);
	public void guardar(EstadoCivil estadoCivil);
	public void actualizar(EstadoCivil estadoCivil);
	public void inactivar(Long id);
}