/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 19/04/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.TipoAgencia;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.service.exceptions.FechaInicioNullException;
import com.cystesoft.vyrbus.service.exceptions.FechaMenorCalendarioException;
import com.cystesoft.vyrbus.service.exceptions.FechaVencimientoNullException;
import com.cystesoft.vyrbus.service.exceptions.IntervaloAsientosException;
import com.cystesoft.vyrbus.service.exceptions.PromocionExcepcion;
import com.cystesoft.vyrbus.service.exceptions.TarifarioException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndPromociones extends WndBase {
	private static final long serialVersionUID = 1L;
	private Textbox txtDenominacion;
	private Textbox txtDenominacionB;
	private Textbox txtRuta;
	private Textbox txtServicio;
	private Textbox txtPuntoVenta;
	private Textbox txtCanalVenta;
	private Textbox txtCliente;
	private Textbox txtClienteB;
	private Textbox txtAsientos;
	private Intbox intbxCantidadViajes;
//	private Doublebox dblbxImporte;
	private Doublebox dblbxDescuento;
	private Combobox cmbTipoDescuento;
	private Combobox cmbTemporada;
	private Combobox cmbFormaPago;
	private Combobox cmbTipoFormaPago;
	private Combobox cmbFiltro;
	private Combobox cmbServicioB;
	private Combobox cmbRutaB;
	private Combobox cmbTipoDescuentoB;
	private Textbox txtTarjeta;
	private Datebox dtbxFechaInicio;
	private Datebox dtbxFechaFin;
	private Checkbox chkPasajeroNuevo;
	private Checkbox chkIdaVuelta;
	private Checkbox chkPaxFre;
	private Image imgRuta;
	private Image imgServicio;
	private Image imgPuntoVenta;
	private Image imgCanalVenta;
	private Image imgCliente;
	private Image imgTarjeta;
	private Listbox lbxPromociones;
	private Listbox lbxContenedorLeft;
	private Listbox lbxContenedorRight;
	private Listbox lbxNuevaPromocion;
	private Listitem item = null;
	private Window wndCriterios;
	private Textbox txtPatron;
	private Listbox lbxClientes;
	private Label lblCliente;
	private Label lblFiltro;
	private Toolbarbutton tlbrbtnNuevo;
	private Toolbarbutton tlbrbtnBuscar;
	private Toolbarbutton tlbrbtnCancelar;
	private Button btnAgregarPromocion;
	private Button btnBuscar;
	private Label lblDescuento;
	private Label lblSeparador;
	private Spinner spnnrInicio;
	private Spinner spnnrFin;
	private Checkbox chkbxPorRango;
	private Grid grdCriterios;
	private Button btnGuardar;
	private Label lblPermitirDescuento;
	private Checkbox chbxPermitirDescuento;
	private Textbox txtHoraPartida;
	private Image imgAddhoraPartida;
	
	private Promocion promocion = null;
	private Promocion promocionEditar = null;
	
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();
		TreeMap<String, Object> parametros = new TreeMap<String, Object>();
		parametros.put("esPromocion", FormaPago.N_ESPROMOCION);
		UtilData.cargarDataCombo(cmbFormaPago, FormaPago.class, parametros, true);
		UtilData.cargarDataCombo(cmbRutaB, Ruta.class, true);
		UtilData.cargarDataCombo(cmbServicioB, Servicio.class, true);
		loadTipoDescuento(cmbTipoDescuentoB);
		loadTipoDescuento();
		loadTemporada();
		disabledControls(true);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		txtDenominacion = (Textbox)this.getFellow("txtDenominacion");
		txtDenominacionB = (Textbox)this.getFellow("txtDenominacionB");
		txtRuta = (Textbox)this.getFellow("txtRuta");
		txtServicio = (Textbox)this.getFellow("txtServicio");
		txtPuntoVenta = (Textbox)this.getFellow("txtPuntoVenta");
		txtCanalVenta = (Textbox)this.getFellow("txtCanalVenta");
		cmbTipoDescuento = (Combobox)this.getFellow("cmbTipoDescuento");
		cmbFormaPago = (Combobox)this.getFellow("cmbFormaPago");
		cmbTipoFormaPago = (Combobox)this.getFellow("cmbTipoFormaPago");
		cmbServicioB = (Combobox)this.getFellow("cmbServicioB");
		cmbRutaB = (Combobox)this.getFellow("cmbRutaB");
		cmbTipoDescuentoB = (Combobox)this.getFellow("cmbTipoDescuentoB");
		txtTarjeta = (Textbox)this.getFellow("txtTarjeta");
		cmbTemporada = (Combobox)this.getFellow("cmbTemporada");
		txtCliente = (Textbox)this.getFellow("txtCliente");
		txtClienteB = (Textbox)this.getFellow("txtClienteB");
		lblCliente = (Label)this.getFellow("lblCliente");
		txtAsientos = (Textbox)this.getFellow("txtAsientos");
		intbxCantidadViajes = (Intbox)this.getFellow("intbxCantidadViajes");
		chkPasajeroNuevo = (Checkbox)this.getFellow("chkPasajeroNuevo");
		chkIdaVuelta = (Checkbox)this.getFellow("chkIdaVuelta");
		chkPaxFre = (Checkbox)this.getFellow("chkPaxFre");
		dblbxDescuento = (Doublebox)this.getFellow("dblbxDescuento");
//		dblbxImporte = (Doublebox)this.getFellow("dblbxImporte");
		dtbxFechaInicio = (Datebox)this.getFellow("dtbxFechaInicio");
		dtbxFechaFin = (Datebox)this.getFellow("dtbxFechaFin");
		imgRuta = (Image)this.getFellow("imgRuta");
		imgServicio = (Image)this.getFellow("imgServicio");
		imgPuntoVenta = (Image)this.getFellow("imgPuntoVenta");
		imgCanalVenta = (Image)this.getFellow("imgCanalVenta");
		imgCliente = (Image)this.getFellow("imgCliente");
		imgTarjeta = (Image)this.getFellow("imgTarjeta");
		tlbrbtnNuevo = (Toolbarbutton)this.getFellow("tlbrbtnNuevo");
		tlbrbtnCancelar = (Toolbarbutton)this.getFellow("tlbrbtnCancelar");
		tlbrbtnBuscar = (Toolbarbutton)this.getFellow("tlbrbtnBuscar");
		lbxPromociones = (Listbox)this.getFellow("lbxPromociones");
		btnAgregarPromocion = (Button)this.getFellow("btnAgregarPromocion");
		lblDescuento = (Label)this.getFellow("lblDescuento");
		lblSeparador = (Label)this.getFellow("lblSeparador");
		spnnrInicio = (Spinner)this.getFellow("spnnrInicio");
		spnnrFin = (Spinner)this.getFellow("spnnrFin");
		chkbxPorRango = (Checkbox)this.getFellow("chkbxPorRango");
		grdCriterios = (Grid)this.getFellow("grdCriterios");
		btnBuscar = (Button)this.getFellow("btnBuscar");
		lblPermitirDescuento=(Label)this.getFellow("lblPermitirDescuento");
		chbxPermitirDescuento=(Checkbox)this.getFellow("chbxPermitirDescuento");
		txtHoraPartida=(Textbox)this.getFellow("txtHoraPartida");
		imgAddhoraPartida=(Image)this.getFellow("imgAddhoraPartida");
		
		cmbFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				loadTipoFormaPago();
			}
		});
		
		cmbTipoFormaPago.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				loadTarjetaCredito();
			}
		});
		
		tlbrbtnNuevo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				disabledControls(false);
				cleanControls();
				promocion = new Promocion();
				tlbrbtnBuscar.setDisabled(true);
				tlbrbtnCancelar.setDisabled(false);
				tlbrbtnNuevo.setDisabled(true);
				txtDenominacion.setFocus(true);
			}
		});
		
		tlbrbtnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				grdCriterios.setVisible(true);
