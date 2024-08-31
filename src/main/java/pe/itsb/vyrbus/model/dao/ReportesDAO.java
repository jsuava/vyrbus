package pe.itsb.vyrbus.model.dao;

import java.util.ArrayList;
import java.util.Date;

import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.bean.Transbordo;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.report.RptAvanceBuses;

public interface ReportesDAO extends GenericDAO {
	/**
	 * CONSULATA: TARIFARIO.
	 * @param fechaInical	: Fecha inicial.
	 * @param fechaFinal	: Fecha final
	 * @param idServicio	: Identificador del Servicio
	 * @param idLocalidad	: identificador de la Localidad
	 * @param incluirTarifaCero : true=Incluye tarifa Cero, false=no incluye
	 * @param idRuta		: Identificador de la ruta
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DetalleItinerario> tarifario(String fechaInical, String fechaFinal, Integer idServicio, Integer idLocalidad, boolean incluirTarifaCero, Integer idRuta) throws Exception;

//	/**
//	 * CONSULTA: DIARIO ACUMULADO.
//	 * @param fechaInical 	: Fecha inicial.
//	 * @param fechaFinal  	: Fecha final
//	 * @param servicio		: Nombre del Servicio a consultar
//	 * @param rutaItinerario: (0)Lima-Provincias; (1)Provincias-Lima, (2)Entre Provincias.
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList<VentaPasaje> diarioAcumulado(String fechaInical, String fechaFinal, String servicio, String rutaItinerario) throws Exception;
	/**
	 * CONSULTA: DIARIO ACUMULADO.
	 * @param fechaInicial	: Fecha inciacial de la busqueda.
	 * @param fechaFinal	: Fecha final de la busqueda.
	 * @param idServicio		: Identificador del Servicio
	 * @param limaProvincias	: Indica si la consulta es lima provincias, provincias lima, provincias o todos.
	 * @param mostrarCuadroIngresos	: true=mostrar cuadro de ingresos; false lo contrario.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> diarioAcumulado(Date fechaInicial, Date fechaFinal, long idServicio, int limaProvincias, boolean mostrarCuadroIngresos)throws Exception;
	/**
	 * CONSULTA: DETALLEDO DEL DIARIO ACUMULADO.
	 * @param fechaInicial	: Fecha inicial de la busqueda.
	 * @param fechaFinal	: Fecha final de la busqueda.
	 * @param idServicio	: Identificador del servicio.
	 * @param limaProvincias	: Indica si la consulta es lima provincias, provincias lima, provincias o todos.
	 * @param mostrarCuadroIngresos :  true=mostrar cuadro de ingresos; false lo contrario.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> diarioAcumuladoDetallado(Date fechaInicial, Date fechaFinal, long idServicio, int limaProvincias, boolean mostrarCuadroIngresos) throws Exception;

	/**
	 * CONSULTA: AVANCE SEMANAL DE VENTAS POR RUTAS.
	 * @param fechaInicial 	: Fecha Inicial
	 * @param fechaFinal	: Fecha Final
	 * @param tipoConsulta	: Indica (0) Todos, (1) ida, (2)Retorno, (3)Provincias
	 * @param transaccion	: Indica (1) Venta, (2) Reserva
	 * @return
	 * @throws Exception
	 */
	public ArrayList<VentaPasaje> avanceSemanalXRutas(String fechaInicial, String fechaFinal, String tipoConsulta, String transaccion) throws Exception;
	/**
	 * CONSULTA: PASAJEROS TRANBORDADOS.
	 * @param fechaTransInicio	: Fecha inicio del transbordo.
	 * @param fechaTransFin		: Fecha fin del Transbordo.
	 * @param origen			: Origen.
	 * @param destino			: Destino.
	 * @param boleto			: Número de Boleto.
	 * @param idPasajero		: Identificador del Pasajero.
	 * @return Lista pasajeros transbordados.
	 * @throws Exception
	 */
	public ArrayList<Transbordo> pasajerosTransbordados(String fechaTransInicio, String fechaTransFin,String origen, String destino, String boleto, Long idPasajero) throws Exception;
	/**
	 * CONSULTA: CENAS POR RUTA.
	 * @param fechaInicial	: Fecha inicio de la busqueda
	 * @param fechaFinal	: Fecha fin de la busqueda.
	 * @param tipoConsulta	: Indica el tipo de consulta, Ida,retorno o procincias
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PreferenciaAlimentaria> cenasXRutas(String fechaInicial,String fechaFinal, String tipoConsulta)throws Exception;
	/**
	 * CONSULTA: VENTAS CON PROMOCION
	 * @param fechaInicio	: Fecha inicio de la busqueda.
	 * @param fechaFin		: Fecha fin de la busqueda.
	 * @param idPromocion	: Identificador de la promoción
	 * @param tipoDescuento	: Identificador del tipo de descuento
	 * @return	Lista de ventas asociadas a una promocion.
	 * @throws Exception
	 */
	public ArrayList<Promocion>ventasPromocion(String fechaInicio,String fechaFin, String idPromocion, String tipoDescuento, Integer agencia_id, Integer usuario_id)throws Exception;
	/**
	 * CONSULTA: Todas las promociones emitidas en la venta, segun los parametros que se envíe
	 * @param fechaInicio	: Fecha incio.
	 * @param fechaFin		: Fecha fin.
	 * @return	Listado de promociones.
	 * @throws Exception
	 */
	public ArrayList<Object>ventasPromocionLstPromociones(String fechaInicio,String fechaFin)throws Exception;
	/**
	 * CONSULTA: EL DETALLE DE LAS VENTAS DE LA PROMOCION
	 * @param fechaInicio	: Fecha inicio de la busqueda.
	 * @param fechafin		: Fecha fin de la busqueda.
	 * @param idPromocion	: Identificador de la promocion.
	 * @return Lista de ventas
	 * @throws Exception
	 */
	public ArrayList<VentaPasaje>ventasPromocionDeta(String fechaInicio, String fechafin, String idPromocion)throws Exception;
	/**
	 * CONSULTA: AVANCE DE BUSES, SEGUN RANGO DE FECHAS.
	 * @param fechaInicio	: Fecha inicio de la busqueda.
	 * @param fechaFin	: Fecha fina de la busqueda.
	 * @param idLocalidadOrigen	 : Identificador de la localidad origen (Opcional)
	 * @param idLocalidadDestino : Identificador de la localidad destino (Opcional)
	 * @param idServicio : Identificador del Tipo de Servicio (Opcional)
	 * @param codigoBus : código del bus (Opcional)
	 * @return Objet RptAvanceBuses.
	 * @throws Exception
	 */
	public RptAvanceBuses avancesBuses(String fechaInicio, String fechaFin, Integer idLocalidadOrigen, Integer idLocalidadDestino, Integer idServicio, String codigoBus )throws Exception;
}
