/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 20:06:02
 */
package pe.itsb.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.CuponPromocional;
import pe.itsb.vyrbus.model.bean.DetalleItinerario;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TarifaByAsiento;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoDetalle;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezadoDetalle;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaPromocion;
import pe.itsb.vyrbus.model.bean.TarifarioByAsientoByAvanceVentas;
import pe.itsb.vyrbus.model.bean.TarifarioByAsientoSubByAvanceVentas;
import pe.itsb.vyrbus.model.bean.TipoAsiento;
import pe.itsb.vyrbus.model.bean.TipoPrecio;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.mappers.Asiento;
import pe.itsb.vyrbus.service.mappers.Cafeteria;
import pe.itsb.vyrbus.service.mappers.Coordenada;
import pe.itsb.vyrbus.service.mappers.Monitor;
import pe.itsb.vyrbus.service.mappers.SecuenciaTramo;
import pe.itsb.vyrbus.service.mappers.ServiciosHigienicos;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;

import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author abant
 *
 */
public class WndTarifaByAsiento extends WndBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Combobox cmbEmpresa;
	private Combobox cmbPantillaEncabezado;
	private Combobox cmbOrigen;
	private Combobox cmbDestino;
	private Listbox ltbxNuevasTarifas;
	private Listbox ltbxServicios;
	private Datebox dtbxDesde;
	private Datebox dtbxHasta;
	private Combobox cmbServicio;
	private Window wndTarifaByAsiento;
	private Button btnCrearTarifario;
	private Button btnNuevoTarifario;
	private Button btnAgregarServicio;
	private Button btnAgregarRuta;
	private Label lblTipoPrecio;
	private Tabpanel tabPanelEdicionServicio;
	private Checkbox chbxReemplazarPromociones;
	private Div divDestRatioDias;
	private Intbox itbxRatioDiasPartida;
	private Combobox cmbTipoDescuento;
	private Doublebox dblDescuentoMaximoSoles;
	private Textbox txtNombreDescuento;
	private Column colGuardar;
	
	private Listhead listhead; 
	private List<TarifaByAsientoPlantillaEncabezadoDetalle>resultPlantillaEncabezadoDet;
	private List<TarifaByAsiento>listTarifas;
	private Menupopup menupopupTarifas= new Menupopup();
	private Menupopup menupopupServicios= new Menupopup();
	private TarifaByAsientoPlantillaEncabezado tarifaPlantillaEncabezado;
	
	/*Edicion de tarifas*/
	private Datebox dtbxED_Fecha;
	private Combobox cmbED_servicio;
	private Combobox cmbED_origen;
	private Combobox cmbED_destino;
	
	private Window wndEdicionTarifa;
    private Textbox txtAsientosSuite;
    private Combobox cmbTipoTarifaSuite;
    private Doublebox dbxValorTarifaSuite;
    private Textbox txtAsientosCamaSemicama;
    private Combobox cmbTipoTarifaCamaSemicama;
    private Doublebox dbxValorTarifaCamaSemicama;
    private Checkbox chbxTodosSuites;
	private Checkbox chbxTodosCasmaSemicama;	
	private Doublebox dbxIncRebAsientoSuite;	
	private Doublebox dbxIncRebAsientoCamaSemicama;
	private Radio rdIncremento;
	private Radio rdRebaja;
	
	private Map<String, Asiento> mapAsientosId = null;	
	private EventListener oEventListenerFilter;
	private Map<String, Asiento> mapaAsientos = null;
	private String prefijoAsiento="";
	private static final String IMAGE_PRIMER_PISO = "resources/mapa/bus_primerPiso.png";
	private static final String IMAGE_SEGUNDO_PISO = "resources/mapa/bus_segundoPiso.png";
	private static final int TIPO_ASIENTO = 0;
	private static final int TIPO_MONITOR = 1;
	private static final int TIPO_CAFETERIA = 2;
	
	private Tree  tree=new Tree();
	private Treecols treecols=new Treecols();
	private Treechildren treechildrenRutas=null;
	private Grid grid= new Grid();
	
	private TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas;
	private TarifaByAsiento tarifaByAsiento;
	private List<TarifaByAsiento>listED_TarifaByAsiento;
	private Ruta ruta;
	
	private Listbox ltbxTarifarioServicio= new Listbox();
	private Listbox ltbxPlantillaPromos= new Listbox();
	
	private boolean habilitarEdicionTarifas=false; //Determina si esta permitida o no la edicion de la tarifa del servicio
	
	/*Mantenimineto de plantillas de asientos*/
	private Tab tabCPA_ListPlantillaAsientos;
	private Tab tabCPA_MantenimientoPlantillaAsientos;
	private Combobox cmbBusqCPA_empresa;
	private Listbox listCPA_listado;
	private Combobox cmbCPA_empresa;
	private Textbox txtCPA_nombre;
	private Combobox cmbCPA_tipoPrecio;
	private Combobox cmbCPA_TipoAsiento;
	private Textbox txtCPA_Asientos;
	private Button btnCPA_agregar;
	private Button btnCPA_Nuevo;
	private Button btnCPA_Guardar;
	private Button btnCPA_Cancelar;
	private Listbox lstCPA_plantillaAsientosDetalle;
	private TarifaByAsientoPlantillaEncabezado cpa_tarifaPlantillaEncabezado;
	
	private Date fechaPartida;
	
	/*Codigos promocionales*/
	private Tab tabCP_mantenimiento;
//	private Tab tabCP_lista;
	private Datebox dtbxCP_FechaInicio;
	private Datebox dtbxCP_FechaFin;
	private Checkbox chbxCP_omitirFechas;
	private Textbox txtCP_busqDenominacion;
	private Listbox ltbxCP_codigosPromos;
	
	private Textbox txtCP_denominacion;
	private Textbox txtCP_rutas;
	private Textbox txtCP_servicios;
	private Textbox txtCP_canalesVenta;
	private Textbox txtCP_asientos;
	private Textbox txtCP_tiposAsiento;
	private Doublebox dbxCP_valorDescuento;
	private Textbox txtCP_codigo;
	private Combobox cmbCP_tipoDescuento;
	private Doublebox dbxCP_valorMaximoDesciento;
	private Datebox dtbxCP_validoDesde;
	private Datebox dtbxCP_validoHasta;
	private Button btnCP_busqRutas;
	private Button btnCP_busqServicios;
	private Button btnCP_busqCanalesVenta;
	private Button btnCP_busqTiposAsientos;
	private Intbox itbxCP_cantMaxPorServicio;
	private Button btnCP_Nuevo;
	private Button btnCP_Guardar;
	private Button btnCP_Cancelar;	
	private Checkbox chbxTipoFormaPago_PagoEfectivo;
	private Radio rdTodas;
	private Radio rdIdaVuelta;
	private Radio rbNoIdaVuelta;
	private Checkbox chbxIdaVuelta_AplicarIda;
	private Checkbox chbxIdaVuelta_AplicarRetorno;
	private Textbox txtCP_busqCodigo;
	
	private Window wndCriterios;	
	private Listbox lbxContenedorLeft;
	private Listbox lbxContenedorRight;
	private CuponPromocional cuponPromocional;
	private Menupopup menupopup= new Menupopup();
		
	
	
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		cmbEmpresa = (Combobox)this.getFellow("cmbEmpresa");
		cmbPantillaEncabezado=(Combobox)this.getFellow("cmbPantillaEncabezado");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		cmbDestino=(Combobox)this.getFellow("cmbDestino");
		ltbxNuevasTarifas=(Listbox)this.getFellow("ltbxNuevasTarifas");
		ltbxServicios=(Listbox)this.getFellow("ltbxServicios");
		dtbxDesde=(Datebox)this.getFellow("dtbxDesde");
		dtbxHasta=(Datebox)this.getFellow("dtbxHasta");
		cmbServicio=(Combobox)this.getFellow("cmbServicio");
		wndTarifaByAsiento=(Window)this.getFellow("wndTarifaByAsiento");
		btnCrearTarifario=(Button)this.getFellow("btnCrearTarifario");
		btnNuevoTarifario=(Button)this.getFellow("btnNuevoTarifario");
		btnAgregarServicio=(Button)this.getFellow("btnAgregarServicio");
		btnAgregarRuta=(Button)this.getFellow("btnAgregarRuta");
		lblTipoPrecio=(Label)this.getFellow("lblTipoPrecio");
		tabPanelEdicionServicio=(Tabpanel)this.getFellow("tabPanelEdicionServicio");
		chbxReemplazarPromociones=(Checkbox)this.getFellow("chbxReemplazarPromociones");
		divDestRatioDias=(Div)this.getFellow("divDestRatioDias");
		itbxRatioDiasPartida=(Intbox)this.getFellow("itbxRatioDiasPartida");
		cmbTipoDescuento=(Combobox)this.getFellow("cmbTipoDescuento");
		dblDescuentoMaximoSoles=(Doublebox)this.getFellow("dblDescuentoMaximoSoles");
		txtNombreDescuento=(Textbox)this.getFellow("txtNombreDescuento");
		colGuardar=(Column)this.getFellow("colGuardar");
		
		/*Edicion de tarifas*/
		dtbxED_Fecha=(Datebox)this.getFellow("dtbxED_Fecha");
		cmbED_servicio=(Combobox)this.getFellow("cmbED_servicio");
		cmbED_origen=(Combobox)this.getFellow("cmbED_origen");
		cmbED_destino=(Combobox)this.getFellow("cmbED_destino");
		
		/*Mantenimineto de plantillas de asientos*/
		tabCPA_ListPlantillaAsientos= (Tab)this.getFellow("tabCPA_ListPlantillaAsientos");
		tabCPA_MantenimientoPlantillaAsientos= (Tab)this.getFellow("tabCPA_MantenimientoPlantillaAsientos");
		cmbBusqCPA_empresa = (Combobox)this.getFellow("cmbBusqCPA_empresa");
		listCPA_listado= (Listbox)this.getFellow("listCPA_listado");
		cmbCPA_empresa = (Combobox)this.getFellow("cmbCPA_empresa");
		txtCPA_nombre= (Textbox)this.getFellow("txtCPA_nombre");
		cmbCPA_tipoPrecio= (Combobox)this.getFellow("cmbCPA_tipoPrecio");
		cmbCPA_TipoAsiento= (Combobox)this.getFellow("cmbCPA_TipoAsiento");
		txtCPA_Asientos= (Textbox)this.getFellow("txtCPA_Asientos");
		btnCPA_agregar= (Button)this.getFellow("btnCPA_agregar");
		btnCPA_Nuevo = (Button)this.getFellow("btnCPA_Nuevo");
		btnCPA_Guardar=(Button)this.getFellow("btnCPA_Guardar");
		btnCPA_Cancelar=(Button)this.getFellow("btnCPA_Cancelar");
		lstCPA_plantillaAsientosDetalle= (Listbox)this.getFellow("lstCPA_plantillaAsientosDetalle");
		
		/*Cupones promocionales*/
		tabCP_mantenimiento=(Tab)this.getFellow("tabCP_mantenimiento");