//				buscar();
			}
		});
		
		tlbrbtnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				tlbrbtnNuevo.setDisabled(false);
				tlbrbtnBuscar.setDisabled(false);
				tlbrbtnCancelar.setDisabled(true);
				cleanControls();
				disabledControls(true);
			}
		});
		
		txtDenominacion.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e){
				txtRuta.setFocus(true);
			}
		});
		
		txtRuta.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					onCrearVentana("Asignación de Rutas a la Promoción", "DENOMINACION", Ruta.class);
			}
		});
		imgRuta.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				onCrearVentana("Asignación de Rutas a la Promoción", "DENOMINACION", Ruta.class);
			}
		});
		
		txtServicio.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					onCrearVentana("Asignación de Servicios a la Promoción", "DENOMINACION", Servicio.class);
			}
		});
		imgServicio.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				onCrearVentana("Asignación de Servicios a la Promoción", "DENOMINACION", Servicio.class);
			}
		});
		
		txtPuntoVenta.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					onCrearVentana("Asignación de Puntos de Venta a la Promoción", "DENOMINACION", Agencia.class);
			}
		});
		imgPuntoVenta.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				onCrearVentana("Asignación de Puntos de Venta a la Promoción", "DENOMINACION", Agencia.class);
			}
		});
		
		txtCanalVenta.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					onCrearVentana("Asignación de Canales de Venta a la Promoción", "DENOMINACION", CanalVenta.class);
			}
		});
		imgCanalVenta.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				onCrearVentana("Asignación de Canales de Venta a la Promoción", "DENOMINACION", CanalVenta.class);
			}
		});
		
		txtTarjeta.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					onCrearVentana("Asignación de Tarjetas de Crédito a la Promoción", "DENOMINACION", TarjetaCredito.class);
			}
		});
		imgTarjeta.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				onCrearVentana("Asignación de Tarjetas de Crédito a la Promoción", "DENOMINACION", TarjetaCredito.class);
			}
		});
		
		txtCliente.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			public void onEvent(Event e){
				int key = ((KeyEvent)e).getKeyCode();
				if(key==116)
					loadCliente("Busqueda de Clientes");
			}
		});
		imgCliente.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				loadCliente("Busqueda de Clientes");
			}
		});
		
		dtbxFechaInicio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				String fecha = Util.DatetoString(dtbxFechaInicio.getValue(), "yyyyMMdd");
				dtbxFechaFin.setConstraint("after "+fecha);
				dtbxFechaFin.setDisabled(false);
			}
		});
		
		btnAgregarPromocion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				agregarPromocion();
			}
		});
		
		cmbTipoDescuento.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event e){
				chbxPermitirDescuento.setVisible(false);
				chbxPermitirDescuento.setChecked(false);
				lblPermitirDescuento.setVisible(false);
				if(cmbTipoDescuento.getText().equals(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_FIJO)){
					lblDescuento.setValue("DESCUENTO* :");					
//					dblbxDescuento.setDisabled(false);
//					dblbxImporte.setDisabled(false);
				}else if(cmbTipoDescuento.getText().equals(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE)){
					lblDescuento.setValue("% DESCUENTO* :");
//					dblbxDescuento.setDisabled(false);
//					dblbxImporte.setDisabled(true);
				}else if(cmbTipoDescuento.getText().equals(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_TARIFA)){
					lblDescuento.setValue("TARIFA SERVICIO* :");
					chbxPermitirDescuento.setVisible(true);
					lblPermitirDescuento.setVisible(true);
				}
				dblbxDescuento.setFocus(true);
			}
		});
		
		chkbxPorRango.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event e){
				if(chkbxPorRango.isChecked())
					habilitarControlAsientos(true);
				else				
					habilitarControlAsientos(false);
			}
		});
		
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				buscar();
			}
		});
	}
	
	private void habilitarControlAsientos(boolean arg){
		spnnrInicio.setVisible(arg);
		lblSeparador.setVisible(arg);
		spnnrFin.setVisible(arg);
		txtAsientos.setVisible(!arg);
		spnnrInicio.setValue(null);
		spnnrFin.setValue(null);
		txtAsientos.setText("");
	}
	
	private void disabledControls(boolean arg){
		txtDenominacion.setDisabled(arg);
		txtServicio.setDisabled(arg);
		txtPuntoVenta.setDisabled(arg);
		txtCanalVenta.setDisabled(arg);
		cmbTipoDescuento.setDisabled(arg);
		cmbFormaPago.setDisabled(arg);
		if(!cmbTipoFormaPago.isDisabled())
			cmbTipoFormaPago.setDisabled(true);
		if(!txtTarjeta.isDisabled())
			txtTarjeta.setDisabled(true);
		cmbTemporada.setDisabled(arg);
		txtCliente.setDisabled(arg);
		txtAsientos.setDisabled(arg);
		intbxCantidadViajes.setDisabled(arg);
		chkPasajeroNuevo.setDisabled(arg);
		chkPaxFre.setDisabled(arg);
		chkIdaVuelta.setDisabled(arg);
//		if(!dblbxDescuento.isDisabled())
//			dblbxDescuento.setDisabled(true);
		dblbxDescuento.setDisabled(arg);
//		if(!dblbxImporte.isDisabled())
//			dblbxImporte.setDisabled(true);
//		dblbxImporte.setDisabled(arg);
		dtbxFechaInicio.setDisabled(arg);
//		dtbxFechaFin.setDisabled(arg);
		imgRuta.setVisible(!arg);
		imgServicio.setVisible(!arg);
		imgPuntoVenta.setVisible(!arg);
		imgCanalVenta.setVisible(!arg);
		imgCliente.setVisible(!arg);
		btnAgregarPromocion.setDisabled(arg);
		imgAddhoraPartida.setVisible(!arg);
	}
	
	private void cleanControls(){
		txtDenominacion.setText("");
		txtRuta.setText("");
		txtServicio.setText("");
		txtPuntoVenta.setText("");
		txtCanalVenta.setText("");
		txtCliente.setText("");
		intbxCantidadViajes.setValue(null);
		txtAsientos.setText("");
		chkPasajeroNuevo.setChecked(false);
		chkIdaVuelta.setChecked(false);
		dblbxDescuento.setValue(null);
		cmbTipoDescuento.setSelectedIndex(0);
//		dblbxImporte.setValue(null);
		cmbFormaPago.setSelectedIndex(0);
		cmbTipoFormaPago.setText("");
		cmbTipoFormaPago.getItems().clear();
		txtTarjeta.setText("");
		cmbTemporada.setSelectedIndex(0);
		dtbxFechaInicio.setValue(null);
		dtbxFechaFin.setValue(null);
		lblCliente.setValue("");
		if(!txtTarjeta.isDisabled()){
			txtTarjeta.setDisabled(true);
			imgTarjeta.setVisible(false);
		}
		if(!dtbxFechaFin.isDisabled())
			dtbxFechaFin.setDisabled(true);
		promocion=null;
		txtHoraPartida.setText("");
	}
	
	/**
	 * Carga el combo con los tipos de forma de pago.
	 */
	private void loadTipoFormaPago(){
		try{
			if(cmbFormaPago.getSelectedItem().getValue() instanceof FormaPago && ((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_FORPAG_CONTADO){
				cmbTipoFormaPago.getItems().clear();
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("esPromocion", TipoFormaPago.N_ESPROMOCION);
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<TipoFormaPago> lstTipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
				cmbTipoFormaPago.appendChild(comboitem);
				cmbTipoFormaPago.setSelectedItem(comboitem);
				for(TipoFormaPago tipoFormaPago : lstTipoFormaPago){
					comboitem = new Comboitem(tipoFormaPago.getDenominacion());
					comboitem.setValue(tipoFormaPago);
					cmbTipoFormaPago.appendChild(comboitem);					
				}
				cmbTipoFormaPago.setDisabled(false);
//				imgTipoFormaPago.setVisible(true);
			}else{
				cmbTipoFormaPago.getItems().clear();
				cmbTipoFormaPago.setDisabled(true);
				cmbTipoFormaPago.setText("");
//				imgTipoFormaPago.setVisible(false);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Habilita los controles para seleccionar la Tarjeta de Credito.
	 */
	private void loadTarjetaCredito(){
		try{
			txtTarjeta.setText("");
			if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago && ((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_TARJETA){
				txtTarjeta.setDisabled(false);
				imgTarjeta.setVisible(true);
				txtTarjeta.setFocus(true);
			}else{
				txtTarjeta.setDisabled(true);
				txtTarjeta.setText("");
				imgTarjeta.setVisible(false);
				cmbTemporada.setFocus(true);
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Carga el combo con los tipos de descuento.
	 */
	private void loadTipoDescuento(){
		try{
			cmbTipoDescuento.getItems().clear();
			Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
			cmbTipoDescuento.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_FIJO);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO);
			cmbTipoDescuento.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE);
			cmbTipoDescuento.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_TARIFA);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_TARIFA);
			cmbTipoDescuento.appendChild(comboitem);
			cmbTipoDescuento.setSelectedIndex(0);
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Carga el combo con los tipos de descuento.
	 */
	private void loadTipoDescuento(Combobox combobox){
		try{
			combobox.getItems().clear();
			Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
			combobox.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_FIJO);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO);
			combobox.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE);
			combobox.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_TARIFA);
			comboitem.setValue(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_TARIFA);
			combobox.appendChild(comboitem);
			combobox.setSelectedIndex(0);
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Carga las temporadas de las promociones al combobox.
	 */
	private void loadTemporada(){
		try{
			cmbTemporada.getItems().clear();
			Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_TODOS);
			cmbTemporada.appendChild(comboitem);
			comboitem = new Comboitem(Promocion.PROMOCION_TEMPORADA_LABEL_NOALTA);
			comboitem.setValue(Promocion.PROMOCION_TEMPORADA_VALUE_NOALTA);
			cmbTemporada.appendChild(comboitem);
			cmbTemporada.setSelectedIndex(0);
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
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
		
		if(oClase.equals(Agencia.class)){
			Hbox hlayoutTipo = new Hbox();
			hlayoutTipo.setAlign("center");
			hlayoutTipo.setHeight("20px");
			lblFiltro = new Label("TIPO AGENCIA :");
			hlayoutTipo.appendChild(lblFiltro);
			cmbFiltro = new Combobox();
			cmbFiltro.addEventListener(Events.ON_SELECTION, new EventListener<Event>() {
				public void onEvent(Event e){
					if(cmbFiltro.getSelectedItem().getValue()!=null)
						buscarAgencias();
				}
			});
			hlayoutTipo.appendChild(cmbFiltro);
			wndCriterios.appendChild(hlayoutTipo);
			wndCriterios.setHeight("430px");
			Space space = new Space();
			space.setOrient("vertical");
			space.setHeight("10px");
			wndCriterios.appendChild(space);
		}
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
		Listheader listheader = new Listheader(header, "", "200px");
		listheader.setStyle("color: #ffffff;");
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
		listheader = new Listheader(header, "", "200px");
		listheader.setStyle("color: #ffffff;");
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
//		btnAceptar.setHeight("28px");
		btnAceptar.setClass("btnCommandM");
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
				if(!txtRuta.getText().equals("*"))
					criterioRutas = txtRuta.getText().split(",");
					
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
				if(!txtServicio.getText().equals("*"))
					criterioServicios = txtServicio.getText().split(",");
					
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
			}else if(oClase.equals(Agencia.class)){
				UtilData.cargarDataCombo(cmbFiltro, TipoAgencia.class, false);
				Util.seleccionarValorItemCombo(TipoAgencia.class, cmbFiltro, Constantes.ID_TIPAGE_TEPSA);
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
				criteriosBusqueda.put("tipoAgencia.id", ((TipoAgencia)cmbFiltro.getSelectedItem().getValue()).getId());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("denominacion");
				List<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				String[] criterioAgencias = null;
				if(!txtPuntoVenta.getText().equals("*"))
					criterioAgencias = txtPuntoVenta.getText().split(",");
				
				if(lstAgencias.size()>0){
					Listitem listitem = null;					
					for(Agencia agencia : lstAgencias){
						listitem = new Listitem(agencia.getDenominacion());
						listitem.setValue(agencia);
						if(criterioAgencias!=null){
							for(String criterio : criterioAgencias){
								if(criterio.equals(agencia.getId().toString())){
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
				if(!txtCanalVenta.getText().equals("*"))
					criterioCanalVenta = txtCanalVenta.getText().split(",");
					
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
			}else if(oClase.equals(TarjetaCredito.class)){
				List<TarjetaCredito> lstTarjetaCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion");
				String[] criterioTarjetaCredito = null;
				if(!txtTarjeta.getText().equals("*"))
					criterioTarjetaCredito = txtTarjeta.getText().split(",");
					
				if(lstTarjetaCredito.size()>0){
					Listitem listitem = null;					
					for(TarjetaCredito tarjetaCredito : lstTarjetaCredito){
						listitem = new Listitem(tarjetaCredito.getDenominacion());
						listitem.setValue(tarjetaCredito);
						if(criterioTarjetaCredito!=null){
							for(String criterio : criterioTarjetaCredito){
								if(criterio.equals(tarjetaCredito.getId().toString())){
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
	
	private void buscarAgencias(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("tipoAgencia.id", ((TipoAgencia)cmbFiltro.getSelectedItem().getValue()).getId());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("denominacion");
			List<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			
			lbxContenedorLeft.getItems().clear();
			if(lstAgencias.size()>0){
				Listitem listitem = null;					
				for(Agencia agencia : lstAgencias){
					listitem = new Listitem(agencia.getDenominacion());
					listitem.setValue(agencia);
					lbxContenedorLeft.appendChild(listitem);
				}
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ ex.getMessage());
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
		if(oClass.equals(Ruta.class)){
			txtRuta.setText("");
			promocion.setListRutas(null);
		}else if(oClass.equals(Servicio.class)){
			txtServicio.setText("");
			promocion.setListServicio(null);
		}else if(oClass.equals(Agencia.class)){
			txtPuntoVenta.setText("");
			promocion.setListPuntoVenta(null);
		}else if(oClass.equals(CanalVenta.class)){
			txtCanalVenta.setText("");
			promocion.setListCanalVenta(null);
		}else if(oClass.equals(TarjetaCredito.class)){
			txtTarjeta.setText("");
			promocion.setListTarjetaCredito(null);
		}
		
		if(lbxContenedorLeft.getItems().size()==0){
			if(oClass.equals(Ruta.class))
				txtRuta.setText("*");
			else if(oClass.equals(Servicio.class))
				txtServicio.setText("*");
			else if(oClass.equals(Agencia.class))
				txtPuntoVenta.setText("*");
			else if(oClass.equals(CanalVenta.class))
				txtCanalVenta.setText("*");
			else if(oClass.equals(TarjetaCredito.class))
				txtTarjeta.setText("*");
		}else{
			for(int i=0; i<lbxContenedorRight.getItems().size(); i++){
				Listitem item = lbxContenedorRight.getItemAtIndex(i);
				if(oClass.equals(Ruta.class)){
					Ruta ruta = (Ruta)item.getValue();
					txtRuta.setText(txtRuta.getText().trim().equals("")?(ruta.getId().toString()):(txtRuta.getText().trim()+","+ruta.getId().toString()));
					/*	Para asignar la Lista de Rutas al Objeto Promocion	*/
					if(promocion.getListRutas()==null){
						List<Ruta> lstRutas = new ArrayList<Ruta>();
						lstRutas.add(ruta);
						promocion.setListRutas(lstRutas);
					}else
						promocion.getListRutas().add(ruta);
				}else if(oClass.equals(Servicio.class)){
					Servicio servicio = (Servicio)item.getValue();
					txtServicio.setText(txtServicio.getText().trim().equals("")?(servicio.getId().toString()):(txtServicio.getText().trim()+","+servicio.getId().toString()));
					/*	Para asignar la Lista de Servicios al objeto Promocion	*/
					if(promocion.getListServicio()==null){
						List<Servicio> lstServicios = new ArrayList<Servicio>();
						lstServicios.add(servicio);
						promocion.setListServicio(lstServicios);
					}else
						promocion.getListServicio().add(servicio);
				}else if(oClass.equals(Agencia.class)){
					Agencia agencia = (Agencia)item.getValue();
					txtPuntoVenta.setText(txtPuntoVenta.getText().trim().equals("")?(agencia.getId().toString()):(txtPuntoVenta.getText().trim()+","+agencia.getId().toString()));
					/*	Para asignar la Lista de Puntos de Venta al objeto Promocion	*/
					if(promocion.getListPuntoVenta()==null){
						List<Agencia> lstPuntoVentas = new ArrayList<Agencia>();
						lstPuntoVentas.add(agencia);
						promocion.setListPuntoVenta(lstPuntoVentas);
					}else
						promocion.getListPuntoVenta().add(agencia);
				}else if(oClass.equals(CanalVenta.class)){
					CanalVenta canalVenta = (CanalVenta)item.getValue();
					txtCanalVenta.setText(txtCanalVenta.getText().trim().equals("")?(canalVenta.getId().toString()):(txtCanalVenta.getText().trim()+","+canalVenta.getId().toString()));
					/*	Para asignar la Lista de Canales de Venta al objeto Promocion	*/
					if(promocion.getListCanalVenta()==null){
						List<CanalVenta> lstCanalVenta = new ArrayList<CanalVenta>();
						lstCanalVenta.add(canalVenta);
						promocion.setListCanalVenta(lstCanalVenta);
					}else
						promocion.getListCanalVenta().add(canalVenta);
				}else if(oClass.equals(TarjetaCredito.class)){
					TarjetaCredito tarjeta = (TarjetaCredito)item.getValue();
					txtTarjeta.setText(txtTarjeta.getText().trim().equals("")?(tarjeta.getId().toString()):(txtTarjeta.getText().trim()+","+tarjeta.getId().toString()));
					/*	Para asignar la Lista de Tarjetas de Credito al objeto Promocion	*/
					if(promocion.getListTarjetaCredito()==null){
						List<TarjetaCredito> lstTarjetaCredito = new ArrayList<TarjetaCredito>();
						lstTarjetaCredito.add(tarjeta);
						promocion.setListTarjetaCredito(lstTarjetaCredito);
					}else
						promocion.getListTarjetaCredito().add(tarjeta);
				}
			}
		}
		wndCriterios.onClose();
	}
	
	/**
	 * Busca los clientes de acuerdo al criterio de busqueda.
	 */
	private void buscarClientes(String patron, Listbox lbxParent){
		try{
			lbxClientes.getItems().clear();
			List<Cliente> lstClientes = null;
			if(!patron.isEmpty()){
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("razonSocial");
				if(Character.isDigit(patron.charAt(0))){
					TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
					criteriosBusqueda.put("numeroDocumento", patron+"%");
					lstClientes = ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
				}else{
					String[] razonSocial = patron.split(" ");
					lstClientes = ServiceLocator.getClienteManager().buscarPorRazonSocial(razonSocial);
				}
				
				if(lstClientes.size()>0){
					for(Cliente cliente : lstClientes){
						Listitem listitem = new Listitem();
						listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
							public void onEvent(Event e){
								asignarCliente();
							}
						});
						Listcell listcell = new Listcell(cliente.getNumeroDocumento());
						listitem.appendChild(listcell);
						
						listcell = new Listcell(cliente.getRazonSocial());
						listitem.appendChild(listcell);
						lbxParent.appendChild(listitem);
						listitem.setValue(cliente);
					}
				}
			}else{
				DlgMessage.information(Messages.getString("WndPromociones.information.noPatronBusqueda"), txtPatron);				
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getSimpleName()+" "+ ex.getMessage());
		}
	}
	/**
	 * Permite crear la ventana para la busqueda de los clientes.
	 * @param title	: Titulo de la venta a crear.
	 */
	@SuppressWarnings("deprecation")
	public void onCreateVentanaHoraPartida(){
		wndCriterios = new Window("HORAS DE PARTIDA DEL SERVICIO", "normal", true);
		wndCriterios.setWidth("372px");
		wndCriterios.setHeight("250px");
		final Listbox lbxHoraPartida  = new Listbox();
		
		Hlayout hlayout = new Hlayout();
		hlayout.setHeight("24px");
		hlayout.setValign("middle");
		hlayout.setStyle("padding:4px");
		Label label = new Label("HORA PARTIDA :");
		hlayout.appendChild(label);
		final Timebox dtbxHoraPartida = new Timebox();
		dtbxHoraPartida.setWidth("100px");
	 	dtbxHoraPartida.setFormat("HH:mm");
	 	dtbxHoraPartida.setInstant(false);
		dtbxHoraPartida.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e){
				if(!(dtbxHoraPartida.getText().trim().isEmpty())){
					Listitem item=new Listitem();
					Listcell cell=new Listcell(dtbxHoraPartida.getText());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					cell=new Listcell();
					Toolbarbutton tbtnEliminar=new Toolbarbutton("Eliminar");
					tbtnEliminar.setAttribute("item", item);
					tbtnEliminar.setStyle("text-transform:none;");
					cell.appendChild(tbtnEliminar);
					item.appendChild(cell);
					
					item.setValue(dtbxHoraPartida.getText());
					lbxHoraPartida.appendChild(item);
					dtbxHoraPartida.setText("00:00");
					
					/*Evento que elimina el item agredado*/
					tbtnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						public void onEvent(Event et){
							Listitem itemRemove=(Listitem)et.getTarget().getAttribute("item");
							lbxHoraPartida.getItems().remove(itemRemove.getIndex());
						}
					});
				}
			}
		});
		dtbxHoraPartida.setTooltiptext("Presione la tecla Enter para agregar a la lista.");
		hlayout.appendChild(dtbxHoraPartida);
		wndCriterios.appendChild(hlayout);
		
		lbxHoraPartida.setHeight("140px");
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader("HORA PARTIDA", null, "90px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		listheader = new Listheader("", null,"");
		listhead.appendChild(listheader);
		lbxHoraPartida.appendChild(listhead);		
		wndCriterios.appendChild(lbxHoraPartida);
		
		
		Space space = new Space();
		space.setOrient("vertical");
		space.setHeight("5px");
		wndCriterios.appendChild(space);
		
		Div div = new Div();
		div.setAlign("center");
		Button btnAceptar = new Button("Aceptar", "resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				String horaPartida="";
				for(Listitem itemf:lbxHoraPartida.getItems()){
					if(horaPartida.isEmpty())
						horaPartida=itemf.getValue().toString();
					else
						horaPartida+=","+itemf.getValue().toString();
				}
				txtHoraPartida.setText(horaPartida);
				wndCriterios.onClose();
			}
		});
//		btnAceptar.setHeight("28px");
		btnAceptar.setClass("btnCommandM");
		div.appendChild(btnAceptar);
		Label lblSpace = new Label(" ");
		div.appendChild(lblSpace);
		
		Button btnCancelar = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				wndCriterios.onClose();
			}
		});
//		btnCancelar.setHeight("28px");
		btnCancelar.setClass("btnCommandM");
		div.appendChild(btnCancelar);
		wndCriterios.appendChild(div);

		
		
		/*Carga las horas de partida (si es que se modifica)*/
		if(!(txtHoraPartida.getText().trim().isEmpty())){
			String[] horasPartida=txtHoraPartida.getText().split(",");
			for(String horap: horasPartida){
				Listitem newItem =new Listitem();
				Listcell cell=new Listcell(horap);
				cell.setStyle("font-size:11px !important");
				newItem.appendChild(cell);
				cell=new Listcell();
				Toolbarbutton tbtnEliminar=new Toolbarbutton("Eliminar");
				tbtnEliminar.setAttribute("item", newItem);
				tbtnEliminar.setStyle("text-transform:none;");
				cell.appendChild(tbtnEliminar);
				newItem.appendChild(cell);
				
				newItem.setValue(horap);
				lbxHoraPartida.appendChild(newItem);
				
				/*Evento que elimina el item agredado*/
				tbtnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event et){
						Listitem itemRemove=(Listitem)et.getTarget().getAttribute("item");
						lbxHoraPartida.getItems().remove(itemRemove.getIndex());
					}
				});
			}
			
		}
		
		
		
		this.appendChild(wndCriterios);
		wndCriterios.doModal();
	}
	
	/**
	 * Permite crear la ventana para la busqueda de los clientes.
	 * @param title	: Titulo de la venta a crear.
	 */
	@SuppressWarnings("deprecation")
	private void loadCliente(String title){
		wndCriterios = new Window(title, "normal", true);
		wndCriterios.setWidth("372px");
		wndCriterios.setHeight("250px");
		Hlayout hlayout = new Hlayout();
		hlayout.setHeight("24px");
		hlayout.setValign("middle");
		hlayout.setStyle("padding:4px");
		Label label = new Label("BUSCAR :");
		hlayout.appendChild(label);
		txtPatron = new Textbox();
		txtPatron.addEventListener(Events.ON_OK, new EventListener<Event>() {
			public void onEvent(Event e){
				buscarClientes(txtPatron.getText().trim(), lbxClientes);
			}
		});
		txtPatron.setWidth("200px");
		txtPatron.setTooltiptext("Presione la tecla Enter para realizar la busqueda.");
		hlayout.appendChild(txtPatron);
		wndCriterios.appendChild(hlayout);
		
		lbxClientes = new Listbox();
		lbxClientes.setHeight("140px");
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader("RUC", null, "80px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		listheader = new Listheader("RAZON SOCIAL", null, "280px");
		listheader.setStyle("color: #ffffff;");
		listhead.appendChild(listheader);
		lbxClientes.appendChild(listhead);		
		wndCriterios.appendChild(lbxClientes);
		
		Space space = new Space();
		space.setOrient("vertical");
		space.setHeight("5px");
		wndCriterios.appendChild(space);
		
		Div div = new Div();
		div.setAlign("center");
		Button btnAceptar = new Button("Aceptar", "resources/mp_aceptarEnabled.png");
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				asignarCliente();
			}
		});
