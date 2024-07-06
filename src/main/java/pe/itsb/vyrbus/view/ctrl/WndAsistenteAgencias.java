/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Avalos
 * Fecha		: 13/09/2013
 */
package pe.itsb.vyrbus.view.ctrl;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.CanalVenta;
import pe.itsb.vyrbus.model.bean.CarteraCliente;
import pe.itsb.vyrbus.model.bean.CentroCosto;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.Concesionario;
import pe.itsb.vyrbus.model.bean.EspecieValorada;
import pe.itsb.vyrbus.model.bean.LineaCreditoCliente;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Rol;
import pe.itsb.vyrbus.model.bean.TipoAgencia;
import pe.itsb.vyrbus.model.bean.TipoCentroCosto;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TitanComisionPersonaBase;
import pe.itsb.vyrbus.model.bean.TitanFuncionarioPersonaPasaje;
import pe.itsb.vyrbus.model.bean.TitanPersona;
import pe.itsb.vyrbus.model.bean.TitanUsuarioPersonal;
import pe.itsb.vyrbus.model.bean.Ubigeo;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.bean.UsuarioRol;
import pe.itsb.vyrbus.model.bean.UsuarioRolID;
import pe.itsb.vyrbus.service.exceptions.AgenciaNullException;
import pe.itsb.vyrbus.service.exceptions.ApellidoPaternoNullException;
import pe.itsb.vyrbus.service.exceptions.CentroCostoException;
import pe.itsb.vyrbus.service.exceptions.ClienteException;
import pe.itsb.vyrbus.service.exceptions.ConcesionarioNullException;
import pe.itsb.vyrbus.service.exceptions.CorrelativoException;
import pe.itsb.vyrbus.service.exceptions.DenominacionDuplicadaException;
import pe.itsb.vyrbus.service.exceptions.DenominacionNullException;
import pe.itsb.vyrbus.service.exceptions.EmailNullException;
import pe.itsb.vyrbus.service.exceptions.LocalidadNullException;
import pe.itsb.vyrbus.service.exceptions.LoginNullException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.NombreCortoNullException;
import pe.itsb.vyrbus.service.exceptions.NombresNullException;
import pe.itsb.vyrbus.service.exceptions.PasswordException;
import pe.itsb.vyrbus.service.exceptions.RazonSocialDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.RolNullException;
import pe.itsb.vyrbus.service.exceptions.RucDuplicadoException;
import pe.itsb.vyrbus.service.exceptions.TipoAgenciaNullException;
import pe.itsb.vyrbus.service.exceptions.TipoComisionException;
import pe.itsb.vyrbus.service.exceptions.UbigeoNullException;
import pe.itsb.vyrbus.service.exceptions.UsuarioLoginDuplicadoException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 * @author Jose
 *
 */
public class WndAsistenteAgencias extends WndBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private Tab tabCliente;
	private Tab tabConcesionario;
	private Tab tabAgencia;
	private Tab tabUsuarioHardware;
	private Tab tabUsuarios;
	private Toolbar tlbrBarraHerramientas;
	private Toolbarbutton tlbrbtnNuevo;
	private Toolbarbutton tlbrbtnBuscar;
	private Toolbarbutton tlbrbtnEditar;
	private Toolbarbutton tlbrbtnCancelar;
	private Toolbarbutton tlbrbtnGuardar;
	private Textbox txtBuscar;
	private Textbox txtRUCCliente;
	private Textbox txtRazonSocialCliente;
	private Textbox txtDireccionCliente;
	private Textbox txtUbigeoCliente;
	private Textbox txtRUCConcesionario;
	private Textbox txtRazonSocialConcesionario;
	private Textbox txtDireccionConcesionario;
	private Textbox txtUbigeoAgencia;
	private Textbox txtUbigeoAgenciaId;
	private Textbox txtDenominacionAgencia;
	private Textbox txtNombreCortoAgencia;
	private Textbox txtDireccionMAC;
	private Textbox txtCodigoPC;
	private Textbox txtNombrePC;
	private Textbox txtCodigoCentroCosto;
	private Textbox txtDenominacionCentroCosto;
	private Combobox cmbGrupoCentroCosto;
	private Textbox txtApellidoPaterno;
	private Textbox txtApellidoMaterno;
	private Textbox txtNombres;
	private Textbox txtResponsableCentroCosto;
	private Textbox txtLogin;
	private Textbox txtPassword;
	private Textbox txtEmailInfo;
	private Doublebox dblbxLineaAprobada;
	private Doublebox dblbxSobregiro;
	private Datebox dtbxFechaActivacionCliente;
	private Datebox dtbxFechaSuspensionCliente;
	private Datebox dtbxFechaActivacionConcesionario;
	private Button btnBuscar;
	private Button btnMostrarTabConcesionario;
	private Button btnMostrarTabAgencia;
	private Button btnMostrarTabUsuarioHW;
	private Button btnMostrarTabUsuario;
	private Button btnUbigeoAgencia;
	private Combobox cmbTipoAgencia;
	private Combobox cmbCanalVenta;
	private Combobox cmbConcesionario;
	private Combobox cmbLocalidad;
	private Combobox cmbConcesionarioCentroCosto;
	private Combobox cmbEstadoCentroCosto;
	private Combobox cmbAgencia;
	private Combobox cmbUsuarioHardware;
	private Combobox cmbRol;
	private Combobox cmbEstadoUsuario;
//	private Checkbox chkTerminal;
	private Checkbox chkCentroCosto;
	private Image imgGeneratePassword;
	private Label lblInformativo;
	private Groupbox grpbxCentroCosto;
	private Listbox lbxCentroCosto;
	private Listbox lbxUsuarios;
	private Row rowUsuario;
	private Intbox ibxComision;
	private Combobox cmbTipoComision;
	private Checkbox ckbIncluyeIgv;
	private Textbox txtCorrelativo;
	private Textbox txtBaseHistorica;
	/*Tipos de centro de costo*/
	private Tab tabCentrosCosto;
	private Tab tabGrupoCentroCosto;
	private Combobox cmbConcesionarioTipoCentroCosto;
	private Textbox txtDenominacionTipoCentroCosto;
	private Listbox lbxTiposCentroCosto;
	private Combobox cmbEstadoTipoCentroCosto;
	private Combobox cmbBusqGrupoCentroCosto;

	private Concesionario concesionario = null;
	private Agencia agencia = null;
	private UsuarioHardware usuarioHardware = null;
	private CentroCosto centroCosto = null;
	private TipoCentroCosto tipoCentroCosto=null;
	private Usuario usuario = null;
	private UsuarioRol usuarioRol = null;
	private CarteraCliente carteraCliente=null;
	private Boolean comisionable=false;

	private static final int ACTION_NEW = 1;
	private static final int ACTION_MODIFY = 2;
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		// TODO Auto-generated method stub
		super.onCreate();
		tabConcesionario.setDisabled(true);
		tabAgencia.setDisabled(true);
		tabUsuarioHardware.setDisabled(true);
		tabUsuarios.setDisabled(true);
		UtilData.enlazarUbigeo(txtUbigeoAgenciaId, txtUbigeoAgencia, btnUbigeoAgencia,null);
		deshabilitar(true);
		txtBuscar.setFocus(true);
		UtilData.cargarTipoComsion(cmbTipoComision);
	}
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		super.initComponents();
		tabCliente = (Tab)this.getFellow("tabCliente");
		tabConcesionario = (Tab)this.getFellow("tabConcesionario");
		tabAgencia = (Tab)this.getFellow("tabAgencia");
		tabUsuarioHardware = (Tab)this.getFellow("tabUsuarioHardware");
		tabUsuarios = (Tab)this.getFellow("tabUsuarios");
		tlbrBarraHerramientas = (Toolbar)this.getFellow("tlbrBarraHerramientas");
		tlbrbtnNuevo = (Toolbarbutton)this.getFellow("tlbrbtnNuevo");
		tlbrbtnBuscar = (Toolbarbutton)this.getFellow("tlbrbtnBuscar");
		tlbrbtnEditar = (Toolbarbutton)this.getFellow("tlbrbtnEditar");
		tlbrbtnCancelar = (Toolbarbutton)this.getFellow("tlbrbtnCancelar");
		tlbrbtnGuardar = (Toolbarbutton)this.getFellow("tlbrbtnGuardar");
		txtBuscar = (Textbox)this.getFellow("txtBuscar");
		txtRUCCliente = (Textbox)this.getFellow("txtRUCCliente");
		txtRazonSocialCliente = (Textbox)this.getFellow("txtRazonSocialCliente");
		txtDireccionCliente = (Textbox)this.getFellow("txtDireccionCliente");
		txtUbigeoCliente = (Textbox)this.getFellow("txtUbigeoCliente");
		txtRUCConcesionario = (Textbox)this.getFellow("txtRUCConcesionario");
		txtRazonSocialConcesionario = (Textbox)this.getFellow("txtRazonSocialConcesionario");
		txtDireccionConcesionario = (Textbox)this.getFellow("txtDireccionConcesionario");
		txtUbigeoAgencia = (Textbox)this.getFellow("txtUbigeoAgencia");
		txtUbigeoAgenciaId = (Textbox)this.getFellow("txtUbigeoAgenciaId");
		txtDenominacionAgencia = (Textbox)this.getFellow("txtDenominacionAgencia");
		txtNombreCortoAgencia = (Textbox)this.getFellow("txtNombreCortoAgencia");
		txtDireccionMAC = (Textbox)this.getFellow("txtDireccionMAC");
		txtCodigoPC = (Textbox)this.getFellow("txtCodigoPC");
		txtNombrePC = (Textbox)this.getFellow("txtNombrePC");
		txtCodigoCentroCosto = (Textbox)this.getFellow("txtCodigoCentroCosto");
		txtDenominacionCentroCosto = (Textbox)this.getFellow("txtDenominacionCentroCosto");
		cmbGrupoCentroCosto=(Combobox)this.getFellow("cmbGrupoCentroCosto");
		txtResponsableCentroCosto = (Textbox)this.getFellow("txtResponsableCentroCosto");
		txtApellidoPaterno = (Textbox)this.getFellow("txtApellidoPaterno");
		txtApellidoMaterno = (Textbox)this.getFellow("txtApellidoMaterno");
		txtNombres = (Textbox)this.getFellow("txtNombres");
		txtLogin = (Textbox)this.getFellow("txtLogin");
		txtPassword = (Textbox)this.getFellow("txtPassword");
		txtEmailInfo = (Textbox)this.getFellow("txtEmailInfo");
		dblbxLineaAprobada = (Doublebox)this.getFellow("dblbxLineaAprobada");
		dblbxSobregiro = (Doublebox)this.getFellow("dblbxSobregiro");
		dtbxFechaActivacionCliente = (Datebox)this.getFellow("dtbxFechaActivacionCliente");
		dtbxFechaSuspensionCliente = (Datebox)this.getFellow("dtbxFechaSuspensionCliente");
		dtbxFechaActivacionConcesionario = (Datebox)this.getFellow("dtbxFechaActivacionConcesionario");
		btnBuscar = (Button)this.getFellow("btnBuscar");
		btnMostrarTabConcesionario = (Button)this.getFellow("btnMostrarTabConcesionario");
		btnMostrarTabAgencia = (Button)this.getFellow("btnMostrarTabAgencia");
		btnMostrarTabUsuario = (Button)this.getFellow("btnMostrarTabUsuario");
		btnMostrarTabUsuarioHW = (Button)this.getFellow("btnMostrarTabUsuarioHW");