//		tabCP_lista=(Tab)this.getFellow("tabCP_lista");
		dtbxCP_FechaInicio= (Datebox)this.getFellow("dtbxCP_FechaInicio");
		dtbxCP_FechaFin=(Datebox)this.getFellow("dtbxCP_FechaFin");
		chbxCP_omitirFechas=(Checkbox)this.getFellow("chbxCP_omitirFechas");
		txtCP_busqDenominacion=(Textbox)this.getFellow("txtCP_busqDenominacion");
		ltbxCP_codigosPromos=(Listbox)this.getFellow("ltbxCP_codigosPromos");		
		txtCP_denominacion=(Textbox)this.getFellow("txtCP_denominacion");
		txtCP_rutas=(Textbox)this.getFellow("txtCP_rutas");
		txtCP_servicios=(Textbox)this.getFellow("txtCP_servicios");
		txtCP_canalesVenta=(Textbox)this.getFellow("txtCP_canalesVenta");
		txtCP_asientos=(Textbox)this.getFellow("txtCP_asientos");
		txtCP_tiposAsiento=(Textbox)this.getFellow("txtCP_tiposAsiento");
		dbxCP_valorDescuento=(Doublebox)this.getFellow("dbxCP_valorDescuento");
		cmbCP_tipoDescuento=(Combobox)this.getFellow("cmbCP_tipoDescuento");
		txtCP_codigo=(Textbox)this.getFellow("txtCP_codigo");
		dbxCP_valorMaximoDesciento=(Doublebox)this.getFellow("dbxCP_valorMaximoDesciento");
		dtbxCP_validoDesde=(Datebox)this.getFellow("dtbxCP_validoDesde");
		dtbxCP_validoHasta=(Datebox)this.getFellow("dtbxCP_validoHasta");
		btnCP_busqRutas=(Button)this.getFellow("btnCP_busqRutas");
		btnCP_busqServicios=(Button)this.getFellow("btnCP_busqServicios");
		btnCP_busqCanalesVenta=(Button)this.getFellow("btnCP_busqCanalesVenta");
		btnCP_busqTiposAsientos=(Button)this.getFellow("btnCP_busqTiposAsientos");
		btnCP_Nuevo=(Button)this.getFellow("btnCP_Nuevo");
		btnCP_Guardar=(Button)this.getFellow("btnCP_Guardar");
		btnCP_Cancelar=(Button)this.getFellow("btnCP_Cancelar");
		itbxCP_cantMaxPorServicio=(Intbox)this.getFellow("itbxCP_cantMaxPorServicio");
		chbxTipoFormaPago_PagoEfectivo =(Checkbox)this.getFellow("chbxTipoFormaPago_PagoEfectivo");
		rdTodas=(Radio)this.getFellow("rdTodas");
		rdIdaVuelta=(Radio)this.getFellow("rdIdaVuelta");
		rbNoIdaVuelta=(Radio)this.getFellow("rbNoIdaVuelta");
		chbxIdaVuelta_AplicarIda=(Checkbox)this.getFellow("chbxIdaVuelta_AplicarIda");
		chbxIdaVuelta_AplicarRetorno=(Checkbox)this.getFellow("chbxIdaVuelta_AplicarRetorno");
		txtCP_busqCodigo = (Textbox)this.getFellow("txtCP_busqCodigo");
		
		ltbxCP_codigosPromos.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onEvent_ltbxCP_cuponPromos();				
			}
		});
		rdTodas.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				chbxIdaVuelta_AplicarIda.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarIda.setChecked(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setChecked(rdIdaVuelta.isChecked());
			}
		});
		rdIdaVuelta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				chbxIdaVuelta_AplicarIda.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarIda.setChecked(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setChecked(rdIdaVuelta.isChecked());
			}
		});
		rbNoIdaVuelta.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				chbxIdaVuelta_AplicarIda.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setVisible(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarIda.setChecked(rdIdaVuelta.isChecked());
				chbxIdaVuelta_AplicarRetorno.setChecked(rdIdaVuelta.isChecked());
			}
		});
		
	}
	
	
	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarEmpresa(cmbEmpresa, true);
		UtilData.cargarDataCombo(cmbOrigen, Localidad.class, false);
		UtilData.cargarDataCombo(cmbDestino, Localidad.class, false);		
		UtilData.cargarDataCombo(cmbServicio, Servicio.class, true);
		
		if(cmbEmpresa.getItemCount()==2)
			cmbEmpresa.setSelectedIndex(1);
	
		cargarPlantillaEcabezado();
		dtbxDesde.setValue(new Date());
		dtbxHasta.setValue(new Date());
		
		/*Edicion de tarifas*/
		UtilData.cargarDataCombo(cmbED_servicio, Servicio.class, true);
		UtilData.cargarDataCombo(cmbED_origen, Localidad.class, true);
		UtilData.cargarDataCombo(cmbED_destino, Localidad.class, true);
		dtbxED_Fecha.setValue(new Date());
		
		/**Carga los tipos de descuento*/
		cmbTipoDescuento.appendChild(new Comboitem(Constantes.COMBO_LABEL_SELECCIONE));
		Comboitem comboitem= new Comboitem("PORCENTAJE");
		comboitem.setValue("P");
		cmbTipoDescuento.appendChild(comboitem);
		comboitem= new Comboitem("SOLES");
		comboitem.setValue("S");
		cmbTipoDescuento.appendChild(comboitem);
		
		dblDescuentoMaximoSoles.setLocale(Locale.US);
		
		//Manitenimiento de Plantilla de asientos
		UtilData.cargarEmpresa(cmbBusqCPA_empresa, true);
		UtilData.cargarEmpresa(cmbCPA_empresa, true);
		UtilData.cargarTipoPrecio(cmbCPA_tipoPrecio, false);
		UtilData.cargarTipoAsiento(cmbCPA_TipoAsiento, false);
		habilitarControlsMntoPlatilla(true);
		btnCPA_Nuevo.setDisabled(false);
		btnCPA_Guardar.setDisabled(true);
		btnCPA_Cancelar.setDisabled(false);
		if(cmbBusqCPA_empresa.getItemCount() ==2)
			cmbBusqCPA_empresa.setSelectedIndex(1);
		if(cmbCPA_empresa.getItemCount() == 2)
			cmbCPA_empresa.setSelectedIndex(1);
		
		//Codigos promocionales
		dtbxCP_FechaInicio.setValue(new Date());
		dtbxCP_FechaFin.setValue(new Date());
		
		/**Carga los tipos de descuento*/
		cmbCP_tipoDescuento.appendChild(new Comboitem(Constantes.COMBO_LABEL_SELECCIONE));
		comboitem= new Comboitem("PORCENTAJE");
		comboitem.setValue("P");
		cmbCP_tipoDescuento.appendChild(comboitem);
		comboitem= new Comboitem("SOLES");
		comboitem.setValue("S");
		cmbCP_tipoDescuento.appendChild(comboitem);
		
		habilitarControlsCuponPromo(false);
		dbxCP_valorDescuento.setLocale(Locale.US);
		dbxCP_valorMaximoDesciento.setLocale(Locale.US);
		btnCP_Guardar.setDisabled(true);
		
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void cargarPlantillaEcabezado()throws Exception{
		Util.limpiarCombobox(cmbPantillaEncabezado);
		UtilData.cargarGenericData(cmbPantillaEncabezado, false);
		List<TarifaByAsientoPlantillaEncabezado> result=ServiceLocator.getTarifaByAsientoPlantillaEncabezadoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		for(TarifaByAsientoPlantillaEncabezado tarifaPlantillaEncabezado :result){
			Comboitem comboitem= new Comboitem(tarifaPlantillaEncabezado.getDenominacion());
			comboitem.setValue(tarifaPlantillaEncabezado);
			cmbPantillaEncabezado.appendChild(comboitem);
			cmbPantillaEncabezado.setSelectedIndex(0);
		}
	}
	
	public void onSelect_plantillaEncabezado(){
		try {		
			colGuardar.setLabel("GUARDAR");
			
			divDestRatioDias.setVisible(false);
			itbxRatioDiasPartida.setText("");
			dblDescuentoMaximoSoles.setText("");
			txtNombreDescuento.setText("");
			cmbTipoDescuento.setSelectedIndex(0);
			
			chbxReemplazarPromociones.setVisible(false);
			chbxReemplazarPromociones.setDisabled(false);
			lblTipoPrecio.setValue("");
			menupopupTarifas.detach();
			Util.limpiarListbox(ltbxNuevasTarifas);
			listhead= new Listhead();
			if(ltbxNuevasTarifas.getListhead()!=null)
				ltbxNuevasTarifas.getListhead().detach();
			if(cmbPantillaEncabezado.getSelectedItem().getValue() instanceof TarifaByAsientoPlantillaEncabezado){
				
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("tarifaByAsientoPlantillaEncabezado", cmbPantillaEncabezado.getSelectedItem().getValue());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String>criteriosOrdenar= new ArrayList<>();
				criteriosOrdenar.add("orden");
				resultPlantillaEncabezadoDet=ServiceLocator.getTarifaByAsientoPlantillaEncabezadoDetalleManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				
				Listheader listheader= new Listheader("RUTA");
				listheader.setWidth("180px");
				listhead.appendChild(listheader);
				
				for(TarifaByAsientoPlantillaEncabezadoDetalle tarifaPlantillaEncabezadoDetalle :resultPlantillaEncabezadoDet){
					listheader= new Listheader();
					String nameColumn=tarifaPlantillaEncabezadoDetalle.getTipoAsiento().getDenominacion() + " - "+tarifaPlantillaEncabezadoDetalle.getAsientos();
					listheader.setLabel(nameColumn);
					int cantAsientos=0;
					if(tarifaPlantillaEncabezadoDetalle.getAsientos().indexOf(",")>=0)
						cantAsientos=tarifaPlantillaEncabezadoDetalle.getAsientos().split(",").length;
					else
						cantAsientos=tarifaPlantillaEncabezadoDetalle.getAsientos().split("-").length;
					int lengthtiptar=tarifaPlantillaEncabezadoDetalle.getTipoAsiento().getDenominacion().length();
					int widthHeader=(lengthtiptar+50)+(cantAsientos*18);
					listheader.setWidth(String.valueOf(widthHeader)+"px");
					listheader.setValue(tarifaPlantillaEncabezadoDetalle);
					listheader.setAlign("center");
					listhead.appendChild(listheader);					
				}
				
				ltbxNuevasTarifas.appendChild(listhead);
				
				
				((Comboitem)cmbOrigen.getChildren().get(0)).setLabel(Constantes.COMBO_LABEL_SELECCIONE);
				cmbOrigen.setText(Constantes.COMBO_LABEL_SELECCIONE);
				
				((Comboitem)cmbDestino.getChildren().get(0)).setLabel(Constantes.COMBO_LABEL_SELECCIONE);
				cmbDestino.setText(Constantes.COMBO_LABEL_SELECCIONE);
				//				
				TarifaByAsientoPlantillaEncabezado tarifaPlantillaEncabezado=cmbPantillaEncabezado.getSelectedItem().getValue();
				if(tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_REGULAR){
					lblTipoPrecio.setValue("CREACIÓN DE TARIFAS REGULARES");					
				}else if(tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_PROMOCIONAL){
					//Tarifas promocionales
					lblTipoPrecio.setValue("CREACIÓN DE TARIFAS PROMOCIONALES. asegúrese de haber creado las tarifas Regulares, caso contrario no se crearán las promocionales ");
					chbxReemplazarPromociones.setVisible(true);
					chbxReemplazarPromociones.setChecked(true);
				}else if(tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR){					
//					lblTipoPrecio.setValue(cmbPantillaEncabezado.getText());
					((Comboitem)cmbOrigen.getChildren().get(0)).setLabel(Constantes.COMBO_LABEL_TODOS);
					cmbOrigen.setText(Constantes.COMBO_LABEL_TODOS);
					((Comboitem)cmbDestino.getChildren().get(0)).setLabel(Constantes.COMBO_LABEL_TODOS);
					cmbDestino.setText(Constantes.COMBO_LABEL_TODOS);
					
					divDestRatioDias.setVisible(true);					
				}
				colGuardar.setLabel("GUARDAR " + cmbPantillaEncabezado.getText());
				
				cmbOrigen.setSelectedIndex(0);
				cmbDestino.setSelectedIndex(0);
				cmbOrigen.setFocus(true);
				cmbOrigen.select();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Agrega las rutas para la creacion de la tarifa
	 */
	public void onAgregarRuta(){
		try {
			
			if(cmbPantillaEncabezado.getSelectedIndex()<=0){
				DlgMessage.information("Debe de seleccionar la plantilla del encabezado para el ingreso de las tarifas.",cmbPantillaEncabezado);
				return;
			}
			
			Ruta ruta=null;
			TarifaByAsientoPlantillaEncabezado plantillaEncabezado=cmbPantillaEncabezado.getSelectedItem().getValue();
			boolean isDescuentoPromo=plantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR;
			
			if(isDescuentoPromo){
				if(cmbOrigen.getSelectedIndex()>0 && cmbDestino.getSelectedIndex()<=0){
					DlgMessage.information("Debe de seleccionar la Localidad Destino.",cmbDestino);
					return;
				}else if (cmbOrigen.getSelectedIndex()<=0 && cmbDestino.getSelectedIndex()>0){
					DlgMessage.information("Debe de seleccionar la Localidad Origen.",cmbOrigen);
					return;
				}				
			}else{
				if(cmbOrigen.getSelectedIndex()<=0){
					DlgMessage.information("Debe de seleccionar la Localidad Origen.",cmbOrigen);
					return;
				}else if(cmbDestino.getSelectedIndex()<=0){
					DlgMessage.information("Debe de seleccionar la Localidad Destino.",cmbDestino);
					return;
				}
			}
				
			if(cmbOrigen.getSelectedIndex()>0 && cmbDestino.getSelectedIndex()>0){
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("localidadOrigen", cmbOrigen.getSelectedItem().getValue());
				criteriosBusqueda.put("localidadDestino", cmbDestino.getSelectedItem().getValue());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Ruta> resultRuta=ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, null);
				if(resultRuta.size()==0){
					DlgMessage.information("No existe una ruta creada para el Origen y Destino seleccionado",cmbOrigen);
					return;
				}			
				ruta=resultRuta.get(0);	
			}
			
			//Valida que la ruta a agregar no existe en el listbox
			for(Listitem item:ltbxNuevasTarifas.getItems()){
				TarifaByAsiento tarifa=item.getValue();
				if(ruta!=null && tarifa.getRuta()!=null && tarifa.getRuta().getId().intValue()==ruta.getId().intValue()){
					DlgMessage.information("La Ruta "+ ruta.toString() + " ya existe en la lista.",cmbOrigen);
					return;
				}else if (ruta==null && ltbxNuevasTarifas.getItems().size()>0){
					DlgMessage.information("Ya existe una ruta con la misma denominación.",cmbOrigen);
					return;
				}
			}
			
			TarifaByAsiento tarifa= new TarifaByAsiento();
			tarifa.setRuta(ruta);
			
			Listitem item= new Listitem();
			Listcell cell= new Listcell(tarifa.getRuta()!=null?tarifa.getRuta().toString():"TODAS LAS RUTAS"); 
			item.appendChild(cell);
			for(TarifaByAsientoPlantillaEncabezadoDetalle plantillaEncabezadoDetalle: resultPlantillaEncabezadoDet){
				cell= new Listcell("0.00");
				cell.setStyle("font-size:12px !important;text-align:right !important");
				Doublebox dbbxTarifa= new Doublebox();
				dbbxTarifa.setVisible(false);
				dbbxTarifa.setWidth("90%");
				dbbxTarifa.setFormat("#,###.00");
				dbbxTarifa.setLocale(Locale.US);
				dbbxTarifa.setValue(0.00);
				cell.appendChild(dbbxTarifa);
				cell.setValue(plantillaEncabezadoDetalle);
				item.appendChild(cell);
				
				item.setContext(getMenucontexNuevasTarifas(item));
				item.setValue(tarifa);
				ltbxNuevasTarifas.appendChild(item);
				
				cmbOrigen.setFocus(true);
				cmbOrigen.select();
				
				cell.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							if(btnCrearTarifario.isDisabled()==false){
								onEvent_control(event);
								event.stopPropagation();
							}								
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}							
					}
				});
				
				dbbxTarifa.addEventListener(Events.ON_OK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
				dbbxTarifa.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private Menupopup getMenuContextServicio(final Listitem item)throws Exception{
		menupopupServicios= new Menupopup();
		
		Menuitem menuitem=new Menuitem("Eliminar Item","/resources/mp_removeEnabled.png");
		menuitem.setDisabled(btnCrearTarifario.isDisabled());
		menuitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					
					ltbxServicios.removeItemAt(item.getIndex());
					
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});		
		menupopupServicios.appendChild(menuitem);
		
		
		wndTarifaByAsiento.appendChild(menupopupServicios);
		return menupopupServicios;
	}
	
	/**
	 * Crea el menu contextual para el Listbox (Click derecho)
	 * @param ventaPasaje
	 * @return
	 * @throws Exception
	 */
	private Menupopup getMenucontexNuevasTarifas(final Listitem item)throws Exception{
		menupopupTarifas= new Menupopup();
		
		Menuitem menuitem=new Menuitem("Eliminar Item","/resources/mp_removeEnabled.png");	
		menuitem.setDisabled(btnCrearTarifario.isDisabled());
		menuitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					
					ltbxNuevasTarifas.removeItemAt(item.getIndex());
					
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});		
		menupopupTarifas.appendChild(menuitem);
		
		
		wndTarifaByAsiento.appendChild(menupopupTarifas);
		return menupopupTarifas;
	}
	/**
	 * Agrega los servicios a  la lista para la creacion de la tarifa
	 */
	public void onAgregarServicio(){
		try {
			if(cmbServicio.getSelectedItem().getValue() instanceof Servicio){				
				Servicio servicio=cmbServicio.getSelectedItem().getValue();
				List<TipoAsiento>listTipoAsientos=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(servicio.getId());
				servicio.setListTipoAsientos(listTipoAsientos);
				
				//Valida que el servicio a agregar no este agregado
				for(Listitem item: ltbxServicios.getItems()){
					Servicio _servicio=item.getValue();
					if(_servicio.getId().intValue()==servicio.getId().intValue()){
						DlgMessage.information("El Servicio "+servicio.getDenominacion()+" ya existe en la lista.");
						return;
					}
				}								
				
				Listitem item= new Listitem();
				Listcell cell= new Listcell(servicio.getDenominacion());
				item.appendChild(cell);
				
				item.setContext(getMenuContextServicio(item));
				item.setValue(servicio);
				ltbxServicios.appendChild(item);				
			}else{
				
				//Valida que el servicio a agregar no este agregado
				for(Listitem item: ltbxServicios.getItems()){
					Servicio _servicio=item.getValue();					
					for(Comboitem comboitem :cmbServicio.getItems()){
						if(comboitem.getValue()!=null && comboitem.getValue() instanceof Servicio){
							if(((Servicio)comboitem.getValue()).getId().intValue()==_servicio.getId().intValue()){
								DlgMessage.information("El Servicio "+((Servicio)comboitem.getValue()).getDenominacion()+" ya existe en la lista.");
								return;
							}
						}
					}
				}	
				
				menupopupServicios.detach();
				for(Comboitem comboitem :cmbServicio.getItems()){
					if(comboitem.getValue()!=null && comboitem.getValue() instanceof Servicio){
						Servicio servicio=comboitem.getValue();
						List<TipoAsiento>listTipoAsientos=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(servicio.getId());
						servicio.setListTipoAsientos(listTipoAsientos);
						
						Listitem item= new Listitem();
						Listcell cell= new Listcell(servicio.getDenominacion());
						item.appendChild(cell);
						
						item.setContext(getMenuContextServicio(item));
						item.setValue(servicio);
						ltbxServicios.appendChild(item);						
					}					
				}				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * Guarda la tarifa
	 */
	public void onGuardarTarifa(){
		try {
			if(dtbxHasta.getValue().getTime()<dtbxDesde.getValue().getTime()){
				DlgMessage.information("La fecha inicial no puede ser menor a la final.",dtbxHasta);
				return;
			}else if (ltbxNuevasTarifas.getItems().size()==0){
				DlgMessage.information("No ha ingresado las rutas a las cuales se les va crear el tarifario");
				return;
			}else if (ltbxServicios.getItems().size()==0){
				DlgMessage.information("Debe de ingresar los servicios a los cuales va a aplicar el tarifario.");
				return;
			}
			if(divDestRatioDias.isVisible()){
				if(itbxRatioDiasPartida.getValue()==null || itbxRatioDiasPartida.getValue()<=0){
					DlgMessage.information("Debe de ingresar la cantidad de días.",itbxRatioDiasPartida);
					return;
				}else if (cmbTipoDescuento.getSelectedIndex()<=0){
					DlgMessage.information("Debe de seleccionar el tipo de descuento.",cmbTipoDescuento);
					return;
				}else if (dblDescuentoMaximoSoles.getText().isEmpty()){
					DlgMessage.information("Debe de ingresar el valor del descuento máximo en Soles.",dblDescuentoMaximoSoles);
					return;
				}else if (txtNombreDescuento.getText().trim().isEmpty()){
					DlgMessage.information("Debe de ingresar el nombre o denominación del descuento a crear.",txtNombreDescuento);
					return;
				}
			}			
			tarifaPlantillaEncabezado=cmbPantillaEncabezado.getSelectedItem().getValue();								
			Date fechaDesde=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxDesde.getValue()));
			Date fechaHasta=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxHasta.getValue()));
			
			listTarifas= new ArrayList<>();
			long cantDias=(long)1;			
			cantDias+= (fechaHasta.getTime()-fechaDesde.getTime())/Constantes.MILISEGUNDOS_X_DIA;
			
			for(int x=0; x<(int)cantDias;x++){				
				Date fecha= new Date(fechaDesde.getTime()+(x*Constantes.MILISEGUNDOS_X_DIA));
				for(Listitem itemRutas: ltbxNuevasTarifas.getItems()){															
					for(Listitem itemServicio : ltbxServicios.getItems()){
						TarifaByAsiento tarifa= new TarifaByAsiento();
						tarifa.setRuta(itemRutas.getValue()!=null?((TarifaByAsiento)itemRutas.getValue()).getRuta():null);
						tarifa.setFecha(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(fecha)));
						Servicio servicio=itemServicio.getValue();
						tarifa.setServicio(servicio);
						tarifa.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						UtilData.auditarRegistro(tarifa, getUsuario(), Executions.getCurrent());						
						
						//Crea el detalle de la tarifa
						List<TarifaByAsientoDetalle> listTarifaDetalle= new ArrayList<>();
						List<Component>listHearders=ltbxNuevasTarifas.getListhead().getChildren();
						for(Component component: listHearders){
							if(component instanceof Listheader){
								Listheader listheader=((Listheader)component);
								if(listheader.getValue()!=null && listheader.getValue() instanceof TarifaByAsientoPlantillaEncabezadoDetalle){
									TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalleListHeader=listheader.getValue();
									
									boolean addTarifaDetalle=false;
									String asientos=encabezadoDetalleListHeader.getAsientos();
									Integer asientoIni=(asientos.split("-").length>1?Integer.valueOf(asientos.split("-")[0]):0);
									Integer asientoFin=(asientos.split("-").length>1?Integer.valueOf(asientos.split("-")[1]):0);
									
									//Valida la capacidad de asientos del servicio
									if(asientoFin.intValue()>0 && asientoFin>servicio.getNumeroAsientosPiso1().intValue()){
										asientoFin=servicio.getNumeroAsientosPiso1();
										asientos=asientos.split("-")[0]+"-"+asientoFin.toString();
									}
																			
									//Validando los tipos de asientos del servicio
									for(TipoAsiento tipoAsiento: servicio.getListTipoAsientos()){
										if(tipoAsiento.getId().intValue()==encabezadoDetalleListHeader.getTipoAsiento().getId().intValue()){
											addTarifaDetalle=true;
											if(asientoIni>0 && asientoIni< tipoAsiento.getAsientoInicial().intValue())
												asientos=tipoAsiento.getAsientoInicial().toString()+"-"+ asientos.split("-")[1];											
											break;
										}
									}
									
									Double precio=getPrecioByRutaByTipoAsiento(itemRutas, encabezadoDetalleListHeader);
									if(addTarifaDetalle && precio!=null && precio > 0.0){
										TarifaByAsientoDetalle tarifaDetalle=new TarifaByAsientoDetalle();
										tarifaDetalle.setCanalVenta(null); //***POR IMPLEMENTAR****
										tarifaDetalle.setAgencia(null); //***POR IMPLEMENTAR***
										tarifaDetalle.setTipoAsiento(encabezadoDetalleListHeader.getTipoAsiento());
										tarifaDetalle.setAsientos(asientos);
										tarifaDetalle.setPrecio(precio);
										tarifaDetalle.setTipoPrecio(((TarifaByAsientoPlantillaEncabezado)cmbPantillaEncabezado.getSelectedItem().getValue()).getTipoPrecio());
										
										if(divDestRatioDias.isVisible()){
											tarifaDetalle.setPrecio(0.00);
											tarifaDetalle.setValorDescuento(precio);
											tarifaDetalle.setRatioDiasFechaEmison(itbxRatioDiasPartida.getValue());
											tarifaDetalle.setTipoDescuento(cmbTipoDescuento.getSelectedItem().getValue().toString());
											tarifaDetalle.setValorMaximoDescuentoSoles(dblDescuentoMaximoSoles.getValue());
											tarifaDetalle.setObservaciones(txtNombreDescuento.getText().trim().toUpperCase());
										}
										
										tarifaDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);
										UtilData.auditarRegistro(tarifaDetalle, getUsuario(), Executions.getCurrent());
										listTarifaDetalle.add(tarifaDetalle);
									}			
								}								
							}														
						}
						tarifa.setListTarifaByAsientoDetalle(listTarifaDetalle);
						listTarifas.add(tarifa);
					}				
				}					
			}
			
			
			if(listTarifas.size()>0){
				Messagebox.show("¿Realmente desea Crear el tarifario, este proceso puede tardar unos minutos?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
					@Override
					public void onEvent(Event e){
						if(e.getName().equals("onYes")){							
							try {
								
								int isCorrect= ServiceLocator.getTarifaByAsientoManager().guardar(listTarifas, tarifaPlantillaEncabezado, chbxReemplazarPromociones.isChecked());
								if(isCorrect==Constantes.CORRECT){
									btnCrearTarifario.setDisabled(true);
									btnCrearTarifario.setImage("/resources/toolbar/mp_toolbarGuardarDisabled.png");									
									btnAgregarRuta.setDisabled(true);
									btnAgregarServicio.setDisabled(true);
									cmbOrigen.setDisabled(true);
									cmbDestino.setDisabled(true);
									cmbPantillaEncabezado.setDisabled(true);
									cmbServicio.setDisabled(true);
									dtbxDesde.setDisabled(true);
									dtbxHasta.setDisabled(true);
									chbxReemplazarPromociones.setDisabled(true);
									itbxRatioDiasPartida.setDisabled(true);
									dblDescuentoMaximoSoles.setDisabled(true);
									cmbTipoDescuento.setDisabled(true);
									txtNombreDescuento.setDisabled(true);
									
									btnNuevoTarifario.setDisabled(false);
									btnNuevoTarifario.setImage("/resources/toolbar/mp_toolbarNuevo.png");
									
									DlgMessage.information("El Tarifario fue creado correctamente.");
								}else{
									DlgMessage.information("Ha ocurrido un error y no se pudo crear el tarifario");
								}								
							} catch (Exception e1) {
								e1.printStackTrace();
								DlgMessage.error(e1.getMessage());
							}
							
						}
					}
				});				
			}else{
				DlgMessage.information("No se a podido crear la estructura para la creación de las tarifas, por favor vuelva a intentarlo.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	
	public void onNuevoTarifario(){
		try {
			Util.limpiarListbox(ltbxServicios);
			cmbPantillaEncabezado.setSelectedIndex(0);
			onSelect_plantillaEncabezado();
			cmbOrigen.setSelectedIndex(0);
			cmbDestino.setSelectedIndex(0);
			cmbServicio.setSelectedIndex(0);
			dtbxDesde.setValue(new Date());
			dtbxHasta.setValue(new Date());
			
			cmbPantillaEncabezado.setDisabled(false);
			cmbOrigen.setDisabled(false);
			cmbDestino.setDisabled(false);
			cmbServicio.setDisabled(false);
			dtbxDesde.setDisabled(false);
			dtbxHasta.setDisabled(false);
			itbxRatioDiasPartida.setDisabled(false);
			dblDescuentoMaximoSoles.setDisabled(false);
			cmbTipoDescuento.setDisabled(false);
			txtNombreDescuento.setDisabled(false);
			btnAgregarRuta.setDisabled(false);
			btnAgregarServicio.setDisabled(false);
			btnCrearTarifario.setDisabled(false);
			btnCrearTarifario.setImage("resources/toolbar/mp_toolbarGuardar.png");
			
			cmbPantillaEncabezado.setFocus(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.information(e.getMessage());
		}
	}
	
	private Double getPrecioByRutaByTipoAsiento(Listitem itemRuta, TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalleListHeader)throws Exception{
		Double precio=0.00;
		//Obtiene la tarifa correspondiente a la ruta y el tipo de asiento
		for(Component componentCell: itemRuta.getChildren()){
			if(componentCell instanceof Listcell && ((Listcell) componentCell).getValue()!=null && ((Listcell) componentCell).getValue() instanceof TarifaByAsientoPlantillaEncabezadoDetalle){
				TarifaByAsientoPlantillaEncabezadoDetalle _plantillaEncabezadoDetalle=((Listcell) componentCell).getValue();
				if(_plantillaEncabezadoDetalle.getId().intValue()==encabezadoDetalleListHeader.getId().intValue()){
					precio=Double.valueOf(((Listcell)componentCell).getLabel());
				}
			}
		}
		
		return precio;
	}
	
	/**
	 * Habilita el control para la edicion en el listbox
	 * @param component
	 * @throws Exception
	 */
	private void enabledControl(Component component)throws Exception{
		if(component!=null){
			component.setVisible(true);			
			((Listcell)component.getParent()).setLabel("");
			if(component instanceof Combobox){
				if(((Combobox)component).getItems().size()==2)
					((Combobox)component).setSelectedIndex(1);
				((Combobox)component).setFocus(true);
				((Combobox)component).select();
			}else if(component instanceof Doublebox){
				((Doublebox)component).setFocus(true);
				((Doublebox)component).select();
			}else if (component instanceof Textbox){
				((Textbox)component).setFocus(true);
//				((Textbox)component).select();
			}
		}		
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void onEvent_control(Event event)throws Exception{		
		if(event.getTarget() instanceof Listcell){
			Component component=((Listcell)event.getTarget()).getFirstChild();
			enabledControl(component);	
		}else{
			
			Component component=event.getTarget(); 
			component.setVisible(false);			
			Listcell cell=(Listcell) component.getParent();
			if(component instanceof Doublebox){
				Doublebox valDoublebox=(Doublebox) component;
				if(valDoublebox.getValue()==null)
					valDoublebox.setValue(0.00);
				cell.setLabel(Util.toNumberFormat(valDoublebox.getValue(), 2));
				
				if(valDoublebox.getAttribute(TarifaByAsientoDetalle.class.getName())!=null){					
					TarifaByAsientoDetalle tarifaDetalle=ServiceLocator.getTarifaByAsientoDetalleManager().buscarPorId(((TarifaByAsientoDetalle)valDoublebox.getAttribute(TarifaByAsientoDetalle.class.getName())).getId());
					if((tarifaDetalle.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR && tarifaDetalle.getValorDescuento().doubleValue()!=valDoublebox.getValue() ||
							tarifaDetalle.getTipoPrecio().getId().intValue()!=Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR && tarifaDetalle.getPrecio().doubleValue()!=valDoublebox.getValue())){
						//Clonamos el objeto para la nueva insercion
						TarifaByAsientoDetalle oTarifaDetalle=(TarifaByAsientoDetalle) tarifaDetalle.clone();
							
						//Anula el registro si es un descuento tarifa regular - jabanto 08/04/2019
						if(tarifaDetalle.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR &&
								tarifaDetalle.getItinerario()!=null){
							TarifaByAsientoDetalle anul_tarifaDetalle=ServiceLocator.getTarifaByAsientoDetalleManager().buscarPorId(tarifaDetalle.getId());
							UtilData.auditarRegistro(anul_tarifaDetalle, true, getUsuario(), Executions.getCurrent());
							anul_tarifaDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
							ServiceLocator.getTarifaByAsientoDetalleManager().actualizar(anul_tarifaDetalle);
						}
						
						//inserta un nuevo registro con la midificacion de la tarifa
						oTarifaDetalle.setId(null);
						if(tarifaDetalle.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR)
							oTarifaDetalle.setValorDescuento(valDoublebox.getValue());
						else
							oTarifaDetalle.setPrecio(valDoublebox.getValue());
						oTarifaDetalle.setItinerario(new Itinerario(tarifarioByAvanceVentas.getItinerario_id()));
						oTarifaDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);					
						UtilData.auditarRegistro(oTarifaDetalle, getUsuario(), Executions.getCurrent());
						ServiceLocator.getTarifaByAsientoDetalleManager().guardar(oTarifaDetalle);
						
						//Vuelve a cargar el tarifario
						cargarTarifario(tarifarioByAvanceVentas, tarifaByAsiento);
					}					
				}else if(valDoublebox.getAttribute(TarifaByAsientoPlantillaPromocion.class.getName())!=null){
					TarifaByAsientoPlantillaPromocion plantillaPromocion=ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().buscarPorId(((TarifaByAsientoPlantillaPromocion)valDoublebox.getAttribute(TarifaByAsientoPlantillaPromocion.class.getName())).getId().longValue());
					if(plantillaPromocion.getValorDescuento().doubleValue()!=valDoublebox.getValue()){
						//Clonamos el objeto para la nueva insercion
						TarifaByAsientoPlantillaPromocion oPlantillaPromocion=(TarifaByAsientoPlantillaPromocion) plantillaPromocion.clone();
						
						//Anulamos el registtro actual
						plantillaPromocion.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(plantillaPromocion, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().actualizar(plantillaPromocion);
						
						//Inserte un nuevo registro con la modificacion de la plantilla
						oPlantillaPromocion.setId(null);
						oPlantillaPromocion.setValorDescuento(valDoublebox.getValue());
						oPlantillaPromocion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().guardar(oPlantillaPromocion);
						
						//Vuelve a cargar las plantillas con los asientos promocionales
						cargarPlantillasPromociones(tarifarioByAvanceVentas, tarifaByAsiento);
					}					
				}
			}
			else if (component instanceof Textbox){
				Textbox valTextbox=(Textbox) component;
				if(valTextbox.getValue()==null)
					valTextbox.setText("");
				cell.setLabel(valTextbox.getText().trim());
				if(valTextbox.getAttribute(TarifaByAsientoDetalle.class.getName())!=null){					
					TarifaByAsientoDetalle tarifaDetalle=ServiceLocator.getTarifaByAsientoDetalleManager().buscarPorId(((TarifaByAsientoDetalle)valTextbox.getAttribute(TarifaByAsientoDetalle.class.getName())).getId());
					if(tarifaDetalle.getAsientos().trim()!=valTextbox.getText().trim()){
						//Valida expresion con los numeros de asientos
						boolean isValidExpresion = Pattern.matches("^[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70]*$", valTextbox.getText().trim());
						if (!(isValidExpresion)){
							DlgMessage.information("La expresión (asientos: "+valTextbox.getText()+") no es válida. \n\n No se ha realziado ningún cambio.");
							//Vuelve a cargar el tarifario
							cargarTarifario(tarifarioByAvanceVentas, tarifaByAsiento);
							
							return;
						}
						
						//Actualiza solo si los valores son diferentes
						if(!(tarifaDetalle.getAsientos().equals(valTextbox.getText().trim()))){
							//Clonamos el objeto para la nueva insercion
							TarifaByAsientoDetalle oTarifaDetalle=(TarifaByAsientoDetalle) tarifaDetalle.clone();
							
							//Primero anula el registro actual
							tarifaDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
							UtilData.auditarRegistro(tarifaDetalle, true, getUsuario(), Executions.getCurrent());
							ServiceLocator.getTarifaByAsientoDetalleManager().actualizar(tarifaDetalle);
							
							//inserta un nuevo registro con la midificacion de los asientos
							oTarifaDetalle.setId(null);
							oTarifaDetalle.setAsientos(valTextbox.getText().trim());
							oTarifaDetalle.setItinerario(new Itinerario(tarifarioByAvanceVentas.getItinerario_id()));
							oTarifaDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);					
							UtilData.auditarRegistro(oTarifaDetalle, getUsuario(), Executions.getCurrent());
							ServiceLocator.getTarifaByAsientoDetalleManager().guardar(oTarifaDetalle);
							
							//Vuelve a cargar el tarifario
							cargarTarifario(tarifarioByAvanceVentas, tarifaByAsiento);							
						}						
					}					
				}else if(valTextbox.getAttribute(TarifaByAsientoPlantillaPromocion.class.getName())!=null){
					TarifaByAsientoPlantillaPromocion plantillaPromocion=ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().buscarPorId(((TarifaByAsientoPlantillaPromocion)valTextbox.getAttribute(TarifaByAsientoPlantillaPromocion.class.getName())).getId().longValue());
					if(plantillaPromocion.getAsientos().trim()!=valTextbox.getText().trim()){
						//Clonamos el objeto para la nueva insercion
						TarifaByAsientoPlantillaPromocion oPlantillaPromocion=(TarifaByAsientoPlantillaPromocion) plantillaPromocion.clone();
						
						//Anulamos el registtro actual
						plantillaPromocion.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(plantillaPromocion, true, getUsuario(), Executions.getCurrent());
						ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().actualizar(plantillaPromocion);
						
						//Inserte un nuevo registro con la modificacion de la plantilla
						oPlantillaPromocion.setId(null);
						oPlantillaPromocion.setAsientos(valTextbox.getText().trim());
						oPlantillaPromocion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
						ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().guardar(oPlantillaPromocion);
						
						//Vuelve a cargar las plantillas con los asientos promocionales
						cargarPlantillasPromociones(tarifarioByAvanceVentas, tarifaByAsiento);
					}					
				}
			}
		}
	}
	
	
	/***********************************************/
	/*EDICION DE TARIFAS*/
	/***********************************************/
	public void onBuscarServiciosOcupacion(){
		try {
			String fecha=Constantes.FORMAT_DATE.format(dtbxED_Fecha.getValue());
			Integer servicio_id=null;
			Integer localidad_idOrigen=null;
			Integer localidad_idDestino=null;
			if(cmbED_servicio.getSelectedItem().getValue() instanceof Servicio)
				servicio_id=((Servicio)cmbED_servicio.getSelectedItem().getValue()).getId();
			if(cmbED_origen.getSelectedItem().getValue() instanceof Localidad)
				localidad_idOrigen=((Localidad)cmbED_origen.getSelectedItem().getValue()).getId();
			if(cmbED_destino.getSelectedItem().getValue() instanceof Localidad)
				localidad_idDestino=((Localidad)cmbED_destino.getSelectedItem().getValue()).getId();
			
			List<TarifarioByAsientoByAvanceVentas> result = 
					ServiceLocator.getVentaPasajesManager().buscarAvanceVentasByTarifarioByAsiento(fecha, servicio_id, localidad_idOrigen, localidad_idDestino);
			
			tree.detach();
			tree= new Tree();
			tree.setWidth("100%");
			tree.setRows(15);
			//Solo habilita los de la fecha actual en adelante
			Date fechaBusqueda=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxED_Fecha.getValue()));
			Date fechaHoy=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
			if(fechaBusqueda.getTime()>=fechaHoy.getTime())
				tree.setCheckmark(true);
				tree.setMultiple(true);
			tree.setZclass("z-dottree");
			tree.getChildren().clear();
			/*Genera columnas*/
			treecols= new Treecols();
			treecols.appendChild(new Treecol("Ruta","","240px"));
			treecols.appendChild(new Treecol("Hora","","60px"));
			treecols.appendChild(new Treecol("Servicio","","140px"));			
			Treecol treecol= new Treecol("Ocp.Cama","","79px");
			treecol.setTooltiptext("Ocupación Asientos Cama y Semicama");
			treecols.appendChild(treecol);			
			treecol= new Treecol("Ocp.Suite","","83px");
			treecol.setTooltiptext("Ocupación Asientos Suite");
			treecols.appendChild(treecol);
			treecol= new Treecol("S/ Estandar","","100px");
			treecol.setTooltiptext("Tarifa de Asientos Cama y Semicama");
			treecols.appendChild(treecol);
			treecol= new Treecol("S/ Suite","","75px");
			treecol.setTooltiptext("Tarifa de Asientos Suite");
			treecols.appendChild(treecol);
			treecols.appendChild(new Treecol());
			((Treecol)treecols.getChildren().get(1)).setAlign("Center");
			((Treecol)treecols.getChildren().get(3)).setAlign("Center");
			((Treecol)treecols.getChildren().get(4)).setAlign("Center");
			tree.appendChild(treecols);
			
			Treechildren treechildren = new Treechildren();
			Treeitem itemDate= new Treeitem("------");
			Treerow treerow = new Treerow();
			Treecell treecell = new Treecell();
			
			
			itemDate = new Treeitem();			
			treerow = new Treerow();
			treecell = new Treecell(Constantes.FORMAT_DATE.format(dtbxED_Fecha.getValue()),"resources/mp_calendarEnabled.png");
			treerow.appendChild(treecell);
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.appendChild(new Treecell());
			treerow.setStyle("background:#A9CCE3");
			
			treechildrenRutas = new Treechildren();			
			for(TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas :result){
				Treeitem itemRutas = new Treeitem();
				Treerow rowRutas = new Treerow();
				
				//Inserta los tramos
				Treechildren treechildrenDetIti = new Treechildren();
				for(TarifarioByAsientoSubByAvanceVentas subByAvanceVentas:tarifarioByAvanceVentas.getTarifarioByAsientoSubByAvanceVentas()){
					Treeitem itemDetIti = new Treeitem();
					Treerow rowDetIti = new Treerow();
					
					Treecell cellDetIti = new Treecell(subByAvanceVentas.getRuta().toString());
					rowDetIti.appendChild(cellDetIti);					
					cellDetIti= new Treecell("");
					rowDetIti.appendChild(cellDetIti);					
					cellDetIti= new Treecell("");
					rowDetIti.appendChild(cellDetIti);														
					cellDetIti= new Treecell(subByAvanceVentas.getOcupacionAsientosOtros().toString());
					rowDetIti.appendChild(cellDetIti);	
					cellDetIti= new Treecell(subByAvanceVentas.getOcupacionAsientosSuite().toString());
					rowDetIti.appendChild(cellDetIti);
					
					//Obtiene la tarifa de presentacion de la ruta
					Itinerario itinerario= new Itinerario(tarifarioByAvanceVentas.getItinerario_id());
					itinerario.setServicio(new Servicio(tarifarioByAvanceVentas.getServicio().getId()));
					DetalleItinerario detalleItinerario= new DetalleItinerario();
					detalleItinerario.setItinerario(itinerario);
					detalleItinerario.setRuta(new Ruta(subByAvanceVentas.getRuta().getId()));
					detalleItinerario.setFechaPartida(subByAvanceVentas.getFecha());
										
					Double tarifaSuite=UtilData.getTarifaByAsientoPresentacion(detalleItinerario, true); 
					Double tarifaOtro=UtilData.getTarifaByAsientoPresentacion(detalleItinerario, false);													
					
					cellDetIti= new Treecell(Util.toNumberFormat(tarifaOtro, 2));
					cellDetIti.setStyle("text-align:right");
					rowDetIti.appendChild(cellDetIti);
					
					cellDetIti= new Treecell(Util.toNumberFormat(tarifaSuite, 2));
					cellDetIti.setStyle("text-align:right");
					rowDetIti.appendChild(cellDetIti);									
					
					cellDetIti= new Treecell();
					Toolbarbutton btnTarifa= new Toolbarbutton("Tarifas");
					btnTarifa.setStyle("color:blue");
					cellDetIti.appendChild(btnTarifa);
					rowDetIti.appendChild(cellDetIti);					
					
					itemDetIti.appendChild(rowDetIti);
					treechildrenDetIti.appendChild(itemDetIti);
					
					itemDetIti.setValue(subByAvanceVentas);										
					
					btnTarifa.setAttribute(TarifarioByAsientoByAvanceVentas.class.getName(), tarifarioByAvanceVentas);
					btnTarifa.setAttribute(TarifarioByAsientoSubByAvanceVentas.class.getName(),subByAvanceVentas);
					btnTarifa.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							
							createWindowEdicionTarifa(event);
						}
					});					
				}
				//Obtiene la tarifa de presentacion de la ruta				
				Itinerario itinerario= new Itinerario(tarifarioByAvanceVentas.getItinerario_id());
				itinerario.setServicio(new Servicio(tarifarioByAvanceVentas.getServicio().getId()));
				DetalleItinerario detalleItinerario= new DetalleItinerario();
				detalleItinerario.setItinerario(itinerario);
				detalleItinerario.setRuta(new Ruta(tarifarioByAvanceVentas.getRutaMayor().getId()));
				detalleItinerario.setFechaPartida(tarifarioByAvanceVentas.getFecha());
				
				Double tarifaSuite=UtilData.getTarifaByAsientoPresentacion(detalleItinerario, true); 
				Double tarifaOtro=UtilData.getTarifaByAsientoPresentacion(detalleItinerario, false);
				
				List<TipoAsiento>listTipoAsiento=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifarioByAvanceVentas.getServicio().getId());
				int capMaxSuite=0, capMaxOtro=0;
				for(TipoAsiento tipoAsiento: listTipoAsiento){
					if(tipoAsiento.getId().intValue()==Constantes.ID_TIPASI_SUITE)
						capMaxSuite= tipoAsiento.getAsientoFinal()-tipoAsiento.getAsientoInicial()+1;
					else
						capMaxOtro= tipoAsiento.getAsientoFinal()-tipoAsiento.getAsientoInicial()+1;						
				}
				
				itemRutas.appendChild(treechildrenDetIti);
				itemRutas.setOpen(false);				
				Treecell treecellPadre = new Treecell(tarifarioByAvanceVentas.getRutaMayor().toString());
				rowRutas.appendChild(treecellPadre);				
				treecellPadre = new Treecell(tarifarioByAvanceVentas.getHoraSalida());
				rowRutas.appendChild(treecellPadre);				
				treecellPadre = new Treecell(tarifarioByAvanceVentas.getServicio().toString());
				rowRutas.appendChild(treecellPadre);				
				
								
				
				//Calcula el porcentaje de ocupabilidad de los asientos cama o semicama
				Double porOcup=0.0;
				if(capMaxOtro>0){
					porOcup=(double) (tarifarioByAvanceVentas.getOcupacionAsientosOtros() *100);
					porOcup /= (capMaxOtro *100);
					porOcup *= 100;
				}
				treecellPadre = new Treecell(tarifarioByAvanceVentas.getOcupacionAsientosOtros().toString()+" - ("+porOcup.intValue()+" %)");
				treecellPadre.setStyle("font-weight:bold;color:#0934D4;text-align:center");
				rowRutas.appendChild(treecellPadre);
				
				//Calcula el porcentaje de ocupabilidad de los asientos suite
				porOcup=0.0;
				if(capMaxSuite>0){
					porOcup=(double) (tarifarioByAvanceVentas.getOcupacionAsientosSuite() *100);
					porOcup /= (capMaxSuite *100);
					porOcup *= 100;
				}
				treecellPadre = new Treecell(tarifarioByAvanceVentas.getOcupacionAsientosSuite().toString()+" - ("+porOcup.intValue()+" %)");
				treecellPadre.setStyle("font-weight:bold;color:#0934D4;text-align:center");
				rowRutas.appendChild(treecellPadre);
				
				treecellPadre = new Treecell(Util.toNumberFormat(tarifaOtro, 2));
				treecellPadre.setStyle("font-weight:bold;color:#0934D4;text-align:right");
				rowRutas.appendChild(treecellPadre);
				
				treecellPadre = new Treecell(Util.toNumberFormat(tarifaSuite, 2));
				treecellPadre.setStyle("font-weight:bold;color:#0934D4;text-align:right");
				rowRutas.appendChild(treecellPadre);							
				
				treecellPadre = new Treecell();				
				Toolbarbutton btnModificarTarifa= new Toolbarbutton("Ver Tarifario");
				btnModificarTarifa.setStyle("color:red");
				btnModificarTarifa.setAttribute(TarifarioByAsientoByAvanceVentas.class.getName(), tarifarioByAvanceVentas);
				btnModificarTarifa.setAttribute("itinerario_id",tarifarioByAvanceVentas.getItinerario_id());
				treecellPadre.appendChild(btnModificarTarifa);
				rowRutas.appendChild(treecellPadre);
				
				btnModificarTarifa.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						
						createWindowEdicionTarifa(event);
					}
				});
				
				
				rowRutas.setStyle("background:#D4E6F1");
				itemRutas.appendChild(rowRutas);
				itemRutas.setValue(tarifarioByAvanceVentas);
				treechildrenRutas.appendChild(itemRutas);				
				
				/*Agrega el item rutas al itemGoupFechas*/
				itemDate.appendChild(treechildrenRutas);
				itemDate.setOpen(false);
			}
			itemDate.appendChild(treerow);
			treechildren.appendChild(itemDate);
			itemDate.setOpen(true);
			tree.appendChild(treechildren);
			tabPanelEdicionServicio.appendChild(tree);
		
			tree.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					SelectEvent<?, ?> selectEvent=(SelectEvent<?, ?>) event;
					Object object= selectEvent.getReference();
					if(object instanceof Treeitem){
						Treeitem item= (Treeitem) object;
						if(item.getValue()!=null && item.getValue() instanceof TarifarioByAsientoByAvanceVentas){
							//Item rutas mayores
							Component components= item.getChildren().get(0);
							for(Component component: components.getChildren()){
								Treeitem itemTramo=(Treeitem) component;
								itemTramo.setSelected(item.isSelected());
							}
						}else if(item.getValue()!=null && item.getValue() instanceof TarifarioByAsientoSubByAvanceVentas){
							//Item con los trammos de la ruta mayor
							Component component=item.getParent().getParent();
							if(component instanceof Treeitem){
								Treeitem itemPadre=(Treeitem) component;
								itemPadre.setSelected(false);								
								Component components= itemPadre.getChildren().get(0);
								for(Component component2: components.getChildren()){
									Treeitem iemTramo=(Treeitem) component2;
									if(iemTramo.isSelected())
										itemPadre.setSelected(true);
								}
							}							
						}else{
							//Item principal con la fecha
							Component components= item.getChildren().get(0);
							for(Component component: components.getChildren()){
								//Item rutas mayores
								Treeitem itemRutaMayor=(Treeitem) component;
								itemRutaMayor.setSelected(item.isSelected());								
								//Item con los trammos de la ruta mayor
								for(Component component2: itemRutaMayor.getChildren().get(0).getChildren()){
									((Treeitem)component2).setSelected(itemRutaMayor.isSelected());									
								}
							}			
						}
					}
					
				}
			});
			
			grid.detach();
			grid= new Grid();
			Columns columns= new Columns();
			Column column= new Column();
			columns.appendChild(column);
			grid.appendChild(columns);
			
			Rows rows= new Rows();
			Row row= new Row();
			Div div= new Div();
			div.setWidth("100%");
			Separator separator= new Separator("vertical");
			separator.setWidth("237px");
			div.appendChild(separator);
			
			Radiogroup radiogroup= new Radiogroup();			
			rdIncremento= new Radio("Incremento de Tarifa");
			rdIncremento.setStyle("color:blue");
			rdIncremento.setDisabled(!tree.isCheckmark());
			radiogroup.appendChild(rdIncremento);			
			separator= new Separator("vertical");
			separator.setWidth("25px");
			radiogroup.appendChild(separator);						
			rdRebaja= new Radio("Rebaja de Tarifa");
			rdRebaja.setStyle("color:red");
			rdRebaja.setDisabled(!tree.isCheckmark());
			radiogroup.appendChild(rdRebaja);					
			
			div.appendChild(radiogroup);
			
			separator= new Separator("vertical");
			separator.setWidth("100px");
			div.appendChild(separator);			
			
			separator= new Separator("vertical");
			separator.setWidth("5px");
			div.appendChild(separator);
			
			dbxIncRebAsientoCamaSemicama= new Doublebox();
			dbxIncRebAsientoCamaSemicama.setFormat("#,###.00");
			dbxIncRebAsientoCamaSemicama.setWidth("91px");
			dbxIncRebAsientoCamaSemicama.setLocale(Locale.US);
			dbxIncRebAsientoCamaSemicama.setDisabled(!tree.isCheckmark());
			div.appendChild(dbxIncRebAsientoCamaSemicama);			
			
			separator= new Separator("vertical");
			separator.setWidth("10px");
			div.appendChild(separator);
			
			dbxIncRebAsientoSuite= new Doublebox();
			dbxIncRebAsientoSuite.setFormat("#,###.00");
			dbxIncRebAsientoSuite.setWidth("92px");
			dbxIncRebAsientoSuite.setLocale(Locale.US);
			dbxIncRebAsientoSuite.setDisabled(!tree.isCheckmark());
			div.appendChild(dbxIncRebAsientoSuite);
			
			Toolbarbutton btnAplicar= new Toolbarbutton("Aplicar","/resources/mp_acceptEnabled.png");
			btnAplicar.setAutodisable("self");
			btnAplicar.setDisabled(!tree.isCheckmark());
			div.appendChild(btnAplicar);
			
			btnAplicar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {					
					incrementarRebajarPrecio();
				}
			});
			
			
			row.appendChild(div);
			rows.appendChild(row);
			grid.appendChild(rows);
			tabPanelEdicionServicio.appendChild(grid);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void incrementarRebajarPrecio(){
		try {
			if(tree.getSelectedItems().size()==0){
				DlgMessage.information("Debe de seleccionar la ruta a la cual de va aplicar el Incremento o Rebaja");
				return;
			}else if(rdIncremento.isSelected()==false && rdRebaja.isSelected()==false){
				DlgMessage.information("Debe se eleccionar una Opción, 'Incremento o Rebaja de la tarifa'");
				return;
			}else if (dbxIncRebAsientoSuite.getValue()==null && dbxIncRebAsientoCamaSemicama.getValue()==null){				
				DlgMessage.information("Debe de ingresar el valor del Incremento o Rebaja del Precio",dbxIncRebAsientoCamaSemicama);
				return;
			}			
			
			listED_TarifaByAsiento= new ArrayList<>();			
			for(Treeitem item : tree.getSelectedItems()){
				if(item.getValue()!=null && item.getValue() instanceof TarifarioByAsientoByAvanceVentas){
					TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas=item.getValue();
					
					Component components= item.getChildren().get(0);
					for(Component component: components.getChildren()){
						Treeitem subItem=(Treeitem) component;
						if(subItem.isSelected()){
							TarifarioByAsientoSubByAvanceVentas subByAvanceVentas=subItem.getValue();
							
							TarifaByAsiento tarifa= new TarifaByAsiento();
							tarifa.setFecha(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(dtbxED_Fecha.getValue())));
							tarifa.setRuta(subByAvanceVentas.getRuta());
							tarifa.setServicio(tarifarioByAvanceVentas.getServicio());
							
							List<TarifaByAsientoDetalle>listTarifaDetalle= new ArrayList<>();
							List<TipoAsiento> result= ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifa.getServicio().getId());
							for(TipoAsiento tipoAsiento: result){
								if(tipoAsiento.getId().intValue()==Constantes.ID_TIPASI_SUITE && dbxIncRebAsientoSuite.getValue()!=null && dbxIncRebAsientoSuite.getValue()>0.00){
									TarifaByAsientoDetalle tarifaDetalle= new TarifaByAsientoDetalle();
									tarifaDetalle.setTipoAsiento(tipoAsiento);
									tarifaDetalle.setValue(dbxIncRebAsientoSuite.getValue());
									tarifaDetalle.setIncremento(rdIncremento.isSelected());
									tarifaDetalle.setItinerario(new Itinerario(tarifarioByAvanceVentas.getItinerario_id()));
									
									listTarifaDetalle.add(tarifaDetalle);
								}else if (tipoAsiento.getId().intValue()!=Constantes.ID_TIPASI_SUITE && dbxIncRebAsientoCamaSemicama.getValue()!=null && dbxIncRebAsientoCamaSemicama.getValue()>0.00){
									TarifaByAsientoDetalle tarifaDetalle= new TarifaByAsientoDetalle();
									tarifaDetalle.setTipoAsiento(tipoAsiento);
									tarifaDetalle.setValue(dbxIncRebAsientoCamaSemicama.getValue());
									tarifaDetalle.setIncremento(rdIncremento.isSelected());
									tarifaDetalle.setItinerario(new Itinerario(tarifarioByAvanceVentas.getItinerario_id()));
									
									listTarifaDetalle.add(tarifaDetalle);
								}
							}
							tarifa.setListTarifaByAsientoDetalle(listTarifaDetalle);
							listED_TarifaByAsiento.add(tarifa);
						}
					}
				}								
			}
			
			
			Messagebox.show("¿Realmente desea aplicar las nuevas tarifas?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception{														
					if(e.getName().equals("onYes")){
						
						ServiceLocator.getTarifaByAsientoManager().guardarIncrementoRebaja(listED_TarifaByAsiento);
						onBuscarServiciosOcupacion();
					}
				}
			});			
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void createWindowEdicionTarifa(Event event)throws Exception{
		
		tarifarioByAvanceVentas=(TarifarioByAsientoByAvanceVentas) event.getTarget().getAttribute(TarifarioByAsientoByAvanceVentas.class.getName());
		TarifarioByAsientoSubByAvanceVentas subByAvanceVentas=(TarifarioByAsientoSubByAvanceVentas) event.getTarget().getAttribute(TarifarioByAsientoSubByAvanceVentas.class.getName());
		Long itinerario_id=tarifarioByAvanceVentas.getItinerario_id();				
		
		
		ruta=null;
		String horaPartida="";
		if(subByAvanceVentas==null){
			ruta=tarifarioByAvanceVentas.getRutaMayor();
			horaPartida=tarifarioByAvanceVentas.getHoraSalida();
			fechaPartida=tarifarioByAvanceVentas.getFecha();
		}else{
			ruta=subByAvanceVentas.getRuta();
			horaPartida=subByAvanceVentas.getHoraSalida();
			fechaPartida=subByAvanceVentas.getFecha();
		}		
		
		Date fechaHoy=Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date()));
		habilitarEdicionTarifas=(fechaPartida.getTime()>=fechaHoy.getTime());
		
		
		String title=ruta.toString()+" - "+Constantes.FORMAT_DATE.format(fechaPartida)+" - "+horaPartida;
		
		final Window win = new Window(title, "normal", true);
		win.setWidth("840px");
		
		/* Busca itinerario para la carga del Mapa */
		List<DetalleItinerario> list = ServiceLocator.getItinerarioManager().buscarItinerariosMantenimiento(itinerario_id,"", "", "", "", "", "","","");
		DetalleItinerario detalleItinerario = list.get(0);
		
		Caption caption = null;		
		
		/* ------------------ CONTENEDOR DE OBJETOS --------------------- */
		Grid grdContenedor = new Grid();
		grdContenedor.setStyle("padding:0px; border:none");
		Columns columns = new Columns();
		Column column = new Column(); 
		column.setWidth("174px");
		columns.appendChild(column);
		column = new Column();
		column.setWidth("100%");
		columns.appendChild(column);
		grdContenedor.appendChild(columns);
		
		Rows rowsContenedor = new Rows();
		Row rowContenedor = new Row();
		rowContenedor.setValign("top");
		rowsContenedor.appendChild(rowContenedor);
		
		
		/* ------------------ MAPA DEL BUS	--------------------- */
		Groupbox grpMapa = new Groupbox();
		grpMapa.setMold("3d");
		caption = new Caption();
		caption.setLabel("MAPA DEL BUS");
		grpMapa.appendChild(caption);
		
		Grid gridPiso = new Grid();
		crearEstructura(detalleItinerario.getItinerario().getServicio().getId(), grpMapa, false, detalleItinerario, mapAsientosId, null, gridPiso,"manifiesto", habilitarEdicionTarifas);
		rowContenedor.appendChild(grpMapa);
		

		/* ***********************************************************************************/
		//TARIFARIO DEL SERVICIO
		/* ***********************************************************************************/
		Grid grid_2= new Grid();
		Columns columns_2= new Columns();
		Column column_2= new Column("TARIFARIO ACTUAL DEL SERVIO [ "+title+" ]");
		column_2.setStyle("font-weight: bold;color:red");
		columns_2.appendChild(column_2);
		grid_2.appendChild(columns_2);
		Rows rows_2= new Rows();
		Row row_2= new Row();
		
		ltbxTarifarioServicio= new Listbox();
		ltbxTarifarioServicio.setRows(7);
		Listhead listhead= new Listhead();
		listhead.appendChild(new Listheader("T.ASIENTO", "", "60px"));
		listhead.appendChild(new Listheader("ASIENTOS", "", "180px"));
		listhead.appendChild(new Listheader("VALOR", "", "60px"));
		listhead.appendChild(new Listheader("TIPO", "", "120px"));
		listhead.appendChild(new Listheader("","","30px"));
		listhead.appendChild(new Listheader("F.CREACION", "", "90px"));
		listhead.appendChild(new Listheader("U.CREACION", "", "70px"));
		ltbxTarifarioServicio.appendChild(listhead);
		
		
		/*Busca el tarifario para el servicio*/
		tarifaByAsiento=ServiceLocator.getTarifaByAsientoManager().buscarTarifaByAsientoByRutaServicio(Constantes.FORMAT_DATE.format(fechaPartida), ruta.getId(), tarifarioByAvanceVentas.getServicio().getId());
		if(tarifaByAsiento!=null)	
			cargarTarifario(tarifarioByAvanceVentas,tarifaByAsiento);		
		
		row_2.appendChild(ltbxTarifarioServicio);
		rows_2.appendChild(row_2);		
