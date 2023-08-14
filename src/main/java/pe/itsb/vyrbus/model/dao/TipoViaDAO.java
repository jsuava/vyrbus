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

import pe.itsb.vyrbus.model.bean.TipoVia;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface TipoViaDAO extends GenericDAO {
	public ArrayList<TipoVia> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoVia> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoVia buscarPorId(Long id);
	public void guardar(TipoVia tipoVia);
	public void actualizar(TipoVia tipoVia);
	public void inactivar(Long id);
}