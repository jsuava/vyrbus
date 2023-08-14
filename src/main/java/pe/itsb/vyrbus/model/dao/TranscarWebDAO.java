/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 2 may. 2022
 * Hora			: 22:43:53
 */
package pe.itsb.vyrbus.model.dao;

import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.TranscarLiquidacionTurno;
import pe.itsb.vyrbus.model.bean.TranscarRolUsuario;
import pe.itsb.vyrbus.model.bean.TranscarUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.mappers.ResumenVentas;
import pe.itsb.vyrbus.service.mappers.VentasPiloto;

/**
 * @author abant
 *
 */
public interface TranscarWebDAO {
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
	public TranscarUsuarioPersonal buscarUsuario(String login)throws Exception;
	/**
	 * Guardar o actualiza un usuario
	 * @param transcarUsuario	: Instancia de las class
	 * @param isNuevo	: indica si el usuario es nuevo(true) o ya existe (false)
	 * @throws Exception
	 */
	public void guardarUsuario(TranscarUsuarioPersonal transcarUsuario, String idsRoles, boolean isNuevo)throws Exception;
//	/**
//	 * Realizala busqueda del identificado de la agencia de carga, a travez del codigo de agencia de pasajes
//	 * @param codigoAgenciaPasajes : codigo de la agencia en pasajes.
//	 * @return identificador de la agencia en carga
//	 * @throws Exception
//	 */
//	public Integer buscarIdAgenciaByCodigoAgenciaPasajes(String codigoAgenciaPasajes)throws Exception;
	/**
	 * Realiza la apertura de la liquidacion de turno
	 * @param liquidacionTurno	: instancia de la class
	 * @param isReapertura		: True, cuando es una reapertura de caja; False, cuando es una apertura de caja
	 * @return Si es correcto Null, lo contrario cuando ocurre un error
	 * @throws Exception
	 */
	public String aperturarLiquidacion(TranscarLiquidacionTurno liquidacionTurno, boolean isReapertura)throws Exception;
	/**
	 * Realiza la busqueda del detalle de ventas
	 * @param usuario	: Identificador del usuario - Transcar
	 * @param agenciaId	: Identificador de la agencia - Transcar
	 * @param fechaInicial	: Fecha inicial de la busqueda
	 * @param fechaFinal	: Fecha Final de la busqueda
	 * @return lista de resultados
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalleVentas(TranscarUsuarioPersonal usuario, Integer agenciaId, String fechaInicial, String fechaFinal)throws Exception;
//	/**
//	 * Realiza la busqueda de todas las agencias
//	 * @return
//	 * @throws Exception
//	 */
//	public List<Agencia> buscarAgencias()throws Exception;
	/**
	 * Realiza la busqueda de todos los usuarios asociados a una determinada agencia, en funcion a la venta
	 * @param agenciaId		: Identificador de la agencia
	 * @param fechaInicio	: Fecha incio de la busqueda
	 * @param fechaFin		: fecha fin de la busqueda
	 * @return Lista de resultados
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuariosByVenta(Integer agenciaId, String fechaInicio, String fechaFin)throws Exception;
	/**
	 * Realiza la busqueda del resumen de especies valoradas emitidas
	 * @param usuarioId		: Identificador unico del usuario
	 * @param agenciaId		: Identificador unico de la agencia
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFin		: Fecha fin de la bsuqueda
	 * @return
	 * @throws Exception
	 */
	public List<Liquidacion> buscarLiquidacionTurnoResumenEspVal(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin)throws Exception;
	/**
	 * Realiza la busqueda de los montos separados por forma de pago para la liquidacion de turno
	 * @param usuarioId		: Identificador unico del usuario
	 * @param agenciaId		: Identificador unico de la agencia
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFin		: Fecha fin de la bsuqueda
	 * @return
	 * @throws Exception
	 */
	public Liquidacion buscarLiquidacionTurno(Integer usuarioId, Integer agenciaId, String fechaInicio, String fechaFin)throws Exception;
	/**
	 * Realiza el cierre de la liquidacion de turno
	 * @param usuarioId			: Identificador del usuario.
	 * @param agenciaId			: Identificador de la agencia
	 * @param fechaLiquidacion	: Fecha de la liquidacion
	 * @param efectivoIngresado	: Total efectivo ingresado
	 * @param efectivoLiquidado	: Total efectivo liquidado
	 * @throws Exception
	 */
	public void cerrarLiquidacion(Integer usuarioId, Integer agenciaId, String fechaLiquidacion, Double efectivoIngresado, Double efectivoLiquidado)throws Exception;
	/**
	 * Realiza la busqueda de la liquidacino del bus.
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFin		: Fecha fin de la busqueda
	 * @param codigoBus		: codigo del bus
	 * @return
	 * @throws Exception
	 */
	public TreeMap<String, Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus)throws Exception;
	/**
	 * Realiza la busqueda de las liquidaciones del counter
	 * @param fechaInicio	: Fecha inicio de busqueda
	 * @param fechaFin		: Fecha fin de busqueda
	 * @param agenciaId		: Identificador de la Agencia
	 * @param usuarioId		: Identificador del usuario
	 * @return	Lista de resultados.
	 * @throws Exception
	 */
	public TreeMap<String, Liquidacion> buscarLiquidacionCounter(String fechaInicio, String fechaFin, Integer agenciaId, Integer usuarioId) throws Exception;
	/**
	 * Realiza la actualizaci�n del Password del usario por Login
	 * @param login : Login del usuario a cambiar el Password
	 * @param passwordNew : Nuevo password
	 * @throws Exception
	 */
	public void actualizarPasswordUsuarioByLogin(String login, String passwordNew)throws Exception;

	/*
	 * Recupera las ventas de Encomiendas para el Registro de Ventas
	 * @param fechaInicio	: Fecha inicio de busqueda
	 * @param fechaFin		: Fecha fin de busqueda
	 */
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception;

	/*
	 * Recupera las ventas de encomiendas desde la BD de TrabscarWeb en PostgreSQL hacia la tabla resumen del VYRBUS
	 * @param fechaDesde	: Fecha de inicio de la b�squeda
	 * @param fechaHasta	: Fecha final de la b�squeda
	 *
	 */
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta);

	/**
	 * Busca el total de ventas en efectivo de un detarmido usuario, Esto es para validar el ingreso de gastos a la liquidacion.
	 * @param idUsuario	: Identificador del usuario
	 * @param idAgencia	: Identificador de la agencia
	 * @param fecha		: Fecha de la venta.
	 * @return Total ventas en efectivo
	 * @throws Exception
	 */
	public Double buscaTotalVentasEfectivo(String loginUsuario,Integer idAgencia, String fecha) throws Exception;
}
