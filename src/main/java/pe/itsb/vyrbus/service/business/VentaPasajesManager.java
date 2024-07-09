package pe.itsb.vyrbus.service.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.EntidadEncomiendaPasajes;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Liquidacion;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.TarifarioByAsientoByAvanceVentas;
import pe.itsb.vyrbus.model.bean.TipoNota;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.mappers.GpsComprobante;
import pe.itsb.vyrbus.service.mappers.ResumenAnulacionPostergacion;
import pe.itsb.vyrbus.service.mappers.ResumenVentas;
import pe.itsb.vyrbus.service.mappers.VentasPiloto;
import pe.itsb.vyrbus.service.util.VentasNotas;

public interface VentaPasajesManager {
	/**
	 * Guarda la operacion de la venta.
	 * @param ventaPasaje		: Objeto a guardar.
	 * @param isFechaAbierta	: Indica si se trata de una venta fecha abierta.
	 * @param generaControl		: Indica si se va a generar numero de control.
	 * @param validaBloqueo		: indica si se debe validar que el registro del bloqueo de asiento exista.
	 * @return -1=Fallo, 1=Exito
	 * @throws Exception
	 */
	public int guardarVenta(VentaPasaje ventaPasaje, boolean isFechaAbierta, boolean generaControl, boolean validaBloqueo, boolean validarDuplicidadAsiento)throws Exception;
	/**
	 * Guarda la operacion de la venta.
	 * @param lstVentas			: Objeto que contiene la venta de ida y retorno.
	 * @param generaControl		: Indica si se va a generar numero de control.
	 * @return -1=Fallo, 1=Exito
	 * @throws Exception
	 */
	public int guardarVentaIdaVuelta(List<VentaPasaje> lstVentas, boolean generaControl) throws Exception;
	
	/**
	 * 
	 * @param lstVentas
	 * @return
	 * @throws Exception
	 */
	public int guardarVenta(List<VentaPasaje> lstVentas, Boolean ejecutarSeqByCorrelativo) throws Exception;
	
