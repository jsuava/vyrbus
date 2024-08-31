/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 1 feb. 2024
 * Hora			: 12:16:05
 */
package pe.itsb.vyrbus.view.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TimerTask;
import org.zkoss.zk.au.AuResponse;
import org.zkoss.zk.au.out.AuInvoke;
import org.hibernate.mapping.Collection;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Iframe;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.AutorizadorDescuento;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.ControlEspecieValorada;
import pe.itsb.vyrbus.model.bean.Descuento;
import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.DetalleManifiesto;
import pe.itsb.vyrbus.model.bean.DocumentoBus;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.HRE;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaPartida;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Manifiesto;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.MovimientoPasajes;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Personal;
import pe.itsb.vyrbus.model.bean.PreferenciaAlimentaria;
import pe.itsb.vyrbus.model.bean.ProgramacionServicio;
import pe.itsb.vyrbus.model.bean.Promocion;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.bean.TarifaRegular;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TipoMovimiento;
import pe.itsb.vyrbus.model.bean.TipoNota;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientosID;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.VentaTramo;
import pe.itsb.vyrbus.service.exceptions.CapacityExceedsException;
import pe.itsb.vyrbus.service.exceptions.DuplicateSeatException;
import pe.itsb.vyrbus.service.exceptions.EspecieValoradaNotAvailableException;
import pe.itsb.vyrbus.service.exceptions.ItinerarioException;
import pe.itsb.vyrbus.service.exceptions.ManifiestoDuplicateException;
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
import pe.itsb.vyrbus.service.util.CreateDocument;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Printapi;
import pe.itsb.vyrbus.service.util.RESTCiva;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.UtilFlag;
import pe.itsb.vyrbus.service.util.WSFE;
import pe.itsb.vyrbus.service.xml.XmlCarpetaDespacho;
import pe.itsb.vyrbus.service.xml.XmlDetalleCarpetaDespacho;
import pe.itsb.vyrbus.service.xml.XmlDetalleManifiesto;
import pe.itsb.vyrbus.service.xml.XmlItemDetalleDespacho;
import pe.itsb.vyrbus.service.xml.XmlItemDetalleManifiesto;
import pe.itsb.vyrbus.service.xml.XmlManifiesto;
import pe.itsb.vyrbus.service.xml.XmlPrintLaser;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;
import sun.misc.BASE64Encoder;

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
//	private Listbox ltbxBusqComprobantesPax;
	private Textbox txtBusqBy;
	private Button btnBusqBy;
	private Radio rdBusqIda;	
	private Radio rdBusqIdaVuelta;
	private Checkbox chbxBusqConfirmaconFA;
	private Combobox cmbBusqOrigen;
	private Listbox ltbxBusqDestinos;
	private Vbox vboxCalendarVuelta;
	private Calendar clrBusqIda;
	private Calendar clrBusqIdaVuelta;
	private Button btnBuscar;
	private Button btnBusqCancelar;
	private Tab tabBusqIda;
	private Tab tabBusqVuelta;
	private Label lblBusqTitleSelectViajeIda;
	private Label lblBusqInfoViajeIda;
	private Listbox ltbxBusqIda;
	private Label lblBusqInfoViajeVuelta;
	private Listbox ltbxBusqVuelta;
	
	private Div divPaso_2;
	private Tab tabVtaIdaMapa;
	private Tab tabVtaVueltaMapa;
	private Groupbox gpbxVtaIdaMapa;
	private Caption capVtaIdaMapa;
	private Caption capVtaRetornoMapa;
	private Groupbox gpbxVtaVueltaMapa;
	private Label lblVtaTipoComprobante;
	private Label lblVtaNumeroComprobante;
	private Checkbox chbxVtaIdaVuelta;
	private Checkbox chbxVtaUpdateMapAuto;
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
	private Radio rdVtaConfirmacionFA;
//	private Radio rdVtaPostergacion;
	private Radio rdVtaPostergacionFA;
	private Radio rdVtaCambioNombre;
//	private Grid gridVtaInfoViajeIda;
	private Label lblVtaIdaDestino_fecha;
	private Combobox cmbVtaIdaEmbarque_hora;
	private Combobox cmbVtaIdaDesembarque_hora;
	private Label lblVtaIdaAsiento;
	private Doublebox dbxVtaIdaTarifa;
	private Textbox txtVtaIdaObservaciones;
	private Doublebox dbxVtaIdaRecargo;
	private Column columnVtaInfoViajeIda;
	private Column columnVtaInfoViajeVuelta;
	private Grid gridVtaInfoViajeVuelta;
	private Label lblVtaVueltaDestino_fecha;
	private Combobox cmbVtaVueltaEmbarque_hora;
	private Combobox cmbVtaVueltaDesembarque_hora;
	private Label lblVtaVueltaAsiento;
	private Doublebox dbxVtaVueltaTarifa;
	private Textbox txtVtaVueltaObservaciones;
	private Doublebox dbxVtaVueltaRecargo;
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
	
	private String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPisoHor.png";
	private String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPisoHor.png";
	private int TIPO_ASIENTO = 0;
	private int TIPO_MONITOR = 1;
	private int TIPO_CAFETERIA = 2;
	private Window wndModal = null;
	private Listbox ltbxResultBusqueda = null;
	private Date fechaLiquidacion = null;
	private String prefijoAsiento="";
	private String key = "-1";
	private Map<String, Asiento> mapaAsientosIda = null;
	private Map<String, Asiento> mapaAsientosRetorno = null;	
	private Timer timerRefreshMapa = null;
	private boolean isVtaIdaVuelta = false;
	private boolean isEnabledVenta = false;
	private int minutosEdicionAsiento;
	
	private DetalleItinerario detalleItinerarioIda = null;
	private DetalleItinerario detalleItinerarioVuelta = null;
	private Pasajero pasajero = null;
	private Cliente cliente = null;
	private CanalVenta canalVenta = null;
	private VentaPasaje ventaPasaje_x = null;
	
	
	//Historial compras pasajero
	private Radio rdHpTodos;
	private Radio rdHpViajesVigentes;
	private Radio rdHpReservas;
	private Radio rdHpFechaAbierta;
	private Button btnHpReimprimir;
	private Button btnHpEditar;
	private Button btnHpCancelar;
	private Listbox ltbxHp;
	
	//Controles para la postergacion
	private Combobox cmbPostEmpresa;
	private Combobox cmbPostOrigen;
	private Combobox cmbPostDestino;
	private Datebox dtbxPostFecha;
	private Listbox ltbxPostItienerarios;
	private Groupbox gpbxPostMapa;
	private Listbox ltbxPostAsientoSeleccionado;
	private Label lblPostRuta_fecha;
	private Combobox cmbPostEmbarque_hora;
	private Combobox cmbPostDesembarque_hora;
	private Label lblPostAsiento;
	private Doublebox dbxPostTarifa;
	private Doublebox dbxPostMontoAnterior;
	private Doublebox dbxPostPenalidad;
	private Doublebox dbxPostTotalPagar;
	private Combobox cmbPostTipoFormaPago;
	private Combobox cmbPostOperadorTarjeta;
	private Combobox cmbPostTarjeta;
	private Textbox txtPostNumeroOperacion;
	private Textbox txtPostObservaciones;
	private Hbox hboxPost_1;
	private Hbox hboxPost_2;
	private DetalleItinerario postDetalleItinerario = null;
	private Map<String, Asiento> postMapaAsientos = null;
	List<Localidad> listLocalidades = null;
	private boolean boletoManifestado = false;
	
	//Controles para la Programacion de Bus
	private Combobox cmbPbBus;
	private Combobox cmbPbPiloto;
	private Combobox cmbPbCopiloto;
	private Combobox cmbPbCopilotoAuxiliar;
	private Combobox cmbPbTripulante;
	private ProgramacionServicio programacionServicio;
	private Button btnPbEditar;
	private Button btnPbGuardar;
	private Button btnPbAnular;	
	private Button btnPbCerrar;
	private int TIPO_PERSONAL_PILOTO = 1;
	private int TIPO_PERSONAL_COPILOTO = 2;
	private int TIPO_PERSONAL_TRIPULANTE = 3;
	
	//Controles para el Manifiesto de Pasajeros
	private Button btnManPaxGuardar;
	private Button btnManPaxAnular;
	private Radio rdManPaxPrintLaser;
	private Radio rdManPaxPrintMatricial;		
	private Button btnManPaxPrint;
	private String ROTULO_SUNAT="SUNAT";
	private String ROTULO_TRANSPORTISTA="TRANSPORTISTA";
	private String ROTULO_ARCHIVO="ARCHIVO";
	
	//Controles para la Carpeta de Despacho
	private Combobox cmbCardesPuntoControl;
	
	private Iframe iframe;
//	private Double totalDescuento;
	
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		try {
			super.onCreate();
			
			divPaso_2.setVisible(false);
//			ltbxBusqComprobantesPax.setVisible(false);
			vboxCalendarVuelta.setVisible(false);
			rdBusqIda.setChecked(true);
			
			listLocalidades = ServiceLocator.getLocalidadManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
			
			UtilData.cargarDataCombo(cmbBusqOrigen, Localidad.class, true);
			Util.seleccionarValorItemCombo(Localidad.class, cmbBusqOrigen, getAgencia().getLocalidad().getId());			
			loadBusqLocalidadesDestino(cmbBusqOrigen.getSelectedItem().getValue());
			
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
			loadTipoFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO), cmbVtaTipoFormaPago);
			
			//Autorizadores de descuento
			List<AutorizadorDescuento> listAutoDescuento = ServiceLocator.getAutorizadorDescuentoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, null);
			for(AutorizadorDescuento autorizadorDescuento: listAutoDescuento) {
				Comboitem  comboitem = new Comboitem(autorizadorDescuento.getUsuario().toString());
				comboitem.setValue(autorizadorDescuento);
				cmbVtaAutorizaDescuento.appendChild(comboitem);
			}
			
			lblBusqTitleSelectViajeIda.setValue("SELECCION DEL VIAJE :::: ");
			
			dbxVtaIdaTarifa.setLocale(Locale.US);
			dbxVtaIdaRecargo.setLocale(Locale.US);
			dbxVtaVueltaTarifa.setLocale(Locale.US);
			dbxVtaVueltaRecargo.setLocale(Locale.US);
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
//			ltbxBusqComprobantesPax = (Listbox)this.getFellow("ltbxBusqComprobantesPax");
			txtBusqBy = (Textbox)this.getFellow("txtBusqBy");
			btnBusqBy = (Button)this.getFellow("btnBusqBy");
			rdBusqIda = (Radio)this.getFellow("rdBusqIda");
			rdBusqIdaVuelta = (Radio)this.getFellow("rdBusqIdaVuelta");
			chbxBusqConfirmaconFA = (Checkbox)this.getFellow("chbxBusqConfirmaconFA");
			cmbBusqOrigen = (Combobox)this.getFellow("cmbBusqOrigen");
			ltbxBusqDestinos = (Listbox)this.getFellow("ltbxBusqDestinos");
			vboxCalendarVuelta = (Vbox)this.getFellow("vboxCalendarVuelta");
			clrBusqIda = (Calendar)this.getFellow("clrBusqIda");
			clrBusqIdaVuelta = (Calendar)this.getFellow("clrBusqIdaVuelta");
			btnBuscar = (Button)this.getFellow("btnBuscar");
			btnBusqCancelar = (Button)this.getFellow("btnBusqCancelar");
			tabBusqIda = (Tab)this.getFellow("tabBusqIda");
			tabBusqVuelta = (Tab)this.getFellow("tabBusqVuelta");
			lblBusqTitleSelectViajeIda = (Label)this.getFellow("lblBusqTitleSelectViajeIda");
			lblBusqInfoViajeIda = (Label)this.getFellow("lblBusqInfoViajeIda");
			ltbxBusqIda = (Listbox)this.getFellow("ltbxBusqIda");
			lblBusqInfoViajeVuelta = (Label)this.getFellow("lblBusqInfoViajeVuelta");
			ltbxBusqVuelta = (Listbox)this.getFellow("ltbxBusqVuelta");			
			divPaso_2 = (Div)this.getFellow("divPaso_2");
			tabVtaIdaMapa = (Tab)this.getFellow("tabVtaIdaMapa");
			tabVtaVueltaMapa =  (Tab)this.getFellow("tabVtaVueltaMapa");
			gpbxVtaIdaMapa = (Groupbox)this.getFellow("gpbxVtaIdaMapa");
			capVtaIdaMapa = (Caption)this.getFellow("capVtaIdaMapa");
			capVtaRetornoMapa = (Caption)this.getFellow("capVtaRetornoMapa");
			gpbxVtaVueltaMapa = (Groupbox)this.getFellow("gpbxVtaVueltaMapa");
			lblVtaTipoComprobante = (Label)this.getFellow("lblVtaTipoComprobante");
			lblVtaNumeroComprobante = (Label)this.getFellow("lblVtaNumeroComprobante");
			chbxVtaIdaVuelta = (Checkbox)this.getFellow("chbxVtaIdaVuelta");
			chbxVtaUpdateMapAuto = (Checkbox)this.getFellow("chbxVtaUpdateMapAuto");
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
			rdVtaConfirmacionFA = (Radio)this.getFellow("rdVtaConfirmacionFA");
//			rdVtaPostergacion = (Radio)this.getFellow("rdVtaPostergacion");
			rdVtaPostergacionFA = (Radio)this.getFellow("rdVtaPostergacionFA");
			rdVtaCambioNombre = (Radio)this.getFellow("rdVtaCambioNombre");
