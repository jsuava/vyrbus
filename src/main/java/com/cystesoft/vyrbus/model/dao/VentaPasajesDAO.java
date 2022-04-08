/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas.
 * Descripción	: Declaración de los metodos relacionados con la Venta de Pasajes.
 * Autor		: José Sullo Avalos
 * Fecha		: 05/07/2012
 */
package com.cystesoft.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import com.cystesoft.vyrbus.service.mappers.ResumenVentas;
import com.cystesoft.vyrbus.service.mappers.VentasPiloto;

/**
 *
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public interface VentaPasajesDAO extends GenericDAO {
	/**
	 * Realiza la busqueda de las ventas para el armado del mapa del bus.
	 * @param idItinerario	: Identificador del itinerario.
	 * @return Lista de ventas.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarVentasForMapaBus(Long idItinerario)throws Exception;
	/**
	 * Realiza la busqueda de la venta especifica.
	 * @param idVenta	: Identificador de la venta.
	 * @return Venta.
	 * @throws Exception
	 */
	public VentaPasaje buscarVentasByIdVenta(Long idVenta)throws Exception;
	/**
	 * Realiza la validación de que el asiento no haya sido utilizado antes de la venta.
	 * @param itinerario	: Objeto itinerario.
	 * @param ruta			: Objeto ruta.
	 * @param asiento		: Numero del asiento.
	 * @param piso			: Numero del piso al cual pertenece el asiento
	 * @return Identificador de la venta.
	 * @throws Exception
	 */
	public Long validateSeat(Itinerario itinerario, Ruta ruta, Integer asiento, Integer piso)throws Exception;
	/**
	 * Valida que no haya ventas asociadas al servicio, para permitir guardar el mapa del bus.
	 * @param idServicio	: Identificador del servicio.
	 * @return Numero total de registros encontrados.
	 * @throws Exception
	 */
	public Long validarServicio(Integer idServicio)throws Exception;
	/**
	 * Realiza la busqueda de la reservas que estan por confirmar
	 * @param idOrigen			: Identificador del origen.
	 * @param idDestino			: Identificador del Destino.
	 * @param pasajero			: Apellidos del pasajero.
	 * @param numeroDocumento	: Numero de control de la reserva.
	 * @param numeroBoleto		: Numero de boleto que se desea buscar.
	 * @param fechaPartida		: Fecha de partida del servicio.
	 * @return Lista de reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarReservasPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroDocumento, String numeroBoleto, String fechaPartida, Integer idAgencia)throws Exception;
	/**
	 * Busca la venta por su ID.
	 * @param idVenta	: Identificador de la venta.
	 * @return Venta de pasaje
	 * @throws Exception
	 */
	public VentaPasaje buscarVentaById(Long idVenta)throws Exception;
	/**
     * Obtiene la fecha de la Base de Datos
     */
    public String getDateSystem()throws Exception;
    /**
	 * Realiza la busqueda de las ventas a Fecha Abierta.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del Destino.
	 * @param pasajero		: Nombres del pasajero.
	 * @param numeroControl	: Número de control de la Venta Fecha Abierta.
	 * @param numeroBoleto	: Numero de boleto de la Venta a Fecha Abierta..
	 * @return Lista de reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarFechaAbiertaPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String numeroDocumento)throws Exception;
	/**
	 * Busca los usuarios que realizaron ventas en una determinada agencia.
	 * @param idAgencia		: Identificador de la agencia.
	 * @param estado		: Estado de los registros.
	 * @param fechaInicio	: Fecha inicial de busqueda basada en la fecha de liquidacion.
	 * @param fechaFin		: Fecha final de busqueda basada en la fecha de liquidacion.
	 * @return Lista de usuario.
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia, String estado, String fechaInicio, String fechaFin, String rucCredito)throws Exception;
	/**
	 * Realiza la busqueda del detalle de ventas de un usuario para la agencia seleccionada.
	 * @param idAgencia			: Identificador de la agencia.
	 * @param idUsuario			: Identificador del usuario.
	 * @param idTipoMovimiento	: Identificador del tipo de movimiento.
	 * @param fechaInicio		: Fecha inicial de busqueda basada en la fecha de liquidacion.
	 * @param fechaFin			: Fecha final de busqueda basada en la fecha de liquidacion.
	 * @param estado			: Estado de los registros a consultar.
	 * @return Lista de ventas.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalladoVentas(String idAgencia, String idUsuario, String idTipoMovimiento, String fechaInicio, String fechaFin, String estado)throws Exception;
	/**
	 * Valida que un boleto no haya sido utilizado. 
	 * @param numeroBoleto		: Numero de boleto a validar.
	 * @param idTipoComprobante	: Identificador del tipo de comprobante a validar.
	 * @return Integer
	 */
	public Integer validarNumeroBoleto(String numeroBoleto, Integer idTipoComprobante)throws Exception;
	/**
	 * Realiza la busqueda de las ventas.
	 * @param idOrigen			: Identificador del Origen.
	 * @param idDestino			: Identificador del Destino.
	 * @param pasajero			: Apellidos del pasajero.
	 * @param numeroControl		: Numero de control del boleto a buscar.
	 * @param numeroBoleto		: Numero de boleto que se desea buscar.
	 * @param fechaPartida		: Fecha de partida del servicio.
	 * @return Lista de reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarVentasPostergar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String fechaPartida)throws Exception;
	
	/**
	 * Busca el avance semanal de las ventas
	 * @param idOrigen			: Identificador de la localidad origen.
	 * @param idDestino			: Identificador de la localidad destino.
	 * @param tipoTransaction	: (1)Ventas, (2)Reservas.
	 * @param idServicio		: Identificador del Servicio.
	 * @param fechaDesde		: Fecha desde de la busqueda, basada en la fecha de viaje del pasajero
	 * @param fechaHasta		: Fecha Hasta de la busqueda, basada en la fecha de viaje del pasajero
	 * @return 
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarAvanceSemanalVentas(String idOrigen, String idDestino, String tipoTransaction, String idServicio, String fechaDesde, String fechaHasta) throws Exception;
	
	/**
	 * Busca el avance semanal de las ventas para generar las columnas en el Listbox de la consulta.
	 * @param idOrigen			: Identificador de la localidad origen.
	 * @param idDestino			: Identificador de la localidad destino.
	 * @param tipoTransaction	: (1)Ventas, (2)Reservas.
	 * @param idServicio		: Identificador del Servicio.
	 * @param fechaDesde		: Fecha desde de la busqueda, basada en la fecha de viaje del pasajero
	 * @param fechaHasta		: Fecha Hasta de la busqueda, basada en la fecha de viaje del pasajero
	 * @return 
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarAvanceSemanalVentasColumns(String idOrigen,String idDestino, String tipoTransaction, String idServicio,String fechaDesde, String fechaHasta)throws Exception;
	
	/**
	 * Cuenta la cantidad de viajes validos en el intervalo de fechas solicitado.
	 * @param idPasajero	: Identificador del pasajero del cual se desea sus viajes.
	 * @param fechaInicial	: Fecha inicial para el conteo de viajes
	 * @param fechaFinal	: Fecha final para el conteo.
	 * @return Cantidad de viajes.
	 * @throws Exception
	 */
	public int contarViajesValidos(Long idPasajero, String fechaInicial, String fechaFinal)throws Exception;
	/**
	 * Buscar venta - Utilizado para el transbordo de Pasajeros
	 * @param id : Identificador de la Venta.
	 * @return
	 */
	public VentaPasaje buscarPorId(Long id);
	/**
	 * Realiza el transbordo del pasajero
	 * @param numeroAsiento : Número de asiento
	 * @param idVentaPasaje : Identificador de la venta en donde se realizará el Update
	 * @param itinerario	: Identificador del Itinerario el cual sera actualizado en la Venta
	 * @throws Exception
	 */
	public void transbordarPax(Integer numeroAsiento, Long idVentaPasaje, Itinerario itinerario) throws Exception;
	
	
	/**
	 * Busca pasajes de un determinado pasajero para la activacion a Pasajero Frecuente.
	 * @param fechaInicial 	: Fecha inical de la busqueda
	 * @param fechaFinal	: Fecha final de la busqueda
	 * @param pasajeroID	: Identificador del Pasajero
	 * @return
	 */
	public ArrayList<VentaPasaje> buscarVentasPax(String fechaInicial, String fechaFinal,Long pasajeroID)throws Exception;
	/**
	 * Realiza la busqueda de boleto a reimprimir
	 * @param numeroDocumento	: Numero de documento del Pasajero.
	 * @param pasajero			: Nombres del pasajero.
	 * @param fechaPartida		:Fecha de partida del servicio.
	 * @return Lista con las ocurrencias.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletosReimprimir(String numeroDocumento, String[] pasajero, String fechaPartida)throws Exception;
	/**
	 * Busca las ventas para realizar la devolucion
	 * @param numeroDocumento	: Numero de Documento.
	 * @param numeroControl		: Numero de Control.
	 * @param numeroBoleto		: Numero del Boleto.
	 * @return Lista de ocurrencias.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletosDevolucion(String numeroDocumento, String numeroControl, String numeroBoleto)throws Exception;
	/**
	 * Realiza la busqueda de los comprobantes que no tienen aun boleto de viaje.
	 * @param fechaPartida		: Fecha de partida del servicio.
	 * @param idAgencia			: Identificador de la agencia.
	 * @param idTipoComprobante : Identificador del Tipo de comprobante.
	 * @param idRol				: Identificador del rol del usuario.
	 * @param idAgenciaEmision	: Identificador de la agencia que emitio el voucher, RC.
	 * @return Lista de ocurrencias;
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarComprobantesSinBoleto(String fechaPartida, Integer idAgencia, Integer idTipoComprobante, Integer idRol, Integer idAgenciaEmision)throws Exception;
	
	/**
	 * Valida Ocupabilidad de todos los asientos ha a generar las ventas del servicio especial.
	 * @param idItinerario	: Identificador del Itinerario
	 * @param idRuta		: Identificador de la ruta
	 * @param numeroAsientos: Números de asientos a validar
	 * @param numeroPiso	: Piso de los asientos a validar
	 * @return	Números de Asientos ocupados.
	 * @throws Exception
	 */
	public List<VentaPasaje> validaOcupabilidad(Long idItinerario, Integer idRuta, String numeroAsientos, Integer numeroPiso ) throws Exception;
	
	/**
	 * Valida los boletos que se van a generar para las ventas en los Servicios Especiales, que no esten utilizados.
	 * @param boletoInicial : Boleto inical
	 * @param boletoFinal	: Boleto final
	 * @return cantidad de boletos existente
	 * @throws Exception
	 */
	public Integer validaBoletos_ServicioEspecial(String boletoInicial, String boletoFinal) throws Exception;
	
	/**
	 * Busca los ultimos correlativo utilizados poar la agencia. 
	 * @param fechaUltimoEnvio : Fecha del ultimo envio del las especies valoradas el punto de venta(Agencia).
	 * @param serie			   : Numero de serie a consultal.
	 * @param idComprobante	   : Identimicsr de tipo de comprobante.
	 * @param idAgencia		   : Identificador de la Agencia.
	 * @return
	 */
	public List<VentaPasaje> buscarUltimoCorrelativoEmitido(String fechaUltimoEnvio, String serie, Integer idComprobante, Integer idAgencia) throws Exception;
	
	/**
	 * Busca los correlativos faltantes
	 * @param fechaInico		: Fecha inicio
	 * @param serie				: Numero de serie a consultal.
	 * @param idComprobante		: Identimicsr de tipo de comprobante.
	 * @param idAgencia			: Identificador de la Agencia.
	 * @param fechaFin			: Fecha Fin
	 * @param idUsuario			: Identificador del Usuario
	 * @throws Exception
	 */
	public List<VentaPasaje> correlativosFaltantesX(String fechaInico, String serie, Integer idComprobante,Integer idAgencia, String fechaFin, Integer idUsuario) throws Exception;
	
	/**
	 * Busca el total de ventas en efectivo de un detarmido usuario, Esto es para validar el ingreso de gastos a la liquidacion.
	 * @param idUsuario	: Identificador del usuario
	 * @param idAgencia	: Identificador de la agencia
	 * @param fecha		: Fecha a consultar
	 * @return	total de ventas realizadas en efectivo
	 * @throws Exception
	 */
	public Double buscaTotalVentasEfectivo(Integer idUsuario,Integer idAgencia, String fecha) throws Exception;
	
	/**
	 * Realiza la busqueda a travez de los criterios enviado en el treeMap
	 * @param criteriosBusqueda : treeMap con los criterios de busqueda
	 * @param criteriosOrdenar  : criteorios para el order by
	 * @return lista de ventas
	 */
	public ArrayList<VentaPasaje> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar);
