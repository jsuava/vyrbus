/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 1 feb. 2024
 * Hora			: 12:16:05
 */
package pe.itsb.vyrbus.view.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaPartida;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TarifaRegular;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientosID;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.VentaTramo;
import pe.itsb.vyrbus.service.exceptions.CapacityExceedsException;
import pe.itsb.vyrbus.service.exceptions.DuplicateSeatException;
import pe.itsb.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import pe.itsb.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.Asiento;
import pe.itsb.vyrbus.service.mappers.Cafeteria;
import pe.itsb.vyrbus.service.mappers.Coordenada;
import pe.itsb.vyrbus.service.mappers.Monitor;
import pe.itsb.vyrbus.service.mappers.SecuenciaTramo;
import pe.itsb.vyrbus.service.mappers.ServiciosHigienicos;
import pe.itsb.vyrbus.service.mappers.VentaIdaRetorno;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.RESTCiva;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.WSCruzdelsur;
import pe.itsb.vyrbus.service.util.WSFE;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author abant
 *
 */
public class WndVentaReservaNew  extends WndBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window wndVentaReservaNew;
	private Div divPaso_1;
	private Listbox ltbxBusqComprobantesPax;
	private Textbox txtBusqBy;
	private Radio rdBusqIda;
	private Radio rdBusqIdaVuelta;
	private Combobox cmbBusqOrigen;
	private Listbox ltbxBusqDestinos;
	private Vbox vboxCalendarVuelta;
	private Calendar clrBusqIda;
	private Calendar clrBusqIdaVuelta;
	private Button btnBuscar;
	private Button btnBusqCancelar;
	private Tab tabBusqIda;
	private Tab tabBusqVuelta;
	private Label lblBusqInfoViajeIda;
	private Listbox ltbxBusqIda;
	private Label lblBusqInfoViajeVuelta;
	private Listbox ltbxBusqVuelta;
	
	private Div divPaso_2;
	private Tab tabVtaIdaMapa;
	private Tab tabVtaVueltaMapa;
	private Groupbox gpbxVtaIdaMapa;
	private Groupbox gpbxVtaVueltaMapa;
	private Label lblVtaTipoComprobante;
	private Label lblVtaNumeroComprobante;
	private Label lblVtaInfoTipoOperacion;
	private Button btnVtaActualizarMapa;
	private Button btnVtaProgramarServicios;
	private Button btnVtaManifiestoPasajetos;
	private Button btnVtaCarpetaDespacho;
	private Button btnVtaContribucionByAgencia;
	private Button btnVtaReimpresion;
	private Button btnVtaPostergar;
	private Button btnVtaAnular;
	private Button btnVtaLeyendaAsientos;
	private Radio rdVtaVenta;
	private Radio rdVtaReserva;
	private Radio rdVtaPostergacion;
	private Radio rdVtaPostergacionFA;
	private Radio rdVtaCambioNombre;
	private Grid gridVtaInfoViajeIda;
	private Label lblVtaIdaDestino_fecha;
	private Combobox cmbVtaIdaEmbarque_hora;
	private Combobox cmbVtaIdaDesembarque_hora;
	private Label lblVtaIdaAsiento;
	private Doublebox dbxVtaIdaTarifa;
	private Grid gridVtaInfoViajeVuelta;
	private Label lblVtaVueltaDestino_fecha;
	private Combobox cmbVtaVueltaEmbarque_hora;
	private Combobox cmbVtaVueltaDesembarque_hora;
	private Label lblVtaVueltaAsiento;
	private Doublebox dbxVtaVueltaTarifa;
	private Listbox ltbxVtaAsientosSeleccionados;
	private Textbox txtVtaPaxBusqueda;
	private Combobox cmbVtaPaxTipoDocumento;
	private Textbox txtVtaPaxNumeroDocumento;
	private Textbox txtVtaPaxNombres;
	private Textbox txtVtaPaxApePaterno;
	private Textbox txtVtaPaxApeMaterno;
	private Radio rdVtaPaxFemenino;
	private Radio rdVtaPaxMasculino;
	private Intbox itbxVtaPaxEdad;
	private Textbox txtVtaPaxTelefono;
	private Textbox txtVtaPaxCorreo;
	private Checkbox chbxVtaFactura;
	private Textbox txtVtaFacRuc;
	private Textbox txtVtaFacRazonSocial;
	private Textbox txtVtaFacDireccion;
	private Combobox cmbVtaInfTipoDocumento;
	private Textbox txtVtaInfNumeroDocumento;
	private Textbox txtVtaInfNombres;
	private Textbox txtVtaInfApePat;
	private Textbox txtVtaInfApeMat;
	private Combobox cmbVtaInfParentes;
	private Textbox txtVtaInfParentescoInfoOtro;
	private Combobox cmbVtaDocumentoAutoInf;
	private Textbox txtVtaDocumentoAutoInfOtro;
	private Radio rdVtaInfFemenino;
	private Radio rdVtaInfMasculino;
	private Intbox itbxVtaInfEdad;
	private Grid gridVtaDatosPago;
	private Doublebox dbxVtaTotalPagar;
	private Combobox cmbVtaTipoFormaPago;
	private Combobox cmbVtaOperadorTarjeta;
	private Combobox cmbVtaTarjeta;
	private Textbox txtVtaNumeroOperacion;
	private Combobox cmbVtaAutorizaDescuento;
	private Textbox txtVtaAutorizaDescuentoCodigo;
	private Grid gridVtaIdaDatosReserva;
	private Intbox itbxVtaIdaTiempoReserva;
	private Datebox dtbxVtaIdaExpiraReserva;
	private Column clmnVtaIdaDatosReserva;
	private Grid gridVtaVueltaDatosReserva;
	private Intbox itbxVtaVueltaTiempoReserva;
	private Datebox dtbxVtaVueltaExpiraReserva;
	private Separator sptrVtaIdaDatosReserva;
	private Button btnVtaGuardar;
	private Button btnVtaCancelar;
	
	
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPisoHor.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPisoHor.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;