	/**
	 * Realiza la busqueda de las ventas para el armado del mapa del bus.
	 * @param idItinerario	: Identificador del itinerario.
	 * @return Lista de ventas.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarVentasForMapaBus(Long idItinerario)throws Exception;
	/**
	 * Realiza la busqueda de la venta especifica.
	 * @param idVenta	: Identificador de la venta a buscar.
	 * @return Objeto de la venta completo.
	 * @throws Exception
	 */
	public VentaPasaje buscarVentaById(Long idVenta)throws Exception;
	/**
     * Obtiene la fecha de la Base de Datos
     */
    public String getDateSystem()throws Exception;
	/**
	 * Realiza la busqueda de la venta especifica.
	 * @param idVenta	: Identificador de la venta.
	 * @return Venta.
	 * @throws Exception
	 */
	public VentaPasaje buscarVentasByIdVenta(Long idVenta)throws Exception;
	/**
	 * Realiza la busqueda de la reservas que estan por confirmar
	 * @param idOrigen			: Identificador del origen.
	 * @param idDestino			: Identificador del Destino.
	 * @param pasajero			: Apellidos del pasajero.
	 * @param numeroDocumento	: Numero de documento del pasajero.
	 * @param numeroBoleto		: Numero de boleto que se desea buscar.
	 * @param fechaPartida		: Fecha de partida del servicio.
	 * @return Lista de reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarReservasPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroDocumento, String numeroBoleto, String fechaPartida, Integer idAgencia, Long ventaPasajeId)throws Exception;
	/**
	 * Realiza la busqueda de las ventas a Fecha Abierta.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del Destino.
	 * @param pasajero		: Nombres del pasajero.
	 * @param numeroControl	: N�mero de control de la Venta Fecha Abierta.
	 * @param numeroBoleto	: Numero de boleto de la Venta a Fecha Abierta..
	 * @return Lista de reservas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarFechaAbiertaPorConfirmar(Integer idOrigen, Integer idDestino, String[] pasajero, String numeroControl, String numeroBoleto, String numeroDocumento)throws Exception;
	/**
	 * Busca los usuarios que realizaron ventas en una determinada agencia.
	 * @param idAgencia	: Identificador de la agencia.
	 * @param estado	: Estado de los registros.
	 * @param fechainicio	: Fecha inicial de busqueda basada en la fecha de liquidacion.
	 * @param fechaFin		: Fecha final de busqueda basada en la fecha de liquidacion.
	 * @return Lista de usuario.
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia, String estado, String fechainicio, String fechaFin, String rucCredito)throws Exception;
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
	 * Realiza la duplicaci�n de un boleto en el mismo turno.
	 * @param ventaOriginal		: Objeto que se anulara para poder duplicar la venta.
	 * @param ventaDuplicado	: Objeto que representa la duplicaci�n de la venta
	 * @return -1=Fallo, 1=Exito.
	 * @throws Exception
	 */
	public int duplicarBoleto(VentaPasaje ventaOriginal, VentaPasaje ventaDuplicado)throws Exception;
	/**
	 * Realiza la anulaci�n del movimiento venta o reserva en el mismo turno, no se genera nuevo registro.
	 * @param movimiento	: Movimiento que se desea anular
	 * @return -1=Fallo, 1=Exito.
	 * @throws Exception
	 */
	public VentaPasaje anularMovimiento(VentaPasaje movimiento, boolean forzarNotaCredito)throws Exception;
	/**
	 * Realiza la confirmaci�n de un Boleto Fecha Abierta.
	 * @param ventaPasaje	: Boleto que se desea confirmar.
	 * @return	: -1=Fallo, 1=Exito.
	 * @throws Exception
	 */
	public VentaPasaje confirmarFechaAbierta(VentaPasaje ventaPasaje, TipoNota tipoNotaCredito, Boolean ejecutarSeqByCorrelativo)throws Exception;
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
	 * Realiza el guardado de la postergacion del boleto.
	 * @param boletoPostergar	: Venta que se desea postergar.
	 * @param validaBloqueo		: Indica si se debe validar que el registro del bloqueo de asiento exista.
	 * @return nota de credito
	 * @throws Exception
	 */
	public VentaPasaje postergarBoleto(VentaPasaje boletoPostergar,Boolean validaBloqueo, VentaPasaje gastoAdministrativo)throws Exception;
	/**
	 * Realiza el guardado de la postergacion de uno o varios comprobantes
	 * @param boletosPostergar
	 * @param validaBloqueo
	 * @return
	 * @throws Exception
	 */
	public List<VentaPasaje> postergarBoleto(List<VentaPasaje> boletosPostergar,Boolean validaBloqueo, Boolean ejecutarSeqByCorrelativo)throws Exception;
	/**
	 * Realiza la reimpresion de un boleto por perdida o deterioro
	 * @param ventaOriginal		: Venta original sin ninguna modificaci�n mas que el Tipo de Movimiento.
	 * @param ventaReimprimir	: Venta que se desea reimprimir.
	 * @return -1=Fallo, 1=Exito.
	 * @throws Exception
	 */
	public int reimprimirBoleto(VentaPasaje ventaOriginal, VentaPasaje ventaReimprimir)throws Exception;
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
	 * Realiza la devoluci�n del comprobante
	 * @param venta: venta que se desea devolver.
	 * @return  nota de credito
	 * @throws Exception
	 */
	public VentaPasaje devolucionBoleto(VentaPasaje venta, VentaPasaje gastoAdministrativo)throws Exception;

