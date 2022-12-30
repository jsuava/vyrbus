/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripciï¿½n	: Clase que centraliza las Constantes que se usa en todo el sistema.
 * Autor		: Josï¿½ Sullo Avalos
 * Fecha		: 08/04/2012
 */
package com.cystesoft.vyrbus.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * Clase que centraliza las Constantes que se usa en todo el sistema.
 * @author Josï¿½ Sullo Avalos
 * @since JDK1.6
 */
public class Constantes {
	/**
	 * Vension actual del Sistema
	 */
	public static final String SYSTEM_VERSION = "Versión 3.2.5";
		
	/**
	 * Constante para la Acciï¿½n Nuevo
	 * @see WndOpcionesMantenimiento#onCancel(int)
	 * @see WndOpcionesMantenimiento#onSave(int)
	 */
	public static final int ACTION_NEW = 0;
	/**
	 * Constante para la Acciï¿½n Modificar
	 * @see WndOpcionesMantenimiento#onCancel(int)
	 * @see WndOpcionesMantenimiento#onSave(int)
	 */
	public static final int ACTION_MODIFY =  1;
	/**
	 * Constante para la Acciï¿½n Consultar
	 * @see WndOpcionesMantenimiento#onCancel(int)
	 * @see WndOpcionesMantenimiento#onSave(int)
	 */
	public static final int ACTION_CONSULTA = 2;

	public static final String KEY_CRYPTO = "wsS3c#r1tyTB@ITSB";
	/*	Para las fechas*/
	public static final String DATE_FORMAT="dd/MM/yyyy";
	public static final String DATE_TIME_FORMAT="dd/MM/yyyy HH:mm:ss";
	public static final String DATE_TIME_SHORT_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_SHORT_FORMAT = "HH:mm";

	/*	Para el estado de Registro	*/
	public static final String VALUE_ACTIVO="A";
	public static final String VALUE_INACTIVO="I";
 	public static final String LABEL_ACTIVO_DESCP="ACTIVO";
	public static final String LABEL_INACTIVO_DESCP="INACTIVO";

	/*	Para el estado de la Liquidacion */
	public static final int LIQUI_ESTA_CERRADO=0;
	public static final int LIQUI_ESTA_ABIERTO=1;
	public static final String LIQUI_ESTA_CERRADO_LABEL="CERRADA";
	public static final String LIQUI_ESTA_ABIERTO_LABEL="ABIERTA";

	public static final int AMBOS=2;
	public static final String AMBOS_DESCP="AMBOS";

	/*	Para los combos	*/
	public static final String COMBO_LABEL_SELECCIONE = "SELECCIONE";
	public static final String COMBO_LABEL_TODOS = "TODOS";
	/*	Para verificar si fue o no correcto la operacion	*/
	public static final int CORRECT = 1;
	public static final int FAILURE = -1;
	/*	Para controlar el estado de los asientos	*/
	public static final int ASIENTO_DISPONIBLE = 0;
	public static final int ASIENTO_VENDIDO = 1;
	public static final int ASIENTO_RESERVADO = 2;
	public static final int ASIENTO_BLOQUEADO = 3;

	public static final String DISPONIBLE = "DISPONIBLE";
	public static final String VENDIDO = "VENDIDO";
	public static final String RESERVADO = "RESERVADO";
	public static final String BLOQUEADO = "BLOQUEADO";

	/*	Para la ruta de las imagenes	*/
	public static final String PATH_PARTIAL="/resources/asientos/";
	public static final String IMAGE_EXTENSION=".png";
	/*	Variables para cargar los iconos de los asientos	*/
	public static final String ICON_DISPONIBLE = PATH_PARTIAL+"asientoDisponible_";
	public static final String ICON_VENDIDO_FEMALE = PATH_PARTIAL+"asientoVendidoFemale_";
	public static final String ICON_VENDIDO_MALE = PATH_PARTIAL+"asientoVendidoMale_";
	public static final String ICON_VENDIDO_WEB_FEMALE = PATH_PARTIAL+"asientoVendidoWebFemale_";
	public static final String ICON_VENDIDO_WEB_MALE = PATH_PARTIAL+"asientoVendidoWebMale_";
	public static final String ICON_RESERVADO = PATH_PARTIAL+"asientoReservado_";
	public static final String ICON_RESERVADO_DELIVERY = PATH_PARTIAL+"asientoReservadoDelivery_";
	public static final String ICON_RESERVADO_AGENTE = PATH_PARTIAL+"asientoReservadoAgente_";
	public static final String ICON_SEMI_OCUPADO = PATH_PARTIAL+"asientoSemiOcupado_";
	public static final String ICON_BLOQUEADO = PATH_PARTIAL+"asientoBloqueado_";
	/*	Para indicar si es verdadero o falso*/
	public static final int TRUE_VALUE = 1;
	public static final int FALSE_VALUE = 0;
	/*	Para las fecha en null	*/
	public static final Date FECHA_NULL= Util.StringtoDate("01/01/1960", DATE_FORMAT);
	/*	Para el Applet	*/
	public static final int VALIDAR_APPLET = 1;
	/* Guarda el valor del IGV actual */
	public static final double IGV=18;