//			gridVtaInfoViajeIda = (Grid)this.getFellow("gridVtaInfoViajeIda");
			lblVtaIdaDestino_fecha = (Label)this.getFellow("lblVtaIdaDestino_fecha");
			cmbVtaIdaEmbarque_hora = (Combobox)this.getFellow("cmbVtaIdaEmbarque_hora");
			cmbVtaIdaDesembarque_hora = (Combobox)this.getFellow("cmbVtaIdaDesembarque_hora");
			lblVtaIdaAsiento = (Label)this.getFellow("lblVtaIdaAsiento");
			dbxVtaIdaTarifa = (Doublebox)this.getFellow("dbxVtaIdaTarifa");
			txtVtaIdaObservaciones = (Textbox)this.getFellow("txtVtaIdaObservaciones");
			dbxVtaIdaRecargo = (Doublebox)this.getFellow("dbxVtaIdaRecargo");
			columnVtaInfoViajeIda = (Column)this.getFellow("columnVtaInfoViajeIda");
			columnVtaInfoViajeVuelta = (Column)this.getFellow("columnVtaInfoViajeVuelta");
			gridVtaInfoViajeVuelta = (Grid)this.getFellow("gridVtaInfoViajeVuelta");
			lblVtaVueltaDestino_fecha = (Label)this.getFellow("lblVtaVueltaDestino_fecha");
			cmbVtaVueltaEmbarque_hora = (Combobox)this.getFellow("cmbVtaVueltaEmbarque_hora");
			cmbVtaVueltaDesembarque_hora = (Combobox)this.getFellow("cmbVtaVueltaDesembarque_hora");
			lblVtaVueltaAsiento = (Label)this.getFellow("lblVtaVueltaAsiento");
			dbxVtaVueltaTarifa = (Doublebox)this.getFellow("dbxVtaVueltaTarifa");
			txtVtaVueltaObservaciones = (Textbox)this.getFellow("txtVtaVueltaObservaciones");
			dbxVtaVueltaRecargo = (Doublebox)this.getFellow("dbxVtaVueltaRecargo");
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
			txtBusqBy.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onOk_txtBusqBy();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnBusqBy.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onOk_txtBusqBy();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			chbxBusqConfirmaconFA.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					onCheck_chbxBusqConfirmaconFA();
				}
			});
			cmbBusqOrigen.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onChange_cmbBusqOrigen();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
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
			
			
			chbxVtaUpdateMapAuto.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_chbxVtaUpdateMapAuto();
						
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
					try {
						
						onChange_cmbVtaVueltaEmbarque_hora();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaIdaRecargo.addEventListener(Events.ON_CHANGING, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						onChanging_dbxVtaIdaRecargo(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaIdaRecargo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						onChanging_dbxVtaIdaRecargo(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaIdaRecargo.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						focusCursorByTipoOperacion(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaVueltaRecargo.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						onChanging_dbxVtaVueltaRecargo(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaVueltaRecargo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						onChanging_dbxVtaVueltaRecargo(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			dbxVtaVueltaRecargo.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						focusCursorByTipoOperacion(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			cmbVtaIdaDesembarque_hora.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					
					focusCursorByTipoOperacion(event);
				}
			});
			txtVtaIdaObservaciones.addEventListener(Events.ON_OK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						focusCursorByTipoOperacion(event);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}					
				}
			});
			txtVtaVueltaObservaciones.addEventListener(Events.ON_OK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					try {
						
						focusCursorByTipoOperacion(event);
						
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
			rdVtaPostergacionFA.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_rdVtaPostergacionFA(event);						
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			rdVtaCambioNombre.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onCheck_rdVtaCambioNombre(event);						
						
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
					
						onSelect_ltbxVtaAsientosSeleccionados(event);						
						
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
//						if(((Textbox)event.getTarget()).isReadonly()==false)
//							buscarCliente(((Textbox)event.getTarget()).getText().trim().toUpperCase());
						txtVtaFacRazonSocial.setFocus(true);
						
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
						if(((Textbox)event.getTarget()).isReadonly()==false)
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
			txtVtaAutorizaDescuentoCodigo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
							onBlur_txtVtaAutorizaDescuentoCodigo();						
						
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
			btnVtaPostergar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaPostergar();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaProgramarServicios.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaProgramarServicios();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaManifiestoPasajetos.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaManifiestoPasajeros();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnVtaCarpetaDespacho.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						onClick_btnVtaCarpetaDespacho();;
						
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
					try {
						onClick_btnVtaGuargar();;
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			chbxVtaIdaVuelta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					ononCheck_chbxVtaIdaVuelta(event);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
		}
	}
	
	/**
	 * Carga las localidades destino
	 * @throws Exception
	 */
	private void loadBusqLocalidadesDestino(Localidad localidadOrigen)throws Exception{
		try {
			Util.limpiarListbox(ltbxBusqDestinos);
			//Carga localidades destino
			Listitem item = new Listitem();
			item.appendChild(new Listcell("--TODOS--"));
			ltbxBusqDestinos.appendChild(item);
			for(Localidad localidadDestino: listLocalidades) {
				if(localidadOrigen==null || localidadDestino.getId()!=localidadOrigen.getId()) {
					item = new Listitem();
					Listcell cell = new Listcell(localidadDestino.getDenominacion());
					item.appendChild(cell);
					item.setValue(localidadDestino);
					ltbxBusqDestinos.appendChild(item);
					if(localidadOrigen!=null && localidadOrigen.getId()!=Constantes.ID_LOC_LIMA && localidadDestino.getId()==Constantes.ID_LOC_LIMA)
						ltbxBusqDestinos.selectItem(item);
				}
			}
			if(ltbxBusqDestinos.getSelectedIndex() <0 )
				ltbxBusqDestinos.setSelectedIndex(0);
			
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
	private void onOk_txtBusqBy()throws Exception{
		if(!txtBusqBy.getText().trim().isEmpty()) {
			String datoBusq = txtBusqBy.getText().trim().toUpperCase();
//			int ix = datoBusq.indexOf("B");
			boolean isComprobante = ((datoBusq.indexOf("B")==0 || datoBusq.indexOf("F")==0) && datoBusq.indexOf("-")==4);
			Long pasajeroId = null;
			String numeroComprobante = null;
			if(isComprobante)
				numeroComprobante = datoBusq;
			else {
				TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("numeroDocumento", datoBusq);
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);				
				List<Pasajero> resultPax = ServiceLocator.getPasajeroManager().buscarPorX(criteriosBusqueda, null);
				if(resultPax.size()>0)
					pasajeroId = resultPax.get(0).getId();
				else {					
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.busqVtaPax.noExistDoctPax"), txtBusqBy);
					return;
				}					
			}
			
			List<VentaPasaje> resultVentasPax = ServiceLocator.getVentaPasajesManager().buscarVentasByPasajero(pasajeroId, numeroComprobante, true);
			if(resultVentasPax.size()==0) {
				DlgMessage.information(Messages.getString("WndVouchers.information.noRegistros"), txtBusqBy);				
				return;
			}
			
			minutosEdicionAsiento = UtilFlag.getTiempoPostergacion();
			wndModal = createWindowHistoriaPasajero(resultVentasPax, minutosEdicionAsiento);
			wndVentaReservaNew.appendChild(wndModal);
			wndModal.setMode("modal");	
			//Selecciona el primer item de la lista
			if(ltbxHp.getItemCount()>0) {
				ltbxHp.setSelectedIndex(0);
				ltbxHp.setFocus(true);
				habilitarOperacionHp();
			}else
				btnHpCancelar.setFocus(true);		
		}
	}
	
	/**
	 * crea una venta para mostrar las ventas del Pasajero.
	 * @param listVentasPax: Lista de ventas
	 * @return
	 * @throws Exception
	 */
	private Window createWindowHistoriaPasajero(List<VentaPasaje> listVentasPax, int minutosEdicionAsiento)throws Exception{		
		try {
			Window win = new Window("HISTORIAL COMPRAS PASAJERO", "normal", true);
			win.setWidth("920px");
						
			Pasajero pasajero = listVentasPax.get(0).getPasajero();			
			
			Groupbox groupbox = new Groupbox();
			groupbox.setMold("3d");
			groupbox.setClosable(false);
			groupbox.appendChild(new Caption("DATOS DEL PASAJERO"));
			Grid grid = new Grid();
			grid.setStyle("border:none");
			Columns columns = new Columns();
			Column column = new Column(null, null, "110px");
			column.setAlign("right");
			columns.appendChild(column);
			columns.appendChild(new Column());
			grid.appendChild(columns);
			Rows rows =new Rows();
			Row row = new Row();
			row.appendChild(new Label("NRO. DOCUMENTO : "));
			Label label = new Label(pasajero.getNumeroDocumento());
			label.setSclass("label-size11-bold");
			label.setStyle("font-size:13px !important");
			row.appendChild(label);
			rows.appendChild(row);
			row = new Row();
			row.appendChild(new Label("NOMBRES : "));
			label = new Label(pasajero.toString());
			label.setSclass("label-size11-bold");
			label.setStyle("font-size:13px !important");
			row.appendChild(label);
			rows.appendChild(row);
			row = new Row();
			row.appendChild(new Separator());
			Hbox hbox = new Hbox();
			hbox.setAlign("center");
			Radiogroup radiogroup = new Radiogroup();
			radiogroup.setId("rdg");
			rdHpTodos = new Radio("TODOS");
			rdHpTodos.setStyle("margin-right:20px;color:blue");
			radiogroup.appendChild(rdHpTodos);
			rdHpViajesVigentes = new Radio("VIGENTES");
			rdHpViajesVigentes.setStyle("margin-right:20px;color:blue");
			rdHpViajesVigentes.setChecked(true);
			radiogroup.appendChild(rdHpViajesVigentes);
			rdHpReservas = new Radio("RESERVAS");
			rdHpReservas.setStyle("margin-right:20px;color:blue");
			radiogroup.appendChild(rdHpReservas);
			rdHpFechaAbierta = new Radio("FECHA ABIERTA");
			rdHpFechaAbierta.setStyle("margin-right:20px;color:blue");
			radiogroup.appendChild(rdHpFechaAbierta);
			
			hbox.appendChild(radiogroup);
			row.appendChild(hbox);
			rows.appendChild(row);
			grid.appendChild(rows);			
			groupbox.appendChild(grid);			
			win.appendChild(groupbox);
			
			ltbxHp = new Listbox();
			ltbxHp.setHeight("300px");
			Listhead listhead = new Listhead();		
			Listheader listheader=null;
			listheader = new Listheader("EMPRESA", null, "120px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("TIPO MOVIMIENTO", null, "110px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.VIAJE", null, "70px");
			listheader.setAlign("center");
			listheader.setTooltiptext("Fecha de Viaje");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("H.VIAJE", null, "60px");
			listheader.setAlign("center");
			listheader.setTooltiptext("Hora de Viaje");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("SERVICIO", null, "120px");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listhead.appendChild(new Listheader("RUTA", null, "120px"));
			listheader = new Listheader("ASIENTO", null, "50px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("COMPROBANTE", null, "100px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("IMPORTE", null, "100px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.CADUCIDAD", null, "130px");
			listheader.setAlign("center");			
			listheader.setTooltiptext("Fecha/hora de caducidad");
			listheader.setSort("auto");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.EMISION", null, "70px");
			listheader.setAlign("center");
			listheader.setSort("auto");
			listheader.setTooltiptext("Fecha Emisión");
			listhead.appendChild(listheader);
			listheader = new Listheader("USUARIO", null, "100px");
			listheader.setSort("auto");
			listheader.setTooltiptext("Usuario Emisión");
			listhead.appendChild(listheader);			
			listhead.appendChild(new Listheader());			
			ltbxHp.appendChild(listhead);
			win.appendChild(ltbxHp);
			
			grid = new Grid();
			columns = new Columns();
			column = new Column();
			column.setAlign("right");
			columns.appendChild(column);
			grid.appendChild(columns);
			rows = new Rows();
			row = new Row();
			hbox = new Hbox();
			hbox.setAlign("center");
			btnHpReimprimir = new Button();
			btnHpReimprimir.setId("btnHpReimprimir");
			btnHpReimprimir.setDisabled(true);
			btnHpReimprimir.setLabel("Reimprimir");
			btnHpReimprimir.setImage("resources/buttons/mp_print-termico.png");
			btnHpReimprimir.setStyle("margin-right:525px");
			btnHpReimprimir.setAutodisable("self");
			hbox.appendChild(btnHpReimprimir);
			
			btnHpEditar = new Button();
			btnHpEditar.setId("btnHpEditar");
			btnHpEditar.setLabel("Editar");			
			btnHpEditar.setSclass("btnCommandL");
			btnHpEditar.setAutodisable("self");
			disabled_btnModificar(btnHpEditar, true);
			hbox.appendChild(btnHpEditar);
			
			btnHpCancelar = new Button();
			btnHpCancelar.setLabel("Cancelar");
			btnHpCancelar.setSclass("btnCommandL");
			btnHpCancelar.setImage("resources/mp_cerrar.png");
			btnHpCancelar.setStyle("margin-left:15px");
		    btnHpCancelar.setAutodisable("self");
			hbox.appendChild(btnHpCancelar);			
			
			row.appendChild(hbox);
			rows.appendChild(row);
			grid.appendChild(rows);
			win.appendChild(grid);
			
			buscarTipoVentaHpInListbox(listVentasPax);
			
			ltbxHp.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						habilitarOperacionHp();
						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			ltbxHp.addEventListener(Events.ON_OK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					btnHpEditar.setFocus(btnHpEditar.isVisible());
//					habilitarOperacionHp();
				}
			});
			ltbxHp.addEventListener(Events.ON_CANCEL, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					win.onClose();
				}
			});
			rdHpTodos.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					buscarTipoVentaHpInListbox(listVentasPax);
				}
			});
			rdHpViajesVigentes.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					buscarTipoVentaHpInListbox(listVentasPax);
				}
			});
			rdHpReservas.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					buscarTipoVentaHpInListbox(listVentasPax);
				}
			});
			rdHpFechaAbierta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					buscarTipoVentaHpInListbox(listVentasPax);
				}
			});
			btnHpReimprimir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						
						VentaPasaje _ventaPasaje =ltbxHp.getSelectedItem().getValue();
						
						List<VentaPasaje> listVentasReimpresion = new ArrayList<VentaPasaje>();
						listVentasReimpresion.add(_ventaPasaje);
						
						Messagebox.show(Messages.getString("WndVentaReservaNew.question.reimpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
							@Override
							public void onEvent(Event e){
								try{
									if(e.getName().equals("onYes")){
										
										WSFE.reimprimirComprobante(listVentasReimpresion, wndVentaReservaNew, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
										
										/************** Tracking (GPS) **********************/
//										listVentasReimpresion.forEach(vta -> {
//											try {	
//												VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(vta.getId());
//												String motivoMovimiento = "REIMPRESION";										
//												MovimientoPasajes movimientoPasajes = UtilData.createTracking(ventaPasaje, motivoMovimiento);
//												ServiceLocator.getMovimientoPasajesManager().guardar(movimientoPasajes);
//											} catch (Exception e1) {
//												e1.printStackTrace();
//												log.error(e1);
//												DlgMessage.error(e1.getMessage());
//											}								
//										});
										/****************************************************/
									}
								}catch(Exception ex) {
									ex.printStackTrace();
									log.error(ex);
									DlgMessage.error(ex.getMessage());
								}
							}
						});
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnHpEditar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						String valorBusqHp = txtBusqBy.getText().trim().toUpperCase(); //Backu del valor ingresado para la busqueda
						cleanBusquedaSeleccionHp();
						if(ltbxHp.getSelectedCount()>0) {							
							if(isVtaIdaVuelta) {
								rdBusqIda.setChecked(true);
								onCheck_rdBusqIda();	
							}			
							txtBusqBy.setText(valorBusqHp); //Recupera el valor
							rdBusqIdaVuelta.setDisabled(true);
							ventaPasaje_x = ltbxHp.getSelectedItem().getValue();
							String messageQuestionUser = "";
							int tipoMovimientoId = ventaPasaje_x.getTipoMovimiento().getId();
							boolean isConfirmaFA = (tipoMovimientoId==Constantes.ID_TIPMOV_POSTERGACION_FA || tipoMovimientoId==Constantes.ID_TIPMOV_FECHA_ABIERTA);
							boolean isConfirmaReserva = (!isConfirmaFA && ventaPasaje_x.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA));
							if(isConfirmaFA)
								messageQuestionUser = Messages.getString("WndVentaReservaNew.question.HistoricoPasajero.confirmacionFA");
							else if(isConfirmaReserva)
								messageQuestionUser = Messages.getString("WndVentaReservaNew.question.HistoricoPasajero.confirmacionReserva");
							else
								messageQuestionUser = Messages.getString("WndVentaReservaNew.question.HistoricoPasajero.edicionVenta");
							
							Messagebox.show(messageQuestionUser, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
								@Override
								public void onEvent(Event e){
									try {
										if(e.getName().equals("onYes")) {
											if(isConfirmaFA) {
												chbxBusqConfirmaconFA.setChecked(true);
												chbxBusqConfirmaconFA.setDisabled(false);
												chbxBusqConfirmaconFA.setStyle("color:red;font-weight: bold");
												if(ltbxBusqIda.getItemCount()>0) {
													ltbxBusqIda.setSelectedIndex(0);
													ltbxBusqIda.setFocus(true);
												}else if(ltbxBusqDestinos.getSelectedCount()==0) {
													ltbxBusqDestinos.setSelectedIndex(0);
													ltbxBusqDestinos.setFocus(true);	
												}else
													btnBusqBy.setFocus(true);
											}else
												onClick_btnSeleccionItinerario(event);
											
											win.onClose();
										}
									} catch (Exception e2) {
										e2.printStackTrace();
										DlgMessage.error(e2.getMessage());
									}								
								}								
							});
						}						
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			btnHpCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					win.onClose();
				}
			});
			
			return win;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
			return null;
		}			
	}
	
	/**
	 * Valida el estado de la compra del pasajero
	 * @param ventaPasaje
	 * @param btnEditar
	 * @param minutosEdicionAsiento
	 * @throws Exception
	 */
	private void habilitarBtnHpEditar(VentaPasaje ventaPasaje, Button btnEditar, int minutosEdicionAsiento)throws Exception{
		disabled_btnModificar(btnEditar, true);
		if(ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
			btnEditar.setLabel("Confirmar");
			disabled_btnModificar(btnEditar, false);
		}else {
			int tipoMovimientoId = ventaPasaje.getTipoMovimiento().getId();
			if(tipoMovimientoId==Constantes.ID_TIPMOV_FECHA_ABIERTA || tipoMovimientoId==Constantes.ID_TIPMOV_POSTERGACION_FA) {				
				disabled_btnModificar(btnEditar, false);
				btnEditar.setLabel("Confirmar");
			}else {
				btnEditar.setLabel("Editar");
				if(validarEdicionAsiento(ventaPasaje, minutosEdicionAsiento))				
					disabled_btnModificar(btnEditar, false);
			}
		}	
	}
	
	/**
	 * Habilita las operaciones permitidas en la compra del pasajero (Historial Pasajero)
	 * @throws Exception
	 */
	private void habilitarOperacionHp()throws Exception{		
		VentaPasaje _ventaPasaje = ltbxHp.getSelectedItem().getValue();
		habilitarBtnHpEditar(_ventaPasaje, btnHpEditar, minutosEdicionAsiento);
		habilitarBtnReimpresion(_ventaPasaje, btnHpReimprimir);		
	}
	
	/**
	 * Busca el tipo de venta del pasajero, en funcion de los resgitros contenidos en el listbox
	 * @throws Exception
	 */
	private void buscarTipoVentaHpInListbox(List<VentaPasaje> listVentasPax)throws Exception{
		Util.limpiarListbox(ltbxHp);
		disabled_btnModificar(btnHpEditar, true);
		btnHpReimprimir.setDisabled(true);
		
		final Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
		
		if(rdHpTodos.isChecked())
			addItemListHp(listVentasPax);
		else if(rdHpViajesVigentes.isChecked()) {
			//Considera las Fechas Abiertas, Reservas y los con fecha de partida mayor o igual a la fecha actual
			List<VentaPasaje> result = listVentasPax.stream()
					.filter(vta -> ((vta.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_FECHA_ABIERTA || vta.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_POSTERGACION_FA) 
							      || (vta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA) && vta.getFechaExpiracionReserva().getTime()>=fechaActual.getTime() )
							      || (vta.getFechaPartida()!=null && vta.getFechaPartida().getTime()>=fechaActual.getTime())))
					.collect(Collectors.toList());
			
			addItemListHp(result);
		}else if(rdHpReservas.isChecked()) {
			List<VentaPasaje> result = listVentasPax.stream()
					.filter(vta -> (vta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA) && vta.getFechaExpiracionReserva().getTime()>=fechaActual.getTime()))
					.collect(Collectors.toList());
			
			addItemListHp(result);			
		}else if(rdHpFechaAbierta.isChecked()) {
			List<VentaPasaje> result = listVentasPax.stream()
					.filter(vta -> (vta.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_FECHA_ABIERTA || vta.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_POSTERGACION_FA))
					.collect(Collectors.toList());
			
			addItemListHp(result);
			
		}
		
		
		if(ltbxHp.getItemCount()>0) {
			ltbxHp.setSelectedIndex(0);
			habilitarOperacionHp();
		}
		
	}
	
	/**
	 * 
	 * @param listVentas
	 * @throws Exception
	 */
	private void addItemListHp(List<VentaPasaje> listVentasPax)throws Exception{
		
		for(VentaPasaje ventaPasaje: listVentasPax) {
			Listitem item = new Listitem();
			Listcell cell = new Listcell(ventaPasaje.getEmpresa().getNombreCorto());
			cell.setStyle("text-align:left");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getTipoMovimiento().getDenominacion());
			cell.setStyle("text-align:left");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getFechaPartida()!=null? Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"-");
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getHoraPartida()!=null? ventaPasaje.getHoraPartida():"-");
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getServicio().getDenominacion());
			cell.setStyle("text-align:left");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getRuta().toString());
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento().toString():"-");
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getNumeroBoleto()!=null?ventaPasaje.getNumeroBoleto():"");
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);	
			cell = new Listcell(Util.toNumberFormat(ventaPasaje.getImportePagado(), 2));
			cell.setStyle("font-size:12px !important;text-align:right");
			item.appendChild(cell);
			cell = new Listcell("-");
			if(ventaPasaje.getFechaExpiracionReserva()!=null)
				cell.setLabel(Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaExpiracionReserva()));
			else if(ventaPasaje.getFechaCaducidad()!=null)
				cell.setLabel(Constantes.FORMAT_DATE_TIME_24H.format(ventaPasaje.getFechaCaducidad()));				
			cell.setStyle("font-size:12px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getFechaLiquidacion()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaLiquidacion()):"-");
			cell.setStyle("font-size:12px !important;text-align:center");
			item.appendChild(cell);			
			cell = new Listcell(ventaPasaje.getUsuario().getLogin());
			item.appendChild(cell);
			
			item.setValue(ventaPasaje);
			ltbxHp.appendChild(item);
		}	
		
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_cmbBusqOrigen()throws Exception{
		
		loadBusqLocalidadesDestino(cmbBusqOrigen.getSelectedItem().getValue());
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdBusqIda()throws Exception{		
		isVtaIdaVuelta = false;
		lblBusqTitleSelectViajeIda.setValue("SELECCION DEL VIAJE :::: ");
		enabledControlsVueltaBusqItienrario();
		cleanBusquedaItienrarios();
		txtBusqBy.setDisabled(false);
		btnBusqBy.setDisabled(false);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_rdBusqIdaVuelta()throws Exception{		
		isVtaIdaVuelta = true;
		lblBusqTitleSelectViajeIda.setValue("SELECCION DEL VIAJE DE IDA :::: ");
		cleanBusquedaItienrarios();
		enabledControlsVueltaBusqItienrario();
		txtBusqBy.setDisabled(false);
		btnBusqBy.setDisabled(false);
	}
	
	private void onCheck_chbxBusqConfirmaconFA()throws Exception{
		
		cleanBusquedaSeleccionHp();
	}
	
	/**
	 * Limpia de la seleccion del pasaje desde el Historico de compras del pasajero
	 * @throws Exception
	 */
	private void cleanBusquedaSeleccionHp()throws Exception{
		ventaPasaje_x = null;
		txtBusqBy.setText("");
		chbxBusqConfirmaconFA.setChecked(false);
		chbxBusqConfirmaconFA.setDisabled(true);
		chbxBusqConfirmaconFA.setStyle(null);
		rdBusqIdaVuelta.setDisabled(false);
		rdVtaConfirmacionFA.setChecked(false);
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
		rdBusqIdaVuelta.setDisabled(false);
		rdBusqIda.setChecked(true);
		onCheck_rdBusqIda();
		cleanBusquedaSeleccionHp();
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChange_clBusqIda(Event event)throws Exception{
		isEnabledVenta = true;
		rdBusqIdaVuelta.setDisabled(false);
		Date fechaActual = Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
		if(clrBusqIda.getValue().getTime() < fechaActual.getTime()) {			
			rdBusqIdaVuelta.setDisabled(true);
			rdBusqIda.setChecked(true);
			onCheck_rdBusqIda();
			isEnabledVenta = false;
//			clrBusqIda.setValue(fechaActual);
//			DlgMessage.information(Messages.getString("WndVentaReserva.information.FechaViajeNoValida"));
//			return;
		}
		
		if(isVtaIdaVuelta) {
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
		if(isVtaIdaVuelta) {
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
		
		if(isVtaIdaVuelta &&  !(cmbBusqOrigen.getSelectedItem().getValue() instanceof Localidad)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"));
			return;
		}else if(isVtaIdaVuelta && (ltbxBusqDestinos.getSelectedCount()==0 || ltbxBusqDestinos.getSelectedItem().getValue()==null)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"));
			return;
		}
		Localidad localidadOrigen = (cmbBusqOrigen.getSelectedIndex()>0? cmbBusqOrigen.getSelectedItem().getValue():null);		
		
		String empresa = "";
		String origen = (localidadOrigen!=null?localidadOrigen.getDenominacion():"");
		String destino = (ltbxBusqDestinos.getSelectedCount()>0 && ltbxBusqDestinos.getSelectedItem().getValue()!=null?((Localidad)ltbxBusqDestinos.getSelectedItem().getValue()).getDenominacion():"");
		String fechaIda = Constantes.FORMAT_DATE.format(clrBusqIda.getValue());
		String fechaVuelta = (isVtaIdaVuelta? Constantes.FORMAT_DATE.format(clrBusqIdaVuelta.getValue()): null);
		
		//Busca itinerarios para la Ida
		List<DetalleItinerario> lstItinerariosIda = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaIda, origen, destino, empresa);
		
		//Filtra los puntos de embarque que corresponden a la localidad Origen seleccionada.
		if(localidadOrigen!=null)
			lstItinerariosIda = lstItinerariosIda.stream()
					.filter(_detalleItinerario -> _detalleItinerario.getPuntoEmbarque().getAgencia().getLocalidad().getId().intValue()==localidadOrigen.getId().intValue())
					.collect(Collectors.toList());
		
		//Busca itinerarios para la Vuelta
		List<DetalleItinerario> lstItinerariosVuelta =  new ArrayList<DetalleItinerario>();
		if(isVtaIdaVuelta) {			
			lstItinerariosVuelta = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaVuelta, destino, origen, empresa);
			Localidad localidadOrigenVuelta = ltbxBusqDestinos.getSelectedItem().getValue();
			
			//Filtra los puntos de embarque que corresponden a la localidad Origen seleccionada.
			lstItinerariosVuelta = lstItinerariosVuelta.stream()
					.filter(_detalleItinerario -> _detalleItinerario.getPuntoEmbarque().getAgencia().getLocalidad().getId().intValue()==localidadOrigenVuelta.getId().intValue())
					.collect(Collectors.toList());
		}
		
		//Carga itinerarios de Ida
		loadItienrariosBusqueda(lstItinerariosIda, true, fechaIda);
		
		//Carga itinerarios de para la vuelta
		if(isVtaIdaVuelta)
			loadItienrariosBusqueda(lstItinerariosVuelta, false, fechaVuelta);
		
		// Valida la venta del itienerario
		isEnabledVenta = true;		
		Date fechaActual = Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
		if(clrBusqIda.getValue().getTime() < fechaActual.getTime()) {			
			isEnabledVenta = false;
		}
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
		
		boolean enabledCtrlsVuelta = isVtaIdaVuelta;
		
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
			cell = new Listcell(detalleItinerario.getPuntoEmbarque().getAgencia().getNombreCorto());
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getPuntoEmbarque().getHoraPartida());
			cell.setStyle("font-size:11px !important");
			cell.setTooltiptext("Hora de Partida");
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
			item.appendChild(cell);			
			/*Tarifa Estandar*/
			List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1,
					detalleItinerario.getItinerario().getServicio().getId(),
					detalleItinerario.getRuta().getId(), fechaViaje,
					detalleItinerario.getHoraPartida(), null, null, detalleItinerario.getItinerario().getEmpresa().getId());
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
			
			///****************PROGRAMACION BUS
			boolean isBusProgramado = (detalleItinerario.getItinerario().getBus()!=null);
			A linkBus=new A();
			linkBus.setAttribute(DetalleItinerario.class.getName(), detalleItinerario);
			habilitarLinkProgramacionBus(linkBus, detalleItinerario);
			String datosBusProgramado = "";
			if(isBusProgramado) {
				String codigoBus = (detalleItinerario.getItinerario().getBus().getCodigo()!=null?detalleItinerario.getItinerario().getBus().getCodigo()+" ":null);
				String placa = detalleItinerario.getItinerario().getBus().getNumeroPlaca();
				
				datosBusProgramado = (codigoBus!=null?codigoBus+"("+placa+")":placa);				
				linkBus.setTooltiptext("Visualizar Programación");
				linkBus.setStyle("color: #0065CC;text-align:center;font-size: 11px;");
			}else {
				datosBusProgramado = "Programar";
				linkBus.setStyle("color: #FF0000;text-align:center;font-size: 11px;text-transform:none");
			}
			linkBus.setLabel(datosBusProgramado);
			
			cell = new Listcell();			
			if(!linkBus.isDisabled())
				cell.appendChild(linkBus);
			else if(isBusProgramado)
				cell.setLabel(datosBusProgramado);
			else
				cell.setLabel("-");			
			item.appendChild(cell);
			item.setValue(detalleItinerario);
			
			cell = new Listcell();
			Button btnSeleccionar = new Button("Seleccionar");
			btnSeleccionar.setAutodisable("self");
			btnSeleccionar.setAttribute(Listitem.class.getName(), item);
			btnSeleccionar.setHeight("20px");
			btnSeleccionar.setAutag("self");
			cell.appendChild(btnSeleccionar);
			item.appendChild(cell);
			
			linkBus.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					DetalleItinerario _detalleItinerario = (DetalleItinerario) event.getTarget().getAttribute(DetalleItinerario.class.getName());
					showWindowProgramacionBus(_detalleItinerario);
				}
			});
			
			btnSeleccionar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub					
					try {						
						
						onClick_btnSeleccionItinerario(event);
						
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
						
						onClick_btnSeleccionItinerario(event);
						
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
		Map<String, Asiento> mapaAsientos = new HashMap<>();

		/*	Recorremos la cantidad de pisos del servicio	*/
		for(int i = 0; i < nPisos; i++){
			/*	Si cambiamos al siguiente piso redefinimos los valores de las variables	*/
			if(i==1) {
				nFilas = servicio.getNumeroFilasPiso2();
				nColumnas = servicio.getNumeroColumnasPiso2();
			}
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
								/*	Verificamos que el objeto sea del tipo Asiento	*/
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									asiento.setKey();
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
	private void onClick_btnSeleccionItinerario(Event event)throws Exception{
		cleanDatosVenta();
		enabledControlsVueltaVenta();
		
		Listitem item = null;
		if(event.getTarget() instanceof Listitem)
			item = (Listitem)event.getTarget();
		else if(event.getTarget() instanceof Button && !((Button)event.getTarget()).getId().equals("btnHpEditar"))
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
			int index = detItinerario.getHoraPartida().toString().indexOf(":");
			String sHora = detItinerario.getHoraPartida().substring(0,index);
			String sMinuto = detItinerario.getHoraPartida().substring(index+1,detItinerario.getHoraPartida().length());
			//Fecha hora de partida
			String fecha = Constantes.FORMAT_DATE.format(detItinerario.getFechaPartida());
			Date dFecHoraPart = Constantes.FORMAT_DATE.parse(fecha);
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
			if(isEnabledVenta && dFecHoraPart.getTime() < dFechHorActual.getTime()){
				Messagebox.show(Messages.getString("WndVentaReserva.information.noHoraMenorServicio"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						try {
							if(e.getName().equals("onYes"))
								onClick_btnSeleccionItinerarioConfirm(event);
							
						} catch (Exception e2) {
							e2.printStackTrace();
							DlgMessage.error(e2.getMessage());
						}
						
					}
					
				});
			}else
				onClick_btnSeleccionItinerarioConfirm(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void onClick_btnSeleccionItinerarioConfirm(Event event)throws Exception{		
		detalleItinerarioIda = ltbxBusqIda.getSelectedItem().getValue();
		createEstructura(detalleItinerarioIda.getItinerario().getServicio().getId(), gpbxVtaIdaMapa, false, detalleItinerarioIda, mapaAsientosIda, null, false);
		
//		/*	Si se trata de un ida y vuelta	*/
//		if(isVtaIdaVuelta){
//			detalleItinerarioVuelta = ltbxBusqVuelta.getSelectedItem().getValue();
//			crearEstructura(detalleItinerarioVuelta.getItinerario().getServicio().getId(), gpbxVtaVueltaMapa, true, detalleItinerarioVuelta, mapaAsientosRetorno, null, false);
//		}
		
		lblVtaIdaDestino_fecha.setValue(detalleItinerarioIda.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()));
		loadPuntoEmbarque(detalleItinerarioIda, cmbVtaIdaEmbarque_hora);
		loadPuntoDesembarque(detalleItinerarioIda, cmbVtaIdaDesembarque_hora);
		seleccionarPuntoEmbarque(cmbVtaIdaEmbarque_hora, detalleItinerarioIda.getPuntoEmbarque().getAgencia());
		if(cmbVtaIdaEmbarque_hora.getSelectedIndex() <0 )
			cmbVtaIdaEmbarque_hora.setSelectedIndex(0);
			
		if(cmbVtaIdaDesembarque_hora.getItemCount()==2)
			cmbVtaIdaDesembarque_hora.setSelectedIndex(1);
		lblVtaIdaAsiento.setValue("-");
		dbxVtaIdaTarifa.setValue(.00);
		dbxVtaIdaRecargo.setValue(.00);
		columnVtaInfoViajeIda.setLabel("INFORMACION DEL VIAJE ::: ");
		columnVtaInfoViajeIda.getChildren().clear();
		Label lblVtainfoViajeEmpresaIda = new Label(detalleItinerarioIda.getItinerario().getEmpresa().getNombreCorto());
		lblVtainfoViajeEmpresaIda.setSclass("label-size11-bold");
		lblVtainfoViajeEmpresaIda.setStyle("font-size:13px !important");
		columnVtaInfoViajeIda.appendChild(lblVtainfoViajeEmpresaIda);
		capVtaIdaMapa.getChildren().clear();
		capVtaIdaMapa.setLabel("SELECCION DE ASIENTO(S)");
		if(detalleItinerarioIda.getItinerario().getBus()!=null)
			loadDatosBusMapa(detalleItinerarioIda.getItinerario().getBus(), capVtaIdaMapa);
		
		if(isVtaIdaVuelta) {
			detalleItinerarioVuelta = ltbxBusqVuelta.getSelectedItem().getValue();
			createEstructura(detalleItinerarioVuelta.getItinerario().getServicio().getId(), gpbxVtaVueltaMapa, true, detalleItinerarioVuelta, mapaAsientosRetorno, null, false);
			
			columnVtaInfoViajeIda.setLabel("INFORMACION DEL VIAJE DE IDA ::: ");
			capVtaIdaMapa.setLabel("SELECCION DE ASIENTO(S) DE IDA");
			capVtaRetornoMapa.getChildren().clear();
			capVtaRetornoMapa.setLabel("SELECCION DE ASIENTO(S) DE VUELTA");
			if(detalleItinerarioVuelta.getItinerario().getBus()!=null)
				loadDatosBusMapa(detalleItinerarioVuelta.getItinerario().getBus(), capVtaRetornoMapa);
			columnVtaInfoViajeVuelta.setLabel("INFORMACION DEL VIAJE DE VUELTA :::: ");
			columnVtaInfoViajeVuelta.getChildren().clear();
			Label lblVtainfoViajeEmpresaVuelta = new Label(detalleItinerarioVuelta.getItinerario().getEmpresa().getNombreCorto());
			lblVtainfoViajeEmpresaVuelta.setSclass("label-size11-bold");
			lblVtainfoViajeEmpresaVuelta.setStyle("font-size:13px !important");
			columnVtaInfoViajeVuelta.appendChild(lblVtainfoViajeEmpresaVuelta);
			
			lblVtaVueltaDestino_fecha.setValue(detalleItinerarioVuelta.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()));
			loadPuntoEmbarque(detalleItinerarioVuelta, cmbVtaVueltaEmbarque_hora);
			loadPuntoDesembarque(detalleItinerarioVuelta, cmbVtaVueltaDesembarque_hora);
			seleccionarPuntoEmbarque(cmbVtaVueltaEmbarque_hora, detalleItinerarioVuelta.getPuntoEmbarque().getAgencia());
			if(cmbVtaVueltaEmbarque_hora.getSelectedIndex() <0 )
				cmbVtaVueltaEmbarque_hora.setSelectedIndex(0);			
			lblVtaVueltaAsiento.setValue("-");
			dbxVtaVueltaTarifa.setValue(.00);
			dbxVtaVueltaRecargo.setValue(.00);
			chbxVtaIdaVuelta.setChecked(true);
			chbxVtaIdaVuelta.setDisabled(false);
		}
		
				
		resertOperacionAsiento();		
				
		tabVtaIdaMapa.setSelected(true);
		divPaso_1.setVisible(false);
		divPaso_2.setVisible(true);
		
		habilitarBtnActualizarMapa();
		habilitarUpdateMapaAutomaticamente();
		habilitarSelectAsientoVendido();
		enabledAllBtnOptionsByAsiento();

		//**********************************************************************
//		chbxVtaUpdateMapAuto.setChecked(false);
//		chbxVtaUpdateMapAuto.setDisabled(true);
//		Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
//		Date fechaSalida = detalleItinerarioIda.getFechaPartida();		
//		if(fechaSalida.getTime() >= fechaActual.getTime()) {
//			chbxVtaUpdateMapAuto.setChecked(true);
//			onCheck_chbxVtaUpdateMapAuto();	
//			chbxVtaUpdateMapAuto.setDisabled(false);
//		}
		//**********************************************************************
		
		
		//*********************************************************************************
		//Caga datos de la venta del pasajero seleccionado desde el historial de compras.
		//*********************************************************************************
		if(ventaPasaje_x!=null) {
			ventaPasaje_x = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasaje_x.getId());
			Pasajero pasajero = ServiceLocator.getPasajeroManager().buscarPorId(ventaPasaje_x.getPasajero().getId());
			boolean isConfirmaFA = (ventaPasaje_x.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_FECHA_ABIERTA || ventaPasaje_x.getTipoMovimiento().getId()==Constantes.ID_TIPMOV_POSTERGACION_FA);
			boolean isConfirmaReserva = (!isConfirmaFA && ventaPasaje_x.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA));
			boolean isEdicionVenta = (!isConfirmaFA && isConfirmaReserva);
			
			if(isConfirmaFA || isEdicionVenta) {
				lblVtaNumeroComprobante.setValue(ventaPasaje_x.getNumeroBoleto());
				lblVtaTipoComprobante.setValue(ventaPasaje_x.getTipoComprobante().getDenominacion());
				if(isConfirmaFA) {
					lblVtaInfoTipoOperacion.setValue("CONFIRMACION F.A.");
					rdVtaConfirmacionFA.setChecked(true);
					disabledControlsPasajero(true);
				}
			}			
			
			loadDatosPasajero(pasajero);			
		}
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onCheck_chbxVtaUpdateMapAuto()throws Exception{
		
		if(chbxVtaUpdateMapAuto.isChecked())		
			autoRefreshMapaBus();
		else {
			timerRefreshMapa.stop();
			log.info("El usuario ha detenido la actualización automátoca del mapa");
		}
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
	private void onChanging_dbxVtaIdaRecargo(Event event)throws Exception{
		try {
			Double recargoIda = Optional.ofNullable(((InputEvent)event).getValue())
					.map(val -> val.toString().isEmpty()? .00 : Double.valueOf(val))
					.orElse(.00);
			
			if(recargoIda < 0) {
				recargoIda = .00;
				((Doublebox)event.getTarget()).setValue(.00);
			}
			
			// Habilita/Deshabilita el descuento						
			cleanDatosDescuento();
			
			Double recargoVuelta = Optional.ofNullable(dbxVtaVueltaRecargo.getValue())
					.orElse(.00);
			
			if(recargoIda > 0 || recargoVuelta > 0) {
				disabledControlsDescuento(true);
			}else {
				disabledControlsDescuento(false);
			}
			
			calcularTotalPago(recargoIda);
			
		} catch (Exception e) {}		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onChanging_dbxVtaVueltaRecargo(Event event)throws Exception{
		try {
			Double recargoVuelta = Optional.ofNullable(((InputEvent)event).getValue())
					.map(val -> val.toString().isEmpty()? .00 : Double.valueOf(val))
					.orElse(.00);
			
			if(recargoVuelta < 0) {
				recargoVuelta = .00;
				((Doublebox)event.getTarget()).setValue(.00);
			}
			
			// Habilita/Deshabilita el descuento
			cleanDatosDescuento();
			
			Double recargoIda = Optional.ofNullable(dbxVtaIdaRecargo.getValue())
					.orElse(.00);
			
			if(recargoVuelta > 0 || recargoIda > 0) {
				disabledControlsDescuento(true);
			}else {
				disabledControlsDescuento(false);
			}
			
			calcularTotalPago(recargoVuelta);
			
		} catch (Exception e) {}
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
	private void onBlur_txtVtaAutorizaDescuentoCodigo()throws Exception{
		dbxVtaIdaRecargo.setDisabled(false);		
		VentaIdaRetorno venta = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();		
		dbxVtaIdaTarifa.setValue(venta.getDetalleItinerarioIDA().getTarifa());
		if(isVtaIdaVuelta) {
			dbxVtaVueltaTarifa.setValue(venta.getDetalleItinerarioRETORNO().getTarifa());
			dbxVtaVueltaRecargo.setDisabled(false);
		}			

		calcularTotalPago();
		
		if(txtVtaAutorizaDescuentoCodigo.getText().trim().isEmpty()) {
			return;
		}
			
		
		if(ltbxVtaAsientosSeleccionados.getSelectedCount()==0) {
			DlgMessage.information(Messages.getString("WndVentaReservaNew.information.noSelectAsiento"));
			return;
		}else if(!txtVtaAutorizaDescuentoCodigo.getText().trim().isEmpty() && cmbVtaAutorizaDescuento.getSelectedIndex()<0) {
			txtVtaAutorizaDescuentoCodigo.setText("");
			DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.noSelectAutorizador"), cmbVtaAutorizaDescuento);
			return;
		}
		
		Descuento descuento = validarCodigoDescuento(cmbVtaAutorizaDescuento, txtVtaAutorizaDescuentoCodigo, isVtaIdaVuelta);
		
		if(descuento != null) {			
			Double valorInicial = descuento.getValorMinimo();
			Double valorFinal = descuento.getValorMaximo();
			
			if(valorInicial.doubleValue() == valorFinal.doubleValue()) {
				Double valorAplica = valorInicial;
				aplicarCodigoDescuento(descuento, isVtaIdaVuelta, valorAplica, true);	
			}else {
				//Cuando permite aplicar el descuento por rango
				wndModal = createWindowDescuentoByRango(descuento);
				this.appendChild(wndModal);
				wndModal.setMode("modal");
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
		habilitarOpcionVenta(true);
		habilitarOpcionReserva(false);
		cleanDatosPago(event);
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
		habilitarOpcionReserva(true);
		habilitarOpcionVenta(false);
		cleanDatosInfoComprobante();
		cleanDatosPago(event);
		lblVtaInfoTipoOperacion.setValue(((Radio)event.getTarget()).getLabel());
		
		if(event!=null)
			focusCursorByTipoOperacion(event);
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void onCheck_rdVtaPostergacionFA(Event event)throws Exception{
		cleanDatosPago(event);
		cleanDatosInfoComprobante();
		disabledControlsPago(true);
		disabledControlsDescuento(true);
		onSelect_ltbxVtaAsientosSeleccionados(event);
		loadDatosPostergacionFA();
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void onCheck_rdVtaCambioNombre(Event event)throws Exception{
		cleanDatosPago(event);
		cleanDatosInfoComprobante();
		onSelect_ltbxVtaAsientosSeleccionados(event);
		loadDatosCambioNombre();
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
	private void onSelect_ltbxVtaAsientosSeleccionados(Event event)throws Exception{
		if(!rdVtaConfirmacionFA.isChecked())
			cleanDatosInfoComprobante();
		lblVtaIdaAsiento.setValue("-");
		dbxVtaIdaTarifa.setValue(.00);
		dbxVtaIdaRecargo.setValue(.00);
		lblVtaVueltaAsiento.setValue("-");
		dbxVtaVueltaTarifa.setValue(.00);
		dbxVtaVueltaRecargo.setValue(.00);
		if(!rdVtaConfirmacionFA.isChecked())
			cleanDatosPasajero(true);
		cleanDatosInfante();
		cleanDatosPago(event);
		if(ltbxVtaAsientosSeleccionados.getItemCount()==0)
			resertOperacionAsiento();
		cleanDatosViaje();
		disabled_btnGuardar(btnVtaGuardar, true);
		disabledAllBtnOptionsByAsiento();
		
		if(ltbxVtaAsientosSeleccionados.getItemCount()>0 && ltbxVtaAsientosSeleccionados.getSelectedCount() ==0)
			ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
		
		if(ltbxVtaAsientosSeleccionados.getSelectedCount()>0) {
			VentaIdaRetorno venta = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();
			DetalleItinerario detalleItinerarioIda = (venta.getDetalleItinerarioIDA()!=null?venta.getDetalleItinerarioIDA():null);
			DetalleItinerario detalleItinerarioVuelta = (venta.getDetalleItinerarioRETORNO()!=null?venta.getDetalleItinerarioRETORNO():null);
			if(detalleItinerarioIda!=null) {
				String numeroAsiento =  getFormatAsiento(Integer.valueOf(detalleItinerarioIda.getAsiento()));
				
				lblVtaIdaDestino_fecha.setValue(detalleItinerarioIda.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()));
				lblVtaIdaAsiento.setValue(numeroAsiento);
				dbxVtaIdaTarifa.setValue(detalleItinerarioIda.getTarifa());
				
				//Valida si es una confirmacion de FA
				if(rdVtaConfirmacionFA.isChecked()) {
					disabledControlsInfoViaje(false);							
				}else {
					habilitarOperacionAsiento(detalleItinerarioIda.getObjAsiento());
				}
				
			}
			
			if(detalleItinerarioVuelta!=null) {
				String numeroAsiento = getFormatAsiento(Integer.valueOf(detalleItinerarioVuelta.getAsiento()));
				
				lblVtaVueltaDestino_fecha.setValue(detalleItinerarioVuelta.getRuta().toString() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()));
				lblVtaVueltaAsiento.setValue(numeroAsiento);
				dbxVtaVueltaTarifa.setValue(detalleItinerarioVuelta.getTarifa());
			}
			
			if(isVtaIdaVuelta && detalleItinerarioIda!=null && detalleItinerarioVuelta!=null && detalleItinerarioIda.getObjAsiento().getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO)
				disabled_btnGuardar(btnVtaGuardar, false);
			else if(!isVtaIdaVuelta && detalleItinerarioIda!=null && detalleItinerarioIda.getObjAsiento().getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO)
				disabled_btnGuardar(btnVtaGuardar, false);
			
			if(!rdVtaConfirmacionFA.isChecked()) {
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaPaxTipoDocumento, Constantes.ID_TIPDOC_DNI);
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbVtaInfTipoDocumento, Constantes.ID_TIPDOC_DNI);	
			}			
			focusCursorByTipoOperacion(null);
		}
		
		calcularTotalPago();
		
		if(rdVtaPostergacionFA.isChecked())
			loadDatosPostergacionFA();
		else if(rdVtaCambioNombre.isChecked())
			loadDatosCambioNombre();	
		
		//Habilita los datos del pago si es total a pagar es mayo que cero
		if(dbxVtaTotalPagar.getValue()!=null && dbxVtaTotalPagar.getValue()>.00) {
			disabledControlsPago(false);
			disabledControlsDescuento(false);
			
			//cuando es una confirmacion FA, deshabilita el ingreso de descuentos.
			if(rdVtaConfirmacionFA.isChecked()) {
				cmbVtaAutorizaDescuento.setDisabled(true);
				txtVtaAutorizaDescuentoCodigo.setDisabled(true);
			}
		}
	}
	
	/**
	 * Habilita/deshabilita las operaciones permitidas para con el asiento
	 * @param asiento
	 * @throws Exception
	 */
	private void habilitarOperacionAsiento(Asiento asiento)throws Exception{
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
				lblVtaInfoTipoOperacion.setValue("CONFIRMACION DE RESERVA");			
			disabledControlsInfoViaje(false);
			disabledControlsPasajero(false);
			disabledControlsCliente(false);
			disabledControlsPago(false);
			disabledControlsDescuento(false);
			disabled_btnGuardar(btnVtaGuardar, false);
		}
		
		if(asiento.getVentaPasaje()!=null) {			
			loadDatosPasajero(asiento.getVentaPasaje().getPasajero());
			if(asiento.getVentaPasaje().getCliente()!=null) {
				chbxVtaFactura.setChecked(true);
				onCheck_chbxVtaFactura();
				loadDatosCliente(asiento.getVentaPasaje().getCliente());
			}
			loadDatosViaje(true, asiento.getVentaPasaje());
			loadDatosinfoComprobante(asiento.getVentaPasaje());
			
//			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
//			Date fechaPartida = asiento.getVentaPasaje().getFechaPartida();
						
			//Habilita la reimpresión
//			if(estadoAsiento == Constantes.ASIENTO_VENDIDO && fechaPartida.getTime() >= fechaActual.getTime()) {
			if(estadoAsiento == Constantes.ASIENTO_VENDIDO) {
				habilitarBtnReimpresion(asiento.getVentaPasaje(), btnVtaReimpresion);
			}
			
			//Habilita la anulación
			if(estadoAsiento == Constantes.ASIENTO_VENDIDO || estadoAsiento == Constantes.ASIENTO_RESERVADO) {
				habilitarBtnAnulacion();
			}
			
			//habilita otras opciones disponibles para el asiento
			if(estadoAsiento == Constantes.ASIENTO_VENDIDO) {
				habilitarOpcionPostergacion(asiento.getVentaPasaje());
				habilitarOpcionPostergacionFA(asiento.getVentaPasaje());
				habilitarOpcionCambioNombre(asiento.getVentaPasaje());
			}
		}
	}
	
	/**
	 * Carga datos del Bus programado, para mostrarlos en los mapas
	 * @param bus: Instancia de la Class Bus
	 * @param capVtaMapa: Instancia de la class Caption en donde se va mostrar los datos del bus.
	 * @throws Exception
	 */
	private void loadDatosBusMapa(Bus bus, Caption capVtaMapa)throws Exception{
		capVtaMapa.getChildren().clear();
		
		String datosBus = bus.getCodigo();
		datosBus += " ("+bus.getNumeroPlaca()+")";			
		Label lblDatosBus = new Label(datosBus);
		lblDatosBus.setSclass("label-size11-bold");
		lblDatosBus.setStyle("font-size:14px !important;");
		capVtaMapa.appendChild(lblDatosBus);
		
	}
	
	/**
	 * Habilita/deshabilita la Operacion Venta
	 * @param enabled
	 * @throws Exception
	 */
	private void habilitarOpcionVenta(boolean enabled)throws Exception{		
		gridVtaDatosPago.setVisible(enabled);
		
	}
	
	/**
	 * Habilita/deshabilita la operación reserva
	 * @param enabled
	 * @throws Exception
	 */
	private void habilitarOpcionReserva(boolean enabled)throws Exception{		
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
												
						/************** Tracking (GPS) **********************/
//						listVentasReimpresion.forEach(vta -> {
//							try {	
//								VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(vta.getId());
//								String motivoMovimiento = "REIMPRESION";										
//								MovimientoPasajes movimientoPasajes = UtilData.createTracking(ventaPasaje, motivoMovimiento);
//								ServiceLocator.getMovimientoPasajesManager().guardar(movimientoPasajes);
//							} catch (Exception e1) {
//								e1.printStackTrace();
//								log.error(e1);
//								DlgMessage.error(e1.getMessage());
//							}								
//						});
						/****************************************************/
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
								onSelect_ltbxVtaAsientosSeleccionados(null);														
								onRefreshMap();
								disabledControlsInfoViaje(true);
								disabledControlsPasajero(true);
								disabledControlsCliente(true);
								disabledControlsPago(true);
								disabledControlsDescuento(true);
								disabledAllBtnOptionsByAsiento();
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
					
					Integer rolId = getRol().getId();
					Integer usuarioId = getUsuario().getId();
					Integer usuarioIdVenta = ventaOriginal.getUsuario().getId();
					
//					if (getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO && !(ventaOriginal.getUsuario().getId().equals(getUsuario().getId())))
//						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.otroUsuario"));
//					else if(getRol().getId().intValue()!=Constantes.ID_ROL_SUPER_USUARIO &&
//							!(Constantes.FORMAT_DATE.format(ventaOriginal.getFechaInsercion()).equals(Constantes.FORMAT_DATE.format(new Date()))))
//						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.fechaPasada"));
//					else 
					
					if(!rolId.equals(Constantes.ID_ROL_SUPER_USUARIO) && !usuarioId.equals(usuarioIdVenta)) {
						DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.otroUsuario"));
					}else if(ventaOriginal.getLiquidacion() == null){
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
	private void onClick_btnVtaPostergar()throws Exception{
		VentaIdaRetorno venta = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();
		
		VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarPorId(venta.getDetalleItinerarioIDA().getObjAsiento().getVentaPasaje().getId());
		
		if(ventaPasaje.getCliente() != null) {
			ventaPasaje.setCliente(ServiceLocator.getClienteManager().buscarPorId(ventaPasaje.getCliente().getId()));
		}
		
		boletoManifestado = ServiceLocator.getDetalleManifiestoManager().validarVentaManifiesto(ventaPasaje.getId());
		
		TipoNota penalidad = ServiceLocator.getTipoNotaManager().buscarPorId(Long.valueOf(Constantes.ID_TIPNOTA_POSTERGACION));
		
		Double montoPenalidad = .00;
		if(penalidad!=null && penalidad.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
			montoPenalidad = penalidad.getGastoAdminEfectivo();
		}
			
			
		wndModal = createWindowPostergacion(ventaPasaje, montoPenalidad);
		this.appendChild(wndModal);
		wndModal.setMode("modal");
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaProgramarServicios()throws Exception{		
//		Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(detalleItinerarioIda.getItinerario().getId());
//		TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();
//		criteriosBusqueda.put("itinerario", itinerario);
//		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);		
//		List<ProgramacionServicio> listProgramacion = ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);
//		programacionServicio = null;
//		if(listProgramacion.size()>0)
//			programacionServicio = listProgramacion.get(0);
//		
//		//busca el manifiesto
//		Manifiesto manifiesto = ServiceLocator.getManifiestoManager().consultaMinifiestImpreso(itinerario.getId());
//		
//		wndModal = createWindowProgramacionBus(itinerario, manifiesto);
//		this.appendChild(wndModal);
//		wndModal.setMode("modal");
//		
//		if(manifiesto!=null)
//			DlgMessage.information("El Manifiesto ya está emitido, no podrá realizar cambios en la Programación.");
		
		showWindowProgramacionBus(detalleItinerarioIda);
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaManifiestoPasajeros()throws Exception{
		programacionServicio = null;
		Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(detalleItinerarioIda.getItinerario().getId());
		
		//Busca la programcion
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("itinerario", itinerario);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);		
		List<ProgramacionServicio> listProgramacion = ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);		
		if(listProgramacion.size()>0) {
			programacionServicio = listProgramacion.get(0);
			
			//Busca el TUC del bus
			criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("bus", programacionServicio.getBus());
			criteriosBusqueda.put("tipoDocumento", new TipoDocumento(Constantes.ID_TIPDOC_TARJETA_CIRCULACION));
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<DocumentoBus> listdoctBus = ServiceLocator.getDocumentoBusManager().buscarPorX(criteriosBusqueda, null);
			for(DocumentoBus _documentoBus: listdoctBus) {
				Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
				Date fechaVenceDoctBus = Constantes.FORMAT_DATE.parse(_documentoBus.getFechaVencimiento());
				if(fechaVenceDoctBus.getTime()>=fechaActual.getTime())
					itinerario.getBus().setDocumentoBus(_documentoBus);;				
			}			
		}
		
		//Busca el manifiesto
	 	Manifiesto manifiesto = ServiceLocator.getManifiestoManager().consultaMinifiestImpreso(itinerario.getId());
	 	if(manifiesto!=null)
	 		manifiesto = ServiceLocator.getManifiestoManager().buscarPorId(manifiesto.getId());
	 	
	 	//Busca el correlativo para el manifiesto
	 	EspecieValorada especieValoradaSunat =  ServiceLocator.getManifiestoManager().consultaAutorizacionSunat(getAgencia().getId(), itinerario.getEmpresa().getId());	 	
	 	
	 	//
		wndModal = createWindowManifiesto_carpetaDespacho(true, itinerario, programacionServicio, manifiesto, especieValoradaSunat);
		this.appendChild(wndModal);
		wndModal.setMode("modal");		
		
		//
		if(especieValoradaSunat==null)
			DlgMessage.information(Messages.getString("WndManifiesto.information.noCorrelativoManifiesto"));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaCarpetaDespacho()throws Exception{
		programacionServicio = null;
		Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(detalleItinerarioIda.getItinerario().getId());
		//Busca la programcion
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("itinerario", itinerario);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);		
		List<ProgramacionServicio> listProgramacion = ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);		
		if(listProgramacion.size()>0) {
			programacionServicio = listProgramacion.get(0);
			itinerario.getBus().setProgramacionServicio(programacionServicio);	
		}
		
		wndModal = createWindowManifiesto_carpetaDespacho(false, itinerario, null, null, null);
		this.appendChild(wndModal);
		wndModal.setMode("modal");		
		
		
	}
	
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaCancelar()throws Exception{
		if(timerRefreshMapa!=null)
			timerRefreshMapa.stop();
		
		liberarAsientos(ltbxVtaAsientosSeleccionados);
		cleanDatosVenta();
		divPaso_2.setVisible(false);
		divPaso_1.setVisible(true);		
		tabBusqIda.setSelected(true);
		txtBusqBy.setText("");
		cleanBusquedaSeleccionHp();
		onChange_clBusqIda(null);
//		onClick_btnBuscar();
	}
	
	
	private void onClick_btnVtaGuargar()throws Exception{		
//		if(!rdVtaVenta.isChecked() && !rdVtaReserva.isChecked() && !rdVtaPostergacion.isChecked() && !rdVtaPostergacionFA.isChecked() && !rdVtaCambioNombre.isChecked()) {
		if(!rdVtaVenta.isChecked() && !rdVtaReserva.isChecked() && !rdVtaPostergacionFA.isChecked() && 
				!rdVtaCambioNombre.isChecked() && !rdVtaConfirmacionFA.isChecked()) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTipoOperacion"));
			return;
		}
		
		boolean isReserva = rdVtaReserva.isChecked();
		boolean isVenta = rdVtaVenta.isChecked();
		boolean isPostergacionFA = rdVtaPostergacionFA.isChecked();
		boolean isCambioNombre = rdVtaCambioNombre.isChecked();
		boolean isConfirmacionFA = rdVtaConfirmacionFA.isChecked();
		
		boolean emiteComprobantePago = (gridVtaDatosPago.isVisible() && dbxVtaTotalPagar.getValue()!=null && dbxVtaTotalPagar.getValue()>.00);
		
		//Valida Especie Valorada: Cuando es una ida y vuelta y las empresas del itinerario de ida y del retorno son diferenctes
		if(emiteComprobantePago && isVtaIdaVuelta 
				&& detalleItinerarioIda.getItinerario().getEmpresa().getId().intValue()!=detalleItinerarioVuelta.getItinerario().getEmpresa().getId().intValue()) {
			//Primero valida la especie valorada del retorno
			ControlEspecieValorada controlEspecieValorada = loadEspecieValoradaByVenta(false);
			if(controlEspecieValorada ==null)
				return;
		}
		if(emiteComprobantePago) {
			//Valida especie valorada de Ida
			ControlEspecieValorada controlEspecieValorada = loadEspecieValoradaByVenta(true);
			if(controlEspecieValorada ==null)
				return;
		}
						
		//si es una venta ida y vuelta, valida que se hayan seleccionado la misma cantidad de asientos tanto de ida como de vuelta
		if(isVtaIdaVuelta) {
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno ventaIdaRetorno = item.getValue();
				if(ventaIdaRetorno.getDetalleItinerarioIDA()==null || ventaIdaRetorno.getDetalleItinerarioRETORNO()==null) {
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noEqualsSeatSelections"));
					return;
				}
			}
		}
		//Valida la tarifa
		if(!isPostergacionFA && !isCambioNombre) {
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				VentaIdaRetorno ventaIdaRetorno = item.getValue();
				if(ventaIdaRetorno.getDetalleItinerarioIDA().getTarifa()==null || ventaIdaRetorno.getDetalleItinerarioIDA().getTarifa()==.00) {
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioAsientoIda").replace("@asiento", ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento()));
					return;
				}else if(isVtaIdaVuelta && (ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa()==null || ventaIdaRetorno.getDetalleItinerarioRETORNO().getTarifa()==.00)) {
					DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerarioAsientoRetorno").replace("@asiento", ventaIdaRetorno.getDetalleItinerarioIDA().getAsiento()));
					return;
				}
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
		}else if(isVtaIdaVuelta && !(cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSeleccionoAgenciaPartidaRetorno"), cmbVtaVueltaEmbarque_hora);
			return;
		}else if(isVtaIdaVuelta && !(cmbVtaVueltaDesembarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)) {
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
		if(emiteComprobantePago) {
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
			}else if(isVtaIdaVuelta && dtbxVtaVueltaExpiraReserva.getValue()==null && dtbxVtaVueltaExpiraReserva.isDisabled()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTiempoReserva"), itbxVtaVueltaTiempoReserva);
				return;
			}else if(isVtaIdaVuelta && dtbxVtaVueltaExpiraReserva.getValue()==null && !dtbxVtaVueltaExpiraReserva.isDisabled()) {
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
			if(isVtaIdaVuelta) {
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
		
		//Valida liquidacion
		if(emiteComprobantePago) {
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
		
		//Valida el codigo de descuento
		if(!txtVtaAutorizaDescuentoCodigo.getText().trim().isEmpty()) {
			Descuento descuento = validarCodigoDescuento(cmbVtaAutorizaDescuento, txtVtaAutorizaDescuentoCodigo, isVtaIdaVuelta);		
			if(descuento==null)
				return;
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
		if(isReserva || isPostergacionFA)
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
		
		String messageConfirmationUser = "";
		List<VentaPasaje> listVentas = new ArrayList<VentaPasaje>();
		//*****************************************************************************************
		//*****************************************************************************************	
		if(isVenta || isReserva) {
			for(DetalleItinerario detalleItinerario: listDetalleItienrario) {
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = null;
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = null;
				String observaciones = null;
				Double importePagado = .00;
				Double recargo = .00;
				Date fechaExpiraReserva = null;
				boolean isConfirmaReserva = false;
				
				VentaPasaje ventaPasajeRef = null;
				if(detalleItinerario.getObjAsiento().getVentaPasaje()!=null)
					ventaPasajeRef = detalleItinerario.getObjAsiento().getVentaPasaje();
				
				// Valida si es una confirmacion de reserva
				if(ventaPasajeRef!=null && ventaPasajeRef.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
					isConfirmaReserva = true;
				}
				
				// Crea una nueva instacia de VentaPasajes
				ventaPasaje_x = new VentaPasaje();			
				
				if(detalleItinerario.getEsIda()) {
					itinerarioAgenciaPartida = cmbVtaIdaEmbarque_hora.getSelectedItem().getValue();
					itinerarioAgenciaLlegada = cmbVtaIdaDesembarque_hora.getSelectedItem().getValue();					
					observaciones = (txtVtaIdaObservaciones.getText().trim().length()>0 ? txtVtaIdaObservaciones.getText().trim().toUpperCase(): null);
					if(isVenta) {
						// Venta
//						importePagado = dbxVtaIdaTarifa.getValue();
						importePagado = dbxVtaTotalPagar.getValue();
						recargo = Optional.ofNullable(dbxVtaIdaRecargo.getValue()).orElse(.00);
					}else {
						// Reserva
						fechaExpiraReserva = dtbxVtaIdaExpiraReserva.getValue();
					}
				}else {
					itinerarioAgenciaPartida = cmbVtaVueltaEmbarque_hora.getSelectedItem().getValue();
					itinerarioAgenciaLlegada = cmbVtaVueltaDesembarque_hora.getSelectedItem().getValue();
					observaciones = (txtVtaVueltaObservaciones.getText().trim().length()>0 ? txtVtaVueltaObservaciones.getText().trim().toUpperCase(): null);
					if(isVenta) {
						// Venta
//						importePagado = dbxVtaVueltaTarifa.getValue();
						importePagado = dbxVtaTotalPagar.getValue();
						recargo = Optional.ofNullable(dbxVtaVueltaRecargo.getValue()).orElse(.00);
					}else {
						// Reserva
						fechaExpiraReserva = dtbxVtaVueltaExpiraReserva.getValue();
					}
				}
				
				if(isReserva) {
					ventaPasaje_x.setFechaLiquidacion(new Date());
					ventaPasaje_x.setImportePagado(.00);
					ventaPasaje_x.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_RESERVA));
					ventaPasaje_x.setTipoTransaccion(Constantes.TIPO_OPERACION_RESERVA);
					ventaPasaje_x.setFechaExpiracionReserva(fechaExpiraReserva);					
				}else {
					//Cuando es una confimación de reserva, valida que esta aún este disponible.
//					if(ventaPasajeRef!=null && ventaPasajeRef.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
					if(isConfirmaReserva) {
						VentaPasaje reserva = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeRef.getId());
						int tipoMovimientoId = reserva.getTipoMovimiento().getId();
						if(tipoMovimientoId==Constantes.ID_TIPMOV_ANULACION_SISTEMA || tipoMovimientoId==Constantes.ID_TIPMOV_ANULACION) {
							DlgMessage.information(Messages.getString("WndVentaReservaNew.information.fechaExpiraReservaMayorSalida"));
							onRefreshMap();
							return;
						}
					}					
					ventaPasaje_x.setFechaLiquidacion(fechaLiquidacion);
					ventaPasaje_x.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
					ventaPasaje_x.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
					ventaPasaje_x.setImportePagado(importePagado);
				}
				ventaPasaje_x.setVentaOriginal(ventaPasajeRef!=null && !isConfirmaReserva?ventaPasajeRef.getId():null);
				ventaPasaje_x.setVentaPasaje(ventaPasajeRef);
				ventaPasaje_x.setEsFechaAbierta(Constantes.FALSE_VALUE);
				ventaPasaje_x.setIdaRetorno(isVtaIdaVuelta?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
				ventaPasaje_x.setItinerario(detalleItinerario.getItinerario());			
				ventaPasaje_x.setEmpresa(detalleItinerario.getItinerario().getEmpresa());
				ventaPasaje_x.setRuta(detalleItinerario.getRuta());
				ventaPasaje_x.setPasajero(pasajero);
				ventaPasaje_x.setCliente(cliente);
				ventaPasaje_x.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO)); //almenos por ahora siempre será contado
				ventaPasaje_x.setServicio(detalleItinerario.getItinerario().getServicio());
				ventaPasaje_x.setTipoComprobante(new TipoComprobante(cliente!=null?Constantes.ID_TIPCOM_FACTURA:Constantes.ID_TIPCOM_BOLETA_VENTA));
				ventaPasaje_x.setTipoFormaPago(cmbVtaTipoFormaPago.getSelectedIndex()>=0?cmbVtaTipoFormaPago.getSelectedItem().getValue():null);
				if(cmbVtaTarjeta.getSelectedIndex()>=0 && cmbVtaTarjeta.getSelectedItem().getValue() instanceof TarjetaCredito)
					ventaPasaje_x.setTarjetaCredito(cmbVtaTarjeta.getSelectedItem().getValue());
				ventaPasaje_x.setNumeroOperacionBancaria(txtVtaNumeroOperacion.getText().equals("")?null:txtVtaNumeroOperacion.getText());
				ventaPasaje_x.setNumeroBoleto(!lblVtaNumeroComprobante.getValue().trim().isEmpty()? lblVtaNumeroComprobante.getValue().trim().toUpperCase():null);
//				else if(rdVtaPostergacion.isChecked())
//					ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION));
//				else if(rdVtaPostergacionFA.isChecked())
//					ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION_FA));
//				else if(rdVtaCambioNombre.isChecked())
//					ventaPasaje.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_EFECTIVO));
				ventaPasaje_x.setNumeroAsiento(Integer.valueOf(detalleItinerario.getAsiento()));
				ventaPasaje_x.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
				ventaPasaje_x.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				ventaPasaje_x.setFechaPartida(detalleItinerario.getFechaPartida());
				ventaPasaje_x.setHoraPartida(detalleItinerario.getHoraPartida());
				ventaPasaje_x.setHoraEmbarque(itinerarioAgenciaPartida.getHoraPartida());
				ventaPasaje_x.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
				ventaPasaje_x.setFechaLlegada(detalleItinerario.getFechaLlegada());
				ventaPasaje_x.setHoraLllegada(detalleItinerario.getHoraLlegada());
				ventaPasaje_x.setHoraDesembarque(itinerarioAgenciaLlegada.getHoraLlegada());
				ventaPasaje_x.setPreferenciaAlimentaria(new PreferenciaAlimentaria(Constantes.ID_PREALIM_MENU_DEL_DIA)); //Volor por defecto
				//*******************************************
				ventaPasaje_x.setSecuencial(0);
				//*******************************************
				ventaPasaje_x.setCodigoDescuento(txtVtaAutorizaDescuentoCodigo.getText().trim().toUpperCase());
				ventaPasaje_x.setTarifa(detalleItinerario.getTarifa());
				ventaPasaje_x.setRecargo(recargo);
				// cuando existe un recargo a la tarifa, el valor de la tarifa real es reemplazado por el Importe pagado (tarifa + recargo)
				if(ventaPasaje_x.getRecargo() != null && ventaPasaje_x.getRecargo().doubleValue() > .00)
					ventaPasaje_x.setTarifa(ventaPasaje_x.getImportePagado());
				ventaPasaje_x.setDescuento(.00);
				if(ventaPasaje_x.getCodigoDescuento()!=null) {
					ventaPasaje_x.setDescuento(ventaPasaje_x.getTarifa() - ventaPasaje_x.getImportePagado());
					// cuando existe un descuento a la tarifa, el valor de la tarifa real es reemplazado por el Importe pagado (tarifa - descuento) - jabanto 30/08/2024
					ventaPasaje_x.setTarifa(ventaPasaje_x.getImportePagado());
				}					
				ventaPasaje_x.setPenalidad(.00);
				ventaPasaje_x.setAcuenta(.00);
				ventaPasaje_x.setImportePagadoByDiferencia(.00);
				ventaPasaje_x.setImportePagadoEfectivo(.00);
				ventaPasaje_x.setImportePagadoTarjeta(.00);				
				String fechaCaducidad=Constantes.FORMAT_DATE.format(ventaPasaje_x.getFechaPartida())+ " "+ventaPasaje_x.getHoraPartida();
				Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
				ventaPasaje_x.setFechaCaducidad(dateCaducidad);
				ventaPasaje_x.setAgencia(getAgencia());
				ventaPasaje_x.setUsuario(getUsuario());
				ventaPasaje_x.setCanalVenta(canalVenta);
				ventaPasaje_x.setUsuarioHardware(getUsuarioHardware());
				ventaPasaje_x.setEsRemoto(false);			
				ventaPasaje_x.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				ventaPasaje_x.setLiquidacion(null);
				ventaPasaje_x.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
				ventaPasaje_x.setNumeroControl("T00000");
				ventaPasaje_x.setObservaciones(observaciones);
				UtilData.auditarRegistro(ventaPasaje_x, false, getUsuario(), Executions.getCurrent());
				
				System.out.println("Tarifa => "+ ventaPasaje_x.getTarifa());
				System.out.println("Importe Pagado => "+ ventaPasaje_x.getImportePagado());
				/************** Tracking (GPS) **********************/
				String motivoMovimiento = (isReserva? "RESERVA" : "VENTA");
				if(isConfirmaReserva) {
					motivoMovimiento += " (CONFIRMACION DE RESERVA)";
				}
				if(!isReserva) {
					MovimientoPasajes movimientoPasajes = UtilData.createTracking(ventaPasaje_x, motivoMovimiento);
					ventaPasaje_x.setMovimientoPasajes(movimientoPasajes);	
				}				
				/****************************************************/
				
				listVentas.add(ventaPasaje_x);
			}
			if(isVenta)
				messageConfirmationUser = "WndVentaReserva.information.confirmacionGuardarVenta";
			else
				messageConfirmationUser = "WndVentaReserva.information.confirmacionGuardarReserva";
		}else if(isPostergacionFA){	
			Double importePagar = .00;
			if(emiteComprobantePago) {
				Double totalPago = dbxVtaTotalPagar.getValue();
				importePagar = (totalPago / listDetalleItienrario.size());
			}
			
			for(DetalleItinerario detalleItinerario: listDetalleItienrario) {
				Long ventaPasajeId = detalleItinerario.getObjAsiento().getVentaPasaje().getId();
				VentaPasaje ventaPasajero = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeId);
				VentaPasaje postergacionFA = (VentaPasaje) ventaPasajero.clone();
				postergacionFA.setItinerario(ServiceLocator.getItinerarioManager().buscarPorId(postergacionFA.getItinerario().getId()));
				postergacionFA.setVentaPasaje(ventaPasajero);
				postergacionFA.setEnviadoSFE(null);
				postergacionFA.setFechaEnvioSFE(null);
				postergacionFA.setNumeroAsiento(null);
				postergacionFA.setFechaPartida(null);
				postergacionFA.setHoraPartida(null);
				postergacionFA.setFechaLlegada(null);
				postergacionFA.setHoraLllegada(null);
				postergacionFA.setAgenciaPartida(null);
				postergacionFA.setAgenciaLlegada(null);
				postergacionFA.setNumeroPiso(null);
				postergacionFA.setEsFechaAbierta(Constantes.TRUE_VALUE);
				postergacionFA.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION_FA));
				postergacionFA.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO*Constantes.MILISEGUNDOS_X_DIA)));
				postergacionFA.setImportePagado(importePagar);
				postergacionFA.setUsuarioHardware(getUsuarioHardware());	
//				postergacionFA.setObservaciones(postergacionFA.getObservaciones()!=null?postergacionFA.getObservaciones()+";"+"POSTERGACION F.A.":"POSTERGACION F.A.");
				UtilData.auditarRegistro(postergacionFA, true, getUsuario(), Executions.getCurrent());
				
				/************** Tracking (GPS) **********************/
				String motivoMovimiento = "POSTERGACION F.A.";
				MovimientoPasajes movimientoPasajes = UtilData.createTracking(postergacionFA, motivoMovimiento);
				postergacionFA.setMovimientoPasajes(movimientoPasajes);
				/****************************************************/
				
				listVentas.add(postergacionFA);
			}
			messageConfirmationUser = "WndVentaReserva.question.postergacionFA";
		}else if(isCambioNombre) {
			Double importePagar = .00;
			if(emiteComprobantePago) {
				Double totalPago = dbxVtaTotalPagar.getValue();
				importePagar = (totalPago / listDetalleItienrario.size());
			}
			
			for(DetalleItinerario detalleItinerario: listDetalleItienrario) {
				Long ventaPasajeId =  detalleItinerario.getObjAsiento().getVentaPasaje().getId();
				VentaPasaje ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarPorId(ventaPasajeId);
				VentaPasaje ventaPasajeCambioNombre = (VentaPasaje) ventaPasaje.clone(); 
				ventaPasajeCambioNombre.setVentaPasaje(ventaPasaje);
				ventaPasajeCambioNombre.setItinerario(detalleItinerario.getItinerario());
				ventaPasajeCambioNombre.setRuta(detalleItinerario.getRuta());
				ventaPasajeCambioNombre.setImportePagado(importePagar);
				ventaPasajeCambioNombre.setPasajero(pasajero);
				ventaPasajeCambioNombre.setUsuarioHardware(getUsuarioHardware());
//				ventaPasajeCambioNombre.setObservaciones(ventaPasajeCambioNombre.getObservaciones()!=null?ventaPasajeCambioNombre.getObservaciones()+";"+"CAMBIO DE NOMBRE":"CAMBIO DE NOMBRE");				
				UtilData.auditarRegistro(ventaPasajeCambioNombre, true, getUsuario(), Executions.getCurrent());
				
				/************** Tracking (GPS) **********************/
				String motivoMovimiento = "CAMBIO DE NOMBRE";
				MovimientoPasajes movimientoPasajes = UtilData.createTracking(ventaPasajeCambioNombre, motivoMovimiento);
				ventaPasajeCambioNombre.setMovimientoPasajes(movimientoPasajes);
				/****************************************************/
				
				listVentas.add(ventaPasajeCambioNombre);
			}
			messageConfirmationUser = "WndVentaReserva.question.cambioNombre";
			
		}else if(isConfirmacionFA) {
			for(DetalleItinerario detalleItinerario: listDetalleItienrario) {
				VentaPasaje confirmacionFA = new VentaPasaje();
				confirmacionFA.setItinerario(detalleItinerario.getItinerario());
				confirmacionFA.setEmpresa(detalleItinerario.getItinerario().getEmpresa());			
				confirmacionFA.setRuta(detalleItinerario.getRuta());
				confirmacionFA.setCliente(ventaPasaje_x.getCliente()!=null?ventaPasaje_x.getCliente():null);
				confirmacionFA.setPasajero(ventaPasaje_x.getPasajero());			
				confirmacionFA.setFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO));
				confirmacionFA.setServicio(detalleItinerario.getItinerario().getServicio());
				confirmacionFA.setTipoComprobante(new TipoComprobante(confirmacionFA.getCliente()!=null?Constantes.ID_TIPCOM_FACTURA:Constantes.ID_TIPCOM_BOLETA_VENTA));
				confirmacionFA.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_CONFIRMACION_FA));
				confirmacionFA.setNumeroBoletoAnterior(ventaPasaje_x.getNumeroBoleto());
				/*Valida si la diferencia a es a favor de tepsa*/
				if(dbxVtaTotalPagar.getValue()>.00)
					confirmacionFA.setImportePagadoByDiferencia(dbxVtaTotalPagar.getValue());
				else
					confirmacionFA.setImportePagadoByDiferencia(0.00);
				confirmacionFA.setNumeroBoleto(!lblVtaNumeroComprobante.getValue().trim().isEmpty()? lblVtaNumeroComprobante.getValue().trim().toUpperCase():null);			
				confirmacionFA.setVentaOriginal(ventaPasaje_x.getVentaOriginal());			
				confirmacionFA.setTipoFormaPago(cmbVtaTipoFormaPago.getSelectedIndex()>=0?cmbVtaTipoFormaPago.getSelectedItem().getValue():null);
				if(cmbVtaTarjeta.getSelectedIndex()>=0 && cmbVtaTarjeta.getSelectedItem().getValue() instanceof TarjetaCredito)
					confirmacionFA.setTarjetaCredito(cmbVtaTarjeta.getSelectedItem().getValue());
				confirmacionFA.setNumeroOperacionBancaria(txtVtaNumeroOperacion.getText().equals("")?null:txtVtaNumeroOperacion.getText());
				confirmacionFA.setNumeroAsiento(Integer.valueOf(detalleItinerario.getAsiento()));
				confirmacionFA.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
				ItinerarioAgenciaPartida itinerarioAgenciaPartida = (ItinerarioAgenciaPartida) cmbVtaIdaEmbarque_hora.getSelectedItem().getValue();
				confirmacionFA.setAgenciaPartida(itinerarioAgenciaPartida.getAgencia());
				confirmacionFA.setFechaPartida(detalleItinerario.getFechaPartida());
				confirmacionFA.setHoraPartida(detalleItinerario.getHoraPartida());
				ItinerarioAgenciaLlegada itinerarioAgenciaLlegada = (ItinerarioAgenciaLlegada) cmbVtaIdaDesembarque_hora.getSelectedItem().getValue();
				confirmacionFA.setAgenciaLlegada(itinerarioAgenciaLlegada.getAgencia());
				confirmacionFA.setFechaLlegada(detalleItinerario.getFechaLlegada());
				confirmacionFA.setHoraLllegada(detalleItinerario.getHoraLlegada());
				confirmacionFA.setPreferenciaAlimentaria(new PreferenciaAlimentaria(Constantes.ID_PREALIM_MENU_DEL_DIA)); //Valor por default
				confirmacionFA.setSecuencial(ventaPasaje_x.getSecuencial());
				confirmacionFA.setTarifa(dbxVtaIdaTarifa.getValue());
				confirmacionFA.setRecargo(Optional.ofNullable(dbxVtaIdaRecargo.getValue()).orElse(.00));
				confirmacionFA.setDescuento(.00);
				confirmacionFA.setPenalidad(0.0);
				confirmacionFA.setAcuenta(ventaPasaje_x.getImportePagado());
				confirmacionFA.setImportePagadoEfectivo(.00);
				confirmacionFA.setImportePagadoTarjeta(.00);
				String fechaCaducidad=Constantes.FORMAT_DATE.format(confirmacionFA.getFechaPartida())+" "+confirmacionFA.getHoraPartida();
				Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
				confirmacionFA.setFechaCaducidad(dateCaducidad);				
				confirmacionFA.setImportePagado(dbxVtaTotalPagar.getValue());
				confirmacionFA.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
				confirmacionFA.setAgencia(getAgencia());
				confirmacionFA.setUsuario(getUsuario());
				confirmacionFA.setCanalVenta(canalVenta);
				confirmacionFA.setVentaPasaje(ventaPasaje_x);
				confirmacionFA.setLiquidacion(null);
				confirmacionFA.setFechaTransferencia(null);
				confirmacionFA.setFechaLiquidacion(fechaLiquidacion);
				confirmacionFA.setIdaRetorno(ventaPasaje_x.getIdaRetorno());
				confirmacionFA.setEsFechaAbierta(Constantes.FALSE_VALUE);
				confirmacionFA.setEsRemoto(false);
				
				if(confirmacionFA.getImportePagado()==.00) {
					//No emite un nuevo comprobante
					confirmacionFA.setId(ventaPasaje_x.getId());
					confirmacionFA.setTarifa(ventaPasaje_x.getTarifa());
					confirmacionFA.setDescuento(ventaPasaje_x.getDescuento());
					confirmacionFA.setImportePagado(ventaPasaje_x.getImportePagado());
					confirmacionFA.setFormaPago(ventaPasaje_x.getFormaPago());
					confirmacionFA.setTipoFormaPago(ventaPasaje_x.getTipoFormaPago());
				}
				
				confirmacionFA.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
				confirmacionFA.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				confirmacionFA.setPromocion(null);
				confirmacionFA.setObservaciones(ventaPasaje_x.getObservaciones());
				confirmacionFA.setNumeroControl(ventaPasaje_x.getNumeroControl());
				confirmacionFA.setUsuarioHardware(getUsuarioHardware());
				final boolean isNewComprobante = (confirmacionFA.getId()==null);
				if(isNewComprobante)
					UtilData.auditarRegistro(confirmacionFA, false, getUsuario(), Executions.getCurrent());
				else
					UtilData.auditarRegistro(confirmacionFA, true, getUsuario(), Executions.getCurrent());	
				
				/************** Tracking (GPS) **********************/
				String motivoMovimiento = "CONFIRMACION F.A.";
				MovimientoPasajes movimientoPasajes = UtilData.createTracking(confirmacionFA, motivoMovimiento);
				confirmacionFA.setMovimientoPasajes(movimientoPasajes);
				/****************************************************/
				
				listVentas.add(confirmacionFA);
			}
			
			messageConfirmationUser = "WndVentaReserva.information.confirmacionGuardarVenta";
		}
		Messagebox.show(Messages.getString(messageConfirmationUser), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				try {
					if(e.getName().equals("onYes")){
						if(isVenta || isReserva) {
							//Guarda la venta
							ServiceLocator.getVentaPasajesManager().guardarVenta(listVentas, false);						
							
							if(isVenta){
								List<VentaPasaje> listVentasEnvioFE = new ArrayList<VentaPasaje>();
								
								listVentas.stream().forEach(ventaPasaje -> {
									try {
										//Actualiza el correlativo de la venta
										ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(ventaPasaje, true);
										
										//Busca la Venta y agrega a la lista de envio a FE
										VentaPasaje _ventaPasaje = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
										listVentasEnvioFE.add(_ventaPasaje);
									} catch (Exception e1) {
										throw new RuntimeException("Error al actualizar el correlativo", e1);
									}
								});
								
								//envia el comprobante al servidor de Facturación Electrónica
								WSFE.sendVenta(listVentasEnvioFE, wndVentaReservaNew, true, null, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
								
								//Elimina el asiento de la lista 
								ltbxVtaAsientosSeleccionados.removeItemAt(ltbxVtaAsientosSeleccionados.getSelectedIndex());
								if(ltbxVtaAsientosSeleccionados.getItemCount() > 0)
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta"),txtVtaPaxBusqueda);
								else
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta"));
							}else if(isReserva) {
								Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
								cleanDatosReserva();
								DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarReserva"));
								habilitarOpcionReserva(false);
								habilitarOpcionVenta(true);
							}
						}else if(isPostergacionFA || isCambioNombre) {
							List<VentaPasaje> listNotasCredito = ServiceLocator.getVentaPasajesManager().postergarBoleto(listVentas,false, false);							
							if(emiteComprobantePago) {
								List<VentaPasaje> listVentasEnvioFE = new ArrayList<VentaPasaje>();
								for(VentaPasaje ventaPasaje: listVentas) {
									//Actualiza el correlativo
									ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(ventaPasaje, true);
									
									//Busca la venta
									VentaPasaje postergacionFA = ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
									listVentasEnvioFE.add(postergacionFA);
								}
								
								//Envia los nuevos comprobantes al Servidor de Facturación Electrónica
								WSFE.sendVenta(listVentasEnvioFE, wndVentaReservaNew, true, null, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
							}
							
							//Envia las NC a sunat
							for(VentaPasaje notaCredito: listNotasCredito) {								
								WSFE.sendNota(notaCredito);
							}
							
							if(isPostergacionFA) {
								Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
								DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoPostergacionFA"));
							}else {
								ltbxVtaAsientosSeleccionados.removeItemAt(ltbxVtaAsientosSeleccionados.getSelectedIndex());
								if(ltbxVtaAsientosSeleccionados.getItemCount()>0)
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoCambioNombre"),txtVtaPaxBusqueda);
								else
									DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoCambioNombre"));
							}
						}else if(isConfirmacionFA) {
							VentaPasaje vtaPasajeConfirmaFA = listVentas.get(0);	
							TipoNota tipoNotaCredito=null;
							VentaPasaje notaCredito = ServiceLocator.getVentaPasajesManager().confirmarFechaAbierta(vtaPasajeConfirmaFA, tipoNotaCredito, false);

							Util.limpiarListbox(ltbxVtaAsientosSeleccionados);					
							rdVtaConfirmacionFA.setChecked(false);
							
							//Valida si ha emitido un nuevo comprobante
							if (vtaPasajeConfirmaFA.getId().longValue()!=ventaPasaje_x.getId().longValue() && vtaPasajeConfirmaFA.getImportePagado() > .00) {
								//Actualiza el correlativo
								ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(vtaPasajeConfirmaFA, true);
								
								//Busca la venta
								Servicio servicio=ServiceLocator.getServicioManager().buscarPorId(vtaPasajeConfirmaFA.getServicio().getId().longValue());
								vtaPasajeConfirmaFA.setServicio(servicio);
								vtaPasajeConfirmaFA = ServiceLocator.getVentaPasajesManager().buscarPorId(vtaPasajeConfirmaFA.getId());
					
								//Envia el comprobante al Servidor de Facturación Electrónica
								WSFE.sendVenta(Arrays.asList(vtaPasajeConfirmaFA), wndVentaReservaNew, true, notaCredito, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
								
								DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoGuardarVenta"));
							}
						}
						
						Double totalPago = (dbxVtaTotalPagar.getValue()!=null && dbxVtaTotalPagar.getValue()>0?dbxVtaTotalPagar.getValue():.00);						
						cleanDatosInfoComprobante();
						if(cmbVtaAutorizaDescuento.getSelectedIndex()>=0)
							cmbVtaAutorizaDescuento.setSelectedIndex(-1);
						txtVtaAutorizaDescuentoCodigo.setText("");
						txtVtaPaxBusqueda.setText("");																					
						disabledControlsInfoViaje(true);
						disabledControlsPasajero(true);
						disabledControlsCliente(true);
						disabledControlsPago(true);
						disabledControlsDescuento(true);
						onSelect_ltbxVtaAsientosSeleccionados(e);
						onRefreshMap();
						
						if(ltbxVtaAsientosSeleccionados.getItemCount()>0 && gridVtaDatosPago.isVisible()) { 
							dbxVtaTotalPagar.setValue(totalPago);
							loadEspecieValoradaByVenta(true);
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
			asiento.setVentaPasaje(null);
		}
	}
	
	/**
	 * Habilita/Deshabilita los controles del retorno en la busqueda
	 * @throws Exception
	 */
	private void enabledControlsVueltaVenta()throws Exception{				
		boolean enabledCtrlsVuelta = isVtaIdaVuelta;
			
		tabVtaVueltaMapa.setDisabled(!enabledCtrlsVuelta);
		gridVtaInfoViajeVuelta.setVisible(enabledCtrlsVuelta);	
	}
	
	/**
	 * Limpia todos los datos de la venta
	 * @throws Exception
	 */
	private void cleanDatosVenta()throws Exception{
		programacionServicio = null;
		detalleItinerarioIda = null;
		detalleItinerarioVuelta = null;
		mapaAsientosIda = null;
		mapaAsientosRetorno = null;
		
		tabVtaVueltaMapa.setDisabled(true);
		
		txtVtaPaxBusqueda.setText("");
		
		btnVtaActualizarMapa.setDisabled(true);
		btnVtaLeyendaAsientos.setDisabled(true);
		
		disabledAllBtnOptionsByAsiento();		
		
		gridVtaInfoViajeVuelta.setVisible(false);		
		chbxVtaFactura.setChecked(false);
		Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
		
		lblVtaIdaDestino_fecha.setValue("");
		txtVtaIdaObservaciones.setText("");
		
		lblVtaVueltaDestino_fecha.setValue("");
		txtVtaVueltaObservaciones.setText("");
		
		chbxVtaIdaVuelta.setChecked(false);
		chbxVtaIdaVuelta.setDisabled(true);
		
		cleanDatosInfoComprobante();
		resertOperacionAsiento();		
		cleanDatosInfoViaje(true, true);		
		cleanDatosInfoViaje(false, true);				
		cleanDatosPasajero(true);
		enabledControlsFactura(false);
		cleanDatosInfante();
		cleanDatosPago(null);
		
		
		habilitarOpcionReserva(false);
		habilitarOpcionVenta(true);
		
		disabledControlsInfoViaje(true);
		disabledControlsPasajero(true);
		disabledControlsCliente(true);
		disabledControlsPago(true);
		disabledControlsDescuento(true);
		
		disabled_btnGuardar(btnVtaGuardar, true);
	}
	
	/**
	 * Limpia los datos con la información del Vieje
	 * @param isIda: (True) Es el viaje de Ida, (False) es el Vieaje de Vuelta
	 * @param cleanPuntoEmbarque: (True) para limpiar los controles de embarque y deseambarque; (False) solamente resetea la selección del embarque y deseambarque. 
	 * @throws Exception
	 */
	private void cleanDatosInfoViaje(boolean isIda, boolean cleanPuntoEmbarque)throws Exception{
		
		if(isIda) {			
			if(cleanPuntoEmbarque) {
				Util.limpiarCombobox(cmbVtaIdaEmbarque_hora);
				Util.limpiarCombobox(cmbVtaIdaDesembarque_hora);
			}else {			
				seleccionarPuntoEmbarque(cmbVtaIdaEmbarque_hora, detalleItinerarioIda.getPuntoEmbarque().getAgencia());
				if(cmbVtaIdaEmbarque_hora.getSelectedIndex()<0)
					cmbVtaIdaEmbarque_hora.setSelectedIndex(0);					
				
				if(cmbVtaIdaDesembarque_hora.getItemCount()==2)
					cmbVtaIdaDesembarque_hora.setSelectedIndex(1);
				else
					cmbVtaIdaDesembarque_hora.setSelectedIndex(0);
			}			
			lblVtaIdaAsiento.setValue("");
			dbxVtaIdaTarifa.setValue(.00);
			dbxVtaIdaRecargo.setValue(.00);
			txtVtaIdaObservaciones.setText("");
		}else {			
			if(cleanPuntoEmbarque) {				
				Util.limpiarCombobox(cmbVtaVueltaEmbarque_hora);
				Util.limpiarCombobox(cmbVtaVueltaDesembarque_hora);
			}else {
				seleccionarPuntoEmbarque(cmbVtaVueltaEmbarque_hora, detalleItinerarioVuelta.getPuntoEmbarque().getAgencia());
				if(cmbVtaVueltaEmbarque_hora.getSelectedIndex()<0)
					cmbVtaVueltaEmbarque_hora.setSelectedIndex(0);				
				
				if(cmbVtaVueltaDesembarque_hora.getItemCount()==2)
					cmbVtaVueltaDesembarque_hora.setSelectedIndex(1);
				else
					cmbVtaVueltaDesembarque_hora.setSelectedIndex(0);
			}			
			lblVtaVueltaAsiento.setValue("");
			dbxVtaVueltaTarifa.setValue(.00);
			dbxVtaVueltaRecargo.setValue(.00);
			txtVtaVueltaObservaciones.setText("");
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
		if(!txtVtaFacRuc.isReadonly())
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
	private void cleanDatosPago(Event event)throws Exception{
		if( (event!=null && event.getTarget() instanceof Radio) || ltbxVtaAsientosSeleccionados.getItemCount()==0) {
			dbxVtaTotalPagar.setValue(null);
			cmbVtaTipoFormaPago.setSelectedIndex(-1);
			cmbVtaOperadorTarjeta.setSelectedIndex(-1);
			Util.limpiarCombobox(cmbVtaTarjeta);
			cmbVtaTarjeta.setText("");
			txtVtaNumeroOperacion.setText("");
			
			cleanDatosDescuento();
			
			cmbVtaOperadorTarjeta.setDisabled(true);
			cmbVtaTarjeta.setDisabled(true);
			txtVtaNumeroOperacion.setDisabled(true);
			cmbVtaAutorizaDescuento.setDisabled(true);
			txtVtaAutorizaDescuentoCodigo.setDisabled(true);
		}
	}
	
	/**
	 * Limpia los datos del descuento
	 * @throws Exception
	 */
	private void cleanDatosDescuento()throws Exception{
		if(cmbVtaAutorizaDescuento.getItemCount() > 0) {
			cmbVtaAutorizaDescuento.setSelectedIndex(-1);	
		}		
		txtVtaAutorizaDescuentoCodigo.setText("");
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
	private void createEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno,
								 DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, Grid gridOcupabilidad, boolean isMapaPostergacion){
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
								if(objetoBus.getTipoObjeto().intValue() == TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
										@Override
										public void onEvent(Event e){
											Component component = e.getTarget().getParent().getParent().getParent().getParent();
											if(component.getId().equals("grdIdaPiso1") || component.getId().equals("grdIdaPiso2")) {
												if(isMapaPostergacion)
													onClickAsientoMapPost(e, postDetalleItinerario, postMapaAsientos);
												else
													onClickAsiento(e, detalleItinerarioIda, mapaAsientosIda, true);													
											}else
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
				else {
					if(isMapaPostergacion)
						postMapaAsientos = mapaAsientos;
					else
						mapaAsientosIda = mapaAsientos;
				}
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
							
							String tooltiptextAsiento = getTooltiptextAsiento(venta);
							asiento.setTooltiptext(tooltiptextAsiento);
//							asiento.setTooltiptext(venta.getRuta().getOrigen()+"-"+venta.getRuta().getDestino());
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
		rdVtaPostergacionFA.setChecked(false);
		rdVtaCambioNombre.setChecked(false);
		
		rdVtaVenta.setDisabled(true);
		rdVtaReserva.setDisabled(true);
		rdVtaConfirmacionFA.setDisabled(true);
		rdVtaPostergacionFA.setDisabled(true);
		rdVtaCambioNombre.setDisabled(true);

		if(!rdVtaConfirmacionFA.isChecked())
			lblVtaInfoTipoOperacion.setValue("-");
	}
	
	
	/**
	 * Deshabilita todos los Botones con opciones por asiento
	 * @throws Exception
	 */
	private void disabledAllBtnOptionsByAsiento()throws Exception{
		btnVtaContribucionByAgencia.setDisabled(true);
		btnVtaReimpresion.setDisabled(true);
		btnVtaPostergar.setDisabled(true);
		btnVtaAnular.setDisabled(true);
	}
	
	/**
	 * Habilita los botones con opciones por asiento
	 * @throws Exception
	 */
	private void enabledAllBtnOptionsByAsiento()throws Exception{
		habilitarBtnProgramacionBus();
		habilitarBtnManifiestoPasajeros();
		habilitarBtnCarpetaDespacho();
		habilitarBtnReimpresion(null, btnVtaReimpresion);
		habilitarBtnAnulacion();
	}
	
	
	/**
	 * Habiliat/Deshabilita los controles de la información del viaje
	 * @param disabled: (True) deshabilita controles; (false)Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsInfoViaje(boolean disabled)throws Exception{
		cmbVtaIdaEmbarque_hora.setDisabled(disabled);
		cmbVtaIdaDesembarque_hora.setDisabled(disabled);
		dbxVtaIdaTarifa.setDisabled(disabled);
		txtVtaIdaObservaciones.setDisabled(disabled);
		dbxVtaIdaRecargo.setDisabled(disabled);
		if(isVtaIdaVuelta) {
			cmbVtaVueltaEmbarque_hora.setDisabled(disabled);
			cmbVtaVueltaDesembarque_hora.setDisabled(disabled);
			dbxVtaVueltaTarifa.setDisabled(disabled);
			dbxVtaVueltaRecargo.setDisabled(disabled);
			txtVtaVueltaObservaciones.setDisabled(disabled);
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
//		chbxVtaFactura.setDisabled(disabled);
//		txtVtaFacRuc.setReadonly(disabled);
//		txtVtaFacRazonSocial.setReadonly(disabled);
//		txtVtaFacDireccion.setReadonly(disabled);
	}
	
	/**
	 * Habilita/Deshabilita los controles del Cliente
	 * @param disabled: (True) deshabilita controles; (false) Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsCliente(boolean disabled)throws Exception{
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
	}
	
	/**
	 * Habilita/Deshabilita los controles del Descuento
	 * @param disabled: (True) deshabilita controles; (false)Habilita controles
	 * @throws Exception
	 */
	private void disabledControlsDescuento(boolean disabled)throws Exception{
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
			seleccionarPuntoEmbarque(cmbVtaIdaEmbarque_hora, detalleItinerarioIda.getPuntoEmbarque().getAgencia());
			if(cmbVtaIdaEmbarque_hora.getSelectedIndex() <0 )
				cmbVtaIdaEmbarque_hora.setSelectedIndex(0);
			
			if(cmbVtaIdaDesembarque_hora.getItemCount()==2)
				cmbVtaIdaDesembarque_hora.setSelectedIndex(1);
			else
				cmbVtaIdaDesembarque_hora.setSelectedIndex(0);
			
			if(isVtaIdaVuelta) {
				seleccionarPuntoEmbarque(cmbVtaVueltaEmbarque_hora, detalleItinerarioVuelta.getPuntoEmbarque().getAgencia());
				if(cmbVtaVueltaEmbarque_hora.getSelectedIndex() <0 )
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
			Asiento asientoSeleccionado = (Asiento)e.getTarget();
			key = asientoSeleccionado.getKey();
			
			// Valida si esta habilitada la seleccion de asientos disponibles
			if(asientoSeleccionado.getEstadoAsiento().equals(Constantes.ASIENTO_DISPONIBLE)) {
				if(!habilitarSelectAsientoDisponible()) {
					return;	
				}				
			}
			
			//Valida que en una confirmacion de FA no se seleccione mas de un asiento
			if(rdVtaConfirmacionFA.isChecked() && ltbxVtaAsientosSeleccionados.getItemCount()==1) {				
				String _piso = key.split("\\-")[1];
				String _asiento = key.split("\\-")[0];
				
				//Valida si se trata del mismo asiento y piso. ocurre al bloquear y desbloquear un mismo asiento
				List<Listitem> result = ltbxVtaAsientosSeleccionados.getItems().stream()
						.filter(_item -> (((VentaIdaRetorno)_item.getValue()).getDetalleItinerarioIDA().getPiso().equals(_piso) &&
										  ((VentaIdaRetorno)_item.getValue()).getDetalleItinerarioIDA().getAsiento().equals(_asiento)))
						.collect(Collectors.toList());
				
				if(result.size()==0) {
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.confirmaFA.noSelectVariosAsientos"));
					return;
				}				
			}
			
			txtVtaPaxBusqueda.setText("");
			disabled_btnGuardar(btnVtaGuardar, true);			
			resertOperacionAsiento();
			cleanDatosInfoViaje(true, false);
			if(gridVtaInfoViajeVuelta.isVisible())
				cleanDatosInfoViaje(false, false);					
			disabledControlsInfoViaje(true);
			disabledControlsPago(true);
			disabledControlsDescuento(true);
			disabledAllBtnOptionsByAsiento();
			if(!rdVtaConfirmacionFA.isChecked()) {				
				disabledControlsPasajero(true);
				disabledControlsCliente(true);
			}			
//			Asiento asientoSeleccionado = (Asiento)e.getTarget();
//			key = asientoSeleccionado.getKey();			
			/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
			if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario(),ltbxVtaAsientosSeleccionados)) {
				onSelect_ltbxVtaAsientosSeleccionados(e);
				return;
			}
			
			/*	validamos la cantidad maxima de asientos seleccionados	*/
			if(ltbxVtaAsientosSeleccionados.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
				/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/
				if(consultaAsientoBloqueado(key, mapaAsientos)){					
					if(isVtaIdaVuelta || asientoSeleccionado.getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO || rdVtaConfirmacionFA.isChecked())
						DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
					else {
						//Las operaciones con asientos ocupados, solamente están disponibles para el tipo de operación SOLO IDA
						//Obtener la tarifa regular del asiento y almacenarlo en DetalleItinerario del asientoSeleccionado
						List<TarifaRegular> lstTarifaRegular = buscarTarifa(
								asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(),
								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
								asientoSeleccionado.getPiso(),
								asientoSeleccionado.getNumeroZona(),
								asientoSeleccionado.getDetalleItinerario().getItinerario().getEmpresa().getId());
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
								asientoSeleccionado.getNumeroZona(),
								asientoSeleccionado.getDetalleItinerario().getItinerario().getEmpresa().getId());
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
	private List<TarifaRegular> buscarTarifa(Integer servicioId, Integer rutaId, String fechaPartida, String horaPartida, Integer piso, Integer numeroZona, Integer empresaID)throws Exception{
		
		List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(
				Constantes.ID_CANVEN_COUNTER, servicioId, rutaId, fechaPartida, horaPartida, piso, numeroZona, empresaID);
		
		
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
						liberarAsientos(ltbxVtaAsientosSeleccionados);
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
			onSelect_ltbxVtaAsientosSeleccionados(null);
		}
		
		calcularTotalPago();
	}
	
	/**
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String key, Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario, Listbox ltbxAsientosSeleccionados){
		try{
			Long itinerarioId = detalleItinerario.getItinerario().getId();
			
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			List<Listitem> items = ltbxAsientosSeleccionados.getItems();
			for(int i=0; i<items.size(); i++){
				Listitem listitem = items.get(i);
				VentaIdaRetorno venta = listitem.getValue();
				boolean desbloquearAsiento = false;
				
				if(venta.getDetalleItinerarioIDA()!=null && venta.getDetalleItinerarioIDA().getItinerario().getId().longValue()==itinerarioId 
						&& venta.getDetalleItinerarioIDA().getAsiento().equals(buffer[0]) && venta.getDetalleItinerarioIDA().getPiso().equals(buffer[1])) {					
					
					venta.setDetalleItinerarioIDA(null);
					if(venta.getDetalleItinerarioIDA()==null && venta.getDetalleItinerarioRETORNO()==null)
						ltbxAsientosSeleccionados.removeItemAt(i);
					else if(venta.getDetalleItinerarioRETORNO()!=null) {
						((Listcell)listitem.getFirstChild()).setLabel(venta.getDetalleItinerarioRETORNO().getRuta().toString());
						((Listcell)listitem.getLastChild()).setLabel(venta.getDetalleItinerarioRETORNO().getAsiento());
					}
					
					desbloquearAsiento = true;
				}else if(venta.getDetalleItinerarioRETORNO()!=null && venta.getDetalleItinerarioRETORNO().getItinerario().getId().longValue()==itinerarioId 
						&& venta.getDetalleItinerarioRETORNO().getAsiento().equals(buffer[0]) && venta.getDetalleItinerarioRETORNO().getPiso().equals(buffer[1])) {
					
					venta.setDetalleItinerarioRETORNO(null);
					if(venta.getDetalleItinerarioRETORNO()==null && venta.getDetalleItinerarioIDA()==null)
						ltbxAsientosSeleccionados.removeItemAt(i);
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
		if(isVtaIdaVuelta)
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
	public void loadTipoFormaPago(FormaPago formaPago, Combobox cmbTipoFormaPago){
		try{			
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
				cmbTipoFormaPago.appendChild(item);
			}

			if(formaPago.getId().equals(Constantes.ID_FORPAG_CONTADO))
				Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, Constantes.ID_TIPFORPAG_EFECTIVO);				
			
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
	private void liberarAsientos(Listbox ltbxAsientosSeleccionados){
		try{			
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(getUsuarioHardware().getId());			
			ltbxAsientosSeleccionados.getItems().clear();
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
		calcularTotalPago(.00);
	}
	
	/**
	 * Calcula el monto total a pagar
	 * @param recargo : valor del recargo
	 * @throws Exception
	 */
	private void calcularTotalPago(Double recargo)throws Exception{
		Double totalPago = .00;
		if(rdVtaVenta.isChecked() || rdVtaConfirmacionFA.isChecked()) {
			for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
				if(!item.isSelected() || rdVtaConfirmacionFA.isChecked()) {
					VentaIdaRetorno venta = item.getValue();
					if(venta.getDetalleItinerarioIDA()!=null)
						totalPago += (venta.getDetalleItinerarioIDA().getTarifa()!=null?venta.getDetalleItinerarioIDA().getTarifa():.00);			
					if(venta.getDetalleItinerarioRETORNO()!=null)
						totalPago += (venta.getDetalleItinerarioRETORNO().getTarifa()!=null?venta.getDetalleItinerarioRETORNO().getTarifa():.00);	
				}				
			}
		
			if(rdVtaVenta.isChecked()) {
				totalPago += dbxVtaIdaTarifa.getValue();
				if(gridVtaInfoViajeVuelta.isVisible())
					totalPago += dbxVtaVueltaTarifa.getValue();	
			}else if(rdVtaConfirmacionFA.isChecked()){
				Double tarifaVtaFA = ventaPasaje_x.getTarifa();
				Double importePagoVtaFA = ventaPasaje_x.getImportePagado();
				Double importeValida = (importePagoVtaFA >= tarifaVtaFA? importePagoVtaFA: tarifaVtaFA);
				
				if(importeValida < totalPago)
					totalPago += - importeValida;
				else
					totalPago = .00;
			}
			
			// Recargo
			totalPago += recargo;
			
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
		if(isIda) {
			String numeroAsiento = getFormatAsiento(ventaPasaje.getNumeroAsiento());
			
			seleccionarPuntoEmbarque(cmbVtaIdaEmbarque_hora, ventaPasaje.getAgenciaPartida());
			seleccionarPuntoDesembarque(cmbVtaIdaDesembarque_hora, ventaPasaje.getAgenciaLlegada());			
			lblVtaIdaAsiento.setValue(numeroAsiento);
			txtVtaIdaObservaciones.setText(Optional.ofNullable(ventaPasaje.getObservaciones()).orElse(""));
			if(!ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
				dbxVtaIdaTarifa.setValue(ventaPasaje.getImportePagado());
				dbxVtaIdaRecargo.setValue(ventaPasaje.getRecargo());
			}
			    	
		}else {
			String numeroAsiento = getFormatAsiento(ventaPasaje.getNumeroAsiento());
			
			seleccionarPuntoEmbarque(cmbVtaVueltaEmbarque_hora, ventaPasaje.getAgenciaPartida());
			seleccionarPuntoDesembarque(cmbVtaVueltaDesembarque_hora, ventaPasaje.getAgenciaLlegada());
			lblVtaVueltaAsiento.setValue(numeroAsiento);
			txtVtaVueltaObservaciones.setText(Optional.ofNullable(ventaPasaje.getObservaciones()).orElse(""));
			if(!ventaPasaje.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)) {
				dbxVtaVueltaTarifa.setValue(ventaPasaje.getImportePagado());
				dbxVtaVueltaRecargo.setValue(ventaPasaje.getRecargo());
			}
				
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
		else if(txtVtaFacRuc.isVisible() && !txtVtaFacRuc.isReadonly() && txtVtaFacRuc.getText().trim().isEmpty())
			txtVtaFacRuc.setFocus(true);
		else if(gridVtaDatosPago.isVisible() && cmbVtaTipoFormaPago.getSelectedIndex()<0 && !cmbVtaTipoFormaPago.isDisabled()) {			
			cmbVtaTipoFormaPago.setSelectedIndex(0);
			cmbVtaTipoFormaPago.open();
			cmbVtaTipoFormaPago.setFocus(true);						
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
	 * Busca los datos del Cliente
	 * @param patron
	 * @throws Exception
	 */
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
			cmbVtaTipoFormaPago.setSelectedIndex(0);
			cmbVtaTipoFormaPago.open();
			cmbVtaTipoFormaPago.setFocus(true);
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
	 * Carga los datos necesarios para la Postergación a Fecha Abierta
	 * @throws Exception
	 */
	private void loadDatosPostergacionFA()throws Exception{
		disabledAllBtnOptionsByAsiento();
		lblVtaInfoTipoOperacion.setValue(rdVtaPostergacionFA.getLabel());
		disabledControlsPasajero(true);
		disabledControlsCliente(true);
		disabled_btnGuardar(btnVtaGuardar, false);
	}
	
	/**
	 * Carga los datos necesarios para el Cambio de Nombre
	 * @throws Exception
	 */
	private void loadDatosCambioNombre()throws Exception{
		
		disabledAllBtnOptionsByAsiento();
		lblVtaInfoTipoOperacion.setValue(rdVtaCambioNombre.getLabel());
		
		TipoNota tipoNota = ServiceLocator.getTipoNotaManager().buscarPorId(Long.valueOf(Constantes.ID_TIPNOTA_CAMBIO_NOMBRE_PASAJERO));
		if(tipoNota!=null && tipoNota.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
			Double penalidad = tipoNota.getGastoAdminEfectivo();
			int countItem = ltbxVtaAsientosSeleccionados.getItemCount();
			Double totalPago = (penalidad * countItem);
			dbxVtaTotalPagar.setValue(totalPago);			
		}
		if(dbxVtaTotalPagar.getValue() >.00) {
			disabledControlsPago(false);
			disabledControlsDescuento(false);
		}
		disabledControlsPasajero(false);
		cleanDatosPasajero(false);
		disabled_btnGuardar(btnVtaGuardar, false);
		txtVtaPaxBusqueda.setFocus(true);
		
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
	 * Muestra la venta de la Promacion del Bus
	 * @param detalleItinerario
	 * @throws Exception
	 */
	private void showWindowProgramacionBus(DetalleItinerario detalleItinerario)throws Exception{
		Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(detalleItinerario.getItinerario().getId());
		TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("itinerario", itinerario);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);		
		List<ProgramacionServicio> listProgramacion = ServiceLocator.getProgramacionServiciosManager().buscarPorX(criteriosBusqueda, null);
		programacionServicio = null;
		if(listProgramacion.size()>0)
			programacionServicio = listProgramacion.get(0);
		
		//busca el manifiesto
		Manifiesto manifiesto = ServiceLocator.getManifiestoManager().consultaMinifiestImpreso(itinerario.getId());
		
		wndModal = createWindowProgramacionBus(itinerario, manifiesto);
		this.appendChild(wndModal);
		wndModal.setMode("modal");
		
		if(manifiesto != null && isEnabledVenta)
			DlgMessage.information("El Manifiesto ya está emitido, no podrá realizar cambios en la Programación.");
	}
	
	/**
	 * Crea una venta para mostrar la lista de pasajeros
	 * @param listPasajero: Instancia con la lista de pasajeros
	 * @throws Exception
	 */
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
			listhead.appendChild(new Listheader("NOMBRES/RAZON SOCIAL"));
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
			Button btnAceptar = new Button();
			btnAceptar.setLabel("Aceptar");
			btnAceptar.setImage("resources/mp_aceptarEnabled.png");			
			btnAceptar.setSclass("btnCommandL");
			hbox.appendChild(btnAceptar);
			
			Button btnCancelar = new Button();
			btnCancelar.setLabel("Cancelar");
			btnCancelar.setSclass("btnCommandL");
			btnCancelar.setImage("resources/mp_cerrar.png");
			btnCancelar.setStyle("margin-left:15px");
			hbox.appendChild(btnCancelar);
			
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
		if(!cmbVtaIdaEmbarque_hora.isDisabled() && cmbVtaIdaEmbarque_hora.getSelectedIndex()==0) {
			cmbVtaIdaEmbarque_hora.setFocus(true);
		}else if(!cmbVtaIdaDesembarque_hora.isDisabled() && cmbVtaIdaDesembarque_hora.getSelectedIndex()==0) {
			cmbVtaIdaDesembarque_hora.setFocus(true);
		}else if(!txtVtaIdaObservaciones.isDisabled() && txtVtaIdaObservaciones.getText().trim().isEmpty() && event != null && 
				!event.getTarget().getId().equals(txtVtaIdaObservaciones.getId()) && 
				!event.getTarget().getId().equals(dbxVtaIdaRecargo.getId()) ) {
			txtVtaIdaObservaciones.setFocus(true);
		}else if(gridVtaInfoViajeVuelta.isVisible() && !cmbVtaVueltaEmbarque_hora.isDisabled() && cmbVtaVueltaEmbarque_hora.getSelectedIndex()==0) {
			cmbVtaVueltaEmbarque_hora.setFocus(true);
		}else if(gridVtaInfoViajeVuelta.isVisible() && !cmbVtaVueltaDesembarque_hora.isDisabled() && cmbVtaVueltaDesembarque_hora.getSelectedIndex()==0) {
			cmbVtaVueltaDesembarque_hora.setFocus(true);
		}else if(gridVtaInfoViajeVuelta.isVisible() && !txtVtaVueltaObservaciones.isDisabled() && 
				txtVtaVueltaObservaciones.getText().trim().isEmpty() && event != null && 
				!event.getTarget().getId().equals(txtVtaVueltaObservaciones.getId()) &&
				!event.getTarget().getId().equals(dbxVtaVueltaRecargo.getId())) {
			txtVtaVueltaObservaciones.setFocus(true);
		}else if(event!=null && event.getTarget().getId().equals(rdVtaVenta.getId())) {				
			if(!txtVtaPaxNumeroDocumento.isDisabled() && txtVtaPaxNumeroDocumento.getText().trim().isEmpty()) {
				txtVtaPaxBusqueda.setFocus(true);
			}else if(!cmbVtaTipoFormaPago.isDisabled() && cmbVtaTipoFormaPago.getSelectedIndex()<0) {
				cmbVtaTipoFormaPago.open();
				cmbVtaTipoFormaPago.setFocus(true);				
			}else
				btnVtaGuardar.setFocus(true);
		}else if(event!=null && event.getTarget().getId().equals(rdVtaReserva.getId())) {
			if(txtVtaPaxNumeroDocumento.getText().trim().isEmpty())
				txtVtaPaxBusqueda.setFocus(true);
			else if(!itbxVtaIdaTiempoReserva.isDisabled()) {
				itbxVtaIdaTiempoReserva.setFocus(true);
				itbxVtaIdaTiempoReserva.select();
			}
		}else if(rdVtaConfirmacionFA.isChecked()) {
			if(gridVtaInfoViajeVuelta.isVisible()) 
		  		cmbVtaVueltaEmbarque_hora.setFocus(true);
		  	else if(!txtVtaPaxBusqueda.isReadonly()) 
		  		txtVtaPaxBusqueda.setFocus(true); 
		  	else if(!cmbVtaTipoFormaPago.isDisabled() && cmbVtaTipoFormaPago.getSelectedIndex()<0) {
		  		cmbVtaTipoFormaPago.setSelectedIndex(0);
		  		cmbVtaTipoFormaPago.open();
		  	}else
		  		btnVtaGuardar.setFocus(true);		
		}else if(pasajero==null)
			txtVtaPaxBusqueda.setFocus(true);		
		else
			btnVtaGuardar.setFocus(true);
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
	 * Habilita la Opción de Postergación 
	 * @throws Exception
	 */
	private void habilitarOpcionPostergacion(VentaPasaje ventaPasajeEdicion)throws Exception{
		if(isEnabledVenta) {
			Integer minutos = UtilFlag.getTiempoPostergacion();
			if(minutos !=null) {
				boolean permiteEdicion = validarEdicionAsiento(ventaPasajeEdicion, minutos);
				if(permiteEdicion)
					btnVtaPostergar.setDisabled(false);			
			}
		}			
	}

	/**
	 * Habilita la Opción de Postergación F.A. 
	 * @throws Exception
	 */
	private void habilitarOpcionPostergacionFA(VentaPasaje ventaPasajeEdicion)throws Exception{
		Integer minutos = UtilFlag.getTiempoPostergacionFA();
		if(minutos !=null) {
			boolean permiteEdicion = validarEdicionAsiento(ventaPasajeEdicion, minutos);
			if(permiteEdicion)
					rdVtaPostergacionFA.setDisabled(false);
		}		
	}
	
	/**
	 * Habilita la Opción Cambio de Nombre 
	 * @throws Exception
	 */
	private void habilitarOpcionCambioNombre(VentaPasaje ventaPasajeEdicion)throws Exception{
		Integer minutos = UtilFlag.getTiempoCambioNombre();
		if(minutos !=null) {
			boolean permiteEdicion = validarEdicionAsiento(ventaPasajeEdicion, minutos);
			if(permiteEdicion)
				rdVtaCambioNombre.setDisabled(false);
		}		
	}
	
	/**
	 * Determina el estado del Check de Actualización de Mapa automatico.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarUpdateMapaAutomaticamente()throws Exception{
		chbxVtaUpdateMapAuto.setChecked(false);
		chbxVtaUpdateMapAuto.setDisabled(true);
		
		if(isEnabledVenta) {			
			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
			Date fechaSalida = detalleItinerarioIda.getFechaPartida();		
			if(fechaSalida.getTime() >= fechaActual.getTime()) {
				chbxVtaUpdateMapAuto.setChecked(true);
				onCheck_chbxVtaUpdateMapAuto();	
				chbxVtaUpdateMapAuto.setDisabled(false);
			}
		}		
	}
	
	/**
	 * Determina el estado de la selección de un asiento vendido
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarSelectAsientoVendido()throws Exception{
				
	}
	
	/**
	 * Determina el estado de la selección de un asiento disponible
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private boolean habilitarSelectAsientoDisponible()throws Exception{
		return isEnabledVenta;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void habilitarLinkProgramacionBus(A linkProgramacionBus, DetalleItinerario detalleItinerario)throws Exception{
		int diasPermiteProgramarBus = 1;
		Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
		Date fechaPartida = detalleItinerario.getFechaPartida();
		Long diasDif = (fechaPartida.getTime() - fechaActual.getTime());
		diasDif = (diasDif==0? 0 :diasDif / Constantes.MILISEGUNDOS_X_DIA);
		if(diasDif <= diasPermiteProgramarBus)		
			linkProgramacionBus.setDisabled(false);
		else
			linkProgramacionBus.setDisabled(true);
	}
	
	/**
	 * Determina el estado del button Programación Bus.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnProgramacionBus()throws Exception{		
		btnVtaProgramarServicios.setDisabled(isVtaIdaVuelta);
		
	}
	
	/**
	 * Determina el estado del button Manifiesto de Pasajeros.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnManifiestoPasajeros()throws Exception{
		btnVtaManifiestoPasajetos.setDisabled(isVtaIdaVuelta);		
		
	}
	
	/**
	 * Determina el estado del button Carpeta de Despacho.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnCarpetaDespacho()throws Exception{
		btnVtaCarpetaDespacho.setDisabled(isVtaIdaVuelta);
	
	}
	
	/**
	 * Determina el estado del button Reimpresión.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnReimpresion(VentaPasaje ventaPasaje, Button btnReimpresion)throws Exception{
		btnReimpresion.setDisabled(true);
		if(isEnabledVenta || btnReimpresion.getId().equals("btnHpReimprimir")) {
			if(ventaPasaje!=null && ventaPasaje.getNumeroBoleto()!=null && !ventaPasaje.getNumeroBoleto().trim().isEmpty()){
				if(ventaPasaje.getFechaPartida()!=null) {
					Date fechaPartida = ventaPasaje.getFechaPartida();
					Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
					if(fechaPartida.getTime() >= fechaActual.getTime())
						btnReimpresion.setDisabled(false);	
				}else //son los F.A.
					btnReimpresion.setDisabled(false);			
			}	
		}		
	}
	
	/**
	 * Determina el estado del button Anulación.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnAnulacion()throws Exception{
		btnVtaAnular.setDisabled(true);
		if(isEnabledVenta) {
			btnVtaAnular.setDisabled(false);
		}
	}
	
	/**
	 * Determina el estado del button Anulación.
	 * @return (true) Desabilita, (false) habilita
	 * @throws Exception
	 */
	private void habilitarBtnActualizarMapa()throws Exception{
		btnVtaActualizarMapa.setDisabled(true);
		if(isEnabledVenta) {
			btnVtaActualizarMapa.setDisabled(false);
		}
	}
	
	/**
	 * Valida si el asiento puede ser editado
	 * @param ventaPasajeEdicion: Instancia a la cual se va ha validar
	 * @param minutosEdicion: Representa el tiempo en minutos antes de la salida del servicio, en que el asiento puede podría ser modificado.
	 * @throws Exception
	 */
	private boolean validarEdicionAsiento(VentaPasaje ventaPasajeEdicion, int minutosEdicion)throws Exception{
		boolean permiteEdicion = false;
		if(isEnabledVenta) {
			if(ventaPasajeEdicion.getFechaPartida()!=null && ventaPasajeEdicion.getHoraPartida()!=null) {
				String fechaPartida = Constantes.FORMAT_DATE.format(ventaPasajeEdicion.getFechaPartida());
//				String horaPartida = ventaPasajeEdicion.getHoraPartida();
				String horaPartida = UtilData.obtenerHoraEmbarque(detalleItinerarioIda.getItinerario().getId(), ventaPasajeEdicion.getAgenciaPartida().getId());
				Date fechaHoraPartida = Util.convertirLocalDateTimeToDate(convertirStringToLocalDateTime(fechaPartida+" "+horaPartida));				
				Date fechaHoraPostergacion = Util.convertirLocalDateTimeToDate(LocalDateTime.now().plusMinutes(minutosEdicion));
				
				if(fechaHoraPartida.getTime() > fechaHoraPostergacion.getTime())
					permiteEdicion = true;
			}	
		}		
		return permiteEdicion;
	}
	
	/**
	 * Selecciona el punto de embarque
	 * @param isIda
	 * @param agencia
	 * @throws Exception
	 */
	private void seleccionarPuntoEmbarque(Combobox comboboxEmbarque, Agencia agencia)throws Exception{		
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
	private void seleccionarPuntoDesembarque(Combobox comboboxDesembarque, Agencia agencia)throws Exception{
		for(Comboitem comboitem: comboboxDesembarque.getItems()) {
			if(comboitem.getValue()!=null && comboitem.getValue() instanceof ItinerarioAgenciaLlegada) {
				ItinerarioAgenciaLlegada itiAgePartida = comboitem.getValue();
				if(itiAgePartida.getAgencia().getId().intValue()==agencia.getId().intValue()) {
					comboboxDesembarque.setSelectedItem(comboitem);
					break;
				}
			}				
		}
	}
	
	/**
	 * Aplica código de descuento a la venta 
	 * @throws Exception
	 */
	private Descuento validarCodigoDescuento(Combobox cmbVtaAutorizaDescuento, Textbox txtVtaAutorizaDescuentoCodigo, boolean isIdaVuelta)throws Exception{
		Descuento descuento = null;
		
		AutorizadorDescuento autDescuento = cmbVtaAutorizaDescuento.getSelectedItem().getValue();
		String codigoDescuento = txtVtaAutorizaDescuentoCodigo.getText().trim().toUpperCase();
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("codigo", codigoDescuento);
		criteriosBusqueda.put("autorizadorDescuento", autDescuento);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Descuento> listDescuento = ServiceLocator.getDescuentoManager().buscarPorX(criteriosBusqueda, null);
		if(listDescuento.size()>0) {
			Descuento _descuento = listDescuento.get(0);
			boolean isInicioVigencia = Util.comparaFechas(_descuento.getFechaInicio(), new Date(), Util.OPER_MENOR_IGUAL);
			boolean isFinVigencia = Util.comparaFechas(new Date(),_descuento.getFechaFin(), Util.OPER_MAYOR);
			if(isInicioVigencia && !isFinVigencia)
				descuento = listDescuento.get(0);
			else
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.noExistCodigo")
						.replace("@codigo", codigoDescuento), txtVtaAutorizaDescuentoCodigo);				
		}else
			DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.noExistCodigo")
					.replace("@codigo", codigoDescuento), txtVtaAutorizaDescuentoCodigo);
		
		return descuento;
	}
	
	/**
	 * Aplica el descuento a la venta
	 * @param descuento: Instancia de la Class Descuento
	 * @param isVentaIdaVuelta: (true) indica que es una venta de tipo Ida y Vuelta, (false)venta de solo Ida
	 * @param valorAplica: Valor que va a aplicar como descuento/tarifa
	 * @throws Exception
	 */
	private void aplicarCodigoDescuento(Descuento descuento, boolean isVentaIdaVuelta, Double valorAplica,  boolean showMessageDescuento)throws Exception{
		String tipoDescuento = descuento.getTipoDescuento();
		
		if(tipoDescuento.equals(Constantes.TIPO_DESCUENTO_TARIFA)) {
			dbxVtaIdaTarifa.setValue(valorAplica);
			if(descuento.getIdaVuelta().intValue()==Constantes.TRUE_VALUE && isVentaIdaVuelta) {
				dbxVtaVueltaTarifa.setValue(valorAplica);
				if(showMessageDescuento)
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.replazaTarifaIdaVuelta")
															.replace("@codigo", descuento.getCodigo())
															.replace("@valor", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
			}else if(showMessageDescuento)
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.replazaTarifa")
														.replace("@codigo", descuento.getCodigo())
														.replace("@valor", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
		}else if(tipoDescuento.equals(Constantes.TIPO_DESCUENTO_SOLES)) {
			Double newTarifaIda = dbxVtaIdaTarifa.getValue() - valorAplica;
			dbxVtaIdaTarifa.setValue(newTarifaIda);
			if(descuento.getIdaVuelta().intValue()==Constantes.TRUE_VALUE && isVentaIdaVuelta) {
				Double newTarifaVuleta = dbxVtaVueltaTarifa.getValue() - valorAplica;
				dbxVtaVueltaTarifa.setValue(newTarifaVuleta);
				if(showMessageDescuento)
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.solesIdaVuelta")
															.replace("@descuento", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
			}else if(showMessageDescuento)
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.soles")
														.replace("@descuento", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
		}else if(tipoDescuento.equals(Constantes.TIPO_DESCUENTO_PORCENTAJE)) {
			Double newTarifaIda = (dbxVtaIdaTarifa.getValue() * (valorAplica/100)) - (valorAplica/100);
			dbxVtaIdaTarifa.setValue(newTarifaIda);
			if(descuento.getIdaVuelta().intValue()==Constantes.TRUE_VALUE && isVentaIdaVuelta) {
				Double newTarifaVuleta = (dbxVtaVueltaTarifa.getValue() * (valorAplica/100)) - (valorAplica/100);
				dbxVtaVueltaTarifa.setValue(newTarifaVuleta);
				if(showMessageDescuento)
					DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.porcentajeIdaVuelta")
															.replace("@porcentaje", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
			}else if(showMessageDescuento)
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuento.porcentaje")
														.replace("@porcentaje", Util.toNumberFormat(valorAplica, 2)), btnVtaGuardar);
		}
		
		dbxVtaIdaRecargo.setDisabled(true);
		if(isVentaIdaVuelta) {
			dbxVtaVueltaRecargo.setDisabled(true);	
		}
		
		calcularTotalPago();
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
			public void onEvent(Event e) throws Exception{
				if(txtMotivoAnulacion.getText().trim().isEmpty()){
					DlgMessage.information(Messages.getString("WndLiquidacionDiariaVentas.information.noMotivoAnulacion"),txtMotivoAnulacion);
					return;
				}
				
				/************** Tracking (GPS) **********************/
				String motivoMovimiento = "ANULACION VTA";
				motivoMovimiento += " ("+ txtMotivoAnulacion.getText().trim().toUpperCase() +")";
				MovimientoPasajes movimientoPasajes = UtilData.createTracking(ventaOriginal, motivoMovimiento);
				ventaOriginal.setMovimientoPasajes(movimientoPasajes);
				/****************************************************/
				
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
			onSelect_ltbxVtaAsientosSeleccionados(null);														
			onRefreshMap();
			disabledControlsInfoViaje(true);
			disabledControlsPasajero(true);
			disabledControlsCliente(true);
			disabledControlsPago(true);
			disabledControlsDescuento(true);
			if(ltbxVtaAsientosSeleccionados.getItemCount()==0)
				disabledAllBtnOptionsByAsiento();
		}
	}
	
	@SuppressWarnings("deprecation")
	private Window createWindowPostergacion(final VentaPasaje ventaOriginal, Double montoPenalidad)throws Exception{
		ltbxPostAsientoSeleccionado = new Listbox();
		
		final Window win = new Window("", "normal", true);
		win.setWidth("950px");

		Caption caption = new Caption("POSTERGACION", "resources/buttons/mp_postergacion.png");
		win.appendChild(caption);
		
		Hbox hbox = new Hbox();
		
		Grid grid = new Grid();
		grid.setWidth("170px");
		Columns columns = new Columns();
		columns.appendChild(new Column(null, null, "50px"));
		columns.appendChild(new Column());
		grid.appendChild(columns);
		Rows rows = new Rows();
		Row row =new Row();
		row.appendChild(new Label("EMPRESA"));
		cmbPostEmpresa = new Combobox();
		cmbPostEmpresa.setDisabled(true);
		cmbPostEmpresa.setWidth("100%");
		row.appendChild(cmbPostEmpresa);
		rows.appendChild(row);
		row =new Row();
		row.appendChild(new Label("ORIGEN"));
		cmbPostOrigen = new Combobox();
		cmbPostOrigen.setReadonly(true);
		cmbPostOrigen.setWidth("100%");		
		row.appendChild(cmbPostOrigen);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("DESTINO"));
		cmbPostDestino = new Combobox();
		cmbPostDestino.setReadonly(true);
		cmbPostDestino.setWidth("100%");		
		row.appendChild(cmbPostDestino);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("FECHA"));
		dtbxPostFecha = new Datebox(new Date(new Date().getTime()+Constantes.MILISEGUNDOS_X_DIA));
		dtbxPostFecha.setConstraint("no past");
		dtbxPostFecha.setFormat("dd/MM/yyyy");
		dtbxPostFecha.setReadonly(true);
		dtbxPostFecha.setWidth("100%");
		row.appendChild(dtbxPostFecha);
		rows.appendChild(row);
		
		row = new Row();
		row.setSpans("2");
		Button btnBuscar = new Button("Buscar");
		btnBuscar.setWidth("120px");
		btnBuscar.setHeight("34px");
		btnBuscar.setImage("/resources/mp_buscarEnabled.png");
		btnBuscar.setStyle("margin-left:15px;");
		btnBuscar.setSclass("btnCommandM");
		row.appendChild(btnBuscar);
		rows.appendChild(row);
		grid.appendChild(rows);
		
		ltbxPostItienerarios = new Listbox();
		ltbxPostItienerarios.setHeight("147px");
		Listhead listhead = new Listhead();
		listhead.appendChild(new Listheader("EMPRESA", null, "130px"));
		listhead.appendChild(new Listheader("RUTA", null, "140px"));
		listhead.appendChild(new Listheader("EMBARQUE", null, "120px"));
		listhead.appendChild(new Listheader("H.SALIDA", null, "60px"));
		listhead.appendChild(new Listheader("SERVICIO", null, "100px"));
		listhead.appendChild(new Listheader("TARIFA", null, "90px"));
		listhead.appendChild(new Listheader(null, null, "90px"));
		ltbxPostItienerarios.appendChild(listhead);
		
		hbox.appendChild(grid);
		hbox.appendChild(ltbxPostItienerarios);
		
		win.appendChild(hbox);
		
		
		hboxPost_1 = new Hbox();
		hboxPost_1.setStyle("margin-top:2px");
		hboxPost_1.setVisible(false);
		gpbxPostMapa = new Groupbox();
		gpbxPostMapa.setClosable(false);
		gpbxPostMapa.setMold("3d");
		gpbxPostMapa.setWidth("710px");
		gpbxPostMapa.setHeight("209px");
		caption = new Caption("MAPA DE ASIENTOS");
		caption.setStyle("color: #ffffff;");
		gpbxPostMapa.appendChild(caption);
		
		Groupbox gpbxPostInfoViaje = new Groupbox();		
		gpbxPostInfoViaje.setMold("3d");
		gpbxPostInfoViaje.setClosable(false);
		gpbxPostInfoViaje.setWidth("220px");
		caption = new Caption("DATOS DE LA POSTERGACION");
		caption.setStyle("color: #ffffff;");
		gpbxPostInfoViaje.appendChild(caption);
		grid = new Grid();
		grid.setStyle("border:none");
		columns = new Columns();
		columns.appendChild(new Column(null, null, "65px"));
		columns.appendChild(new Column());
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		row.setSpans("2");
		Div div = new Div();
		div.setStyle("text-align:center");
		lblPostRuta_fecha = new Label("-");
		lblPostRuta_fecha.setSclass("label-size11-bold");
		lblPostRuta_fecha.setStyle("color:red");
		div.appendChild(lblPostRuta_fecha);
		row.appendChild(div);		
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("EMBARQUE "));
		cmbPostEmbarque_hora = new Combobox();
		cmbPostEmbarque_hora.setReadonly(true);
		cmbPostEmbarque_hora.setWidth("140px");
		row.appendChild(cmbPostEmbarque_hora);		
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("DESEMBAR "));
		cmbPostDesembarque_hora = new Combobox();
		cmbPostDesembarque_hora.setReadonly(true);
		cmbPostDesembarque_hora.setWidth("140px");
		row.appendChild(cmbPostDesembarque_hora);		
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("ASIENTO "));
		lblPostAsiento = new Label("-");
		lblPostAsiento.setSclass("label-size11-bold");
		lblPostAsiento.setStyle("font-size:15px !important;color:blue");
		row.appendChild(lblPostAsiento);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("TARIFA ACTUAL"));
		dbxPostTarifa = new Doublebox(.00);
		dbxPostTarifa.setSclass("label-size11-bold");
		dbxPostTarifa.setStyle("font-size:12px !important;");
		dbxPostTarifa.setFormat("#,##0.00");
		dbxPostTarifa.setLocale(Locale.US);
		dbxPostTarifa.setWidth("50%");
		dbxPostTarifa.setReadonly(true);
		row.appendChild(dbxPostTarifa);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("TARIFA ANTERIOR "));
		
		dbxPostMontoAnterior = new Doublebox(ventaOriginal.getTarifa());
		dbxPostMontoAnterior.setSclass("label-size11-bold");
		dbxPostMontoAnterior.setStyle("font-size:12px !important;");
		dbxPostMontoAnterior.setFormat("#,##0.00");
		dbxPostMontoAnterior.setWidth("50%");
		dbxPostMontoAnterior.setReadonly(true);
		dbxPostMontoAnterior.setLocale(Locale.US);
		row.appendChild(dbxPostMontoAnterior);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("PENALIDAD "));
		dbxPostPenalidad = new Doublebox(montoPenalidad);
		dbxPostPenalidad.setSclass("label-size11-bold");
		dbxPostPenalidad.setStyle("font-size:12px !important;");
		dbxPostPenalidad.setFormat("#,##0.00");
		dbxPostPenalidad.setWidth("50%");
		dbxPostPenalidad.setLocale(Locale.US);
		dbxPostPenalidad.setReadonly(true);
		row.appendChild(dbxPostPenalidad);
		rows.appendChild(row);
		grid.appendChild(rows);		
		gpbxPostInfoViaje.appendChild(grid);
		
		hboxPost_1.appendChild(gpbxPostMapa);
		hboxPost_1.appendChild(gpbxPostInfoViaje);
		win.appendChild(hboxPost_1);
		
		hboxPost_2 = new Hbox();
		hboxPost_2.setStyle("margin-top:2px");
		hboxPost_2.setVisible(false);
		Groupbox gpbxPostInfoComprobante = new Groupbox();
		gpbxPostInfoComprobante.setMold("3d");
		gpbxPostInfoComprobante.setWidth("630px");
		caption = new Caption("DATOS DEL COMPROBANTE A POSTERGAR");
		caption.setStyle("color: #ffffff;");
		gpbxPostInfoComprobante.appendChild(caption);
		grid = new Grid();
		grid.setStyle("border:none");
		columns = new Columns();
		Column column = new Column(null, null, "110px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column(null, null, "150px"));
		column = new Column(null, null, "110px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column());
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		row.setSpans("1,3");
		row.appendChild(new Label("EMPRESA :"));	
		Label label = new Label(ventaOriginal.getEmpresa().getNombreCorto());
		label.setSclass("label-size11-bold");
		label.setStyle("color:red");
		row.appendChild(label);		
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("No COMPROBANTE :"));
		label = new Label(ventaOriginal.getNumeroBoleto());
		label.setStyle("font-size:12px !important");
		row.appendChild(label);
		row.appendChild(new Label("RUTA :"));
		row.appendChild(new Label(ventaOriginal.getRuta().toString()));		
		rows.appendChild(row);		
		row = new Row();
		row.appendChild(new Label("FECHA/HORA VIAJE :"));
		label = new Label(Constantes.FORMAT_DATE.format(ventaOriginal.getFechaPartida()) +" "+ventaOriginal.getHoraPartida());
		label.setStyle("font-size:12px !important");
		row.appendChild(label);
		row.appendChild(new Label("PTO. EMBARQUE :"));
		row.appendChild(new Label(ventaOriginal.getAgenciaPartida().getDenominacion()));		
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("ASIENTO :"));
		label = new Label(ventaOriginal.getNumeroAsiento().toString());
		label.setStyle("font-size:12px !important");
		row.appendChild(label);
		row.appendChild(new Label("IMPORTE PAGADO :"));
		label = new Label(Util.toNumberFormat(ventaOriginal.getImportePagado(), 2));
		label.setStyle("font-size:12px !important");
		row.appendChild(label);
		rows.appendChild(row);		
		row = new Row();
		row.setSpans("1,3");
		row.appendChild(new Label("PASAJERO :"));
		row.appendChild(new Label(ventaOriginal.getPasajero().toString()));
		rows.appendChild(row);
		row = new Row();
		row.setSpans("4");
		Separator separator = new Separator();
		separator.setHeight("25px");
		row.appendChild(separator);
		rows.appendChild(row);
		grid.appendChild(rows);
		gpbxPostInfoComprobante.appendChild(grid);
		hboxPost_2.appendChild(gpbxPostInfoComprobante);
		
		Groupbox gpbxPostInfoPago = new Groupbox();
		gpbxPostInfoPago.setMold("3d");
		gpbxPostInfoPago.setWidth("301px");
		caption = new Caption("DATOS DEL PAGO");
		caption.setStyle("color: #ffffff;");
		gpbxPostInfoPago.appendChild(caption);
		grid = new Grid();
		grid.setStyle("border:none");
		columns = new Columns();
		columns.appendChild(new Column());
		columns.appendChild(new Column());
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		row.setSpans("2");
		hbox = new Hbox();
		hbox.setAlign("center");
		label = new Label("TOTAL A PAGAR");
		label.setSclass("label-size11-bold");
		label.setStyle("color:blue");
		dbxPostTotalPagar = new Doublebox(.00);
		dbxPostTotalPagar.setStyle("font-size:15px !important;text-align:center;margin-left:54px");
		dbxPostTotalPagar.setSclass("label-size12-bold-alert");
		dbxPostTotalPagar.setFormat("#,##0.00");
		dbxPostTotalPagar.setWidth("120px");
		dbxPostTotalPagar.setLocale(Locale.US);
		dbxPostTotalPagar.setReadonly(true);
		hbox.appendChild(label);
		hbox.appendChild(dbxPostTotalPagar);
		row.appendChild(hbox);
		rows.appendChild(row);
		row = new Row();
		cmbPostTipoFormaPago = new Combobox();
		cmbPostTipoFormaPago.setReadonly(true);
		cmbPostTipoFormaPago.setPlaceholder("MEDIO DE PAGO");
		cmbPostTipoFormaPago.setWidth("120px");
		cmbPostTipoFormaPago.setDisabled(true);
		row.appendChild(cmbPostTipoFormaPago);
		cmbPostOperadorTarjeta = new Combobox();
		cmbPostOperadorTarjeta.setReadonly(true);
		cmbPostOperadorTarjeta.setWidth("130px");
		cmbPostOperadorTarjeta.setPlaceholder("OPERADOR TARJETA");
		cmbPostOperadorTarjeta.setDisabled(true);
		row.appendChild(cmbPostOperadorTarjeta);
		rows.appendChild(row);	
		row = new Row();
		cmbPostTarjeta = new Combobox();
		cmbPostTarjeta.setReadonly(true);
		cmbPostTarjeta.setPlaceholder("TARJETA");
		cmbPostTarjeta.setWidth("120px");
		cmbPostTarjeta.setDisabled(true);
		row.appendChild(cmbPostTarjeta);
		txtPostNumeroOperacion = new Textbox();
		txtPostNumeroOperacion.setPlaceholder("NUMERO OPERACION");
		txtPostNumeroOperacion.setWidth("120px");
		txtPostNumeroOperacion.setDisabled(true);
		row.appendChild(txtPostNumeroOperacion);
		rows.appendChild(row);
		grid.appendChild(rows);		
		gpbxPostInfoPago.appendChild(grid);
		
		txtPostObservaciones = new Textbox();
		txtPostObservaciones.setPlaceholder("OBSERVACIONES");
		txtPostObservaciones.setWidth("290px");
		txtPostObservaciones.setDisabled(true);		
		
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
		Button btnPostGuardar = new Button("Postergar");
		btnPostGuardar.setImage("resources/mp_guardarEnabled.png");
		btnPostGuardar.setSclass("btnCommandL");
		Button btnPostCancelar = new Button("Cancelar");
		btnPostCancelar.setImage("resources/mp_cancelarEnabled.png");
		btnPostCancelar.setSclass("btnCommandL");
		row.appendChild(btnPostGuardar);
		row.appendChild(btnPostCancelar);
		rows.appendChild(row);
		grid.appendChild(rows);
		Vbox vbox = new Vbox();
		vbox.setAlign("center");
						
		vbox.appendChild(gpbxPostInfoPago);
		vbox.appendChild(txtPostObservaciones);
		vbox.appendChild(grid);
		hboxPost_2.appendChild(vbox);		
		win.appendChild(hboxPost_2);
		
		// Carga datos de la empresa
		UtilData.cargarEmpresa(cmbPostEmpresa, false);
		
		// Carga las localidades para el Origen
		UtilData.cargarDataCombo(cmbPostOrigen, Localidad.class, false);
		
		// Carga las localidades para el destino
		UtilData.cargarDataCombo(cmbPostDestino, Localidad.class, false);
		
		// Selecciona la empresa
		Util.seleccionarValorItemCombo(Empresa.class, cmbPostEmpresa, ventaOriginal.getEmpresa().getId());
		
		// Selecciona la localida origen en funcion a la venta 
		Util.seleccionarValorItemCombo(Localidad.class, cmbPostOrigen, ventaOriginal.getRuta().getLocalidadOrigen().getId());
		
		// Selecciona la localida destino en funcion a la venta 
		Util.seleccionarValorItemCombo(Localidad.class, cmbPostDestino, ventaOriginal.getRuta().getLocalidadDestino().getId());
		
		// Carga los tipo de formas de pago
		loadTipoFormaPago(new FormaPago(Constantes.ID_FORPAG_CONTADO), cmbPostTipoFormaPago);
		
		// Limpia controles de la prostergacion
		cleanDatosPagoPost();
		
		//Carlos itinerarios por defecto
//		onClick_btnBusarItinerarioByPost(ventaOriginal);
		
		cmbPostOrigen.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cleanBusquedaItinerariosPost();
			}
		});
		cmbPostDestino.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cleanBusquedaItinerariosPost();
			}
		});
		dtbxPostFecha.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cleanBusquedaItinerariosPost();
			}
		});
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					onClick_btnBusarItinerarioByPost(ventaOriginal);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		cmbPostTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					cmbPostOperadorTarjeta.getItems().clear();
					cmbPostOperadorTarjeta.setSelectedIndex(-1);
					cmbPostOperadorTarjeta.setDisabled(true);
					cmbPostTarjeta.getItems().clear();
					cmbPostTarjeta.setText("");
					cmbPostTarjeta.setDisabled(true);
					txtPostNumeroOperacion.setText("");

					if(cmbPostTipoFormaPago.getSelectedIndex()>=0){
						TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbPostTipoFormaPago.getSelectedItem().getValue();
						if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CREDITO){
							cmbPostOperadorTarjeta.getItems().clear();
							UtilData.cargarDataCombo(cmbPostOperadorTarjeta, OperadorTarjetaCredito.class, false);
							cmbPostOperadorTarjeta.setDisabled(false);
							txtPostNumeroOperacion.setDisabled(false);
//							cmbPostOperadorTarjeta.setFocus(true);
						}else if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TRANSFERENCIA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_YAPE) {
							txtPostNumeroOperacion.setDisabled(false);
//							txtVtaNumeroOperacion.setFocus(true);
						}else{
							txtPostNumeroOperacion.setDisabled(true);
							cmbPostOperadorTarjeta.setDisabled(true);
//							btnVtaGuardar.setFocus(true);
						}
					}else{
						txtPostNumeroOperacion.setDisabled(true);
						cmbPostOperadorTarjeta.setDisabled(true);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					log.error(e);
				}
			}
		});
		cmbPostOperadorTarjeta.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					cmbPostTarjeta.getItems().clear();
					cmbPostTarjeta.setText("");
					cmbPostTarjeta.setDisabled(true);

					if(cmbPostOperadorTarjeta.getSelectedItem().getValue() instanceof OperadorTarjetaCredito){
						OperadorTarjetaCredito operadorTarjetaCredito = cmbPostOperadorTarjeta.getSelectedItem().getValue();
						TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
						criteriosBusqueda.put("operadorTarjetaCredito.id", operadorTarjetaCredito.getId());
						List<String> criteriosOrdenar = new ArrayList<>();
						criteriosOrdenar.add("denominacion");
						List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
						UtilData.cargarGenericData(cmbPostTarjeta, false);
						for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
							if(tarjetaCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)) {
								Comboitem item = new Comboitem();
								item.setLabel(tarjetaCredito.getDenominacion());
								item.setValue(tarjetaCredito);
								cmbPostTarjeta.appendChild(item);
							}
						}
						cmbPostTarjeta.setDisabled(false);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					log.error(e);
				}
			}
		});
		cmbPostTipoFormaPago.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if(!cmbPostOperadorTarjeta.isDisabled())
					cmbPostOperadorTarjeta.setFocus(true);
				else if(!txtPostNumeroOperacion.isDisabled())
					txtPostNumeroOperacion.setFocus(true);
				else if(!txtPostObservaciones.isDisabled())
					txtPostObservaciones.setFocus(true);
				else
					btnPostGuardar.setFocus(true);
			}
		});
		cmbPostOperadorTarjeta.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbPostTarjeta.setFocus(true);
			}
		});
		cmbPostTarjeta.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				txtPostNumeroOperacion.setFocus(true);
			}
		});
		txtPostNumeroOperacion.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!txtPostObservaciones.isDisabled())
					txtPostObservaciones.setFocus(true);
				else					
					btnPostGuardar.setFocus(true);
			}
		});
		txtPostObservaciones.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				btnPostGuardar.setFocus(true);				
			}
		});
		btnPostCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					win.onClose();
					liberarAsientos(ltbxPostAsientoSeleccionado);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnPostGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onClick_btnPostergar(ventaOriginal);
					
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});
		win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					liberarAsientos(ltbxPostAsientoSeleccionado);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		return win;
	}
	
	
	/**
	 * 
	 * @param ventaOriginal
	 * @throws Exception
	 */
	private void onClick_btnPostergar(VentaPasaje ventaOriginal)throws Exception{
		if(ltbxPostAsientoSeleccionado.getItemCount()==0) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAsientoSeleccionado"));
			return;
		}else if(dbxPostTarifa.getValue()==null || dbxPostTarifa.getValue()<=.00) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerario"));
			return;			
		}else if(!(cmbPostEmbarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPostEmbarque_hora);
			return;
		}else if(!(cmbPostDesembarque_hora.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPostDesembarque_hora);
			return;
		}
		boolean emitirComprobante = (dbxPostTotalPagar.getValue()>.00);
		
		if(emitirComprobante) {
			ControlEspecieValorada controlEspecieValorada = loadEspecieValoradaByVenta(true);
			if(controlEspecieValorada ==null)
				return;
			
			if(cmbPostTipoFormaPago.getSelectedIndex() < 0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noMedioPago"), cmbPostTipoFormaPago);
				return;
			}else if(!cmbPostOperadorTarjeta.isDisabled() && cmbPostOperadorTarjeta.getSelectedIndex() <= 0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noOperadorTarjetaCredito"), cmbPostOperadorTarjeta);
				return;
			}else if(!cmbPostTarjeta.isDisabled() && cmbPostTarjeta.getSelectedIndex() <= 0) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarjetaCredito"), cmbPostTarjeta);
				return;
			}else if(!txtPostNumeroOperacion.isDisabled() && txtPostNumeroOperacion.getText().trim().isEmpty()) {
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroOperacionBancaria"), txtPostNumeroOperacion);
				return;
			}	
			
			//Valida liquidacion
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
				
		VentaIdaRetorno venta = ltbxPostAsientoSeleccionado.getItems().get(0).getValue();
		DetalleItinerario detalleItinerario = venta.getDetalleItinerarioIDA();
		
		VentaPasaje postergacion = (VentaPasaje)ventaOriginal.clone();
		postergacion.setId(null);
		postergacion.setVentaPasaje(ventaOriginal);
		if(emitirComprobante) {
			postergacion.setTipoFormaPago(cmbPostTipoFormaPago.getSelectedItem().getValue());
			if(cmbPostTarjeta.getSelectedIndex()>=0)
				postergacion.setTarjetaCredito(cmbPostTarjeta.getSelectedItem().getValue());
			if(!txtPostNumeroOperacion.getText().trim().isEmpty())
				postergacion.setNumeroOperacionBancaria(txtPostNumeroOperacion.getText().trim().toUpperCase());
		}
		
		// Asigna el tipo de comprobante cuando se va a postergar un BOLETO DE VIAJE
		if(ventaOriginal.getTipoComprobante().getId().equals(Constantes.ID_TIPCOM_BOLETO_VIAJE)) {
			if(ventaOriginal.getCliente() != null) {
				postergacion.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_FACTURA));
			}else {
				postergacion.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_BOLETA_VENTA));
			}
		}
		postergacion.setItinerario(detalleItinerario.getItinerario());
		postergacion.setRuta(detalleItinerario.getRuta());
		postergacion.setServicio(detalleItinerario.getItinerario().getServicio());
		postergacion.setNumeroAsiento(Integer.valueOf(lblPostAsiento.getValue()));
		postergacion.setNumeroPiso(Integer.valueOf(detalleItinerario.getPiso()));
		postergacion.setFechaPartida(detalleItinerario.getFechaPartida());
		postergacion.setHoraPartida(detalleItinerario.getHoraPartida());
		postergacion.setFechaLlegada(detalleItinerario.getFechaLlegada());
		postergacion.setHoraLllegada(detalleItinerario.getHoraLlegada());
		postergacion.setAgenciaPartida(((ItinerarioAgenciaPartida)cmbPostEmbarque_hora.getSelectedItem().getValue()).getAgencia());
		postergacion.setAgenciaLlegada(((ItinerarioAgenciaLlegada)cmbPostDesembarque_hora.getSelectedItem().getValue()).getAgencia());
		postergacion.setEsFechaAbierta(Constantes.FALSE_VALUE);
		String fechaCaducidad=Constantes.FORMAT_DATE.format(postergacion.getFechaPartida())+" "+postergacion.getHoraPartida();
		Date dateCaducidad=Constantes.FORMAT_LONG.parse(fechaCaducidad);
		postergacion.setFechaCaducidad(dateCaducidad);
		postergacion.setTipoMovimiento(new TipoMovimiento(Constantes.ID_TIPMOV_POSTERGACION));
