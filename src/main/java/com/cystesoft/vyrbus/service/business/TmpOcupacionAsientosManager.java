/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 14/11/2012
 */
package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;

import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;

/**
 * @author Jose
 *
 */
public interface TmpOcupacionAsientosManager {
	/**
	 * Realiza el bloqueo del asiento para realizar la venta
	 * @param tmpOcupacion	: Objeto para realiza el insert;
	 * @return 1=Exitoso, -1=Fallo
	 * @throws Exception
	 */
	public int bloquearAsiento(TmpOcupacionAsientos tmpOcupacion)throws Exception;
	/**
	 * Realiza el desbloqueo del asiento.
	 * @param tmpOcupacion	: Objeto a desbloquear.
	 * @return 1=Exitoso, -1=Fallo
	 * @throws Exception
	 */
	public int desbloquearAsiento(TmpOcupacionAsientos tmpOcupacion)throws Exception;
	/**
	 * Realiza el desbloqueo de asientos por Usuario Hardware.
	 * @param idUsuarioHardware	: Identificador del Usuario Hardware
	 * @return 1=Exitoso, -1=Fallo.
	 * @throws Exception
	 */
	public int desbloquearAsientoByUsuarioHardware(Integer idUsuarioHardware)throws Exception;
//	/**
//	 * Busca todos los asientos bloqueados de acuerdo a los criterios enviados.
//	 * @param campo		: Campo dentro de la tabla donde se buscara.
//	 * @param patron	: Texto a buscar dentro del campo.
//	 * @param estado	: Estado de los registros ACTIVOS, INACTIVOS.
//	 * @return Lista de objetos TmpOcupacionAsientos;
//	 * @throws Exception
//	 */
//	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(String campo, String patron, String estado)throws Exception;
	/**
	 * Busca los asientos bloqueados para el itierario enviado.
	 * @param idItinerario	: Identificador del itinerario.
	 * @return Lista con los asientos bloqueados.
	 * @throws Exception
	 */
	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(Long idItinerario) throws Exception;

	/**
	 * Busca por estado registro
	 * @param estado	: estado a consultar
	 * @param criterioOrden: criterios para el ordenamiento de los datos
	 * @return
	 */
	public ArrayList<TmpOcupacionAsientos> buscarPorEstadoRegistro(String estado);

	/**
	 * Realiza el desbloqueo por usuario hardware y itinerario
	 * @param idUsuarioHardware	: Identificador del usuario hardware
	 * @param idItinerario 		: Identificador del itinerario
	 * @return
	 * @throws Exception
	 */
	public int desbloquearAsientoByUsuarioHardwareAndItinerario(Integer idUsuarioHardware, Long idItinerario) throws Exception;

	/***
	 * realiza el desbloque del asiento por usuario hardware, itinerario y numero de asiento
	 * @param idUsuarioHardware	: Identificador del usuario hardware.
	 * @param idItinerario		: Identificador del itinerario.
	 * @param asiento			: Numero del asiento a desbloquear
	 * @return
	 * @throws Exception
	 */
	public int desbloquearAsiento(Integer idUsuarioHardware, Long idItinerario, Integer asiento) throws Exception;
}
