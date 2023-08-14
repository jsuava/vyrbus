/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 26/08/2014
 * Hora			: 08:45:22
 */
package pe.itsb.vyrbus.model.dao;

import java.util.List;

import pe.itsb.vyrbus.model.bean.MTCDetalleRuta;

/**
 * @author JABANTO
 *
 */
public interface MTCDetalleRutaDAO {

	/**
	 * Busca por el identificador de la Ruta registra en el sisvyr
	 * @param idRutaVyr	: Identificador de la ruta
	 * @return	MTCDetalleRuta
	 * @throws Exception
	 */
	public MTCDetalleRuta buscarPorIdRuta(Integer idRutaVyr)throws Exception;
	/**
	 * Guarda el detalle de la ruta
	 * @param detalleRuta
	 * @throws Exception
	 */
	public void guardar(MTCDetalleRuta detalleRuta)throws Exception;
	/**
	 * Actualiza el detalle de la ruta.
	 * @param detalleRuta
	 * @throws Exception
	 */
	public void actualizar(MTCDetalleRuta detalleRuta)throws Exception;
	/**
	 * Busca el detalle de la ruta
	 * @param idRutaMtc
	 * @return
	 * @throws Exception
	 */
	public List<MTCDetalleRuta> buscarPorIdRutaMtc(Integer idRutaMtc)throws Exception;
	/**
	 * Busca el detalle de la rutaMTC por estado
	 * @param estado	: Estado
	 * @return Lista de rutas asociadas
	 * @throws Exception
	 */
	public List<MTCDetalleRuta> buscarPorEstado(String estado)throws Exception;
}
