/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 11:28:28
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoDetalle;


/**
 * @author Jose Abanto
 *
 */
public interface TarifaByAsientoDetalleManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaByAsientoDetalle> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TarifaByAsientoDetalle> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TarifaByAsientoDetalle buscarPorId(Long id);
	/**
	 * 
	 * @param tarifaByAsientoDetalle
	 */
	public void guardar(TarifaByAsientoDetalle tarifaByAsientoDetalle)throws Exception;
	/**
	 * 
	 * @param tarifaByAsientoDetalle
	 */
	public void actualizar(TarifaByAsientoDetalle tarifaByAsientoDetalle);
	
	/**
	 * Realiza la busqueda del detalle de la tarifa por el identificador de la tarifa
	 * @param tarifaId	: Identificador de la tarifa.
	 * @return
	 * @throws Exception
	 */
	public List<TarifaByAsientoDetalle>buscarByTarifaId(Long tarifaId)throws Exception;
	/**
	 * 
	 * @param tarifa_id
	 * @param tipoAsiento_id
	 * @param tipoPrecio_id
	 * @return
	 * @throws Exception
	 */
	public Double maxPrecioByTipoPrecio(Long tarifa_id, Integer tipoAsiento_id, Integer tipoPrecio_id)throws Exception;
	/**
	 * 
	 * @param tarifaId
	 * @param tipoAsientoId
	 * @return
	 * @throws Exception
	 */
	public List<TarifaByAsientoDetalle> buscarByTarifaId(Long tarifaId, Integer tipoAsientoId, Integer tipoPrecioId) throws Exception;
	/**
	 * Realiza la busqueda de un configuracion de asientos duplicada
	 * @param tarifaByAsientoDetalle	: Instacia del objeto tarifaDetalle
	 * @return lista de coincidencias
	 * @throws Exception
	 */
	public List<TarifaByAsientoDetalle> buscarConfigAsientosDuplicate(TarifaByAsientoDetalle tarifaByAsientoDetalle)throws Exception;
	/**
	 * Realiza la busqueda de la tarifa de presentacion
	 * @param itinerarioId	: Identificador del itinerario
	 * @param rutaId		: Identificador de la ruta
	 * @param fechaPartida	: Fecha de partida
	 * @param servicioId	: Identificador del servicio
	 * @param isAsientoSuite : 1=Suite, 0=Otros tipos de asientos
	 * @param canalVentaId	: Identificador del canal de venta
	 * @param agenciaId		: Identificador de la agencia
	 * @return
	 * @throws Exception
	 */
	public Double buscraTarifaPresentacion(Long itinerarioId, Integer rutaId, String fechaPartida, Integer servicioId, Integer isAsientoSuite, Integer canalVentaId, Integer agenciaId)throws Exception;
}
