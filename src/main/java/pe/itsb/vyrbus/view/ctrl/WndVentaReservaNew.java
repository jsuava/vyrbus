/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 1 feb. 2024
 * Hora			: 12:16:05
 */
package pe.itsb.vyrbus.view.ctrl;

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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.FormaPago;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import pe.itsb.vyrbus.model.bean.ItinerarioAgenciaPartida;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.OperadorTarjetaCredito;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TarifaRegular;
import pe.itsb.vyrbus.model.bean.TarjetaCredito;
import pe.itsb.vyrbus.model.bean.TipoDocumento;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientosID;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.bean.VentaTramo;
import pe.itsb.vyrbus.service.exceptions.DuplicateSeatException;
import pe.itsb.vyrbus.service.exceptions.SaldoInsuficienteException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.Asiento;
import pe.itsb.vyrbus.service.mappers.Cafeteria;
import pe.itsb.vyrbus.service.mappers.Coordenada;
import pe.itsb.vyrbus.service.mappers.Monitor;
import pe.itsb.vyrbus.service.mappers.SecuenciaTramo;
import pe.itsb.vyrbus.service.mappers.ServiciosHigienicos;
import pe.itsb.vyrbus.service.util.AsientoPool;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.RESTCiva;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.service.util.WSCruzdelsur;
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
	private Label lblVtaIdaTarifa;
	private Grid gridVtaInfoViajeVuelta;
	private Label lblVtaVueltaDestino_fecha;
	private Combobox cmbVtaVueltaEmbarque_hora;
	private Combobox cmbVtaVueltaDesembarque_hora;
	private Label lblVtaVueltaAsiento;
	private Label lblVtaVueltaTarifa;
	private Listbox ltbxVtaAsientosSeleccionados;
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
	private Doublebox dbxVtaTotalPagar;
	private Combobox cmbVtaTipoFormaPago;
	private Combobox cmbVtaOperadorTarjeta;
	private Combobox cmbVtaTarjeta;
	private Textbox txtVtaNumeroOperacion;
	private Combobox cmbVtaAutorizaDescuento;
	private Textbox txtVtaAutorizaDescuentoCodigo;
	private Button btnVtaGuardar;
	private Button btnVtaCancelar;
	
	
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPisoHor.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPisoHor.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;

	private int action = Constantes.FAILURE;
	private int actionc = Constantes.FAILURE;
	private String prefijoAsiento="";
	private String key = "-1";
	private Map<String, Asiento> mapaAsientosIda = null;
	private Map<String, Asiento> mapaAsientosRetorno = null;
	private DetalleItinerario detalleItinerarioIda = null;
	private DetalleItinerario detalleItinerarioVuelta = null;
	
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
			
			dbxVtaTotalPagar.setLocale(Locale.US);
			
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
			lblVtaIdaTarifa = (Label)this.getFellow("lblVtaIdaTarifa");
			gridVtaInfoViajeVuelta = (Grid)this.getFellow("gridVtaInfoViajeVuelta");
			lblVtaVueltaDestino_fecha = (Label)this.getFellow("lblVtaVueltaDestino_fecha");
			cmbVtaVueltaEmbarque_hora = (Combobox)this.getFellow("cmbVtaVueltaEmbarque_hora");
			cmbVtaVueltaDesembarque_hora = (Combobox)this.getFellow("cmbVtaVueltaDesembarque_hora");
			lblVtaVueltaAsiento = (Label)this.getFellow("lblVtaVueltaAsiento");
			lblVtaVueltaTarifa = (Label)this.getFellow("lblVtaVueltaTarifa");
			ltbxVtaAsientosSeleccionados = (Listbox)this.getFellow("ltbxVtaAsientosSeleccionados");
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
			dbxVtaTotalPagar = (Doublebox)this.getFellow("dbxVtaTotalPagar");
			cmbVtaTipoFormaPago = (Combobox)this.getFellow("cmbVtaTipoFormaPago");
			cmbVtaOperadorTarjeta = (Combobox)this.getFellow("cmbVtaOperadorTarjeta");
			cmbVtaTarjeta = (Combobox)this.getFellow("cmbVtaTarjeta");
			txtVtaNumeroOperacion = (Textbox)this.getFellow("txtVtaNumeroOperacion");
			cmbVtaAutorizaDescuento = (Combobox)this.getFellow("cmbVtaAutorizaDescuento");
			txtVtaAutorizaDescuentoCodigo = (Textbox)this.getFellow("txtVtaAutorizaDescuentoCodigo");
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
			if(i == 1){
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
	
								HashMap<String, String> propiedades = new HashMap<>();
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
			}
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
//		txtIdEmpresaIda.setValue(detalleItinerarioIda.getItinerario().getEmpresa().getId().toString());
		/*	Si se trata de un ida y vuelta	*/
		if(rdBusqIdaVuelta.isChecked()){
			detalleItinerarioVuelta = ltbxBusqVuelta.getSelectedItem().getValue();
			crearEstructura(detalleItinerarioVuelta.getItinerario().getServicio().getId(), gpbxVtaVueltaMapa, true, detalleItinerarioVuelta, mapaAsientosRetorno, null);
//			txtIdEmpresaRetorno.setValue(detalleItinerarioRetorno.getItinerario().getEmpresa().getId().toString());
		}
		
		lblVtaIdaDestino_fecha.setValue(detalleItinerarioIda.getRuta().getDestino() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioIda.getFechaPartida()));
		loadPuntoEmbarque(detalleItinerarioIda, cmbVtaIdaEmbarque_hora);
		loadPuntoDesembarque(detalleItinerarioIda, cmbVtaIdaDesembarque_hora);
		lblVtaIdaAsiento.setValue("-");
		lblVtaIdaTarifa.setValue("-");
		if(detalleItinerarioVuelta!=null) {
			lblVtaVueltaDestino_fecha.setValue(detalleItinerarioVuelta.getRuta().getDestino() +" - "+ Constantes.FORMAT_DATE.format(detalleItinerarioVuelta.getFechaPartida()));
			loadPuntoEmbarque(detalleItinerarioVuelta, cmbVtaVueltaEmbarque_hora);
			loadPuntoDesembarque(detalleItinerarioVuelta, cmbVtaVueltaDesembarque_hora);			
			lblVtaVueltaAsiento.setValue("-");
			lblVtaVueltaTarifa.setValue("-");
		}
		
		divPaso_1.setVisible(false);
		divPaso_2.setVisible(true);
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

		if(cmbVtaTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
			TipoFormaPago tipoFormaPago = (TipoFormaPago)cmbVtaTipoFormaPago.getSelectedItem().getValue();
			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CREDITO){
				cmbVtaOperadorTarjeta.getItems().clear();
				UtilData.cargarDataCombo(cmbVtaOperadorTarjeta, OperadorTarjetaCredito.class, false);
				cmbVtaOperadorTarjeta.setDisabled(false);
				txtVtaNumeroOperacion.setDisabled(false);
			}else if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_TRANSFERENCIA || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_YAPE)
				txtVtaNumeroOperacion.setDisabled(false);
			else{
				txtVtaNumeroOperacion.setDisabled(true);
				cmbVtaOperadorTarjeta.setDisabled(true);
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
		lblVtaIdaAsiento.setValue("-");
		lblVtaIdaTarifa.setValue("-");
		lblVtaVueltaAsiento.setValue("-");
		lblVtaVueltaTarifa.setValue("-");
		
		if(ltbxVtaAsientosSeleccionados.getItemCount()>0 && ltbxVtaAsientosSeleccionados.getSelectedCount() ==0)
			ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
		
		if(ltbxVtaAsientosSeleccionados.getSelectedCount()>0) {			
			DetalleItinerario detalleItinerario = ltbxVtaAsientosSeleccionados.getSelectedItem().getValue();
			if(detalleItinerario.getEsIda()) {
				lblVtaIdaAsiento.setValue(detalleItinerario.getAsiento());
				lblVtaIdaTarifa.setValue(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));				
			}else {
				lblVtaVueltaAsiento.setValue(detalleItinerario.getAsiento());
				lblVtaVueltaTarifa.setValue(Util.toNumberFormat(detalleItinerario.getTarifa(), 2));
			}			
		}		
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	private void onClick_btnVtaCancelar()throws Exception{		
		cleanDatosVenta();
		divPaso_2.setVisible(false);
		divPaso_1.setVisible(true);
		
		liberarAsientos();
		
		//Actualiza los resultados de la busqueda
//		onClick_btnBuscar();
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
	 * 
	 * @throws Exception
	 */
	private void cleanDatosVenta()throws Exception{
		
		detalleItinerarioIda = null;
		detalleItinerarioVuelta = null;
		mapaAsientosIda = null;
		mapaAsientosRetorno = null;
		
		tabVtaVueltaMapa.setDisabled(true);
		
		lblVtaTipoComprobante.setValue(" ");
		lblVtaNumeroComprobante.setValue(" ");
		lblVtaInfoTipoOperacion.setValue(" ");
		
		btnVtaProgramarServicios.setDisabled(true);
		btnVtaManifiestoPasajetos.setDisabled(true);
		btnVtaCarpetaDespacho.setDisabled(true);
		btnVtaContribucionByAgencia.setDisabled(true);
		btnVtaReimpresion.setDisabled(true);
		btnVtaPostergar.setDisabled(true);
		btnVtaAnular.setDisabled(true);
		btnVtaLeyendaAsientos.setDisabled(true);
		
		rdVtaVenta.setChecked(false);
		rdVtaReserva.setChecked(false);
		rdVtaPostergacion.setChecked(false);
		rdVtaPostergacionFA.setChecked(false);
		rdVtaCambioNombre.setChecked(false);
		
		lblVtaIdaDestino_fecha.setValue("");
		Util.limpiarCombobox(cmbVtaIdaEmbarque_hora);
		Util.limpiarCombobox(cmbVtaIdaDesembarque_hora);
		lblVtaIdaAsiento.setValue("");
		lblVtaIdaTarifa.setValue("");
		
		gridVtaInfoViajeVuelta.setVisible(false);
		lblVtaVueltaDestino_fecha.setValue("");
		Util.limpiarCombobox(cmbVtaVueltaEmbarque_hora);
		Util.limpiarCombobox(cmbVtaVueltaDesembarque_hora);
		lblVtaVueltaAsiento.setValue("");
		lblVtaVueltaTarifa.setValue("");		
		
		Util.limpiarListbox(ltbxVtaAsientosSeleccionados);
		
		cleanDatosPasajero(true);
		enabledControlsFactura(false);
		cleanDatosInfante();
		cleanDatosPago();
		
	}
	
	private void cleanDatosPasajero(boolean cleanDatosFactura)throws Exception{		
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
			txtVtaFacRuc.setText("");
			txtVtaFacRazonSocial.setText("");
			txtVtaFacDireccion.setText("");
		}				
	}
	
	private void enabledControlsFactura(boolean enabled)throws Exception{
		txtVtaFacRuc.setText("");
		txtVtaFacRazonSocial.setText("");
		txtVtaFacDireccion.setText("");
		
		txtVtaFacRuc.setVisible(enabled);
		txtVtaFacRazonSocial.setVisible(enabled);
		txtVtaFacDireccion.setVisible(enabled);
		
	}
	
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
	
	private void cleanDatosPago()throws Exception{
		
		dbxVtaTotalPagar.setValue(null);
		cmbVtaTipoFormaPago.setSelectedIndex(-1);
		cmbVtaOperadorTarjeta.setSelectedIndex(-1);
		Util.limpiarCombobox(cmbVtaTarjeta);
		txtVtaNumeroOperacion.setText("");
		cmbVtaAutorizaDescuento.setSelectedIndex(-1);
		txtVtaAutorizaDescuentoCodigo.setText("");
		
		
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

//			mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param asiento
	 * @throws Exception
	 */
	private void validarOperacionAsiento(Asiento asiento)throws Exception{
		
		
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

		try{
			/*	Elimina el asiento de la lista de asientos seleccionados y desbloquea el asiento*/
			if(removerAsientoSeleccionado(key, mapaAsientos, asientoSeleccionado.getDetalleItinerario())) {
				onSelect_ltbxVtaAsientosSeleccionados();
				calcularTotalPago();
				return;
			}

			/*	validamos la cantidad maxima de asientos seleccionados	*/
			if(ltbxVtaAsientosSeleccionados.getItemCount()<=Constantes.MAX_SEAT_SELECTED){
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
						if(esIda)
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), null);
						else
							onRefreshMapaAsientos(mapaAsientos, asientoSeleccionado.getDetalleItinerario(), null);
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
						listitemAsientos.setValue(detalle);
						ltbxVtaAsientosSeleccionados.appendChild(listitemAsientos);
						
						if(ltbxVtaAsientosSeleccionados.getSelectedCount()==0) {
							ltbxVtaAsientosSeleccionados.setSelectedIndex(0);
							onSelect_ltbxVtaAsientosSeleccionados();
						}
						
						calcularTotalPago();
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
	 * Realiza la eliminacion del asiento seleccionado y luego lo desbloquea.
	 * @param seatSelected	: Asiento seleccionado.
	 * @return True si el asiento existe en la lista de asientos seleccionados.
	 */
	private boolean removerAsientoSeleccionado(String key, Map<String, Asiento> mapaAsientos, DetalleItinerario detalleItinerario){
		try{
			String[] buffer = key.split("-");	//Almacenamos en un array el asiento y el piso
			List<Listitem> items = ltbxVtaAsientosSeleccionados.getItems();
			for(int i=0; i<items.size(); i++){
				Listitem listitem = items.get(i);
				if(((DetalleItinerario)listitem.getValue()).getItinerario().getId().intValue()==detalleItinerario.getItinerario().getId().intValue()
						&& ((DetalleItinerario)listitem.getValue()).getAsiento().equals(buffer[0])
						&& ((DetalleItinerario)listitem.getValue()).getPiso().equals(buffer[1])){
					ltbxVtaAsientosSeleccionados.removeItemAt(i);
					if(mapaAsientos.get(key).getEstadoAsiento().intValue()==Constantes.ASIENTO_BLOQUEADO){
						mapaAsientos.get(key).setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
						mapaAsientos.get(key).setSrc(Constantes.ICON_DISPONIBLE+buffer[0]+Constantes.IMAGE_EXTENSION);
					}
					/*	Habilita o deshablita el boton para pasar a la siguiente pestana	*/
//					if(lbxVtaAsientosSeleccionados.getItems().size()==0)
//						btnNextTabVenta.setDisabled(true);
//					else
//						btnNextTabVenta.setDisabled(false);

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
		for(Listitem item: ltbxVtaAsientosSeleccionados.getItems()) {
			DetalleItinerario detalleItinerario = item.getValue();
			totalPago += (detalleItinerario.getTarifa()!=null?detalleItinerario.getTarifa():.00);			
		}		
		
		dbxVtaTotalPagar.setValue(totalPago);
		
		
	}
	
	
	
	
}
