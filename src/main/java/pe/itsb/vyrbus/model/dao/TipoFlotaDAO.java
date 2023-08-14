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

import pe.itsb.vyrbus.model.bean.TipoFlota;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoFlotaDAO extends GenericDAO {
	public ArrayList<TipoFlota> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoFlota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoFlota buscarPorId(Long id);
	public void guardar(TipoFlota tipoFlota);
	public void actualizar(TipoFlota tipoFlota);
	public void inactivar(Long id);
}