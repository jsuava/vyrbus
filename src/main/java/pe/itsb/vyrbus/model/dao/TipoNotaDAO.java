/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 26/10/2016
 * Hora			: 15:40:02
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TipoNota;

/**
 * @author jabanto
 *
 */
public interface TipoNotaDAO extends GenericDAO{
	public ArrayList<TipoNota> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<TipoNota> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public TipoNota buscarPorId(Long id);
	public void guardar(TipoNota tipoNota);
	public void actualizar(TipoNota tipoNota);
	public void inactivar(Long id);
}
