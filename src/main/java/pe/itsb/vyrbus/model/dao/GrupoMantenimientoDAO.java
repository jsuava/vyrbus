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

import pe.itsb.vyrbus.model.bean.GrupoMantenimiento;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface GrupoMantenimientoDAO extends GenericDAO {
	public ArrayList<GrupoMantenimiento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<GrupoMantenimiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public GrupoMantenimiento buscarPorId(Long id);
	public void guardar(GrupoMantenimiento grupoMantenimiento);
	public void actualizar(GrupoMantenimiento grupoMantenimiento);
	public void inactivar(Long id);
}