package com.cystesoft.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.EspecieValorada;

public interface EspecieValoradaManager {
	public ArrayList<EspecieValorada> buscarPorEstadoRegistro(String estado, String criterioOrden)throws Exception;
	public ArrayList<EspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception;
	public EspecieValorada buscarPorId(Long id)throws Exception;
	/**
	 * Guarda una nueva instancia de una especia valorada  
	 * @param especieValorada		: Objeto especie valorada
	 * @param confirmaDuplicidadSerie	: true cuanto el usuario confirma que desea utilizar la misma serie aun cuando esta ya este registrada
	 * en otra agencia
	 * @throws Exception
	 */
	public void guardar(EspecieValorada especieValorada)throws Exception;
	
	public void actualizar(EspecieValorada especieValorada)throws Exception;
	
	public void inactivar(Long id)throws Exception;
	
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
	 * @return
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
	public EspecieValorada buscarUltimaEspecieRegistrada(Integer idTipoComprobante, String numeroSerie,Integer idAgencia)throws Exception;
	/**
	 * realiza la ejecuicion del secuenciador, la cual hace la funcion del correlativo
	 * @param especieValorada	: Instancia de la clase especiaValorada.
	 * @return especieValorada
	 * @throws Exception
	 */
	public EspecieValorada ejecutarSeqCorrelativo(EspecieValorada especieValorada)throws Exception;
}