//	private int action = Constantes.FAILURE;
//	private int actionc = Constantes.FAILURE;
	private String prefijoAsiento="";
	private String key = "-1";
	private Map<String, Asiento> mapaAsientosIda = null;
	private Map<String, Asiento> mapaAsientosRetorno = null;
	
	private DetalleItinerario detalleItinerarioIda = null;
	private DetalleItinerario detalleItinerarioVuelta = null;
	private Pasajero pasajero = null;
	private Cliente cliente = null;
	private CanalVenta canalVenta = null;
	private VentaPasaje ventaPasaje = null;
	
	private Window wndModal = null;
	private Listbox ltbxResultBusqueda = null;
	private Date fechaLiquidacion = null;
	
	Timer timerRefreshMapa = null;
	
	
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		try {
			super.onCreate();
			
			divPaso_2.setVisible(false);
			ltbxBusqComprobantesPax.setVisible(false);
			vboxCalendarVuelta.setVisible(false);
			rdBusqIda.setChecked(true);
			
			UtilData.cargarDataCombo(cmbBusqOrigen, Localidad.class, false);
			Util.seleccionarValorItemCombo(Localidad.class, cmbBusqOrigen, getAgencia().getLocalidad().getId());			
			//Carga localidades destino
			List<Localidad> listLocalidades = ServiceLocator.getLocalidadManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			Listitem item = new Listitem();
			item.appendChild(new Listcell("--TODOS--"));
			ltbxBusqDestinos.appendChild(item);
			for(Localidad localidad: listLocalidades) {
				if(localidad.getId()!=getAgencia().getLocalidad().getId()) {
					item = new Listitem();
					Listcell cell = new Listcell(localidad.getDenominacion());
					item.appendChild(cell);
					item.setValue(localidad);
					ltbxBusqDestinos.appendChild(item);
				}
			}
			ltbxBusqDestinos.setSelectedIndex(0);
			
			Date fechaActual = Constantes.FORMAT_DATE.parse(new MyTime().dateServer());			
			clrBusqIda.setValue(fechaActual);			
			
			//Carga los tipos de documento para el pasajero
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);		
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			UtilData.cargarDataCombo(cmbVtaPaxTipoDocumento, TipoDocumento.class, criteriosBusqueda, null);
			//Carga los tipos de documento para el Infante
			criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);		
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			UtilData.cargarDataCombo(cmbVtaInfTipoDocumento, TipoDocumento.class, criteriosBusqueda, null);
			//Tipo de pago
			loadTipoFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
			
			dbxVtaIdaTarifa.setLocale(Locale.US);
			dbxVtaVueltaTarifa.setLocale(Locale.US);
			dbxVtaTotalPagar.setLocale(Locale.US);
						
			canalVenta = (CanalVenta)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
			fechaLiquidacion = (Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
									
			cleanBusquedaItienrarios();
			enabledControlsVueltaBusqItienrario();
			enabledControlsVueltaVenta();
			cleanDatosVenta();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub		
		try {
			super.initComponents();
			
			wndVentaReservaNew = (Window)this.getFellow("wndVentaReservaNew");
			divPaso_1 = (Div)this.getFellow("divPaso_1");
			ltbxBusqComprobantesPax = (Listbox)this.getFellow("ltbxBusqComprobantesPax");
			txtBusqBy = (Textbox)this.getFellow("txtBusqBy");
			rdBusqIda = (Radio)this.getFellow("rdBusqIda");
			rdBusqIdaVuelta = (Radio)this.getFellow("rdBusqIdaVuelta");
			cmbBusqOrigen = (Combobox)this.getFellow("cmbBusqOrigen");
			ltbxBusqDestinos = (Listbox)this.getFellow("ltbxBusqDestinos");
			vboxCalendarVuelta = (Vbox)this.getFellow("vboxCalendarVuelta");
			clrBusqIda = (Calendar)this.getFellow("clrBusqIda");
			clrBusqIdaVuelta = (Calendar)this.getFellow("clrBusqIdaVuelta");
			btnBuscar = (Button)this.getFellow("btnBuscar");
			btnBusqCancelar = (Button)this.getFellow("btnBusqCancelar");
			tabBusqIda = (Tab)this.getFellow("tabBusqIda");
			tabBusqVuelta = (Tab)this.getFellow("tabBusqVuelta");
			lblBusqInfoViajeIda = (Label)this.getFellow("lblBusqInfoViajeIda");
			ltbxBusqIda = (Listbox)this.getFellow("ltbxBusqIda");
			lblBusqInfoViajeVuelta = (Label)this.getFellow("lblBusqInfoViajeVuelta");
			ltbxBusqVuelta = (Listbox)this.getFellow("ltbxBusqVuelta");			
			divPaso_2 = (Div)this.getFellow("divPaso_2");
			tabVtaIdaMapa = (Tab)this.getFellow("tabVtaIdaMapa");
			tabVtaVueltaMapa =  (Tab)this.getFellow("tabVtaVueltaMapa");
			gpbxVtaIdaMapa = (Groupbox)this.getFellow("gpbxVtaIdaMapa");
			gpbxVtaVueltaMapa = (Groupbox)this.getFellow("gpbxVtaVueltaMapa");
			lblVtaTipoComprobante = (Label)this.getFellow("lblVtaTipoComprobante");
			lblVtaNumeroComprobante = (Label)this.getFellow("lblVtaNumeroComprobante");
			lblVtaInfoTipoOperacion = (Label)this.getFellow("lblVtaInfoTipoOperacion");
			btnVtaActualizarMapa = (Button)this.getFellow("btnVtaActualizarMapa");
			btnVtaProgramarServicios = (Button)this.getFellow("btnVtaProgramarServicios");
			btnVtaManifiestoPasajetos = (Button)this.getFellow("btnVtaManifiestoPasajetos");
			btnVtaCarpetaDespacho = (Button)this.getFellow("btnVtaCarpetaDespacho");
			btnVtaContribucionByAgencia = (Button)this.getFellow("btnVtaContribucionByAgencia");
			btnVtaReimpresion = (Button)this.getFellow("btnVtaReimpresion");
			btnVtaPostergar = (Button)this.getFellow("btnVtaPostergar");
			btnVtaAnular = (Button)this.getFellow("btnVtaAnular");
			btnVtaLeyendaAsientos = (Button)this.getFellow("btnVtaLeyendaAsientos");
			rdVtaVenta = (Radio)this.getFellow("rdVtaVenta");
			rdVtaReserva = (Radio)this.getFellow("rdVtaReserva");
			rdVtaPostergacion = (Radio)this.getFellow("rdVtaPostergacion");
			rdVtaPostergacionFA = (Radio)this.getFellow("rdVtaPostergacionFA");
			rdVtaCambioNombre = (Radio)this.getFellow("rdVtaCambioNombre");
			gridVtaInfoViajeIda = (Grid)this.getFellow("gridVtaInfoViajeIda");
			lblVtaIdaDestino_fecha = (Label)this.getFellow("lblVtaIdaDestino_fecha");
			cmbVtaIdaEmbarque_hora = (Combobox)this.getFellow("cmbVtaIdaEmbarque_hora");
			cmbVtaIdaDesembarque_hora = (Combobox)this.getFellow("cmbVtaIdaDesembarque_hora");
			lblVtaIdaAsiento = (Label)this.getFellow("lblVtaIdaAsiento");
			dbxVtaIdaTarifa = (Doublebox)this.getFellow("dbxVtaIdaTarifa");
			gridVtaInfoViajeVuelta = (Grid)this.getFellow("gridVtaInfoViajeVuelta");
			lblVtaVueltaDestino_fecha = (Label)this.getFellow("lblVtaVueltaDestino_fecha");
			cmbVtaVueltaEmbarque_hora = (Combobox)this.getFellow("cmbVtaVueltaEmbarque_hora");
			cmbVtaVueltaDesembarque_hora = (Combobox)this.getFellow("cmbVtaVueltaDesembarque_hora");
			lblVtaVueltaAsiento = (Label)this.getFellow("lblVtaVueltaAsiento");
			dbxVtaVueltaTarifa = (Doublebox)this.getFellow("dbxVtaVueltaTarifa");
			ltbxVtaAsientosSeleccionados = (Listbox)this.getFellow("ltbxVtaAsientosSeleccionados");
			txtVtaPaxBusqueda = (Textbox)this.getFellow("txtVtaPaxBusqueda");
			cmbVtaPaxTipoDocumento = (Combobox)this.getFellow("cmbVtaPaxTipoDocumento");
			txtVtaPaxNumeroDocumento = (Textbox)this.getFellow("txtVtaPaxNumeroDocumento");
			txtVtaPaxNombres = (Textbox)this.getFellow("txtVtaPaxNombres");
			txtVtaPaxApePaterno = (Textbox)this.getFellow("txtVtaPaxApePaterno");
			txtVtaPaxApeMaterno = (Textbox)this.getFellow("txtVtaPaxApeMaterno");
			rdVtaPaxFemenino = (Radio)this.getFellow("rdVtaPaxFemenino");
			rdVtaPaxMasculino = (Radio)this.getFellow("rdVtaPaxMasculino");
			itbxVtaPaxEdad = (Intbox)this.getFellow("itbxVtaPaxEdad");
			txtVtaPaxTelefono = (Textbox)this.getFellow("txtVtaPaxTelefono");
			txtVtaPaxCorreo = (Textbox)this.getFellow("txtVtaPaxCorreo");
			chbxVtaFactura = (Checkbox)this.getFellow("chbxVtaFactura");
			txtVtaFacRuc = (Textbox)this.getFellow("txtVtaFacRuc");
			txtVtaFacRazonSocial = (Textbox)this.getFellow("txtVtaFacRazonSocial");
			txtVtaFacDireccion = (Textbox)this.getFellow("txtVtaFacDireccion");
			cmbVtaInfTipoDocumento = (Combobox)this.getFellow("cmbVtaInfTipoDocumento");
			txtVtaInfNumeroDocumento = (Textbox)this.getFellow("txtVtaInfNumeroDocumento");
			txtVtaInfNombres = (Textbox)this.getFellow("txtVtaInfNombres");
			txtVtaInfApePat = (Textbox)this.getFellow("txtVtaInfApePat");
			txtVtaInfApeMat = (Textbox)this.getFellow("txtVtaInfApeMat");
			cmbVtaInfParentes = (Combobox)this.getFellow("cmbVtaInfParentes");
			txtVtaInfParentescoInfoOtro = (Textbox)this.getFellow("txtVtaInfParentescoInfoOtro");
			cmbVtaDocumentoAutoInf = (Combobox)this.getFellow("cmbVtaDocumentoAutoInf");
			txtVtaDocumentoAutoInfOtro = (Textbox)this.getFellow("txtVtaDocumentoAutoInfOtro");
			rdVtaInfFemenino = (Radio)this.getFellow("rdVtaInfFemenino");
			rdVtaInfMasculino = (Radio)this.getFellow("rdVtaInfMasculino");
			itbxVtaInfEdad = (Intbox)this.getFellow("itbxVtaInfEdad");
			gridVtaDatosPago = (Grid)this.getFellow("gridVtaDatosPago");
			dbxVtaTotalPagar = (Doublebox)this.getFellow("dbxVtaTotalPagar");
			cmbVtaTipoFormaPago = (Combobox)this.getFellow("cmbVtaTipoFormaPago");
			cmbVtaOperadorTarjeta = (Combobox)this.getFellow("cmbVtaOperadorTarjeta");
			cmbVtaTarjeta = (Combobox)this.getFellow("cmbVtaTarjeta");
			txtVtaNumeroOperacion = (Textbox)this.getFellow("txtVtaNumeroOperacion");
			cmbVtaAutorizaDescuento = (Combobox)this.getFellow("cmbVtaAutorizaDescuento");
			txtVtaAutorizaDescuentoCodigo = (Textbox)this.getFellow("txtVtaAutorizaDescuentoCodigo");
			gridVtaIdaDatosReserva = (Grid)this.getFellow("gridVtaIdaDatosReserva");
			itbxVtaIdaTiempoReserva = (Intbox)this.getFellow("itbxVtaIdaTiempoReserva");
			dtbxVtaIdaExpiraReserva = (Datebox)this.getFellow("dtbxVtaIdaExpiraReserva");
			clmnVtaIdaDatosReserva = (Column)this.getFellow("clmnVtaIdaDatosReserva");
			gridVtaVueltaDatosReserva = (Grid)this.getFellow("gridVtaVueltaDatosReserva");
			itbxVtaVueltaTiempoReserva = (Intbox)this.getFellow("itbxVtaVueltaTiempoReserva");
			dtbxVtaVueltaExpiraReserva = (Datebox)this.getFellow("dtbxVtaVueltaExpiraReserva");
			sptrVtaIdaDatosReserva = (Separator)this.getFellow("sptrVtaIdaDatosReserva");
			btnVtaGuardar = (Button)this.getFellow("btnVtaGuardar");
			btnVtaCancelar = (Button)this.getFellow("btnVtaCancelar");
			
			
			///EVENTS
			rdBusqIda.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_rdBusqIda();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			rdBusqIdaVuelta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
					
						onCheck_rdBusqIdaVuelta();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			cmbBusqOrigen.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					cleanBusquedaItienrarios();
				}
			});
			ltbxBusqDestinos.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					cleanBusquedaItienrarios();
				}
			});
			btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnBuscar();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnBusqCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnBusqCancelar();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			clrBusqIda.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub							
					try {
						
						onChange_clBusqIda(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			clrBusqIdaVuelta.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub							
					try {
						
						onChange_clrBusqIdaVuelta();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			
			
			cmbVtaIdaEmbarque_hora.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbVtaIdaEmbarque_hora();						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			cmbVtaVueltaEmbarque_hora.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbVtaVueltaEmbarque_hora();						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			rdVtaVenta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_rdVtaVenta(event);						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			rdVtaReserva.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_rdVtaReserva(event);						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			ltbxVtaAsientosSeleccionados.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
					
						onSelect_ltbxVtaAsientosSeleccionados();						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			cmbVtaPaxTipoDocumento.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbVtaPaxTipoDocumento();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			txtVtaPaxBusqueda.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
					
//						buscarPasajero(((Textbox)event.getTarget()).getText().trim().toUpperCase(),event);
						ltbxVtaAsientosSeleccionados.setFocus(true);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});		
			txtVtaPaxBusqueda.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
					
						buscarPasajero(((Textbox)event.getTarget()).getText().trim().toUpperCase(), event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});		
			chbxVtaFactura.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_chbxVtaFactura();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			txtVtaFacRuc.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						buscarCliente(((Textbox)event.getTarget()).getText().trim().toUpperCase());
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			txtVtaFacRuc.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						buscarCliente(((Textbox)event.getTarget()).getText().trim().toUpperCase());
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			cmbVtaTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbVtaTipoFormaPago();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			cmbVtaOperadorTarjeta.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbVtaOperadorTarjeta();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			itbxVtaIdaTiempoReserva.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_itbxVtaIdaTiempoReserva();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			itbxVtaIdaTiempoReserva.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(!dtbxVtaIdaExpiraReserva.isDisabled())
						dtbxVtaIdaExpiraReserva.setFocus(true);
					else if(gridVtaVueltaDatosReserva.isVisible() && !itbxVtaVueltaTiempoReserva.isDisabled())
						itbxVtaVueltaTiempoReserva.setFocus(true);
					else if(gridVtaVueltaDatosReserva.isVisible() && !dtbxVtaVueltaExpiraReserva.isDisabled())
						dtbxVtaVueltaExpiraReserva.setFocus(true);
					else 
						btnVtaGuardar.setFocus(true);
				}
			});
			dtbxVtaIdaExpiraReserva.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(gridVtaVueltaDatosReserva.isVisible() && !itbxVtaVueltaTiempoReserva.isDisabled())
						itbxVtaVueltaTiempoReserva.setFocus(true);
					else if(gridVtaVueltaDatosReserva.isVisible() && !dtbxVtaVueltaExpiraReserva.isDisabled())
						dtbxVtaVueltaExpiraReserva.setFocus(true);
					else 
						btnVtaGuardar.setFocus(true);
				}
			});
			itbxVtaVueltaTiempoReserva.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_itbxVtaVueltaTiempoReserva();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			itbxVtaVueltaTiempoReserva.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if(!dtbxVtaVueltaExpiraReserva.isDisabled())
						dtbxVtaVueltaExpiraReserva.setFocus(true);					
					else 
						btnVtaGuardar.setFocus(true);
				}
			});
			dtbxVtaVueltaExpiraReserva.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub 
					btnVtaGuardar.setFocus(true);
				}
			});
			btnVtaActualizarMapa.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaActualizarMapa();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaReimpresion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaReimprimir();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaAnular();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaCancelar();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaGuargar();;
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdBusqIda()throws Exception{	
		enabledControlsVueltaBusqItienrario();
		cleanBusquedaItienrarios();
		
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdBusqIdaVuelta()throws Exception{		
		cleanBusquedaItienrarios();
		enabledControlsVueltaBusqItienrario();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnBuscar()throws Exception {
		
		buscarItinerarios();
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnBusqCancelar()throws Exception{
		rdBusqIda.setChecked(true);
		onCheck_rdBusqIda();
		
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_clBusqIda(Event event)throws Exception{
		Date fechaActual = Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
		if(clrBusqIda.getValue().getTime() < fechaActual.getTime()) {		
			clrBusqIda.setValue(fechaActual);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.FechaViajeNoValida"));
			return;
		}
		
		if(rdBusqIdaVuelta.isChecked()) {
			Date fechaIda = clrBusqIda.getValue(); 
			Date fechaVuelta = clrBusqIdaVuelta.getValue();
			boolean sugerirFechaVuelta = (fechaIda.getTime() >= fechaVuelta.getTime());
			
			if(sugerirFechaVuelta)
				clrBusqIdaVuelta.setValue(getFechaSugerenciaVuelta(fechaIda));
		}			
		buscarItinerarios();
		
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_clrBusqIdaVuelta()throws Exception{
		if(rdBusqIdaVuelta.isChecked()) {
			Date fechaIda = clrBusqIda.getValue();
			Date fechaVuelta = clrBusqIdaVuelta.getValue();
			
			if(fechaIda.getTime() >= fechaVuelta.getTime())
				clrBusqIdaVuelta.setValue(getFechaSugerenciaVuelta(fechaIda));						
		}		
		
		buscarItinerarios();
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void buscarItinerarios()throws Exception{
		cleanBusquedaItienrarios();
		boolean isTipoIdaVuelta = rdBusqIdaVuelta.isChecked();
		
		if(!(cmbBusqOrigen.getSelectedItem().getValue() instanceof Localidad)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"));
			return;
		}else if(isTipoIdaVuelta && (ltbxBusqDestinos.getSelectedCount()==0 || ltbxBusqDestinos.getSelectedItem().getValue()==null)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"));
			return;
		}
		
		String empresa = "";
		String origen = (((Localidad)cmbBusqOrigen.getSelectedItem().getValue()).getDenominacion());
		String destino = (ltbxBusqDestinos.getSelectedCount()>0 && ltbxBusqDestinos.getSelectedItem().getValue()!=null?((Localidad)ltbxBusqDestinos.getSelectedItem().getValue()).getDenominacion():"");
		String fechaIda = Constantes.FORMAT_DATE.format(clrBusqIda.getValue());
		String fechaVuelta = (isTipoIdaVuelta? Constantes.FORMAT_DATE.format(clrBusqIdaVuelta.getValue()): null);
		
		//Busca itinerarios para la Ida
		List<DetalleItinerario> lstItinerariosIda = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaIda, origen, destino, empresa);
		
		//Busca itinerarios para la Vuelta
		List<DetalleItinerario> lstItinerariosVuelta =  new ArrayList<DetalleItinerario>();
		if(isTipoIdaVuelta)
			lstItinerariosVuelta = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaVuelta, destino, origen, empresa);
		
		//Carga itinerarios de Ida
		loadItienrariosBusqueda(lstItinerariosIda, true, fechaIda);
		
		//Carga itinerarios de para la vuelta
		if(isTipoIdaVuelta)
			loadItienrariosBusqueda(lstItinerariosVuelta, false, fechaVuelta);
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void cleanBusquedaItienrarios() throws Exception{
		Util.limpiarListbox(ltbxBusqIda);
		Util.limpiarListbox(ltbxBusqVuelta);
		lblBusqInfoViajeIda.setValue("");
		lblBusqInfoViajeVuelta.setValue("");
		
	}
	
	/**
	 * Sugiere una fecha para la vuelta
	 * @param fechaIda: Fecha de Ida
	 * @return Retorna la fecha sugerida para el retono
	 * @throws Exception
	 */
	private Date getFechaSugerenciaVuelta(Date fechaIda)throws Exception{
		Date fechaVueltaSugerencia = new Date(fechaIda.getTime() + Constantes.MILISEGUNDOS_X_DIA);
		
		return fechaVueltaSugerencia;
	}
	
	/**
	 * Habilita/Deshabilita los controles del retorno en la busqueda
	 * @throws Exception
	 */
	private void enabledControlsVueltaBusqItienrario()throws Exception{
		Util.limpiarListbox(ltbxBusqVuelta);
		lblBusqInfoViajeVuelta.setValue("");
		clrBusqIdaVuelta.setValue(clrBusqIda.getValue());		
		
		boolean enabledCtrlsVuelta = rdBusqIdaVuelta.isChecked();
		
		vboxCalendarVuelta.setVisible(enabledCtrlsVuelta);
		tabBusqVuelta.setDisabled(!enabledCtrlsVuelta);
		
		if(!enabledCtrlsVuelta)
			tabBusqIda.setSelected(true);	
		
		if(enabledCtrlsVuelta) {
			Date fechaIda = clrBusqIda.getValue(); 
			clrBusqIdaVuelta.setValue(getFechaSugerenciaVuelta(fechaIda));
		}
		
	}
	
	/**
	 * Carga lista de itinerarios
	 * @param listItinerarios: List con los itinerarios
	 * @param isIda: (True) indica que son itinerarios de Ida, (False) Itienrarios para la Vuelta 
	 * @throws Exception
	 */
	private void loadItienrariosBusqueda(List<DetalleItinerario> listItinerarios, boolean isIda, String fechaViaje)throws Exception{
		Listbox listbox = (isIda? ltbxBusqIda: ltbxBusqVuelta);
		Label lblInfoViaje = (isIda? lblBusqInfoViajeIda: lblBusqInfoViajeVuelta);
		
		String nameDiaSemana = Util.getDiaSemana(fechaViaje).toUpperCase();
		String nameMes = Util.getNombreMes(fechaViaje).toUpperCase();
		String diaSemana = Constantes.FORMAT_DAY.format(Constantes.FORMAT_DATE.parse(fechaViaje));
		String anio = Constantes.FORMAT_YEAR.format(Constantes.FORMAT_DATE.parse(fechaViaje));
		lblInfoViaje.setValue(nameDiaSemana+" "+diaSemana+ " DE " +nameMes+ " DE "+anio);
		
		for(DetalleItinerario detalleItinerario : listItinerarios){
			Listitem item = new Listitem();
			Listcell cell =  new Listcell(detalleItinerario.getItinerario().getEmpresa().getNombreCorto()); 
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getRuta().toString());
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getAgenciaPartida().getNombreCorto());
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getHoraPartida());
			cell.setStyle("font-size:11px !important");
			cell.setTooltiptext("Hora de Partida");
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
			item.appendChild(cell);			
			/*Tarifa Estandar*/
			List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1,
					detalleItinerario.getItinerario().getServicio().getId(),
					detalleItinerario.getRuta().getId(), fechaViaje,
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
			Listcell cellTarifaStandar = new Listcell(strTarifas);
			cellTarifaStandar.setStyle("font-size:13px !important;");		
			item.appendChild(cellTarifaStandar);
			
			Integer asientosDisponiblesRuta = getCantidadAsientosdisponibles(detalleItinerario);
			cell = new Listcell(asientosDisponiblesRuta.toString());
			cell.setStyle("font-size:13px !important;font-weight: bold");
			cell.setTooltiptext(cell.getLabel() +" asientos disponibles");
			item.appendChild(cell);

			cell = new Listcell(detalleItinerario.getItinerario().getBus()!=null?detalleItinerario.getItinerario().getBus().getNumeroPlaca():"-");
			item.appendChild(cell);
			item.setValue(detalleItinerario);
			
			cell = new Listcell();
			Button btnSeleccionar = new Button("Seleccionar");
			btnSeleccionar.setAttribute(Listitem.class.getName(), item);
			btnSeleccionar.setHeight("20px");
			btnSeleccionar.setAutag("self");
			cell.appendChild(btnSeleccionar);
			item.appendChild(cell);
			
			btnSeleccionar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub					
					try {						
						
						onClick_btnSeleccionarItinerario(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnSeleccionarItinerario(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			listbox.appendChild(item);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private Integer getCantidadAsientosdisponibles(DetalleItinerario detalleItinerario)throws Exception{
		Servicio servicio = null;
		/*	Busca el mapa del bus de acuerdo al servicio	*/
		List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBusHorizontal(detalleItinerario.getItinerario().getServicio().getId(), Constantes.VALUE_ACTIVO);

		/*	Creamos un Map el cual tendra como key un objeto coordenada y como valor el objeto MapaBus esto es deacuerdo a
		 * lo que tenemos en la Base de datos	*/
		Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<>();
		for(MapaBus mapaBus : lstMapaBus){
			Coordenada coordenada = new Coordenada(mapaBus.getNumeroFila(), mapaBus.getNumeroColumna(), mapaBus.getNumeroPiso());
			mapCoordenadas.put(coordenada, mapaBus);
		}

		if(lstMapaBus.size()>0)
			servicio = lstMapaBus.get(0).getServicio();

		int nPisos = servicio.getNumeroPisos();
		int nFilas = servicio.getNumeroFilasPiso1();
		int nColumnas = servicio.getNumeroColumnasPiso1();
//		int numeroAsiento = 0;
		Map<String, Asiento> mapaAsientos = new HashMap<>();
//		Hlayout ohlayout = new Hlayout();


		/*	Recorremos la cantidad de pisos del servicio	*/
		for(int i = 0; i < nPisos; i++){
			/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
//			if(i == 1){
				nFilas = servicio.getNumeroFilasPiso2();
				nColumnas = servicio.getNumeroColumnasPiso2();
//				numeroAsiento = 0;
				/*	Recorremos la cantidad de filas	*/
				for(int j=0; j<nFilas; j++){
					/*	Recorremos la cantidad de columnas	*/
					for(int k=0; k<nColumnas; k++){
						/*	Definimos la coordenada actual	*/
						String coordenadaActual = j+"-"+k+"-"+i;
	
						/*	Iteramos el mapa de coordenadas creado al inicio y comparamos	*/
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								/*	Obtenemos el objeto y empezamos a setear las imagenes	*/
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
	
//								HashMap<String, String> propiedades = new HashMap<>();
//								numeroAsiento++;
	
								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
	//								propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
//									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
//									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
//									asiento.setFila(j);
//									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
//									asiento.setNumeroZona(objetoBus.getNumeroZona());
//									asiento.setPropiedades(propiedades);
//									asiento.setDraggable("true");
//									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
//									asiento.setStyle("cursor:pointer");
//									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
								}
								break;
							}
						}					
					}
				}		
//			}
		}
		
		
		onCleanMap(mapaAsientos);
		/*Busca configuracion para la validacion de la venta adelantada*/
		VentaTramo ventaTramo=ServiceLocator.getVentaTramoManager().buscarPorItinerarioRuta(detalleItinerario.getItinerario(), detalleItinerario.getRuta());

		/*	Obtenemos el subconjunto que queremos buscar segun la ruta*/
		List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(),detalleItinerario.getRuta().getLocalidadDestino().getId());

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
						if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA))							
							asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
						else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA))							
							asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);

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
				if(asiento.getSrc()!=null && asiento.getEstadoAsiento().intValue()==Constantes.ASIENTO_DISPONIBLE &&
				   !asiento.getSrc().equals(Constantes.ICON_SEMI_OCUPADO+asiento.getNumeroAsiento()+Constantes.IMAGE_EXTENSION)){
					nOcupados++;
				}
			}
		}
		
		Integer disponibles = (detalleItinerario.getItinerario().getServicio().getNumeroPisos()==2
				 ? (detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1() +
						 detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso2()) - nOcupados
				 : detalleItinerario.getItinerario().getServicio().getNumeroAsientosPiso1() - nOcupados);
		
		
		
		return disponibles;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void onClick_btnSeleccionarItinerario(Event event)throws Exception{
		cleanDatosVenta();
		enabledControlsVueltaVenta();
		
		Listitem item = null;
		if(event.getTarget() instanceof Listitem)
			item = (Listitem)event.getTarget();
		else if(event.getTarget() instanceof Button)
			item = (Listitem) ((Button)event.getTarget()).getParent().getParent();
		
		if(item!=null) {
			Listbox listboxSelect = ((Listbox)item.getParent());
			listboxSelect.setSelectedItem(item);
			
			//seleccionar el tab del retorno
			if(listboxSelect.getId().equals(ltbxBusqIda.getId()) && !tabBusqVuelta.isDisabled()) {
				tabBusqVuelta.setSelected(true);
				return;
			}
			
			if(ltbxBusqIda.getSelectedCount()==0) {
				tabBusqIda.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioIdaSeleccionado"), ltbxBusqIda);
				return;
			}else if(!tabBusqVuelta.isDisabled() && ltbxBusqVuelta.getSelectedCount()==0) {
				tabBusqVuelta.setSelected(true);
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noItinerarioRetornoSeleccionado"), ltbxBusqVuelta);
				return;
			}
			
			
			DetalleItinerario detItinerario=ltbxBusqIda.getSelectedItem().getValue();
			int index=detItinerario.getHoraPartida().toString().indexOf(":");
			String sHora=detItinerario.getHoraPartida().substring(0,index);
			String sMinuto=detItinerario.getHoraPartida().substring(index+1,detItinerario.getHoraPartida().length());
			//Fecha hora de partida
			String fecha=Constantes.FORMAT_DATE.format(detItinerario.getFechaPartida());
			Date dFecHoraPart=Constantes.FORMAT_DATE.parse(fecha);
			dFecHoraPart.setHours(Integer.valueOf(sHora));
			dFecHoraPart.setMinutes(Integer.valueOf(sMinuto));

			/*Valida si la ruta esta configurada para permitir la venta antes o despuesta de la hora de salida*/
			if (!(UtilData.permiteVentaByTramo(detItinerario.getRuta().getId(),
											   detItinerario.getItinerario().getId(),
											   Constantes.FORMAT_DATE.format(detItinerario.getFechaPartida())))){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.ventaNoPermitida"));
				return;
			}
			
			Date dFechHorActual = Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
			if(dFecHoraPart.getTime()<dFechHorActual.getTime()){
				Messagebox.show(Messages.getString("WndVentaReserva.information.noHoraMenorServicio"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try {
							if(e.getName().equals("onYes"))
								onClick_btnSeleccionarItinerarioConfirm(event);
							
						} catch (Exception e2) {
							e2.printStackTrace();
							DlgMessage.error(e2.getMessage());
						}
						
					}
					
				});
			}else
				onClick_btnSeleccionarItinerarioConfirm(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void onClick_btnSeleccionarItinerarioConfirm(Event event)throws Exception{
		detalleItinerarioIda = ltbxBusqIda.getSelectedItem().getValue();
		crearEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), gpbxVtaIdaMapa, false, detalleItinerarioIda, mapaAsientosIda, null);
		/*	Si se trata de un ida y vuelta	*/
		if(rdBusqIdaVuelta.isChecked()){
			detalleItinerarioVuelta = ltbxBusqVuelta.getSelectedItem().getValue();
			crearEstructura(detalleItinerarioVuelta.getItinerario().getServicio().getId(), gpbxVtaVueltaMapa, true, detalleItinerarioVuelta, mapaAsientosRetorno, null);
		}
		
		lblVtaIdaDestino_fecha.setValue(detalleItinerarioIda.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()));
		loadPuntoEmbarque(detalleItinerarioIda, cmbVtaIdaEmbarque_hora);
		loadPuntoDesembarque(detalleItinerarioIda, cmbVtaIdaDesembarque_hora);
		if(cmbVtaIdaEmbarque_hora.getItemCount()==2)
			cmbVtaIdaEmbarque_hora.setSelectedIndex(1);
		else if(cmbVtaIdaEmbarque_hora.getSelectedIndex()==0) {
			//Selecciona por defecto el embarque la agencia desde donde se esta vendiento el Pasaje
			seleccionarPuntoEmbarque(true, getAgencia());
		}
			
		if(cmbVtaIdaDesembarque_hora.getItemCount()==2)
			cmbVtaIdaDesembarque_hora.setSelectedIndex(1);
		lblVtaIdaAsiento.setValue("-");
		dbxVtaIdaTarifa.setValue(.00);
		if(detalleItinerarioVuelta!=null) {
			lblVtaVueltaDestino_fecha.setValue(detalleItinerarioVuelta.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()));
			loadPuntoEmbarque(detalleItinerarioVuelta, cmbVtaVueltaEmbarque_hora);
			loadPuntoDesembarque(detalleItinerarioVuelta, cmbVtaVueltaDesembarque_hora);
			if(cmbVtaVueltaEmbarque_hora.getItemCount()==2)
				cmbVtaVueltaEmbarque_hora.setSelectedIndex(1);
			if(cmbVtaVueltaDesembarque_hora.getItemCount()==2)
				cmbVtaVueltaDesembarque_hora.setSelectedIndex(1);			
			lblVtaVueltaAsiento.setValue("-");
			dbxVtaVueltaTarifa.setValue(.00);
		}
				
		resertOperacionAsiento();		
		btnVtaActualizarMapa.setDisabled(false);
		
		tabVtaIdaMapa.setSelected(true);
		divPaso_1.setVisible(false);
		divPaso_2.setVisible(true);
		
		//**********************************************
		autoRefreshMapaBus();
		//**********************************************
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_cmbVtaIdaEmbarque_hora()throws Exception{
		if(rdVtaReserva.isChecked()) {
			dtbxVtaIdaExpiraReserva.setValue(null);
			if(cmbVtaIdaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida) {
				Integer tiempoReserva = (itbxVtaIdaTiempoReserva.getValue()!=null && itbxVtaIdaTiempoReserva.getValue()>0? itbxVtaIdaTiempoReserva.getValue():null);
				boolean isCorrect = calcularFechaExpiraReserva(true, tiempoReserva);
				if(!isCorrect)
					itbxVtaIdaTiempoReserva.setFocus(true);
			}			
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_cmbVtaVueltaEmbarque_hora()throws Exception{
		if(rdVtaReserva.isChecked()) {
			dtbxVtaVueltaExpiraReserva.setValue(null);
			if(cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida) {
				Integer tiempoReserva = (itbxVtaVueltaTiempoReserva.getValue()!=null && itbxVtaVueltaTiempoReserva.getValue()>0? itbxVtaVueltaTiempoReserva.getValue():null);
				boolean isCorrect = calcularFechaExpiraReserva(false, tiempoReserva);
				if(!isCorrect)
					itbxVtaVueltaTiempoReserva.setFocus(true);
			}			
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_itbxVtaIdaTiempoReserva()throws Exception{		
		dtbxVtaIdaExpiraReserva.setValue(null);
		if(!(cmbVtaIdaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbVtaIdaEmbarque_hora);
			itbxVtaIdaTiempoReserva.setValue(null);
			return;
		}
		
		Integer tiempoReserva = (itbxVtaIdaTiempoReserva.getValue()!=null && itbxVtaIdaTiempoReserva.getValue()>0? itbxVtaIdaTiempoReserva.getValue():null);
		if(tiempoReserva!=null) {			
			if(!validarTiempoPermitidoReserva(true, tiempoReserva))
				return;
			if(!calcularFechaExpiraReserva(true, tiempoReserva)) {
				itbxVtaIdaTiempoReserva.setValue(null);
				itbxVtaIdaTiempoReserva.setFocus(true);				
			}
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_itbxVtaVueltaTiempoReserva()throws Exception{
		dtbxVtaVueltaExpiraReserva.setValue(null);
		if(!(cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbVtaVueltaEmbarque_hora);
			itbxVtaVueltaTiempoReserva.setValue(null);
			return;
		}
		
		Integer tiempoReserva = (itbxVtaVueltaTiempoReserva.getValue()!=null && itbxVtaVueltaTiempoReserva.getValue()>0? itbxVtaVueltaTiempoReserva.getValue():null);
		if(tiempoReserva!=null) {
			if(!validarTiempoPermitidoReserva(false, tiempoReserva))
				return;
			if(!calcularFechaExpiraReserva(true, tiempoReserva)) {
				itbxVtaVueltaTiempoReserva.setValue(null);
				itbxVtaVueltaTiempoReserva.setFocus(true);
			}
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_cmbVtaPaxTipoDocumento()throws Exception{
		TipoDocumento tipoDocumento = cmbVtaPaxTipoDocumento.getSelectedItem().getValue();
		if(tipoDocumento.getId().intValue()!=Constantes.ID_TIPDOC_DNI)
			txtVtaPaxNumeroDocumento.setMaxlength(25);
		else
			txtVtaPaxNumeroDocumento.setMaxlength(8);		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdVtaVenta(Event event)throws Exception{
		habilitaOperacionVenta(true);
		habilitaOperacionReserva(false);
		cleanDatosPago();
		lblVtaInfoTipoOperacion.setValue(rdVtaVenta.getLabel());
		loadEspecieValoradaByVenta(true);

		if(event!=null)
			focusCursorByTipoOperacion(event);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdVtaReserva(Event event)throws Exception{
		habilitaOperacionReserva(true);
		habilitaOperacionVenta(false);
		cleanDatosInfoComprobante();
		lblVtaInfoTipoOperacion.setValue(((Radio)event.getTarget()).getLabel());
		
		if(event!=null)
			focusCursorByTipoOperacion(event);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_chbxVtaFactura()throws Exception{
		
		enabledControlsFactura(chbxVtaFactura.isChecked());
		loadEspecieValoradaByVenta(true);
		
	}
	
	/*
	 * 
	 */
	private void onChange_cmbVtaTipoFormaPago() throws Exception {
		cmbVtaOperadorTarjeta.getItems().clear();
		cmbVtaOperadorTarjeta.setText("");
		cmbVtaOperadorTarjeta.setDisabled(true);
		cmbVtaTarjeta.getItems().clear();
		cmbVtaTarjeta.setText("");
		cmbVtaTarjeta.setDisabled(true);
		txtVtaNumeroOperacion.setText("");

		if(cmbVtaTipoFormaPago.getSelectedIndex()>=0){
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbVtaTipoFormaPago.getSelectedItem().getValue();
			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CREDITO){
				cmbVtaOperadorTarjeta.getItems().clear();
				UtilData.cargarDataCombo(cmbVtaOperadorTarjeta, OperadorTarjetaCredito.class, false);
				cmbVtaOperadorTarjeta.setDisabled(false);
				txtVtaNumeroOperacion.setDisabled(false);
//				cmbVtaOperadorTarjeta.setFocus(true);
			}else if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TRANSFERENCIA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_YAPE) {
				txtVtaNumeroOperacion.setDisabled(false);
//				txtVtaNumeroOperacion.setFocus(true);
			}else{
				txtVtaNumeroOperacion.setDisabled(true);
				cmbVtaOperadorTarjeta.setDisabled(true);
//				btnVtaGuardar.setFocus(true);
			}
		}else{
			txtVtaNumeroOperacion.setDisabled(true);
			cmbVtaOperadorTarjeta.setDisabled(true);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_cmbVtaOperadorTarjeta()throws Exception{		
		cmbVtaTarjeta.getItems().clear();
		cmbVtaTarjeta.setText("");
		cmbVtaTarjeta.setDisabled(true);

		if(cmbVtaOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
			OperadorTarjetaCredito operadorTarjetaCredito = cmbVtaOperadorTarjeta.getSelectedItem().getValue();
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			UtilData.cargarGenericData(cmbVtaTarjeta, false);
			for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
				if(tarjetaCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
					Comboitem item = new Comboitem();
					item.setLabel(tarjetaCredito.getDenominacion());
					item.setValue(tarjetaCredito);
					cmbVtaTarjeta.appendChild(item);
				}
			}
			cmbVtaTarjeta.setDisabled(false);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onSelect_ltbxVtaAsientosSeleccionados()throws Exception{
		boolean isIdaVuelta = rdBusqIdaVuelta.isChecked();
		cleanDatosInfoComprobante();
		lblVtaIdaAsiento.setValue("-");
		dbxVtaIdaTarifa.setValue(.00);
		lblVtaVueltaAsiento.setValue("-");
		dbxVtaVueltaTarifa.setValue(.00);
		cleanDatosPasajero(true);
		cleanDatosInfante();
		cleanDatosPago();		
		resertOperacionAsiento();		
		cleanDatosViaje();
		setDisabled_btmVtaGuardar(true);
		
		if(ltbxVtaAsientosSeleccionados.getItemCount()>0 && ltbxVtaAsientosSeleccionados.getSelectedCount() ==0)
			ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
		
		if(ltbxVtaAsientosSeleccionados.getSelectedCount()>0) {
			VentaIdaRetorno venta = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();
			DetalleItinerario detalleItinerarioIda = (venta.getDetalleItinerarioIDA()!=null?venta.getDetalleItinerarioIDA():null);
			DetalleItinerario detalleItinerarioVuelta = (venta.getDetalleItinerarioRETORNO()!=null?venta.getDetalleItinerarioRETORNO():null);
			if(detalleItinerarioIda!=null) {
				lblVtaIdaDestino_fecha.setValue(detalleItinerarioIda.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()));
				lblVtaIdaAsiento.setValue(detalleItinerarioIda.getAsiento());
				dbxVtaIdaTarifa.setValue(detalleItinerarioIda.getTarifa());
				
				habilitaOperacionAsiento(detalleItinerarioIda.getObjAsiento());
			}
			
			if(detalleItinerarioVuelta!=null) {		
				lblVtaVueltaDestino_fecha.setValue(detalleItinerarioVuelta.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()));
				lblVtaVueltaAsiento.setValue(detalleItinerarioVuelta.getAsiento());
				dbxVtaVueltaTarifa.setValue(detalleItinerarioVuelta.getTarifa());
			}
			
			if(isIdaVuelta && detalleItinerarioIda!=null && detalleItinerarioVuelta!=null && detalleItinerarioIda.getObjAsiento().getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO)
				setDisabled_btmVtaGuardar(false);
			else if(!isIdaVuelta && detalleItinerarioIda!=null && detalleItinerarioIda.getObjAsiento().getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO)
				setDisabled_btmVtaGuardar(false);
			
			Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaPaxTipoDocumento, Constantes.ID_TIPDOC_DNI);
			Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaInfTipoDocumento, Constantes.ID_TIPDOC_DNI);
			
			
//			if(cmbVtaIdaEmbarque_hora.getSelectedIndex()==0) {
//				cmbVtaIdaEmbarque_hora.setFocus(true);
//				cmbVtaIdaEmbarque_hora.open();
//			}else if(cmbVtaIdaDesembarque_hora.getSelectedIndex()==0) {
//				cmbVtaIdaDesembarque_hora.setFocus(true);
//				cmbVtaIdaDesembarque_hora.open();
//			}else if(detalleItinerarioVuelta!=null && cmbVtaVueltaEmbarque_hora.getSelectedIndex()==0) {
//				cmbVtaVueltaEmbarque_hora.setFocus(true);
//				cmbVtaVueltaEmbarque_hora.open();
//			}else if(detalleItinerarioVuelta!=null && cmbVtaVueltaDesembarque_hora.getSelectedIndex()==0) {
//				cmbVtaVueltaDesembarque_hora.setFocus(true);
//				cmbVtaVueltaDesembarque_hora.open();
//			}else
//				txtVtaPaxBusqueda.setFocus(true);
			focusCursorByTipoOperacion(null);
		}
		
		calcularTotalPago();
	}
	
	
	/**
	 * Habilita/deshabilita las operaciones permitidas para con el asiento
	 * @param asiento
	 * @throws Exception
	 */
	private void habilitaOperacionAsiento(Asiento asiento)throws Exception{
		cleanDatosInfoComprobante();
		int estadoAsiento = asiento.getEstadoAsiento();
		
		//Cuando el asiento es bloqueado por el usuario actual
		if(estadoAsiento==Constantes.ASIENTO_BLOQUEADO || estadoAsiento==Constantes.ASIENTO_RESERVADO) {								
			if(!rdVtaVenta.isChecked() && !rdVtaReserva.isChecked()) {
				rdVtaVenta.setChecked(true);
				onCheck_rdVtaVenta(null);
			}			
			rdVtaVenta.setDisabled(false);
			if(estadoAsiento==Constantes.ASIENTO_BLOQUEADO)
				rdVtaReserva.setDisabled(false);
			else if(estadoAsiento==Constantes.ASIENTO_RESERVADO && rdVtaVenta.isChecked())
				lblVtaInfoTipoOperacion.setValue("CONFIRMACIÓN DE RESERVA");			
			disabledControlsViaje(false);
			disabledControlsPasajero(false);
			disabledControlsPago(false);
			setDisabled_btmVtaGuardar(false);
		}
		
		if(asiento.getVentaPasaje()!=null) {
			loadDatosinfoComprobante(asiento.getVentaPasaje());
			loadDatosViaje(true, asiento.getVentaPasaje());
			loadDatosPasajero(asiento.getVentaPasaje().getPasajero());
			if(asiento.getVentaPasaje().getCliente()!=null) {
				chbxVtaFactura.setChecked(true);
				onCheck_chbxVtaFactura();
				loadDatosCliente(asiento.getVentaPasaje().getCliente());
			}
			
			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
			Date fechaPartida = asiento.getVentaPasaje().getFechaPartida();
			
			//Habilita la reimpresión
			if(estadoAsiento==Constantes.ASIENTO_VENDIDO && fechaPartida.getTime() >= fechaActual.getTime())
				btnVtaReimpresion.setDisabled(false);
			
			//Habilita la anulación
			if((estadoAsiento==Constantes.ASIENTO_VENDIDO || estadoAsiento==Constantes.ASIENTO_RESERVADO) && getRol().getId().intValue()==Constantes.ID_ROL_SUPER_USUARIO)
				btnVtaAnular.setDisabled(false); //Super Usuario
			else if(estadoAsiento==Constantes.ASIENTO_RESERVADO && asiento.getVentaPasaje().getUsuario().getId().intValue()==getUsuario().getId().intValue())
				btnVtaAnular.setDisabled(false); //Solamente para el usuario que realizó la reserva
		}
			
	}
	
	/**
	 * Habilita/deshabilita la Operacion Venta
	 * @param enabled
	 * @throws Exception
	 */
	private void habilitaOperacionVenta(boolean enabled)throws Exception{		
		gridVtaDatosPago.setVisible(enabled);
		
	}
	
	/**
	 * Habilita/deshabilita la operación reserva
	 * @param enabled
	 * @throws Exception
	 */
	private void habilitaOperacionReserva(boolean enabled)throws Exception{
		boolean isVtaIdaVuelta = rdBusqIdaVuelta.isChecked();
		sptrVtaIdaDatosReserva.setHeight(null);
		itbxVtaIdaTiempoReserva.setValue(null);
		itbxVtaVueltaTiempoReserva.setValue(null);
		gridVtaIdaDatosReserva.setVisible(enabled);
		clmnVtaIdaDatosReserva.setValign(isVtaIdaVuelta?"DATOS DE LA RESERVA DE IDA":"DATOS DE LA RESERVA");
		gridVtaVueltaDatosReserva.setVisible(isVtaIdaVuelta && enabled);
		if(!gridVtaVueltaDatosReserva.isVisible())
			sptrVtaIdaDatosReserva.setHeight("65px");
		
		//Reserva de IDA
		if(gridVtaIdaDatosReserva.isVisible())
			calcularFechaExpiraReserva(true, null);		
		//Reserva de VUELTA
		if(gridVtaVueltaDatosReserva.isVisible())
			calcularFechaExpiraReserva(false, null);		
	}
	
	
	/**
	 * Calcula la fecha de expiracion de una reserva, por defecto 
	 * @param isIda
	 * @throws Exception 
	 */
	private boolean calcularFechaExpiraReserva(boolean isIda, Integer minutosReserva) throws Exception {
		boolean isCorrect = false;
		boolean isMinutosAsignadosByDefault = false;
		if(minutosReserva==null) {
			//**************************************************
			minutosReserva = 60; //Por defecto asigna 60 minutos, luego llevar este valor a un parametro configurable
			//**************************************************
			isMinutosAsignadosByDefault = true;
		}
		DateFormat FORMAT_DATE = new SimpleDateFormat ("yyyyMMdd");
		if(isIda) {
			dtbxVtaIdaExpiraReserva.setValue(null);
			if(!dtbxVtaIdaExpiraReserva.isDisabled() && cmbVtaIdaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida) {
				dtbxVtaIdaExpiraReserva.setConstraint("between "+ FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()) +" and "+ FORMAT_DATE.format(new Date()));

				String horaPartida = ((ItinerarioAgenciaPartida)cmbVtaIdaEmbarque_hora.getSelectedItem().getValue()).getHoraPartida();				
				String fechaPartida = Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida());
				String sfechaHoraPartida = fechaPartida+" "+horaPartida;
//				
			 	Date fechaHoraPartida = Util.convertirLocalDateTimeToDate(convertirStringToLocalDateTime(sfechaHoraPartida));
				Date fechaHoraReserva = getFechaHoraDefaultReserva(minutosReserva);
				
//				if(fechaHoraReserva.getTime()<=fechaHoraPartida.getTime()) {
//					dtbxVtaIdaExpiraReserva.setValue(fechaHoraReserva);
//					isCorrect = true;
//				}else if(minutosReservaAsignadosByDefault) {
//					dtbxVtaIdaExpiraReserva.setValue(fechaHoraPartida);
//					isCorrect = true;
//				}else
//					DlgMessage.information(minutosReserva +" minutos excede la hora de salida del servicio.");
				
				
				boolean excedeSalida = isFechaExpiraReservaExcedeSalida(isIda, fechaHoraReserva);
				if(!excedeSalida) {
					dtbxVtaIdaExpiraReserva.setValue(fechaHoraReserva);
					isCorrect = true;
				}else if(isMinutosAsignadosByDefault) {
					dtbxVtaIdaExpiraReserva.setValue(fechaHoraPartida);
					isCorrect = true;
				}else
					DlgMessage.information(minutosReserva +" minutos excede la hora de salida del servicio.");				
				
			}
		}else {
			dtbxVtaVueltaExpiraReserva.setValue(null);
			if(!dtbxVtaVueltaExpiraReserva.isDisabled() && cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida) {
				dtbxVtaVueltaExpiraReserva.setConstraint("between "+ FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()) +" and "+ FORMAT_DATE.format(new Date()));

				String horaPartida = ((ItinerarioAgenciaPartida)cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue()).getHoraPartida();
				String fechaPartida = Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida());
				String sfechaHoraPartida = fechaPartida+" "+horaPartida;
				
				Date fechaHoraPartida = Util.convertirLocalDateTimeToDate(convertirStringToLocalDateTime(sfechaHoraPartida));
				Date fechaHoraReserva = getFechaHoraDefaultReserva(minutosReserva);
				
//				if(fechaHoraReserva.getTime()<=fechaHoraPartida.getTime()) {
//					dtbxVtaVueltaExpiraReserva.setValue(fechaHoraReserva);
//					isCorrect = true;
//				}else if(minutosReservaAsignadosByDefault) {
//					dtbxVtaVueltaExpiraReserva.setValue(fechaHoraPartida);
//					isCorrect = true;
//				}else
//					DlgMessage.information(minutosReserva +" minutos excede la hora de salida del servicio.");
				
				
				boolean excedeSalida = isFechaExpiraReservaExcedeSalida(isIda, fechaHoraReserva);
				if(!excedeSalida) {
					dtbxVtaVueltaExpiraReserva.setValue(fechaHoraReserva);
					isCorrect = true;
				}else if(isMinutosAsignadosByDefault) {
					dtbxVtaVueltaExpiraReserva.setValue(fechaHoraPartida);
					isCorrect = true;
				}else
					DlgMessage.information(minutosReserva +" minutos excede la hora de salida del servicio.");	
			}			
		}
		
		return isCorrect;
	}
	
	
	private boolean isFechaExpiraReservaExcedeSalida(boolean isIda, Date fechaHoraExpiraReserva)throws Exception{
		Combobox _cmbEmbarque = null;
		DetalleItinerario _detalleItinerario = null; 
		if(isIda) {
			_cmbEmbarque = cmbVtaIdaEmbarque_hora;
			_detalleItinerario = detalleItinerarioIda;
		}else {
			_cmbEmbarque = cmbVtaVueltaEmbarque_hora;
			_detalleItinerario = detalleItinerarioVuelta;
		}
			
		String horaPartida = ((ItinerarioAgenciaPartida)_cmbEmbarque.getSelectedItem().getValue()).getHoraPartida();				
		String fechaPartida = Constantes.FORMAT_DATE.format(_detalleItinerario.getFechaPartida());
		String sfechaHoraPartida = fechaPartida+" "+horaPartida;
		
		boolean excedeSalida = false;
		
		Date fechaHoraPartida = Util.convertirLocalDateTimeToDate(convertirStringToLocalDateTime(sfechaHoraPartida));
		if(fechaHoraExpiraReserva.getTime() > fechaHoraPartida.getTime())
			excedeSalida = true;
		
		return excedeSalida;
	}
	
	/**
	 * 
	 * @param minutosSumar
	 * @return
	 * @throws Exception
	 */
	private Date getFechaHoraDefaultReserva(int minutosSumar)throws Exception{		
		String fechaHoraActual = Constantes.FORMAT_LONG.format(new Date());
		
		LocalDateTime fechaHora = convertirStringToLocalDateTime(fechaHoraActual);
		LocalDateTime ltFechaHoraReserva = fechaHora.plusMinutes(minutosSumar);		
		Date fechaHoraReserva = Util.convertirLocalDateTimeToDate(ltFechaHoraReserva);
		
		return fechaHoraReserva;
	}
	
	/**
	 * 
	 * @param fecha
	 * @param hora
	 * @return
	 * @throws Exception
	 */
	private LocalDateTime convertirStringToLocalDateTime(String fechaHora)throws Exception{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime ltfechaHora = LocalDateTime.parse(fechaHora, formatter);
				
		return ltfechaHora;
	}
	
	/**
	 * Actualiza el o los Mapas
	 * @throws Exception
	 */
	private void onClick_btnVtaActualizarMapa()throws Exception{
		onRefreshMap();			
	}
	
	/**
	 * Reimprime uno o mas comprobantes
	 * @throws Exception
	 */
	private void onClick_btnVtaReimprimir()throws Exception{
		if(ltbxVtaAsientosSeleccionados.getItemCount()==0) {
			DlgMessage.information(Messages.getString("WndVentaReservaNew.information.reimpresion.noSelectAsiento"));
			return;
		}
		
		List<VentaPasaje> listVentasReimpresion = new ArrayList<VentaPasaje>();
		for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
			if(item.getValue()!=null && item.getValue() instanceof VentaIdaRetorno) {
				VentaIdaRetorno venta = item.getValue();
				if(venta.getDetalleItinerarioIDA()!=null)
					listVentasReimpresion.add(venta.getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje());
				if(venta.getDetalleItinerarioRETORNO()!=null)
					listVentasReimpresion.add(venta.getDetalleItinerarioRETORNO().getObjAsiento().getVentaPasaje());				
			}
		}
		
		Messagebox.show(Messages.getString("WndVentaReservaNew.question.reimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try{
					if(e.getName().equals("onYes")){
						
						WSFE.reimprimirComprobante(listVentasReimpresion, wndVentaReservaNew, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
					}
				}catch(Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(ex.getMessage());
				}
			}
		});
		
	}
	
	/**
	 * Anula una venta/reserva
	 * @throws Exception
	 */
	private void onClick_btnVtaAnular()throws Exception{
		try{
			if(ltbxVtaAsientosSeleccionados.getItemCount()==0) {
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.noSelectAsiento"));
				return;
			}
			
			List<Listitem> listVenta = new ArrayList<Listitem>();
			if(((VentaIdaRetorno)ltbxVtaAsientosSeleccionados.getSelectedItem().getValue()).getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje()
					.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
				//Cuando es una reserva, la anulación es de todos los asientos seleccionados.
				
				ltbxVtaAsientosSeleccionados.getItems().forEach(item -> listVenta.add(item));
			}else {
				//Cuando es una venta, la anulación es uno a uno.
				listVenta.add(ltbxVtaAsientosSeleccionados.getSelectedItem());
			}
			
			//Valida si la anualación es de Reservas
			if( ((VentaIdaRetorno)listVenta.get(0).getValue()).getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje()
					.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
				//Anulación de Reservas
				Messagebox.show(Messages.getString("WndConfirmarReserva.information.confirmaAnularReserva"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						if(e.getName().equals("onYes")){
							try {
								
								for(Listitem item: listVenta) {
									VentaPasaje ventaPasajeAsiento = ((VentaIdaRetorno)item.getValue()).getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje();
									VentaPasaje reserva = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(ventaPasajeAsiento.getId()));
									reserva.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
									UtilData.auditarRegistro(reserva, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getVentaPasajesManager().anularReserva(reserva);									
								}
								
								txtVtaPaxBusqueda.setText("");
								Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
								cleanDatosInfoComprobante();								
								onSelect_ltbxVtaAsientosSeleccionados();														
								onRefreshMap();
								disabledControlsViaje(true);
								disabledControlsPasajero(true);
								disabledControlsPago(true);
								disabledAllButtonsOptinsByAsiento();
								DlgMessage.information(Messages.getString("WndConfirmarReserva.information.reservaAnulada"));
								
							}catch(Exception ex){
								DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
								log.error(ex);
							}
						}						
					}
				});
			}else {
				//Anulación de Ventas
				for(Listitem item: listVenta) {
					VentaPasaje ventaPasajeAsiento = ((VentaIdaRetorno)item.getValue()).getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje();
									
					VentaPasaje ventaOriginal = ServiceLocator.getVentaPasajesManager().buscarVentaById(Long.valueOf(ventaPasajeAsiento.getId()));
					VentaPasaje ultimoMoviento = ServiceLocator.getVentaPasajesManager().buscarUltimoRegistro(ventaOriginal.getVentaOriginal());

					/*Valida si el boleto tiene movimientos superiores al que se esta anulando*/
					if(ultimoMoviento.getId().longValue()!=ventaOriginal.getId().longValue()){
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noAnulacion"));
						return;
					}else if(ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_ANULACION)
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoAnulado"));
					else if (ventaOriginal.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_DEVOLUCION)
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoDevuelto"));
					else if(ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(ventaOriginal.getId())) {
						DlgMessage.information(Messages.getString("Generales.information.manifiestoImpreso"));
						return;
					}

					if (getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO && !(ventaOriginal.getUsuario().getId().equals(getUsuario().getId())))
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.otroUsuario"));
					else if(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO &&
							!(Constantes.FORMAT_DATE.format(ventaOriginal.getFechaInsercion()).equals(Constantes.FORMAT_DATE.format(new Date()))))
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.fechaPasada"));
					else if(ventaOriginal.getLiquidacion()==null){
							wndModal = createWindowAnulacion(ventaOriginal);
							this.appendChild(wndModal);
							wndModal.setMode("modal");
					}else
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.boletoLiquidado"));
					
				}
			}

		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaCancelar()throws Exception{
		if(timerRefreshMapa!=null)
			timerRefreshMapa.stop();
		
		liberarAsientos();
		cleanDatosVenta();
		divPaso_2.setVisible(false);
		divPaso_1.setVisible(true);		
		tabBusqIda.setSelected(true);
		onClick_btnBuscar();
	}
	
	
	private void onClick_btnVtaGuargar()throws Exception{		
		if(!rdVtaVenta.isChecked() && !rdVtaReserva.isChecked() && !rdVtaPostergacion.isChecked() && !rdVtaPostergacionFA.isChecked() && !rdVtaCambioNombre.isChecked()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoOperacion"));
			return;
		}
		
		boolean isIdaVuelta = rdBusqIdaVuelta.isChecked();
		boolean isReserva = rdVtaReserva.isChecked();
		boolean isVenta = rdVtaVenta.isChecked();
		
		//Valida Especie Valorada: Cuando es una ida y vuelta y las empresas del itinerario de ida y del retorno son diferenctes
		if(!isReserva && isIdaVuelta 
				&& detalleItinerarioIda.getItinerario().getEmpresa().getId().intValue()!=detalleItinerarioVuelta.getItinerario().getEmpresa().getId().intValue()) {
			//Primero valida la especie valorada del retorno
			ControlEspecieValorada controlEspecieValorada = loadEspecieValoradaByVenta(false);
			if(controlEspecieValorada ==null)
				return;
		}
		if(!isReserva) {
			//Valida especie valorada de Ida
			ControlEspecieValorada controlEspecieValorada = loadEspecieValoradaByVenta(true);
			if(controlEspecieValorada ==null)
				return;
		}
						
		//si es una venta ida y vuelta, valida que se hayan seleccionado la misma cantidad de asientos tanto de ida como de vuelta
		if(isIdaVuelta) {
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno ventaIdaRetorno = item.getValue();
				if(ventaIdaRetorno.getDetalleItinerarioIDA()==null || ventaIdaRetorno.getDetalleItinerarioRETORNO()==null) {
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noEqualsSeatSelections"));
					break;
				}
			}
		}
		//Valida la tarifa
		for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
			VentaIdaRetorno ventaIdaRetorno = item.getValue();
			if(ventaIdaRetorno.getDetalleItinerarioIDA().getTarifa()==null || ventaIdaRetorno.getDetalleItinerarioIDA().getTarifa()==.00) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioAsientoIda").replace("@asiento", ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento()));
				break;
			}else if(isIdaVuelta && (ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa()==null || ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa()==.00)) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioAsientoRetorno").replace("@asiento", ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento()));
				break;
			}
		}
		
		
		if(ltbxVtaAsientosSeleccionados.getSelectedCount()==0) { //Valida que se haya seleccionado el asiento
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
			return;
		}else if(!(cmbVtaIdaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbVtaIdaEmbarque_hora);
			return;
		}else if(!(cmbVtaIdaDesembarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbVtaIdaDesembarque_hora);
			return;
		}else if(isIdaVuelta && !(cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoAgenciaPartidaRetorno"), cmbVtaVueltaEmbarque_hora);
			return;
		}else if(isIdaVuelta && !(cmbVtaVueltaDesembarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoAgenciaLlegadaRetorno"), cmbVtaVueltaDesembarque_hora);
			return;
		}
		//Valida los datos del pasajero
		if(cmbVtaPaxTipoDocumento.getSelectedIndex()<0) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionTipoDocumento"), cmbVtaPaxTipoDocumento);
			return;
		}else if(txtVtaPaxNumeroDocumento.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"), txtVtaPaxNumeroDocumento);
			return;
		}else if(((TipoDocumento)cmbVtaPaxTipoDocumento.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI &&
				txtVtaPaxNumeroDocumento.getText().trim().length()!=8) {			
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noValidDocumentPax"), txtVtaPaxNumeroDocumento);
			return;
		}else if(txtVtaPaxNombres.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"), txtVtaPaxNombres);
			return;
		}else if(txtVtaPaxApePaterno.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"), txtVtaPaxApePaterno);
			return;
		}else if(!rdVtaPaxFemenino.isChecked() && !rdVtaPaxMasculino.isChecked()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noGenero"));
			return;
		}else if(itbxVtaPaxEdad.getValue()==null || itbxVtaPaxEdad.getValue().intValue()<=0) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noEdadPax"), itbxVtaPaxEdad);
			return;
		}else if(txtVtaPaxTelefono.getText().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTelefono"), txtVtaPaxTelefono);
			return;
		}else if(txtVtaPaxTelefono.getText().trim().length()<6 || txtVtaPaxTelefono.getText().trim().length()>11) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTelefono.NoValid"), txtVtaPaxTelefono);
			return;
		}else if(!txtVtaPaxCorreo.getText().trim().isEmpty() && !UtilData.validateEmail(txtVtaPaxCorreo.getText())) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.ingreseCorreoValido"), txtVtaPaxCorreo);
			return;
		}
		
		//Valida datos de la factura,
		if(chbxVtaFactura.isChecked()) {
			if(txtVtaFacRuc.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"), txtVtaFacRuc);
				return;
			}else if(!Util.validarRUC(txtVtaFacRuc.getText().trim())) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.DocumentoClienteNoValido"), txtVtaFacRuc);
				return;
			}else if(txtVtaFacRazonSocial.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"), txtVtaFacRazonSocial);
				return;
			}else if(txtVtaFacDireccion.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noDireccionCliente"), txtVtaFacDireccion);
				return;
			}else if(txtVtaFacDireccion.getText().trim().length()<5) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.direccionIncorrect"), txtVtaFacDireccion);
				return;
			}
		}
		
		//Valida los datos del pago
		if(isVenta) {
			if(cmbVtaTipoFormaPago.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noMedioPago"), cmbVtaTipoFormaPago);
				return;
			}else if(!cmbVtaOperadorTarjeta.isDisabled() && cmbVtaOperadorTarjeta.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbVtaOperadorTarjeta);
				return;
			}else if(!cmbVtaTarjeta.isDisabled() && cmbVtaTarjeta.getSelectedIndex()<0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbVtaTarjeta);
				return;
			}else if(!txtVtaNumeroOperacion.isDisabled() && txtVtaNumeroOperacion.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"), txtVtaNumeroOperacion);
				return;
			}
		}else if(isReserva) {
			if(dtbxVtaIdaExpiraReserva.getValue()==null && dtbxVtaIdaExpiraReserva.isDisabled()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTiempoReserva"), itbxVtaIdaTiempoReserva);
				return;
			}else if(dtbxVtaIdaExpiraReserva.getValue()==null && !dtbxVtaIdaExpiraReserva.isDisabled()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noFechaExpiraReserva"), dtbxVtaIdaExpiraReserva);
				return;
			}else if(isIdaVuelta && dtbxVtaVueltaExpiraReserva.getValue()==null && dtbxVtaVueltaExpiraReserva.isDisabled()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTiempoReserva"), itbxVtaVueltaTiempoReserva);
				return;
			}else if(isIdaVuelta && dtbxVtaVueltaExpiraReserva.getValue()==null && !dtbxVtaVueltaExpiraReserva.isDisabled()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noFechaExpiraReserva"), dtbxVtaVueltaExpiraReserva);
				return;
			} 
			
			//Valida el tiempo límite permitido 
			if(!validarTiempoPermitidoReserva(true, null))
				return;
			
			//Valida que no exceda la hora de salida del servicio			
			Date fechaHoraReserva = dtbxVtaIdaExpiraReserva.getValue();			
			boolean isMayorHoraSalida = isFechaExpiraReservaExcedeSalida(true, fechaHoraReserva);
			if(isMayorHoraSalida) {
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.fechaExpiraReservaMayorSalida"), dtbxVtaIdaExpiraReserva);
				return;
			}
			if(isIdaVuelta) {
				//Valida el tiempo límite permitido 
				if(!validarTiempoPermitidoReserva(false, null))
					return;
				
				fechaHoraReserva = dtbxVtaVueltaExpiraReserva.getValue();			
				isMayorHoraSalida = isFechaExpiraReservaExcedeSalida(true, fechaHoraReserva);
				if(isMayorHoraSalida) {
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.fechaExpiraReservaMayorSalida"), dtbxVtaVueltaExpiraReserva);
					return;
				}				
			}
			
		}
		
		//Valida el estado de la liquidación (No aplica para las reservas)
		if(!isReserva) {
			if(fechaLiquidacion==null) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noLiquidacion"));
				return;
			}
			String fechaActual = Constantes.FORMAT_DATE.format(new Date());
			String vfechaLiquidacion = Constantes.FORMAT_DATE.format(fechaLiquidacion);		
			if(!fechaActual.equals(vfechaLiquidacion)) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.fechaLiquidacionDiferente"));
				return;
			}	
		}
		
		

		//Seate los dados del Pasajero
		if(pasajero ==null)
			pasajero = new Pasajero();
		
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("tipoDocumento", cmbVtaPaxTipoDocumento.getSelectedItem().getValue());
		criteriosBusqueda.put("numeroDocumento", txtVtaPaxNumeroDocumento.getText().trim().toUpperCase());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Pasajero> resultPasajero = ServiceLocator.getPasajeroManager().buscarPorX(criteriosBusqueda, null);
		if(resultPasajero.size()>0)
			pasajero = resultPasajero.get(0);
		else
			pasajero.setId(null);
		
		pasajero.setTipoDocumento(cmbVtaPaxTipoDocumento.getSelectedItem().getValue());
		pasajero.setNumeroDocumento(txtVtaPaxNumeroDocumento.getText().trim().toUpperCase());
		pasajero.setNombre(txtVtaPaxNombres.getText().trim().toUpperCase());
		pasajero.setApellidoPaterno(txtVtaPaxApePaterno.getText().trim().toUpperCase());
		pasajero.setApellidoMaterno(!txtVtaPaxApeMaterno.getText().trim().isEmpty()? txtVtaPaxApeMaterno.getText().trim().toUpperCase(): null);
		pasajero.setNombresApellidos(pasajero.getNombre()+" "+pasajero.getApellidoPaterno()+(pasajero.getApellidoMaterno()!=null?" "+ pasajero.getApellidoMaterno():""));
		pasajero.setSexo(rdVtaPaxFemenino.isChecked()?new Sexo(Constantes.ID_SEXO_FEMENINO):new Sexo(Constantes.ID_SEXO_MASCULINO));
		pasajero.setFechaNacimiento(getFechaNacimiento(itbxVtaPaxEdad.getValue()));
		pasajero.setTelefono(!txtVtaPaxTelefono.getText().trim().isEmpty()?txtVtaPaxTelefono.getText().trim():null);
		if(pasajero.getUbigeo() ==null)
			pasajero.setUbigeo(new Ubigeo(Constantes.ID_UBIGEO_LIMA));
		if(pasajero.getEmail()==null && !txtVtaPaxCorreo.getText().trim().isEmpty()) {
			pasajero.setFechaTieneEmail(new Date());
			pasajero.setUsuarioTieneEmail(getUsuario());
			pasajero.setAgenciaTieneEmail(getAgencia());
		}
		pasajero.setEmail(!txtVtaPaxCorreo.getText().trim().isEmpty()?txtVtaPaxCorreo.getText().trim().toLowerCase():null);		
		pasajero.setTieneEmail(pasajero.getEmail()==null?Constantes.FALSE_VALUE:Constantes.TRUE_VALUE);
		if(pasajero.getAgencia()==null)
			pasajero.setAgencia(getAgencia());
		if(pasajero.getKilometros()==null)
			pasajero.setKilometros(.00);
		if(pasajero.getIndeseable()==null)
			pasajero.setIndeseable(Constantes.FALSE_VALUE);
		
		pasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		if(pasajero.getId()==null)
			UtilData.auditarRegistro(pasajero, getUsuario(), Executions.getCurrent());
		else
			UtilData.auditarRegistro(pasajero, true, getUsuario(), Executions.getCurrent());
		
		
		//Seata los datos del Cliente
		if(chbxVtaFactura.isChecked()) {			
			cliente = ServiceLocator.getClienteManager().buscarPorRuc(txtVtaFacRuc.getText().trim().toUpperCase());
			if(cliente == null)
				cliente = new Cliente();
			cliente.setNumeroDocumento(txtVtaFacRuc.getText().trim());
			cliente.setRazonSocial(txtVtaFacRazonSocial.getText().trim().toUpperCase());
			cliente.setDireccion(txtVtaFacDireccion.getText().trim().toUpperCase());
			if(cliente.getAgencia()==null)
				cliente.setAgencia(getAgencia());
			if(cliente.getUbigeo()==null)
				cliente.setUbigeo(new Ubigeo(Constantes.ID_UBIGEO_LIMA));
			if(cliente.getCantidadTrabajadores()==null)
				cliente.setCantidadTrabajadores(0);
			if(cliente.getKilometros()==null)
				cliente.setKilometros(.00);
			cliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			if(cliente.getId()==null)
				UtilData.auditarRegistro(cliente, getUsuario(), Executions.getCurrent());
			else
				UtilData.auditarRegistro(cliente, true, getUsuario(), Executions.getCurrent());
		}
				
		List<DetalleItinerario> listDetalleItienrario = new ArrayList<DetalleItinerario>();
		if(isReserva)
			//Agrega todos los asientos seleccionados (se guardaran todos los asientos para el mismo pasajero)
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno ventaIdaRetorno = item.getValue();
				listDetalleItienrario.add(ventaIdaRetorno.getDetalleItinerarioIDA());
				if(ventaIdaRetorno.getDetalleItinerarioRETORNO()!=null)
					listDetalleItienrario.add(ventaIdaRetorno.getDetalleItinerarioRETORNO());				
			}
		else {
			VentaIdaRetorno ventaIdaRetorno = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();
			listDetalleItienrario.add(ventaIdaRetorno.getDetalleItinerarioIDA());
			if(ventaIdaRetorno.getDetalleItinerarioRETORNO()!=null)
				listDetalleItienrario.add(ventaIdaRetorno.getDetalleItinerarioRETORNO());	
		}
				
		
		//*****************************************************************************************
		List<VentaPasaje> listVentas = new ArrayList<VentaPasaje>();
		for(DetalleItinerario detalleItinerario: listDetalleItienrario) {
			ItinerarioAgenciaPartida itinerarioAgenciaPartida = null;
			ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = null;
			
			VentaPasaje ventaPasajeRef = null;
			if(detalleItinerario.getObjAsiento().getVentaPasaje()!=null)
				ventaPasajeRef = detalleItinerario.getObjAsiento().getVentaPasaje();
			ventaPasaje = new VentaPasaje();			
			
			if(detalleItinerario.getEsIda()) {
				itinerarioAgenciaPartida = cmbVtaIdaEmbarque_hora.getSelectedItem().getValue();
				itinerarioAgenciaLlegada = cmbVtaIdaDesembarque_hora.getSelectedItem().getValue();
			}else {
				itinerarioAgenciaPartida = cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue();
				itinerarioAgenciaLlegada = cmbVtaVueltaDesembarque_hora.getSelectedItem().getValue();
			}
			
			if(isReserva) {
				ventaPasaje.setFechaLiquidacion(new Date());
				ventaPasaje.setImportePagado(.00);
				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_RESERVA));
				ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_RESERVA);
				if(detalleItinerario.getEsIda())
					ventaPasaje.setFechaExpiracionReserva(dtbxVtaIdaExpiraReserva.getValue());
				else
					ventaPasaje.setFechaExpiracionReserva(dtbxVtaVueltaExpiraReserva.getValue());
			}else {
				//Cuando es una consimación de reserva, valida que esta aún este disponible.
				if(ventaPasajeRef!=null && ventaPasajeRef.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
					VentaPasaje reserva = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeRef.getId());
					int tipoMovimientoId = reserva.getTipoMovimiento().getId();
					if(tipoMovimientoId==Constantes.ID_TIPMOV_ANULACION_SISTEMA || tipoMovimientoId==Constantes.ID_TIPMOV_ANULACION) {
						DlgMessage.information(Messages.getString("WndVentaReservaNew.information.fechaExpiraReservaMayorSalida"));
						onRefreshMap();
						return;
					}
				}
				
				ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
				ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
				if(detalleItinerario.getEsIda())
					ventaPasaje.setImportePagado(dbxVtaIdaTarifa.getValue());
				else
					ventaPasaje.setImportePagado(dbxVtaVueltaTarifa.getValue());
			}
			ventaPasaje.setVentaOriginal(ventaPasajeRef!=null?ventaPasajeRef.getId():null);
			ventaPasaje.setVentaPasaje(ventaPasajeRef);
			ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);
			ventaPasaje.setIdaRetorno(isIdaVuelta?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
			ventaPasaje.setItinerario(detalleItinerario.getItinerario());			
			ventaPasaje.setEmpresa(detalleItinerario.getItinerario().getEmpresa());
			ventaPasaje.setRuta(detalleItinerario.getRuta());
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setCliente(cliente);
			ventaPasaje.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO)); //almenos por ahora siempre será contado
			ventaPasaje.setServicio(detalleItinerario.getItinerario().getServicio());
			ventaPasaje.setTipoComprobante(new TipoComprobante(cliente!=null?Constantes.ID_TIPCOM_FACTURA:Constantes.ID_TIPCOM_BOLETA_VENTA));
			ventaPasaje.setTipoFormaPago(cmbVtaTipoFormaPago.getSelectedIndex()>=0?cmbVtaTipoFormaPago.getSelectedItem().getValue():null);
			if(cmbVtaTarjeta.getSelectedIndex()>=0 && cmbVtaTarjeta.getSelectedItem().getValue() instanceof TarjetaCredito)
				ventaPasaje.setTarjetaCredito(cmbVtaTarjeta.getSelectedItem().getValue());
			ventaPasaje.setNumeroOperacionBancaria(txtVtaNumeroOperacion.getText().equals("")?null:txtVtaNumeroOperacion.getText());
			ventaPasaje.setNumeroBoleto(!lblVtaNumeroComprobante.getValue().trim().isEmpty()? lblVtaNumeroComprobante.getValue().trim().toUpperCase():null);