//		btnNuevoCentroCosto = (Button)this.getFellow("btnNuevoCentroCosto");
//		btnModificarCentroCosto = (Button)this.getFellow("btnModificarCentroCosto");
//		btnGuardarCentroCosto = (Button)this.getFellow("btnGuardarCentroCosto");
//		btnNuevoUsuario = (Button)this.getFellow("btnNuevoUsuario");
//		btnModificarUsuario = (Button)this.getFellow("btnModificarUsuario");
//		btnGuardarUsuario = (Button)this.getFellow("btnGuardarUsuario");
		cmbCanalVenta = (Combobox)this.getFellow("cmbCanalVenta");
		cmbTipoAgencia = (Combobox)this.getFellow("cmbTipoAgencia");
		cmbConcesionario = (Combobox)this.getFellow("cmbConcesionario");
		cmbLocalidad = (Combobox)this.getFellow("cmbLocalidad");
		cmbConcesionarioCentroCosto = (Combobox)this.getFellow("cmbConcesionarioCentroCosto");
		cmbEstadoCentroCosto = (Combobox)this.getFellow("cmbEstadoCentroCosto");
		cmbEstadoUsuario = (Combobox)this.getFellow("cmbEstadoUsuario");
		cmbAgencia = (Combobox)this.getFellow("cmbAgencia");
		cmbUsuarioHardware = (Combobox)this.getFellow("cmbUsuarioHardware");
		cmbRol = (Combobox)this.getFellow("cmbRol");
//		chkTerminal = (Checkbox)this.getFellow("chkTerminal");
		chkCentroCosto = (Checkbox)this.getFellow("chkCentroCosto");
		imgGeneratePassword = (Image)this.getFellow("imgGeneratePassword");
		btnUbigeoAgencia = (Button)this.getFellow("btnUbigeoAgencia");
		lblInformativo = (Label)this.getFellow("lblInformativo");
		grpbxCentroCosto = (Groupbox)this.getFellow("grpbxCentroCosto");
		lbxCentroCosto = (Listbox)this.getFellow("lbxCentroCosto");
		lbxUsuarios = (Listbox)this.getFellow("lbxUsuarios");
//		rowCentroCosto = (Row)this.getFellow("rowCentroCosto");
		rowUsuario = (Row)this.getFellow("rowUsuario");
		ibxComision=(Intbox)this.getFellow("ibxComision");
		cmbTipoComision=(Combobox)this.getFellow("cmbTipoComision");
		ckbIncluyeIgv=(Checkbox)this.getFellow("ckbIncluyeIgv");
		txtCorrelativo=(Textbox)this.getFellow("txtCorrelativo");
		txtBaseHistorica=(Textbox)this.getFellow("txtBaseHistorica");
		/*Tipos de centro de costo*/
		tabCentrosCosto=(Tab)this.getFellow("tabCentrosCosto");
		tabGrupoCentroCosto=(Tab)this.getFellow("tabGrupoCentroCosto");
		cmbConcesionarioTipoCentroCosto=(Combobox)this.getFellow("cmbConcesionarioTipoCentroCosto");
		txtDenominacionTipoCentroCosto=(Textbox)this.getFellow("txtDenominacionTipoCentroCosto");
		lbxTiposCentroCosto=(Listbox)this.getFellow("lbxTiposCentroCosto");
		cmbEstadoTipoCentroCosto=(Combobox)this.getFellow("cmbEstadoTipoCentroCosto");
		cmbBusqGrupoCentroCosto=(Combobox)this.getFellow("cmbBusqGrupoCentroCosto");

		dblbxLineaAprobada.setLocale(Locale.US);
		dblbxSobregiro.setLocale(Locale.US);


		tabCentrosCosto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				limpiar();
				tlbrbtnCancelar_cancelar();
			}
		});

		tabGrupoCentroCosto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				limpiar();
				tlbrbtnCancelar_cancelar();
			}
		});

		txtBuscar.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnBuscar_buscarCliente();
			}
		});

		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnBuscar_buscarCliente();
			}
		});

		btnMostrarTabConcesionario.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnMostrarTabConcesionario_mostrarConcesionario();
			}
		});

		btnMostrarTabAgencia.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				try{
					if(ibxComision.getValue()!=null && ibxComision.getValue()>0 && (Integer) cmbTipoComision.getSelectedItem().getValue()==-1){
						throw new TipoComisionException();
					}else if(ibxComision.getValue()==null || ibxComision.getValue()==0){
						Messagebox.show(Messages.getString("WndAsistenteAgencias.information.questionNoComision"),DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION,DlgMessage.BTN_DEFAULT_NO, new EventListener<Event>() {
							@Override
							public void onEvent(Event ev) throws Exception {
								if(ev.getName().equals("onYes")){
									btnMostrarTabAgencia_guardarConcesionario();
								}else
									ibxComision.setFocus(true);
							}
						});
					}else{
						btnMostrarTabAgencia_guardarConcesionario();
					}
				}catch (NumberFormatException nfex ){
					DlgMessage.information(Messages.getString("WndConcesionario.information.noComision"),ibxComision);
				}catch (TipoComisionException tcex){
					DlgMessage.information(Messages.getString("WndConcesionario.information.noSelectedTipoComision"),cmbTipoComision);
				}

			}
		});

		btnMostrarTabUsuarioHW.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnMostrarTabUsuarioHW_guardarAgencia();
			}
		});

		btnMostrarTabUsuario.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				btnMostrarTabUsuario_guardarUsuarioHW();
			}
		});

//		btnNuevoCentroCosto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnNuevoCentroCosto_nuevo();
//			}
//		});
//
//		btnModificarCentroCosto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnModificarCentroCosto_modificar();
//			}
//		});
//
//		btnGuardarCentroCosto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnGuardarCentroCosto_guardar();
//			}
//		});

		tlbrbtnNuevo.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				tlbrbtnNuevo_nuevo();
			}
		});
		tlbrbtnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				tlbrbtnBuscar_buscar();
			}
		});
		tlbrbtnEditar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				tlbrbtnEditar_editar();
			}
		});
		tlbrbtnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				tlbrbtnCancelar_cancelar();
			}
		});
		tlbrbtnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e) throws Exception{
				tlbrbtnGuardar_guardar();
			}
		});


//		btnNuevoUsuario.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnNuevoUsuario_nuevo();
//			}
//		});
//
//		btnModificarUsuario.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnModificarUsuario_modificar();
//			}
//		});
//
//		btnGuardarUsuario.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				btnGuardarUsuario_guardar();
//			}
//		});

		chkCentroCosto.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				chkCentroCosto_mostrarCentrosCosto();
			}
		});

