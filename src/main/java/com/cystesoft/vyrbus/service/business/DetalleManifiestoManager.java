/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José
 * Fecha		: 09/11/2013
 */
package com.cystesoft.vyrbus.service.business;

import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.DetalleManifiesto;

/**
 * @author JABANTO
 *
 */
public interface DetalleManifiestoManager {

	public List<DetalleManifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);

	/**
	 * Guarda el detalle del manifiesto
	 * @param detalleManifiesto : Obejto DetalleManifiesto
	 * @throws Exception
	 */
	public void guradar(DetalleManifiesto detalleManifiesto)throws Exception;

	/**
	 * Actualiza el detalle del manifiesto
	 * @param detalleManifiesto
	 * @throws Exception
	 */
	public void actualizar(DetalleManifiesto detalleManifiesto)throws Exception;

	/***
	 * Busca los pasajeros para el embarque, que esten dentro del manifiesto previamente impreso.
	 * @param idManifiesto			: Identificador del Manifiesto
	 * @param idLocalidadOrigen		: Identificador de la localidad de Origen
	 * @param idAgenciaPuntoEmbarque: Opcional, Identificador de la agencia de punto de embarque
	 * @return Lista de Pasajeros a Embarcar
	 * @throws Exception
	 */
	public List<DetalleManifiesto> buscarPasajeros(Long idManifiesto,Integer idLocalidadOrigen, Integer idAgenciaPuntoEmbarque)throws Exception;

	/**
	 * Realiza el cierre del despacho de pasajeros
	 * @param idAgencia		: Identificador la agencia donde se estan embarcando los pasajeros
	 * @param idManifiesto	: Identificador del Manifiesto
	 * @param idsVentas		: Array con los ids de las ventas a actualizar
	 * @throws Exception
	 */
	public void cerrarDespachoPasajeros(Integer idAgencia, Long idManifiesto, String idsVentas) throws Exception;

	/**
	 * Valida si una venta tiene o no manifiesto
	 * @param idVenta : Identificador de la Venta
	 * @return true si la venta tiene manifiesto, false lo contrario
	 * @throws Exception
	 */
	public Boolean validarVentaManifiesto(Long idVenta)throws Exception;
}