//			else if(rdVtaPostergacion.isChecked())
//				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION));
//			else if(rdVtaPostergacionFA.isChecked())
//				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION_FA));
//			else if(rdVtaCambioNombre.isChecked())
//				ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
			ventaPasaje.setNumeroAsiento(Integer.valueOf(detalleItinerario.getAsiento()));
			ventaPasaje.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
			ventaPasaje.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
			ventaPasaje.setFechaPartida(detalleItinerario.getFechaPartida());
			ventaPasaje.setHoraPartida(detalleItinerario.getHoraPartida());
			ventaPasaje.setHoraEmbarque(itinerarioAgenciaPartida.getHoraPartida());
			ventaPasaje.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
			ventaPasaje.setFechaLlegada(detalleItinerario.getFechaLlegada());
			ventaPasaje.setHoraLllegada(detalleItinerario.getHoraLlegada());
			ventaPasaje.setHoraDesembarque(itinerarioAgenciaLlegada.getHoraLlegada());
			ventaPasaje.setPreferenciaAlimentaria(new PreferenciaAlimentaria(Constantes.ID_PREALIM_MENU_DEL_DIA)); //Volor por defecto
			//*******************************************
			ventaPasaje.setSecuencial(0);
			//*******************************************
			ventaPasaje.setTarifa(detalleItinerario.getTarifa());
			ventaPasaje.setRecargo(.00);
			ventaPasaje.setDescuento(.00);
			ventaPasaje.setPenalidad(.00);
			ventaPasaje.setAcuenta(.00);
			ventaPasaje.setImportePagadoByDiferencia(.00);
			ventaPasaje.setImportePagadoEfectivo(.00);
			ventaPasaje.setImportePagadoTarjeta(.00);
			String fechaCaducidad=Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida())+ " "+ventaPasaje.getHoraPartida();
			Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
			ventaPasaje.setFechaCaducidad(dateCaducidad);
			ventaPasaje.setAgencia(getAgencia());
			ventaPasaje.setUsuario(getUsuario());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setUsuarioHardware(getUsuarioHardware());
			ventaPasaje.setEsRemoto(false);			
			ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			ventaPasaje.setLiquidacion(null);
			ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
			ventaPasaje.setNumeroControl("T00000");
			UtilData.auditarRegistro(ventaPasaje, false, getUsuario(), Executions.getCurrent());

			listVentas.add(ventaPasaje);
		}
		
		Messagebox.show(Messages.getString("WndVentaReserva.information.confirmacionGuardarVenta"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				try {
					if(e.getName().equals("onYes")){						
						int result = ServiceLocator.getVentaPasajesManager().guardarVenta(listVentas);						
						if(result == Constantes.CORRECT){							
							ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
							if(isVenta) {
								/*Realiza el envio del boleto y realiza la impresion*/
//								boolean printComprobante = true;

								List<VentaPasaje> listVentaPasajes= new ArrayList<>();
								listVentaPasajes.add(ventaPasaje);
									
								//Aqui se envia el comprobante al servidor de Facturación Electrónica
//								WSFE.sendVenta(listVentaPasajes, wndVentaReservaNew, printComprobante, null, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
							
								ltbxVtaAsientosSeleccionados.removeItemAt(ltbxVtaAsientosSeleccionados.getSelectedIndex());
								Double totalPago = dbxVtaTotalPagar.getValue();
								if(ltbxVtaAsientosSeleccionados.getItemCount()>0) {
									dbxVtaTotalPagar.setValue(totalPago);									
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta"),txtVtaPaxBusqueda);
								}else
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta"));
							}else if(isReserva) {
								Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
								cleanDatosReserva();
								DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarReserva"));
								habilitaOperacionReserva(false);
								habilitaOperacionVenta(true);
							}
							cleanDatosInfoComprobante();
							txtVtaPaxBusqueda.setText("");														
							onSelect_ltbxVtaAsientosSeleccionados();
							disabledControlsViaje(true);
							disabledControlsPasajero(true);
							disabledControlsPago(true);
							onRefreshMap();
						}
					}
				}catch(CapacityExceedsException ceex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.changeCapacidad"));
				}catch(DuplicateSeatException dsex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoVendido"));
				}catch(NumeroBoletoDuplicadoException nbdex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
				}catch(TiempoExpiracionBloqueoException tebex){
					DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
				}catch(Exception ex){
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					ex.printStackTrace();
					log.error(ex);
				}
			}				
		});
		
		
		
		
		
	}
	
	
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la ruta que estamos buscando.
	 * @param lstSecuencias	: Lista de secuencia segun el itinerario.
	 * @param idOrigen		: Identificador del origen.
	 * @param idDestino		: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen, Integer idDestino){
		List<Integer> lstSubconjunto = new ArrayList<>();
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
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, tmp.getRuta().getLocalidadOrigen().getId(), tmp.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			}else if(obj instanceof VentaPasaje){
				VentaPasaje ventaPasaje = (VentaPasaje)obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, ventaPasaje.getRuta().getLocalidadOrigen().getId(), ventaPasaje.getRuta().getLocalidadDestino().getId());
				ventaPasaje.setSubConjunto(subConjunto);
				result.add(ventaPasaje);
			}
		}
		return result;
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
	 * Habilita/Deshabilita los controles del retorno en la busqueda
	 * @throws Exception
	 */
	private void enabledControlsVueltaVenta()throws Exception{				
		boolean enabledCtrlsVuelta = rdBusqIdaVuelta.isChecked();
			
		tabVtaVueltaMapa.setDisabled(!enabledCtrlsVuelta);
		gridVtaInfoViajeVuelta.setVisible(enabledCtrlsVuelta);	
	}
	
	/**
	 * Limpia todos los datos de la venta
	 * @throws Exception
	 */
	private void cleanDatosVenta()throws Exception{				
		detalleItinerarioIda = null;
		detalleItinerarioVuelta = null;
		mapaAsientosIda = null;
		mapaAsientosRetorno = null;
		
		tabVtaVueltaMapa.setDisabled(true);
		
		txtVtaPaxBusqueda.setText("");
		
		btnVtaActualizarMapa.setDisabled(true);
		btnVtaLeyendaAsientos.setDisabled(true);
		
		disabledAllButtonsOptinsByAsiento();		
		
		gridVtaInfoViajeVuelta.setVisible(false);		
		chbxVtaFactura.setChecked(false);
		Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
		
		lblVtaIdaDestino_fecha.setValue("");
		lblVtaVueltaDestino_fecha.setValue("");		
		
		cleanDatosInfoComprobante();
		resertOperacionAsiento();		
		cleanDatosInfoviaje(true, true);		
		cleanDatosInfoviaje(false, true);				
		cleanDatosPasajero(true);
		enabledControlsFactura(false);
		cleanDatosInfante();
		cleanDatosPago();
		
		
		habilitaOperacionReserva(false);
		habilitaOperacionVenta(true);
		
		disabledControlsViaje(true);
		disabledControlsPasajero(true);
		disabledControlsPago(true);
		
		setDisabled_btmVtaGuardar(true);
	}
	
	/**
	 * Limpia los datos con la información del Vieje
	 * @param isIda: (True) Es el viaje de Ida, (False) es el Vieaje de Vuelta
	 * @param cleanPuntoEmbarque: (True) para limpiar los controles de embarque y deseambarque; (False) solamente resetea la selección del embarque y deseambarque. 
	 * @throws Exception
	 */
	private void cleanDatosInfoviaje(boolean isIda, boolean cleanPuntoEmbarque)throws Exception{
		
		if(isIda) {			
			if(cleanPuntoEmbarque) {
				Util.limpiarCombobox(cmbVtaIdaEmbarque_hora);
				Util.limpiarCombobox(cmbVtaIdaDesembarque_hora);
			}else {
				cmbVtaIdaEmbarque_hora.setSelectedIndex(0);
				cmbVtaIdaDesembarque_hora.setSelectedIndex(0);
			}			
			lblVtaIdaAsiento.setValue("");
			dbxVtaIdaTarifa.setValue(.00);
		}else {			
			if(cleanPuntoEmbarque) {				
				Util.limpiarCombobox(cmbVtaVueltaEmbarque_hora);
				Util.limpiarCombobox(cmbVtaVueltaDesembarque_hora);
			}else {
				if(cmbVtaVueltaEmbarque_hora.getItemCount()>0)
					cmbVtaVueltaEmbarque_hora.setSelectedIndex(0);
				if(cmbVtaVueltaDesembarque_hora.getItemCount()>0)
					cmbVtaVueltaDesembarque_hora.setSelectedIndex(0);
			}			
			lblVtaVueltaAsiento.setValue("");
			dbxVtaVueltaTarifa.setValue(.00);
		}
	}
	
	/**
	 * Limpia los controles del pasajero
	 * @param cleanDatosFactura: Indica si debe o no resetar los datos para la factura
	 * @throws Exception
	 */
	private void cleanDatosPasajero(boolean cleanDatosFactura)throws Exception{
		pasajero = null;
		cmbVtaPaxTipoDocumento.setSelectedIndex(-1);
		txtVtaPaxNumeroDocumento.setText("");
		txtVtaPaxNombres.setText("");
		txtVtaPaxApePaterno.setText("");
		txtVtaPaxApeMaterno.setText("");
		rdVtaPaxFemenino.setChecked(false);
		rdVtaPaxMasculino.setChecked(false);
		itbxVtaPaxEdad.setText("");
		txtVtaPaxTelefono.setText("");
		txtVtaPaxCorreo.setText("");
		if(cleanDatosFactura) {
			chbxVtaFactura.setChecked(false);
			enabledControlsFactura(false);
		}				
	}
	
	/**
	 * Habilita/Deshabilita los controles para la Factura
	 * @param enabled: (True) Habilita, (Flase) Deshabilita
	 * @throws Exception
	 */
	private void enabledControlsFactura(boolean enabled)throws Exception{
		cliente = null;
		txtVtaFacRuc.setText("");
		txtVtaFacRazonSocial.setText("");
		txtVtaFacDireccion.setText("");
		
		txtVtaFacRuc.setVisible(enabled);
		txtVtaFacRazonSocial.setVisible(enabled);
		txtVtaFacDireccion.setVisible(enabled);
		txtVtaFacRuc.setFocus(enabled);
	}
	
	/**
	 * Limpia los datos del Infante
	 * @throws Exception
	 */
	private void cleanDatosInfante()throws Exception{
		cmbVtaInfTipoDocumento.setSelectedIndex(-1);
		txtVtaInfNumeroDocumento.setText("");
		txtVtaInfNombres.setText("");
		txtVtaInfApePat.setText("");
		txtVtaInfApeMat.setText("");
		cmbVtaInfParentes.setSelectedIndex(-1);
		txtVtaInfParentescoInfoOtro.setText("");
		cmbVtaDocumentoAutoInf.setSelectedIndex(-1);
		txtVtaDocumentoAutoInfOtro.setText("");
		rdVtaInfFemenino.setChecked(false);
		rdVtaInfMasculino.setChecked(false);
		itbxVtaInfEdad.setText("");
		
	}
	
	/**
	 * Limpia los datos del Pago
	 * @throws Exception
	 */
	private void cleanDatosPago()throws Exception{
		if(ltbxVtaAsientosSeleccionados.getItemCount()==0) {
			dbxVtaTotalPagar.setValue(null);
			cmbVtaTipoFormaPago.setSelectedIndex(-1);
			cmbVtaOperadorTarjeta.setSelectedIndex(-1);
			Util.limpiarCombobox(cmbVtaTarjeta);
			txtVtaNumeroOperacion.setText("");
			cmbVtaAutorizaDescuento.setSelectedIndex(-1);
			txtVtaAutorizaDescuentoCodigo.setText("");	
		}
	}
	
	/**
	 * Limpia los datos que muestran información del comprobante
	 * @throws Exception
	 */
	private void cleanDatosInfoComprobante()throws Exception{
		lblVtaTipoComprobante.setValue("-");
		lblVtaNumeroComprobante.setValue("-");
//		lblVtaInfoTipoOperacion.setValue("-");
//		
//		if(rdVtaVenta.isChecked())
//			lblVtaInfoTipoOperacion.setValue(rdVtaVenta.getLabel());
//		else if(rdVtaReserva.isChecked())
//			lblVtaInfoTipoOperacion.setValue(rdVtaReserva.getLabel());
//		else if(rdVtaPostergacion.isChecked())
//			lblVtaInfoTipoOperacion.setValue(rdVtaPostergacion.getLabel());
//		else if(rdVtaPostergacionFA.isChecked())
//			lblVtaInfoTipoOperacion.setValue(rdVtaPostergacionFA.getLabel());
//		else if(rdVtaCambioNombre.isChecked())
//			lblVtaInfoTipoOperacion.setValue(rdVtaCambioNombre.getLabel());
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
//	@SuppressWarnings("deprecation")
	private void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno,
								 DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, Grid gridOcupabilidad){
		try{
			Servicio servicio = null;
			/*	Busca el mapa del bus de acuerdo al servicio	*/
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBusHorizontal(idServicio, Constantes.VALUE_ACTIVO);

			/*	Creamos un Map el cual tendra como key un objeto coordenada y como valor el objeto MapaBus esto es deacuerdo a
			 * lo que tenemos en la Base de datos	*/
			Map<Coordenada, MapaBus> mapCoordenadas = new HashMap<>();
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
			int numeroAsiento = 0;

			inicializarEstructura(grpbxParent);

			//Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 43, 170);

			mapaAsientos = new HashMap<>();
			Hlayout ohlayout = new Hlayout();


			/*	Recorremos la cantidad de pisos del servicio	*/
			for(int i = 0; i < nPisos; i++){
				String idGrid = "grdIdaPiso1";
				String idTam = (nPisos == 2 ? "130px" : "500px");
				if(esRetorno)
					idGrid = "grdRetornoPiso1";
				/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
				if(i == 1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_";
					idGrid = "grdIdaPiso2";
					idTam = "450px";
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					//imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 34, 170);
				}
				/*	Creando la grilla contenedora de asientos	*/
				ohlayout.appendChild(imagen);
				Grid gridPiso = new Grid();
				gridPiso.setId(idGrid);
				gridPiso.setStyle("border:none;background:white");
				//gridPiso.setWidth("154px");
				gridPiso.setWidth(idTam);
				gridPiso.setHeight("170px");
				Rows rows = new Rows();
				Row row = new Row();
//				row.setSpans(String.valueOf(nColumnas));
//				row.appendChild(imagen);
//				row.setStyle("background:white; padding:0px");
//				rows.appendChild(row);
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

								HashMap<String, String> propiedades = new HashMap<>();
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
												onClickAsiento(e, detalleItinerarioVuelta, mapaAsientosRetorno, false);
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
				//grpbxParent.appendChild(gridPiso);
				ohlayout.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario, gridOcupabilidad);
				if(esRetorno)
					mapaAsientosRetorno = mapaAsientos;
				else
					mapaAsientosIda = mapaAsientos;
			}
			grpbxParent.appendChild(ohlayout);
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
	
	/**
	 * Realiza el refresco del mapa del bus.
	 * @param mapa				: Mapa de asientos
	 * @param detalleItinerario	: Itinerario con el que se desea actualizar el mapa.
	 */
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		try{
			/*Busca configuracion para la validacion de la venta adelantada - 09/02/2015 (jabanto)*/
			VentaTramo ventaTramo=ServiceLocator.getVentaTramoManager().buscarPorItinerarioRuta(detalleItinerario.getItinerario(), detalleItinerario.getRuta());

			onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(),detalleItinerario.getRuta().getLocalidadDestino().getId());

//			int nOcupados = 0;
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
								if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO) {
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
										asiento.setSrc(Constantes.ICON_VENDIDO_WEB_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									else
										asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								}else {
									if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_WEB)
										asiento.setSrc(Constantes.ICON_VENDIDO_WEB_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
									else
										asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								}
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
							asiento.setVentaPasaje(venta);
//							nOcupados++;
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
//							nOcupados++;
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
//						nOcupados++;
					}
				}
			}

