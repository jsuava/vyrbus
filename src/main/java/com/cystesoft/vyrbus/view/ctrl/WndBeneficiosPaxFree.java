package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cortesia;
import com.cystesoft.vyrbus.model.bean.DetalleItinerario;
import com.cystesoft.vyrbus.model.bean.EspecieValorada;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.PasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.PreferenciaAlimentaria;
import com.cystesoft.vyrbus.model.bean.Promocion;
import com.cystesoft.vyrbus.model.bean.PuntosPasajeroFrecuente;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioHardware;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.AnioCumpleaniosPaxNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.CortesiaXCumpleaniosEmitidaException;
import com.cystesoft.vyrbus.service.exceptions.FechaViajeFueraRangoException;
import com.cystesoft.vyrbus.service.exceptions.FechaViajeNoValidaException;
import com.cystesoft.vyrbus.service.exceptions.ItinerarioException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoFueraRangoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroAsientoNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroBoletoNullException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroIndeseableException;
import com.cystesoft.vyrbus.service.exceptions.PaxFreeInactivoException;
import com.cystesoft.vyrbus.service.exceptions.PaxFreeNoRegistradoException;
import com.cystesoft.vyrbus.service.exceptions.PreferenciaAlimentariaException;
import com.cystesoft.vyrbus.service.exceptions.PuntosPaxfreeInsuficientesException;
import com.cystesoft.vyrbus.service.exceptions.RangoCanjeCumpleaniosException;
import com.cystesoft.vyrbus.service.exceptions.RutaNullException;
import com.cystesoft.vyrbus.service.exceptions.RutaSinPuntajeException;
import com.cystesoft.vyrbus.service.exceptions.TiempoExpiracionBloqueoException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.AplicarPromocion;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.service.util.WSFE;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author JABANTO
 * 
 */
public class WndBeneficiosPaxFree extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 5027089313223381893L;

	private Groupbox gbxDatosCortesia;
	private Groupbox gbxViajesPax;
	private Radio rdPorDocumento;
	private Radio rdPorApellidos;
	private Textbox txtBuscarPax;
	private Textbox txtidPasajero;
	private Textbox txtAsiento;
	private Textbox txtNumeroBoleto;
	private Textbox txtObserveaciones;
	private Listbox lisboxPasajeros;
	private Listbox listViajesPax;
	private Listbox lstServicios;
	private Combobox cmbRuta;
	private Combobox cmbCiudadOrigen;
	private Combobox cmbPuntoEmbarque;
	private Combobox cmbPuntoLlegada;
	private Combobox cmbAlimentacion;
	private Combobox cmbTipoFormaPago;
	private Label lbPasajero;
	private Label lbNumeroDocuemnto;
	private Label lbFechaNacimiento;
	private Label lbUbigeo;
	private Label lbPaxfri;
	private Label lbPuntosPax;
	private Label lbCantViajesPax;
	private Label lblFechaPartida;
	private Label lblFechaLlegada;
	private Label lblHoraPartida;
	private Label lblHoraLlegada;
	private Image imgSelectAsiento;
	private Image imgRefreshBoleto;
	private Datebox dbxFechaViaje;
	private Grid grInfoPax;
	private Button btnBuscarPax;
	private Caption cpViajesPax;
	private Intbox itAnioCortesia;
	private Listheader lstHeaderAnio;
	private Bandbox bdbxServicios;
	private Window wndCortesia;
	
	private Cortesia cortesia = null;
	private Pasajero pasajero = null;
	private CanalVenta canalVenta = null;
	private Date fechaLiquidacion=null;
	private UsuarioHardware usuarioHardware=null;

	private String imgDisabledBusq="resources/mp_buscarDisabled.png";
	private String imgEnabledBusq="resources/mp_buscarEnabled.png";
	private String imgDisabledRefresh="resources/mp_refrescarDisabled.png";
	private String imgEnabledRefresh="resources/mp_refrescarEnabled.png";
		
	public Integer action = null;
	