//	/**
//	 * Realiza la busqueda de las ventas ida y vuelta por su identificador.
//	 * @param identificador	: Campo de la venta que indica el id de la venta ida retorno.
//	 * @return Lista de registros.
//	 * @throws Exception
//	 */
//	public ArrayList<VentaPasaje> buscarPorIdReferenciaIdaRetorno(Long identificador)throws Exception;
	/**
	 * Busca el detallado de ventas diarias de acuerdo a los criterios enviados.
	 * @param idAgencia		: Identificador de la agencia.
	 * @param idUsuario		: Identificador del usuario.
	 * @param fechaInicial	: Fecha inicial del intervalo de busqueda.
	 * @param fechaFinal	: Fecha final del intervalo de busqueda.
	 * @param criterio		: Indica el tipo de registros que se desea mostrar, DEVOLUCIONES, ANULACIONES, CORTESIAS, etc.
	 * @return Lista de ventas.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalladoVentas(Integer idAgencia, Integer idUsuario, String fechaInicial, String fechaFinal, Integer criterio)throws Exception;
	
	/**
	 * Busca el detalle de venta diarias de una agencia de viajes o cliente corporativo.
	 * @param fechaInicio	: Fecha de Inicio de la busqueda.
	 * @param fechaFin		: Fecha final de la busqueda.
	 * @param rucClienteCredito		: Numero de ruc del cliente credito
	 * @param idUsuario		: Idenficador del Usuario.
	 * @param orden			: campos por el cual de debe de ordenar la consulta. 
	 * @param incluirAnulados : true=incluye vauchers anulados, false=lo contrario.
	 * @param isSoles : indica si la consulta es en nomeda soles(true), o en moneda extranjeta(false)
	 * @param estadoBoletos : Determina el estado de los boletos (PAG) pagados, (PEN) pendientes, Null todos.
	 * @return Lista de Ventas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalleVentasAgencia(String fechaInicio, String fechaFin,String  rucClienteCredito, Long idUsuario, String orden, Boolean incluirAnulados,boolean isSoles, String estadoBoletos, Integer centroCostoID, boolean byFechaReimpresion) throws Exception;
	
	/**
	 * Obtiene el ultimo registro de la venta, para la validación en las anulaciones
	 * @param idVentaOriginal: Identificador de l aventa original
	 * @return
	 */
	public VentaPasaje buscarUltimoRegistro(Long idVentaOriginal);
	
	/**
	 * Activa el recibo de caja
	 * @param idVentaOriginal : Identificador de la venta original
	 */
	public void activarReciboCaja(Long idVentaOriginal);
	
	/**
	 * Busca vaucher para la anulación.
	 * @param numVoucher		: Número del Voucher.
	 * @param numcontrol		: Número de control del Voucher.	
	 * @param idTipoComprobante : Identificador del tipo de comprobante.
	 * @param rucCliente 		: Númerod e ruc del Cliente
	 * @param fechaPartida 		: Fecha de partida
	 * @param horaPartida 		: Hora de partida del servicio
	 * @param boleto			: Número de boleto
	 */
	public List<VentaPasaje> buscarVoucherForAnulacion(Integer idTipoComprobante,String numVoucher, String numcontrol,String rucCliente,String fechaPartida,String horaPartida,String boleto);
	
	/**
	 * Busca ventas o reservas, de la consulta ESTADO DE VENTAS Y RESERVAS.
	 * @param fechaInicialVenta	: Fecha inicial de la venta.
	 * @param fechaFinVenta		: Fecha final de la venta	.
	 * @param tipoBusqCliente	: Tipo de busqueda a considerar para el Cliente; (0 Por Numero Documento, 1 Razon Social).
	 * @param busqCliente		: Parametros a busacar del Cliente.
	 * @param tipoBusqPax		: Tipo de busqueda a considerar para el Pasajero; (0 Por Numero Documento, 1 Razon Social).
	 * @param busqPax			: Parametros a busacar del Pasajero.
	 * @param fechaPartida		: Fecha de partida.
	 * @param idUsuario			: Identificador del usuario que registro la Venta o Reserva.
	 * @param numeroControl		: Número de Control.
	 * @param numeroBoleto		: Número de Boleto
	 * @param idOrigen			: Identificador del Origen.
	 * @param idDestino			: Identificador del Destino.	
	 * @param tipoMovimiento	: Tipode movimiento; (venta, reserva o fecha abirta)
	 * @return Lista de ventas o reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarEstadoVentasReservas(String fechaInicialVenta, String fechaFinVenta, Integer tipoBusqCliente, String busqCliente,Integer tipoBusqPax, String busqPax, String fechaPartida, Integer idUsuario,String numeroControl,String numeroBoleto, Integer idOrigen, Integer idDestino, Integer tipoMovimiento) throws Exception;
	
	/**
	 * Busca ventas para la consulta de operaciones remotas, segun parametros enviados.
	 * @param fechaPartida	: Fecha de partida del servicio.
	 * @param idOrigen		: Identificado del Origen.
	 * @param idDestino		: Identificador del Destino.	
	 * @param numeroBoleto	: Numero de Boleto
	 * @param numeroControl	: Numero de Control
	 * @param documentoPax	: Numero de documento del Pasajero
	 * @return Lista de ventas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarOperacionesRemotras(String fechaPartida,Integer idOrigen, Integer idDestino,String numeroBoleto, String numeroControl, String documentoPax) throws Exception;
	
	/**
	 * Busca el total de ventas emitidas, segun parametros enviados.
	 * @param fechaLiquidacion	: Fecha de la liquidacion.
	 * @param idAgencia			: Identificador de la Agencia.
	 * @param idUsuario			: Identificador del Usuario.
	 * @return totalVentas
	 */
	public Double buscarTotalVentas(String fechaLiquidacion,Integer idAgencia, Integer idUsuario);
	/**
	 * Busca el historial de viajes del pasajero.
	 * @param idPasajero	: Identificador del pasajero.
	 * @return Lista de viajes.
	 */
	public List<VentaPasaje> buscarVentasByPasajero(Long idPasajero);
	/**
	 * Busca datos del boleto a liberar del manifiesto de pasajeros.
	 * @param numeroboleto	: Número de Boleto a buscar.
	 * @return	datos del boleto
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletoLiberarManifiesto(String numeroboleto)throws Exception;
	
	
	/**
	 * Busca el boleto para la venta del seguro.
	 * @param numeroBoleto	: Número del Boleto
	 * @return
	 * @throws Exception
	 */
	public VentaPasaje buscarBoletoByAsegurado(String numeroBoleto)throws Exception;
	/**
	 * Busca pasajeros para el envio del manifiesto de pasajeros
	 * @param codigoBus		: Identificador del Itinerario
	 * @return 
	 * @throws Exception
	 */
	public List<VentaPasaje>buscarPasajerosByME(Long itinerarioId)throws Exception;
	/**
	 * Busca las ventas por punto de venta y usuario.
	 * @param fechaInicial		: Fecha incial de la busqueda.
	 * @param fechaFinal		: Fecha final de la busqueda.
	 * @param idAgencia			: Obcional, Identificador de la Agencia.
	 * @param idUsuario			: Obcional, Identificador del Usuario.
	 * @param idsTiposMovimiento	: Obcional, Identificador del tipo de comprobante.
	 * @param idFormaPago 		: Obcional, Identificador de la forma de pago.
	 * @return Lista de resultados.
	 * @throws Exception
	 */
	public List<Agencia> buscarVentasPorPuntoVenta(String fechaInicial, String fechaFinal,Integer idAgencia, Integer idUsuario, String idsTiposMovimiento, Integer idFormaPago)throws Exception;
	/**
	 * Realiza la busqueda del avance de ventas
	 * @param idOrigen		: Origen de los Itinerarios
	 * @param idDestino		: Destino de los itinerarios
	 * @param idServicio	: Servicio de la consulta
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @return Listados de itinerarios.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarAvanceVentas(String idOrigen, String idDestino, String idServicio, String fechaDesde, String fechaHasta) throws Exception;
	/**
	 * Realiza la busqueda de boletos anulados agrupado por Usuario o Agencia
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param criterio		: Criterio a tomar en cuenta para obtener la informacion.
	 * @return
	 */
	public List<ResumenAnulacionPostergacion> buscarBoletosAnuladosByX(String fechaDesde, String fechaHasta, Integer criterio);
	/**
	 * Realiza la busqueda de boletos anulados por usuario
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param id			: Identificador del usuario o Agencia
	 * @param criterio		: Criterio a tomar en cuenta para obtener la informacion.
	 * @return
	 */
	public List<VentaPasaje> buscarBoletosAnuladosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio);
	/**
	 * Busca los boletos postergados por Usuario o Agencia
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param criterio		: Criterio a tomar en cuenta para obtener la informacion.
	 * @param nroPostergaciones		: 0 sin cantidad, mayor a 0 segun se indique.
	 * @return
	 */
	public List<ResumenAnulacionPostergacion> buscarBoletosPostergadosByX(String fechaDesde, String fechaHasta, Integer criterio, Integer nroPostergaciones);

	/**
	 * Busca los boletos postergados por Usuario o Agencia
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param criterio		: Criterio a tomar en cuenta para obtener la informacion.
	 * @param nroPostergaciones		: 0 sin cantidad, mayor a 0 segun se indique.
	 * @return
	 */
	public List<VentaPasaje> buscarBoletosPostergadosDetalladoByX(String fechaDesde, String fechaHasta, Integer id, Integer criterio, Integer nroPostergaciones);
	
	/**
	 * Busca el resumen de ventas 
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param idagencia		: Especifica alguna agencia en especial, 0 equivale a todos.
	 * @param nroConsulta	: 1, 2 o 3 diferentes tipos de consulta.
	 * @return
	 */	
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta, Integer idAgencia, Integer nroConsulta);
	
	/**
	 * Busca el historial de un comprobante a partir de su numero
	 * @param numeroComprobante	: Numero del comprobante a buscar
	 * @return
	 */	
	public List<VentaPasaje> buscarHistorialComprobante(String numeroComprobante);
	
	public List<List<VentaPasaje>> obtenerVentasResumenLiquidacion(Integer idAgencia, Integer idUsuario, String fechaLiquidacion);
	/**
	 * Guarda las ventas de Servicios Especiales
	 * @param ventaPasaje	: Objeto venta que se guardara
	 * @return
	 * @throws Exception
	 */
	public void guardarServicioEspecial(VentaPasaje ventaPasaje) throws Exception;
	/**
	 * Busca los comprobantes emitidos por Servicos Especiales
	 * @param numComprobante	: Numero del comprobante
	 * @param fDesde			: Fecha de inicio de busqueda
	 * @param fHasta			: Fecha de fin de busqueda
	 * @return Lista de Ventas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarFacturasServicioEspecial (String numComprobante, String fDesde, String fHasta) throws Exception;
	/**
	 * Busca las ventas para el pago de Pilotos
	 * @param fInicio	: Fecha de inicio de busqueda
	 * @param fFin		: Fecha fin de busqueda
	 * @return
	 * @throws Exception
	 */
	public List<VentasPiloto> buscarVentasPagoPilotos(String fInicio, String fFin) throws Exception;
	/**
	 * Busca las ventas para realizar la perdida de servicio
	 * @param numeroDocumento	: Numero de Documento.
	 * @param numeroControl		: Numero de Control.
	 * @param numeroBoleto		: Numero del Boleto.
	 * @return Lista de ocurrencias.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletosPerdidaServicio(String numeroDocumento, String numeroControl, String numeroBoleto)throws Exception;
	/**
	 * Guarda el boleto como perdida de servicio
	 * @param perdidaServicio	: Registro a marcar como perdida de servicio
	 * @throws Exception
	 */
	public void guardarPerdidaServicio(VentaPasaje perdidaServicio) throws Exception;
	/**
	 * Realiza la busqueda de la liquidacino del bus.
	 * @param fechaInicio	: Fecha inicio de la busqueda
	 * @param fechaFin		: Fecha fin de la busqueda
	 * @param codigoBus		: codigo del bus
	 * @return	
	 * @throws Exception
	 */
	public List<Manifiesto> buscarLiquidacionBus(String fechaInicio, String fechaFin, String codigoBus)throws Exception;
}
