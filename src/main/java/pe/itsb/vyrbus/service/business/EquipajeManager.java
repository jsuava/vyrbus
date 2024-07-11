/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:04:36
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Equipaje;

/**
 * @author abant
 *
 */
public interface EquipajeManager {
	/**
	 *
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<Equipaje> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Equipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 *
	 * @param id
	 * @return
	 */
	public Equipaje buscarPorId(Long id);
	/**
	 *
	 * @param equipaje
	 */
	public void guardar(Equipaje equipaje);
	/**
	 *
	 * @param equipaje
	 */
	public void actualizar(Equipaje equipaje);
	/**
	 * Busque equipajes
	 * @param fechaInicio : Fecha inicio de la busqueda
	 * @param fechaFin : Fecha fin de la busqueda
	 * @param agenciaIdEmbaruqe : Identificador de la agencia que realiza el embaruqe
	 * @param localidadIdOrigen : Identificador de la localidad origen
	 * @param localidadIdDestino : Identificador de la localidad destino
	 * @return Lista de equipajes
	 */
	public List<Equipaje> buscar(String fechaInicio, String fechaFin, Integer agenciaIdEmbaruqe, Integer localidadIdOrigen, Integer localidadIdDestino);
}
