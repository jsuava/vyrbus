package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.cystesoft.vyrbus.model.bean.CarteraCliente;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.RangoSobregiro;
import com.cystesoft.vyrbus.model.bean.SolicitudCartera;
import com.cystesoft.vyrbus.model.bean.SolicitudClienteCredito;
import com.cystesoft.vyrbus.model.bean.TipoCobranza;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioRol;
import com.cystesoft.vyrbus.service.exceptions.AmpliacionCreditoNoValidoException;
import com.cystesoft.vyrbus.service.exceptions.CantidadTrabajadoresNullException;
import com.cystesoft.vyrbus.service.exceptions.ClienteException;
import com.cystesoft.vyrbus.service.exceptions.ContactoException;
import com.cystesoft.vyrbus.service.exceptions.DireccionFacturacionNullException;
import com.cystesoft.vyrbus.service.exceptions.DireccionNullException;
import com.cystesoft.vyrbus.service.exceptions.EmailNullException;
import com.cystesoft.vyrbus.service.exceptions.FormaCobranzaNullException;
import com.cystesoft.vyrbus.service.exceptions.LineaCreditoSolicitadaNullExeption;
import com.cystesoft.vyrbus.service.exceptions.MailIncorectoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoIncorrectoException;
import com.cystesoft.vyrbus.service.exceptions.NumeroDocumentoNullException;
import com.cystesoft.vyrbus.service.exceptions.OrigenNullException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.RazonSocialNullException;
import com.cystesoft.vyrbus.service.exceptions.RucDuplicadoException;
import com.cystesoft.vyrbus.service.exceptions.TelefonoNullException;
import com.cystesoft.vyrbus.service.exceptions.TipoConvenioNullException;
import com.cystesoft.vyrbus.service.exceptions.UbigeoNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 *
 * @author 	JOSE ABANTO
 * @date	16/02/2013
 *
 */

