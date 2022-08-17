package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.model.bean.LiquidacionOficina;

public interface LiquidacionOficinaDAO extends GenericDAO {

	/**
	 * Busca Liquidaciones pendientes de po liquidar.
	 * @param fecha		: fecha de la liquidacion a liquidar
	 * @param idAgencia	: indentificador de la agencia
	 * @return
	 * @throws Exception
	 */
	public List<Liquidacion> buscarLiquidacionPendiente(String fecha,Integer idAgencia) throws Exception;


//	/**
//	 * Busca liquidacion de oficina.
//	 * @param idUsuario : identificador del usuario
//	 * @param fecha		: fecha de la loquidacion
//	 * @param idAgencia	: identificador de la agencia
//	 * @return
//	 */
//	public List<LiquidacionOficina> buscarLiquidacionOficiana(Integer idUsuario, String fecha, Integer idAgencia);


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
