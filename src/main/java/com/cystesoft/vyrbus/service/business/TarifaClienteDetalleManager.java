/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 07/07/2016
 * Hora			: 11:19:15
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.TarifaClienteDetalle;

/**
 * @author jabanto
 *
 */
public interface TarifaClienteDetalleManager {
	/**
	 * Realiza la busqueda segun el estado del registro
	 * @param estado	: estado del registro (A,I)
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaClienteDetalle> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * Realiza la busqueda segun los criterios de busqueda y orden que se envien
	 * @param criteriosBusqueda	: criterios de busqueda
	 * @param criteriosOrdenar	: Criterios para ordenar el resultado de la busqueda
	 * @return
	 */
	public ArrayList<TarifaClienteDetalle> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Realiza la busqueda por su identificador.
	 * @param id	: Identificador de la entidad
	 * @return
	 */
	public TarifaClienteDetalle buscarPorId(Long id);
	/**
	 * Guarda una nueva instacia.
	 * @param tarifaClienteDetalle
	 */
	public void guardar(TarifaClienteDetalle tarifaClienteDetalle);
	/**
	 * Actualiza un instancia
	 * @param tarifaClienteDetalle
	 */
	public void actualizar(TarifaClienteDetalle tarifaClienteDetalle);
	/**
	 * Realiza la busqueda del tarifario personalizado del cliente.
	 * @param clienteID	: Identificador del Cliente.
	 * @param rutaID	: Identificador de la Ruta.
	 * @param servicioID	: Identificador del servicio.
	 * @param fechaPartida	: Fecha de partida del servicio
	 * @return Lista con el detalle de la tarifa.
	 * @throws Exception
	 */
	public List<TarifaClienteDetalle> buscarTarifaPersonalizada(Long clienteID, Integer rutaID, Integer servicioID, String fechaPartida)throws Exception;
	/**
	 * Realiza la busqueda del tarifario personalizado del cliente.
	 * @param clienteID	: Identificador del Cliente.
	 * @param rutaID	: Identificador de la Ruta.
	 * @param servicioID	: Identificador del servicio.
	 * @param fechaPartida	: Fecha de partida del servicio
	 * @param asiento	: Numero del Asiento Seleccionado
	 * @return detalle de la tarifa.
	 * @throws Exception
	 */
	public TarifaClienteDetalle buscarTarifaPersonalizada(Long clienteID, Integer rutaID, Integer servicioID, String fechaPartida, Integer asiento)throws Exception;
	/**
	 * Realiza la busqueda por del ruc del cliente
	 * @param ruc	: número de ruc del Cliente
	 * @return Lista de resultados
	 * @throws Exception
	 */
	public List<TarifaClienteDetalle> buscarByRuc(String ruc)throws Exception;
}