//		listbox.appendChild(listhead);		
		grid_2.appendChild(rows_2);
		
		
		/* ********************************************************************************/
		//PLANTILLA CON ASIENTOS PROMOCIONALES DISPONIBLES
		/* ********************************************************************************/	
		Tabbox tabbox= new Tabbox();
		Tabs tabs= new Tabs();
		tabs.appendChild(new Tab("Asientos Personalizados"));
		tabs.appendChild(new Tab("Plantillas pre-definidas"));
		tabbox.appendChild(tabs);
		
		Tabpanels tabpanels= new Tabpanels();
		Tabpanel tabpanel= new Tabpanel();
		
		//------
		//Creacion de descuentos / incrementos personalizados
		Grid grid_4= new Grid();
		Columns columns_4= new Columns();
		Column column_4 = new Column("Asientos Suite", "", "50%");
		column_4.setStyle("color:red");
		columns_4.appendChild(column_4);
		column_4 = new Column("Asientos Cama/Semicama");
		column_4.setStyle("color:red");
		columns_4.appendChild(column_4);
		grid_4.appendChild(columns_4);
		Rows rows_4= new Rows();		
		
		Div div= new Div();
		Row row_4= new Row();
		div.setStyle("border-right:1px solid");
		Hbox hbox= new Hbox();
		hbox.setAlign("center");
		Label label= new Label("ASIENTOS SELECCIONADOS:");
		chbxTodosSuites= new Checkbox("Todos");
		chbxTodosSuites.setStyle("color:blue");
		chbxTodosSuites.setDisabled(!habilitarEdicionTarifas);
		hbox.appendChild(label);
		hbox.appendChild(chbxTodosSuites);
		div.appendChild(hbox);
		
		chbxTodosSuites.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(((Checkbox)event.getTarget()).isChecked()){
					List<TipoAsiento>result=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifarioByAvanceVentas.getServicio().getId());
					for(TipoAsiento tipoAsiento:result){
						if(tipoAsiento.getId().intValue()==Constantes.ID_TIPASI_SUITE){
							txtAsientosSuite.setText(tipoAsiento.getAsientoInicial().toString()+"-"+tipoAsiento.getAsientoFinal().toString());
							dbxValorTarifaSuite.setFocus(true);
						}
					}
				}else{
					txtAsientosSuite.setText("");
				}				
			}
		});
		
		txtAsientosSuite= new Textbox();
		txtAsientosSuite.setReadonly(true);
		txtAsientosSuite.setWidth("180px");
		txtAsientosSuite.setStyle("font-size:12px !important;font-weight: bold;color:blue");
		div.appendChild(txtAsientosSuite);
		div.appendChild(new Separator());

		label= new Label("TARIFA :");
		Separator separator= new Separator();	
		separator.setWidth("21px");
		hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(separator);
		hbox.appendChild(label); 
		dbxValorTarifaSuite=new Doublebox();
		dbxValorTarifaSuite.setWidth("118px");
		dbxValorTarifaSuite.setFormat("###,##0.00");
		dbxValorTarifaSuite.setLocale(Locale.US);
		dbxValorTarifaSuite.setDisabled(!habilitarEdicionTarifas);
		hbox.appendChild(dbxValorTarifaSuite);
		div.appendChild(hbox);
		div.appendChild(new Separator());

		label= new Label("TIPO TARIFA :");
		hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(label); 
		cmbTipoTarifaSuite= new Combobox();
		cmbTipoTarifaSuite.setReadonly(true);
		cmbTipoTarifaSuite.setWidth("125px");
		cmbTipoTarifaSuite.setDisabled(!habilitarEdicionTarifas);
		UtilData.cargarGenericData(cmbTipoTarifaSuite, false);
		List<TipoPrecio>resultTipoPrecio=ServiceLocator.getTipoPrecioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		for(TipoPrecio tipoPrecio:resultTipoPrecio){
			if(tipoPrecio.getEsTarifa()!=null && tipoPrecio.getEsTarifa().intValue()==Constantes.TRUE_VALUE){
				Comboitem comboitem= new Comboitem(tipoPrecio.getDenominacion());
				comboitem.setValue(tipoPrecio);
				cmbTipoTarifaSuite.appendChild(comboitem);	
			}			
		}
		cmbTipoTarifaSuite.setSelectedIndex(0);
		hbox.appendChild(cmbTipoTarifaSuite);
		div.appendChild(hbox);

		row_4.appendChild(div);
		
		div = new Div();
		hbox= new Hbox();
		hbox.setAlign("center");
		label= new Label("ASIENTOS SELECCIONADOS:");
		label.setStyle("color:black");
		chbxTodosCasmaSemicama= new Checkbox("Todos");
		chbxTodosCasmaSemicama.setStyle("color:blue");
		chbxTodosCasmaSemicama.setDisabled(!habilitarEdicionTarifas);
		hbox.appendChild(label);
		hbox.appendChild(chbxTodosCasmaSemicama);
		div.appendChild(hbox);		
		chbxTodosCasmaSemicama.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(((Checkbox)event.getTarget()).isChecked()){
					List<TipoAsiento>result=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifarioByAvanceVentas.getServicio().getId());
					for(TipoAsiento tipoAsiento:result){
						if(tipoAsiento.getId().intValue()!=Constantes.ID_TIPASI_SUITE){
							txtAsientosCamaSemicama.setText(tipoAsiento.getAsientoInicial().toString()+"-"+tipoAsiento.getAsientoFinal().toString());
							dbxValorTarifaCamaSemicama.setFocus(true);
						}
					}
				}else{
					txtAsientosCamaSemicama.setText("");
				}					
			}
		});
		
		txtAsientosCamaSemicama= new Textbox();
		txtAsientosCamaSemicama.setReadonly(true);
		txtAsientosCamaSemicama.setWidth("180px");
		txtAsientosCamaSemicama.setStyle("font-size:12px !important;font-weight: bold;color:blue");	
		div.appendChild(txtAsientosCamaSemicama);
		div.appendChild(new Separator());
		
		separator= new Separator();	
		separator.setWidth("21px");
		hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(separator);
		hbox.appendChild(new Label("TARIFA :")); 
		dbxValorTarifaCamaSemicama=new Doublebox();
		dbxValorTarifaCamaSemicama.setWidth("118px");
		dbxValorTarifaCamaSemicama.setFormat("###,##0.00");
		dbxValorTarifaCamaSemicama.setLocale(Locale.US);
		dbxValorTarifaCamaSemicama.setDisabled(!habilitarEdicionTarifas);
		hbox.appendChild(dbxValorTarifaCamaSemicama);
		div.appendChild(hbox);
		div.appendChild(new Separator());

		hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(new Label("TIPO TARIFA :")); 
		cmbTipoTarifaCamaSemicama= new Combobox();
		cmbTipoTarifaCamaSemicama.setReadonly(true);
		cmbTipoTarifaCamaSemicama.setWidth("125px");
		cmbTipoTarifaCamaSemicama.setDisabled(!habilitarEdicionTarifas);
		UtilData.cargarGenericData(cmbTipoTarifaCamaSemicama, false);
		resultTipoPrecio=ServiceLocator.getTipoPrecioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
		for(TipoPrecio tipoPrecio:resultTipoPrecio){
			if(tipoPrecio.getEsTarifa()!=null && tipoPrecio.getEsTarifa().intValue()==Constantes.TRUE_VALUE){
				Comboitem comboitem= new Comboitem(tipoPrecio.getDenominacion());
				comboitem.setValue(tipoPrecio);
				cmbTipoTarifaCamaSemicama.appendChild(comboitem);
			}			
		}
		cmbTipoTarifaCamaSemicama.setSelectedIndex(0);
		hbox.appendChild(cmbTipoTarifaCamaSemicama);
		div.appendChild(hbox);
		
		row_4.appendChild(div);
		rows_4.appendChild(row_4);
		grid_4.appendChild(rows_4);		
		
		Toolbarbutton btnAplicarTarifa= new Toolbarbutton("Aplicar Nueva Tarifa", "resources/mp_acceptEnabled.png");
		btnAplicarTarifa.setAutodisable("self");
		btnAplicarTarifa.setDisabled(!habilitarEdicionTarifas);
		Toolbar toolbar= new Toolbar();
		toolbar.setAlign("center");
		toolbar.appendChild(btnAplicarTarifa);
		
		btnAplicarTarifa.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					//Validaciones
					if(!(txtAsientosSuite.getText().trim().isEmpty())){
						if(!(cmbTipoTarifaSuite.getSelectedItem().getValue() instanceof TipoPrecio)){
							DlgMessage.information("Debe de seleccionar el tipo de tarifa que se va aplicar para los asientos suite seleccionados",cmbTipoTarifaSuite);
							return;
						}else if (dbxValorTarifaSuite.getText().trim().isEmpty()){
							DlgMessage.information("Debe de Ingresar la Tarifa que se va aplicar para los asientos suite seleccionados",dbxValorTarifaSuite);
							return;
						}
					}else if(!(txtAsientosCamaSemicama.getText().trim().isEmpty())){
						if(!(cmbTipoTarifaCamaSemicama.getSelectedItem().getValue() instanceof TipoPrecio)){
							DlgMessage.information("Debe de seleccionar el tipo de tarifa que se va aplicar para los asientos Cama/Semicama seleccionados",cmbTipoTarifaCamaSemicama);
							return;
						}else if (dbxValorTarifaCamaSemicama.getText().trim().isEmpty()){
							DlgMessage.information("Debe de Ingresar la Tarifa que se va aplicar para los asientos Cama/Semicama seleccionados",dbxValorTarifaCamaSemicama);
							return;
						}								
					}else if(txtAsientosSuite.getText().trim().isEmpty() && txtAsientosCamaSemicama.getText().trim().isEmpty()){
						DlgMessage.information("Primero debe seleccionar los asientos a los cuales va a aplicar la nueva tarifa.");
						return;
					}
					
					Messagebox.show("¿Realmente desea aplicar las nuevas tarifas?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception{														
							if(e.getName().equals("onYes")){								
																
								if(!(txtAsientosSuite.getText().trim().isEmpty())){
									//Asientos suite									
									TarifaByAsientoPlantillaPromocion plantillaPromocion= new TarifaByAsientoPlantillaPromocion();
									plantillaPromocion.setTipoAsiento(new TipoAsiento(Constantes.ID_TIPASI_SUITE));
									plantillaPromocion.setAsientos(txtAsientosSuite.getText().trim());
									plantillaPromocion.setPrecio(dbxValorTarifaSuite.getValue());
									plantillaPromocion.setTipoPrecio((TipoPrecio)cmbTipoTarifaSuite.getSelectedItem().getValue());	
									
									aplicarNuevaTarifa(tarifarioByAvanceVentas.getItinerario_id(), plantillaPromocion);									
								}
								if(!(txtAsientosCamaSemicama.getText().trim().isEmpty())){
									//Para los tipos de asiento cama y/o semicama
									
									TipoAsiento tipoAsiento=null;
									//Recupera el tipo de asiento del servico, pero el tipo diferente a suite.
									List<TipoAsiento>result=ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifarioByAvanceVentas.getServicio().getId());
									for(TipoAsiento _tipoAsiento:result){
										if(_tipoAsiento.getId().intValue()!=Constantes.ID_TIPASI_SUITE){
											tipoAsiento=_tipoAsiento;
											break;
										}
									}
									if(tipoAsiento!=null){
										TarifaByAsientoPlantillaPromocion plantillaPromocion= new TarifaByAsientoPlantillaPromocion();
										plantillaPromocion.setTipoAsiento(tipoAsiento);
										plantillaPromocion.setAsientos(txtAsientosCamaSemicama.getText().trim());
										plantillaPromocion.setPrecio(dbxValorTarifaCamaSemicama.getValue());
										plantillaPromocion.setTipoPrecio((TipoPrecio)cmbTipoTarifaCamaSemicama.getSelectedItem().getValue());
																			
										aplicarNuevaTarifa(tarifarioByAvanceVentas.getItinerario_id(), plantillaPromocion);
									}									
								}
								
								cargarTarifario(tarifarioByAvanceVentas, tarifaByAsiento);
								txtAsientosSuite.setText("");
								cmbTipoTarifaSuite.setSelectedIndex(0);
								dbxValorTarifaSuite.setText("");
								
								txtAsientosCamaSemicama.setText("");
								cmbTipoTarifaCamaSemicama.setSelectedIndex(0);
								dbxValorTarifaCamaSemicama.setText("");
								chbxTodosSuites.setChecked(false);
								chbxTodosCasmaSemicama.setChecked(false);
							}
						}
					});
					
					
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}				
			}
		});
		
		tabpanel.appendChild(grid_4);
		tabpanel.appendChild(new Separator());
		tabpanel.appendChild(toolbar);
		tabpanels.appendChild(tabpanel);
		
		//------		
		tabpanel = new Tabpanel();
		
		ltbxPlantillaPromos= new Listbox();
		ltbxPlantillaPromos.setRows(4);
		listhead= new Listhead();
