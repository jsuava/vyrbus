package com.cystesoft.vyrbus.view.ctrl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
//import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import pe.gob.mtc.wshr.ResultIdentidad;

import com.cystesoft.vyrbus.model.bean.AutorizadorCortesia;
import com.cystesoft.vyrbus.model.bean.CanalVenta;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.Cortesia;
import com.cystesoft.vyrbus.model.bean.EstadoCivil;
import com.cystesoft.vyrbus.model.bean.FormaPago;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.MotivoCortesia;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Reniec;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.TipoFormaPago;
import com.cystesoft.vyrbus.model.bean.TipoMovimiento;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.service.exceptions.ApellidoMaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.ApellidoPaternoNullException;
import com.cystesoft.vyrbus.service.exceptions.AutorizadorNullException;
import com.cystesoft.vyrbus.service.exceptions.CancelaGrabacionException;
import com.cystesoft.vyrbus.service.exceptions.DocumentoPaxDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.FechaCaducidadNullException;
import com.cystesoft.vyrbus.service.exceptions.MailIncorectoException;
import com.cystesoft.vyrbus.service.exceptions.MotivoCorteciaNullException;
import com.cystesoft.vyrbus.service.exceptions.NoRemoverRegistroDelListBox;
import com.cystesoft.vyrbus.service.exceptions.NombresNullException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoIncorrectoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroException;
import com.cystesoft.vyrbus.service.exceptions.PasajeroIndeseableException;
import com.cystesoft.vyrbus.service.exceptions.PorcentajeDescuentoNullException;
import com.cystesoft.vyrbus.service.exceptions.RutaNullException;
import com.cystesoft.vyrbus.service.exceptions.RutaSinPuntajeException;
import com.cystesoft.vyrbus.service.exceptions.SexoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoException;
import com.cystesoft.vyrbus.service.exceptions.TipoFormaPagoNullException;
import com.cystesoft.vyrbus.service.exceptions.UbigeoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndFiltrarParametros;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * 
 * @author JABANTO
 * 
 */
public class WndCortesia extends WndOpcionesMantenimiento {
	private static final long serialVersionUID = 5027089313223381893L;

	private Groupbox gbxDatosCortesia;
	private Combobox cmbMotivoCortesia;
//	private Datebox dbFechaInicio;
	private Datebox dbFechaCaducidad;
	private Combobox cmbRuta;
	private Intbox itProcentejeDescuento;
	private Combobox cmbAutorizadorCortesia;
	private Radio rdPorDocumento;
	private Radio rdPorApellidos;
	private Textbox txtBuscarPax;
	private Listbox lisboxPasajeros;
	private Textbox txtidPasajero;
	private Label lbPasajero;
//	private Datebox dbxFechaViaje;
	private Combobox cmbCiudadOrigen;
	private Label lbNumeroDocumento;
	private Image imgValidacionDNISearch;
	private Label lbFechaNacimiento;
	private Label lbUbigeo;
	private Button btnNuevoPax;
	private Button btnEditarPax;
	private Grid grInfoPax;
	private Button btnBuscarPax;
	private Row rowDetalleCortesia;
	private Textbox txtDetalleCortesia;
	private Textbox txtRuc;
	private Textbox txtRazonSocial;
	private Row rowCanje;
	private Label lblCliente;
	private Button btnBuscarCliente;

	/* Declara controles Mantenimiento de Pasajeros */
	private Groupbox gbxMantPasajero;
	private Combobox cmbDocumentoIdentificacion;
	private Textbox txtNumeroDocumento;
	private Textbox txtApellidoPaterno;
	private Textbox txtApellidoMaterno;
	private Textbox txtNombre;
	private Datebox dbfechaNacimiento;
	private Combobox cmbSexo;
	private Combobox cmbEstadoCivil;
	private Textbox txtDireccion;
	private Textbox txtIdUbigeo;
	private Textbox txtUbicacionGeografica;
	private Textbox txtTelefono;
	private Textbox txtCorreoElectronico;
	private Button btnUbicacionGeografica;
	private Combobox cmbTipoFormaPago;
	private Image imgValidacionDNI;
	/* End */
	 

	private Cortesia cortesia = null;
	private Pasajero pasajero = null;
	private CanalVenta canalVenta = null;
	private Cliente oCliente = null;
//	private Date fechaLiquidacion=null;
	private boolean TIENE_CANJE = false;

	public Integer action = null;
	private Window wndValidaUsuario = null;

	int ID_MOTCOR_ORDEN_TRABAJO=3;
	int ID_MOTCOR_CANJE_PUBLICITARIO=15;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#initComponents()
	 */
	@Override
	public void initComponents() {
		gbxDatosCortesia = (Groupbox) this.getFellow("gbxDatosCortesia");
		cmbMotivoCortesia = (Combobox) this.getFellow("cmbMotivoCortesia");
//		dbFechaInicio = (Datebox) this.getFellow("dbFechaInicio");
		dbFechaCaducidad = (Datebox) this.getFellow("dbFechaCaducidad");
		cmbRuta = (Combobox) this.getFellow("cmbRuta");
		itProcentejeDescuento = (Intbox) this.getFellow("itProcentejeDescuento");
		cmbAutorizadorCortesia = (Combobox) this.getFellow("cmbAutorizadorCortesia");
		rdPorDocumento = (Radio) this.getFellow("rdPorDocumento");
		rdPorApellidos = (Radio) this.getFellow("rdPorApellidos");
		txtBuscarPax = (Textbox) this.getFellow("txtBuscarPax");
		lisboxPasajeros = (Listbox) this.getFellow("lisboxPasajeros");
		txtidPasajero = (Textbox) this.getFellow("txtidPasajero");
		lbPasajero = (Label) this.getFellow("lbPasajero");
//		dbxFechaViaje = (Datebox) this.getFellow("dbxFechaViaje");
		cmbCiudadOrigen = (Combobox) this.getFellow("cmbCiudadOrigen");
		lbNumeroDocumento = (Label) this.getFellow("lbNumeroDocumento");
		imgValidacionDNISearch=(Image)this.getFellow("imgValidacionDNISearch");
		lbUbigeo = (Label) this.getFellow("lbUbigeo");
		btnNuevoPax = (Button) this.getFellow("btnNuevoPax");
		btnEditarPax = (Button) this.getFellow("btnEditarPax");
		grInfoPax = (Grid) this.getFellow("grInfoPax");
		btnBuscarPax = (Button) this.getFellow("btnBuscarPax");
		lbFechaNacimiento = (Label) this.getFellow("lbFechaNacimiento");
		rowDetalleCortesia = (Row)this.getFellow("rowDetalleCortesia");
		txtDetalleCortesia = (Textbox)this.getFellow("txtDetalleCortesia");
		txtRuc = (Textbox)this.getFellow("txtRuc");
		txtRazonSocial = (Textbox)this.getFellow("txtRazonSocial");
		rowCanje = (Row)this.getFellow("rowCanje");
		lblCliente = (Label)this.getFellow("lblCliente");
		btnBuscarCliente=(Button)this.getFellow("btnBuscarCliente");

		/* Inicia controles Mantenimiento de Pasajeros */
		gbxMantPasajero = (Groupbox) this.getFellow("gbxMantPasajero");
		cmbDocumentoIdentificacion = (Combobox) this.getFellow("cmbDocumentoIdentificacion");
		txtNumeroDocumento = (Textbox) this.getFellow("txtNumeroDocumento");
		txtApellidoPaterno = (Textbox) this.getFellow("txtApellidoPaterno");
		txtApellidoMaterno = (Textbox) this.getFellow("txtApellidoMaterno");
		txtNombre = (Textbox) this.getFellow("txtNombre");
		dbfechaNacimiento = (Datebox) this.getFellow("dbfechaNacimiento");
		cmbSexo = (Combobox) this.getFellow("cmbSexo");
		cmbEstadoCivil = (Combobox) this.getFellow("cmbEstadoCivil");
		txtDireccion = (Textbox) this.getFellow("txtDireccion");
		txtIdUbigeo = (Textbox) this.getFellow("txtIdUbigeo");
		txtUbicacionGeografica = (Textbox) this.getFellow("txtUbicacionGeografica");
		txtTelefono = (Textbox) this.getFellow("txtTelefono");
		txtCorreoElectronico = (Textbox) this.getFellow("txtCorreoElectronico");
		btnUbicacionGeografica = (Button) this.getFellow("btnUbicacionGeografica");
		cmbTipoFormaPago = (Combobox) this.getFellow("cmbTipoFormaPago");
		imgValidacionDNI=(Image)this.getFellow("imgValidacionDNI");
		/* End */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		canalVenta = (CanalVenta) this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_CANAL_VENTA);
//		fechaLiquidacion=(Date)this.getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_FECHA_LIQUIDACION);
		/*********************************************************************/
		/* Carga datos predeterminados para la cortesia */
		UtilData.cargarGenericData(cmbMotivoCortesia, false);
		UtilData.cargarDataCombo(cmbCiudadOrigen, Localidad.class, false);
		UtilData.cargarGenericData(cmbRuta, false);
		UtilData.cargarDataCombo(cmbAutorizadorCortesia,AutorizadorCortesia.class, false);
		UtilData.enlazarUbigeo(txtIdUbigeo, txtUbicacionGeografica,btnUbicacionGeografica,null);
		cargarTipoFormaPago();
		
