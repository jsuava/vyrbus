/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 30/01/2019
 * Hora			: 13:33:23
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle;


/**
 * @author Jose Abanto
 *
 */
public interface TarifaByAsientoPlantillaEncabezadoDetalleManager {
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaEncabezadoDetalle> buscarPorX (TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TarifaByAsientoPlantillaEncabezadoDetalle buscarPorId (Long id);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaEncabezadoDetalle
	 */
	public void guardar(TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaEncabezadoDetalle
	 */
	public void actualizar(TarifaByAsientoPlantillaEncabezadoDetalle tarifaByAsientoPlantillaEncabezadoDetalle);
}
