package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;

public interface ManifiestoManager {

	/**
	 * Anula el manifiesto
	 * @param id :identificador del manifiesto
	 */
	public void inactivar(Long id);

	/**
	 * consulta itinerario.
	 * @param itinerario : identificador del Itinerario.
	 * @param origen	 : Localidad origen
	 * @param destino	 : Localidad destino
	 * @return
	 * @throws Exception
	 */
	public Itinerario consultaItinerario (Long idItinerario, String origen, String destino) throws Exception;

	/**
	 * consulta los pasajeros asociados al itinerarios a consultar.
	 * @param idItinerario : identificador del Itinerario
	 * @param idPruntoEmbarque: Identificador del punto de embarque
	 * @return
	 * @throws Exception
	 */
	public List<VentaPasaje> consultaPasajeros (Long idItinerario, Integer idPruntoEmbarque) throws Exception;

	/**
	 * Consulta los puntos de control del Itinerario.
	 * @param idItinerario :identificador del Itinerario
	 * @return : los puntos de control
	 * @throws Exception
	 */
	public List<Agencia> consultaPtoControl(Long idItinerario) throws Exception;

	/**
	 * Consulta detalle de pasajeros por Ruta.
	 * @param idItinerario
	 * @return
	 * @throws Exception
	 */
	public List<VentaPasaje> consultaDetaPaxXRuta(Long idItinerario, Integer agenciaIdPartida) throws Exception;

	/**
	 * Consulta datos de autorizaci�n por la Sunat. (numero de autorizaci�n, serie y munero de manifiesto)
	 * @param idAgencia : identificador de la Agencia.
	 * @return numero autorizacion de la sunat.
	 * @throws Exception
	 */
	public EspecieValorada consultaAutorizacionSunat(Integer idAgencia) throws Exception;

	/**
	 * Guarda Manifiesto
	 */
	public void guarda(Manifiesto manifiesto) throws Exception;

	/**
	 * Consulta si el manifiesto ya fue impreso
	 * @param idItinerario : identificador del Itinerario.
	 * @return : manifiesto
	 * @throws Exception
	 */
	public Manifiesto consultaMinifiestImpreso(Long idItinerario) throws Exception;

	/**
	 * Actualiza el correlativo del numero de manifiesto y las ventas con el idManifiesto
	 * @param especieValorada : Clase de donde se toma el numero del manifiesto.
	 * @param Manifiesto	  : clase Manifiesto
	 * @throws Exception
	 */
	public void updateCorrelativo(EspecieValorada especieValorada, Manifiesto manifiesto) throws Exception;

	/**
	 * Busqueda manifiestos emitidas, para el mantenimiento
	 * @param fechaInicial	: fecha inicial de la busqueda
	 * @param fechaFinal	: fecha final de la busqueda
	 * @param idOrigen		: Identificador de la localidad origen
	 * @param idDestino		: Identificador de la localidad destino
	 * @param idBus			: Idendificador del Bus.
	 * @param idAgenciaEmision	: Identificador de la agencia que realiza la emision del manifiesto
	 * @return
	 */
	public List<Manifiesto> buscarManifiesto(String fechaInicial, String fechaFinal, Integer idOrigen, Integer idDestino, Integer idBus, Integer idAgenciaEmision);
	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Manifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Busca por el identificador del manifiesto
	 * @param id	: Identificador del Manifiesto.
	 * @return manifiesto
	 * @throws Exception
	 */
	public Manifiesto buscarPorId(Long id)throws Exception;
	/**
	 * Actualiza el manifiesto
	 * @param manifiesto : instancia a actualizar
	 * @throws Exception
	 */
	public void actualizar(Manifiesto manifiesto)throws Exception;

	/**
	 * Busqueda manifiestos emitidas, para el mantenimiento
	 * @param fechaInicial	: fecha inicial de la busqueda
	 * @param fechaFinal	: fecha final de la busqueda
	 * @param per4949		: parametro otorgado por SUNAT, el usuario debe ingresarlo
	 * @param periodo		: perodo al cual pertenece el ISC
	 *
	 */
	public List<Manifiesto> buscarDevolucionIsc(String fechaInicial, String fechaFinal, String per4949, String periodo);
	
}