	/*	Para los tipos de venta	*/
	public static final int TIPO_VENTA_NORMAL = 1;
	public static final int TIPO_VENTA_FECHAABIERTA = 2;
	public static final int TIPO_VENTA_IDAVUELTA = 3;

	/*	Constantes para identificar si es una servicio de Ida o Retorno.	*/
	public static final int SERVICIO_IDA = 1;
	public static final int SERVICIO_RETORNO = 2;

	/*	Para expresar el tiempo en milisegundos	*/
	public static final long MILISEGUNDOS_X_DIA = 86400000;
	public static final long MILISEGUNDOS_X_HORA = 3600000;
	public static final long MILISEGUNDOS_X_MINUTO=  60000;
	public static final long MILISEGUNDOS_X_SEGUNDO=  1000;
	/*	Para dar formato a las fechas y horas	*/
	public static final DateFormat FORMAT_DATE = new SimpleDateFormat ("dd/MM/yyyy");
	public static final DateFormat FORMAT_YEAR = new SimpleDateFormat ("yyyy");
	public static final DateFormat FORMAT_MONTH = new SimpleDateFormat ("MM");
	public static final DateFormat FORMAT_DAY = new SimpleDateFormat ("dd");
	public static final DateFormat FORMAT_TIME = new SimpleDateFormat ("HH:mm");
	public static final DateFormat FORMAT_TIME_12HRAS = new SimpleDateFormat ("hh:mm");
	public static final DateFormat FORMAT_LONG = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
	public static final DateFormat FORMAT_DATE_TIME_24H = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");

	public static final int HORAS_MAXIMO_ANULACION=72; //Tiempo maximo en el que el comprobante se puede anular, caso contrario debe emitir una n.c.
	public static final int TIPO_ANULACION_REGULAR=0;
	public static final int TIPO_ANULACION_NC=1;
	public static final int TIPO_ANULACION_NC_NEW_COMPROBANTE=2;
	/*Guarda los IDS  de los clientes que van a utilizar el excel Encabelzados_de_reporte.xls */
//	public static final String IDS_CLIENTES_RPT_PERSONALIZADO="21996"; //-->21996=GYM,47653
	/*boton por defecto en un un messagebox question yes no*/

	/*	Tipo de Rubros	*/
	public static final Integer RUBRO_PASAJES=1;
	public static final Integer RUBRO_CARGA=2;
	public static final Integer RUBRO_AMBOS=3;
	public static final String RUBRO_PASAJES_DESC="PASAJES";
	public static final String RUBRO_CARGA_DESC="CARGA";
	public static final String RUBRO_AMBOS_DESC="AMBOS";

	public static final String SIN_TARIFA="SIN TARIFA";

	public static final String CLAVE_PAHT="TPSPSJS-";
	/*	Path para la creacion de los documentos Boleto, Manifiesto	*/
	public static final String SERVER_PROTOCOL = Executions.getCurrent().getScheme();
//	public static final String SERVER_HOST = Executions.getCurrent().getHeader("host");
//	public static final String SERVER_HOST = "ventas.tepsa.com.pe/";

//	public static final String SERVER_HOST = "localhost:8080";

	//SERVER_HOST PRODUCCION TRANSMAR
	public static final String SERVER_HOST = "119.8.145.122:8080";


	public static final String SERVER_CONTEXT = Executions.getCurrent().getContextPath();
	public static final String URL_FORMATOS = SERVER_PROTOCOL+"://"+SERVER_HOST+SERVER_CONTEXT+"/formatos/";
	public static final String URL_FORMATOS_BOLETOS = URL_FORMATOS+"boletos/";
	public static final String URL_FORMATOS_RECIBO_CAJA = URL_FORMATOS+"reciboCaja/";
	public static final String URL_FORMATOS_MANIFIESTOS = URL_FORMATOS+"manifiestos/";
	public static final String URL_FORMATOS_DESPACHOS = URL_FORMATOS+"despachos/";
	public static final String URL_FORMATOS_LISTADOS = URL_FORMATOS+"listados/";
	public static final String URL_FORMATOS_LIQUIDACION = URL_FORMATOS+"liquidaciones/";
	public static final String URL_FORMATOS_PDF = URL_FORMATOS+"pdf/";
	public static final String URL_FORMATOS_CERTIFICADOS = URL_FORMATOS+"certificadoSerguro/";
	public static final String URL_HTML = SERVER_PROTOCOL+"://"+SERVER_HOST+SERVER_CONTEXT+"/html/";
	public static final String URL_DIRECTORY_DETALLE_LIQUIDACION=URL_FORMATOS+"detalleLiquidacion/";
	public static final String URL_DIRECTORY_DETALLE_VENTA_SEGUROS=URL_FORMATOS+"detalleVentaSeguros/";
	public static final String DIRECTORY_HTML=URL_HTML;
	public static final String URL_FORMATOS_HRE = URL_FORMATOS+"hre/";
	public static final String URL_FORMAT_TICKET = URL_FORMATOS+"formatTicket/";

