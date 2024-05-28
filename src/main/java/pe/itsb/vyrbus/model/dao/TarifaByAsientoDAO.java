/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 21:28:43
 */
package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Tarifa;
import pe.itsb.vyrbus.model.bean.TarifaByAsiento;


/**
 * @author abant
 *
 */
public interface TarifaByAsientoDAO extends GenericDAO{
	/**
	 * 
	 * @param estado
	 * @param criterioOrden
	 * @return
	 */
	public ArrayList<TarifaByAsiento> buscarPorEstadoRegistro(String estado, String criterioOrden);
	/**
	 * 
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<TarifaByAsiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TarifaByAsiento buscarPorId(Long id);
	/**
	 * 
	 * @param tarifaByAsiento
	 */
	public void guardar(TarifaByAsiento tarifaByAsiento);
	/**
	 * 
	 * @param tarifaByAsiento
	 */
	public void actualizar(TarifaByAsiento tarifaByAsiento);
	/**
	 * Valida si ya existen tarifas creadas para las rutas y servicios en el rango de fehas enviados 
	 * @param fechaInicio	: Fecha incio
	 * @param fechaFin		: Fecha fin
	 * @param rutas_ids		: Identificadores de las rutas a validar
	 * @param servicios_ids	: Identificadores de los servicios a validar
	 * @return Lista de rutas y servicios que ya tiene tarifa creada
	 * @throws Exception
	 */
	public List<TarifaByAsiento>validarTarifasCreadas(String fechaInicio, String fechaFin, String rutas_ids, String servicios_ids)throws Exception;
	/**
	 * Realiza la busqueda de la tarifa.
	 * @param fecha			: Fecha
	 * @param ruta_Id		: Identificador de la ruta
	 * @param servicio_Id	: Identificador del servicio
	 * @return
	 * @throws Exception
	 */
	public TarifaByAsiento buscarTarifaByRutaServicio(String fecha, Integer ruta_Id, Integer servicio_Id)throws Exception;
	/**
	 * 
	 * @param fecha
	 * @param localidad_idOrigen
	 * @param localidad_idDestino
	 * @return
	 * @throws Exception
	 */
	public List<TarifaByAsiento> buscarRutasGroupByRuta(String fecha, Integer localidad_idOrigen, Integer localidad_idDestino)throws Exception;
	/**
	 * Realiza la busqueda de tarifa por fecha y servicio
	 * @param fecha			: fecha de partida
	 * @param servicio_id	: Identificador del servicio
	 * @return	Lista de resultados.
	 * @throws Exception
	 */
	public List<TarifaByAsiento> buscarTarifasByFechaServicio(String fecha, Integer servicio_id)throws Exception;

}
