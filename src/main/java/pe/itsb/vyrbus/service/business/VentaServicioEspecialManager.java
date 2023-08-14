/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 22:46:54
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.VentaServicioEspecial;

/**
 * @author Jose
 *
 */
public interface VentaServicioEspecialManager {
	public ArrayList<VentaServicioEspecial> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<VentaServicioEspecial> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public VentaServicioEspecial buscarPorId (Long id)throws Exception;
	public int guardar(VentaServicioEspecial ventaServicioEspecial)throws Exception;
	public void actualizar(VentaServicioEspecial ventaServicioEspecial)throws Exception;
	public void inactivar(long id)throws Exception;
}