	/**
	 * Busca el avance semanal de las ventas.
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
	 * Realiza la busqueda de boleto a reimprimir
	 * @param numeroDocumento	: Numero de documento del Pasajero.
	 * @param pasajero			: Nombres del pasajero.
	 * @param fechaPartida		:Fecha de partida del servicio.
	 * @return Lista con las ocurrencias.
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletosReimprimir(String numeroDocumento, String[] pasajero, String fechaPartida)throws Exception;
	/**
	 * Realiza la busqueda de los comprobantes que no tienen aun boleto de viaje.
	 * @param fechaPartida	: Fecha de partida del servicio.
	 * @param idAgencia		: Identificador de la agencia.
	 * @param idTipoComprobante : Identificador del Tipo de comprobante.
	 * @param idRol				: Identificador del rol del usuario.
	 * @param idAgenciaEmision	: Identificador de la agencia que emitio el voucher, RC.
	 * @return Lista de ocurrencias;
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarComprobantesSinBoleto(String fechaPartida, Integer idAgencia, Integer idTipoComprobante, Integer idRol, Integer idAgenciaEmision)throws Exception;


	/*UTILIZADO EN LOS TRANSBORDOS*/
	/**
	 * Buascar venta - Utilizado para el transbordo de Pasajeros
	 * @param id : Identificador de la Venta.
	 * @return
	 */
	public VentaPasaje buscarPorId(Long id);
	/**
	 * Realiza el transborde del pasajero
	 * @param numeroAsiento : N�mero de asiento
	 * @param idVentaPasaje : Identificador de la venta en donde se realizar� el Update
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
	 * Valida Ocupabilidad de todos los asientos ha a generar las ventas del servicio especial.
	 * @param idItinerario	: Identificador del Itinerario
	 * @param idRuta		: Identificador de la ruta
	 * @param numeroAsientos: N�meros de asientos a validar
	 * @param numeroPiso	: Piso de los asientos a validar
	 * @return	N�meros de Asientos ocupados.
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
	 * @param rucClienteCredito		: Numero de ruc del Cliente credito
	 * @param idUsuario		: Idenficador del Usuario.
	 * @param orden			: campos por el cual de debe de ordenar la consulta.
	 * @param incluirAnulados : true=incluye vauchers anulados, false=lo contrario.
	 * @param isSoles : indica si la consulta es en nomeda soles(true), o en moneda extranjeta(false)
	 * @param estadoBoletos : Determina el estado de los boletos (PAG) pagados, (PEN) pendientes, Null todos.
	 * @return Lista de Ventas
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarDetalleVentasAgencia(String fechaInicio, String fechaFin,String rucClienteCredito, Long idUsuario, String orden,Boolean incluirAnulados, boolean isSoles, String estadoBoletos, Integer centroCostoID,boolean byFechaReimpresion) throws Exception;

	/**
	 *Obtiene el ultimo registro de la venta, para la validaci�n en las anulaciones
	 * @param idVentaOriginal: Identificador de l aventa original
	 * @return
	 */
	public VentaPasaje buscarUltimoRegistro(Long idVentaOriginal);

	/**
	 * Busca vaucher para la anulaci�n.
	 * @param numVoucher		: N�mero del Voucher.
	 * @param numcontrol		: N�mero de control del Voucher.
	 * @param idTipoComprobante : Identificador del tipo de comprobante.
	 * @param rucCliente 		: N�merod e ruc del Cliente
	 * @param fechaPartida 		: fecha de partida
	 * @param horaPartida 		: Hora de partida del servicio
	 * @param boleto			: N�mero de boleto.
	 */
	public List<VentaPasaje> buscarVoucherForAnulacion(Integer idTipoComprobante,String numVoucher, String numcontrol,String rucCliente,String fechaPartida,String horaPartida,String boleto);
	/**
	 * Realiza la anulaci�n de la reserva.
	 * @param reserva	: objeto a anular.
	 * @return -1 error, 1=Exito.
	 */
	public int anularReserva(VentaPasaje reserva)throws Exception;

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
	 * @param numeroControl		: N�mero de Control.
	 * @param numeroBoleto		: N�mero de Boleto
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
	 * Realiza el cambio de asiento de un pasajero en el mismo servicio.
	 * @param nuevoAsiento	: Nuevo numero de asiento que se le va asignar al pasajero
	 * @param numeroPiso	: Numero del piso a donde corresponde el asiento.
	 * @param idVentaPasajes: Identificador de la venta de pasajes donde se va a realizar la actualizacion.
	 * @throws Exception
	 */
	public void cambiarAsientoPax(Integer nuevoAsiento,Integer numeroPiso, Long idVentaPasajes)throws Exception;
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
	 * @param numeroBoleto : Número de Boleto
	 * @return Lista de viajes.
	 */
	public List<VentaPasaje> buscarVentasByPasajero(Long idPasajero, String numeroBoleto, Boolean incluirReservas)throws Exception;
	/**
	 * Busca datos del boleto a liberar del manifiesto de pasajeros.
	 * @param numeroboleto	: N�mero de Boleto a buscar.
	 * @return	datos del boleto
	 * @throws Exception
	 */
	public List<VentaPasaje> buscarBoletoLiberarManifiesto(String numeroboleto)throws Exception;
	/**
	 * Realiza la anulaci�n del boleto Cr�dito.
	 * @param boleto	: Objeto venta, a anular
	 * @throws Exception
	 */
	public VentaPasaje anularBoletoCredito(VentaPasaje boleto,Usuario usuarioAuditoria)throws Exception;

