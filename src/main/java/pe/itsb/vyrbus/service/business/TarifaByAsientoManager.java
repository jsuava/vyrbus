/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 22:07:54
 */
package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.TarifaByAsiento;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;


/**
 * @author abant
 *
 */
public interface TarifaByAsientoManager {
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
	 * @param TarifaByAsiento
	 */
	public void guardar(TarifaByAsiento TarifaByAsiento);
	/**
	 * 
	 * @param TarifaByAsiento
	 */
	public void actualizar(TarifaByAsiento TarifaByAsiento);
	/**
	 * 
	 * @param listTarifaByAsientos
	 * @return
	 * @throws Exception
	 */
	public int guardar(List<TarifaByAsiento> listTarifaByAsientos, TarifaByAsientoPlantillaEncabezado  TarifaByAsientoPlantillaEncabezado, boolean isReemplazarPromos)throws Exception;
	/**
	 * Realiza el incremento o rebaja de las TarifaByAsientos
	 * @param listTarifaByAsientos	: Lista de TarifaByAsientos a actualizar
	 * @return
	 * @throws Exception
	 */
	public int guardarIncrementoRebaja(List<TarifaByAsiento> listTarifaByAsientos)throws Exception;
	/**
	 * Valida si ya existen TarifaByAsientos creadas para las rutas y servicios en el rango de fehas enviados 
	 * @param fechaInicio	: Fecha incio
	 * @param fechaFin		: Fecha fin
	 * @param rutas_ids		: Identificadores de las rutas a validar
	 * @param servicios_ids	: Identificadores de los servicios a validar
	 * @return Lista de rutas y servicios que ya tiene TarifaByAsiento creada
	 * @throws Exception
	 */
	public List<TarifaByAsiento>validarTarifaByAsientoCreadas(String fechaInicio, String fechaFin, String rutas_ids, String servicios_ids)throws Exception;
	/**
	 * Realiza la busqueda de la TarifaByAsiento.
	 * @param fecha			: Fecha
	 * @param ruta_Id		: Identificador de la ruta
	 * @param servicio_Id	: Identificador del servicio
	 * @return
	 * @throws Exception
	 */
	public TarifaByAsiento buscarTarifaByAsientoByRutaServicio(String fecha, Integer ruta_Id, Integer servicio_Id)throws Exception;
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
	 * Realiza la busqueda de TarifaByAsiento por fecha y servicio
	 * @param fecha			: fecha de partida
	 * @param servicio_id	: Identificador del servicio
	 * @return	Lista de resultados.
	 * @throws Exception
	 */
	public List<TarifaByAsiento> buscarTarifaByAsientosByFechaServicio(String fecha, Integer servicio_id)throws Exception;

}
