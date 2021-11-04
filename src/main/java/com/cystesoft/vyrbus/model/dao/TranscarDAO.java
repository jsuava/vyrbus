/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 26 oct. 2021
 * Hora			: 10:24:58
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.List;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.TranscarLiquidacionTurno;
import com.cystesoft.vyrbus.model.bean.TranscarRolUsuario;
import com.cystesoft.vyrbus.model.bean.TranscarUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;

/**
 * @author abant
 *
 */
public interface TranscarDAO {
	
	/**
	 * Realiza la busqueda de todos los roles
	 * @return
	 * @throws Exception
	 */
	public List<TranscarRolUsuario> buscarRolesUsuario()throws Exception;
	
	/**
	 * Realiza la busqueda de los roles asignados a un usuario
	 * @param usuarioId	: Identificador del usuario
	 * @return
	 * @throws Exception
	 */
	public List<TranscarRolUsuario> buscarRolesUsuario(Integer usuarioId) throws Exception;
	/**
	 * Realiza la busqueda de un usuario por su Login
	 * @param login	: Login del usuario a buscar
	 * @return
	 * @throws Exception
	 */
	public TranscarUsuarioPersonal buscarUsuarioPersonal(String login)throws Exception;
	/**
	 * Guardar o actualiza un usuario
	 * @param transcarUsusrioPeronal	: Instancia de las class
	 * @param isNuevo	: indica si el usuario es nuevo(true) o ya existe (false)
	 * @throws Exception
	 */
	public void guardarUsuarioPersonal(TranscarUsuarioPersonal transcarUsusrioPeronal, String idsRoles, boolean isNuevo)throws Exception;
	/**
	 * Realizala busqueda del identificado de la agencia de carga, a travez del codigo de agencia de pasajes
	 * @param codigoAgenciaPasajes : codigo de la agencia en pasajes.
	 * @return identificador de la agencia en carga
	 * @throws Exception
	 */
	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes)throws Exception;
	/**
	 * Realiza la apertura de la liquidacion de turno
	 * @param liquidacionTurno	: instancia de la class
	 * @return Si es correcto Null, lo contrario cuando ocurre un error 
	 * @throws Exception
	 */
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno)throws Exception;
	/**
	 * Realiza la busqueda del detalle de ventas 
	 * @param usuarioId	: Identificador del usuario - Transcar
	 * @param agenciaId	: Identificador de la agencia - Transcar
	 * @param fechaInicial	: Fecha inicial de la busqueda
	 * @param fechaFinal	: Fecha Final de la busqueda
	 * @return lista de resultados
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalleVentas(Integer usuarioId, Integer agenciaId, String fechaInicial, String fechaFinal)throws Exception;
	/**
	 * Realiza la busqueda de todas las agencias
	 * @return
	 * @throws Exception
	 */
	public List<Agencia> buscarAgencias()throws Exception;
	/**
	 * Realiza la busqueda de todos los usuarios asociados a una determinada agencia, en funcion a la venta
	 * @param agenciaId		: Identificador de la agencia
	 * @param fechaInicio	: Fecha incio de la busqueda
	 * @param fechaFin		: fecha fin de la busqueda
	 * @return Lista de resultados
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)throws Exception;
}