	/**
	 * Busca el boleto para la venta del seguro.
	 * @param numeroBoleto	: N�mero del Boleto
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
	 * actualizar venta
	 * @param ventaPasaje
	 * @throws Exception
	 */
	public void actualizar (VentaPasaje ventaPasaje)throws Exception;
	/**
	 * Busca las ventas por punto de venta y usuario.
	 * @param fechaInicial		: Fecha incial de la busqueda.
	 * @param fechaFinal		: Fecha final de la busqueda.
	 * @param idAgencia			: Obcional, Identificador de la Agencia.
	 * @param idUsuario			: Obcional, Identificador del Usuario.
	 * @param idsTiposMovimientos	: Obcional, Identificador del tipo de comprobante.
	 * @param idFormaPago 		: Obcional, Identificador de la forma de pago.
	 * @return Lista de resultados.
	 * @throws Exception
	 */
	public List<Agencia> buscarVentasPorPuntoVenta(String fechaInicial, String fechaFinal,Integer idAgencia, Integer idUsuario, String idsTiposMovimientos, Integer idFormaPago)throws Exception;
	/**
	 * Genera una nota de credito a un determinado comprobante
	 * @param ventaAplica : Instancia de la venta a quien se va aplicar la nota de credito
	 * @param tipoNota : Instancia del tipo de nota de credito que se va aplicar
	 * @param anularMovimiento	: True=Genera un movimiento "Anulacion Sistema"; false=No genera.
	 * @param copyCanalOriginal	: True=Copia el canal a la nota de credito del Comprobante al cual se va aplicar la nota de credito
	 * @return notaCredito generada
	 * @throws Exception
	 */
	public VentaPasaje generarNotaCredito(VentaPasaje ventaAplica, TipoNota tipoNota,boolean anularMovimiento,boolean copyCanalOriginal)throws Exception;
	/**
	 * Genera una nota de credito a un determinado comprobante
	 * @param ventaAplica : Instancia de la venta a quien se va aplicar la nota de credito
	 * @param tipoNota : Instancia del tipo de nota de credito que se va aplicar
	 * @param anularMovimiento	: True=Genera un movimiento "Anulacion Sistema"; false=No genera.
	 * @param copyCanalOriginal	: True=Copia el canal a la nota de credito del Comprobante al cual se va aplicar la nota de credito
	 * @param liquidacion : Instancia de la liquidacion a la cual se asignara la nota de credito
	 * @return NotaCredito generada
	 * @throws Exception
	 */
	public VentaPasaje generarNotaCredito(VentaPasaje ventaAplica, TipoNota tipoNota,boolean anularMovimiento,boolean copyCanalOriginal, Liquidacion liquidacion)throws Exception;
	/**
	 *
	 * @param gastoAdministrativo
	 * @param generarCorrelativo : True=Genera el correlativo; false=No genera, solamente lo valida
	 * @return
	 * @throws Exception
	 */
	public int generarGastoAdministrativo(VentaPasaje gastoAdministrativo, Boolean generarCorrelativo)throws Exception;
//	/**
//	 * Proceso masivo que aplica notas de credito a un lista de ventas, con la posibilidad de generar un nuevo comprobante de venta
//	 * @param listVentas	: Lista de ventas a las cuales se le debe aplicar las notas de credito
//	 * @param tipoNota	: Tipo de nota de credito a aplicar.
//	 * @param newCompVenta	: True=si debe generar un nuevo comprobante de venta, False=Solo notas de credito
//	 * @return
//	 * @throws Exception
//	 */
//	public VentasNotas generarNotaCreditoRegularizacion(List<VentaPasaje> listVentas, TipoNota tipoNota, boolean newCompVenta)throws Exception;