	public static final String DIRECTORY_BOLETOS = Util.getPath()+Util.separator+"formatos"+Util.separator+"boletos"+Util.separator;
	public static final String DIRECTORY_RECIBO_CAJA = Util.getPath()+Util.separator+"formatos"+Util.separator+"reciboCaja"+Util.separator;
	public static final String DIRECTORY_MANIFIESTOS = Util.getPath()+Util.separator+"formatos"+Util.separator+"manifiestos"+Util.separator;
	public static final String DIRECTORY_DESPACHOS = Util.getPath()+Util.separator+"formatos"+Util.separator+"despachos"+Util.separator;
	public static final String DIRECTORY_LISTADOS = Util.getPath()+Util.separator+"formatos"+Util.separator+"listados"+Util.separator;
	public static final String DIRECTORY_LIQUIDACION = Util.getPath()+Util.separator+"formatos"+Util.separator+"liquidaciones"+Util.separator;
	public static final String DIRECTORY_UPLOADS = "uploads";
	public static final String DIRECTORY_PDF = Util.getPath()+Util.separator+"formatos"+Util.separator+"pdf"+Util.separator;
	public static final String DIRECTORY_DETALLE_LIQUIDACION=Util.getPath()+Util.separator+"formatos"+Util.separator+"detalleLiquidacion"+Util.separator;
	public static final String DIRECTORY_EXCEL = Util.getPath()+Util.separator+"formatos"+Util.separator+"excel"+Util.separator;
	public static final String DIRECTORY_AFILIACIONES = Util.getPath()+Util.separator+"formatos"+Util.separator+"afiliaciones"+Util.separator;
	public static final String DIRECTORY_CERTIFICADOR_SEGURO=Util.getPath()+Util.separator+"formatos"+Util.separator+"certificadoSerguro"+Util.separator;
	public static final String DIRECTORY_DETALLE_VENTA_SEGUROS=Util.getPath()+Util.separator+"formatos"+Util.separator+"detalleVentaSeguros"+Util.separator;
	public static final String DIRECTORY_HRE=Util.getPath()+Util.separator+"formatos"+Util.separator+"hre"+Util.separator;
	public static final String DIRECTORY_FORMAT_TICKET=Util.getPath()+Util.separator+"formatos"+Util.separator+"formatTicket"+Util.separator;



	/* Constantes que indican el piso del bus*/
	public static final int PISO_UNO=0;
	public static final int PISO_DOS=1;

	/*Constante dias del anio expresado en dï¿½as*/
	public static final int DIAS_DEL_ANIO=365;

	/* Constantes que indican el Tipo de convenio con el cliente*/
	public static final String TIPCONVCLI_CREDITO_CANJE_PUBLICITARIO="CLIENTE CON CREDITO PARA CANJE PUBLICITARIO";
	public static final String TIPCONVCLI_CREDITO_SIN_CANJE_PUBLICITARIO="CLIENTE SIN CREDITO PARA CANJE PUBLICITARIO";
	public static final String TIPCONVCLI_CORPORATIVO="CLIENTE CON CREDITO CORPORATIVO";
	public static final String TIPCONVCLI_CONTADO_DESCUENTO="CLIENTE CON DESCUENTO CORPORATIVO";
	public static final String TIPCONVCLI_CONTADO="CLIENTE CONTADO";

	/* Constantes que indican el Tipo de convenio con el cliente*/
	public static final int TIPCON_CONTADO=1;
	public static final int TIPCON_CREDITO=2;
	public static final String TIPCON_CONTADO_DESC="CONTADO";
	public static final String TIPCON_CREDITO_DESC="CREDITO";

	/* Constantes que indican el Origen de un cliente corporativo*/
	public static final String ORIGEN_LIMA="L";
	public static final String ORIGEN_PROVINCIAS="P";
	public static final String ORIGEN_LIMA_DESC="LIMA";
	public static final String ORIGEN_PROVINCIAS_DESC="PROVINCIAS";

	/* Cosntantes que indican el estado de una solicitud en el proceso de la aprobancion*/
	public static final String ESTADOSOL_ACTIVA="ACT";
	public static final String ESTADOSOL_INACTIVA="INA";
	public static final String ESTADOSOL_ANULADA="ANU";
	public static final String ESTADOSOLCAR_EN_ESPERA="ESP";

	/*Conatantes que describen el estado de una solicitud en el proceso de aprobacion*/
	public static final String LABEL_ESTADOSOLCAR_EN_ESPERA_DESC="EN ESPERA";
	public static final String LABEL_ESTADOSOL_ACTIVA_DESC="ACTIVO";
	public static final String LABEL_ESTADOSOL_INACTIVA_DESC="INACTIVO";
	public static final String LABEL_ESTADOSOL_ANULADA_DESC="ANULADO";
	/*Constantes que indican el estado la solicitud de la Linea de credito de un cliente*/
	public static final String APROBADO_DESC="APROBADO";
	public static final String DESAPROBADO_DESC="DESAPROBADO";