//			mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Resetea todas las operaciones para con los asientos (rdVtaVenta, rdVtaReserva, rdVtaPostergacion, rdVtaPostergacion, rdVtaPostergacionFA y rdVtaCambioNombre)
	 */
	private void resertOperacionAsiento() {
		rdVtaVenta.setChecked(false);
		rdVtaReserva.setChecked(false);
		rdVtaPostergacion.setChecked(false);
		rdVtaPostergacionFA.setChecked(false);
		rdVtaCambioNombre.setChecked(false);
		
		rdVtaVenta.setDisabled(true);
		rdVtaReserva.setDisabled(true);
		rdVtaPostergacion.setDisabled(true);
		rdVtaPostergacionFA.setDisabled(true);
		rdVtaCambioNombre.setDisabled(true);
		lblVtaInfoTipoOperacion.setValue("-");
		
	}
	
	
	/**
	 * Deshabilita todos los Botones con opciones por asiento
	 * @throws Exception
	 */
	private void disabledAllButtonsOptinsByAsiento()throws Exception{
		btnVtaProgramarServicios.setDisabled(true);
		btnVtaManifiestoPasajetos.setDisabled(true);
		btnVtaCarpetaDespacho.setDisabled(true);
		btnVtaContribucionByAgencia.setDisabled(true);
		btnVtaReimpresion.setDisabled(true);
		btnVtaPostergar.setDisabled(true);
		btnVtaAnular.setDisabled(true);
	}
	
	/**
	 * Habiliat/Deshabilita los controles de la información del viaje
	 * @param disabled: (True) deshabilita controles; (false)Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsViaje(boolean disabled)throws Exception{
		cmbVtaIdaEmbarque_hora.setDisabled(disabled);
		cmbVtaIdaDesembarque_hora.setDisabled(disabled);
		dbxVtaIdaTarifa.setDisabled(disabled);
		if(gridVtaInfoViajeVuelta.isVisible()) {
			cmbVtaVueltaEmbarque_hora.setDisabled(disabled);
			cmbVtaVueltaDesembarque_hora.setDisabled(disabled);
			dbxVtaVueltaTarifa.setDisabled(disabled);
		}
	}
	
	/**
	 * Habilita/Deshabilita los controles del Pasajero
	 * @param disabled: (True) deshabilita controles; (false)Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsPasajero(boolean disabled)throws Exception{
		txtVtaPaxBusqueda.setReadonly(disabled);
		cmbVtaPaxTipoDocumento.setDisabled(disabled);
		txtVtaPaxNumeroDocumento.setReadonly(disabled);
		txtVtaPaxNombres.setReadonly(disabled);
		txtVtaPaxApePaterno.setReadonly(disabled);
		txtVtaPaxApeMaterno.setReadonly(disabled);
		rdVtaPaxFemenino.setDisabled(disabled);
		rdVtaPaxMasculino.setDisabled(disabled);
		itbxVtaPaxEdad.setReadonly(disabled);
		txtVtaPaxTelefono.setReadonly(disabled);
		txtVtaPaxCorreo.setReadonly(disabled);
		chbxVtaFactura.setDisabled(disabled);
		txtVtaFacRuc.setReadonly(disabled);
		txtVtaFacRazonSocial.setReadonly(disabled);
		txtVtaFacDireccion.setReadonly(disabled);
	}
	
	/**
	 * Habilita/Deshabilita los controles del Pago
	 * @param disabled: (True) deshabilita controles; (false)Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsPago(boolean disabled)throws Exception{
		cmbVtaTipoFormaPago.setDisabled(disabled);
		cmbVtaAutorizaDescuento.setDisabled(disabled);
		txtVtaAutorizaDescuentoCodigo.setDisabled(disabled);
	}
	
	
	/**
	 * Limpia los datos del Vieja
	 * @throws Exception
	 */
	private void cleanDatosViaje()throws Exception{
		//cuando aun hay asientos aseleccionados ya no resetea los puntos de embarque y desembarque, 
		//con el fin de agilizar
		if(ltbxVtaAsientosSeleccionados.getItemCount()==0) {
			if(cmbVtaIdaEmbarque_hora.getItemCount()==2)
				cmbVtaIdaEmbarque_hora.setSelectedIndex(1);
			else
				cmbVtaIdaEmbarque_hora.setSelectedIndex(0);
			if(cmbVtaIdaDesembarque_hora.getItemCount()==2)
				cmbVtaIdaDesembarque_hora.setSelectedIndex(1);
			else
				cmbVtaIdaDesembarque_hora.setSelectedIndex(0);
			
			if(gridVtaInfoViajeVuelta.isVisible()) {
				if(cmbVtaVueltaEmbarque_hora.getItemCount()==2)
					cmbVtaVueltaEmbarque_hora.setSelectedIndex(1);
				else
					cmbVtaVueltaEmbarque_hora.setSelectedIndex(0);
				if(cmbVtaVueltaDesembarque_hora.getItemCount()==2)
					cmbVtaVueltaDesembarque_hora.setSelectedIndex(1);
				else
					cmbVtaVueltaDesembarque_hora.setSelectedIndex(0);
			}	
		}				
	}
	
	
	/**
	 * Limapia los datos de la Reserva
	 * @throws Exception
	 */
	private void cleanDatosReserva()throws Exception{		
		itbxVtaIdaTiempoReserva.setValue(null);
		dtbxVtaIdaExpiraReserva.setValue(null);
		
		if(gridVtaVueltaDatosReserva.isVisible()) {
			itbxVtaVueltaTiempoReserva.setValue(null);
			dtbxVtaVueltaExpiraReserva.setValue(null);	
		}		
	}
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, boolean esIda){
		try{
			txtVtaPaxBusqueda.setText("");
			setDisabled_btmVtaGuardar(true);
			resertOperacionAsiento();
			cleanDatosInfoviaje(true, false);
			if(gridVtaInfoViajeVuelta.isVisible())
				cleanDatosInfoviaje(false, false);
			disabledControlsViaje(true);
			disabledControlsPasajero(true);
			disabledControlsPago(true);
			disabledAllButtonsOptinsByAsiento();
			
			Asiento asientoSeleccionado = (Asiento)e.getTarget();
			key = asientoSeleccionado.getKey();			
			
			
			/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
			if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario())) {
				onSelect_ltbxVtaAsientosSeleccionados();
				return;
			}
			
			/*	validamos la cantidad maxima de asientos seleccionados	*/
			if(ltbxVtaAsientosSeleccionados.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
				/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/
				if(consultaAsientoBloqueado(key, mapaAsientos)){					
					if(rdBusqIdaVuelta.isChecked() || asientoSeleccionado.getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO)
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
					else {
						//Las operaciones con asientos no disponibles, solamente están disponibles para el tipo de operación SOLO IDA
						//Obtener la tarifa regular del asiento y almacenarlo en DetalleItinerario del asientoSeleccionado
						List<TarifaRegular> lstTarifaRegular = buscarTarifa(
								asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(),
								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
								asientoSeleccionado.getPiso(),
								asientoSeleccionado.getNumeroZona());
						if(lstTarifaRegular.size()>0) {
							Double montoTarifa = .00;
							for(TarifaRegular tarifaRegular: lstTarifaRegular) {
								if(tarifaRegular.getTarifa()!=null && tarifaRegular.getTarifa().getPisoBus().intValue()==asientoSeleccionado.getPiso()) {
									montoTarifa = (tarifaRegular.getMonto()!=null?tarifaRegular.getMonto():.00);
									break;
								}										
							}															
							asientoSeleccionado.getDetalleItinerario().setTarifa(montoTarifa);
						}else
							asientoSeleccionado.getDetalleItinerario().setTarifa(0.00);
						
						
						DetalleItinerario detalle = (DetalleItinerario)asientoSeleccionado.getDetalleItinerario().clone();
						detalle.setAsiento(asientoSeleccionado.getNumeroAsiento().toString());
						detalle.setPiso(String.valueOf(asientoSeleccionado.getPiso()));
						detalle.setZona(String.valueOf(asientoSeleccionado.getNumeroZona()));
						detalle.setObjAsiento(asientoSeleccionado);
						detalle.setEsIda(esIda);
						
						//Agerga a la lista de asientos seleccionaddos
						addListAsientoSeleccionado(detalle);
					}
					key="-1";
				}else{
					/*	Insertamos el asiento a la tmpOcupacionAsientos	*/
					TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
					tmpOcupacionAsientos.setUsuarioHardware(getUsuarioHardware());
					tmpOcupacionAsientos.setUsuario(getUsuario());

					tmpOcupacionAsientos.setRuta(detalleItinerario.getRuta());
					tmpOcupacionAsientos.setItinerario(detalleItinerario.getItinerario());
					tmpOcupacionAsientos.setNumeroAsiento(asientoSeleccionado.getNumeroAsiento());
					tmpOcupacionAsientos.setNumeroPiso(asientoSeleccionado.getPiso());
					tmpOcupacionAsientos.setFechaPartida(Util.DatetoString(detalleItinerario.getFechaPartida(), Constantes.DATE_FORMAT));
					tmpOcupacionAsientos.setHoraPartida(detalleItinerario.getHoraPartida());
					tmpOcupacionAsientos.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					UtilData.auditarRegistro(tmpOcupacionAsientos, false, getUsuario(), Executions.getCurrent());
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
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), null);
						else
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), null);
						return;
					}else{	/*	Agregamos el asiento a la lista de seleccionados	*/
						mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);

