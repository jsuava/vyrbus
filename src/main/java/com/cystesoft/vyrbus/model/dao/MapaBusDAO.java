/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 07/07/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.MapaBus;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public interface MapaBusDAO extends GenericDAO {
	/**
	 * Busca los objetos que conforman el mapa del bus de acuerdo al servicio enviado como parametro.
	 * @param idServicio	: Identificador del servicio.
	 * @param estado		: Estado de los registros a buscar Activos o Inactivos
	 * @return Lista del tipo MapaBus.
	 * @throws Exception
	 */
	public List<MapaBus> buscarMapaBus(Integer idServicio, String estado)throws Exception;
	/**
	 * Busca los servicios que ya tiene asignado un mapa de bus.
	 * @return Lista con los servicios.
	 * @throws Exception
	 */
	public List<MapaBus> buscarServiciosWithMapa()throws Exception;
	/**
	 * Elimina el mapa asociado a un servicio.
	 * @param idServicio	: Identificador del servicio
	 * @return -1=Fallo 1=Exito
	 * @throws Exception
	 */
	public int deleteMapaBus(Integer idServicio)throws Exception;	
}