	/*Constantes que indican si el cliente sera comisionable, caje, apliacion de Liena credito o no*/
	public static final String NO="N";
	public static final String SI="S";

	/*Numero de copias de impresiones*/
	public static final int NUMERO_COPIAS_COMPROBANTE_EXCESO = 2;
	public static final int NUMERO_COPIAS_COMPROBANTE_PASAJES = 1;
	
	/*Constante que indica la fecha de suspencion O cadicidad del convenio*/
	public static final String FECHA_DEFAULT="31/12/2029 23:59:59";

	/*Constantes que indican los niveles de aprobacion de las solicitudes contado y/o credito*/
	public static final int NIVEL_UNO=1;
	public static final int NIVEL_DOS=2;
	public static final int NIVEL_TRES=3;

	/* Constante que indica que el pasajero es indeseable. */
	public static final int PASAJERO_INDESEABLE=1;
	/* constantes que indican si la promocion es temporada alta o no*/
	public static final String PROMO_TEMPO_ALTA="A";
	public static final String PROMO_TEMPO_BAJA="B";
	public static final String PROMO_TEMPO_TODOS="*";

	/*	Constantes para la ida y el retorno	*/
	public static final String ASIENTOS_IDA = "ASIENTOS_IDA";
	public static final String ASIENTOS_RETORNO = "ASIENTOS_RETORNO";

	/**
	 * Identificador del ubigeo de Lima
	 */
	public static final String ID_UBIGEO_LIMA = "150101";

	/**
	 * Identificadores de los tipo de impresion del sistema
	 */
//	public static final int FORMATO_IMPRESION_MATRICIAL = 999;
	public static final int FORMATO_IMPRESION_TICKET = 1;
	public static final int FORMATO_IMPRESION_A4 = 2;

	/* Crados para la integracion del l os web service de Cruz del sur y civa*/
	public static final String RUC_CRUZ_DEL_SUR="20100227461";
	public static final String RUC_CIVA="20102427891";
	public static final String RUC_TEPSA="20502324927";
	public static final String RUC_TRANSMAR="20501622819";
	public static final String OPERADO_POR_CRUZ_DEL_SUR="CRUZ DEL SUR";
	public static final String OPERADO_CIVA="EXCLUCIVA";

	/**/
	public static final Double MONTO_MINIMO_EXCESO = 5.0;

	/**
	 * Pasara manejar las insidencias que se podrian dar al regsitrar la venta y la validacion con el reniec.
	 */
	public static final Integer INCIDENCIA_VENTA_PAX_DNI_INVALIDO=1;
	public static final Integer INCIDENCIA_VENTA_PAX_MENOR_EDAD=2;
	public static final String IMAGE_VALIDACION_DNI_OK="/resources/mp_aceptarEnabled.png";
	public static final String IMAGE_VALIDACION_DNI_ERROR="/resources/mp_cerrar.png";


	/*	Estado del documento*/
	public static final String ESTADO_DOCUMENTO_PAGADO="PAG";
	public static final String ESTADO_DOCUMENTO_ACTIVO="ACT";

	/*	******************************************************************************************************************************************
	 * CONSTANTES QUE GUARDAN RELACION CON LAS VARIABLES DE SESSION
	 *********************************************************************************************************************************************/
	public static final String ATRIBUTO_PARAMETROS = "PARAMETROS";
	public static final String ATRIBUTO_USUARIO = "USUARIO";
	public static final String ATRIBUTO_USUARIO_HARDWARE = "USUHAR";
	public static final String ATRIBUTO_CANAL_VENTA = "CANVEN";
	public static final String ATRIBUTO_AGENCIA = "AGENCIA";
	public static final String ATRIBUTO_TIPO_COMPROBANTE = "TIPCOM";
	public static final String ATRIBUTO_FECHA_LIQUIDACION = "FECLIQ";
	public static final String ATRIBUTO_USUARIO_APROBADOR = "USUAPR";
	public static final String ATRIBUTO_ROL = "ROL";
	public static final String ATRIBUTO_IMPRESORAS_EQUIPO = "IMPRESORAS_EQUIPO";
	public static final String ATRIBUTO_IMPRESORA="IMPRESORA";
	public static final String ATRIBUTO_DIR_MAC="DIRECCION_MAC";
	/*	******************************************************************************************************************************************
	 * CONSTANTES QUE GUARDAR RELACION CON LA BASE DE DATOS Y QUE DEBERAN CAMBIAR SI CAMBIA SUS IDs EN LA DB
	 *********************************************************************************************************************************************/
	/*Identificadores del tipo de asiento*/
	public static final int ID_TIPASI_SUITE=3;