// 						Listitem listitemAsientos = new Listitem();
//						Listcell cell = new Listcell(asientoSeleccionado.getDetalleItinerario().getRuta().toString());
//						listitemAsientos.appendChild(cell);
//						cell = new Listcell(asientoSeleccionado.getNumeroAsiento().toString());
//						listitemAsientos.appendChild(cell);
						asientoSeleccionado.getDetalleItinerario().setAsiento(asientoSeleccionado.getNumeroAsiento().toString());
						asientoSeleccionado.getDetalleItinerario().setPiso(String.valueOf(asientoSeleccionado.getPiso()));
						asientoSeleccionado.getDetalleItinerario().setZona(String.valueOf(asientoSeleccionado.getNumeroZona()));

						//MAOE: La idea es que cada asiento seleccionado tenga su tarifa de acuerdo al nuevo modelo
						//Obtener la tarifa regular del asiento y almacenarlo en DetalleItinerario del asientoSeleccionado
//						List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(
//								1, asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(),
//								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
//								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
//								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
//								asientoSeleccionado.getPiso(),
//								asientoSeleccionado.getNumeroZona());
						List<TarifaRegular> lstTarifaRegular = buscarTarifa(
								asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(),
								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
								asientoSeleccionado.getPiso(),
								asientoSeleccionado.getNumeroZona());
						if(lstTarifaRegular.size()>0) {
							Double montoTarifa = .00;
							for(TarifaRegular tarifaRegular: lstTarifaRegular) {
								if(tarifaRegular.getTarifa()!=null && tarifaRegular.getTarifa().getPisoBus().intValue()==asientoSeleccionado.getPiso()) {
									montoTarifa = (tarifaRegular.getMonto()!=null?tarifaRegular.getMonto():.00);
									break;
								}										
							}															
							asientoSeleccionado.getDetalleItinerario().setTarifa(montoTarifa);
						}else
							asientoSeleccionado.getDetalleItinerario().setTarifa(0.00);

						/*============================================================*/
						asientoSeleccionado.getDetalleItinerario().setEsIda(esIda);
						/*============================================================*/
						DetalleItinerario detalle = (DetalleItinerario)asientoSeleccionado.getDetalleItinerario().clone();
						detalle.setObjAsiento(asientoSeleccionado);
