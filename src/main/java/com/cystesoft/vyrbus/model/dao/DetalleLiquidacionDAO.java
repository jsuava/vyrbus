package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.DetalleLiquidacion;


/**
 * 
 * @author JABANTO
 *
 */
public interface DetalleLiquidacionDAO extends GenericDAO {

	/**
	 * Guarda el detalle de la liquidación.
	 * @param detalleLiquidacion : objeto
	 * @throws Exception
	 */
	public void guarda(DetalleLiquidacion detalleLiquidacion) throws Exception;
	
	/**
	 * Actualiza Detalle de la liquidación
	 * @param detalleLiquidacion
	 * @throws Exception
	 */
	public void actualizar(DetalleLiquidacion detalleLiquidacion) throws Exception;
	
	/**
	 * Elimnia el detalle de la loquidacion, segun el idDetalleLiquidacion
	 * @param id : identificador del detalle de la liquidacion
	 */
	public void delete(Long id);
	
	/**
	 * Busca ventas a liquidar.
	 * @param fechaLiquidacion : Fecha de la liquidacion.
	 * @param idAgencia        : identificador de la agencia
	 * @param idUsuario		   : identificador del usuario
	 * @return
	 */
	public List<DetalleLiquidacion> buscarVentasALiquidar(String fechaLiquidacion, Integer idAgencia, Integer idUsuario);
	
	/**
	 * Actualiza el campo idLiquidacion de las ventas liquidadas.
	 * @param idLiquidacion	    : idDentificador de la liquidacion
	 * @param fechaLiquidacion 	: Fecha de la liquidación
	 * @param idAgencia			: identificador de la agencia
	 * @param idUsuario			: identificador del usuario
	 */
	public void actualizaIdLiquidacionVentasLiquidadas(Long idLiquidacion,String fechaLiquidacion, Integer idAgencia, Integer idUsuario);
	/**
	 * Elimina el detalle de la liquidacion, ha excepcion de los gastos
	 * @param idLiquidacion	: Identificador de la Liquidacion
	 * @throws Exception
	 */
	public void  deleteXidLiquidacion(Long idLiquidacion)throws Exception;
	
}
