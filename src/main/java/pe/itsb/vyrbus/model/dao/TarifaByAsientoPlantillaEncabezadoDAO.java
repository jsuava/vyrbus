/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 29/01/2019
 * Hora			: 09:35:13
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;



/**
 * @author Jose Abanto
 *
 */
public interface TarifaByAsientoPlantillaEncabezadoDAO extends GenericDAO{
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TarifaByAsientoPlantillaEncabezado> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TarifaByAsientoPlantillaEncabezado buscarPorId(Long id);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaEncabezado
	 */
	public void guardar(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado);
	/**
	 * 
	 * @param tarifaByAsientoPlantillaEncabezado
	 */
	public void actualizar(TarifaByAsientoPlantillaEncabezado tarifaByAsientoPlantillaEncabezado);
}