//						listitemAsientos.setValue(detalle);
//						ltbxVtaAsientosSeleccionados.appendChild(listitemAsientos);
						
						//Agerga a la lista de asientos seleccionaddos
						addListAsientoSeleccionado(detalle);
						
//						if(ltbxVtaAsientosSeleccionados.getSelectedCount()==0) {
//							ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
//							onSelect_ltbxVtaAsientosSeleccionados();
//						}
						
//						operacionAsiento(asientoSeleccionado);
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
	}
	
	
	/**
	 * Busca la tarifa
	 * @param servicioId
	 * @param rutaId
	 * @param fechaPartida
	 * @param horaPartida
	 * @param piso
	 * @param numeroZona
	 * @return
	 * @throws Exception
	 */
	private List<TarifaRegular> buscarTarifa(Integer servicioId, Integer rutaId, String fechaPartida, String horaPartida, Integer piso, Integer numeroZona)throws Exception{
		
		List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(
				Constantes.ID_CANVEN_COUNTER, servicioId, rutaId, fechaPartida, horaPartida, piso, numeroZona);
		
		
		return lstTarifaRegular;
	}
	
	/**
	 * 
	 * @param detlleItinerario
	 * @throws Exception
	 */
	private void addListAsientoSeleccionado(DetalleItinerario detalleItinerario)throws Exception{
		int estadoAsientoSelect = detalleItinerario.getObjAsiento().getEstadoAsiento();

		//Valida que, el estado del asiento seleccionado sea igual a los ya seleccionados
		for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
			VentaIdaRetorno venta = item.getValue();
			Asiento asiento = null;
			if(venta.getDetalleItinerarioIDA()!=null)
				asiento = venta.getDetalleItinerarioIDA().getObjAsiento();
			else if(venta.getDetalleItinerarioRETORNO()!=null)
				asiento = venta.getDetalleItinerarioRETORNO().getObjAsiento();
			
			if(asiento!=null) {
				int estadoAsientoList = asiento.getEstadoAsiento();				
				if(estadoAsientoList !=estadoAsientoSelect) {
					if(estadoAsientoList==Constantes.ASIENTO_BLOQUEADO) {
						liberarAsientos();
						onRefreshMap();
					}
					Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
					break;
				}				
			}		
		}
		
		boolean isItemUpdated = false;
		if(ltbxVtaAsientosSeleccionados.getItemCount()>0) {
			for(Listitem _item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno venta = _item.getValue();
				if(venta.getDetalleItinerarioIDA()!=null && venta.getDetalleItinerarioRETORNO()==null && !detalleItinerario.getEsIda()) {
					venta.setDetalleItinerarioRETORNO(detalleItinerario);					
					String nameRuta = venta.getDetalleItinerarioIDA().getRuta().toString();
					nameRuta += " - "+ detalleItinerario.getRuta().getDestino();
					
					String numeroAsiento = venta.getDetalleItinerarioIDA().getAsiento();
					numeroAsiento += " - "+ detalleItinerario.getAsiento();
					
					((Listcell) _item.getFirstChild()).setLabel(nameRuta);
					((Listcell) _item.getLastChild()).setLabel(numeroAsiento);
					
					isItemUpdated = true;
					break;
				}else if(venta.getDetalleItinerarioRETORNO()!=null && venta.getDetalleItinerarioIDA()==null && detalleItinerario.getEsIda()) {
					venta.setDetalleItinerarioIDA(detalleItinerario);					
					String nameRuta = detalleItinerario.getRuta().toString();
					nameRuta += " - "+ venta.getDetalleItinerarioRETORNO().getRuta().getDestino();
					
					String numeroAsiento = detalleItinerario.getAsiento();
					numeroAsiento += " - "+ venta.getDetalleItinerarioRETORNO().getAsiento();
					
					((Listcell) _item.getFirstChild()).setLabel(nameRuta);
					((Listcell) _item.getLastChild()).setLabel(numeroAsiento);
					
					isItemUpdated = true;
					break;
				}
			}
		}		
		
		if(!isItemUpdated) {
			VentaIdaRetorno venta = new VentaIdaRetorno();
			if(detalleItinerario.getEsIda())
				venta.setDetalleItinerarioIDA(detalleItinerario);
			else
				venta.setDetalleItinerarioRETORNO(detalleItinerario);
			Listitem listitemAsientos = new Listitem();
			Listcell cell = new Listcell(detalleItinerario.getRuta().toString());
			listitemAsientos.appendChild(cell);
			cell = new Listcell(detalleItinerario.getAsiento());
			listitemAsientos.appendChild(cell);
			listitemAsientos.setValue(venta);
			ltbxVtaAsientosSeleccionados.appendChild(listitemAsientos);
		}
		