//		postergacion.setObservaciones(postergacion.getObservaciones()==null?"POSTERGACION":postergacion.getObservaciones()+";"+"POSTERGACIÓN");
		postergacion.setSecuencial(postergacion.getSecuencial()+1);
		postergacion.setTarifa(dbxPostTarifa.getValue());
		postergacion.setPenalidad(dbxPostPenalidad.getValue());
		postergacion.setImportePagado(dbxPostTotalPagar.getValue());
		postergacion.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
		postergacion.setFechaLiquidacion(fechaLiquidacion);
		postergacion.setAgencia(getAgencia());
		postergacion.setUsuario(getUsuario());
		postergacion.setCanalVenta(canalVenta);
		postergacion.setLiquidacion(null);
		postergacion.setFechaTransferencia(null);
		postergacion.setEmpresa(detalleItinerario.getItinerario().getEmpresa());
		postergacion.setRucClienteCredito(null);
		postergacion.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
		postergacion.setUsuarioHardware(getUsuarioHardware());
		postergacion.setNumeroBoletoAnterior(ventaOriginal.getNumeroBoleto());
		postergacion.setEnviadoSFE(null);
		postergacion.setFechaEnvioSFE(null);
		postergacion.setObservaciones(!txtPostObservaciones.getText().trim().isEmpty()? txtPostObservaciones.getText().trim().toUpperCase(): null);
		
		/************** Tracking (GPS) **********************/
		String motivoMovimiento = "";
		
		// Valida si es una postergacion
		if(!postergacion.getItinerario().getId().equals(ventaOriginal.getItinerario().getId())) {
			motivoMovimiento = "POSTERGACION";
		}else {
			// Valida si es un cambio de asiento
			motivoMovimiento = "CAMBIO DE ASIENTO";
		}
		
		// Valida si es cambio de servicio
		if(!postergacion.getServicio().getId().equals(ventaOriginal.getServicio().getId())) {
			motivoMovimiento += (motivoMovimiento.length() > 0? ", " : "");
			motivoMovimiento += "CAMBIO DE SERVICIO";
		}
		
		MovimientoPasajes movimientoPasajes = UtilData.createTracking(postergacion, motivoMovimiento);
		postergacion.setMovimientoPasajes(movimientoPasajes);
		/****************************************************/
		
		UtilData.auditarRegistro(postergacion,getUsuario(), Executions.getCurrent());
		
		Messagebox.show(Messages.getString("WndPostergacion.information.confirmacionPostergacion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try {
					if(e.getName().equals("onYes")){
						//Quitamos el boleto del manifiesto
						if(boletoManifestado)
							ServiceLocator.getDetalleManifiestoManager().quitarManifiesto(postergacion.getVentaPasaje().getId());

						List<VentaPasaje> listVentas = new ArrayList<VentaPasaje>();
						listVentas.add(postergacion);
						List<VentaPasaje> listNotasCredito = ServiceLocator.getVentaPasajesManager().postergarBoleto(listVentas, true, false);
						
						//Valida si debe o no enviar el comprobante a FE
						if(emitirComprobante) {
							List<VentaPasaje> listVentasEnvioFE = new ArrayList<VentaPasaje>();
							for(VentaPasaje _ventaPasaje: listVentas) {
								//Actualiza el correlativo
								ServiceLocator.getVentaPasajesManager().actualizarCorrelativoComprobante(_ventaPasaje, true);
								
								//Busca la venta
								VentaPasaje _postergacion = ServiceLocator.getVentaPasajesManager().buscarVentaById(_ventaPasaje.getId());
								listVentasEnvioFE.add(_postergacion);
							}
							WSFE.sendVenta(listVentasEnvioFE, wndVentaReservaNew, true, null, Constantes.NUMERO_COPIAS_COMPROBANTE_PASAJES);
						}
										
						//Envia las NC a sunat
						for(VentaPasaje notaCredito: listNotasCredito) {								
							WSFE.sendNota(notaCredito);
						}
						
						DlgMessage.information(Messages.getString("WndVentaReserva.information.exitoPostergacion"));
						
						ltbxVtaAsientosSeleccionados.removeItemAt(ltbxVtaAsientosSeleccionados.getSelectedItem().getIndex());
						boletoManifestado = false;
						cleanDatosInfoComprobante();
						txtVtaPaxBusqueda.setText("");																					
						disabledControlsInfoViaje(true);
						disabledControlsPasajero(true);
						disabledControlsCliente(true);
						disabledControlsPago(true);
						disabledControlsDescuento(true);
						onSelect_ltbxVtaAsientosSeleccionados(e);
						onRefreshMap();
						wndModal.onClose();
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
					ex.printStackTrace();
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnBusarItinerarioByPost(VentaPasaje ventaPasaje)throws Exception{
		Util.limpiarListbox(ltbxPostItienerarios);
		cleanBusquedaItinerariosPost();
		
		if(!(cmbPostOrigen.getSelectedItem().getValue() instanceof Localidad)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noOrigenSeleccionado"), cmbPostOrigen);
			return;
		}
		else if(!(cmbPostDestino.getSelectedItem().getValue() instanceof Localidad)) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDestinoSeleccionado"), cmbPostDestino);
			return;
		}
		
		Localidad localidadOrigen = cmbPostOrigen.getSelectedItem().getValue();
		
		String rucEmpresa = ((Empresa)cmbPostEmpresa.getSelectedItem().getValue()).getNumeroDocumento();
		String origen = localidadOrigen.getDenominacion();
		String destino = (((Localidad)cmbPostDestino.getSelectedItem().getValue()).getDenominacion());
		String fechaIda = Constantes.FORMAT_DATE.format(dtbxPostFecha.getValue());
		
		
		//Busca itinerarios para la Ida
		List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaIda, origen, destino, rucEmpresa);
		
		//Filtra los puntos de embarque que corresponden a la localidad Origen seleccionada.
		lstItinerarios = lstItinerarios.stream()
				.filter(_detalleItinerario -> _detalleItinerario.getPuntoEmbarque().getAgencia().getLocalidad().getId().intValue()==localidadOrigen.getId().intValue())
				.collect(Collectors.toList());
						
		for(DetalleItinerario detalleItinerario : lstItinerarios){
			Listitem item = new Listitem();
			Listcell cell =  new Listcell(detalleItinerario.getItinerario().getEmpresa().getNombreCorto()); 
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getRuta().toString());
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getPuntoEmbarque().getAgencia().getNombreCorto());
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getPuntoEmbarque().getHoraPartida());
			cell.setStyle("font-size:11px !important;text-align:center");
			cell.setTooltiptext("Hora de Partida");
			item.appendChild(cell);
			cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
			item.appendChild(cell);			
			/*Tarifa Estandar*/
			List<TarifaRegular> lstTarifaRegular = ServiceLocator.getTarifaRegularManager().buscarTarifaPorServicio(1,
					detalleItinerario.getItinerario().getServicio().getId(),
					detalleItinerario.getRuta().getId(), fechaIda,
					detalleItinerario.getHoraPartida(), null, null, detalleItinerario.getItinerario().getEmpresa().getId());
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
			cellTarifaStandar.setStyle("font-size:13px !important;text-align:center");		
			item.appendChild(cellTarifaStandar);
			
			cell = new Listcell();
			Button btnSeleccionar = new Button("Seleccionar");
			btnSeleccionar.setAutodisable("self");
			btnSeleccionar.setAttribute(Listitem.class.getName(), item);
			btnSeleccionar.setHeight("20px");
			btnSeleccionar.setAutag("self");
			cell.appendChild(btnSeleccionar);
			item.appendChild(cell);
			
			item.setValue(detalleItinerario);
			
			btnSeleccionar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub					
					try {						
						onClick_btnSeleccionItinerarioPost(event, gpbxPostMapa, ventaPasaje);
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					try {
						onClick_btnSeleccionItinerarioPost(event, gpbxPostMapa, ventaPasaje);
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName() + " "+ex.getMessage());
					}
				}
			});
			
			ltbxPostItienerarios.appendChild(item);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnSeleccionItinerarioPost(Event event, Groupbox gpbxMapa, VentaPasaje ventaPasaje)throws Exception{
		
		Listitem item = null;
		if(event.getTarget() instanceof Listitem)
			item = (Listitem)event.getTarget();
		else if(event.getTarget() instanceof Button)
			item = (Listitem) ((Button)event.getTarget()).getParent().getParent();
		
		if(item!=null) {
			liberarAsientos(ltbxPostAsientoSeleccionado);
			
			Listbox listboxSelect = ((Listbox)item.getParent());
			listboxSelect.setSelectedItem(item);
			postDetalleItinerario = item.getValue();
			lblPostRuta_fecha.setValue(postDetalleItinerario.getRuta().toString()+" - "+Constantes.FORMAT_DATE.format(postDetalleItinerario.getFechaPartida()));
			loadPuntoEmbarque(postDetalleItinerario, cmbPostEmbarque_hora);
			loadPuntoDesembarque(postDetalleItinerario, cmbPostDesembarque_hora);
//			seleccionarPuntoEmbarque(cmbPostEmbarque_hora, ventaPasaje.getAgenciaPartida());
			seleccionarPuntoEmbarque(cmbPostEmbarque_hora, postDetalleItinerario.getPuntoEmbarque().getAgencia());
			seleccionarPuntoDesembarque(cmbPostDesembarque_hora, ventaPasaje.getAgenciaLlegada());
			lblPostAsiento.setValue("-");
			dbxPostTarifa.setValue(.00);
			createEstructura(postDetalleItinerario.getItinerario().getServicio().getId(), gpbxMapa, false, postDetalleItinerario, postMapaAsientos, null, true);
			hboxPost_1.setVisible(true);
			hboxPost_2.setVisible(true);
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void cleanBusquedaItinerariosPost()throws Exception{
		Util.limpiarListbox(ltbxPostItienerarios);
		Util.limpiarCombobox(cmbPostEmbarque_hora);
		Util.limpiarCombobox(cmbPostDesembarque_hora);
		lblPostRuta_fecha.setValue("-");
		lblPostAsiento.setValue("-");
		dbxPostTarifa.setValue(.00);
		inicializarEstructura(gpbxPostMapa);
		hboxPost_1.setVisible(false);
		hboxPost_2.setVisible(false);
	}
	
	/**
	 * Limpia los datos del Pago
	 * @throws Exception
	 */
	private void cleanDatosPagoPost()throws Exception{
		dbxPostTotalPagar.setValue(.00);
		cmbPostTipoFormaPago.setSelectedIndex(-1);
		cmbPostOperadorTarjeta.setSelectedIndex(-1);
		Util.limpiarCombobox(cmbPostTarjeta);
		cmbPostTarjeta.setText("");
		txtPostNumeroOperacion.setText("");
		
		cmbPostOperadorTarjeta.setDisabled(true);
		cmbPostTarjeta.setDisabled(true);
		txtPostNumeroOperacion.setDisabled(true);
	}
	
	
	/**
	 * Calcula el monto total a pagar en la postergación
	 * @throws Exception
	 */
	private void calcularTotalPagoPost()throws Exception{
		Double tarifaActual = Optional.ofNullable(dbxPostTarifa.getValue()).orElse(.00);
		Double tarifaAnterior = Optional.ofNullable(dbxPostMontoAnterior.getValue()).orElse(.00);
		Double penalidad = Optional.ofNullable(dbxPostPenalidad.getValue()).orElse(.00);
		
		Double totalPagar = .00;
		
		if(tarifaActual >.00) {						
			totalPagar = penalidad;			
			if(tarifaActual > tarifaAnterior)
				totalPagar += (tarifaActual - tarifaAnterior);
		}
		
		dbxPostTotalPagar.setValue(totalPagar);
		
		boolean disableControlsPost = (totalPagar==0);		
		cmbPostTipoFormaPago.setDisabled(disableControlsPost);
		txtPostObservaciones.setDisabled(disableControlsPost);
	}
	
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e				: Evento
	 * @param mapaAsientos	: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda			: Indica si es Ida o retorno.
	 */
	private void onClickAsientoMapPost(Event e, DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos){
		try{
			lblPostAsiento.setValue("-");
			dbxPostTarifa.setValue(.00);
			dbxPostTotalPagar.setValue(.00);
			cmbPostTipoFormaPago.setDisabled(true);
			cleanDatosPagoPost();
			Asiento asientoSeleccionado = (Asiento)e.getTarget();
			key = asientoSeleccionado.getKey();						
			
			/*============================================================*/
			boolean liberarAsientos = true;
			for(Listitem item: ltbxPostAsientoSeleccionado.getItems()) {
				VentaIdaRetorno venta = item.getValue();
				Asiento _asiento = venta.getDetalleItinerarioIDA().getObjAsiento();
				if(_asiento.getKey().equals(key)) {
					liberarAsientos = false;
					break;
				}
			}
			if(liberarAsientos) {
				liberarAsientos(ltbxPostAsientoSeleccionado);
				onRefreshMapaAsientos(postMapaAsientos, postDetalleItinerario, null);
			}						
			/*============================================================*/
			
			
			/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
			if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario(), ltbxPostAsientoSeleccionado)) {
				return;
			}
			
			/*	validamos la cantidad maxima de asientos seleccionados	*/
//			if(ltbxVtaAsientosSeleccionados.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
				/*	Si el asiento se encuentra bloqueado mostrar mensaje de no disponibilidad	*/
				if(consultaAsientoBloqueado(key, mapaAsientos)){					
					DlgMessage.information(Messages.getString("WndVentaReserva.information.asientoNoDisponible"));
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
						onRefreshMapaAsientos(postMapaAsientos, asientoSeleccionado.getDetalleItinerario(), null);
						return;
					}else{	/*	Agregamos el asiento a la lista de seleccionados	*/
						mapaAsientos.get(key).setSrc(Constantes.PATH_PARTIAL+"asientoBloqueado_"+asientoSeleccionado.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_BLOQUEADO);

						asientoSeleccionado.getDetalleItinerario().setAsiento(asientoSeleccionado.getNumeroAsiento().toString());
						asientoSeleccionado.getDetalleItinerario().setPiso(String.valueOf(asientoSeleccionado.getPiso()));
						asientoSeleccionado.getDetalleItinerario().setZona(String.valueOf(asientoSeleccionado.getNumeroZona()));

						List<TarifaRegular> lstTarifaRegular = buscarTarifa(
								asientoSeleccionado.getDetalleItinerario().getItinerario().getServicio().getId(),
								asientoSeleccionado.getDetalleItinerario().getRuta().getId(),
								Util.DatetoString(asientoSeleccionado.getDetalleItinerario().getFechaPartida(), Constantes.DATE_FORMAT),
								asientoSeleccionado.getDetalleItinerario().getHoraPartida(),
								asientoSeleccionado.getPiso(),
								asientoSeleccionado.getNumeroZona(),
								asientoSeleccionado.getDetalleItinerario().getItinerario().getEmpresa().getId());
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
						asientoSeleccionado.getDetalleItinerario().setEsIda(true);
						/*============================================================*/						
						Listitem listitemAsientos = new Listitem();
						Listcell cell = new Listcell(asientoSeleccionado.getDetalleItinerario().getRuta().toString());
						listitemAsientos.appendChild(cell);
						cell = new Listcell(asientoSeleccionado.getNumeroAsiento().toString());
						listitemAsientos.appendChild(cell);
						DetalleItinerario detalle = (DetalleItinerario)asientoSeleccionado.getDetalleItinerario().clone();
						detalle.setObjAsiento(asientoSeleccionado);
						VentaIdaRetorno venta = new VentaIdaRetorno();
						venta.setDetalleItinerarioIDA(detalle);
						listitemAsientos.setValue(venta);
						ltbxPostAsientoSeleccionado.appendChild(listitemAsientos);
						lblPostAsiento.setValue(asientoSeleccionado.getNumeroAsiento().toString());
						dbxPostTarifa.setValue(detalle.getTarifa());
						calcularTotalPagoPost();
						if(!cmbPostTipoFormaPago.isDisabled()) {
							cmbPostTipoFormaPago.open();
							cmbPostTipoFormaPago.setFocus(true);							
						}	
					}
				}
//			}
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
	
	
	@SuppressWarnings("deprecation")
	private Window createWindowProgramacionBus(Itinerario itinerario, Manifiesto manifiesto)throws Exception{
		
		final Window win = new Window("", "normal", true);
		win.setWidth("600px");

		Caption caption = new Caption("PROGRAMACION DE BUS", "resources/buttons/mp_bus-clock.png");
		win.appendChild(caption);
		
		Groupbox groupbox = new Groupbox();
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption("DATOS DEL ITINERARIO");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		Grid grid = new Grid();
		grid.setStyle("border:none");
		Columns columns = new Columns();
		Column column = new Column(null, null, "100px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column(null, null, "180px"));
		column = new Column(null, null, "110px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column());
		Rows rows = new Rows();
		Row row = new Row();
		row.setSpans("1,3");
		row.appendChild(new Label("EMPRESA :"));
		Label label = new Label(itinerario.getEmpresa().getNombreCorto());
		label.setSclass("label-alert");
		row.appendChild(label);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("ITINERARIO :"));
		label = new Label(itinerario.getId().toString());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		row.appendChild(new Label("TIPO SERVICIO :"));
		label = new Label(itinerario.getServicio().getDenominacion());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("ORIGEN INICIAL :"));
		label = new Label(itinerario.getRuta().getOrigen());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		row.appendChild(new Label("DESTINO FINAL :"));
		label = new Label(itinerario.getRuta().getDestino());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("AGENCIA PARTIDA :"));
		label = new Label(itinerario.getAgenciaPartida().getDenominacion());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		row.appendChild(new Label("AGENCIA LLEGADA :"));
		label = new Label(itinerario.getAgenciaLlegada().getDenominacion());
		label.setSclass("label-size11-bold");
		row.appendChild(label);
		rows.appendChild(row);
		row = new Row();		
		row.appendChild(new Label("FECHA/HORA PARTIDA :"));
		label = new Label(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());
		label.setSclass("label-size11-bold");
		label.setStyle("font-size:13px !important");
		row.appendChild(label);
		row.appendChild(new Label("FECHA/HORA LLEGADA :"));
		label = new Label(Constantes.FORMAT_DATE.format(itinerario.getFechaLlegada())+" "+itinerario.getHoraLlegada());
		label.setSclass("label-size11-bold");
		label.setStyle("font-size:13px !important");
		row.appendChild(label);
		rows.appendChild(row);
		grid.appendChild(rows);
		grid.appendChild(columns);		
		groupbox.appendChild(grid);		
		win.appendChild(groupbox);
		
		groupbox = new Groupbox();
		groupbox.setStyle("margin-top:2px");
		groupbox.setClosable(false);
		groupbox.setMold("3d");
		caption = new Caption("DATOS DE LA PROGRAMACION");
		caption.setStyle("color: #ffffff;");
		groupbox.appendChild(caption);
		grid = new Grid();
		grid.setStyle("border:none");
		columns = new Columns();
		column = new Column(null, null, "100px");
		column.setAlign("right");
		columns.appendChild(column);
		columns.appendChild(new Column());
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		row.appendChild(new Label("BUS :"));
		cmbPbBus = new Combobox();
		cmbPbBus.setMold("rounded");
		cmbPbBus.setWidth("60%");
		row.appendChild(cmbPbBus);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("PILOTO :"));
		cmbPbPiloto = new Combobox();
		cmbPbPiloto.setMold("rounded");
		cmbPbPiloto.setWidth("90%");
		row.appendChild(cmbPbPiloto);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("COPILOTO :"));
		cmbPbCopiloto = new Combobox();
		cmbPbCopiloto.setMold("rounded");
		cmbPbCopiloto.setWidth("90%");
		row.appendChild(cmbPbCopiloto);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("COPILOTO AUX :"));
		cmbPbCopilotoAuxiliar = new Combobox();
		cmbPbCopilotoAuxiliar.setMold("rounded");
		cmbPbCopilotoAuxiliar.setWidth("90%");
		row.appendChild(cmbPbCopilotoAuxiliar);
		rows.appendChild(row);
		row = new Row();
		row.appendChild(new Label("TRIPULANTE :"));
		cmbPbTripulante = new Combobox();
		cmbPbTripulante.setMold("rounded");
		cmbPbTripulante.setWidth("90%");
		row.appendChild(cmbPbTripulante);
		rows.appendChild(row);
		grid.appendChild(rows);
		groupbox.appendChild(grid);
		win.appendChild(groupbox);
		
		grid = new Grid();
		grid.setStyle("margin-top:3px");
		columns = new Columns();
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		rows = new Rows();
		row = new Row();
		Div div = new Div();
		div.setStyle("text-align:center;");
		btnPbEditar = new Button("Editar");
		btnPbEditar.setAutodisable("self");
				
		btnPbGuardar = new Button("Guardar");
		btnPbGuardar.setAutodisable("self");		
		btnPbAnular = new Button("Anular");		
		btnPbAnular.setAutodisable("self");		
		btnPbCerrar = new Button("Cerrar");
		btnPbCerrar.setImage("resources/mp_cerrar.png");		
		btnPbCerrar.setSclass("btnCommandL");
		btnPbCerrar.setAutodisable("self");
		div.appendChild(btnPbEditar);
		div.appendChild(new Separator("vertical"));
		div.appendChild(btnPbGuardar);
		div.appendChild(new Separator("vertical"));
		div.appendChild(btnPbAnular);
		div.appendChild(new Separator("vertical"));
		div.appendChild(btnPbCerrar);
		row.appendChild(div);
		rows.appendChild(row);
		grid.appendChild(rows);
		win.appendChild(grid);
		
		loadBusesPb(cmbPbBus, itinerario.getEmpresa(), itinerario.getServicio());
		UtilData.cargarPersonalXtipo(cmbPbPiloto, null, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbPbCopiloto, null, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbPbCopilotoAuxiliar, null, Constantes.ID_TIPPER_PILOTO_COPILOTO);
		UtilData.cargarPersonalXtipo(cmbPbTripulante, null, Constantes.ID_TIPPER_TRIPULANTE);
		
		if(programacionServicio!=null) {
			Util.seleccionarValorItemCombo(Bus.class, cmbPbBus, programacionServicio.getBus().getId());
			Util.seleccionarValorItemCombo(Personal.class, cmbPbPiloto, programacionServicio.getPiloto().getId());
			if(programacionServicio.getCopiloto()!=null)
				Util.seleccionarValorItemCombo(Personal.class, cmbPbCopiloto, programacionServicio.getCopiloto().getId());
			if(programacionServicio.getCopilotoAuxiliar()!=null)
				Util.seleccionarValorItemCombo(Personal.class, cmbPbCopilotoAuxiliar, programacionServicio.getCopilotoAuxiliar().getId());
			if(programacionServicio.getTripulante()!=null)
				Util.seleccionarValorItemCombo(Personal.class, cmbPbTripulante, programacionServicio.getTripulante().getId());
		}	
		
		boolean isDisabledBtnModificar = true;
		boolean isDisabledBtnGuardar = true;
		boolean isDisabledBtnAnular = true;
		boolean isDisabledCtrls = true;
		
		if(isEnabledVenta) {
			isDisabledBtnModificar = (programacionServicio == null || manifiesto != null);
			isDisabledBtnGuardar = (programacionServicio != null);
			isDisabledBtnAnular = (programacionServicio == null || manifiesto != null);
			isDisabledCtrls = (programacionServicio != null);
		}
		