	/*Constantes que refieren el tipo de documeto del Pasajero*/
	public static final int ID_TIPDOC_DNI=1;
	public static final int ID_TIPDOC_RUC=2;
	public static final int ID_TIPDOC_TARJETA_CIRCULACION=3;
	public static final int ID_TIPDOC_SN=9;
	public static final int ID_TIPDOC_CARNET_EXTRANJERIA=8;
	public static final int ID_TIPDOC_PASAPORTE=6;
	public static final int ID_TIPDOC_CEDULA_IDENTIDAD=7;

	/*Constantes que refieren al Servicio*/
	public static final int ID_SERVICIO_TEPSASUITE=4;
	public static final int ID_SERVICIO_TEPSACAMASUITE=5;
	public static final int ID_SERVICIO_POOL_CRUZDELSUR=99;
	public static final int ID_SERVICIO_POOL_EXCLUCIVA=98;


	/*	Para saber si es una venta o reserva DB	*/
	public static final String TIPO_OPERACION_VENTA = "1";
	public static final String TIPO_OPERACION_RESERVA = "2";
	public static final String TIPO_OPERACION_VARIOS = "3";
	public static final String TIPO_OPERACION_VENTA_POOL = "4";
	public static final String TIPO_OPERACION_VENTA_ESPECIAL = "5";
	public static final String TIPO_OPERACION_EXCESO = "6";
	public static final String TIPO_OPERACION_PERDIDA_SERVICIO = "7";

	/* Constantes que referencian al rol */
//	public static final int ID_ROL_ASISTENTE_ADMIN_COMERCIAL=17;
//	public static final int ID_ROL_GERENCIA_COMERCIAL=2;
//	public static final int ID_ROL_JEFE_VENTAS=3;
	
//	public static final int ID_ROL_ADMIN=5;
//	public static final int ID_ROL_FINANZAS=10;
	public static final int ID_ROL_SUPER_USUARIO=1;
	public static final int ID_ROL_ADMINISTRADOR=2;
	public static final int ID_ROL_COUNTER=3;
	public static final int ID_ROL_FLOTA=4;
	public static final int ID_ROL_ADMIN=5;
	
	
	public static final int ID_ROL_CLIENTE_CORPORATIVO=90;
	public static final int ID_ROL_FUNCIONARIO=91;
//	public static final int ID_ROL_REP_VENTAS=92;
	public static final int ID_ROL_AGENCIA_VIAJES=93;
//	public static final int ID_ROL_ADMIN_PUNTO_VENTA=8;
//	public static final int ID_ROL_ADMIN_COMERCIAL=6;
//	public static final int ID_ROL_FISCALIZACION=11;
//	public static final int ID_ROL_CREDITOS_COBRANZAS=12;
//	public static final int ID_ROL_MARKETING=7;
//	public static final int ID_ROL_LEGAL=25;
//	public static final int ID_ROL_GESTION_CORPORATIVA=19;
//	public static final int ID_ROL_FLOTA=20;

	/*Constantes que refieren a la Localidad*/
	public static final int ID_LOC_LIMA=1;

	/*	Constantes que referencian al tipo de movimiento	*/
	public static final int ID_TIPMOV_EFECTIVO = 1;
	public static final int ID_TIPMOV_POSTERGACION = 2;
	public static final int ID_TIPMOV_REIMPRESION = 3;
	public static final int ID_TIPMOV_CREDITO = 4;
	public static final int ID_TIPMOV_ANULACION_SISTEMA = 5;
	public static final int ID_TIPMOV_DEVOLUCION = 6;
	public static final int ID_TIPMOV_CONFIRMACION_FA = 7;
	public static final int ID_TIPMOV_FECHA_ABIERTA = 8;
	public static final int ID_TIPMOV_POSTERGACION_FA = 9;
	public static final int ID_TIPMOV_CORTESIA = 10;
	public static final int ID_TIPMOV_RESERVA = 11;
	public static final int ID_TIPMOV_PREPAGADO = 12;
	public static final int ID_TIPMOV_ANULACION = 13;
	public static final int ID_TIPMOV_GASTOS_ADMINISTRATIVOS = 14;
	public static final int ID_TIPMOV_SERVICIO_ESPECIAL = 16;
	public static final int ID_TIPMOV_GRT = 17;
	public static final int ID_TIPMOV_CANJE_GRT = 18;
	/**
	 *Movimiento que no debe ser tomado como venta, este se emite cuando a un conprobante se le aplica un NC y ese comprobante no se le genera un movimiento de anulacion(5) - 10/01/2017 - jabanto
	 */
//	public static final int ID_TIPMOV_REGULACION=15;