		/* Carga combo para el Mantenimiento de Pasajeros */
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<String, Object>();
		criteriosBusqueda.put("tipo", TipoDocumento.PERSONALES);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarDataCombo(cmbDocumentoIdentificacion,TipoDocumento.class, criteriosBusqueda, false);
		UtilData.cargarDataCombo(cmbSexo, Sexo.class, false);
		UtilData.cargarDataCombo(cmbEstadoCivil, EstadoCivil.class, false);
		cmbDocumentoIdentificacion.setSelectedIndex(0);
		cmbSexo.setSelectedIndex(0);
		cmbEstadoCivil.setSelectedIndex(0);
		dbfechaNacimiento.setText("01/01/1900");
				
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
		
		cmbMotivoCortesia.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				if(cmbMotivoCortesia.getSelectedItem().getValue() instanceof MotivoCortesia 
						&& ((MotivoCortesia)cmbMotivoCortesia.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_MOTCOR_OTROS){
					rowDetalleCortesia.setVisible(true);
					txtDetalleCortesia.setText("");
					txtDetalleCortesia.setFocus(true);
				}else{
					rowDetalleCortesia.setVisible(false);
					txtDetalleCortesia.setText("");
					itProcentejeDescuento.setFocus(true);
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
		Util.limpiarCombobox(cmbMotivoCortesia);
		Util.limpiarCombobox(cmbCiudadOrigen);
		Util.limpiarCombobox(cmbRuta);
		Util.limpiarCombobox(cmbAutorizadorCortesia);
		Util.limpiarCombobox(cmbTipoFormaPago);
		UtilData.cargarGenericData(cmbMotivoCortesia, false);
		UtilData.cargarDataCombo(cmbCiudadOrigen, Localidad.class, false);
		UtilData.cargarGenericData(cmbRuta, false);
		UtilData.cargarDataCombo(cmbAutorizadorCortesia,AutorizadorCortesia.class, false);
		cargarTipoFormaPago();
		cmbMotivoCortesia.setSelectedIndex(0);
		cmbCiudadOrigen.setSelectedIndex(0);
		cmbRuta.setSelectedIndex(0);
		cmbAutorizadorCortesia.setSelectedIndex(0);
		cmbTipoFormaPago.setSelectedIndex(0);
		rdPorDocumento.setSelected(true);
//		dbFechaInicio.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		fechaCaducidad();// Establece la fecha de caducidad por defecto.
		lisboxPasajeros.setVisible(false);
		
		lblCliente.setVisible(false);
		rowDetalleCortesia.setVisible(false);
		rowCanje.setVisible(false);
//		dbxFechaViaje.setValue(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())));

		activarBtnEditar(false);
		activarBtnNuevo(true);
		activarBtnBuscar(true);
		habilitaControleCortesia(true);
		gbxDatosCortesia.setVisible(true);
		
		String fecha = Util.DatetoString(new Date(), "yyyyMMdd");
		dbFechaCaducidad.setConstraint("after "+fecha);
		dbFechaCaducidad.setValue(new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		final WndFiltrarParametros oWndFiltrar = new WndFiltrarParametros();
//		oWndFiltrar.addParameter("Tipo forma de Pago", TipoFormaPago.class);
		oWndFiltrar.addParameter("Motivo Cortesia", MotivoCortesia.class);
		oWndFiltrar.addParameter("Fecha", Datebox.class);
		oWndFiltrar.addParameter("Doct.Pasajero", String.class);

		this.appendChild(oWndFiltrar);
		oWndFiltrar.setMode("modal");
		oWndFiltrar.addEventListener(com.cystesoft.vyrbus.view.ui.Events.ON_FILTER,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
//						TipoFormaPago tipoFormaPago = (TipoFormaPago) oWndFiltrar.getParameterValue("Tipo forma de Pago");
						MotivoCortesia motivoCortesia = (MotivoCortesia) oWndFiltrar.getParameterValue("Motivo Cortesia");
						Date fechaInicio = (Date) oWndFiltrar.getParameterValue("Fecha");
						String docPasajero = (String) oWndFiltrar.getParameterValue("Doct.Pasajero");

						Cortesia ocortesia = new Cortesia();
						if (!(docPasajero.isEmpty())) {
							Pasajero pasajero = new Pasajero();
							TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
							criterioBusqueda.put("numeroDocumento", docPasajero);
							List<?> listPax = ServiceLocator.getPasajeroManager().buscarPorX(criterioBusqueda, null);
							
							if (listPax.size() > 0) {
								Long idPasajero = ((Pasajero) listPax.get(0)).getId();
								pasajero.setId(idPasajero);
								ocortesia.setPasajero(pasajero);
							} else {
								pasajero.setId((long) -1);
								ocortesia.setPasajero(pasajero);
							}
						}
						ocortesia.setFechaInicio(fechaInicio);
//						ocortesia.setTipoFormaPago(tipoFormaPago);
						ocortesia.setMotivoCortesia(motivoCortesia);
						Util.limpiarListbox(listboxLista);
						listarCortesia(ServiceLocator.getCortesiaManager().BuscarCortesia(ocortesia));
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
		edicionCortesia();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		gbxMantPasajero.setVisible(false);
		//gbxViajesPax.setVisible(false);
		activarBtnEditar(false);
		activarBtnNuevo(false);
		pasajero = null;
		oCliente = null;
		TIENE_CANJE = false;
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
			else if (!(cmbRuta.getSelectedItem().getValue() instanceof Ruta))
				throw new RutaNullException();
//			else if (dbFechaInicio.getText().isEmpty())
//				throw new FechaInicioNullException();
			else if (dbFechaCaducidad.getText().isEmpty())
				throw new FechaCaducidadNullException();
			else if(!(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago))
				throw new TipoFormaPagoNullException();
//			else if (!(cmbAutorizadorCortesia.isDisabled())){
			else if (!(cmbAutorizadorCortesia.getSelectedItem().getValue() instanceof AutorizadorCortesia))
				throw new AutorizadorNullException();
//			}if (!(cmbMotivoCortesia.isDisabled())){
			else if (!(cmbMotivoCortesia.getSelectedItem().getValue() instanceof MotivoCortesia))
				throw new MotivoCorteciaNullException();
			else if (itProcentejeDescuento.getValue() == null)
				throw new PorcentajeDescuentoNullException();
			else if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago 
					&& ((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO 
					&& !TIENE_CANJE)
				throw new TipoFormaPagoException();
			else if (rowDetalleCortesia.isVisible() && txtDetalleCortesia.getText().trim().isEmpty()){
				DlgMessage.information(Messages.getString("WndCortesia.information.noDetalleCortesia"),txtDetalleCortesia);
				throw new CancelaGrabacionException();
			}

			final TipoFormaPago tipoFormaPago = cmbTipoFormaPago.getSelectedItem().getValue();
			
			/* Solicita confirmacion al usuario*/
			final int accion=action;
			Messagebox.show(Messages.getString("WndCortesia.information.confirmaGuardarCortesia"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						//valida si el usuario autorizador tiene configurado la confirmación mediante password.
						if(cmbAutorizadorCortesia.getSelectedItem().getValue() instanceof AutorizadorCortesia){
							AutorizadorCortesia autoriCort= cmbAutorizadorCortesia.getSelectedItem().getValue();
							if(autoriCort.getPassword()!=null)
								ventanaSolicitaPassword(accion);
							else
								guardaCortesia(tipoFormaPago);
						}else
							guardaCortesia(tipoFormaPago);
					}	
				}	
			});
			throw new CancelaGrabacionException();
		}catch (TipoFormaPagoNullException tfpnex){
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoTipoFormaPago"),cmbTipoFormaPago);
			throw new CancelaGrabacionException();
		}catch (CancelaGrabacionException pswx){
			throw new CancelaGrabacionException();
		}catch (MotivoCorteciaNullException mcnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoMotivoCortesia"),cmbMotivoCortesia);
			throw new CancelaGrabacionException();
//		}catch (FechaInicioNullException finex) {
//			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoFechaInicio"),dbFechaInicio);
//			throw new CancelaGrabacionException();
		}catch (FechaCaducidadNullException fcnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoFechaCaducidad"),dbFechaCaducidad);
			throw new CancelaGrabacionException();
		}catch (PorcentajeDescuentoNullException pdnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoPorcentajeDescuento"),itProcentejeDescuento);
			throw new CancelaGrabacionException();
		}catch (PasajeroException pnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoPasajero"),txtBuscarPax);
			cmbMotivoCortesia.setSelectedIndex(0);
			throw new CancelaGrabacionException();
		}catch (RutaNullException rnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoRuta"),cmbRuta);
			throw new CancelaGrabacionException();
		}catch (AutorizadorNullException aucnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSeleccionoAutorizador"),cmbAutorizadorCortesia);
			throw new CancelaGrabacionException();
		}catch (WrongValueException wn) {
			DlgMessage.information(wn.getMessage() + " para la fecha de viaje.");
			throw new CancelaGrabacionException();
		}catch(TipoFormaPagoException tfpex){
			if(txtRazonSocial.getText().trim().isEmpty())
				DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoCliente"), txtRuc);
			else if(!TIENE_CANJE)
				DlgMessage.information(Messages.getString("WndCortesia.information.clienteSinCanje"), txtRuc);
			throw new CancelaGrabacionException();
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
			ex.printStackTrace();
			throw new CancelaGrabacionException();
		}
	}

	/**		
	 * @param tipoFormaPago	: TipoFormaPago de la cortesia
	 * @throws Exception
	 */
	private void guardaCortesia(TipoFormaPago tipoFormaPago) throws Exception{		
		cortesia = new Cortesia();
		Ruta ruta = new Ruta();
		ruta = cmbRuta.getSelectedItem().getValue();
							
		/* Realiza la Reserva a fecha abierta */
		VentaPasaje ventaPasaje = new VentaPasaje();
		Itinerario itinerario = new Itinerario();
		FormaPago formaPago = new FormaPago();
		Servicio servicio = new Servicio();
		TipoComprobante tipoComprobante = new TipoComprobante();
		TipoMovimiento tipoMovimiento = new TipoMovimiento();
		
		itinerario.setId(new Long(1));
		// Esto es para el CANJE PUBLICITARIO
		if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO){
			formaPago.setId(Constantes.ID_FORPAG_CREDITO);
			tipoComprobante.setId(Constantes.ID_TIPCOM_FACTURA);
		}else{
			formaPago.setId(Constantes.ID_FORPAG_CORTESIA);
			tipoComprobante.setId(Constantes.ID_TIPCOM_BOLETA_VENTA);
		}
		servicio.setId(1);
//		tipoComprobante.setId(Constantes.ID_TIPCOM_BOLETO_VIAJE);
		tipoMovimiento.setId(Constantes.ID_TIPMOV_FECHA_ABIERTA);
		
		ventaPasaje.setItinerario(itinerario);
		ventaPasaje.setRuta(ruta);
		ventaPasaje.setPasajero(pasajero);
		ventaPasaje.setFormaPago(formaPago);
		ventaPasaje.setServicio(servicio);
		ventaPasaje.setTipoComprobante(tipoComprobante);
		ventaPasaje.setTipoMovimiento(tipoMovimiento);
		ventaPasaje.setTipoFormaPago(tipoFormaPago);
		ventaPasaje.setNumeroPiso(0);
		ventaPasaje.setNumeroControl("DEMO");
		ventaPasaje.setSecuencial(0);
		ventaPasaje.setTarifa(.00);
		ventaPasaje.setRecargo(.00);
		ventaPasaje.setDescuento(.00);
		ventaPasaje.setPenalidad(.00);
		ventaPasaje.setAcuenta(.00);
		ventaPasaje.setImportePagado(.00);
		ventaPasaje.setTipoTransaccion(Constantes.TIPO_OPERACION_RESERVA);
		ventaPasaje.setFechaCaducidad(Util.StringtoDate(Util.DatetoString(dbFechaCaducidad.getValue(), Constantes.DATE_FORMAT)+ " 23:59:59", Constantes.DATE_TIME_FORMAT));
		ventaPasaje.setAgencia(getAgencia());
		ventaPasaje.setUsuario(getUsuario());
		ventaPasaje.setCanalVenta(canalVenta);
		ventaPasaje.setIdaRetorno(Constantes.FALSE_VALUE);
		ventaPasaje.setEsFechaAbierta(Constantes.TRUE_VALUE);
		ventaPasaje.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		ventaPasaje.setImportePagadoEfectivo(.00);
		ventaPasaje.setImportePagadoTarjeta(.00);
		ventaPasaje.setEstadoDocumento(Constantes.ESTADO_DOCUMENTO_PAGADO);
//		ventaPasaje.setFechaLiquidacion(fechaLiquidacion);
		ventaPasaje.setFechaLiquidacion(Constantes.FORMAT_DATE.parse(Constantes.FORMAT_DATE.format(new Date())));
		ventaPasaje.setEsRemoto(false);
		if(TIENE_CANJE){
			ventaPasaje.setCliente(oCliente);
			ventaPasaje.setRucClienteCredito(oCliente.getNumeroDocumento());
		}
			
		UtilData.auditarRegistro(ventaPasaje, false, getUsuario(),Executions.getCurrent());
		ServiceLocator.getVentaPasajesManager().guardarVenta(ventaPasaje, true, true, false, true);
		
		/* Guarda la cortesia */
		cortesia.setTipoFormaPago(tipoFormaPago);
		cortesia.setPasajero(pasajero);
		cortesia.setVentaPasaje(ventaPasaje);
		if(cmbAutorizadorCortesia.getSelectedItem().getValue() instanceof AutorizadorCortesia){
			AutorizadorCortesia autorizadorCortesia = new AutorizadorCortesia();
			autorizadorCortesia = cmbAutorizadorCortesia.getSelectedItem().getValue();
			cortesia.setPersonal(autorizadorCortesia.getPersonal());
			cortesia.setAutorizadorCortesia(autorizadorCortesia);
			cortesia.setPersonaAutoriza(cmbAutorizadorCortesia.getText());
		}
		
		if(cmbMotivoCortesia.getSelectedItem().getValue() instanceof MotivoCortesia){
			cortesia.setMotivoCortesia(((MotivoCortesia)cmbMotivoCortesia.getSelectedItem().getValue()));
			cortesia.setDetalleMotivoCortesia(txtDetalleCortesia.getText().trim().isEmpty()?null:txtDetalleCortesia.getText().trim().toUpperCase());
		}
		
		cortesia.setRuta(ruta);
		cortesia.setFechaInicio(Util.StringtoDate(Util.DatetoString(new Date(), Constantes.DATE_FORMAT), Constantes.DATE_FORMAT));
		cortesia.setFechaCaducidad(Util.StringtoDate(Util.DatetoString(dbFechaCaducidad.getValue(), Constantes.DATE_FORMAT)+ " 23:59:59", Constantes.DATE_TIME_FORMAT));
		cortesia.setPorcentaje(itProcentejeDescuento.getValue());
		cortesia.setFechaViaje(null);
//		cortesia.setAnioCumpleanios(itAnioCortesia.getValue());
		cortesia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		
		UtilData.auditarRegistro(cortesia, getUsuario(),Executions.getCurrent());
		ServiceLocator.getCortesiaManager().guardar(cortesia);
		textboxId.setText(cortesia.getId().toString());
		
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
		listarCortesia(ServiceLocator.getCortesiaManager().BuscarCortesia(cortesia));
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		try {
			Listitem itemList = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			Cortesia cortesia = itemList.getValue();
			
			if(cortesia.getTipoFormaPago().getId().equals(Constantes.ID_TIPFORPAG_PUNTOS))
				if(cortesia.getRuta().getPuntaje()==null || cortesia.getRuta().getPuntaje()<=0)
					throw new RutaSinPuntajeException();
			
			//Valida si la cortesia esta confirmada. 
			if (!(ServiceLocator.getCortesiaManager().cortesiaConfirmada(cortesia.getVentaPasaje().getNumeroControl()))) {
				//Anula cortesia
				ServiceLocator.getCortesiaManager().inactivar(cortesia.getId());
				
				//Anula la reserva fecha habierta
				TipoMovimiento tipoMovimiento= new TipoMovimiento();
				VentaPasaje movimiento= ServiceLocator.getVentaPasajesManager().buscarPorId(cortesia.getVentaPasaje().getId());
				UtilData.auditarRegistro(movimiento, true, getUsuario(), Executions.getCurrent());
				tipoMovimiento.setId(Constantes.ID_TIPMOV_ANULACION_SISTEMA);
				movimiento.setTipoMovimiento(tipoMovimiento);
				ServiceLocator.getVentaPasajesManager().anularMovimiento(movimiento,false);
				
				limpiaControlesPasajero();
			} else {
				throw new NoRemoverRegistroDelListBox();
			}
		}catch (RutaSinPuntajeException rsp){
			DlgMessage.information(Messages.getString("WndCortesia.information.rutaSinPuntaje"));
			throw new NoRemoverRegistroDelListBox();
		} catch (NoRemoverRegistroDelListBox e) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noSePuedeAnularCortesia"));
			throw new NoRemoverRegistroDelListBox();
		}
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
		edicionCortesia();
	}

	/**
	 * Carga Rutas, según la Ciudad Origen.
	 * 
	 * @throws Exception
	 */
	public void onChangeRutas() throws Exception {
		cmbRuta.getItems().clear();
		if (cmbCiudadOrigen.getSelectedItem().getValue() instanceof Localidad) {
			Integer Origen = ((Localidad) cmbCiudadOrigen.getSelectedItem().getValue()).getId();
//			String puntos =lbPuntosPax.getValue().substring(lbPuntosPax.getValue().indexOf(":")+1,lbPuntosPax.getValue().toString().trim().length());
			
			UtilData.cargarRutas(cmbRuta, false, Origen,null,true);
		} else
			UtilData.cargarGenericData(cmbRuta, false);
		cmbRuta.setSelectedIndex(0);
	}

	/**
	 * Corresponde al evento onSelect del combo tipo Forma de Pago.
	 * @throws Exception 
	 */
	public void onSelectTipoFormaPago() throws Exception{
		Util.limpiarCombobox(cmbMotivoCortesia);
		UtilData.cargarGenericData(cmbMotivoCortesia, false);
		cmbAutorizadorCortesia.setDisabled(true);
		cmbMotivoCortesia.setDisabled(true);
		itProcentejeDescuento.setText("");
		itProcentejeDescuento.setDisabled(true);
		cmbAutorizadorCortesia.setSelectedIndex(0);
		cmbMotivoCortesia.setSelectedIndex(0);
		txtRuc.setText("");
		txtRazonSocial.setText("");
		lblCliente.setValue("");
		TIENE_CANJE = false;
			
		if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
			TipoFormaPago tipoFormaPago=cmbTipoFormaPago.getSelectedItem().getValue();

			//Habilita autorizador y motivo cortesia
			if (tipoFormaPago.getId().intValue() == Constantes.ID_TIPFORPAG_CORTESIA
					|| tipoFormaPago.getId().intValue() == Constantes.ID_TIPFORPAG_ORDEN_TRABAJO
					|| tipoFormaPago.getId().intValue() == Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO){
				cmbAutorizadorCortesia.setDisabled(false);
				cmbMotivoCortesia.setDisabled(false);
				itProcentejeDescuento.setDisabled(false);
			}
			
			if(tipoFormaPago.getId().intValue() == Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO)
				rowCanje.setVisible(true);				
			else
				rowCanje.setVisible(false);
				
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
	 * Lista los pasajeros encontrados en l abusqueda
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
			habilitaControleCortesia(false);
			
			muestraPax(pasajero);
			grInfoPax.setVisible(true);
			lisboxPasajeros.setVisible(false);
			btnEditarPax.setDisabled(true);
			
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
						@Override
						public void onEvent(Event event) {
							try {
								pasajero = ServiceLocator.getPasajeroManager().buscarPorId(((Pasajero) lisboxPasajeros.getSelectedItem().getValue()).getId());
								habilitaControleCortesia(false);
								muestraPax(pasajero);
								grInfoPax.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
							lisboxPasajeros.setVisible(false);
							txtBuscarPax.setText("");
//							cmbAutorizadorCortesia.setDisabled(true);
//							cmbMotivoCortesia.setDisabled(true);
						}
					});
		} else {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noPasajerosEncontrados"));
			limpiaControlesPasajero();
			activarBtnEditar(false);
			pasajero = null;
		}
		txtBuscarPax.setText("");