//		btnAceptar.setHeight("28px");
		btnAceptar.setClass("btnCommandM");
		div.appendChild(btnAceptar);
		
		Label lblSpace = new Label(" ");
		div.appendChild(lblSpace);
		
		Button btnCancelar = new Button("Cancelar", "resources/mp_cancelarEnabled.png");
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e){
				txtCliente.setText("");
				lblCliente.setValue("");
				wndCriterios.onClose();
			}
		});
//		btnCancelar.setHeight("28px");
		btnCancelar.setClass("btnCommandM");
		div.appendChild(btnCancelar);
		wndCriterios.appendChild(div);
		
		this.appendChild(wndCriterios);
		wndCriterios.doModal();
	}
	
	/**
	 * Permite la asignacion del cliente a la caja de texto.
	 */
	private void asignarCliente(){
		if(lbxClientes.getSelectedIndex()>=0){
			Cliente cliente = (Cliente)lbxClientes.getSelectedItem().getValue();
			txtCliente.setText(cliente.getId().toString());
			lblCliente.setValue(cliente.getRazonSocial());
			wndCriterios.onClose();
		}else
			DlgMessage.information(Messages.getString("WndPromociones.information.noClienteSeleccionado"));
	}
	
	/**
	 * Agrega la promocion a la grilla.
	 */
	private void agregarPromocion(){
		try{
			if(txtDenominacion.getText().equals(""))
				throw new PromocionExcepcion(PromocionExcepcion.DENOMINACION);
			else if(cmbTipoDescuento.getSelectedItem().getValue()==null)
				throw new PromocionExcepcion(PromocionExcepcion.TIPO_DESCUENTO);
//			else if(cmbTipoDescuento.getText().equals(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE) && dblbxDescuento.getValue()==null)
//				throw new PromocionExcepcion(PromocionExcepcion.NO_DESCUENTO);
//			else if(cmbTipoDescuento.getText().equals(Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_FIJO) && dblbxDescuento.getValue()==null && dblbxImporte.getValue()==null)
//				throw new PromocionExcepcion(PromocionExcepcion.NO_DESCUENTO_IMPORTE);
//			else if(dblbxDescuento.getValue()!=null && dblbxImporte.getValue()!=null && dblbxDescuento.getValue()>0.0 && dblbxImporte.getValue()>0.0)
//				throw new PromocionExcepcion(PromocionExcepcion.DESCUENTO_IMPORTE);
			else if(dblbxDescuento.getValue()==null)
				throw new PromocionExcepcion(PromocionExcepcion.NO_DESCUENTO);
			else if(dtbxFechaInicio.getValue()==null)
				throw new FechaInicioNullException();
			else if(dtbxFechaFin.getValue()==null)
				throw new FechaVencimientoNullException();
			
			
			/*Validando el formato del la hora de partida*/
			if(!(txtHoraPartida.getText().trim().isEmpty())){
				
			}
			
			
			
			item = new Listitem();
			Listcell cell = null;
			
			promocion.setDenominacion(txtDenominacion.getText().trim().toUpperCase());
			cell = new Listcell(txtDenominacion.getText().trim());
			cell.setStyle("background:#99D9EA");
			item.appendChild(cell);
	
			promocion.setRutas(txtRuta.getText().trim().isEmpty()?"*":txtRuta.getText().trim());
			cell = new Listcell(txtRuta.getText().trim().isEmpty()?"*":promocion.getListRutas().toString());
			item.appendChild(cell);
			
			promocion.setServicios(txtServicio.getText().trim().isEmpty()?"*":txtServicio.getText().trim());
			cell = new Listcell(txtServicio.getText().trim().isEmpty()?"*":promocion.getListServicio().toString());
			item.appendChild(cell);
			
			promocion.setPuntoVenta(txtPuntoVenta.getText().trim().isEmpty()?"*":txtPuntoVenta.getText().trim());
			cell = new Listcell(txtPuntoVenta.getText().trim().isEmpty()?"*":promocion.getListPuntoVenta().toString());
			item.appendChild(cell);
			
			promocion.setCanalVenta(txtCanalVenta.getText().trim().isEmpty()?"*":txtCanalVenta.getText().trim());
			cell = new Listcell(txtCanalVenta.getText().trim().isEmpty()?"*":promocion.getListCanalVenta().toString());
	//		cell.setTooltiptext("celda 1");
			item.appendChild(cell);
			
			promocion.setCliente(txtCliente.getText().trim().isEmpty()?"*":txtCliente.getText().trim());
			cell = new Listcell(txtCliente.getText().trim().isEmpty()?"*":lblCliente.getValue());
			item.appendChild(cell);
			
			if(chkbxPorRango.isChecked()){
				if(spnnrInicio.getValue()==null || spnnrInicio.getValue()<=0)
					throw new IntervaloAsientosException(IntervaloAsientosException.INTERVALO_INICIAL);
				else if(spnnrFin.getValue()==null || spnnrFin.getValue()>44)
					throw new IntervaloAsientosException(IntervaloAsientosException.INTERVALO_FINAL);
				else if(spnnrInicio.getValue()>=spnnrFin.getValue())
					throw new IntervaloAsientosException(IntervaloAsientosException.INTERVALO_INICIAL_MAYOR);
				for(int i=spnnrInicio.getValue(); i<=spnnrFin.getValue(); i++){
					txtAsientos.setText(txtAsientos.getText()+String.valueOf(i));
					if(i!=spnnrFin.getValue().intValue())
						txtAsientos.setText(txtAsientos.getText()+",");
				}
			}
			promocion.setAsientos(txtAsientos.getText().trim().isEmpty()?"*":txtAsientos.getText().trim());
			cell = new Listcell(txtAsientos.getText().trim().isEmpty()?"*":txtAsientos.getText().trim());
			item.appendChild(cell);
			
			promocion.setCantidadViajesPasajero(intbxCantidadViajes.getText().trim().isEmpty()?"*":intbxCantidadViajes.getText().trim());
			cell = new Listcell(intbxCantidadViajes.getText().trim().isEmpty()?"*":intbxCantidadViajes.getText().trim());
			item.appendChild(cell);
			
			if(cmbTipoDescuento.getSelectedItem().getValue().toString().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO) 
					|| cmbTipoDescuento.getSelectedItem().getValue().toString().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE)){
				promocion.setPorImporte(null);
				promocion.setValorDescuento(dblbxDescuento.getValue());
				promocion.setEsTarifa(Constantes.FALSE_VALUE);
				cell = new Listcell("");
				item.appendChild(cell);
				cell = new Listcell(dblbxDescuento.getText().trim());
				item.appendChild(cell);
			}else if(cmbTipoDescuento.getSelectedItem().getValue().toString().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_TARIFA)){
				promocion.setPorImporte(dblbxDescuento.getValue());
				promocion.setValorDescuento(null);
				promocion.setEsTarifa(Constantes.TRUE_VALUE);
				cell = new Listcell(dblbxDescuento.getText().trim());
				item.appendChild(cell);
				cell = new Listcell("");
				item.appendChild(cell);
			}
				