//		lbxCentroCosto.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
//			public void onEvent(Event e){
//				lbxCentroCosto_seleccionar();
//			}
//		});

		lbxUsuarios.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				lbxUsuario_seleccionar();
			}
		});

		imgGeneratePassword.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event e){
				generarPassword();
			}
		});

		cmbBusqGrupoCentroCosto.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				buscarCentroCosto();
			}
		});
	}

	/**
	 * Busca el cliente en base al RUC ingresado.
	 */
	private void btnBuscar_buscarCliente(){
		try{
			if(txtBuscar.getText().trim().isEmpty())
				throw new ClienteException(ClienteException.RUC_NULL);

//			List<Cliente> lstClientes = ServiceLocator.getClienteManager().buscarClienteAgencia(txtBuscar.getText().trim());

			TreeMap<String, Object> criteriosBusqueda = new  TreeMap<>();
			criteriosBusqueda.put("numeroDocumento", txtBuscar.getText().trim());
			List<Cliente> lstClientes = ServiceLocator.getClienteManager().buscarPorX(criteriosBusqueda, null);

			if(lstClientes.size()==1){
				mostrarDatosCliente(lstClientes.get(0));
				btnMostrarTabConcesionario.setFocus(true);
			}else if(lstClientes.size()>1){
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.muchasOcurrenciasCliente"));
				lblInformativo.setValue("");
			}else{
				lblInformativo.setValue("No existe ningun Cliente registrado con el numero de RUC proporcionado.");
				lblInformativo.setStyle("color:red; font-size:12px !important; font-weight:bold");
				btnMostrarTabConcesionario.setDisabled(true);
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noHayOcurrenciasCliente"));
			}
		}catch(ClienteException cex) {
			if(cex.getTipo().intValue()==ClienteException.RUC_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaIngresarRUC"), txtBuscar);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Muetra los datos del Cliente y la linea de Credito.
	 * @param cliente	: Objeto cliente.
	 */
	private void mostrarDatosCliente(Cliente cliente){
		try{
//			this.cliente = cliente;
			comisionable=false;
			txtRUCCliente.setText(cliente.getNumeroDocumento());
			txtRazonSocialCliente.setText(cliente.getRazonSocial());
			txtDireccionCliente.setText(cliente.getDireccion());
			String ubigeo = ServiceLocator.getUbigeoManager().ubicacionGeografica(cliente.getUbigeo());
			txtUbigeoCliente.setText(ubigeo);

			LineaCreditoCliente lineaCreditoCliente = ServiceLocator.getLineaCreditoClienteManager().lineaCreditoCliente(cliente.getId());
			if(lineaCreditoCliente!=null){
				dblbxLineaAprobada.setValue(lineaCreditoCliente.getLineaCreditoAprobada());
				Double sobregiro = (lineaCreditoCliente.getLineaCreditoAprobada() * lineaCreditoCliente.getSobregiro())/100;
				dblbxSobregiro.setValue(sobregiro);
				dtbxFechaActivacionCliente.setValue(lineaCreditoCliente.getFechaActivacion());
				dtbxFechaSuspensionCliente.setValue(lineaCreditoCliente.getFechaSuspension());
				lblInformativo.setValue("CLIENTE CON LINEA DE CREDITO ACTIVA");
				lblInformativo.setStyle("color:blue; font-size:12px !important; font-weight:bold");
				btnMostrarTabConcesionario.setDisabled(false);
				carteraCliente=lineaCreditoCliente.getCarteraCliente();
				carteraCliente.setCliente(cliente);
				comisionable=(lineaCreditoCliente.getEsComisionable().equals(Constantes.SI)?true:false);
			}else{
				lblInformativo.setValue("CLIENTE CON LINEA DE CREDITO SUSPENDIDA");
				lblInformativo.setStyle("color:red; font-size:12px !important; font-weight:bold");
				btnMostrarTabConcesionario.setDisabled(true);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Muestra la informacion del Concesionario
	 */
	private void btnMostrarTabConcesionario_mostrarConcesionario(){
		try{
			ibxComision.setDisabled(false);
			cmbTipoComision.setDisabled(false);

			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("ruc", txtRUCCliente.getText().trim());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<Concesionario> lstConcesionario = ServiceLocator.getConcesionarioManager().buscarPorX(criteriosBusqueda, null);
			if(lstConcesionario.size()==1){
				concesionario = lstConcesionario.get(0);
				txtRUCConcesionario.setText(concesionario.getRuc());
				txtRazonSocialConcesionario.setText(concesionario.getRazonSocial());
				txtDireccionConcesionario.setText(concesionario.getDireccion());
				dtbxFechaActivacionConcesionario.setValue(concesionario.getFechaActivacion());
				ibxComision.setValue(concesionario.getComision()!=null?concesionario.getComision():null);
				if(concesionario.getTipoComision()!=null)
					Util.seleccionarValorItemCombobox(cmbTipoComision, concesionario.getTipoComision());
				else
					cmbTipoComision.setSelectedIndex(0);
				ckbIncluyeIgv.setChecked(concesionario.getIncluyeIgv()!=null? concesionario.getIncluyeIgv()==1:true);

//				ibxComision.setDisabled(true);
//				cmbTipoComision.setDisabled(true);
//				ckbIncluyeIgv.setDisabled(true);

			}else{
				concesionario = null;
				txtRUCConcesionario.setText(txtRUCCliente.getText().trim());
				txtRazonSocialConcesionario.setText(txtRazonSocialCliente.getText().trim());
				txtDireccionConcesionario.setText(txtDireccionCliente.getText());
				dtbxFechaActivacionConcesionario.setValue(Util.StringtoDate(ServiceLocator.getVentaPasajesManager().getDateSystem(), Constantes.DATE_FORMAT));
				ibxComision.setValue(null);
				cmbTipoComision.setSelectedIndex(0);
				ckbIncluyeIgv.setChecked(true);

//				ibxComision.setDisabled(false);
//				cmbTipoComision.setDisabled(false);
				ckbIncluyeIgv.setDisabled(false);
			}

			btnMostrarTabAgencia.setDisabled(false);
			tabConcesionario.setDisabled(false);
			tabConcesionario.setSelected(true);
			btnMostrarTabAgencia.setFocus(true);
			ibxComision.setFocus(true);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Guardar los datos del Concesionario (si es necesario) y muestra la informacion de la Agencia.
	 */
	private void btnMostrarTabAgencia_guardarConcesionario(){
		try{
			agencia = null;
			Integer incluyeIgv=ckbIncluyeIgv.isChecked()?1:0;
			Integer comision=ibxComision.getValue()!=null? ibxComision.getValue():0;
			Integer tipoComision=cmbTipoComision.getSelectedItem().getValue();

			if(concesionario==null){
				concesionario = new Concesionario();
				concesionario.setRuc(txtRUCConcesionario.getText());
				concesionario.setRazonSocial(txtRazonSocialConcesionario.getText());
				concesionario.setDireccion(txtDireccionConcesionario.getText());
				concesionario.setFechaActivacion(dtbxFechaActivacionConcesionario.getValue());
				concesionario.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				concesionario.setId(0);

				concesionario.setComision(comision);
				concesionario.setTipoComision(tipoComision>-1?tipoComision:null);
				concesionario.setIncluyeIgv(comision>0?incluyeIgv:null);

				UtilData.auditarRegistro(concesionario, getUsuario(), Executions.getCurrent());
				int result = ServiceLocator.getConcesionarioManager().guardar(concesionario);
				if(result==Constantes.FAILURE){
					DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorGuardarConcesionario"));
					return;
				}
			}else{
				if(concesionario.getComision()!=comision || concesionario.getTipoComision()!=tipoComision ||
						concesionario.getIncluyeIgv()!=incluyeIgv){

					concesionario.setComision(comision);
					concesionario.setTipoComision(tipoComision>-1?tipoComision:null);
					concesionario.setIncluyeIgv(comision>0?incluyeIgv:null);
					UtilData.auditarRegistro(concesionario, true, getUsuario(), Executions.getCurrent());
					ServiceLocator.getConcesionarioManager().actualizar(concesionario);
				}
			}

			UtilData.cargarDataCombo(cmbTipoAgencia, TipoAgencia.class, false);
			UtilData.cargarDataCombo(cmbLocalidad, Localidad.class, false);
			UtilData.cargarDataCombo(cmbConcesionario, Concesionario.class, false);

			if(concesionario != null && concesionario.getId()!=null){
				TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
				criteriosBusqueda.put("concesionario.id", concesionario.getId());
				criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
				List<Agencia> lstAgencia = ServiceLocator.getAgenciaManager().buscarPorX(criteriosBusqueda, null);
				if(lstAgencia.size()==1){
					agencia = lstAgencia.get(0);
					Util.seleccionarValorItemCombo(TipoAgencia.class, cmbTipoAgencia, agencia.getTipoAgencia().getId());
					if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
						chkCentroCosto.setVisible(true);
					else
						chkCentroCosto.setVisible(false);
					Util.seleccionarValorItemCombo(Localidad.class, cmbLocalidad, agencia.getLocalidad().getId());
					Util.seleccionarValorItemCombo(Concesionario.class, cmbConcesionario, concesionario.getId());
					String ubigeo = ServiceLocator.getUbigeoManager().ubicacionGeografica(agencia.getUbigeo());
					txtUbigeoAgencia.setText(ubigeo);
					txtDenominacionAgencia.setText(agencia.getDenominacion());
					txtNombreCortoAgencia.setText(agencia.getNombreCorto()==null?"":agencia.getNombreCorto());
					deshabilitarAgencia(true);

					//Busca el correlativo asignado a la agencia o cliente corporativo.
					if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_VIAJES)){
//						EspecieValorada especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, agencia, false);
//						txtCorrelativo.setText(especieValorada.toString());
//						txtCorrelativo.setText(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES, agencia));
					}else if(agencia.getTipoAgencia().getId().equals(Constantes.ID_TIPAGE_CORPORATIVO)){
//						EspecieValorada especieValorada=UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, agencia, false);
//						txtCorrelativo.setText(especieValorada.toString());
//						txtCorrelativo.setText(UtilData.buscarEspecieValorada(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO, agencia));
					}

				}else{
					UtilData.cargarDataCombo(cmbConcesionario, Concesionario.class, false);
					Util.seleccionarValorItemCombo(Concesionario.class, cmbConcesionario, concesionario.getId());
					deshabilitarAgencia(false);
				}

				loadEstado(cmbEstadoCentroCosto);
				loadEstado(cmbEstadoTipoCentroCosto);
				btnMostrarTabAgencia.setDisabled(false);
				tabAgencia.setDisabled(false);
				tabCliente.setDisabled(true);
				tabConcesionario.setDisabled(true);
				tabAgencia.setSelected(true);
				cmbTipoAgencia.setFocus(true);
			}

		}catch (RazonSocialDuplicadoException rsdex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.razonSocialDuplicada"));
		}catch(RucDuplicadoException rdnex){
			DlgMessage.information(Messages.getString("WndConcesionario.information.numeroRucInvalido"));
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void btnMostrarTabUsuarioHW_guardarAgencia(){
		try{
			if(agencia==null){
				if (!(cmbTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia))
					throw new TipoAgenciaNullException();
				else if (!(cmbLocalidad.getSelectedItem().getValue() instanceof Localidad))
					throw new LocalidadNullException();
				else if (!(cmbConcesionario.getSelectedItem().getValue() instanceof Concesionario))
					throw new ConcesionarioNullException();
				else if (txtUbigeoAgencia.getText().trim().equals(""))
					throw new UbigeoNullException();
				else if (txtDenominacionAgencia.getText().trim().equals(""))
					throw new DenominacionNullException();
				else if (txtNombreCortoAgencia.getText().trim().equals(""))
					throw new NombreCortoNullException();
				else if (txtCorrelativo.getText().trim().isEmpty())
					throw new CorrelativoException();

				agencia = new Agencia();
				agencia.setTipoAgencia((TipoAgencia)cmbTipoAgencia.getSelectedItem().getValue());
				agencia.setConcesionario((Concesionario)cmbConcesionario.getSelectedItem().getValue());
				agencia.setLocalidad((Localidad)cmbLocalidad.getSelectedItem().getValue());
				Ubigeo ubigeo = new Ubigeo();
				ubigeo.setId(txtUbigeoAgenciaId.getText());
				agencia.setUbigeo(ubigeo);
				agencia.setDenominacion(txtDenominacionAgencia.getText().trim().toUpperCase());
				agencia.setNombreCorto(txtNombreCortoAgencia.getText().trim().toUpperCase());
				agencia.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				agencia.setEsTerminal(false);
				UtilData.auditarRegistro(agencia, getUsuario(), Executions.getCurrent());

				int result = ServiceLocator.getAgenciaManager().guardar(agencia);
				if(result==Constantes.FAILURE){
					DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorGuardarAgencia"));
					return;
				}
				generarCorrelativo(agencia);

				/*Guarda base historica si esta es mayor a Cero, esto se deberia cumplir solo para los clientes corporativos*/
				if(!(txtBaseHistorica.getText().isEmpty()) && Util.parseNumberFormat(txtBaseHistorica.getText(),2)>0){
					CarteraCliente oCarteraCliente=ServiceLocator.getCarteraClienteManager().buscarPorId(carteraCliente.getId());
					oCarteraCliente.setBaseHistorica(Util.parseNumberFormat(txtBaseHistorica.getText(),2));
					UtilData.auditarRegistro(oCarteraCliente, true,getUsuario(), Executions.getCurrent());
					ServiceLocator.getCarteraClienteManager().actualizar(oCarteraCliente);
				}
			}

			/*********ASOCIA EL CLIENTE A LA CARTERA DEL FUNCIONARIO EN EL SISTEMA TITAN*********************/
			/**==============================================IMPL- JABANTO - 20/11/2014 ===============================================*/
			/* Registra y/o Asocia el cliente al funcionario en TITAN  */
			TitanUsuarioPersonal titanUsuarioPersonalRegistro=ServiceLocator.getTitanManager().buscarUsuarioPersonalPorLogin(getUsuario().getLogin());

			//Busca el funcionario por su login.
			TitanUsuarioPersonal titanFuncionario = ServiceLocator.getTitanManager().buscarUsuarioPersonalPorLogin(carteraCliente.getUsuario().getLogin());
			if(titanFuncionario==null){
				//Registra el funcionario en Titan
				titanFuncionario=new TitanUsuarioPersonal();
				titanFuncionario.setNombres(carteraCliente.getUsuario().getNombre());
				titanFuncionario.setApellidoPaterno(carteraCliente.getUsuario().getApellidoPaterno());
				titanFuncionario.setApellidoMaterno(carteraCliente.getUsuario().getApellidoMaterno());
				titanFuncionario.setLogin(carteraCliente.getUsuario().getLogin());
				ServiceLocator.getTitanManager().guardarUsuarioPersonal(titanFuncionario);
			}

			//Busca el cliente por el numero de RUC.
			TitanPersona titanPersona= ServiceLocator.getTitanManager().buscarPersonaPorRuc(carteraCliente.getCliente().getNumeroDocumento());
			if(titanPersona==null){
				//Registra el Cliente en Titan
				titanPersona=new TitanPersona();
				titanPersona.setTipoDocumentoIdentidadID(1); //Ruc
				titanPersona.setNumeroDocumentoSunat(carteraCliente.getCliente().getNumeroDocumento());
				titanPersona.setCodigoCliente(carteraCliente.getCliente().getNumeroDocumento());
				titanPersona.setRazonSocial(carteraCliente.getCliente().getRazonSocial());
				titanPersona.setOrigen(1); // A solicitud de marco (Solo lima) - 29/09/2015
				titanPersona.setUsuarioPersonal(titanUsuarioPersonalRegistro!=null?titanUsuarioPersonalRegistro:null);
				if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES)
					titanPersona.setTipoCredito(String.valueOf(Constantes.TRUE_VALUE));
				else if (agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
					titanPersona.setTipoCredito(String.valueOf(Constantes.FALSE_VALUE));
				else
					titanPersona.setTipoCredito(String.valueOf(0));
				titanPersona.setIp(UtilData.ipLocal(Executions.getCurrent()));
				ServiceLocator.getTitanManager().guardarPersona(titanPersona);
			}

			//Valida si el cliente esta actualmente en alguna cartera
			TitanFuncionarioPersonaPasaje titanFuncionarioPersonaPasaje=ServiceLocator.getTitanManager().buscarFuncionarioPersonaPasajePorIdPersona(titanPersona.getId());
			if(titanFuncionarioPersonaPasaje==null){
				//Asocia el Cliente al Funcionario.
				titanFuncionarioPersonaPasaje=new TitanFuncionarioPersonaPasaje();
				titanFuncionarioPersonaPasaje.setFuncionario(titanFuncionario);
				titanFuncionarioPersonaPasaje.setFuncionarioActual(titanFuncionario);
				titanFuncionarioPersonaPasaje.setComisionable(comisionable?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
				titanFuncionarioPersonaPasaje.setPersona(titanPersona);
				titanFuncionarioPersonaPasaje.setFechaActivacion(Constantes.FORMAT_DATE.format(new Date()));
				titanFuncionarioPersonaPasaje.setFechaSuspencion(Constantes.FORMAT_DATE.format(new Date(new Date().getTime()+(Constantes.MILISEGUNDOS_X_DIA*3650)))); //10 a�os
				titanFuncionarioPersonaPasaje.setEstadoRegistroID(Constantes.TRUE_VALUE);
				titanFuncionarioPersonaPasaje.setIp(UtilData.ipLocal(Executions.getCurrent()));
				titanFuncionarioPersonaPasaje.setUsuarioPersonal(titanUsuarioPersonalRegistro!=null?titanUsuarioPersonalRegistro:null);
				ServiceLocator.getTitanManager().guardaFuncionarioPerosnaPasajes(titanFuncionarioPersonaPasaje);

				//Registra base historica.
				TitanComisionPersonaBase titanComisionPersonaBase=ServiceLocator.getTitanManager().buscarBaseHistoricaPorIdPersona(titanPersona.getId());
				if(titanComisionPersonaBase!=null){
					//Inactiva el registro recuperado
					titanComisionPersonaBase.setEstadoRegistroID(Constantes.FALSE_VALUE);
					ServiceLocator.getTitanManager().actualizaBaseHistorica(titanComisionPersonaBase);
				}
				titanComisionPersonaBase=new TitanComisionPersonaBase();
				titanComisionPersonaBase.setPersona(titanPersona);
				titanComisionPersonaBase.setMontoBase(carteraCliente.getBaseHistorica());
				titanComisionPersonaBase.setEstadoRegistroID(Constantes.TRUE_VALUE);
				ServiceLocator.getTitanManager().guardaBaseHistorica(titanComisionPersonaBase);

			}else if (titanFuncionarioPersonaPasaje.getFuncionarioActual()==null || titanFuncionarioPersonaPasaje.getFuncionarioActual().getId().longValue()!=titanFuncionario.getId().longValue()){
				/*Solo si el funcionario al que esta asignado el cliente en TITAN es diferente al que esta asignado en SISVYR*/
				titanFuncionarioPersonaPasaje.setFuncionario(titanFuncionario);
				titanFuncionarioPersonaPasaje.setFuncionarioActual(titanFuncionario);
				titanFuncionarioPersonaPasaje.setComisionable(comisionable?Constantes.TRUE_VALUE:Constantes.FALSE_VALUE);
				titanFuncionarioPersonaPasaje.setUsuarioPersonal(titanUsuarioPersonalRegistro!=null?titanUsuarioPersonalRegistro:null);
				titanFuncionarioPersonaPasaje.setFechaActivacion(Constantes.FORMAT_DATE.format(new Date()));
				titanFuncionarioPersonaPasaje.setFechaSuspencion(Constantes.FORMAT_DATE.format(new Date(new Date().getTime()+(Constantes.MILISEGUNDOS_X_DIA*365))));
				titanFuncionarioPersonaPasaje.setIp(UtilData.ipLocal(Executions.getCurrent()));
				ServiceLocator.getTitanManager().actualizaFuncionarioPerosnaPasajes(titanFuncionarioPersonaPasaje);
			}
			/***==============================================END IMPL- JABANTO - 20/11/2014 ===============================================*/




			if(agencia != null && agencia.getId()!=null){
				tabAgencia.setDisabled(true);
				tabUsuarioHardware.setDisabled(false);
				tabUsuarioHardware.setSelected(true);
				UtilData.cargarDataCombo(cmbCanalVenta, CanalVenta.class, false);

				for(int i=0; i<cmbCanalVenta.getItems().size();i++){
					CanalVenta canalVenta = cmbCanalVenta.getItems().get(i).getValue();
					if(canalVenta != null){
						if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES
								&& canalVenta.getId().intValue()==Constantes.ID_CANVEN_AGENCIA_VIAJES){
							cmbCanalVenta.setSelectedIndex(i);
							break;
						}else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO
								&& canalVenta.getId().intValue()==Constantes.ID_CANVEN_CORPORATIVO){
							cmbCanalVenta.setSelectedIndex(i);
							break;
						}
					}
				}
				txtDireccionMAC.setText("00-00-00-00-00-00");
				txtCodigoPC.setText(Util.generarCodigo(txtDireccionMAC.getText()));
				String nombre = (agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO?"COR-":"AGE-")+agencia.getConcesionario().getRuc();
				txtNombrePC.setText(nombre);
			}

		}catch (CorrelativoException cexc){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noCorrelativo"), cmbTipoAgencia);
		}catch (TipoAgenciaNullException tanex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoTipoAgencia"), cmbTipoAgencia);
		}catch (LocalidadNullException lnex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoLocalidad"), cmbLocalidad);
		}catch (ConcesionarioNullException cnex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoConcesionario"), cmbConcesionario);
		}catch (UbigeoNullException unex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoUbigeo"), btnUbigeoAgencia);
		}catch (DenominacionNullException dnex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noIngresoNombre"), txtDenominacionAgencia);
		}catch (NombreCortoNullException ncnex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noIngresoNombreCorto"), txtNombreCortoAgencia);
		}catch(DenominacionDuplicadaException ddex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.denominacionDuplicadaAgencia"), txtDenominacionAgencia);
		}catch(NombreCortoDuplicadoException ncdex){
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.nombreCortoDuplicadoAgencia"), txtNombreCortoAgencia);
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void btnMostrarTabUsuario_guardarUsuarioHW(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("agencia.id", agencia.getId());
			List<UsuarioHardware> lstUsuariohardware = ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
			if(lstUsuariohardware.size()>0)
				usuarioHardware = lstUsuariohardware.get(0);
			else
				usuarioHardware = null;

			if(usuarioHardware==null){
				usuarioHardware = new UsuarioHardware();
				usuarioHardware.setAgencia(agencia);
				usuarioHardware.setCanalVenta((CanalVenta)cmbCanalVenta.getSelectedItem().getValue());
				usuarioHardware.setCodigo(txtCodigoPC.getText().trim());
				usuarioHardware.setDireccionMAC(txtDireccionMAC.getText().trim());
				usuarioHardware.setDescripcion(txtNombrePC.getText().trim());
				usuarioHardware.setEstadoRegistro(Constantes.VALUE_ACTIVO);
				UtilData.auditarRegistro(usuarioHardware, getUsuario(), Executions.getCurrent());

				int result = ServiceLocator.getUsuarioHardwareManager().guardar(usuarioHardware);
				lstUsuariohardware = ServiceLocator.getUsuarioHardwareManager().buscarPorX(criteriosBusqueda, null);
				if(result==Constantes.FAILURE){
					DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorGuardarUsuarioHardware"));
					return;
				}
			}

			UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
			UtilData.cargarDataCombo(cmbRol, Rol.class, false);
			if(agencia != null && agencia.getId()!=null){
				Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, agencia.getId());
				tabUsuarioHardware.setDisabled(true);
				tabUsuarios.setDisabled(false);
				tabUsuarios.setSelected(true);
				loadEstado(cmbEstadoUsuario);
				loadUsuarioHardware(lstUsuariohardware);
				buscarUsuarios();
			}
			tlbrBarraHerramientas.setParent(rowUsuario);
			txtEmailInfo.setPlaceholder("Ingrese una direcci�n de correo valida");
		}catch(Exception ex){
			ex.printStackTrace();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Realiza la busqueda de la ultima seria asignada segun el tipo de agencia seleccionada.
	 * @throws Exception
	 */
	public void cmbTipoAgencia_OnChange() throws Exception{
		txtBaseHistorica.setText("0.00");
		if(cmbTipoAgencia.getSelectedItem().getValue() instanceof TipoAgencia){
			TipoAgencia tipoAgencia=cmbTipoAgencia.getSelectedItem().getValue();
			Integer idTipoComprobante=null;
			if(tipoAgencia.getId().equals(Constantes.ID_TIPAGE_VIAJES))
				idTipoComprobante=Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES;
			else if(tipoAgencia.getId().equals(Constantes.ID_TIPAGE_CORPORATIVO)){
				idTipoComprobante=Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO;

				/*Calcula la base historica del Cliente - 27/12/2014*/
				Double baseHistorica=ServiceLocator.getSolicitudCarteraManager().baseHistoricaCliente(carteraCliente.getCliente().getId());
				txtBaseHistorica.setText(Util.toNumberFormat(baseHistorica, 2));
			}

			if(idTipoComprobante!=null){
				String ultimaSeria=ServiceLocator.getEspecieValoradaManager().buscarUltimaSerieUtilAge(idTipoComprobante);
				txtCorrelativo.setText(String.valueOf(Integer.valueOf(ultimaSeria).intValue()+1)+" - 0000001");
			}else txtCorrelativo.setText("");
		}

	}

	/**
	 * Habilita o deshabilita los controles relacionados a la Agencia.
	 * @param arg	: Boolean
	 */
	private void deshabilitarAgencia(boolean arg){
		cmbTipoAgencia.setDisabled(arg);
//		cmbConcesionario.setDisabled(arg);
		cmbLocalidad.setDisabled(arg);
		txtDenominacionAgencia.setDisabled(arg);
		txtNombreCortoAgencia.setDisabled(arg);
		btnUbigeoAgencia.setDisabled(arg);
	}

	/**
	 * Muestra la informacion relacioanda a los Centros de Costo.
	 */
	private void chkCentroCosto_mostrarCentrosCosto(){
		try{
			centroCosto = null;
			if(chkCentroCosto.isChecked()){
				grpbxCentroCosto.setVisible(true);
				UtilData.cargarDataCombo(cmbConcesionarioCentroCosto, Concesionario.class, false);
				Util.seleccionarValorItemCombo(Concesionario.class, cmbConcesionarioCentroCosto, concesionario.getId());

				/*Tipo Centro de costo*/
				Util.limpiarCombobox(cmbGrupoCentroCosto);
				UtilData.cargarDataCombo(cmbConcesionarioTipoCentroCosto, Concesionario.class, false);
				Util.seleccionarValorItemCombo(Concesionario.class, cmbConcesionarioTipoCentroCosto, concesionario.getId());
				loadGruposCentroCosto();

				tlbrbtnBuscar_buscar();
//				List<CentroCosto> lstCentroCosto = buscarCentroCosto();
//				listarCentroCosto(lstCentroCosto);
			}else{
				grpbxCentroCosto.setVisible(false);
				lbxCentroCosto.getItems().clear();
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Realiza el llenado del combo de Estado de los Centros de Costo
	 */
	private void loadEstado(Combobox combobox){
		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		combobox.appendChild(comboitem);
		comboitem = new Comboitem(Constantes.LABEL_ACTIVO_DESCP);
		comboitem.setValue(Constantes.VALUE_ACTIVO);
		combobox.appendChild(comboitem);
		comboitem = new Comboitem(Constantes.LABEL_INACTIVO_DESCP);
		comboitem.setValue(Constantes.VALUE_INACTIVO);
		combobox.appendChild(comboitem);
		combobox.setSelectedIndex(0);
	}

	/**
	 * Realiza el llenado del combo de Usuarios Hardware.
	 */
	private void loadUsuarioHardware(List<UsuarioHardware> lstUsuarioHardware){
		Comboitem comboitem = new Comboitem(Constantes.COMBO_LABEL_SELECCIONE);
		cmbUsuarioHardware.appendChild(comboitem);
		for(UsuarioHardware usuarioHardware: lstUsuarioHardware){
			comboitem = new Comboitem(usuarioHardware.getDescripcion());
			comboitem.setValue(usuarioHardware);
			cmbUsuarioHardware.appendChild(comboitem);
		}
		if(cmbUsuarioHardware.getItems().size()==0)
			cmbUsuarioHardware.setSelectedIndex(0);
		else
			cmbUsuarioHardware.setSelectedIndex(1);
	}


	/******************************		PARA LA BARRA DE HERRAMIENTAS	***********************************/
	private void tlbrbtnNuevo_nuevo(){
		deshabilitar(false);
		limpiar();
		tlbrbtnNuevo.setDisabled(true);
		tlbrbtnBuscar.setDisabled(true);
		tlbrbtnEditar.setDisabled(true);
		tlbrbtnCancelar.setDisabled(false);
		tlbrbtnGuardar.setDisabled(false);

		if(tabAgencia.isSelected()){
			txtDenominacionCentroCosto.setFocus(true);
			centroCosto=null;
		}else{
			//Creacion de usuarios
			txtApellidoPaterno.setFocus(true);
			usuario=null;
			cmbEstadoUsuario.setSelectedIndex(1);

			cmbRol.setDisabled(true);
			if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO){
				Util.seleccionarValorItemCombo(Rol.class, cmbRol, Constantes.ID_ROL_CLIENTE_CORPORATIVO);
			}else if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_VIAJES){
				Util.seleccionarValorItemCombo(Rol.class, cmbRol, Constantes.ID_ROL_AGENCIA_VIAJES);
			}else
				cmbRol.setSelectedIndex(0);

		}
	}

	private void tlbrbtnEditar_editar(){
		deshabilitar(false);
		tlbrbtnNuevo.setDisabled(true);
		tlbrbtnBuscar.setDisabled(true);
		tlbrbtnEditar.setDisabled(true);
		tlbrbtnCancelar.setDisabled(false);
		tlbrbtnGuardar.setDisabled(false);

		if(usuario!=null)
			txtEmailInfo.setDisabled(usuario.getEstadoRegistro().equals(Constantes.VALUE_INACTIVO));
	}

	private void tlbrbtnBuscar_buscar(){
		deshabilitar(true);
		if(tabAgencia.isSelected()){
			cmbBusqGrupoCentroCosto.setSelectedIndex(0);
			buscarCentroCosto();
			buscarTipoCentroCosto();
		}else
			buscarUsuarios();

		tlbrbtnNuevo.setDisabled(false);
		tlbrbtnBuscar.setDisabled(false);
		tlbrbtnEditar.setDisabled(true);
		tlbrbtnCancelar.setDisabled(true);
		tlbrbtnGuardar.setDisabled(true);
	}

	private void tlbrbtnCancelar_cancelar(){
		tlbrbtnNuevo.setDisabled(false);
		tlbrbtnBuscar.setDisabled(false);
		tlbrbtnEditar.setDisabled(true);
		tlbrbtnCancelar.setDisabled(true);
		tlbrbtnGuardar.setDisabled(true);
		deshabilitar(true);
		limpiar();
	}

	private void tlbrbtnGuardar_guardar() throws Exception{
//		tlbrbtnNuevo.setDisabled(false);
//		tlbrbtnBuscar.setDisabled(false);
//		tlbrbtnEditar.setDisabled(true);
//		tlbrbtnCancelar.setDisabled(true);
//		tlbrbtnGuardar.setDisabled(true);
		if(tabAgencia.isSelected()){
			if(tabCentrosCosto.isSelected()){
				guardarCentroCosto();
			}else{
				guardarTipoCentroCosto();
			}
		}else
			guardarUsuario();
	}

	private void deshabilitar(boolean arg){
		if(tabAgencia.isSelected()){
			if(tabCentrosCosto.isSelected()){
				txtCodigoCentroCosto.setDisabled(arg);
				txtDenominacionCentroCosto.setDisabled(arg);
				txtResponsableCentroCosto.setDisabled(arg);
				cmbEstadoCentroCosto.setDisabled(arg);
				cmbGrupoCentroCosto.setDisabled(arg);
			}else{
				txtDenominacionTipoCentroCosto.setDisabled(arg);
				cmbEstadoTipoCentroCosto.setDisabled(arg);
			}
		}else{
			txtApellidoPaterno.setDisabled(arg);
			txtApellidoMaterno.setDisabled(arg);
			txtNombres.setDisabled(arg);
			txtLogin.setDisabled(arg);
			txtPassword.setDisabled(arg);
//			cmbRol.setDisabled(arg);
			txtEmailInfo.setDisabled(arg);
			cmbEstadoUsuario.setDisabled(arg);
			imgGeneratePassword.setVisible(!arg);
		}
	}

	private void limpiar(){
		if(tabAgencia.isSelected()){
			if(tabCentrosCosto.isSelected()){
				txtCodigoCentroCosto.setText("");
				txtResponsableCentroCosto.setText("");
				txtDenominacionCentroCosto.setText("");
				cmbGrupoCentroCosto.setSelectedIndex(0);
				cmbEstadoCentroCosto.setSelectedIndex(1);
				centroCosto = null;
			}else{
				txtDenominacionTipoCentroCosto.setText("");
				cmbEstadoTipoCentroCosto.setSelectedIndex(1);
				tipoCentroCosto=null;
			}
		}else{
			txtApellidoPaterno.setText("");
			txtApellidoMaterno.setText("");
			txtNombres.setText("");
			txtLogin.setText("");
			txtPassword.setText("");
			cmbEstadoUsuario.setSelectedIndex(0);
			cmbRol.setSelectedIndex(0);
			txtEmailInfo.setText("");
//			txtEmailInfo.setText("-");
		}
	}



	private List<CentroCosto> buscarCentroCosto(){
		List<CentroCosto> lstCentroCosto = null;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("concesionario.id", concesionario.getId());
			if(cmbBusqGrupoCentroCosto.getSelectedItem().getValue() instanceof TipoCentroCosto)
				criteriosBusqueda.put("tipoCentroCosto", cmbBusqGrupoCentroCosto.getSelectedItem().getValue());
			criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			lstCentroCosto = ServiceLocator.getCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			listarCentroCosto(lstCentroCosto);


		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		return lstCentroCosto;
	}

	private List<TipoCentroCosto> buscarTipoCentroCosto(){
		List<TipoCentroCosto> lstTipoCentroCosto = null;
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("concesionario.id", concesionario.getId());
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("denominacion");
			lstTipoCentroCosto = ServiceLocator.getTipoCentroCostoManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			listarTiposCentroCosto(lstTipoCentroCosto);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
		return lstTipoCentroCosto;
	}


	private void listarCentroCosto(List<CentroCosto> lstCentroCosto){
		lbxCentroCosto.getItems().clear();
		if(lstCentroCosto.size()>0){
			for(CentroCosto centroCosto : lstCentroCosto){
				Listitem item = new Listitem();
				Listcell cell = new Listcell(centroCosto.getCodigo());
				cell.setStyle("padding: 1px;font-size:11px !important");
				item.appendChild(cell);
				cell = new Listcell(centroCosto.getDenominacion());
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				cell = new Listcell(centroCosto.getResponsable()==null?"":centroCosto.getResponsable());
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				cell = new Listcell(centroCosto.getTipoCentroCosto()!=null?centroCosto.getTipoCentroCosto().getDenominacion():"");
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				cell = new Listcell();
				Image image = new Image();
				if(centroCosto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					image.setSrc("resources/mp_activo.png");
				else
					image.setSrc("resources/mp_inactivo.png");
				cell.appendChild(image);
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				item.setValue(centroCosto);

				item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						lbxCentroCosto_seleccionar();
					}
				});

				lbxCentroCosto.appendChild(item);
			}
		}
	}


	private void listarTiposCentroCosto(List<TipoCentroCosto> lstTipoCentroCosto){
		lbxTiposCentroCosto.getItems().clear();
		if(lstTipoCentroCosto.size()>0){
			for(TipoCentroCosto tipoCentroCosto : lstTipoCentroCosto){
				Listitem item = new Listitem();
				Listcell cell = new Listcell(tipoCentroCosto.getDenominacion());
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				cell = new Listcell();
				Image image = new Image();
				if(tipoCentroCosto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					image.setSrc("resources/mp_activo.png");
				else
					image.setSrc("resources/mp_inactivo.png");
				cell.appendChild(image);
				cell.setStyle("padding: 1px;");
				item.appendChild(cell);
				item.setValue(tipoCentroCosto);

				item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						lbxTipoCentroCosto_seleccionar();
					}
				});

				lbxTiposCentroCosto.appendChild(item);
			}
		}
	}

	private void lbxTipoCentroCosto_seleccionar(){
		if(lbxTiposCentroCosto.getSelectedItem().getValue() instanceof TipoCentroCosto){
			tipoCentroCosto = (TipoCentroCosto)lbxTiposCentroCosto.getSelectedItem().getValue();
			txtDenominacionTipoCentroCosto.setText(tipoCentroCosto.getDenominacion());
			Util.seleccionarValorItemCombobox(cmbEstadoTipoCentroCosto, (tipoCentroCosto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?Constantes.VALUE_ACTIVO:Constantes.VALUE_INACTIVO));
			tlbrbtnEditar.setDisabled(false);
		}
	}

	private void lbxCentroCosto_seleccionar(){
		cmbGrupoCentroCosto.setSelectedIndex(0);
		if(lbxCentroCosto.getSelectedItem().getValue() instanceof CentroCosto){
			centroCosto = (CentroCosto)lbxCentroCosto.getSelectedItem().getValue();
			txtResponsableCentroCosto.setText(centroCosto.getResponsable()==null?"":centroCosto.getResponsable());
			txtDenominacionCentroCosto.setText(centroCosto.getDenominacion());
			txtCodigoCentroCosto.setText(centroCosto.getCodigo());
			Util.seleccionarValorItemCombobox(cmbEstadoCentroCosto, (centroCosto.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?Constantes.VALUE_ACTIVO:Constantes.VALUE_INACTIVO));
			if(centroCosto.getTipoCentroCosto()!=null)
				Util.seleccionarValorItemCombo(TipoCentroCosto.class, cmbGrupoCentroCosto, centroCosto.getTipoCentroCosto().getId());
			tlbrbtnEditar.setDisabled(false);
		}
	}

	private void guardarCentroCosto(){
		try{
			if(!(cmbConcesionarioCentroCosto.getSelectedItem().getValue() instanceof Concesionario))
				throw new CentroCostoException(CentroCostoException.CONCESIONARIO_NULL);
			else if(txtDenominacionCentroCosto.getText().trim().isEmpty())
				throw new CentroCostoException(CentroCostoException.DENOMINACION_NULL);
			else if(txtCodigoCentroCosto.getText().trim().isEmpty())
				throw new CentroCostoException(CentroCostoException.CODIGO_NULL);
			else if(cmbEstadoCentroCosto.getSelectedItem().getValue() == null)
				throw new CentroCostoException(CentroCostoException.ESTADO_NULL);

			if(centroCosto==null)
				centroCosto = new CentroCosto();

			centroCosto.setConcesionario(concesionario);
			if(cmbGrupoCentroCosto.getSelectedItem().getValue() instanceof TipoCentroCosto)
				centroCosto.setTipoCentroCosto((TipoCentroCosto)cmbGrupoCentroCosto.getSelectedItem().getValue());
			centroCosto.setResponsable(txtResponsableCentroCosto.getText().trim().isEmpty()?null:txtResponsableCentroCosto.getText().trim().toUpperCase());
			centroCosto.setDenominacion(txtDenominacionCentroCosto.getText().trim().toUpperCase());
			centroCosto.setCodigo(txtCodigoCentroCosto.getText().trim().toUpperCase());
			centroCosto.setEstadoRegistro(cmbEstadoCentroCosto.getSelectedItem().getValue().toString());
			if(centroCosto.getId()==null)
				UtilData.auditarRegistro(centroCosto, getUsuario(), Executions.getCurrent());
			else
				UtilData.auditarRegistro(centroCosto, true, getUsuario(), Executions.getCurrent());

			String msg = "";
			if (centroCosto.getId() == null)
				msg = Messages.getString("WndAsistenteAgencias.question.guardarCentroCosto");
			else
				msg = Messages.getString("WndAsistenteAgencias.question.actualizarCentroCosto");
			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							int result = Constantes.FAILURE;
							if(centroCosto.getId()==null){
								result = ServiceLocator.getCentroCostoManager().guardar(centroCosto);
								if(result==Constantes.CORRECT)
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.exitoGuardarCentroCosto"));
								else
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorGuardarCentroCosto"));
							}else{
								result = ServiceLocator.getCentroCostoManager().actualizar(centroCosto);
								if(result==Constantes.CORRECT)
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.exitoActualizarCentroCosto"));
								else
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorActualizarCentroCosto"));
							}
							centroCosto = null;
							tlbrbtnCancelar_cancelar();
							limpiar();
							tlbrbtnBuscar_buscar();

							tlbrbtnNuevo.setDisabled(false);
							tlbrbtnBuscar.setDisabled(false);
							tlbrbtnEditar.setDisabled(true);
							tlbrbtnCancelar.setDisabled(true);
							tlbrbtnGuardar.setDisabled(true);

						}
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
					}
				}
			});
		}catch(CentroCostoException ccex){
			if(ccex.getTipo().intValue()==CentroCostoException.CONCESIONARIO_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaConcesionarioCentroCosto"));
			else if(ccex.getTipo().intValue()==CentroCostoException.DENOMINACION_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaDenominacionCentroCosto"));
			else if(ccex.getTipo().intValue()==CentroCostoException.CODIGO_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaCodigoCentroCosto"));
			else if(ccex.getTipo().intValue()==CentroCostoException.ESTADO_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaEstadoCentroCosto"));
			siGuardarFalla();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			siGuardarFalla();
		}
	}

	private void guardarTipoCentroCosto(){
		try{
			if(!(cmbConcesionarioTipoCentroCosto.getSelectedItem().getValue() instanceof Concesionario))
				throw new CentroCostoException(CentroCostoException.CONCESIONARIO_NULL);
			else if(txtDenominacionTipoCentroCosto.getText().trim().isEmpty())
				throw new CentroCostoException(CentroCostoException.DENOMINACION_NULL);
			else if(cmbEstadoTipoCentroCosto.getSelectedItem().getValue() == null)
				throw new CentroCostoException(CentroCostoException.ESTADO_NULL);

			if(tipoCentroCosto==null)
				tipoCentroCosto = new TipoCentroCosto();

			tipoCentroCosto.setConcesionario(concesionario);
			tipoCentroCosto.setDenominacion(txtDenominacionTipoCentroCosto.getText().trim().toUpperCase());
			tipoCentroCosto.setEstadoRegistro(cmbEstadoTipoCentroCosto.getSelectedItem().getValue().toString());
			if(tipoCentroCosto.getId()==null)
				UtilData.auditarRegistro(tipoCentroCosto, getUsuario(), Executions.getCurrent());
			else
				UtilData.auditarRegistro(tipoCentroCosto, true, getUsuario(), Executions.getCurrent());

//			String msg = "";
//			if (tipoCentroCosto.getId() == null)
//			msg = Messages.getString("Generales.query.guardar");
//			else
//				msg = Messages.getString("WndAsistenteAgencias.question.actualizarTipoCentroCosto");
			Messagebox.show(Messages.getString("Generales.query.guardar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){
							if(tipoCentroCosto.getId()==null)
								ServiceLocator.getTipoCentroCostoManager().guardar(tipoCentroCosto);
							else
								ServiceLocator.getTipoCentroCostoManager().actualizar(tipoCentroCosto);
							DlgMessage.information(Messages.getString("Generales.information.exitoGuardar"));

							tipoCentroCosto = null;
							tlbrbtnCancelar_cancelar();
							limpiar();
							tlbrbtnBuscar_buscar();
							loadGruposCentroCosto();

							tlbrbtnNuevo.setDisabled(false);
							tlbrbtnBuscar.setDisabled(false);
							tlbrbtnEditar.setDisabled(true);
							tlbrbtnCancelar.setDisabled(true);
							tlbrbtnGuardar.setDisabled(true);
						}
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
					}
				}
			});
		}catch(CentroCostoException ccex){
			if(ccex.getTipo().intValue()==CentroCostoException.CONCESIONARIO_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaConcesionarioCentroCosto"));
			else if(ccex.getTipo().intValue()==CentroCostoException.DENOMINACION_NULL)
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.faltaDenominacionTipoCentroCosto"),txtDenominacionTipoCentroCosto);
			else if(ccex.getTipo().intValue()==CentroCostoException.ESTADO_NULL)
				DlgMessage.information(Messages.getString("WWndAsistenteAgencias.information.faltaTipoEstadoCentroCosto"),cmbEstadoTipoCentroCosto);
			siGuardarFalla();
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
			siGuardarFalla();
		}
	}

	/**
	 * Carga los tipos de centro de costo
	 * @throws Exception
	 */
	private void loadGruposCentroCosto()throws Exception{
		Util.limpiarCombobox(cmbGrupoCentroCosto);
		Util.limpiarCombobox(cmbBusqGrupoCentroCosto);

		TreeMap<String, Object>parametros=new TreeMap<>();
		parametros.put("concesionario", concesionario);
		parametros.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		UtilData.cargarDataCombo(cmbGrupoCentroCosto, TipoCentroCosto.class, parametros, false);
		UtilData.cargarDataCombo(cmbBusqGrupoCentroCosto, TipoCentroCosto.class, parametros, true);
	}

	/**
	 * Realiza la busqueda de los usuarios de la agencia.
	 */
	private void buscarUsuarios(){
		try{
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
   			criteriosBusqueda.put("agencia.id", ((Agencia)cmbAgencia.getSelectedItem().getValue()).getId());
			List<String> criteriosOrdenar = new ArrayList<>();
			criteriosOrdenar.add("apellidoPaterno");
			criteriosOrdenar.add("apellidoMaterno");
			criteriosOrdenar.add("nombre");
			List<Usuario> lstUsuarios = ServiceLocator.getUsuarioManager().buscarPorX(criteriosBusqueda, criteriosOrdenar);
			listarUsuarios(lstUsuarios);
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	/**
	 * Lista los usuarios encontrados
	 * @param lstUsuarios	: Lista de usuarios a mostrar.
	 */
	private void listarUsuarios(List<Usuario> lstUsuarios){
		lbxUsuarios.getItems().clear();
		if(lstUsuarios.size()>0){
			for(Usuario usuario : lstUsuarios){
				Listitem listitem = new Listitem();
				Listcell listcell = new Listcell(usuario.toString());
				listitem.appendChild(listcell);
				listcell = new Listcell(usuario.getLogin());
				listitem.appendChild(listcell);
				listcell = new Listcell();
				Image image = new Image();
				if(usuario.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO))
					image.setSrc("resources/mp_activo.png");
				else
					image.setSrc("resources/mp_inactivo.png");
				listcell.appendChild(image);
				listitem.appendChild(listcell);
				listitem.setValue(usuario);
				lbxUsuarios.appendChild(listitem);
			}
		}else
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.usuariosNoEncontrados"));
	}

	private void lbxUsuario_seleccionar(){
		try{
			if(lbxUsuarios.getSelectedItem().getValue() instanceof Usuario){
				usuario = (Usuario)lbxUsuarios.getSelectedItem().getValue();
				Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, usuario.getAgencia().getId());
				txtApellidoPaterno.setText(usuario.getApellidoPaterno());
				txtApellidoMaterno.setText(usuario.getApellidoMaterno()==null?"":usuario.getApellidoMaterno());
				txtNombres.setText(usuario.getNombre());
				txtLogin.setText(usuario.getLogin());
				txtPassword.setText(usuario.getPwdNormal());
				Util.seleccionarValorItemCombobox(cmbEstadoUsuario, (usuario.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)?Constantes.VALUE_ACTIVO:Constantes.VALUE_INACTIVO));
				List<UsuarioRol>lstUsuarioRol=ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(usuario.getId());
				if(lstUsuarioRol.size()>0){
//					usuarioRol=ServiceLocator.getUsuarioRolManager().buscarXIdUsuario(usuario.getId());
					usuarioRol=lstUsuarioRol.get(0);
					Util.seleccionarValorItemCombo(Rol.class, cmbRol, usuarioRol.getRol().getId());
				}


				tlbrbtnNuevo.setDisabled(false);
				tlbrbtnBuscar.setDisabled(false);
				tlbrbtnEditar.setDisabled(false);
				tlbrbtnCancelar.setDisabled(true);
				tlbrbtnGuardar.setDisabled(true);
				deshabilitar(true);
			}
		}catch(Exception ex){
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	private void guardarUsuario(){
		try{
			if(!(cmbAgencia.getSelectedItem().getValue() instanceof Agencia))
				throw new AgenciaNullException();
			else if (txtApellidoPaterno.getValue().trim().isEmpty())
				throw new ApellidoPaternoNullException();
			else if (txtNombres.getValue().trim().isEmpty())
				throw new NombresNullException();
			else if(!(cmbEstadoUsuario.getSelectedItem().getValue() instanceof String)){
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoEstado"), cmbEstadoUsuario);
				siGuardarFalla();
				return;
			}else if (txtLogin.getValue().trim().isEmpty())
				throw new LoginNullException();
			else if (txtPassword.getValue().trim().isEmpty())
				throw new  PasswordException(PasswordException.PASSWORD_NULL);
			else if(!(cmbRol.getSelectedItem().getValue() instanceof Rol))
				throw new RolNullException();

			if(!txtEmailInfo.isDisabled() && txtEmailInfo.getText().trim().isEmpty())
				throw new EmailNullException(EmailNullException.EMAIL_ENVIO_INFO);
			else if (!txtEmailInfo.isDisabled()){
				if(!(UtilData.validateEmail(txtEmailInfo.getText().trim()))){
					throw new EmailNullException(EmailNullException.EMAIL_INVALID);
				}
			}

			if(usuario==null)
				usuario = new Usuario();

//			usuario.setAgencia((Agencia)cmbAgencia.getSelectedItem().getValue());
//			usuario.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
//			usuario.setApellidoMaterno(txtApellidoMaterno.getText().trim().isEmpty()?null:txtApellidoMaterno.getText().trim().toUpperCase());
//			usuario.setNombre(txtNombres.getText().trim().toUpperCase());
//			usuario.setEstadoRegistro(cmbEstadoUsuario.getSelectedItem().getValue().toString());
//			usuario.setLogin(txtLogin.getText().toLowerCase());
//			usuario.setPassword(txtPassword.getText());
//			usuario.setTipoPassword(Usuario.TIPPAS_ALEATORIO);
//			usuario.setEmailInfo(txtEmailInfo.getText());
//			usuario.setUsuarioHardware((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue());
//			usuario.setTipoSeguridad(Constantes.FALSE_VALUE);
//			if(usuario.getId()==null)
//				UtilData.auditarRegistro(usuario, getUsuario(), Executions.getCurrent());
//			else
//				UtilData.auditarRegistro(usuario, true, getUsuario(), Executions.getCurrent());

			String msg = "";
			if (usuario==null)
				msg = Messages.getString("WndAsistenteAgencias.question.guardarUsuario");
			else
				msg = Messages.getString("WndAsistenteAgencias.question.actualizarUsuario");

			Messagebox.show(msg, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e){
					try{
						if(e.getName().equals("onYes")){

							if(usuario==null)
								usuario = new Usuario();
							usuario.setAgencia((Agencia)cmbAgencia.getSelectedItem().getValue());
							usuario.setApellidoPaterno(txtApellidoPaterno.getText().trim().toUpperCase());
							usuario.setApellidoMaterno(txtApellidoMaterno.getText().trim().isEmpty()?null:txtApellidoMaterno.getText().trim().toUpperCase());
							usuario.setNombre(txtNombres.getText().trim().toUpperCase());
							usuario.setEstadoRegistro(cmbEstadoUsuario.getSelectedItem().getValue().toString());
							usuario.setLogin(txtLogin.getText().trim().toLowerCase());
							usuario.setPwdNormal(txtPassword.getText());
							usuario.setTipoPassword(Usuario.TIPPAS_ALEATORIO);
							usuario.setEmailInfo(txtEmailInfo.getText().trim().isEmpty()?null:txtEmailInfo.getText().trim().toLowerCase());
							usuario.setUsuarioHardware((UsuarioHardware)cmbUsuarioHardware.getSelectedItem().getValue());
							usuario.setTipoSeguridad(Constantes.FALSE_VALUE);

							int result = Constantes.FAILURE;
							Rol rol= ((Rol)cmbRol.getSelectedItem().getValue());

							if(usuario.getId()==null){
								UtilData.auditarRegistro(usuario, getUsuario(), Executions.getCurrent());
								result = ServiceLocator.getUsuarioManager().guardar(usuario);
								if(result==Constantes.CORRECT){

									usuarioRol= new UsuarioRol();
									usuarioRol.setRol(rol);
									usuarioRol.setUsuario(usuario);
									guardarUsuarioRol(ACTION_NEW,usuarioRol);
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.exitoGuardarUsuario"));
								}else
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorGuardarUsuario"));
							}else{
								UtilData.auditarRegistro(usuario, true, getUsuario(), Executions.getCurrent());
								ServiceLocator.getUsuarioManager().actualizar(usuario);

								result = Constantes.CORRECT;
								if(rol.getId().intValue()!=usuarioRol.getRol().getId().intValue()){
									usuarioRol.setEstadoRegistro(Constantes.VALUE_INACTIVO);
									UtilData.auditarRegistro(usuarioRol, true, getUsuario(), Executions.getCurrent());
									ServiceLocator.getUsuarioRolManager().actualizar(usuarioRol);

									/*	Para validar si el usuario tuvo en algun momento el rol seleccionado y por algun motivo este esta inactivo	*/
									usuarioRol= ServiceLocator.getUsuarioRolManager().buscarXidUsuarioAndIdRol(usuario.getId(), rol.getId());
									if(usuarioRol!=null){
										/* Solo actualiza el estado*/
										guardarUsuarioRol(ACTION_MODIFY, usuarioRol);
									}else{
										/* Inserta un nuevo registro*/
										usuarioRol= new UsuarioRol();
										usuarioRol.setRol(rol);
										usuarioRol.setUsuario(usuario);
										guardarUsuarioRol(ACTION_NEW, usuarioRol);
									}
								}

								if(result==Constantes.CORRECT)
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.exitoActualizarUsuario"));
								else
									DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.errorActualizarUsuario"));
							}
							usuario = null;
							cmbEstadoUsuario.setSelectedIndex(0);
							tlbrbtnCancelar_cancelar();
							limpiar();
							tlbrbtnBuscar_buscar();

							tlbrbtnNuevo.setDisabled(false);
							tlbrbtnBuscar.setDisabled(false);
							tlbrbtnEditar.setDisabled(true);
							tlbrbtnCancelar.setDisabled(true);
							tlbrbtnGuardar.setDisabled(true);
						}
					}catch(UsuarioLoginDuplicadoException uldex){
						DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.usuarioDuplicado"), txtLogin);
						siGuardarFalla();
					}catch(Exception ex){
						DlgMessage.error(this.getClass().getSimpleName() + " " + ex.getMessage());
						siGuardarFalla();
					}
				}
			});
		}catch(RolNullException rne){
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoRol"),cmbRol);
		}catch(ApellidoPaternoNullException apnex){
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noApellidoPaterno"),txtApellidoPaterno);
		}catch(NombresNullException nnex){
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noNombres"),txtNombres);
		}catch(LoginNullException lnex){
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noLogin"),txtLogin);
		}catch(PasswordException cp){
			if(cp.getTipo().intValue()==PasswordException.PASSWORD_NULL){
				DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noPassword"),txtPassword);
			}
			siGuardarFalla();
		}catch(AgenciaNullException anex){
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.noSeleccionoAgencia"), cmbAgencia);
		}catch (EmailNullException emex) {
			siGuardarFalla();
			DlgMessage.information(Messages.getString("WndAsistenteAgencias.information.emailinvalid"), txtEmailInfo);
		}catch(Exception ex){
			siGuardarFalla();
			DlgMessage.error(this.getClass().getSimpleName()+" "+ex.getMessage());
		}
	}

	public void generarPassword(){
		String passwd = Util.generarPassword();
		txtPassword.setText(passwd);
	}

	/**
	 * Guarda el Usuario rol
	 * @param action		: accion a realizar (nuevo actualizar)
	 * @param usuarioRol	: Objeto UsuarioRol
	 * @throws Exception
	 */
	private void guardarUsuarioRol(int action, UsuarioRol usuarioRol) throws Exception{
		UsuarioRolID usuarioRolID = new UsuarioRolID();
		usuarioRolID.setIdRol(usuarioRol.getRol().getId());
		usuarioRolID.setIdUsuario(usuarioRol.getUsuario().getId());

		usuarioRol.setUsuarioRolID(usuarioRolID);
		usuarioRol.setRol(usuarioRol.getRol());
		usuarioRol.setUsuario(usuarioRol.getUsuario());
		usuarioRol.setEstadoRegistro(Constantes.VALUE_ACTIVO);

		switch (action) {
			case ACTION_NEW:
				UtilData.auditarRegistro(usuarioRol, this.getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioRolManager().guardar(usuarioRol);
				break;

			case ACTION_MODIFY:
				UtilData.auditarRegistro(usuarioRol, true, this.getUsuario(), Executions.getCurrent());
				ServiceLocator.getUsuarioRolManager().actualizar(usuarioRol);
				break;
		}
	}

	private void siGuardarFalla(){
		tlbrbtnNuevo.setDisabled(true);
		tlbrbtnBuscar.setDisabled(true);
		tlbrbtnEditar.setDisabled(true);
		tlbrbtnCancelar.setDisabled(false);
		tlbrbtnGuardar.setDisabled(false);
	}

	public void cmbUsuario_onChange(){
		txtEmailInfo.setText("");
		if(cmbEstadoUsuario.getSelectedItem().getValue() instanceof String)
			txtEmailInfo.setDisabled(cmbEstadoUsuario.getSelectedItem().getValue().toString().equals(Constantes.VALUE_INACTIVO));
	}

	public void btnNuevo_OnClick(){
		//Informacion cliente
		txtBuscar.setText("");
		txtRUCCliente.setText("");
		txtRazonSocialCliente.setText("");
		txtDireccionCliente.setText("");
		txtUbigeoCliente.setText("");
		dblbxLineaAprobada.setText("");
		dblbxSobregiro.setText("");
		dtbxFechaActivacionCliente.setValue(null);
		dtbxFechaSuspensionCliente.setValue(null);
		lblInformativo.setValue("");

		//Infromaci�n de concesionario.
		txtRUCConcesionario.setText("");
		txtRazonSocialConcesionario.setText("");
		txtDireccionConcesionario.setText("");
		dtbxFechaActivacionConcesionario.setText("");
		ibxComision.setText("");
		cmbTipoComision.setSelectedIndex(0);
		ckbIncluyeIgv.setChecked(false);

		//Informaci�n de la Agencia
		cmbAgencia.setSelectedIndex(0);
		cmbConcesionario.setSelectedIndex(0);
		cmbLocalidad.setSelectedIndex(0);
		txtUbigeoAgenciaId.setText("");
		txtUbigeoAgencia.setText("");
		txtDenominacionAgencia.setText("");
		txtNombreCortoAgencia.setText("");
		chkCentroCosto.setChecked(false);
		//Centro costo
		cmbConcesionarioCentroCosto.setText("");
		txtResponsableCentroCosto.setText("");
		txtDenominacionCentroCosto.setText("");
		txtCodigoCentroCosto.setText("");
		cmbEstadoCentroCosto.setText("");
		txtCorrelativo.setText("");
		Util.limpiarListbox(lbxCentroCosto);

		//Informa usuario
		cmbAgencia.setSelectedIndex(0);
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtNombres.setText("");
		cmbEstadoUsuario.setSelectedIndex(0);
		txtPassword.setText("");
//		txtEmailInfo.setText(null);
		Util.limpiarListbox(lbxUsuarios);

		tabCliente.setDisabled(false);
		tabConcesionario.setDisabled(true);
		tabAgencia.setDisabled(true);
		tabUsuarios.setDisabled(true);

		btnMostrarTabConcesionario.setDisabled(true);

		tabCliente.setSelected(true);
		txtBuscar.setFocus(true);
	}

	private void generarCorrelativo(Agencia agencia) throws Exception{
		Integer ultimaSeria= Integer.valueOf(txtCorrelativo.getText().substring(0,txtCorrelativo.getText().indexOf("-")).trim() );

		//Genera Especie Valorada.
		EspecieValorada especieValorada= new EspecieValorada();
		if(agencia.getTipoAgencia().getId().intValue()==Constantes.ID_TIPAGE_CORPORATIVO)
			especieValorada.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO));
		else
			especieValorada.setTipoComprobante(new TipoComprobante(Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES));
		especieValorada.setAgencia(agencia);
		especieValorada.setSerie(ultimaSeria);
		especieValorada.setCorrelativoInicial(Long.valueOf(1));
		especieValorada.setCorrelativoFinal(Long.valueOf(999999999));
		especieValorada.setCorrelativoActual(especieValorada.getCorrelativoInicial());
		especieValorada.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(especieValorada, getUsuario(), Executions.getCurrent());
		ServiceLocator.getEspecieValoradaManager().guardar(especieValorada);
	}
//
//	private void siBuscarFalla(){
//		tlbrbtnNuevo.setDisabled(false);
//		tlbrbtnBuscar.setDisabled(false);
//		tlbrbtnEditar.setDisabled(true);
//		tlbrbtnCancelar.setDisabled(true);
//		tlbrbtnGuardar.setDisabled(true);
//	}
//
//	private void siEditarFalla(){
//		tlbrbtnNuevo.setDisabled(false);
//		tlbrbtnBuscar.setDisabled(false);
//		tlbrbtnEditar.setDisabled(false);
//		tlbrbtnCancelar.setDisabled(true);
//		tlbrbtnGuardar.setDisabled(true);
//	}
//
//	private void siNuevoFalla(){
//		tlbrbtnNuevo.setDisabled(false);
//		tlbrbtnBuscar.setDisabled(false);
//		tlbrbtnEditar.setDisabled(true);
//		tlbrbtnCancelar.setDisabled(true);
//		tlbrbtnGuardar.setDisabled(true);
//	}
}
