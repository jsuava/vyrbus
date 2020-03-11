package com.cystesoft.vyrbus.service.business;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;

/**
 * 
 * @author JABANTO
 *
 */
public interface LiquidacionOficinaManager {
	/**
	 * Busca Lquidaciones pendientes de po liquidar.
	 * @param fecha		: fecha de la liquidacion a liquidar
	 * @param idAgencia	: indentificador de la agencia 
	 * @return
	 * @throws Exception
	 */
	public List<Liquidacion> buscarLiquidacionPendiente(String fecha,Integer idAgencia) throws Exception;

	/**
	 * Busca las liquidaciones del counter que ya aysn sido liquidadas
	 * @param fecha		: fecha liquidacion
	 * @param idAgencia	: identificador de la agencia
	 * @return
	 */
	public List<Liquidacion> buscarLiquidacionLiquidadas(String fecha, Integer idAgencia);
	
	
	/**
	 * Guara liquidacion de oficina
	 * @param liquidacionOficina
	 * @throws Exception
	 */
	public void  guardar(LiquidacionOficina liquidacionOficina) throws Exception; 
	
	/**
	 * Inactivar registro
	 * @param id : Identificador de la Liquidación de Ofician
	 */
	public void inactivar(Long id);
}
