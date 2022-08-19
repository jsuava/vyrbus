/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 21/02/2017
 * Hora			: 10:27:20
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ComprobantesBloqueados;


/**
 * @author Jose Abanto
 *
 */
public interface ComprobantesBloqueadosDAO {
	/**
	 * Realiza el bloqueo del comprobante
	 * @param tmpOcupacion	: Objeto para realiza el insert;
	 * @return 1=Exitoso, -1=Fallo
	 * @throws Exception
	 */
	public int bloquearComprobante(ComprobantesBloqueados ComprobantesBloqueados)throws Exception;
	/**
	 * Realiza el desbloqueo del comprobante
	 * @param tmpOcupacion	: Objeto a desbloquear.
	 * @return 1=Exitoso, -1=Fallo
	 * @throws Exception
	 */
	public int desbloquearComprobante(ComprobantesBloqueados comprobantesBloqueados)throws Exception;

	/**
	 * Realiza el desbloqueo del comprobante por Usuario Hardware.
	 * @param idUsuarioHardware	: Identificador del Usuario Hardware
	 * @return 1=Exitoso, -1=Fallo.
	 * @throws Exception
	 */
	public int desbloquearComprobanteByUsuarioHardware(Integer idUsuarioHardware)throws Exception;
//	/**
//	 * Busca todos los asientos bloqueados de acuerdo a los criterios enviados.
//	 * @param campo		: Campo dentro de la tabla donde se buscara.
//	 * @param patron	: Texto a buscar dentro del campo.
//	 * @param estado	: Estado de los registros ACTIVOS, INACTIVOS.
//	 * @return Lista de objetos TmpOcupacionAsientos;
//	 * @throws Exception
//	 */
//	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(String campo, String patron, String estado)throws Exception;
//	/**
//	 * Busca los comprobante bloqueados para el itierario enviado.
//	 * @param idItinerario	: Identificador del itinerario.
//	 * @return Lista con los asientos bloqueados.
//	 * @throws Exception
//	 */
//	public List<ComprobantesBloqueados> buscarAsientosBloqueados(Long idItinerario) throws Exception;

	/**
	 * Busca por estado registro
	 * @param estado	: estado a consultar
	 * @param criterioOrden: criterios para el ordenamiento de los datos
	 * @return
	 */
	public ArrayList<ComprobantesBloqueados> buscarPorEstadoRegistro(String estado);


//	/**
//	 * Realiza el desbloqueo por usuario hardware y itinerario
//	 * @param idUsuarioHardware	: Identificador del usuario hardware
//	 * @param idItinerario 		: Identificador del itinerario
//	 * @return
//	 * @throws Exception
//	 */
//	public int desbloquearAsientoByUsuarioHardwareAndItinerario(Integer idUsuarioHardware, Long idItinerario) throws Exception;

	/***
	 * realiza el desbloque del comprobante por el identificador de la venta
	 * @param idVentaPasaje			: Identificador de la venta
	 * @return
	 * @throws Exception
	 */
	public int desbloquearComprobante(Long idVentaPasaje) throws Exception;
	/**
	 * Realiza la busqueda de los asientos bloqueados
	 * @param criteriosBusqueda	: criterios para la busqueda de la informacion.
	 * @param criteriosOrdenar	: Criterios para el ordenamiento de los datos
	 * @return Lista de ocurrencias.
	 */
	public ArrayList<ComprobantesBloqueados> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la busqueda por su identificador
	 * @param ventaPasajeId	: Identificador de la entidad
	 * @return
	 * @throws Exception
	 */
	public ComprobantesBloqueados buscarPorId(Long ventaPasajeId)throws Exception;
}