	/*	Constantes que referencian a los tipos de forma de pago	*/
	public static final int ID_TIPFORPAG_EFECTIVO = 1;
	public static final int ID_TIPFORPAG_CANJE_PUBLICITARIO = 2;
	public static final int ID_TIPFORPAG_CUMPLEANIOS = 3;
	public static final int ID_TIPFORPAG_CREDITO = 4;
	public static final int ID_TIPFORPAG_PUNTOS = 5;
	public static final int ID_TIPFORPAG_TRANSFERENCIA=6;
	public static final int ID_TIPFORPAG_TARJETA=7;
	public static final int ID_TIPFORPAG_CORTESIA=8;
	public static final int ID_TIPFORPAG_ORDEN_TRABAJO=9;
	public static final int ID_TIPFORPAG_PCE=13;
	public static final int ID_TIPFORPAG_YAPE=14;
//	public static final int ID_TIPFORPAG_PASE_VACACIONAL=10;

	/*	Constantes que referencian a las formas de pago	*/
	public static final int ID_FORPAG_CONTADO=1;
	public static final int ID_FORPAG_CREDITO=2;
	public static final int ID_FORPAG_CORTESIA=3;
//	public static final int ID_FORPAG_PREPAGADO=8;

	/*	Constantes que referencian a los operadores de tarjeta de credito	*/
	public static final int ID_OPETARCRE_MSTERCARD=1;
	public static final int ID_OPETARCRE_VISA=2;

	/*	Constantes que referencian a los tipos de Agencia	*/
	public static final int ID_TIPAGE_TEPSA = 1;
	public static final int ID_TIPAGE_VIAJES = 2;
	public static final int ID_TIPAGE_CORPORATIVO = 3;

	/*	Constantes que referencian a los tipos de moneda	*/
	public static final int ID_TIPMON_SOLES=1;

	/*	Constantes que referencian a los Tipos de Comprobante	*/
	public static final int ID_TIPCOM_BOLETO_VIAJE=1;
	public static final int ID_TIPCOM_FACTURA=2;
	public static final int ID_TIPCOM_RECIBO_CAJA=3;
	public static final int ID_TIPCOM_VOUCHER_AGENCIA_VIAJES=5;
	public static final int ID_TIPCOM_VOUCHER_CORPORATIVO=6;
	public static final int ID_TIPCOM_MANIFIESTO_PAX=4;
	public static final int ID_TIPCOM_BOLETA_VENTA=7;
	public static final int ID_TIPCOM_NOTA_CREDITO=8;
	public static final int ID_TIPCOM_NOTA_DEBITO=9;
	public static final int ID_TIPCOM_GUIA_TRANSPORTISTA=10;
	public static final int ID_TIPCOM_TICKET_EQUIPAJE=11;

	/* Constantes que referencian a los identificadores de los tipos de documento en transcarweb */
	public static final int TRANSCARWEB_ID_TIPCOM_PCE=3;
	
	
	
	/* Constantes que referencian a los Motivos de Cortesia*/
	public static final int ID_MOTCOR_OTROS=14;

	/*	Constantes que referencian al Sexo de las Personas	*/
	public static final int ID_SEXO_FEMENINO = 1;
	public static final int ID_SEXO_MASCULINO = 2;

	/*	Constantes que referencian a los Canales de Venta	*/
	public static final int ID_CANVEN_DELIVERY = 4;
	public static final int ID_CANVEN_AGENTE = 3;
	public static final int ID_CANVEN_WEB = 2;
	public static final int ID_CANVEN_AGENCIA_VIAJES = 5;
	public static final int ID_CANVEN_CORPORATIVO = 6;
	public static final int ID_CANVEN_COUNTER = 1;

	/* Constantes que refieren al tipo de Alimentacion*/
	public static final int ID_PREALIM_MENU_DEL_DIA = 1;

	/* Constante que refieren el Tipo de Itinerario*/
	public static final int ID_TIPITI_REGULAR = 1;
	public static final int ID_TIPITI_ESPECIAL = 2;

	/* constantes que refieren el tipo de Personal*/
	public static final int ID_TIPPER_PILOTO_COPILOTO = 3;
	public static final int ID_TIPPER_TRIPULANTE = 4;

	/* Constantes que refiren el tipo de gasto*/
	public static final int ID_TIPGAS_GASTOS_VARIOS=1;
	public static final int ID_TIPGAS_PEAJES=2;
	public static final int ID_TIPGAS_PAGO_GIROS=3;  		//CAMBIO A PAGO DE COMISIONES A COMISIONISTA
	public static final int ID_TIPGAS_CONDOCUMENTO=4;		//CAMBIO A PAGO CON YAPE
	public static final int ID_TIPGAS_CTACTE=7;

	/* Constantes que se refieren a las agencias	*/
	public static final int ID_AGENCIA_TUENTRADA = 390;
	public static final int ID_AGENCIA_SUPERMERCADOS_PERUANOS = 869;
	public static final int ID_AGENCIA_MOTA_ENGIL = 603;