//		listhead.appendChild(new Listheader("NOMBRE", "", "100px"));
		listhead.appendChild(new Listheader("T.ASIENTO", "", "60px"));
		listhead.appendChild(new Listheader("ASIENTOS", "", "180px"));
		listhead.appendChild(new Listheader("DSCT.", "", "40px"));
		listhead.appendChild(new Listheader("TIPO", "", "30px"));
		listhead.appendChild(new Listheader());
		ltbxPlantillaPromos.appendChild(listhead);
				
		//Carga las plantillas de de asientos promocionales predefinidos
		cargarPlantillasPromociones(tarifarioByAvanceVentas, tarifaByAsiento);
			
		tabpanel.appendChild(ltbxPlantillaPromos);
		tabpanels.appendChild(tabpanel);
		
		tabbox.appendChild(tabpanels);
		
		//------------------------------------------------------------------
		Vbox vbox= new Vbox();
		vbox.appendChild(grid_2);
		vbox.appendChild(tabbox);		
		rowContenedor.appendChild(vbox);
		rowsContenedor.appendChild(rowContenedor);
		
		grdContenedor.appendChild(rowsContenedor);
		win.appendChild(grdContenedor);
		
		
		wndEdicionTarifa= win;
		this.appendChild(wndEdicionTarifa);
		wndEdicionTarifa.setMode(MODAL);		
	}
	
	/**
	 * 
	 * @param tarifarioByAvanceVentas
	 * @param tarifaByAsiento
	 * @throws Exception
	 */
	private void cargarPlantillasPromociones(final TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas, final TarifaByAsiento tarifaByAsiento)throws Exception{
		Util.limpiarListbox(ltbxPlantillaPromos);
		
		List<TarifaByAsientoPlantillaPromocion> listPlantillaPromo= new ArrayList<>();
		List<TipoAsiento> listTipoAsientos= ServiceLocator.getMapaBusManager().buscarTipoAsientoByServicio(tarifarioByAvanceVentas.getServicio().getId());
		String tipoAsientos_ids="";
		for(TipoAsiento tipoAsiento : listTipoAsientos){
			tipoAsientos_ids +=(tipoAsientos_ids==""?tipoAsiento.getId().toString():","+tipoAsiento.getId().toString());
		}		
		
		if(tipoAsientos_ids.length()>0)
			listPlantillaPromo=ServiceLocator.getTarifaByAsientoPlantillaPromocionManager().buscarByTipoAsiento(tipoAsientos_ids);
		
		for(TarifaByAsientoPlantillaPromocion plantillaPromocion: listPlantillaPromo){
			Listitem item= new Listitem();
//			Listcell cell= new Listcell(plantillaPromocion.getDenominacion());
//			item.appendChild(cell);
			Listcell cell= new Listcell(plantillaPromocion.getTipoAsiento().getDenominacion());
			item.appendChild(cell);
			Listcell cellAsientos= new Listcell(plantillaPromocion.getAsientos());
			Textbox txtAsientos= new Textbox(cellAsientos.getLabel());
			txtAsientos.setVisible(false);
			txtAsientos.setWidth("90%");
			txtAsientos.setStyle("font-size:11px !important");
			txtAsientos.setAttribute(TarifaByAsientoPlantillaPromocion.class.getName(), plantillaPromocion);
			cellAsientos.setStyle("font-size:11px !important");
			cellAsientos.appendChild(txtAsientos);
			item.appendChild(cellAsientos);
			
			Listcell cellValDescuento= new Listcell(Util.toNumberFormat(plantillaPromocion.getValorDescuento(), 2));
			cellValDescuento.setStyle("font-size:11px !important;text-align: right");
			Doublebox dbbxValDescuento= new Doublebox();
			dbbxValDescuento.setVisible(false);
			dbbxValDescuento.setWidth("90%");
			dbbxValDescuento.setFormat("#,###.00");
			dbbxValDescuento.setLocale(Locale.US);
			dbbxValDescuento.setValue(plantillaPromocion.getValorDescuento());
			dbbxValDescuento.setAttribute(TarifaByAsientoPlantillaPromocion.class.getName(), plantillaPromocion);
			cellValDescuento.appendChild(dbbxValDescuento);
			item.appendChild(cellValDescuento);
			cell= new Listcell(plantillaPromocion.getIsDescuentoSoles().intValue()==Constantes.TRUE_VALUE?"S/":"%");
			cell.setTooltiptext(plantillaPromocion.getIsDescuentoSoles().intValue()==Constantes.TRUE_VALUE?"Descuento en Soles":"Descuento en Porcentaje");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);			
			
			//Valida si la promocion se debe por der aplicar o no
			boolean isEnabledApliPromo=true;
			if(tarifaByAsiento==null)
				isEnabledApliPromo=false;
			else{
				//Valida que esta promo no haya sido aplicada al servicio
				for(Listitem _item: ltbxTarifarioServicio.getItems()){
					TarifaByAsientoDetalle _tarifaDetalle=_item.getValue();
					if(_tarifaDetalle.getTarifaByAsientoPlantillaPromocion()!=null && 
							_tarifaDetalle.getTarifaByAsientoPlantillaPromocion().getId().intValue()==plantillaPromocion.getId().intValue()){
						isEnabledApliPromo=false;
						break;
					}
				}
			}
			
			if(isEnabledApliPromo && !habilitarEdicionTarifas)
				isEnabledApliPromo=false;
			
			cell= new Listcell();
			Image img= new Image(isEnabledApliPromo? "resources/mp_acceptEnabled.png":"resources/mp_acceptDisabled.png");
			img.setAttribute(TarifaByAsientoPlantillaPromocion.class.getName(), plantillaPromocion);
			cell.appendChild(img);
			item.appendChild(cell);
			item.setValue(plantillaPromocion);
			ltbxPlantillaPromos.appendChild(item);
			
			if(isEnabledApliPromo){
				img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) throws Exception {
						Messagebox.show("¿Realmente sea aplicar la promoción?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception{
								if(e.getName().equals("onYes")){
									aplicarNuevaTarifa(tarifarioByAvanceVentas.getItinerario_id(),(TarifaByAsientoPlantillaPromocion) event.getTarget().getAttribute(TarifaByAsientoPlantillaPromocion.class.getName()));
									cargarTarifario(tarifarioByAvanceVentas, tarifaByAsiento);
									cargarPlantillasPromociones(tarifarioByAvanceVentas, tarifaByAsiento);
								}
							}
						});
					}
				});
				
				cellAsientos.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();					
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}							
					}
				});
				txtAsientos.addEventListener(Events.ON_OK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
				txtAsientos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
				
				cellValDescuento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
								onEvent_control(event);
								event.stopPropagation();							
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}							
					}
				});
				
				dbbxValDescuento.addEventListener(Events.ON_OK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
				dbbxValDescuento.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						try {
							onEvent_control(event);
							event.stopPropagation();	
						} catch (Exception e) {
							e.printStackTrace();
							DlgMessage.error(e.getMessage());
						}
					}
				});
			}			
		}
	}
	
	/**
	 * 
	 * @param tarifarioByAvanceVentas
	 * @param ruta
	 * @param plantillaPromocion
	 * @throws Exception
	 */
	private void aplicarNuevaTarifa(Long itinerarioId,TarifaByAsientoPlantillaPromocion plantillaPromocion)throws Exception{
		Double nuevoPrecio=.00;
		TipoPrecio tipoPrecio=null;
		if(plantillaPromocion.getPrecio()==null){//cuando se aplica desde las plantilla predefinidas
			Double maxPrecioRegular=0.00;
			//Busca el precio mas alto de la tarifa regular para aplicar el descuento en base a esta tarifa
			if(tarifaByAsiento!=null){
				maxPrecioRegular=ServiceLocator.getTarifaByAsientoDetalleManager().maxPrecioByTipoPrecio(tarifaByAsiento.getId(), plantillaPromocion.getTipoAsiento().getId(), Constantes.ID_TIPOPRECIO_REGULAR);
				if(maxPrecioRegular==0)			
					throw new Exception("No se puedo encontrar la tarifa base regular, por lo tanto no se puede aplicar la promoción al servicio");
			}else{
				throw new Exception("No se puedo encontrar la tarifa base regular, por lo tanto no se puede aplicar la promoción al servicio");
			}
			
			
			Double valorDesciento=0.00;
			if(plantillaPromocion.getIsDescuentoSoles().intValue()==Constantes.TRUE_VALUE)
				valorDesciento=plantillaPromocion.getValorDescuento();
			else
				valorDesciento=Double.valueOf((int) (maxPrecioRegular*(plantillaPromocion.getValorDescuento()/100)));				
			
			nuevoPrecio=maxPrecioRegular-valorDesciento;
			tipoPrecio = new TipoPrecio(Constantes.ID_TIPOPRECIO_PROMOCIONAL);
		}else{
			//cuando se aplica desde la seleccion de asientos del mapa
			nuevoPrecio=plantillaPromocion.getPrecio();
			tipoPrecio=plantillaPromocion.getTipoPrecio();
		}				
		
		if(tarifaByAsiento==null){
			tarifaByAsiento= new TarifaByAsiento();
			tarifaByAsiento.setFecha(fechaPartida);
			tarifaByAsiento.setRuta(ruta);
			tarifaByAsiento.setServicio(tarifarioByAvanceVentas.getServicio());
			tarifaByAsiento.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(tarifaByAsiento, getUsuario(), Executions.getCurrent());
		}
		
		TarifaByAsientoDetalle tarifaDetalle= new TarifaByAsientoDetalle();
		tarifaDetalle.setTarifaByAsiento(tarifaByAsiento);
		tarifaDetalle.setTipoAsiento(plantillaPromocion.getTipoAsiento());
		tarifaDetalle.setAsientos(plantillaPromocion.getAsientos());
		tarifaDetalle.setPrecio(nuevoPrecio);
		tarifaDetalle.setTipoPrecio(tipoPrecio);
		tarifaDetalle.setItinerario(new Itinerario(itinerarioId));
		if(plantillaPromocion.getId()!=null)
			tarifaDetalle.setTarifaByAsientoPlantillaPromocion(plantillaPromocion);
		tarifaDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(tarifaDetalle, getUsuario(), Executions.getCurrent());		
	
		ServiceLocator.getTarifaByAsientoDetalleManager().guardar(tarifaDetalle);
	}
	
	/**
	 * 
	 * @param tarifarioByAvanceVentas
	 * @param ruta
	 * @throws Exception
	 */
	private void cargarTarifario(final TarifarioByAsientoByAvanceVentas tarifarioByAvanceVentas , final TarifaByAsiento tarifa) throws Exception{		
		Util.limpiarListbox(ltbxTarifarioServicio);				
		if(tarifa!=null){
			List<TarifaByAsientoDetalle>listTarifaDetalle=ServiceLocator.getTarifaByAsientoDetalleManager().buscarByTarifaId(tarifa.getId());			
//			tarifa.setListTarifaDetalle(listTarifaDetalle);
			
			String styleColorEditable="color:blue;";
					
			for(TarifaByAsientoDetalle tarifaDetalle:listTarifaDetalle){
				if(tarifaDetalle.getItinerario()==null || (tarifaDetalle.getItinerario()!=null && tarifaDetalle.getItinerario().getId().longValue()==tarifarioByAvanceVentas.getItinerario_id().longValue())){
					boolean isEditable=false;
					if(tarifaDetalle.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_PROMOCIONAL ||
							tarifaDetalle.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR ||
							tarifaDetalle.getItinerario()!=null){
						
						if(habilitarEdicionTarifas)
							isEditable=true;
					}					
					
					Listitem item= new Listitem();
					Listcell cell=new Listcell(tarifaDetalle.getTipoAsiento().getDenominacion());
					cell.setStyle(isEditable?styleColorEditable:"");
					item.appendChild(cell);
					Listcell cellAsientos=new Listcell(tarifaDetalle.getAsientos());
					Textbox txtAsientos= new Textbox(cellAsientos.getLabel());
					txtAsientos.setVisible(false);
					txtAsientos.setWidth("95%");
					txtAsientos.setStyle("font-size:11px !important;"+(isEditable?styleColorEditable:""));
					txtAsientos.setAttribute(TarifaByAsientoDetalle.class.getName(), tarifaDetalle);					
					cellAsientos.setStyle("font-size:11px !important;"+(isEditable?styleColorEditable:""));
					cellAsientos.appendChild(txtAsientos);
					item.appendChild(cellAsientos);
					
					Listcell cellPrecio=new Listcell();
					cellPrecio.setStyle("font-size:11px !important;text-align: right;"+(isEditable?styleColorEditable:""));
					Doublebox dbbxPrecio= new Doublebox();
					dbbxPrecio.setVisible(false);
					dbbxPrecio.setWidth("90%");
					dbbxPrecio.setFormat("#,###.00");
					dbbxPrecio.setLocale(Locale.US);
					if(tarifaDetalle.getPrecio().doubleValue()==0 && tarifaDetalle.getValorDescuento()!=null && tarifaDetalle.getValorDescuento().doubleValue()>0){
						dbbxPrecio.setValue(tarifaDetalle.getValorDescuento());
						cellPrecio.setLabel("% "+Util.toNumberFormat(dbbxPrecio.getValue(), 2));
					}else{
						dbbxPrecio.setValue(tarifaDetalle.getPrecio());
						cellPrecio.setLabel("S/ "+Util.toNumberFormat(dbbxPrecio.getValue(), 2));
					}
					dbbxPrecio.setAttribute(TarifaByAsientoDetalle.class.getName(), tarifaDetalle);					
					cellPrecio.appendChild(dbbxPrecio);
					item.appendChild(cellPrecio);	
					
					cell=new Listcell(tarifaDetalle.getTipoPrecio().getDenominacion());
					cell.setStyle(isEditable?styleColorEditable:"");
					item.appendChild(cell);
					cell= new Listcell();
					Image imgAnular= new Image("/resources/mp_anular.png");
					imgAnular.setTooltiptext("Anular Tarifa");
					imgAnular.setAttribute(TarifaByAsientoDetalle.class.getName(), tarifaDetalle);
					cell.appendChild(imgAnular);					
					if(isEditable)
						item.appendChild(cell);
					else
						item.appendChild(new Listcell());
					
					cell=new Listcell(Constantes.FORMAT_LONG.format(tarifaDetalle.getFechaInsercion()));
					cell.setStyle(isEditable?styleColorEditable:"");
					item.appendChild(cell);
					
					cell=new Listcell(tarifaDetalle.getUsuarioInsercion());
					cell.setStyle("font-size:10px !important;text-transform:lowercase;"+(isEditable?styleColorEditable:""));
					item.appendChild(cell);
					
					/*Agrega el botton anular, solo para las tarifas promocionales*/
					if(isEditable){
						imgAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							@Override
							public void onEvent(final Event event) throws Exception {
								Messagebox.show("¿Realmente sea anular la tarifa?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
									@Override
									public void onEvent(Event e){
										if(e.getName().equals("onYes")){
											try {
												TarifaByAsientoDetalle anulaTarifaDetalle=ServiceLocator.getTarifaByAsientoDetalleManager().buscarPorId(((TarifaByAsientoDetalle)event.getTarget().getAttribute(TarifaByAsientoDetalle.class.getName())).getId().longValue());
												anulaTarifaDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
												UtilData.auditarRegistro(anulaTarifaDetalle, true, getUsuario(), Executions.getCurrent());
												ServiceLocator.getTarifaByAsientoDetalleManager().actualizar(anulaTarifaDetalle);
												
												cargarTarifario(tarifarioByAvanceVentas,tarifa);
												cargarPlantillasPromociones(tarifarioByAvanceVentas, tarifa);
											} catch (Exception e2) {
												e2.printStackTrace();
												DlgMessage.error(e2.getMessage());
											}
											
										}
									}
								});
							}
						});	
						
						cellAsientos.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();																
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}							
							}
						});
						txtAsientos.addEventListener(Events.ON_OK, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();	
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}
							}
						});
						txtAsientos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();	
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}
							}
						});			
						
						
						cellPrecio.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}							
							}
						});
						
						dbbxPrecio.addEventListener(Events.ON_OK, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();	
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}
							}
						});
						dbbxPrecio.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								try {
									onEvent_control(event);
									event.stopPropagation();	
								} catch (Exception e) {
									e.printStackTrace();
									DlgMessage.error(e.getMessage());
								}
							}
						});						
					}
					
					item.setValue(tarifaDetalle);
					ltbxTarifarioServicio.appendChild(item);					
				}								
			}	
		}			
	}
	
	@SuppressWarnings({ "deprecation","unchecked" })
	public void crearEstructura(Integer idServicio, Groupbox grpbxParent, boolean esRetorno, final DetalleItinerario detalleItinerario, Map<String, Asiento> mapaAsientos, 
								final Grid gridOcupabilidad, Grid gridPiso, String identifica, boolean habilitarEdicion){
		try{
			
			/*Retorna el numero de asiento seleccionado*/
			EventListener<Event> selectedEventListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getTarget() instanceof Asiento) {
						if(oEventListenerFilter !=null){
//							asientoSeleccionado=(Asiento)event.getTarget();	
							oEventListenerFilter.onEvent(new Event(pe.itsb.vyrbus.view.ui.Events.ON_SELECT));
						}
					}
				}
			};
		
			Servicio servicio = null;
			List<MapaBus> lstMapaBus = ServiceLocator.getMapaBusManager().buscarMapaBus(idServicio, Constantes.VALUE_ACTIVO);			
			
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
			prefijoAsiento = "imgAsientoIdaPiso1_"+identifica;
			if(esRetorno)
				prefijoAsiento = "imgAsientoRetornoPiso1_"+identifica;
			Integer numeroAsiento = 0;
			
			inicializarEstructura(grpbxParent);
			
			Image imagen = generarImagen(IMAGE_PRIMER_PISO, 154, 43);
			
			mapaAsientos = new HashMap<String, Asiento>();
			this.mapaAsientos = new HashMap<String, Asiento>();
			
			for(int i=0; i<nPisos; i++){
				String idGrid = "grdIdaPiso1"+identifica;
				if(i==1){
					nFilas = servicio.getNumeroFilasPiso2();
					nColumnas = servicio.getNumeroColumnasPiso2();
					prefijoAsiento = "imgAsientoIdaPiso2_"+identifica;
					idGrid = "grdIdaPiso2"+identifica;
					if(esRetorno){
						prefijoAsiento = "imgAsientoRetornoPiso2_";
						idGrid = "grdRetornoPiso2";
					}
					imagen = generarImagen(IMAGE_SEGUNDO_PISO, 154, 34);
					
				}
				gridPiso = new Grid();
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
				for(int j=0; j<nFilas; j++){
					row = new Row();
					for(int k=0; k<nColumnas; k++){
						Div oDiv = new Div();
						oDiv.setWidth("28px");
						oDiv.setHeight("28px");
						oDiv.setStyle("padding:none");
						
						String coordenadaActual = j+"-"+k+"-"+i;
						
						for(Coordenada coordenada : mapCoordenadas.keySet()){
							if(coordenada.toString().equals(coordenadaActual)){
								MapaBus objetoBus = mapCoordenadas.get(coordenada);
								
								HashMap<String, String> propiedades = new HashMap<String, String>();
								numeroAsiento++;
								
								if(objetoBus.getTipoObjeto().intValue()==TIPO_ASIENTO){
									Asiento asiento = new Asiento();
									asiento.addEventListener(Events.ON_CLICK, selectedEventListener);
									
									if(habilitarEdicion){
										asiento.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
											public void onEvent(Event e){
												onClickAsiento(e,detalleItinerario,gridOcupabilidad );
											}
										});
									}									
									
									propiedades.put("ocupante", "pasajero");
									asiento.setId(prefijoAsiento + objetoBus.getNumeroAsiento());
									asiento.setOcupante(Asiento.OCUPANTE_PASAJERO);
									asiento.setEstadoAsiento(Constantes.ASIENTO_DISPONIBLE);
									asiento.setFila(j);
									asiento.setColumna(k);
									asiento.setPiso(i);
									asiento.setNumeroAsiento(objetoBus.getNumeroAsiento());
									asiento.setSrc(objetoBus.getPathImagen());
									asiento.setPropiedades(propiedades);
									asiento.setDraggable("true");
									asiento.setDetalleItinerario(detalleItinerario);
									asiento.setKey();
									asiento.setStyle("cursor:pointer;");
									asiento.setTipoAsiento(objetoBus.getTipoAsiento());
									oDiv.appendChild(asiento);
									/*	Agregando los asientos a un Hashmap*/
									mapaAsientos.put(asiento.getKey(), asiento);
									this.mapaAsientos.put(asiento.getKey(), asiento);
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
						row.setStyle("padding-top:2px; padding-left:3px; padding-right:0px; border:normal !important; background:#99D9EA");// background:#eeeeee");
					}
					rows.appendChild(row);
				}
				gridPiso.appendChild(rows);
				grpbxParent.appendChild(gridPiso);
				onRefreshMapaAsientos(mapaAsientos, detalleItinerario, gridOcupabilidad);
//				if(esRetorno)
//					mapaAsientosRetorno = mapaAsientos;
//				else
					mapAsientosId = mapaAsientos;					
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Evento utilizado cuando el usuario hace click en un asiento.
	 * @param e					: Evento
	 * @param mapaAsientos				: Mapa de asientos.
	 * @param ventaPasaje	: Itinerario seleccionado.
	 * @param esIda				: Indica si es Ida o retorno.
	 */
	private void onClickAsiento(Event e,DetalleItinerario detalleItinerario, Grid gridOcupabilidad){
		Asiento asientoSeleccionado =(Asiento)e.getTarget();
		//String _asientoSeleccionado=(asientoSeleccionado.getNumeroAsiento().toString().length()==1?"0"+asientoSeleccionado.getNumeroAsiento().toString():asientoSeleccionado.getNumeroAsiento().toString());
		String _asientoSeleccionado=asientoSeleccionado.getNumeroAsiento().toString();
		
		Textbox _txtAsientos;
		if(asientoSeleccionado.getTipoAsiento().getId().intValue()==Constantes.ID_TIPASI_SUITE){		
			_txtAsientos=txtAsientosSuite;
			dbxValorTarifaSuite.setFocus(true);
		}else{
			_txtAsientos=txtAsientosCamaSemicama;
			dbxValorTarifaCamaSemicama.setFocus(true);
		}
		
//		previousKey = key;
//		key = asientoSeleccionado.getKey();
//		
		boolean existeAsiento=false;
		String[] yAsientosYaSeleccionados=_txtAsientos.getText().trim().split(",");
		for(String asiento : yAsientosYaSeleccionados){			
			if(asiento.equals(_asientoSeleccionado))
				existeAsiento=true;
		}
		if(existeAsiento){			
			_txtAsientos.setText(_txtAsientos.getText().trim().replace(_asientoSeleccionado+",",""));
			_txtAsientos.setText(_txtAsientos.getText().trim().replace(","+_asientoSeleccionado,""));
			_txtAsientos.setText(_txtAsientos.getText().trim().replace(_asientoSeleccionado,""));
		}else{
			String asientos=_txtAsientos.getText().trim();
			
			_txtAsientos.setText(asientos.isEmpty()?_asientoSeleccionado:asientos+","+_asientoSeleccionado);
		}
			
				
	}
	
	@SuppressWarnings("unchecked")
	public void onRefreshMapaAsientos(Map<String, Asiento> mapaAsientos, DetalleItinerario odetalleItinerario, Grid gridOcupabilidad){
		try{
						
			final DetalleItinerario detalleItinerario=ServiceLocator.getDetalleItinerarioManager().buscarPorId(odetalleItinerario.getId());
			//onCleanMap(mapaAsientos);
			/*	Obtenemos el subconjunto que queremos buscar segun la ruta seleccioanda		*/
			List<Integer> subConjuntoBuscar = obtenerSubconjunto(detalleItinerario.getItinerario().getListSecuenciaTramo(), detalleItinerario.getRuta().getLocalidadOrigen().getId(), detalleItinerario.getItinerario().getRuta().getLocalidadDestino().getId());
			
			List<VentaPasaje> lstVentas = ServiceLocator.getVentaPasajesManager().buscarVentasForMapaBus(detalleItinerario.getItinerario().getId());
			lstVentas = obtenerConjuntos(lstVentas, detalleItinerario.getItinerario().getListSecuenciaTramo());
			if(lstVentas.size()>0){
				for(VentaPasaje venta : lstVentas){
					String key = venta.getNumeroAsiento()+"-"+(venta.getNumeroPiso());
					//String key = venta.getNumeroAsiento()+"-"+ 0;
					for(Integer orden : subConjuntoBuscar){
						if(mapaAsientos.containsKey(key) && venta.getSubConjunto().contains(orden)){
							//if(mapaAsientos.containsKey(key)){
							Asiento asiento = mapaAsientos.get(key);
							/*	Para identificar si la venta es completa o es un tramo	*/
							if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_VENTA)){
								if(venta.getPasajero().getSexo().getId().intValue()==Constantes.ID_SEXO_FEMENINO)
									asiento.setSrc(Constantes.ICON_VENDIDO_FEMALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_VENDIDO_MALE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_VENDIDO);
							}else if(venta.getTipoTransaccion().equals(Constantes.TIPO_OPERACION_RESERVA)){
								if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_CALLCENTER)
									asiento.setSrc(Constantes.ICON_RESERVADO_DELIVERY+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else if(venta.getCanalVenta().getId().intValue()==Constantes.ID_CANVEN_AGENTE)
									asiento.setSrc(Constantes.ICON_RESERVADO_AGENTE+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								else
									asiento.setSrc(Constantes.ICON_RESERVADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);
								asiento.setEstadoAsiento(Constantes.ASIENTO_RESERVADO);
							}
							asiento.setTooltiptext(venta.getRuta().toString()+"\n"+venta.getPasajero().toString());
							break;
						}	
					}
					/*	Para identificar las prioridades del tramos para la venta	*/
					if(venta.getRuta().getLocalidadDestino().getId()==detalleItinerario.getRuta().getLocalidadOrigen().getId()){
						Asiento asiento = mapaAsientos.get(key);
						asiento.setSrc(Constantes.ICON_SEMI_OCUPADO+venta.getNumeroAsiento()+Constantes.IMAGE_EXTENSION);								
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
			//mostrarOcupabilidad(nOcupados, detalleItinerario, gridOcupabilidad);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	/**
	 * Obtine los subconjuntos de un registro de venta, tmpocupacion o de la
	 * ruta que estamos buscando.
	 * @param lstSecuencias : Lista de secuencia segun el itinerario.
	 * @param idOrigen : Identificador del origen.
	 * @param idDestino: Identificador del destino.
	 * @return
	 */
	private List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, Integer idOrigen,Integer idDestino) {
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		/* Recorremos la secuencia de tramos del itinerario */
		for (int j = 0; j < lstSecuencias.size(); j++) {
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			/*
			 * Validamos si el origen de la secuencia coincide con el origen de
			 * la ruta
			 */
			if (secuencia.getOrigen().intValue() == idOrigen.intValue()) {
				/* Recorremos la secuencia de tramos desde la posicion j */
				for (int k = j; k < lstSecuencias.size(); k++) {
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					/*
					 * Validamos si el destino de la secuencia coincide con el
					 * destino de la ruta
					 */
					if (secuencia.getDestino().intValue() == idDestino.intValue())
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	
	/**
	 * Obtiene los subconjuntos de una lista de ventas, tmpOcupacion.
	 * @param lista : Lista de registros de los cuales queremos obtener los subconjuntos.
	 * @param lstSecuencias : Lista de secuencia segun el itinerario.
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	private List obtenerConjuntos(List lista, List<SecuenciaTramo> lstSecuencias) {
		List result = new ArrayList();
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof TmpOcupacionAsientos) {
				TmpOcupacionAsientos tmp = (TmpOcupacionAsientos) obj;
				List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias,
						tmp.getRuta().getLocalidadOrigen().getId(), tmp
								.getRuta().getLocalidadDestino().getId());
				tmp.setSubConjunto(subConjunto);
				result.add(tmp);
			} else if (obj instanceof VentaPasaje) {
				VentaPasaje ventaPasaje = (VentaPasaje) obj;
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
	 * Inicializa(limpia los objetos existentes) el contenedor de los asientos.
	 */
	private void inicializarEstructura(Groupbox groupbox){
		for(int i=groupbox.getChildren().size()-1; i>-1; i--){
			Component component = (Component)groupbox.getChildren().get(i);
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
	 * 
	 */
	public void onBuscarPlatillasAsientos(){
		try {
			Util.limpiarListbox(listCPA_listado);
			List<TarifaByAsientoPlantillaEncabezado> lstplantilla=ServiceLocator.getTarifaByAsientoPlantillaEncabezadoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "id");
			int x=0;
			for(TarifaByAsientoPlantillaEncabezado plantilla: lstplantilla){
				x++;
				Listitem item= new Listitem();
				Listcell cell= new Listcell(String.valueOf(x));
				item.appendChild(cell);
				cell= new Listcell(plantilla.getEmpresa()!=null? plantilla.getEmpresa().getNombreCorto(): "TODOS");
				item.appendChild(cell);
				cell= new Listcell(plantilla.getTipoPrecio().getDenominacion());
				item.appendChild(cell);
				cell= new Listcell(plantilla.getDenominacion());
				item.appendChild(cell);
				
				Hbox hbox= new Hbox();
				hbox.setAlign("center");
				Toolbarbutton btnEditar= new Toolbarbutton("", "resources/mp_editarEnabled.png");
				btnEditar.setTooltiptext("Editar");
				btnEditar.setAutodisable("self");
				btnEditar.setAttribute(TarifaByAsientoPlantillaEncabezado.class.getName(), plantilla);
				hbox.appendChild(btnEditar);
				
				Toolbarbutton btnAnular= new Toolbarbutton("", "resources/mp_anular.png");
				btnAnular.setTooltiptext("Anular");
				btnAnular.setAutodisable("self");
				btnAnular.setAttribute(TarifaByAsientoPlantillaEncabezado.class.getName(), plantilla);
				hbox.appendChild(btnAnular);
				cell= new Listcell();
				cell.appendChild(hbox);
				item.appendChild(cell);
				
				
				btnEditar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						limpiarControlMntoPlantilla();
						Util.limpiarListbox(lstCPA_plantillaAsientosDetalle);
						
						cpa_tarifaPlantillaEncabezado=(TarifaByAsientoPlantillaEncabezado) arg0.getTarget().getAttribute(TarifaByAsientoPlantillaEncabezado.class.getName());
						if(cpa_tarifaPlantillaEncabezado.getEmpresa()!=null)
							Util.seleccionarValorItemCombo(Empresa.class, cmbCPA_empresa, cpa_tarifaPlantillaEncabezado.getEmpresa().getId());
						else
							cmbCPA_empresa.setSelectedIndex(0);
						txtCPA_nombre.setText(cpa_tarifaPlantillaEncabezado.getDenominacion());
						Util.seleccionarValorItemCombo(TipoPrecio.class, cmbCPA_tipoPrecio, cpa_tarifaPlantillaEncabezado.getTipoPrecio().getId());
						
						TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
						criteriosBusqueda.put("tarifaByAsientoPlantillaEncabezado", cpa_tarifaPlantillaEncabezado);
						criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						List<String> criteriosOrdenar= new ArrayList<>();
						criteriosOrdenar.add("orden");
						List<TarifaByAsientoPlantillaEncabezadoDetalle> result=ServiceLocator.getTarifaByAsientoPlantillaEncabezadoDetalleManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
						
						for(TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle: result){
							addItemDetallePalntilla(encabezadoDetalle);												
						}												
						tabCPA_MantenimientoPlantillaAsientos.setSelected(true);
						habilitarControlsMntoPlatilla(false);
						cmbCPA_empresa.setDisabled(true);
						btnCPA_Guardar.setDisabled(false);
					}
				});
				
				
				btnAnular.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(final Event arg0) throws Exception {
						Messagebox.show("¿Realmente desea anular el Registro?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception{
								if(e.getName().equals("onYes")){
									TarifaByAsientoPlantillaEncabezado plantillaEncabezado=(TarifaByAsientoPlantillaEncabezado) arg0.getTarget().getAttribute(TarifaByAsientoPlantillaEncabezado.class.getName());
									plantillaEncabezado.setEstadoRegistro(Constantes.VALUE_INACTIVO);
									UtilData.auditarRegistro(plantillaEncabezado, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getTarifaByAsientoPlantillaEncabezadoManager().actualizar(plantillaEncabezado);
									
									//Recarga la lista
									onBuscarPlatillasAsientos();
								}
							}
						});
						
					}
				});
				
				item.setValue(plantilla);
				listCPA_listado.appendChild(item);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param isDisabled
	 * @throws Exception
	 */
	private void habilitarControlsMntoPlatilla(boolean isDisabled)throws Exception{
		cmbCPA_empresa.setDisabled(isDisabled);
		txtCPA_nombre.setDisabled(isDisabled);
		cmbCPA_tipoPrecio.setDisabled(isDisabled);
		cmbCPA_TipoAsiento.setDisabled(isDisabled);
		txtCPA_Asientos.setDisabled(isDisabled);
		btnCPA_agregar.setDisabled(isDisabled);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void limpiarControlMntoPlantilla()throws Exception{
		cmbCPA_empresa.setSelectedIndex(0);
		txtCPA_nombre.setText("");
		cmbCPA_tipoPrecio.setSelectedIndex(0);
		cmbCPA_TipoAsiento.setSelectedIndex(0);
		txtCPA_Asientos.setText("");
	}
	
	/**
	 * 
	 * @param encabezadoDetalle
	 * @throws Exception
	 */
	private void addItemDetallePalntilla(TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle)throws Exception{		
		Listitem item= new Listitem();
		Listcell cell= new Listcell(String.valueOf(lstCPA_plantillaAsientosDetalle.getItemCount()+1));
		item.appendChild(cell);
		cell= new Listcell(encabezadoDetalle.getTipoAsiento().getDenominacion());
		item.appendChild(cell);
		cell= new Listcell(encabezadoDetalle.getAsientos());
		cell.setStyle("font-size:12px !important");
		item.appendChild(cell);							
		
		Hbox _hbox= new Hbox();							
		Toolbarbutton btnEliminar= new Toolbarbutton("", "resources/mp_eliminarEnabled.png");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.setAttribute(TarifaByAsientoPlantillaEncabezadoDetalle.class.getName(), encabezadoDetalle);
		btnEliminar.setAttribute(Listitem.class.getName(), item);
		_hbox.appendChild(btnEliminar);
		cell= new Listcell();
		cell.appendChild(_hbox);
		item.appendChild(cell);
		
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event arg0)throws Exception {
				Messagebox.show("¿Realmente desea anular el Registro?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception{
						if(e.getName().equals("onYes")){
							TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle=(TarifaByAsientoPlantillaEncabezadoDetalle) arg0.getTarget().getAttribute(TarifaByAsientoPlantillaEncabezadoDetalle.class.getName());
							if(encabezadoDetalle.getId()!=null){
								encabezadoDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								UtilData.auditarRegistro(encabezadoDetalle, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getTarifaByAsientoPlantillaEncabezadoDetalleManager().actualizar(encabezadoDetalle);
							}							
							
							Listitem deleteItem=(Listitem) arg0.getTarget().getAttribute(Listitem.class.getName());
							lstCPA_plantillaAsientosDetalle.removeItemAt(deleteItem.getIndex());
						}
					}
				});									
			}
		});
		
		item.setValue(encabezadoDetalle);
		lstCPA_plantillaAsientosDetalle.appendChild(item);		
	}
	
	/**
	 * 
	 */
	public void onEvent_btnCPA_Add(){
		try {
			
			if(cmbCPA_TipoAsiento.getSelectedIndex()<=0){
				DlgMessage.information("Debe de seleccionar el tipo de Asiento.");
				return;
			}else if (txtCPA_Asientos.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar los numeros de Asientos");
				return;
			}
				
			TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle=new TarifaByAsientoPlantillaEncabezadoDetalle();
			encabezadoDetalle.setTipoAsiento((TipoAsiento)cmbCPA_TipoAsiento.getSelectedItem().getValue());
			encabezadoDetalle.setAsientos(txtCPA_Asientos.getText().trim());
			
			addItemDetallePalntilla(encabezadoDetalle);
			
			cmbCPA_TipoAsiento.setSelectedIndex(0);
			txtCPA_Asientos.setText("");
			cmbCPA_TipoAsiento.setFocus(true);
			cmbCPA_TipoAsiento.select();
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void onEvent_btnCPA_Nuevo(){
		try {
			cpa_tarifaPlantillaEncabezado= null;
			limpiarControlMntoPlantilla();
			Util.limpiarListbox(lstCPA_plantillaAsientosDetalle);
			habilitarControlsMntoPlatilla(false);
			btnCPA_Nuevo.setDisabled(true);
			btnCPA_Guardar.setDisabled(false);
			btnCPA_Cancelar.setDisabled(false);
			
			cmbCPA_empresa.setFocus(true);
			cmbCPA_empresa.select();
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void onEvent_btnCPA_Guardar(){
		try {
			boolean isNuevo=(cpa_tarifaPlantillaEncabezado==null);
			if(isNuevo)
				cpa_tarifaPlantillaEncabezado= new TarifaByAsientoPlantillaEncabezado();
			if(cmbCPA_empresa.getSelectedItem().getValue() instanceof Empresa)
				cpa_tarifaPlantillaEncabezado.setEmpresa(cmbCPA_empresa.getSelectedItem().getValue());
			cpa_tarifaPlantillaEncabezado.setTipoPrecio((TipoPrecio)cmbCPA_tipoPrecio.getSelectedItem().getValue());
			cpa_tarifaPlantillaEncabezado.setDenominacion(txtCPA_nombre.getText().toUpperCase().trim());
			cpa_tarifaPlantillaEncabezado.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			if(isNuevo)
				UtilData.auditarRegistro(cpa_tarifaPlantillaEncabezado, getUsuario(), Executions.getCurrent());
			else				
				UtilData.auditarRegistro(cpa_tarifaPlantillaEncabezado, getUsuario(), Executions.getCurrent());			
			
			List<TarifaByAsientoPlantillaEncabezadoDetalle> listPlantillaEncabezadoDetalles= new ArrayList<>();
			for(Listitem item: lstCPA_plantillaAsientosDetalle.getItems()){
				TarifaByAsientoPlantillaEncabezadoDetalle encabezadoDetalle=item.getValue();
				if(encabezadoDetalle.getId()==null){					
					encabezadoDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);
					UtilData.auditarRegistro(encabezadoDetalle, getUsuario(), Executions.getCurrent());					
					listPlantillaEncabezadoDetalles.add(encabezadoDetalle);
				}				
			}
			
			ServiceLocator.getTarifaByAsientoPlantillaEncabezadoManager().guardar(cpa_tarifaPlantillaEncabezado, listPlantillaEncabezadoDetalles);
			habilitarControlsMntoPlatilla(true);
			
			btnCPA_Nuevo.setDisabled(false);
			btnCPA_Guardar.setDisabled(true);
			
			onBuscarPlatillasAsientos();
			//
			cargarPlantillaEcabezado();//Actualiza la lista de plantillas en la pestaña de creacción de tarifas
			//
			DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void onEvent_btnPCA_Cancelar(){
		try {
			limpiarControlMntoPlantilla();
			Util.limpiarListbox(lstCPA_plantillaAsientosDetalle);
			habilitarControlsMntoPlatilla(true);
			
			if(btnCPA_Guardar.isDisabled())			
				tabCPA_ListPlantillaAsientos.setSelected(true);				
			else{
				btnCPA_Nuevo.setDisabled(false);
				btnCPA_Guardar.setDisabled(true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	
	/* *************************************************************************/
	/** **************CODIGOS PROMOCIONALES*************************************/
	/* *************************************************************************/
	public void onEvent_chbxCP_omitirFechas(){
		try {
			dtbxCP_FechaInicio.setDisabled(chbxCP_omitirFechas.isChecked());
			dtbxCP_FechaFin.setDisabled(chbxCP_omitirFechas.isChecked());
			
			if(chbxCP_omitirFechas.isChecked()){
				dtbxCP_FechaInicio.setValue(null);
				dtbxCP_FechaFin.setValue(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_buscar(){
		try {
			Util.limpiarListbox(ltbxCP_codigosPromos);
			
			if(txtCP_busqDenominacion.getText().trim().isEmpty() && txtCP_busqCodigo.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar un patrón de la búsqueda.",txtCP_busqCodigo);
				return;
			}
			
			String fechaInicio = null;  //(dtbxCP_FechaInicio.getValue()!=null?Constantes.FORMAT_DATE.format(dtbxCP_FechaInicio.getValue()):null);
			String fechaFinal = null; //(dtbxCP_FechaFin.getValue()!=null?Constantes.FORMAT_DATE.format(dtbxCP_FechaFin.getValue()):null);
			String denominacion=(txtCP_busqDenominacion.getText().trim().isEmpty()?null:txtCP_busqDenominacion.getText().trim().toUpperCase());
			String codigo =txtCP_busqCodigo.getText().trim().isEmpty()?null:txtCP_busqCodigo.getText().trim().toUpperCase();
			
			List<CuponPromocional>result=ServiceLocator.getCuponPromocionalManager().buscar(fechaInicio, fechaFinal, denominacion,codigo);
			for(CuponPromocional codigoPromocional: result){
				Listitem item= new Listitem();
				Listcell cell= new Listcell(codigoPromocional.getDenominacion());
				item.appendChild(cell);
				cell= new Listcell(codigoPromocional.getRutas()!=null?"":"TODAS");
				item.appendChild(cell);
				cell= new Listcell(codigoPromocional.getServicios()!=null?"":"TODOS");
				item.appendChild(cell);
				String canalesVenta="";
				if(codigoPromocional.getCanalesVenta()!=null){
					List<Integer> listCanalesVenta=codigoPromocional.getListCanalesVentaId();					
					for(Integer idCanalVenta: listCanalesVenta){
						CanalVenta canalVenta= ServiceLocator.getCanalVentaManager().buscarPorId(idCanalVenta.longValue());						
						canalesVenta +=(canalesVenta.trim().isEmpty()?"["+canalVenta.toString():" , "+canalVenta.toString());
					}
					canalesVenta += "]";					
				}else
					canalesVenta="TODOS";
				cell= new Listcell(canalesVenta);
				item.appendChild(cell);				
				cell= new Listcell(codigoPromocional.getAsientos()!=null?codigoPromocional.getAsientos():"TODOS");
				if(codigoPromocional.getAsientos()!=null)
					cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell= new Listcell(Util.toNumberFormat(codigoPromocional.getValorDescuento(), 2));
				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell= new Listcell(codigoPromocional.getTipoDescuento().equals("P")?"%":"S/");
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				cell= new Listcell(codigoPromocional.getCodigo());
				cell.setStyle("font-size:11px !important;color:red;font-weight:bold");
				item.appendChild(cell);
				cell= new Listcell(codigoPromocional.getValorMaximoDescuento()!=null?Util.toNumberFormat(codigoPromocional.getValorMaximoDescuento(),2):"");
				if(codigoPromocional.getValorMaximoDescuento()!=null)
					cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell= new Listcell(Constantes.FORMAT_DATE.format(codigoPromocional.getFechaInicial()));
				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);
				cell= new Listcell(Constantes.FORMAT_DATE.format(codigoPromocional.getFechaFinal()));
				cell.setStyle("font-size:12px !important");
				item.appendChild(cell);											
				
				item.setContext(getMenucontex(codigoPromocional));
				item.setValue(codigoPromocional);
				ltbxCP_codigosPromos.appendChild(item);
			}						
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}		
	}
	
	private Menupopup getMenucontex(final CuponPromocional cuponPromocional)throws Exception{
		String idContext=cuponPromocional.getId().toString();
		
		/*Elimina el componemte que coincida con el nuevo a agregar*/
		for(Component component :this.wndTarifaByAsiento.getChildren()){
			if(component instanceof Menupopup){
				Menupopup menupopup= (Menupopup)component;
				if(menupopup.getId().equals(idContext)){
					this.wndTarifaByAsiento.removeChild(component);
					break;
				}
			}
		}		
		menupopup= new Menupopup();
		menupopup.setId(idContext);
				
		Menuitem menuitem=new Menuitem("Anular Cupón","/resources/mp_cerrar.png");		
		menuitem.setDisabled(!cuponPromocional.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO));				
		menuitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				try {
					Messagebox.show("¿Realmente esta seguro de anular el Cupón?", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
						public void onEvent(Event e) throws Exception{						
							if(e.getName().equals("onYes")){
								CuponPromocional oCuponPromocional= ServiceLocator.getCuponPromocionalManager().buscarPorId(cuponPromocional.getId());
								oCuponPromocional.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								UtilData.auditarRegistro(oCuponPromocional, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getCuponPromocionalManager().actualizar(oCuponPromocional);
								
								onEvent_btnCP_buscar();
							}
						}
					});							
				} catch (Exception e) {
					e.printStackTrace();
					DlgMessage.error(e.getMessage());
				}
			}
		});		
		menupopup.appendChild(menuitem);		
		
		wndTarifaByAsiento.appendChild(menupopup);
		return menupopup;
	}	
	
	public void onEvent_btnCP_busqRutas(){
		try {
			
			onCrearVentana("Asignación de Rutas al Cupón Promocional", "Rutas", Ruta.class);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_busqServicios(){
		try {
			
			onCrearVentana("Asignación de Servicios al Cupón Promocional", "Servicios", Servicio.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	
	public void onEvent_btnCP_busqCanalesVenta(){
		try {
			
			onCrearVentana("Asignación de Canales de Venta al Cupón Promocional", "Canales Venta", CanalVenta.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_busqTipoAsientos(){
		try {
			
			onCrearVentana("Asignación de Tipos de Asiento al Cupón Promocional", "Canales Venta", TipoAsiento.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_Nuevo(){
		try {
			limapiarControlesCuponPromo();
			habilitarControlsCuponPromo(true);
			txtCP_denominacion.setFocus(true);
			
			chbxIdaVuelta_AplicarIda.setVisible(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarRetorno.setVisible(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarIda.setChecked(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarRetorno.setChecked(rdIdaVuelta.isChecked());
			
			btnCP_Nuevo.setDisabled(true);
			btnCP_Guardar.setDisabled(false);
			btnCP_Cancelar.setDisabled(false);
			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_Guardar(){
		try {			
			if(txtCP_denominacion.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar la el nombre o denominación",txtCP_denominacion);
				return;
			}else if (dbxCP_valorDescuento.getValue()==null || dbxCP_valorDescuento.getValue()<=0){
				DlgMessage.information("Debe de ingresar el valor del descuento",dbxCP_valorDescuento);
				return;
			}else if (cmbCP_tipoDescuento.getSelectedIndex()<=0){
				DlgMessage.information("Debe de seleccionar el tipo de descuento",cmbCP_tipoDescuento);
				return;
			}else if(txtCP_codigo.getText().trim().isEmpty()){
				DlgMessage.information("Debe de ingresar el Cupón Promocional",txtCP_codigo);
				return;
			}else if (dbxCP_valorMaximoDesciento.getValue()==null){
				DlgMessage.information("Debe de ingresar el valor máximo permitido para el descuento, expresado en Soles",dbxCP_valorMaximoDesciento);
				return;
			}else if (dtbxCP_validoDesde.getValue()==null){
				DlgMessage.information("Debe de seleccionar la fecha desde que el Cupón Promocional es válido",dtbxCP_validoDesde);
				return;				
			}else if (dtbxCP_validoHasta.getValue()==null){
				DlgMessage.information("Debe de seleccionar la fecha en que finaliza el valor del Cupón Promocional",dtbxCP_validoHasta);
				return;				
			}
			
			final boolean isNuevo=(cuponPromocional==null);
			if(isNuevo){
				cuponPromocional=new CuponPromocional();
			
				//Valida que el código promocional no exista en la base de datos
				TreeMap<String, Object>criteriosBusqueda= new TreeMap<>();
				criteriosBusqueda.put("codigo", txtCP_codigo.getText().trim().toUpperCase());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<CuponPromocional> resultDupl= ServiceLocator.getCuponPromocionalManager().buscarPorX(criteriosBusqueda, null);
				if(resultDupl.size()>0){
					DlgMessage.information("El Cupón "+txtCP_codigo.getText().trim().toUpperCase()+" ya esta registrado.",txtCP_codigo);
					return;
				}			
			}
			
			cuponPromocional.setDenominacion(txtCP_denominacion.getText().trim().toUpperCase());
			if(!(txtCP_rutas.getText().trim().isEmpty() || txtCP_rutas.getText().trim().equals("*")))
				cuponPromocional.setRutas(txtCP_rutas.getText().trim());
			if(!(txtCP_servicios.getText().trim().isEmpty() || txtCP_servicios.getText().trim().equals("*")))
				cuponPromocional.setServicios(txtCP_servicios.getText().trim());
			if(!(txtCP_canalesVenta.getText().trim().isEmpty() || txtCP_canalesVenta.getText().trim().equals("*")))
				cuponPromocional.setCanalesVenta(txtCP_canalesVenta.getText().trim());
			if(!(txtCP_tiposAsiento.getText().trim().isEmpty() || txtCP_tiposAsiento.getText().trim().equals("*")))
				cuponPromocional.setTipoAsientos(txtCP_tiposAsiento.getText().trim());
			if(chbxTipoFormaPago_PagoEfectivo.isChecked())
				cuponPromocional.setTipoFormaPagos(String.valueOf(Constantes.ID_TIPFORPAG_PAGOEFECTIVO));
			
			cuponPromocional.setCantidaMaximaServicio(itbxCP_cantMaxPorServicio.getValue()!=null?itbxCP_cantMaxPorServicio.getValue():0);			
			cuponPromocional.setValorDescuento(dbxCP_valorDescuento.getValue());
			cuponPromocional.setTipoDescuento(cmbCP_tipoDescuento.getSelectedItem().getValue().toString());
			cuponPromocional.setCodigo(txtCP_codigo.getText().trim().toUpperCase());
			cuponPromocional.setValorMaximoDescuento(dbxCP_valorMaximoDesciento.getValue());
			cuponPromocional.setFechaInicial(dtbxCP_validoDesde.getValue());
			cuponPromocional.setFechaFinal(dtbxCP_validoHasta.getValue());			
			if(rdTodas.isChecked())
				cuponPromocional.setIdaVuelta("*");
			else if(rdIdaVuelta.isChecked())
				cuponPromocional.setIdaVuelta("S");
			else if (rbNoIdaVuelta.isChecked())
				cuponPromocional.setIdaVuelta("N");
			if(chbxIdaVuelta_AplicarIda.isChecked() && chbxIdaVuelta_AplicarRetorno.isChecked())
				cuponPromocional.setTipoIdaVuelta("*");
			else if (chbxIdaVuelta_AplicarIda.isChecked() && chbxIdaVuelta_AplicarRetorno.isChecked()==false)
				cuponPromocional.setTipoIdaVuelta("IDA");
			else if (chbxIdaVuelta_AplicarIda.isChecked()==false && chbxIdaVuelta_AplicarRetorno.isChecked())
				cuponPromocional.setTipoIdaVuelta("VUE");
			cuponPromocional.setCantidaMaximaServicio(itbxCP_cantMaxPorServicio.getValue()!=null?itbxCP_cantMaxPorServicio.getValue():0);
			cuponPromocional.setTipoCupon("DES");//por ahora es fijo - 17/09/2020
			cuponPromocional.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			Messagebox.show("¿Realmente desea guardar el Cupón Promocional?.", DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_YES, new EventListener<Event>() {				
				@Override
				public void onEvent(Event e){
					if(e.getName().equals("onYes")){							
						try {
							
							if(isNuevo){
								UtilData.auditarRegistro(cuponPromocional, getUsuario(), Executions.getCurrent());
								ServiceLocator.getCuponPromocionalManager().guardar(cuponPromocional);
							}else{
								UtilData.auditarRegistro(cuponPromocional, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getCuponPromocionalManager().actualizar(cuponPromocional);
							}
															
							habilitarControlsCuponPromo(false);
							btnCP_Nuevo.setDisabled(false);
							btnCP_Guardar.setDisabled(true);
							btnCP_Cancelar.setDisabled(false);
							
							DlgMessage.information("El regsitro del Cupón Promocional se registró correctamente");
							
//							onEvent_btnCP_buscar();
							
						} catch (Exception e2) {
							e2.printStackTrace();
							DlgMessage.error(e2.getMessage());
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	public void onEvent_btnCP_Cancelar(){
		try {			
			limapiarControlesCuponPromo();
			habilitarControlsCuponPromo(false);
																	
			chbxIdaVuelta_AplicarIda.setVisible(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarRetorno.setVisible(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarIda.setChecked(rdIdaVuelta.isChecked());
			chbxIdaVuelta_AplicarRetorno.setChecked(rdIdaVuelta.isChecked());
			
			btnCPA_Nuevo.setDisabled(false);
			btnCPA_Guardar.setDisabled(true);
			btnCP_Nuevo.setDisabled(false);
			btnCP_Guardar.setDisabled(true);						
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
		
	
	
	private void onEvent_ltbxCP_cuponPromos(){
		try {
			if(ltbxCP_codigosPromos.getSelectedCount()>0){
				cuponPromocional =ltbxCP_codigosPromos.getSelectedItem().getValue();	
				txtCP_denominacion.setText(cuponPromocional.getDenominacion());
				if(cuponPromocional.getRutas()!=null)
					txtCP_rutas.setAttribute(txtCP_rutas.getName(), cuponPromocional.getRutas());
				if(cuponPromocional.getServicios()!=null)
					txtCP_servicios.setAttribute(txtCP_servicios.getName(), cuponPromocional.getServicios());
				if(cuponPromocional.getCanalesVenta()!=null)
					txtCP_canalesVenta.setAttribute(txtCP_canalesVenta.getName(), cuponPromocional.getCanalesVenta());
				if(cuponPromocional.getAsientos()!=null)
					txtCP_asientos.setText(cuponPromocional.getAsientos());
				dbxCP_valorDescuento.setValue(cuponPromocional.getValorDescuento());
				for(Comboitem comboitem: cmbCP_tipoDescuento.getItems()){
					if(comboitem.getValue()!=null && comboitem.getValue().toString().equals(cuponPromocional.getTipoDescuento())){
						cmbCP_tipoDescuento.setSelectedItem(comboitem);
						break;
					}					
				}
				txtCP_codigo.setText(cuponPromocional.getCodigo());
				dbxCP_valorMaximoDesciento.setValue(cuponPromocional.getValorMaximoDescuento()!=null?cuponPromocional.getValorMaximoDescuento():null);
				dtbxCP_validoDesde.setValue(cuponPromocional.getFechaInicial());
				dtbxCP_validoHasta.setValue(cuponPromocional.getFechaFinal());
				tabCP_mantenimiento.setSelected(true);
				
				btnCP_Nuevo.setDisabled(false);
				btnCP_Guardar.setDisabled(true);
				btnCP_Cancelar.setDisabled(false);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}
	}
	
	private void habilitarControlsCuponPromo(boolean isEnabled)throws Exception{
		boolean isDisabled= !isEnabled;
		
		txtCP_denominacion.setDisabled(isDisabled);
		txtCP_rutas.setDisabled(isDisabled);
		btnCP_busqRutas.setDisabled(isDisabled);
		txtCP_servicios.setDisabled(isDisabled);
		btnCP_busqServicios.setDisabled(isDisabled);		
		txtCP_canalesVenta.setDisabled(isDisabled);
		btnCP_busqCanalesVenta.setDisabled(isDisabled);
		txtCP_asientos.setDisabled(isDisabled);
		txtCP_tiposAsiento.setDisabled(isDisabled);
		btnCP_busqTiposAsientos.setDisabled(isDisabled);
		itbxCP_cantMaxPorServicio.setDisabled(isDisabled);
		dbxCP_valorDescuento.setDisabled(isDisabled);
		cmbCP_tipoDescuento.setDisabled(isDisabled);
		txtCP_codigo.setDisabled(isDisabled);
		dbxCP_valorMaximoDesciento.setDisabled(isDisabled);
		dtbxCP_validoDesde.setDisabled(isDisabled);
		dtbxCP_validoHasta.setDisabled(isDisabled);
		chbxTipoFormaPago_PagoEfectivo.setDisabled(isDisabled);
		rdTodas.setDisabled(isDisabled);
		rdIdaVuelta.setDisabled(isDisabled);
		rbNoIdaVuelta.setDisabled(isDisabled);
		chbxIdaVuelta_AplicarIda.setDisabled(isDisabled);
		chbxIdaVuelta_AplicarRetorno.setDisabled(isDisabled);
	}
	
	private void limapiarControlesCuponPromo()throws Exception{
		cuponPromocional=null;
		txtCP_denominacion.setText("");
		txtCP_rutas.setText("");
		txtCP_servicios.setText("");
		txtCP_canalesVenta.setText("");
		txtCP_asientos.setText("");
		txtCP_tiposAsiento.setText("");
		itbxCP_cantMaxPorServicio.setText("");
		dbxCP_valorDescuento.setText("");
		cmbCP_tipoDescuento.setSelectedIndex(0);
		txtCP_codigo.setText("");
		dbxCP_valorMaximoDesciento.setText("");
		dtbxCP_validoDesde.setValue(null);
		dtbxCP_validoHasta.setValue(null);
		chbxTipoFormaPago_PagoEfectivo.setChecked(false);
		rdTodas.setChecked(true);
		chbxIdaVuelta_AplicarIda.setChecked(false);
		chbxIdaVuelta_AplicarRetorno.setChecked(false);
	}
	
	/**
	 * Crea la ventana utilizada para seleccionar mas de una opcion para los criterios. 
	 * @param title		: Titulo de la venta a crear.
	 * @param header	: Nombre de la cabecera de la columna.
	 * @param oClase	: Tipo de objeto a utilizar para el llenado de informacion.
	 */
	@SuppressWarnings("deprecation")
	public void onCrearVentana(String title, String header, final Class<?> oClase){
		wndCriterios = new Window(title, "normal", true);
		wndCriterios.setWidth("472px");
		wndCriterios.setHeight("400px");
		
		
		Hlayout hlayout = new Hlayout();
		lbxContenedorLeft = new Listbox();
		lbxContenedorLeft.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveToRight();
			}
		});
		lbxContenedorLeft.setWidth("200px");
		lbxContenedorLeft.setHeight("320px");
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader(header, "", "180px");
		listhead.appendChild(listheader);
		lbxContenedorLeft.appendChild(listhead);
		hlayout.appendChild(lbxContenedorLeft);
		
		Vbox vbox = new Vbox();
		vbox.setWidth("40px");
		vbox.setAlign("center");
		
		Space space = new Space();
		space.setOrient("vertical");
		space.setHeight("70px");
		vbox.appendChild(space);
		
		Image imagen = new Image("resources/mp_rightArrowEnabled.png");
		imagen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveToRight();
			}
		});
		imagen.setStyle("cursor:pointer");
		vbox.appendChild(imagen);
		
		imagen = new Image("resources/mp_allRightArrowEnabled.png");
		imagen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveAllToRight();
			}
		});
		imagen.setStyle("cursor:pointer");
		vbox.appendChild(imagen);
		
		space = new Space();
		space.setOrient("vertical");
		space.setHeight("50px");
		vbox.appendChild(space);
		
		imagen = new Image("resources/mp_leftArrowEnabled.png");
		imagen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveToLeft();
			}
		});
		imagen.setStyle("cursor:pointer");
		vbox.appendChild(imagen);
		
		imagen = new Image("resources/mp_allLeftArrowEnabled.png");
		imagen.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveAllToLeft();
			}
		});
		imagen.setStyle("cursor:pointer");
		vbox.appendChild(imagen);
		hlayout.appendChild(vbox);
		
		lbxContenedorRight = new Listbox();
		lbxContenedorRight.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				moveToLeft();
			}
		});
		lbxContenedorRight.setWidth("200px");
		lbxContenedorRight.setHeight("320px");
		listhead = new Listhead();
		listheader = new Listheader(header, "", "180px");
		listhead.appendChild(listheader);
		lbxContenedorRight.appendChild(listhead);
		hlayout.appendChild(lbxContenedorRight);
		
		wndCriterios.appendChild(hlayout);
		
		space = new Space();
		space.setOrient("vertical");
		space.setHeight("5px");
		wndCriterios.appendChild(space);
		
		Div div = new Div();
		div.setAlign("center");
		Button btnAceptar = new Button("Aceptar", "resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				asignarCriterios(oClase);
			}
		});
		btnAceptar.setHeight("28px");		
		div.appendChild(btnAceptar);
		wndCriterios.appendChild(div);
		
		this.appendChild(wndCriterios);
		loadListbox(oClase, lbxContenedorLeft, lbxContenedorRight);
		wndCriterios.doModal();		
	}
	
	/**
	 * Permite cargar los listbox con información de acuerdo a la clase enviada.
	 * @param oClase				: Objetos que se mostraran en los listbox.
	 * @param lbxCriteriosXAsignar	: Listbox que contiene los criterios que se podrian asignar.
	 */
	private void loadListbox(Class<?> oClase, Listbox lbxCriteriosXAsignar, Listbox lbxCriteriosAsignados){
		try{
			if(oClase.equals(Ruta.class)){
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("origen");
				criteriosOrdenar.add("destino");
				List<Ruta> lstRutas = ServiceLocator.getRutaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				String[] criterioRutas = null;
				if(!txtCP_rutas.getText().equals("*"))
					criterioRutas = txtCP_rutas.getText().split(",");
					
				if(lstRutas.size()>0){
					Listitem listitem = null;					
					for(Ruta ruta : lstRutas){
						listitem = new Listitem(ruta.getOrigen()+" - "+ruta.getDestino());
						listitem.setValue(ruta);
						if(criterioRutas!=null){
							for(String criterio : criterioRutas){
								if(criterio.equals(ruta.getId().toString())){
									lbxCriteriosAsignados.appendChild(listitem);
									break;
								}else
									lbxCriteriosXAsignar.appendChild(listitem);
							}
						}else
							lbxCriteriosAsignados.appendChild(listitem);
					}
				}
			}else if(oClase.equals(Servicio.class)){
				List<Servicio> lstServicios = ServiceLocator.getServicioManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
				String[] criterioServicios = null;
				if(!txtCP_servicios.getText().equals("*"))
					criterioServicios = txtCP_servicios.getText().split(",");
					
				if(lstServicios.size()>0){
					Listitem listitem = null;					
					for(Servicio servicio : lstServicios){
						listitem = new Listitem(servicio.getDenominacion());
						listitem.setValue(servicio);
						if(criterioServicios!=null){
							for(String criterio : criterioServicios){
								if(criterio.equals(servicio.getId().toString())){
									lbxCriteriosAsignados.appendChild(listitem);
									break;
								}else
									lbxCriteriosXAsignar.appendChild(listitem);
							}
						}else
							lbxCriteriosAsignados.appendChild(listitem);
					}
				}				
			}else if(oClase.equals(CanalVenta.class)){
				List<CanalVenta> lstCanalVenta = ServiceLocator.getCanalVentaManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
				String[] criterioCanalVenta = null;
				if(!txtCP_canalesVenta.getText().equals("*"))
					criterioCanalVenta = txtCP_canalesVenta.getText().split(",");
					
				if(lstCanalVenta.size()>0){
					Listitem listitem = null;					
					for(CanalVenta canalVenta : lstCanalVenta){
						listitem = new Listitem(canalVenta.getDenominacion());
						listitem.setValue(canalVenta);
						if(criterioCanalVenta!=null){
							for(String criterio : criterioCanalVenta){
								if(criterio.equals(canalVenta.getId().toString())){
									lbxCriteriosAsignados.appendChild(listitem);
									break;
								}else
									lbxCriteriosXAsignar.appendChild(listitem);
							}
						}else
							lbxCriteriosAsignados.appendChild(listitem);
					}
				}
			}else if(oClase.equals(TipoAsiento.class)){
				List<TipoAsiento> lstTipoAsiento=ServiceLocator.getTipoAsientoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
				
				String[] criterioTipoAsiento = null;
				if(!txtCP_tiposAsiento.getText().equals("*"))
					criterioTipoAsiento = txtCP_tiposAsiento.getText().split(",");
					
				if(lstTipoAsiento.size()>0){
					Listitem listitem = null;					
					for(TipoAsiento tipoAsiento: lstTipoAsiento){
						listitem = new Listitem(tipoAsiento.getDenominacion());
						listitem.setValue(tipoAsiento);
						if(criterioTipoAsiento!=null){
							for(String criterio : criterioTipoAsiento){
								if(criterio.equals(tipoAsiento.getId().toString())){
									lbxCriteriosAsignados.appendChild(listitem);
									break;
								}else
									lbxCriteriosXAsignar.appendChild(listitem);
							}
						}else
							lbxCriteriosAsignados.appendChild(listitem);
					}
				}
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Mueve el registro seleccionado a la grilla de la derecha.
	 */
	private void moveToRight(){
		if(lbxContenedorLeft.getSelectedItem()!=null){
			Listitem item = lbxContenedorLeft.getSelectedItem();
			lbxContenedorRight.appendChild(item);
		}else
			DlgMessage.information(Messages.getString("WndPromociones.information.noSelectionItemLeft"), lbxContenedorLeft);
	}
	
	/**
	 * Mueve todos los registros de la grila Izquierda a la grilla de la derecha.
	 */
	private void moveAllToRight(){
		if(lbxContenedorLeft.getItems().size()>0){
			for(Listitem item : lbxContenedorLeft.getItems()){
				Listitem listitem = new Listitem(item.getLabel());
				listitem.setValue(item.getValue());
				lbxContenedorRight.appendChild(listitem);
			}
			lbxContenedorLeft.getItems().clear();
		}else{
			DlgMessage.information(Messages.getString("WndPromociones.information.noItemsMoveToRight"));
		}
	}
	
	/**
	 * Mueve el registro seleccionado a la grilla de la izquierda.
	 */
	private void moveToLeft(){
		if(lbxContenedorRight.getSelectedItem()!=null){
			Listitem item = lbxContenedorRight.getSelectedItem();
			lbxContenedorLeft.appendChild(item);
		}else
			DlgMessage.information(Messages.getString("WndPromociones.information.noSelectionItemRight"), lbxContenedorRight);
	}
	
	/**
	 * Mueve todos los registros de la grilla derecha a la grilla de la izquierda.
	 */
	private void moveAllToLeft(){
		if(lbxContenedorRight.getItems().size()>0){
			for(Listitem item : lbxContenedorRight.getItems()){
				Listitem listitem = new Listitem(item.getLabel());
				listitem.setValue(item.getValue());
				lbxContenedorLeft.appendChild(listitem);
			}
			lbxContenedorRight.getItems().clear();
		}else{
			DlgMessage.information(Messages.getString("WndPromociones.information.noItemsMoveToLeft"));
		}
	}
	
	/**
	 * Recorremos los listbox para asiganr los criterios a las respectivos textbox
	 * @param oClass	: Nombre de la clase de la cual se desea obtener los criterios.
	 */
	private void asignarCriterios(Class<?> oClass){
		if(oClass.equals(Ruta.class))
			txtCP_rutas.setText("");
		else if(oClass.equals(Servicio.class))
			txtCP_servicios.setText("");
		else if(oClass.equals(CanalVenta.class))
			txtCP_canalesVenta.setText("");		
		else if (oClass.equals(TipoAsiento.class))
			txtCP_tiposAsiento.setText("");

		if(lbxContenedorLeft.getItems().size()==0){
			if(oClass.equals(Ruta.class))
				txtCP_rutas.setText("*");
			else if(oClass.equals(Servicio.class))
				txtCP_servicios.setText("*");
			else if(oClass.equals(CanalVenta.class))
				txtCP_canalesVenta.setText("*");
			else if (oClass.equals(TipoAsiento.class))
				txtCP_tiposAsiento.setText("*");			
		}else{
			for(int i=0; i<lbxContenedorRight.getItems().size(); i++){
				Listitem item = lbxContenedorRight.getItemAtIndex(i);
				if(oClass.equals(Ruta.class)){
					Ruta ruta = (Ruta)item.getValue();
					txtCP_rutas.setText(txtCP_rutas.getText().trim().equals("")?(ruta.getId().toString()):(txtCP_rutas.getText().trim()+","+ruta.getId().toString()));
				}else if(oClass.equals(Servicio.class)){
					Servicio servicio = (Servicio)item.getValue();
					txtCP_servicios.setText(txtCP_servicios.getText().trim().equals("")?(servicio.getId().toString()):(txtCP_servicios.getText().trim()+","+servicio.getId().toString()));				
				}else if(oClass.equals(CanalVenta.class)){
					CanalVenta canalVenta = (CanalVenta)item.getValue();
					txtCP_canalesVenta.setText(txtCP_canalesVenta.getText().trim().equals("")?(canalVenta.getId().toString()):(txtCP_canalesVenta.getText().trim()+","+canalVenta.getId().toString()));					
				}else if(oClass.equals(TipoAsiento.class)){
					TipoAsiento tipoAsiento=(TipoAsiento)item.getValue();
					txtCP_tiposAsiento.setText(txtCP_tiposAsiento.getText().trim().equals("")?(tipoAsiento.getId().toString()):(txtCP_tiposAsiento.getText().trim()+","+tipoAsiento.getId().toString()));
				}
			}
		}
		
		wndCriterios.onClose();
	}
}
