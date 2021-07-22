/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que administra todo lo relacionado a la venta de pasajes.
 * Autor		: José Avalos Sullo
 * Fecha		: 25/06/2012
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import pe.gob.mtc.wshr.ResultIdentidad;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.CentroCosto;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Concesionario;
import com.cystesoft.vyrbus.model.bean.ConfiguracionImpresora;
import com.cystesoft.vyrbus.model.bean.ControlEspecieValorada;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.HistoricoMembresia;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartidaID;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MapaBus;
import com.cystesoft.vyrbus.model.bean.Menu;
import com.cystesoft.vyrbus.model.bean.Nacionalidad;
import com.cystesoft.vyrbus.model.bean.ObjectCiva;
import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Reniec;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TarifaClienteDetalle;
import com.cystesoft.vyrbus.model.bean.TarifaRegular;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TerminosVenta;
import com.cystesoft.vyrbus.model.bean.TipoCambio;
import com.cystesoft.vyrbus.model.bean.TipoCentroCosto;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMoneda;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientosID;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.bean.VentaTramo;
import com.cystesoft.vyrbus.service.exceptions.AgenciaNullException;
import com.cystesoft.vyrbus.service.exceptions.ApellidoPaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.CapacityExceedsException;
import com.cystesoft.vyrbus.service.exceptions.CentroCostoException;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.ConcesionarioNullException;
import com.cystesoft.vyrbus.service.exceptions.DocumentoPaxDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.DuplicateSeatException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaExpedicionNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaMenorCalendarioException;
import com.cystesoft.vyrbus.service.exceptions.FechaNacimientoException;
import com.cystesoft.vyrbus.service.exceptions.FechaViajeNoValidaException;
import com.cystesoft.vyrbus.service.exceptions.FormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.ImporteMixtoNullException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.LineaCreditoClienteException;
import com.cystesoft.vyrbus.service.exceptions.LiquidacionNullException;
import com.cystesoft.vyrbus.service.exceptions.LocalidadNullException;
import com.cystesoft.vyrbus.service.exceptions.MailIncorectoException;
import com.cystesoft.vyrbus.service.exceptions.NacionalidadException;
import com.cystesoft.vyrbus.service.exceptions.NombresNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroOperacionBancariaNullException;
import com.cystesoft.vyrbus.service.exceptions.OpenDateOnCreditNotAllowedException;
import com.cystesoft.vyrbus.service.exceptions.OperadorTarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroIndeseableException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroNoSavedExeption;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialNullException;
import com.cystesoft.vyrbus.service.exceptions.RucDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.RucInvalidoException;
import com.cystesoft.vyrbus.service.exceptions.SaldoInsuficienteException;
import com.cystesoft.vyrbus.service.exceptions.SexoNullException;
import com.cystesoft.vyrbus.service.exceptions.TarjetaCreditoNullException;
import com.cystesoft.vyrbus.service.exceptions.TelefonoNullException;
import com.cystesoft.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import com.cystesoft.vyrbus.service.exceptions.TipoComprobanteNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.UbigeoNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioHardwareNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioNullException;
import com.cystesoft.vyrbus.service.exceptions.VentaReservaException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.mappers.Asiento;
import com.cystesoft.vyrbus.service.mappers.Cafeteria;
import com.cystesoft.vyrbus.service.mappers.Coordenada;
import com.cystesoft.vyrbus.service.mappers.DetalleCalculadora;
import com.cystesoft.vyrbus.service.mappers.Monitor;
import com.cystesoft.vyrbus.service.mappers.SecuenciaTramo;
import com.cystesoft.vyrbus.service.mappers.ServiciosHigienicos;
import com.cystesoft.vyrbus.service.mappers.VentaIdaRetorno;
import com.cystesoft.vyrbus.service.util.AplicarPromocion;
import com.cystesoft.vyrbus.service.util.AsientoPool;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.RESTCiva;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSCruzdelsur;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndIFrame;

import cruzdelsur.ws.com.pe.Generic;
import cruzdelsur.ws.com.pe.Horario;
import cruzdelsur.ws.com.pe.ResultVenta;
/**
 *
 * @author MArco Oscco Espinoza
 * @since JDK1.6
 */
public class WndVentaReserva extends WndBase {
	private static final long serialVersionUID = 1L;
	public static final int SEARCH_BY_DOCUMENTO = 1;
	public static final int SEARCH_BY_NOMBRES = 2;
	public static final int SEARCH_BY_RAZON = 3;
	
	private Row rowIda;
	private Row rowRetorno;
	private Row rowFechaAbierta;
	private Row rowNacionalidad;
	private Row rowAdicional;
	private Radio rdVentaNormal;
	private Radio rdVentaFechaAbierta;
	private Radio rdVentaIdaVuelta;
	private Checkbox chkVentaRemota;
	private Checkbox chkBoletoPrepagado;
	private Checkbox chkPagoMixto;
	private Tab tabItinerario;
	private Tab tabAsientos;
	private Tab tabVenta;
	private Tab tabInformacionVentaIda;
	private Tab tabInformacionVentaRetorno;
	private Tab tabPasajero;
	private Tab tabCliente;
	private Tab tabPagos;
	private Combobox cmbTipoOperacion;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Combobox cmbAgenciaRemota;
	private Combobox cmbPtoEmbarque;
	private Combobox cmbPtoEmbarqueRetorno;
	private Combobox cmbPtoDesembarque;
	private Combobox cmbPtoDesembarqueRetorno;
	private Combobox cmbTipoDocumento;
	private Combobox cmbSexo;
//	private Combobox cmbEstadoCivil;
	private Combobox cmbTipoComprobante;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoFormaPago;
	private Combobox cmbOperadorTarjetaCredito;
	private Combobox cmbTarjetaCredito;
	private Combobox cmbAlimentacion;
	private Combobox cmbAlimentacionRetorno;
	private Combobox cmbNacionalidad;
	private Combobox cmbUsuarioRemoto;
	private Radio rdPrepagadoRemoto;
	private Radio rdBoletoRemoto;
	private Radio rdElectronicoRemoto;
	private Datebox dtbxFechaNacimiento;
//	private Combobox cmbDia;
//	private Combobox cmbMes;
//	private Combobox cmbAnio;
	private Textbox txtNumeroBoleto;
	private Textbox txtNumeroBoletoRetorno;
	private Textbox txtDocumentoPax;
	private Textbox txtApePat;
	private Textbox txtApeMat;
	private Textbox txtNombres;
	private Textbox txtDireccionPax;
	private Textbox txtTelefono;
	private Textbox txtUbigeoPax;
	private Textbox txtUbigeoIdPax;
	private Textbox txtEmailPax;
	private Textbox txtDocumentoCliente;
	private Textbox txtRazonSocial;
	private Textbox txtContactoCliente;
	private Textbox txtDireccionCliente;
	private Textbox txtTelefonoClienteOne;
	private Textbox txtTelefonoClienteTwo;
	private Textbox txtEmailCliente;
	private Textbox txtUbigeoCliente;
	private Textbox txtUbigeoIdCliente;
	private Textbox txtRubro;
	private Textbox txtOperacionBancaria;
	private Textbox txtIdPromocion;
	private Textbox txtObservacionesIda;
	private Textbox txtObservacionesRetorno;
	private Datebox dtbxExpiracionReserva;
//	private Datebox dtbxExpiracionReservaRetorno;
//	private Datebox dtbxFechaNacimiento;
	private Calendar cldrFechaPartida;
	private Calendar cldrFechaRetorno;
	private Listbox lbxItinerariosIda;
	private Listbox lbxItinerariosRetorno;
	private Listbox lbxItinerariosFechaAbierta;
	private Listbox lbxAsientos;
	private Listbox lbxAsientosIdaRetorno;
	private Listbox lbxPasajeros;
	private Listbox lbxClientes;
	private Listbox lbxUsuarioHardware;
	private Combobox cmbCentroCosto;
	private Toolbarbutton tlbbtnNuevoPax;
	private Toolbarbutton tlbbtnModificarPax;
	private Toolbarbutton tlbbtnCancelarPax;
	private Toolbarbutton tlbbtnGuardarPax;
	private Toolbarbutton tlbbtnLimpiarPax;
	private Toolbarbutton tlbbtnNuevoClient;
	private Toolbarbutton tlbbtnModificarClient;
	private Toolbarbutton tlbbtnCancelarClient;
	private Toolbarbutton tlbbtnGuardarClient;
	private Toolbarbutton tlbbtnLimpiarClient;
	private Button btnNextTabAsientos;
	private Button btnNextFechaAbierta;
	private Button btnNextTabVenta;
	private Button btnUbigeoPax;
	private Button btnUbigeoCliente;
	private Grid grdOcupabilidadIda;
	private Grid grdOcupabilidadRetorno;
//	private Grid grdPromociones;
	private Label lblOrigen;
	private Label lblOrigenRetorno;
	private Label lblDestino;
	private Label lblDestinoRetorno;
	private Label lblFechaPartida;
	private Label lblFechaPartidaRetorno;
	private Label lblFechaLlegada;
	private Label lblFechaLlegadaRetorno;
	private Label lblHoraPartida;
	private Label lblHoraPartidaRetorno;
	private Label lblHoraLlegada;
	private Label lblHoraLlegadaRetorno;
	private Label lblNroAsiento;
	private Label lblNroAsientoRetorno;
	private Label lblTarifaDeno;
//	private Label lblTarifaDenoRetorno;
	private Label lblTarifa;
	private Label lblTarifaRetorno;
	private Label lblExpiracionReserva;
//	private Label lblExpiracionReservaRetorno;
	private Label lblInformativo1;
	private Label lblDescuento;
	private Label lblInformativo2;
	private Label lblInformativo3;
	private Label lblEmail;
	private Label lblImporteEfectivo;
	private Label lblImporteTarjeta;
	private Label lblPromocion;
	private Label lblCentroCosto;
	private Label lblGrupoCentroCosto;
	private Label lblBoleto;
//	private Label lblBoletoRetorno;
	private Combobox cmbGrupoCentroCosto;
//	private Label lblFechaNacimiento;
	private Groupbox grpbxMapaIda;
	private Groupbox grpbxMapaRetorno;
	private Groupbox grpVentaRemota;
	private Groupbox grpbxListaPasajeros;
	private Groupbox grpbxListaClientes;
	private Vlayout vLayoutCalendarIda;
//	private Hlayout hlytFechaNacimiento;
	private Doublebox dblTarifa;
	private Doublebox dblTarifaRetorno;
	private Doublebox dblRecargo;
	private Doublebox dblDescuento;
	private Doublebox dblDescuentoRetorno;
	private Doublebox dblImporte;
	private Doublebox dblImporteEfectivo;
	private Doublebox dblImporteTarjeta;
	private Image imgPasajero;
	private Image imgDetalleMotivo;
	private Image imgRefreshBoleto;
//	private Image imgRefreshBoletoRetorno;
	private Image imgPromocion;
	private Image imgQuitarPromocion;
	private Image imgFidelizarPasajero;
	private Intbox ibxCantidadTrabajadores;
	private Intbox ntbxEdad;
	private Image imgValidacionDNI;
	private Combobox cmbTipoMoneda;
	private Doublebox dblTarifaOtraMoneda;
	private Doublebox dblTarifaRetornoOtraMoneda;
	private Doublebox dblDescuentoOtraMoneda;
	private Doublebox dblDescuentoRetornoOtraMoneda;
	private Doublebox dblImporteOtraMoneda;
	private Listheader listheaderOtraTarifa;
	private Listheader listheaderOtraTarifaRetorno;
	private Column columnItinerarios;
//	private Label lblTarifaDenoRetornoOtraMoneda;
	private Listheader listheaderOperadoPor;
	private Div divLogOperadorPor1;
	private Image imgLogoOperadorPor1;
	private Div divLogOperadorPor2;
	private Image imgLogoOperadorPor2;
	/*Para la alimentacion en el servicio de cruz del sur*/
	private Label lblAlimen1;
	private Combobox cmbAlimen1;
	private Label lblAlimen2;
	private Combobox cmbAlimen2;
	private Label lblAlimen3;
	private Combobox cmbAlimen3;
	private Label lblAlimen4;
	private Combobox cmbAlimen4;
	private Checkbox chbxNoTieneMail;

	private Window wndVentaReserva;
	
	
	/*	Para la calculadora	*/
	private Bandbox bndbxUsuarioHardware;

	private DetalleItinerario detalleItinerarioIda = null;
	private DetalleItinerario detalleItinerarioRetorno = null;
	private DetalleItinerario detalleItinerarioFechaAbierta = null;
	private Pasajero oPasajero = null;
	private Cliente oCliente = null;
	private VentaPasaje ventaPasaje = null;
	private VentaPasaje ventaIDA = null;
	private VentaPasaje ventaRETORNO = null;
	private DetalleItinerario detailItinerary = null;
	private LineaCreditoCliente lineaCreditoCliente=null;
	private Integer usuhar = null;
	private Agencia agencia = null;
	private Usuario usuario = null;
	private CanalVenta canalVenta = null;
	private Date fechaLiquidacion = null;
	private Promocion promocionAplicada = null;
	private UsuarioHardware usuarioHardwareRemoto = null;
	private Promocion promocionTarifa=null;
//	private PagoSoles pagoSoles=null;
	private TipoCambio tipoCambio=null;
	private TipoMoneda tipoMonedaAgencia=null;
	/**
	 * Referencia a una Agencia o Corporativo
	 */
	private Cliente clienteCredito=null; //Se puede referir a una agencia o un cliente corporativo - 16/03/2013
	
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
	
	private int action = Constantes.FAILURE;
	private String prefijoAsiento="";
	private String key = "-1";
	private Map<String, Asiento> mapaAsientosIda = null;
	private Map<String, Asiento> mapaAsientosRetorno = null;
	private ArrayList<DetalleCalculadora> lstDetalleCalculadora = new ArrayList<DetalleCalculadora>();
	private boolean convertirPaxFre = false;
	/**
	 * Para almacenar los vouchers de las agencias de viaje
	 */
	private List<VentaPasaje> lstVentasVoucher = new ArrayList<VentaPasaje>(); 
	
	/**
	 * Utilizado para ver si asignamos o no la fecha del retorno.
	 */
	private boolean asignarFechaFin = true;
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
//			/*Valida si el usuario tiene una liquidación aperturada*/
//			if(getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
//				throw new LiquidacionNullException();	
//			
			tabAsientos.setDisabled(true);
			tabVenta.setDisabled(true);
			onLoadTipoOperacion();
			dblImporte.setReadonly(true);
			dblTarifa.setReadonly(true);
						
			/*	Realizamos la carga de información a los controles combobox	*/
			UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false );
//			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(getAgencia().getId().longValue());
			UtilData.cargarDataCombo(cmbDestino, Localidad.class, true);
//			UtilData.cargarDataCombo(cmbAgenciaRemota, Agencia.class, false);
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);			//Usar los alias de los campos segun el xml de mapeo
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);	//Usar los alias de los campos segun el xml de mapeo
			UtilData.cargarDataCombo(cmbTipoDocumento, TipoDocumento.class, criteriosBusqueda, false);
			Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
			UtilData.cargarDataCombo(cmbSexo, Sexo.class, false);
//			UtilData.cargarDataCombo(cmbEstadoCivil, EstadoCivil.class, false);
			UtilData.cargarDataCombo(cmbNacionalidad, Nacionalidad.class, false);
			UtilData.cargarTipoMoneda(cmbTipoMoneda,false);
			UtilData.enlazarUbigeo(txtUbigeoIdPax, txtUbigeoPax, btnUbigeoPax,null);
			UtilData.enlazarUbigeo(txtUbigeoIdPax, txtUbigeoPax, null,txtEmailPax);
			UtilData.enlazarUbigeo(txtUbigeoIdCliente, txtUbigeoCliente, btnUbigeoCliente,null);
			
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("rubro", TipoComprobante.RUBRO_PASAJES);
			UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, criteriosBusqueda, false);
			cargarFormaPago(cmbFormaPago, false);
			UtilData.cargarDataCombo(cmbAlimentacion, PreferenciaAlimentaria.class, false);
			UtilData.cargarDataCombo(cmbAlimentacionRetorno, PreferenciaAlimentaria.class, false);
			
			disabledControlsPax(true);
			disabledControlsClient(true);
			
			lbxItinerariosIda.getPagingChild().setMold("os");
			lbxItinerariosRetorno.getPagingChild().setMold("os");

			/*	*********************************************************************************************************	*/
			/*	Obteniendo las variables de la Sesion	*/
			UsuarioHardware usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
			agencia = (Agencia)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);
			usuario = (Usuario)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
			/*Refuerza la session con del objeto agencia*/
			agencia=ServiceLocator.getAgenciaManager().buscarPorId(agencia.getId().longValue());
			
			usuhar = usuarioHardware.getId();
			/*	*********************************************************************************************************	*/
			/*	Seleccionamos por defecto como origen la localidad de la Agencia.	*/
			Util.seleccionarValorItemCombo(Localidad.class, cmbOrigen, agencia.getLocalidad().getId());
			if(agencia.getNacionalidad()!=null && agencia.getNacionalidad().getTipoMoneda()!=null){
				Util.seleccionarValorItemCombo(TipoMoneda.class, cmbTipoMoneda, agencia.getNacionalidad().getTipoMoneda().getId());
				tipoMonedaAgencia=agencia.getNacionalidad().getTipoMoneda();
				cmbTipoMoneda.setDisabled(false);
			}else{
				//Carga por defecto Soles
				Util.seleccionarValorItemCombo(TipoMoneda.class, cmbTipoMoneda, Constantes.ID_TIPMON_SOLES);
				cmbTipoMoneda.setDisabled(true);
			}
			cmbTipoMoneda.removeItemAt(0);
			
			/*	Definiendo el formato de moneda a utilizar en los controles	*/
			dblTarifa.setLocale(Locale.US);
			dblTarifaRetorno.setLocale(Locale.US);
			dblRecargo.setLocale(Locale.US);
			dblDescuento.setLocale(Locale.US);
			dblDescuentoRetorno.setLocale(Locale.US);
			dblImporte.setLocale(Locale.US);
			dblImporteEfectivo.setLocale(Locale.US);
			dblImporteTarjeta.setLocale(Locale.US);
			
			dblTarifaOtraMoneda.setLocale(Locale.US);
			dblTarifaRetornoOtraMoneda.setLocale(Locale.US);
			dblDescuentoOtraMoneda.setLocale(Locale.US);
			dblDescuentoRetornoOtraMoneda.setLocale(Locale.US);
			dblImporteOtraMoneda.setLocale(Locale.US);
			
			/*	Verificamos que no se trate de una agencia TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
				
				/*Valida concesionario para luego recuperarlo*/
				if(agencia.getConcesionario()==null)
					throw new ConcesionarioNullException();
				Concesionario concesionario=ServiceLocator.getConcesionarioManager().buscarPorId(agencia.getConcesionario().getId().longValue());
				
				/*	Busca que la agencia se encuentre registrada como cliente */
				TreeMap<String, Object> mapCliente = new TreeMap<String, Object>();
				mapCliente.put("numeroDocumento",concesionario.getRuc());
				mapCliente.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Cliente> listCliente = ServiceLocator.getClienteManager().buscarPorX(mapCliente, null);
				if(!(listCliente.size()>0))
					throw new ClienteException();
				
				clienteCredito=listCliente.get(0);
				lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(clienteCredito.getId());
				
				/*Valida el credito de la Agencia o cliente Corporativo - 15-03-2013*/
				if(lineaCreditoCliente == null || lineaCreditoCliente.getSaldo()<=0)
					throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);
				
				/*	Si se trata de una agencia corporativa	*/
				if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_CORPORATIVO)){
					mantenimientoRegistroClient(clienteCredito.getId());
					tlbbtnNuevoClient.setDisabled(true);
					tlbbtnModificarClient.setDisabled(true);
					btnUbigeoCliente.setDisabled(true);					
					lblDescuento.setValue(Constantes.TIPCONVCLI_CORPORATIVO);
					txtDocumentoCliente.setDisabled(true);
					txtRazonSocial.setDisabled(true);
				}
				
				cmbTipoOperacion.setDisabled(true);
				rdVentaFechaAbierta.setDisabled(true);
				chkBoletoPrepagado.setDisabled(true);
				chkVentaRemota.setDisabled(true);
				tlbbtnModificarPax.setVisible(false);
			}
			onSelectDefaultFormaPago();
			onSelectDefaultTipoComprobante();
//			Util.loadAnios(cmbAnio);
//			Util.loadMeses(cmbMes);
			
			if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
				listheaderOtraTarifa.setVisible(true);
				listheaderOtraTarifaRetorno.setVisible(true);
				columnItinerarios.setWidth("566px");
			}else{
				listheaderOtraTarifa.setVisible(false);
				listheaderOtraTarifaRetorno.setVisible(false);
//				columnItinerarios.setWidth("371px");
				columnItinerarios.setWidth("508px");
			}
			
			
		}catch (ConcesionarioNullException ccnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noConcecionario"));
			closeTabWindow();
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch (LineaCreditoClienteException lccex) {
			if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.noSaldoDisponible"));
			else if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.lineaNoActiva"));
			closeTabWindow();
		}catch(ClienteException cex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noCliente"));
			closeTabWindow();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.window.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		tabItinerario = (Tab)this.getFellow("tabItinerario");
		tabAsientos = (Tab)this.getFellow("tabAsientos");
		tabVenta = (Tab)this.getFellow("tabVenta");
		/*	TAB SELECCION DE ITINERARIO	*/
		cmbTipoOperacion = (Combobox)this.getFellow("cmbTipoOperacion");
		cmbUsuarioRemoto=(Combobox)this.getFellow("cmbUsuarioRemoto");
		rdPrepagadoRemoto=(Radio)this.getFellow("rdPrepagadoRemoto");
		rdBoletoRemoto=(Radio)this.getFellow("rdBoletoRemoto");
		rdElectronicoRemoto=(Radio)this.getFellow("rdElectronicoRemoto");
		lbxUsuarioHardware = (Listbox)this.getFellow("lbxUsuarioHardware");
		bndbxUsuarioHardware = (Bandbox)this.getFellow("bndbxUsuarioHardware");
		chkVentaRemota = (Checkbox)this.getFellow("chkVentaRemota");
		chkBoletoPrepagado = (Checkbox)this.getFellow("chkBoletoPrepagado");
		rdVentaNormal = (Radio)this.getFellow("rdVentaNormal");
		rdVentaFechaAbierta = (Radio)this.getFellow("rdVentaFechaAbierta");
		rdVentaIdaVuelta = (Radio)this.getFellow("rdVentaIdaVuelta");
		grpVentaRemota = (Groupbox)this.getFellow("grpVentaRemota");
		cmbAgenciaRemota = (Combobox)this.getFellow("cmbAgenciaRemota");
		cmbOrigen = (Combobox)this.getFellow("cmbOrigen");
		cmbDestino = (Combobox)this.getFellow("cmbDestino");
		vLayoutCalendarIda = (Vlayout)this.getFellow("vLayoutCalendarIda");
		cldrFechaPartida = (Calendar)this.getFellow("cldrFechaPartida");
		lbxItinerariosIda = (Listbox)this.getFellow("lbxItinerariosIda");
		rowIda = (Row)this.getFellow("rowIda");
		rowRetorno = (Row)this.getFellow("rowRetorno");
		rowFechaAbierta = (Row)this.getFellow("rowFechaAbierta");
		cldrFechaRetorno = (Calendar)this.getFellow("cldrFechaRetorno");
		lbxItinerariosRetorno = (Listbox)this.getFellow("lbxItinerariosRetorno");
		btnNextTabAsientos = (Button)this.getFellow("btnNextTabAsientos");
		lbxItinerariosFechaAbierta = (Listbox)this.getFellow("lbxItinerariosFechaAbierta");
		btnNextFechaAbierta = (Button)this.getFellow("btnNextFechaAbierta");
		/*	TAB SELECCION DE ASIENTOS	*/
		grpbxMapaRetorno = (Groupbox)this.getFellow("grpbxMapaRetorno");
		grpbxMapaIda = (Groupbox)this.getFellow("grpbxMapaIda");
		btnNextTabVenta = (Button)this.getFellow("btnNextTabVenta");
		grdOcupabilidadIda = (Grid)this.getFellow("grdOcupabilidadIda");
		grdOcupabilidadRetorno = (Grid)this.getFellow("grdOcupabilidadRetorno");
		/*	TAB INFORMACION DE LA VENTA	*/
		lbxAsientos = (Listbox)this.getFellow("lbxAsientos");
		lbxAsientosIdaRetorno = (Listbox)this.getFellow("lbxAsientosIdaRetorno");
		tabInformacionVentaIda = (Tab)this.getFellow("tabInformacionVentaIda");
		tabInformacionVentaRetorno = (Tab)this.getFellow("tabInformacionVentaRetorno");
		/*	TAB INFORMACION DE LA VENTA DE IDA	*/
		lblOrigen = (Label)this.getFellow("lblOrigen");
		lblDestino = (Label)this.getFellow("lblDestino");
		cmbPtoEmbarque = (Combobox)this.getFellow("cmbPtoEmbarque");
		cmbPtoDesembarque = (Combobox)this.getFellow("cmbPtoDesembarque");
		lblFechaPartida = (Label)this.getFellow("lblFechaPartida");
		lblFechaLlegada = (Label)this.getFellow("lblFechaLlegada");
		lblHoraPartida = (Label)this.getFellow("lblHoraPartida");
		lblHoraLlegada = (Label)this.getFellow("lblHoraLlegada");
		lblNroAsiento = (Label)this.getFellow("lblNroAsiento");
		lblTarifaDeno = (Label)this.getFellow("lblTarifaDeno");
		lblExpiracionReserva = (Label)this.getFellow("lblExpiracionReserva");
		lblTarifa = (Label)this.getFellow("lblTarifa");
		dtbxExpiracionReserva = (Datebox)this.getFellow("dtbxExpiracionReserva");
		txtNumeroBoleto = (Textbox)this.getFellow("txtNumeroBoleto");
		imgRefreshBoleto = (Image)this.getFellow("imgRefreshBoleto");
		cmbAlimentacion = (Combobox)this.getFellow("cmbAlimentacion");
		txtObservacionesIda = (Textbox)this.getFellow("txtObservacionesIda");
		lblBoleto=(Label)this.getFellow("lblBoleto");
//		lblBoletoRetorno=(Label)this.getFellow("lblBoletoRetorno");
//		lblTotalPagarPreliminar = (Label)this.getFellow("lblTotalPagarPreliminar");
		/*	TAB INFORMACION DE LA VENTA DE RETORNO	*/
		lblOrigenRetorno = (Label)this.getFellow("lblOrigenRetorno");
		lblDestinoRetorno = (Label)this.getFellow("lblDestinoRetorno");
		cmbPtoEmbarqueRetorno = (Combobox)this.getFellow("cmbPtoEmbarqueRetorno");
		cmbPtoDesembarqueRetorno = (Combobox)this.getFellow("cmbPtoDesembarqueRetorno");
		lblFechaPartidaRetorno = (Label)this.getFellow("lblFechaPartidaRetorno");
		lblFechaLlegadaRetorno = (Label)this.getFellow("lblFechaLlegadaRetorno");
		lblHoraPartidaRetorno = (Label)this.getFellow("lblHoraPartidaRetorno");
		lblHoraLlegadaRetorno = (Label)this.getFellow("lblHoraLlegadaRetorno");
		lblNroAsientoRetorno = (Label)this.getFellow("lblNroAsientoRetorno");
//		lblTarifaDenoRetorno = (Label)this.getFellow("lblTarifaDenoRetorno");
//		lblExpiracionReservaRetorno = (Label)this.getFellow("lblExpiracionReservaRetorno");
		lblTarifaRetorno = (Label)this.getFellow("lblTarifaRetorno");
//		dtbxExpiracionReservaRetorno = (Datebox)this.getFellow("dtbxExpiracionReservaRetorno");
		txtNumeroBoletoRetorno = (Textbox)this.getFellow("txtNumeroBoletoRetorno");
//		imgRefreshBoletoRetorno = (Image)this.getFellow("imgRefreshBoletoRetorno");		
		cmbAlimentacionRetorno = (Combobox)this.getFellow("cmbAlimentacionRetorno");
		txtObservacionesRetorno = (Textbox)this.getFellow("txtObservacionesRetorno");
		columnItinerarios=(Column)this.getFellow("columnItinerarios");
		
		tabPasajero = (Tab)this.getFellow("tabPasajero");
		tabCliente = (Tab)this.getFellow("tabCliente");
		tabPagos = (Tab)this.getFellow("tabPagos");
		/*	TAB PASAJERO	*/
		grpbxListaPasajeros = (Groupbox) this.getFellow("grpbxListaPasajeros");
		lblDescuento = (Label)this.getFellow("lblDescuento");
		lbxPasajeros = (Listbox)this.getFellow("lbxPasajeros");
		lblInformativo1 = (Label)this.getFellow("lblInformativo1");
		lblInformativo2 = (Label)this.getFellow("lblInformativo2");
		lblInformativo3 = (Label)this.getFellow("lblInformativo3");
		imgFidelizarPasajero = (Image)this.getFellow("imgFidelizarPasajero");
		tlbbtnNuevoPax = (Toolbarbutton) this.getFellow("tlbbtnNuevoPax");
		tlbbtnLimpiarPax = (Toolbarbutton) this.getFellow("tlbbtnLimpiarPax");
		tlbbtnModificarPax = (Toolbarbutton) this.getFellow("tlbbtnModificarPax");
		tlbbtnCancelarPax = (Toolbarbutton) this.getFellow("tlbbtnCancelarPax");
		tlbbtnGuardarPax = (Toolbarbutton) this.getFellow("tlbbtnGuardarPax");
		imgDetalleMotivo = (Image)this.getFellow("imgDetalleMotivo");
		imgPasajero = (Image)this.getFellow("imgPasajero");
		rowAdicional = (Row) this.getFellow("rowAdicional");
		cmbTipoDocumento = (Combobox)this.getFellow("cmbTipoDocumento");
		txtDocumentoPax = (Textbox)this.getFellow("txtDocumentoPax");
		txtApePat = (Textbox)this.getFellow("txtApePat");
		txtApeMat = (Textbox)this.getFellow("txtApeMat");
		txtNombres = (Textbox)this.getFellow("txtNombres");
		txtDireccionPax = (Textbox)this.getFellow("txtDireccionPax");
		txtTelefono = (Textbox)this.getFellow("txtTelefono");
		txtUbigeoPax = (Textbox)this.getFellow("txtUbigeoPax");
		txtUbigeoIdPax = (Textbox)this.getFellow("txtUbigeoIdPax");
		btnUbigeoPax = (Button)this.getFellow("btnUbigeoPax");
		txtEmailPax = (Textbox)this.getFellow("txtEmailPax");
		ntbxEdad = (Intbox)this.getFellow("ntbxEdad");
		imgValidacionDNI=(Image)this.getFellow("imgValidacionDNI");
		dtbxFechaNacimiento=(Datebox)this.getFellow("dtbxFechaNacimiento");
		chbxNoTieneMail=(Checkbox)this.getFellow("chbxNoTieneMail");
//		dtbxFechaNacimiento = (Datebox)this.getFellow("dtbxFechaNacimiento");
//		cmbDia = (Combobox)this.getFellow("cmbDia");
//		cmbMes = (Combobox)this.getFellow("cmbMes");
//		cmbAnio = (Combobox)this.getFellow("cmbAnio");
		cmbSexo = (Combobox)this.getFellow("cmbSexo");
//		cmbEstadoCivil = (Combobox)this.getFellow("cmbEstadoCivil");
		cmbNacionalidad = (Combobox)this.getFellow("cmbNacionalidad");
		rowNacionalidad = (Row)this.getFellow("rowNacionalidad");
		lblEmail = (Label)this.getFellow("lblEmail");
		/*	TAB CLIENTE	*/
		grpbxListaClientes = (Groupbox) this.getFellow("grpbxListaClientes");
		lbxClientes = (Listbox)this.getFellow("lbxClientes");
		txtDocumentoCliente = (Textbox)this.getFellow("txtDocumentoCliente");
		txtRazonSocial = (Textbox)this.getFellow("txtRazonSocial");
		txtContactoCliente = (Textbox)this.getFellow("txtContactoCliente");
		txtDireccionCliente = (Textbox)this.getFellow("txtDireccionCliente");
		txtTelefonoClienteOne = (Textbox)this.getFellow("txtTelefonoClienteOne");
		txtTelefonoClienteTwo = (Textbox)this.getFellow("txtTelefonoClienteTwo");
		txtEmailCliente = (Textbox)this.getFellow("txtEmailCliente");
		txtUbigeoCliente = (Textbox)this.getFellow("txtUbigeoCliente");
		txtUbigeoIdCliente = (Textbox)this.getFellow("txtUbigeoIdCliente");
		btnUbigeoCliente = (Button)this.getFellow("btnUbigeoCliente");
		tlbbtnNuevoClient = (Toolbarbutton) this.getFellow("tlbbtnNuevoClient");
		tlbbtnLimpiarClient = (Toolbarbutton) this.getFellow("tlbbtnLimpiarClient");
		tlbbtnModificarClient = (Toolbarbutton) this.getFellow("tlbbtnModificarClient");
		tlbbtnCancelarClient = (Toolbarbutton) this.getFellow("tlbbtnCancelarClient");
		tlbbtnGuardarClient = (Toolbarbutton) this.getFellow("tlbbtnGuardarClient");
		txtRubro = (Textbox)this.getFellow("txtRubro");
		ibxCantidadTrabajadores = (Intbox)this.getFellow("ibxCantidadTrabajadores");
		lblCentroCosto = (Label)this.getFellow("lblCentroCosto");
		cmbCentroCosto = (Combobox)this.getFellow("cmbCentroCosto");
		lblGrupoCentroCosto=(Label)this.getFellow("lblGrupoCentroCosto");
		cmbGrupoCentroCosto=(Combobox)this.getFellow("cmbGrupoCentroCosto");
		/*	TAB PAGOS	*/
		dblTarifa = (Doublebox)this.getFellow("dblTarifa");
		dblTarifaRetorno = (Doublebox)this.getFellow("dblTarifaRetorno");
		dblRecargo = (Doublebox)this.getFellow("dblRecargo");
		dblDescuento = (Doublebox)this.getFellow("dblDescuento");
		dblDescuentoRetorno = (Doublebox)this.getFellow("dblDescuentoRetorno");
		txtIdPromocion = (Textbox)this.getFellow("txtIdPromocion");
		imgPromocion = (Image)this.getFellow("imgPromocion");
		imgQuitarPromocion = (Image)this.getFellow("imgQuitarPromocion");
		dblImporte = (Doublebox)this.getFellow("dblImporte");
		chkPagoMixto = (Checkbox)this.getFellow("chkPagoMixto");
		lblImporteEfectivo = (Label)this.getFellow("lblImporteEfectivo");
		lblImporteTarjeta = (Label)this.getFellow("lblImporteTarjeta");
		lblPromocion = (Label)this.getFellow("lblPromocion");
		dblImporteEfectivo = (Doublebox)this.getFellow("dblImporteEfectivo");
		dblImporteTarjeta = (Doublebox)this.getFellow("dblImporteTarjeta");
		txtOperacionBancaria = (Textbox)this.getFellow("txtOperacionBancaria");
		cmbTipoComprobante = (Combobox)this.getFellow("cmbTipoComprobante");
		cmbFormaPago = (Combobox)this.getFellow("cmbFormaPago");
		cmbTipoFormaPago = (Combobox)this.getFellow("cmbTipoFormaPago");
		cmbOperadorTarjetaCredito = (Combobox)this.getFellow("cmbOperadorTarjetaCredito");
		cmbTarjetaCredito = (Combobox)this.getFellow("cmbTarjetaCredito");
		cmbTipoMoneda=(Combobox)this.getFellow("cmbTipoMoneda");
		
		dblTarifaOtraMoneda=(Doublebox)this.getFellow("dblTarifaOtraMoneda");
		dblTarifaRetornoOtraMoneda=(Doublebox)this.getFellow("dblTarifaRetornoOtraMoneda");
		dblDescuentoOtraMoneda=(Doublebox)this.getFellow("dblDescuentoOtraMoneda");
		dblDescuentoRetornoOtraMoneda=(Doublebox)this.getFellow("dblDescuentoRetornoOtraMoneda");
		dblImporteOtraMoneda=(Doublebox)this.getFellow("dblImporteOtraMoneda");
//		lblTarifaDenoRetornoOtraMoneda=(Label)this.getFellow("lblTarifaDenoRetornoOtraMoneda");
		listheaderOtraTarifa=(Listheader)this.getFellow("listheaderOtraTarifa");
		listheaderOtraTarifaRetorno=(Listheader)this.getFellow("listheaderOtraTarifaRetorno");
		listheaderOperadoPor=(Listheader)this.getFellow("listheaderOperadoPor");
		divLogOperadorPor1=(Div)this.getFellow("divLogOperadorPor1");
		imgLogoOperadorPor1=(Image)this.getFellow("imgLogoOperadorPor1");
		divLogOperadorPor2=(Div)this.getFellow("divLogOperadorPor2");
		imgLogoOperadorPor2=(Image)this.getFellow("imgLogoOperadorPor2");
		/*Para la alimentacion en el servicio de cruz del sur*/
		lblAlimen1=(Label)this.getFellow("lblAlimen1");
		cmbAlimen1=(Combobox)this.getFellow("cmbAlimen1");
		lblAlimen2=(Label)this.getFellow("lblAlimen2");
		cmbAlimen2=(Combobox)this.getFellow("cmbAlimen2");
		lblAlimen3=(Label)this.getFellow("lblAlimen3");
		cmbAlimen3=(Combobox)this.getFellow("cmbAlimen3");
		lblAlimen4=(Label)this.getFellow("lblAlimen4");
		cmbAlimen4=(Combobox)this.getFellow("cmbAlimen4");
		
		wndVentaReserva=(Window)this.getFellow("wndVentaReserva");
		
		
		txtNumeroBoleto.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(cmbTipoComprobante.getSelectedIndex()>0 && ((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE){					
					txtNumeroBoleto.setText(Util.autocompleNumberBoleto(txtNumeroBoleto.getText().trim()));
				}
			}
		});
		
		imgRefreshBoleto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				onLoadEspecieValorada(txtNumeroBoleto);
			}
		});
		
		/*	En caso seleccione la opcion boleto prepagado	*/
		chkBoletoPrepagado.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				seleccionarPrepagado();
			}
		});
		
		/*	En caso seleccione la opcion Venta Remota	*/
		chkVentaRemota.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				onSelectVentaRemota();
			}
		});
		
		/*	En caso haga docble click en la lista de pasajeros, muestra la informacion del mismo.	*/
		lbxPasajeros.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				mantenimientoRegistroPax(new Long((String) lbxPasajeros.getSelectedItem().getValue()));
				grpbxListaPasajeros.setVisible(false);
			}
		});
		
		/*	En caso haga doble click en la lista de clientes, muestra la informacion del mismo.	*/
		lbxClientes.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				mantenimientoRegistroClient(new Long((String) lbxClientes.getSelectedItem().getValue()));
				grpbxListaClientes.setVisible(false);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtDocumentoPax realiza la busqueda del pasajero	*/
		txtDocumentoPax.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtDocumentoPax.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtDocumentoPax);
					else
						onSearchPax(SEARCH_BY_DOCUMENTO);
				}else
					txtApePat.setFocus(true);
			}
		});
//		/*	En caso haya cambiado el contenido del control txtDocumentoPax realiza la busqueda del pasajero	*/
//		txtDocumentoPax.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
//					if(txtDocumentoPax.getText().trim().equals(""))
//						DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtDocumentoPax);
//					else
//						onSearchPax(SEARCH_BY_DOCUMENTO);
//				}else
//					txtApePat.setFocus(true);
//			}
//		});
		
		/*	En caso haya apretado la tecla enter en el control txtApePat realiza la busqueda del pasajero	*/
		txtApePat.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtApeMat.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtApeMat realiza la busqueda del pasajero	*/
		txtApeMat.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtNombres.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtNombres realiza la busqueda del pasajero	*/
		txtNombres.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtApePat.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
					else if(txtNombres.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
					else
						onSearchPax(SEARCH_BY_NOMBRES);
				}else
					txtDireccionPax.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtDocumentoCliente realiza la busqueda del cliente	*/
		txtDocumentoCliente.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtDocumentoCliente.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"), txtDocumentoCliente);
					else
						onSearchClient(SEARCH_BY_DOCUMENTO);
				}else
					txtRazonSocial.setFocus(true);
			}
		});
		
		/*	En caso haya apretado la tecla enter en el control txtRazonSocial realiza la busqueda del cliente	*/
		txtRazonSocial.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(action!=Constantes.ACTION_NEW && action!=Constantes.ACTION_MODIFY){
					if(txtRazonSocial.getText().trim().equals(""))
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"), txtRazonSocial);
					else
						onSearchClient(SEARCH_BY_RAZON);
				}else
					txtContactoCliente.setFocus(true);
			}
		});
		
		tabPasajero.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				cmbTipoDocumento.setFocus(true);
			}
		});
		
		tabCliente.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				txtDocumentoCliente.setFocus(true);
			}
		});
		
		tabPagos.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				cmbTipoFormaPago.setFocus(true);
			}
		});
		
		imgPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				imgPromocion_loadPromociones();
			}
		});
		
		imgQuitarPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				quitarPromocion();
			}
		});
		
		imgFidelizarPasajero.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				convertirPaxfree();
			}
		});
		
		cmbDestino.addEventListener(Events.ON_CHANGING, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lbxItinerariosIda.getItems().clear();
				lbxItinerariosRetorno.getItems().clear();
//				onBuscarItinerarios();
			}
		});
		cmbDestino.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lbxItinerariosIda.getItems().clear();
				lbxItinerariosRetorno.getItems().clear();
//				onBuscarItinerarios();
			}
		});
		
		cmbAlimentacion.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
 				if(rdVentaIdaVuelta.isChecked()){
					if(cmbAlimentacion.getSelectedItem().getValue() instanceof PreferenciaAlimentaria)
						Util.seleccionarValorItemCombo(PreferenciaAlimentaria.class, cmbAlimentacionRetorno, ((PreferenciaAlimentaria)cmbAlimentacion.getSelectedItem().getValue()).getId());
					else
						cmbAlimentacionRetorno.setSelectedIndex(0);
				}
			}
		});
		
		cmbAlimentacionRetorno.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(rdVentaIdaVuelta.isChecked()){
					if(cmbAlimentacionRetorno.getSelectedItem().getValue() instanceof PreferenciaAlimentaria)
						Util.seleccionarValorItemCombo(PreferenciaAlimentaria.class, cmbAlimentacion, ((PreferenciaAlimentaria)cmbAlimentacionRetorno.getSelectedItem().getValue()).getId());
					else
						cmbAlimentacion.setSelectedIndex(0);
				}
			}
		});
		
		cmbAgenciaRemota.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
 				loadUsuarioRemoto();
 				loadUsuarioHardware();
			}
		});
		
		lbxUsuarioHardware.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				onSelectUsuarioHardware();
			}
		});
		
//		cmbMes.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				if(cmbAnio.getSelectedIndex()>0){
//					if(cmbMes.getSelectedItem().getValue() instanceof Integer)
//						Util.loadDias(cmbDia, (Integer)cmbMes.getSelectedItem().getValue(), (Integer)cmbAnio.getSelectedItem().getValue());
//				}
//				
//			}
//		});
//		
//		cmbAnio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
//			public void onEvent(Event e){
//				Util.loadMeses(cmbMes);
//				cmbDia.setSelectedIndex(-1);
//				cmbDia.setText("");
//			}
//		});
		
		cmbGrupoCentroCosto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				loadCentroCosto();
			}
		});
		
		ntbxEdad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(ntbxEdad.getText().trim().isEmpty()))
					dtbxFechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(calcularFechaNacimiento()));				
			}			
		});
		
		chbxNoTieneMail.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				txtEmailPax.setDisabled(chbxNoTieneMail.isChecked());				
			}
		});
	}
	
	/**
	 * Realiza el llenado del combo que tiene la información sobre el tipo de Operacion.
	 */
	private void onLoadTipoOperacion(){
		Comboitem cmbItem = new Comboitem("VENTA");
		cmbItem.setValue(Constantes.TIPO_OPERACION_VENTA);
		cmbTipoOperacion.appendChild(cmbItem);
		cmbItem = new Comboitem("RESERVA");
		cmbItem.setValue(Constantes.TIPO_OPERACION_RESERVA);
		cmbTipoOperacion.appendChild(cmbItem);
		cmbTipoOperacion.setSelectedIndex(0);		
	}
	
	/**
	 * Para seleccionar el Tipo de Comprobante por defecto
	 */
	private void onSelectDefaultTipoComprobante(){
		txtNumeroBoleto.setReadonly(true);
		for(Comboitem comboitem : cmbTipoComprobante.getItems()){
			/*	Si la agencia pertenece a TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				/*	Si es PREPAGADO	*/
//				if(chkBoletoPrepagado.isChecked()){ //##End Begin 10/11/2016 - jabanto
				if(rdPrepagadoRemoto.isChecked()){ //##Begin 10/11/2016 - jabanto
					if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_RECIBO_CAJA){
						cmbTipoComprobante.setSelectedItem(comboitem);
						lblBoleto.setValue("N° RECIBO CAJA :");
					}
				}else{
					/*End Begin 21/10/2016 - jabanto*/
//					if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE)
//						cmbTipoComprobante.setSelectedItem(comboitem);
					
					/*Begin 21/10/2016 - jabanto [Modify 10/11/2016]*/
					if(chkVentaRemota.isChecked() && rdBoletoRemoto.isChecked()){
						lblBoleto.setValue("N° BOLETO :");
						if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE){
							cmbTipoComprobante.setSelectedItem(comboitem);
							txtNumeroBoleto.setReadonly(false);
						}
					}else{
						if(oCliente==null){
							if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA){
								cmbTipoComprobante.setSelectedItem(comboitem);
								lblBoleto.setValue("N° BOLETA :");
							}
						}else{
							if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
								cmbTipoComprobante.setSelectedItem(comboitem);
								lblBoleto.setValue("N° FACTURA :");
							}
						}
					}
				}			
			}else if(agencia.getTipoAgencia().getId().intValue() == Constantes.ID_TIPAGE_VIAJES){	//Si es AGENCIA DE VIAJES
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){
					cmbTipoComprobante.setSelectedItem(comboitem);
					lblBoleto.setValue("N° VOUCHER :");
				}
			}else if(agencia.getTipoAgencia().getId().intValue() == Constantes.ID_TIPAGE_CORPORATIVO){		// Si es CORPORATIVO
				if(comboitem.getValue() instanceof TipoComprobante && ((TipoComprobante)comboitem.getValue()).getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO){
					cmbTipoComprobante.setSelectedItem(comboitem);
					lblBoleto.setValue("N° VOUCHER :");
				}
			}
		}
		cmbTipoComprobante.setDisabled(true);
	}
	
	/**
	 * Selecciona por defecto el item del Combobox Forma de Pago.
	 */
	private void onSelectDefaultFormaPago(){
		/*	Seleccionamos por defecto la Forma de pago	*/
		for(Comboitem comboitem : cmbFormaPago.getItems()){
			/*	Si la agencia pertenece a TEPSA*/
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				if(comboitem.getValue() instanceof FormaPago) {
					if(((FormaPago)comboitem.getValue()).getId().equals(Constantes.ID_FORPAG_CONTADO)){
						cmbFormaPago.setSelectedItem(comboitem);
						onLoadTipoFormaPago();
						for(Comboitem item : cmbTipoFormaPago.getItems()){
							if(item.getValue() instanceof TipoFormaPago && ((TipoFormaPago)item.getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_EFECTIVO)
								cmbTipoFormaPago.setSelectedItem(item);
						}
					}
				}
			}else{
				if(comboitem.getValue() instanceof FormaPago && ((FormaPago)comboitem.getValue()).getId().intValue()==Constantes.ID_FORPAG_CREDITO){
					cmbFormaPago.setSelectedItem(comboitem);
					onLoadTipoFormaPago();					
				}
			}
		}
		cmbFormaPago.setDisabled(true);
	}
	
	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 */
	private void onLoadEspecieValorada(Textbox txtBoleto)throws Exception{
		EspecieValorada especieValorada=null;
		ControlEspecieValorada controlEspecieValorada = null;
		if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//			if(chkBoletoPrepagado.isChecked()){
			if(rdPrepagadoRemoto.isChecked()){
				if(chkVentaRemota.isChecked()){
					especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_RECIBO_CAJA,(Agencia)cmbAgenciaRemota.getSelectedItem().getValue(), false);
					txtBoleto.setValue(especieValorada.toString());
				}else{
//					txtBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_RECIBO_CAJA, agencia,false));
					/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//					especieValorada=UtilData.buscarEspecieValorada(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), agencia, false);
//					txtBoleto.setValue(especieValorada.toString());
					controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), agencia, false, getUsuarioHardware(), null);
					txtBoleto.setValue(controlEspecieValorada.toString());
					/*END 16/06/2021 - javalos - Correlativo by caja*/
				}
			}else if(chkVentaRemota.isChecked()){
//				txtBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardwareRemoto.getId()));
				if(rdElectronicoRemoto.isChecked()){
					/*BEGIN 16/06/2021 - javalos - Correlativo by caja*/
//					especieValorada=UtilData.buscarEspecieValorada(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), (Agencia)cmbAgenciaRemota.getSelectedItem().getValue(), false);
//					txtBoleto.setValue(especieValorada.toString());
					controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), (Agencia)cmbAgenciaRemota.getSelectedItem().getValue(), false, getUsuarioHardware(), null);
					txtBoleto.setValue(controlEspecieValorada.toString());
					/*END 16/06/2021 - javalos - Correlativo by caja*/
				}else if(rdBoletoRemoto.isChecked())
					txtBoleto.setValue("");
			}else{
//				txtBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuhar));
				controlEspecieValorada=UtilData.buscarEspecieValoradaByCaja(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId(), getAgencia(), false, getUsuarioHardware(), null);
				txtBoleto.setValue(controlEspecieValorada.toString());
			}
		}else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES){
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, agencia,false);
			txtBoleto.setValue(especieValorada.toString());
		}else{
			especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, agencia,false);
			txtBoleto.setValue(especieValorada.toString());
		}
	}
	
	/**
	 * Para habilitar o deshabilitar controles segun el tipo de operación VENTA o RESERVA
	 */
	public void onSelectTipoOperacion(){
		if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
			if(cmbTipoOperacion.getSelectedIndex()==0){
				rdVentaFechaAbierta.setDisabled(false);
				rdVentaIdaVuelta.setDisabled(false);
				chkBoletoPrepagado.setDisabled(false);
				
				lblExpiracionReserva.setVisible(false);
				dtbxExpiracionReserva.setVisible(false);
				lblTarifa.setVisible(true);
				lblTarifaDeno.setVisible(true);
				chkPagoMixto.setDisabled(false);
				rdVentaNormal.setChecked(true);
				onSelectTipoVenta();
			}else{
				rdVentaFechaAbierta.setDisabled(true);
				chkBoletoPrepagado.setDisabled(true);
				chkBoletoPrepagado.setChecked(false);
				
				lblExpiracionReserva.setVisible(true);
				dtbxExpiracionReserva.setVisible(true);
				lblTarifa.setVisible(false);
				lblTarifaDeno.setVisible(false);
				chkPagoMixto.setDisabled(true);
				/*add jabanto 27/07/2013*/
				rdVentaFechaAbierta.setChecked(false);
				rdVentaNormal.setChecked(true);
				onSelectTipoVenta();
			}
		}else{
			rdVentaFechaAbierta.setDisabled(true);
			rdVentaIdaVuelta.setDisabled(false);
			chkBoletoPrepagado.setDisabled(true);
			chkVentaRemota.setDisabled(true);
			chkPagoMixto.setDisabled(true);
		}
	}
	
	/**
	 * Metodo para identificar el tipo de venta que se desea realizar.
	 */
	public void onSelectTipoVenta(){		
		if(rdVentaIdaVuelta.isSelected()){
			cldrFechaRetorno.setValue(new Date(new Date().getTime()+Constantes.MILISEGUNDOS_X_DIA));
			rowIda.setVisible(true);
			rowRetorno.setVisible(true);
			rowFechaAbierta.setVisible(false);
			grpbxMapaRetorno.setVisible(true);
			vLayoutCalendarIda.setVisible(true);
			imgPromocion.setVisible(true);
			if(chkBoletoPrepagado.isChecked()){
				rdVentaNormal.setSelected(true);
				onSelectTipoVenta();
			}
		}else if(rdVentaFechaAbierta.isSelected()){
			rowIda.setVisible(false);
			rowRetorno.setVisible(false);
			rowFechaAbierta.setVisible(true);
			grpbxMapaRetorno.setVisible(false);
			vLayoutCalendarIda.setVisible(false);
			imgPromocion.setVisible(false);
			if(chkBoletoPrepagado.isChecked()){
				rdVentaNormal.setSelected(true);
				onSelectTipoVenta();
			}
		}else if(rdVentaNormal.isSelected()){
			rowIda.setVisible(true);
			rowRetorno.setVisible(false);
			rowFechaAbierta.setVisible(false);
			grpbxMapaRetorno.setVisible(false);
			vLayoutCalendarIda.setVisible(true);
			imgPromocion.setVisible(true);
		}
		asignarFechaFin=true;
		limpiarDatosItinerario();
	} 
	
	/**
	 * Realiza la limpieza de las variables globales relacionadas con los itinerarios, mapa de asientos y listbox de itinerarios.
	 */
	private void limpiarDatosItinerario(){
		lbxItinerariosIda.getItems().clear();
		lbxItinerariosRetorno.getItems().clear();
		lbxItinerariosFechaAbierta.getItems().clear();
		mapaAsientosIda = null;
		mapaAsientosRetorno = null;
		detalleItinerarioIda = null;
		detalleItinerarioRetorno = null;
		detalleItinerarioFechaAbierta = null;
	}
	
	/**
	 * Para limpiar la lista de itinerarios en caso se seleccione otra fecha de partida. 
	 */
	public void onSelectCalendarIda() throws Exception{
		limpiarDatosItinerario();
		if(rdVentaIdaVuelta.isSelected()){
			if(asignarFechaFin){
				cldrFechaRetorno.setValue(new Date(cldrFechaPartida.getValue().getTime()+Constantes.MILISEGUNDOS_X_DIA));
			}
		}else
			onBuscarItinerarios();
	}
	
	/**
	 * Para limpiar la lista de itinerarios en caso se seleccione otra fecha de retorno. 
	 */
	public void onSelectCalendarRetorno(){
		if(Util.comparaFechas(cldrFechaRetorno.getValue(), cldrFechaPartida.getValue(), Util.OPER_MENOR_IGUAL)){
			DlgMessage.warning(Messages.getString("WndVentaReserva.warning.fechaRetornoNoValida"));
			cldrFechaRetorno.setValue(cldrFechaPartida.getValue());
		}
		asignarFechaFin = false;
		limpiarDatosItinerario();
	}
	
	/**
	 * Realiza la busqueda de los itinerarios segun la fecha de partida, origen o destino.
	 */
	public void onBuscarItinerarios(){
		try{
			Date fechaActual= Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
			if (cldrFechaPartida.getValue().getTime() < fechaActual.getTime())
				throw new FechaViajeNoValidaException();
			else if(rdVentaIdaVuelta.isChecked() && 
					Util.comparaFechas(cldrFechaRetorno.getValue(), cldrFechaPartida.getValue(), Util.OPER_MENOR_IGUAL))
				throw new FechaMenorCalendarioException();
			
			
			lbxItinerariosIda.getItems().clear();
			lbxItinerariosRetorno.getItems().clear();
			lbxItinerariosFechaAbierta.getItems().clear();
			if(!(cmbOrigen.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.ORIGEN);
			else if(rdVentaIdaVuelta.isSelected() && !(cmbDestino.getSelectedItem().getValue() instanceof Localidad))
				throw new LocalidadNullException(LocalidadNullException.DESTINO);				
			
			String fechaPartida = Util.DatetoString(cldrFechaPartida.getValue(), Constantes.DATE_FORMAT);
			String origen = cmbOrigen.getText();
			String destino = "";
			if(cmbDestino.getSelectedItem().getValue() instanceof Localidad)
				destino = cmbDestino.getText();
			
			/*	Si no se trata de una venta a fecha abierta	*/
			if(!rdVentaFechaAbierta.isSelected()){
				/*Consulta itinerarios - TEPSA*/
				List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaPartida, origen, destino);
				
				//VALIDO SOLO PARA LA VENTA - (No aplica el ida y vuelta)
				if(rdVentaNormal.isChecked() && cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_VENTA) 
						&& !chkVentaRemota.isChecked() && !chkBoletoPrepagado.isChecked()
						&& getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
					
//Comentado por MAOE 27/10/2019 ya no es aplicable					
					/*Consulta itinerarios - CRUZ DEL SUR*/
//					Localidad localidadOriegn=cmbOrigen.getSelectedItem().getValue();
//					Localidad localidadDestino=cmbDestino.getSelectedItem().getValue();
//					List<DetalleItinerario> itinerariosCruzdelSur =WSCruzdelsur.getHorarios(fechaPartida, localidadOriegn.getId(), localidadDestino.getId());
//					if(itinerariosCruzdelSur!=null){
//						for(DetalleItinerario detalleItinerario:itinerariosCruzdelSur){
//							lstItinerarios.add(detalleItinerario);
//						}	
//					}
//					
//					/*Consulta itinerarios - CIVA*/
//					itinerariosCruzdelSur = RESTCiva.getItienrarios(fechaPartida, localidadOriegn.getId(), localidadDestino.getId());
//					if(itinerariosCruzdelSur!=null){
//						for(DetalleItinerario detalleItinerario:itinerariosCruzdelSur){
//							lstItinerarios.add(detalleItinerario);
//						}	
//					}					
				}
				
							
				if(lstItinerarios.size()>0){
					Listitem item = null;
					Listcell cell = null;
					/*	Llenando el listbox con los itinerarios encontrados	*/
					for(DetalleItinerario detalleItinerario : lstItinerarios){
						listheaderOperadoPor.setVisible(false);
						if(detalleItinerario.getItinerario().getTipoItinerario().getId().equals(Constantes.ID_TIPITI_REGULAR)){
							item = new Listitem();
							if(detalleItinerario.getItinerario().getOperadoPor()==null)
								item.setImage("/resources/mp_tepsa.png");
							else if(detalleItinerario.getItinerario().getOperadoPor().equals(Constantes.OPERADO_POR_CRUZ_DEL_SUR))
								item.setImage("/resources/mp_cruzdelsur.png");
							else if (detalleItinerario.getItinerario().getOperadoPor().equals(Constantes.OPERADO_CIVA))
								item.setImage("/resources/mp_excluciva.png");
//							cell = new Listcell(detalleItinerario.getItinerario().getOperadoPor()==null?"TEPSA":detalleItinerario.getItinerario().getOperadoPor());
//							cell.setStyle("font-size:9px !important");
//							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getAgenciaPartida().getNombreCorto());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getRuta().toString());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getHoraPartida());
							cell.setStyle("font-size:11px !important");
							cell.setTooltiptext("Hora de Partida");
							item.appendChild(cell);
							
							/*Valida si el servicio es operado por tepsa*/
							if(detalleItinerario.getItinerario().getOperadoPor()==null){ //Operado por tepsa
								
								//Tarifa Estandar, en soles								
								List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1, 
										detalleItinerario.getItinerario().getServicio().getId(),
										detalleItinerario.getRuta().getId(), fechaPartida, 
										detalleItinerario.getHoraPartida(), null, null);
								String strTarifas="";
								if(lstTarifaRegular.size()>0){
									for(TarifaRegular tarifa: lstTarifaRegular){
										strTarifas += tarifa.getMonto().toString();
										if(lstTarifaRegular.size()>1)
											strTarifas += " / ";
									}
									if(lstTarifaRegular.size()>1)
										strTarifas = strTarifas.substring(0, strTarifas.length()-3);								
								}
								else
									strTarifas="0.00";

								detalleItinerario.setTarifa(Double.valueOf(strTarifas.substring(0, 2)));
								
								//Listcell cellTarifaStandar = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
								Listcell cellTarifaStandar = new Listcell(strTarifas);
								cellTarifaStandar.setStyle("font-size:11px !important");
								cellTarifaStandar.setTooltiptext("Tarifa Estandar");
								
								//Tarifa suite, en soles
								//Comentado por MAOE
//								TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
//								criteriosBusqueda.put("rutas", "%" + detalleItinerario.getRuta().getId().toString() + "%");
//								criteriosBusqueda.put("servicios", "%" + detalleItinerario.getItinerario().getServicio().getId().toString() + "%");
//								criteriosBusqueda.put("esTarifa", Constantes.TRUE_VALUE);
//								criteriosBusqueda.put("esAcumulable", Constantes.TRUE_VALUE);
//								criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//								List<Promocion> resultPromo = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()));
								
								/*
								 * 12/07/2020
								 * MAOE
								 * Comentado porque no aplica el tema del Suite
								 */
//								Listcell cellTarifaSuite = new Listcell();
//								cellTarifaSuite.setStyle("font-size:11px !important");
//								cellTarifaSuite.setTooltiptext("Tarifa Suite");
								
								
//								if(resultPromo.size()==1){
//									cellTarifaSuite.setLabel(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
//									cellTarifaStandar.setLabel(Util.toNumberFormat(resultPromo.get(0).getPorImporte(), 2));
//								}else if (resultPromo.size()>1){
//									int countPrms=0;
//									boolean tarifaByHora=false;
//									Double tarifaSuite=.00;
//									
//									/*Busca la ruta exacta por si la consulta traiga mas de un resultado como consecuensia del LIKE en la Ruta (Ejemplo C_RUTAS LIKE '%1%')*/
//									for(Promocion promocion: resultPromo){
//										String[] rutas=promocion.getRutas().split(",");
//										for(String rutaId: rutas){
//											if(Integer.valueOf(rutaId).intValue()==detalleItinerario.getRuta().getId().intValue()){
//												if(promocion.getHorasPartida().trim().equals("*")){
//													countPrms++;
//													tarifaSuite=promocion.getPorImporte();
//												}else{
//													/*Tarifa por hora de partida*/
//													String horaPartida=promocion.getHorasPartida().toString().replace(".", ":");
//													if(horaPartida.equals(detalleItinerario.getHoraPartida())){
//														countPrms++;
//														tarifaByHora=true;
//														tarifaSuite=promocion.getPorImporte();
//														break;
//													}
//												}
//											}
//										}
//										if(tarifaByHora)
//											break;
//									}
//									
//									if(countPrms==1){
//										cellTarifaSuite.setLabel(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
//										cellTarifaStandar.setLabel(Util.toNumberFormat(tarifaSuite,2));
//									}else{
//										cellTarifaSuite.setLabel("Results "+ String.valueOf(resultPromo.size()));
//										cellTarifaSuite.setStyle("font-size:10px !important;text-transform:none");
//									}
//								}else
								
								/*
								 * Comentado por MAOE 16/07/2020
								 */
//									cellTarifaSuite.setLabel("0.00");
															
								
								/*BEGIN 11/07/2016 - JABANTO (Tarifario personalizado por cliente)*/
								if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA && clienteCredito!=null){
									/*Validando si el cliente tarifa personalizada, si tiene reemplaza las tarifas regulares por la personalizada*/
									List<TarifaClienteDetalle> result= ServiceLocator.getTarifaClienteDetalleManager().buscarTarifaPersonalizada(
																					clienteCredito.getId(), 
																					detalleItinerario.getRuta().getId(), 
																					detalleItinerario.getItinerario().getServicio().getId(), 
																					fechaPartida);
									for(TarifaClienteDetalle tarifaClienteDetalle :result){
										/*
										 * Comentado el 16/07/2020 por MAOE porque el Suite no aplica
										 */
//										if(tarifaClienteDetalle.getTipoAsiento().getId().intValue()==Constantes.ID_TIPASI_SUITE) //Si es suite
//											cellTarifa.setLabel(Util.toNumberFormat(tarifaClienteDetalle.getTarifa(), 2));
//										else //Es regular
											cellTarifaStandar.setLabel(Util.toNumberFormat(tarifaClienteDetalle.getTarifa(), 2));
									}
								}
																
								item.appendChild(cellTarifaStandar); //Tarifa estandar
								/*
								 * Comentado el 16/07/2020 por MAOE porque el Suite no aplica
								 */
//								item.appendChild(cellTarifaSuite);//Tarifa Suite
								
								/*Tarifa en otra moneda, segun la configuracion de la agencia*/
								if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
									TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), detalleItinerario.getTarifa(),false);
									cell = new Listcell(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():
																			detalleItinerario.getTarifa()), 2));
									
									cell.setTooltiptext("Tarifa Estandar en " + tipoMonedaAgencia.getDenominacion().toLowerCase());
									listheaderOtraTarifa.setVisible(true);
									listheaderOtraTarifa.setLabel(tipoMonedaAgencia.getSimboloMonetario()+" TARIFA");
									item.appendChild(cell);
								}
							}else{
								listheaderOperadoPor.setVisible(true);
								//Operado por otras empresas
								//Tarifa primer piso
								cell= new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
								cell.setStyle("font-size:11px !important");
								cell.setTooltiptext("Primer piso");
								item.appendChild(cell);
								//Tarifa Segudo piso
								cell= new Listcell("0.00");
								if(detalleItinerario.getTarifaSegundoPiso()!=null){
									cell.setLabel(Util.toNumberFormat(detalleItinerario.getTarifaSegundoPiso(), 2));
									cell.setStyle("font-size:11px !important");
									cell.setTooltiptext("Segundo piso");
								}
								item.appendChild(cell);
							}
							
							item.setValue(detalleItinerario);
							lbxItinerariosIda.appendChild(item);
						}
					}
				}else
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerariosIda"));
				
				/*	Validando si selecciono ida y vuelta	*/
				if(rdVentaIdaVuelta.isSelected()){
					String fechaRetorno = Util.DatetoString(cldrFechaRetorno.getValue(), Constantes.DATE_FORMAT);
					origen = cmbDestino.getText();
					destino = cmbOrigen.getText();
					lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaRetorno, origen, destino);
					if(lstItinerarios.size()>0){
						Listitem item = null;
						Listcell cell = null;
						/*	Cargando los itinerarios de retorno	*/
						for(DetalleItinerario detalleItinerario : lstItinerarios){
							item = new Listitem();
							cell = new Listcell(detalleItinerario.getAgenciaPartida().getNombreCorto());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getRuta().toString());
							cell.setStyle("font-size:9px !important");
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getHoraPartida());
							cell.setStyle("font-size:11px !important");
							cell.setTooltiptext("Hora de Partida");
							item.appendChild(cell);
							/*Tarifa Estandar, en soles*/
							List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1, 
									detalleItinerario.getItinerario().getServicio().getId(),
									detalleItinerario.getRuta().getId(), fechaRetorno, 
									detalleItinerario.getHoraPartida(), null, null);
							String strTarifas="";
							if(lstTarifaRegular.size()>0){
								for(TarifaRegular tarifa: lstTarifaRegular){
									strTarifas += tarifa.getMonto().toString();
									if(lstTarifaRegular.size()>1)
										strTarifas += " / ";
								}
								if(lstTarifaRegular.size()>1)
									strTarifas = strTarifas.substring(0, strTarifas.length()-3);
							}
							else
								strTarifas="0.00";
							detalleItinerario.setTarifa(Double.valueOf(strTarifas.substring(0, 2)));
//							Listcell cellTarifaStandar = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
							Listcell cellTarifaStandar = new Listcell(strTarifas);
							cellTarifaStandar.setStyle("font-size:11px !important");
							cellTarifaStandar.setTooltiptext("Tarifa Estandar en Soles");
							item.appendChild(cellTarifaStandar);
							
							
							
							
							//Tarifa suite, en soles
							//Comentado por MAOE
//							TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
//							criteriosBusqueda.put("rutas", "%" + detalleItinerario.getRuta().getId().toString() + "%");
//							criteriosBusqueda.put("servicios", "%" + detalleItinerario.getItinerario().getServicio().getId().toString() + "%");
//							criteriosBusqueda.put("esTarifa", Constantes.TRUE_VALUE);
//							criteriosBusqueda.put("esAcumulable", Constantes.TRUE_VALUE);
//							criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//							List<Promocion> resultPromo = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Constantes.FORMAT_DATE.format(detalleItinerario.getFechaPartida()));
							Listcell cellTarifaSuite = new Listcell();
							cellTarifaSuite.setStyle("font-size:11px !important");
							cellTarifaSuite.setTooltiptext("Tarifa Suite en Soles");
//							if(resultPromo.size()==1){
//								cellTarifaSuite.setLabel(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
//								cellTarifaStandar.setLabel(Util.toNumberFormat(resultPromo.get(0).getPorImporte(), 2));
//							}else if (resultPromo.size()>1){
//								int countPrms=0;
//								boolean tarifaByHora=false;
//								Double tarifaSuite=.00;
//								/*Busca la ruta exacta por si la consulta traiga mas de un resultado como consecuensia del LIKE en la Ruta (Ejemplo C_RUTAS LIKE '%1%')*/
//								for(Promocion promocion: resultPromo){
//									String[] rutas=promocion.getRutas().split(",");
//									for(String rutaId: rutas){
//										if(Integer.valueOf(rutaId).intValue()==detalleItinerario.getRuta().getId().intValue()){
//											if(promocion.getHorasPartida().trim().equals("*")){
//												countPrms++;
//												tarifaSuite=promocion.getPorImporte();
//											}else{
//												/*Tarifa por hora de partida*/
//												String horaPartida=promocion.getHorasPartida().toString().replace(".", ":");
//												if(horaPartida.equals(detalleItinerario.getHoraPartida())){
//													countPrms++;
//													tarifaByHora=true;
//													tarifaSuite=promocion.getPorImporte();
//													break;
//												}
//											}
//										}
//										
//									}
//									
//									if(tarifaByHora)
//										break;
//								}
//								if(countPrms==1){
//									cellTarifaSuite.setLabel(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
//									cellTarifaStandar.setLabel(Util.toNumberFormat(tarifaSuite,2));
//								}else{
//									cellTarifaSuite.setLabel("Results "+ String.valueOf(resultPromo.size()));
//									cellTarifaSuite.setStyle("font-size:10px !important;text-transform:none");
//								}
//							}else
								cellTarifaSuite.setLabel("0.00");
							item.appendChild(cellTarifaSuite);
							/*Tarifa en otra moneda, segun la configuracion de la agencia*/
							if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
								TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), detalleItinerario.getTarifa(),false);
								cell = new Listcell(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():
																		detalleItinerario.getTarifa()), 2));
								
								cell.setTooltiptext("Tarifa Estandar en " + tipoMonedaAgencia.getDenominacion().toLowerCase());
								listheaderOtraTarifaRetorno.setVisible(true);
								listheaderOtraTarifaRetorno.setLabel(tipoMonedaAgencia.getSimboloMonetario()+" TARIFA");
								item.appendChild(cell);
							}
							
							item.setValue(detalleItinerario);
							lbxItinerariosRetorno.appendChild(item);
						}
					}else
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerariosRetorno"));
				}
			}else{	//Para una venta fecha abierta
				/*	Cargamos los itinerarios del dia	*/
				List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerariosFechaAbierta(
																								fechaPartida, 
																								origen, 
																								destino);
				if(lstItinerarios.size()>0){
					Listitem item = null;
					Listcell cell = null;
					for(DetalleItinerario detalleItinerario : lstItinerarios){
						item = new Listitem();
						cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
						item.appendChild(cell);
						cell = new Listcell(detalleItinerario.getRuta().toString());
						item.appendChild(cell);
//						cell = new Listcell(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
						cell = new Listcell(Util.toNumberFormat(getTarifaFechaAbierta(detalleItinerario),2)); 
						item.appendChild(cell);
						item.setValue(detalleItinerario);
						lbxItinerariosFechaAbierta.appendChild(item);
					}
				}
			}
			
			
			if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
				columnItinerarios.setWidth("566px");
			}else{
				if(!(listheaderOperadoPor.isVisible())){
					columnItinerarios.setWidth("508px");
				}else{
					columnItinerarios.setWidth("622px");
					listheaderOperadoPor.setWidth("115px");
				}
			}
			
		}catch (FechaViajeNoValidaException fnv){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.FechaViajeNoValida"));			
		}catch(FechaMenorCalendarioException fmcex){
			DlgMessage.warning(Messages.getString("WndVentaReserva.warning.fechaRetornoNoValida"));
		}catch(LocalidadNullException lnex){
			if(lnex.getOrigenDestino().intValue()==LocalidadNullException.ORIGEN)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"));
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"));
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Metodo utilizado cuando el usuario selecciona un itinerario.
	 * @param direccion	: Indica si es ida o retorno.
	 */
	public void onSelectItinerario(Integer direccion)throws Exception{
		if(direccion!=null){
			if(direccion.intValue()==Constantes.SERVICIO_IDA){
				if(lbxItinerariosIda.getSelectedItem().getValue() instanceof DetalleItinerario){
					detalleItinerarioIda = lbxItinerariosIda.getSelectedItem().getValue();
					btnNextTabAsientos.setDisabled(false);
				}
			}else{
				if(lbxItinerariosRetorno.getSelectedItem().getValue() instanceof DetalleItinerario){
					detalleItinerarioRetorno = lbxItinerariosRetorno.getSelectedItem().getValue();
					btnNextTabAsientos.setDisabled(false);
				}
			}
		}
	}
	
	/**
	 * Metodo utilizado cuando el usuario selecciona un itinerario fecha abierta.
	 */
	public void onSelectItinerarioFechaAbierta()throws Exception{
		if(lbxItinerariosFechaAbierta.getSelectedItem().getValue() instanceof DetalleItinerario){
			detalleItinerarioFechaAbierta = lbxItinerariosFechaAbierta.getSelectedItem().getValue();
			btnNextFechaAbierta.setDisabled(false);
		}
	}
		
	
	/**
	 * Para controlar la funcionalidad del boton siguiente.
	 */
	@SuppressWarnings("deprecation")
	public void next() throws Exception{
		try{			
			
			if(tabItinerario.isSelected()){
				divLogOperadorPor1.setVisible(false);
				divLogOperadorPor2.setVisible(false);
					
				/*Si es TUENTRADA, valida que tenga un impresora configurada - 20/01/2017 - jabanto*/
				if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA || 
				   getAgencia().getId().intValue()==Constantes.ID_AGENCIA_SUPERMERCADOS_PERUANOS){
					ConfiguracionImpresora configuracionImpresora= UtilData.getConfiguracionImpresora(getUsuarioHardware().getId());
					if(configuracionImpresora==null){
						DlgMessage.information("No puede continuar debido a que no tiene configurada la impresión.");
						return;
					}
				}
				
				
//				/*Valida si el usuario tiene una liquidación aperturada*/
//				if(cmbTipoOperacion.getSelectedIndex()==0 && getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
//					throw new LiquidacionNullException();
				
				if(rdVentaNormal.isSelected() && detalleItinerarioIda==null)
					throw new ItinerarioException(ItinerarioException.ES_NULL_IDA);// ItinerarioNullException(ItinerarioNullException.ES_IDA);
				else if(rdVentaIdaVuelta.isSelected()){
					if(detalleItinerarioIda==null) 
						throw new ItinerarioException(ItinerarioException.ES_NULL_IDA); //ItinerarioNullException(ItinerarioNullException.ES_IDA);
					else if(detalleItinerarioRetorno==null)
						throw new ItinerarioException(ItinerarioException.ES_NULL_RETORNO); //ItinerarioNullException(ItinerarioNullException.ES_RETORNO);
				}
				/*Validamos la tarifa del Itinerario	*/
				if(rdVentaIdaVuelta.isSelected()){
					if(detalleItinerarioIda!=null && detalleItinerarioIda.getTarifa()==0.0)
						throw new ItinerarioException(ItinerarioException.TARIFA_IDA_CERO);
					else if(detalleItinerarioRetorno!=null && detalleItinerarioRetorno.getTarifa()==0.0)
						throw new ItinerarioException(ItinerarioException.TARIFA_RETORNO_CERO);
				}else if(detalleItinerarioIda!=null && detalleItinerarioIda.getTarifa()==0.0)
					throw new ItinerarioException(ItinerarioException.TARIFA_IDA_CERO);
				
				/*	Si es una venta remota	*/
				if(chkVentaRemota.isChecked()){
					if(rdVentaIdaVuelta.isChecked() && rdElectronicoRemoto.isChecked()==false){
						DlgMessage.information("No es posible realizar una venta remota de Ida y Vuelta, cuando los comprobantes a emitir no son Electrónicos.");
						return;
					}
					
					if (!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia))
						throw new AgenciaNullException();
					else if (!(cmbUsuarioRemoto.getSelectedIndex()>0))
						throw new UsuarioNullException();
					else if (rdPrepagadoRemoto.isChecked()==false && rdBoletoRemoto.isChecked()==false && rdElectronicoRemoto.isChecked()==false)
						throw new UsuarioHardwareNullException();
//					else if(!(lbxUsuarioHardware.getSelectedIndex()>=0))
//						throw new UsuarioHardwareNullException();
//					usuarioHardwareRemoto = ((ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware();
					TreeMap<String, Object> criteriosBusqueda= new TreeMap<>();
					criteriosBusqueda.put("agencia", cmbAgenciaRemota.getSelectedItem().getValue());
					criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
					List<UsuarioHardware> result= ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
					if(result.size()>0){
						usuarioHardwareRemoto = result.get(0);	
					}else{
						throw new UsuarioHardwareNullException();
					}
				}else
					usuarioHardwareRemoto = null;
				
//				tabAsientos.setDisabled(false);
//				tabAsientos.setSelected(true);
				
				/*Velida la hora de partida del servicio*/
				if(lbxItinerariosIda.getSelectedIndex()>=0){
					DetalleItinerario detItinerario=lbxItinerariosIda.getSelectedItem().getValue();
					int index=detItinerario.getHoraPartida().toString().indexOf(":");
					String sHora=detItinerario.getHoraPartida().substring(0,index);
					String sMinuto=detItinerario.getHoraPartida().substring(index+1,detItinerario.getHoraPartida().length());
					//Fecha hora de partida
					String fecha=Constantes.FORMAT_DATE.format(detItinerario.getFechaPartida());
					Date dFecHoraPart=Constantes.FORMAT_DATE.parse(fecha);
					dFecHoraPart.setHours(Integer.valueOf(sHora));
					dFecHoraPart.setMinutes(Integer.valueOf(sMinuto));
					
					/*Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida ## impl 10/11/2014 - jabanto*/
					if (!(UtilData.permiteVentaByTramo(detItinerario.getRuta().getId(), 
													   detItinerario.getItinerario().getId(), 
													   Constantes.FORMAT_DATE.format(detItinerario.getFechaPartida())))){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
						return;
					}
					
					//Fecha y hora actual
					Date dFechHorActual=new Date();
					if(dFecHoraPart.getTime()<dFechHorActual.getTime()){
						Messagebox.show(Messages.getString("WndVentaReserva.information.noHoraMenorServicio"), 
								     						DlgMessage.NOMBREAPLICACION, 
								     						DlgMessage.BTN_YESNO, 
								     						Messagebox.QUESTION,
								     						DlgMessage.BTN_DEFAULT_NO, 
								     						new EventListener<Event>() {
							@Override
							public void onEvent(Event e){
								try{
									if(e.getName().equals("onYes")){
										tabAsientos.setDisabled(false);
										tabAsientos.setSelected(true);
										if(detalleItinerarioIda.getItinerario().getOperadoPor()==null) { //Tepsa
											crearEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), 
															grpbxMapaIda, 
															false, 
															detalleItinerarioIda, 
															mapaAsientosIda, 
															grdOcupabilidadIda);
											
											/*	Si se trata de un ida y vuelta	*/
											if(rdVentaIdaVuelta.isSelected()){
												crearEstructura(detalleItinerarioRetorno.getItinerario().getServicio().getId(), 
																grpbxMapaRetorno, 
																true, 
																detalleItinerarioRetorno, 
																mapaAsientosRetorno, 
																grdOcupabilidadRetorno);
											}
										}else{
											//Operado por otra empresa
											if(detalleItinerarioIda.getItinerario().getOperadoPor().equals(Constantes.OPERADO_POR_CRUZ_DEL_SUR)){
												detalleItinerarioIda.getObjectCruzdelsur().setListAsientos(lbxAsientos);
												detalleItinerarioIda.getObjectCruzdelsur().setBtnNextTabVenta(btnNextTabVenta);
												WSCruzdelsur.crearEstructura(grpbxMapaIda, 
																			 detalleItinerarioIda, 
																			 grdOcupabilidadIda,
																			 getAgencia(),
																			 getUsuario());
												detailItinerary=detalleItinerarioIda;
												divLogOperadorPor1.setVisible(true);
												divLogOperadorPor2.setVisible(true);
												imgLogoOperadorPor1.setSrc("/resources/logos/mp_logoCruzdelsur.png");
												imgLogoOperadorPor2.setSrc("/resources/logos/mp_logoCruzdelsur.png");
											}else if (detalleItinerarioIda.getItinerario().getOperadoPor().equals(Constantes.OPERADO_CIVA)){
												detalleItinerarioIda.getObjectCiva().setListAsientos(lbxAsientos);
												detalleItinerarioIda.getObjectCiva().setBtnNextTabVenta(btnNextTabVenta);
												RESTCiva.crearEstructura(grpbxMapaIda, 
																		 detalleItinerarioIda, 
																		 grdOcupabilidadIda,
																		 getAgencia(),
																		 getUsuario());
												detailItinerary=detalleItinerarioIda;
												divLogOperadorPor1.setVisible(true);
												divLogOperadorPor2.setVisible(true);
												imgLogoOperadorPor1.setSrc("/resources/logos/mp_logoExcluciva.png");
												imgLogoOperadorPor2.setSrc("/resources/logos/mp_logoExcluciva.png");
											}
										}
									}
								}catch(Exception ex){}
							}
						});
					}else{
						tabAsientos.setDisabled(false);
						tabAsientos.setSelected(true);
						if(detalleItinerarioIda.getItinerario().getOperadoPor()==null) { //Tepsa
							crearEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), 
											grpbxMapaIda, 
											false, 
											detalleItinerarioIda, 
											mapaAsientosIda, 
											grdOcupabilidadIda);
							/*	Si se trata de un ida y vuelta	*/
							if(rdVentaIdaVuelta.isSelected()){
								crearEstructura(detalleItinerarioRetorno.getItinerario().getServicio().getId(), 
												grpbxMapaRetorno, 
												true, 
												detalleItinerarioRetorno, 
												mapaAsientosRetorno, 
												grdOcupabilidadRetorno);
							}
						}else{
							//Operado por otra empresa
							if(detalleItinerarioIda.getItinerario().getOperadoPor().equals(Constantes.OPERADO_POR_CRUZ_DEL_SUR)){
								detalleItinerarioIda.getObjectCruzdelsur().setListAsientos(lbxAsientos);
								detalleItinerarioIda.getObjectCruzdelsur().setBtnNextTabVenta(btnNextTabVenta);
								WSCruzdelsur.crearEstructura(grpbxMapaIda, detalleItinerarioIda, grdOcupabilidadIda,getAgencia(),getUsuario());
								detailItinerary=detalleItinerarioIda;
								divLogOperadorPor1.setVisible(true);
								divLogOperadorPor2.setVisible(true);
								imgLogoOperadorPor1.setSrc("/resources/logos/mp_logoCruzdelsur.png");
								imgLogoOperadorPor2.setSrc("/resources/logos/mp_logoCruzdelsur.png");
							}else if (detalleItinerarioIda.getItinerario().getOperadoPor().equals(Constantes.OPERADO_CIVA)){
								detalleItinerarioIda.getObjectCiva().setListAsientos(lbxAsientos);
								detalleItinerarioIda.getObjectCiva().setBtnNextTabVenta(btnNextTabVenta);
								RESTCiva.crearEstructura(grpbxMapaIda, detalleItinerarioIda, grdOcupabilidadIda,getAgencia(),getUsuario());
								detailItinerary=detalleItinerarioIda;
								divLogOperadorPor1.setVisible(true);
								divLogOperadorPor2.setVisible(true);
								imgLogoOperadorPor1.setSrc("/resources/logos/mp_logoExcluciva.png");
								imgLogoOperadorPor2.setSrc("/resources/logos/mp_logoExcluciva.png");
							}
						}
						
					}
				}
				
//				crearEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), grpbxMapaIda, false, detalleItinerarioIda, mapaAsientosIda, grdOcupabilidadIda);
//				/*	Si se trata de un ida y vuelta	*/
//				if(rdVentaIdaVuelta.isSelected()){
//					crearEstructura(detalleItinerarioRetorno.getItinerario().getServicio().getId(), grpbxMapaRetorno, true, detalleItinerarioRetorno, mapaAsientosRetorno, grdOcupabilidadRetorno);
//				}
			}else if(tabAsientos.isSelected()){	/*	Si estamos ubicados en el tab mapa del bus	*/
				if(detalleItinerarioIda.getItinerario().getOperadoPor()==null && 
						!validarCantidadAsientosSeleccionados() && 
						rdVentaIdaVuelta.isSelected()){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noEqualsSeatSelections"));
					return;
				}
//				for(Listitem item : lbxAsientos.getItems()){
//					DetalleItinerario detalle = (DetalleItinerario)item.getValue();
//					totalPagarPreliminar = totalPagarPreliminar+detalle.getTarifa();
//				}
//				lblTotalPagarPreliminar.setValue("TOTAL A PAGAR S./ " + Util.toNumberFormat(totalPagarPreliminar, 2));
				
				tabInformacionVentaIda.setSelected(true);
				tabVenta.setDisabled(false);
				tabVenta.setSelected(true);
				onSelectDefaultTipoComprobante();
				
				if(rdVentaIdaVuelta.isSelected() && cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_VENTA)){
					agruparAsientos();
					lbxAsientos.setVisible(false);
					lbxAsientosIdaRetorno.setVisible(true);
					tabInformacionVentaIda.setLabel("INFORMACIÓN DE LA VENTA DE IDA");
					tabInformacionVentaRetorno.setVisible(true);
					lbxAsientosIdaRetorno.setSelectedIndex(0);
					lbxAsientosIdaRetorno_onSelect();
				}else{
					lbxAsientos.setVisible(true);
					lbxAsientosIdaRetorno.setVisible(false);
					tabInformacionVentaRetorno.setVisible(false);
					tabInformacionVentaIda.setLabel("INFORMACIÓN DE LA VENTA");
					lbxAsientos.setSelectedIndex(0);
					lbxAsientos_onSelect();
				}				
				
//				if(!rdVentaIdaVuelta.isSelected() && lbxAsientos.getItems().size()>0){
//					lbxAsientos.setSelectedIndex(0);
//					lbxAsientos_onSelect();
//				}
				onCancelPax();
				txtDocumentoPax.setFocus(true);
				
				
				
//				/*Valida debe combertir la tarifa a otra moneda diferente a soles*/
//				pagoSoles=null;
//				if(cmbTipoMoneda.getSelectedItem().getValue() instanceof TipoMoneda){
//					if(((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPMON_SOLES)
//						changeTarifaOtraMoneda();
//				}
			}
			
		}catch (LiquidacionNullException lnullex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
			closeTabWindow();
		}catch (AgenciaNullException anex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaRemota"), cmbAgenciaRemota);
		}catch (UsuarioNullException urnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUsuarioRemoto"), cmbUsuarioRemoto);
		}catch(ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.ES_NULL_IDA) // .getIdaRetorno().intValue()==ItinerarioNullException.ES_IDA)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioIdaSeleccionado"));
			else if(inex.getTipo().intValue()==ItinerarioException.ES_NULL_RETORNO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioRetornoSeleccionado"));
			else if(inex.getTipo().intValue()==ItinerarioException.TARIFA_IDA_CERO){
				if(rdVentaIdaVuelta.isSelected())
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioIda"));
				else
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerario"));
			}else if(inex.getTipo().intValue()==ItinerarioException.TARIFA_RETORNO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioRetorno"));
		}catch(UsuarioHardwareNullException uhnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoUsuarioHardware"), bndbxUsuarioHardware);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Valida que la cantidad de asientos seleccionados en una venta ida y vuelta sea la misma. 
	 * @return <b>true</b> si la validación es satisfactoria, <b>false</b> caso contrario.
	 */
	private boolean validarCantidadAsientosSeleccionados(){
		boolean continuar = true;
		Map<Integer, Integer> mapSeleccionados = new HashMap<Integer, Integer>();
		for(Listitem item : lbxAsientos.getItems()){
			DetalleItinerario detalle = (DetalleItinerario)item.getValue();
			Integer key = detalle.getRuta().getId();
			if(mapSeleccionados.containsKey(key))
				mapSeleccionados.put(key, mapSeleccionados.get(key)+1);
			else
				mapSeleccionados.put(key, 1);
		}
		
		Object[] obj = mapSeleccionados.values().toArray();
		if(obj.length==2 && obj[0]!=obj[1])
			continuar = false;
		else if(obj.length==1)
			continuar = false;
		
		return continuar;
	}
	
	/**
	 * Metodo a utilizar para pasar entre paginas en caso de ventas a Fecha Abierta
	 * @throws Exception 
	 */
	public void onNextFechaAbierta() throws Exception{
		
		/*Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida ## impl 10/11/2014 - jabanto*/
//		if (!(UtilData.permiteVentaByTramo(detalleItinerarioFechaAbierta.getRuta().getId(), null,null))){
//			DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
//			return;
//		}
		
		tabVenta.setDisabled(false);
		tabVenta.setSelected(true);
		onLoadDatosVentaFechaAbierta(detalleItinerarioFechaAbierta);
		tabPagos.setDisabled(false);
		tabInformacionVentaRetorno.setVisible(false);
		try{
			
			/*	Si es una venta remota	*/
			if(chkVentaRemota.isChecked()){
				if (!(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia))
					throw new AgenciaNullException();
				else if (!(cmbUsuarioRemoto.getSelectedIndex()>0))
					throw new UsuarioNullException();
				else if (rdPrepagadoRemoto.isChecked()==false && rdBoletoRemoto.isChecked()==false && rdElectronicoRemoto.isChecked()==false)
					throw new UsuarioHardwareNullException();
//				else if(!(lbxUsuarioHardware.getSelectedIndex()>=0))
//					throw new UsuarioHardwareNullException();
//				usuarioHardwareRemoto = ((ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware();
				
				/*Saca el usuario hardware de un equipo de la agencia remota*/
				TreeMap<String, Object> criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("agencia", cmbAgenciaRemota.getSelectedItem().getValue());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<UsuarioHardware> result= ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
				if(result.size()>0){
					usuarioHardwareRemoto = result.get(0);	
				}else{
					throw new UsuarioHardwareNullException();
				}
			}else
				usuarioHardwareRemoto = null;
			lbxAsientosIdaRetorno.setVisible(false);
			
			onSelectDefaultTipoComprobante();
			onLoadEspecieValorada(txtNumeroBoleto);
			
		}catch (AgenciaNullException anex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaRemota"), cmbAgenciaRemota);
		}catch (UsuarioNullException urnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUsuarioRemoto"), cmbUsuarioRemoto);
		}catch(UsuarioHardwareNullException uhnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoUsuarioHardware"), bndbxUsuarioHardware);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
		
	}
	
	/**
	 * Crea la estructura que va a contener el Mapa del Bus.
	 * @param idServicio		: Identificador del servicio.
	 * @param grpbxParent		: Objeto padre contenedor.
	 * @param esRetorno			: Indica si el mapa es de ida o retorno.
	 * @param detalleItinerario	: Objeto del Detalle de Itinerario.
	 * @param mapaAsientos		: Objeto contenedor.
	 * @param gridOcupabilidad	: grilla para la ocupabilidad.
	 */
	@SuppressWarnings("deprecation")
	private void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno, 
								 DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, Grid gridOcupabilidad){
		try{
			Servicio servicio = null;
			/*	Busca el mapa del bus de acuerdo al servicio	*/
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);			
			
			/*	Creamos un Map el cual tendra como key un objeto coordenada y como valor el objeto MapaBus esto es deacuerdo a 
			 * lo que tenemos en la Base de datos	*/
			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<Coordenada, MapaBus>();
			for(MapaBus mapaBus : lstMapaBus){
				Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
				mapCoordenadas.put(coordenada, mapaBus);
			}
			
			if(lstMapaBus.size()>0)
				servicio = lstMapaBus.get(0).getServicio();
			
			int nPisos = servicio.getNumeroPisos();
			int nFilas = servicio.getNumeroFilasPiso1();
			int nColumnas = servicio.getNumeroColumnasPiso1();
			prefijoAsiento = "imgAsientoIdaPiso1_";
			if(esRetorno)
				prefijoAsiento = "imgAsientoRetornoPiso1_";
			Integer numeroAsiento = 0;
			
			inicializarEstructura(grpbxParent);
			
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			
			mapaAsientos = new HashMap<String, Asiento>();
			
			/*	Recorremos la cantidad de pisos del servicio	*/
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdIdaPiso1";
				if(esRetorno)
					idGrid = "grdRetornoPiso1";
				/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_";
					idGrid = "grdIdaPiso2";
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);					
				}
				/*	Creando la grilla contenedora de asientos	*/
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				gridPiso.setWidth("154px");
				Rows rows = new Rows();
				Row row = new Row();
				row.setSpans(String.valueOf(nColumnas));
				row.appendChild(imagen);
				row.setStyle("background:white; padding:0px");
				rows.appendChild(row);
				numeroAsiento = 0;
				/*	Recorremos la cantidad de filas	*/
				for(int j=0; j<nFilas; j++){
					row = new Row();
					/*	Recorremos la cantidad de columnas	*/
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setStyle("padding:none");
						
						/*	Definimos la coordenada actual	*/
						String coordenadaActual = j+"-"+k+"-"+i;
						
						/*	Iteramos el mapa de coordenadas creado al inicio y comparamos	*/
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								/*	Obtenemos el objeto y empezamos a setear las imagenes	*/
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
								
								HashMap<String, String> propiedades = new HashMap<String, String>();
								numeroAsiento++;
								
								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
										@Override
										public void onEvent(Event e){
											Component component = e.getTarget().getParent().getParent().getParent().getParent();
											if(component.getId().equals("grdIdaPiso1") || component.getId().equals("grdIdaPiso2"))
												onClickAsiento(e, detalleItinerarioIda, mapaAsientosIda, true);
											else
												onClickAsiento(e, detalleItinerarioRetorno, mapaAsientosRetorno, false);
										}
									});
									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
									asiento.setFila(j);
									asiento.setColumna(k);
									//MAOE: 05/03/2020
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									//MAOE: Para el tema del nuevo modelo de  Tarifas 05/03/2020
									asiento.setNumeroZona(objetoBus.getNumeroZona());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
									asiento.setStyle("cursor:pointer");
									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_MONITOR){
									Monitor monitor = new Monitor();
									monitor.setId(prefijoAsiento + numeroAsiento);
									monitor.setFila(j);
									monitor.setColumna(k);
									monitor.setPiso(i);
									monitor.setSrc(objetoBus.getPathImagen());
									monitor.setPropiedades(propiedades);
									oDiv.appendChild(monitor);
								}else if(objetoBus.getTipoObjeto().intValue()==TIPO_CAFETERIA){
									Cafeteria cafeteria = new Cafeteria();
									cafeteria.setId(prefijoAsiento + numeroAsiento);
									cafeteria.setFila(j);
									cafeteria.setColumna(k);
									cafeteria.setPiso(i);
									cafeteria.setSrc(objetoBus.getPathImagen());
									cafeteria.setPropiedades(propiedades);
									oDiv.appendChild(cafeteria);
								}else{
									ServiciosHigienicos sshh = new ServiciosHigienicos();
									sshh.setId(prefijoAsiento + numeroAsiento);
									sshh.setFila(j);
									sshh.setColumna(k);
									sshh.setPiso(i);
									sshh.setSrc(objetoBus.getPathImagen());
									sshh.setPropiedades(propiedades);
									oDiv.appendChild(sshh);
								}
								break;
							}
						}
						row.appendChild(oDiv);
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#eeeeee");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				grpbxParent.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario, gridOcupabilidad);
				if(esRetorno)
					mapaAsientosRetorno = mapaAsientos;
				else
					mapaAsientosIda = mapaAsientos;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = groupbox.getChildren().get(i);
			if(!(component instanceof Caption))
				groupbox.removeChild(component);
		}
	}
	
	/**
	 * Genera el objeto imagen para los pisos del bus
	 * @param src		: Path de la imagen a mostrar.
	 * @param width		: Ancho de la imagen.
	 * @param height	: Alto de la imagen.
	 * @return Image.
	 */
	private Image generarImagen(String src, int width, int height){
		Image imagen = new Image();
		imagen.setSrc(src);
		imagen.setWidth(String.valueOf(width)+"px");
		imagen.setHeight(String.valueOf(height)+"px");
		return imagen;
	}
	
//	/**
//	 * Obtiene la separación de los asientos en el mapa.
//	 * @param servicio	: Tipo de servicio, CAMA, PRE-40, PRE, SUITE
//	 * @return
//	 */
//	private String obtenerPadding(Servicio servicio){
//		String padding = "2px";
//		if(servicio.getNumeroAsientosPiso1()==36)
//			padding = "6px";
//		else if(servicio.getNumeroAsientosPiso1()==40)
//			padding = "4px";
//		return padding;
//	}
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @throws Exception
	 */
	public void onRefreshMap() {
		onRefreshMapaAsientos(mapaAsientosIda, detalleItinerarioIda, grdOcupabilidadIda);
		if(rdVentaIdaVuelta.isSelected())
			onRefreshMapaAsientos(mapaAsientosRetorno, detalleItinerarioRetorno, grdOcupabilidadRetorno);
	}
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		try{
			/*Busca configuracion para la validacion de la venta adelantada - 09/02/2015 (jabanto)*/
			VentaTramo ventaTramo=ServiceLocator.getVentaTramoManager().buscarPorItinerarioRuta(detalleItinerario.getItinerario(), 
																								detalleItinerario.getRuta());
			
			onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), 
																 detalleItinerario.getRuta().getLocalidadOrigen().getId(), 
																 detalleItinerario.getRuta().getLocalidadDestino().getId());
			
			int nOcupados = 0;
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				/*	Recorremos las ventas	*/
				for(VentaPasaje venta : lstVentas){
					String key=venta.getNumeroAsiento()+"-"+venta.getNumeroPiso();
					/*	Recorremos los subconjuntos	*/
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(key) && venta.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(key);
							if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
								if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO)
									asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
							}else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
								if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_DELIVERY)
									asiento.setSrc(Constantes.ICON_RESERVADO_DELIVERY+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENTE)
									asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_RESERVADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);
							}
							asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());
							nOcupados++;
							break;
						}
					}
					/*	Para identificar las prioridades del tramos para la venta	*/
					if(venta.getRuta().getLocalidadDestino().getId()==detalleItinerario.getRuta().getLocalidadOrigen().getId()){
						Asiento asiento = mapaAsientos.get(key);
						asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						
					}else 
						//Valida si la ruta esta configurada para la validacion de la venta adelantada - 09/02/2015 (jabanto)
						if(ventaTramo!=null && ventaTramo.getDespuesHoraSalida().intValue()==Constantes.FALSE_VALUE){
							//Valida si el destino de la ruta es igual al origen de la ruta vendida y si el asiento esta disponible  - 04/02/2015 (jabanto)
							if(detalleItinerario.getRuta().getLocalidadDestino().getId().intValue()==venta.getRuta().getLocalidadOrigen().getId().intValue()
									&& venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)
									&& mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE ){
								Asiento asiento = mapaAsientos.get(key);
								asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						}
					}
				}
			}			
			
			/*	BUSCAMOS LOS ASIENTOS QUE ESTEN BLOQUEADOS PARA EL ITINERARIO SELECCIONADO	*/
			List<TmpOcupacionAsientos> lstBloqueados = ServiceLocator.getTmpOcupacionAsientosManager().buscarAsientosBloqueados(detalleItinerario.getItinerario().getId());
			lstBloqueados = obtenerConjuntos(lstBloqueados, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstBloqueados.size()>0){
				for(TmpOcupacionAsientos bloqueado : lstBloqueados){
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(bloqueado.getKey()) && bloqueado.getSubConjunto().contains(orden)){
							Asiento asiento = mapaAsientos.get(bloqueado.getKey());
							asiento.setSrc(Constantes.ICON_BLOQUEADO+bloqueado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
							asiento.setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
							nOcupados++;
							break;
						}
					}
				}
			}
			
			//Valida si la ruta esta configurada para la validacion de la venta adelantada - 09/02/2015 (jabanto)
			if(ventaTramo!=null && ventaTramo.getDespuesHoraSalida().intValue()==Constantes.FALSE_VALUE){
				for(Entry<?,?> e : mapaAsientos.entrySet()) {
					Asiento asiento=(Asiento) e.getValue();
					//Valida si el asiento esta disponible y no semiocupado (Prioridad venta)
					if(asiento.getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE && 
					   !asiento.getSrc().equals(Constantes.ICON_SEMI_OCUPADO+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION)){
						//Coloca el estado del asiento ha ocupado
						asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						asiento.setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
						asiento.setTooltiptext("OCUPADO");
						nOcupados++;
					}
				}
			}
			
			mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Realiza la limpieza del mapa del bus
	 */
	public void onCleanMap(Map<String, Asiento> mapa){
		for(String key : mapa.keySet()){
			Asiento asiento = mapa.get(key);
			asiento.setSrc(Constantes.ICON_DISPONIBLE+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
			asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
			asiento.setTooltiptext("DISPONIBLE");
		}
	}
	
	/**
	 * Obtiene los subconjuntos de una lista de ventas, tmpOcupacion.
	 * @param lista			: Lista de registros de los cuales queremos obtener los subconjuntos.
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @return
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private List obtenerConjuntos(List lista, List<SecuenciaTramo> lstSecuencias){
		List result = new ArrayList();
		for(int i=0; i<lista.size(); i++){
			Object obj = lista.get(i);
			if(obj instanceof TmpOcupacionAsientos){
				TmpOcupacionAsientos tmp = (TmpOcupacionAsientos)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, 
																tmp.getRuta().getLocalidadOrigen().getId(), 
																tmp.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			}else if(obj instanceof VentaPasaje){
				VentaPasaje ventaPasaje = (VentaPasaje)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, 
																ventaPasaje.getRuta().getLocalidadOrigen().getId(), 
																ventaPasaje.getRuta().getLocalidadDestino().getId());
				ventaPasaje.setSubConjunto(subConjunto);
				result.add(ventaPasaje);
			}
		}
		return result;
	}
	
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la ruta que estamos buscando. 
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen, Integer idDestino){
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		/*	Recorremos la secuencia de tramos del itinerario	*/
		for(int j=0; j<lstSecuencias.size(); j++){
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*	Validamos si el origen de la secuencia coincide con el origen de la ruta	*/
			if(secuencia.getOrigen().intValue()==idOrigen.intValue()){
				/*	Recorremos la secuencia de tramos desde la posicion j	*/
				for(int k=j; k<lstSecuencias.size(); k++){
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*	Validamos si el destino de la secuencia coincide con el destino de la ruta	*/
					if(secuencia.getDestino().intValue()==idDestino.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	
	/**
	 * Muestra los valores de la ocupabilidad de asientos
	 * @param nOcupados			: Cantidad de asientos ocupados
	 * @param detalleItinerario	: Itinerarios seleccionado.
	 * @param grid				: Grid que contendra los objetos.
	 */
	private void mostrarOcupabilidad(Integer nOcupados, DetalleItinerario detalleItinerario, Grid grid){
		grid.getRows().detach();
		Rows rows = new Rows();
		
		Row row = new Row();
		Label label = new Label(detalleItinerario.getRuta().toString());
		row.appendChild(label);
		label = new Label(nOcupados.toString());
		row.appendChild(label);
		label = new Label(String.valueOf(detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1() - nOcupados));
		row.appendChild(label);
		rows.appendChild(row);
		grid.appendChild(rows);
	}
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, boolean esIda){
		Asiento asientoSeleccionado = (Asiento)e.getTarget();
		key = asientoSeleccionado.getKey();
		
		/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
		if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario()))			
			return;

		try{
			/*	validamos la cantidad maxima de asientos seleccionados	*/
			if(lbxAsientos.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
				/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/
				if(consultaAsientoBloqueado(key, mapaAsientos)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
					key="-1";
				}else{
					/*	Insertamos el asiento a la tmpOcupacionAsientos	*/
					TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
					
					//Valida si es una venta remota -- implemetado 21/04/2014 jabanto
					if(chkVentaRemota.isChecked()){
						tmpOcupacionAsientos.setUsuarioHardware(usuarioHardwareRemoto);
						tmpOcupacionAsientos.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
					}else{
						tmpOcupacionAsientos.setUsuarioHardware(new UsuarioHardware(usuhar));
						tmpOcupacionAsientos.setUsuario(new Usuario(usuario.getId()));
					}
										
					tmpOcupacionAsientos.setRuta(detalleItinerario.getRuta());
					tmpOcupacionAsientos.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacionAsientos.setNumeroAsiento(asientoSeleccionado.getNumeroAsiento());
					tmpOcupacionAsientos.setNumeroPiso(asientoSeleccionado.getPiso());
					tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
					tmpOcupacionAsientos.setHoraPartida(detalleItinerario.getHoraPartida());
					tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					UtilData.auditarRegistro(tmpOcupacionAsientos, false, usuario, Executions.getCurrent());
					TmpOcupacionAsientosID tmpOcupacionAsientosID = new TmpOcupacionAsientosID();
					tmpOcupacionAsientosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());
					tmpOcupacionAsientosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
					tmpOcupacionAsientosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
					tmpOcupacionAsientosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
					tmpOcupacionAsientosID.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
					tmpOcupacionAsientos.setTmpOcupacionAsientosID(tmpOcupacionAsientosID);
					
					int result = ServiceLocator.getTmpOcupacionAsientosManager().bloquearAsiento(tmpOcupacionAsientos);
					/*	En caso el bloqueo de asiento no haya tenido exito	*/
					if(result < 0){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
						if(esIda)
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), grdOcupabilidadIda);
						else
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), grdOcupabilidadRetorno);
						return;
					}else{	/*	Agregamos el asiento a la lista de seleccionados	*/
						mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);
						
 						Listitem listitemAsientos = new Listitem();
						Listcell cell = new Listcell(asientoSeleccionado.getDetalleItinerario().getRuta().toString());
						listitemAsientos.appendChild(cell);
						cell = new Listcell(asientoSeleccionado.getNumeroAsiento().toString());
						listitemAsientos.appendChild(cell);
						asientoSeleccionado.getDetalleItinerario().setAsiento(asientoSeleccionado.getNumeroAsiento().toString());
						asientoSeleccionado.getDetalleItinerario().setPiso(String.valueOf(asientoSeleccionado.getPiso()));
						asientoSeleccionado.getDetalleItinerario().setZona(String.valueOf(asientoSeleccionado.getNumeroZona()));
						
						//MAOE: La idea es que cada asiento seleccionado tenga su tarifa de acuerdo al nuevo modelo 
						//Obtener la tarifa regular del asiento y almacenarlo en DetalleItinerario del asientoSeleccionado
						List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(
								1, asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(), 
								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
								asientoSeleccionado.getPiso(),
								asientoSeleccionado.getNumeroZona());
						if(lstTarifaRegular.size()>0)
							asientoSeleccionado.getDetalleItinerario().setTarifa(lstTarifaRegular.get(0).getMonto()!=null?lstTarifaRegular.get(0).getMonto():0.00);
						else
							asientoSeleccionado.getDetalleItinerario().setTarifa(0.00);
						
						/*============================================================*/
						asientoSeleccionado.getDetalleItinerario().setEsIda(esIda);
						/*============================================================*/
						DetalleItinerario detalle = (DetalleItinerario)asientoSeleccionado.getDetalleItinerario().clone();
						listitemAsientos.setValue(detalle);
						lbxAsientos.appendChild(listitemAsientos);
					}
				}
			}
		}catch(DuplicateSeatException dsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
			onRefreshMap();
		}catch(DataIntegrityViolationException divex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoBloqueado"));
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
		
		/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
        if(lbxAsientos.getItems().size()==0)
			btnNextTabVenta.setDisabled(true);
		else
			btnNextTabVenta.setDisabled(false);
	}
	
	/**
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String key, Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			List<Listitem> items = lbxAsientos.getItems();
			for(int i=0; i<items.size(); i++){
				Listitem listitem = items.get(i);
				if(((DetalleItinerario)listitem.getValue()).getItinerario().getId().intValue()==detalleItinerario.getItinerario().getId().intValue() 
						&& ((DetalleItinerario)listitem.getValue()).getAsiento().equals(buffer[0]) 
						&& ((DetalleItinerario)listitem.getValue()).getPiso().equals(buffer[1])){
					lbxAsientos.removeItemAt(i);
					if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
						mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);
					}
					/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
					if(lbxAsientos.getItems().size()==0)
						btnNextTabVenta.setDisabled(true);
					else
						btnNextTabVenta.setDisabled(false);
					
					/*	Desbloqueamos el asiento	*/
					TmpOcupacionAsientos tmpOcupacion = new TmpOcupacionAsientos();
					tmpOcupacion.setRuta(detalleItinerario.getRuta());
					tmpOcupacion.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacion.setNumeroAsiento(Integer.valueOf(buffer[0]));
					tmpOcupacion.setNumeroPiso(Integer.valueOf(buffer[1]));
					ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacion);
					return true;
				}
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
		return false;
	}
	
	/**
	 * Consulta si el asiento esta bloqueado.
	 * @param key	: Clave a buscar en el mapa de asientos.
	 * @return boolean
	 */
	private boolean consultaAsientoBloqueado(String key, Map<String, Asiento> mapaAsientos){
		if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE)
			return false;
		return true;
	}
	
	/**
	 * Selecciona el asiento de la lista de asientos escogidos para venta.
	 * @throws Exception 
	 */
	public void lbxAsientos_onSelect() throws Exception{
		cmbAlimentacion.setDisabled(false);
		Util.limpiarCombobox(cmbAlimentacion);
		
		if(lbxAsientos.getSelectedItem().getValue() instanceof DetalleItinerario){
			txtObservacionesIda.setDisabled(false);
			UtilData.cargarDataCombo(cmbAlimentacion, PreferenciaAlimentaria.class, false);
			detailItinerary = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
			onLoadDatosVentaIda(detailItinerary);
			lblNroAsiento.setValue(detailItinerary.getAsiento());
			onLoadPagos(detailItinerary);
			onLoadPuntoEmbarque(detailItinerary, cmbPtoEmbarque);
			onLoadPuntoDesembarque(detailItinerary, cmbPtoDesembarque);
			if(!cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_RESERVA)){
				onLoadEspecieValorada(txtNumeroBoleto);
				
				/*Implementado 08/08/2015 - jabanto*/
//				TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), detailItinerary.getTarifa());
//				lblTarifa.setValue(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():detailItinerary.getTarifa()), 2));
				//Comentado por MAOE
				lblTarifa.setValue(Util.toNumberFormat(detailItinerary.getTarifa(), 2));
				
				tabPagos.setDisabled(false);
			}else{
				cmbTipoComprobante.setSelectedIndex(1);
				cmbFormaPago.setSelectedIndex(1);
				onLoadTipoFormaPago();
				cmbTipoFormaPago.setSelectedIndex(1);
				tabPagos.setDisabled(true);
				calcularFechaExpiracion(detailItinerary.getFechaPartida(), detailItinerary.getHoraPartida());				
			}
			//Comentado por MAOE
//			buscarPromocionPorTarifa(detailItinerary, lblTarifa, true);
			
			//Busca si el cliente credito tiene o no tarifa personalizada
			//Falta implementar de acuerdo al nuevo modelo
//			buscarTarifaPersonalizadaCliente(detailItinerary, lblTarifa);
			
			/*Valida debe convertir la tarifa a otra moneda diferente a soles*/
			validarOtraMoneda();
			
		}else if (lbxAsientos.getSelectedItem().getValue() instanceof AsientoPool){
			UtilData.cargarGenericData(cmbAlimentacion, false);
			AsientoPool asientoPool=lbxAsientos.getSelectedItem().getValue();
			/*===========================================================*/
			/*Si es una venta en el servicio de Cruz del Sur*/
			/*===========================================================*/
			if(asientoPool.getObjectCruzdelsur()!=null){
				cmbAlimentacion.setDisabled(true);
				Horario horario=asientoPool.getObjectCruzdelsur().getHorario();
				String[] yruta=asientoPool.getObjectCruzdelsur().getHorario().getRuta().getValue().split("-");
				lblOrigen.setValue(yruta[0].toUpperCase());
				lblDestino.setValue(yruta[1].toUpperCase());
				/*Cargando los puntos de embarque*/
				UtilData.cargarGenericData(cmbPtoEmbarque, false);
				Comboitem comboitem= new Comboitem(horario.getAgenciaEmbarque1().getValue());
				comboitem.setValue(horario.getAgenciaEmbarqueLlave1().getValue());
				cmbPtoEmbarque.appendChild(comboitem);
				if(horario.getAgenciaEmbarque2().getValue()!=null){
					comboitem= new Comboitem(horario.getAgenciaEmbarque2().getValue());
					comboitem.setValue(horario.getAgenciaEmbarqueLlave2().getValue());
					cmbPtoEmbarque.appendChild(comboitem);
					cmbPtoEmbarque.setSelectedIndex(0);
				}else
					cmbPtoEmbarque.setSelectedIndex(1);
				/*Cargando los puntos de desembarque*/
				UtilData.cargarGenericData(cmbPtoDesembarque, false);
				comboitem= new Comboitem(horario.getAgenciaDesembarque1().getValue());
				comboitem.setValue(horario.getAgenciaDesembarqueLlave1().getValue());
				cmbPtoDesembarque.appendChild(comboitem);
				if(horario.getAgenciaDesembarque2().getValue()!=null){
					comboitem= new Comboitem(horario.getAgenciaDesembarque2().getValue());
					comboitem.setValue(horario.getAgenciaDesembarqueLlave2().getValue());
					cmbPtoDesembarque.appendChild(comboitem);
					cmbPtoDesembarque.setSelectedIndex(0);
				}else
					cmbPtoDesembarque.setSelectedIndex(1);
				/*Cargar Alimnentacion*/
				if(horario.getMenus()!=null && horario.getMenus().getValue()!=null){
					for(Generic generic:horario.getMenus().getValue().getGeneric()){
						if(!(lblAlimen1.isVisible())){
							lblAlimen1.setValue(generic.getValue().getValue().toUpperCase());
							UtilData.cargarGenericData(cmbAlimen1, false);
							for(Generic oGeneric :generic.getListGeneric().getValue().getGeneric()){
								comboitem= new Comboitem(oGeneric.getValue().getValue());
								Generic generic2= new Generic();
								generic2.setKey(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"key"), String.class,generic.getKey().getValue()));//El identificador del tipo de alimentacion
								generic2.setValue(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"value"), String.class,oGeneric.getKey().getValue())); //El identificador del la alimentacacion 
								comboitem.setValue(generic2);
								cmbAlimen1.appendChild(comboitem);
							}
							lblAlimen1.setVisible(true);
							cmbAlimen1.setVisible(true);
						}else if(!(lblAlimen2.isVisible())){
							lblAlimen2.setValue(generic.getValue().getValue().toUpperCase());
							UtilData.cargarGenericData(cmbAlimen2, false);
							for(Generic oGeneric :generic.getListGeneric().getValue().getGeneric()){
								comboitem= new Comboitem(oGeneric.getValue().getValue());
								Generic generic2= new Generic();
								generic2.setKey(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"key"), String.class,generic.getKey().getValue()));//El identificador del tipo de alimentacion
								generic2.setValue(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"value"), String.class,oGeneric.getKey().getValue())); //El identificador del la alimentacacion 
								comboitem.setValue(generic2);
								cmbAlimen2.appendChild(comboitem);
							}
							lblAlimen2.setVisible(true);
							cmbAlimen2.setVisible(true);
						}else if(!(lblAlimen3.isVisible())){
							lblAlimen3.setValue(generic.getValue().getValue().toUpperCase());
							UtilData.cargarGenericData(cmbAlimen3, false);
							for(Generic oGeneric :generic.getListGeneric().getValue().getGeneric()){
								comboitem= new Comboitem(oGeneric.getValue().getValue());
								Generic generic2= new Generic();
								generic2.setKey(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"key"), String.class,generic.getKey().getValue()));//El identificador del tipo de alimentacion
								generic2.setValue(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"value"), String.class,oGeneric.getKey().getValue())); //El identificador del la alimentacacion 
								comboitem.setValue(generic2);
								cmbAlimen3.appendChild(comboitem);
							}
							lblAlimen3.setVisible(true);
							cmbAlimen3.setVisible(true);
						}else if(!(lblAlimen4.isVisible())){
							lblAlimen4.setValue(generic.getValue().getValue().toUpperCase());
							UtilData.cargarGenericData(cmbAlimen4, false);
							for(Generic oGeneric :generic.getListGeneric().getValue().getGeneric()){
								comboitem= new Comboitem(oGeneric.getValue().getValue());
								Generic generic2= new Generic();
								generic2.setKey(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"key"), String.class,generic.getKey().getValue()));//El identificador del tipo de alimentacion
								generic2.setValue(new JAXBElement<String>(new QName(WSCruzdelsur.NAMESPACE,"value"), String.class,oGeneric.getKey().getValue())); //El identificador del la alimentacacion 
								comboitem.setValue(generic2);
								cmbAlimen4.appendChild(comboitem);
							}
							lblAlimen4.setVisible(true);
							cmbAlimen4.setVisible(true);
						}
					}
				}
				/*Otros datos de la venta*/
				String fechaEmbarque=horario.getFechaHoraEmbarque1().getValue().split(" ")[0];
				String horaPartida=horario.getFechaHoraEmbarque1().getValue().split(" ")[1];
				String fechaDesembarque=horario.getFechaHoraDesembarque1().getValue().split(" ")[0];
				String horaLlegada=horario.getFechaHoraDesembarque1().getValue().split(" ")[1];
				lblFechaPartida.setValue(fechaEmbarque);
				lblFechaLlegada.setValue(fechaDesembarque);
				lblHoraPartida.setValue(horaPartida);
				lblHoraLlegada.setValue(horaLlegada);
				String piso=(asientoPool.getNivelAsiento().intValue()==1?"[PISO1]":"[PSIO2]");
				lblNroAsiento.setValue(asientoPool.getNumeroAsiento()+" "+piso);
				lblTarifa.setValue(Util.toNumberFormat(asientoPool.getTarifa(), 2));
				onLoadEspecieValorada(txtNumeroBoleto);
				txtObservacionesIda.setDisabled(true);
				dblTarifa.setValue(asientoPool.getTarifa());
				dblDescuento.setValue(.00);
				dblRecargo.setValue(.00);
				dblImporte.setValue(asientoPool.getTarifa());
			}else if(asientoPool.getObjectCiva()!=null){
				/*===========================================================*/
				/*Si es una venta en el servicio de Civa/Excluciva*/
				/*===========================================================*/
				ObjectCiva objectCiva=asientoPool.getObjectCiva();
//				String[] yruta=objectCiva.getRutaBus().split("-");
				String[] yruta=objectCiva.getRutaRecorrido().split("-");
				lblOrigen.setValue(yruta[0].toUpperCase());
				lblDestino.setValue(yruta[1].toUpperCase());
				/*Cargando los puntos de embarque*/
				UtilData.cargarGenericData(cmbPtoEmbarque, false);
				Comboitem comboitem= new Comboitem(objectCiva.getDireccionEmbarque().toUpperCase());
				comboitem.setValue(objectCiva.getDireccionEmbarque().toUpperCase());
				cmbPtoEmbarque.appendChild(comboitem);
				cmbPtoEmbarque.setSelectedIndex(1);
				/*Cargando los puntos de desembarque*/
				UtilData.cargarGenericData(cmbPtoDesembarque, false);
				comboitem= new Comboitem(objectCiva.getDireccionDesembarque().toUpperCase());
				comboitem.setValue(objectCiva.getDireccionDesembarque().toUpperCase());
				cmbPtoDesembarque.appendChild(comboitem);
				cmbPtoDesembarque.setSelectedIndex(1);
				/*Cargar Alimnentacion*/
				if(objectCiva.getMenus()!=null){
					for(TreeMap<String, Object> menu : objectCiva.getMenus()){
						Menu omenu = (Menu) menu.get("Menu");
						comboitem= new Comboitem(omenu.getDenominacion());
						comboitem.setValue(omenu);
						cmbAlimentacion.appendChild(comboitem);
					}
				}
//				if(horario.getMenus()!=null && horario.getMenus().getValue()!=null){
//					for(Generic generic:horario.getMenus().getValue().getGeneric()){
//						comboitem= new Comboitem(generic.getValue().getValue());
//						comboitem.setValue(generic);
//						cmbAlimentacion.appendChild(comboitem);
//					}
//				}
				/*Otros datos de la venta*/
				String fechaEmbarque=objectCiva.getFechaSalidaBus();
				String horaPartida=objectCiva.getHoraEmbarque();
				String fechaDesembarque=objectCiva.getFechaLlegadaBus();
				String horaLlegada=objectCiva.getHoraDesembarque();
				lblFechaPartida.setValue(fechaEmbarque);
				lblFechaLlegada.setValue(fechaDesembarque);
				lblHoraPartida.setValue(horaPartida);
				lblHoraLlegada.setValue(horaLlegada);
				String piso=(asientoPool.getNivelAsiento().intValue()==1?"[PISO1]":"[PSIO2]");
				lblNroAsiento.setValue(asientoPool.getNumeroAsiento()+" "+piso);
				lblTarifa.setValue(Util.toNumberFormat(asientoPool.getTarifa(), 2));
				onLoadEspecieValorada(txtNumeroBoleto);
				txtObservacionesIda.setDisabled(true);
				dblTarifa.setValue(asientoPool.getTarifa());
				dblDescuento.setValue(.00);
				dblRecargo.setValue(.00);
				dblImporte.setValue(asientoPool.getTarifa());
				
			}
		}
	}
	
	public void lbxAsientosIdaRetorno_onSelect() throws Exception{
		cmbAlimentacion.setDisabled(false);
		Util.limpiarCombobox(cmbAlimentacion);
		
		if(lbxAsientosIdaRetorno.getSelectedItem().getValue() instanceof VentaIdaRetorno){
			txtObservacionesIda.setDisabled(false);
			UtilData.cargarDataCombo(cmbAlimentacion, PreferenciaAlimentaria.class, false);
			tabInformacionVentaIda.setSelected(true);
			VentaIdaRetorno ventaIdaRetorno = (VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue();
			detailItinerary = ventaIdaRetorno.getDetalleItinerarioIDA();
			onLoadDatosVentaIda(detailItinerary);
			onLoadDatosVentaRetorno(ventaIdaRetorno.getDetalleItinerarioRETORNO());
			
			lblNroAsiento.setValue(detailItinerary.getAsiento());
			onLoadPagos(detailItinerary);
			onLoadPuntoEmbarque(detailItinerary, cmbPtoEmbarque);
			onLoadPuntoDesembarque(detailItinerary, cmbPtoDesembarque);
			
			if(!cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_RESERVA)){
				onLoadEspecieValorada(txtNumeroBoleto);
				
				/*08/08/2015 - jabanto*/
//				TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), detailItinerary.getTarifa());
//				lblTarifa.setValue(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():detailItinerary.getTarifa()), 2));
				lblTarifa.setValue(Util.toNumberFormat(detailItinerary.getTarifa(), 2));
								
				/*08/08/2015 - jabanto*/
//				tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa());
//				lblTarifaRetorno.setValue(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa()), 2));
				lblTarifaRetorno.setValue(Util.toNumberFormat(ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa(), 2));
				tabPagos.setDisabled(false);
			}else{
				cmbTipoComprobante.setSelectedIndex(1);
				cmbFormaPago.setSelectedIndex(1);
				onLoadTipoFormaPago();
				cmbTipoFormaPago.setSelectedIndex(1);
				tabPagos.setDisabled(true);
				calcularFechaExpiracion(detailItinerary.getFechaPartida(), detailItinerary.getHoraPartida());				
			}
			
			buscarPromocionPorTarifa(ventaIdaRetorno.getDetalleItinerarioIDA(), lblTarifa, true);
			buscarPromocionPorTarifa(ventaIdaRetorno.getDetalleItinerarioRETORNO(), lblTarifaRetorno, false);
			dblTarifaRetorno.setValue(Util.parseNumberFormat(lblTarifaRetorno.getValue(), 2));

			/*Busca si el cliente credito tiene o no tarifa personalizada*/
			buscarTarifaPersonalizadaCliente(ventaIdaRetorno.getDetalleItinerarioIDA(), lblTarifa);
			buscarTarifaPersonalizadaCliente(ventaIdaRetorno.getDetalleItinerarioRETORNO(), lblTarifaRetorno);
			
			/*Valida debe combertir la tarifa a otra moneda diferente a soles*/
			validarOtraMoneda();
//			}
		}
	}
	
	/**
	 * Busca si el cliente credito tiene tarifa personalizada
	 * @param detalleItinerario : Instancia de la clase detalleItinerario
	 * @param lblTarifa : Control en donde se va a cargar la tarifa
	 * @throws Exception
	 */
	private void buscarTarifaPersonalizadaCliente(DetalleItinerario detalleItinerario, Label lblTarifa) throws Exception{
		/*BEGIN 11/07/2016 - JABANTO (Tarifario personalizado por cliente)*/
		if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA && clienteCredito!=null){
			TarifaClienteDetalle tarifaClienteDetalle=ServiceLocator.getTarifaClienteDetalleManager().buscarTarifaPersonalizada(clienteCredito.getId(), detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getServicio().getId(), Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT), Integer.valueOf(detalleItinerario.getAsiento()));
			if(tarifaClienteDetalle!=null){
				detailItinerary.setTarifa(tarifaClienteDetalle.getTarifa());
				onLoadPagos(detailItinerary);
				lblTarifa.setValue(Util.toNumberFormat(tarifaClienteDetalle.getTarifa(), 2));
			}
		}
	}
	
	/**
	 * Buscamos las promociones que haran de tarifa en el caso del TEPSA SUITE o cualquier otro servicio.
	 * @param detalleItinerario	: Datos del itinerario a utilizar para realizar la busqueda de promocion por tarifa.
	 * @param lblTarifa			: Objeto en el cual se colocara la tarifa en caso de existir.
	 * @param esIda				: Booleano que indica si es la ida o el retorno.
	 */
	private void buscarPromocionPorTarifa(DetalleItinerario detalleItinerario, Label lblTarifa, boolean esIda){
		/**	
		 * Esta seccion es para obtener las promociones que son por tarifa	
		 * y que reeemplazaran la tarifa real del servicio.
		 */
		try {
			/*	Obtenemos las promociones que reemplazaran a la tarifa	*/
			List<Promocion>lstPromocion=ServiceLocator.getPromocionManager().buscarPorTarifa(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT), detalleItinerario.getRuta().getId().toString(), detalleItinerario.getItinerario().getServicio().getId().toString(),detalleItinerario.getHoraPartida().replaceAll(":", "."));
			Promocion promo = null;
			/*	Validando si la promocion cumple con el requisito del Servicio y la Ruta	*/
			for(int i=0; i<lstPromocion.size(); i++){
				String[] rutas = lstPromocion.get(i).getRutas().split(",");
				String[] servicios = lstPromocion.get(i).getServicios().split(",");
				String[] asientos = lstPromocion.get(i).getAsientos().split(",");
				for(int j=0; j<rutas.length; j++){
					if(rutas[j].equals(detalleItinerario.getRuta().getId().toString())){
						for(int k=0; k<servicios.length; k++){
							if(servicios[k].equals(detalleItinerario.getItinerario().getServicio().getId().toString())){
								if(asientos.length==1 && asientos[0].equals("*"))
									promo = lstPromocion.get(i);
								else{
									for(int m=0; m<asientos.length; m++){
										if(asientos[m].equals(detalleItinerario.getAsiento().toString())){
											promo = lstPromocion.get(i);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			
			/*	Solo en caso de ventas	*/
			imgPromocion.setVisible(true);
			promocionTarifa=null;
			if(promo!=null && cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_VENTA)){
				AplicarPromocion aplicarPromocion=createObjectAplicarPromocion(esIda);
				promocionAplicada= aplicarPromocion.executePromocion(promo.getId().toString(), false);
				if(promocionAplicada!=null){
					
					/*08/08/2015 jabanto*/
//					TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), promocionAplicada.getPorImporte());
//					lblTarifa.setValue(Util.toNumberFormat((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():promocionAplicada.getPorImporte()),2));
					lblTarifa.setValue(Util.toNumberFormat(promocionAplicada.getPorImporte(),2));
					
					promocionTarifa=(Promocion) promocionAplicada.clone();
					
					if(promocionTarifa.getEsAcumulable().intValue()!=Constantes.TRUE_VALUE)
						imgPromocion.setVisible(false);
					lblPromocion.setValue("Promoción : "+promocionTarifa.getDenominacion());
				}
			}
			
		} catch (Exception ex) {
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Realiza la carga de los pagos que se debe realizar.
	 * @param detalleItinerario	: tarifa del servicio de acuerdo al Itinerario.
	 * @throws Exception 
	 */
	private void onLoadPagos(DetalleItinerario detalleItinerario) throws Exception{
		/*	Si es una reserva la tarifa y el importe es 0.0		*/
		if(cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_RESERVA)){
			dblTarifa.setValue(0.0);
			dblRecargo.setValue(0.0);
			dblDescuento.setValue(0.0);
			dblImporte.setValue(0.0);
		}else{
			if(rdVentaFechaAbierta.isChecked()){
				dblTarifa.setValue(getTarifaFechaAbierta(detalleItinerario));
			}else{
				/*08/08/2015 - jabanto*/
//				TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), detalleItinerario.getTarifa());
//				dblTarifa.setValue((tipoCambio!=null?tipoCambio.getEquivalenteMonedaLocal():detalleItinerario.getTarifa()));
				dblTarifa.setValue(detalleItinerario.getTarifa());
			}
			//Begin custom 04-MAY-2013 TEPSA jsullo CR#sisvyr-Promociones
//			if(lineaContadoCliente !=null)
//				dblDescuento.setValue(aplicarPorcentajeDescuento(lineaContadoCliente.getDescuentoBaja()));
//			else
			//End custom 04-MAY-2013 TEPSA jsullo CR#sisvyr-Promociones
			dblDescuento.setValue(0.0);	
			dblRecargo.setValue(0.0);
			dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue() - dblDescuento.getValue());
		}
		dblTarifaRetorno.setValue(0.0);
		dblDescuentoRetorno.setValue(0.0);
		chkPagoMixto.setChecked(false);
		habilitarPagoMixto();
		dblImporteEfectivo.setValue(0.0);
		dblImporteTarjeta.setValue(0.0);
		lblPromocion.setValue("");
		imgQuitarPromocion.setVisible(false);
	}
	
	/**
	 * Muestra los datos del itinerario seleccionado.
	 * @param detalleItinerario	: Itinerario seleccionado.
	 */
	private void onLoadDatosVentaIda(DetalleItinerario detalleItinerario){
		lblOrigen.setValue(detalleItinerario.getRuta().getOrigen());
		lblDestino.setValue(detalleItinerario.getRuta().getDestino());
		//Si no es Fecha Abierta
		if(!rdVentaFechaAbierta.isSelected()){
			lblFechaPartida.setValue(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
			lblFechaLlegada.setValue(Util.DatetoString(detalleItinerario.getFechaLlegada(), Constantes.DATE_FORMAT));
			lblHoraPartida.setValue(detalleItinerario.getHoraPartida());
			lblHoraLlegada.setValue(detalleItinerario.getHoraLlegada());
		}
	}
	
	/**
	 * Muestra los datos del itinerario de retorno seleccionado.
	 * @param detalleItinerario	: Itinerario seleccionado.
	 */
	private void onLoadDatosVentaRetorno(DetalleItinerario detalleItinerario){
		lblOrigenRetorno.setValue(detalleItinerario.getRuta().getOrigen());
		lblDestinoRetorno.setValue(detalleItinerario.getRuta().getDestino());
		lblFechaPartidaRetorno.setValue(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
		lblFechaLlegadaRetorno.setValue(Util.DatetoString(detalleItinerario.getFechaLlegada(), Constantes.DATE_FORMAT));
		lblHoraPartidaRetorno.setValue(detalleItinerario.getHoraPartida());
		lblHoraLlegadaRetorno.setValue(detalleItinerario.getHoraLlegada());
		lblNroAsientoRetorno.setValue(detalleItinerario.getAsiento());
		onLoadPuntoEmbarque(detalleItinerario, cmbPtoEmbarqueRetorno);
		onLoadPuntoDesembarque(detalleItinerario, cmbPtoDesembarqueRetorno);
	}
	
	/**
	 * Muestra los datos del itinerario a fecha abierta.
	 * @param detalleItinerario	: Itinerario seleccionado.
	 * @throws Exception 
	 */
	private void onLoadDatosVentaFechaAbierta(DetalleItinerario detalleItinerario) throws Exception{
		lblOrigen.setValue(detalleItinerario.getRuta().getOrigen());
		lblDestino.setValue(detalleItinerario.getRuta().getDestino());
		onLoadPagos(detalleItinerario);
	}
	
	/**
	 * Cargamos los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void onLoadPuntoEmbarque(DetalleItinerario detItinerario, Combobox cmbParent){
		try{
			cmbParent.getItems().clear();
			
			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<ItinerarioAgenciaPartida>();
			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadOrigen().getId());
			/*	Si la agencia de partida del itinerario es la misma a la agencia de la ruta seleccionada	*/
//			if(detItinerario.getItinerario().getAgenciaPartida().getId().intValue()==detItinerario.getAgenciaPartida().getId().intValue())
//				arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
//			else{
//				ItinerarioAgenciaPartida itiAgePartida = new ItinerarioAgenciaPartida();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaPartida().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaPartida().getDenominacion());
//				agencia.setNombreCorto(detItinerario.getAgenciaPartida().getNombreCorto()!=null?detItinerario.getAgenciaPartida().getNombreCorto():null);
//				itiAgePartida.setAgencia(agencia);
////				itiAgePartida.setHoraPartida(detItinerario.getAgenciaPartida().getHoraPartida());
//				itiAgePartida.setHoraPartida(detItinerario.getHoraPartida());
//				arrayItiAgePartida.add(itiAgePartida);
//			}
			UtilData.cargarGenericData(cmbParent, false);
			/*	Cargamos los puntos de embarque	*/
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbParent.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbParent.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbParent.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Cargamos los puntos de desembarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de desembarque.
	 * @throws Exception
	 */
	private void onLoadPuntoDesembarque(DetalleItinerario detItinerario, Combobox cmbParent) {
		try{
			cmbParent.getItems().clear();
			ArrayList<ItinerarioAgenciaLlegada> arrayItiAgeLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			/*	Si la agencia de llegada del itinerario es la misma a la agencia de llegada de la ruta seleccionada	*/
			arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadDestino().getId());
//			if(detItinerario.getItinerario().getAgenciaLlegada().getId().intValue()==detItinerario.getAgenciaLlegada().getId().intValue())
//				arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadDestino().getId());
//			else{
//				ItinerarioAgenciaLlegada itiAgeLlegada = new ItinerarioAgenciaLlegada();
//				Agencia agencia = new Agencia();
//				agencia.setId(detItinerario.getAgenciaLlegada().getId());
//				agencia.setDenominacion(detItinerario.getAgenciaLlegada().getDenominacion());
//				itiAgeLlegada.setAgencia(agencia);
////				itiAgeLlegada.setHoraLlegada(detItinerario.getAgenciaLlegada().getHoraPartida()); //## 26/04/2016 - END BEGIN (Error, en algunos escenarios va ser null) - jabanto
//				
//				//## 26/04/2016 - GEBIN - jabanto
//				if(detItinerario.getAgenciaLlegada().getHoraPartida()==null)
//					itiAgeLlegada.setHoraLlegada(detItinerario.getHoraLlegada()); 
//				else
//					itiAgeLlegada.setHoraLlegada(detItinerario.getAgenciaLlegada().getHoraPartida());
//					
//				arrayItiAgeLlegada.add(itiAgeLlegada);
//			}	
			
			UtilData.cargarGenericData(cmbParent, false);
			for(ItinerarioAgenciaLlegada itiAgeLlegada : arrayItiAgeLlegada){
				Comboitem item = new Comboitem(itiAgeLlegada.getAgencia().getDenominacion());
                item.setValue(itiAgeLlegada);
                cmbParent.appendChild(item);
				if(arrayItiAgeLlegada.size()==1){
					cmbParent.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}else if(detItinerario.getAgenciaLlegada().getId().intValue()==itiAgeLlegada.getAgencia().getId().intValue()){
					cmbParent.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}	
	/**
	 * Muestra la hora de embarque segun el punto de embarque seleccionado
	 */
	public void onSelectPtoEmbarque(boolean esIda){
		if(esIda){
			if(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)
				lblHoraPartida.setValue(((ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue()).getHoraPartida());
			else
				lblHoraPartida.setValue("");
		}else{
			if(cmbPtoEmbarqueRetorno.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)
				lblHoraPartidaRetorno.setValue(((ItinerarioAgenciaPartida)cmbPtoEmbarqueRetorno.getSelectedItem().getValue()).getHoraPartida());
			else
				lblHoraPartidaRetorno.setValue("");
		}
	}
	
	/**
	 * Muestrala hora de llegada segun el punto de desembarque seleccionado.
	 */
	public void onSelectPtoDesembarque(boolean esIda){
		if(esIda){
			if(cmbPtoDesembarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)
				lblHoraLlegada.setValue(((ItinerarioAgenciaLlegada)cmbPtoDesembarque.getSelectedItem().getValue()).getHoraLlegada());
			else
				lblHoraLlegada.setValue("");	
		}else{
			if(cmbPtoDesembarqueRetorno.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)
				lblHoraLlegadaRetorno.setValue(((ItinerarioAgenciaLlegada)cmbPtoDesembarqueRetorno.getSelectedItem().getValue()).getHoraLlegada());
			else
				lblHoraLlegadaRetorno.setValue("");
		}
	}
	
	
	
	/* *******************************	IMPLEMENTACIONES PARA EL PASAJERO	******************************************** */
	/**
	 * Para un nuevo pasajero
	 */
	public void onNewPax()throws Exception {
		String numeroDocumento=txtDocumentoPax.getText().trim();
		onCleanControlsPax();
		disabledControlsPax(false);
		/*		PARA PRECARGAR EL UBIGEO EN EL CASO DE LOS CORPORATIVOS Y AGENCIAS DE VIAJE*/
		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
			txtUbigeoPax.setReadonly(false);
			txtUbigeoPax.setText("");
			txtUbigeoIdPax.setText("");
			dtbxFechaNacimiento.setDisabled(false);
//			cmbAnio.setDisabled(false);
//			cmbMes.setDisabled(false);
//			cmbDia.setDisabled(false);			
		}else{
			txtUbigeoPax.setReadonly(true);
//			cmbAnio.setDisabled(true);
//			cmbMes.setDisabled(true);
//			cmbDia.setDisabled(true);
			dtbxFechaNacimiento.setDisabled(true);
			Ubigeo oUbigeo = new Ubigeo();
			oUbigeo.setId(Constantes.ID_UBIGEO_LIMA);
			oUbigeo.setCodigoDepartamento("15");
			oUbigeo.setCodigoProvincia("01");
			oUbigeo.setCodigoDistrito("01");
			oUbigeo.setNombreUbigeo("LIMA");
			try{
				if(oUbigeo!=null) {
					String idUbigeo = oUbigeo.getId();
					String ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
					txtUbigeoPax.setText(ubicacionCompleta);
					txtUbigeoIdPax.setText(idUbigeo);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		action = Constantes.ACTION_NEW;
		tlbbtnNuevoPax.setDisabled(true);
		tlbbtnModificarPax.setDisabled(true);
		tlbbtnCancelarPax.setDisabled(false);
		tlbbtnGuardarPax.setDisabled(false);
		cmbTipoDocumento.setFocus(true);
		dblDescuento.setValue(0.0);
		imgQuitarPromocion.setVisible(false);
		lblPromocion.setValue("");
		promocionAplicada=null;
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
		oPasajero=null;
		
		habilitarNacionalidad();		
		txtDocumentoPax.setText(numeroDocumento);
		verificarPaxReniec();
		if(!(txtNombres.getText().trim().isEmpty()))
			txtTelefono.setFocus(true);
	}

	/**
	 * Para modificar a un pasajero
	 */
	public void onModifyPax() {
		action = Constantes.ACTION_MODIFY;
		// rowPaxFree.setVisible(false);
		disabledControlsPax(false);
		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA)
			ntbxEdad.setDisabled(true);
		else
			ntbxEdad.setDisabled(false);
		disabledControlsPaxDatosPer(true);
		
		tlbbtnNuevoPax.setDisabled(true);
		tlbbtnModificarPax.setDisabled(true);
		tlbbtnLimpiarPax.setDisabled(true);
		tlbbtnCancelarPax.setDisabled(false);
		tlbbtnGuardarPax.setDisabled(false);
		cmbTipoDocumento.setFocus(true);
		chbxNoTieneMail.setChecked(false);
	}
	
	/**
	 * Para cancelar el registro o la modificacion del pasajero
	 */
	public void onCancelPax() {
		disabledControlsPax(true);
		disabledControlsPaxDatosPer(false);
		tlbbtnNuevoPax.setDisabled(false);
		tlbbtnModificarPax.setDisabled(oPasajero==null);
		tlbbtnCancelarPax.setDisabled(true);
		tlbbtnGuardarPax.setDisabled(true);
		tlbbtnLimpiarPax.setDisabled(false);
		action = Constantes.FAILURE;
		txtDocumentoPax.setFocus(true);
		
		//06/09/2013 - jabanto
		if(oPasajero==null)
			onCleanControlsPax();
		
		
//		txtApePat.setDisabled(false);
//		txtApeMat.setDisabled(false);
//		txtNombres.setDisabled(false);
	}
	
	/**
	 * Limpia los controles del pasajero.
	 */
	public void onCleanControlsPax() {
		if(oPasajero!=null && oPasajero.isDescuentoAutoByPaxFree()){
			if(oCliente==null || oCliente.isDescuentoAutoByCliente()==false)
				quitarPromocion();
			else if(oCliente.isDescuentoAutoByCliente()==true){
				oPasajero = null;
				mantenimientoRegistroClient(oCliente.getId());
			}
		}
		
		cmbTipoDocumento.setSelectedIndex(0);
		Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
		txtDocumentoPax.setText("");
		txtApePat.setText("");
		txtApeMat.setText("");
		txtNombres.setText("");
		txtDireccionPax.setText("");
		txtTelefono.setText("");
		txtEmailPax.setText("");
		txtUbigeoPax.setText("");
		txtUbigeoIdPax.setText("");
//		dtbxFechaNacimiento.setValue(Constantes.FECHA_NULL);
		cmbSexo.setSelectedIndex(0);
//		cmbEstadoCivil.setSelectedIndex(0);
		cmbNacionalidad.setSelectedIndex(0);
		rowAdicional.setVisible(false);
		oPasajero = null;
		imgPasajero.setSrc("");
//		cmbAnio.setSelectedIndex(-1);
//		cmbMes.setSelectedIndex(-1);
//		cmbDia.setSelectedIndex(-1);
		dtbxFechaNacimiento.setValue(null);
		cmbTipoDocumento.setFocus(true);
		ntbxEdad.setValue(null);
		imgValidacionDNI.setSrc("");
		txtApePat.setDisabled(false);
		txtApeMat.setDisabled(false);
		txtNombres.setDisabled(false);
		chbxNoTieneMail.setChecked(false);
	}
	
	/**
	 * Activa o desactiva los controles de los datos personales del pasajero
	 * @param arg
	 */
	private void disabledControlsPaxDatosPer(boolean arg){
		cmbTipoDocumento.setDisabled(arg);
		txtDocumentoPax.setDisabled(arg);
		txtApePat.setDisabled(arg);
		txtApeMat.setDisabled(arg);
		txtNombres.setDisabled(arg);
	}
	
	/**
	 * Deshabilita o habilita los controles relacionados al pasajero.
	 * @param arg	: Boolean valor a utilizar.
	 */
	public void disabledControlsPax(boolean arg) {		
		txtDireccionPax.setDisabled(arg);
		txtTelefono.setDisabled(arg);
		txtEmailPax.setDisabled(arg);
		txtUbigeoPax.setDisabled(arg);
//		dtbxFechaNacimiento.setDisabled(arg);
		cmbSexo.setDisabled(arg);
//		cmbEstadoCivil.setDisabled(arg);
		btnUbigeoPax.setDisabled(arg);
		cmbNacionalidad.setDisabled(arg);
//		cmbAnio.setDisabled(arg);
//		cmbMes.setDisabled(arg);
//		cmbDia.setDisabled(arg);
		dtbxFechaNacimiento.setDisabled(arg);
		ntbxEdad.setDisabled(arg);
		chbxNoTieneMail.setDisabled(arg);
	}
	
	/**
	 * Realiza la busqueda de pasajeros
	 * @param criterio
	 */
	public void onSearchPax(Integer criterio){
		try{
			oPasajero=null;
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			ArrayList<Pasajero> lstPasajeros = null;
			if(criterio.intValue()==SEARCH_BY_DOCUMENTO){
				criterioBusqueda.put("numeroDocumento", txtDocumentoPax.getText().toUpperCase()+"%");
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("apellidoPaterno");
				criteriosOrdenar.add("apellidoMaterno");
				criteriosOrdenar.add("nombre");
				lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
			}else{
				String nombres = txtApePat.getText().trim().toUpperCase() + 
						(txtApeMat.getText().trim().equals("")?"":(" " + txtApeMat.getText().trim().toUpperCase())) + 
						" " + txtNombres.getText().trim().toUpperCase();
				String[] str1 = nombres.trim().split(" ");
				lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorFullTextIndex(str1);
			}
			
			listarRegistrosPax(lstPasajeros);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Lista los resultados de la busqueda
	 * @param lstRegistros	: Lista de resultados.
	 * @throws Exception
	 */
	private void listarRegistrosPax(ArrayList<Pasajero> lstRegistros)throws Exception {
		ArrayList<Object> lstPasajeros = new ArrayList<Object>();
		grpbxListaPasajeros.setVisible(false);
		
		if(lstRegistros.size()==1){
			mantenimientoRegistroPax(lstRegistros.get(0).getId());
		}else if(lstRegistros.size()>1){
			for (int r = 0; r < lstRegistros.size(); r++) {
				Pasajero oPasajero = lstRegistros.get(r);
				ArrayList<Object> lstFila = new ArrayList<Object>();
				lstFila.add(oPasajero.getId());
				lstFila.add(r + 1);
				lstFila.add(oPasajero.getTipoDocumento().getDenominacion());
				lstFila.add(oPasajero.getNumeroDocumento());
				lstFila.add(oPasajero.toString());
				lstFila.add(Util.toNumberFormat(oPasajero.getKilometros(), 2));
				lstPasajeros.add(lstFila);
			}
			Util.llenarListbox(lbxPasajeros, lstPasajeros, true);
			grpbxListaPasajeros.setVisible(true);
		}else{
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajerosEncontrados"));
			String numeroDocumento=txtDocumentoPax.getText().trim().toUpperCase();
			onCleanControlsPax();
			txtDocumentoPax.setText(numeroDocumento);
			txtDocumentoPax.select();
		}
		disabledControlsPax(true);
	}
	
	
//	/**
//	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas
//	 * @throws Exception 
//	 * @throws WrongValueException 
//	 */
//	public void validarPax_MuestraBDReniec() throws WrongValueException, Exception{
//		if(action==Constantes.ACTION_NEW  && !(txtDocumentoPax.getText().trim().isEmpty()) 
//				&& cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento 
//				&& ((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
//			
//			Reniec reniec= ServiceLocator.getReniecManager().buscarPax(txtDocumentoPax.getText().trim());
//			if(reniec!=null){
//				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
//				txtApePat.setText(reniec.getApellidoPaterno());
//				txtApeMat.setText(reniec.getApellidoMaterno());
//				txtNombres.setText(reniec.getNombres());
////				dtbxFechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(reniec.getFechaNacimiento()));
//				mostrarFechaNacimiento(reniec.getFechaNacimiento());
//				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
//				
//			}else{
//				String numeroDocumento=txtDocumentoPax.getText().trim();
//				Integer idTipoDocumento= null;
//				if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento)
//					idTipoDocumento=((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId();
//				
//				onCleanControlsPax();
//				//recupera valores ingresado por el usuario
//				txtDocumentoPax.setText(numeroDocumento);
//				if(idTipoDocumento!=null) 
//					Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, idTipoDocumento);
//				txtApePat.setFocus(true);
//				
//				if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
//					Ubigeo oUbigeo = new Ubigeo();
//					oUbigeo.setId(Constantes.ID_UBIGEO_LIMA);
//					oUbigeo.setCodigoDepartamento("15");
//					oUbigeo.setCodigoProvincia("01");
//					oUbigeo.setCodigoDistrito("01");
//					oUbigeo.setNombreUbigeo("LIMA");
//					try{
//						if(oUbigeo!=null) {
//							String idUbigeo = oUbigeo.getId();
//							String ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
//							txtUbigeoPax.setText(ubicacionCompleta);
//							txtUbigeoIdPax.setText(idUbigeo);
//						}
//					}catch(Exception ex){
//						ex.printStackTrace();
//					}
//				}
//			}
//		}
//	}
	
//	/**
////	 * Reliza la validacion del pasajero con el metodop getIdentifidad del WS del mtc en funcion al parametro configurado.
////	 * @throws WrongValueException
////	 * @throws Exception
////	 */
//	public void verificarPaxReniec() throws WrongValueException, Exception{
//		imgValidacionDNI.setSrc("");
//		Parametros parametros=ServiceLocator.getParametrosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
//		if(parametros.getValidarDNIgetIdentidad()!=null && parametros.getValidarDNIgetIdentidad().intValue()==Constantes.TRUE_VALUE)
//			validarPax_getIdentidadMTC();
//		else
//			validarPax_MuestraBDReniec();
//	}
	
	/**
	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas - 04/04/2015 - jabanto
	 * @throws Exception 
	 * @throws WrongValueException 
	 */
	public void verificarPaxReniec() throws WrongValueException, Exception{
		txtApePat.setDisabled(false);
		txtApeMat.setDisabled(false);
		txtNombres.setDisabled(false);
		
		if(action==Constantes.ACTION_NEW  
				&& !(txtDocumentoPax.getText().trim().isEmpty()) 
				&& cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento 
				&& ((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
			
			String numerodocumento=txtDocumentoPax.getText().trim();
			
			/*Valida con el metodo getIdentidad */
			ResultIdentidad resultIdentidad=Util.getResultIdentidad(numerodocumento,imgValidacionDNI);
			if(resultIdentidad!=null){
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
				/*si el DNI es correcto*/
				if(resultIdentidad.isReturn()){
					/*Para no permitir la modificacion de los appellidos y nombres al usuario*/
					txtApePat.setDisabled(true);
					txtApeMat.setDisabled(true);
					txtNombres.setDisabled(true);
					/*Carga los apellidos y nombres retornados por la reniec*/
					txtApePat.setText(resultIdentidad.getPaterno()!=null && !(resultIdentidad.getPaterno().trim().isEmpty()) ?resultIdentidad.getPaterno().trim():"");
					txtApeMat.setText(resultIdentidad.getMaterno()!=null && !(resultIdentidad.getMaterno().trim().isEmpty()) ? resultIdentidad.getMaterno().trim():"");
					txtNombres.setText(resultIdentidad.getNombre().trim());
					/*Obtiene datos como la fecha de nacimiento y el sexo de NUESTRA base de datos de la Reniec*/
					if(resultIdentidad.getReniec()!=null){
						mostrarFechaNacimiento(resultIdentidad.getReniec().getFechaNacimiento());
						if(dtbxFechaNacimiento.getValue()!=null)
							calcularEdad(Constantes.FORMAT_DATE.format(dtbxFechaNacimiento.getValue()));
						Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(resultIdentidad.getReniec().getSexo()));
					}
					txtDireccionPax.setFocus(true);
				}
			}else{
				/*Consulta BD reniec local*/
				List<String> dni = RESTCiva.getDatosDni(numerodocumento);
				
//				Reniec reniec= ServiceLocator.getReniecManager().buscarPax(numerodocumento);
				if(dni!=null){
				Reniec reniec = new Reniec();
				reniec.setNumeroDocumento(dni.get(0));
				reniec.setNombres(dni.get(1));
				reniec.setApellidoPaterno(dni.get(2));
				reniec.setApellidoMaterno(dni.get(3));
				ntbxEdad.setValue(30);
				dtbxFechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(calcularFechaNacimiento()));
				
				
//				if(reniec!=null){
					Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
					txtApePat.setText(reniec.getApellidoPaterno());
					txtApeMat.setText(reniec.getApellidoMaterno());
					txtNombres.setText(reniec.getNombres());
//					mostrarFechaNacimiento(reniec.getFechaNacimiento());
//					if(dtbxFechaNacimiento.getValue()!=null)
//						calcularEdad(Constantes.FORMAT_DATE.format(dtbxFechaNacimiento.getValue()));
//					Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
				}else{
					String numeroDocumento=txtDocumentoPax.getText().trim();
					Integer idTipoDocumento= null;
					if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento)
						idTipoDocumento=((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId();
					
					onCleanControlsPax();		
					//recupera valores ingresado por el usuario
					txtDocumentoPax.setText(numeroDocumento);
					if(idTipoDocumento!=null) 
						Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, idTipoDocumento);
					txtApePat.setFocus(true);
					
					if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
						Ubigeo oUbigeo = new Ubigeo();
						oUbigeo.setId(Constantes.ID_UBIGEO_LIMA);
						oUbigeo.setCodigoDepartamento("15");
						oUbigeo.setCodigoProvincia("01");
						oUbigeo.setCodigoDistrito("01");
						oUbigeo.setNombreUbigeo("LIMA");
						try{
							if(oUbigeo!=null) {
								String idUbigeo = oUbigeo.getId();
								String ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
								txtUbigeoPax.setText(ubicacionCompleta);
								txtUbigeoIdPax.setText(idUbigeo);
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	
//	/**
//	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas - 04/04/2015 - jabanto
//	 * @throws Exception 
//	 * @throws WrongValueException 
//	 */
//	public void validarPax_getIdentidadMTC() throws WrongValueException, Exception{
//		txtApePat.setDisabled(false);
//		txtApeMat.setDisabled(false);
//		txtNombres.setDisabled(false);
//		if(action==Constantes.ACTION_NEW  && !(txtDocumentoPax.getText().trim().isEmpty()) 
//				&& cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento 
//				&& ((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
//			
//			String numerodocumento=txtDocumentoPax.getText().trim();
//			/*Consulta DNI con la reniec a travez del Ws del MTC*/
//			ResultIdentidad resultIdentidad=WSMTC.getIdentidad(numerodocumento,true, imgValidacionDNI);
//			if(resultIdentidad!=null){
//				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
//				/*si el DNI es correcto*/
//				if(resultIdentidad.isReturn()){
//					/*Para no permitir la modificacion de los appellidos y nombres al usuario*/
//					txtApePat.setDisabled(true);
//					txtApeMat.setDisabled(true);
//					txtNombres.setDisabled(true);
//					/*Carga los apellidos y nombres retornados por la reniec*/
//					txtApePat.setText(resultIdentidad.getPaterno()!=null && !(resultIdentidad.getPaterno().trim().isEmpty()) ?resultIdentidad.getPaterno().trim():"");
//					txtApeMat.setText(resultIdentidad.getMaterno()!=null && !(resultIdentidad.getMaterno().trim().isEmpty()) ? resultIdentidad.getMaterno().trim():"");
//					txtNombres.setText(resultIdentidad.getNombre().trim());
//					/*Obtiene datos como la fecha de nacimiento y el sexo de NUESTRA base de datos de la Reniec*/
//					if(resultIdentidad.getReniec()!=null){
//						mostrarFechaNacimiento(resultIdentidad.getReniec().getFechaNacimiento());
//						Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(resultIdentidad.getReniec().getSexo()));
//					}
//					
//					txtDireccionPax.setFocus(true);
//				}
//			}else{
//				/*Consulta BD reniec local*/
//				Reniec reniec= ServiceLocator.getReniecManager().buscarPax(numerodocumento);
//				if(reniec!=null){
//					Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, Constantes.ID_TIPDOC_DNI);
//					txtApePat.setText(reniec.getApellidoPaterno());
//					txtApeMat.setText(reniec.getApellidoMaterno());
//					txtNombres.setText(reniec.getNombres());
//					mostrarFechaNacimiento(reniec.getFechaNacimiento());
//					Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
//				}else{
//					String numeroDocumento=txtDocumentoPax.getText().trim();
//					Integer idTipoDocumento= null;
//					if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento)
//						idTipoDocumento=((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId();
//					
//					onCleanControlsPax();		
//					//recupera valores ingresado por el usuario
//					txtDocumentoPax.setText(numeroDocumento);
//					if(idTipoDocumento!=null) 
//						Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, idTipoDocumento);
//					txtApePat.setFocus(true);
//					
//					if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
//						Ubigeo oUbigeo = new Ubigeo();
//						oUbigeo.setId(Constantes.ID_UBIGEO_LIMA);
//						oUbigeo.setCodigoDepartamento("15");
//						oUbigeo.setCodigoProvincia("01");
//						oUbigeo.setCodigoDistrito("01");
//						oUbigeo.setNombreUbigeo("LIMA");
//						try{
//							if(oUbigeo!=null) {
//								String idUbigeo = oUbigeo.getId();
//								String ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
//								txtUbigeoPax.setText(ubicacionCompleta);
//								txtUbigeoIdPax.setText(idUbigeo);
//							}
//						}catch(Exception ex){
//							ex.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//	}
	
	/**
	 * Muestra los datos del registro que se va a modificar.
	 * @param id	: Identificador del registro a modificar
	 * @throws Exception
	 */
	private void mantenimientoRegistroPax(Long idPasajero) {
		try {
			convertirPaxFre = false;
			promocionAplicada = null;
			imgQuitarPromocion.setVisible(false);
			imgFidelizarPasajero.setVisible(false);
			tlbbtnModificarPax.setDisabled(false);
			oPasajero = ServiceLocator.getPasajeroManager().buscarPorId(idPasajero);
									
			Ubigeo oUbigeo = oPasajero.getUbigeo();
			TipoDocumento oTipoDocumento = oPasajero.getTipoDocumento();
			Sexo oSexo = oPasajero.getSexo();
			
			Nacionalidad oNacionalidad = oPasajero.getNacionalidad();
			String idUbigeo = new String();
			String ubicacionCompleta = new String();

			if(oUbigeo!=null) {
				idUbigeo = oUbigeo.getId();
				ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
			}
			
//			if(oEstadoCivil!=null)
//				Util.seleccionarValorItemCombo(EstadoCivil.class, cmbEstadoCivil, oPasajero.getEstadoCivil().getId());
//			else
//				cmbEstadoCivil.setSelectedIndex(0);
			
			if(oTipoDocumento!=null)
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbTipoDocumento, oPasajero.getTipoDocumento().getId());
			else
				cmbTipoDocumento.setSelectedIndex(0);
			
			if(oSexo!=null)
				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, oPasajero.getSexo().getId());
			else
				cmbSexo.setSelectedIndex(0);
			
			if(oNacionalidad!=null)
				Util.seleccionarValorItemCombo(Nacionalidad.class, cmbNacionalidad, oNacionalidad.getId());
			else
				cmbNacionalidad.setSelectedIndex(0);
			
			habilitarNacionalidad();		

			txtDocumentoPax.setText(oPasajero.getNumeroDocumento()!=null?oPasajero.getNumeroDocumento().toString():"");
			txtApePat.setText(oPasajero.getApellidoPaterno());
			txtApeMat.setText(oPasajero.getApellidoMaterno());
			txtNombres.setText(oPasajero.getNombre());
			/*		PARA LA FECHA DE NACIMIENTO VARIAMOS PARA LOS CORPORATIVOS Y AGENCIAS DE VIAJE	*/
			if(oPasajero.getFechaNacimiento() != null){
				mostrarFechaNacimiento(oPasajero.getFechaNacimiento());
				calcularEdad(oPasajero.getFechaNacimiento());
			}else{
				mostrarFechaNacimiento(null);
				calcularEdad(null);
			}
			txtDireccionPax.setText(oPasajero.getDireccion());
			txtUbigeoIdPax.setText(idUbigeo);
			txtUbigeoPax.setText(ubicacionCompleta);
			txtTelefono.setText(oPasajero.getTelefono());
			txtEmailPax.setText(oPasajero.getEmail());

			if(oPasajero.getIndeseable().intValue() == Constantes.TRUE_VALUE) {
				lblInformativo1.setVisible(false);
				lblInformativo2.setValue("***  RESTRICCION DEL PASAJERO  ***");
				lblInformativo3.setValue(oPasajero.getMotivo());
				imgDetalleMotivo.setVisible(true);
			}else{
				lblInformativo1.setVisible(true);
				lblInformativo2.setValue("");
				lblInformativo3.setValue("");
				imgDetalleMotivo.setVisible(false);
			}

			if (oPasajero.getSexo() == null) {
				imgPasajero.setSrc("");
//			} else if (oPasajero.getSexo().getDenominacion().equals("FEMENINO"))
			} else if (oPasajero.getSexo().getId().equals(Constantes.ID_SEXO_FEMENINO))
				imgPasajero.setSrc("resources/mp_woman.png");
			else
				imgPasajero.setSrc("resources/mp_man.png");

			rowAdicional.setVisible(true);
			/* RECUPERANDO LOS DATOS DEL PASAJERO FRECUENTE */
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("pasajero.id", oPasajero.getId());
//			criteriosBusqueda.put("estado", Constantes.TRUE_VALUE);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<PasajeroFrecuente> lstPaxfree = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);

			String finicio = "";
			String ffin = "";
			Integer totalViajes = 0;
			/*
			 * Validando que el pasajero no sea Indeseable para hacer la validacion de PAXFREE
			 */
			if (oPasajero.getIndeseable().intValue() == Constantes.FALSE_VALUE) {
				if (lstPaxfree.size() > 0) {
					PasajeroFrecuente paxFree=lstPaxfree.get(0);
					
					//Cuando el pasajero frecuente esta activo
					if(paxFree.getEstado().intValue()==Constantes.TRUE_VALUE){
						lblInformativo1.setValue("PASAJERO FRECUENTE");
						finicio=Constantes.FORMAT_DATE_TIME_24H.format(paxFree.getFechaActivacion());
						ffin=Constantes.FORMAT_DATE_TIME_24H.format(paxFree.getFechaCaducidad());
					}else{
						//Cuendo el pasajero frecuente esta inactivo
						lblInformativo1.setValue("PASAJERO FRECUENTE INACTIVO");
						
						MyTime time= new MyTime();
						Date mfechaActual=Constantes.FORMAT_DATE_TIME_24H.parse(time.dateServer());
						long diasTrasncurridos=mfechaActual.getTime()-paxFree.getFechaSuspension().getTime();
						
						//Si los dias transcurridos desde la fecha de suspencion hasta la fecha actual es menor o igual se toma la fecha inicial desde la fecha suspencion
						if(diasTrasncurridos<=Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE){
							finicio=Constantes.FORMAT_DATE_TIME_24H.format(paxFree.getFechaSuspension());
						}else{
							//Si los dias transcurridos desde la fecha de suspencion hasta la fecha actual es Mayor se toma la fecha inicial desde la fecha actual menos 6 meses atras.
							finicio=Constantes.FORMAT_DATE_TIME_24H.format(mfechaActual.getTime()-(Constantes.MILISEGUNDOS_X_DIA*Constantes.TIEMPO_PASAR_PAXFREE));
						}
						ffin=Constantes.FORMAT_DATE_TIME_24H.format(mfechaActual);
					}
					
//					finicio=Constantes.FORMAT_DATE_TIME_24H.format(lstPaxfree.get(0).getFechaActivacion());
//					ffin=Constantes.FORMAT_DATE_TIME_24H.format(lstPaxfree.get(0).getFechaCaducidad());
					totalViajes = ServiceLocator.getVentaPasajesManager().contarViajesValidos(oPasajero.getId(), finicio, ffin);
					
					lblInformativo3.setValue("NUMERO DE VIAJES VALIDOS DEL " + finicio.substring(0,10) + "  AL  " + ffin.substring(0,10) + " : " + totalViajes);
					oPasajero.setPaxFree(true);
					oPasajero.setPasajeroFrecuente(lstPaxfree.get(0));
					
					/*	Buscamos la promocion del Pasajero Frecuente, Solo cuando es una venta	*/
					if(cmbTipoOperacion.getSelectedIndex()==0){
						if(promocionTarifa==null || (promocionTarifa.getEsAcumulable().intValue()==Constantes.TRUE_VALUE)){
							lblPromocion.setValue("");
							onSearchPromoPaxFree(oPasajero);
						}
					}else
						lblPromocion.setValue("");
				}else {
					//Pasajeros regulares
					oPasajero.setPaxFree(false);
					oPasajero.setPasajeroFrecuente(null);
					//Verificar el parametro de numero de viajes acumulados necesarios para canjear un pasaje gratis
					if(Constantes.NUMERO_VIAJES_ACUMULADOS > 0){
						//Buscar la fecha de inicio en la tabla de pasajes canjeados
						//La busqueda se realizara desde el mismo metodo que cuenta los viajes validos
					
						//En esta version no se utilizara el programa de pasajero frecuente
						//Secomentan estas lineas por MAOE el 18/11/2020
//						Long tiempo = new Date().getTime() - (Constantes.MILISEGUNDOS_X_DIA * Constantes.TIEMPO_PASAR_PAXFREE);
//						finicio = Util.DatetoString(new Date(tiempo), Constantes.DATE_FORMAT);
						
						ffin = Util.DatetoString(new Date(), Constantes.DATE_TIME_FORMAT);
						totalViajes = ServiceLocator.getVentaPasajesManager().contarViajesValidos(oPasajero.getId(), finicio, ffin);
						lblInformativo1.setValue("CANTIDAD DE VIAJES VALIDOS ACUMULADOS: " + totalViajes.toString());
					}
					//En esta version no se utilizara el programa de pasajero frecuente
					//Se comentaron esta lisneas por MAOE el 18/11/2020
//					if (totalViajes.intValue() >= Constantes.NUMERO_VIAJES_PAXFREE && cmbTipoOperacion.getSelectedIndex()==0){
//						lblInformativo3.setValue("EL CLIENTE CALIFICA PARA SER PASAJERO FRECUENTE");
//						convertirPaxFre = true;
//					}else
						lblInformativo3.setValue("");
					dblDescuento.setValue(0.00);
					dblDescuento.setTooltiptext("");
					
					if(promocionTarifa!=null)
						lblPromocion.setValue("Promoción : "+promocionTarifa.getDenominacion());
					else{
						lblPromocion.setValue("");
						//Recupera el descuento automatico del Cliente, si es que este tiene
						if(oCliente!=null && oCliente.isDescuentoAutoByCliente())
							mantenimientoRegistroClient(oCliente.getId());
					}
					dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
				}
				
//				tlbbtnModificarPax.setDisabled(oPasajero.isPaxFree());
				
				/*Establece la imagen segun validacion del DNI del pasajero con la reniec - 04/04/2015 - jabanto*/
				if(oPasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI)
					Util.imagenValidacionDNIReniec(oPasajero.getValidadoReniec(), imgValidacionDNI);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
		}
	}
	
	
	private void onSearchPromoPaxFree(Pasajero oPasajero) throws Exception{
//		if(detailItinerary.getItinerario().getOperadoPor()==null){
		/*Valida que la localidades destino no este registrada como parate de las rutas del pool*/
		if(!(Util.isDestinoPool(detailItinerary))){
			/*	Buscamos la promocion del Pasajero Frecuente	*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("cliente", "*");
			criteriosBusqueda.put("pasajeroFrecuente", "S");
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			/*	Validando que el PAXFRE se encuentre activo	*/
			if(oPasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE){
				List<Promocion> lstPromocion = null;
				if(rdVentaFechaAbierta.isChecked()){
					Date fechaPartida = Util.StringtoDate(ServiceLocator.getVentaPasajesManager().getDateSystem(), Constantes.DATE_FORMAT);
					lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(fechaPartida, Constantes.DATE_FORMAT));
				}else{
					lstPromocion = ServiceLocator.getPromocionManager().buscarPorX( criteriosBusqueda, null, Util.DatetoString(detailItinerary.getFechaPartida(), Constantes.DATE_FORMAT));
				}
				
				if(lstPromocion!=null && lstPromocion.size()==1){
					AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
					promocionAplicada = aplicarPromocion.executePromocion(lstPromocion.get(0).getId().toString(), false);
					if(promocionAplicada!=null){
						imgQuitarPromocion.setVisible(true);
						this.oPasajero.setDescuentoAutoByPaxFree(true);
					}
				}else if(lstPromocion.size()>1)
					DlgMessage.information(Messages.getString("WndVentaPasajes.information.muchasPromocionesPaxFre"));
			}else{
				dblDescuento.setValue(0.00);
				dblDescuento.setTooltiptext("");
				lblPromocion.setValue("");
				dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
//				promocionAplicada = null;
				/*Recupera el descuento automatico del Cliente, si es que este lo tubiera*/
				if(oCliente!=null && oCliente.isDescuentoAutoByCliente())
					mantenimientoRegistroClient(oCliente.getId());
			}
		}
	}
	
	
	/**
	 * Realiza el guardado del pasajero
	 */
	public void onSavePax() {
		try {
			if (!(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento))
				throw new TipoDocumentoNullException();
			else if (!cmbTipoDocumento.getText().equals("S/D") && txtDocumentoPax.getText().equals(""))
				throw new NumeroDocumentoNullException();
			else if (txtApePat.getText().trim().equals(""))
				throw new ApellidoPaternoNullException();
			else if (txtNombres.getText().trim().equals(""))
				throw new NombresNullException();
			else if (txtUbigeoPax.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if (txtTelefono.getText().trim().equals(""))
				throw new TelefonoNullException();
			else if (!(cmbSexo.getSelectedItem().getValue() instanceof Sexo))
				throw new SexoNullException();
			else if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento && 
					((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPDOC_DNI){
				if(!(cmbNacionalidad.getSelectedItem().getValue() instanceof Nacionalidad))
					throw new NacionalidadException();
//				else if(txtEmailPax.getText().equals(""))
//					throw new EmailNullException();
			}
			
			/*Obliga el ingreso del email, mientras el usuario no marque el check "No tiene" - 03/05/2017*/
			//Comentado por MAOE TRANSMAR no necesita el email
//			if(!(txtEmailPax.isDisabled()) && txtEmailPax.getText().trim().isEmpty())
//				throw new EmailNullException();
			
			if(cmbTipoDocumento.getSelectedItem().getValue() instanceof TipoDocumento && 
					((TipoDocumento)cmbTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(txtDocumentoPax.getText().trim().length()<8)
					throw new VentaReservaException(VentaReservaException.LONGITUD_NUMERO_DOCUMENTO);
				else if (Util.isNumeric(txtDocumentoPax.getText().trim())==false)
					throw new PasajeroException(PasajeroException.NUMERO_DOCUMENTO_INCORRECTO);
			}
			
			//Valida fecha de nacimiento - cuando no este tepsa
			if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA && ntbxEdad.getValue()==null){
				throw new PasajeroException(PasajeroException.EDAD_NULL);
			}
			
			//Valida fecha de nacimiento - cuando es tepsa			
			if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA && dtbxFechaNacimiento.getText().trim().isEmpty()){
//			if(getAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA && dtbxFechaNacimiento.getValue()==null){								//				
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noFechaNacimiento"),dtbxFechaNacimiento);
				return;								
			}
			//Valida que la fecha de nacimiento no sea menor a 100 años atras - 05/05/2017 - (validacion propuesta por moscco)
			java.util.Calendar calFechaNacimiento=java.util.Calendar.getInstance();
			calFechaNacimiento.setTimeInMillis(dtbxFechaNacimiento.getValue().getTime());
			int anioActual= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
			int anioFecha= calFechaNacimiento.get(java.util.Calendar.YEAR);
			int anioMinimo=anioActual-anioFecha; //Minimo 100 anios atras
			if(anioMinimo>100){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noFechaNacimientoInvalida"),dtbxFechaNacimiento);
				return;
			}
			
			
			/*Validando que los apellidos y nombres no incluyan comillas simples - jabanto */
			if(txtApePat.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice el Apellido Parteno del Pasajero.",txtApePat);
				return;
			}else if(txtApeMat.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice el Apellido Parteno del Materno.",txtApeMat);
				return;
			}else if(txtNombres.getText().trim().indexOf("'")>0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice los Nombres del Pasajero.",txtNombres);
				return;
			}
			
			//Valida fecha de nacimiento
//			if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA)
//				validarFechaNacimiento();
			
			
			if (!(txtEmailPax.getText().trim().isEmpty())){
				if (!(UtilData.validateEmail(txtEmailPax.getText().trim())))
					throw new MailIncorectoException();
			}

			if (action == Constantes.ACTION_NEW)
				oPasajero = new Pasajero();

			Ubigeo oUbigeo = new Ubigeo();
			oUbigeo.setId(txtUbigeoIdPax.getText());
						
			oPasajero.setApellidoPaterno(txtApePat.getText().trim().toUpperCase());
			oPasajero.setApellidoMaterno(txtApeMat.getText().trim().equals("")?null:txtApeMat.getText().trim().toUpperCase());
			oPasajero.setNombre(txtNombres.getText().trim().toUpperCase());
			oPasajero.setTipoDocumento((TipoDocumento) cmbTipoDocumento.getSelectedItem().getValue());
			oPasajero.setNumeroDocumento(txtDocumentoPax.getText().trim().toUpperCase());
			oPasajero.setSexo((Sexo) cmbSexo.getSelectedItem().getValue());
//			oPasajero.setEstadoCivil(cmbEstadoCivil.getSelectedItem().getValue() instanceof EstadoCivil ? (EstadoCivil) cmbEstadoCivil.getSelectedItem().getValue() : null);
			oPasajero.setUbigeo(oUbigeo);
			oPasajero.setDireccion(txtDireccionPax.getText().trim().toUpperCase());
			oPasajero.setEmail(txtEmailPax.getText().trim());
			oPasajero.setTelefono(txtTelefono.getText().trim().toUpperCase());
			oPasajero.setAgencia(agencia);
//			oPasajero.setFechaNacimiento(Util.DatetoString(dtbxFechaNacimiento.getValue(), Constantes.DATE_FORMAT).
//					equals(Util.DatetoString(Constantes.FECHA_NULL,Constantes.DATE_FORMAT)) ? null : Util.DatetoString(dtbxFechaNacimiento.getValue(),Constantes.DATE_FORMAT));
			if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				oPasajero.setFechaNacimiento(generarFechaNacimiento());
			}else{
				if(action == Constantes.ACTION_NEW)
					oPasajero.setFechaNacimiento(calcularFechaNacimiento());
			}
			oPasajero.setNacionalidad(cmbNacionalidad.getSelectedItem().getValue() instanceof Nacionalidad?(Nacionalidad)cmbNacionalidad.getSelectedItem().getValue():null);
			oPasajero.setNombresApellidos(oPasajero.getNombre()+" "+oPasajero.getApellidoPaterno()+(oPasajero.getApellidoMaterno()==null?"":(" "+oPasajero.getApellidoMaterno())));

			if (action == Constantes.ACTION_NEW) {
				oPasajero.setIndeseable(Constantes.FALSE_VALUE);
				oPasajero.setMotivo("");
				oPasajero.setKilometros(0.00);
				UtilData.auditarRegistro(oPasajero, false, usuario, Executions.getCurrent());
			} else {
				UtilData.auditarRegistro(oPasajero, true, usuario, Executions.getCurrent());
			}
			
			/*Valida si es DNI y si fue validado correctamente por la RENIEC. - 06/04/2015*/
			oPasajero.setValidadoReniec(Constantes.FALSE_VALUE);
			if(oPasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(imgValidacionDNI.getSrc()!=null && !(imgValidacionDNI.getSrc().trim().isEmpty()) && imgValidacionDNI.getSrc().equals(Constantes.IMAGE_VALIDACION_DNI_OK))
					oPasajero.setValidadoReniec(Constantes.TRUE_VALUE);
			}
			
			/*Registra auditoria si tiene o no email el cliente - 03/05/2017*/
			oPasajero.setTieneEmail(chbxNoTieneMail.isChecked()?Constantes.FALSE_VALUE:Constantes.TRUE_VALUE);
			oPasajero.setFechaTieneEmail(Constantes.FORMAT_DATE_TIME_24H.parse(MyTime.dateTimeServer()));
			oPasajero.setUsuarioTieneEmail(getUsuario());
			oPasajero.setAgenciaTieneEmail(getAgencia());
			
			oPasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);

			String msg = "";
			if (action == Constantes.ACTION_NEW)
				msg = Messages.getString("WndVentaReserva.question.guardarPasajero");
			else
				msg = Messages.getString("WndVentaReserva.question.actualizarPasajero");

			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, EventSavePax(oPasajero));

		} catch (TipoDocumentoNullException tdnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionTipoDocumento"), cmbTipoDocumento);			
		} catch (NumeroDocumentoNullException ndnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtDocumentoPax);
		} catch (ApellidoPaternoNullException apnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtApePat);
		} catch (NombresNullException nnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtNombres);
		} catch (UbigeoNullException unex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"), btnUbigeoPax);
		} catch (SexoNullException snex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionSexo"), cmbSexo);
		} catch (NacionalidadException nex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoNacionalidad"), cmbNacionalidad);
		} catch (TelefonoNullException nex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTelefono"), txtTelefono);
//		} catch(EmailNullException enex){
//			DlgMessage.information(Messages.getString("WndVentaReserva.information.noIngresoEmail"), txtEmailPax);
		} catch(MailIncorectoException miex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.ingreseCorreoValido"), txtEmailPax);
		} catch(VentaReservaException vrex){
			if(vrex.getTipo().intValue()==VentaReservaException.LONGITUD_NUMERO_DOCUMENTO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.longitudDocumento"), txtDocumentoPax);
		}catch(PasajeroException pax){
			if(pax.getTipo().intValue()==PasajeroException.EDAD_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noIngresoEdad"), ntbxEdad);
			else if(pax.getTipo().intValue()==PasajeroException.NUMERO_DOCUMENTO_INCORRECTO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.docPaxIncorrect"), txtDocumentoPax);
		}catch(FechaNacimientoException fn){
//			if(fn.getTipo().intValue()==FechaNacimientoException.ANIO_NO_VALID){
//				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaNacimientoAnioInvalidadPax"), cmbAnio);
//			}else if (fn.getTipo().intValue()==FechaNacimientoException.MES_NO_VALID){
//				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaNacimientoMesInvalidadPax"), cmbMes);
//			}else if (fn.getTipo().intValue()==FechaNacimientoException.DIA_NO_VALID){
//				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaNacimientoDiaInvalidadPax"), cmbDia);
//			}
		}catch (Exception ex) {
			DlgMessage.information(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Evento para controlar el guardado del registro de Pasajero.
	 * @param action	: Indica si es una insercion o actualizacion.
	 * @param oPasajero	: Objeto a guardar.
	 * @return Event
	 */
	private EventListener<Event> EventSavePax(final Pasajero oPasajero) {
		EventListener<Event> ev = new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				try {
					if (e.getName().equals("onYes")) {
						if (action == Constantes.ACTION_NEW) {
							ServiceLocator.getPasajeroManager().guardar(oPasajero);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardar"));
						} else {
							ServiceLocator.getPasajeroManager().actualizar(oPasajero);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoActualizar"));
						}
						tlbbtnNuevoPax.setDisabled(false);
						tlbbtnModificarPax.setDisabled(false);
						tlbbtnCancelarPax.setDisabled(true);
						tlbbtnGuardarPax.setDisabled(true);
						action = Constantes.FAILURE;
						disabledControlsPax(true);
						disabledControlsPaxDatosPer(false);
					}
				} catch (DocumentoPaxDuplicadoException dpdnex) {
					DlgMessage.information(Messages.getString("WndPasajero.information.noDocumentoPaxDuplicado"), txtDocumentoPax);
				} catch (Exception ex) {
					DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		};
		return ev;
	}
	

	/* *******************************	IMPLEMENTACIONES PARA EL CLIENTE	******************************************** */
	/**
	 * Para ingresar un nuevo cliente
	 */
	public void onNewClient()throws Exception {
		String nroRuc = txtDocumentoCliente.getValue().trim(); 
		onCleanControlsClient();
		disabledControlsClient(false);
		action = Constantes.ACTION_NEW;
		tlbbtnNuevoClient.setDisabled(true);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(false);
		tlbbtnGuardarClient.setDisabled(false);
		txtDocumentoCliente.setFocus(true);
		
		dblDescuento.setValue(0.0);
		imgQuitarPromocion.setVisible(false);
		lblPromocion.setValue("");
		promocionAplicada=null;
		dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
		txtDocumentoCliente.setValue(nroRuc);
		verificarClienteSunat();
	}
	
	public void verificarClienteSunat()throws WrongValueException, Exception{
		if(action==Constantes.ACTION_NEW  
			&& !(txtDocumentoCliente.getText().trim().isEmpty())){
			
			String nroDocumento=txtDocumentoCliente.getText().trim();
			
				//Consulta RUC EN sunat
				List<String> ruc = RESTCiva.getDatosRuc(nroDocumento);
				

				if(ruc!=null){
//				Reniec reniec = new Reniec();
				txtDocumentoCliente.setValue(ruc.get(0));
				txtRazonSocial.setValue(ruc.get(1));
				txtDireccionCliente.setValue(ruc.get(2));

				}else{
					String numeroDocumento=txtDocumentoCliente.getText().trim();
					
					onCleanControlsClient();		
					//recupera valores ingresado por el usuario
					txtDocumentoPax.setText(numeroDocumento);
					
//					if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
//						Ubigeo oUbigeo = new Ubigeo();
//						oUbigeo.setId(Constantes.ID_UBIGEO_LIMA);
//						oUbigeo.setCodigoDepartamento("15");
//						oUbigeo.setCodigoProvincia("01");
//						oUbigeo.setCodigoDistrito("01");
//						oUbigeo.setNombreUbigeo("LIMA");
//						try{
//							if(oUbigeo!=null) {
//								String idUbigeo = oUbigeo.getId();
//								String ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
//								txtUbigeoPax.setText(ubicacionCompleta);
//								txtUbigeoIdPax.setText(idUbigeo);
//							}
//						}catch(Exception ex){
//							ex.printStackTrace();
//						}
					
				}
			}		
	}
	
	/**
	 * Para modificar un Cliente existente
	 */
	public void onModifyClient() {
		action = Constantes.ACTION_MODIFY;
		disabledControlsClient(false);
		tlbbtnNuevoClient.setDisabled(true);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(false);
		tlbbtnGuardarClient.setDisabled(false);
		txtDocumentoCliente.setFocus(true);
		
	}
	
	/**
	 * Para cancelar la edicion o registro de un cliente
	 */
	public void onCancelClient() {
		disabledControlsPax(true);
		tlbbtnNuevoClient.setDisabled(false);
		tlbbtnModificarClient.setDisabled(true);
		tlbbtnCancelarClient.setDisabled(true);
		tlbbtnGuardarClient.setDisabled(true);
		tlbbtnLimpiarClient.setDisabled(false);
		action = Constantes.FAILURE;
		txtDocumentoCliente.setFocus(true);		
	}

	/**
	 * Limpia los controles del cliente.
	 */
	public void onCleanControlsClient()throws Exception {
		/*Si el cliente no tiene promosion, recupera la promocion del paxfree si es que este lo tubiera*/
		if(oCliente!=null && oCliente.isDescuentoAutoByCliente()){
			if(oPasajero==null || oPasajero.isDescuentoAutoByPaxFree()==false)
				quitarPromocion();
			else if(oPasajero.isDescuentoAutoByPaxFree()){
				try {
					oCliente = null;
					onSearchPromoPaxFree(oPasajero);
				} catch (Exception e) {}
			}
		}
		
		txtDocumentoCliente.setText("");
		txtRazonSocial.setText("");
		txtDireccionCliente.setText("");
		txtTelefonoClienteOne.setText("");
		txtTelefonoClienteTwo.setText("");
		txtEmailCliente.setText("");
		txtUbigeoCliente.setText("");
		txtUbigeoIdCliente.setText("");
		txtContactoCliente.setText("");
		txtRubro.setText("");
		ibxCantidadTrabajadores.setText("");
		oCliente = null;
		lineaCreditoCliente=null;
//		lineaContadoCliente=null;
		lblDescuento.setValue("");
		txtDocumentoCliente.setFocus(true);
		
		
		/*21/10/2016 - jabanto*/
		onSelectDefaultTipoComprobante();
		onLoadEspecieValorada(txtNumeroBoleto);
		
		
		
		/*Si el cliente no tiene promosion, recupera la promocion del paxfree si es que este lo tubiera*/
//		if(oPasajero!=null && oPasajero.isDescuentoAutoByPaxFree()){
//			try {
//				onSearchPromoPaxFree(oPasajero);
//			} catch (Exception e) {}	
//		}else if (promocionTarifa==null || promocionTarifa.getEsAcumulable().intValue()==Constantes.TRUE_VALUE)
//			quitarPromocion();
				
		
	}

	/**
	 * Deshabilita o habilita los controles relacionados al cliente.
	 * @param arg	= Boolean valor a utilizar.
	 */
	public void disabledControlsClient(boolean arg) {
		txtDireccionCliente.setDisabled(arg);
		txtTelefonoClienteOne.setDisabled(arg);
		txtTelefonoClienteTwo.setDisabled(arg);
		txtEmailCliente.setDisabled(arg);
		txtContactoCliente.setDisabled(arg);
		txtUbigeoCliente.setDisabled(arg);
		txtRubro.setDisabled(arg);
		ibxCantidadTrabajadores.setDisabled(arg);
	}
	
	/**
	 * Busca los clientes de acuerdo al criterio de busqueda.
	 * @param criterio	: Puede ser DOCUMENTO o RAZON SOCIAL.
	 */
	public void onSearchClient(Integer criterio){
		try{
			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			ArrayList<Cliente> lstClientes = null;
			if(criterio.intValue()==SEARCH_BY_DOCUMENTO){
				criterioBusqueda.put("numeroDocumento", txtDocumentoCliente.getText().toUpperCase()+"%");
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("razonSocial");
				criteriosOrdenar.add("numeroDocumento");
				lstClientes = ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
			}else{
				String[] str1 = txtRazonSocial.getText().trim().toUpperCase().split(" ");
				lstClientes = ServiceLocator.getClienteManager().buscarPorRazonSocial(str1);
			}
			listarClientes(lstClientes);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Permite crear un array de array para luego cargarlos en un listbox.
	 * @param lstRegistros	: ArrayList con los datos de los clientes encontrados.
	 * @throws Exception
	 */
	private void listarClientes(ArrayList<Cliente> lstRegistros)throws Exception{
		ArrayList<Object> lstClientes = new ArrayList<Object>();
		grpbxListaClientes.setVisible(false);
		
		if(lstRegistros.size()==1){
			Cliente cliente = lstRegistros.get(0);
			mantenimientoRegistroClient(cliente.getId());
		}else if(lstRegistros.size()>0){
			for (int r = 0; r < lstRegistros.size(); r++) {
				Cliente oCliente = lstRegistros.get(r);
				ArrayList<Object> lstFila = new ArrayList<Object>();
				lstFila.add(oCliente.getId());
				lstFila.add(r + 1);
				lstFila.add(oCliente.getNumeroDocumento());
				lstFila.add(oCliente.getRazonSocial());
				lstFila.add(oCliente.getContacto());
				lstClientes.add(lstFila);
			}
			Util.llenarListbox(lbxClientes, lstClientes, true);
			grpbxListaClientes.setVisible(true);			
		}else{
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noClientesEncontrados"));
			String nroRuc = txtDocumentoCliente.getValue();
			onCleanControlsClient();
			txtDocumentoCliente.setValue(nroRuc);
			txtDocumentoCliente.select();
		}
		disabledControlsPax(true);
	}
	
	/**
	 * Muestra los datos del registro que se va a modificar.
	 * @param id	: Identificador del registro a modificar.
	 * @throws Exception
	 */
	 private void mantenimientoRegistroClient(Long id) {		 
		try {
			
			
			
			promocionAplicada = null;
			tlbbtnModificarClient.setDisabled(false);
			oCliente = ServiceLocator.getClienteManager().buscarPorId(id);
			
			/*21/10/2016 - jabanto*/
			onSelectDefaultTipoComprobante();
			onLoadEspecieValorada(txtNumeroBoleto);
			
			
			Ubigeo oUbigeo = oCliente.getUbigeo();
			String idUbigeo = new String();
			String ubicacionCompleta = new String();
			if (oUbigeo != null) {
				idUbigeo = oUbigeo.getId();
				ubicacionCompleta = ServiceLocator.getUbigeoManager().ubicacionGeografica(oUbigeo);
			}

			txtRazonSocial.setText(oCliente.getRazonSocial());
			txtDocumentoCliente.setText(oCliente.getNumeroDocumento());
			txtDireccionCliente.setText(oCliente.getDireccion());
			txtUbigeoIdCliente.setText(idUbigeo);
			txtUbigeoCliente.setText(ubicacionCompleta);
			txtContactoCliente.setText(oCliente.getContacto());
			txtTelefonoClienteOne.setText(oCliente.getTelefonoFijo());
			txtTelefonoClienteTwo.setText(oCliente.getTelefonoFijo2());
			txtEmailCliente.setText(oCliente.getEmail());
			txtRubro.setText(oCliente.getRubro());
			ibxCantidadTrabajadores.setText(oCliente.getCantidadTrabajadores().toString());
			
			lineaCreditoCliente=null;
//			lineaContadoCliente=null;

			lblDescuento.setValue("");
			
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
				loadTiposCentroCosto();
				loadCentroCosto();
				tlbbtnLimpiarClient.setDisabled(true);
			}else
				tlbbtnLimpiarClient.setDisabled(false);
			
			/*	Si la venta es desde un punto TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
				/* Valida si el cliente tiene credito- Implementado 17/03/2013 */
				LineaCreditoCliente lineaCreditoCliente = ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(oCliente.getId());
				/*	Si tiene linea de credito*/
				if(lineaCreditoCliente != null) {
					/* Desactiva el button "Modificar cliente" */
					tlbbtnModificarClient.setDisabled(true);
					tlbbtnModificarClient.setImage("resources/mp_editarDisabled.png");
					if(lineaCreditoCliente.getEsCanje().equals(Constantes.SI)) {
						lblDescuento.setValue(Constantes.TIPCONVCLI_CREDITO_CANJE_PUBLICITARIO);
					}else{
						lblDescuento.setValue(Constantes.TIPCONVCLI_CORPORATIVO);
					}
					this.lineaCreditoCliente = lineaCreditoCliente;
					Util.seleccionarValorItemCombo(FormaPago.class,cmbFormaPago, Constantes.ID_FORPAG_CONTADO);
					onLoadTipoFormaPago();
				}else {
					lblDescuento.setValue(Constantes.TIPCONVCLI_CONTADO);
					cmbFormaPago.setDisabled(true);
					tlbbtnModificarClient.setDisabled(false);
					tlbbtnModificarClient.setImage("resources/mp_editarEnabled.png");
				}
			}
			
			
			
			/*	Verificamos promociones para el cliente, pero si el destino no este considerado en el pool */
//			if(detailItinerary.getItinerario().getOperadoPor()==null){ //No apica descuentos a otro operador, como civa o cruz del sur
			if(!(Util.isDestinoPool(detailItinerary))){
				if(cmbTipoOperacion.getSelectedIndex()==0){
					if(promocionTarifa==null || (promocionTarifa.getEsAcumulable().intValue()==Constantes.TRUE_VALUE)){
						TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
						criteriosBusqueda.put("cliente", oCliente.getId().toString());
						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						
						List<Promocion> lstPromocion = null;
						if(rdVentaFechaAbierta.isSelected())
							lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detalleItinerarioFechaAbierta.getFechaPartida(), Constantes.DATE_FORMAT));
						else{
							if(detailItinerary!=null)
								lstPromocion = ServiceLocator.getPromocionManager().buscarPorX(criteriosBusqueda, null, Util.DatetoString(detailItinerary.getFechaPartida(), Constantes.DATE_FORMAT));
						}
						if(lstPromocion!=null && lstPromocion.size()>0){
							for(int i=0; i<lstPromocion.size(); i++){
								AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
								promocionAplicada = aplicarPromocion.executePromocion(lstPromocion.get(i).getId().toString(), false);
								if(promocionAplicada!=null){
									oCliente.setDescuentoAutoByCliente(true);
									break;
								}
								imgQuitarPromocion.setVisible(true);
							}
							
							dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
						}
						
						/*Si el cliente no tiene promosion, recupera la promocion del paxfree si es que este lo tubiera*/
						if(promocionAplicada==null && oPasajero!=null && oPasajero.isDescuentoAutoByPaxFree()){
							onSearchPromoPaxFree(oPasajero);
						}else if (promocionAplicada==null){
							if(oPasajero ==null || oPasajero.isDescuentoAutoByPaxFree()==false)
								quitarPromocion();
						}
					}
				}
			}			
			
			
			
		}catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
		}
	}
	
	 
	/**
	 * Realiza el guardado de los datos del cliente
	 * @throws Exception
	 */
	public void onSaveClient()throws Exception{
		try{
			if(txtDocumentoCliente.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if(txtRazonSocial.getText().trim().equals(""))
				throw new RazonSocialNullException();
			else if(txtUbigeoCliente.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if (txtRazonSocial.getText().trim().length()<=5){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.razonSocialIncorrect"), txtRazonSocial);
				return;
			}else if (txtDireccionCliente.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"), txtDireccionCliente);
				return;
			}else if (txtDireccionCliente.getText().trim().length()<10){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.direccionIncorrect"), txtDireccionCliente);
				return;
			}
			
//			else if (ibxCantidadTrabajadores.getText().trim().isEmpty() || ibxCantidadTrabajadores.getValue().intValue()<=0)
//				throw new CantidadTrabajadoresNullException();
			
			/*Validando que los datos del cliente no incluyan comillas simples - jabanto */
			if(txtRazonSocial.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice la Razón Social del Cliente.",txtRazonSocial);
				return;
			}else if(txtDireccionCliente.getText().trim().indexOf("'")>=0){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noComillaSimple")+", revice la Dirección del Cliente.",txtDireccionCliente);
				return;
			}
			
			
			if (!(txtEmailCliente.getText().trim().isEmpty())){
				if (!(UtilData.validateEmail(txtEmailCliente.getText().trim())))
					throw new MailIncorectoException();
			}
			
			if(!Util.validarRUC(txtDocumentoCliente.getText().trim()))
				throw new RucInvalidoException();
			
			if (action == Constantes.ACTION_NEW)
				oCliente = new Cliente();
			
			Ubigeo oUbigeo = new Ubigeo();
			oUbigeo.setId(txtUbigeoIdCliente.getText());
			
			oCliente.setNumeroDocumento(txtDocumentoCliente.getValue().toString());
			oCliente.setRazonSocial(txtRazonSocial.getText().toUpperCase());
			oCliente.setContacto(txtContactoCliente.getText().toUpperCase());
			oCliente.setDireccion(txtDireccionCliente.getText().toUpperCase());
			oCliente.setTelefonoFijo(txtTelefonoClienteOne.getText().toUpperCase());
			oCliente.setTelefonoFijo2(txtTelefonoClienteTwo.getText().toUpperCase());
			oCliente.setEmail(txtEmailCliente.getText());
			oCliente.setRubro(txtRubro.getText().trim().toUpperCase());
			oCliente.setCantidadTrabajadores(0);
			oCliente.setUbigeo(oUbigeo);
			oCliente.setAgencia(agencia);
			
			if(oCliente.getId()==null){
				oCliente.setKilometros(0.00);
				UtilData.auditarRegistro(oCliente, false, usuario, Executions.getCurrent()); 
			}else{
				UtilData.auditarRegistro(oCliente, true, usuario, Executions.getCurrent());
			}
			oCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			String msg = "";
			if(action == Constantes.ACTION_NEW)
				msg = Messages.getString("WndVentaReserva.question.guardarCliente");
			else
				msg = Messages.getString("WndVentaReserva.question.actualizarCliente");
			
			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, EventSaveClient(oCliente));
			
			
			
			
//		}catch (CantidadTrabajadoresNullException ctnex){
//			DlgMessage.information(Messages.getString("WndCliente.information.CantidadTrabajadoresNull"));	
		}catch (MailIncorectoException miec){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.EmailIncorrecto"), txtEmailCliente);
		}catch(NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"), txtDocumentoCliente);
		}catch(RazonSocialNullException rsnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"));
			txtRazonSocial.setFocus(true);
		}catch(UbigeoNullException unex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"));
			txtUbigeoCliente.setFocus(true);
		}catch(RucInvalidoException riex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.rucInvalido"), txtDocumentoCliente);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	/**
	 * Evento para controlar el guardado del registro de Cliente.
	 * @param action	: Indica si es una insercion o actualizacion.
	 * @param oCliente	: Objeto a guardar.
	 * @return Event
	 */
	private EventListener<Event> EventSaveClient(final Cliente oCliente){
		EventListener<Event> ev = new EventListener<Event>() {
			@Override
			public void onEvent(Event e) {
				try {
					if (e.getName().equals("onYes")) {
						if (action == Constantes.ACTION_NEW) {
							ServiceLocator.getClienteManager().guardar(oCliente);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardar"));
						} else {
							ServiceLocator.getClienteManager().actualizar(oCliente);
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoActualizar"));
						}
						tlbbtnNuevoClient.setDisabled(false);
						tlbbtnModificarClient.setDisabled(false);
						tlbbtnCancelarClient.setDisabled(true);
						tlbbtnGuardarClient.setDisabled(true);
						action = Constantes.FAILURE;
						disabledControlsClient(true);
						
						/*21/10/2016 - jabanto*/
						onSelectDefaultTipoComprobante();
						onLoadEspecieValorada(txtNumeroBoleto);
					}
				} catch (RucDuplicadoException rdex) {
					DlgMessage.information(Messages.getString("WndCliente.information.RucDuplicado"), txtDocumentoCliente);
				} catch (RazonSocialDuplicadoException rsdex) {
					DlgMessage.information(Messages.getString("WndCliente.information.RazonSocialDuplicado"), txtRazonSocial);
				} catch (Exception ex) {ex.printStackTrace();
					DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
				}
			}
		};		
		return ev;
	}
	
	/**
	 * Carga los tipos de Forma de pago.
	 */
	public void onLoadTipoFormaPago(){
		try{			
			cmbTipoFormaPago.getItems().clear();
			
			/*Implementado 15-03-2013 - jabanto*/
			cmbOperadorTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.getItems().clear();
			cmbTipoFormaPago.setText(null);
			cmbOperadorTarjetaCredito.setText(null);
			cmbTarjetaCredito.setText(null);
			cmbOperadorTarjetaCredito.setDisabled(true);
			cmbTarjetaCredito.setDisabled(true);
										
			if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago){
				FormaPago formaPago = cmbFormaPago.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("formaPago.id", formaPago.getId());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				UtilData.cargarGenericData(cmbTipoFormaPago, false);
				for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
					Comboitem item = new Comboitem();
					item.setLabel(tipoFormaPago.getDenominacion());
					item.setValue(tipoFormaPago);
					cmbTipoFormaPago.appendChild(item);
				}
				cmbTipoFormaPago.setDisabled(true);				
				
				/*Valida si el Cliente credito cuenta con saldo suficiente - Implementado 15/03/2013 jabanto*/
				if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_TEPSA)){
					cmbTipoFormaPago.setDisabled(false);					
					if(formaPago.getId().equals(Constantes.ID_FORPAG_CREDITO)){
						if(lineaCreditoCliente!=null){
							if(lineaCreditoCliente.getSaldo()<=0 || lineaCreditoCliente.getSaldo() < (dblImporte.getValue()!=null? dblImporte.getValue(): .00)){
								cmbFormaPago.setSelectedIndex(0);
								cmbTipoFormaPago.getItems().clear();
								cmbTipoFormaPago.setText(null);
								cmbTipoFormaPago.setDisabled(true);
								throw new SaldoInsuficienteException();
							}
							
							if(lineaCreditoCliente.getEsCanje().equals(Constantes.SI))
								Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO);								
							else
								Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CREDITO);								
							cmbTipoFormaPago.setDisabled(true);
						}
					}else if(formaPago.getId().equals(Constantes.ID_FORPAG_CONTADO))
						Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_EFECTIVO);
				}else{
					Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_CREDITO);
					if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_VIAJES)){
						onValidateTipoFormaPago();
					}
				}
			}else
				cmbTipoFormaPago.setDisabled(true);
			
		}catch(SaldoInsuficienteException siex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.saldoInsuficiente"));
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Realiza una validación del Tipo de Forma de Pago, para habilitar o deshabilitar algunos controles.
	 * @throws Exception 
	 */
	public void onValidateTipoFormaPago() throws Exception{
		cmbOperadorTarjetaCredito.getItems().clear();
		cmbOperadorTarjetaCredito.setText("");
		cmbOperadorTarjetaCredito.setDisabled(true);
		cmbTarjetaCredito.getItems().clear();
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.setDisabled(true);
		
		quitarPromocion();
		/* recupera la promocion del pasajero si es paxfri */
		if(oPasajero!=null && oPasajero.getPasajeroFrecuente()!=null && oPasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE){
			/*Valida si es una promocion por tarifa y si esta permite aplicar o no descuento - 15/03/2017 - jabanto*/
			if(promocionTarifa==null || (promocionTarifa.getEsAcumulable().intValue()==Constantes.TRUE_VALUE))
				onSearchPromoPaxFree(oPasajero);
		}
		
		
		if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){ 
			/*	Si es tarjeta cargamos los operadores de tarjeta de credito	*/
			/*	Aqui realizamos una jugada para el caso de TU ENTRADA y agencias de viaje para que ingresen la tarjeta de credito	*/
			if(cmbTipoFormaPago.getText().equals("TARJETA") || cmbTipoFormaPago.getText().equals("CREDITO")){
				cmbOperadorTarjetaCredito.getItems().clear();
				UtilData.cargarDataCombo(cmbOperadorTarjetaCredito, OperadorTarjetaCredito.class, false);
				cmbOperadorTarjetaCredito.setDisabled(false);
			}else if(cmbTipoFormaPago.getText().equals("TRANSFERENCIA"))	//Si es una transferecnia bancaria
				txtOperacionBancaria.setDisabled(false);
			else{
				txtOperacionBancaria.setDisabled(true);
				cmbOperadorTarjetaCredito.setDisabled(true);
			}
		}else{
			txtOperacionBancaria.setDisabled(true);
			cmbOperadorTarjetaCredito.setDisabled(true);
		}
	}	
	
	/**
	 * Carga los diferentes tarjetas de credito, de acuerdo al operador seleccionado.
	 */
	public void onLoadTarjetas(){
		try{
			cmbTarjetaCredito.getItems().clear();
			cmbTarjetaCredito.setText("");
			cmbTarjetaCredito.setDisabled(true);
			
			if(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
				OperadorTarjetaCredito operadorTarjetaCredito = cmbOperadorTarjetaCredito.getSelectedItem().getValue();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				UtilData.cargarGenericData(cmbTarjetaCredito, false);
				for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
					Comboitem item = new Comboitem();
					item.setLabel(tarjetaCredito.getDenominacion());
					item.setValue(tarjetaCredito);
					cmbTarjetaCredito.appendChild(item);
				}
				cmbTarjetaCredito.setDisabled(false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Proceso que selecciona el metodo correcto a utilizar al momento de guaradr la venta o reserva.
	 */
	public void onGuardarVentaReserva()throws Exception{
		try{
			/*Valida si el usuario tiene una liquidación aperturada - 22/07/2021 - jabanto*/
			if(cmbTipoOperacion.getSelectedIndex()==0 && getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION)==null)
				throw new LiquidacionNullException();
			
			//Valida que, la fecha de la liquidación sea igual a la actual - 22/07/2021 - jabanto
			String fechaActual=Constantes.FORMAT_DATE.format(new Date());
			String s_fechaLiquidacion=(fechaLiquidacion!=null?Constantes.FORMAT_DATE.format(fechaLiquidacion):"");
			if(!(fechaActual.equals(s_fechaLiquidacion))) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaLiquidacionDiferente"));
				return;
			}
				
			
			/*	Valida el credito del cliente, si la venta es al Credito	*/
			if(!(cmbTipoMoneda.getSelectedItem().getValue() instanceof TipoMoneda)){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectTipoMoneda"));
				return; 
			}
			
			if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO)){
				lineaCreditoCliente=null;
				if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_TEPSA))
					lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(oCliente.getId());
				else /*Valida credito de Agencias de viaje o Clintes corporativos, u otros tipos de clientes con Credito.*/
					lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(clienteCredito.getId());
				
				/*Valida el tipo de moneda*/
//				if(((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPMON_SOLES){
				if(lineaCreditoCliente!=null && lineaCreditoCliente.getSaldo() < dblImporte.getValue())
					throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);
//				}else{
//					if(lineaCreditoCliente!=null && lineaCreditoCliente.getSaldo() < pagoSoles.getImportePagado())
//						throw new LineaCreditoClienteException(LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO);
//				}
//				
								
			}
			
			lstVentasVoucher.clear();
			
			
			/*Validando si es una venta del POOL*/
			if(lbxAsientos.getSelectedItem()!=null && lbxAsientos.getSelectedItem().getValue() instanceof AsientoPool){
				AsientoPool oasientoPool= lbxAsientos.getSelectedItem().getValue();
				if(detalleItinerarioIda == null){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
					return;
				}else if(cmbPtoEmbarque.getSelectedIndex()<=0){
					tabInformacionVentaIda.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPtoEmbarque);
					return;
				}else if(cmbPtoDesembarque.getSelectedIndex()<=0){
					tabInformacionVentaIda.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPtoDesembarque);
					return;
				}
				else if(oasientoPool.getObjectCiva()!=null && cmbAlimentacion.getSelectedIndex()<=0){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noPreferenciaAlimentaria"), cmbAlimentacion);
					return;
				}
				
				if(oasientoPool.getObjectCruzdelsur()!=null){
					if(cmbAlimen1.isVisible() && cmbAlimen1.getSelectedIndex()<=0){
						DlgMessage.information("Debe seleccionar "+lblAlimen1.getValue(), cmbAlimen1);
						return;
					}else if(cmbAlimen2.isVisible() && cmbAlimen2.getSelectedIndex()<=0){
						DlgMessage.information("Debe seleccionar "+lblAlimen2.getValue(), cmbAlimen2);
						return;
					}else if(cmbAlimen3.isVisible() && cmbAlimen3.getSelectedIndex()<=0){
						DlgMessage.information("Debe seleccionar "+lblAlimen3.getValue(), cmbAlimen3);
						return;
					}else if(cmbAlimen4.isVisible() && cmbAlimen4.getSelectedIndex()<=0){
						DlgMessage.information("Debe seleccionar "+lblAlimen3.getValue(), cmbAlimen3);
						return;
					}else if (txtTelefono.getText().trim().isEmpty() || txtTelefono.getText().trim().length()<=5){
						DlgMessage.information("Debe ingresar el Número de Teléfono y/o este no es válido", txtTelefono);
						return;
					}
				}
				
				if(txtNumeroBoleto.getText().trim().equals("") && ((String)cmbTipoOperacion.getSelectedItem().getValue()).equals(Constantes.TIPO_OPERACION_VENTA)){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));
					return;
				}else if (!(tlbbtnGuardarPax.isDisabled())){// 06/09/2013 - jabanto
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noSavedPax"));
					tabPasajero.setSelected(true);
//					onSavePax();
					return;
				}else if(oPasajero==null){
					tabPasajero.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajero"), txtDocumentoPax);
					return;
				}else if(oPasajero!=null && oPasajero.getId()==null){
					tabPasajero.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajero"), txtDocumentoPax);
					return;
				}else if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante)){
					tabPagos.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"), cmbTipoComprobante);
					return;
				}else if(!(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago)){
					tabPagos.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noFormaPago"), cmbFormaPago);
					return;
				}else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago)){
					tabPagos.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"), cmbTipoFormaPago);
					return;
				}else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
					if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito)){
						tabPagos.setSelected(true);
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
						return;
					}else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)){
						tabPagos.setSelected(true);
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbTarjetaCredito);
						return;
					}
				}else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TRANSFERENCIA)){
					if(txtOperacionBancaria.getText().trim().equals("")){
						tabPagos.setSelected(true);
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"), txtOperacionBancaria);
						return;
					}
				}else if(oPasajero.getIndeseable().intValue()==Constantes.TRUE_VALUE){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.pasajeroIndeseable"));
					return;
				}else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago 
						&& cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_CREDITO)){
					if((cmbOperadorTarjetaCredito.getItems().size()>0 && cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito) && !(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito)){
						tabPagos.setSelected(true);
						DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
						return;
					}
				}else if(dblTarifa.getValue()<=0.0){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneTarifaIda"));
					return;
				}
				else if (oCliente!=null && (oCliente.getDireccion()==null || oCliente.getDireccion().trim().isEmpty())){
					tabCliente.setSelected(true);
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"),txtDireccionCliente);
				}
				
				Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarVenta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								AsientoPool asientoPool= lbxAsientos.getSelectedItem().getValue();
								TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
								criteriosBusqueda.put("localidadOrigen", cmbOrigen.getSelectedItem().getValue());
								criteriosBusqueda.put("localidadDestino",cmbDestino.getSelectedItem().getValue());
								criteriosBusqueda.put("estadoRegistro",Constantes.VALUE_ACTIVO);
								List<Ruta> result= ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
								
								VentaPasaje ventaPasaje= new VentaPasaje();
								ventaPasaje.setItinerario(new Itinerario((long)1));
								ventaPasaje.setRuta(result.get(0));
								ventaPasaje.setPasajero(oPasajero);
								ventaPasaje.setCliente(oCliente);
								FormaPago formaPago=ServiceLocator.getFormaPagoManager().buscarPorId(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().longValue());
								ventaPasaje.setFormaPago(formaPago);
								if(asientoPool.getObjectCruzdelsur()!=null)
									ventaPasaje.setServicio(new Servicio(6));//Identificador del Servicio "VENTA POOL - CRUZ DEL SUR"
								else
									ventaPasaje.setServicio(new Servicio(7));//Identificador del Servicio "VENTA POOL - EXCLUCIVA"
								ventaPasaje.setTipoComprobante((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue());
								ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
								ventaPasaje.setTipoFormaPago(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()));
								if(cmbTarjetaCredito.getSelectedIndex()>0 && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
									TarjetaCredito tarjetaCredito=ServiceLocator.getTarjetaCreditoManager().buscarPorId(((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()).getId().longValue());
									ventaPasaje.setTarjetaCredito(tarjetaCredito);
								}
								ventaPasaje.setNumeroBoleto(txtNumeroBoleto.getText().trim());
								ventaPasaje.setNumeroAsiento(Integer.valueOf(asientoPool.getNumeroAsiento()));
								ventaPasaje.setNumeroPiso(asientoPool.getNivelAsiento());
								ventaPasaje.setNumeroControl("--");
								if(asientoPool.getObjectCruzdelsur()!=null){
									ventaPasaje.setAgenciaPartida(ServiceLocator.getAgenciaManager().buscarPorId((long)837)); //Identificador de la agencia Cruz del sur
									ventaPasaje.setAgenciaLlegada(ServiceLocator.getAgenciaManager().buscarPorId((long)837)); //Identificador de la agencia Cruz del sur
								}else{
									ventaPasaje.setAgenciaPartida(ServiceLocator.getAgenciaManager().buscarPorId((long)838)); //Identificador de la agencia Excluciva
									ventaPasaje.setAgenciaLlegada(ServiceLocator.getAgenciaManager().buscarPorId((long)838)); //Identificador de la agencia Excluciva
								}
								ventaPasaje.setFechaPartida(Constantes.FORMAT_DATE.parse(lblFechaPartida.getValue().trim()));
								ventaPasaje.setHoraPartida(lblHoraPartida.getValue().trim());
								
//								criteriosBusqueda= new TreeMap<>();
//								criteriosBusqueda.put("localidad", ventaPasaje.getRuta().getLocalidadDestino());
//								criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//								List<Agencia>resultAgencia=ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, null);
//								ventaPasaje.setAgenciaLlegada(resultAgencia.get(0));
								
								ventaPasaje.setFechaLlegada(Constantes.FORMAT_DATE.parse(lblFechaLlegada.getValue()));
								ventaPasaje.setHoraLllegada(lblHoraLlegada.getValue());
								ventaPasaje.setSecuencial(0);
								ventaPasaje.setTarifa(dblTarifa.getValue());
								ventaPasaje.setRecargo(0.00);
								ventaPasaje.setDescuento(dblDescuento.getValue());
								ventaPasaje.setPenalidad(0.00);
								ventaPasaje.setAcuenta(0.00);
								ventaPasaje.setImportePagado(dblImporte.getValue());
								ventaPasaje.setImportePagadoEfectivo(0.00);
								ventaPasaje.setImportePagadoTarjeta(0.00);
								/*Calcula la fecha de caducidad del boleto*/
								String fechaCaducidad=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+" "+ventaPasaje.getHoraPartida();
								Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
								ventaPasaje.setFechaCaducidad(dateCaducidad);
								/* ***********************************************************/
								ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
								if(chkVentaRemota.isChecked()){
									ventaPasaje.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
									ventaPasaje.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
									ventaPasaje.setUsuarioRemoto((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
									ventaPasaje.setUsuarioHardware(usuarioHardwareRemoto);
									ventaPasaje.setUsuarioHardwareRemoto(usuarioHardwareRemoto);
									ventaPasaje.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
								}else{
									ventaPasaje.setAgencia(getAgencia());
									ventaPasaje.setUsuario(getUsuario());
									ventaPasaje.setUsuarioHardware(getUsuarioHardware());
									ventaPasaje.setCanalVenta(getUsuarioHardware().getCanalVenta());
								}
								ventaPasaje.setPreferenciaAlimentaria(new PreferenciaAlimentaria(1));
								ventaPasaje.setIdaRetorno(0);
								ventaPasaje.setEsFechaAbierta(0);
								ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
								ventaPasaje.setEstadoDocumento("PAG");
								ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA_POOL);//SOLO POOL ES CUATRO
								String observaciones="OPERADO:"+(asientoPool.getObjectCruzdelsur()!=null?Constantes.OPERADO_POR_CRUZ_DEL_SUR:Constantes.OPERADO_CIVA)+";"
											       + "SERVICIO:"+detalleItinerarioIda.getItinerario().getServicio()+";"
												   + "PTOEMB:"+cmbPtoEmbarque.getText().trim().toUpperCase()+";"
												   + "PTODES:"+cmbPtoDesembarque.getText().trim().toUpperCase()+";";
								ventaPasaje.setObservaciones(observaciones);
								UtilData.auditarRegistro(ventaPasaje, getUsuario(), Executions.getCurrent());
								
//								ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, false);
//								ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());

								//Valida si es Cruz del Sur
								if(asientoPool.getObjectCruzdelsur()!=null){
									/*Impacta la venta en nuestra base de datos*/
									ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, false,true);
									ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
									
									String llaveEmbarque=cmbPtoEmbarque.getSelectedItem().getValue().toString();
									String llaveDesembarque=cmbPtoDesembarque.getSelectedItem().getValue().toString();
									String codigoTransaccion=((AsientoPool)lbxAsientos.getSelectedItem().getValue()).getResultBloquearAsiento().getCodigoTransaccion().getValue();
									//Envia la venta
									List<Generic> listMenus= new ArrayList<>();
									if(cmbAlimen1.isVisible()){
										Generic generic=cmbAlimen1.getSelectedItem().getValue();
										listMenus.add(generic);
									}
									if(cmbAlimen2.isVisible()){
										Generic generic=cmbAlimen2.getSelectedItem().getValue();
										listMenus.add(generic);
									}
									if(cmbAlimen3.isVisible()){
										Generic generic=cmbAlimen3.getSelectedItem().getValue();
										listMenus.add(generic);
									}
									if(cmbAlimen4.isVisible()){
										Generic generic=cmbAlimen4.getSelectedItem().getValue();
										listMenus.add(generic);
									}
									ResultVenta resultVenta = WSCruzdelsur.enviarVenta(codigoTransaccion, llaveEmbarque, llaveDesembarque, oPasajero, listMenus,ventaPasaje.getNumeroBoleto());
									if(resultVenta.isIsCorrect()){
										/**Actualiza el numero de boleto generado por Cruz del sru**/
										ventaPasaje.setNumeroBoletoAnterior(resultVenta.getNumeroBoleto().getValue());
										VentaPasaje ventaPasajeUp=ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje.getId());
										ventaPasajeUp.setNumeroBoletoAnterior(resultVenta.getNumeroBoleto().getValue());
										ServiceLocator.getVentaPasajesManager().actualizar(ventaPasajeUp);
										
										List<VentaPasaje>listVentaPasaje= new ArrayList<>();
										listVentaPasaje.add(ventaPasaje);
										
										//Comentado temporalmente por MAOE para pruebasc con Transmar
										//WSFE.sendVenta(listVentaPasaje, wndVentaReserva, true,null);
										
										//Limpia los controles
										onCleanControlsPax();
										onCleanControlsClient();
										onCleanPagos();
										onCleanPartialListAsientosSeleccionados();
										onCleanInformacionVenta();
										tabPasajero.setSelected(true);
									}else{
										DlgMessage.information(resultVenta.getError().getValue().getMessage().getValue());
									}
								}else if (asientoPool.getObjectCiva()!=null){
									//si es Civa
									String idMenu=((Menu)cmbAlimentacion.getSelectedItem().getValue()).getId().toString();
									String boletoEnviado=ventaPasaje.getNumeroBoleto();
									String numeroPedido=RESTCiva.enviarVenta(ventaPasaje.getPasajero(), oCliente, asientoPool.getObjectCiva().getReservaID().longValue(),idMenu,boletoEnviado);
									if(numeroPedido!=null){
										/**Impacta la venta en nuestra base de datos*/
										ventaPasaje.setNumeroBoletoAnterior(numeroPedido);
										ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, false,true);
										ventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
										
										/**Valida si el numero de boleto enviado ha cambiado*/
										if(!(boletoEnviado.equals(ventaPasaje.getNumeroBoleto()))){
											String nuevoNumeroComprobante=ventaPasaje.getNumeroBoleto();
											RESTCiva.actualizarNumeroComprobante(numeroPedido, boletoEnviado, nuevoNumeroComprobante);
										}
										
										/**Envia el comprobante a sunat*/
										List<VentaPasaje>listVentaPasaje= new ArrayList<>();
										listVentaPasaje.add(ventaPasaje);
										WSFE.sendVenta(listVentaPasaje, wndVentaReserva, true,null);
										
										//Limpia los controles
										onCleanControlsPax();
										onCleanControlsClient();
										onCleanPagos();
										onCleanPartialListAsientosSeleccionados();
										onCleanInformacionVenta();
										tabPasajero.setSelected(true);
									}else{
										DlgMessage.information("No se pudo realizar la venta, por favor vuelva a intentar");
									}
								}
							}
						}catch(Exception ex ){
							ex.printStackTrace();
							DlgMessage.error(ex.getMessage());
						}
					}
				});
			}else{
				/*	Validando que el pasajero sea convertido a Paxfre */
				if(convertirPaxFre && getAgencia().getTipoAgencia().getId()==Constantes.ID_TIPAGE_TEPSA)
					throw new VentaReservaException(VentaReservaException.CONVERTIR_PASAJERO_PAXFRE);
				
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO 
						&& cmbCentroCosto.getItems().size()>1 
						&& !(cmbCentroCosto.getSelectedItem().getValue() instanceof CentroCosto))
					throw new CentroCostoException();
				
				if(cmbTipoOperacion.getSelectedItem().getValue().equals(Constantes.TIPO_OPERACION_RESERVA))
					guardarReserva();
				else
					onCrearObjetoVenta();
			}
		
		}catch(LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
		}catch (LineaCreditoClienteException lccex) {
			if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_SIN_SALDO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.noSaldoDisponible"));
			else if(lccex.getTipo().intValue()==LineaCreditoClienteException.LINEA_CREDITO_NO_ACTIVO)
				DlgMessage.information(Messages.getString("tblLineaCreditoCliente.information.lineaNoActiva"));
			liberarAsientos();
			closeTabWindow();
		}catch(VentaReservaException vrex){
			if(vrex.getTipo().intValue()==VentaReservaException.CONVERTIR_PASAJERO_PAXFRE){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fidelizarPasajero"));
				tabPasajero.setSelected(true);
			}
		}catch(CentroCostoException ccex){
			ccex.printStackTrace();
			DlgMessage.information("Debe de seleccionar el Centro de Costo para poder continuar con la venta.");
			tabCliente.setSelected(true);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	/**
	 * Realiza el proceso de guardado de la Reserva.
	 * En caso de haber mas de una reserva las realiza todos en un solo evento.
	 */
	@SuppressWarnings("deprecation")
	private void guardarReserva(){
		final List<VentaPasaje> lstReservas = new LinkedList<VentaPasaje>();
		
		VentaPasaje reserva = null;
		
		try{
			if(detailItinerary == null)
				throw new ItinerarioException(ItinerarioException.NO_SELECT); // ItinerarioNotSelectedException();
			else if(!(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); // ItinerarioAgenciaPartidaNullException();
			else if(!(cmbPtoDesembarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
				throw new  ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); //ItinerarioAgenciaLlegadaNullException();
			//Preferencias Alimentarias son opcionales, comentado por MAOE 26/06/2021
//			else if(!(cmbAlimentacion.getSelectedItem().getValue() instanceof PreferenciaAlimentaria))
//				throw new PreferenciaAlimentariaException();
			else if (!(tlbbtnGuardarPax.isDisabled()))// 06/09/2013 - jabanto
					throw new PasajeroNoSavedExeption();
			else if(oPasajero==null)
				throw new PasajeroException();
			else if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if(oPasajero.getIndeseable().intValue()==Constantes.TRUE_VALUE)
				throw new PasajeroIndeseableException();
			else if (oCliente!=null && (oCliente.getDireccion()==null || oCliente.getDireccion().trim().isEmpty())){
				throw new ClienteException(ClienteException.NO_DIRECCION);
			}
			//else if (dtbxExpiracionReserva.getValue().getTime()>)
			
//			if(chkVentaRemota.isChecked()){
//				Liquidacion liquidacion = ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(), 
//							((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.LIQUI_ESTA_ABIERTO);
//				if(liquidacion==null){
//					fechaLiquidacionRemota = null;
//					throw new LiquidacionNullException();
//				}else
//					fechaLiquidacionRemota = liquidacion.getFechaLiquidacion();
//			}
			
			//Valida el tiempo maximo permitido para realizar la reserva antes de la hora de partida del servicio.
			if(lbxAsientos.getItemCount()>0){
				DetalleItinerario detalleItinerario = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
				//Da formato a la fecha y hora de salida del servicio
				Date salidaServicio=new Date(detalleItinerario.getFechaPartida().getTime());
				salidaServicio.setHours(Integer.valueOf(detalleItinerario.getHoraPartida().split(":")[0]));
				salidaServicio.setMinutes(Integer.valueOf(detalleItinerario.getHoraPartida().split(":")[1]));
				salidaServicio.setSeconds(0);
				//Da formato a la fecha y hora de expiración seleccionda por el usuario.
				Date fechaHoraExpiracion=dtbxExpiracionReserva.getValue();
				//Obtiene la diferencia de la fecha hora de salida del servicio y el fecha y hora actual
				Integer diferencia= (int) ((salidaServicio.getTime()-fechaHoraExpiracion.getTime())/Constantes.MILISEGUNDOS_X_MINUTO);
				//Realiza la validacion
				if(diferencia.intValue()<Constantes.TIEMPO_MAXIMO_PERMITE_RESERVA)
					throw new FechaExpedicionNullException(FechaExpedicionNullException.FECHA_EXPIRACION_MAYOR);
				
				//Valida que la fecha y/o hora de expiracion de la reserva no se menor a la hora actual.
				Date fechaHoraActual=new Date();
				if(fechaHoraExpiracion.getTime()<fechaHoraActual.getTime())
					throw new FechaExpedicionNullException(FechaExpedicionNullException.FECHA_EXPIRACION_MENOR);
				
			}
			
			
			for(int i=0; i<lbxAsientos.getItemCount(); i++){
				reserva = new VentaPasaje();
				lbxAsientos.setSelectedIndex(i);
				DetalleItinerario detalleItinerario = null;
				detalleItinerario = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
				lblNroAsiento.setValue(detalleItinerario.getAsiento());
				
				reserva.setItinerario(detalleItinerario.getItinerario());
				reserva.setRuta(detalleItinerario.getRuta());
				reserva.setCliente(oCliente);
				reserva.setPasajero(oPasajero);
				reserva.setServicio(detalleItinerario.getItinerario().getServicio());
				TipoComprobante tipoComprobante = (TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue();
				reserva.setTipoComprobante(tipoComprobante);
				reserva.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_RESERVA));
				reserva.setNumeroBoleto(null);
				
				reserva.setNumeroAsiento(Integer.valueOf(lblNroAsiento.getValue()));
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue();
				reserva.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				reserva.setFechaPartida(detalleItinerario.getFechaPartida());
				reserva.setHoraPartida(detalleItinerario.getHoraPartida());
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)cmbPtoDesembarque.getSelectedItem().getValue();
				reserva.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
				reserva.setFechaLlegada(detalleItinerario.getFechaLlegada());
				reserva.setHoraLllegada(detalleItinerario.getHoraLlegada());
				PreferenciaAlimentaria preferenciaAlimentaria = (PreferenciaAlimentaria)cmbAlimentacion.getSelectedItem().getValue();
				reserva.setPreferenciaAlimentaria(preferenciaAlimentaria);
				reserva.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
				
				reserva.setSecuencial(0);
				reserva.setTarifa(dblTarifa.getValue());
				reserva.setRecargo(0.0);
				reserva.setDescuento(0.0);
				reserva.setPenalidad(0.0);
				reserva.setAcuenta(0.0);
				reserva.setImportePagado(0.0);
				reserva.setImportePagadoByDiferencia(0.00);
				reserva.setImportePagadoEfectivo(0.0);
				reserva.setImportePagadoTarjeta(0.0);
				reserva.setTipoTransaccion(cmbTipoOperacion.getSelectedItem().getValue().toString());
//				reserva.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO*Constantes.MILISEGUNDOS_X_DIA)));
				if(chkVentaRemota.isChecked()){
					reserva.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
					reserva.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
					reserva.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
//					reserva.setFechaLiquidacion(fechaLiquidacionRemota);
					reserva.setUsuarioHardware(usuarioHardwareRemoto);
					reserva.setUsuarioRemoto((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
					reserva.setUsuarioHardwareRemoto(usuarioHardwareRemoto);
//					reserva.setUsuarioRemoto(getUsuario());
//					reserva.setUsuarioHardwareRemoto(getUsuarioHardware());
					reserva.setEsRemoto(true);
				}else{
					reserva.setAgencia(agencia);
					reserva.setUsuario(usuario);
//					reserva.setFechaLiquidacion(fechaLiquidacion);
					reserva.setCanalVenta(canalVenta);
					reserva.setUsuarioHardware(new UsuarioHardware(usuhar));
					reserva.setEsRemoto(false);
				}			
				
				reserva.setFechaLiquidacion(fechaLiquidacion);
				reserva.setNumeroOperacionBancaria(null);
				reserva.setLiquidacion(null);
				reserva.setEsFechaAbierta(Constantes.FALSE_VALUE);
				reserva.setObservaciones(txtObservacionesIda.getText().trim().isEmpty()?null:txtObservacionesIda.getText().trim());
				
				/*	Verificando si se trata de una venta a IDA y VUELTA	*/
				if(rdVentaIdaVuelta.isSelected())
					reserva.setIdaRetorno(Constantes.TRUE_VALUE);
				else
					reserva.setIdaRetorno(Constantes.FALSE_VALUE);
				
//				String datetime = Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT)+" "+detalleItinerario.getHoraPartida()+":00";
//				Date expiracion = Util.StringtoDate(datetime, Constantes.DATE_TIME_FORMAT);<
//				expiracion = new Date(expiracion.getTime()-(Constantes.TIEMPO_EXPIRA_RESERVA*Constantes.MILISEGUNDOS_X_HORA));
				
				reserva.setFechaExpiracionReserva(Util.StringtoDate(Util.DatetoString(dtbxExpiracionReserva.getValue(), Constantes.DATE_TIME_FORMAT), Constantes.DATE_FORMAT));
				reserva.setHoraExpiracionReserva(Util.DatetoString(dtbxExpiracionReserva.getValue(), Constantes.TIME_FORMAT));
				
				String fechaCaducidad=Constantes.FORMAT_DATE.format(reserva.getFechaExpiracionReserva())+ " "+reserva.getHoraExpiracionReserva();
				Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
				reserva.setFechaCaducidad(dateCaducidad);
				
				reserva.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				/*	*********************************************************************************************************	*/
				reserva.setNumeroControl("DEMO");
				/*	*********************************************************************************************************	*/
				UtilData.auditarRegistro(reserva, false, usuario, Executions.getCurrent());
				
				lstReservas.add(reserva);
			}
			
			Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarReserva"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							int result = Constantes.FAILURE;
							for(VentaPasaje reserva : lstReservas){
								result = ServiceLocator.getVentaPasajesManager().guardarVenta(reserva, rdVentaFechaAbierta.isSelected(), true, true,true);								
							}
							
							result = Constantes.CORRECT;
							if(result == Constantes.CORRECT){
									
								DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarReserva"));
								onCleanControlsPax();
								onCleanControlsClient();
								onCleanPagos();
								onCleanPartialListAsientosSeleccionados();
								onCleanInformacionVenta();								
							}
						}
					}catch(CapacityExceedsException ceex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
					}catch(DuplicateSeatException dsex){
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());ex.printStackTrace();
					}
				}
			});
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.NO_SELECT)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
			else if(i.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPtoDesembarque);
			else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPtoEmbarque);
//		}catch(PreferenciaAlimentariaException panex){
//			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPreferenciaAlimentaria"), cmbAlimentacion);
		}catch(PasajeroException pnex){
			tabPasajero.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajero"), txtDocumentoPax);
		}catch(TipoComprobanteNullException tcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"), cmbTipoComprobante);
		}catch(PasajeroIndeseableException piex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.pasajeroIndeseable"));
		}catch (PasajeroNoSavedExeption pns){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSavedPax"));
			tabPasajero.setSelected(true);
		}catch(LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneLiquidacionAbierta"));
		}catch (FechaExpedicionNullException fex) {
			if(fex.getTipo()==FechaExpedicionNullException.FECHA_EXPIRACION_MAYOR){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaExiraReservaMayor")+" "+Constantes.TIEMPO_MAXIMO_PERMITE_RESERVA+" minutos antes de la hora de Salida del Servicio.");
			}else if(fex.getTipo()==FechaExpedicionNullException.FECHA_EXPIRACION_MENOR){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaExiraReservaMenorFechaActual"));
			}
		}catch (ClienteException cex){
			if(cex.getTipo().intValue()==ClienteException.NO_DIRECCION){
				tabCliente.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"),txtDireccionCliente);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	private void calcularFechaExpiracion(Date fechaPartida, String horaPartida){
		String datetime = Util.DatetoString(fechaPartida, Constantes.DATE_FORMAT)+" "+horaPartida+":00";
		Date expiracion = Util.StringtoDate(datetime, Constantes.DATE_TIME_FORMAT);
		expiracion = new Date(expiracion.getTime()-(Constantes.TIEMPO_EXPIRA_RESERVA*Constantes.MILISEGUNDOS_X_HORA));
		dtbxExpiracionReserva.setValue(expiracion);
	}
	
	public VentaPasaje onCrearObjetoVenta(){
		try{
			imgRefreshBoleto.setVisible(false);
			ventaPasaje = null;
			
			if(!rdVentaFechaAbierta.isSelected()){
				if(detailItinerary == null)
					throw new ItinerarioException(ItinerarioException.NO_SELECT); // ItinerarioNotSelectedException();
				else if(!(cmbPtoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
					throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL); // ItinerarioAgenciaPartidaNullException();
				else if(!(cmbPtoDesembarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
					throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL); //ItinerarioAgenciaLlegadaNullException();
				//Comentado por MAOE 26/06/2021
//				else if(!(cmbAlimentacion.getSelectedItem().getValue() instanceof PreferenciaAlimentaria))
//					throw new PreferenciaAlimentariaException(PreferenciaAlimentariaException.ALIMENTACION_IDA_NULL);			
				/*	EN CASO SE TRATE DE UNA VENTA IDA Y VUELTA	*/
				else if(rdVentaIdaVuelta.isChecked() && !(cmbPtoEmbarqueRetorno.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
					throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_RETORNO_NULL); // ItinerarioAgenciaPartidaNullException();
				else if(rdVentaIdaVuelta.isChecked() && !(cmbPtoDesembarqueRetorno.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
					throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_RETORNO_NULL); //ItinerarioAgenciaLlegadaNullException();
	//			else if(txtNumeroBoleto.getText().trim().equals("") && ((String)cmbTipoOperacion.getSelectedItem().getValue()).equals(Constantes.TIPO_OPERACION_VENTA))
	//				throw new NumeroBoletoNullException();
				//COmentado por MAOE 26/06/2021
//				else if(rdVentaIdaVuelta.isChecked() && !(cmbAlimentacionRetorno.getSelectedItem().getValue() instanceof PreferenciaAlimentaria))
//					throw new PreferenciaAlimentariaException(PreferenciaAlimentariaException.ALIMENTACION_RETORNO_NULL);
			}
			
			if(txtNumeroBoleto.getText().trim().equals("") && ((String)cmbTipoOperacion.getSelectedItem().getValue()).equals(Constantes.TIPO_OPERACION_VENTA))
				throw new NumeroBoletoNullException();
			else if (!(tlbbtnGuardarPax.isDisabled()))// 06/09/2013 - jabanto
				throw new PasajeroNoSavedExeption();
			else if(oPasajero==null)
				throw new PasajeroException();
			else if(oPasajero!=null && oPasajero.getId()==null)
				throw new PasajeroException();
			else if(!(cmbTipoComprobante.getSelectedItem().getValue() instanceof TipoComprobante))
				throw new TipoComprobanteNullException();
			else if(!(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago))
				throw new FormaPagoNullException();
			else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
				throw new TipoFormaPagoNullException();
			else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TARJETA)){
				if(!(cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito))
					throw new OperadorTarjetaCreditoNullException();
				else if(!(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
					throw new TarjetaCreditoNullException();
			}else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_TRANSFERENCIA)){
				if(txtOperacionBancaria.getText().trim().equals(""))
					throw new NumeroOperacionBancariaNullException();
			}else if(oPasajero.getIndeseable().intValue()==Constantes.TRUE_VALUE)
				throw new PasajeroIndeseableException();
			else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago 
					&& cmbTipoFormaPago.getText().equals(TipoFormaPago.TIPO_CREDITO)){
				if((cmbOperadorTarjetaCredito.getItems().size()>0 && cmbOperadorTarjetaCredito.getSelectedItem().getValue() instanceof OperadorTarjetaCredito) && !(cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito))
					throw new TarjetaCreditoNullException();
			}else if(dblTarifa.getValue()<=0.0)
				throw new VentaReservaException(VentaReservaException.TARIFA_IDA_CERO);
			else if(rdVentaIdaVuelta.isChecked() && dblTarifaRetorno.getValue()<=0.0)
				throw new VentaReservaException(VentaReservaException.TARIFA_RETORNO_CERO);
			else if (!(cmbTipoMoneda.getSelectedItem().getValue() instanceof TipoMoneda))
				throw new VentaReservaException(VentaReservaException.NO_SELECTION_TIPO_MONEDA);
			else if (oCliente!=null && (oCliente.getDireccion()==null || oCliente.getDireccion().trim().isEmpty())){
				throw new ClienteException(ClienteException.NO_DIRECCION);
			}
			
			/*Reliza la validacion del ingreso del email - 03/05/2017*/
			//Comentado por MAOE 19/07/2019
//			if(tlbbtnModificarPax.isVisible()){
//				if(txtEmailPax.getText().trim().isEmpty() && !(chbxNoTieneMail.isChecked()))
//					throw new EmailNullException();
//			}
			
			
//			if(chkVentaRemota.isChecked()){
//				Liquidacion liquidacion = ServiceLocator.getLiquidacionManager().buscarUltimaLiquidacion(((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId(), 
//							((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue()).getId(), Constantes.LIQUI_ESTA_ABIERTO);
//				if(liquidacion==null){
//					fechaLiquidacionRemota = fechaLiquidacion;
////					throw new LiquidacionNullException();
//				}else
//					fechaLiquidacionRemota = liquidacion.getFechaLiquidacion();
//			}
			
			//validacion para cuando es un boleto de biaje - 13/03/2017 - jabanto
			if(((TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPCOM_BOLETO_VIAJE){
				if(txtNumeroBoleto.getText().trim().indexOf("-")!=3){
					throw new Exception("El formato del Número de Boleto no es válido");
				}
			}			
			
			ventaPasaje = new VentaPasaje();
			
			DetalleItinerario detalleItinerario = null;
			if(rdVentaFechaAbierta.isSelected()){
				detalleItinerario = detalleItinerarioFechaAbierta;
				detalleItinerario.getItinerario().setId(new Long(1));
				ventaPasaje.setEsFechaAbierta(Constantes.TRUE_VALUE);
				ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
			}else if(rdVentaIdaVuelta.isSelected()){
				VentaIdaRetorno ventaIdaRetorno = (VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue();
				detalleItinerario = ventaIdaRetorno.getDetalleItinerarioIDA();
				ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);
				ventaPasaje.setIdaRetorno(Constantes.TRUE_VALUE);
			}else{
				detalleItinerario = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
				ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);
				ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
			}

			if(chkPagoMixto.isChecked()){
				if(dblImporteEfectivo.getValue()<=0.0 || dblImporteTarjeta.getValue()<=0.0)
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_CERO);
			}
//			ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
			ventaPasaje.setItinerario(detalleItinerario.getItinerario());
			ventaPasaje.setRuta(detalleItinerario.getRuta());
			ventaPasaje.setCliente(oCliente);
			ventaPasaje.setPasajero(oPasajero);
			FormaPago formaPago = (FormaPago)cmbFormaPago.getSelectedItem().getValue();
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setServicio(detalleItinerario.getItinerario().getServicio());
			TipoComprobante tipoComprobante = (TipoComprobante)cmbTipoComprobante.getSelectedItem().getValue();
			ventaPasaje.setTipoComprobante(tipoComprobante);
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue();
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			/*	Si ha seleccionado algun tipo de Tarjeta de Credito	*/
			if(cmbTarjetaCredito.getSelectedItem()!=null && cmbTarjetaCredito.getSelectedItem().getValue() instanceof TarjetaCredito){
				TarjetaCredito tarjetaCredito = (TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue();
				ventaPasaje.setTarjetaCredito(tarjetaCredito);
			}			
			ventaPasaje.setNumeroBoleto(txtNumeroBoleto.getText().equals("")?null:txtNumeroBoleto.getText());			
			/*	Realizar si no es Fecha Abierta	*/
			if(!rdVentaFechaAbierta.isSelected()){
				if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA)
					ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CREDITO));
				else
					ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
				ventaPasaje.setNumeroAsiento(Integer.valueOf(lblNroAsiento.getValue()));
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbPtoEmbarque.getSelectedItem().getValue();
				ventaPasaje.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				ventaPasaje.setFechaPartida(detalleItinerario.getFechaPartida());
				ventaPasaje.setHoraPartida(detalleItinerario.getHoraPartida());
				ventaPasaje.setHoraEmbarque(lblHoraPartida.getValue());
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)cmbPtoDesembarque.getSelectedItem().getValue();
				ventaPasaje.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
				ventaPasaje.setFechaLlegada(detalleItinerario.getFechaLlegada());
				ventaPasaje.setHoraLllegada(detalleItinerario.getHoraLlegada());
				ventaPasaje.setHoraDesembarque(lblHoraLlegada.getValue());
				PreferenciaAlimentaria preferenciaAlimentaria = (PreferenciaAlimentaria)cmbAlimentacion.getSelectedItem().getValue();
				ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
				ventaPasaje.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
			}else
				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_FECHA_ABIERTA));
			
			ventaPasaje.setSecuencial(0);

//			TipoMoneda tipoMoneda=cmbTipoMoneda.getSelectedItem().getValue();
			
			ventaPasaje.setTarifa(dblTarifa.getValue());
			ventaPasaje.setRecargo(dblRecargo.getValue());
			ventaPasaje.setDescuento(dblDescuento.getValue());
			ventaPasaje.setImportePagado(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
			
			/*19/08/2015 - jabanto*/
			/*Si la agencia tiene configurado otra moneda diferente a las Soles*/
			if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
				/*Otra moneda*/
//				ventaPasaje.setTarifa(pagoSoles.getTarifa());
//				ventaPasaje.setRecargo(pagoSoles.getRecargo());
//				ventaPasaje.setDescuento(pagoSoles.getDescuento());
//				ventaPasaje.setImportePagado(pagoSoles.getTarifa()+pagoSoles.getRecargo()-pagoSoles.getDescuento());
								
				ventaPasaje.setTipoCambio(tipoCambio.getTipoCambio());
				ventaPasaje.setTipoMoneda(tipoMonedaAgencia);
				ventaPasaje.setImportePagadoEquibalente(dblTarifaOtraMoneda.getValue()-dblDescuentoOtraMoneda.getValue());
				ventaPasaje.setDescuentoEquibalente(dblDescuentoOtraMoneda.getValue());
				ventaPasaje.setTarifaEquibalente(dblTarifaOtraMoneda.getValue());
			}
			
			ventaPasaje.setPenalidad(0.0);
			ventaPasaje.setAcuenta(0.0);
			ventaPasaje.setImportePagadoByDiferencia(0.00);
			ventaPasaje.setImportePagadoEfectivo(dblImporteEfectivo.getValue());
			ventaPasaje.setImportePagadoTarjeta(dblImporteTarjeta.getValue());
			ventaPasaje.setTipoTransaccion(cmbTipoOperacion.getSelectedItem().getValue().toString());
			/*Begin 14/11/2016 - jabanto*/
			if(!rdVentaFechaAbierta.isSelected()){
				String fechaCaducidad=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+ " "+ventaPasaje.getHoraPartida();
				Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
				ventaPasaje.setFechaCaducidad(dateCaducidad);	
			}else{
				ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
			}
			
//			ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
			if(chkVentaRemota.isChecked()){
				ventaPasaje.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
				ventaPasaje.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
				ventaPasaje.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
				ventaPasaje.setUsuarioHardware(usuarioHardwareRemoto);
				ventaPasaje.setUsuarioRemoto((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
				ventaPasaje.setUsuarioHardwareRemoto(usuarioHardwareRemoto);
//				ventaPasaje.setFechaLiquidacion(fechaLiquidacionRemota);
//				ventaPasaje.setUsuarioRemoto(getUsuario());
//				ventaPasaje.setUsuarioHardwareRemoto(getUsuarioHardware());
				ventaPasaje.setEsRemoto(true);
			}else{
				ventaPasaje.setAgencia(agencia);
				ventaPasaje.setUsuario(usuario);
				ventaPasaje.setCanalVenta(canalVenta);
//				ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
				ventaPasaje.setUsuarioHardware(new UsuarioHardware(usuhar));
				ventaPasaje.setEsRemoto(false);
			}
			ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
			ventaPasaje.setNumeroOperacionBancaria(txtOperacionBancaria.getText().equals("")?null:txtOperacionBancaria.getText());
			ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			ventaPasaje.setLiquidacion(null);
			ventaPasaje.setPromocion(txtIdPromocion.getText().trim().isEmpty()?null:new Promocion(Long.valueOf(txtIdPromocion.getText().trim())));
			ventaPasaje.setObservaciones(txtObservacionesIda.getText().trim().isEmpty()?null:txtObservacionesIda.getText().trim().toUpperCase());
			
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO 
					&& cmbCentroCosto.getItems().size()>0 
					&& cmbCentroCosto.getSelectedItem().getValue() instanceof CentroCosto)
				ventaPasaje.setCentroCosto((CentroCosto)cmbCentroCosto.getSelectedItem().getValue());
			
			/*	Validando la forma de pago para las fechas abiertas	*/
			if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO && rdVentaFechaAbierta.isSelected())
				throw new OpenDateOnCreditNotAllowedException();
			
			/*	Obteniendo el RUC del concesionario en caso no sea una agencia TEPSA	*/
			if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA){
				Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(Long.valueOf(this.agencia.getId()));
				ventaPasaje.setRucClienteCredito(agencia.getConcesionario().getRuc());
			}
						
			//Coloca en el campo estado documento PAG a todos los boleto o RC que no sen credito. - jabanto 16/10/2014
			if(ventaPasaje.getFormaPago()!=null){
				if(ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CREDITO)
					ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_ACTIVO);
				else
					ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
			}
									
						
			/*	Numero de Control inicial	*/
			ventaPasaje.setNumeroControl("T00000");
			UtilData.auditarRegistro(ventaPasaje, false, usuario, Executions.getCurrent());
			/*	Clonamos la venta de IDA	*/
			ventaIDA = (VentaPasaje)ventaPasaje.clone();
			
			/*	************************************************************************************************************************************************	*/
			/*	**************************************			CREANDO EL OBJETO VENTA PARA EL RETORNO					****************************************	*/
			/*	*************************************************************************************************************************************************	*/
			if(rdVentaIdaVuelta.isSelected()){
				VentaIdaRetorno ventaIdaRetorno = (VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue();
				ventaPasaje.setItinerario(ventaIdaRetorno.getDetalleItinerarioRETORNO().getItinerario());
				ventaPasaje.setRuta(ventaIdaRetorno.getDetalleItinerarioRETORNO().getRuta());
				ventaPasaje.setServicio(ventaIdaRetorno.getDetalleItinerarioRETORNO().getItinerario().getServicio());
				ventaPasaje.setNumeroBoleto(txtNumeroBoletoRetorno.getText().equals("")?null:txtNumeroBoletoRetorno.getText());			
				ventaPasaje.setNumeroAsiento(Integer.valueOf(lblNroAsientoRetorno.getValue()));
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida)cmbPtoEmbarqueRetorno.getSelectedItem().getValue();
				ventaPasaje.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				ventaPasaje.setFechaPartida(ventaIdaRetorno.getDetalleItinerarioRETORNO().getFechaPartida());
				ventaPasaje.setHoraPartida(ventaIdaRetorno.getDetalleItinerarioRETORNO().getHoraPartida());
				ventaPasaje.setHoraEmbarque(lblHoraPartidaRetorno.getValue());
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada)cmbPtoDesembarqueRetorno.getSelectedItem().getValue();
				ventaPasaje.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
				ventaPasaje.setFechaLlegada(ventaIdaRetorno.getDetalleItinerarioRETORNO().getFechaLlegada());
				ventaPasaje.setHoraLllegada(ventaIdaRetorno.getDetalleItinerarioRETORNO().getHoraLlegada());
				ventaPasaje.setHoraDesembarque(lblHoraLlegadaRetorno.getValue());
				PreferenciaAlimentaria preferenciaAlimentaria = (PreferenciaAlimentaria)cmbAlimentacionRetorno.getSelectedItem().getValue();
				ventaPasaje.setPreferenciaAlimentaria(preferenciaAlimentaria);
				ventaPasaje.setNumeroPiso(Integer.valueOf(ventaIdaRetorno.getDetalleItinerarioRETORNO().getPiso()));
				ventaPasaje.setSecuencial(0);
				
//				ventaPasaje.setTarifa(dblTarifaRetorno.getValue());
//				ventaPasaje.setRecargo(dblRecargo.getValue());
//				ventaPasaje.setDescuento(dblDescuentoRetorno.getValue()==null?0.0:dblDescuentoRetorno.getValue());
//				ventaPasaje.setImportePagado(dblTarifaRetorno.getValue()+dblRecargo.getValue()-(dblDescuentoRetorno.getValue()==null?0.0:dblDescuentoRetorno.getValue()));
				
				ventaPasaje.setTarifa(dblTarifaRetorno.getValue());
				ventaPasaje.setRecargo(dblRecargo.getValue());
				ventaPasaje.setDescuento(dblDescuentoRetorno.getValue()==null?0.0:dblDescuentoRetorno.getValue());
				ventaPasaje.setImportePagado(dblTarifaRetorno.getValue()+dblRecargo.getValue()-(dblDescuentoRetorno.getValue()==null?0.0:dblDescuentoRetorno.getValue()));
					
				/*19/08/2015 - jabanto*/
				/*Si la agencia tiene configurado otra moneda diferente a las Soles*/
				if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
					/*Otro tipo de moneda*/
//					ventaPasaje.setTarifa(pagoSoles.getTarifaRetorno());
//					ventaPasaje.setRecargo(pagoSoles.getRecargo());
//					ventaPasaje.setDescuento(pagoSoles.getDescuentoRetorno());
//					ventaPasaje.setImportePagado(pagoSoles.getTarifaRetorno()+pagoSoles.getRecargo()-pagoSoles.getDescuentoRetorno());
					
					ventaPasaje.setTipoMoneda(tipoMonedaAgencia);
					ventaPasaje.setTipoCambio(tipoCambio.getTipoCambio());
					ventaPasaje.setImportePagadoEquibalente(dblTarifaRetornoOtraMoneda.getValue()-(dblDescuentoRetornoOtraMoneda.getValue()==null?0.0:dblDescuentoRetornoOtraMoneda.getValue()));
					ventaPasaje.setDescuentoEquibalente(dblDescuentoRetornoOtraMoneda.getValue());
					ventaPasaje.setTarifaEquibalente(dblTarifaRetornoOtraMoneda.getValue());
				}
				
				ventaPasaje.setPenalidad(0.0);
				ventaPasaje.setAcuenta(0.0);
				ventaPasaje.setImportePagadoByDiferencia(0.00);
				ventaPasaje.setImportePagadoEfectivo(dblImporteEfectivo.getValue());
				ventaPasaje.setImportePagadoTarjeta(dblImporteTarjeta.getValue());
				ventaPasaje.setTipoTransaccion(cmbTipoOperacion.getSelectedItem().getValue().toString());
//				ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
				
				/*Begin 14/11/2016 - jabanto*/
				if(!rdVentaFechaAbierta.isSelected()){
					String fechaCaducidad=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+ " "+ventaPasaje.getHoraPartida();
					Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
					ventaPasaje.setFechaCaducidad(dateCaducidad);	
				}else{
					ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
				}
				if(chkVentaRemota.isChecked()){
					ventaPasaje.setAgencia((Agencia)cmbAgenciaRemota.getSelectedItem().getValue());
					ventaPasaje.setUsuario((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
					ventaPasaje.setCanalVenta(usuarioHardwareRemoto.getCanalVenta());
					ventaPasaje.setUsuarioRemoto((Usuario)cmbUsuarioRemoto.getSelectedItem().getValue());
					ventaPasaje.setUsuarioHardwareRemoto(usuarioHardwareRemoto);
					ventaPasaje.setUsuarioHardware(usuarioHardwareRemoto);
//					ventaPasaje.setFechaLiquidacion(fechaLiquidacionRemota);										
//					ventaPasaje.setUsuarioRemoto(getUsuario());
//					ventaPasaje.setUsuarioHardwareRemoto(getUsuarioHardware());
					ventaPasaje.setEsRemoto(true);
				}else{
					ventaPasaje.setAgencia(agencia);
					ventaPasaje.setUsuario(usuario);
					ventaPasaje.setCanalVenta(canalVenta);
//					ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
					ventaPasaje.setUsuarioHardware(new UsuarioHardware(usuhar));
					ventaPasaje.setEsRemoto(false);
				}
				ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
				ventaPasaje.setNumeroOperacionBancaria(txtOperacionBancaria.getText().equals("")?null:txtOperacionBancaria.getText());
				ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				ventaPasaje.setLiquidacion(null);
				ventaPasaje.setPromocion(txtIdPromocion.getText().trim().isEmpty()?null:new Promocion(Long.valueOf(txtIdPromocion.getText().trim())));
				ventaPasaje.setObservaciones(txtObservacionesRetorno.getText().trim().isEmpty()?null:txtObservacionesRetorno.getText().trim().toUpperCase());
				
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO 
						&& cmbCentroCosto.getItems().size()>0 
						&& cmbCentroCosto.getSelectedItem().getValue() instanceof CentroCosto)
					ventaPasaje.setCentroCosto((CentroCosto)cmbCentroCosto.getSelectedItem().getValue());
				
				/*	Numero de Control inicial	*/
				ventaPasaje.setNumeroControl("T00000");
			}
			
			/*	Clonamos la venta de IDA	*/
			ventaRETORNO = (VentaPasaje)ventaPasaje.clone();
			
			/*	Validando que el monto en efectivo + el monto en tarjeta sumen el importe pagado	*/
			if(chkPagoMixto.isChecked()){
				if(dblImporte.getValue().doubleValue()!=(dblImporteEfectivo.getValue().doubleValue()+dblImporteTarjeta.getValue().doubleValue()))
					throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS);
				if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()!=Constantes.ID_TIPFORPAG_TARJETA)
						throw new ImporteMixtoNullException(ImporteMixtoNullException.IMPORTE_MIXTO_NOT_SELECT_CARD);
			}
			
			if(rdVentaIdaVuelta.isSelected()){
				final List<VentaPasaje> lstVentas = new ArrayList<VentaPasaje>();
				/*	Calculando el descuento del boleto de retorno	*/
				promocionAplicada = null;
				if(ventaPasaje.getPromocion()!=null){
					AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(false);
					promocionAplicada = aplicarPromocion.executePromocion(ventaPasaje.getPromocion().getId().toString(), false);
					if(promocionAplicada==null){
						dblDescuentoRetorno.setValue(0.0);
						Messagebox.show(Messages.getString("WndVentaReserva.question.promocionNoAplicableAlRetorno"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
							@Override
							public void onEvent(Event e){
								if(e.getName().equals("onYes")){
									return;
								}else{
//									/*Soles*/
//									if(((TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPMON_SOLES){
									ventaRETORNO.setDescuento(dblDescuentoRetorno.getValue());
									ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-ventaRETORNO.getDescuento());
//									}else{
										
									/*Otra moneda*/
									if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
//										ventaRETORNO.setDescuento(pagoSoles.getDescuentoRetorno());
//										ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-pagoSoles.getDescuentoRetorno());
										ventaRETORNO.setImportePagadoEquibalente(ventaRETORNO.getTarifaEquibalente()-(dblDescuentoRetornoOtraMoneda.getValue()!=null?dblDescuentoRetornoOtraMoneda.getValue():.00));
										ventaRETORNO.setDescuentoEquibalente(dblDescuentoRetornoOtraMoneda.getValue());
									}
									
									lstVentas.add(ventaIDA);
									lstVentas.add(ventaRETORNO);
									guardarVentaIdaVuelta(lstVentas);								
								}
							}
						});
					}else{
						ventaRETORNO.setDescuento(dblDescuentoRetorno.getValue());
						ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-ventaRETORNO.getDescuento());
							
						/*Otra moneda*/
						if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
							/*Otra moneda*/
//							ventaRETORNO.setDescuento(pagoSoles.getDescuentoRetorno());
//							ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-pagoSoles.getDescuentoRetorno());
							ventaRETORNO.setImportePagadoEquibalente(ventaRETORNO.getTarifaEquibalente()-(dblDescuentoRetornoOtraMoneda.getValue()!=null?dblDescuentoRetornoOtraMoneda.getValue():.00));
							ventaRETORNO.setDescuentoEquibalente(dblDescuentoRetornoOtraMoneda.getValue());
						}
						lstVentas.add(ventaIDA);
						lstVentas.add(ventaRETORNO);
						guardarVentaIdaVuelta(lstVentas);
					}
				}else{
					ventaRETORNO.setDescuento(dblDescuentoRetorno.getValue());
					ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-ventaRETORNO.getDescuento());

					/*Otra moneda*/
					if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
//						ventaRETORNO.setDescuento(pagoSoles.getDescuentoRetorno());
//						ventaRETORNO.setImportePagado(ventaRETORNO.getTarifa()+ventaRETORNO.getRecargo()-pagoSoles.getDescuentoRetorno());
						ventaRETORNO.setImportePagadoEquibalente(ventaRETORNO.getTarifaEquibalente()-(dblDescuentoRetornoOtraMoneda.getValue()!=null?dblDescuentoRetornoOtraMoneda.getValue():.00));
						ventaRETORNO.setDescuentoEquibalente(dblDescuentoRetornoOtraMoneda.getValue());
					}
					
					lstVentas.add(ventaIDA);
					lstVentas.add(ventaRETORNO);
					guardarVentaIdaVuelta(lstVentas);
				}
			}else{
				guardarVenta(ventaIDA);
			}
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.NO_SELECT)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
			else if(i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL){
				tabInformacionVentaIda.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPtoEmbarque);
			}else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL){
				tabInformacionVentaIda.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPtoDesembarque);
			}else if(i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_RETORNO_NULL){
				tabInformacionVentaRetorno.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoAgenciaPartidaRetorno"), cmbPtoEmbarqueRetorno);
			}else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_RETORNO_NULL){
				tabInformacionVentaRetorno.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoAgenciaLlegadaRetorno"), cmbPtoDesembarqueRetorno);
			}
		//Comentado por MAOE 26/06/2021
//		}catch(PreferenciaAlimentariaException panex){
//			if(panex.getTipo().intValue()==PreferenciaAlimentariaException.ALIMENTACION_IDA_NULL)
//				DlgMessage.information(Messages.getString("WndVentaReserva.information.noPreferenciaAlimentaria"), cmbAlimentacion);
//			else
//				DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoPreferenciaAlimentariaRetorno"), cmbAlimentacionRetorno);
		}catch(NumeroBoletoNullException nbnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));			
		}catch(PasajeroException pnex){
			tabPasajero.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajero"), txtDocumentoPax);
		}catch(TipoComprobanteNullException tcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoComprobante"), cmbTipoComprobante);
		}catch(FormaPagoNullException fpnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noFormaPago"), cmbFormaPago);
		}catch(TipoFormaPagoNullException tfpnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoFormaPago"), cmbTipoFormaPago);
		}catch(OperadorTarjetaCreditoNullException otcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbOperadorTarjetaCredito);
		}catch(TarjetaCreditoNullException tcnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbTarjetaCredito);
		}catch(NumeroOperacionBancariaNullException nobnex){
			tabPagos.setSelected(true);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"), txtOperacionBancaria);
		}catch(PasajeroIndeseableException piex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.pasajeroIndeseable"));
		}catch(OpenDateOnCreditNotAllowedException odcaex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.openDateOnCreditNotAllowed"));
		}catch(ImporteMixtoNullException imnex){
			if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoCero"), dblImporteEfectivo);
			else if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_NOT_EQUALS)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNotEquals"), dblImporteEfectivo);
			else if(imnex.getTipoError().intValue()==ImporteMixtoNullException.IMPORTE_MIXTO_NOT_SELECT_EFECTIVO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNoSeleccionoEfectivo"));
			else
				DlgMessage.information(Messages.getString("WndVentaReserva.information.importeMixtoNoSeleccionoTarjeta"));
		}catch (PasajeroNoSavedExeption pnsex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSavedPax"));
			tabPasajero.setSelected(true);
		}catch(LiquidacionNullException lnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneLiquidacionAbierta"));
		}catch(VentaReservaException vrex){
			if(vrex.getTipo()==VentaReservaException.TARIFA_IDA_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneTarifaIda"));
			else if(vrex.getTipo()==VentaReservaException.TARIFA_RETORNO_CERO)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTieneTarifaRetorno"));
			else if (vrex.getTipo()==VentaReservaException.NO_SELECTION_TIPO_MONEDA)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectTipoMoneda"));
		}catch (ClienteException cex){
			if(cex.getTipo().intValue()==ClienteException.NO_DIRECCION){
				tabCliente.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"),txtDireccionCliente);
			}
		} catch(EmailNullException enex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noIngresoEmail"), txtEmailPax);
			tabPasajero.setSelected(true);
		}catch(Exception ex){
			DlgMessage.information(ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
		return ventaPasaje;
	}
	
	private void guardarVenta(VentaPasaje venta){
		ventaPasaje = venta;
//		showCalculadora = false;
		Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarVenta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				try{
					if(e.getName().equals("onYes")){
						int result = ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, rdVentaFechaAbierta.isSelected(), true, true,true);
						        
						/*Resta el saldo de LC del Cliente*/
						if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO)){
							ServiceLocator.getLineaCreditoClienteManager().restarSaldo(lineaCreditoCliente.getSaldo(), ventaPasaje.getImportePagado(), lineaCreditoCliente.getId());
//							ServiceLocator.getLineaCreditoClienteManager().restarSaldo(lineaCreditoCliente.getSaldo(), dblImporte.getValue(), lineaCreditoCliente.getId());
						}
							
															
						if(result == Constantes.CORRECT){
							DetalleCalculadora detalleCalculadora = new DetalleCalculadora();
							detalleCalculadora.setDenominacion("BOLETO "+ventaPasaje.getRuta().getOrigen()+" - "+ventaPasaje.getRuta().getDestino());
							detalleCalculadora.setTarifa(ventaPasaje.getTarifa());
							detalleCalculadora.setDescuento(ventaPasaje.getDescuento());
							lstDetalleCalculadora.add(detalleCalculadora);
							onCleanControlsPax();
							if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
								onCleanControlsClient();
							else{
								if(cmbCentroCosto.getItems().size()>0)
									cmbCentroCosto.setSelectedIndex(0);
							}
							
//							if(!chkVentaRemota.isChecked()){
//								if(!chkBoletoPrepagado.isChecked()){
								ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
								if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
									/**Solo Boletas y facturas*/
									if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || 
											ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
										/*Realiza el envio del boleto y realiza la impresion*/
//										boolean printComprobante=(chkVentaRemota.isChecked()?false:true);
										
										List<VentaPasaje> listVentaPasajes= new ArrayList<>();
										listVentaPasajes.add(ventaPasaje);
										
										//Aqui se envia el comprobante al servidor de Facturación Electrónica
										//Comentado temporalmente por MAOE
										//WSFE.sendVenta(listVentaPasajes,wndVentaReserva,printComprobante,null);
									}
									
									
//									File file= CreateDocument.crearBoleto(ventaPasaje);
									/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//									boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//									File file= CreateDocument.crearBoleto(ventaPasaje,formato);
//									
//									if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
////											String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+ventaPasaje.getNumeroControl()+".txt";
//										String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+Constantes.CLAVE_PAHT+ventaPasaje.getNumeroControl()+".txt";
//										Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//										win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//										win.setAttribute("msg", "Imprimiendo el Boleto de Viaje "+ ventaPasaje.getNumeroBoleto()+"... ");
//										win.setAttribute("urlDocumento", fileBoleto);
//										if(lbxAsientos.getItems().size() == 1 && !cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
//											win.setAttribute("showCalculator", true);
//											win.setAttribute("detalleCalculadora", lstDetalleCalculadora.clone());
//										}else
//											win.setAttribute("showCalculator", false);
//										String msg = "La venta se registro correctamente, Número de Control : "+ventaPasaje.getNumeroControl();
//										win.setAttribute("numeroControl", msg);
//										win.doModal();
//									}else{
//										//Descarga el archivo para la impresion
//										Util.descargarArchivo(file);
//									}										
								}else if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA || getAgencia().getId().intValue()==Constantes.ID_AGENCIA_SUPERMERCADOS_PERUANOS){
									/*Begin 19/01/2017 - jabanto*/
									/**Realiza la impresion*/
									if(ventaPasaje.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES){
										List<VentaPasaje> listVentaPasajes= new ArrayList<>();
										listVentaPasajes.add(ventaPasaje);
										WSFE.printVouchers(listVentaPasajes,wndVentaReserva);
									}
									
									/*End begin 19/01/2017 - jabanto*/
//									Window win = (Window)Executions.createComponents("ticketTuentrada.zul", null, null);
//									win.setAttribute("formato", WndTicketTuentrada.FORMAT_BOLETO);
//									win.setAttribute("msg", "Imprimiendo el Boleto de Viaje "+ ventaPasaje.getNumeroBoleto()+"... ");
//									List<VentaPasaje> lstVentaPasaje = new ArrayList<VentaPasaje>();
//									lstVentaPasaje.add(ventaPasaje);
//									win.setAttribute("lstVentas", lstVentaPasaje);
//									String msg = "La venta se registro correctamente, Número de Control : "+ventaPasaje.getNumeroControl();
//									win.setAttribute("numeroControl", msg);
//									win.doModal();
								}else{
									if(ventaPasaje.getCentroCosto()!=null){
										CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(ventaPasaje.getCentroCosto().getId().longValue());
										ventaPasaje.setCentroCosto(centroCosto);
									}
									TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
									ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
									itinerarioAgenciaPartidaID.setIdItinerario(ventaPasaje.getItinerario().getId());
									itinerarioAgenciaPartidaID.setIdAgencia(ventaPasaje.getAgenciaPartida().getId());
									criteriosBusqueda.put("itinerarioAgenciaPartidaID", itinerarioAgenciaPartidaID);
									List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
									if(lstItinerarioAgenciaPartida.size()==0)
										ventaPasaje.setHoraEmbarque(ventaPasaje.getHoraPartida());
									else
										ventaPasaje.setHoraEmbarque(lstItinerarioAgenciaPartida.get(0).getHoraPartida());
									lstVentasVoucher.add((VentaPasaje)ventaPasaje.clone());
									
//										if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA){
//											printTicketTuentrada(lstVentasVoucher);
//											String msg = "La venta se registro correctamente, Número de Control : "+ventaPasaje.getNumeroControl();
//											DlgMessage.information(msg);
//										}else{
										exportTicket(lstVentasVoucher);
//										}
								}
								
								/*En Begin 21/10/2016 - jabanto (Ya no van los recivos de caja)*/
//								}else{
//									ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
//									File file=CreateDocument.crearRecibCaja(ventaPasaje);
//									if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
//										String fileRC = Constantes.URL_FORMATOS_RECIBO_CAJA+Constantes.CLAVE_PAHT+ventaPasaje.getNumeroControl()+".txt";
//										Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//										win.setAttribute("formato", WndImprimir.FORMAT_RECIBO_CAJA);
//										win.setAttribute("msg", "Imprimiendo el Recibo de Caja "+ ventaPasaje.getNumeroBoleto()+"... ");
//										win.setAttribute("urlDocumento", fileRC);
//										if(lbxAsientos.getItems().size() == 1 && !cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
//											win.setAttribute("showCalculator", true);
//											win.setAttribute("detalleCalculadora", lstDetalleCalculadora.clone());
//										}else
//											win.setAttribute("showCalculator", false);
//										String msg = "La venta se registro correctamente, Número de Control : "+ventaPasaje.getNumeroControl();
//										win.setAttribute("numeroControl", msg);
//										win.doPopup();	
//									}else{
//										/*Descar el archivo para la impresion*/
//										Util.descargarArchivo(file);
//									}
//								}
//							}
							onCleanPagos();
							onCleanPartialListAsientosSeleccionados();
							onCleanInformacionVenta();
							tabPasajero.setSelected(true);
						}
					}
				}catch(CapacityExceedsException ceex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
				}catch(DuplicateSeatException dsex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
				}catch(NumeroBoletoDuplicadoException nbdex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
					imgRefreshBoleto.setVisible(true);
				}catch(TiempoExpiracionBloqueoException tebex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
					tiempoExpiracionBloqueo();
				}catch(Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
					log.error(ex);
				}
			}
		});
	}
	
	private void guardarVentaIdaVuelta(final List<VentaPasaje> lstVentas){
		Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarVenta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				try{
					if(e.getName().equals("onYes")){
						int result = ServiceLocator.getVentaPasajesManager().guardarVentaIdaVuelta(lstVentas, true);
						if(!chkVentaRemota.isChecked()){
//							for(int i=0; i<lstVentas.size(); i++){
//								ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(i).getId());
//								VentaPasaje printVenta=(VentaPasaje) ventaPasaje.clone();
//								
//								if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//									CreateDocument.crearBoleto(printVenta);	
//									
//									String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+ventaPasaje.getNumeroControl()+".txt";
//									Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//									win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//									win.setAttribute("msg", "Imprimiendo el Boleto de Viaje "+ printVenta.getNumeroBoleto()+"... ");
//									win.setAttribute("urlDocumento", fileBoleto);
//									win.doPopup();
//								}else{
//									if(printVenta.getCentroCosto()!=null){
//										CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(printVenta.getCentroCosto().getId().longValue());
//										printVenta.setCentroCosto(centroCosto);
//									}
//									TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
//									ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
//									itinerarioAgenciaPartidaID.setIdItinerario(printVenta.getItinerario().getId());
//									itinerarioAgenciaPartidaID.setIdAgencia(printVenta.getAgenciaPartida().getId());
//									criteriosBusqueda.put("itinerarioAgenciaPartidaID", itinerarioAgenciaPartidaID);
//									List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
//									if(lstItinerarioAgenciaPartida.size()==0)
//										printVenta.setHoraEmbarque(printVenta.getHoraPartida());
//									else
//										printVenta.setHoraEmbarque(lstItinerarioAgenciaPartida.get(0).getHoraPartida());
//									
//									lstVentasVoucher.add((VentaPasaje)printVenta.clone());
//								}
//								/*Resta el saldo de LC del Cliente*/
//								if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO))
//									ServiceLocator.getLineaCreditoClienteManager().restarSaldo(lineaCreditoCliente.getSaldo(), printVenta.getImportePagado(), lineaCreditoCliente.getId());
//							}
							
							if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
								if(lstVentas.size()>0){
									VentaPasaje ventaPasajeIDA=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(0).getId());
									VentaPasaje ventaPasajeRETORNO=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(1).getId());
									
									/*Begin 24/10/2016 - jabanto*/
									List<VentaPasaje> ventasIdaRetorno= new ArrayList<>();
									ventasIdaRetorno.add(ventaPasajeIDA);
									ventasIdaRetorno.add(ventaPasajeRETORNO);
									
									//Comentado temporalmente por MAOE
									//WSFE.sendVenta(ventasIdaRetorno, wndVentaReserva, true,null);
									
									/*End Begin 24/10/2016 - jabanto*/
//									/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//									boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//									
//								 	File fileIda=CreateDocument.crearBoleto(ventaPasajeIDA,formato);
//									File fileRetorno=CreateDocument.crearBoleto(ventaPasajeRETORNO,formato);
//									if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
////										String fileBoletoIDA = Constantes.URL_FORMATOS_BOLETOS+ventaPasajeIDA.getNumeroControl()+".txt";
////										String fileBoletoRETORNO = Constantes.URL_FORMATOS_BOLETOS+ventaPasajeRETORNO.getNumeroControl()+".txt";
//										String fileBoletoIDA = Constantes.URL_FORMATOS_BOLETOS+Constantes.CLAVE_PAHT+ventaPasajeIDA.getNumeroControl()+".txt";
//										String fileBoletoRETORNO = Constantes.URL_FORMATOS_BOLETOS+Constantes.CLAVE_PAHT+ventaPasajeRETORNO.getNumeroControl()+".txt";
//										
//										Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//										win.setAttribute("formato", WndImprimir.FORMAT_BOLETO_IDA_VUELTA);
//										win.setAttribute("msg", "Imprimiendo el Boleto de Ida y Retorno : "+ ventaPasajeIDA.getNumeroBoleto()+" y "+ventaPasajeRETORNO.getNumeroBoleto()+"... ");
//										win.setAttribute("urlDocumento", fileBoletoIDA);
//										win.setAttribute("urlDocumento1", fileBoletoRETORNO);
//										win.doPopup();
//									}else{
//										//Descarga los archivos del ida y vuelta para la impresion
//										File fileIdaRetorno=new File(fileIda.getPath().replaceAll(".txt", "")+"COMP.txt");
//										
//										FileInputStream inputIda= new FileInputStream(fileIda);
//										FileInputStream inputRetorno= new FileInputStream(fileRetorno);
//																				
//										OutputStream Salida = new FileOutputStream(fileIdaRetorno);
//										byte[] buffer = new byte[1024];  
//						                int tamaño;  
//						                while ((tamaño = inputIda.read(buffer)) > 0) { //lee el fichero sunat a copiar cada 1MB  
//						                	Salida.write(buffer, 0, tamaño);//Escribe el MB en el fichero destino   
//						                }
//						                inputIda.close();
//						                while ((tamaño = inputRetorno.read(buffer)) > 0) {//lee el fichero transportista a copiar cada 1MB  
//						                	Salida.write(buffer, 0, tamaño);//Escribe el MB en el fichero destino 
//						                }
//						                 inputRetorno.close();
//						                 Salida.close();  
//						                 Util.descargarArchivo(fileIdaRetorno);
//									}

								}
							}else if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA || getAgencia().getId().intValue()==Constantes.ID_AGENCIA_SUPERMERCADOS_PERUANOS){
								if(lstVentas.size()>0){
									final VentaPasaje ventaPasajeIDA=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(0).getId());
									final VentaPasaje ventaPasajeRETORNO=ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(1).getId());									
									/*Begin 19/01/2017*/
									List<VentaPasaje> ventasIdaRetorno= new ArrayList<>();
									ventasIdaRetorno.add(ventaPasajeIDA);
									ventasIdaRetorno.add(ventaPasajeRETORNO);
									WSFE.printVouchers(ventasIdaRetorno, wndVentaReserva);
									
									
									/*End begin 19/01/2017 - jabanto*/
//									final Window win = (Window)Executions.createComponents("ticketTuentrada.zul", null, null);
//									win.setAttribute("formato", WndTicketTuentrada.FORMAT_BOLETO);
//									win.setAttribute("msg", "Imprimiendo el Boleto de Viaje Ida y Retorno "+ ventaPasajeIDA.getNumeroBoleto()+" y "+ventaPasajeRETORNO.getNumeroBoleto()+"... ");
//									List<VentaPasaje> lstVentaPasaje = new ArrayList<VentaPasaje>();
//									lstVentaPasaje.add(ventaPasajeIDA);
//									win.setAttribute("lstVentas", lstVentaPasaje);
//									String msg = "La venta se registro correctamente";//+ventaPasaje.getNumeroControl();
//									win.setAttribute("numeroControl", msg);
//									win.doModal();
//									
//									win.addEventListener(Events.ON_MOVE,new EventListener<Event>() {
//										@Override
//										public void onEvent(Event event)throws Exception {
//											// TODO Auto-generated method stub
//											Window win1 = (Window)Executions.createComponents("ticketTuentradaRetorno.zul", null, null);
//											win1.setAttribute("formato", WndTicketTuentradaRetorno.FORMAT_BOLETO);
//											win1.setAttribute("msg", "Imprimiendo el Boleto de Viaje Ida y Retorno "+ ventaPasajeIDA.getNumeroBoleto()+" y "+ventaPasajeRETORNO.getNumeroBoleto()+"... ");
//											List<VentaPasaje> lstVentaPasaje1 = new ArrayList<VentaPasaje>();
//											lstVentaPasaje1.add(ventaPasajeRETORNO);
//											win1.setAttribute("lstVentas", lstVentaPasaje1);
//											String msg1 = "La venta se registro correctamente";//+ventaPasaje.getNumeroControl();
//											win1.setAttribute("numeroControl", msg1);
//											win.onClose();
//											win1.setVisible(false);
//										}
//									});
								}
							}else{
								for(int i=0; i<lstVentas.size(); i++){
									ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(lstVentas.get(i).getId());
									VentaPasaje printVenta=(VentaPasaje) ventaPasaje.clone();
								
									if(printVenta.getCentroCosto()!=null){
										CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(printVenta.getCentroCosto().getId().longValue());
										printVenta.setCentroCosto(centroCosto);
									}
									TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
									ItinerarioAgenciaPartidaID itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaID();
									itinerarioAgenciaPartidaID.setIdItinerario(printVenta.getItinerario().getId());
									itinerarioAgenciaPartidaID.setIdAgencia(printVenta.getAgenciaPartida().getId());
									criteriosBusqueda.put("itinerarioAgenciaPartidaID", itinerarioAgenciaPartidaID);
									List<ItinerarioAgenciaPartida> lstItinerarioAgenciaPartida = ServiceLocator.getItinerarioAgenciaPartidaManager().buscarPorX(criteriosBusqueda, null);
									if(lstItinerarioAgenciaPartida.size()==0)
										printVenta.setHoraEmbarque(printVenta.getHoraPartida());
									else
										printVenta.setHoraEmbarque(lstItinerarioAgenciaPartida.get(0).getHoraPartida());

									lstVentasVoucher.add((VentaPasaje)printVenta.clone());
									
									/*Resta el saldo de LC del Cliente*/
									if(((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_FORPAG_CREDITO))
										ServiceLocator.getLineaCreditoClienteManager().restarSaldo(lineaCreditoCliente.getSaldo(), printVenta.getImportePagado(), lineaCreditoCliente.getId());
								}
							}
							
							if(getAgencia().getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA && getAgencia().getId().intValue()!=Constantes.ID_AGENCIA_TUENTRADA){  
//								if(getAgencia().getId().intValue()==Constantes.ID_AGENCIA_TUENTRADA){
//									printTicketTuentrada(lstVentasVoucher);
//								}else{
									exportTicket(lstVentasVoucher);
//								}
							}
						}
						
						if(result == Constantes.CORRECT){
							DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVentaIdaVuelta")+"\n"+lstVentas.get(0).getNumeroControl() + "\n"+lstVentas.get(1).getNumeroControl());
//							DetalleCalculadora detalleCalculadora = new DetalleCalculadora();
//							detalleCalculadora.setDenominacion("BOLETO "+ventaPasaje.getRuta().getOrigen()+" - "+ventaPasaje.getRuta().getDestino());
//							detalleCalculadora.setTarifa(ventaPasaje.getTarifa());
//							detalleCalculadora.setDescuento(ventaPasaje.getDescuento());
//							lstDetalleCalculadora.add(detalleCalculadora);
							onCleanControlsPax();
							if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
								onCleanControlsClient();
							else
								cmbCentroCosto.setSelectedIndex(0);
							onCleanPagos();
							onCleanPartialListAsientosSeleccionados();
							onCleanInformacionVenta();
							tabPasajero.setSelected(true);
						}
					}
				}catch(CapacityExceedsException ceex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
				}catch(DuplicateSeatException dsex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
				}catch(NumeroBoletoDuplicadoException nbdex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
					imgRefreshBoleto.setVisible(true);
				}catch(TiempoExpiracionBloqueoException tebex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
					tiempoExpiracionBloqueo();
				}catch(Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
					log.error(ex);
				}
			}
		});
	}
	
	private void tiempoExpiracionBloqueo()throws Exception{
		if(rdVentaIdaVuelta.isSelected()){
			if(lbxAsientosIdaRetorno.getSelectedIndex()>=0){
				VentaIdaRetorno ventaIdaRetorno = (VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue();
				for(int i=0; i<lbxAsientos.getItems().size(); i++){
					DetalleItinerario detalle = (DetalleItinerario)lbxAsientos.getItems().get(i).getValue();
					if(detalle.getItinerario().getId().intValue()==ventaIdaRetorno.getDetalleItinerarioIDA().getItinerario().getId().intValue() 
							&& detalle.getAsiento().equals(ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento())){
						lbxAsientos.removeItemAt(i);
						break;
					}
				}
				for(int i=0; i<lbxAsientos.getItems().size(); i++){
					DetalleItinerario detalle = (DetalleItinerario)lbxAsientos.getItems().get(i).getValue();
					if(detalle.getItinerario().getId().intValue()==ventaIdaRetorno.getDetalleItinerarioRETORNO().getItinerario().getId().intValue() 
							&& detalle.getAsiento().equals(ventaIdaRetorno.getDetalleItinerarioRETORNO().getAsiento())){
						lbxAsientos.removeItemAt(i);
						break;
					}
				}
				lbxAsientosIdaRetorno.removeItemAt(lbxAsientosIdaRetorno.getSelectedIndex());
			}
			if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
				lbxAsientosIdaRetorno.getItems().clear();
				lbxAsientos.getItems().clear();
			}
		}else{
			if(lbxAsientos.getSelectedIndex()>=0)
				lbxAsientos.removeItemAt(lbxAsientos.getSelectedIndex());
			if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA))
				lbxAsientos.getItems().clear();
		}
		onCleanControlsPax();
		if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
			onCleanControlsClient();
		else{
			if(cmbCentroCosto.getItems().size()>0)
				cmbCentroCosto.setSelectedIndex(0);
		}
		onCleanPagos();
//		onCleanPartialListAsientosSeleccionados();
		onCleanInformacionVenta();
		tabPasajero.setSelected(true);
	}
	
	/**
	 * Limpiando el contenido de los controles relacionados a los pagos
	 */
	private void onCleanPagos(){
		cmbTipoComprobante.setSelectedIndex(0);
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoFormaPago.getItems().clear();
		cmbTipoFormaPago.setText("");
		cmbTipoFormaPago.setDisabled(true);
		cmbOperadorTarjetaCredito.getItems().clear();
		cmbOperadorTarjetaCredito.setText("");
		cmbOperadorTarjetaCredito.setDisabled(true);
		cmbTarjetaCredito.getItems().clear();
		cmbTarjetaCredito.setText("");
		cmbTarjetaCredito.setDisabled(true);
		dblTarifa.setValue(.00);
		dblTarifaRetorno.setValue(.00);
		dblRecargo.setValue(.00);
		dblDescuento.setValue(.00);
		dblDescuentoRetorno.setValue(.00);
		dblImporte.setValue(.00);
		txtOperacionBancaria.setText("");
		promocionAplicada=null;
		onSelectDefaultTipoComprobante();
		onSelectDefaultFormaPago();
		lblPromocion.setValue("");
		txtIdPromocion.setText("");
		imgQuitarPromocion.setVisible(false);
//		pagoSoles=null;
		tipoCambio=null;
	}
	
	/**
	 * Limpiando el contenido de los controles que muestran informacion de la venta
	 */
	private void onCleanInformacionVenta(){
		lblOrigen.setValue("");
		lblDestino.setValue("");
		cmbPtoEmbarque.getItems().clear();
		cmbPtoEmbarque.setText("");
		cmbPtoDesembarque.getItems().clear();
		cmbPtoDesembarque.setText("");
		lblFechaPartida.setValue("");
		lblFechaLlegada.setValue("");
		lblHoraPartida.setValue("");
		lblHoraLlegada.setValue("");
		lblNroAsiento.setValue("");
		lblTarifa.setValue("");
		txtNumeroBoleto.setText("");
		cmbAlimentacion.setSelectedIndex(0);
		detailItinerary=null;
		ventaPasaje = null;
		lbxAsientos.setSelectedIndex(-1);
		txtObservacionesIda.setText("");
		/*	PARA EL RETORNO	*/
		lblOrigenRetorno.setValue("");
		lblDestinoRetorno.setValue("");
		cmbPtoEmbarqueRetorno.getItems().clear();
		cmbPtoEmbarqueRetorno.setText("");
		cmbPtoDesembarqueRetorno.getItems().clear();
		cmbPtoDesembarqueRetorno.setText("");
		lblFechaPartidaRetorno.setValue("");
		lblFechaLlegadaRetorno.setValue("");
		lblHoraPartidaRetorno.setValue("");
		lblHoraLlegadaRetorno.setValue("");
		lblNroAsientoRetorno.setValue("");
		lblTarifaRetorno.setValue("");
		txtNumeroBoletoRetorno.setText("");
		cmbAlimentacionRetorno.setSelectedIndex(0);
		txtObservacionesRetorno.setText("");
//		detailItinerary=null;
		ventaIDA = null;
		ventaRETORNO = null;
		lbxAsientosIdaRetorno.setSelectedIndex(-1);
		
		/*controles de la alimentacion de los servicios de cruz del sur*/
		lblAlimen1.setVisible(false);
		lblAlimen2.setVisible(false);
		lblAlimen3.setVisible(false);
		lblAlimen4.setVisible(false);
		cmbAlimen1.setVisible(false);
		cmbAlimen2.setVisible(false);
		cmbAlimen3.setVisible(false);
		cmbAlimen4.setVisible(false);
		Util.limpiarCombobox(cmbAlimen1);
		Util.limpiarCombobox(cmbAlimen2);
		Util.limpiarCombobox(cmbAlimen3);
		Util.limpiarCombobox(cmbAlimen4);
	}
	
	/**
	 * Limpiando controles
	 * @throws Exception 
	 */
	private void onCleanPartialListAsientosSeleccionados() throws Exception{
		if(rdVentaIdaVuelta.isSelected()){
			if(lbxAsientosIdaRetorno.getSelectedIndex()>=0){
				VentaIdaRetorno ventaIdaRetorno = (VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue();
				for(int i=0; i<lbxAsientos.getItems().size(); i++){
					DetalleItinerario detalle = (DetalleItinerario)lbxAsientos.getItems().get(i).getValue();
					if(detalle.getItinerario().getId().intValue()==ventaIdaRetorno.getDetalleItinerarioIDA().getItinerario().getId().intValue() 
							&& detalle.getAsiento().equals(ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento())){
						lbxAsientos.removeItemAt(i);
						break;
					}
				}
				for(int i=0; i<lbxAsientos.getItems().size(); i++){
					DetalleItinerario detalle = (DetalleItinerario)lbxAsientos.getItems().get(i).getValue();
					if(detalle.getItinerario().getId().intValue()==ventaIdaRetorno.getDetalleItinerarioRETORNO().getItinerario().getId().intValue() 
							&& detalle.getAsiento().equals(ventaIdaRetorno.getDetalleItinerarioRETORNO().getAsiento())){
						lbxAsientos.removeItemAt(i);
						break;
					}
				}
				lbxAsientosIdaRetorno.removeItemAt(lbxAsientosIdaRetorno.getSelectedIndex());
			}if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
				lbxAsientosIdaRetorno.getItems().clear();
				lbxAsientos.getItems().clear();
			}
			
			/*	Validando la cantidad de asientos seleccionados para reiniciar todos los objetos	*/
			if(lbxAsientosIdaRetorno.getItems().size() == 0 && !cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
//				crearCalculadora();
				selectedTabItinerario();
//				lstDetalleCalculadora.clear();
				rdVentaNormal.setSelected(true);
				onSelectTipoVenta();
				asignarFechaFin = true;						
//				chkBoletoPrepagado.setChecked(false);
			}else if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA))
				selectedTabItinerario();
		}else{
			if(lbxAsientos.getSelectedIndex()>=0)
				lbxAsientos.removeItemAt(lbxAsientos.getSelectedIndex());
			if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA))
				lbxAsientos.getItems().clear();
			/*	Validando la cantidad de asientos seleccionados para reiniciar todos los objetos	*/
			if(lbxAsientos.getItems().size() == 0 && !cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA)){
//				showCalculadora = true;
//				crearCalculadora();
				selectedTabItinerario();
//				lstDetalleCalculadora.clear();
				rdVentaNormal.setSelected(true);
				onSelectTipoVenta();
				chkBoletoPrepagado.setChecked(false);
			}else if(cmbTipoOperacion.getSelectedItem().getValue().toString().equals(Constantes.TIPO_OPERACION_RESERVA))
				selectedTabItinerario();
		}
	}
	
	/**
	 * Limpia los datos del itinerarios seleccionado
	 */
	private void onCleanDatosItinerario(){
		if(rdVentaFechaAbierta.isSelected()){
			lbxItinerariosFechaAbierta.clearSelection();
			detalleItinerarioFechaAbierta = null;				
		}else {
			/*	IDA		*/
			lbxItinerariosIda.clearSelection();
			detalleItinerarioIda = null;
			mapaAsientosIda = null;
			/*	RETORNO	*/
			if(rdVentaIdaVuelta.isSelected()){
				lbxItinerariosRetorno.clearSelection();
				detalleItinerarioRetorno = null;
				mapaAsientosRetorno = null;
			}
		}
	}
	
	/**
	 * Permite liberar los asientos cuando se cambia de pestaña dentro de la venta
	 */
	public void liberarAsientos(){
		try{
			/*Desbloquea asietos de los servicios pool (Cruz del sur o civa)*/
			if(lbxAsientos.getItems().size()>0 && lbxAsientos.getItems().get(0).getValue() instanceof AsientoPool){
				AsientoPool asiento=lbxAsientos.getItems().get(0).getValue();
				//Valida si es cruz del sur o civa
				if(asiento.getObjectCruzdelsur()!=null)
					WSCruzdelsur.desbloquearAsientos(lbxAsientos);
				else if (asiento.getObjectCiva()!=null)
					RESTCiva.desbloquearAsientos(lbxAsientos);
			}else{
				//Asientos TEPSA
				ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(usuhar);	
			}			
			lbxAsientos.getItems().clear();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	/**
	 * Utilizado cuando el usuario hace click en el tab de Itinerarios, efecto de regresar
	 * @throws Exception 
	 */
	public void selectedTabItinerario() throws Exception{
		if(!tabAsientos.isDisabled() || !tabVenta.isDisabled()){
			selectedTabAsientos();
			tabAsientos.setDisabled(true);
			chkBoletoPrepagado.setChecked(false);
			seleccionarPrepagado();
			liberarAsientos();
			onCleanDatosItinerario();
			chkVentaRemota.setChecked(false);
			onSelectVentaRemota();
			btnNextTabAsientos.setDisabled(true);
			btnNextTabVenta.setDisabled(true);
			btnNextFechaAbierta.setDisabled(true);
			
			if(grdOcupabilidadIda.getRows().getChildren().size()>0)
				grdOcupabilidadIda.getRows().getChildren().get(0).detach();
			if(grdOcupabilidadRetorno.getRows().getChildren().size()>0)
				grdOcupabilidadRetorno.getRows().getChildren().get(0).detach();
			
			lstDetalleCalculadora.clear();
//			totalPagarPreliminar=0.0;
			tabItinerario.setSelected(true);
			tabPasajero.setSelected(true);
			if(lbxAsientosIdaRetorno.isVisible()){
				lbxAsientosIdaRetorno.getItems().clear();
				lbxAsientosIdaRetorno.setVisible(false);
			}
		}
	}
	
	/**
	 * Utilizado cuando el usuario hace click en el tab de Asientos, efecto de regresar
	 * @throws Exception 
	 */
	public void selectedTabAsientos() throws Exception{
		tabVenta.setDisabled(true);
		onCleanInformacionVenta();
		onCleanControlsPax();
		if(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_CORPORATIVO)
			onCleanControlsClient();
		onCleanPagos();
		lstDetalleCalculadora.clear();
//		totalPagarPreliminar=0.0;
		tabPasajero.setSelected(true);
	}
	
	/**
	 * habilita los controles de la nacionalidad.
	 */
	public void habilitarNacionalidad(){
		TipoDocumento tipoDocumento = cmbTipoDocumento.getSelectedItem().getValue();
		if(tipoDocumento instanceof TipoDocumento && tipoDocumento.getId().intValue()!=Constantes.ID_TIPDOC_DNI){
			rowNacionalidad.setVisible(true);
			lblEmail.setValue("EMAIL (*):");
			txtDocumentoPax.setMaxlength(20);
		}else{
			rowNacionalidad.setVisible(false);
			lblEmail.setValue("EMAIL (*):");
			txtDocumentoPax.setMaxlength(8);
		}
		
		txtApeMat.setDisabled(false);
		txtApePat.setDisabled(false);
		txtNombres.setDisabled(false);
		txtDocumentoPax.setText("");
		imgValidacionDNI.setSrc("");
		//Cuando es S/N
		if(tipoDocumento instanceof TipoDocumento && tipoDocumento.getId().intValue()==Constantes.ID_TIPDOC_SN){
//			txtDocumentoPax.setText("");
			txtDocumentoPax.setDisabled(true);
		}else{
			txtDocumentoPax.setDisabled(false);
			txtDocumentoPax.setFocus(true);
		}
	}
	
	/**
	 * Habilita los controles para el pago mixto.
	 */
	public void habilitarPagoMixto(){
		boolean arg = false;
		if(chkPagoMixto.isChecked())
			arg = true;
		
		lblImporteEfectivo.setVisible(arg);
		lblImporteTarjeta.setVisible(arg);
		dblImporteEfectivo.setVisible(arg);
		dblImporteTarjeta.setVisible(arg);
		dblImporteEfectivo.setValue(0.0);
		dblImporteTarjeta.setValue(0.0);
		dblImporteEfectivo.setFocus(true);
	}
	
	/**
	 * Realiza el llenado del combobox con las formas de pago.
	 * @param combobox	: Objeto al cual se le cargaran los datos.
	 * @param todos		: Indica el primer elemente del objeto combobox.
	 * @throws Exception
	 */
	public static void cargarFormaPago(Combobox combobox, boolean todos) throws Exception {
		ArrayList<FormaPago> lstFormaPago = ServiceLocator.getFormaPagoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		UtilData.cargarGenericData(combobox, todos);	
		for (FormaPago formaPago: lstFormaPago) {
			if (!(formaPago.getId().equals(Constantes.ID_FORPAG_CORTESIA))){
				Comboitem oComboitem = new Comboitem();	
				oComboitem.setValue(formaPago);
				oComboitem.setLabel(formaPago.getDenominacion());	
				combobox.appendChild(oComboitem);
			}
		}
	}
	
//	/**
//	 * Crea la ventana calculadora, donde coloca el importe pagado y devuelve el vuelto.
//	 */
//	@SuppressWarnings("deprecation")
//	private void crearCalculadora(){
//		if(getAgencia().getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_TEPSA){
//			
//			final Window win = new Window("Calculadora", "normal", true);
//			win.setHeight("270px");
//			win.setContentStyle("overflow:auto !important");
//			Grid grid = new Grid();
//			grid.setWidth("280px");
//			Columns columns = new Columns();
//			Column column = new Column();
//			column = new Column("Descripción", null, "200px");
//			column.setAlign("right");
//			columns.appendChild(column);
//			column = new Column("Tarifa", null, "60px");
//			columns.appendChild(column);
//			grid.appendChild(columns);
//			
//			Rows rows = new Rows();
//			Row row = null;
//			Label label = null;
//			Textbox textbox = null;
//			Double dsct = 0.0;
//			Double total = 0.0;
//	//		final Double importe = 0.0;
//			for(DetalleCalculadora detalle : lstDetalleCalculadora){
//				row = new Row();
//				label = new Label(detalle.getDenominacion());
//				row.appendChild(label);
//				textbox = new Textbox(Util.toNumberFormat(detalle.getTarifa(), 2));
//				textbox.setStyle("text-align:right");
//				textbox.setWidth("40px");
//				textbox.setDisabled(true);
//				row.appendChild(textbox);
//				rows.appendChild(row);
//				dsct = dsct + detalle.getDescuento();
//				total = total + detalle.getTarifa();
//			}
//			row = new Row();
//			label = new Label("DESCUENTO");
//			row.appendChild(label);
//			textbox = new Textbox(Util.toNumberFormat(dsct, 2));
//			textbox.setStyle("text-align:right");
//			textbox.setWidth("40px");
//			textbox.setDisabled(true);
//			row.appendChild(textbox);
//			rows.appendChild(row);
//			
//			totalPagar = total-dsct;
//			row = new Row();
//			label = new Label("TOTAL A PAGAR");
//			row.appendChild(label);
//			textbox = new Textbox(Util.toNumberFormat(totalPagar, 2));
//			textbox.setStyle("text-align:right");
//			textbox.setWidth("40px");
//			textbox.setDisabled(true);
//			row.appendChild(textbox);
//			rows.appendChild(row);
//			
//			row = new Row();
//			label = new Label("EFECTIVO");
//			row.appendChild(label);
//			final Doublebox dblbxEfectivo = new Doublebox();
//			dblbxEfectivo.setStyle("font-size:10px");
//			dblbxEfectivo.addEventListener(Events.ON_OK, new EventListener<Event>() {
//				public void onEvent(Event e){
//					if(dblbxEfectivo.getValue()!=null && dblbxEfectivo.getValue()>0 && dblbxEfectivo.getValue() >= totalPagar){
//						txtVuelto.setValue(Util.toNumberFormat((dblbxEfectivo.getValue() - totalPagar), 2));
//						button.setFocus(true);
//					}else
//						button.setFocus(true);
//				}
//			});
//			row.appendChild(dblbxEfectivo);
//			rows.appendChild(row);
//			
//			row = new Row();
//			label = new Label("VUELTO");
//			row.appendChild(label);
//			txtVuelto = new Textbox();
//			txtVuelto.setStyle("text-align:right");
//			txtVuelto.setWidth("40px");
//			row.appendChild(txtVuelto);
//			rows.appendChild(row);
//			
//			row = new Row();
//			row.setSpans("2");
//			row.setAlign("center");
//			button = new Button("ACEPTAR", "/resources/mp_aceptarEnabled.png");
//			button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//				public void onEvent(Event e){
//					win.onClose();
//				}
//			});
//			button.setStyle("font-size:10px");
//			row.appendChild(button);
//			rows.appendChild(row);
//			
//			grid.appendChild(rows);
//			win.appendChild(grid);
//			this.appendChild(win);
//			dblbxEfectivo.setFocus(true);
//			win.doModal();
//		}
//	}
	
	/**
	 * Obtiene las promociones vigentes de la base de datos y las muestras en pantalla.
	 */
	private void imgPromocion_loadPromociones(){
		try{
			boolean paxfre = false;
			if(oPasajero==null){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajeroPromocion"), txtDocumentoPax);
				tabPasajero.setSelected(true);
				return;
			}
			
			
			if(oPasajero.getPasajeroFrecuente()!=null && oPasajero.getPasajeroFrecuente().getEstado().intValue()==Constantes.TRUE_VALUE)
				paxfre=true;
			
//			if(oPasajero.isPaxFree())
//				paxfre=true;
			String idCliente = null;
			if(oCliente!=null)
				idCliente = oCliente.getId().toString();
			
			/*Valida que la localidad destino no pertenesca a la */
			if(Util.isDestinoPool(detailItinerary)){
				DlgMessage.information(Messages.getString("WndVentaReserva.infomration.promosDesabled"));
				return;
			}
			
			AplicarPromocion aplicarPromocion = createObjectAplicarPromocion(true);
			Window win = aplicarPromocion.loadPromociones(paxfre, idCliente, lblFechaPartida.getValue());
			/*	Validando que el Objeto Window sea distinto de null	*/
			if(win!=null){
				this.appendChild(win);
				win.doModal();
			}
		}catch(Exception ex){
			ex.getStackTrace();
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	private AplicarPromocion createObjectAplicarPromocion(boolean esIda){
		AplicarPromocion aplicarPromocion = null;
		DetalleItinerario detalle = null;
		
		Integer idTipoMoneda=(tipoMonedaAgencia!=null?tipoMonedaAgencia.getId():Constantes.ID_TIPMON_SOLES);
		Integer idCanalVenta = getUsuarioHardware().getCanalVenta().getId();
		Integer idAgencia = getAgencia().getId();
		if(chkVentaRemota.isChecked()){
			idCanalVenta = usuarioHardwareRemoto.getCanalVenta().getId();
//			idCanalVenta = ((ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue()).getUsuarioHardware().getCanalVenta().getId();
			idAgencia = ((Agencia)cmbAgenciaRemota.getSelectedItem().getValue()).getId();
		}
		
		if(esIda){
			if(rdVentaIdaVuelta.isChecked())
				detalle = ((VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue()).getDetalleItinerarioIDA();
			else{
				if(lbxAsientos.getItemCount()==0)
					detalle = detalleItinerarioFechaAbierta;
				else
					detalle = (DetalleItinerario)lbxAsientos.getSelectedItem().getValue();
			}
			
			aplicarPromocion = new AplicarPromocion(detalle.getRuta().getId(), detalle.getItinerario().getServicio().getId(), 
					idAgencia, idCanalVenta, null, null, lblNroAsiento.getValue().equals("")?"*":lblNroAsiento.getValue(), 
					oCliente==null?null:oCliente.getId(), rdVentaIdaVuelta.isSelected(), ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId(), 
					((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId(), 
					(cmbTarjetaCredito.getSelectedItem()==null?null:((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()).getId()), 
					(lblFechaPartida.getValue().equals("")?null:Util.StringtoDate(lblFechaPartida.getValue(), Constantes.DATE_FORMAT)), 
					(oPasajero==null?false:oPasajero.isPaxFree()), dblTarifa, dblDescuento, dblImporte, dblRecargo, lblPromocion, imgQuitarPromocion, txtIdPromocion,
					agencia,
					(idTipoMoneda.intValue()==Constantes.ID_TIPMON_SOLES?null:dblImporteOtraMoneda),
					(idTipoMoneda.intValue()==Constantes.ID_TIPMON_SOLES?null:dblDescuentoOtraMoneda),
					(lblHoraPartida.getValue().trim().isEmpty()?"*":lblHoraPartida.getValue().trim()));
		}else{
			detalle = ((VentaIdaRetorno)lbxAsientosIdaRetorno.getSelectedItem().getValue()).getDetalleItinerarioRETORNO();
			aplicarPromocion = new AplicarPromocion(detalle.getRuta().getId(), detalle.getItinerario().getServicio().getId(), 
					idAgencia, idCanalVenta, null, null, lblNroAsientoRetorno.getValue().equals("")?"*":lblNroAsientoRetorno.getValue(), 
					oCliente==null?null:oCliente.getId(), rdVentaIdaVuelta.isSelected(), ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId(), 
					((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId(), 
					(cmbTarjetaCredito.getSelectedItem()==null?null:((TarjetaCredito)cmbTarjetaCredito.getSelectedItem().getValue()).getId()), 
					(lblFechaPartidaRetorno.getValue().equals("")?null:Util.StringtoDate(lblFechaPartidaRetorno.getValue(), Constantes.DATE_FORMAT)), 
					(oPasajero==null?false:oPasajero.isPaxFree()), dblTarifaRetorno, dblDescuentoRetorno, dblImporte, dblRecargo, lblPromocion, imgQuitarPromocion, txtIdPromocion,
					agencia,
					(idTipoMoneda.intValue()==Constantes.ID_TIPMON_SOLES?null:dblImporteOtraMoneda),
					(idTipoMoneda.intValue()==Constantes.ID_TIPMON_SOLES?null:dblDescuentoRetornoOtraMoneda),
					(lblHoraPartidaRetorno.getValue().trim().isEmpty()?"*":lblHoraPartidaRetorno.getValue().trim()));
			
		}
		return aplicarPromocion;
	}
	
	private void quitarPromocion(){
		if(dblImporte.getValue()!=null){
			lblPromocion.setValue("");
			dblDescuento.setTooltiptext("");
			dblDescuento.setValue(0.0);
			dblDescuentoRetorno.setValue(0.0);
			dblImporte.setValue(dblTarifa.getValue()+dblRecargo.getValue()-dblDescuento.getValue());
			txtIdPromocion.setText("");
			imgQuitarPromocion.setVisible(false);
			
			dblDescuentoOtraMoneda.setValue(0.00);
			dblImporteOtraMoneda.setValue((dblTarifaOtraMoneda.getValue()!=null && !(dblTarifaOtraMoneda.getText().isEmpty())?dblTarifaOtraMoneda.getValue():.00)-
					(dblDescuentoOtraMoneda.getValue()!=null && !(dblDescuentoOtraMoneda.getText().isEmpty())?dblDescuentoOtraMoneda.getValue():.00));
			
			/*Por si los soles hayna sido combertidos a otra moneda*/
//			if(pagoSoles!=null){
//				pagoSoles.setDescuento(.00);
//				pagoSoles.setDescuentoRetorno(.00);
//				pagoSoles.setImportePagado(pagoSoles.getTarifa()+pagoSoles.getRecargo()-pagoSoles.getDescuento());
//			}
		}
	}
	
	private void convertirPaxfree(){
		Messagebox.show(Messages.getString("WndVentaReserva.question.fidelizarPasajeroFrecuente"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(e.getName().equals("onYes")){
					try{
						PasajeroFrecuente paxfree = ServiceLocator.getPasajeroFrecuenteManager().buscarPaxFree(oPasajero.getId());
						/*	Obteniendo la fecha y hora del servidor	*/
						String dateTimeSystem = ServiceLocator.getVentaPasajesManager().getDateSystem();
						/*	Obteniendo la fecha de caducidad	*/
						String fechaCaducidad = Util.getFechaCaducidadPaxFre(dateTimeSystem);
						PasajeroFrecuente pasajeroFrecuente = null;
						HistoricoMembresia historicoMembresia = null;
						if(paxfree == null){
							pasajeroFrecuente = new PasajeroFrecuente();
							pasajeroFrecuente.setPasajero(oPasajero);
							pasajeroFrecuente.setPuntosAcumulados(0);
							pasajeroFrecuente.setPuntosUtilizados(0);
							pasajeroFrecuente.setNumeroTarjeta(getAgencia().getId()+"-"+getUsuario().getId()+"-"+Util.getGenerarAleatorio(9999));
							pasajeroFrecuente.setFechaIngreso(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							pasajeroFrecuente.setAgencia(getAgencia());
							pasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
							pasajeroFrecuente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							ServiceLocator.getPasajeroFrecuenteManager().guardar(pasajeroFrecuente);
						}else if(paxfree.getEstado().intValue()==Constantes.FALSE_VALUE){//PAXFRE Inactivo
							historicoMembresia = new HistoricoMembresia();
							historicoMembresia.setPasajeroFrecuente(paxfree);
							historicoMembresia.setFechaActivacionAnterior(paxfree.getFechaActivacion());
							historicoMembresia.setFechaCaducidadAnterior(paxfree.getFechaCaducidad());
							historicoMembresia.setFechaSuspensionAnterior(paxfree.getFechaSuspension());
							historicoMembresia.setNuevaFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							historicoMembresia.setNuevaFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							historicoMembresia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(historicoMembresia, getUsuario(), Executions.getCurrent());
							ServiceLocator.getHistoricoMembresiaManager().guardar(historicoMembresia);
							/*	Actualizando al pasajero frecuente	*/				
							pasajeroFrecuente = paxfree;
							pasajeroFrecuente.setFechaActivacion(Constantes.FORMAT_DATE_TIME_24H.parse(dateTimeSystem));
							pasajeroFrecuente.setFechaCaducidad(Constantes.FORMAT_DATE_TIME_24H.parse((fechaCaducidad)));
							pasajeroFrecuente.setFechaSuspension(null);
							pasajeroFrecuente.setEstado(Constantes.TRUE_VALUE);
							UtilData.auditarRegistro(paxfree, true, getUsuario(),Executions.getCurrent());
							pasajeroFrecuente.setHistoricoMembresia(historicoMembresia);
							ServiceLocator.getPasajeroFrecuenteManager().actualizar(pasajeroFrecuente);
						}
						DlgMessage.information(Messages.getString("WndVentaReserva.information.fidelizacionRealizada"));
						mantenimientoRegistroPax(oPasajero.getId());
						imgFidelizarPasajero.setVisible(false);
					}catch(Exception ex){
						ex.printStackTrace();
						DlgMessage.error(Messages.getString("WndVentaReserva.information.fidelizacionNoRealizada"));
						log.error(ex);
					}
				}
			}
		});
	}
	
	
	private void agruparAsientos(){
		lbxAsientosIdaRetorno.getItems().clear();
		Map<String, List<DetalleItinerario>> mapAsientosIdaRetorno = new TreeMap<String, List<DetalleItinerario>>();
		for(int i=0; i<lbxAsientos.getItems().size(); i++){
			DetalleItinerario detalle = (DetalleItinerario)lbxAsientos.getItems().get(i).getValue();
			List<DetalleItinerario> lstDetalle = null;
			if(detalle.getEsIda()){
				if(mapAsientosIdaRetorno.containsKey(Constantes.ASIENTOS_IDA))
					lstDetalle = mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA);
				else
					lstDetalle = new ArrayList<DetalleItinerario>();
				lstDetalle.add(detalle);
				mapAsientosIdaRetorno.put(Constantes.ASIENTOS_IDA, lstDetalle);
			}else{
				if(mapAsientosIdaRetorno.containsKey(Constantes.ASIENTOS_RETORNO))
					lstDetalle = mapAsientosIdaRetorno.get(Constantes.ASIENTOS_RETORNO);
				else
					lstDetalle = new ArrayList<DetalleItinerario>();
				lstDetalle.add(detalle);
				mapAsientosIdaRetorno.put(Constantes.ASIENTOS_RETORNO, lstDetalle);
			}
		}
		
		int limite = lbxAsientos.getItems().size()/2;
		for(int i=0; i<limite; i++){
			VentaIdaRetorno ventaIdaRetorno = new VentaIdaRetorno();
			ventaIdaRetorno.setDetalleItinerarioIDA(mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA).get(i));
			ventaIdaRetorno.setDetalleItinerarioRETORNO(mapAsientosIdaRetorno.get(Constantes.ASIENTOS_RETORNO).get(i));
			Listitem item = new Listitem();
			Listcell cell = new  Listcell();
			String ruta = mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA).get(i).getRuta().getOrigen()+" - "+mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA).get(i).getRuta().getDestino()+" - "+mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA).get(i).getRuta().getOrigen();
			cell.setLabel(ruta);
			item.appendChild(cell);
			cell = new Listcell();
			cell.setLabel(mapAsientosIdaRetorno.get(Constantes.ASIENTOS_IDA).get(i).getAsiento()+" - "+mapAsientosIdaRetorno.get(Constantes.ASIENTOS_RETORNO).get(i).getAsiento());
			item.appendChild(cell);
			item.setValue(ventaIdaRetorno);
			lbxAsientosIdaRetorno.appendChild(item);
		}		
	}
	
	private void onSelectVentaRemota(){
		if(chkVentaRemota.isChecked()){
			grpVentaRemota.setVisible(true);
			limpiarDatosVentaRemota();
			loadAgenciasRemotas();
		}else{
			grpVentaRemota.setVisible(false);
		}
	}
	
	private void limpiarDatosVentaRemota(){
		cmbUsuarioRemoto.setText("");
		cmbUsuarioRemoto.getItems().clear();
		bndbxUsuarioHardware.setText("");
		lbxUsuarioHardware.getItems().clear();
		cmbUsuarioRemoto.setDisabled(true);
		bndbxUsuarioHardware.setDisabled(true);
		rdPrepagadoRemoto.setChecked(false);
		rdBoletoRemoto.setChecked(false);
		rdElectronicoRemoto.setChecked(false);
	}
	
	private void loadAgenciasRemotas(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipoAgencia.id", Constantes.ID_TIPAGE_TEPSA);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			UtilData.cargarAgencia(cmbAgenciaRemota, false, criteriosBusqueda, criteriosOrdenar);
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	private void loadUsuarioRemoto(){
		try{
			if(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
				Date fechaInicio = new Date(new Date().getTime()-(60*Constantes.MILISEGUNDOS_X_DIA));
				UtilData.cargarUsuariosLiquidacion(cmbUsuarioRemoto, Constantes.FORMAT_DATE.format(fechaInicio), 
						   Constantes.FORMAT_DATE.format(new Date()), false, agencia.getId());
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	private void loadUsuarioHardware(){
		try{
			lbxUsuarioHardware.getItems().clear();
			bndbxUsuarioHardware.setText(Constantes.COMBO_LABEL_SELECCIONE);
			if(cmbAgenciaRemota.getSelectedItem().getValue() instanceof Agencia){
				Agencia agencia = (Agencia)cmbAgenciaRemota.getSelectedItem().getValue();
				/*End Begin 24/10/2016 - abanto*/
//				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//				criteriosBusqueda.put("usuarioHardware.agencia.id", agencia.getId());
//				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//				List<String> criteriosOrdenar = new ArrayList<String>();
//				criteriosOrdenar.add("usuarioHardware.descripcion");
//				criteriosOrdenar.add("serie");
//				List<ControlEspecieValorada> lstControl = ServiceLocator.getControlEspecieValoradaManager().buscarEspecieValoradaPorAgencia(agencia.getId());
//				for(ControlEspecieValorada controlEspecieValorada : lstControl){
//					Listitem listitem = new Listitem();
//					Listcell listcell = new Listcell(controlEspecieValorada.getUsuarioHardware().getDescripcion());
//					listitem.appendChild(listcell);
//					listcell = new Listcell(controlEspecieValorada.getSerie());
//					listitem.appendChild(listcell);
//					listcell = new Listcell(controlEspecieValorada.getCorrelativoActual().toString());
//					listitem.appendChild(listcell);
//					listitem.setValue(controlEspecieValorada);
//					lbxUsuarioHardware.appendChild(listitem);
//				}
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("agencia",agencia );
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				
				List<EspecieValorada> lstEspecieValorada = ServiceLocator.getEspecieValoradaManager().buscarPorX(criteriosBusqueda, null);
				for(EspecieValorada especieValorada: lstEspecieValorada){
					if(especieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_BOLETA_VENTA || especieValorada.getTipoComprobante().getId().intValue()==Constantes.ID_TIPCOM_FACTURA){
						Listitem listitem = new Listitem();
						Listcell listcell = new Listcell(especieValorada.getTipoComprobante().getDenominacion());
						listitem.appendChild(listcell);
						listcell = new Listcell(especieValorada.getSeriefe());
						listcell.setStyle("font-size:11px !important");
						listitem.appendChild(listcell);
						listcell = new Listcell(especieValorada.toString().split("-")[1]);
						listcell.setStyle("font-size:11px !important");
						listitem.appendChild(listcell);
						listitem.setValue(especieValorada);
						lbxUsuarioHardware.appendChild(listitem);
					}
				}
				bndbxUsuarioHardware.setDisabled(false);
				cmbUsuarioRemoto.setDisabled(false);
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	private void onSelectUsuarioHardware(){
		try{
			/*End Begin 24/10/2016 - jabanto*/
//			if(lbxUsuarioHardware.getSelectedItem().getValue() instanceof ControlEspecieValorada){
//				ControlEspecieValorada control = (ControlEspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue();
//				bndbxUsuarioHardware.setText(control.getUsuarioHardware().getDescripcion()+" - "+control.getSerie());
//				bndbxUsuarioHardware.close();			
//			}
			
			/*Begin 24/10/2016 - jabanto*/
			if(lbxUsuarioHardware.getSelectedItem().getValue() instanceof EspecieValorada){
				EspecieValorada especieValorada = (EspecieValorada)lbxUsuarioHardware.getSelectedItem().getValue();
				bndbxUsuarioHardware.setText(especieValorada.getTipoComprobante().getDenominacion()+" - "+especieValorada.toString());
				bndbxUsuarioHardware.close();
			}
		}catch(Exception ex){
			DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	private void mostrarFechaNacimiento(String fechaNacimiento)throws Exception{
		if(fechaNacimiento != null){
//			String dia = fechaNacimiento.substring(0, 2);
//			String mes = fechaNacimiento.substring(3, 5);
//			String anio = fechaNacimiento.substring(6);
//			for(int i=0; i<cmbAnio.getItems().size(); i++){
//				if(cmbAnio.getItems().get(i).getValue().toString().equals(anio))
//					cmbAnio.setSelectedIndex(i);
//			}
//			
//			if(cmbAnio.getSelectedIndex()>=0){
//				for(int i=0; i<cmbMes.getItems().size(); i++){
//					if(((Integer)cmbMes.getItems().get(i).getValue()).intValue()==Integer.valueOf(mes)){
//						cmbMes.setSelectedIndex(i);
//						Util.loadDias(cmbDia, (Integer)cmbMes.getSelectedItem().getValue(), (Integer)cmbAnio.getSelectedItem().getValue());
//					}
//				}
//				
//				for(int i=0; i<cmbDia.getItems().size(); i++){
//					if(((Integer)cmbDia.getItems().get(i).getValue()).intValue() == Integer.valueOf(dia))
//						cmbDia.setSelectedIndex(i);
//				}
//			}
			dtbxFechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(fechaNacimiento));
		}else{
//			cmbAnio.setSelectedIndex(-1);
//			cmbAnio.setText("");
//			cmbMes.setSelectedIndex(-1);
//			cmbMes.setText("");
//			cmbDia.setSelectedIndex(-1);
//			cmbDia.setText("");
			dtbxFechaNacimiento.setValue(null);
		}
	}
	
	private String generarFechaNacimiento(){
		String fechaNacimiento = Constantes.FORMAT_DATE.format(dtbxFechaNacimiento.getValue());
//		if(cmbAnio.getSelectedIndex()>=0 && cmbMes.getSelectedIndex()>=0 && cmbDia.getSelectedIndex()>=0){
//			String dia = "00"+ cmbDia.getText();
//			String mes = "00" + cmbMes.getSelectedItem().getValue();
//			fechaNacimiento = dia.substring(dia.length()-2) + "/" + mes.substring(mes.length()-2) + "/" + cmbAnio.getText();
//		}
		return fechaNacimiento;
	}
	
//	/**
//	 * Valida que la fecha de nacimiento este correcta
//	 * @return true si esta es correcta; false no es correcta.
//	 * @throws Exception 
//	 */
//	private void validarFechaNacimiento() throws Exception{
//		try{
//			//Valida que el anio ingresado sea válido
//			boolean isValidAnio=false;
//			for(int x=0;x<cmbAnio.getItems().size();x++){
//				Comboitem _anio=cmbAnio.getItemAtIndex(x);
//				if(cmbAnio.getText().equals(_anio.getLabel())){
//					isValidAnio=true;
//					break;
//				}
//			}
//			if(isValidAnio==false)
//				throw new FechaNacimientoException(FechaNacimientoException.ANIO_NO_VALID);
//			
//			//Valida que el mes seleccionado se valido
//			boolean isValidMes=false;
//			for(int x=0;x<cmbMes.getItems().size();x++){
//				Comboitem _anio=cmbMes.getItemAtIndex(x);
//				if(cmbMes.getText().equals(_anio.getLabel())){
//					isValidMes=true;
//					break;
//				}
//			}
//			if(isValidMes==false)
//				throw new FechaNacimientoException(FechaNacimientoException.MES_NO_VALID);
//			
//			//Valida que el dia ingresado sea valido
//			boolean isValidDia=false;
//			for(int x=0;x<cmbDia.getItems().size();x++){
//				Comboitem _anio=cmbDia.getItemAtIndex(x);
//				if(cmbDia.getText().equals(_anio.getLabel())){
//					isValidDia=true;
//					break;
//				}
//			}
//			if(isValidDia==false)
//				throw new FechaNacimientoException(FechaNacimientoException.DIA_NO_VALID);
//			
//			
//		}catch(FechaNacimientoException fn){
//			if(fn.getTipo().intValue()==FechaNacimientoException.ANIO_NO_VALID){
//				throw new FechaNacimientoException(FechaNacimientoException.ANIO_NO_VALID);
//			}else if (fn.getTipo().intValue()==FechaNacimientoException.MES_NO_VALID){
//				throw new FechaNacimientoException(FechaNacimientoException.MES_NO_VALID);
//			}else if (fn.getTipo().intValue()==FechaNacimientoException.DIA_NO_VALID){
//				throw new FechaNacimientoException(FechaNacimientoException.DIA_NO_VALID);
//			}
//		}catch (Exception ex){
//			log.error(ex);
//			throw new Exception(ex.getMessage());			
//		}
//	}
	
	private void loadTiposCentroCosto(){
		try {
			cmbGrupoCentroCosto.getItems().clear();
			
			/*Carga el tipo de centro de costo*/
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("concesionario.id", getAgencia().getConcesionario().getId());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<TipoCentroCosto>lstTipoCentroCosto=ServiceLocator.getTipoCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			UtilData.cargarGenericData(cmbGrupoCentroCosto, true);
			for(TipoCentroCosto tipoCentroCosto:lstTipoCentroCosto){
					Comboitem comboitem=new Comboitem(tipoCentroCosto.getDenominacion());
					comboitem.setValue(tipoCentroCosto);
					cmbGrupoCentroCosto.appendChild(comboitem);
			}
			cmbGrupoCentroCosto.setSelectedIndex(0);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void loadCentroCosto(){
		try{
			cmbCentroCosto.getItems().clear();
			
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("concesionario.id", getAgencia().getConcesionario().getId());
			if(cmbGrupoCentroCosto.getSelectedItem().getValue() instanceof TipoCentroCosto)
				criteriosBusqueda.put("tipoCentroCosto", (cmbGrupoCentroCosto.getSelectedItem().getValue()));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			List<CentroCosto> lstCentroCosto = ServiceLocator.getCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			cmbCentroCosto.getItems().clear();
			Comboitem item = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
			cmbCentroCosto.appendChild(item);
			cmbCentroCosto.setSelectedIndex(0);
			if(lstCentroCosto.size()>0){
				for(CentroCosto centroCosto : lstCentroCosto){
					item = new Comboitem(centroCosto.getDenominacion());
					item.setValue(centroCosto);
					cmbCentroCosto.appendChild(item);
				}
				lblCentroCosto.setVisible(true);
				cmbCentroCosto.setVisible(true);
				lblGrupoCentroCosto.setVisible(true);
				cmbGrupoCentroCosto.setVisible(true);
			}else{
				lblCentroCosto.setVisible(false);
				cmbCentroCosto.setVisible(false);
				lblGrupoCentroCosto.setVisible(false);
				cmbGrupoCentroCosto.setVisible(false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
//	/**
//	 * Impresion tuentrada
//	 * @param lstVentas
//	 * @throws Exception
//	 */
//	private void printTicketTuentrada(List<VentaPasaje> lstVentas)throws Exception{
//		Session session = getDesktop().getSession();
//	    HttpSession httpSession = (HttpSession)session.getNativeSession();
//	    httpSession.setAttribute("lstVentas", lstVentas);
//	    
//	    final WndIFrame iFrame = new WndIFrame();
//	    iFrame.btnCerrar.setVisible(false);
//	    iFrame.oThisWindow.setTitle("E-VOUCHER");
//	    iFrame.oThisWindow.setClosable(true);
//		iFrame.setSrc("evoucherTuEntrada.zul");
//		iFrame.setWidth("810");
//		iFrame.setheight("500");
//		iFrame.loadiframe();
//		this.appendChild(iFrame);
//		iFrame.setMode("modal");
//		iFrame.setVisible(false);
//	}
	
	private void exportTicket(List<VentaPasaje> lstVentas){
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("idioma");
		criteriosOrdenar.add("orden");
		List<TerminosVenta> lstTerminos = ServiceLocator.getTerminosVentaManager().buscarPorX(criteriosBusqueda, null);
		
		try{
//			if(venta.getCentroCosto()!=null){
//				CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(venta.getCentroCosto().getId().longValue());
//				venta.setCentroCosto(centroCosto);
//			}
			
			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(lstVentas.get(0).getAgencia().getId().longValue());
			Cliente cliente = (lstVentas.get(0).getCliente()!=null?ServiceLocator.getClienteManager().buscarPorId(lstVentas.get(0).getCliente().getId()):null);
			lstVentas.get(0).setAgencia(agencia);
			lstVentas.get(0).setCliente(cliente);
			Session session = getDesktop().getSession();
	        HttpSession httpSession = (HttpSession)session.getNativeSession();
	        httpSession.setAttribute("lstVentas", lstVentas);
	        httpSession.setAttribute("terminos", lstTerminos);
	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
//	        httpSession.setAttribute("tipoMoneda",tipoCambio!=null?tipoCambio.getTipoMoneda():"");
//	        getDesktop().getExecution().sendRedirect("evoucher.zul", "_black");
//	        Session session = getDesktop().getSession();
//	        HttpSession httpSession = (HttpSession)session.getNativeSession();
//	        httpSession.setAttribute("venta", venta);
//	        httpSession.setAttribute("terminos", lstTerminos);
//	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
	        
	        final WndIFrame iFrame = new WndIFrame();
	        iFrame.btnCerrar.setVisible(false);
	        iFrame.oThisWindow.setTitle("E-VOUCHER");
	        iFrame.oThisWindow.setClosable(true);
			iFrame.setSrc("evoucher.zul");
			iFrame.setWidth("810");
			iFrame.setheight("500");
			iFrame.loadiframe();
			this.appendChild(iFrame);
			iFrame.setMode("modal");
	        
//	        Executions.sendRedirect("/servlet/EticketServlet");
	        
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
    }
	
//	private void exportTicket(VentaPasaje venta){
//		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		List<String> criteriosOrdenar = new ArrayList<String>();
//		criteriosOrdenar.add("idioma");
//		criteriosOrdenar.add("orden");
//		List<TerminosVenta> lstTerminos = ServiceLocator.getTerminosVentaManager().buscarPorX(criteriosBusqueda, null);
//		
//		try{
//			if(venta.getCentroCosto()!=null){
//				CentroCosto centroCosto=ServiceLocator.getCentroCostoManager().buscarPorId(venta.getCentroCosto().getId().longValue());
//				venta.setCentroCosto(centroCosto);
//			}
//			
//			Agencia agencia = ServiceLocator.getAgenciaManager().buscarPorId(venta.getAgencia().getId().longValue());
//			Session session = getDesktop().getSession();
//	        HttpSession httpSession = (HttpSession)session.getNativeSession();
//	        httpSession.setAttribute("venta", venta);
//	        httpSession.setAttribute("terminos", lstTerminos);
//	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
////	        getDesktop().getExecution().sendRedirect("evoucher.zul", "_black");
////	        Session session = getDesktop().getSession();
////	        HttpSession httpSession = (HttpSession)session.getNativeSession();
////	        httpSession.setAttribute("venta", venta);
////	        httpSession.setAttribute("terminos", lstTerminos);
////	        httpSession.setAttribute("concesionario", agencia.getConcesionario().getRazonSocial());
//	        
//	        final WndIFrame iFrame = new WndIFrame();
//	        iFrame.btnCerrar.setVisible(false);
//	        iFrame.oThisWindow.setTitle("E-VOUCHER");
//	        iFrame.oThisWindow.setClosable(true);
//			iFrame.setSrc("evoucher.zul");
//			iFrame.setWidth("810");
//			iFrame.setheight("500");
//			iFrame.loadiframe();
//			this.appendChild(iFrame);
//			iFrame.setMode("modal");
//	        
////	        Executions.sendRedirect("/servlet/EticketServlet");
//	        
//		}catch(Exception ex){
//			ex.printStackTrace();
//			log.error(ex);
//		}
//    }
	
	private void seleccionarPrepagado(){
		if(chkBoletoPrepagado.isChecked()){
			rdVentaNormal.setDisabled(false);
			rdVentaFechaAbierta.setDisabled(true);
			rdVentaIdaVuelta.setDisabled(true);
		}else{
//			rdVentaFechaAbierta.setDisabled(false);
			rdVentaFechaAbierta.setDisabled(agencia.getTipoAgencia().getId().intValue()!=Constantes.ID_TIPAGE_TEPSA);
			rdVentaIdaVuelta.setDisabled(false);
		}
		onSelectDefaultTipoComprobante();
	}
		
	/**
	 * Busca la tarifa para las fechas abiertas
	 * @param detalleItinerario	
	 * @return
	 * @throws Exception
	 */
	private Double getTarifaFechaAbierta(DetalleItinerario detalleItinerario)throws Exception{
		Double tarifaFechaAbierta=ServiceLocator.getTarifaFechaAbierta().buscarTarifa(detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getServicio().getId());
		
//		TipoCambio tipoCambio=Util.getTipoCambioEquiMonedaLocal(getAgencia(), tarifaFechaAbierta);
//		if(tipoCambio!=null)
//			return tipoCambio.getEquivalenteMonedaLocal();
//		else
		return tarifaFechaAbierta;
	}
	
	private void calcularEdad(String fechaNacimiento){
		if(fechaNacimiento != null){
			String anio = fechaNacimiento.substring(fechaNacimiento.length()-4);
			String year = Util.DatetoString(new Date(), "yyyy");
			int edad = Integer.valueOf(year)-Integer.valueOf(anio);
			ntbxEdad.setValue(edad);
		}else
			ntbxEdad.setValue(null);
	}
	
	private String calcularFechaNacimiento(){
		final DateFormat FORMAT = new SimpleDateFormat ("dd/MM");
		String year = Util.DatetoString(new Date(), "yyyy");
		Integer anio = Integer.valueOf(year) - ntbxEdad.getValue();
//		String fechaNacimiento = "01/01/"+anio;
		String fechaNacimiento = FORMAT.format(new Date())+"/"+anio;
		return fechaNacimiento;
	}
	
	
	/**
	 * Carga tarifas, pagos en tro moneda
	 * @throws Exception
	 */
	public void validarOtraMoneda()throws Exception{
//				if(lblTarifa.getValue()!=null && !(lblTarifa.getValue().trim().isEmpty())){
//					lblTarifa.setValue(Util.toNumberFormat(getValorOtraMoneda(Util.parseNumberFormat(lblTarifa.getValue(),2)), 2));
//				}
//				if(lblTarifaRetorno.getValue()!=null && !(lblTarifaRetorno.getValue().trim().isEmpty())){
//					lblTarifaRetorno.setValue(Util.toNumberFormat(getValorOtraMoneda(Util.parseNumberFormat(lblTarifaRetorno.getValue(),2)), 2));
//				}
				/*Tab pagos*/
				/*Primero almacena los valores de las pagos en la clase soles*/
//				pagoSoles=new PagoSoles();
//				pagoSoles.setTarifa(dblTarifa.getValue()!=null?dblTarifa.getValue():.00);
//				pagoSoles.setRecargo(dblRecargo.getValue()!=null?dblRecargo.getValue():.00);
//				pagoSoles.setDescuento(dblDescuento.getValue()!=null?dblDescuento.getValue():.00);
//				pagoSoles.setImportePagado(dblImporte.getValue()!=null?dblImporte.getValue():.00);
//				pagoSoles.setImportePagadoEfectivo(dblImporteEfectivo.getValue()!=null?dblImporteEfectivo.getValue():.00);
//				pagoSoles.setImportePagadoTarjeta(dblImporteTarjeta.getValue()!=null?dblImporteTarjeta.getValue():.00);
//				pagoSoles.setTarifaRetorno(dblTarifaRetorno.getValue()!=null?dblTarifaRetorno.getValue():.00);
//				pagoSoles.setDescuentoRetorno(dblDescuentoRetorno.getValue()!=null?dblDescuentoRetorno.getValue():.00);
				
				/*combierte al tipo de moneda del pais, segun la nacionalidad*/
//				dblTarifa.setValue(getValorOtraMoneda(pagoSoles.getTarifa()));
//				dblRecargo.setValue(getValorOtraMoneda(pagoSoles.getRecargo()));
//				dblDescuento.setValue(getValorOtraMoneda(pagoSoles.getDescuento()));
//				dblImporte.setValue(getValorOtraMoneda(pagoSoles.getImportePagado()));
//				dblImporteEfectivo.setValue(getValorOtraMoneda(pagoSoles.getImportePagadoEfectivo()));
//				dblImporteTarjeta.setValue(getValorOtraMoneda(pagoSoles.getImportePagadoTarjeta()));
//				dblTarifaRetorno.setValue(getValorOtraMoneda(pagoSoles.getTarifaRetorno()));
//				dblDescuentoRetorno.setValue(getValorOtraMoneda(pagoSoles.getDescuentoRetorno()));
		
		/*Valida si la agencia tiene configurada otra moneda diferente a soles para hacer la conversion*/
//		lblTarifa.setValue("0");
		if(tipoMonedaAgencia!=null && tipoMonedaAgencia.getId().intValue()!=Constantes.ID_TIPMON_SOLES){
//			if(lblTarifa.getValue()!=null && !(lblTarifa.getValue().trim().isEmpty())){
//				lblTarifa.setValue(Util.toNumberFormat(getValorOtraMoneda(Util.parseNumberFormat(lblTarifa.getValue(),2)), 2));
//			}
			if(lblTarifaRetorno.getValue()!=null && !(lblTarifaRetorno.getValue().trim().isEmpty())){
				lblTarifaRetorno.setValue(Util.toNumberFormat(getValorOtraMoneda(Util.parseNumberFormat(lblTarifaRetorno.getValue(),2)), 2));
			}
			
			/*combierte al tipo de moneda del pais, segun la nacionalidad*/
			dblTarifaOtraMoneda.setValue(getValorOtraMoneda(dblTarifa.getValue()));
			dblDescuentoOtraMoneda.setValue(getValorOtraMoneda(dblDescuento.getValue()));
			dblImporteOtraMoneda.setValue(getValorOtraMoneda(dblImporte.getValue()));
			dblTarifaRetornoOtraMoneda.setValue(getValorOtraMoneda(dblTarifaRetorno.getValue()));
			dblDescuentoRetornoOtraMoneda.setValue(getValorOtraMoneda(dblDescuentoRetorno.getValue()));
			
			if(cmbTipoMoneda.getSelectedItem().getValue() instanceof TipoMoneda){
				TipoMoneda tipoMonedaSelected=(TipoMoneda)cmbTipoMoneda.getSelectedItem().getValue();
				if(tipoMonedaSelected.getId().intValue()==Constantes.ID_TIPMON_SOLES){
					lblTarifa.setValue(Util.toNumberFormat(dblTarifa.getValue(), 2));
					if(dblTarifaRetorno!=null)
						lblTarifaRetorno.setValue(Util.toNumberFormat(dblTarifaRetorno.getValue(),2));
				}else{
					lblTarifa.setValue(Util.toNumberFormat(dblTarifaOtraMoneda.getValue(), 2));
					if(dblTarifaRetornoOtraMoneda.getValue()!=null)
						lblTarifaRetorno.setValue(Util.toNumberFormat(dblTarifaRetornoOtraMoneda.getValue(),2));
				}
				
				
				
				/*habilita / deshabilita la visibilidad de importes en otra moneda*/
				dblTarifaOtraMoneda.setVisible(tipoMonedaSelected.getId().intValue()!=Constantes.ID_TIPMON_SOLES);
				dblDescuentoOtraMoneda.setVisible(tipoMonedaSelected.getId().intValue()!=Constantes.ID_TIPMON_SOLES);
				dblImporteOtraMoneda.setVisible(tipoMonedaSelected.getId().intValue()!=Constantes.ID_TIPMON_SOLES);
				
				/*habilita / deshabilita la visibilidad de importes en soles*/
				dblTarifa.setVisible(tipoMonedaSelected.getId().intValue()==Constantes.ID_TIPMON_SOLES);
				dblDescuento.setVisible(tipoMonedaSelected.getId().intValue()==Constantes.ID_TIPMON_SOLES);
				dblImporte.setVisible(tipoMonedaSelected.getId().intValue()==Constantes.ID_TIPMON_SOLES);
				
				/*habilita / deshabilita la visibilidad de importes en otra moneda*/
//				dblTarifaOtraMoneda.setVisible(true);
//				dblDescuentoOtraMoneda.setVisible(true);
//				dblImporteOtraMoneda.setVisible(true);
				
				/*habilita / deshabilita la visibilidad de importes en soles*/
//				dblTarifa.setVisible(true);
//				dblDescuento.setVisible(true);
//				dblImporte.setVisible(true);
			}
		}
	}

	/**
	 * Combierte a otra modena los soles
	 * @param value : Valor a combertir
	 * @return
	 * @throws Exception
	 */
	private Double getValorOtraMoneda(Double value)throws Exception{
		TipoCambio  otipoCambio= Util.getTipoCambioEquiMonedaLocal(getAgencia(), value,false);
		if(tipoCambio==null)
			tipoCambio=otipoCambio;
		
		if(otipoCambio!=null)
			return otipoCambio.getEquivalenteMonedaLocal();
		else
			return value;
	}
		

}