//		if(ltbxVtaAsientosSeleccionados.getSelectedCount()==0) {
		if(ltbxVtaAsientosSeleccionados.getItemCount()>0) {
			ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
			onSelect_ltbxVtaAsientosSeleccionados();
		}
		
		calcularTotalPago();
	}
	
	/**
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String key, Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{
			Long itinerarioId = detalleItinerario.getItinerario().getId();
			
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			List<Listitem> items = ltbxVtaAsientosSeleccionados.getItems();
			for(int i=0; i<items.size(); i++){
				Listitem listitem = items.get(i);
				VentaIdaRetorno venta = listitem.getValue();
				boolean desbloquearAsiento = false;
				
				if(venta.getDetalleItinerarioIDA()!=null && venta.getDetalleItinerarioIDA().getItinerario().getId().longValue()==itinerarioId 
						&& venta.getDetalleItinerarioIDA().getAsiento().equals(buffer[0]) && venta.getDetalleItinerarioIDA().getPiso().equals(buffer[1])) {					
					
					venta.setDetalleItinerarioIDA(null);
					if(venta.getDetalleItinerarioIDA()==null && venta.getDetalleItinerarioRETORNO()==null)
						ltbxVtaAsientosSeleccionados.removeItemAt(i);
					else if(venta.getDetalleItinerarioRETORNO()!=null) {
						((Listcell)listitem.getFirstChild()).setLabel(venta.getDetalleItinerarioRETORNO().getRuta().toString());
						((Listcell)listitem.getLastChild()).setLabel(venta.getDetalleItinerarioRETORNO().getAsiento());
					}
					
					desbloquearAsiento = true;
				}else if(venta.getDetalleItinerarioRETORNO()!=null && venta.getDetalleItinerarioRETORNO().getItinerario().getId().longValue()==itinerarioId 
						&& venta.getDetalleItinerarioRETORNO().getAsiento().equals(buffer[0]) && venta.getDetalleItinerarioRETORNO().getPiso().equals(buffer[1])) {
					
					venta.setDetalleItinerarioRETORNO(null);
					if(venta.getDetalleItinerarioRETORNO()==null && venta.getDetalleItinerarioIDA()==null)
						ltbxVtaAsientosSeleccionados.removeItemAt(i);
					else if(venta.getDetalleItinerarioIDA()!=null) {
						((Listcell)listitem.getFirstChild()).setLabel(venta.getDetalleItinerarioIDA().getRuta().toString());
						((Listcell)listitem.getLastChild()).setLabel(venta.getDetalleItinerarioIDA().getAsiento());
					}
					
					desbloquearAsiento = true;					
				}
								
				if(desbloquearAsiento) {					
					if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
						mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);
					}

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
	 * Realiza el refresco del mapa del bus.
	 * @throws Exception
	 */
	public void onRefreshMap() {
		onRefreshMapaAsientos(mapaAsientosIda, detalleItinerarioIda, null);
		if(rdBusqIdaVuelta.isSelected())
			onRefreshMapaAsientos(mapaAsientosRetorno, detalleItinerarioVuelta, null);
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
	 * Carga los tipos de Forma de pago.
	 */
	public void loadTipoFormaPago(FormaPago formaPago){
		try{			
			/*Implementado 15-03-2013 - jabanto*/
			cmbVtaOperadorTarjeta.getItems().clear();
			cmbVtaTarjeta.getItems().clear();			
			cmbVtaOperadorTarjeta.setText(null);
			cmbVtaTarjeta.setText(null);
			cmbVtaOperadorTarjeta.setDisabled(true);
			cmbVtaTarjeta.setDisabled(true);
			txtVtaNumeroOperacion.setDisabled(true);
			
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("formaPago.id", formaPago.getId());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			List<TipoFormaPago> lstTipoFormasPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);				
			for(TipoFormaPago tipoFormaPago : lstTipoFormasPago){
				Comboitem item = new Comboitem();
				item.setLabel(tipoFormaPago.getDenominacion());
				item.setValue(tipoFormaPago);
				cmbVtaTipoFormaPago.appendChild(item);
			}

			if(formaPago.getId().equals(Constantes.ID_FORPAG_CONTADO))
				Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbVtaTipoFormaPago, Constantes.ID_TIPFORPAG_EFECTIVO);				
			
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	/**
	 * Cargamos los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void loadPuntoEmbarque(DetalleItinerario detItinerario, Combobox cmbParent){
		try{
			cmbParent.getItems().clear();
			int agenciaIgualEmbarque=0;

			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<>();
			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(),
																							 Constantes.VALUE_ACTIVO,
																							 detItinerario.getRuta().getLocalidadOrigen().getId());
			UtilData.cargarGenericData(cmbParent, false);
			/*	Cargamos los puntos de embarque	*/
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion() +" - "+ itiAgePartida.getHoraPartida());
				item.setValue(itiAgePartida);
				cmbParent.appendChild(item);
				if(arrayItiAgePartida.size() == 1)
					cmbParent.setSelectedItem(item);								
				else if(getAgencia().getId().intValue() == itiAgePartida.getAgencia().getId().intValue()){
					//Si la agencia que vende es punto de embarque y esta dentro del itinerario, seleccionamos esa agencia
					cmbParent.setSelectedItem(item);
					agenciaIgualEmbarque = 1;
				} else if(agenciaIgualEmbarque == 0)
					cmbParent.setSelectedIndex(0);
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
	private void loadPuntoDesembarque(DetalleItinerario detItinerario, Combobox cmbParent) {
		try{
			cmbParent.getItems().clear();
			ArrayList<ItinerarioAgenciaLlegada> arrayItiAgeLlegada = new ArrayList<>();
			/*	Si la agencia de llegada del itinerario es la misma a la agencia de llegada de la ruta seleccionada	*/
			arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO, detItinerario.getRuta().getLocalidadDestino().getId());
			UtilData.cargarGenericData(cmbParent, false);
			for(ItinerarioAgenciaLlegada itiAgeLlegada : arrayItiAgeLlegada){
				Comboitem item = new Comboitem(itiAgeLlegada.getAgencia().getDenominacion()+" - "+itiAgeLlegada.getHoraLlegada());
                item.setValue(itiAgeLlegada);
                cmbParent.appendChild(item);
				if(arrayItiAgeLlegada.size()==1){
					cmbParent.setSelectedItem(item);
				}
				else
					cmbParent.setSelectedIndex(0);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Permite liberar todos los asientos asientos bloqueados 
	 */
	private void liberarAsientos(){
		try{			
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(getUsuarioHardware().getId());			
			ltbxVtaAsientosSeleccionados.getItems().clear();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			log.error(ex);
		}
	}
	
	
	/**
	 * Calcula el monto total a pagar
	 * @throws Exception
	 */
	private void calcularTotalPago()throws Exception{
		Double totalPago = .00;
		if(rdVtaVenta.isSelected()) {
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno venta = item.getValue();
				if(venta.getDetalleItinerarioIDA()!=null)
					totalPago += (venta.getDetalleItinerarioIDA().getTarifa()!=null?venta.getDetalleItinerarioIDA().getTarifa():.00);			
				if(venta.getDetalleItinerarioRETORNO()!=null)
					totalPago += (venta.getDetalleItinerarioRETORNO().getTarifa()!=null?venta.getDetalleItinerarioRETORNO().getTarifa():.00);
			}	
		}		
		dbxVtaTotalPagar.setValue(totalPago);
	}
	
	/**
	 * Realiza la búsqueda del pasajero
	 */
	private void buscarPasajero(String patron, Event event)throws Exception {
		try {				
			if(patron.length() > 5) {
				cleanDatosPasajero(false);
				TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
				ArrayList<Pasajero> listPasajeros = null;
				
				if(Util.isNumeric(patron)) {
					criterioBusqueda.put("numeroDocumento", patron.toUpperCase()+"%");
					List<String> criteriosOrdenar = new ArrayList<>();
					criteriosOrdenar.add("apellidoPaterno");
					criteriosOrdenar.add("apellidoMaterno");
					criteriosOrdenar.add("nombre");
					listPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
				}else {
					String nombres = patron.trim().toUpperCase();
					String[] str1 = nombres.trim().split(" ");
					listPasajeros = ServiceLocator.getPasajeroManager().buscarPorFullTextIndex(str1);
				}
				
				if(listPasajeros != null && listPasajeros.size()>0) {
					if(listPasajeros.size()==1)
						loadDatosPasajero(listPasajeros.get(0));
					else {
						List<Object> _listPasajeros = new ArrayList<Object>();			
						listPasajeros.forEach(pasajero -> _listPasajeros.add(pasajero));						
						showWindowListPasajero(_listPasajeros);
					}
				}else {					
					if(Util.isNumeric(patron)) {
						List<String> dni = RESTCiva.getDatosDni(patron);
						if(dni!=null){							
							Pasajero pasajero = new Pasajero();
							pasajero.setTipoDocumento(new TipoDocumento(Constantes.ID_TIPDOC_DNI));
							pasajero.setNumeroDocumento(dni.get(0));
							pasajero.setNombre(dni.get(1));
							pasajero.setApellidoPaterno(dni.get(2));
							pasajero.setApellidoMaterno(dni.get(3)!=null?dni.get(3):null);
							pasajero.setValidadoReniec(Constantes.TRUE_VALUE);
							loadDatosPasajero(pasajero);
						}else {
							Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaPaxTipoDocumento, Constantes.ID_TIPDOC_DNI);
							txtVtaPaxNumeroDocumento.setText(patron);
							txtVtaPaxNombres.setFocus(true);
						}
					}
				}
			}else
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaPaxTipoDocumento, Constantes.ID_TIPDOC_DNI);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Carga los datos de la informacion del viaje
	 * @param isIda: (true) viaje de Ida, (false) viaje de vuelta
	 * @param ventaPasaje
	 * @throws Exception
	 */
	private void loadDatosViaje(boolean isIda, VentaPasaje ventaPasaje)throws Exception{
		seleccionarPuntoEmbarque(isIda, ventaPasaje.getAgenciaPartida());
		seleccionarPuntoDesembarque(isIda, ventaPasaje.getAgenciaLlegada());
		if(isIda) {
			lblVtaIdaAsiento.setValue(ventaPasaje.getNumeroAsiento().toString());
			if(!ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA))
			    dbxVtaIdaTarifa.setValue(ventaPasaje.getImportePagado());	
		}else {
			lblVtaVueltaAsiento.setValue(ventaPasaje.getNumeroAsiento().toString());
			if(!ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA))
				dbxVtaVueltaTarifa.setValue(ventaPasaje.getImportePagado());
		}
	}
	
	/**
	 * Carga los datos del tipo y numero del comprobante
	 * @param ventaPasaje
	 * @throws Exception
	 */
	private void loadDatosinfoComprobante(VentaPasaje ventaPasaje)throws Exception{
		if(!ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
			lblVtaTipoComprobante.setValue(ventaPasaje.getTipoComprobante().getDenominacion());
			lblVtaNumeroComprobante.setValue(ventaPasaje.getNumeroBoleto());
		}		
	}
	
	/**
	 * Carga los datos del pasajero
	 * @param pasajero: Instancia de la clase pasajero
	 * @throws Exception
	 */
	private void loadDatosPasajero(Pasajero pasajero)throws Exception{
		cleanDatosPasajero(false);
		//
		Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaPaxTipoDocumento, pasajero.getTipoDocumento().getId());
		txtVtaPaxNumeroDocumento.setText(pasajero.getNumeroDocumento());
		txtVtaPaxNombres.setText(pasajero.getNombre());
		txtVtaPaxApePaterno.setText(pasajero.getApellidoPaterno());
		txtVtaPaxApeMaterno.setText(pasajero.getApellidoMaterno()!=null?pasajero.getApellidoMaterno():"");
		if(pasajero.getSexo()!=null) {
			rdVtaPaxFemenino.setChecked(pasajero.getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO);
			rdVtaPaxMasculino.setChecked(pasajero.getSexo().getId().intValue()==Constantes.ID_SEXO_MASCULINO);	
		}		
		itbxVtaPaxEdad.setValue(pasajero.getFechaNacimiento()!=null?getEdad(pasajero.getFechaNacimiento()):null);
		txtVtaPaxTelefono.setText(pasajero.getTelefono()!=null? pasajero.getTelefono():"");
		txtVtaPaxCorreo.setText(pasajero.getEmail()!=null?pasajero.getEmail():"");
		
		this.pasajero = pasajero;
		
		if(pasajero.getId()==null)
			rdVtaPaxFemenino.setFocus(true);
		else if(txtVtaFacRuc.isVisible() && txtVtaFacRuc.getText().trim().isEmpty())
			txtVtaFacRuc.setFocus(true);
		else if(gridVtaDatosPago.isVisible() && cmbVtaTipoFormaPago.getSelectedIndex()<0 && !cmbVtaTipoFormaPago.isDisabled()) {
			cmbVtaTipoFormaPago.setFocus(true);
//			cmbVtaTipoFormaPago.open();
		}else if(gridVtaIdaDatosReserva.isVisible()) {			
			if(!itbxVtaIdaTiempoReserva.isDisabled()) {
				itbxVtaIdaTiempoReserva.setFocus(true);
				itbxVtaIdaTiempoReserva.select();
			}else if(!dtbxVtaIdaExpiraReserva.isDisabled())
				dtbxVtaIdaExpiraReserva.setFocus(true);
		}else
			btnVtaGuardar.setFocus(true);
	}
	
	private void buscarCliente(String patron)throws Exception {
		try {
			cliente = null;
			txtVtaFacRazonSocial.setText("");
			txtVtaFacDireccion.setText("");
			
			TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
			ArrayList<Cliente> lstClientes = null;
			if(patron.length() > 5) {
				if(Util.isNumericLong(patron)) {
					criterioBusqueda.put("numeroDocumento", patron.toUpperCase()+"%");
					List<String> criteriosOrdenar = new ArrayList<>();
					criteriosOrdenar.add("razonSocial");
					criteriosOrdenar.add("numeroDocumento");
					lstClientes = ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
				}else {
					String razon = patron.trim().toUpperCase();
					String[] str1 = razon.trim().split(" ");
					lstClientes = ServiceLocator.getClienteManager().buscarPorRazonSocial(str1);
				}
				if(lstClientes != null && lstClientes.size()>0) {
					if(lstClientes.size()==1)
						loadDatosCliente(lstClientes.get(0));
					else {
						List<Object> _listClientes = new ArrayList<Object>();			
						lstClientes.forEach(pasajero -> _listClientes.add(pasajero));						
						showWindowListPasajero(_listClientes);
					}
				}else {
					
					if(Util.isNumericLong(patron)) {
						List<String> ruc = RESTCiva.getDatosRuc(patron);
						if(ruc!=null){
							Cliente cliente = new Cliente();
							cliente.setNumeroDocumento(ruc.get(0));
							cliente.setRazonSocial(ruc.get(1));
							cliente.setDireccion(ruc.get(2)!=null?ruc.get(2):null);
							loadDatosCliente(cliente);
						}else {							
							txtVtaFacRuc.setText(patron);		
							txtVtaFacRazonSocial.setFocus(true);
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Carga los datos del cliente
	 * @param cliente: isntancia de la class cliente
	 * @throws Exception
	 */
	private void loadDatosCliente(Cliente cliente)throws Exception{
		txtVtaFacRuc.setText(cliente.getNumeroDocumento());
		txtVtaFacRazonSocial.setText(cliente.getRazonSocial());
		txtVtaFacDireccion.setText(cliente.getDireccion()!=null?cliente.getDireccion():"");
				
		this.cliente = cliente;
		
		chbxVtaFactura.setChecked(true);
		if(txtVtaFacDireccion.getText().trim().isEmpty())
			txtVtaFacDireccion.setFocus(true);
		else if(gridVtaDatosPago.isVisible() && !cmbVtaTipoFormaPago.isDisabled() && cmbVtaTipoFormaPago.getSelectedIndex()<0 && !cmbVtaTipoFormaPago.isDisabled()) {
			cmbVtaTipoFormaPago.setFocus(true);
//			cmbVtaTipoFormaPago.open();
		}else if(gridVtaIdaDatosReserva.isVisible()) {
			if(!itbxVtaIdaTiempoReserva.isDisabled()) {
				itbxVtaIdaTiempoReserva.setFocus(true);
				itbxVtaIdaTiempoReserva.select();
			}else if(!dtbxVtaIdaExpiraReserva.isDisabled())
				dtbxVtaIdaExpiraReserva.setFocus(true);
		}else
			btnVtaGuardar.setFocus(true);
	}
	
	
	/**
	 * Muestra la lista de pasajeros en una ventana modal
	 * @param listPasajero: Instancia con la lista de pasajeros
	 * @throws Exception
	 */
	private void showWindowListPasajero(List<Object> listResultados)throws Exception{		
		wndModal = createWindowListPasajero(listResultados);
		wndVentaReservaNew.appendChild(wndModal);
		wndModal.setMode("modal");
		if(ltbxResultBusqueda.getItemCount()>0)
			ltbxResultBusqueda.setSelectedIndex(0);
	}
	
	
	/**
	 * Crea una venta para mostrar la lista de pasajeros
	 * @param listPasajero: Instancia con la lista de pasajeros
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private Window createWindowListPasajero(List<Object> listResultados)throws Exception{
		try {
			final Window window = new Window("Lista de registros", "normal", true);
			window.setWidth("750px");			
			
			Grid grid = new Grid();
			Columns columns = new Columns();
			columns.appendChild(new Column(null, null, "100%"));
			grid.appendChild(columns);
			Rows rows = new Rows();
			Row row = new Row();
			
			ltbxResultBusqueda = new Listbox();
			ltbxResultBusqueda.setHeight("350px");
			Listhead listhead = new Listhead();
			listhead.appendChild(new Listheader("TIPO DOCUMENTO", null, "120px"));
			listhead.appendChild(new Listheader("NRO DOCUMENTO", null, "160px"));
			listhead.appendChild(new Listheader("NOMBRES/RAZÓN SOCIAL"));
			ltbxResultBusqueda.appendChild(listhead);
			
			for(Object object: listResultados) {
				Pasajero pasajero = null;
				Cliente cliente = null;
				
				if(object instanceof Pasajero)
					pasajero = (Pasajero) object;					
				else if(object instanceof Cliente)
					cliente = (Cliente) object;
					
				if(pasajero !=null || cliente !=null) {
					Listitem item = new Listitem();
					Listcell cell = new Listcell(pasajero!=null?pasajero.getTipoDocumento().getDenominacion():"RUC");
					item.appendChild(cell);
					cell = new Listcell(pasajero!=null?pasajero.getNumeroDocumento():cliente.getNumeroDocumento());
					cell.setStyle("font-size:12px !important");
					item.appendChild(cell);
					cell = new Listcell(pasajero!=null?pasajero.toString():cliente.toString());
					item.appendChild(cell);					
					item.setValue(object);
					ltbxResultBusqueda.appendChild(item);
				}				
			}
			
			row.appendChild(ltbxResultBusqueda);
			rows.appendChild(row);			
			grid.appendChild(rows);
			window.appendChild(grid);
			
			grid = new Grid();
			columns = new Columns();
			Column column = new Column();
			column.setAlign("right");
			columns.appendChild(column);
			grid.appendChild(columns);
			rows = new Rows();
			row = new Row();
			Hbox hbox = new Hbox();
			hbox.setAlign("center");
			Button btnCancelar = new Button();
			btnCancelar.setLabel("Cancelar");
			btnCancelar.setSclass("btnCommandL");
			btnCancelar.setSrc("resources/mp_cerrar.png");
			hbox.appendChild(btnCancelar);
			
			Button btnAceptar = new Button();
			btnAceptar.setLabel("Aceptar");
			btnAceptar.setSrc("resources/mp_aceptarEnabled.png");
			btnAceptar.setStyle("margin-left:15px");
			btnAceptar.setSclass("btnCommandL");
			hbox.appendChild(btnAceptar);
			
			row.appendChild(hbox);
			rows.appendChild(row);
			grid.appendChild(rows);
			window.appendChild(grid);
			
			ltbxResultBusqueda.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						Object object = ltbxResultBusqueda.getSelectedItem().getValue();
						if(object instanceof Pasajero)
							loadDatosPasajero((Pasajero)object);
						else if(object instanceof Cliente)
							loadDatosCliente((Cliente)object);
						
						window.onClose();
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					}
				}
			});
			
			btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						if(ltbxResultBusqueda.getSelectedCount()==0) {
							DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectRegistro"), ltbxResultBusqueda);
							return;
						}
						
						Object object = ltbxResultBusqueda.getSelectedItem().getValue();
						if(object instanceof Pasajero)
							loadDatosPasajero((Pasajero)object);
						else if(object instanceof Cliente)
							loadDatosCliente((Cliente)object);
						
						window.onClose();
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					}
				}
			});
			
			btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						window.onClose();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					}
				}
			});
			
			return window;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e);
			return null;
		}
	}
	
	/**
	 * Calcula la edad, a partir de la fecha
	 * @param fechaNacimiento
	 * @return
	 */
	private Integer getEdad(String fechaNacimiento){
		Integer edad = null;
		if(fechaNacimiento != null){
			String anio = fechaNacimiento.substring(fechaNacimiento.length()-4);
			String year = Util.DatetoString(new Date(), "yyyy");
			edad = Integer.valueOf(year)-Integer.valueOf(anio);
		}
		
		return edad;
	}
	
	/**
	 * Calcula la fecha de nacimiento a partir de la edad
	 * @param edad: Edad
	 * @return
	 * @throws Exception
	 */
	private String getFechaNacimiento(int edad)throws Exception{
		String fechaNacimiento = null;
		
		String month = Util.DatetoString(new Date(), "MM");
		String day = Util.DatetoString(new Date(), "dd");
		String year = Util.DatetoString(new Date(), "yyyy");
		Integer yearNacimiento = Integer.valueOf(year) - edad;
		
		fechaNacimiento = day+"/"+month+"/"+yearNacimiento;
		
		return fechaNacimiento;
	}
	
	/**
	 * Carga la especie valodarada para la venta
	 * @throws Exception
	 */
	private ControlEspecieValorada loadEspecieValoradaByVenta(boolean isIda)throws Exception{
		ControlEspecieValorada controlEspecieValorada = null;
		int empresa_id = (isIda? detalleItinerarioIda.getItinerario().getEmpresa().getId(): detalleItinerarioVuelta.getItinerario().getEmpresa().getId());
		if(chbxVtaFactura.isChecked())
			controlEspecieValorada = loadEspecieValorada(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA), empresa_id);
		else
			controlEspecieValorada = loadEspecieValorada(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA), empresa_id);
		
		return controlEspecieValorada;
	}
	
	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 */
	private ControlEspecieValorada loadEspecieValorada(TipoComprobante tipoComprobante, Integer idEmpresa)throws Exception{
		try {
			cleanDatosInfoComprobante();
			
			ControlEspecieValorada controlEspecieValorada = null;
			controlEspecieValorada = UtilData.buscarEspecieValoradaByCaja(tipoComprobante.getId(), getAgencia(), false, getUsuarioHardware(), null, idEmpresa);				
			if(controlEspecieValorada !=null) {
				lblVtaTipoComprobante.setValue(controlEspecieValorada.getTipoComprobante().getDenominacion());
				lblVtaNumeroComprobante.setValue(controlEspecieValorada.toString());
			}			
			
			return controlEspecieValorada;
		} catch (EspecieValoradaNotAvailableException nev) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));
			log.error(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));
			return null;
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * Analisa a que control se enviará el foco del curso, según el control en donde se ha producido el evento
	 * @throws Exception
	 */
	private void focusCursorByTipoOperacion(Event event)throws Exception{
//		if(event!=null) {
//			if(event.getTarget().getId().equals(rdVtaVenta.getId()) || event.getTarget().getId().equals(rdVtaReserva.getId())) {
				if(!cmbVtaIdaEmbarque_hora.isDisabled() && cmbVtaIdaEmbarque_hora.getSelectedIndex()==0) {
					cmbVtaIdaEmbarque_hora.setFocus(true);
//					cmbVtaIdaEmbarque_hora.open();
				}else if(!cmbVtaIdaDesembarque_hora.isDisabled() && cmbVtaIdaDesembarque_hora.getSelectedIndex()==0) {
					cmbVtaIdaDesembarque_hora.setFocus(true);
//					cmbVtaIdaDesembarque_hora.open();
				}else if(gridVtaInfoViajeVuelta.isVisible() && !cmbVtaVueltaEmbarque_hora.isDisabled() && cmbVtaVueltaEmbarque_hora.getSelectedIndex()==0) {
					cmbVtaVueltaEmbarque_hora.setFocus(true);
//					cmbVtaVueltaEmbarque_hora.open();
				}else if(gridVtaInfoViajeVuelta.isVisible() && !cmbVtaVueltaDesembarque_hora.isDisabled() && cmbVtaVueltaDesembarque_hora.getSelectedIndex()==0) {
					cmbVtaVueltaDesembarque_hora.setFocus(true);
//					cmbVtaVueltaDesembarque_hora.open();					
				}else if(event!=null && event.getTarget().getId().equals(rdVtaVenta.getId())) {				
					if(!txtVtaPaxNumeroDocumento.isDisabled() && txtVtaPaxNumeroDocumento.getText().trim().isEmpty()) {
						txtVtaPaxBusqueda.setFocus(true);
					}else if(!cmbVtaTipoFormaPago.isDisabled() && cmbVtaTipoFormaPago.getSelectedIndex()<0 && !cmbVtaTipoFormaPago.isDisabled()) {
						cmbVtaTipoFormaPago.setFocus(true);
						cmbVtaTipoFormaPago.open();
					}else
						btnVtaGuardar.setFocus(true);
				}else if(event!=null && event.getTarget().getId().equals(rdVtaReserva.getId())) {
					if(txtVtaPaxNumeroDocumento.getText().trim().isEmpty())
						txtVtaPaxBusqueda.setFocus(true);
					else if(!itbxVtaIdaTiempoReserva.isDisabled()) {
						itbxVtaIdaTiempoReserva.setFocus(true);
						itbxVtaIdaTiempoReserva.select();
					}
				}
//			}
//		}
	}
	
	/**
	 * 
	 * @param isIda
	 * @param tiempoReserva
	 * @return
	 * @throws Exception
	 */
	private boolean validarTiempoPermitidoReserva(boolean isIda, Integer tiempoReserva)throws Exception{
		int tiempoMaximoPermitido = 0;
		
		if(tiempoReserva==null) {
			String horaPartida = null;
			String fechaPartida = null;
			Date fechaHoraReserva = null;
			if(isIda) {
				horaPartida = ((ItinerarioAgenciaPartida)cmbVtaIdaEmbarque_hora.getSelectedItem().getValue()).getHoraPartida();				
				fechaPartida = Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida());			
				fechaHoraReserva = dtbxVtaIdaExpiraReserva.getValue();
			}else {
				horaPartida = ((ItinerarioAgenciaPartida)cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue()).getHoraPartida();				
				fechaPartida = Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida());			
				fechaHoraReserva = dtbxVtaVueltaExpiraReserva.getValue();
			}			
			
			String sfechaHoraPartida = fechaPartida+" "+horaPartida;
			Date fechaHoraPartida = Util.convertirLocalDateTimeToDate(convertirStringToLocalDateTime(sfechaHoraPartida));			
			tiempoReserva = (int) (fechaHoraPartida.getTime() - fechaHoraReserva.getTime());
		}
				
		if(tiempoMaximoPermitido >0 && tiempoReserva > tiempoMaximoPermitido) {
			Intbox intboxTiempo = (isIda?itbxVtaIdaTiempoReserva:itbxVtaVueltaTiempoReserva);
			intboxTiempo.setValue(null);
			DlgMessage.information(Messages.getString("WndVentaReserva.information.tiempoReservaExcede").replace("@tiempo", String.valueOf(tiempoMaximoPermitido)), intboxTiempo);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Habilita/Deshabilita el Button Gurdar
	 * @param disabled
	 * @throws Exception
	 */
	private void setDisabled_btmVtaGuardar(boolean disabled)throws Exception{
		
		btnVtaGuardar.setDisabled(disabled);
		if(disabled) {
			btnVtaGuardar.setSclass("");
			btnVtaGuardar.setWidth("120px");
			btnVtaGuardar.setHeight("30px");
			btnVtaGuardar.setImage("resources/mp_guardarDisabled.png");	
		}else {
			btnVtaGuardar.setSclass("btnCommandL");
			btnVtaGuardar.setImage("resources/mp_guardarEnabled.png");	
		}		
	}

	
	/**
	 * Selecciona el punto de embarque
	 * @param isIda
	 * @param agencia
	 * @throws Exception
	 */
	private void seleccionarPuntoEmbarque(boolean isIda, Agencia agencia)throws Exception{
		Combobox comboboxEmbarque = null;
		if(isIda)
			comboboxEmbarque = cmbVtaIdaEmbarque_hora;
		else
			comboboxEmbarque = cmbVtaVueltaEmbarque_hora;
		
		for(Comboitem comboitem: comboboxEmbarque.getItems()) {
			if(comboitem.getValue()!=null && comboitem.getValue() instanceof ItinerarioAgenciaPartida) {
				ItinerarioAgenciaPartida itiAgePartida = comboitem.getValue();
				if(itiAgePartida.getAgencia().getId().intValue()==agencia.getId().intValue()) {
					comboboxEmbarque.setSelectedItem(comboitem);
					break;
				}
			}				
		}
	}
	
	/**
	 * Selecciona el punto de embarque
	 * @param isIda
	 * @param agencia
	 * @throws Exception
	 */
	private void seleccionarPuntoDesembarque(boolean isIda, Agencia agencia)throws Exception{
		Combobox comboboxEmbarque = null;
		if(isIda)
			comboboxEmbarque = cmbVtaIdaDesembarque_hora;
		else
			comboboxEmbarque = cmbVtaVueltaDesembarque_hora;
		
		for(Comboitem comboitem: comboboxEmbarque.getItems()) {
			if(comboitem.getValue()!=null && comboitem.getValue() instanceof ItinerarioAgenciaLlegada) {
				ItinerarioAgenciaLlegada itiAgePartida = comboitem.getValue();
				if(itiAgePartida.getAgencia().getId().intValue()==agencia.getId().intValue()) {
					comboboxEmbarque.setSelectedItem(comboitem);
					break;
				}
			}				
		}
	}
	
	/***
	 * Crea una venta para la anulación de una venta
	 * @param ventaOriginal
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Window createWindowAnulacion(final VentaPasaje ventaOriginal){
		Caption caption = null;
		Groupbox groupbox = null;
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = null;
		Rows rows = new Rows();
		Row row = null;
		Label label = null;
		Textbox text = null;

		final Window win = new Window("", "normal", true);
		win.setWidth("500px");

		caption = new Caption("ANULACION DE BOLETO", "resources/mp_anular.png");
		win.appendChild(caption);
		label = new Label("Se va a realizar la Anulacion del Boleto con los siguientes datos :");
		label.setStyle("font-size:12px !important");
		win.appendChild(label);

		win.appendChild(new Separator("horizontal"));

		groupbox = new Groupbox();
		groupbox.setClosable(false);
		caption = new Caption("Datos del Boleto");
		groupbox.appendChild(caption);

		/*	Columna 1	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 2	*/
		column = new Column();
		columns.appendChild(column);
		/*	Columna 3	*/
		column = new Column();
		column.setAlign("right");
		columns.appendChild(column);
		/*	Columna 4	*/
		column = new Column();
		columns.appendChild(column);

		grid.appendChild(columns);

		row = new Row();
		label = new Label("NUMERO BOLETO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getNumeroBoleto());
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);
		label = new Label("FECHA VIAJE :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getFechaPartida()==null?"":Util.DatetoString(ventaOriginal.getFechaPartida(), Constantes.DATE_FORMAT));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		row.setSpans("1,3");
		label = new Label("PASAJERO :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getPasajero().toString());
		text.setReadonly(true);
		text.setWidth("314px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);
		rows.appendChild(row);

		row = new Row();
		label = new Label("RUTA :");
		row.appendChild(label);
		text = new Textbox(ventaOriginal.getRuta().getOrigen()+" - " + ventaOriginal.getRuta().getDestino());
		text.setReadonly(true);
		text.setWidth("100px");
		row.appendChild(text);
		label = new Label("IMPORTE :");
		row.appendChild(label);
		text = new Textbox(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
		text.setReadonly(true);
		text.setWidth("80px");
		text.setStyle("font-size:11px !important");
		row.appendChild(text);
		rows.appendChild(row);

		row=new Row();
		row.setSpans("1,4");
		label = new Label("MOTIVO ANULACION (*) :");
		row.appendChild(label);
		final Textbox txtMotivoAnulacion = new Textbox();
		txtMotivoAnulacion.setWidth("314px");
		txtMotivoAnulacion.setMultiline(true);
		txtMotivoAnulacion.setRows(3);
		txtMotivoAnulacion.setMaxlength(255);
		txtMotivoAnulacion.setStyle("font-size:11px !important");
		row.appendChild(txtMotivoAnulacion);
		rows.appendChild(row);


		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);

		grid = new Grid();
		columns = new Columns();
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();

		Button button = new Button("Continuar", "resources/mp_anular.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(txtMotivoAnulacion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noMotivoAnulacion"),txtMotivoAnulacion);
					return;
				}
				Messagebox.show(Messages.getString("WndLiquidacionDiariaVentas.information.confirmarAnulacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try{
							if(e.getName().equals("onYes")){
								confirmaAnulacion(ventaOriginal, wndModal,txtMotivoAnulacion.getText().trim().toUpperCase());
							}
						}catch(Exception ex){
							ex.printStackTrace();
							DlgMessage.information(this.getClass().getSimpleName()+" "+ex.getMessage());
						}
					}
				});
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		row.appendChild(button);
		button = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		button.setClass("btnCommandM");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				win.onClose();
			}
		});
		button.setHeight("28px");
		button.setWidth("100px");
		button.setFocus(true);
		row.appendChild(button);

		rows.appendChild(row);

		grid.appendChild(rows);
		win.appendChild(grid);
		return win;
	}

	/**
	 * Confirma la anulación de la venta
	 * @param ventaOriginal
	 * @param wndAnular
	 * @param motivo
	 * @throws Exception
	 */
	private void confirmaAnulacion(VentaPasaje ventaOriginal, Window wndAnular, String motivo)throws Exception{
		int result=Constantes.FAILURE;

		ventaOriginal.setTarifa(0.0);
		ventaOriginal.setRecargo(0.0);
		ventaOriginal.setDescuento(0.0);
		ventaOriginal.setImportePagado(0.0);
		ventaOriginal.setAcuenta(0.0);
		ventaOriginal.setImportePagado(0.0);
		ventaOriginal.setTarifaEquibalente(ventaOriginal.getTarifaEquibalente()!=null?.00:null);
		ventaOriginal.setDescuentoEquibalente(ventaOriginal.getDescuentoEquibalente()!=null?.00:null);
		ventaOriginal.setImportePagadoEquibalente(ventaOriginal.getImportePagadoEquibalente()!=null?.00:null);
		ventaOriginal.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_ANULACION));
		ventaOriginal.setObservaciones(motivo);
		ventaOriginal.setFechaAnulacion(new Date());
		ventaOriginal.setUsuarioAnulacion(getUsuario());
		UtilData.auditarRegistro(ventaOriginal, true, getUsuario(), Executions.getCurrent());
		VentaPasaje notaCredito= ServiceLocator.getVentaPasajesManager().anularMovimiento(ventaOriginal,false);
		if(notaCredito!=null){
			WSFE.sendNota(notaCredito);
		}
		result=Constantes.CORRECT;
		if(result==Constantes.CORRECT){
			DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.exitoAnulacion"));
			wndAnular.onClose();
		
			
			txtVtaPaxBusqueda.setText("");
			ltbxVtaAsientosSeleccionados.removeItemAt(ltbxVtaAsientosSeleccionados.getSelectedItem().getIndex());
			cleanDatosInfoComprobante();								
			onSelect_ltbxVtaAsientosSeleccionados();														
			onRefreshMap();
			disabledControlsViaje(true);
			disabledControlsPasajero(true);
			disabledControlsPago(true);
			if(ltbxVtaAsientosSeleccionados.getItemCount()==0)
				disabledAllButtonsOptinsByAsiento();
		}
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void autoRefreshMapaBus()throws Exception{
		try {

//			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
//			Date fechaSalida = detalleItinerarioIda.getFechaPartida();
			
			//Valida la ejecución de l atarea
//			if(fechaSalida.getTime() >= fechaActual.getTime()) {
//				int minutos = 5; //Cada cuantos minutos se va a ejecutar
//				int ltime = (int) (Constantes.MILISEGUNDOS_X_MINUTO * minutos);
//		        timerRefreshMapa=new Timer(ltime);
//		        timerRefreshMapa.setRepeats(true);
//		        timerRefreshMapa.start();
//				timerRefreshMapa.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						try {
//							if(divPaso_2.isVisible()) {
//								//Actualiza el Mapa
//					        	onRefreshMap();
//					            // Coloca aquí el código que deseas ejecutar
//					        	String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
//					        		   _message += " Ejecución de proceso automático que actualiza el Mapa del bus, cada "+ String.valueOf(minutos) +" minuto(s)...";
//					            System.out.println(_message);
//					            log.info("autoRefreshMapaBus "+ _message);
//							}else {
//								timerRefreshMapa.stop();
//					            // Coloca aquí el código que deseas ejecutar
//								String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
//								       _message +=  " Finalizando Proceso automático...";
//					            System.out.println(_message);
//					            log.info("autoRefreshMapaBus "+ _message);
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//							log.info(e);
//							if(timerRefreshMapa !=null) {
//								timerRefreshMapa.stop();
//								String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
//									   _message += " Ocurrió un error, se ha detenido el la tarea. Error:"+ e.getMessage();
//								System.out.println(_message);
//								log.error(_message);
//							}
//						}
//					}
//				});			
//				wndVentaReservaNew.appendChild(timerRefreshMapa);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(timerRefreshMapa !=null) {
				timerRefreshMapa.stop();
				String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
				   _message += " Ocurrió un error, se ha detenido el la tarea. Error:"+ e.getMessage();
			System.out.println(_message);
			log.error(_message);
			}
		}
	}
}