//			promocion.setPorImporte(dblbxImporte.getText().trim().isEmpty()?null:dblbxImporte.getValue());
//			cell = new Listcell(dblbxImporte.getText().trim().isEmpty()?"":dblbxImporte.getText().trim());
//			item.appendChild(cell);
//			
//			promocion.setValorDescuento(dblbxDescuento.getText().trim().isEmpty()?null:dblbxDescuento.getValue());
//			cell = new Listcell(dblbxDescuento.getText().trim().isEmpty()?"":dblbxDescuento.getText().trim());
//			item.appendChild(cell);
			
			promocion.setTipoDescuento(String.valueOf(cmbTipoDescuento.getSelectedItem().getValue()));
			cell = new Listcell(cmbTipoDescuento.getText());
			item.appendChild(cell);
			
			promocion.setEnTemporada(cmbTemporada.getSelectedItem().getValue()==null?"*":String.valueOf(cmbTemporada.getSelectedItem().getValue()));
			cell = new Listcell(cmbTemporada.getSelectedItem().getValue()==null?"*":cmbTemporada.getText());
			item.appendChild(cell);
			
			promocion.setFormaPago(cmbFormaPago.getSelectedItem().getValue()==null?"*":((FormaPago)cmbFormaPago.getSelectedItem().getValue()).getId().toString());
			cell = new Listcell(cmbFormaPago.getSelectedItem().getValue()==null?"*":cmbFormaPago.getText());
			item.appendChild(cell);
			
			promocion.setTipoFormaPago(cmbTipoFormaPago.getText().equals("")?"*":(cmbTipoFormaPago.getSelectedItem().getValue()==null?"*":((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().toString()) );
			cell = new Listcell(cmbTipoFormaPago.getText().equals("")?"*":cmbTipoFormaPago.getText());
			item.appendChild(cell);
			
			promocion.setTarjetaCredito(txtTarjeta.getText().trim().isEmpty()?"*":txtTarjeta.getText().trim());
			cell = new Listcell(txtTarjeta.getText().trim().isEmpty()?"*":promocion.getListTarjetaCredito().toString().substring(1, (promocion.getListTarjetaCredito().toString().length()-1)));
			item.appendChild(cell);
			
			promocion.setFechaInicio((dtbxFechaInicio.getValue()==null?null:dtbxFechaInicio.getValue()));
			cell = new Listcell(dtbxFechaInicio.getValue()==null?"*":Util.DatetoString(dtbxFechaInicio.getValue(), Constantes.DATE_FORMAT));
			item.appendChild(cell);
			
			promocion.setFechaFin((dtbxFechaFin.getValue()==null?null:dtbxFechaFin.getValue()));
			cell = new Listcell(dtbxFechaFin.getValue()==null?"*":Util.DatetoString(dtbxFechaFin.getValue(), Constantes.DATE_FORMAT));
			item.appendChild(cell);
			
			promocion.setPasajeroNuevo(chkPasajeroNuevo.isChecked()?"S":"*");
			cell = new Listcell(chkPasajeroNuevo.isChecked()?"S":"*");
			item.appendChild(cell);
			
			promocion.setIdaVuelta(chkIdaVuelta.isChecked()?"S":"*");
			cell = new Listcell(chkIdaVuelta.isChecked()?"S":"*");
			item.appendChild(cell);
			
			promocion.setPasajeroFrecuente(chkPaxFre.isChecked()?"S":"*");
			cell = new Listcell(chkPaxFre.isChecked()?"S":"*");
			item.appendChild(cell);
			
			promocion.setHorasPartida(txtHoraPartida.getText().trim().isEmpty()?"*":txtHoraPartida.getText().trim().replaceAll(":", "."));
			cell = new Listcell(promocion.getHorasPartida());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell = new Listcell();
			Toolbarbutton toolbarbutton = new Toolbarbutton("", "resources/mp_editarEnabled.png");
			cell.appendChild(toolbarbutton);
			toolbarbutton = new Toolbarbutton("", "resources/mp_eliminarEnabled.png");
			cell.appendChild(toolbarbutton);
			item.appendChild(cell);
			
			/*	ARMANDO LA CADENA DE TOKENS	*/
			StringBuffer expresion = new StringBuffer();
			expresion.append(Promocion.TOKEN_RUTA+":"+promocion.getRutas()+";");
			expresion.append(Promocion.TOKEN_SERVICIO+":"+promocion.getServicios()+";");
			expresion.append(Promocion.TOKEN_PUNTO_VENTA+":"+promocion.getPuntoVenta()+";");
			expresion.append(Promocion.TOKEN_CANAL_VENTA+":"+promocion.getCanalVenta()+";");
			expresion.append(Promocion.TOKEN_PASAJERO_NUEVO+":"+promocion.getPasajeroNuevo()+";");
			expresion.append(Promocion.TOKEN_CANTIDAD_VIAJES+":"+promocion.getCantidadViajesPasajero()+";");
			expresion.append(Promocion.TOKEN_ASIENTO+":"+promocion.getAsientos()+";");
			expresion.append(Promocion.TOKEN_CLIENTE+":"+promocion.getCliente()+";");
			expresion.append(Promocion.TOKEN_IDA_VUELTA+":"+promocion.getIdaVuelta()+";");
			expresion.append(Promocion.TOKEN_FORMA_PAGO+":"+promocion.getFormaPago()+";");
			expresion.append(Promocion.TOKEN_TIPO_FORMA_PAGO+":"+promocion.getTipoFormaPago()+";");
			expresion.append(Promocion.TOKEN_TARJETA_CREDITO+":"+promocion.getTarjetaCredito()+";");
			expresion.append(Promocion.TOKEN_TEMPORADA+":"+promocion.getEnTemporada()+";");
			expresion.append(Promocion.TOKEN_PAXFRE+":"+promocion.getPasajeroFrecuente()+";");
			expresion.append(Promocion.TOKEN_HORA_PARTIDA+":"+promocion.getHorasPartida());
			promocion.setExpresion(expresion.toString());
			
			StringBuffer beneficio = new StringBuffer();
			beneficio.append(Promocion.TOKEN_TIPO_DESCUENTO+":"+promocion.getTipoDescuento()+";");
			beneficio.append(Promocion.TOKEN_VALOR_DESCUENTO+":"+(promocion.getValorDescuento()==null?"*":promocion.getValorDescuento())+";");
			beneficio.append(Promocion.TOKEN_IMPORTE+":"+(promocion.getPorImporte()==null?"*":promocion.getPorImporte()));
			promocion.setBeneficio(beneficio.toString());
			promocion.setEsAcumulable(Constantes.FALSE_VALUE);
//			promocion.setEsTarifa(Constantes.FALSE_VALUE);
			
			/*Implementado 01/10/2014 - jabanto*/
			String question=Messages.getString("WndPromociones.information.confirmacionGuardarPromocion");
			if(lblPermitirDescuento.isVisible() && chbxPermitirDescuento.isVisible() ){
				if(chbxPermitirDescuento.isChecked()){
					question=Messages.getString("WndPromociones.information.permitirDescuento");
					promocion.setEsAcumulable(Constantes.TRUE_VALUE);
				}else
					question=Messages.getString("WndPromociones.information.noPermitirDescuento");
			}
			
			promocion.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			UtilData.auditarRegistro(promocion, getUsuario(), Executions.getCurrent());
			Messagebox.show(question, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							int result = Constantes.FAILURE;
							result = ServiceLocator.getPromocionManager().guardarPromocion(promocion);
							if(result == Constantes.CORRECT){
								DlgMessage.information(Messages.getString("WndPromociones.information.exitoGuardarPromocion"));
								item.setValue(promocion.clone());
								lbxPromociones.appendChild(item);
								cleanControls();
								disabledControls(true);
								chkbxPorRango.setChecked(false);
								habilitarControlAsientos(false);
								tlbrbtnNuevo.setDisabled(false);
								tlbrbtnCancelar.setDisabled(true);
								tlbrbtnBuscar.setDisabled(false);
							}
						}
					}catch(PromocionExcepcion pex){
						DlgMessage.information(Messages.getString("WndPromociones.information.denominacionDuplicada"), txtDenominacion);
					}catch(Exception ex){ex.printStackTrace();
						ex.printStackTrace();
						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
					}
				}
			});			
		}catch(PromocionExcepcion pex){
			if(pex.getTipo().intValue()==PromocionExcepcion.DENOMINACION)
				DlgMessage.information(Messages.getString("WndPromociones.information.noDenominacion"), txtDenominacion);
			else if(pex.getTipo().intValue()==PromocionExcepcion.TIPO_DESCUENTO)
				DlgMessage.information(Messages.getString("WndPromociones.information.noTipoDescuento"), cmbTipoDescuento);
			else if(pex.getTipo().intValue()==PromocionExcepcion.NO_DESCUENTO){
				if(cmbTipoDescuento.getSelectedItem().getValue().toString().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO))
					DlgMessage.information(Messages.getString("WndPromociones.information.noValorDescuento"), dblbxDescuento);
				else if(cmbTipoDescuento.getSelectedItem().getValue().toString().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE))
					DlgMessage.information(Messages.getString("WndPromociones.information.noPorcentajeDescuento"), dblbxDescuento);
				else
					DlgMessage.information(Messages.getString("WndPromociones.information.noTarifaDescuento"), dblbxDescuento);
			}