//	/*Variables utilizadas para la validacion de la tarifa por asiento, en el caso de los servicios suite*/
//	public Doublebox dblImporte=null;
//	public Label lblPromocion=null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		gbxDatosCortesia = (Groupbox) this.getFellow("gbxDatosCortesia");
		cmbRuta = (Combobox) this.getFellow("cmbRuta");
		rdPorDocumento = (Radio) this.getFellow("rdPorDocumento");
		rdPorApellidos = (Radio) this.getFellow("rdPorApellidos");
		txtBuscarPax = (Textbox) this.getFellow("txtBuscarPax");
		lisboxPasajeros = (Listbox) this.getFellow("lisboxPasajeros");
		txtidPasajero = (Textbox) this.getFellow("txtidPasajero");
		lbPasajero = (Label) this.getFellow("lbPasajero");
		dbxFechaViaje = (Datebox) this.getFellow("dbxFechaViaje");
		cmbCiudadOrigen = (Combobox) this.getFellow("cmbCiudadOrigen");
		lbNumeroDocuemnto = (Label) this.getFellow("lbNumeroDocuemnto");
		lbUbigeo = (Label) this.getFellow("lbUbigeo");
		grInfoPax = (Grid) this.getFellow("grInfoPax");
		btnBuscarPax = (Button) this.getFellow("btnBuscarPax");
		lbPaxfri = (Label) this.getFellow("lbPaxfri");
		gbxViajesPax = (Groupbox) this.getFellow("gbxViajesPax");
		cpViajesPax = (Caption) this.getFellow("cpViajesPax");
		listViajesPax = (Listbox) this.getFellow("listViajesPax");
		itAnioCortesia = (Intbox) this.getFellow("itAnioCortesia");
		lbCantViajesPax = (Label) this.getFellow("lbCantViajesPax");
		lbFechaNacimiento = (Label) this.getFellow("lbFechaNacimiento");
		lstHeaderAnio = (Listheader) this.getFellow("lstHeaderAnio");
		lbPuntosPax=(Label)this.getFellow("lbPuntosPax");
		cmbTipoFormaPago = (Combobox) this.getFellow("cmbTipoFormaPago");
		bdbxServicios=(Bandbox)this.getFellow("bdbxServicios");
		lstServicios=(Listbox)this.getFellow("lstServicios");
		txtAsiento=(Textbox)this.getFellow("txtAsiento");
		imgSelectAsiento=(Image)this.getFellow("imgSelectAsiento");
		cmbPuntoEmbarque=(Combobox)this.getFellow("cmbPuntoEmbarque");
		cmbPuntoLlegada=(Combobox)this.getFellow("cmbPuntoLlegada");
		lblFechaPartida=(Label)this.getFellow("lblFechaPartida");
		lblFechaLlegada=(Label)this.getFellow("lblFechaLlegada");
		lblHoraPartida=(Label)this.getFellow("lblHoraPartida");
		lblHoraLlegada=(Label)this.getFellow("lblHoraLlegada");
		cmbAlimentacion=(Combobox)this.getFellow("cmbAlimentacion");
		txtNumeroBoleto=(Textbox)this.getFellow("txtNumeroBoleto");
		imgRefreshBoleto=(Image)this.getFellow("imgRefreshBoleto");
		txtObserveaciones=(Textbox)this.getFellow("txtObserveaciones");
		wndCortesia=(Window)this.getFellow("wndCortesia");
		
		imgSelectAsiento.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if(imgSelectAsiento.getSrc().equals(imgEnabledBusq)){
					Textbox txtNumeroPiso= new Textbox();
					txtNumeroPiso.setText("0");
					DetalleItinerario detalleItinerario= lstServicios.getSelectedItem().getValue();
					Itinerario itinerario=detalleItinerario.getItinerario();
					ItinerarioAgenciaPartida itinerarioAgenciaPartida=(ItinerarioAgenciaPartida)cmbPuntoEmbarque.getSelectedItem().getValue();
					
					VentaPasaje ventaPasaje= new VentaPasaje();
					ventaPasaje.setServicio(itinerario.getServicio());
					ventaPasaje.setRuta((Ruta)cmbRuta.getSelectedItem().getValue());
					ventaPasaje.setItinerario(itinerario);
					ventaPasaje.setFechaPartida(dbxFechaViaje.getValue());
					ventaPasaje.setHoraPartida(itinerarioAgenciaPartida.getHoraPartida());
//					ventaPasaje.setHoraPartida(detalleItinerario.getHoraPartida());
					
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("ventaPasaje", ventaPasaje);
					params.put("detalleItinerario", ((DetalleItinerario)lstServicios.getSelectedItem().getValue()));
					params.put("txtAsientoSeleccionado", txtAsiento);
					params.put("txtPisoSeleccionado",txtNumeroPiso); //txtNumeroPiso
					params.put("selectAsiento", true);
					Window win = (Window) Executions.createComponents("mapa.zul", null, params);
					win.setMode(MODAL);
				}
			}
		});
		
		imgRefreshBoleto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event e) throws Exception{
				if(imgRefreshBoleto.getSrc().equals(imgEnabledRefresh))
					onLoadEspecieValorada();
			}
		});
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		canalVenta = (CanalVenta) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
		fechaLiquidacion=(Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
		usuarioHardware = (UsuarioHardware) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_HARDWARE);
		/*********************************************************************/
		/* Carga datos predeterminados para la cortesia */
		UtilData.cargarDataCombo(cmbAlimentacion, PreferenciaAlimentaria.class, false);
		UtilData.cargarDataCombo(cmbCiudadOrigen, Localidad.class, false);
		UtilData.cargarGenericData(cmbRuta, false);
		UtilData.cargarGenericData(cmbPuntoEmbarque, false);
		UtilData.cargarGenericData(cmbPuntoLlegada, false);
		cargarTipoFormaPago();
		
		/* Carga combo para el Mantenimiento de Pasajeros */
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			
		btnEliminar.setVisible(false);
		btnRefrescar.setVisible(false);
		
		listboxLista.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				btnModificar.setDisabled(true);
			}
		});	
		
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				btnModificar.setDisabled(true);
			}
		});
		
		oTabLista.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if (tabUsuario == TAB_LIST) {
					btnModificar.setDisabled(true);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		/* Carga datos predeterminados para la cortesia */
		Util.limpiarCombobox(cmbCiudadOrigen);
		Util.limpiarCombobox(cmbRuta);
		Util.limpiarCombobox(cmbTipoFormaPago);
		UtilData.cargarDataCombo(cmbCiudadOrigen, Localidad.class, false);
		UtilData.cargarGenericData(cmbRuta, false);
		cargarTipoFormaPago();
		cmbCiudadOrigen.setSelectedIndex(0);
		cmbRuta.setSelectedIndex(0);
		cmbTipoFormaPago.setSelectedIndex(0);
		cmbAlimentacion.setSelectedIndex(0);
		
		rdPorDocumento.setSelected(true);
		lisboxPasajeros.setVisible(false);
		
//		dbxFechaViaje.setValue(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())));

		activarBtnBuscar(true);
		habilitaControleCortesia(true);
		gbxDatosCortesia.setVisible(true);
		
		onLoadEspecieValorada();
		
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dbxFechaViaje.setConstraint("after "+fecha);
		
		limpiaControlesPasajero();
		limpiarDatosItinerario();
		disabledImgRefresh(false);
		Util.limpiarListbox(listViajesPax);
		gbxViajesPax.setVisible(false);
		lbCantViajesPax.setValue("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
		oWndFiltrar.addParameter("Tipo forma de Pago", TipoFormaPago.class);
		oWndFiltrar.addParameter("Fecha", Datebox.class);
		oWndFiltrar.addParameter("Doct.Pasajero", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						TipoFormaPago tipoFormaPago = (TipoFormaPago) oWndFiltrar.getParameterValue("Tipo forma de Pago");
						Date fecha = (Date) oWndFiltrar.getParameterValue("Fecha");
						String docPasajero = (String) oWndFiltrar.getParameterValue("Doct.Pasajero");

						TreeMap<String, Object>criterioBusqueda= new TreeMap<String, Object>();
						if(tipoFormaPago!=null)							
							criterioBusqueda.put("tipoFormaPago", tipoFormaPago);
						criterioBusqueda.put("fechaLiquidacion", fecha);
						if(!(docPasajero.trim().isEmpty())){
							TreeMap<String, Object> criterioBusquedaPax = new TreeMap<String, Object>();
							criterioBusquedaPax.put("numeroDocumento", docPasajero);
							List<?> listPax = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusquedaPax, null);
							Pasajero pasajero = new Pasajero();
							if (listPax.size() > 0) 
								pasajero.setId((Long) ((Pasajero) listPax.get(0)).getId());
							 else 
								pasajero.setId((long) -1);
							criterioBusqueda.put("pasajero", pasajero);
						}
						FormaPago formaPago= new FormaPago();
						formaPago.setId(Constantes.ID_FORPAG_CORTESIA);
						
						criterioBusqueda.put("formaPago",formaPago );
						criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
						
						Integer idTipoFormaPago=null;
						if(tipoFormaPago!=null) 
							idTipoFormaPago=tipoFormaPago.getId();
						
						listarCortesia(ServiceLocator.getCortesiaManager().buscarCortesia(Constantes.FORMAT_DATE.format(fecha), docPasajero, idTipoFormaPago));
//						listarCortesia(ServiceLocator.getVentaPasajesManager().buscarPorX(criterioBusqueda, null));
						
						
//						Cortesia ocortesia = new Cortesia();
//						if (!(docPasajero.isEmpty())) {
//							Pasajero pasajero = new Pasajero();
////							TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
//							criterioBusqueda.put("numeroDocumento", docPasajero);
//							List<?> listPax = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, null);
//							
//							if (listPax.size() > 0) {
//								Long idPasajero = (Long) ((Pasajero) listPax.get(0)).getId();
//								pasajero.setId(idPasajero);
//								ocortesia.setPasajero(pasajero);
//							} else {
//								pasajero.setId((long) -1);
//								ocortesia.setPasajero(pasajero);
//							}
//						}
						
//						ocortesia.setFechaInicio(fechaInicio);
//						ocortesia.setTipoFormaPago(tipoFormaPago);
//						Util.limpiarListbox(listboxLista);
						
//						listarCortesia(ServiceLocator.getCortesiaManager().BuscarCortesia(ocortesia));
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		EdicionCortesia();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		pasajero = null;
		limpiaControlesPasajero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
//	@SuppressWarnings("deprecation")
	@Override
	public void onSave(int action) throws Exception {
		try {
			if (pasajero == null)
				throw new PasajeroException();
			else if (txtNumeroBoleto.getText().trim().isEmpty())
				throw new NumeroBoletoNullException();
			else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
				throw new TipoFormaPagoNullException();
			else if (dbxFechaViaje.getValue()==null)
				throw new FechaViajeNoValidaException();
			else if (!(cmbRuta.getSelectedItem().getValue() instanceof Ruta))
				throw new RutaNullException();
			else if (bdbxServicios.getText().trim().isEmpty())
				throw new ItinerarioException(ItinerarioException.NO_SELECT);
			else if (!(cmbPuntoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida))
				throw new ItinerarioException(ItinerarioException.AGENCIA_PARTIDA_NULL);
			else if (!(cmbPuntoLlegada.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada))
				throw new ItinerarioException(ItinerarioException.AGENCIA_LLEGADA_NULL);
			else if (txtAsiento.getText().trim().isEmpty())
				throw new NumeroAsientoNullException();
			else if(!(cmbAlimentacion.getSelectedItem().getValue() instanceof PreferenciaAlimentaria))
				throw new PreferenciaAlimentariaException();
			else if (((TipoFormaPago) cmbTipoFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS))
				if (itAnioCortesia.getValue() == null|| itAnioCortesia.getValue().toString().length() < 4)
					throw new AnioCumpleaniosPaxNullException();
			
			// Valida que el pasajero sea PAXFREE y que este activo, para los casos de canje por cumpleanios o puntos.
			final TipoFormaPago tipoFormaPago = cmbTipoFormaPago.getSelectedItem().getValue();
			if (tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS) || tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)) {
				if (pasajero.getPasajeroFrecuente() == null)
					throw new PaxFreeNoRegistradoException();
				else if (pasajero.getPasajeroFrecuente().getEstado().equals(Constantes.FALSE_VALUE))
					throw new PaxFreeInactivoException();
			}

			List<PuntosPasajeroFrecuente>lstPuntaje=null;
						
			// Si la forma de pago es por Cumpleaños.
			if (tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS)) {
				
				if (!(validadFechaCumpleanios(null)))
					throw new RangoCanjeCumpleaniosException();

				// Valida si el pasajero tiene algún boleto cajeado por cumpleanios en el anio ingresado. 
				Integer anio = itAnioCortesia.getValue();
				for (int x = 0; x < listViajesPax.getItems().size(); x++) {
					Listitem listitem = listViajesPax.getItemAtIndex(x);
					Cortesia cortesia = listitem.getValue();
					if (cortesia.getAnioCumpleanios().equals(anio)|| anio < cortesia.getAnioCumpleanios())
						throw new CortesiaXCumpleaniosEmitidaException();
				}
				
				/* Si la forma de pago es por puntos	*/
			} else if (tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)) { 
				Ruta ruta = cmbRuta.getSelectedItem().getValue();
				/*Valida que la ruta cuenta con puntaje*/
				if (ruta.getPuntaje() == null || ruta.getPuntaje() <= 0) 
					throw new RutaSinPuntajeException();
			
				Integer puntosRuta = ruta.getPuntaje();
				Integer puntosPax=0;
				/*Busca puntaje del Paxfree*/
				lstPuntaje=new ArrayList<PuntosPasajeroFrecuente>(ServiceLocator.getPuntosPasajeroFrecuenteManager().buscarPuntosDisponibles(pasajero.getPasajeroFrecuente().getId()));
				if(lstPuntaje.size()>0)
					puntosPax=((PuntosPasajeroFrecuente)lstPuntaje.get(0)).getTotalPuntaje();
				
				//Valida que el Pasajero cuente con los puntos necesarios para el canje.
				if (puntosPax < puntosRuta){
					lbPuntosPax.setValue("PUNTOS: "+puntosPax);
					throw new PuntosPaxfreeInsuficientesException();
				}
			}
			
			//Valida si la fecha de viaje esta dentro de lo permitido
			if (tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS))
				if (!(validadFechaCumpleanios(dbxFechaViaje.getValue())))
					throw new FechaViajeFueraRangoException();

			/* Solicita confirmacion al usuario*/
			final List<PuntosPasajeroFrecuente>lstePuntaje=lstPuntaje;
			Messagebox.show(Messages.getString("WndCortesia.information.confirmaGuardarCortesia"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						guardaCortesia(tipoFormaPago,lstePuntaje);
					}	
				}	
			});
			
			
		throw new CancelaGrabacionException();
		
		
		}catch (FechaViajeFueraRangoException fvfrex){
			DlgMessage.information(Messages.getString("WndCortesia.information.fechaViajeFueraRango"));
			throw new CancelaGrabacionException();
		}catch (NumeroBoletoNullException nbnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNumeroBoleto"));
			throw new CancelaGrabacionException();
		}catch (FechaViajeNoValidaException fvnex){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoFechaViaje"),dbxFechaViaje);
			throw new CancelaGrabacionException();
		}catch (NumeroAsientoNullException nanex){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoAsiento"),txtAsiento);
			throw new CancelaGrabacionException();
		}catch (PreferenciaAlimentariaException panex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPreferenciaAlimentaria"), cmbAlimentacion);
			throw new CancelaGrabacionException();
		}catch (ItinerarioException i){
			if(i.getTipo().intValue()==ItinerarioException.NO_SELECT)
				DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoServicio"), bdbxServicios);
			else if (i.getTipo().intValue()==ItinerarioException.AGENCIA_LLEGADA_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciallegadaSeleccionada"), cmbPuntoEmbarque);
			else if(i.getTipo().intValue()==ItinerarioException.AGENCIA_PARTIDA_NULL)
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noAgenciaPartidaSeleccionada"), cmbPuntoLlegada);
			throw new CancelaGrabacionException();
		}catch (TipoFormaPagoNullException tfpnex){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoTipoFormaPago"),cmbTipoFormaPago);
			throw new CancelaGrabacionException();
		}catch (CancelaGrabacionException pswx){
			throw new CancelaGrabacionException();
		} catch (RutaNullException rnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoRuta"),cmbRuta);
			throw new CancelaGrabacionException();
		} catch (AnioCumpleaniosPaxNullException acnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoAnioCumpleanios"),itAnioCortesia);
			throw new CancelaGrabacionException();
		} catch (PaxFreeNoRegistradoException pfnrex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.paxFreeNoRegistrado"),txtBuscarPax);
			throw new CancelaGrabacionException();
		} catch (PaxFreeInactivoException pfiex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.paxFreeInactivo"),txtBuscarPax);
			throw new CancelaGrabacionException();
		} catch (RutaSinPuntajeException rspex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.rutaSinPuntaje"),cmbRuta);
			throw new CancelaGrabacionException();
		} catch (RangoCanjeCumpleaniosException cfrpexc) {
			DlgMessage.information(Messages.getString("WndCortesia.information.cumpleaniosFueraDelRangoPermitido"));
			throw new CancelaGrabacionException();
		} catch (WrongValueException wn) {
			DlgMessage.information(wn.getMessage() + " para la fecha de viaje.");
			throw new CancelaGrabacionException();
		}catch (PasajeroException pnex){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoPasajero"),txtBuscarPax);
			throw new CancelaGrabacionException();
		}catch(CortesiaXCumpleaniosEmitidaException cc){
			DlgMessage.information(Messages.getString("WndCortesia.information.cortesiaCumpleaniosNoPermitida")+" "+itAnioCortesia.getValue()+".");
			throw new CancelaGrabacionException();
		} catch (Exception ex) {
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
			ex.printStackTrace();
			throw new CancelaGrabacionException();
		}
	}

	/**		
	 * @param tipoFormaPago	: TipoFormaPago de la cortesia
	 * @param lstPuntaje	: Opsional, lista con los puntos del paxfree, solo si es un canje por puntos si no sera null. 
	 * @throws Exception
	 */
	private void guardaCortesia(TipoFormaPago tipoFormaPago, List<PuntosPasajeroFrecuente> lstPuntaje) throws Exception{		
		try{
			cortesia = new Cortesia();
			Ruta ruta = new Ruta();
			ruta = cmbRuta.getSelectedItem().getValue();
								
			/* genera la venta  */
			VentaPasaje ventaPasaje = new VentaPasaje();
			Itinerario itinerario = new Itinerario();
			FormaPago formaPago = new FormaPago();
			Servicio servicio = new Servicio();
			TipoComprobante tipoComprobante = new TipoComprobante();
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			TipoDocumento tipoDocumento= new TipoDocumento();
			tipoDocumento=ServiceLocator.getTipoDocumentoManager().buscarPorId(pasajero.getTipoDocumento().getId().longValue());
			pasajero.setTipoDocumento(tipoDocumento);
			
			DetalleItinerario detalleItinerario= lstServicios.getSelectedItem().getValue();
						
			itinerario=detalleItinerario.getItinerario();
			servicio=itinerario.getServicio();
			
			//Valida el itinerario tiene tarifa
			if(detalleItinerario.getTarifa().doubleValue()==0.0)
				throw new ItinerarioException(ItinerarioException.TARIFA_IDA_CERO);
			
			if(servicio.getId().intValue()==Constantes.ID_SERVICIO_TEPSASUITE && Integer.valueOf(txtAsiento.getText())<=10)
				throw new NumeroAsientoFueraRangoException();
			if(servicio.getId().intValue()==Constantes.ID_SERVICIO_TEPSACAMASUITE && Integer.valueOf(txtAsiento.getText())<=10)
				throw new NumeroAsientoFueraRangoException();
			
			ventaPasaje.setNumeroAsiento(Integer.valueOf(txtAsiento.getText()));
			buscarPromocionPorTarifa(detalleItinerario, ventaPasaje);
			if(ventaPasaje.getTarifa()==null){
				ventaPasaje.setTarifa(detalleItinerario.getTarifa());
				ventaPasaje.setImportePagado(detalleItinerario.getTarifa());
			}
			
			
					
			/* Valida si se trara de servico SUITE, para recuperar la tarifa por asiento */
//			if(servicio.getId().intValue()==Constantes.ID_SERVICIO_TEPSASUITE){
//				List<Promocion>lstPromocion=ServiceLocator.getPromocionManager().buscarPorTarifa(Constantes.FORMAT_DATE.format(dbxFechaViaje.getValue()), ruta.getId().toString(), servicio.getId().toString());
//				if(lstPromocion.size()>0){
//					Promocion promocion=lstPromocion.get(0);
//					String[] asientos=promocion.getAsientos().split(",");
//					for(int i=0;i<asientos.length; i++){	
//						if(txtAsiento.getText().equals(asientos[i].toString())){
//							tarifa=promocion.getPorImporte();
//							break;
//						}
//					}
//				}
//			}
			
			formaPago.setId(Constantes.ID_FORPAG_CORTESIA);
			formaPago.setDenominacion("CORTESIA");
			tipoComprobante.setId(Constantes.ID_TIPCOM_BOLETA_VENTA);
			tipoMovimiento.setId(Constantes.ID_TIPMOV_EFECTIVO);
			
			ventaPasaje.setItinerario(itinerario);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setFormaPago(formaPago);
			ventaPasaje.setServicio(servicio);
			ventaPasaje.setTipoComprobante(tipoComprobante);
			ventaPasaje.setTipoMovimiento(tipoMovimiento);
			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			ventaPasaje.setPreferenciaAlimentaria((PreferenciaAlimentaria)cmbAlimentacion.getSelectedItem().getValue());
			ventaPasaje.setNumeroPiso(0);
			ventaPasaje.setNumeroControl("T00000");
			ventaPasaje.setSecuencial(0);
			ventaPasaje.setRecargo(.00);
			ventaPasaje.setDescuento(.00);
			ventaPasaje.setPenalidad(.00);
			ventaPasaje.setAcuenta(.00);
			ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_VENTA);
			ventaPasaje.setAgencia(getAgencia());
			ventaPasaje.setUsuario(getUsuario());
			ventaPasaje.setCanalVenta(canalVenta);
			ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
			ventaPasaje.setEsFechaAbierta(Constantes.FALSE_VALUE);
			ventaPasaje.setFechaCaducidad(new Date(new Date().getTime()+(Constantes.TIEMPO_CADUCA_BOLETO * Constantes.MILISEGUNDOS_X_DIA)));
			ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			ventaPasaje.setImportePagadoEfectivo(.00);
			ventaPasaje.setImportePagadoTarjeta(.00);
			ventaPasaje.setFechaLiquidacion(fechaLiquidacion); 
			ventaPasaje.setFechaPartida(dbxFechaViaje.getValue());
			ventaPasaje.setFechaLlegada(Constantes.FORMAT_DATE.parse(lblFechaLlegada.getValue()));
			ventaPasaje.setHoraPartida(lblHoraPartida.getValue());
			ventaPasaje.setHoraLllegada(lblHoraLlegada.getValue());
			ventaPasaje.setNumeroBoleto(txtNumeroBoleto.getText());
			ventaPasaje.setAgenciaPartida(((ItinerarioAgenciaPartida)cmbPuntoEmbarque.getSelectedItem().getValue()).getAgencia());
			ventaPasaje.setAgenciaLlegada(((ItinerarioAgenciaLlegada)cmbPuntoLlegada.getSelectedItem().getValue()).getAgencia());
			ventaPasaje.setUsuarioHardware(usuarioHardware);
			ventaPasaje.setObservaciones(txtObserveaciones.getText());
			ventaPasaje.setEsRemoto(false);
			
			UtilData.auditarRegistro(ventaPasaje, false, getUsuario(),Executions.getCurrent());			
			ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, false, true, true,true);
			
			
			/*Begin 27/10/2016 - jabanto*/
			List<VentaPasaje> listVentaPasaje= new ArrayList<>();
			VentaPasaje oventaPasaje=ServiceLocator.getVentaPasajesManager().buscarVentaById(ventaPasaje.getId());
			listVentaPasaje.add(oventaPasaje);
			WSFE.sendVenta(listVentaPasaje, wndCortesia, true, null);
			
			/*##End Begin 27/10/2016 - jabanto*/
			/*Implementacion para el nueno formato 01/02/2016 - jabanto */
