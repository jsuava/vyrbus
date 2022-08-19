/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 27 jun. 2021
 * Hora			: 22:44:36
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.VentaServicioEspecial;


/**
 * @author Jose
 *
 */
public interface VentaServicioEspecialDAO {
	public ArrayList<VentaServicioEspecial> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<VentaServicioEspecial> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public VentaServicioEspecial buscarPorId (Long id);
	public void guardar(VentaServicioEspecial ventaServicioEspecial);
	public void actualizar(VentaServicioEspecial ventaServicioEspecial);
	public void inactivar(long id);
}