	/*Constantes que se refieren al tipo de nota de credito*/
	public static final int ID_TIPNOTA_CAMBIO_RUC=1;
	public static final int ID_TIPNOTA_CAMBIO_RAZON_SOCIAL=2;
	public static final int ID_TIPNOTA_CAMBIO_DIRECCION_FISCAL=3;
	public static final int ID_TIPNOTA_CREDITO_DEVOLUCION=6;
	public static final int ID_TIPNOTA_POSTERGACION=7;
	public static final int ID_TIPNOTA_DUPLICADO=8;
	public static final int ID_TIPNOTA_CAMBIO_NOMBRE_PASAJERO=9;
	public static final int ID_TIPNOTA_CAMBIO_BOLETA_FACTURA=10;
	public static final int ID_TIPNOTA_CAMBIO_FACTURA_BOLETA=11;
	public static final int ID_TIPNOTA_CREDITO_DIFERENCIA_TARIFA_FA=12;
	public static final int ID_TIPNOTA_ANULACION=13;

	/*	******************************************************************************************************************************************
	 * CONSTANTES QUE GUARDAN RELACION CON LA TABLA PARAMETROS Y DEBEN CARGARSE DINAMICAMENTE
	 *********************************************************************************************************************************************/
	/**
	 * Tiempo extra en que se puede realizar el despacho de pasajeros, despues de la hora de embarque establesida por el itinerario. Expresados en minulos por defecto 30 minutos
	 * Ejemplo si la hora de embarque del itinerario es a las: 12:00, se puede hacer hastas las 12:30 como mï¿½ximo
	 */
	public static final int TIEMPO_EXTRA_CIERRE_DESPACHO_PASAJEROS=30;

	/**
	 * Maximo numero que se puede postergar un boleto.
	 */
	public static final int MAXIMO_POSTERGACIONES =(((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getMaximoPostergaciones());
//	public static final int MAXIMO_POSTERGACIONES = 3;
	/**
	 * Penalidad por la emision de un boleto postergado.
	 */
	public static final double PENALIDAD_POSTERGACION = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getPenalidadPostergacion());
//	public static final double PENALIDAD_POSTERGACION = 2.0;
	/**
	 * Penalidad por la reimpresion de un boleto.
	 */
	public static final double PENALIDAD_REIMPRESION= (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getPenalidadReimpresion());
//	public static final double PENALIDAD_REIMPRESION= 2.0;
	/**
	 * Penalidad por la reimpresion de un boleto.
	 */
	public static final double PENALIDAD_CAMBIO_NOMBRE= (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getPenalidadCambioNombre());
	/**
	 * Tiempo a considerar para un Pasajero acumule n viajes para ingresar al Programa Pasajero Frecuente
	 */
	public static final int TIEMPO_PASAR_PAXFREE = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoProgramaPasajeroFrecuente());
//	public static final int TIEMPO_PASAR_PAXFREE = 180;
	/**
	 * Tiempo a considerar para la caducidad de un boleto despues de ser emitido, expresado en dias.
	 */
	public static final int TIEMPO_CADUCA_BOLETO = (((Parametros)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoCaducidadBoleto());
//	public static final int TIEMPO_CADUCA_BOLETO = 180;

//	public static final int TIEMPO_EXPIRA_BLOQUEO_COMPROBANTE = (((Parametros)Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoExpiracionBloqueComprobante());

	/**
	 * Tiempo limite antes de que expire una reserva expresado en horas
	 */
	public static final int TIEMPO_EXPIRA_RESERVA = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoExpiraReserva());
//	public static final int TIEMPO_EXPIRA_RESERVA = 6;
	/**
	 * Tiempo limite para poder aceptar la postergaciï¿½n del boleto.
	 */
	public static final int TIEMPO_LIMITE_POSTERGACION = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoPostergacion());
//	public static final int TIEMPO_LIMITE_POSTERGACION = 6;
	/**
	 * Numero de viajes requeridos para ingresar al programa de Paxfree. Por defectos es 5
	 */
	public static final int NUMERO_VIAJES_PAXFREE = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getViajesRequeridosPasajeroFrecuente());
//	public static final int NUMERO_VIAJES_PAXFREE = 5;
	/**
	 * indica la cantidad de puntos ganados por pertenecer al programa PAXFREE
	 */
	public static final int PUNTOS_GANADOS_X_PAXFREE = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getPuntosAcumuladosPasajeroFrecuente());
//	public static final int PUNTOS_GANADOS_X_PAXFREE = 5;
	/**
	 * Tiempo en el que un pasajero frecuente puede realizar el canje de su boleto de cortesia por cumpleanios, pudiendo ser n meses antes o n meses despues de
     * la fecha de su cumpleaï¿½os. Por defecto es 60 dias
	 */
	public static final Integer RANGO_CANJE_CUMPLEANIOS= (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getRangoCanjeCumpleanios());
