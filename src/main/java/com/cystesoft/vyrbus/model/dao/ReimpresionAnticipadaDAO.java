/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 05/07/2016
 * Hora			: 14:54:09
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ReimpresionAnticipada;

/**
 * @author jabanto
 *
 */
public interface ReimpresionAnticipadaDAO extends GenericDAO{
	/**
	 * Realiza la busqueda por su identificador
	 * @param id : Identificador de la reimpresion antecipada
	 * @return 
	 * @throws Exception
	 */
	public ReimpresionAnticipada buscarPorID(Long id) throws Exception;
	
	/**
	 * Busqueda por estado registro
	 * @param estado		:Estado del Registro.
	 * @param criterioOrden	: Criterios para el orden de los datos.
	 * @return
	 */
	public ArrayList<ReimpresionAnticipada> buscarPorEstadoRegistro(String estado, String criterioOrden) throws Exception;
	
	/**
	 * Realiza la busqueda de la reimpresion anticipada, segun la lista de parametros que se envie
	 * @param criteriosBusqueda : Criterios para la busqueda
	 * @param criteriosOrdenar : Criterios para ordenar el resultado.
	 * @return Lista de resultados
	 */
	public ArrayList<ReimpresionAnticipada> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	/**
	 * Guarda el Centro de Costo.
	 * @param reimpresionAnticipada : Objeto que se guardara.
	 */
	public void guardar(ReimpresionAnticipada reimpresionAnticipada) throws Exception;
	
	/**
	 * Actualiza Tipo de Gasto.
	 * @param reimpresionAnticipada : Objeto que se actualizara.
	 */
	public void actualizar(ReimpresionAnticipada reimpresionAnticipada) throws Exception;
	
	/**
	 * realiza la busqueda de la autorizacion para realizar la impresion	
	 * @param agenciaID : Identificador de la agencia de quien pertenece el voucher a reimprimir
	 * @param canalVentaID : Identificaor del canal de venta de queien pertenece el voucher a reimprimir 
	 * @return 
	 * @throws Exception
	 */
	public boolean tieneAutorizacionReimpresion(Integer agenciaID, Integer canalVentaID)throws Exception;

}
