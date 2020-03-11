/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 17/09/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValoradaID;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public interface ControlEspecieValoradaDAO extends GenericDAO {
	/**
	 * Realiza la busqueda por un conjunto de criterios de busqueda.
	 * @param criteriosBusqueda	: Criterios por los cuales se realiza la busqueda.
	 * @param criteriosOrdenar	: Criterios utilizados para ordenar los registros.
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<ControlEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	/**
	 * Realiza la inserción del objeto enviado como parametro.
	 * @param controlEspecieValorada	: Objeto a insertar.
	 * @return -1 Fallo, 1 Correcto
	 */
	public int guardar(ControlEspecieValorada controlEspecieValorada)throws Exception;
	/**
	 * Actualiza el objeto enviado como parametro.
	 * @param controlEspecieValorada	: Objeto a actualizar. 
	 * @return -1 Fallo, 1 Correcto
	 */
	public int actualizar(ControlEspecieValorada controlEspecieValorada)throws Exception;
	/**
	 * Realiza la busqueda del ultimo correlativo de la especie valorada.
	 * @param idTipCom		: Identificador del tipo de comprobante.
	 * @param idUsuHar		: Identificador del usuario Hardware.
	 * @param serie			: Serie de la especie valorada.
	 * @return Especie valorada con el ultimo correlativo.
	 * @throws Exception
	 */
	public ControlEspecieValorada buscarUltimoCorrelativoEspecieValorada(Integer idTipCom, Integer idUsuHar, String serie)throws Exception;
	/**
	 * Realiza la actualización del correlativo de la especie valorada.
	 * @param idTipCom		: Identificador del tipo de comprobante.
	 * @param idUsuHar		: Identificador del usuario Hardware.
	 * @param serie			: Serie de la especie valorada.
	 * @param correlativo	: Numero la la especie valorada que se actualizara.
	 * @return -1 Fallo, 1 Correcto
	 * @throws Exception
	 */
	public int actualizarCorrelativoEspecieValorada(Integer idTipCom, Integer idUsuHar, String serie, long correlativo)throws Exception;
	
	/**
	 * Inactivar Contorl Especie Valorada
	 * @param controlEspecieValoradaID : identifocador de ControlEspecieValorada.
	 * @throws Exception
	 */
	public void inactivar (ControlEspecieValoradaID controlEspecieValoradaID) throws Exception;
	
	/**
	 * busca Control especies valoradas
	 * @param idAgencia			: Obsional, Identificador de la agencia
	 * @param idTipoComprobante	: Obsional, Identificador del Tipo de comprobante 
	 * @param idUsuarioHarware	: Obsional, Identificador del Usuario Hardware
	 * @return
	 * @throws Exception
	 */
	public List<ControlEspecieValorada> buscarEspecieValoradas(Integer idAgencia, Integer idTipoComprobante, Integer idUsuarioHarware) throws Exception;
	
	/**
	 * Valida que el numero rango de correlativo ingresado no haga intersección con algún otro rango ingresado por otro usuario
	 * @param idUsuarioHardware	: Identificador del Usuario Hardware.
	 * @param serie				: Número de Serie.
	 * @param inicial			: Correlativo Inicial.
	 * @param Final				: Correlativo Final
	 * @return
	 * @throws Exception
	 */
	public List<ControlEspecieValorada> validaEVOtrasCajas(Integer idUsuarioHardware,String serie, String inicial, String Final) throws Exception;
	/**
	 * Busca las especies valoradas asignadas por maquina de acuerdo a la agencia.
	 * @param idAgencia	: Identificador de la agencia.
	 * @return Lista de Maquinas Pc con sus respectivas especies valoradas asignadas.
	 * @throws Exception
	 */
	public List<ControlEspecieValorada> buscarEspecieValoradaPorAgencia(Integer idAgencia)throws Exception;
}