//		disabled_btnModificar(btnPbEditar, (programacionServicio == null || manifiesto != null));
//		disabled_btnGuardar(btnPbGuardar, programacionServicio != null);
//		disabled_btnAnular(btnPbAnular, (programacionServicio == null || manifiesto != null));		
		disabled_btnModificar(btnPbEditar, isDisabledBtnModificar);
		disabled_btnGuardar(btnPbGuardar, isDisabledBtnGuardar);
		disabled_btnAnular(btnPbAnular, isDisabledBtnAnular);
		validarEdicionPb(itinerario);
		disabledControlsPb(isDisabledCtrls);
//		disabledControlsPb(programacionServicio != null);
		
		cmbPbBus.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onChange_cmbPbBus(itinerario);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		cmbPbBus.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbPbPiloto.setFocus(true);
			}
		});		
		cmbPbPiloto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onChange_cmbPbPiloto(itinerario);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		cmbPbPiloto.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbPbCopiloto.setFocus(true);
			}
		});
		cmbPbCopiloto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onChange_cmbPbCopiloto(itinerario);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		cmbPbCopiloto.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbPbCopilotoAuxiliar.setFocus(true);
			}
		});
		cmbPbCopilotoAuxiliar.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				cmbPbTripulante.setFocus(true);
			}
		});
		cmbPbTripulante.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onChange_cmbPbTripulante(itinerario);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		cmbPbTripulante.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				btnPbGuardar.setFocus(true);
			}
		});
		cmbPbBus.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				((Combobox)event.getTarget()).select();
			}
		});
		cmbPbPiloto.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				((Combobox)event.getTarget()).select();
			}
		});
		cmbPbCopiloto.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				((Combobox)event.getTarget()).select();
			}
		});
		cmbPbCopilotoAuxiliar.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				((Combobox)event.getTarget()).select();
			}
		});
		cmbPbTripulante.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				((Combobox)event.getTarget()).select();
			}
		});
		btnPbEditar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					disabledControlsPb(false);		
					cmbPbBus.setFocus(true);
					disabled_btnModificar(btnPbEditar, true);
					disabled_btnGuardar(btnPbGuardar, false);
					disabled_btnAnular(btnPbAnular, true);
					btnPbCerrar.setLabel("Cancelar");
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		btnPbGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onClick_btnPbGuardar(itinerario);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		btnPbAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onClick_btnPbAnular();					
					
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
				}
			}
		});
		btnPbCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if(!btnPbGuardar.isDisabled() && programacionServicio!=null && programacionServicio.getId()!=null) {
					disabled_btnModificar(btnPbEditar, false);
					disabled_btnGuardar(btnPbGuardar, true);
					disabled_btnAnular(btnPbAnular, false);
					validarEdicionPb(itinerario);
					btnPbCerrar.setLabel("Cerrar");
					disabledControlsPb(true);
				}else
					win.onClose();
			}
		});
		
		return win;
	}
	
	
	/**
	 * 
	 * @param programacionServicio
	 * @throws Exception
	 */
	private void onClick_btnPbGuardar(Itinerario itinerario)throws Exception{
		if(cmbPbBus.getSelectedIndex()<0) {
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneBus"), cmbPbBus);
			return;
		}else if(cmbPbPiloto.getSelectedIndex()<0) {
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccionePilo"), cmbPbPiloto);
			return;
		}else if(cmbPbCopiloto.getSelectedIndex()<0) {
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccionCopiloto"), cmbPbCopiloto);
			return;
		}else if(cmbPbPiloto.getSelectedItem().getValue().equals(cmbPbCopiloto.getSelectedItem().getValue())) {
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.PilotosIguales"),cmbPbPiloto);
			return;
		}
		
		boolean isEdicion = (programacionServicio!=null && programacionServicio.getId()!=null);
		if(programacionServicio ==null)
			programacionServicio = new ProgramacionServicio();
		
		programacionServicio.setItinerario(itinerario);
		programacionServicio.setBus(cmbPbBus.getSelectedItem().getValue());
		programacionServicio.setPiloto(cmbPbPiloto.getSelectedItem().getValue());
		programacionServicio.setCopiloto(cmbPbCopiloto.getSelectedItem().getValue());
		programacionServicio.setCopilotoAuxiliar(null);
		if(cmbPbCopilotoAuxiliar.getSelectedIndex()>=0)
			programacionServicio.setCopilotoAuxiliar(cmbPbCopilotoAuxiliar.getSelectedItem().getValue());
		if(cmbPbTripulante.getSelectedIndex()>=0)
			programacionServicio.setTripulante(cmbPbTripulante.getSelectedItem().getValue());
		programacionServicio.setEstadoRegistro(Constantes.VALUE_ACTIVO);							
		
		Messagebox.show(Messages.getString("Generales.query.guardar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					try{
						String messageConfirmation = "";
						if(!isEdicion) {
							UtilData.auditarRegistro(programacionServicio, getUsuario(), Executions.getCurrent());
							ServiceLocator.getProgramacionServiciosManager().guardar(programacionServicio);
							messageConfirmation = "WndProgramacionServicios.Information.GuardoCorrectamente";													
						}else {
							UtilData.auditarRegistro(programacionServicio, true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getProgramacionServiciosManager().actualizar(programacionServicio);							
							messageConfirmation = "WndProgramacionServicios.Information.ActualizadoCorrectamente";
						}
						
						//Actualiza el itinerario con el identificacion del bus.
						ServiceLocator.getProgramacionServiciosManager().updateItinerarioBus(itinerario.getId(), programacionServicio.getBus().getId().longValue());
						
						//Actualiza los datos del bus que se muestra en el mapa
						if(itinerario.getId().longValue() == detalleItinerarioIda.getItinerario().getId().longValue())
							loadDatosBusMapa(programacionServicio.getBus(), capVtaIdaMapa);
						else if(isVtaIdaVuelta && itinerario.getId().longValue()==detalleItinerarioVuelta.getItinerario().getId().longValue())
							loadDatosBusMapa(programacionServicio.getBus(), capVtaRetornoMapa);
						
						//
						disabledControlsPb(true);
						disabled_btnModificar(btnPbEditar, false);
						disabled_btnGuardar(btnPbGuardar, true);
						disabled_btnAnular(btnPbAnular, false);	
						validarEdicionPb(itinerario);
						btnPbCerrar.setLabel("Cerrar");
						DlgMessage.information(Messages.getString(messageConfirmation), btnPbCerrar);
						
						//Valida si debe actualizar la lista de itinerarios de la búsqueda
						if(divPaso_1.isVisible())
							onClick_btnBuscar();						
						
					}catch(DuplicateSeatException  ds){
						DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.duplicate"));
					}catch (ItinerarioException inex){
						if(inex.getTipo().intValue()==ItinerarioException.ITINERARIO_NULL)
							DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.SeleccioneItinerario"));
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex);
						DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
					}
				}
			}
		});
	}
	
	/**
	 * Anula Programaci�n.
	 * @throws Exception
	 */
	private void onClick_btnPbAnular() throws Exception{
		/*Valida si el servicio ya tiene manifiesto*/
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		criteriosBusqueda.put("bus", programacionServicio.getBus());
		criteriosBusqueda.put("itinerario", programacionServicio.getItinerario());
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<Manifiesto>listManifiesto=ServiceLocator.getManifiestoManager().buscarPorX(criteriosBusqueda, null);
		if(listManifiesto.size()>0) {
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.EliminacionNoPermitida"));
			return;
		}
		
		/*Valida si el servicio ya esta asociado a una hre*/
		HRE hre= ServiceLocator.getHREManager().buscarHREEmitida(programacionServicio.getItinerario().getId());
		if(hre!=null){
			DlgMessage.information(Messages.getString("WndProgramacionServicios.Information.deleteServicioHRE"));
			return;
		}

		Messagebox.show(Messages.getString("WndProgramacionServicios.question.AnularProgramacion"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					//Anula la programación
					programacionServicio.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					UtilData.auditarRegistro(programacionServicio,true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getProgramacionServiciosManager().actualizar(programacionServicio);
					
					//Actualiza el itinerario
					Itinerario itinerario = ServiceLocator.getItinerarioManager().buscarPorId(programacionServicio.getItinerario().getId());
					itinerario.setBus(null);
					UtilData.auditarRegistro(itinerario,true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getItinerarioManager().actualizar(itinerario);
					
					//Actualiza los datos del bus que se muestra en el mapa
					if(itinerario.getId().longValue()==detalleItinerarioIda.getItinerario().getId().longValue())
						capVtaIdaMapa.getChildren().clear();
					else if(isVtaIdaVuelta && itinerario.getId().longValue()==detalleItinerarioVuelta.getItinerario().getId().longValue())
						capVtaRetornoMapa.getChildren().clear();
					
					//
					programacionServicio = null;
					disabledControlsPb(false);
					cleanDatosPb();
					disabled_btnModificar(btnPbEditar, true);
					disabled_btnGuardar(btnPbGuardar, false);
					disabled_btnAnular(btnPbAnular, true);
					
					//Actualiza la busqueda de los itinerarios, 
					if(divPaso_1.isVisible())
						onClick_btnBuscar();					
					
					
					cmbPbBus.setFocus(true);
				}
			}
		});
	}
	
	/**
	 * 
	 * @param itinerario
	 * @throws Exception
	 */
	private void onChange_cmbPbBus (Itinerario itinerario)throws Exception{
		if(cmbPbBus.getSelectedIndex()>=0) {
			Bus bus = cmbPbBus.getSelectedItem().getValue();
			String fechaPartidad= Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
			String horaPartida=itinerario.getHoraPartida();
			List<ProgramacionServicio> list = ServiceLocator.getProgramacionServiciosManager().validacionBusesProgramados(fechaPartidad,horaPartida,bus.getId(),itinerario.getAgenciaPartida().getId());
			if(list.size()>0) {
				ProgramacionServicio _programacionServicio = list.get(0);
				if(this.programacionServicio!=null && this.programacionServicio.getId().longValue()!=_programacionServicio.getId().intValue()) {
					Messagebox.show("El Bus seleccionado ya está Programado para esta fecha. "+
							"	Itinerario:" +  _programacionServicio.getItinerario().getId() +
							"	Ruta:" + _programacionServicio.getItinerario().getRuta().getOrigen() + " - " + _programacionServicio.getItinerario().getRuta().getDestino() +
							". 	¿Desea continuar?",
						DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if(!e.getName().equals("onYes")){
								cmbPbBus.setText("");
								cmbPbBus.setSelectedIndex(-1);
							}
						}
					});
				}
			}
		}		
	}
	
	/**
	 * 
	 * @param itinerario
	 * @throws Exception
	 */
	private void onChange_cmbPbPiloto (Itinerario itinerario)throws Exception{	
		if(cmbPbPiloto.getSelectedIndex()>=0) {
			Personal piloto = cmbPbPiloto.getSelectedItem().getValue();
			ProgramacionServicio programacion =  validarPbPilotoTripulante(TIPO_PERSONAL_PILOTO, piloto.getId().intValue(), itinerario);
			if(programacion!=null) {
				Messagebox.show("El Piloto seleccionado se encuentra programado en el Servicio: " +
								programacion.getItinerario().getRuta().getOrigen() +" - "+programacion.getItinerario().getRuta().getDestino() +
								",  Hora Partida:"+programacion.getItinerario().getHoraPartida()+", Bus:"+programacion.getBus().getCodigo() +
								".\n ¿Desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(!e.getName().equals("onYes")){
							cmbPbPiloto.setText("");
							cmbPbPiloto.setSelectedIndex(-1);
						}
					}
				});
			}
		}
	}
	
	/**
	 * 
	 * @param itinerario
	 * @throws Exception
	 */
	private void onChange_cmbPbCopiloto (Itinerario itinerario)throws Exception{	
		if(cmbPbCopiloto.getSelectedIndex()>=0) {
			Personal piloto = cmbPbCopiloto.getSelectedItem().getValue();
			ProgramacionServicio programacion =  validarPbPilotoTripulante(TIPO_PERSONAL_COPILOTO, piloto.getId().intValue(), itinerario);
			if(programacion!=null) {
				Messagebox.show("El Copiloto seleccionado se encuentra programado en el Servicio: " +
								programacion.getItinerario().getRuta().getOrigen() +" - "+programacion.getItinerario().getRuta().getDestino() +
								",  Hora Partida:"+programacion.getItinerario().getHoraPartida()+", Bus:"+programacion.getBus().getCodigo() +
								".\n ¿Desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(!e.getName().equals("onYes")){
							cmbPbCopiloto.setText("");
							cmbPbCopiloto.setSelectedIndex(-1);
						}
					}
				});
			}
		}
	}
	
	/**
	 * 
	 * @param itinerario
	 * @throws Exception
	 */
	private void onChange_cmbPbTripulante (Itinerario itinerario)throws Exception{	
		if(cmbPbTripulante.getSelectedIndex()>=0) {
			Personal piloto = cmbPbTripulante.getSelectedItem().getValue();
			ProgramacionServicio programacion =  validarPbPilotoTripulante(TIPO_PERSONAL_PILOTO, piloto.getId().intValue(), itinerario);
			if(programacion!=null) {
				Messagebox.show("El Tripulante seleccionado se encuentra programado en el Servicio: " +
								programacion.getItinerario().getRuta().getOrigen() +" - "+programacion.getItinerario().getRuta().getDestino() +
								",  Hora Partida:"+programacion.getItinerario().getHoraPartida()+", Bus:"+programacion.getBus().getCodigo() +
								".\n ¿Desea continuar?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(!e.getName().equals("onYes")){
							cmbPbTripulante.setText("");
							cmbPbTripulante.setSelectedIndex(-1);
						}
					}
				});
			}
		}
	}
	
	/**
	 * 
	 * @param idTipoPersonal
	 * @param idPersonal
	 * @param itinerario
	 * @return
	 * @throws Exception
	 */
	private ProgramacionServicio validarPbPilotoTripulante(int idTipoPersonal, int idPersonal, Itinerario itinerario)throws Exception{
						
		String fechaPartida=Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
		Long idPiloto = null, idCopiloto = null, idTripulante = null;
		if(idTipoPersonal==TIPO_PERSONAL_PILOTO)
			idPiloto = (long)idPersonal;
		else if(idTipoPersonal==TIPO_PERSONAL_COPILOTO)
			idCopiloto = (long)idPersonal;
		else if(idTipoPersonal==TIPO_PERSONAL_TRIPULANTE)
			idTripulante = (long)idPersonal;
		
		ProgramacionServicio programacion=ServiceLocator.getProgramacionServiciosManager().validaIngresoChoferTripulante(fechaPartida, idPiloto, idCopiloto, idTripulante,itinerario.getHoraPartida(),itinerario.getAgenciaPartida().getId());
		if(programacion!=null) {
			if(programacionServicio!=null && programacionServicio.getId().longValue()!=programacion.getId().longValue())
				return programacion;
		}
			
		return null;	
	}
	
	/**
	 * Valida la edición del la Programación de Bus
	 * @param itinerario
	 * @throws Exception
	 */
	private void validarEdicionPb(Itinerario itinerario)throws Exception{		
		if(!btnPbAnular.isDisabled() && programacionServicio!=null && programacionServicio.getId()!=null) {
			Date fechaPartida = itinerario.getFechaPartida();
			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
			if(fechaPartida.getTime()<fechaActual.getTime())
				btnPbEditar.setDisabled(true);
		}
	}
	
	
	/**
	 * Carga listado de buses para la Programaci	ón de Buses.
	 * @param cmbBus
	 * @param empresa
	 * @throws Exception
	 */
	private void loadBusesPb(Combobox cmbBus, Empresa empresa, Servicio servicio)throws Exception{		
		TreeMap<String, Object>criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("empresa", empresa);
		criteriosBusqueda.put("servicio", servicio);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);			
		List<String> criteriosOrden = new ArrayList<String>();
		criteriosOrden.add("codigo");
		
		List<Bus>listBuses = ServiceLocator.getBusManager().buscarPorX(criteriosBusqueda, criteriosOrden);
		for(Bus bus: listBuses) {
			String descripcion = "";
			descripcion = bus.getCodigo();
			descripcion += "  PLACA: "+ bus.getNumeroPlaca();
			descripcion += "  CAP: "+ bus.getCapacidad();
			Comboitem comboitem = new Comboitem(descripcion);
			comboitem.setValue(bus);
			cmbBus.appendChild(comboitem);
		}		
	}
	
	
	/**
	 * Habilita/Deshabilita los controles para la Programción del Bus
	 * @param disabled
	 * @throws Exception
	 */
	private void disabledControlsPb(boolean disabled)throws Exception{		
		cmbPbBus.setDisabled(disabled);
		cmbPbPiloto.setDisabled(disabled);
		cmbPbCopiloto.setDisabled(disabled);
		cmbPbCopilotoAuxiliar.setDisabled(disabled);
		cmbPbTripulante.setDisabled(disabled);	
	}
	
	/**
	 * Limpia los datos de la programación de Bus
	 * @throws Exception
	 */
	private void cleanDatosPb()throws Exception{		
		cmbPbBus.setSelectedIndex(-1);
		cmbPbPiloto.setSelectedIndex(-1);
		cmbPbCopiloto.setSelectedIndex(-1);
		cmbPbCopilotoAuxiliar.setSelectedIndex(-1);
		cmbPbTripulante.setSelectedIndex(-1);
		cmbPbBus.setText("");
		cmbPbPiloto.setText("");
		cmbPbCopiloto.setText("");
		cmbPbCopilotoAuxiliar.setText("");
		cmbPbTripulante.setText("");
	}
	
	@SuppressWarnings("deprecation")
	private Window createWindowManifiesto_carpetaDespacho(boolean isManifiesto ,Itinerario itinerario, ProgramacionServicio programacionServicio, Manifiesto manifiesto, EspecieValorada especieValoradaSunat)throws Exception{
		
		final Window win = new Window("", "normal", true);
		win.setWidth("1030px");
		
		String titleWin = "MANIFIESTO DE USUARIOS";
		String imageTitleWin = "resources/buttons/mp_manifiesto-pax.png";
		if(isManifiesto) {
			if(manifiesto == null)
				titleWin += "::VISTA PRELIMINAR";
			else
				titleWin += "::EMITIDO";	
		}else {
			titleWin = "CARPETA DE DESPACHO::VISTA PRELIMINAR";
			imageTitleWin = "resources/buttons/mp_carpeta-despacho.png";
		}
		
		
		Caption caption = new Caption(titleWin, imageTitleWin);
		win.appendChild(caption);
		
		Grid grid = new Grid();
		grid.setStyle("border:none");
		Columns columns = new Columns();
		columns.appendChild(new Column(null, null, "600px"));
		columns.appendChild(new Column());
		grid.appendChild(columns);
		Rows rows= new Rows();
		Row row = new Row();
		row.setSpans("2");
		iframe = new Iframe();
		iframe.setWidth("99.70%");
		iframe.setStyle("height:85vh;overflow:auto;border: 1px solid gray");
		row.appendChild(iframe);
		rows.appendChild(row);
		row = new Row();
		Hbox hbox = new Hbox();
		hbox.setAlign("center");
		btnManPaxGuardar = new Button("Emitir");
		btnManPaxGuardar.setTooltiptext("Emitir Manifiesto");
		btnManPaxGuardar.setAutodisable("self");
		btnManPaxGuardar.setVisible(false);
		disabled_btnGuardar(btnManPaxGuardar, (programacionServicio==null || manifiesto!=null || especieValoradaSunat==null));
		validarEmisionManPax(itinerario);
		btnManPaxAnular = new Button("Anular");
		btnManPaxAnular.setTooltiptext("Anular Manifiesto");
		btnManPaxAnular.setAutodisable("self");
		btnManPaxAnular.setVisible(false);
		disabled_btnAnular(btnManPaxAnular, manifiesto==null);
		validarAnulacionManPax(itinerario);
		Label lblPuntocontrol = new Label("PUNTO CONTROL");
		lblPuntocontrol.setStyle("margin-left:10px");
		cmbCardesPuntoControl = new Combobox();
		cmbCardesPuntoControl.setReadonly(true);
		cmbCardesPuntoControl.setWidth("130px");
		cmbCardesPuntoControl.setStyle("margin-left:5px");
		hbox.appendChild(lblPuntocontrol);
		hbox.appendChild(cmbCardesPuntoControl);		
		hbox.appendChild(btnManPaxGuardar);
		hbox.appendChild(new Separator("vertical"));
		hbox.appendChild(btnManPaxAnular);
		row.appendChild(hbox);
		Radiogroup radiogroup = new Radiogroup();
//		rdManPaxPrintLaser = new Radio("Impresi\u00F3n L\u00E1ser");
		rdManPaxPrintLaser = new Radio("Formato Pdf");
		rdManPaxPrintMatricial = new Radio("Impresi\u00F3n Matricial");
		radiogroup.appendChild(rdManPaxPrintLaser);
		radiogroup.appendChild(new Separator("vertical"));
		radiogroup.appendChild(rdManPaxPrintMatricial);		
		btnManPaxPrint = new Button("Imprimir");
		btnManPaxPrint.setAutodisable("self");
		disabled_btnImprimir(btnManPaxPrint, false);
		hbox = new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(radiogroup);
		hbox.appendChild(new Separator("vertical"));
		hbox.appendChild(btnManPaxPrint);
		row.appendChild(hbox);
		rows.appendChild(row);
		grid.appendChild(rows);
		win.appendChild(grid);
	
		if(isManifiesto) {
			lblPuntocontrol.setVisible(false);
			cmbCardesPuntoControl.setVisible(false);
			disabled_btnImprimir(btnManPaxPrint, manifiesto==null);
			rdManPaxPrintLaser.setDisabled(btnManPaxPrint.isDisabled());
			rdManPaxPrintMatricial.setDisabled(btnManPaxPrint.isDisabled());
			btnManPaxAnular.setVisible(true);
			btnManPaxGuardar.setVisible(true);		
			
			// Solamente se habilita si la agencia esta habilitada para emitir Manifiestos electrónicos.
			if(!rdManPaxPrintLaser.isDisabled() && !UtilFlag.isEnabledManifiestoElectronico(getAgencia().getId())) {
				rdManPaxPrintLaser.setChecked(false);
				rdManPaxPrintLaser.setDisabled(true);
			}
			
			if(programacionServicio!=null) {
				Bus bus = itinerario.getBus();
				bus.setProgramacionServicio(programacionServicio);
				itinerario.setBus(bus);
			}
			
			//Carga el previo del manifiesto de pasajeros
			loadPrevioManPax(manifiesto, itinerario, especieValoradaSunat);
		}else {
			lblPuntocontrol.setVisible(true);
			cmbCardesPuntoControl.setVisible(true);
			loadPuntoControlCarDes(cmbCardesPuntoControl, itinerario.getId());
			loadPrevioCarDes(itinerario, cmbCardesPuntoControl);
		}
		
		
		if(!rdManPaxPrintMatricial.isDisabled())
			rdManPaxPrintMatricial.setChecked(true);
		
		cmbCardesPuntoControl.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				try {
					
					loadPrevioCarDes(itinerario, cmbCardesPuntoControl);
					
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(ex.getMessage());
				}
			}
		});
		btnManPaxGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onClick_btnManPaxGuardar(itinerario, programacionServicio);
					
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(ex.getMessage());
				}
			}
		});
		btnManPaxAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {
					
					onClick_btnManPaxAnular(manifiesto, itinerario);					
					
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(ex.getMessage());
				}
			}
		});
		btnManPaxPrint.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				try {					
					if(isManifiesto) {
						manifiesto.setItinerario(itinerario);
						
						onClick_btnManPaxPrint(manifiesto ,rdManPaxPrintMatricial.isChecked());
					}else
						onClick_btnCarDesPrint(itinerario, rdManPaxPrintMatricial.isChecked(), cmbCardesPuntoControl);
						
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
					log.error(ex);
					DlgMessage.error(ex.getMessage());
				}
			}
		});
		
		return win;
	}
	
	/**
	 * 
	 * @param descuento
	 * @return
	 * @throws Exception
	 */
	private Window createWindowDescuentoByRango(Descuento descuento)throws Exception{
		final Window win = new Window("", "normal", true);
		win.setWidth("605px");
//		win.setWidth("500px");
		Caption caption = new Caption("DESCUENTO POR RANGO", null);
		win.appendChild(caption);
		
		Grid grid = new Grid();
		Columns columns = new Columns();
		Column column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		grid.appendChild(columns);
		Rows rows = new Rows();
		Row row = new Row();
		Hbox hbox = new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(new Label("EL CODIGO  "));
		Label label = new Label(descuento.getCodigo());
		label.setSclass("label-size11-bold");
		label.setStyle("font-size:13px !important");
		hbox.appendChild(label);
		Label lblDesde = new Label(Util.toNumberFormat(descuento.getValorMinimo(), 2));
		lblDesde.setSclass("label-size11-bold");
		lblDesde.setStyle("font-size:13px !important");		
		Label lblHasta = new Label(Util.toNumberFormat(descuento.getValorMaximo(), 2));
		lblHasta.setSclass("label-size11-bold");
		lblHasta.setStyle("font-size:13px !important");
		Label lblTipoValor = new Label();
		
		if(descuento.getTipoDescuento().equals(Constantes.TIPO_DESCUENTO_SOLES) || descuento.getTipoDescuento().equals(Constantes.TIPO_DESCUENTO_PORCENTAJE)) {
			hbox.appendChild(new Label(" PERMITE APLICAR UN DESCUENTO, DESDE "));
			hbox.appendChild(lblDesde);
			hbox.appendChild(new Label(" HASTA "));
			hbox.appendChild(lblHasta);
			if(descuento.getTipoDescuento().equals(Constantes.TIPO_DESCUENTO_SOLES))
				lblTipoValor.setValue(" SOLES");
			else
				lblTipoValor.setValue(" POR CIENTO.");
			hbox.appendChild(lblTipoValor);
		}else {
			lblTipoValor.setValue(" SOLES");
			hbox.appendChild(new Label(" PERMITE REEMPLAZAR EL VALOR DE LA TARIFA ACTUAL, DESDE"));
			hbox.appendChild(lblDesde);
			hbox.appendChild(new Label(" HASTA "));
			hbox.appendChild(lblHasta);			
			hbox.appendChild(lblTipoValor);
		}
		row.appendChild(hbox);
		rows.appendChild(row);
		row = new Row();
		Separator separator = new Separator();
		separator.setHeight("20px");
		row.appendChild(separator);
		rows.appendChild(row);
		row = new Row();
		hbox = new Hbox();
		hbox.setAlign("center");
		label = new Label("VALOR QUE APLICA : ");
		label.setSclass("label-size11-bold");
		label.setStyle("color:blue");
		hbox.appendChild(label);
		Doublebox dbxValorAplicar = new Doublebox();
		dbxValorAplicar.setLocale(Locale.US);
		dbxValorAplicar.setFormat("#,##0.00");
		dbxValorAplicar.setWidth("90px");
		dbxValorAplicar.setSclass("abel-size11-bold");
		dbxValorAplicar.setStyle("font-size:16px !important;color:blue");
		hbox.appendChild(dbxValorAplicar);
		hbox.appendChild(new Separator());
		label = new Label();
		label = (Label) lblTipoValor.clone();
		label.setSclass("label-size11-bold");
		label.setStyle("color:blue");
		hbox.appendChild(label);
		row.appendChild(hbox);
		rows.appendChild(row);	
		row = new Row();
		separator = new Separator();
		separator.setHeight("6px");
		row.appendChild(separator);
		rows.appendChild(row);
		grid.appendChild(rows);		
		win.appendChild(grid);
		
		grid = new Grid();
		columns = new Columns();
		column = new Column();
		column.setAlign("center");
		columns.appendChild(column);
		grid.appendChild(columns);
		rows = new Rows();
		row = new Row();
		hbox = new Hbox();
		hbox.setAlign("center");
		Button btnAceptar= new Button("Aceptar", "resources/mp_acceptEnabled.png");
		btnAceptar.setClass("btnCommandM");
		btnAceptar.setHeight("28px");
		btnAceptar.setWidth("100px");
		btnAceptar.setFocus(true);
		hbox.appendChild(btnAceptar);
		separator = new Separator();
		separator.setWidth("30px");
		hbox.appendChild(separator);
		Button btnCancelar = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		btnCancelar.setClass("btnCommandM");
		btnCancelar.setHeight("28px");
		btnCancelar.setWidth("100px");
		btnCancelar.setFocus(true);
		hbox.appendChild(btnCancelar);
		row.appendChild(hbox);
		rows.appendChild(row);
		grid.appendChild(rows);
		win.appendChild(grid);
		
		dbxValorAplicar.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				dbxValorAplicar.select();
			}
		});
		
		dbxValorAplicar.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				btnAceptar.setFocus(true);
			}
		});
				
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try {
					if(dbxValorAplicar.getValue()==null) {
						DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuentoByRango.noValorAplica"), dbxValorAplicar);
						return;
					}else if(dbxValorAplicar.getValue() < descuento.getValorMinimo()) {
						DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuentoByRango.valorMenorMinimo"), dbxValorAplicar);
						return;
					}else if(dbxValorAplicar.getValue() > descuento.getValorMaximo()) {
						DlgMessage.information(Messages.getString("WndVentaReservaNew.information.descuentoByRango.valorMayorMaximo"), dbxValorAplicar);
						return;
					}
					
					aplicarCodigoDescuento(descuento, isVtaIdaVuelta, dbxValorAplicar.getValue(), true);
					win.onClose();
					
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error(e2);
					
				}
			}
		});
		
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try {
					cleanDatosDescuento();
					win.onClose();
				} catch (Exception e1) {
					log.error(e1);
					e1.printStackTrace();
				}				
			}
		});
		
		return win;
	}
	
	/**
	 * 
	 * @param itinerario
	 * @param programacionServicio
	 * @throws Exception
	 */
	private void onClick_btnManPaxGuardar(Itinerario itinerario, ProgramacionServicio programacionServicio)throws Exception{
		EspecieValorada especieValoradaSunat =  ServiceLocator.getManifiestoManager().consultaAutorizacionSunat(getAgencia().getId(), itinerario.getEmpresa().getId());
		if(especieValoradaSunat==null) {
			DlgMessage.information(Messages.getString("WndManifiesto.information.noCorrelativoManifiesto"));
			return;
		}else if(especieValoradaSunat.getAutorizacionSunat()==null || especieValoradaSunat.getAutorizacionSunat().trim().isEmpty()) {
			DlgMessage.information(Messages.getString("WndManifiesto.information.noCodigoAutorizacionSunat"));
			return;
		}
		int porcentajeCorrelativoManifiesto = (int) UtilData.porcentajeCorrelativoManifiesto(getAgencia(), itinerario.getEmpresa());/*Calcula el porcenaje de correlativos utilizados de los manifiestos*/
		if (porcentajeCorrelativoManifiesto>=100) {
			DlgMessage.information(Messages.getString("WndManifiesto.information.correlativosAgotados"));
			return;
		}else if (itinerario.getBus() ==null) {
			DlgMessage.information(Messages.getString("WndManifiesto.information.servicioNoProgramado"));
			return;
		}else if (itinerario.getBus().getDocumentoBus() ==null) { //Valida el TUC del Bus			
			DlgMessage.information("El Bus "+ itinerario.getBus().getCodigo()+ " "+Messages.getString("WndManifiesto.information.noTieneCertificadoHabilitacionBus"));
			return;
		}			
		/*Valida si el manifiesto ya está emitido*/
		Manifiesto manifiestoEmitido = ServiceLocator.getManifiestoManager().consultaMinifiestImpreso(itinerario.getId());
		if (manifiestoEmitido!=null){
			String message = "El Bus "+ manifiestoEmitido.getCodigoBus() + " esta asociado al manifiesto ";
			message += manifiestoEmitido.getNumeroManifiesto()+ " del día "+ Constantes.FORMAT_DATE.format(manifiestoEmitido.getFechaInsercion());
			message += " a la(s) "+ Constantes.FORMAT_TIME.format(manifiestoEmitido.getFechaInsercion())+ " horas";
			message += " conducido por "+manifiestoEmitido.getPiloto()+ " manifestado por ";
			message += manifiestoEmitido.getUsuarioInsercion()+"."+ " No puede imprimir otro manifiesto.";				
			DlgMessage.information(message);
			return;
		}			
		/*Alerta al usuario si el correlativo del manifiesto es mayor o igual al 80%*/
		if (porcentajeCorrelativoManifiesto >=Constantes.ALERTAR_ENVIO_MANIFIESTOS)
			DlgMessage.information(Messages.getString("WndManifiesto.information.utilizacionCorrelativosManifiesto")+ " " +porcentajeCorrelativoManifiesto + " %");
				
		DocumentoBus documentoBus = new DocumentoBus();
		documentoBus.setNumeroDocumento(itinerario.getBus().getDocumentoBus().getNumeroDocumento());
		Bus bus = new Bus();
		bus.setId(itinerario.getBus().getId());
		bus.setCodigo(itinerario.getBus().getCodigo());
		bus.setNumeroPlaca(itinerario.getBus().getNumeroPlaca());
		bus.setDocumentoBus(documentoBus);
		Manifiesto manifiesto = new Manifiesto();
		manifiesto.setItinerario(itinerario);
		manifiesto.setBus(bus);
		manifiesto.setCodigoBus(bus.getCodigo());
		manifiesto.setNumeroManifiesto(especieValoradaSunat.toString());
		manifiesto.setAutorizacionSunat(especieValoradaSunat.getAutorizacionSunat());
		manifiesto.setPiloto(programacionServicio.getPiloto().toString());
		manifiesto.setCopiloto(programacionServicio.getCopiloto().toString());
		if(programacionServicio.getCopilotoAuxiliar()!=null)
			manifiesto.setCopilotoAuxiliar(programacionServicio.getCopilotoAuxiliar().toString());
		if(programacionServicio.getTripulante() != null)
			manifiesto.setTripulante(programacionServicio.getTripulante().toString());									
		manifiesto.setCertificadoHabilitacion(bus.getDocumentoBus().getNumeroDocumento());
		manifiesto.setPlaca(bus.getNumeroPlaca().toString());
		manifiesto.setAgencia(getAgencia());
		manifiesto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(manifiesto, getUsuario(), Executions.getCurrent());
		
		Integer agenciaIdPartida = null;
		List<VentaPasaje>listVentaPasajes=ServiceLocator.getManifiestoManager().consultaPasajeros(itinerario.getId(), agenciaIdPartida);
		List<DetalleManifiesto> listDetalleManifiesto = new ArrayList<DetalleManifiesto>();
		for(VentaPasaje ventaPasaje : listVentaPasajes) {
			DetalleManifiesto detalleManifiesto=new DetalleManifiesto();
			detalleManifiesto.setVentaPasaje(ventaPasaje);
			detalleManifiesto.setAgencia(getAgencia());
			detalleManifiesto.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(detalleManifiesto, getUsuario(), Executions.getCurrent());
			listDetalleManifiesto.add(detalleManifiesto);
		}
		manifiesto.setListDetalleManifiesto(listDetalleManifiesto);
		
		Messagebox.show(Messages.getString("WndManifiesto.question.confirmarImpresion"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					try{
						ServiceLocator.getManifiestoManager().guarda(manifiesto);
						
						/*Actualiza correlativo manifiesto*/
						UtilData.auditarRegistro(especieValoradaSunat, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getManifiestoManager().updateCorrelativo(especieValoradaSunat, manifiesto);
						
						disabled_btnGuardar(btnManPaxGuardar, true);
						disabled_btnAnular(btnManPaxAnular, false);
						disabled_btnImprimir(btnManPaxPrint, false);
						rdManPaxPrintLaser.setDisabled(false);
						rdManPaxPrintMatricial.setDisabled(false);
						rdManPaxPrintMatricial.setChecked(true);
						
						
						Messagebox.show(Messages.getString("WndManifiesto.information.exitoEmisionManifiesto").replace("@manifiesto", manifiesto.getNumeroManifiesto()), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_OK, Messagebox.INFORMATION, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								wndModal.onClose();
								onClick_btnVtaManifiestoPasajeros();
							}
						});						
												
					}catch(ManifiestoDuplicateException md){
						DlgMessage.information(Messages.getString("WndManifiesto.information.DuplicateManifiesto"));
					}
				}
			}
		});
	}
	
	/**
	 * 
	 * @param manifiesto
	 * @throws Exception
	 */
	private void onClick_btnManPaxAnular(Manifiesto manifiesto, Itinerario itinerario)throws Exception{
		
		Messagebox.show("¿Realmente desea anular el Manifiesto?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){
					Manifiesto _manifiesto = ServiceLocator.getManifiestoManager().buscarPorId(manifiesto.getId());
					_manifiesto.setEstadoRegistro(Constantes.VALUE_INACTIVO);
					UtilData.auditarRegistro(_manifiesto, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getManifiestoManager().actualizar(_manifiesto);
					
					wndModal.onClose();
					onClick_btnVtaManifiestoPasajeros();					
				}
			}
		});
	}
	
	/**
	 * Realiza la impresion del manifiesto de pasajeros
	 * @param isPrintMatricial
	 * @throws Exception
	 */
	private void onClick_btnManPaxPrint(Manifiesto manifiesto, boolean isPrintMatricial)throws Exception{
		
		//Valida el tipo de impresion
		if(isPrintMatricial){
			File fileSunat = null;
			FileInputStream inputSunat= null;
			FileInputStream inputTransp= null;
			FileInputStream inputArchivo= null;
			
			// Valida si la agencia esta habilitada para generar manifiestos electrónicos
			if(UtilFlag.isEnabledManifiestoElectronico(getAgencia().getId())) {
				fileSunat = CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_SUNAT, null);
				File fileTransp = CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_TRANSPORTISTA, null);
				File fileArchivo = CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(),true,ROTULO_ARCHIVO, null);
				inputTransp= new FileInputStream(fileTransp);
				inputArchivo= new FileInputStream(fileArchivo);	
			}else {
				// Se generara el manual de existeir formato configurado
				fileSunat = CreateDocument.creaManifiestoManual_Antezana(manifiesto, getUsuario(), getAgencia(), null);				
			}			
			
			File fileDestino = new File(fileSunat.getPath().replaceAll(".txt", "")+"COMP.txt");						
			OutputStream Salida = new FileOutputStream(fileDestino);
			inputSunat = new FileInputStream(fileSunat);
			
			byte[] buffer = new byte[1024];
            int tamanio;
            //lee el fichero sunat a copiar cada 1MB
            while ((tamanio = inputSunat.read(buffer)) > 0) {                
            	Salida.write(buffer, 0, tamanio);//Escribe el MB en el fichero destino
            }
            inputSunat.close();

            //lee el fichero transportista a copiar cada 1MB
            if(inputTransp !=null) {
            	while ((tamanio = inputTransp.read(buffer)) > 0) {                
                	Salida.write(buffer, 0, tamanio);//Escribe el MB en el fichero destino
                }
                 inputTransp.close();	
            }            

           //lee el fichero Archivo a copiar cada 1MB
            if(inputArchivo !=null) {
            	while ((tamanio = inputArchivo.read(buffer)) > 0) {                 
               	 Salida.write(buffer, 0, tamanio);//Escribe el MB en el fichero destino
                }
                inputArchivo.close();	
            }             

             Salida.close();
             Util.descargarArchivo(fileDestino);
		}else{
			//Impresion lasser
			XmlManifiesto xmlManifiesto_sunat = createXmlPrintLaserManPax(manifiesto, ROTULO_SUNAT);
			XmlManifiesto xmlManifiesto_trans = createXmlPrintLaserManPax(manifiesto, ROTULO_TRANSPORTISTA);
			XmlManifiesto xmlManifiesto_archivo= createXmlPrintLaserManPax(manifiesto, ROTULO_ARCHIVO);

			List<XmlManifiesto> listXmlManifiesto=new ArrayList<>();
			listXmlManifiesto.add(xmlManifiesto_sunat);
			listXmlManifiesto.add(xmlManifiesto_trans);
			listXmlManifiesto.add(xmlManifiesto_archivo);

			Empresa empresa = ServiceLocator.getEmpresaManager().buscarPorId(manifiesto.getItinerario().getEmpresa().getId().longValue());
			//.RPT
//			String pathRpt= Constantes.DIRECTORY_FORMAT_TICKET+"Manifiesto.rpt";
			String pathRpt = WSFE.getPathFormatPrintByEmpresa(empresa.getNumeroDocumento(), Constantes.FORMAT_PRINT_MANIFIESTO);
			if(pathRpt!=null) {
				Path path = Paths.get(pathRpt);
				byte[] contenido = java.nio.file.Files.readAllBytes(path);
				String cryptoRptFormat=new BASE64Encoder().encode(contenido);

				XmlPrintLaser xmlPrintLaser= new XmlPrintLaser();
				xmlPrintLaser.setZ_rpt(cryptoRptFormat);
				xmlPrintLaser.setXmlManifiesto(listXmlManifiesto);

				/*Zippea crea y zippea el archivo xml*/
				String path_sunat=Util.createFileXmlToZipper(xmlPrintLaser, wndModal);


				//************************************************************************************
				//Consulta la version de impresi�n configurada para la agencia - jabanto 16/11/2022
//				Agencia oagencia = (Agencia)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);

//				if(UtilFlag.isFormatPrintViewPdfManifiesto(oagencia.getId())) {
					int len = path_sunat.length();
					int pos = path_sunat.indexOf("PRNTLSR-");
					String nameFileZip = path_sunat.substring(pos,len);
					File file= new File(path_sunat);
					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
//					int x = 0;
					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, true);
					if(filePdfZip !=null) {
						String urlViewPdf = UtilFlag.getUrlView_pdf();
						if(urlViewPdf !=null) {
							String crypto = new BASE64Encoder().encode(filePdfZip);
							Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");
						}
					}

//				}else if(UtilFlag.isFormatPrintDownload(oagencia.getId())) {
//					int len = path_sunat.length();
//					int pos = path_sunat.indexOf("PRNTLSR-");
//					String nameFileZip = path_sunat.substring(pos,len);
//					File file= new File(path_sunat);
//					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
//
//					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, false);
//					if(filePdfZip !=null)
//						Filedownload.save(filePdfZip, "multipart/form-data", nameFileZip);
//
//				}else if(UtilFlag.isFormatPrintViewPdf(oagencia.getId())) {
//					int len = path_sunat.length();
//					int pos = path_sunat.indexOf("PRNTLSR-");
//					String nameFileZip = path_sunat.substring(pos,len);
//					File file= new File(path_sunat);
//					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
////					int x = 0;
//					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, true);
//					if(filePdfZip !=null) {
//						String urlViewPdf = UtilFlag.getUrlView_pdf();
//						if(urlViewPdf !=null) {
//							String crypto = new BASE64Encoder().encode(filePdfZip);
//							Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");
//						}
//					}
//				}else {
//					/*Descarga el archivo .xml*/
//					Filedownload.save(new File(path_sunat), "application/zip");
//				}
				
			}else
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.noFormatPrint"));
		}
	}
	
	/**
	 * Realiza la impresion del manifiesto de pasajeros
	 * @param isPrintMatricial
	 * @throws Exception
	 */
	private void onClick_btnCarDesPrint(Itinerario itinerario, boolean isPrintMatricial, Combobox cmbPuntoControl)throws Exception{
		Itinerario _itinerario = (Itinerario)itinerario.clone();		
		Agencia agenciaPuntoControl= null;
		if(cmbPuntoControl.getSelectedItem().getValue() instanceof Agencia)
			agenciaPuntoControl=(Agencia)cmbPuntoControl.getSelectedItem().getValue();
		_itinerario.setAgenciaPartida(agenciaPuntoControl);
		
		if(isPrintMatricial){
			File file=CreateDocument.creaCarpetaDespacho(_itinerario, getAgencia());
//			String fileName = _itinerario.getId().toString();
//			if(_itinerario.getAgenciaPartida() !=null)
//				fileName += "-" + _itinerario.getAgenciaPartida().getId().toString();
//			fileName += ".txt";			
			Util.descargarArchivo(file);
		}else{
			 XmlCarpetaDespacho xmlCarpetaDespacho= createXmlPrintLaserCarDes(_itinerario);

			 Empresa empresa = ServiceLocator.getEmpresaManager().buscarPorId(itinerario.getEmpresa().getId().longValue());
			//.RPT
//			String pathRpt=Constantes.DIRECTORY_FORMAT_TICKET+"CarpetaDespacho.rpt";
			String pathRpt = WSFE.getPathFormatPrintByEmpresa(empresa.getNumeroDocumento(), Constantes.FORMAT_PRINT_CARPETA_DESPACHO);
			if(pathRpt!=null) {
				Path path = Paths.get(pathRpt);
				byte[] contenido = java.nio.file.Files.readAllBytes(path);
				String cryptoRptFormat=new BASE64Encoder().encode(contenido);

				XmlPrintLaser xmlPrintLaser= new XmlPrintLaser();
//				xmlPrintLaser.setA_configPrint(xmlConfigPrint);
				xmlPrintLaser.setZ_rpt(cryptoRptFormat);
				xmlPrintLaser.setXmlCarpetaDespacho(xmlCarpetaDespacho);

				/*Zippea crea y zip el archivo xml*/
				String path_sunat=Util.createFileXmlToZipper(xmlPrintLaser, wndModal);


				//************************************************************************************
				//Consulta la version de impresi�n configurada para la agencia - jabanto 16/11/2022
//				Agencia oagencia = (Agencia)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_AGENCIA);

//				if(UtilFlag.isFormatPrintViewPdfCarpetaDespacho(oagencia.getId())) {
					int len = path_sunat.length();
					int pos = path_sunat.indexOf("PRNTLSR-");
					String nameFileZip = path_sunat.substring(pos,len);
					File file= new File(path_sunat);
					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());

					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, true);
					if(filePdfZip !=null) {
						String urlViewPdf = UtilFlag.getUrlView_pdf();
						if(urlViewPdf !=null) {
							String crypto = new BASE64Encoder().encode(filePdfZip);
							Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");
						}
					}

//				}else if(UtilFlag.isFormatPrintDownload(oagencia.getId())) {
//					int len = path_sunat.length();
//					int pos = path_sunat.indexOf("PRNTLSR-");
//					String nameFileZip = path_sunat.substring(pos,len);
//					File file= new File(path_sunat);
//					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
//
//					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, false);
//					if(filePdfZip !=null)
//						Filedownload.save(filePdfZip, "multipart/form-data", nameFileZip);
//
//				}else if(UtilFlag.isFormatPrintViewPdf(oagencia.getId())) {
//					int len = path_sunat.length();
//					int pos = path_sunat.indexOf("PRNTLSR-");
//					String nameFileZip = path_sunat.substring(pos,len);
//					File file= new File(path_sunat);
//					byte[] fileXmlZip = java.nio.file.Files.readAllBytes(file.toPath());
//
//					byte[] filePdfZip =  Printapi.getPrintPdf(fileXmlZip, nameFileZip, Constantes.FORMATO_IMPRESION_A4, true);
//					if(filePdfZip !=null) {
//						String urlViewPdf = UtilFlag.getUrlView_pdf();
//						if(urlViewPdf !=null) {
//							String crypto = new BASE64Encoder().encode(filePdfZip);
//							Executions.getCurrent().sendRedirect(urlViewPdf+"?vl="+crypto, "_blank");
//						}
//					}
//
//				}else {
//					/*Descarga el archivo .xml*/
//					Filedownload.save(new File(path_sunat), "application/zip");
//				}
			}else
				DlgMessage.information(Messages.getString("WndVentaReservaNew.information.noFormatPrint"));
		}
		
	}
	
	/**
	 * Crea el XML para la impresion del manifiesto 
	 * @param manifiesto
	 * @param rotulo
	 * @return
	 * @throws Exception
	 */
	private XmlManifiesto createXmlPrintLaserManPax(Manifiesto manifiesto, String rotulo)throws Exception{
		Itinerario itinerario=ServiceLocator.getItinerarioManager().buscarPorId(manifiesto.getItinerario().getId());
		XmlManifiesto xmlManifiesto=new XmlManifiesto();
		xmlManifiesto.setNumeroManifiesto(manifiesto.getNumeroManifiesto());
		xmlManifiesto.setUsuario(getUsuario().toString());
		xmlManifiesto.setAgencia(getAgencia().toString());
		xmlManifiesto.setItinerario(itinerario.getId().toString());
		xmlManifiesto.setServicio(itinerario.getServicio().getDenominacion());
		xmlManifiesto.setBus(itinerario.getBus().getCodigo());
		xmlManifiesto.setOrigen(itinerario.getRuta().getOrigen());
		xmlManifiesto.setDestino(itinerario.getRuta().getDestino());
		xmlManifiesto.setPlaca(itinerario.getBus().getNumeroPlaca());
		xmlManifiesto.setTarjetaHabilitacion(itinerario.getBus().getDocumentoBus().getNumeroDocumento());
//		xmlManifiesto.setMarca(itinerario.getBus().getGrupoMantenimiento().getDenominacion());
		xmlManifiesto.setPiloto(itinerario.getBus().getProgramacionServicio().getPiloto().toString());
		xmlManifiesto.setPilotoLicencia(itinerario.getBus().getProgramacionServicio().getPiloto().getLicencia());
		xmlManifiesto.setCopiloto(itinerario.getBus().getProgramacionServicio().getCopiloto().toString());
		xmlManifiesto.setCopilotoLicencia(itinerario.getBus().getProgramacionServicio().getCopiloto().getLicencia());
		if(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar()!=null){
			xmlManifiesto.setCopilotoAuxiliar(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar().toString());
			xmlManifiesto.setCopilotoAuxiliarLicencia(itinerario.getBus().getProgramacionServicio().getCopilotoAuxiliar().getLicencia());
		}
		if(itinerario.getBus().getProgramacionServicio().getTripulante()!=null){
			xmlManifiesto.setTripulante(itinerario.getBus().getProgramacionServicio().getTripulante().toString());
			xmlManifiesto.setTripulanteDni(itinerario.getBus().getProgramacionServicio().getTripulante().getNroDocumento());
		}else{
			xmlManifiesto.setTripulante("");
			xmlManifiesto.setTripulanteDni("");
		}

		xmlManifiesto.setAutorizacionSunat(manifiesto.getAutorizacionSunat());
		xmlManifiesto.setRotulo(rotulo);
		xmlManifiesto.setSalida(Constantes.FORMAT_DATE.format(manifiesto.getItinerario().getFechaPartida())+" "+manifiesto.getItinerario().getHoraPartida());

		/*Detalle Manifiesto*/
		List<XmlItemDetalleManifiesto> listItemDetalleManifiesto= new ArrayList<>();
		List<VentaPasaje> listPasajeros=ServiceLocator.getManifiestoManager().consultaPasajeros(itinerario.getId(), null);

		int asientosServicio=itinerario.getServicio().getNumeroAsientosPiso1();
		if(itinerario.getServicio().getNumeroAsientosPiso2()!=null)
			asientosServicio += + itinerario.getServicio().getNumeroAsientosPiso2();

		for(int i=0;i<asientosServicio;i++){
			Integer asiento=i+1;

			String asientos="";
			for(VentaPasaje ventaPasaje: listPasajeros){
				if(ventaPasaje.getNumeroAsiento().equals(asiento)){
					if(asientos.length()==0)
						asientos=ventaPasaje.getNumeroAsiento().toString()+"-"+ventaPasaje.getId().toString();
					else
						asientos+=";"+ventaPasaje.getNumeroAsiento().toString()+"-"+ventaPasaje.getId().toString();
				}
			}

			String arrayAsientos[] = asientos.split(";");
//			String asientos[]=getAsientos_IdForGenManifiesto(listPasajeros, asiento, piso).split(";");
			if(arrayAsientos[0].toString().length()>0){
				for (String element : arrayAsientos) {
					long idForGenManifiesto=Long.valueOf(element.split("-")[1]);
					for(VentaPasaje ventaPasaje : listPasajeros){
						if((ventaPasaje.getId().longValue())==idForGenManifiesto){
							XmlItemDetalleManifiesto xmlItemDetalleManifiesto=null;

							xmlItemDetalleManifiesto= new XmlItemDetalleManifiesto();
							xmlItemDetalleManifiesto.setAsiento(ventaPasaje.getNumeroAsiento());
							xmlItemDetalleManifiesto.setBoleto(ventaPasaje.getNumeroBoleto());
							xmlItemDetalleManifiesto.setPasajero(ventaPasaje.getPasajero().toString());
							xmlItemDetalleManifiesto.setEdad(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
							xmlItemDetalleManifiesto.setTipoDocumento(ventaPasaje.getPasajero().getTipoDocumento().getNombreCorto());
							xmlItemDetalleManifiesto.setNumeroDocumento(ventaPasaje.getPasajero().getNumeroDocumento());
//							xmlItemDetalleManifiesto.setDestino(ventaPasaje.getRuta().getDestino());
							xmlItemDetalleManifiesto.setDestino(ventaPasaje.getAgenciaLlegada().getDenominacion());
							xmlItemDetalleManifiesto.setPuntoEmbarque(ventaPasaje.getAgenciaPartida().getDenominacion());
							xmlItemDetalleManifiesto.setFormaPago(ventaPasaje.getFormaPago().getDenominacion());
							xmlItemDetalleManifiesto.setImporte(Util.toNumberFormat(ventaPasaje.getImportePagado(),2));
							listItemDetalleManifiesto.add(xmlItemDetalleManifiesto);

							break;
						}
					}
				}
			}else{
				XmlItemDetalleManifiesto xmlItemDetalleManifiesto= new XmlItemDetalleManifiesto();
				xmlItemDetalleManifiesto.setAsiento(asiento);
				listItemDetalleManifiesto.add(xmlItemDetalleManifiesto);
			}
		}

		XmlDetalleManifiesto xmlDetalleManifiesto= new XmlDetalleManifiesto();
		xmlDetalleManifiesto.setXmlItemDetalleManifiesto(listItemDetalleManifiesto);
		xmlManifiesto.setDetalleManifiesto(xmlDetalleManifiesto);

		Integer numeroAseintos=0;
		for(int x=0; x<itinerario.getServicio().getNumeroPisos(); x++){
			if(x==0)
				numeroAseintos=itinerario.getServicio().getNumeroAsientosPiso1();
			else if(x==1)
				numeroAseintos+=itinerario.getServicio().getNumeroAsientosPiso2();
		}

		xmlManifiesto.setTotalAsientos(numeroAseintos);
		xmlManifiesto.setTotalPasajeros(listPasajeros.size());

		return xmlManifiesto;
	}
	
	/**
	 * Crea el XML para la impresion de la Carpeta de Despacho
	 * @param itinerario
	 * @return
	 * @throws Exception
	 */
	private XmlCarpetaDespacho createXmlPrintLaserCarDes(Itinerario itinerario)throws Exception{

		XmlCarpetaDespacho xmlCarpetaDespacho= new XmlCarpetaDespacho();
		xmlCarpetaDespacho.setSericio(itinerario.getServicio().getDenominacion());
		xmlCarpetaDespacho.setOrigen(itinerario.getRuta().getOrigen());
		xmlCarpetaDespacho.setDestino(itinerario.getRuta().getDestino());
		xmlCarpetaDespacho.setFechaHoraSalida(Constantes.FORMAT_DATE.format(itinerario.getFechaPartida())+" "+itinerario.getHoraPartida());

		List<XmlItemDetalleDespacho>asientosFilas= new ArrayList<>();

		List<MapaBus> resultMapaBus= ServiceLocator.getMapaBusManager().buscarMapaBus(itinerario.getServicio().getId(), Constantes.VALUE_ACTIVO);
		XmlItemDetalleDespacho itemDetalleDespacho= new XmlItemDetalleDespacho();

		int fila_old=0;
		for(int piso=0; piso < itinerario.getServicio().getNumeroPisos(); piso++) {
			for(MapaBus mapaBus : resultMapaBus){
				if(mapaBus.getNumeroAsiento()!=null && mapaBus.getNumeroPiso()!=null && mapaBus.getNumeroPiso().intValue()==piso){
					if(mapaBus.getNumeroFila().intValue()!=fila_old){
						asientosFilas.add(itemDetalleDespacho);
						itemDetalleDespacho= new XmlItemDetalleDespacho();
					}
					if(mapaBus.getNumeroColumna().intValue()==0)
						itemDetalleDespacho.setC1_asiento(mapaBus.getNumeroAsiento().toString());
					else if(mapaBus.getNumeroColumna().intValue()==1)
						itemDetalleDespacho.setC2_asiento(mapaBus.getNumeroAsiento().toString());
					else if(mapaBus.getNumeroColumna().intValue()==3)
						itemDetalleDespacho.setC3_asiento(mapaBus.getNumeroAsiento().toString());
					else if(mapaBus.getNumeroColumna().intValue()==4)
						itemDetalleDespacho.setC4_asiento(mapaBus.getNumeroAsiento().toString());

					fila_old=mapaBus.getNumeroFila();
				}
			}
		}

		asientosFilas.add(itemDetalleDespacho);


		Integer agenciaIdPartida = null;
		if(itinerario.getAgenciaPartida()!=null)
			agenciaIdPartida = itinerario.getAgenciaPartida().getId();

		List<VentaPasaje> list=ServiceLocator.getManifiestoManager().consultaPasajeros(itinerario.getId(), agenciaIdPartida);
		List<XmlItemDetalleDespacho> listDetalleCarpeta= new ArrayList<>();
		int totalFrecuentes=0;
		String asientosFrecuentes="";
		for(XmlItemDetalleDespacho xmlItemDetalleDespacho : asientosFilas){
			int asiento = 0;
			if(xmlItemDetalleDespacho.getC1_asiento()!=null) {
				asiento=Integer.valueOf(xmlItemDetalleDespacho.getC1_asiento());
				for(VentaPasaje ventaPasaje : list){
					if(ventaPasaje.getNumeroAsiento().intValue()==asiento){
						xmlItemDetalleDespacho.setC1_documento(ventaPasaje.getPasajero().getNumeroDocumento());
						xmlItemDetalleDespacho.setC1_embarque(ventaPasaje.getAgenciaPartida().getNombreCorto());
						xmlItemDetalleDespacho.setC1_boleto(ventaPasaje.getNumeroBoleto());
						xmlItemDetalleDespacho.setC1_edad(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
						xmlItemDetalleDespacho.setC1_ruta(ventaPasaje.getRuta().toString());
						xmlItemDetalleDespacho.setC1_pasajero(ventaPasaje.getPasajero().toString());
						boolean isPaxfree= false; //isPaxFee(list, (new Integer(asiento)), 0, 0);
						xmlItemDetalleDespacho.setC1_frecuente(isPaxfree?"#F":"");

						if(isPaxfree){
							totalFrecuentes += 1;
							asientosFrecuentes +=(asientosFrecuentes.length()==0?String.valueOf(asiento):", "+String.valueOf(asiento));
						}
						break;
					}
				}
			}else {
				xmlItemDetalleDespacho.setC1_asiento("");
			}

			if(xmlItemDetalleDespacho.getC2_asiento()!=null) {
				asiento=Integer.valueOf(xmlItemDetalleDespacho.getC2_asiento());
				for(VentaPasaje ventaPasaje : list){
					if(ventaPasaje.getNumeroAsiento().intValue()==asiento){
						xmlItemDetalleDespacho.setC2_documento(ventaPasaje.getPasajero().getNumeroDocumento());
						xmlItemDetalleDespacho.setC2_embarque(ventaPasaje.getAgenciaPartida().getNombreCorto());
						xmlItemDetalleDespacho.setC2_boleto(ventaPasaje.getNumeroBoleto());
						xmlItemDetalleDespacho.setC2_edad(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
						xmlItemDetalleDespacho.setC2_ruta(ventaPasaje.getRuta().toString());
						xmlItemDetalleDespacho.setC2_pasajero(ventaPasaje.getPasajero().toString());
						boolean isPaxfree= false; //isPaxFee(list, (new Integer(asiento)), 0, 0);
						xmlItemDetalleDespacho.setC2_frecuente(isPaxfree?"#F":"");

						if(isPaxfree){
							totalFrecuentes += 1;
							asientosFrecuentes +=(asientosFrecuentes.length()==0?String.valueOf(asiento):", "+String.valueOf(asiento));
						}
						break;
					}
				}
			}else {
				xmlItemDetalleDespacho.setC2_asiento("");
			}


			if(xmlItemDetalleDespacho.getC3_asiento()!=null){
				asiento=Integer.valueOf(xmlItemDetalleDespacho.getC3_asiento());
				for(VentaPasaje ventaPasaje : list){
					if(ventaPasaje.getNumeroAsiento().intValue()==asiento){
						xmlItemDetalleDespacho.setC3_documento(ventaPasaje.getPasajero().getNumeroDocumento());
						xmlItemDetalleDespacho.setC3_embarque(ventaPasaje.getAgenciaPartida().getNombreCorto());
						xmlItemDetalleDespacho.setC3_boleto(ventaPasaje.getNumeroBoleto());
						xmlItemDetalleDespacho.setC3_edad(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
						xmlItemDetalleDespacho.setC3_ruta(ventaPasaje.getRuta().toString());
						xmlItemDetalleDespacho.setC3_pasajero(ventaPasaje.getPasajero().toString());
						boolean isPaxfree= false; //isPaxFee(list, (new Integer(asiento)), 0, 0);
						xmlItemDetalleDespacho.setC3_frecuente(isPaxfree?"#F":"");

						if(isPaxfree){
							totalFrecuentes += 1;
							asientosFrecuentes +=(asientosFrecuentes.length()==0?String.valueOf(asiento):", "+String.valueOf(asiento));
						}
						break;
					}
				}
			}else {
				xmlItemDetalleDespacho.setC3_asiento("");
			}

			if(xmlItemDetalleDespacho.getC4_asiento()!=null) {
				asiento=Integer.valueOf(xmlItemDetalleDespacho.getC4_asiento());
				for(VentaPasaje ventaPasaje : list){
					if(ventaPasaje.getNumeroAsiento().intValue()==asiento){
						xmlItemDetalleDespacho.setC4_documento(ventaPasaje.getPasajero().getNumeroDocumento());
						xmlItemDetalleDespacho.setC4_embarque(ventaPasaje.getAgenciaPartida().getNombreCorto());
						xmlItemDetalleDespacho.setC4_boleto(ventaPasaje.getNumeroBoleto());
						xmlItemDetalleDespacho.setC4_edad(Util.calculaEdad(ventaPasaje.getPasajero().getFechaNacimiento()).toString());
						xmlItemDetalleDespacho.setC4_ruta(ventaPasaje.getRuta().toString());
						xmlItemDetalleDespacho.setC4_pasajero(ventaPasaje.getPasajero().toString());
						boolean isPaxfree= false; //isPaxFee(list, (new Integer(asiento)), 0, 0);
						xmlItemDetalleDespacho.setC4_frecuente(isPaxfree?"#F":"");

						if(isPaxfree){
							totalFrecuentes += 1;
							asientosFrecuentes +=(asientosFrecuentes.length()==0?String.valueOf(asiento):", "+String.valueOf(asiento));
						}
						break;
					}
				}
			}else {
				xmlItemDetalleDespacho.setC4_asiento("");
			}

			listDetalleCarpeta.add(xmlItemDetalleDespacho);
		}

		Integer totalAsientos = itinerario.getServicio().getNumeroAsientosPiso1();
		if(itinerario.getServicio().getNumeroAsientosPiso2()!=null)
			totalAsientos += + itinerario.getServicio().getNumeroAsientosPiso2();

		XmlDetalleCarpetaDespacho xmlDetalleCarpetaDespacho= new XmlDetalleCarpetaDespacho();
		xmlDetalleCarpetaDespacho.setXmlItemDetalleDespacho(listDetalleCarpeta);
		xmlCarpetaDespacho.setXmlDetalleCarpetaDespacho(xmlDetalleCarpetaDespacho);
		xmlCarpetaDespacho.setTotalPasajeros(list.size());
		xmlCarpetaDespacho.setTotalAsientos(totalAsientos);
		xmlCarpetaDespacho.setTotalFrecuentes(totalFrecuentes);
		if(!(asientosFrecuentes.isEmpty()))
			xmlCarpetaDespacho.setAsientosFrecuentes("Asientos [" + asientosFrecuentes + "]");

		return xmlCarpetaDespacho;
	}
	
	/**
	 * Carga la vista previa del manifiesto de pasajeros
	 * @param manifiesto
	 * @param itinerario
	 * @param especieValoradaSunat
	 * @throws Exception
	 */
	private void loadPrevioManPax(Manifiesto manifiesto, Itinerario itinerario, EspecieValorada especieValoradaSunat)throws Exception{
		String numeroManifiesto = "-----------", autorizacionSunat = "";
		if(manifiesto ==null) {						
			if(especieValoradaSunat!=null && especieValoradaSunat.getSerie()!=null && especieValoradaSunat.getCorrelativoActual()!=null) {
				numeroManifiesto = especieValoradaSunat.toString();
				
				if(especieValoradaSunat.getAutorizacionSunat()!=null && !especieValoradaSunat.getAutorizacionSunat().trim().isEmpty())
					autorizacionSunat = especieValoradaSunat.getAutorizacionSunat();
			}
			manifiesto = new Manifiesto();
			manifiesto.setNumeroManifiesto(numeroManifiesto);
			manifiesto.setAutorizacionSunat(autorizacionSunat);
			manifiesto.setItinerario(itinerario);
		}
		
		CreateDocument.creaManifiesto_ListPax(manifiesto,getUsuario(),getAgencia(), true, ROTULO_SUNAT, null);
		String src = Constantes.URL_FORMATOS_MANIFIESTOS + Constantes.CLAVE_PAHT + "MANPAX" + itinerario.getId() + "-" + ROTULO_SUNAT + ".txt";
		iframe.setSrc(src);
	}
	
	/**
	 * Carga la vista previa del manifiesto de pasajeros
	 * @param manifiesto
	 * @param itinerario
	 * @param especieValoradaSunat
	 * @throws Exception
	 */
	private void loadPrevioCarDes(Itinerario itinerario, Combobox cmbCmbPuntoControl)throws Exception{
		
		Agencia agenciaPuntocontrol = null;
		if(cmbCmbPuntoControl.getSelectedItem().getValue() instanceof Agencia)
			agenciaPuntocontrol = cmbCmbPuntoControl.getSelectedItem().getValue();
		
		Itinerario _itinerario = (Itinerario)itinerario.clone();
		_itinerario.setAgenciaPartida(agenciaPuntocontrol);
		
		CreateDocument.creaCarpetaDespacho(_itinerario, getAgencia());		
		String fileName = _itinerario.getId().toString();
		if(_itinerario.getAgenciaPartida() != null)
			fileName += "-" + _itinerario.getAgenciaPartida().getId().toString();
		fileName += ".txt";

		String src = Constantes.URL_FORMATOS_DESPACHOS+Constantes.CLAVE_PAHT+"CARDES"+ fileName;
		iframe.setSrc(src);
	}
	
	/**
	 * Valida la está permitida la nulación del Manifiesto de Pasajeros
	 * @param itinerario
	 * @throws Exception
	 */
	private void validarAnulacionManPax(Itinerario itinerario)throws Exception{
		String fechaActual = Constantes.FORMAT_DATE.format(new Date());
		String fechaPartida = Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
		if(!fechaActual.equals(fechaPartida))
			disabled_btnAnular(btnManPaxAnular, true);
		
	}
	
	/**
	 * Valida la está permitida la nulación del Manifiesto de Pasajeros
	 * @param itinerario
	 * @throws Exception
	 */
	private void validarEmisionManPax(Itinerario itinerario)throws Exception{
		String fechaActual = Constantes.FORMAT_DATE.format(new Date());
		String fechaPartida = Constantes.FORMAT_DATE.format(itinerario.getFechaPartida());
		if(!fechaActual.equals(fechaPartida))
			disabled_btnGuardar(btnManPaxGuardar, true);
		
	}
	
	/**
	 * carga puntos de Control, según el itinerario
	 * @param idItinerario : identificador del itinerario.
	 * @throws Exception
	 */
	private void loadPuntoControlCarDes(Combobox cmbPuntoControl, Long idItinerario) throws Exception{
		ArrayList<Agencia> lsta= (ArrayList<Agencia>) ServiceLocator.getManifiestoManager().consultaPtoControl(idItinerario);
		cmbPuntoControl.getItems().clear();
		UtilData.cargarGenericData(cmbPuntoControl, true);
		for (Agencia agencia : lsta) {
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(agencia.getNombreCorto());
			oComboitem.setValue(agencia);
			cmbPuntoControl.appendChild(oComboitem);
		}
		Util.seleccionarValorItemCombo(Agencia.class, cmbPuntoControl, getAgencia().getId());
		if(cmbPuntoControl.getSelectedIndex()<0)
			cmbPuntoControl.setSelectedIndex(0);
	}
	
	/**
	 * Habilita/Deshabilita el Button Modificar o Editar
	 * @param disabled
	 * @throws Exception
	 */
	private void disabled_btnModificar(Button btnModificar, boolean disabled)throws Exception{
		btnModificar.setDisabled(disabled);
		if(disabled) {
			btnModificar.setSclass("");
			btnModificar.setWidth("120px");
			btnModificar.setHeight("30px");
			btnModificar.setImage("resources/mp_editarDisabled.png");	
		}else {
			btnModificar.setSclass("btnCommandL");
			btnModificar.setImage("resources/mp_editarEnabled.png");	
		}
	}
	
	/**
	 * Habilita/Deshabilita el Button Guardar
	 * @param disabled
	 * @throws Exception
	 */
	private void disabled_btnGuardar(Button btnGuardar, boolean disabled)throws Exception{
		
		btnGuardar.setDisabled(disabled);
		if(disabled) {
			btnGuardar.setSclass("");
			btnGuardar.setHeight("30px");
			btnGuardar.setImage("resources/mp_guardarDisabled.png");
			btnGuardar.setStyle("width:120px !important");
		}else {
			btnGuardar.setSclass("btnCommandL");
			btnGuardar.setImage("resources/mp_guardarEnabled.png");
		}		
	}
	
	/**
	 * Habilita/Deshabilita el Button Guardar
	 * @param disabled
	 * @throws Exception
	 */
	private void disabled_btnAnular(Button btnAnular, boolean disabled)throws Exception{
		
		btnAnular.setDisabled(disabled);
		if(disabled) {
			btnAnular.setSclass("");
			btnAnular.setWidth("120px");
			btnAnular.setHeight("30px");
			btnAnular.setImage("resources/mp_anulacionDisabled.png");	
		}else {
			btnAnular.setSclass("btnCommandL");
			btnAnular.setImage("resources/mp_anular.png");	
		}		
	}
	
	/**
	 * Habilita/Deshabilita el Button Guardar
	 * @param disabled
	 * @throws Exception
	 */
	private void disabled_btnImprimir(Button btnImprimir, boolean disabled)throws Exception{
		
		btnImprimir.setDisabled(disabled);
		if(disabled) {
			btnImprimir.setSclass("");
			btnImprimir.setWidth("120px");
			btnImprimir.setHeight("30px");
			btnImprimir.setImage("resources/mp_imprimir.png");	
		}else {
			btnImprimir.setSclass("btnCommandL");
			btnImprimir.setImage("resources/mp_imprimir.png");	
		}		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void autoRefreshMapaBus()throws Exception{
		try {

			Date fechaActual = Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
			Date fechaSalida = detalleItinerarioIda.getFechaPartida();
			
			//Valida la ejecucion de l atarea
			if(fechaSalida.getTime() >= fechaActual.getTime()) {
				int minutos = 3; //Se ejecuta cada 3 minutos - configurar par�metro
				int ltime = (int) (Constantes.MILISEGUNDOS_X_MINUTO * minutos);
		        timerRefreshMapa=new Timer(ltime);
		        timerRefreshMapa.setRepeats(true);
		        timerRefreshMapa.start();
		        log.info("Iniciando proceso automatico de actualización del Mapa...");
		        System.out.println("Iniciando proceso automatico de actualización de Mapa...");
				timerRefreshMapa.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							if(divPaso_2.isVisible()) {
								//Actualiza el Mapa
					        	onRefreshMap();
					        	String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
					        		   _message += " Ejecuci\\u00F3n de proceso autom\\u00E1tico que actualiza el Mapa del bus, cada "+ String.valueOf(minutos) +" minuto(s)...";
					            System.out.println(_message);
					            log.info("autoRefreshMapaBus "+ _message);
							}else {
								timerRefreshMapa.stop();
								String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
								       _message +=  " Finalizando Proceso automático...";
					            System.out.println(_message);
					            log.info("autoRefreshMapaBus "+ _message);
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.info(e);
							if(timerRefreshMapa !=null) {
								timerRefreshMapa.stop();
								String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
									   _message += " Ocurri\\u00F3 un error, se ha detenido el la tarea. Error:"+ e.getMessage();
								System.out.println(_message);
								log.error(_message);
							}
						}
					}
				});			
				wndVentaReservaNew.appendChild(timerRefreshMapa);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			chbxVtaUpdateMapAuto.setChecked(false);
			if(timerRefreshMapa !=null) {
				timerRefreshMapa.stop();
				String _message = Constantes.FORMAT_DATE_TIME_24H.format(new Date());
				   _message += " Ocurrió un error, se ha detenido el la tarea. Error:"+ e.getMessage();
			System.out.println(_message);
			log.error(_message);
			}
		}
	}
	
	/**
	 *
	 * @throws Exception
	 */
	private void ononCheck_chbxVtaIdaVuelta(Event event)throws Exception{
		if(!chbxVtaIdaVuelta.isChecked()) {
			liberarAsientos(ltbxVtaAsientosSeleccionados);
			rdBusqIda.setChecked(true);
			onCheck_rdBusqIda();
			detalleItinerarioVuelta = null;
			mapaAsientosRetorno = null;
			tabVtaVueltaMapa.setDisabled(true);	
			gridVtaInfoViajeVuelta.setVisible(false);
			lblVtaVueltaDestino_fecha.setValue("");		
			cleanDatosInfoViaje(false, true);				
			tabVtaIdaMapa.setSelected(true);
			onClick_btnVtaActualizarMapa();
			enabledAllBtnOptionsByAsiento();
			chbxVtaIdaVuelta.setDisabled(true);
		}		
	}
	
	private String getTooltiptextAsiento(VentaPasaje venta) {
		int width = 20;
		String tooltiptextAsiento = "";
		tooltiptextAsiento += CreateDocument.getLineText(0, width, "ASIENTO");		
		tooltiptextAsiento += ": "+ venta.getNumeroAsiento();
		tooltiptextAsiento += "\r\n";
		tooltiptextAsiento += CreateDocument.getLineText(0, width-1, "PASAJERO");
		tooltiptextAsiento += ": "+ venta.getPasajero().toString();
		if(venta.getNumeroBoleto() != null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-7, "COMPROBANTE");
			tooltiptextAsiento += ": "+ venta.getNumeroBoleto();
		}
		tooltiptextAsiento += "\r\n";
		tooltiptextAsiento += CreateDocument.getLineText(0, width+4, "RUTA");
		tooltiptextAsiento += ": "+ venta.getRuta().toString();
		if(venta.getAgenciaPartida() !=null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-2, "EMBARQUE");
			tooltiptextAsiento += ": "+ venta.getAgenciaPartida().getNombreCorto();
		}		
		if(venta.getAgenciaLlegada() !=null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-5, "DESEMBARQUE");
			tooltiptextAsiento += ": "+ venta.getAgenciaLlegada().getDenominacion();
		}
		tooltiptextAsiento += "\r\n";
		tooltiptextAsiento += CreateDocument.getLineText(0, width+1, "AG. VENTA");
		tooltiptextAsiento += ": "+ venta.getAgencia().getDenominacion();
		tooltiptextAsiento += "\r\n";
		tooltiptextAsiento += CreateDocument.getLineText(0, width-5, "USUARIO VENTA");
		tooltiptextAsiento += ": "+ venta.getUsuario().getLogin().toUpperCase();				
		if(venta.getFechaInsercion() !=null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-3, "FECHA VENTA");
			tooltiptextAsiento += ": "+ Constantes.FORMAT_DATE_TIME_24H.format(venta.getFechaInsercion());			
		}
		if(venta.getPasajero().getTelefono() != null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-1, "TELEFONO");
			tooltiptextAsiento += ": "+ venta.getPasajero().getTelefono();
		}
		if(venta.getObservaciones() != null) {
			tooltiptextAsiento += "\r\n";
			tooltiptextAsiento += CreateDocument.getLineText(0, width-6, "OBSERVACIONES");
			tooltiptextAsiento += ": "+ venta.getObservaciones();
		}
			
		