//			boolean formato=UtilData.getFormatoImprecion(getAgencia().getId(), getTipocomprobante().getId(), getUsuarioHardware().getId());
//			File file=CreateDocument.crearBoleto(ventaPasaje,formato);
//			if(getUsuarioHardware().getPrintApplet().intValue()==Constantes.TRUE_VALUE){
//				String fileBoleto = Constantes.URL_FORMATOS_BOLETOS+Constantes.CLAVE_PAHT+ventaPasaje.getNumeroControl()+".txt";
//				Window win = (Window)Executions.createComponents("imprimir.zul", null, null);
//				win.setAttribute("formato", WndImprimir.FORMAT_BOLETO);
//				win.setAttribute("msg", "Imprimiendo boleto por "+ventaPasaje.getTipoFormaPago().getDenominacion()+"...");
//				win.setAttribute("urlDocumento", fileBoleto);
//				win.doPopup();	
//			}else{
//				/*Descarga el archivo para la impresion*/
//				Util.descargarArchivo(file);
//			}
			
					
			
			/* Guarda la cortesia */
			cortesia.setTipoFormaPago(tipoFormaPago);
			cortesia.setPasajero(pasajero);
			cortesia.setVentaPasaje(ventaPasaje);
			cortesia.setRuta(ruta);
			cortesia.setPorcentaje(100);
			cortesia.setFechaViaje(dbxFechaViaje.getValue() != null ? dbxFechaViaje.getValue() : null);
			cortesia.setAnioCumpleanios(itAnioCortesia.getValue());
			cortesia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			UtilData.auditarRegistro(cortesia, getUsuario(),Executions.getCurrent());
			ServiceLocator.getCortesiaManager().guardar(cortesia);
			textboxId.setText(cortesia.getId().toString());
			
			/* Si es una Cortesia por Puntos */
			if(tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)){
				/* Resta puntos */
				String iDsPuntPaxFree=""; 
				Integer tPuntosPaxfree=0; //acumula el puntaje necesario para el canje
				
				for (PuntosPasajeroFrecuente puntos: lstPuntaje){
					tPuntosPaxfree+=+puntos.getPuntosAcumulados();
					/*Valida que el puntaje de la ruta aun sea mayor al puntaje que se va acumulando en el for (tPuntosPaxfree)*/
					if(ruta.getPuntaje() >= tPuntosPaxfree){
						if(iDsPuntPaxFree.length()==0)
//							iDsVenta=puntos.getVentaPasaje().getId().toString();
							iDsPuntPaxFree=puntos.getId().toString();
						else
							iDsPuntPaxFree+=","+puntos.getId().toString();
//							iDsVenta+=","+puntos.getVentaPasaje().getId().toString();						
					}
					/*actualiza puntos Si el puntaje de la ruta es menor a la suma de los puntos necesarios para el canje(tPuntosPaxfree) */
					if(ruta.getPuntaje()<=tPuntosPaxfree){
						ServiceLocator.getPuntosPasajeroFrecuenteManager().actualizarPuntosCanjeados(iDsPuntPaxFree, getUsuario().getLogin(), ventaPasaje.getIpInsercion(),ventaPasaje.getId());
						break;
					}
				}
			}
			
		
			oTabLista.setDisabled(false);
			habilitaControles(false);						
			oTabbox.setSelectedIndex(0);
			/*Habilita/Desabilita controles del menu*/
			btnNuevo.setDisabled(false);
			btnBuscar.setDisabled(false);
			btnImprimir.setDisabled(false);
			btnExportar.setDisabled(false);
			btnRefrescar.setDisabled(false);
			btnModificar.setDisabled(true);
			btnCancelar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnEliminar.setDisabled(true);
			btnCerrar.setDisabled(false);
					
			DlgMessage.information("Registro grabado");
			
			limpiaControlesPasajero();
			Util.limpiarListbox(listboxLista);
			Util.limpiarListbox(lstServicios);
			Util.limpiarListbox(listViajesPax);
			
			TreeMap<String,Object> criteriosBusqueda= new TreeMap<String, Object>();
			criteriosBusqueda.put("id", cortesia.getId());
			listarCortesia(ServiceLocator.getCortesiaManager().buscarPorX(criteriosBusqueda, null));
			gbxViajesPax.setVisible(false);
		}catch(ItinerarioException inex){
			if(inex.getTipo().intValue()==ItinerarioException.TARIFA_IDA_CERO){
				DlgMessage.information(Messages.getString("WndVentaReserva.information.noTarifaItinerario"));
			}
		}catch(NumeroBoletoDuplicadoException nbdex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.numeroBoletoVendido"));
			imgRefreshBoleto.setVisible(true);
		}catch(NumeroAsientoFueraRangoException nafrex){
			liberarAsientos();
			txtAsiento.setText("");
			DlgMessage.information(Messages.getString("WndCortesia.information.asientoNoPermitido"), imgSelectAsiento);
		}catch(TiempoExpiracionBloqueoException tebex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.expiroTiempoBloqueoAsiento"));
			tiempoExpiracionBloqueo();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
		}
	}
	
	/**
	 * Buscamos las promociones que haran de tarifa en el caso del TEPSA SUITE o cualquier otro servicio.
	 * @param detalleItinerario	: Datos del itinerario a utilizar para realizar la busqueda de promocion por tarifa.
	 * @throws Exception 
	 */
	private void buscarPromocionPorTarifa(DetalleItinerario detalleItinerario,VentaPasaje ventaPasaje) throws Exception{
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
										if(asientos[m].equals(ventaPasaje.getNumeroAsiento().toString())){
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
			if(promo!=null){
				Integer idCanalVenta = getUsuarioHardware().getCanalVenta().getId();
				Integer idAgencia = getAgencia().getId();
				AplicarPromocion aplicarPromocion= new AplicarPromocion(detalleItinerario.getRuta().getId(), detalleItinerario.getItinerario().getServicio().getId(), 
						idAgencia, idCanalVenta, null, null, ventaPasaje.getNumeroAsiento().toString(),null, false, Constantes.ID_FORPAG_CORTESIA, 
						((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId(),null, 
						Util.StringtoDate(lblFechaPartida.getValue(), Constantes.DATE_FORMAT), 
						true,new Doublebox(0), new Doublebox(0), new Doublebox(0), null, new Label(), new Image(), new Textbox(),lblHoraPartida.getValue());	
				Promocion promocionAplicada= aplicarPromocion.executePromocion(promo.getId().toString(), false);
				
				if(promocionAplicada!=null){
					ventaPasaje.setTarifa(promocionAplicada.getPorImporte());
					ventaPasaje.setImportePagado(promocionAplicada.getPorImporte());
					ventaPasaje.setPromocion(promocionAplicada);
				}
			}
			
		} catch (Exception ex) {
			log.error(ex);
			throw new Exception(ex.getMessage());
		}
	}
	
	private void tiempoExpiracionBloqueo(){
		txtAsiento.setText("");
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
//		try {
//			Listitem itemList = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
//			Cortesia cortesia = itemList.getValue();
//			
//			if(cortesia.getTipoFormaPago().getId().equals(Constantes.ID_TIPFORPAG_PUNTOS))
//				if(cortesia.getRuta().getPuntaje()==null || cortesia.getRuta().getPuntaje()<=0)
//					throw new RutaSinPuntajeException();
//			
//			//Valida si la cortesia esta confirmada. 
//			if (!(ServiceLocator.getCortesiaManager().cortesiaConfirmada(cortesia.getVentaPasaje().getNumeroControl()))) {
//				//Anula cortesia
//				ServiceLocator.getCortesiaManager().inactivar(cortesia.getId());
//				
//				//Anula la reserva fecha habierta
//				TipoMovimiento tipoMovimiento= new TipoMovimiento();
//				VentaPasaje movimiento= ServiceLocator.getVentaPasajesManager().buscarPorId(cortesia.getVentaPasaje().getId());
//				UtilData.auditarRegistro(movimiento, true, getUsuario(), Executions.getCurrent());
//				tipoMovimiento.setId(Constantes.ID_TIPMOV_ANULACION);
//				movimiento.setTipoMovimiento(tipoMovimiento);
//				ServiceLocator.getVentaPasajesManager().anularMovimiento(movimiento);
//				
//				/* Si es por Puntos*/
//				if(cortesia.getTipoFormaPago().getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)){
//					//Buscar paxfree
//					PasajeroFrecuente paxFree=ServiceLocator.getPasajeroFrecuenteManager().buscarPaxFree(cortesia.getPasajero().getId(),1);
//					if(paxFree!=null){
//						/* Busca puntos utilizados*/
//						List<PuntosPasajeroFrecuente>lstResul=ServiceLocator.getPuntosPasajeroFrecuenteManager().buscarPuntosUtilizados
//								(paxFree.getId().toString(), Constantes.FORMAT_DATE.format(cortesia.getFechaInsercion()));
//						
//						Integer puntosRuta=cortesia.getRuta().getPuntaje();
//						Integer puntoConsumidos=0;
//						for(PuntosPasajeroFrecuente  puntosPasajeroFrecuente: lstResul){
//							puntoConsumidos+=+puntosPasajeroFrecuente.getPuntosAcumulados();
//							
//							//reactiva los puntos utilizados
//							if(puntoConsumidos.intValue()<=puntosRuta.intValue()){
//								puntosPasajeroFrecuente.setFechaCanje(null);
//								UtilData.auditarRegistro(puntosPasajeroFrecuente, true, getUsuario(), Executions.getCurrent());
//								ServiceLocator.getPuntosPasajeroFrecuenteManager().actualizar(puntosPasajeroFrecuente);
//							}
//							
//							//Termina instruccion.
//							if(puntoConsumidos.intValue()>=puntosRuta.intValue())
//								break;					
//						}
//					}
//				}
//				limpiaControlesPasajero();
//			} else {
//				throw new NoRemoverRegistroDelListBox();
//			}
//		}catch (RutaSinPuntajeException rsp){
//			DlgMessage.information(Messages.getString("WndCortesia.information.rutaSinPuntaje"));
//			throw new NoRemoverRegistroDelListBox();
//		} catch (NoRemoverRegistroDelListBox e) {
//			DlgMessage.information(Messages.getString("WndCortesia.information.noSePuedeAnularCortesia"));
//			throw new NoRemoverRegistroDelListBox();
//		}
	}

	@Override
	public void onPrint(int tab) throws Exception {
	}

	@Override
	public void onExport(int tab) throws Exception {
	}

	@Override
	public void onHelp() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		EdicionCortesia();
	}
	
	/**
	 * Carga Rutas, según la Ciudad Origen.
	 * 
	 * @throws Exception
	 */
	public void onChangeRutas() throws Exception {
		limpiarDatosItinerario();
		cmbRuta.getItems().clear();
						
		if (cmbCiudadOrigen.getSelectedItem().getValue() instanceof Localidad && cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago) {
			Integer Origen = ((Localidad) cmbCiudadOrigen.getSelectedItem().getValue()).getId();
			
			/*Si es porpuntos, carga rutas cuyo puntaje sea equivalente al puntaje del pasajero*/
			if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)){
				String puntos =lbPuntosPax.getValue().substring(lbPuntosPax.getValue().indexOf(":")+1,lbPuntosPax.getValue().toString().trim().length());
				UtilData.cargarRutas(cmbRuta, false, Origen,Integer.valueOf(puntos.trim()),true);
			}else{
				UtilData.cargarRutas(cmbRuta, false, Origen, null,true);
			}
		} else
			UtilData.cargarGenericData(cmbRuta, false);
		liberarAsientos();
		cmbRuta.setSelectedIndex(0);
	}

	/**
	 * Corresponde al evento onSelect del combo tipo Forma de Pago.
	 * @throws Exception 
	 */
	public void onSelectTipoFormaPago() throws Exception{
		try {
			cmbCiudadOrigen.setSelectedIndex(0);
			cmbRuta.getItems().clear();
			UtilData.cargarGenericData(cmbRuta, false);
			limpiarDatosItinerario();
			Util.limpiarListbox(listViajesPax);
			gbxViajesPax.setVisible(false);
			itAnioCortesia.setText("");
			itAnioCortesia.setReadonly(true);
			
			if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
				TipoFormaPago tipoFormaPago=cmbTipoFormaPago.getSelectedItem().getValue();

				if (pasajero == null)
					throw new PasajeroException();
				else if (pasajero.getPasajeroFrecuente() == null)
					throw new PaxFreeNoRegistradoException();
				else if (pasajero.getPasajeroFrecuente().getEstado().equals(Constantes.FALSE_VALUE))
					throw new PaxFreeInactivoException();		
				
				//Carga lista de pasajes emitidos por cumpleanios emitidos al pasajero.
				if (tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS)) {
					ArrayList<Cortesia> lstCortesia = ServiceLocator.getCortesiaManager().buscarBoletosPaxFreXTipoFormaPago(pasajero.getId(), tipoFormaPago.getId());
					gbxViajesPax.setVisible(true);
					cpViajesPax.setLabel("Historial de viajes por cumpleaños.");
					cpViajesPax.setStyle("color:red ");
					lbCantViajesPax.setValue(String.valueOf(lstCortesia.size()));
					lbCantViajesPax.setStyle("color:red");
					cargaListaViajesPax(lstCortesia);
					itAnioCortesia.setReadonly(false);
					habilitaControleCortesia(false);
				}else if(tipoFormaPago.getId().equals(Constantes.ID_TIPFORPAG_PUNTOS)){
					ArrayList<Cortesia> lstCortesia = ServiceLocator.getCortesiaManager().buscarBoletosPaxFreXTipoFormaPago(pasajero.getId(), tipoFormaPago.getId());
					gbxViajesPax.setVisible(true);
					cpViajesPax.setLabel("Historial de viajes canjeados por puntos.");
					cpViajesPax.setStyle("color:red ");
					lbCantViajesPax.setValue(String.valueOf(lstCortesia.size()));
					lbCantViajesPax.setStyle("color:red");
					cargaListaViajesPax(lstCortesia);
					habilitaControleCortesia(false);
					itAnioCortesia.setReadonly(true);
				}
				
				
			}else{
				habilitaControleCortesia(true);
				cmbTipoFormaPago.setDisabled(false);
			}
		} catch (PasajeroException pnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoPasajero"));
			cmbTipoFormaPago.setSelectedIndex(0);
			txtBuscarPax.setFocus(true);
		} catch (PaxFreeNoRegistradoException pfnrex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.paxFreeNoRegistrado"));
			cmbTipoFormaPago.setSelectedIndex(0);
			txtBuscarPax.setFocus(true);
		} catch (PaxFreeInactivoException pfiex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.paxFreeInactivo"));
			cmbTipoFormaPago.setSelectedIndex(0);
			txtBuscarPax.setFocus(true);
		}
	}
		
	
	/**
	 * Realiza la busqueda, según los parametros elejidos por el usuario.
	 */
	public void buscarPasajero() {
		try {
			if (txtBuscarPax.getText().trim().isEmpty())
				throw new PasajeroException();

			TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
			if (rdPorApellidos.isSelected())
				criterioBusqueda.put("apellidoPaterno", txtBuscarPax.getText().trim().toUpperCase()+ "%");
			else if (rdPorDocumento.isSelected())
				criterioBusqueda.put("numeroDocumento", txtBuscarPax.getText().trim().toUpperCase()+ "%");
			List<String> criteriosOrdenar = new ArrayList<String>();
			criteriosOrdenar.add("apellidoPaterno");
			criteriosOrdenar.add("apellidoMaterno");
			criteriosOrdenar.add("nombre");
			
			ArrayList<Pasajero> lstPasajeros = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda,criteriosOrdenar);
			listarPasajeros(lstPasajeros);

		} catch (PasajeroException pnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoCriterioBusqueda"));
			txtBuscarPax.setFocus(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Lista los pasajeros encontrados en la busqueda
	 * 
	 * @param lstPasajero
	 * @throws Exception
	 */
	private void listarPasajeros(ArrayList<Pasajero> lstPasajero)throws Exception {
		lisboxPasajeros.getItems().clear();
		habilitaControleCortesia(true);

		Listitem item = null;
		Listcell cell = null;
		if (lstPasajero.size() == 1) {
			/* cuando el resultado de la busqueda solo un registro */
			pasajero = lstPasajero.get(0);

			/* Verifica si es paxfre */
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("pasajero", pasajero);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<PasajeroFrecuente> list = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);
						
			for (PasajeroFrecuente frecuente : list) {
				pasajero.setPasajeroFrecuente(frecuente);
			}
						
			muestraPax(pasajero);
			grInfoPax.setVisible(true);
			lisboxPasajeros.setVisible(false);
						
		} else if (lstPasajero.size() > 1) {
			grInfoPax.setVisible(false);
			/* cuando el resultado de la busqueda es mas de un registro */
			for (Pasajero pasajero : lstPasajero) {
				item = new Listitem();
				item.setValue(pasajero);
				cell = new Listcell(pasajero.getTipoDocumento().getDenominacion());
				item.appendChild(cell);
				cell = new Listcell(pasajero.getNumeroDocumento());
				item.appendChild(cell);
				cell = new Listcell(pasajero.toString());
				item.appendChild(cell);

				item.setValue(pasajero);
				lisboxPasajeros.appendChild(item);
			}
			lisboxPasajeros.setVisible(true);

			lisboxPasajeros.addEventListener(Events.ON_DOUBLE_CLICK,
					new EventListener<Event>() {
						public void onEvent(Event event) {
							try {
								pasajero = ServiceLocator.getPasajeroManager().buscarPorId(((Pasajero) lisboxPasajeros.getSelectedItem().getValue()).getId());
								/* Verifica si es paxfre */
								TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
								criteriosBusqueda.put("pasajero", pasajero);
								criteriosBusqueda.put("estadoRegistro",Constantes.VALUE_ACTIVO);
								List<PasajeroFrecuente> list = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);
								for (PasajeroFrecuente frecuente : list) {
									pasajero.setPasajeroFrecuente(frecuente);
								}
								habilitaControleCortesia(false);
								muestraPax(pasajero);
								grInfoPax.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
							lisboxPasajeros.setVisible(false);
							txtBuscarPax.setText("");
							itAnioCortesia.setDisabled(true);
						}
					});
		} else {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajerosEncontrados"));
			limpiaControlesPasajero();
//			activarBtnEditar(false);
			pasajero = null;
		}
		txtBuscarPax.setText("");
		itAnioCortesia.setDisabled(true);
	}

	/**
	 * Muestra datos del Pasajero
	 * 
	 * @param pasajero
	 *            : clase que contiene datos del pasajero a mostrar al usuario.
	 * @throws Exception
	 */
	private void muestraPax(Pasajero pasajero) throws Exception {
		try {
			limpiarDatosItinerario();
			gbxViajesPax.setVisible(false);
			cmbTipoFormaPago.setDisabled(true);
			
			if (pasajero.getIndeseable().equals(Constantes.PASAJERO_INDESEABLE))
				throw new PasajeroIndeseableException();

			txtidPasajero.setText(pasajero.getId().toString());
			lbNumeroDocuemnto.setValue(pasajero.getNumeroDocumento());
			lbPasajero.setValue(pasajero.getApellidoPaterno() + " "+ pasajero.getApellidoMaterno() + ", "+ pasajero.getNombre());
			//lbFechaNacimiento.setValue(pasajero.getFechaNacimiento()!=null?Constantes.FORMAT_DATE.format(pasajero.getFechaNacimiento()):"");
			lbUbigeo.setValue(ServiceLocator.getUbigeoManager().ubicacionGeografica(pasajero.getUbigeo()));
			lbFechaNacimiento.setValue(pasajero.getFechaNacimiento() != null ? pasajero.getFechaNacimiento() : "");
			/* Validación PAXFREE */
			if (pasajero.getPasajeroFrecuente() != null)
				if (pasajero.getPasajeroFrecuente().getEstado().equals(Constantes.TRUE_VALUE)){
					
					Integer puntosPax=0;
					/*Busca puntaje del Paxfree*/
					ArrayList<PuntosPasajeroFrecuente> lstPuntaje=new ArrayList<PuntosPasajeroFrecuente>(ServiceLocator.getPuntosPasajeroFrecuenteManager().buscarPuntosDisponibles(pasajero.getPasajeroFrecuente().getId()));
					if(lstPuntaje.size()>0)
						puntosPax=((PuntosPasajeroFrecuente)lstPuntaje.get(0)).getTotalPuntaje();
	
					lbPaxfri.setValue("PAX FREE: ACTIVO");
					lbPuntosPax.setValue("PUNTOS: "+puntosPax);
					cmbTipoFormaPago.setDisabled(false);
				}else{
					lbPaxfri.setValue("PAX FREE: INACTIVO");
					lbPuntosPax.setValue("PUNTOS: 0");
				}
			else{
				lbPaxfri.setValue("");
				lbPuntosPax.setValue("");
			}
			
			cmbTipoFormaPago.setFocus(true);
			cmbTipoFormaPago.select();
			
		} catch (PasajeroIndeseableException pxidex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.pasajeroIndeseable"));
			txtBuscarPax.setFocus(true);
		}
	}

	/**
	 * Carga cortecias emitidas, según el resultado de la busqueda.
	 * 
	 * @param lstCortecia
	 *            : Array con las cortecias a mostrar en el Listbox
	 * @throws Exception 
	 */
	private void listarCortesia(List<Cortesia> lstcortesia) throws Exception {
		
		Util.limpiarListbox(listboxLista);
		Listitem item = null;
		Listcell cell = null;
		int x=0;
		for (Cortesia cortesia : lstcortesia) {
			VentaPasaje ventaPasaje=cortesia.getVentaPasaje();
			TipoComprobante tipoComprobante=ventaPasaje.getTipoComprobante();
			if(ventaPasaje.getTipoComprobante()==null || ventaPasaje.getTipoComprobante().getDenominacion()==null )
				tipoComprobante=ServiceLocator.getTipoComprobanteManager().buscarPorId(tipoComprobante.getId().longValue());
			
			x++;
			item = new Listitem();
			cell = new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell = new Listcell(tipoComprobante.getDenominacion());
			item.appendChild(cell);
			cell= new Listcell(ventaPasaje.getNumeroBoleto());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getPasajero().getNombresApellidos());
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getRuta().toString());
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getServicio().toString());
			item.appendChild(cell);
			cell = new Listcell(Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getHoraPartida());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getNumeroAsiento().toString());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(ventaPasaje.getTipoFormaPago().getDenominacion());
			item.appendChild(cell);
			
			item.setValue(cortesia);
			listboxLista.appendChild(item);
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void EdicionCortesia() throws Exception {
		Util.limpiarListbox(listViajesPax);
		gbxViajesPax.setVisible(false);
		lbCantViajesPax.setValue("");
		lbPuntosPax.setValue("PUNTOS : 0");
		limpiarDatosItinerario();
		habilitaControleCortesia(true);
		
		if (listboxLista.getSelectedItem().getValue() instanceof Cortesia) {
			cortesia= new Cortesia();
			cortesia=ServiceLocator.getCortesiaManager().buscarPorId(((Cortesia)listboxLista.getSelectedItem().getValue()).getId().longValue());
			VentaPasaje ventaPasaje=cortesia.getVentaPasaje();
			pasajero = cortesia.getPasajero();

			/* Datos del Pasajero */
			txtidPasajero.setText(pasajero.getId().toString());
			lbNumeroDocuemnto.setValue(pasajero.getNumeroDocumento());
			lbPasajero.setValue(pasajero.getNombresApellidos());
			lbFechaNacimiento.setValue(pasajero.getFechaNacimiento()!=null?pasajero.getFechaNacimiento():"");
			lbUbigeo.setValue(ServiceLocator.getUbigeoManager().ubicacionGeografica(pasajero.getUbigeo()));
			/* Verifica si es paxfre */
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
			criteriosBusqueda.put("pasajero", pasajero);
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<PasajeroFrecuente> list = ServiceLocator.getPasajeroFrecuenteManager().buscarPorX(criteriosBusqueda, null);
			if (list.size() > 0)
				pasajero.setPasajeroFrecuente(list.get(0));
			if (pasajero.getPasajeroFrecuente() != null)
				if (pasajero.getPasajeroFrecuente().getEstado().equals(Constantes.TRUE_VALUE))
					lbPaxfri.setValue("PAX FREE: ACTIVO");
				else
					lbPaxfri.setValue("PAX FREE: INACTIVO");
			else
				lbPaxfri.setValue("");

			/* Datos de la cortesia */
			txtNumeroBoleto.setValue(ventaPasaje.getNumeroBoleto());
			Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, ventaPasaje.getTipoFormaPago().getId());
			String fecha = Util.DatetoString(ventaPasaje.getFechaPartida(), "yyyyMMdd");
			dbxFechaViaje.setConstraint("after "+fecha);
			dbxFechaViaje.setValue(ventaPasaje.getFechaPartida());
			Util.seleccionarValorItemCombo(Localidad.class, cmbCiudadOrigen,ventaPasaje.getRuta().getLocalidadOrigen().getId());
			UtilData.cargarRutas(cmbRuta, false, ((Localidad)cmbCiudadOrigen.getSelectedItem().getValue()).getId(), null);
			Util.seleccionarValorItemCombo(Ruta.class, cmbRuta, ventaPasaje.getRuta().getId());
			//bdbxServicios.setValue(ventaPasaje.getServicio().getDenominacion()+" ;"+ventaPasaje.getHoraPartida());
			DetalleItinerario detItinerario= new  DetalleItinerario();
			detItinerario.setAgenciaPartida(ventaPasaje.getAgenciaPartida());
			detItinerario.setAgenciaLlegada(ventaPasaje.getAgenciaLlegada());
			detItinerario.setItinerario(ventaPasaje.getItinerario());
			detItinerario.setFechaPartida(ventaPasaje.getFechaPartida());
			detItinerario.setHoraPartida(ventaPasaje.getHoraPartida());
			detItinerario.setFechaLlegada(ventaPasaje.getFechaLlegada());
			detItinerario.setHoraLlegada(ventaPasaje.getHoraLllegada());
			onSelectServicio(detItinerario);
			txtAsiento.setText(ventaPasaje.getNumeroAsiento().toString());
			Util.seleccionarValorItemCombo(PreferenciaAlimentaria.class, cmbAlimentacion, ventaPasaje.getPreferenciaAlimentaria().getId());
			itAnioCortesia.setText(cortesia.getAnioCumpleanios() != null ? String.valueOf(cortesia.getAnioCumpleanios()) : "");
			txtObserveaciones.setText(ventaPasaje.getObservaciones());
			
			activarBtnBuscar(false);
			btnGuardar.setDisabled(true);
			disabledImgBusqAsiento(true);
			disabledImgRefresh(true);
	
		}
	}

	/**
	 * Lista los viajes del PAXFREE
	 * @param lst : ArrayList que contiene los viajes a cargar.
	 */
	private void cargaListaViajesPax(ArrayList<Cortesia> lst) {
		Listitem item = null;
		Listcell cell = null;
		int i = 0;
		listViajesPax.getItems().clear();//setRows(0);
		
		for (Cortesia cortesia : lst) {
			i++;
			VentaPasaje pasaje = cortesia.getVentaPasaje();
			Usuario usuario = cortesia.getVentaPasaje().getUsuario();

			String ruta = cortesia.getRuta().getOrigen() + " - "+ cortesia.getRuta().getDestino();
			String repVenta = usuario.toString();//.getApellidoPaterno() + ","+ usuario.getApellidoMaterno() + ", " + usuario.getNombre();

			item = new Listitem();
			cell = new Listcell(String.valueOf(i));
			item.appendChild(cell);
			cell = new Listcell(Constantes.FORMAT_DATE.format(cortesia.getFechaInsercion()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			cell = new Listcell(pasaje.getNumeroBoleto() != null ? pasaje.getNumeroBoleto(): "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			
			/*	Validando si es un boleto por cumpleanios	*/
			cell = null;
			if(cortesia.getAnioCumpleanios()==null){
				cell = new Listcell("");
				lstHeaderAnio.setVisible(false);
			}else{
				cell = new Listcell(cortesia.getAnioCumpleanios().toString());
				lstHeaderAnio.setVisible(true);
			}
			item.appendChild(cell);
			
			cell = new Listcell(ruta);
			item.appendChild(cell);
			cell = new Listcell(pasaje.getServicio().getDenominacion() != null ? pasaje.getServicio().getDenominacion() : "");
			item.appendChild(cell);
			cell = new Listcell(pasaje.getFechaPartida() != null ? Constantes.FORMAT_DATE.format(pasaje.getFechaPartida()) : "");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(repVenta);
			item.appendChild(cell);

			item.setValue(cortesia);
			listViajesPax.appendChild(item);
			
			if(listViajesPax.getItems().size()>5)
				listViajesPax.setRows(5);
		}
	}

	/**
	 * Habilita o desabilita controles de la cortesia y limpia los controles si el estado es true
	 * @param estado
	 */
	private void habilitaControleCortesia(boolean estado) {
		cmbCiudadOrigen.setDisabled(estado);
		cmbRuta.setDisabled(estado);
		cmbTipoFormaPago.setDisabled(estado);
		dbxFechaViaje.setDisabled(estado);
		itAnioCortesia.setDisabled(estado);
		bdbxServicios.setDisabled(estado);
		cmbPuntoEmbarque.setDisabled(estado);
		cmbPuntoLlegada.setDisabled(estado);
		txtAsiento.setReadonly(true);	
		cmbAlimentacion.setDisabled(estado);
		txtObserveaciones.setDisabled(estado);
		
		if (estado == true) {
			cmbCiudadOrigen.setSelectedIndex(0);
			Util.limpiarCombobox(cmbRuta);
			UtilData.cargarGenericData(cmbRuta, false);
			cmbRuta.setSelectedIndex(0);
			Util.limpiarListbox(lstServicios);
			txtAsiento.setText("");
			cmbPuntoEmbarque.setSelectedIndex(0);
			cmbPuntoLlegada.setSelectedIndex(0);
			cmbTipoFormaPago.setSelectedIndex(0);
			cmbAlimentacion.setSelectedIndex(0);
			dbxFechaViaje.setText("");
			itAnioCortesia.setText("");
			txtObserveaciones.setText("");
		}
	}

	/**
	 * Activa o desactiva el botón Buscar pasajero
	 * 
	 * @param activar
	 */
	private void activarBtnBuscar(boolean activar) {
		if (activar && accesoConsultar()) {
			txtBuscarPax.setDisabled(false);
			btnBuscarPax.setDisabled(false);
			btnBuscarPax.setImage("/resources/mp_buscarEnabled.png");
			btnBuscarPax.setStyle("cursor:pointer");
		} else {
			txtBuscarPax.setDisabled(true);
			btnBuscarPax.setDisabled(true);
			btnBuscarPax.setImage("/resources/mp_buscarDisabled.png");
			btnBuscarPax.setStyle("cursor:default");
		}
	}

	/**
	 * Limpia Controles del Pasajero
	 * @throws Exception
	 */
	private void limpiaControlesPasajero() throws Exception {
		txtidPasajero.setText("");
		lbNumeroDocuemnto.setValue("");
		lbPasajero.setValue("");
		lbFechaNacimiento.setValue("");
		lbUbigeo.setValue("");
		lbPaxfri.setValue("");
		lbPuntosPax.setValue("");
		
		/*Desbloquea el asiento*/
		if(!(txtAsiento.getText().trim().isEmpty())){
			DetalleItinerario detalleItinerario= ((DetalleItinerario)lstServicios.getSelectedItem().getValue());
			if(detalleItinerario!=null && detalleItinerario.getItinerario()!=null && detalleItinerario.getItinerario().getId()!=null)
				ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(usuarioHardware.getId(), detalleItinerario.getItinerario().getId(), Integer.valueOf(txtAsiento.getText().trim()));
		}
	}

	private void limpiarDatosItinerario(){
		cmbPuntoEmbarque.getItems().clear();
		cmbPuntoLlegada.getItems().clear();
		bdbxServicios.setText("");
		Util.limpiarListbox(lstServicios);
		
		disabledImgBusqAsiento(true);
		UtilData.cargarGenericData(cmbPuntoEmbarque, false);
		UtilData.cargarGenericData(cmbPuntoLlegada, false);
		lblFechaPartida.setValue("");
		lblFechaLlegada.setValue("");
		lblHoraPartida.setValue("");
		lblHoraLlegada.setValue("");
		txtAsiento.setText("");
		itAnioCortesia.setText("");
	}
	/**
	 * Carga los tipos de pago que guardan relación con la forma de fago cortesia.
	 * @throws Exception 
	 */
	private void cargarTipoFormaPago() throws Exception{
		TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		
		FormaPago formaPago= new FormaPago();
		formaPago.setId(Constantes.ID_FORPAG_CORTESIA);
		criterioBusqueda.put("formaPago", formaPago);
		criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		
		List<TipoFormaPago> lstResut= ServiceLocator.getTipoFormaPagoManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
		
		UtilData.cargarGenericData(cmbTipoFormaPago, false);
		for (TipoFormaPago tipoFormaPago: lstResut){
//			if(!(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CORTESIA)){
			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CUMPLEANIOS || tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_PUNTOS){
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(tipoFormaPago.getDenominacion());
				oComboitem.setValue(tipoFormaPago);
				cmbTipoFormaPago.appendChild(oComboitem);
			}
						
		}
	}
	
	/**
	 * Valida si la fecha de viaje esta dentro de lo permitido
	 * @param fechaViaje : fecha de viaje del pasajero
	 * @return true si la fecha de vieje es correcta, false lo contraria
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private boolean validadFechaCumpleanios(Date fechaViaje) throws Exception{
		/*Valida que este dentro del periodo permitido(segun parametro configurable). Ejemplo 2 meses antes o despues del cumpleaños */
		Long rangoPermitido = (Constantes.MILISEGUNDOS_X_DIA * Constantes.RANGO_CANJE_CUMPLEANIOS);
		Date fCumpleanios = new Date();
		fCumpleanios = Constantes.FORMAT_DATE.parse(pasajero.getFechaNacimiento());
		fCumpleanios.setYear(Constantes.FORMAT_YEAR.parse(itAnioCortesia.getValue().toString()).getYear());
		Date fActual = (Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
		
		// El canje es despues de la fecha de cumpleaños.
		if (fCumpleanios.getTime() > fActual.getTime()) {
			Long rangoActual = fCumpleanios.getTime()- fActual.getTime();
			if (rangoActual > rangoPermitido)
				return false;
			
			//Valida que la fecha de viaje selecionada este dentro del periodo permitido
			if(fechaViaje!=null){
				rangoActual = fechaViaje.getTime()-fActual.getTime();
				if(rangoActual > rangoPermitido)
					return false;
			}
			
		} else {//El canje es antes de la fecha de cumpleaños.
			Long rangoActual = fActual.getTime()-fCumpleanios.getTime();
			if (rangoActual > rangoPermitido)
				return false;
			
			//Valida que la fecha de viaje selecionada este dentro del periodo permitido
			if(fechaViaje!=null){				
				rangoActual = fechaViaje.getTime()-fActual.getTime();
				if(rangoActual > rangoPermitido)
					return false;
			}
		}
		return true;
	}
	
	
	
	
	public void onSelectServicio(DetalleItinerario  detalleItinerario){
		bdbxServicios.setText(detalleItinerario.getItinerario().getServicio().getDenominacion()+" ; "+detalleItinerario.getHoraPartida());
		bdbxServicios.close();
		 
		lblFechaPartida.setValue(Constantes.FORMAT_DATE.format(dbxFechaViaje.getValue()));
		lblFechaLlegada.setValue(Constantes.FORMAT_DATE.format(detalleItinerario.getFechaLlegada()));
		onLoadPuntoEmbarque(detalleItinerario);
		onLoadPuntoDesembarque(detalleItinerario);
		disabledImgBusqAsiento(false);
		txtAsiento.setText("");
		liberarAsientos();
	}
	
	public void cargarPuntosEmbarque(){
		
		
	}
	
	public void cargarServicios() throws Exception{
		try{
			limpiarDatosItinerario();
			
			if(dbxFechaViaje.getValue()==null)
				throw new FechaViajeNoValidaException();
			
			if(cmbRuta.getSelectedItem().getValue() instanceof Ruta){
				String fechaPartida=Constantes.FORMAT_DATE.format(dbxFechaViaje.getValue());
				String origen=((Ruta)cmbRuta.getSelectedItem().getValue()).getOrigen();
				String destino=((Ruta)cmbRuta.getSelectedItem().getValue()).getDestino();
			
				List<DetalleItinerario> lstItinerarios = ServiceLocator.getItinerarioManager().buscarItinerarios(fechaPartida, origen, destino);
				if(lstItinerarios.size()>0){
					Listitem item = null;
					Listcell cell = null;
					/*	Llenando el listbox con los itinerarios encontrados	*/
					for(DetalleItinerario detalleItinerario : lstItinerarios){
						if(detalleItinerario.getItinerario().getTipoItinerario().getId().equals(Constantes.ID_TIPITI_REGULAR)){
							item = new Listitem();
							cell = new Listcell(detalleItinerario.getItinerario().getServicio().getDenominacion());
							item.appendChild(cell);
							cell = new Listcell(detalleItinerario.getHoraPartida());
							cell.setStyle("font-size:11px !important");
							item.appendChild(cell);
							
							item.setValue(detalleItinerario);
							lstServicios.appendChild(item);
						}
					}
				}
				liberarAsientos();
			}
		}catch (FechaViajeNoValidaException fve){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoFechaViaje"),dbxFechaViaje);
		}	
	}
	
	/**
	 * Cargamos los puntos de embarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de embarque.
	 * @throws Exception
	 */
	private void onLoadPuntoEmbarque(DetalleItinerario detItinerario){
		try{
			cmbPuntoEmbarque.getItems().clear();
			
			ArrayList<ItinerarioAgenciaPartida> arrayItiAgePartida = new ArrayList<ItinerarioAgenciaPartida>();
			/*	Si la agencia de partida del itinerario es la misma a la agencia de la ruta seleccionada	*/
//			arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
			if(detItinerario.getItinerario().getAgenciaPartida().getId().intValue()==detItinerario.getAgenciaPartida().getId().intValue())
				arrayItiAgePartida = ServiceLocator.getItinerarioManager().buscarAgenciasPartida(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
			else{
				ItinerarioAgenciaPartida itiAgePartida = new ItinerarioAgenciaPartida();
				Agencia agencia = new Agencia();
				agencia.setId(detItinerario.getAgenciaPartida().getId());
				agencia.setDenominacion(detItinerario.getAgenciaPartida().getDenominacion());
				itiAgePartida.setAgencia(agencia);
//				itiAgePartida.setHoraPartida(detItinerario.getAgenciaPartida().getHoraPartida());
				itiAgePartida.setHoraPartida(detItinerario.getHoraPartida());
				arrayItiAgePartida.add(itiAgePartida);
			}
			UtilData.cargarGenericData(cmbPuntoEmbarque, false);
			/*	Cargamos los puntos de embarque	*/
			for(ItinerarioAgenciaPartida itiAgePartida : arrayItiAgePartida){
				Comboitem item = new Comboitem(itiAgePartida.getAgencia().getDenominacion());
				item.setValue(itiAgePartida);
				cmbPuntoEmbarque.appendChild(item);
				if(arrayItiAgePartida.size()==1){
					cmbPuntoEmbarque.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}else if(detItinerario.getAgenciaPartida().getId().intValue()==itiAgePartida.getAgencia().getId().intValue()){
					cmbPuntoEmbarque.setSelectedItem(item);
					lblHoraPartida.setValue(itiAgePartida.getHoraPartida());
				}
			}				
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}
	
	/**
	 * Cargamos los puntos de desembarque.
	 * @param detItinerario	: Itinerario del cual deseamos cargar los puntos de desembarque.
	 * @throws Exception
	 */
	private void onLoadPuntoDesembarque(DetalleItinerario detItinerario) {
		try{
			cmbPuntoLlegada.getItems().clear();
			
			ArrayList<ItinerarioAgenciaLlegada> arrayItiAgeLlegada = new ArrayList<ItinerarioAgenciaLlegada>();
			/*	Si la agencia de llegada del itinerario es la misma a la agencia de llegada de la ruta seleccionada	*/
//			arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
			
			if(detItinerario.getItinerario().getAgenciaLlegada().getId().intValue()==detItinerario.getAgenciaLlegada().getId().intValue())
				arrayItiAgeLlegada = ServiceLocator.getItinerarioManager().buscarAgenciasLlegada(detItinerario.getItinerario().getId(), Constantes.VALUE_ACTIVO);
			else{
				ItinerarioAgenciaLlegada itiAgeLlegada = new ItinerarioAgenciaLlegada();
				Agencia agencia = new Agencia();
				agencia.setId(detItinerario.getAgenciaLlegada().getId());
				agencia.setDenominacion(detItinerario.getAgenciaLlegada().getDenominacion());
				itiAgeLlegada.setAgencia(agencia);
				itiAgeLlegada.setHoraLlegada(detItinerario.getAgenciaLlegada().getHoraPartida());
				arrayItiAgeLlegada.add(itiAgeLlegada);
			}
			
			UtilData.cargarGenericData(cmbPuntoLlegada, false);
			for(ItinerarioAgenciaLlegada itiAgeLlegada : arrayItiAgeLlegada){
				Comboitem item = new Comboitem(itiAgeLlegada.getAgencia().getDenominacion());
                item.setValue(itiAgeLlegada);
                cmbPuntoLlegada.appendChild(item);
				if(arrayItiAgeLlegada.size()==1){
					cmbPuntoLlegada.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}else if(detItinerario.getAgenciaLlegada().getId().intValue()==itiAgeLlegada.getAgencia().getId().intValue()){
					cmbPuntoLlegada.setSelectedItem(item);
					lblHoraLlegada.setValue(itiAgeLlegada.getHoraLlegada());
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
		}
	}	
	
	/**
	 * Muestra la hora de embarque segun el punto de embarque seleccionado
	 */
	public void onSelectPtoEmbarque(){
		if(cmbPuntoEmbarque.getSelectedItem().getValue() instanceof ItinerarioAgenciaPartida)
			lblHoraPartida.setValue(((ItinerarioAgenciaPartida)cmbPuntoEmbarque.getSelectedItem().getValue()).getHoraPartida());
		else
			lblHoraPartida.setValue("");
	}
	
	/**
	 * Muestrala hora de llegada segun el punto de desembarque seleccionado.
	 */
	public void onSelectPtoDesembarque(){
		if(cmbPuntoLlegada.getSelectedItem().getValue() instanceof ItinerarioAgenciaLlegada)
			lblHoraLlegada.setValue(((ItinerarioAgenciaLlegada)cmbPuntoLlegada.getSelectedItem().getValue()).getHoraLlegada());
		else
			lblHoraLlegada.setValue("");
	}
	
	/**
	 * Realiza la busqueda del correlativo para el boleto a emitir.
	 * @throws Exception 
	 */
	private void onLoadEspecieValorada() throws Exception{
//		txtNumeroBoleto.setValue(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETO_VIAJE, usuarioHardware.getId()));
		EspecieValorada especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_BOLETA_VENTA, getAgencia(), false);
		txtNumeroBoleto.setText(especieValorada.toString());
	}
	
	private void disabledImgBusqAsiento(boolean estado){
		if(estado){
			imgSelectAsiento.setSrc(imgDisabledBusq);
			imgSelectAsiento.setStyle("cursor:default");
			imgSelectAsiento.setTooltiptext("");
		}else{
			imgSelectAsiento.setSrc(imgEnabledBusq);
			imgSelectAsiento.setStyle("cursor:pointer");
			imgSelectAsiento.setTooltiptext("Haga click aqui para seleccionar un Asiento.");
		}
	}
	
	private void disabledImgRefresh(boolean estado){
		if(estado){
			imgRefreshBoleto.setSrc(imgDisabledRefresh);
			imgRefreshBoleto.setStyle("cursor:default");
			imgRefreshBoleto.setTooltiptext("");
		}else{
			imgRefreshBoleto.setSrc(imgEnabledRefresh);
			imgRefreshBoleto.setStyle("cursor:pointer");
			imgRefreshBoleto.setTooltiptext("Haga click aqui para refrescar el Número de Boleto.");
		}
	}
	
	/**
	 * Permite liberar los asientos cuando se cambia de pestaña dentro de la venta
	 */
	public void liberarAsientos(){
		try{
			ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsientoByUsuarioHardware(usuarioHardware.getId());
			txtAsiento.setText(null);
//			lbxAsientos.getItems().clear();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
	
}