//			else if(pex.getTipo().intValue()==PromocionExcepcion.NO_DESCUENTO_IMPORTE)
//				DlgMessage.information(Messages.getString("WndPromociones.information.noDescuentoImporte"), dblbxImporte);
//			else if(pex.getTipo().intValue()==PromocionExcepcion.DESCUENTO_IMPORTE)
//				DlgMessage.information(Messages.getString("WndPromociones.information.descuentoImporte"), dblbxImporte);
		}catch(FechaInicioNullException finex){
			DlgMessage.information(Messages.getString("WndPromociones.information.fechaInicio"), dtbxFechaInicio);
		}catch(FechaVencimientoNullException fvnex){
			DlgMessage.information(Messages.getString("WndPromociones.information.fechaFin"), dtbxFechaFin);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
	private void buscar(){
		try{
			lbxPromociones.getItems().clear();
			
			String denominacion = txtDenominacionB.getText().isEmpty()?null:txtDenominacionB.getText().trim().toUpperCase();
			String ruta = null;
			if(cmbRutaB.getSelectedItem().getValue() instanceof Ruta)
				ruta = ((Ruta)cmbRutaB.getSelectedItem().getValue()).getId().toString();
			String servicio = null;
			if(cmbServicioB.getSelectedItem().getValue() instanceof Servicio)
				servicio = ((Servicio)cmbServicioB.getSelectedItem().getValue()).getId().toString();
			String clienteb = txtClienteB.getText().isEmpty()?null:txtClienteB.getText().trim();
			String tipoDescuento = null;
			if(cmbTipoDescuentoB.getSelectedItem().getValue() instanceof String)
				tipoDescuento = ((String)cmbTipoDescuentoB.getSelectedItem().getValue()).toString();			
			
//			List<Promocion> lstPromociones = ServiceLocator.getPromocionManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO, "denominacion, fechaInicio");
			List<Promocion> lstPromociones = ServiceLocator.getPromocionManager().buscarPorCriterios(denominacion, ruta, servicio, clienteb, tipoDescuento, "c_denominacion, d_fecini");
			if(lstPromociones.size()>0){
				Listcell cell = null;
				for(Promocion promocion : lstPromociones){
					Listitem item = new Listitem();
					cell = new Listcell(promocion.getDenominacion());
					item.appendChild(cell);
					/*	Para mostrar las rutas	*/
					if(!promocion.getRutas().equals("*")){
						String[] ids = promocion.getRutas().split(",");
						Integer[] oCriteriosIN = new Integer[ids.length];
						for(int i=0; i<ids.length; i++){
							oCriteriosIN[i]=Integer.valueOf(ids[i]);
						}
						List<String> criteriosOrdenar = new ArrayList<String>();
						criteriosOrdenar.add("origen");
						List<Ruta> lstRutas = ServiceLocator.getRutaManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
						cell = new Listcell(lstRutas.toString().substring(1, lstRutas.toString().length()-1));
					}else{
						cell = new Listcell(promocion.getRutas());						
					}
					item.appendChild(cell);
					/*	Para mostrar los servicios	*/
					if(!promocion.getServicios().equals("*")){
						String[] ids = promocion.getServicios().split(",");
						Integer[] oCriteriosIN = new Integer[ids.length];
						for(int i=0; i<ids.length; i++){
							oCriteriosIN[i]=Integer.valueOf(ids[i]);
						}
						List<String> criteriosOrdenar = new ArrayList<String>();
						criteriosOrdenar.add("denominacion");
						List<Servicio> lstServicios = ServiceLocator.getServicioManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
						cell = new Listcell(lstServicios.toString().substring(1, lstServicios.toString().length()-1));
					}else
						cell = new Listcell(promocion.getServicios());
					item.appendChild(cell);
					/*	Para mostrar los Puntos de venta	*/
					if(!promocion.getPuntoVenta().equals("*")){
						String[] ids = promocion.getPuntoVenta().split(",");
						Integer[] oCriteriosIN = new Integer[ids.length];
						for(int i=0; i<ids.length; i++){
							oCriteriosIN[i]=Integer.valueOf(ids[i]);
						}
						List<String> criteriosOrdenar = new ArrayList<String>();
						criteriosOrdenar.add("denominacion");
						List<Agencia> lstAgencias = ServiceLocator.getAgenciaManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
						cell = new Listcell(lstAgencias.toString().substring(1, lstAgencias.toString().length()-1));
					}else
						cell = new Listcell(promocion.getPuntoVenta());
					item.appendChild(cell);
					/*	Para mostrar los canales de venta	*/
					if(!promocion.getCanalVenta().equals("*")){
						String[] ids = promocion.getCanalVenta().split(",");
						Integer[] oCriteriosIN = new Integer[ids.length];
						for(int i=0; i<ids.length; i++){
							oCriteriosIN[i]=Integer.valueOf(ids[i]);
						}
						List<String> criteriosOrdenar = new ArrayList<String>();
						criteriosOrdenar.add("denominacion");
						List<CanalVenta> lstCanalesVenta = ServiceLocator.getCanalVentaManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
						cell = new Listcell(lstCanalesVenta.toString().substring(1, lstCanalesVenta.toString().length()-1));
					}else
						cell = new Listcell(promocion.getCanalVenta());
					item.appendChild(cell);
					/*	Para cargar el Cliente	*/
					if(!promocion.getCliente().equals("*")){
						Cliente cliente = ServiceLocator.getClienteManager().buscarPorId(Long.valueOf(promocion.getCliente()));
						cell = new Listcell(cliente.getRazonSocial());
					}else
						cell = new Listcell(promocion.getCliente());
					item.appendChild(cell);
					cell = new Listcell(promocion.getAsientos());
					item.appendChild(cell);
					cell = new Listcell(promocion.getCantidadViajesPasajero());
					item.appendChild(cell);
					cell = new Listcell(promocion.getPorImporte()==null?"":Util.toNumberFormat(promocion.getPorImporte(), 2));
					item.appendChild(cell);
					cell = new Listcell(promocion.getValorDescuento()==null?"":Util.toNumberFormat(promocion.getValorDescuento(), 2));
					item.appendChild(cell);
					cell = new Listcell(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_PORCENTAJE)?Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_PORCENTAJE:(promocion.getTipoDescuento().equals(Promocion.PROMOCION_TIPO_DESCUENTO_VALUE_FIJO)?Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_FIJO:Promocion.PROMOCION_TIPO_DESCUENTO_LABEL_TARIFA));
					item.appendChild(cell);
					cell = new Listcell(promocion.getEnTemporada());
					item.appendChild(cell);
					/*	Para cargar las Forma de Pago	*/
					if(!promocion.getFormaPago().equals("*")){
						FormaPago formaPago = ServiceLocator.getFormaPagoManager().buscarPorId(Long.valueOf(promocion.getFormaPago()));
						cell = new Listcell(formaPago.getDenominacion());
					}else
						cell = new Listcell(promocion.getFormaPago());
					item.appendChild(cell);
					if(!promocion.getTipoFormaPago().equals("*")){
						TipoFormaPago tipoFormaPago = ServiceLocator.getTipoFormaPagoManager().buscarPorId(Long.valueOf(promocion.getTipoFormaPago()));
						cell = new Listcell(tipoFormaPago.getDenominacion());
					}else
						cell = new Listcell(promocion.getTipoFormaPago());
					item.appendChild(cell);
					/*	para mostrar las tarjetas de Credito	*/
					if(!promocion.getTarjetaCredito().equals("*")){
						String[] ids = promocion.getTarjetaCredito().split(",");
						Integer[] oCriteriosIN = new Integer[ids.length];
						for(int i=0; i<ids.length; i++){
							oCriteriosIN[i]=Integer.valueOf(ids[i]);
						}
						List<String> criteriosOrdenar = new ArrayList<String>();
						criteriosOrdenar.add("denominacion");
						List<TarjetaCredito> lstTarjetasCredito = ServiceLocator.getTarjetaCreditoManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
						cell = new Listcell(lstTarjetasCredito.toString().substring(1, lstTarjetasCredito.toString().length()-1));
					}else
						cell = new Listcell(promocion.getTarjetaCredito());
					item.appendChild(cell);
					cell = new Listcell(promocion.getFechaInicio()==null?"":Util.DatetoString(promocion.getFechaInicio(), Constantes.DATE_FORMAT));
					item.appendChild(cell);
					cell = new Listcell(promocion.getFechaFin()==null?"":Util.DatetoString(promocion.getFechaFin(), Constantes.DATE_FORMAT));
					item.appendChild(cell);
					cell = new Listcell(promocion.getPasajeroNuevo());
					item.appendChild(cell);
					cell = new Listcell(promocion.getIdaVuelta());
					item.appendChild(cell);
					cell = new Listcell(promocion.getPasajeroFrecuente());
					item.appendChild(cell);
					cell = new Listcell(promocion.getHorasPartida());
					cell.setStyle("font-size:11px !important");
					item.appendChild(cell);
					
					if(promocion.getEsTarifa().intValue()==Constantes.TRUE_VALUE){
						cell = new Listcell();
						Button btnEditar = new Button("Editar");
						btnEditar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							public void onEvent(Event e){
								editarPromocion(Long.valueOf(e.getTarget().getId()));
							}
						});
						btnEditar.setImage("resources/mp_editarEnabled.png");
						btnEditar.setId(""+promocion.getId());
						cell.appendChild(btnEditar);
						item.appendChild(cell);
					}
					
					
					Button btnEliminar=new Button("Eliminar");
					btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
						public void onEvent(final Event e1){
							Messagebox.show(Messages.getString("Generales.question.delete"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
								@Override
								public void onEvent(Event e) throws Exception {
									if(e.getName().equals("onYes")){		
										try{
											
											Listitem oitem=(Listitem) e1.getTarget().getAttribute("ITEM");
											eliminarPromocion(Long.valueOf(e1.getTarget().getId()), oitem);
											
										} catch (Exception e2) {
											e2.printStackTrace();
											DlgMessage.error(e2.getMessage());
										}
									}
								}
							});
						}
					});
					btnEliminar.setImage("resources/mp_anular.png");
					btnEliminar.setId(""+promocion.getId());
					btnEliminar.setAttribute("ITEM", item);
					cell.appendChild(btnEliminar);
					item.appendChild(cell);
					
					
					item.setValue(promocion);
					lbxPromociones.appendChild(item);
					grdCriterios.setVisible(false);
					limpiarCriterios();
				}
			}else
				DlgMessage.information(Messages.getString("WndPromociones.information.noPromociones"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	private void eliminarPromocion(Long idPromocion, Listitem item) throws Exception{
		Promocion oPromocion= ServiceLocator.getPromocionManager().buscarPorId(idPromocion);
		UtilData.auditarRegistro(oPromocion, true, getUsuario(), Executions.getCurrent());
		oPromocion.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		ServiceLocator.getPromocionManager().actualizar(oPromocion);
		
		lbxPromociones.removeChild(item);
	}
	
	
	@SuppressWarnings("deprecation")
	private void editarPromocion(Long idPromocion){		
		try{
			Promocion promocion = ServiceLocator.getPromocionManager().buscarPorId(idPromocion);
			promocionEditar = promocion;
			wndCriterios = new Window("Edición de Promocion", "normal", true);
			wndCriterios.setHeight("350px");
			wndCriterios.setWidth("600px");
			this.appendChild(wndCriterios);
			Listbox listbox = new Listbox();
			Listhead listhead = new Listhead();
			Listheader listheader = new Listheader("DENOMINACION");
			listheader.setWidth("180px");
			listhead.appendChild(listheader);
			listheader = new Listheader("RUTA");
			listheader.setWidth("180px");
			listhead.appendChild(listheader);
			listheader = new Listheader("IMPORTE");
			listheader.setWidth("70px");
			listheader.setAlign("right");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.INICIO");
			listheader.setWidth("70px");
			listheader.setAlign("center");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.FIN");
			listheader.setWidth("70px");
			listheader.setAlign("center");
			listhead.appendChild(listheader);
			listbox.appendChild(listhead);
			
			Listitem listitem = new Listitem();
			Listcell listcell = new Listcell(promocion.getDenominacion());
			listitem.appendChild(listcell);
			/*	Para mostrar las rutas	*/
			if(!promocion.getRutas().equals("*")){
				String[] ids = promocion.getRutas().split(",");
				Integer[] oCriteriosIN = new Integer[ids.length];
				for(int i=0; i<ids.length; i++){
					oCriteriosIN[i]=Integer.valueOf(ids[i]);
				}
				List<String> criteriosOrdenar = new ArrayList<String>();
				criteriosOrdenar.add("origen");
				List<Ruta> lstRutas = ServiceLocator.getRutaManager().buscarPorX("id", oCriteriosIN, criteriosOrdenar, Constantes.VALUE_ACTIVO);
				listcell = new Listcell(lstRutas.toString().substring(1, lstRutas.toString().length()-1));
			}else{
				listcell = new Listcell(promocion.getRutas());						
			}
			listitem.appendChild(listcell);
			listcell = new Listcell(promocion.getPorImporte()==null?"":Util.toNumberFormat(promocion.getPorImporte(), 2));
			listitem.appendChild(listcell);
			listcell = new Listcell(promocion.getFechaInicio()==null?"":Util.DatetoString(promocion.getFechaInicio(), Constantes.DATE_FORMAT));
			listitem.appendChild(listcell);
			listcell = new Listcell(promocion.getFechaFin()==null?"":Util.DatetoString(promocion.getFechaFin(), Constantes.DATE_FORMAT));
			listitem.appendChild(listcell);
			listitem.setValue(promocion);
			listbox.appendChild(listitem);
			wndCriterios.appendChild(listbox);
			
			Grid grid = new Grid();
			Columns columns = new Columns();
			Column column = new Column();
			column.setWidth("30%");
			columns.appendChild(column);
			column = new Column();
			column.setWidth("30%");
			columns.appendChild(column);
			column = new Column();
			column.setWidth("30%");
			columns.appendChild(column);
			column = new Column();
			column.setWidth("10%");
			columns.appendChild(column);
			Rows rows = new Rows();
			Row row = new Row();
			
			Vlayout vlayout = new Vlayout();
			Label label = new Label("FECHA INICIO :");
			vlayout.appendChild(label);
			final Datebox dtbxFechaInicio = new Datebox();
			dtbxFechaInicio.setFormat(Constantes.DATE_FORMAT);
			String fecha = Util.DatetoString(promocion.getFechaInicio(), "yyyyMMdd");
			dtbxFechaInicio.setConstraint("after "+fecha);
//			dtbxFechaInicio.setValue(promocion.getFechaInicio());
			vlayout.appendChild(dtbxFechaInicio);
			row.appendChild(vlayout);
			
			vlayout = new Vlayout();
			label = new Label("FECHA FIN :");
			vlayout.appendChild(label);
			final Datebox dtbxFechaFin = new Datebox();
			dtbxFechaFin.setFormat(Constantes.DATE_FORMAT);
			dtbxFechaFin.setConstraint("before "+Util.DatetoString(promocion.getFechaFin(), "yyyyMMdd"));
			vlayout.appendChild(dtbxFechaFin);
			row.appendChild(vlayout);
			
			vlayout = new Vlayout();
			label = new Label("TARIFA :");
			vlayout.appendChild(label);
			final Doublebox dblbxTarifa = new Doublebox(0.00);
			dblbxTarifa.setLocale(Locale.US);
			dblbxTarifa.setFormat("##0.00");
//			dblbxTarifa.setValue(0.00);
			vlayout.appendChild(dblbxTarifa);
			row.appendChild(vlayout);
			
			final Button btnAgregar = new Button("Generar", "resources/mp_agregarEnabled.png");
			btnAgregar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e){
					if(validar(dtbxFechaInicio, dtbxFechaFin, dblbxTarifa)){
						generarPromocion(dtbxFechaInicio.getValue(), dtbxFechaFin.getValue(), dblbxTarifa.getValue());
						btnAgregar.setDisabled(true);
						btnGuardar.setDisabled(false);
					}
				}
			});
			btnAgregar.setHeight("27px");
			row.appendChild(btnAgregar);
			
			rows.appendChild(row);
			grid.appendChild(rows);
			wndCriterios.appendChild(grid);
			
			lbxNuevaPromocion = new Listbox();
			listhead = new Listhead();
			listheader = new Listheader("DENOMINACION");
			listheader.setWidth("360px");
			listhead.appendChild(listheader);
			listheader = new Listheader("IMPORTE");
			listheader.setWidth("70px");
			listheader.setAlign("right");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.INICIO");
			listheader.setWidth("70px");
			listheader.setAlign("center");
			listhead.appendChild(listheader);
			listheader = new Listheader("F.FIN");
			listheader.setWidth("70px");
			listheader.setAlign("center");
			listhead.appendChild(listheader);
			lbxNuevaPromocion.appendChild(listhead);
			wndCriterios.appendChild(lbxNuevaPromocion);
			
			Div div = new Div();
			div.setAlign("center");
			btnGuardar = new Button("Guardar", "resources/mp_guardarEnabled.png");
			btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e){
					try{
						int result = Constantes.FAILURE;
						List<Promocion> lstPromocion = new ArrayList<Promocion>();
						for(Listitem listitem : lbxNuevaPromocion.getItems()){
							if(listitem.getValue() instanceof Promocion){
								Promocion promocion = listitem.getValue();
								lstPromocion.add(promocion);
							}				
						}
						ServiceLocator.getPromocionManager().guardarPromocion(lstPromocion);
						result = Constantes.CORRECT;
						if(result == Constantes.CORRECT){
							DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));
							btnGuardar.setDisabled(true);
						}
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
						ex.printStackTrace();
					}
				}
			});
			btnGuardar.setHeight("27px");
			btnGuardar.setDisabled(true);
			div.appendChild(btnGuardar);
			Button btnCerrar = new Button("Cerrar", "resources/mp_cerrar.png");
			btnCerrar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event e){
					wndCriterios.onClose();
				}
			});
			btnCerrar.setHeight("27px");
			div.appendChild(btnCerrar);
			wndCriterios.appendChild(div);
			wndCriterios.doModal();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private boolean validar(Datebox dtbxFechaInicio, Datebox dtbxFechaFin, Doublebox dblbxTarifa){
		boolean result = false;
		try{
			if(dtbxFechaInicio.getValue() == null)
				throw new FechaInicioNullException();
			else if(dtbxFechaFin.getValue() == null)
				throw new FechaVencimientoNullException();
			else if(Util.comparaFechas(dtbxFechaFin.getValue(), dtbxFechaInicio.getValue(), Util.OPER_MENOR))
				throw new FechaMenorCalendarioException();
			else if(dblbxTarifa.getValue().doubleValue() <= 0.00)
				throw new TarifarioException();
			result = true;
		}catch(FechaInicioNullException finex){
			DlgMessage.information(Messages.getString("WndPromociones.information.fechaInicio"), dtbxFechaInicio);
		}catch(FechaVencimientoNullException fvnex){
			DlgMessage.information(Messages.getString("WndPromociones.information.fechaFin"), dtbxFechaFin);
		}catch(FechaMenorCalendarioException fmcex){
			DlgMessage.information(Messages.getString("WndPromociones.information.fechaFinalMenorFechaInicial"), dtbxFechaFin);
		}catch(TarifarioException tex){
			DlgMessage.information(Messages.getString("WndPromociones.information.noTarifaDescuento"), dblbxTarifa);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
		}
		return result;
	}
	
	private void generarPromocion(Date fInicio, Date fFin, Double tarifa){
		try{
			List<Promocion> lstPromocion = new ArrayList<Promocion>();
			Promocion promocion = (Promocion)promocionEditar.clone();
			String f1 = Util.DatetoString(promocionEditar.getFechaInicio(),Constantes.DATE_FORMAT);
			String f2 = Util.DatetoString(fInicio, Constantes.DATE_FORMAT);
			String f3 = Util.DatetoString(promocionEditar.getFechaFin(),Constantes.DATE_FORMAT);
			String f4 = Util.DatetoString(fFin, Constantes.DATE_FORMAT);
			if(!f1.equals(f2)){
				Date fechaFin = new Date(fInicio.getTime() - Constantes.MILISEGUNDOS_X_DIA);
				promocion.setFechaFin(fechaFin);
				UtilData.auditarRegistro(promocion, true, getUsuario(), Executions.getCurrent());
				lstPromocion.add(promocion);
				mostrarPromocion(promocion);
				promocion = (Promocion)promocionEditar.clone();
				promocion.setId(null);
				promocion.setFechaInicio(fInicio);
				promocion.setFechaFin(fFin);
				promocion.setPorImporte(tarifa);
				promocion.setBeneficio(promocion.getBeneficio().substring(0, promocion.getBeneficio().length()-4)+Util.toNumberFormat(tarifa, 1));
				UtilData.auditarRegistro(promocion, getUsuario(), Executions.getCurrent());
				lstPromocion.add(promocion);
				mostrarPromocion(promocion);
				if(Util.comparaFechas(promocionEditar.getFechaFin(), fFin, Util.OPER_MAYOR)){
					promocion = (Promocion)promocionEditar.clone();
					promocion.setId(null);
					promocion.setFechaInicio(new Date(fFin.getTime() + Constantes.MILISEGUNDOS_X_DIA));
					UtilData.auditarRegistro(promocion, getUsuario(), Executions.getCurrent());
					lstPromocion.add(promocion);
					mostrarPromocion(promocion);
				}
			}else if(f1.equals(f2) && !f3.equals(f4)){
				promocion.setId(null);
				promocion.setFechaFin(fFin);
				promocion.setPorImporte(tarifa);
				promocion.setBeneficio(promocion.getBeneficio().substring(0, promocion.getBeneficio().length()-4)+Util.toNumberFormat(tarifa, 1));
				UtilData.auditarRegistro(promocion, getUsuario(), Executions.getCurrent());
				lstPromocion.add(promocion);
				mostrarPromocion(promocion);
				promocion = (Promocion)promocionEditar.clone();
				promocion.setFechaInicio(new Date(fFin.getTime() + Constantes.MILISEGUNDOS_X_DIA));
				UtilData.auditarRegistro(promocion, true, getUsuario(), Executions.getCurrent());
				lstPromocion.add(promocion);
				mostrarPromocion(promocion);
			}else{
				promocion.setPorImporte(tarifa);
				promocion.setBeneficio(promocion.getBeneficio().substring(0, promocion.getBeneficio().length()-4)+Util.toNumberFormat(tarifa, 1));
				UtilData.auditarRegistro(promocion, true, getUsuario(), Executions.getCurrent());
				lstPromocion.add(promocion);
				mostrarPromocion(promocion);
			}			
		}catch(PromocionExcepcion pex){
			DlgMessage.information(Messages.getString("WndPromociones.information.denominacionDuplicada"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void mostrarPromocion(Promocion promocion){
		Listitem listitem = new Listitem();
		Listcell listcell = null;
		
		listcell = new Listcell(promocion.getDenominacion());
		listitem.appendChild(listcell);
		listcell = new Listcell(Util.toNumberFormat(promocion.getPorImporte(), 2));
		listitem.appendChild(listcell);
		listcell = new Listcell(Util.DatetoString(promocion.getFechaInicio(), Constantes.DATE_FORMAT));
		listitem.appendChild(listcell);
		listcell = new Listcell(Util.DatetoString(promocion.getFechaFin(), Constantes.DATE_FORMAT));
		listitem.appendChild(listcell);
		listitem.setValue(promocion);
		lbxNuevaPromocion.appendChild(listitem);
	}
	
	private void limpiarCriterios(){
		cmbRutaB.setSelectedIndex(0);
		cmbServicioB.setSelectedIndex(0);
		cmbTipoDescuentoB.setSelectedIndex(0);
		txtDenominacionB.setText("");
		txtClienteB.setText("");
	}
}