//		cmbAutorizadorCortesia.setDisabled(true);
//		cmbMotivoCortesia.setDisabled(true);
	}	
	
//	/**
//	 * Verifica si el Pasajero existe el la reniec si es que se registrando un nuevo Pas
//	 * @throws Exception 
//	 * @throws WrongValueException 
//	 */
//	public void validarPax_MuestraBDReniec() throws Exception{
//		if(action==Constantes.ACTION_NEW  && !(txtNumeroDocumento.getText().trim().isEmpty()) ){
//			Reniec reniec= ServiceLocator.getReniecManager().buscarPax(txtNumeroDocumento.getText().trim());
//			if(reniec!=null){
//				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
//				txtApellidoPaterno.setText(reniec.getApellidoPaterno());
//				txtApellidoMaterno.setText(reniec.getApellidoMaterno());
//				txtNombre.setText(reniec.getNombres());
//				dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(reniec.getFechaNacimiento()));
//				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
//				
//			}else{
//				String numeroDocumento=txtNumeroDocumento.getText().trim();
//				Integer idTipoDocumento= null;
//				if(cmbDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento)
//					idTipoDocumento=((TipoDocumento)cmbDocumentoIdentificacion.getSelectedItem().getValue()).getId();
//				
//				clearControsMantPax();
//				dbfechaNacimiento.setValue(Constantes.FECHA_NULL);
//				
//				//recupera valores ingresado por el usuario
//				txtNumeroDocumento.setText(numeroDocumento);
//				if(idTipoDocumento!=null) Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, idTipoDocumento);
//			}
//		}
//	}
//	
//	/**
//	 * Reliza la validacion del pasajero con el metodop getIdentifidad del WS del mtc en funcion al parametro configurado.
//	 * @throws WrongValueException
//	 * @throws Exception
//	 */
//	public void verificarPaxReniec() throws WrongValueException, Exception{
//		imgValidacionDNISearch.setSrc("");
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
	public void verificarPaxReniec() throws Exception{
		txtApellidoMaterno.setDisabled(false);
		txtApellidoPaterno.setDisabled(false);
		txtNombre.setDisabled(false);
		
		if(action==Constantes.ACTION_NEW  
				&& !(txtNumeroDocumento.getText().trim().isEmpty()) 
				&& cmbDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento 
				&& ((TipoDocumento)cmbDocumentoIdentificacion.getSelectedItem().getValue()).getId().intValue()==Constantes.ID_TIPDOC_DNI){
			
			String numerodocumento=txtNumeroDocumento.getText().trim();
			
			/*Busca el DNI en la reniec, a travez del WS del MTC*/			
			ResultIdentidad resultIdentidad=Util.getResultIdentidad(numerodocumento,imgValidacionDNI);
			if(resultIdentidad!=null){	
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
				/*Si el DNI es correcto*/
				if(resultIdentidad.isReturn()){
					/*Para no permitir la modificacion de los appellidos y nombres al usuario*/
					txtApellidoMaterno.setDisabled(true);
					txtApellidoPaterno.setDisabled(true);
					txtNombre.setDisabled(true);
					/*Carga los apellidos y nombres retornados por la reniec*/
					txtApellidoPaterno.setText(resultIdentidad.getPaterno()!=null && !(resultIdentidad.getPaterno().trim().isEmpty()) ?resultIdentidad.getPaterno().trim():"");
					txtApellidoMaterno.setText(resultIdentidad.getMaterno()!=null && !(resultIdentidad.getMaterno().trim().isEmpty()) ? resultIdentidad.getMaterno().trim():"");
					txtNombre.setText(resultIdentidad.getNombre().trim());
					/*Obtiene datos como la fecha de nacimiento y el sexo de NUESTRA base de datos de la Reniec*/
					if(resultIdentidad.getReniec()!=null){
						dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(resultIdentidad.getReniec().getFechaNacimiento()));
						Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(resultIdentidad.getReniec().getSexo()));
					}
				}
			}else{
				/*Consulta con NUESTRA BD reniec*/
				Reniec reniec= ServiceLocator.getReniecManager().buscarPax(numerodocumento);
				if(reniec!=null){
					Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
					txtApellidoMaterno.setText(reniec.getApellidoPaterno());
					txtApellidoPaterno.setText(reniec.getApellidoMaterno());
					txtNombre.setText(reniec.getNombres());
					dbfechaNacimiento.setValue(Constantes.FORMAT_DATE.parse(reniec.getFechaNacimiento()));
					Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, Integer.valueOf(reniec.getSexo()));
				}else{
					String numeroDocumento=txtNumeroDocumento.getText().trim();
					Integer idTipoDocumento= null;
					if(cmbDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento)
						idTipoDocumento=((TipoDocumento)cmbDocumentoIdentificacion.getSelectedItem().getValue()).getId();
					
					clearControsMantPax();
					dbfechaNacimiento.setValue(Constantes.FECHA_NULL);
					
					//recupera valores ingresado por el usuario
					txtNumeroDocumento.setText(numeroDocumento);
					if(idTipoDocumento!=null) Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, idTipoDocumento);
				}
			}
		}
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
			if (pasajero.getIndeseable().equals(Constantes.PASAJERO_INDESEABLE))
				throw new PasajeroIndeseableException();
			pasajero=ServiceLocator.getPasajeroManager().buscarPorId(pasajero.getId());
			
			txtidPasajero.setText(pasajero.getId().toString());
			lbNumeroDocumento.setValue(pasajero.getNumeroDocumento());
			lbPasajero.setValue(pasajero.toString());
			lbUbigeo.setValue(ServiceLocator.getUbigeoManager().ubicacionGeografica(pasajero.getUbigeo()));
			lbFechaNacimiento.setValue(pasajero.getFechaNacimiento() != null ? pasajero.getFechaNacimiento() : "");
			activarBtnEditar(true);
			if(cmbTipoFormaPago.getItems().size()==2)
				cmbTipoFormaPago.setSelectedIndex(1);
			else
				cmbTipoFormaPago.setSelectedIndex(0);
			
			onSelectTipoFormaPago();		
			
			/*Establece la imagen segun validacion del DNI del pasajero con la reniec - 04/04/2015 - jabanto*/
			imgValidacionDNISearch.setSrc("");
			if(pasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI)
				Util.imagenValidacionDNIReniec(pasajero.getValidadoReniec(), imgValidacionDNISearch);
			
			cmbCiudadOrigen.setFocus(true);
			cmbCiudadOrigen.select();
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
	 */
	private void listarCortesia(List<Cortesia> lstCortecia) {
		Listitem item = null;
		Listcell cell = null;
		int x=0;
		for (Cortesia cortesia : lstCortecia) {
			x++;
			item = new Listitem();
			cell = new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell = new Listcell(cortesia.getPasajero().toString());
			item.appendChild(cell);
			cell = new Listcell(cortesia.getTipoFormaPago().getDenominacion());
			item.appendChild(cell);
			cell = new Listcell(cortesia.getRuta().getOrigen() + " / "+ cortesia.getRuta().getDestino());
			item.appendChild(cell);
			cell = new Listcell(Constantes.FORMAT_DATE.format(cortesia.getFechaInicio()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(Constantes.FORMAT_DATE.format(cortesia.getFechaCaducidad()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell = new Listcell(cortesia.getPorcentaje().toString());
			item.appendChild(cell);
			if (cortesia.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
				cell = new Listcell(Constantes.LABEL_ACTIVO_DESCP);
			else if (cortesia.getEstadoRegistro().equals(
					Constantes.VALUE_INACTIVO))
				cell = new Listcell(Constantes.LABEL_INACTIVO_DESCP);
			item.appendChild(cell);
			
			cell= new Listcell(cortesia.getNumeroControl());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell= new Listcell(cortesia.getVentaPasaje().getNumeroBoleto());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			

			item.setValue(cortesia);
			listboxLista.appendChild(item);
		}
	}

	public void onSelectList() throws Exception {
		if (listboxLista.getSelectedIndex() >= 0) {
			Listitem listitem = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
			Cortesia cortesia = listitem.getValue();
			btnEliminar.setDisabled(ServiceLocator.getCortesiaManager().cortesiaConfirmada(cortesia.getVentaPasaje().getNumeroControl()));
			btnEliminar.setDisabled(true);
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void edicionCortesia() throws Exception {
		activarBtnNuevo(false);
		activarBtnEditar(false);
		activarBtnBuscar(false);
		lblCliente.setVisible(false);
		rowCanje.setVisible(false);
		rowDetalleCortesia.setVisible(false);
		habilitaControleCortesia(true);
		Listitem itCortecia = listboxLista.getItemAtIndex(listboxLista.getSelectedIndex());
		if (itCortecia != null) {
			cortesia = new Cortesia();
			cortesia = ServiceLocator.getCortesiaManager().buscarPorId(((Cortesia) itCortecia.getValue()).getId());
			pasajero = cortesia.getPasajero();

			/* Datos del Pasajero */
			textboxId.setText(cortesia.getId().toString());
			lbNumeroDocumento.setValue(cortesia.getPasajero().getNumeroDocumento());
			lbPasajero.setValue(cortesia.getPasajero().toString());
			lbFechaNacimiento.setValue(cortesia.getPasajero().getSexo().getDenominacion());
			lbUbigeo.setValue(ServiceLocator.getUbigeoManager().ubicacionGeografica(cortesia.getPasajero().getUbigeo()));
			/* Datos de la cortesia */
			textboxId.setText(cortesia.getId().toString());
			txtidPasajero.setText(cortesia.getPasajero().getId().toString());
			Util.seleccionarValorItemCombo(Localidad.class, cmbCiudadOrigen,cortesia.getRuta().getLocalidadOrigen().getId());
			onChangeRutas();
			Util.seleccionarValorItemCombo(Ruta.class, cmbRuta, cortesia.getRuta().getId());
//			dbFechaInicio.setValue(cortesia.getFechaInicio());
			
			String fecha = Util.DatetoString(cortesia.getFechaCaducidad(), "yyyyMMdd");
			dbFechaCaducidad.setConstraint("after "+fecha);
			dbFechaCaducidad.setValue(cortesia.getFechaCaducidad());
			
			if(cortesia.getAutorizadorCortesia()!=null)
				Util.seleccionarValorItemCombo(AutorizadorCortesia.class,cmbAutorizadorCortesia, cortesia.getAutorizadorCortesia().getId());
			else{
				cmbAutorizadorCortesia.setSelectedIndex(0);
			}
			
			Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, cortesia.getTipoFormaPago().getId());
			
			if(cortesia.getMotivoCortesia()!=null){
				cargarMotivoCortesia();
				Util.seleccionarValorItemCombo(MotivoCortesia.class,cmbMotivoCortesia, cortesia.getMotivoCortesia().getId());
				
				if(cmbMotivoCortesia.getSelectedIndex()>0){
					MotivoCortesia motivoCortesia=cmbMotivoCortesia.getSelectedItem().getValue();
					if(motivoCortesia.getId().intValue()==Constantes.ID_MOTCOR_OTROS){
						txtDetalleCortesia.setText(cortesia.getDetalleMotivoCortesia());
						rowDetalleCortesia.setVisible(true);
					}else if (motivoCortesia.getId().intValue()==ID_MOTCOR_CANJE_PUBLICITARIO){
						if(cortesia.getVentaPasaje().getCliente()!=null){
							txtRuc.setText(cortesia.getVentaPasaje().getCliente().getNumeroDocumento());
							txtRazonSocial.setText(cortesia.getVentaPasaje().getCliente().getRazonSocial());
						}
						rowCanje.setVisible(true);
					}
				}
			}else{
				cmbMotivoCortesia.setSelectedIndex(0);
			}

//			Util.seleccionarValorItemCombo(TipoFormaPago.class, cmbTipoFormaPago, cortesia.getTipoFormaPago().getId());
//			if (cortesia.getFechaViaje() != null) dbxFechaViaje.setValue(cortesia.getFechaViaje());
			itProcentejeDescuento.setValue(cortesia.getPorcentaje());
			
		}
	}

	/**
	 * Cancela edicion o el ingreso de un nuevo pax al sistema.
	 */
	public void onClousePax() {
		gbxMantPasajero.setVisible(false);
		gbxDatosCortesia.setVisible(true);
		grInfoPax.setVisible(true);
		activarBtnNuevo(true);
		if (pasajero != null)
			activarBtnEditar(true);
		activarBtnBuscar(true);
		
	}

	/**
	 * Habilita o desabilita controles de la cortesia y limpia los controles si el estado es true
	 * @param estado
	 */
	private void habilitaControleCortesia(boolean estado) {
		cmbCiudadOrigen.setDisabled(estado);
		cmbRuta.setDisabled(estado);
//		dbFechaInicio.setDisabled(estado);
		dbFechaCaducidad.setDisabled(estado);
		cmbMotivoCortesia.setDisabled(estado);
		cmbAutorizadorCortesia.setDisabled(estado);
		cmbTipoFormaPago.setDisabled(estado);
		itProcentejeDescuento.setDisabled(estado);
		txtDetalleCortesia.setDisabled(estado);
		txtRuc.setDisabled(estado);
		txtRazonSocial.setDisabled(estado);
		Util.disabledBtnBuscar(estado, btnBuscarCliente, true);
//		dbxFechaViaje.setDisabled(estado);
//		itAnioCortesia.setDisabled(estado);
		
		if (estado == true) {
			cmbCiudadOrigen.setSelectedIndex(0);
			Util.limpiarCombobox(cmbRuta);
			UtilData.cargarGenericData(cmbRuta, false);
			cmbRuta.setSelectedIndex(0);
			cmbMotivoCortesia.setSelectedIndex(0);
			cmbAutorizadorCortesia.setSelectedIndex(0);
			cmbTipoFormaPago.setSelectedIndex(0);
//			dbxFechaViaje.setText("");
			itProcentejeDescuento.setText("");
//			itAnioCortesia.setText("");
		}
	}

	/**
	 * Establece la fecha de caducidad segun la fecha inicio seleccionada
	 * 
	 * @throws Exception
	 */
	public void fechaCaducidad() throws Exception {
//		Date fechaCaducidad = Constantes.FORMAT_DATE.parse(dbFechaInicio.getText());
		Date fechaCaducidad = new Date();
		fechaCaducidad.setTime(fechaCaducidad.getTime()+ (Constantes.MILISEGUNDOS_X_DIA * Constantes.TIEMPO_CADUCA_CORTESIA));
		dbFechaCaducidad.setValue(fechaCaducidad);
	}

	/**
	 * Carga los motivos de cortesia asiciados al autorizador de la cortesia.
	 */
	public void cargarMotivoCortesia() throws Exception {
				
		itProcentejeDescuento.setText("");
		//itProcentejeDescuento.setDisabled(false);
		cmbMotivoCortesia.getItems().clear();
		UtilData.cargarGenericData(cmbMotivoCortesia, false);

		if (cmbAutorizadorCortesia.getSelectedIndex()>0) {
			
			List<MotivoCortesia> lstMotivos = ServiceLocator.getAutorizadorMotivoCortesiaManager().buscarMotivosCortesia(((AutorizadorCortesia) cmbAutorizadorCortesia.getSelectedItem().getValue()).getPersonal().getId());

			for (MotivoCortesia motivoCortesia : lstMotivos) {
				if(cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
					TipoFormaPago tipoFormaPago=cmbTipoFormaPago.getSelectedItem().getValue();
					
					/* Cuando el tipo forma de pago es ORDEN DE TRABAJO*/
					if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_ORDEN_TRABAJO){
						if(motivoCortesia.getId().intValue()==ID_MOTCOR_ORDEN_TRABAJO){
							Comboitem comboitem = new Comboitem();
							comboitem.setValue(motivoCortesia);
							comboitem.setLabel(motivoCortesia.getDenominacion());

							cmbMotivoCortesia.appendChild(comboitem);
						}
					}else if (tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO){
						/* Cuando el tipo forma de pago es CANJE PUBLICITARIO*/
						if(motivoCortesia.getId().intValue()==ID_MOTCOR_CANJE_PUBLICITARIO){
							Comboitem comboitem = new Comboitem();
							comboitem.setValue(motivoCortesia);
							comboitem.setLabel(motivoCortesia.getDenominacion());

							cmbMotivoCortesia.appendChild(comboitem);
						}
					}else{
						/* Cuando el tipo forma de pago es diferente a los mensionados anteriormente, Ejmplo, Cortesia*/
						if(motivoCortesia.getId().intValue()!=ID_MOTCOR_ORDEN_TRABAJO && motivoCortesia.getId().intValue()!=ID_MOTCOR_CANJE_PUBLICITARIO){
							Comboitem comboitem = new Comboitem();
							comboitem.setValue(motivoCortesia);
							comboitem.setLabel(motivoCortesia.getDenominacion());

							cmbMotivoCortesia.appendChild(comboitem);
						}
					}
				}
			}
		}
		rowDetalleCortesia.setVisible(false);
		txtDetalleCortesia.setText("");
	}

	/*
	 * MANTENIMIENTO DEL PASAJERO
	 */
	/**
	 * Pasajero Nuevo o edisión del mismo.
	 * 
	 * @throws Exception
	 */
	public void onNewEdit(Integer action) throws Exception {
		try {
			if (action == ACTION_NEW) {// Pax Nuevo.
				clearControsMantPax();
				Util.seleccionarValorItemCombo(TipoDocumento.class, cmbDocumentoIdentificacion, Constantes.ID_TIPDOC_DNI);
			} else {// Edición del Pax.
				if (pasajero == null)
					throw new PasajeroException();
				Util.seleccionarValorItemCombo(TipoDocumento.class,cmbDocumentoIdentificacion, pasajero.getTipoDocumento().getId());
				txtNumeroDocumento.setText(pasajero.getNumeroDocumento());
				txtApellidoPaterno.setText(pasajero.getApellidoPaterno());
				txtApellidoMaterno.setText(pasajero.getApellidoMaterno()==null?"":pasajero.getApellidoMaterno());
				txtNombre.setText(pasajero.getNombre());
				dbfechaNacimiento.setValue(pasajero.getFechaNacimiento() != null ? Constantes.FORMAT_DATE.parse(pasajero.getFechaNacimiento()) : null);
				Util.seleccionarValorItemCombo(Sexo.class, cmbSexo, pasajero.getSexo().getId());
				if (pasajero.getEstadoCivil() != null)
					Util.seleccionarValorItemCombo(EstadoCivil.class,cmbEstadoCivil, pasajero.getEstadoCivil().getId());
				else
					cmbEstadoCivil.setSelectedIndex(0);
				txtDireccion.setText(pasajero.getDireccion());
				txtIdUbigeo.setText(pasajero.getUbigeo().getId());
				txtUbicacionGeografica.setText(ServiceLocator.getUbigeoManager().ubicacionGeografica(pasajero.getUbigeo()));
				txtTelefono.setText(pasajero.getTelefono());
				txtCorreoElectronico.setText(pasajero.getEmail());
			}
			this.action = action;
			gbxDatosCortesia.setVisible(false);
			grInfoPax.setVisible(false);
			gbxMantPasajero.setVisible(true);
			activarBtnNuevo(false);
			activarBtnEditar(false);
			activarBtnBuscar(false);

		} catch (PasajeroException pnex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.noPasajeroEncontrado"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Guarda Pasajero
	 * 
	 * @throws Exception
	 */
	public void onSavePax() throws Exception {
		try {
			if (!(cmbDocumentoIdentificacion.getSelectedItem().getValue() instanceof TipoDocumento))
				throw new TipoDocumentoNullException();
			else if (txtNumeroDocumento.getText().trim().equals(""))
				throw new NumeroDocumentoNullException();
			else if (txtApellidoPaterno.getText().trim().equals(""))
				throw new ApellidoPaternoNullException();
			else if (txtApellidoMaterno.getText().trim().equals(""))
				throw new ApellidoMaternoNullException();
			else if (txtNombre.getText().trim().equals(""))
				throw new NombresNullException();
			else if (txtUbicacionGeografica.getText().trim().equals(""))
				throw new UbigeoNullException();
			else if (!(cmbSexo.getSelectedItem().getValue() instanceof Sexo))
				throw new SexoNullException();
			else if (((TipoDocumento) cmbDocumentoIdentificacion.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPDOC_DNI)) {
				if (!(txtNumeroDocumento.getText().trim().length() == 8))
					throw new NumeroDocumentoIncorrectoException();
			} else if (((TipoDocumento) cmbDocumentoIdentificacion.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPDOC_RUC)) {
				if (!(txtNumeroDocumento.getText().trim().length() == 11))
					throw new NumeroDocumentoIncorrectoException();
			}

			if (txtCorreoElectronico.getText().length() > 0)
				if (UtilData.validateEmail(txtCorreoElectronico.getText().trim()) == false)
					throw new MailIncorectoException();
			if (action == ACTION_NEW)
				pasajero = new Pasajero();

			TipoDocumento tipoDocumento = cmbDocumentoIdentificacion.getSelectedItem().getValue();
			Sexo sexo = cmbSexo.getSelectedItem().getValue();
			Ubigeo ubigeo = ServiceLocator.getUbigeoManager().buscarPorId(txtIdUbigeo.getText());

			pasajero.setId(pasajero.getId());
			pasajero.setTipoDocumento(tipoDocumento);
			pasajero.setNumeroDocumento(txtNumeroDocumento.getText().trim().toUpperCase());
			pasajero.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
			pasajero.setApellidoMaterno(txtApellidoMaterno.getText().trim().equals("")?null:txtApellidoMaterno.getText().trim().toUpperCase());
			pasajero.setNombre(txtNombre.getText().trim().toUpperCase());
			pasajero.setFechaNacimiento(dbfechaNacimiento.getText().toString());
			pasajero.setNombresApellidos(pasajero.getNombre()+" "+pasajero.getApellidoPaterno()+" "+pasajero.getApellidoMaterno());
			pasajero.setSexo(sexo);
			if (!(cmbEstadoCivil.getSelectedItem().getValue() instanceof EstadoCivil)) {
				pasajero.setEstadoCivil(null);
			} else {
				EstadoCivil estadoCivil = cmbEstadoCivil.getSelectedItem().getValue();
				pasajero.setEstadoCivil(estadoCivil);
			}
			pasajero.setDireccion(txtDireccion.getText().trim().toUpperCase());
			pasajero.setUbigeo(ubigeo);
			pasajero.setTelefono(txtTelefono.getText());
			pasajero.setEmail(txtCorreoElectronico.getText());
			pasajero.setAgencia(action == ACTION_NEW ? getAgencia() : pasajero.getAgencia());
			pasajero.setKilometros(action == ACTION_NEW ? Double.valueOf(0): pasajero.getKilometros());
			pasajero.setIndeseable(Constantes.FALSE_VALUE);
			pasajero.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			
			/*Valida si es DNI y si fue validado correctamente por la RENIEC. - 06/04/2015*/
			pasajero.setValidadoReniec(Constantes.FALSE_VALUE);
			if(pasajero.getTipoDocumento().getId().intValue()==Constantes.ID_TIPDOC_DNI){
				if(imgValidacionDNI.getSrc()!=null && !(imgValidacionDNI.getSrc().trim().isEmpty()) && imgValidacionDNI.getSrc().equals(Constantes.IMAGE_VALIDACION_DNI_OK))
					pasajero.setValidadoReniec(Constantes.TRUE_VALUE);
			}
			
			switch (action) {
				case ACTION_NEW:
					UtilData.auditarRegistro(pasajero, getUsuario(),Executions.getCurrent());
					ServiceLocator.getPasajeroManager().guardar(pasajero);
					break;
				case ACTION_MODIFY:
					UtilData.auditarRegistro(pasajero, true, getUsuario(),Executions.getCurrent());
					ServiceLocator.getPasajeroManager().actualizar(pasajero);
					break;
			}
			gbxMantPasajero.setVisible(false);
			grInfoPax.setVisible(true);
			gbxDatosCortesia.setVisible(true);
			activarBtnNuevo(true);
			activarBtnEditar(true);
			activarBtnBuscar(true);
			
			if(action==ACTION_NEW && cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago){
				if(((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS)){
					cmbTipoFormaPago.setSelectedIndex(0);
//					itAnioCortesia.setText("");
					itProcentejeDescuento.setText("");
//					itAnioCortesia.setDisabled(true);
					itProcentejeDescuento.setDisabled(true);
//					Util.limpiarListbox(listViajesPax);
//					gbxViajesPax.setVisible(false);
				}
			}
			lbNumeroDocumento.setValue(pasajero.getNumeroDocumento());
			lbPasajero.setValue(pasajero.toString());
			lbFechaNacimiento.setValue(Constantes.FORMAT_DATE.format(dbfechaNacimiento.getValue()));
			//lbSexo.setValue(pasajero.getSexo().getDenominacion());
			lbUbigeo.setValue(txtUbicacionGeografica.getText());

			if (action == ACTION_NEW) {
//				lbPaxfri.setValue("");
				DlgMessage.information(Messages.getString("WndCortesia.information.exitoGuardarPasajero"));
			} else
				DlgMessage.information(Messages.getString("WndCortesia.information.exitoActualizarPasajero"));

		} catch (NumeroDocumentoIncorrectoException ndiex) {
			DlgMessage.information(Messages.getString("WndCortesia.information.numeroDocumentoIncorrecto"));
			txtNumeroDocumento.setFocus(true);
		} catch (MailIncorectoException mie) {
			DlgMessage.information(Messages.getString("Generales.information.mailIncorrecto"));
			txtCorreoElectronico.setFocus(true);
		} catch (TipoDocumentoNullException tdnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionTipoDocumento"));
			cmbDocumentoIdentificacion.setFocus(true);
		} catch (NumeroDocumentoNullException ndnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoPax"));
			txtNumeroDocumento.setFocus(true);
		} catch (ApellidoPaternoNullException apnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noApellidoPaterno"));
			txtApellidoPaterno.setFocus(true);
		} catch (NombresNullException nnex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noNombres"));
			txtNombre.setFocus(true);
		} catch (UbigeoNullException unex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noUbigeo"));
		} catch (SexoNullException snex) {
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noSelectionSexo"));
			cmbSexo.setFocus(true);
		} catch (DocumentoPaxDuplicadoException dpdnex) {
			DlgMessage.information(Messages.getString("WndPasajero.information.noDocumentoPaxDuplicado"));
			txtNumeroDocumento.setFocus(true);
		} catch (ApellidoMaternoNullException amnex) {
			DlgMessage.information(Messages.getString("WndPasajero.information.ApellidoMaterno"));
			txtApellidoMaterno.setFocus(true);

		} catch (Exception ex) {
			DlgMessage.error(this.getClass().getName() + " " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Activa o Inactiva el botón Nuevo pasajero
	 * 
	 * @param activar
	 */
	private void activarBtnNuevo(boolean activar) {
		if (activar && accesoNuevo()) {
			btnNuevoPax.setDisabled(false);
			btnNuevoPax.setImage("/resources/mp_nuevoEnabled.png");
			btnNuevoPax.setStyle("cursor:pointer");

			btnGuardar.setDisabled(false);
		} else {
			btnNuevoPax.setDisabled(true);
			btnNuevoPax.setImage("/resources/mp_nuevoDisabled.png");
			btnNuevoPax.setStyle("cursor:default");

			btnGuardar.setDisabled(true);
		}
	}

	/**
	 * Activa o inactiva el botón Editar Pasajero.
	 * 
	 * @param activar
	 */
	private void activarBtnEditar(boolean activar) {
		if (activar && accesoModificar()) {
			btnEditarPax.setDisabled(false);
			btnEditarPax.setImage("/resources/mp_editarEnabled.png");
			btnEditarPax.setStyle("cursor:pointer");

//			if (cmbTipoFormaPago.getSelectedItem().getValue() instanceof TipoFormaPago)
//				if (((TipoFormaPago) cmbTipoFormaPago.getSelectedItem().getValue()).getId().equals(Constantes.ID_TIPFORPAG_CUMPLEANIOS))
//					gbxViajesPax.setVisible(true);
		} else {
			btnEditarPax.setDisabled(true);
			btnEditarPax.setImage("/resources/mp_editarDisabled.png");
			btnEditarPax.setStyle("cursor:default");
//			gbxViajesPax.setVisible(false);
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
	 */
	private void limpiaControlesPasajero() {
		txtidPasajero.setText("");
		lbNumeroDocumento.setValue("");
		lbPasajero.setValue("");
		lbFechaNacimiento.setValue("");
		lbUbigeo.setValue("");
//		lbPaxfri.setValue("");
		//lbFechaNacimiento.setValue("");
		imgValidacionDNI.setSrc("");
		imgValidacionDNISearch.setSrc("");
	}

	/**
	 * Limpia controlos del mantenimineto del Pasajero.
	 */
	private void clearControsMantPax() {
		cmbDocumentoIdentificacion.setSelectedIndex(0);
		txtNumeroDocumento.setText("");
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtNombre.setText("");
		dbfechaNacimiento.setText("01/01/1900");
		cmbSexo.setSelectedIndex(0);
		cmbEstadoCivil.setSelectedIndex(0);
		txtDireccion.setText("");
		txtIdUbigeo.setText("");
		txtUbicacionGeografica.setText("");
		txtTelefono.setText("");
		txtCorreoElectronico.setText("");
	}

	/**
	 * Carga los tipos de pago que guardan relación con la forma de fago cortesia.
	 * @throws Exception 
	 */
	private void cargarTipoFormaPago() throws Exception{
		List<String> criteriosOrdenar = new ArrayList<String>();
		criteriosOrdenar.add("denominacion");
		
		TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
//		FormaPago formaPago= new FormaPago();
//		formaPago.setId(Constantes.ID_FORPAG_CORTESIA);
//		criterioBusqueda.put("formaPago", formaPago);
		criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		
		List<TipoFormaPago> lstResut= ServiceLocator.getTipoFormaPagoManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
		
		UtilData.cargarGenericData(cmbTipoFormaPago, false);
		for (TipoFormaPago tipoFormaPago: lstResut){
			if(tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CORTESIA 
					|| tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_ORDEN_TRABAJO
					|| tipoFormaPago.getId().intValue()==Constantes.ID_TIPFORPAG_CANJE_PUBLICITARIO){ //Solo muestra el tipo forma pago "CORTESIA"
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(tipoFormaPago.getDenominacion());
				oComboitem.setValue(tipoFormaPago);
				cmbTipoFormaPago.appendChild(oComboitem);
			}
			
		}
	}
	
	private void ventanaSolicitaPassword(int action){
		wndValidaUsuario = createVentanaSolicitaPassword(action);
		this.appendChild(wndValidaUsuario);
		wndValidaUsuario.setMode("modal");
	}
	
	/**
	 * Crea la ventana para validar con password 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Window createVentanaSolicitaPassword(final int action){
		final Window window = new Window("", "none", false);
		window.setWidth("250px");
		
		final Button btnAceptar = new Button("Aceptar");
		btnAceptar.setImage("/resources/mp_aceptarEnabled.png");
		
		Button btncancelar = new Button("Cancelar");
		btncancelar.setImage("/resources/mp_cancelarEnabled.png");
		
		Grid grid= new Grid();
		Columns columns= new Columns();
		Column column= new Column();
		Rows rows= new Rows();
		Row row= new Row();
		column.setWidth("75px");
		columns.appendChild(column);
				
//		grid.appendChild(columns);
		
		final Textbox txtPassrowd = new Textbox();
		txtPassrowd.setType("password");
		txtPassrowd.setWidth("130px");
		txtPassrowd.setMaxlength(20);
		txtPassrowd.setStyle("font-size:12px !important");
		
		Div div= new Div();
		div.setAlign("center");
		Label label= new Label("Debe de ingresar el Password de autorización para la emisión de la Cortersia");
		label.setSclass("label-size11-bold");
		div.appendChild(label);
		row=new Row();
		row.appendChild(div);
		rows.appendChild(row);
		
		label= new Label("------------------------------------------------------------------------------");
		label.setStyle("color:blue");
		row=new Row();
		row.appendChild(label);
		rows.appendChild(row);
		
		label= new Label("Password ");
		label.setSclass("label-size11");
		
		row=new Row();
		Hbox hbox= new Hbox();
		hbox.setAlign("center");
		hbox.appendChild(label);
		hbox.appendChild(txtPassrowd);
		row.appendChild(hbox);
		rows.appendChild(row);
		row=new Row();
		row.appendChild(new Separator());
		rows.appendChild(row);
		
		hbox= new Hbox();
		Separator separator= new Separator();
		separator.setWidth("30px");
		hbox.appendChild(separator);
		hbox.appendChild(btncancelar);
		hbox.appendChild(btnAceptar);
				
		grid.appendChild(rows);
		window.appendChild(grid);
		
		txtPassrowd.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				AutorizadorCortesia autorizadorCortesia=cmbAutorizadorCortesia.getSelectedItem().getValue();
				if(!(txtPassrowd.getText().trim().isEmpty())){
					if(txtPassrowd.getText().trim().equals(autorizadorCortesia.getPassword())){
						guardaCortesia((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue());
						window.onClose();
					}else{
						DlgMessage.information(Messages.getString("WndCortesia.information.passwordIncorrecto"));
						txtPassrowd.setText("");
					}
				}else{
					DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoPassword"));
					txtPassrowd.setFocus(true);
				}
			}
		});
		
		btnAceptar.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				AutorizadorCortesia autorizadorCortesia=cmbAutorizadorCortesia.getSelectedItem().getValue();
				if(!(txtPassrowd.getText().trim().isEmpty())){
					if(txtPassrowd.getText().trim().equals(autorizadorCortesia.getPassword())){
						btnAceptar.setDisabled(true);
						guardaCortesia((TipoFormaPago)cmbTipoFormaPago.getSelectedItem().getValue());
						window.onClose();
					}else{
						DlgMessage.information(Messages.getString("WndCortesia.information.passwordIncorrecto"));
						txtPassrowd.setText("");
					}
				}else{
					DlgMessage.information(Messages.getString("WndCortesia.information.noIngresoPassword"));
					txtPassrowd.setFocus(true);
				}
			}
		});
		
		btncancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				window.onClose();
			}
		});
			
		window.appendChild(hbox);
		return window;
	}
	
	public void buscarCliente(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new  TreeMap<String, Object>();
			criteriosBusqueda.put("numeroDocumento", txtRuc.getText().trim());
			List<Cliente> lstClientes = ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);
			oCliente = null;
			lblCliente.setStyle("color:#ff0000; font-weight:bold; font-size:10px");
			if(lstClientes.size()==1){
				oCliente = lstClientes.get(0);
				txtRazonSocial.setText(oCliente.getRazonSocial());
				/* Valida si el cliente tiene credito- Implementado 22/04/2014 */
				LineaCreditoCliente lineaCreditoCliente = ServiceLocator.getLineaCreditoClienteManager().validacionCreditoCliente(oCliente.getId());
				/*	Si tiene linea de credito*/
				if(lineaCreditoCliente != null) {
					/* Desactiva el button "Modificar cliente" */
					if(lineaCreditoCliente.getEsCanje().equals(Constantes.SI)){
						TIENE_CANJE = true;
						lblCliente.setStyle("color:#008db7; font-weight:bold; font-size:10px");
						lblCliente.setValue(Constantes.TIPCONVCLI_CREDITO_CANJE_PUBLICITARIO);						
					}else{
						TIENE_CANJE = false;
						lblCliente.setValue(Constantes.TIPCONVCLI_CREDITO_SIN_CANJE_PUBLICITARIO);
					}
				}else{
					TIENE_CANJE = false;
					lblCliente.setValue(Constantes.TIPCONVCLI_CREDITO_SIN_CANJE_PUBLICITARIO);
				}
			}else{
				TIENE_CANJE = false;
				txtRazonSocial.setText("");
				lblCliente.setValue("");
				DlgMessage.information(Messages.getString("Generales.information.noDatosEncontrados"), txtRuc);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}
}