	/**
	 * Procesa las anulaciones, segun su tipo.
	 * @param lstVentas	: Lista de ventas a anular
	 * @param tipoAnulacion	: Tipo de anulacion; 0=Anulacion Regular; 1=Anulacion con N.C; 2=Anulacion con N.C y emitir un nuevo comprobante
	 * @param anularMovimiento : true=Anula el registro con una movivmiento de anulacion del Sistema; False=No anula el movimiento
	 * @return Class con la ventas y notas de credito generadas
	 * @throws Exception
	 */
	public VentasNotas procesarAnulacionBy(List<VentaPasaje> lstVentas, int tipoAnulacion, boolean anularMovimiento, Liquidacion liquidacion)throws Exception;
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
	 * Busca los boletos postergados por Usuario o Agencia
	 * @param fechaDesde	: Fecha de inicio de busqueda
	 * @param fechaHasta	: Fecha fin de la busqueda
	 * @param idAgencia		: Agencia para el reporte, 0 todos.
	 * @param nroConsulta	: 1, 2, 3 segun sea el caso.
	 * @return
	 */
	public List<ResumenVentas> buscarResumenVentas(String fechaDesde, String fechaHasta, Integer idAgencia, Integer nroConsulta);

	/**
	 * Busca los boletos postergados por Usuario o Agencia
	 * @param numeroComprobante		: Numero del comprobante a obtener el historial.
	 * @return
	 */
	public List<VentaPasaje> buscarHistorialComprobante(String numeroComprobante);
	/**
	 * Guarda las ventas de Servicios Especiales
	 * @param ventaPasaje	: Objeto venta que se guardara
	 * @return
	 * @throws Exception
	 */
	public int guardarServicioEspecial(VentaPasaje ventaPasaje) throws Exception;
	/**
	 * Procesa las anulaciones de los Servicios de traslado de personal
	 * @param lstVentas			: Lista de ventas que se desea anular
	 * @param tipoAnulacion		:
	 * @param anularMovimiento	:
	 * @param liquidacion		:
	 * @return
	 * @throws Exception
	 */
	public VentasNotas procesarAnulacionServicioEspecial(List<VentaPasaje> lstVentas,int tipoAnulacion, boolean anularMovimiento, Liquidacion liquidacion) throws Exception;
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
	 * Busca las ventas para el Registro de Ventas
	 * @param fInicio	: Fecha de inicio de busqueda
	 * @param fFin		: Fecha fin de busqueda
	 * @return
	 * @throws Exception
	 */
	public List<VentasPiloto> buscarRegistroVentas(String fInicio, String fFin) throws Exception;

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
	/**
	 * @param tipoEntidad : Tipo de entidad, Agencia, Localidad, Servicio
	 * @return
	 * @throws Exception
	 */
	public Map<String, EntidadEncomiendaPasajes> buscarEquivalenciaEntidades(Integer tipoEntidad) throws Exception;

	/**
	 * Realiza la postergacion Masiva de ventas a Fecha abierta
	 * @param lstVentas	: Lista de ventas a postergar.
	 * @param motivo	: Motivo de la postergacion.
	 * @param usuario 	: Usuario que realiza la postergacion.
	 */
	public Integer postergarFAMasivo(List<VentaPasaje> lstVentas, String motivo, String usuario) throws Exception;/* Realiza la actualizaciÃ³n del correlativo al comprobante de venta
	 
	 * @param VentaPasaje: Instancia del comprobante que se va a actualizar.
	 * @return Indca si la actualización fue o no exitosa.
	 * @param ejecutarSeqByCorrelativo : Indica si debe  no ejecutar el sequenciador para generar el correlativo
	 * @throws Exception
	 */
	public void actualizarCorrelativoComprobante(Object object, Boolean ejecutarSeqByCorrelativo) throws Exception;
	/**
	 * Busca el avance de ventas de los servicios para el tarifario
	 * @param fecha					: Fecha de salida de los servicios
	 * @param servicio_id			: Identificador del Servicio
	 * @param localidad_idOrigen	: Identificador de la localidad origen
	 * @param localidad_idDestino	: Identificador de la localidad destino
	 * @return
	 * @throws Exception
	 */
	public List<TarifarioByAsientoByAvanceVentas>buscarAvanceVentasByTarifarioByAsiento(String fecha, Integer servicio_id, Integer localidad_idOrigen, Integer localidad_idDestino)throws Exception;
	/**
	 * Busca el GPS de un comprobante
	 * @param idVentaPasaje	: ID de la venta de pasaje
	 * @return
	 */
	public List<GpsComprobante> buscarGpsComprobante(Long idVentaPasaje);
}
