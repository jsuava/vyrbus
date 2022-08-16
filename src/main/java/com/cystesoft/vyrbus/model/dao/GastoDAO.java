package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Gasto;

/**
 * 
 * @author José Abano
 *
 */
public interface GastoDAO extends GenericDAO {
	
	/**
	 * Busqueda por estado registro
	 * @param estado		: estado 
	 * @param criterioOrden	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<Gasto> buscarPorEstadoRegistro(String estado, String criterioOrden);
	
	/**
	 * Busqueda por un ArraLis de Parametros.
	 * @param criteriosBusqueda : 
	 * @param criteriosOrdenar	: lista de criterios para el orden de los datos
	 * @return
	 */
	public ArrayList<Gasto> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
	
	/**
	 * Busca Gastos de liquidación de turno
	 * @param fechaGasto :Fecha emisión del gasto
	 * @param idTipoGasto:identificador del tipo de gasto
	 * @param idAgencia	 :identidficador de la agencia 
	 * @param idUsuario	 : login del usuario 
	 * @return
	 */
	public List<Gasto> buscarGasto(String fechaGasto, Integer idTipoGasto, Integer idAgencia,Integer idUsuario);
	/**
	 * Busca Gastos de liquidación de turno
	 * @param fechaGasto :Fecha emisión del gasto
	 * @param fechaFinGasto	: Fecha fin gasto
	 * @param idTipoGasto:identificador del tipo de gasto
	 * @param idAgencia	 :identidficador de la agencia 
	 * @param idUsuario	 : login del usuario 
	 * @return
	 */
	public List<Gasto> buscarGasto(String fechaGasto, String fechaFinGasto, Integer idTipoGasto, Integer idAgencia,Integer idUsuario);
	
	/**
	 * Busca gasto de la liquidacion de Oficina.
	 * @param fechaLiquidacion : Fecha a buscar
	 * @param usuario          : identificador del usuaritio
	 * @return
	 */
	public List<Gasto> buscarGastoLiqOficina(String fechaLiquidacion, String usuario);
	
	/**
	 * Busqueda por id.
	 * @param id : identificador del gasto
	 * @return
	 */
	public Gasto buscarXId(Long id);
	
	/**
	 * Guarda 
	 * @param Gasto
	 */
	public void guardar(Gasto gasto);
	
	/***
	 * Actualiza
	 * @param gasto
	 */
	public void actualizar(Gasto gasto);
	
	/**
	 * Inactiva Gasto
	 * @param 
	 * @param 
	 */
	public void inactivar(Long id);
	
	/**
	 * Buscar la suma total de soles en gastos de una liqudiacion
	 * @param fecha		: Fecha de la liquidación
	 * @param idUsuario	: Identicador del usuario
	 * @param idAgencia	: Identificador de la agencia
	 * @return	suma total de tdos los gastos ingresados a la liquidación
	 * @throws Exception
	 */
	public Double BuscarTotalGastos(String fecha,Integer idUsuario, Integer idAgencia)throws Exception;
	/**
	 * Obtiene los gastos de la liquidacion
	 * @param fechaLiquidacion	: Fecha de la liquidacion
	 * @param idAgencia			: Identificador de la agencia
	 * @param idUsuario			: Identificador del usuario dueño de liquidacion
	 * @param isIngreso			: 0=Gasto; 1=Ingreso
	 * @param groupByObs		: Indica si la consulta debe o no agrupar por observacion.
	 * @return
	 */
	public List<Gasto> obtenerGastosByLiquidacion(String fechaLiquidacion, Integer idAgencia, Integer idUsuario, Integer isIngreso, Boolean groupByObs);
	/**
	 * Realiza la busqueda de los gastos y/o ingresos
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFin		: Fecha fin de la busqueda
	 * @param tipoOperacion	: Tipo de Operacion. Gasto(0); Otros Ingreso(1)
	 * @return Lista de resultados
	 * @throws Exception
	 */
	public List<Gasto> buscarGastosIngresos(String fechaInicio, String fechaFin, Integer tipoOperacion)throws Exception;
}
