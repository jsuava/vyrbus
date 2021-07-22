/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 14 jul. 2021
 * Hora			: 12:06:12
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleEquipaje;
import com.cystesoft.vyrbus.model.bean.Equipaje;

/**
 * @author abant
 *
 */
public interface DetalleEquipajeManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<DetalleEquipaje> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<DetalleEquipaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DetalleEquipaje buscarPorId(Long id);
	/**
	 * 
	 * @param detalleEquipaje
	 */
	public void guardar(DetalleEquipaje detalleEquipaje);
	/**
	 * 
	 * @param detalleEquipaje
	 */
	public void actualizar(DetalleEquipaje detalleEquipaje);
	/**
	 * 
	 * @param listDetalleEquipaje
	 * @param equipaje
	 * @throws Exception
	 */
	public void guardar(List<DetalleEquipaje> listDetalleEquipaje, Equipaje equipaje)throws Exception;
	/**
	 * Realiza la busqueda del manifiesto de pasajeros
	 * @param itinerarioId : Identificador del Itinerario.
	 * @param agenciaId		: Identificador de la Agencia.
	 * @return
	 * @throws Exception
	 */
	public List<DetalleEquipaje> buscarManifiestoEquipaje(Long itinerarioId, Integer agenciaId)throws Exception;
}