public class WndSolicitudCartera extends WndBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Window wndVerEstado = null;

	private Boolean selectTabHistorial=false;
	private Boolean esAmpliacion=false;
	private Boolean EditarClienteCartera=false;

	private Tab tabClientes;
	private Tab tabSolicitud;
	private Tab tabHistorialSolicitudes;

	/*CARTERA CLIENTES*/
	private Combobox cmbFuncionario;
	private Combobox cmbCliente;
	private Listbox listClientesCartera;
	private Button btnBuscaClienteCartera;
	private Button btnEditarClienteCartera;
	private Button btnExportar;
	private Textbox txtRubro;
	private Intbox ibxCantidadTrabajadores;
	private Hbox hbxEstadoLc;

	/* Cliente */
	private Radio rdPorRuc;
	private Textbox txtBuscarCliente;
	private Image imgBuscar;
	private Button btnNuevo;
	private Button btnEditar;
	private Longbox txtNumeroRuc;
	private Textbox txtRazonSocial;
	private Textbox txtDireccion;
	private Textbox txtIdUbigeo;
	private Textbox txtUbigeo;
	private Button btnBuscarUbigeo;
	private Textbox txtConApellidos;
	private Textbox txtConNombres;
	private Textbox txtConFinApellidos;
	private Textbox txtConFinNombres;
	private Textbox txtTelefono;
	private Textbox txtEmail;
	private Textbox txtDireccionFactuaracion;
	private Combobox cmbTipoCobranza;
	private Combobox cmbOrigen;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtMovil1;
	private Textbox txtMovil2;
	/* Solicitud*/
	private Groupbox gbxSolicitud;
	private Combobox cmbTipoConvenio;
	private Datebox dbxFechaSolicitud;
	private Button btnEnviarSolicitud;
	private Button btnNuevaSolicitud;
	private Button btnCancelarSolicitud;
	private Label lblAmpliacion;
	/* Cliente contado con DESCUENTO CORPORATIVO*/
	/* cliente CREDITO */
	private Grid grCredito;
	private Doublebox dbxLineaCreditoSolicita;
	private Doublebox dbxSobregiroPermitido;
	private Doublebox dbxMontoSobregiro;
	private Checkbox chbEsComisionable;
	private Checkbox chbesCanje;
	private Checkbox chbEsAmpliacion;
	private Textbox txtObservaciones;
	/*Historial solicitudes*/
	private Datebox dtFechaInicialHistSoli;
	private Datebox dtFechaFinalHistSoli;
	private Combobox cmbFuncionarioHistSoli;
	private Combobox cmbClienteHistSoli;
	private Listbox listHistirialSolicitudes;
	private Button btnAnulaSoli;
	private Button btnEditarSoli;
	private Button btnBuscarHistSoli;


	/*Otras variables*/
	private int action;
	private int actionSol;
	/*Class*/
	private Cliente cliente=null;
	private RangoSobregiro rangoSobregiro=null;

	/*constructor*/
	public WndSolicitudCartera(){
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		tabClientes=(Tab)this.getFellow("tabClientes");
		tabSolicitud=(Tab)this.getFellow("tabSolicitud");
		tabHistorialSolicitudes=(Tab)this.getFellow("tabHistorialSolicitudes");

		/*CARTERA CLIENTES*/
		cmbFuncionario=(Combobox)this.getFellow("cmbFuncionario");
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		listClientesCartera=(Listbox)this.getFellow("listClientesCartera");
		btnEditarClienteCartera=(Button)this.getFellow("btnEditarClienteCartera");
		btnExportar=(Button)this.getFellow("btnExportar");
		btnBuscaClienteCartera=(Button)this.getFellow("btnBuscaClienteCartera");
		hbxEstadoLc=(Hbox)this.getFellow("hbxEstadoLc");

		/*CLIENTE*/
		rdPorRuc=(Radio)this.getFellow("rdPorRuc");
		txtBuscarCliente=(Textbox)this.getFellow("txtBuscarCliente");
		imgBuscar=(Image)this.getFellow("imgBuscar");
		btnNuevo=(Button)this.getFellow("btnNuevo");
		btnEditar=(Button)this.getFellow("btnEditar");
		txtNumeroRuc=(Longbox)this.getFellow("txtNumeroRuc");
		txtRazonSocial=(Textbox)this.getFellow("txtRazonSocial");
		txtDireccion=(Textbox)this.getFellow("txtDireccion");
		txtIdUbigeo=(Textbox)this.getFellow("txtIdUbigeo");
		txtUbigeo=(Textbox)this.getFellow("txtUbigeo");
		btnBuscarUbigeo=(Button)this.getFellow("btnBuscarUbigeo");
		txtConApellidos=(Textbox)this.getFellow("txtConApellidos");
		txtConNombres=(Textbox)this.getFellow("txtConNombres");
		txtConFinApellidos=(Textbox)this.getFellow("txtConFinApellidos");
		txtConFinNombres=(Textbox)this.getFellow("txtConFinNombres");
		txtTelefono=(Textbox)this.getFellow("txtTelefono");
		txtEmail=(Textbox)this.getFellow("txtEmail");
		txtDireccionFactuaracion=(Textbox)this.getFellow("txtDireccionFactuaracion");
		cmbTipoCobranza=(Combobox)this.getFellow("cmbTipoCobranza");
		btnCancelar=(Button)this.getFellow("btnCancelar");
		btnGuardar=(Button)this.getFellow("btnGuardar");
		txtRubro=(Textbox)this.getFellow("txtRubro");
		ibxCantidadTrabajadores=(Intbox)this.getFellow("ibxCantidadTrabajadores");
		txtMovil1=(Textbox)this.getFellow("txtMovil1");
		txtMovil2=(Textbox)this.getFellow("txtMovil2");
		/* Solicitud*/
		gbxSolicitud=(Groupbox)this.getFellow("gbxSolicitud");
		cmbTipoConvenio=(Combobox)this.getFellow("cmbTipoConvenio");
		cmbTipoConvenio.setDisabled(true);
		dbxFechaSolicitud=(Datebox)this.getFellow("dbxFechaSolicitud");
		btnEnviarSolicitud=(Button)this.getFellow("btnEnviarSolicitud");
		btnNuevaSolicitud=(Button)this.getFellow("btnNuevaSolicitud");
		btnCancelarSolicitud=(Button)this.getFellow("btnCancelarSolicitud");
		lblAmpliacion=(Label)this.getFellow("lblAmpliacion");
		/* cliente CREDITO */
		grCredito=(Grid)this.getFellow("grCredito");
		dbxLineaCreditoSolicita=(Doublebox)this.getFellow("dbxLineaCreditoSolicita");
		dbxSobregiroPermitido=(Doublebox)this.getFellow("dbxSobregiroPermitido");
		cmbOrigen=(Combobox)this.getFellow("cmbOrigen");
		dbxMontoSobregiro=(Doublebox)this.getFellow("dbxMontoSobregiro");
		chbEsComisionable=(Checkbox)this.getFellow("chbEsComisionable");
		chbesCanje=(Checkbox)this.getFellow("chbesCanje");
		chbEsAmpliacion=(Checkbox)this.getFellow("chbEsAmpliacion");
		txtObservaciones=(Textbox)this.getFellow("txtObservaciones");
		listHistirialSolicitudes=(Listbox)this.getFellow("listHistirialSolicitudes");

		/*Historial de solicitudes*/
		dtFechaInicialHistSoli=(Datebox)this.getFellow("dtFechaInicialHistSoli");
		dtFechaFinalHistSoli=(Datebox)this.getFellow("dtFechaFinalHistSoli");
		cmbFuncionarioHistSoli=(Combobox)this.getFellow("cmbFuncionarioHistSoli");
		cmbClienteHistSoli=(Combobox)this.getFellow("cmbClienteHistSoli");
		btnAnulaSoli=(Button)this.getFellow("btnAnulaSoli");
		btnEditarSoli=(Button)this.getFellow("btnEditarSoli");
		btnBuscarHistSoli=(Button)this.getFellow("btnBuscarHistSoli");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		MyTime time= new MyTime();

		/*CARTERA CLIENTES*/
		UtilData.cargarFuncionarios(cmbFuncionario, true,null);
		UtilData.cargarClientesCartera(cmbCliente, true, getUsuario());

		Util.seleccionarValorItemCombo(Usuario.class, cmbFuncionario, getUsuario().getId());
		cmbCliente.setSelectedIndex(0);

		if(cmbFuncionario.getSelectedIndex()==-1){
			cmbFuncionario.setSelectedIndex(0);
		}


		/*SOLICITUD*/
		/* Cliente */
		rdPorRuc.setChecked(true);
		UtilData.enlazarUbigeo(txtIdUbigeo, txtUbigeo, btnBuscarUbigeo, null);
		UtilData.cargarDataCombo(cmbTipoCobranza, TipoCobranza.class, false);
		visibleGuardarCancelarCliente(false);
		disabledControlsCliente(true);
//		dbxBaseHistorica.setLocale(Locale.US);

		/* Solicitud */
		UtilData.cargarTipoConvenio(cmbTipoConvenio, false);
		dbxFechaSolicitud.setText(time.dateServer());
		disabledControlsContado_Credito(true);
		/* Cliente CREDITO*/
		grCredito.setVisible(false);
		UtilData.cargarOrigenCliente(cmbOrigen, false);
		dbxLineaCreditoSolicita.setLocale(Locale.US);
		dbxSobregiroPermitido.setLocale(Locale.US);
		/*Otros*/
		actionSol=Constantes.ACTION_NEW;

		/*Historial de solicitudes*/
		dtFechaInicialHistSoli.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		dtFechaFinalHistSoli.setValue(Constantes.FORMAT_DATE.parse(time.dateServer()));
		UtilData.cargarFuncionarios(cmbFuncionarioHistSoli, true,null);
		UtilData.cargarClientesCartera(cmbClienteHistSoli, true, getUsuario());

		Util.seleccionarValorItemCombo(Usuario.class, cmbFuncionarioHistSoli, getUsuario().getId());
		cmbClienteHistSoli.setSelectedIndex(0);

		if(cmbFuncionarioHistSoli.getSelectedIndex()==-1)
			cmbFuncionarioHistSoli.setSelectedIndex(0);
	
		/*Aplica acceso segun rol*/
		Util.disabledBtnBuscar(false,btnBuscaClienteCartera,accesoConsultar());
		Util.disabledBtnExportar(false,btnExportar, accesoExportar());
		Util.disabledBtnBuscar(false,btnBuscarHistSoli,accesoConsultar());
		disabledBuscarCliente(false);
		Util.disabledBtnNuevo(false, btnNuevaSolicitud, accesoNuevo());

		if(getRol().getId().intValue()==Constantes.ID_ROL_FUNCIONARIO){
			cmbFuncionario.setDisabled(true);
			cmbFuncionarioHistSoli.setDisabled(true);

			if (!(cmbFuncionario.getSelectedItem().getValue() instanceof Usuario)){
				cmbCliente.setDisabled(true);
				cmbClienteHistSoli.setDisabled(true);
			}
		}
	}

	/**
	 * Valida si el cliente ya esta asignado a la cartera de algún funcionario
	 * @return
	 */
	private CarteraCliente validacionClienteCartera(){
		TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
		criterioBusqueda.put("cliente", cliente);
		criterioBusqueda.put("estadoCartera", Constantes.ESTADOSOL_ACTIVA);
		ArrayList<CarteraCliente>list=ServiceLocator.getCarteraClienteManager().buscarPorX(criterioBusqueda, null);

		CarteraCliente carteraCliente=null;
		if(list.size()>0)
			carteraCliente=list.get(0);
		return carteraCliente;
	}

	/**
	 * Valida solicitudes pendentes por aprobar.
	 * @return : solicitudClienteCredito
	 * @throws Exception
	 */
	private SolicitudClienteCredito estadoUltimaSolicitado(Long idCliente) throws Exception{
		SolicitudClienteCredito  solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().validadSolicitudPendiente(idCliente);

		return solicitudClienteCredito;
	}


	/*Mantenimiento CLIENTE*/
	public void searchCliente() throws Exception {
		try{
			Util.disabledBtnEditar(true, btnEditar, accesoModificar());
			clearControlCliente();
			clearControlsSolicitud();
			cliente=null;

			if (!(txtBuscarCliente.getText().trim().isEmpty())){
				TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
				criterioBusqueda.put("numeroDocumento", txtBuscarCliente.getText().trim());
				criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				ArrayList<Cliente> arrayCliente=ServiceLocator.getClienteManager().buscarPorX(criterioBusqueda, null);
				if(arrayCliente.size()>0){
					cliente=arrayCliente.get(0);

					//Valida si el Cliente ya esta asignado ha algun funcionario
					SolicitudClienteCredito solicitudClienteCredito=estadoUltimaSolicitado(cliente.getId());
					if(solicitudClienteCredito!=null && solicitudClienteCredito.getNivelAprobacion()<3 &&!(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)) )
						throw new ClienteException(ClienteException.SOLICITUD_CARTERA);
					// Valida si el cliente, ya esta asignado a alguna cartera de un funcionario.
					CarteraCliente carteraCliente=validacionClienteCartera();
					if(carteraCliente!=null && carteraCliente.getUsuario().getId().intValue()!=getUsuario().getId().intValue())
						throw new ClienteException(ClienteException.ASIGNACION_CARTERA);
					else if(carteraCliente!=null && carteraCliente.getUsuario().getId().intValue()==getUsuario().getId().intValue()){
						if (solicitudClienteCredito.getEsAmpliacion().equals("S") &&  (solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)) )
							throw new ClienteException(ClienteException.ASIGNACION_CARTERA);
						else if (solicitudClienteCredito.getEsAmpliacion().equals("N") && !(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)) )
							throw new ClienteException(ClienteException.ASIGNACION_CARTERA);
					}
					CargarDatosCliente(cliente);

					txtBuscarCliente.setText("");
					Util.disabledBtnEditar(false, btnEditar, accesoModificar());
					disabledTipoConvenio(false);
				}else{
					cliente=null;
					clearControlCliente();
					throw new ClienteException(ClienteException.NO_EXISTE); // ClienteNoEncontradoException();
				}
			}

		}catch (ClienteException cl){
			if(cl.getTipo().intValue()==ClienteException.SOLICITUD_CARTERA){
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ClienteSolicitadoCartera"));
				txtBuscarCliente.setFocus(true);
				txtBuscarCliente.select();
			}else if (cl.getTipo().intValue()==ClienteException.ASIGNACION_CARTERA){
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ClienteAsignadoCartera"));
				txtBuscarCliente.setFocus(true);
				txtBuscarCliente.select();
			}else if(cl.getTipo().intValue()==ClienteException.NO_EXISTE){
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.clienteNoEncontrado"));
				txtBuscarCliente.setFocus(true);
				txtBuscarCliente.select();
			}else if (cl.getTipo().intValue()==ClienteException.CLIENTE_NULL){
				DlgMessage.information(Messages.getString("wndAutoprizadorCortesia.information.buscarCliente"));
				txtBuscarCliente.setFocus(true);
			}
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Carga los datos del cliente
	 * @param cliente: Class Cliente
	 * @throws Exception
	 */
	private void CargarDatosCliente(Cliente cliente) throws Exception{
		txtNumeroRuc.setText(cliente.getNumeroDocumento());
		txtRazonSocial.setText(cliente.getRazonSocial());
		txtDireccion.setText(cliente.getDireccion());
		txtUbigeo.setText(ServiceLocator.getUbigeoManager().ubicacionGeografica(cliente.getUbigeo()));
		String contacto=cliente.getContacto();
		if(contacto!=null){
			int indexAps=contacto.trim().indexOf(",");
			if(indexAps>0)
				txtConApellidos.setText(contacto.trim().substring(0, indexAps));
			txtConNombres.setText(contacto.trim().substring(indexAps+1, contacto.length()));
		}
		String contactoFinanciero =cliente.getContactoFinaciero();
		if(contactoFinanciero!=null){
			int indexNb=contactoFinanciero.trim().indexOf(",");
			if(indexNb>0)
				txtConFinApellidos.setText(contactoFinanciero.trim().substring(0, indexNb));
			txtConFinNombres.setText(contactoFinanciero.trim().substring(indexNb+1, contactoFinanciero.trim().length()));
		}
		txtMovil1.setText(cliente.getMovil1());
		txtMovil2.setText(cliente.getMovil2());

		txtTelefono.setText(cliente.getTelefonoFijo());
		txtEmail.setText(cliente.getEmail());
		txtIdUbigeo.setText(cliente.getUbigeo().getId());
		txtDireccionFactuaracion.setText(cliente.getDireccionFacturacion());
		txtRubro.setText(cliente.getRubro());
		ibxCantidadTrabajadores.setValue(cliente.getCantidadTrabajadores());
		if(cliente.getOrigen()!=null)
			Util.seleccionarValorItemCombobox(cmbOrigen, cliente.getOrigen());
		else cmbOrigen.setSelectedIndex(0);
		if(cliente.getTipoCobranza()!=null)
			if(cliente.getTipoCobranza().getId()!=null)
				Util.seleccionarValorItemCombo(TipoCobranza.class, cmbTipoCobranza, cliente.getTipoCobranza().getId());

	}

	/**
	 * Neuvo cliente
	 */
	public void onNewCliente(){
		action=Constantes.ACTION_NEW;
		cliente=null;
		clearControlCliente();
		Util.disabledBtnEditar(true, btnEditar, accesoModificar());
		Util.disabledBtnNuevo(true,btnNuevo,accesoNuevo());
		disabledControlsCliente(false);
		visibleGuardarCancelarCliente(true);
		txtNumeroRuc.setFocus(true);
		visibleSolicitud(false);
		disabledBuscarCliente(true);

		tabHistorialSolicitudes.setDisabled(true);
		tabClientes.setDisabled(true);
	}
	/**
	 * Editar Cliente
	 * @throws Exception
	 */
	public void onEditCliente() throws Exception{
		action=Constantes.ACTION_MODIFY;
		Util.disabledBtnEditar(true, btnEditar, accesoModificar());
		Util.disabledBtnNuevo(true,btnNuevo,accesoNuevo());
		disabledControlsCliente(false);
		visibleGuardarCancelarCliente(true);
		disabledBuscarCliente(true);
		visibleSolicitud(false);
		hbxEstadoLc.setVisible(false);
	}
	/**
	 *
	 * @param action
	 * @throws Exception
	 */
	public void onSaveCliente() throws Exception {
		try {
			if (txtNumeroRuc.getText().trim().isEmpty())
				throw new NumeroDocumentoNullException();
			else if (txtNumeroRuc.getText().trim().length()<11)
				throw new NumeroDocumentoIncorrectoException();
			else if (!(Util.validarRUC(txtNumeroRuc.getValue().toString().trim())))
				throw new NumeroDocumentoIncorrectoException();
			else if (txtRazonSocial.getText().trim().isEmpty())
				throw new RazonSocialNullException();
			else if (txtDireccion.getText().trim().isEmpty())
				throw new DireccionNullException();
			else if (txtUbigeo.getText().trim().isEmpty())
				throw new UbigeoNullException();
			else if (txtConApellidos.getText().trim().isEmpty())
				throw new ContactoException(ContactoException.APELLIDOS_NULL);
			else if (txtConNombres.getText().trim().isEmpty())
				throw new ContactoException(ContactoException.NOMBRES_NULL);
			else if (txtTelefono.getText().trim().isEmpty())
				throw new TelefonoNullException();
			else if (cmbOrigen.getSelectedIndex() <=0)
				throw new OrigenNullException();
			else if (ibxCantidadTrabajadores.getText().trim().isEmpty() || ibxCantidadTrabajadores.getValue()<=0)
				throw new CantidadTrabajadoresNullException();
			else if (!(txtEmail.getText().trim().isEmpty())){
				if (!UtilData.validateEmail(txtEmail.getText().trim()))
					throw new MailIncorectoException();
			}

			if (action==Constantes.ACTION_NEW)
				cliente = new Cliente();

			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setId(txtIdUbigeo.getText());

			cliente.setId(action==Constantes.ACTION_NEW? null: cliente.getId());
			cliente.setRazonSocial(txtRazonSocial.getText().trim().toUpperCase());
			cliente.setNumeroDocumento(txtNumeroRuc.getText().trim());
			cliente.setDireccion(txtDireccion.getText().trim().toUpperCase());
			cliente.setUbigeo(ubigeo);
			cliente.setTelefonoFijo(txtTelefono.getText());
			cliente.setEmail(txtEmail.getText());
			cliente.setKilometros(.00);
			cliente.setAgencia(getAgencia());
			cliente.setDireccionFacturacion(txtDireccionFactuaracion.getText());
			cliente.setOrigen((String) cmbOrigen.getSelectedItem().getValue());
			cliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
			cliente.setContacto(txtConApellidos.getText().trim().toUpperCase()+","+txtConNombres.getText().trim().toUpperCase());
			cliente.setContactoFinaciero(txtConFinApellidos.getText().trim().toUpperCase()+","+txtConFinNombres.getText().trim().toUpperCase());
			cliente.setMovil1(txtMovil1.getText().trim());
			cliente.setMovil2(txtMovil2.getText().trim());
			cliente.setCantidadTrabajadores(ibxCantidadTrabajadores.getValue());
			cliente.setRubro(txtRubro.getText().trim().toUpperCase());

			switch (action) {
				case Constantes.ACTION_NEW:
					UtilData.auditarRegistro(cliente,getUsuario(), Executions.getCurrent());
					ServiceLocator.getClienteManager().guardar(cliente);
					break;
				case Constantes.ACTION_MODIFY:
					UtilData.auditarRegistro(cliente, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getClienteManager().actualizar(cliente);
					break;
			}

			if(action==Constantes.ACTION_NEW){
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.saveCliente"));
				disabledTipoConvenio(false);
			}else{
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.updateCliente"));

			}

			/*Cuando el funcionario edita el cliente desde la lista de clientes en cartera (tabClientes)*/
			if(EditarClienteCartera){
				clearControlCliente();
				tabClientes.setSelected(true);
				EditarClienteCartera=false;
				tabClientes.setDisabled(false);
				tabHistorialSolicitudes.setDisabled(false);
				listClientesCartera();/*Carga nuevamente la lista de clientes con actualización realizada*/
			}else{
				/*Cuando se edita desde la Solicitud (TabSolicitud)*/
				Util.disabledBtnEditar(false, btnEditar, accesoModificar());
			}
			visibleGuardarCancelarCliente(false);
			disabledControlsCliente(true);
			disabledBuscarCliente(esAmpliacion);
			Util.disabledBtnNuevo(chbEsAmpliacion.isChecked(),btnNuevo,accesoNuevo());
			visibleSolicitud(true);


		}catch (CantidadTrabajadoresNullException ctnex){
			DlgMessage.information(Messages.getString("WndCliente.information.CantidadTrabajadoresNull"),ibxCantidadTrabajadores);
		}catch (NumeroDocumentoIncorrectoException ndiex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.incorrecRuc"),txtNumeroRuc);
		}catch (OrigenNullException ornex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.OrigenNullException"),cmbOrigen);
		}catch (MailIncorectoException mie){
			DlgMessage.information(Messages.getString("Generales.information.mailIncorrecto"),txtEmail);
		}catch (NumeroDocumentoNullException ndnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noDocumentoCliente"),txtNumeroRuc);
		}catch (RazonSocialNullException rsnex){
			DlgMessage.information(Messages.getString("WndVentaReserva.information.noRazonSocial"),txtRazonSocial);
		}catch (UbigeoNullException ugnex){
			DlgMessage.information(Messages.getString("WndAgencia.information.Ubigeo"),btnBuscarUbigeo);
		}catch (RucDuplicadoException rdex){
			DlgMessage.information(Messages.getString("WndCliente.information.RucDuplicado"),txtNumeroRuc);
		}catch (RazonSocialDuplicadoException rsdex){
			DlgMessage.information(Messages.getString("WndCliente.information.RazonSocialDuplicado"),txtRazonSocial);
		}catch (DireccionNullException dnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.DirecionNull"),txtDireccion);
		}catch (ContactoException cont){
			if(cont.getTipo()==ContactoException.APELLIDOS_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoApellidoNull"), txtConApellidos);
			else if (cont.getTipo()==ContactoException.NOMBRES_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoNombreNull"), txtConNombres);
		}catch (TelefonoNullException tnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.TelefonoNull"),txtTelefono);
		}catch (Exception ex){
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	/**
	 * Cancela la edicion el nuevo registro de un cliente
	 * @throws Exception
	 */
	public void onCancelCliente() throws Exception{
		if(EditarClienteCartera){/*Cuando el funcionario edita el cliente desde la lista de clientes en cartera (tabClientes)*/
			clearControlCliente();
			tabClientes.setSelected(true);
			EditarClienteCartera=false;
			tabClientes.setDisabled(false);
			tabHistorialSolicitudes.setDisabled(false);
			hbxEstadoLc.setVisible(true);
		}else{
			/*Cuando se edita el cliente desde la Solicitud (tabSolicitud)*/
			if(cliente!=null){
				Util.disabledBtnEditar(false, btnEditar, accesoModificar());
			}else {
				Util.disabledBtnEditar(true, btnEditar, accesoModificar());
				grCredito.setVisible(false);
				//Habilita o deshabilita el Button Enviar Solicitud
				Util.disabledBtnAceptar(true, btnEnviarSolicitud, accesoGrabar());
				cmbTipoConvenio.setDisabled(true);
			}
		}
		if(!(chbEsAmpliacion.isChecked())){
			Util.disabledBtnNuevo(false,btnNuevo,accesoNuevo());
			tabHistorialSolicitudes.setDisabled(false);
			tabClientes.setDisabled(false);
		}
		disabledControlsCliente(true);
		disabledBuscarCliente(false);
		visibleGuardarCancelarCliente(false);
		visibleSolicitud(true);
	}
	/**
	 * Activa o desactiva los controles del mantenimiento de clientes
	 * @param estado : (True)indica que los controles se desactivaran, (false)indica que los controles sera desactivados.
	 */
	private void disabledControlsCliente(boolean disabled){
		txtNumeroRuc.setReadonly(disabled);
		txtRazonSocial.setReadonly(disabled);
		txtDireccion.setReadonly(disabled);
		btnBuscarUbigeo.setDisabled(disabled);
		txtConApellidos.setReadonly(disabled);
		txtConNombres.setReadonly(disabled);
		txtConFinApellidos.setReadonly(disabled);
		txtConFinNombres.setReadonly(disabled);
		txtTelefono.setReadonly(disabled);
		txtEmail.setReadonly(disabled);
		txtDireccionFactuaracion.setReadonly(disabled);
		cmbOrigen.setDisabled(disabled);
		cmbTipoCobranza.setDisabled(disabled);
		txtRubro.setReadonly(disabled);
		ibxCantidadTrabajadores.setReadonly(disabled);
		txtMovil1.setReadonly(disabled);
		txtMovil2.setReadonly(disabled);
	}
	/**
	 * Limpia los controles del mantenimiento de clientes
	 */
	private void clearControlCliente(){
		txtNumeroRuc.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		txtIdUbigeo.setText("");
		txtUbigeo.setText("");
		txtConApellidos.setText("");
		txtConNombres.setText("");
		txtConFinApellidos.setText("");
		txtConFinNombres.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtDireccionFactuaracion.setText("");
		cmbTipoCobranza.setSelectedIndex(0);
		cmbOrigen.setSelectedIndex(0);
		txtRubro.setText("");
		ibxCantidadTrabajadores.setText("");
		txtMovil1.setText("");
		txtMovil2.setText("");
	}



	/**
	 * Activa o desactiva el botón Buscar cliente en la solicitud
	 * @param activar
	 */
	private void disabledBuscarCliente(boolean disabled){
		txtBuscarCliente.setText("");
		if(!(disabled) && accesoConsultar()){
			imgBuscar.setSrc("/resources/mp_buscarEnabled.png");
			imgBuscar.setStyle("cursor:pointer");
		}else{
			imgBuscar.setSrc("/resources/mp_buscarDisabled.png");
			imgBuscar.setStyle("cursor:default");
		}
		if(accesoConsultar())
			txtBuscarCliente.setDisabled(disabled);
		else
			txtBuscarCliente.setDisabled(true);
	}

	/**
	 * Activa o desactiva el button Cancelar solicitud
	 * @param activar : (true)se activara el button, (false) se desactivará el button
	 */
	private void disabledCancelarSolicitud(boolean disabled){
		if(!(disabled)){
			btnCancelarSolicitud.setImage("/resources/mp_cancelarEnabled.png");
			btnCancelarSolicitud.setStyle("cursor:pointer");
		}else{
			btnCancelarSolicitud.setImage("/resources/mp_cancelarDisabled.png");
			btnCancelarSolicitud.setStyle("cursor:default");
		}
		btnCancelarSolicitud.setDisabled(disabled);
	}

	/**
	 * Oculta o visualiza los buttons Guardar y cancelar del mantenimiento de clientes
	 * @param visible: (true) visualizar, (false) ocultar
	 */
	private void visibleGuardarCancelarCliente(boolean visible){
		btnGuardar.setVisible(visible);
		btnCancelar.setVisible(visible);
	}


/* ****SOLICITUD**********************************/
	/**
	 * Selecciona el tipo de Convenio
	 * @throws Exception
	 */
	public void onSelectTipoConvenio() throws Exception{
		try{
			grCredito.setVisible(false);
//			grContado.setVisible(false);
			clearControlsSolicitud();
			disabledControlsContado_Credito(true);
			//Habilita o deshabilita el Button Enviar Solicitud
			Util.disabledBtnAceptar(true, btnEnviarSolicitud, accesoGrabar());

			if(cliente!=null){
				if (cmbTipoConvenio.getSelectedIndex()>0){
					grCredito.setVisible(true);
					//Habilita o deshabilita el Button Enviar Solicitud
					Util.disabledBtnAceptar(false, btnEnviarSolicitud, accesoGrabar());
					disabledControlsContado_Credito(false);
					chbEsComisionable.setChecked(true);
				}
			}else
				throw new ClienteException(ClienteException.CLIENTE_NULL); // ClienteNullException();

		}catch (ClienteException cl){
			if(cl.getTipo().intValue()==ClienteException.CLIENTE_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.clienteNull"));
		}
	}


	/**
	 * Evento onChange del control LineaCreditoSolicita
	 */
	public void onChangeLineaCreditoSolicita()throws Exception{
		dbxMontoSobregiro.setValue(.00);
		dbxSobregiroPermitido.setValue(.00);
		if(!(chbesCanje.isChecked())){
			Double lineaCreditoSolicita=dbxLineaCreditoSolicita.getValue()!=null?dbxLineaCreditoSolicita.getValue():0.00;
			if(lineaCreditoSolicita>0){
				rangoSobregiro=ServiceLocator.getRangoSobregiroManager().buscarByLineaCredito(lineaCreditoSolicita);
				if(rangoSobregiro!=null){
					dbxSobregiroPermitido.setValue(rangoSobregiro.getPorcentajeSobregiroSugerida());
					dbxSobregiroPermitido.setReadonly(rangoSobregiro.esEditableSobregiro()?false:true);
				}else{
					DlgMessage.information(Messages.getString("wndSolicitudCartera.information.lineaCreditoMenorMinimo"),dbxLineaCreditoSolicita);
					dbxLineaCreditoSolicita.setValue(.00);
					return;
				}
			}

			//Calcula el monto del sobregiro
			onChangeSobregiro();
		}
	}

	/**
	 * Calcula el monto del sobregiro, solo es referencial no se guarda en la DB
	 * solo se guarda el porcentaje.
	 */
	public void onChangeSobregiro(){
		if(rangoSobregiro!=null){
			Double lineaSolicta=dbxLineaCreditoSolicita.getValue()!=null?dbxLineaCreditoSolicita.getValue():.00;
			Double porcentaSobregiro=dbxSobregiroPermitido.getValue()!=null?dbxSobregiroPermitido.getValue():.00;

			/*Valida el porcentaje maximo perimitido*/
			if(porcentaSobregiro>rangoSobregiro.getPorcentajeSobregiroMaximo()){
				dbxSobregiroPermitido.setValue(rangoSobregiro.getPorcentajeSobregiroSugerida());
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.porcentajeSobregiroMayorMaximo")+" "+Util.toNumberFormat(rangoSobregiro.getPorcentajeSobregiroMaximo(), 2)+" %",dbxSobregiroPermitido);
				return;
			}

			/*Valida el porcentaje minimo reuqerio*/
			if(porcentaSobregiro<rangoSobregiro.getPorcentajeSobregiroMinimo()){
				dbxSobregiroPermitido.setValue(rangoSobregiro.getPorcentajeSobregiroSugerida());
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.porcentajeSobregiroMenorMinimo")+" "+Util.toNumberFormat(rangoSobregiro.getPorcentajeSobregiroMinimo(), 2)+" %",dbxSobregiroPermitido);
				return;
			}


			Double montoSobregiro=lineaSolicta*(porcentaSobregiro/100);
			dbxMontoSobregiro.setValue(montoSobregiro);
		}else{
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.lineaCreditoMenorMinimo"),dbxLineaCreditoSolicita);
			dbxLineaCreditoSolicita.setValue(.00);
			return;
		}
	}

	/**
	 * Cuando se selecciona comisionable
	 */
	public void onChekedComisionable()throws Exception{
		if(chbEsComisionable.isChecked() ){
			chbesCanje.setChecked(false);
			chbesCanje.setDisabled(true);
			dbxSobregiroPermitido.setReadonly(false);

		}else if (!chbEsAmpliacion.isChecked()){
			chbesCanje.setDisabled(false);
		}
	}

	/**
	 * cuando selecciona por caje
	 */
	public void onChekedCanje()throws Exception{
		if(chbesCanje.isChecked()){
			chbEsComisionable.setChecked(false);
			chbEsComisionable.setDisabled(true);
			chbEsAmpliacion.setChecked(false);
			chbEsAmpliacion.setDisabled(true);
			dbxSobregiroPermitido.setValue(0);
			dbxMontoSobregiro.setValue(0);
			dbxSobregiroPermitido.setReadonly(true);
		}else{
			chbEsComisionable.setDisabled(false);
//			chbEsAmpliacion.setDisabled(false);
			dbxSobregiroPermitido.setDisabled(false);
		}

		onChangeLineaCreditoSolicita();
	}

	/**
	 * cuando selecciona ampliacion
	 */
	public void onChekedAmpliacion(){
		if(chbEsAmpliacion.isChecked()){
			chbesCanje.setChecked(false);
			chbesCanje.setDisabled(true);
		}else{
			chbesCanje.setDisabled(false);
		}
	}

	/**
	 * Activa o desactiva el combo tipo de convenio.
	 * @param disabled: (true) desactiva, (false)activa
	 * @throws Exception
	 */
	private void disabledTipoConvenio(boolean disabled) throws Exception{
//		cmbTipoConvenio.setSelectedIndex(0);
		onSelectTipoConvenio();
		clearControlsSolicitud();

		if(!(disabled)){
			cmbTipoConvenio.setFocus(true);
			cmbTipoConvenio.select();
		}
		cmbTipoConvenio.setDisabled(disabled);
	}

	/**
	 * Nueva solicitud
	 * @throws Exception
	 */
	public void onClikNuevaSolicitud() throws Exception{
		actionSol=Constantes.ACTION_NEW;
		chbEsComisionable.setChecked(true);
		clearControlCliente();
		clearControlsSolicitud();
		disabledBuscarCliente(false);
		Util.disabledBtnNuevo(false,btnNuevo,accesoNuevo());
		Util.disabledBtnEditar(true, btnEditar, accesoModificar());
		grCredito.setVisible(false);
		cmbTipoConvenio.setSelectedIndex(0);
		txtBuscarCliente.setFocus(true);
		selectTabHistorial=false;
		esAmpliacion=false;
		cliente=null;
		lblAmpliacion.setValue("AMPLIACIÓN");
	}

	/**
	 * Activa o desactiva controls contado y credito.
	 * @param disabled	: (true) desactiva, (false)activa
	 */
	private void disabledControlsContado_Credito(boolean disabled){
		dbxLineaCreditoSolicita.setDisabled(disabled);
		dbxSobregiroPermitido.setDisabled(disabled);
		chbEsComisionable.setDisabled(disabled);
		chbesCanje.setDisabled(disabled);
		txtObservaciones.setDisabled(disabled);
	}

	/**
	 * Genera Solicitudad
	 * @throws Exception
	 */
	public void onSaveSolicitud() throws Exception{
		try{
			/*Valida datos del cliente*/
			if (cliente==null)
				throw new ClienteException(ClienteException.CLIENTE_NULL); // ClienteNullException();
			else if (txtDireccion.getText().trim().isEmpty())
				throw new DireccionNullException();
			else if (txtConApellidos.getText().trim().isEmpty())
				throw new ContactoException(ContactoException.APELLIDOS_NULL);
			else if (txtConNombres.getText().trim().isEmpty())
				throw new ContactoException(ContactoException.NOMBRES_NULL);
			else if (txtTelefono.getText().trim().isEmpty())
				throw new TelefonoNullException();
			else if (cmbOrigen.getSelectedIndex() <=0)
				throw new OrigenNullException();

			/*Valida datos de la solicitud*/
			else if (cmbTipoConvenio.getSelectedIndex() <=0 )
				throw new TipoConvenioNullException();
			else if (cmbTipoConvenio.getSelectedItem().getValue().equals(Constantes.TIPCON_CREDITO)){
				if (txtDireccionFactuaracion.getText().trim().isEmpty())
					throw new DireccionFacturacionNullException();
				else if (!(cmbTipoCobranza.getSelectedItem().getValue() instanceof TipoCobranza))
					throw new FormaCobranzaNullException();
				else if (dbxLineaCreditoSolicita.getText().trim().isEmpty() || dbxLineaCreditoSolicita.getValue()<=0)
					throw new LineaCreditoSolicitadaNullExeption();
				if(chbEsAmpliacion.isChecked() && !chbesCanje.isChecked()){
					/*Cuando es una ampliacion*/
					/*Valida linea de credito a solicitar sea mayor a la actual*/
					LineaCreditoCliente  lineaCreditoCliente= ServiceLocator.getLineaCreditoClienteManager().lineaCreditoCliente(cliente.getId());
					if(lineaCreditoCliente!=null){
						Double creditoAprobada=lineaCreditoCliente.getLineaCreditoAprobada();
						Double creditoSolicita=dbxLineaCreditoSolicita.getValue();
						if(creditoSolicita <= creditoAprobada)
							throw new AmpliacionCreditoNoValidoException();
					}
				}
			}

			//Valida si el funcionario tiene un E-Mail configurado.
			if(!UtilData.validateUserMail(getUsuario()))
				throw new EmailNullException(EmailNullException.EMAIL_PERSONAL);

			Messagebox.show(Messages.getString("wndSolicitudCartera.question.ConfirmaEnvioSolicitud"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){

						//VALIDA SI TIENE ALGUNA SOLICITUD DE CREDITO RECHASADA.
						if(!(chbEsAmpliacion.isChecked())){
							SolicitudClienteCredito  solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().validadSolicitudPendiente(cliente.getId());
							if(solicitudClienteCredito!=null && solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)){
								/* Validamos que el Cliente este en la cartera del funcionario*/
								CarteraCliente carteraCliente=validacionClienteCartera();
								if(carteraCliente!=null && carteraCliente.getUsuario().getId().intValue()==getUsuario().getId().intValue()){
									/* Inactiva la cartera asociada a la solicitud rechasada. */
									carteraCliente.setEstadoCartera(Constantes.ESTADOSOL_INACTIVA);
									UtilData.auditarRegistro(carteraCliente, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getCarteraClienteManager().actualizar(carteraCliente);
								}
							}else if(solicitudClienteCredito!=null){
								DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ClienteAsignadoCartera"));
								return;
							}
						}


						/*Guarda Solicitud cartera*/
						SolicitudCartera solicitudCartera= insertarSolicitudCartera();
						/*Inserta solicitud cliente credito (Tambien se insertan los contados)*/
						insertarSolicitudClienteCredito(solicitudCartera);

						disabledBuscarCliente(true);
						Util.disabledBtnNuevo(true,btnNuevo,accesoNuevo());
						Util.disabledBtnEditar(true, btnEditar, accesoModificar());
						cmbTipoConvenio.setDisabled(true);
						disabledControlsContado_Credito(true);
						Util.disabledBtnNuevo(false, btnNuevaSolicitud, accesoNuevo());
						//Habilita o deshabilita el Button Enviar Solicitud
						Util.disabledBtnAceptar(true, btnEnviarSolicitud, accesoGrabar());

						tabClientes.setDisabled(false);
						tabHistorialSolicitudes.setDisabled(false);

						if(actionSol==Constantes.ACTION_NEW)
							DlgMessage.information(Messages.getString("wndSolicitudCartera.information.SaveSolicitud"));
						else
							DlgMessage.information(Messages.getString("wndSolicitudCartera.information.UpdateSolicitud"));
					}
				}
			});

		}catch (EmailNullException emx){
			if(emx.getTipoEmail().intValue()==EmailNullException.EMAIL_PERSONAL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.noMailFunc"));
		}catch(AmpliacionCreditoNoValidoException acnvex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.AmpliacionCreditoNoValido"));
			dbxLineaCreditoSolicita.setFocus(true);
		}catch (FormaCobranzaNullException fcnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.FormaCobranzaNull"));
			cmbTipoCobranza.setFocus(true);
		}catch (DireccionFacturacionNullException dfnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.DireccionFacturacionNull"));
			txtDireccionFactuaracion.setFocus(true);
		}catch (OrigenNullException onex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.OrigenNullException"));
			cmbOrigen.setFocus(true);
		}catch (LineaCreditoSolicitadaNullExeption lcsnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.LineaCreditoSolicitadaNull"));
			dbxLineaCreditoSolicita.setFocus(true);
		}catch (ClienteException cl){
			if(cl.getTipo().intValue()==ClienteException.CLIENTE_NULL){
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.clienteNull"));
				txtBuscarCliente.setFocus(true);
			}
		}catch (TipoConvenioNullException tcnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.TipoConvenioNull"));
			cmbTipoConvenio.setFocus(true);
		}catch (DireccionNullException dnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.DirecionNull"));
			btnBuscarUbigeo.setFocus(true);
		}catch (ContactoException cont){
			if(cont.getTipo()==ContactoException.APELLIDOS_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoApellidoNull"), txtConApellidos);
			else if (cont.getTipo()==ContactoException.NOMBRES_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoNombreNull"), txtConNombres);
			else if (cont.getTipo()==ContactoException.FINANCIERO_APELLIDOS_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoFinacieroApellidosNull"), txtConFinApellidos);
			else if (cont.getTipo()==ContactoException.FINANCIERO_NOMBRES_NULL)
				DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ContactoFinacieroNombreNull"), txtConFinApellidos);
		}catch (TelefonoNullException tlnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.TelefonoNull"));
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Inserta solicitud Cartera
	 * @return	: solicitudCaretra
	 * @throws Exception
	 */
	private SolicitudCartera insertarSolicitudCartera() throws Exception{
		SolicitudCartera solicitudCartera= new SolicitudCartera();
		solicitudCartera.setUsuario(getUsuario());
		solicitudCartera.setCliente(cliente);
		solicitudCartera.setFechaSolicitud(new Date());
		solicitudCartera.setEstadoSolicitud(Constantes.ESTADOSOL_ACTIVA);
		solicitudCartera.setNivelAprobacion(0);
		solicitudCartera.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(solicitudCartera,getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudCarteraManager().guardar(solicitudCartera);

		return solicitudCartera;
	}

	/**
	 * Insertar Solicitud cliente credito (También se insertar los contados)
	 * @param solicitudCartera: Class solicitudCartera
	 * @throws Exception
	 */
	private void insertarSolicitudClienteCredito(SolicitudCartera solicitudCartera) throws Exception{

		SolicitudClienteCredito solicitudClienteCredito=new SolicitudClienteCredito();
		solicitudClienteCredito.setSolicitudCartera(solicitudCartera);
		if(cmbTipoCobranza.getSelectedItem().getValue() instanceof TipoCobranza && cmbTipoConvenio.getSelectedItem().getValue().equals(Constantes.TIPCON_CREDITO))
			solicitudClienteCredito.setTipoCobranza((TipoCobranza) cmbTipoCobranza.getSelectedItem().getValue());

		solicitudClienteCredito.setLineaCreditoSolicitada(dbxLineaCreditoSolicita.getValue()!=null? dbxLineaCreditoSolicita.getValue():0);
		solicitudClienteCredito.setLineaCreditoAprobada(dbxLineaCreditoSolicita.getValue()!=null? dbxLineaCreditoSolicita.getValue():0);
		solicitudClienteCredito.setSobregiro(dbxSobregiroPermitido.getValue()!=null?dbxSobregiroPermitido.getValue(): 0);
		solicitudClienteCredito.setNumeroControl("DEMO");
		solicitudClienteCredito.setNivelAprobacion(0);
		solicitudClienteCredito.setFechaSolicitud(dbxFechaSolicitud.getValue());
		solicitudClienteCredito.setEstadoSolicitud(Constantes.ESTADOSOLCAR_EN_ESPERA);
		solicitudClienteCredito.setEsCanje(chbesCanje.isChecked()?Constantes.SI: Constantes.NO);
		solicitudClienteCredito.setEsComisionable(chbEsComisionable.isChecked()?Constantes.SI:Constantes.NO);
		solicitudClienteCredito.setEsAmpliacion(chbEsAmpliacion.isChecked()?Constantes.SI:Constantes.NO);
		solicitudClienteCredito.setObservaciones(txtObservaciones.getText().trim().toUpperCase());
		solicitudClienteCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(solicitudClienteCredito,getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudClienteCreditoManager().guardar(solicitudClienteCredito);
		/*Actualiza solicitud cliente credito con el numero de control*/
		solicitudClienteCredito.setNumeroControl(controlNumber(solicitudClienteCredito.getId()));
		ServiceLocator.getSolicitudClienteCreditoManager().actualizar(solicitudClienteCredito);
	}

	/**
	 * Oculta o visualiza el grupo solicitud.
	 * @param visible : (true)visualizar, (false)ocultar
	 */
	private void visibleSolicitud(boolean visible){
		gbxSolicitud.setVisible(visible);
		btnEnviarSolicitud.setVisible(visible);
		btnNuevaSolicitud.setVisible(visible);
		//btnCerrar.setVisible(visible);
		btnCancelarSolicitud.setVisible(visible);
	}

	/**
	 * Limpia controles de solicitud
	 */
	private void clearControlsSolicitud(){
		dbxLineaCreditoSolicita.setText("");
		dbxSobregiroPermitido.setText("");
		dbxMontoSobregiro.setText("");
		chbesCanje.setChecked(false);
		chbEsComisionable.setChecked(false);
		chbEsAmpliacion.setChecked(false);
		txtObservaciones.setText("");
	}

	/**
	 * Genera el numero de control para el registro de la solicitud.
	 * @param valor	: Numero hexadecimal con el cual se formara el numero de control.
	 * @return String
	 */
	public static String controlNumber(Long valor){
		String nControl = Util.generateControlNumber(Util.decimalToHexadecimal(valor));
		nControl ="T"+nControl.substring(nControl.length()-11);
		return nControl;
	}

	/**
	 * Cancela solicitud
	 */
	public void onClickCancelarSolicitud(){
		if(selectTabHistorial){
			tabHistorialSolicitudes.setSelected(true);
		}else{
			tabClientes.setSelected(true);
		}
		clearControlCliente();
		clearControlsSolicitud();
		disabledControlsCliente(true);
		disabledControlsContado_Credito(true);
		//Habilita o deshabilita el Button Enviar Solicitud
		Util.disabledBtnAceptar(true, btnEnviarSolicitud, accesoGrabar());
		Util.disabledBtnEditar(true, btnEditar, accesoModificar());
		Util.disabledBtnNuevo(false,btnNuevo,accesoNuevo());
		disabledBuscarCliente(false);
		tabClientes.setDisabled(false);
		tabHistorialSolicitudes.setDisabled(false);
		grCredito.setVisible(false);
		selectTabHistorial=false;
		hbxEstadoLc.setVisible(true);
		lblAmpliacion.setValue("AMPLIACIÓN");
	}

	/*CARTERA CLIENTES*/
	public void listClientesCartera(){
		Util.disabledBtnEditar(true, btnEditarClienteCartera, accesoModificar());
		Integer idFuncionario=null;
		Long idCliente=null;
		if(cmbFuncionario.getSelectedItem().getValue() instanceof Usuario)
			idFuncionario=((Usuario)cmbFuncionario.getSelectedItem().getValue()).getId();
		if(cmbCliente.getSelectedItem().getValue() instanceof Cliente)
			idCliente=((Cliente)cmbCliente.getSelectedItem().getValue()).getId();

		List<CarteraCliente>listCarteraCliente=ServiceLocator.getCarteraClienteManager().buscarClientesCartera(idFuncionario, idCliente);

		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listClientesCartera);
		String colorInactivo=";color:red";
		String colorEnProceso=";color:#59B808";

		for (CarteraCliente carteraCliente: listCarteraCliente) {
			x++;
			Cliente cliente=carteraCliente.getCliente();
			String estadoLcColor="";
			if(carteraCliente.getCliente().getEstadoRegistro().equals("POR APROB"))
				estadoLcColor=colorEnProceso;
			else if(carteraCliente.getCliente().getEstadoRegistro().equals(Constantes.LABEL_ESTADOSOL_INACTIVA_DESC))
				estadoLcColor=colorInactivo;

			item=new Listitem();
			cell=new Listcell(cliente.getNumeroDocumento());
			cell.setStyle("font-size:11px !important "+estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			cell.setStyle(estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(carteraCliente.getFechaAsignacion()));
			cell.setStyle("font-size:11px !important "+estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(cliente.getTipo());
			cell.setStyle(estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(carteraCliente.getLineaCreditoCliente()!=null?Util.toNumberFormat(carteraCliente.getLineaCreditoCliente().getLineaCreditoAprobada(),2):"0.00");
			cell.setStyle("font-size:11px !important;text-align: right; "+estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell("% "+(carteraCliente.getLineaCreditoCliente()!=null?Util.toNumberFormat(carteraCliente.getLineaCreditoCliente().getSobregiro(),2):"0.00"));
			cell.setStyle("font-size:11px !important; "+estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(carteraCliente.getLineaCreditoCliente()!=null?Util.toNumberFormat(carteraCliente.getLineaCreditoCliente().getSaldo(),2):"0.00");
			cell.setStyle("font-size:11px !important;text-align: right; "+estadoLcColor);
			item.appendChild(cell);
			cell=new Listcell(carteraCliente.getUsuario().toString());
			cell.setStyle(estadoLcColor);
			item.appendChild(cell);

			final Toolbarbutton button= new Toolbarbutton("Ampliación");
			button.setTooltiptext("Solicitud ampliación de Linea de Crédito.");
			//Valida si es canje
			if(carteraCliente.getLineaCreditoCliente()!=null && carteraCliente.getLineaCreditoCliente().getEsCanje().equals(Constantes.SI)){
				button.setLabel("Extensión");
				button.setTooltiptext("Solicitud extensión de Canje publicitario.");
			}

			button.setId(String.valueOf(x-1));
			button.setStyle("color:blue; font-size: 8.5px !important");
//			if(cliente.getTipo().equals(Constantes.TIPCON_CREDITO_DESC) && cliente.getEstadoRegistro().equals(Constantes.LABEL_ESTADOSOL_ACTIVA_DESC))
			if(cliente.getEstadoRegistro().equals(Constantes.LABEL_ESTADOSOL_ACTIVA_DESC))
				button.setDisabled(false);
			else button.setDisabled(true);
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);

			//Solicitar ampliacion de Line ade Credito.
			button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					try{
						Listitem listitem=listClientesCartera.getItemAtIndex(Integer.valueOf(button.getId()));
						CarteraCliente carteraCliente=listitem.getValue();
						Cliente cliente= ServiceLocator.getClienteManager().buscarPorId(carteraCliente.getCliente().getId());

						/*Valida si el cliente tiene alguna solicitud pendiente por aprobar*/
						SolicitudClienteCredito solicitudClienteCredito=estadoUltimaSolicitado(cliente.getId());
						if(solicitudClienteCredito!=null && solicitudClienteCredito.getNivelAprobacion()<3 && !(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)) ){
							DlgMessage.information(Messages.getString("wndSolicitudCartera.information.ClienteSolicitadoCartera"));
							return;
						}

						CargarDatosCliente(cliente);

						Util.seleccionarValorItemCombo(TipoCobranza.class, cmbTipoCobranza, carteraCliente.getCliente().getTipoCobranza().getId());
						Util.seleccionarValorItemCombobox(cmbTipoConvenio, Constantes.TIPCON_CREDITO);
						if(carteraCliente.getSolicitudCartera().getEsComisionable().endsWith(Constantes.SI))
							chbEsComisionable.setChecked(true);
						else chbEsComisionable.setChecked(false);
						WndSolicitudCartera.this.cliente=cliente;

						disabledCancelarSolicitud(false);
						disabledControlsContado_Credito(false);
						Util.disabledBtnAceptar(false, btnEnviarSolicitud, accesoGrabar());
						Util.disabledBtnEditar(false, btnEditar, accesoModificar());
						disabledBuscarCliente(true);
						tabHistorialSolicitudes.setDisabled(true);
						tabClientes.setDisabled(true);
						Util.disabledBtnNuevo(true,btnNuevo,accesoNuevo());

						chbEsAmpliacion.setChecked(true);
						chbEsAmpliacion.setDisabled(true);
						chbesCanje.setDisabled(true);
						grCredito.setVisible(true);
						hbxEstadoLc.setVisible(false);

						esAmpliacion=true;
						selectTabHistorial=false;
						tabSolicitud.setSelected(true);
						dbxLineaCreditoSolicita.setFocus(true);

						dbxLineaCreditoSolicita.setText("");
						dbxSobregiroPermitido.setText("");
						dbxMontoSobregiro.setText("");

						/*Valida si es una extencion de canje publictario*/
						if(carteraCliente.getLineaCreditoCliente()!=null && carteraCliente.getLineaCreditoCliente().getEsCanje().equals(Constantes.SI)){
							chbEsComisionable.setChecked(false);
							chbEsComisionable.setDisabled(true);
							chbesCanje.setChecked(true);
							lblAmpliacion.setValue("EXTENSIÓN DE CANJE PUBLICITARIO");
						}else
							lblAmpliacion.setValue("AMPLIACIÓN");
					}catch (Exception e) {
						DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
						e.printStackTrace();
					}
				}
			});

			Util.disabledBtnEditar(false, btnEditarClienteCartera, true);
			item.setValue(carteraCliente);
			listClientesCartera.appendChild(item);
		}
	}

	/**
	 * Permite al funcionario editar el cliente desde la lista de cartera de clientes.
	 * @throws Exception
	 *
	 */
	public void onClickEditarClienteCartera() throws Exception{
		if(listClientesCartera.getSelectedIndex()>=0){
			EditarClienteCartera=true;
			cliente=new Cliente();
			cliente=ServiceLocator.getClienteManager().buscarPorId(((CarteraCliente)listClientesCartera.getSelectedItem().getValue()).getCliente().getId());
			cliente.setTipoCobranza(((CarteraCliente)listClientesCartera.getSelectedItem().getValue()).getCliente().getTipoCobranza());
			cliente.setTipo(((CarteraCliente)listClientesCartera.getSelectedItem().getValue()).getCliente().getTipo());
			if(cliente.getTipo().equals(Constantes.TIPCON_CREDITO_DESC))
				Util.seleccionarValorItemCombobox(cmbTipoConvenio, Constantes.TIPCON_CREDITO);
			CargarDatosCliente(cliente);
			onEditCliente();
			tabSolicitud.setSelected(true);
			tabClientes.setDisabled(true);
			tabHistorialSolicitudes.setDisabled(true);
		}
	}

	/**
	 * Exporta Cartera de clientes al Excel
	 */
	public void exportarCartera(){
		String nombreArchivo=Util.adjuntarFechaHoraExportacion("Cartera Clientes");
        Filedownload.save(Util.generaTablaHtml(listClientesCartera), "application/vnd.ms-excel", nombreArchivo+".xls");
//        Filedownload.save(Util.generaTablaHtml(listClientesCartera), "application/pdf", nombreArchivo+".pdf");
	}


	/*HISTORIAL DE SOLICITUDES*/
	public void listHistorial() throws Exception{
		Util.limpiarListbox(listHistirialSolicitudes);
		disabledEditarSolicitud(true);
		disabledAnularSolicitud(true);

		String fechaInicial=Constantes.FORMAT_DATE.format(dtFechaInicialHistSoli.getValue());
		String fechaFinal=Constantes.FORMAT_DATE.format(dtFechaFinalHistSoli.getValue());
		Integer idFuncionario=null; Long idCliente=null;
		if(cmbFuncionarioHistSoli.getSelectedItem().getValue() instanceof Usuario)
			idFuncionario=((Usuario)cmbFuncionarioHistSoli.getSelectedItem().getValue()).getId();
		if(cmbClienteHistSoli.getSelectedItem().getValue() instanceof Cliente)
			idCliente=((Cliente)cmbClienteHistSoli.getSelectedItem().getValue()).getId();

		List<SolicitudClienteCredito> lista=ServiceLocator.getSolicitudClienteCreditoManager().buscarHistorialSolicitudesCarteraCredito(fechaInicial, fechaFinal, idFuncionario, idCliente);

		Listitem item=null;
		Listcell cell=null;
		int x=0;

		for (SolicitudClienteCredito solicitudClienteCredito: lista){
			Usuario funcionario= solicitudClienteCredito.getSolicitudCartera().getUsuario();
			Cliente cliente=solicitudClienteCredito.getSolicitudCartera().getCliente();

			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(funcionario.toString());
			item.appendChild(cell);
			cell=new Listcell(cliente.getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(solicitudClienteCredito.getSolicitudCartera().getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(solicitudClienteCredito.getEstadoSolicitud());
			item.appendChild(cell);
			cell=new Listcell(cliente.getTipo());
			item.appendChild(cell);

			final Toolbarbutton btnVerEstado= new Toolbarbutton("Ver estado");
			btnVerEstado.setZindex(solicitudClienteCredito.getSolicitudCartera().getId().intValue());
			btnVerEstado.setAttribute("TIPO", cliente.getTipo());
			btnVerEstado.setStyle("color:blue; font-size: 8.5px !important");

			cell=new Listcell();
			cell.appendChild(btnVerEstado);
			item.appendChild(cell);

			btnVerEstado.addEventListener(Events.ON_CLICK, new  EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					verEstado(Long.valueOf(btnVerEstado.getZindex()), btnVerEstado.getAttribute("TIPO").toString());
				}
			});

			item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Listitem listitem=listHistirialSolicitudes.getItemAtIndex(listHistirialSolicitudes.getSelectedIndex());
					SolicitudClienteCredito  solicitudClienteCredito=listitem.getValue();
					Cliente cliente=ServiceLocator.getClienteManager().buscarPorId(solicitudClienteCredito.getSolicitudCartera().getCliente().getId());
					cliente.setTipo(solicitudClienteCredito.getSolicitudCartera().getCliente().getTipo());
					if(solicitudClienteCredito.getSolicitudCartera().getCliente().getTipoCobranza()!=null)
						cliente.setTipoCobranza(solicitudClienteCredito.getSolicitudCartera().getCliente().getTipoCobranza());
					CargarDatosCliente(cliente);
					Util.disabledBtnNuevo(true,btnNuevo,accesoNuevo());

					TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
					List<String> criteriosOrdenar=new ArrayList<>();
					criteriosOrdenar.add("solicitudCartera");
					criterioBusqueda.put("solicitudCartera", solicitudClienteCredito.getSolicitudCartera());
					List<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorX(criterioBusqueda, criteriosOrdenar);
					solicitudClienteCredito=new SolicitudClienteCredito();
					solicitudClienteCredito=list.get(0);

					Util.seleccionarValorItemCombobox(cmbTipoConvenio, Constantes.TIPCON_CREDITO);
					grCredito.setVisible(true);
					dbxLineaCreditoSolicita.setValue(solicitudClienteCredito.getLineaCreditoSolicitada());
					dbxSobregiroPermitido.setValue(solicitudClienteCredito.getSobregiro());
//					onChangeSobregiro();
					dbxMontoSobregiro.setValue(dbxLineaCreditoSolicita.getValue()*(dbxSobregiroPermitido.getValue()/100));
					if(solicitudClienteCredito.getEsCanje().equals(Constantes.SI))
						chbesCanje.setChecked(true);
					else if (solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI))
						chbEsAmpliacion.setChecked(true);
					if(solicitudClienteCredito.getEsComisionable().equals(Constantes.SI))
						chbEsComisionable.setChecked(true);
					else chbEsComisionable.setChecked(false);
					txtObservaciones.setText(solicitudClienteCredito.getObservaciones());

					selectTabHistorial=true;
					tabClientes.setDisabled(true);
					tabHistorialSolicitudes.setDisabled(true);
					tabSolicitud.setSelected(true);
					disabledBuscarCliente(true);
				}
			});

			item.setValue(solicitudClienteCredito);
			listHistirialSolicitudes.appendChild(item);
		}
	}



	public void listHistirialSolicitudes_onSelect(){
		Listitem listitem=listHistirialSolicitudes.getItemAtIndex(listHistirialSolicitudes.getSelectedIndex());
		SolicitudClienteCredito  solicitudClienteCredito=listitem.getValue();
		if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.LABEL_ESTADOSOLCAR_EN_ESPERA_DESC)){
			disabledEditarSolicitud(false);
			disabledAnularSolicitud(false);
		}else{
			disabledEditarSolicitud(true);
			disabledAnularSolicitud(true);
		}
	}




	public void onClickAnulaSolicitud() throws Exception{
		Messagebox.show(Messages.getString("wndSolicitudCartera.question.ConfirmaAnulacionSolicitud"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception {
				if(e.getName().equals("onYes")){

					Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse((new MyTime().dateServer()));

					Listitem itemHist=listHistirialSolicitudes.getItemAtIndex(listHistirialSolicitudes.getSelectedIndex());
					Long idSolicitudCartera=((SolicitudClienteCredito)itemHist.getValue()).getSolicitudCartera().getId();
					Long idSolicitudClienteCredito=((SolicitudClienteCredito)itemHist.getValue()).getId();
					SolicitudCartera  solicitudCartera= ServiceLocator.getSolicitudCarteraManager().buscarPorId(idSolicitudCartera);
					solicitudCartera.setFechaAnulacion(fecha);
					solicitudCartera.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);

					/*Anula solicitud cartera*/
					UtilData.auditarRegistro(solicitudCartera, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getSolicitudCarteraManager().actualizar(solicitudCartera);

					/*Anula solicitud credito*/
					SolicitudClienteCredito solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(idSolicitudClienteCredito);
					solicitudClienteCredito.setFechaAnulacion(fecha);
					solicitudClienteCredito.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
					UtilData.auditarRegistro(solicitudClienteCredito, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getSolicitudClienteCreditoManager().actualizar(solicitudClienteCredito);

					listHistorial();
				}
			}
		});
	}

	/**
	 * Activa o desactiva o button Editar solicitud
	 * @param disabled: (true) desactiva, (false)activa
	 */
	private void disabledEditarSolicitud(boolean disabled){
		if(!(disabled)){
			btnEditarSoli.setImage("/resources/mp_editarEnabled.png");
			btnEditarSoli.setStyle("cursor:pointer");
		}else{
			btnEditarSoli.setImage("/resources/mp_editarDisabled.png");
			btnEditarSoli.setStyle("cursor:default");
		}
		btnEditarSoli.setDisabled(disabled);
	}

	/**
	 * Activa o desactiva o button anular solicitud
	 * @param disabled: (true) desactiva, (false)activa
	 */
	private void disabledAnularSolicitud(boolean disabled){
		if(!(disabled) && accesoEliminar()){
			btnAnulaSoli.setImage("/resources/mp_anular.png");
			btnAnulaSoli.setStyle("cursor:pointer");
		}else{
			btnAnulaSoli.setImage("/resources/mp_anularDisabled.png");
			btnAnulaSoli.setStyle("cursor:default");
		}
		if(accesoEliminar())
			btnAnulaSoli.setDisabled(disabled);
		else
			btnAnulaSoli.setDisabled(true);
	}


	public void onSelectfuncionario(Combobox comboboxFuncionario, Combobox comboboxCliente ) throws Exception{
		Util.limpiarCombobox(comboboxCliente);

		if(comboboxFuncionario.getSelectedItem().getValue() instanceof Usuario)
			UtilData.cargarClientesCartera(comboboxCliente, true, ((Usuario)comboboxFuncionario.getSelectedItem().getValue()));
		else
			UtilData.cargarClientesCartera(comboboxCliente, true, null);
		comboboxCliente.setSelectedIndex(0);
	}


	private void verEstado(Long idSolicitudCartera, String tipoCliente){
		wndVerEstado = createVentanaVerEstado(idSolicitudCartera, tipoCliente);
		this.appendChild(wndVerEstado);
		wndVerEstado.setMode("modal");
	}

	private Window createVentanaVerEstado(Long idSolicitudCartera, String tipoCliente){
		Caption caption = null;

		final Window window = new Window("", "normal", true);
		caption = new Caption("SEGUIMIENTO A LA SOLICITUD");
		window.appendChild(caption);
		window.setWidth("750px");

		Listbox listbox=new Listbox();
		Listhead listhead= new Listhead();
		Listheader listheader=new Listheader();

		listheader.setLabel("TIPO ");listheader.setWidth("150px");listhead.appendChild(listheader);listheader=new Listheader();
		listheader.setLabel("FECHA HORA");listheader.setWidth("100px");listhead.appendChild(listheader);listheader=new Listheader();
		if(tipoCliente.equals(Constantes.TIPCON_CONTADO_DESC)){
			listheader.setLabel("DESCUENTO BAJA");listheader.setWidth("90px");listhead.appendChild(listheader);listheader=new Listheader();
			listheader.setLabel("DESCUENTO ALTA");listheader.setWidth("90px");listhead.appendChild(listheader);listheader=new Listheader();
		}else{
			listheader.setLabel("LC.SOLICITADA");listheader.setWidth("80px"); listhead.appendChild(listheader);listheader=new Listheader();
			listheader.setLabel("LC.APROBADA");listheader.setWidth("80px");listhead.appendChild(listheader);listheader=new Listheader();
		}
		listheader.setLabel("USUARIO");listheader.setWidth("100px");listhead.appendChild(listheader);listheader=new Listheader();
		listheader.setLabel("ROL");listheader.setWidth("120px");listhead.appendChild(listheader);listheader=new Listheader();
		listheader.setLabel("ESTADO");listheader.setWidth("100px");listhead.appendChild(listheader);

		listbox.appendChild(listhead);

		/*Busca historial de la solicitud*/
		TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
		List<String> criteriosOrdenar=new ArrayList<>();
		SolicitudCartera solicitudCartera= new SolicitudCartera();
		solicitudCartera.setId(idSolicitudCartera);
		criteriosOrdenar.add("id");
		criteriosBusqueda.put("solicitudCartera", solicitudCartera);
		List<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);

		Listitem item=null;
		Listcell cell=null;

		for(SolicitudClienteCredito solicitud: list){
			item=new Listitem();

			/*  Column TIPO*/
			if(solicitud.getNivelAprobacion().intValue()==0)//Es solicitud
				cell=new Listcell("SOLICITUD");
			else if(solicitud.getNivelAprobacion().intValue()==1)//Evaluacion Solicitud
				cell=new Listcell("EVALUACIÓN SOLICITUD");
			else if(solicitud.getNivelAprobacion().intValue()==2)//Evaluacion crediticia
				cell=new Listcell("EVALUACIÓN CREDITICIA");
			else if(solicitud.getNivelAprobacion().intValue()==3)//Visto bueno
				cell=new Listcell("VB. GENECIA COMERCIAL");
			else
				cell=new Listcell("NIVEL DESCONCIDO");
			item.appendChild(cell);

			/*  Column FECHA*/
			if(solicitud.getFechaAprobacion()==null && solicitud.getFechaAnulacion()==null)
				cell=new Listcell(Constantes.FORMAT_LONG.format(solicitud.getFechaSolicitud()));
			else if(solicitud.getFechaAprobacion()!=null)
				cell=new Listcell(Constantes.FORMAT_LONG.format(solicitud.getFechaAprobacion()));
			else if(solicitud.getFechaAnulacion()!=null)
				cell=new Listcell(Constantes.FORMAT_LONG.format(solicitud.getFechaAnulacion()));
			else
				cell=new Listcell("");
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			/*  Column LINEA CREDITO SOLICITADO*/
			cell=new Listcell(Util.toNumberFormat(solicitud.getLineaCreditoSolicitada(),2));
			cell.setStyle("font-size:11px !important; text-align: right");
			item.appendChild(cell);
			/*  Column LINEA CREDITO APROBADO*/
			if(solicitud.getUsuarioAprobador()==null)
				cell=new Listcell("0.00");
			else
				cell=new Listcell(Util.toNumberFormat(solicitud.getLineaCreditoAprobada(),2));
			cell.setStyle("font-size:11px !important; text-align: right");
			item.appendChild(cell);
			/*  Column USUARIO*/
			cell=new Listcell(solicitud.getUsuarioAprobador()!=null?solicitud.getUsuarioAprobador().getUsuario().getLogin():solicitud.getUsuarioInsercion());
			item.appendChild(cell);
			/*Busca el Area que aprueba*/
			if(solicitud.getUsuarioAprobador()!=null){
				criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("usuario", solicitud.getUsuarioAprobador().getUsuario());
				List<UsuarioRol>lstUsuarioRol=ServiceLocator.getUsuarioRolManager().buscarPorX(criteriosBusqueda, null);
				if(lstUsuarioRol.size()>0){
					UsuarioRol usuarioRol=lstUsuarioRol.get(0);
					cell=new Listcell(usuarioRol.getRol().getDenominacion());
				}else
					cell=new Listcell("NO ENCONTRADO");
			}else
				cell=new Listcell("FUNCIONARIO");

			item.appendChild(cell);

			/*  Column ESTADO*/
			if(solicitud.getEstadoSolicitud().equals(Constantes.ESTADOSOLCAR_EN_ESPERA))
				cell=new Listcell(Constantes.LABEL_ESTADOSOLCAR_EN_ESPERA_DESC);
			else if(solicitud.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA) && solicitud.getUsuarioAprobador()==null)
				cell=new Listcell(Constantes.LABEL_ESTADOSOL_ANULADA_DESC);
			else if(solicitud.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA) && solicitud.getUsuarioAprobador()!=null)
				cell=new Listcell(Constantes.DESAPROBADO_DESC);
			else if (solicitud.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA))
				cell=new Listcell(Constantes.APROBADO_DESC);
			else cell=new Listcell("DESCONOCIDO");

			item.appendChild(cell);
			listbox.appendChild(item);
		}

		window.appendChild(listbox);

		return window;
	}


}
