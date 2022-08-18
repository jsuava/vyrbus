package com.cystesoft.vyrbus.model.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Liquidacion;
import com.cystesoft.vyrbus.service.mappers.ResumenComprobante;
import com.cystesoft.vyrbus.view.tuentrada.LiquidacionTuentrada;

/**
 *
 * @author José Abanto
 *
 */
public interface LiquidacionDAO extends GenericDAO {

	/**
	 * Busca por el idenficador de la entidad
	 * @param id	: Identificador de la entidad
	 * @return
	 * @throws Exception
	 */
	public Liquidacion buscarPorId(Long id)throws Exception;

	/**
	 * Apertura Liquidacion
	 * @param liquidacion
	 */
	public void aperturarLiquidacion(Liquidacion liquidacion);

	/**
	 *
	 * @param criteriosBusqueda
	 * @param criteriosOrdenar
	 * @return
	 */
	public ArrayList<Liquidacion> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);


	/**
	 * Busca Lquidacion
	 * @param fechaInicial	: Fecha inicial de la busqueda
	 * @param FechaFinal	: Fecha final de la busqueda
	 * @param idAgencia		: identificador de la agencia
	 * @param idUsuario		: identificador del usuario
	 * @param estadoLiquidacion
	 * @return
	 * @throws Exception
	 */
	public List<Liquidacion> buscarLiquidacion(String fechaInicial, String FechaFinal, Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion ) throws Exception;
	/**
	 * Realiza la busqueda de la ultima liquidacion abierta o cerrada segun el parametro.
	 * @param idAgencia			: Identificador de la agencia.
	 * @param idUsuario			: Identificador del Usuario.
	 * @param estadoLiquidacion	: Estado de la liquidacion ABIERTO o CERRADO
	 * @return Liquidacion encontrada.
	 * @throws Exception
	 */
	public Liquidacion buscarUltimaLiquidacion(Integer idAgencia, Integer idUsuario, Integer estadoLiquidacion) throws Exception;
	/**
	 * Actualiza liquidacion
	 * @param liquidacion
	 * @throws Exception
	 */
	public void actualizar(Liquidacion liquidacion)throws Exception;

	/**
	 * Busca las especies valoradas utilizadas en las ventas
	 * @param fechaLiquidacion: fecha de la liquidacion
	 * @param idAgencia	: identificador de la agencia
	 * @param idUsuario	: identidicador del usuario
	 * @return
	 */
	public List<Liquidacion> BuscarEspeciesValoradas(String fechaLiquidacion, Integer idAgencia, Integer idUsuario);

	/**
	 * Utilizado para la recuperacion de datos para generar el reporte/imprecion de la liquidacion de turno.
	 * @param fecha		: fecha de la liquidacion.
	 * @param idAgencia	: identificador de la agencia.
	 * @param idUsuario	: identificador del usuario.
	 * @return
	 */
	public Liquidacion buscarRptLiquidacionTurno(String fechaLiquidacion, Integer idAgencia, Integer idUsuario);
	/**
	 * Busca los boletos vendidos por Tuentrada y devuelve la estructura necesaria para mostrar la información.
	 * @param idAgencia			: Identificador de la agencia.
	 * @param idUsuario			: Identificador del usuario.
	 * @param fechaliquidacion	: Fecha de la liquidacion.
	 * @return
	 */
	public List<LiquidacionTuentrada> liquidacionTuentrada(Integer idAgencia, Integer idUsuario, String fechaliquidacion);
	/**
	 * Realiza la busqueda del resumen por documento para el cierre de caja
	 * @param fechaLiquidacion	: Fecha de la liquidacion
	 * @param idAgencia			: Identificador de la Agencia
	 * @param idUsuario			: Identificador del usuario propietario de la liquidacion
	 * @return
	 */
	public Map<String, ResumenComprobante> buscarResumenComprobantes(String fechaLiquidacion, Integer idAgencia, Integer idUsuario);
//	/**
//	 * Reapertura una liquidación
//	 * @param idliquidacion	: Identificador de la liquidacion.
//	 * @throws Exception
//	 */
//	public void reaperturarLiquidacion(Long idliquidacion)throws Exception;
}
