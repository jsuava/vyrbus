/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Fecha		: 17/06/2014
 */
package com.cystesoft.vyrbus.model.dao;

import com.cystesoft.vyrbus.model.bean.TitanComisionPersonaBase;
import com.cystesoft.vyrbus.model.bean.TitanFuncionarioPersonaPasaje;
import com.cystesoft.vyrbus.model.bean.TitanLiquidacionTurnoPasaje;
import com.cystesoft.vyrbus.model.bean.TitanPersona;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioHardware;
import com.cystesoft.vyrbus.model.bean.TitanUsuarioPersonal;
import com.cystesoft.vyrbus.model.bean.TitanVentaPasaje;

/**
 * @author JABANTO
 *
 */
public interface TitanDAO {

	/* ******** TRANSACIONES REFERIDAS A LA LIQUIDACION TURNO PASAJE **********************/
	/**
	 * Busca la liquidacion del usuario
	 * @param idLiquidacionSisvyr	: Identificador de la liquidacion de turno del Sisvyr.
	 * @return LiquidacionTurnoPasaje
	 * @throws Exception
	 */
	public TitanLiquidacionTurnoPasaje buscarLiquidacionTurnoPasajeByIdLiquidacion(Long idLiquidacionSisvyr)throws Exception;
	/**
	 * Actualiza la liquidacion turno pasaje
	 * @param liquidacionTurnoPasaje	: Instancia a actualizar
	 * @throws Exception
	 */
	public void actualizaLiquidacionTurnoPasajeByIdLiquidacion(TitanLiquidacionTurnoPasaje liquidacionTurnoPasaje)throws Exception;


	/* ******** TRANSACIONES REFERIDAS A LA VENTA PASAJES **********************/
	/**
	 * Busca el Boleto
	 * @param serieBoleto		: 	Serie del Boleto.
	 * @param numeroBoleto		:	Número del Boleto.
	 * @param idCondicionBoleto	:	Identificador de la condicion del Boleto.
	 * @return boleto de viaje
	 * @throws Exception
	 */
	public TitanVentaPasaje buscarBoletoVentaPasaje(String serieBoleto,String numeroBoleto,Integer idCondicionBoleto)throws Exception;
	/**
	 * Realiza la anulacion del Boleto de viaje.
	 * @param idVentaPasje	: Identificador de la venta de pasaje.
	 * @param isAnulacion	: anulacion(true), devolucion(false).
	 * @throws Exception
	 */
	public void anularDevolverBoletoVentaPasaje(Long idVentaPasje, Boolean isAnulacion)throws Exception;
	/**
	 * Actualiza la forma de pago de un boleto
	 * @param titanVentaPasaje	: Instancia del objeto ventaPasajes (parametros obligatorios, idVentaPasajes, idCondicionBoleto, idTarjetas)
	 * @throws Exception
	 */
	public void actualizarFormaPago(TitanVentaPasaje titanVentaPasaje)throws Exception;


	/* ******** TRANSACIONES REFERIDAS AL USUARIO PERSONAL **********************/
	/**
	 * Busca el UsuarioPerosnal por su Login
	 * @param login	: Login a buscar
	 * @return UsuarioPersonal.
	 * @throws Exception
	 */
	public TitanUsuarioPersonal buscarUsuarioPersonalPorLogin(String login)throws Exception;
	/**
	 * Guarda al usuario personal
	 * @param titanUsuarioPersonal
	 * @throws Exception
	 */
	public void guardarUsuarioPersonal(TitanUsuarioPersonal titanUsuarioPersonal)throws Exception;


	/* ******** TRANSACIONES REFERIDAS AL CLIENTE (T_PERSONAL) **********************/
	/**
	 * Busca el Cliente (T_Perosna)
	 * @param numeroRuc : Numero de Ruc a buscar.
	 * @return TitanPersona
	 * @throws Exception
	 */
	public TitanPersona buscarPersonaPorRuc(String numeroRuc)throws Exception;
	/**
	 * Actualiza el Cliente (T_Persona)
	 * @param persona :
	 */
	public void actualizaPersona(TitanPersona persona)throws Exception;
	/**
	 * Guarda El Cliente (T_Pesonal)
	 * @param persona : Nueva instansia a guardar
	 * @throws Exception
	 */
	public void guardarPersona(TitanPersona persona)throws Exception;