//	public static final Integer RANGO_CANJE_CUMPLEANIOS=60;
	/**
	 * numero de dias en que caducan los puntos despues de ser emitidos.
	 */
	public static final int DIAS_CADUCAN_PUNTOS=(((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoCaducidadPuntos());
//	public static final int DIAS_CADUCAN_PUNTOS=365;
	/**
	 * Indica el porcentaje para la alerta a abastecimientos para el envï¿½o de especies valoradas a la Agencia.
	 */
	public static final double ALERTAR_ENVIO_ESPECIES_VALORADAS=(((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getAlertarEnvioEspecieValorda());
//	public static final double ALERTAR_ENVIO_ESPECIES_VALORADAS=85.0;
	/**
	 * Indica el porcentaje para la alerta del envio o solicitud de manifiestos.
	 */
	public static final double ALERTAR_ENVIO_MANIFIESTOS=(((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getAlertarEnvioManifiestoPasajeros());
//	public static final double ALERTAR_ENVIO_MANIFIESTOS=85.0;
	/**
	 * Tiempo limite antes de que expire la cortesia.
	 * */
	public static final Integer TIEMPO_CADUCA_CORTESIA=30;
	/**
	 * Maximo de asientos bloqueados
	 */
	public static final int MAX_SEAT_SELECTED = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getMaximoAsientosSeleccionados());
	/**
	 * Tiempo maximo para realizar el duplicado de boleto
	 */
	public static final int TIEMPO_MAXIMO_DUPLICAR_BOLETO = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoMaximoDuplicarBoleto());
	/**
	 * Usuarios autorizados a realizar ventas remotas a corporativos y agencias de viaje
	 */
	public static final String USUARIO_REMOTO = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getUsuarioRemoto());
	/**
	 * Usuarios autorizados para realizar la anulacon de reservas de cualquier agencia.
	 */
	public static final String USUARIO_ANULA_RESERVA = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getUsuarioAnulaReserva());
	/**
	 * Tiempo en el que expira el bloqueo del asiento.
	 */
	public static final Integer TIEMPO_EXPIRA_BLOQUEO = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoExpiraBloqueo());
	/**
	 * Tiempo Maximo permitido para realizar una reserva antes de que parta el servicio, Expresados en minutos, por defecto 60 minutos.
	 */
	public static final Integer TIEMPO_MAXIMO_PERMITE_RESERVA=((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getTiempoMaximoPermiteReserva();
	/**
	 *Cantidad de meses a considerar para el calculo de la base historica. Expresado en dias 180 (6 meses)
	 */
	public static final int CALCULO_BASE_HISTORICA=180;

	/*
	 * Cantidad de viajes acumulados para canjear un pasaje de cortesia, para Transmar son 7
	 */
	public static final int NUMERO_VIAJES_ACUMULADOS = (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getViajesAcumuladosPasajero());
	/**
	 * IP del proxy que utilizara el sistema para establecer la conexiï¿½n con el WS del MTC.
	 */
	public static final String PROXY= (((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getProxy()!=null?((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS)).getProxy():null);



	/*=========================================================================================================*/
	/* PARAMETROS WEBSERVICE MTC*/
	/*=========================================================================================================*/
	/**
	 * Nï¿½mero de Ruc de la Empresa
	 */
	public static final String empresa="TRANSMAR EXPRESS SAC";
	public static final String direccion_empresa="AV. NICOLAS ARRIOLA NRO. 197";
	public static final String centro_computo = "TRANSMAR";
	public static final String correo_empresa="transmar@transmar.com.pe";
	public static final String nro_telefono="(01)265 0190";
	public static final String ruc="20501622819";
	public static final String usuario="059979";
	public static final String clave="xKIx5z";
	public static final String partida="000530PNR";

	/*	Variables para cargar los iconos de asiento para el avance	*/
	public static final String PATH_IMG_AVANCE = "/resources/avance";
	public static final String ASIENTO_AVANCE_DISPONIBLE = PATH_IMG_AVANCE+"/disponible/disponible-";
	public static final String ASIENTO_AVANCE_BLOQUEADO = PATH_IMG_AVANCE+"/bloqueado/bloqueado-";
	public static final String ASIENTO_AVANCE_ESSALUD = PATH_IMG_AVANCE+"/essalud/Essalud-";
	public static final String ASIENTO_AVANCE_WEB = PATH_IMG_AVANCE+"/ventaweb/web-";
	public static final String ASIENTO_AVANCE_CORPORATIVO = PATH_IMG_AVANCE+"/corporativo/corporativo-";
	public static final String ASIENTO_AVANCE_AGENCIA = PATH_IMG_AVANCE+"/agencia/agencia-";
	public static final String ASIENTO_AVANCE_DELIVERY = PATH_IMG_AVANCE+"/delivery/delivery-";
	public static final String ASIENTO_AVANCE_RESERVA = PATH_IMG_AVANCE+"/reserva/reserva-";
	public static final String ASIENTO_AVANCE_CORTESIA = PATH_IMG_AVANCE+"/cortesia/cortesia-";
	public static final String ASIENTO_AVANCE_COUNTER = PATH_IMG_AVANCE+"/ventacounter/counter-";

	/*	Para obtener el secuenciador de las NC y ND	*/
	public static final Integer APLICAR_NC_A_BOLETA = 1;
	public static final Integer APLICAR_NC_A_FACTURA = 2;
	public static final Integer APLICAR_ND_A_BOLETA = 1;
	public static final Integer APLICAR_ND_A_FACTURA = 2;

}
