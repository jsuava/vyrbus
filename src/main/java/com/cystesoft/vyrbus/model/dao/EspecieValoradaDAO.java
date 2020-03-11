/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.EspecieValorada;

/**
 *
 * @author jM
 * @since JDK1.6
 */
public interface EspecieValoradaDAO extends GenericDAO {
	public ArrayList<EspecieValorada> buscarPorEstadoRegistro(String estado, String criterioOrden);
	public ArrayList<EspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	public EspecieValorada buscarPorId(Long id);
	public void guardar(EspecieValorada especieValorada);
	public void actualizar(EspecieValorada especieValorada);
	public void inactivar(Long id);
	
	/**
	 * Busca la ultima seria utilizada, para la generación de los correlativos automáticos
	 * @param idTipoComprobante :Identificador del tipio de comprobante
	 * @return 
	 */
	public String buscarUltimaSerieUtilAge(Integer idTipoComprobante);
	
	/**
	 * Realiza la actualización de la especia valorada
	 * @param idTipCom	: Identificador del tipo de comprobante.	
	 * @param idAgencia	: Identifiador de la Agencia.
	 * @param serie		: Número de serie.
	 * @param correlativo	: Número de Correlativo.
	 * @throws Exception
	 */
	public void actualizarCorrelativoEspecieValorada(Integer idTipCom, Integer idAgencia, String serie, long correlativo) throws Exception;
	
	/***
	 * Busca la ultima especie valorada registrada, segun los paramentros enviados
	 * @param idTipoComprobante	: Identificador de tipo de comprobante
	 * @param numeroSerie		: Numero de serie
	 * @param idAgencia			: Identificador de la Agencia
	 * @return	especie valorada.
	 * @throws Exception
	 */
	public EspecieValorada buscarUltimaEspecieRegistrada(Integer idTipoComprobante, String numeroSerie, Integer idAgencia)throws Exception;
	/**
	 * realiza la ejecuicion del secuenciador, la cual hace la funcion del correlativo
	 * @param especieValorada	: Instancia de la clase especiaValorada.
	 * @return especieValorada
	 * @throws Exception
	 */
	public EspecieValorada ejecutarSeqCorrelativo(EspecieValorada especieValorada)throws Exception;
//	/**
//	 * Valida si el numero de serie que se va a registrar antes fue registrada en otra agencia
//	 * @param serie				: Numero de serie
//	 * @param idTipocomprobante	: Identificador del tipo de comprobante
//	 * @return
//	 * @throws Exception
//	 */
//	public EspecieValorada validaSerieOtraAgencia(String serie, Integer idTipocomprobante)throws Exception;
}