//		tooltiptextAsiento += "ASIENTO	   :	" + venta.getNumeroAsiento().toString();
//		tooltiptextAsiento += "\r\nPASAJERO 	:	" + venta.getPasajero().toString();
//		if(venta.getNumeroBoleto() != null)
//			tooltiptextAsiento += "\r\nCOMPROBANTE:	" + venta.getNumeroBoleto();
//		tooltiptextAsiento += "\r\nRUTA 		:	" + venta.getRuta().toString();
//		if(venta.getAgenciaPartida() !=null)
//			tooltiptextAsiento += "\r\nEMBARQUE   : 	" + venta.getAgenciaPartida().getNombreCorto();
//		if(venta.getAgenciaLlegada() !=null)
//			tooltiptextAsiento += "\r\nDESEMBARQUE:	" + venta.getAgenciaLlegada().getDenominacion();
//		tooltiptextAsiento += "\r\nAG.VENTA	:	" + venta.getAgencia().getDenominacion();
//		tooltiptextAsiento += "\r\nUSUARIO	:	" + venta.getUsuario().getLogin().toUpperCase();
//		if(venta.getFechaInsercion() !=null)
//			tooltiptextAsiento += "\r\nFECHA VENTA:	" + Constantes.FORMAT_DATE_TIME_24H.format(venta.getFechaInsercion());
		
		return tooltiptextAsiento;
	}
	
	/**
	 * Da formato al numero de asiento
	 * @param numeroAsiento : Numero de asiento
	 * @return
	 */
	private String getFormatAsiento(Integer numeroAsiento) {
		String fnumeroAsiento = String.format("%0" + 2 + "d", numeroAsiento);
		
		return fnumeroAsiento;
	}
}