	/* ******** TRANSACIONES REFERIDAS FUNCIONARIO PERSONA PASAJE **********************/
	/**
	 * Busca en funcionario persona pasajes por el id Persona.
	 * @param idPersona	: Identificador del Cliente (t_Persona)
	 * @throws Exception
	 */
	public TitanFuncionarioPersonaPasaje buscarFuncionarioPersonaPasajePorIdPersona(Long idPersona)throws Exception;
	/**
	 * Actualiza la asociación del funcionario con cliente
	 * @param funcionarioPersonaPasaje
	 * @throws Exception
	 */
	public void actualizaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception;
	/**
	 * Guarda la asociación del funcionario con el Cliente.
	 * @param funcionarioPersonaPasaje
	 * @throws Exception
	 */
	public void guardaFuncionarioPerosnaPasajes(TitanFuncionarioPersonaPasaje funcionarioPersonaPasaje)throws Exception;


	/* ******** TRANSACIONES REFERIDAS COMISION PERSONA BASE HISTORICA **********************/
	/**
	 * Busca base historica del Cliente.
	 * @param idPersona : Identificador del Cliente.
	 * @return
	 * @throws Exception
	 */
	public TitanComisionPersonaBase buscarBaseHistoricaPorIdPersona(Long idPersona)throws Exception;
	/**
	 * Actualiza base historica
	 * @param comsionPersonaBase
	 * @throws Exception
	 */
	public void actualizaBaseHistorica(TitanComisionPersonaBase comsionPersonaBase) throws Exception;
	/**
	 * Guarda base historica
	 * @param comsionPersonaBase
	 * @throws Exception
	 */
	public void guardaBaseHistorica(TitanComisionPersonaBase comsionPersonaBase)throws Exception;

	/* ******** OTRAS TRANSACIONES **********************/
	/**
	 * Realiza la busqueda de la fecha con la fue emitida la factura
	 * @param serie	: numero de serie de la factura
	 * @param numero	: Numero de la factura
	 * @param numeroRuc : Numero de Ruc del Cliente
	 * @return Fecha de la Factura
	 * @throws Exception
	 */
	public String buscarFechaFacturaEspecial(String serie, String numero, String numeroRuc)throws Exception;

	/* ******** TRANSACIONES REFERIDAS AL USUARIO HARDWARE (T_CPU) **********************/
	/**
	 * Guarda el usuario hardware
	 * @param titanUsuarioHardware	: Objeto a guardar
	 * @throws Exception
	 */
	public void guardarUsuarioHardware(TitanUsuarioHardware titanUsuarioHardware) throws Exception;
	/**
	 * Realiza la busqueda del id de Agencia
	 * @param idAgenciaPasajes	: Identificador de la agencia de Pasajes a buscar
	 * @return
	 * @throws Exception
	 */
	public Integer buscarAgencia(Integer idAgenciaPasajes) throws Exception;
	/**
	 * Realiza la busqueda del usuario Hardware
	 * @param id	: Identificador unico del usuario Hardware
	 * @return Usuario Hardware
	 * @throws Exception
	 */
	public TitanUsuarioHardware buscarUsuarioHardwareByIdVyR(Integer id) throws Exception;
	/**
	 * Realiza la actualizacion del Usuario Hardware
	 * @param titanUsuarioHardware	: Referencia del usuario Hardware que se actualizara
	 * @throws Exception
	 */
	public void actualizarUsuarioHardware(TitanUsuarioHardware titanUsuarioHardware) throws Exception;
	/**
	 * Realiza la busqueda del IP del usuario Hardware
	 * @param ip	: Ip que se desea buscar.
	 * @return IP del usaurio Hardware
	 * @throws Exception
	 */
	public String buscarIdUsuarioHardware(String ip) throws Exception;
	/**
	 * Deshabilita el usuario hardware
	 * @param idUsuarioHardwareVyR	: Identificador del Usuario Hardware de Pasajes
	 */
	public void inactivarUsuarioHardware(Integer idUsuarioHardwareVyR);

}
