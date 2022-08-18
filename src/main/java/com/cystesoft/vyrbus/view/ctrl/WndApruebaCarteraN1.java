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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.CarteraCliente;
import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.LineaContadoCliente;
import com.cystesoft.vyrbus.model.bean.SolicitudCartera;
import com.cystesoft.vyrbus.model.bean.SolicitudClienteCredito;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.UsuarioAprobador;
import com.cystesoft.vyrbus.service.exceptions.MotivoDesapruebaNullException;
import com.cystesoft.vyrbus.service.exceptions.UsuarioAprobadorNullException;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 * @author JABANTO
 */
public class WndApruebaCarteraN1 extends WndBase implements Serializable{

	private static final long serialVersionUID = 1L;

	/*Tabs*/
	private Tab tabHistSilicitudes;
	private Tab tabPendientes;
	Boolean selectPendientes=false; //Tru(selecciona el tabHistorial solicitudes), (false) Solicitudes pendientes.

	private Listbox listPendientes;
	private Button btnBuscar;
	private Grid grPendientes;
	private Combobox cmbCliente;

	private Button btnCancelar;
	private Button btnAceptar;

	/*Datos del cliente*/
//	private Grid grCliente;
	private Groupbox grbCliente;
	private Label lblRuc;
	private Label lblRazonSocial;
	private Label lblBaseHistorica;
	private Label lblOrigen;

	/*Datos de la solicitud*/
	private Groupbox grbSolicitud;
	//private Grid grSolicitud;

	/*Datos solicitud contado*/
	private Grid grContado;
	private Textbox txtFechaSolcitud_Contado;
	private Textbox txtFuncionario_Contado;
	private Doublebox dbxDescuentoBaja_Contado;
	private Doublebox dbxDescuentoAlta_Contado;
	private Checkbox chbEsComisionable_Contado;
	private Radio rbAprueba_Contado;
	private Radio rbAnular_Contado;
	private Textbox txtMotivoAnulacion_Contado;
	private Groupbox grbAproDesaContado;

	/*Datos solicitud credito*/
	private Grid grCredito;
	private Label lblFechaSolicitud;
	private Label lblFuncionario;
	private Label lblCreditoSolicitado;
	private Label lblSobregiro;
	private Label lblMontoSobregiro;
	private Checkbox chbEsComisionable_Credito;
	private Checkbox chbesCanje;
	private Checkbox chbEsAmpliacion;
//	private Doublebox dbxDescuentoBaja_Credito;
//	private Doublebox dbxDescuentoAlta_Credito;
	private Textbox txtObservaciones;
	private Doublebox dbxCreditoAprobado;
	private Radio rbAprueba_Credito;
	private Radio rbAnular_Credito;
	private Textbox txtMotivoAnulacion_Credito;
	private Groupbox grbAproDesaCredito;
	private Label lblAmpliacion;


	/*Historia Solicitudes*/
	private Listbox listHistSolicitudes;
	private Datebox dtxFechaInicio;
	private Datebox dtxFechaFinal;
	private Combobox cmbEstadoSolicitud;
	private Combobox cmbClienteHist;
	private Button btnBuscarSolApro;

	/*class*/
	public SolicitudCartera solicitudCartera=null;
	public SolicitudClienteCredito solicitudClienteCredito=null;
//	private LineaContadoCliente lineaContadoCliente=null;
//	private CarteraCliente carteraCliente=null;
	private UsuarioAprobador  usuarioAprobador=null;
	/*Otras*/
	private int action;


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		/*Tabs*/
		tabHistSilicitudes=(Tab)this.getFellow("tabHistSilicitudes");
		tabPendientes=(Tab)this.getFellow("tabPendientes");

		/*Tab Solcicitudes pendientes por aprobar*/
		listPendientes=(Listbox)this.getFellow("listPendientes");
		btnBuscar=(Button)this.getFellow("btnBuscar");
		btnCancelar=(Button)this.getFellow("btnCancelar");
		btnAceptar=(Button)this.getFellow("btnAceptar");
		grPendientes=(Grid)this.getFellow("grPendientes");
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		/*Datos de la solicitud*/
		grbSolicitud=(Groupbox)this.getFellow("grbSolicitud");

		/*Datos del cliente*/
		grbCliente=(Groupbox)this.getFellow("grbCliente");
//		grCliente=(Grid)this.getFellow("grCliente");
		lblRuc=(Label)this.getFellow("lblRuc");
		lblRazonSocial=(Label)this.getFellow("lblRazonSocial");
		lblBaseHistorica=(Label)this.getFellow("lblBaseHistorica");
		lblOrigen=(Label)this.getFellow("lblOrigen");

		/*Datos de la solicitud contado*/
		grContado=(Grid)this.getFellow("grContado");
		txtFechaSolcitud_Contado=(Textbox)this.getFellow("txtFechaSolcitud_Contado");
		txtFuncionario_Contado=(Textbox)this.getFellow("txtFuncionario_Contado");
		dbxDescuentoBaja_Contado=(Doublebox)this.getFellow("dbxDescuentoBaja_Contado");
		dbxDescuentoAlta_Contado=(Doublebox)this.getFellow("dbxDescuentoAlta_Contado");
		rbAprueba_Contado=(Radio)this.getFellow("rbAprueba_Contado");
		rbAnular_Contado=(Radio)this.getFellow("rbAnular_Contado");
		txtMotivoAnulacion_Contado=(Textbox)this.getFellow("txtMotivoAnulacion_Contado");
		chbEsComisionable_Contado=(Checkbox)this.getFellow("chbEsComisionable_Contado");
		grbAproDesaContado=(Groupbox)this.getFellow("grbAproDesaContado");

		/*Datos solicitud credito*/
		grCredito=(Grid)this.getFellow("grCredito");
		lblFechaSolicitud=(Label)this.getFellow("lblFechaSolicitud");
		lblFuncionario=(Label)this.getFellow("lblFuncionario");
		lblCreditoSolicitado=(Label)this.getFellow("lblCreditoSolicitado");
		lblSobregiro=(Label)this.getFellow("lblSobregiro");
		lblMontoSobregiro=(Label)this.getFellow("lblMontoSobregiro");
		chbEsComisionable_Credito=(Checkbox)this.getFellow("chbEsComisionable_Credito");
		chbesCanje=(Checkbox)this.getFellow("chbesCanje");
		chbEsAmpliacion=(Checkbox)this.getFellow("chbEsAmpliacion");
//		dbxDescuentoBaja_Credito=(Doublebox)this.getFellow("dbxDescuentoBaja_Credito");
//		dbxDescuentoAlta_Credito=(Doublebox)this.getFellow("dbxDescuentoAlta_Credito");
		txtObservaciones=(Textbox)this.getFellow("txtObservaciones");
		dbxCreditoAprobado=(Doublebox)this.getFellow("dbxCreditoAprobado");
		rbAprueba_Credito=(Radio)this.getFellow("rbAprueba_Credito");
		rbAnular_Credito=(Radio)this.getFellow("rbAnular_Credito");
		txtMotivoAnulacion_Credito=(Textbox)this.getFellow("txtMotivoAnulacion_Credito");
		grbAproDesaCredito=(Groupbox)this.getFellow("grbAproDesaCredito");
		lblAmpliacion=(Label)this.getFellow("lblAmpliacion");

		/*Historial de Solicitudes*/
		listHistSolicitudes=(Listbox)this.getFellow("listHistSolicitudes");
		dtxFechaInicio=(Datebox)this.getFellow("dtxFechaInicio");
		dtxFechaFinal=(Datebox)this.getFellow("dtxFechaFinal");
		cmbEstadoSolicitud=(Combobox)this.getFellow("cmbEstadoSolicitud");
		cmbClienteHist=(Combobox)this.getFellow("cmbClienteHist");
		btnBuscarSolApro=(Button)this.getFellow("btnBuscarSolApro");

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sis vyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			usuarioAprobador=(UsuarioAprobador)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_APROBADOR);
			if(usuarioAprobador==null)
				throw new UsuarioAprobadorNullException();
//			else{
//				//Validando que el usuario aprobador tenga el nivel correcto
//				if(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_UNO)
//					throw new UsuarioAprobadorNullException();
//			}

			/*Pendientes*/
			CargarListaPendientes();
			cargarClientesSolicitudPendientes();
			/*solicitud*/
			grbSolicitud.setVisible(false);
//			grSolicitud.setVisible(false);

			/*cliente*/
			grbCliente.setVisible(false);
			/*Solicitud Contado*/
			grContado.setVisible(false);
			grbAproDesaContado.setVisible(grContado.isVisible());
			dbxDescuentoAlta_Contado.setLocale(Locale.US);
			dbxDescuentoBaja_Contado.setLocale(Locale.US);
			/*Solicitud Credito*/
			grCredito.setVisible(false);
			grbAproDesaCredito.setVisible(grCredito.isVisible());
//			dbxDescuentoAlta_Credito.setLocale(Locale.US);
//			dbxDescuentoBaja_Credito.setLocale(Locale.US);
//			lblCreditoSolicitado.setLocale(Locale.US);
//			lblSobregiro.setLocale(Locale.US);
			dbxCreditoAprobado.setLocale(Locale.US);

			/*Historial de Solicitudes*/
			UtilData.cargarEstadoSolicitud(cmbEstadoSolicitud, true);
			UtilData.cargarClientesSolicitud(cmbClienteHist, true);
			cmbEstadoSolicitud.setSelectedIndex(0);
			dtxFechaInicio.setValue(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
			dtxFechaFinal.setValue(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));

			/*---*/
			Util.disabledBtnBuscar(false, btnBuscar, accesoConsultar());
			Util.disabledBtnBuscar(false, btnBuscarSolApro, accesoConsultar());

		}catch (UsuarioAprobadorNullException uanex){
			DlgMessage.information(Messages.getString("wndApruebaCartera.infomation.UsuarioAprobadorNull"));
			closeTabWindow();
		}

	}

	/**
	 * Lista las solicitudes pendientes por aprobar
	 */
	public void CargarListaPendientes(){
//		TreeMap<String, Object> criterioBusqueda = new TreeMap<String, Object>();
//		List<String> criteriosOrdenar= new ArrayList<String>();
//		criteriosOrdenar.add("fechaSolicitud");
//
//		criterioBusqueda.put("estadoSolicitud", Constantes.ESTADOSOLCAR_EN_ESPERA);
//		criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
//		ArrayList<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManger().buscarPorX(criterioBusqueda, criteriosOrdenar);
		List<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManager().buscarPendientesN1();
		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listPendientes);

		for(SolicitudClienteCredito solicitudCredito: list){
			SolicitudCartera solicitudCartera=solicitudCredito.getSolicitudCartera();
			Usuario funcionario = solicitudCartera.getUsuario();
			Cliente cliente=solicitudCartera.getCliente();
			String tipo="";
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(funcionario.toString()); //.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			item.appendChild(cell);
			cell=new Listcell(cliente.getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			/*Verifica si la solicitud es o no credito*/
			if (solicitudCredito.getLineaCreditoSolicitada()==0)
				tipo=Constantes.TIPCON_CONTADO_DESC;
			else{
				if(solicitudCredito.getEsAmpliacion().equals(Constantes.SI))
					tipo=Constantes.TIPCON_CREDITO_DESC+"(Ampl.)";
				else tipo=Constantes.TIPCON_CREDITO_DESC;
			}
			cell=new Listcell(tipo);
			item.appendChild(cell);

			final Toolbarbutton button= new Toolbarbutton("VER SOLICITUD ");
			button.setId(String.valueOf(x-1));
			button.setStyle("color:blue; font-style:inherit; font-size: 9px !important");
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);

			button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Listitem listitem=listPendientes.getItemAtIndex(Integer.valueOf(button.getId()));
					SolicitudClienteCredito solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(((SolicitudClienteCredito)listitem.getValue()).getId());
					showDetalleSolicitud(solicitudClienteCredito);
//					showDetalleSolicitud((SolicitudClienteCredito)listitem.getValue());

					visibleListPendientes(false);
					grbCliente.setVisible(true);
					btnCancelar.setVisible(true);
					btnAceptar.setVisible(true);
					grbSolicitud.setVisible(true);
//					lblMontoSobregiro.setDisabled(false);
					tabHistSilicitudes.setDisabled(true);
					DisableAprobarAnular(false);
					selectPendientes=true;
					dbxCreditoAprobado.setReadonly(false);

//					Util.disabledBtnAceptar(false, btnAceptar, accesoGrabar());
					btnAceptar.setDisabled(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_UNO);

					action=Constantes.ACTION_NEW;
				}

			});

			item.setValue(solicitudCredito);
			listPendientes.appendChild(item);
		}
	}

	/**
	 * Carga los clientes con solicitudes pendendientes en el comobobox
	 */
	private void cargarClientesSolicitudPendientes(){
		TreeMap<String, Object> criterioBusqueda = new TreeMap<>();
		List<String> criteriosOrdenar= new ArrayList<>();
		criteriosOrdenar.add("fechaSolicitud");

		criterioBusqueda.put("estadoSolicitud", Constantes.ESTADOSOLCAR_EN_ESPERA);
		criterioBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManager().buscarPendientesN1();
//		ArrayList<SolicitudClienteCredito>list=ServiceLocator.getSolicitudClienteCreditoManger().buscarPorX(criterioBusqueda, criteriosOrdenar);


		UtilData.cargarGenericData(cmbCliente, true);
		for(SolicitudClienteCredito solicitudCredito: list){
			Cliente cliente=solicitudCredito.getSolicitudCartera().getCliente();
			/*Cargar Clientes al Combobox*/
			Comboitem oComboitem = new Comboitem();
			oComboitem.setLabel(cliente.getRazonSocial());
			oComboitem.setValue(cliente.getId());
			cmbCliente.appendChild(oComboitem);
		}
		cmbCliente.setSelectedIndex(0);
	}

	/**
	 * Muestra detalle de la solicitud
	 * @param solicitudClienteCredito : Class SoliciTudCredito
	 */
	private void showDetalleSolicitud(SolicitudClienteCredito solicitudClienteCredito){
		this.solicitudClienteCredito=solicitudClienteCredito;
		solicitudCartera=solicitudClienteCredito.getSolicitudCartera();
		Cliente cliente=solicitudCartera.getCliente();
		Usuario funcionario=solicitudCartera.getUsuario();

		lblRuc.setValue(cliente.getNumeroDocumento());
		lblRazonSocial.setValue(cliente.getRazonSocial());
		if(cliente.getOrigen().equals(Constantes.ORIGEN_LIMA))
			lblOrigen.setValue(Constantes.ORIGEN_LIMA_DESC);
		else lblOrigen.setValue(Constantes.ORIGEN_PROVINCIAS_DESC);
		lblBaseHistorica.setValue(Util.toNumberFormat(solicitudCartera.getBaseHistorica(),2));

		if(solicitudClienteCredito.getEsComisionable().equals(Constantes.SI)){
			chbEsComisionable_Credito.setChecked(true);
			chbEsComisionable_Contado.setChecked(true);
		}else{
			chbEsComisionable_Credito.setChecked(false);
			chbEsComisionable_Contado.setChecked(false);}

		if(solicitudClienteCredito.getLineaCreditoSolicitada()==0){ //CONTADO
			txtFechaSolcitud_Contado.setText(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
			txtFuncionario_Contado.setText(funcionario.toString());//.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			dbxDescuentoBaja_Contado.setValue(solicitudCartera.getDescuentoBaja());
			dbxDescuentoAlta_Contado.setValue(solicitudCartera.getDescuentoAlta());
			rbAprueba_Contado.setChecked(true);
			grContado.setVisible(true);
			grbAproDesaContado.setVisible(grContado.isVisible());

		}else{ // CREDITO
			lblFechaSolicitud.setValue(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
			lblFuncionario.setValue(funcionario.toString());//.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoSolicitada(),2));
			lblSobregiro.setValue(Util.toNumberFormat(solicitudClienteCredito.getSobregiro(),2));
			dbxCreditoAprobado.setValue(solicitudClienteCredito.getLineaCreditoAprobada());
			if(solicitudClienteCredito.getSobregiro()>0)
				lblMontoSobregiro.setValue("% |"+Util.toNumberFormat((dbxCreditoAprobado.getValue()*(solicitudClienteCredito.getSobregiro()/100)),2));
			else
				lblMontoSobregiro.setValue("%");

			if(solicitudClienteCredito.getEsCanje().equals(Constantes.SI))
				chbesCanje.setChecked(true);
			else chbesCanje.setChecked(false);
			if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI))
				chbEsAmpliacion.setChecked(true);
			else chbEsAmpliacion.setChecked(false);
//			dbxDescuentoBaja_Credito.setValue(solicitudCartera.getDescuentoBaja());
//			dbxDescuentoAlta_Credito.setValue(solicitudCartera.getDescuentoAlta());
			txtObservaciones.setText(solicitudClienteCredito.getObservaciones());
			rbAprueba_Credito.setChecked(true);
			grCredito.setVisible(true);
			grbAproDesaCredito.setVisible(grCredito.isVisible());


			/*valida si es una extencion de canje publicitario*/
			if(chbesCanje.isChecked() && chbEsAmpliacion.isChecked())
				lblAmpliacion.setValue("EXTENSIÓN DE CANJE PUBLICITARIO");
			else
				lblAmpliacion.setValue("AMPLIACIÓN");

		}
	}

	/**
	 * Visualizar o ocultar lista de solicitudes
	 * @param visible : (true)visualizar lista de solicitudes, (False)ocultar lista de solictudes
	 */
	private void visibleListPendientes(Boolean visible){
		btnBuscar.setVisible(visible);
		listPendientes.setVisible(visible);
		grPendientes.setVisible(visible);
	}

	/**
	 * Cancela la Aprobacion de la solicitud
	 */
	public void onClickCancelAprobacion(){
		grbCliente.setVisible(false);
		grbSolicitud.setVisible(false);
		grContado.setVisible(false);
		grbAproDesaContado.setVisible(grContado.isVisible());
		grCredito.setVisible(false);
		grbAproDesaCredito.setVisible(grCredito.isVisible());
		visibleListPendientes(true);
		btnCancelar.setVisible(false);
		btnAceptar.setVisible(false);

		tabHistSilicitudes.setDisabled(false);

		clearControlSolicitud();

		tabPendientes.setLabel("Solicitudes Pendientes por Aprobar");
		tabHistSilicitudes.setVisible(true);

		if(!selectPendientes) tabHistSilicitudes.setSelected(true);

	}

	/**
	 * Cuando el usuario aprobador pulsa el botón Aceptar
	 */
	public void onClikAceptar()throws Exception{
		try{
			String msConfimacion="";
			if(rbAnular_Contado.isChecked() || rbAnular_Credito.isChecked())
				msConfimacion="Se va a deshaprobar la solicitud. ¿Desa continuar? ";
			else msConfimacion="Se va a aprobar la solicitud. ¿Desa continuar? ";

			/*Valida el ingreso del motivo de la desaprobacion, en caso de que esta sea desaprovada*/
			if (rbAnular_Contado.isChecked() && txtMotivoAnulacion_Contado.getText().trim().isEmpty())
				throw new MotivoDesapruebaNullException();
			else if (rbAnular_Credito.isChecked() && txtMotivoAnulacion_Credito.getText().trim().isEmpty())
				throw new MotivoDesapruebaNullException();

			//Valiad si el usuario tiene el nivel requerido para la aprobacion
			if(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_UNO){
				DlgMessage.information(Messages.getString("wndAprobarCredito.information.noAccesoAprobar"));
				return;
			}

			/*Solicita consfirmación de la Aprobación o Desaprobación*/
			Messagebox.show(msConfimacion, DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
						//Date fecha= Constantes.FORMAT_LONGSS.parse(new MyTime().dateServer());
						if(action==Constantes.ACTION_NEW){
							/*Actualiza solicitud cartera*/
							actualizaSolicitudCartera(solicitudCartera);

							/*Asigna a la cartera siempre y cuando la solicitud no sea una ampliación o un canje*/
							CarteraCliente carteraCliente= new CarteraCliente();
							if (rbAprueba_Contado.isChecked() || rbAprueba_Credito.isChecked())
								if (!(chbEsAmpliacion.isChecked()))
									carteraCliente=insertarCarteraCliente(solicitudCartera);

							if(rbAprueba_Contado.isChecked()) //inserta linea contado si es un Cliente Contado
								insertarLineaContadoCliente(carteraCliente, solicitudCartera,solicitudClienteCredito.getEsComisionable());

							/*actualiza la solicitud como atendido.*/
							/*Insertar solicitud cliente credito*/
							insertarSolicitudClienteCredito(solicitudCartera, solicitudClienteCredito);
							/*Refresca las solicitudes pendientes*/
							CargarListaPendientes();

						}else if (action==Constantes.ACTION_MODIFY){
							/*Verifica si es una desaprobacion*/
							if (rbAnular_Contado.isChecked() || rbAnular_Credito.isChecked())
								anulaSolicitudCartera(solicitudCartera);

							/*Anula el registro actual solicitudClienteCredito */
							/*inserta un nuevo registro solicitudClienteCredito con los cambios realizados el la Linea de Credito*/
							insertarSolicitudClienteCredito(solicitudCartera, solicitudClienteCredito);
							/*Actualiza las solicitudes desaprobadas*/
							cargarSolicitudesAproDesapro();
						}
						onClickCancelAprobacion();

					}
				}
			});

		}catch(MotivoDesapruebaNullException mdnex){
			DlgMessage.information(Messages.getString("wndApruebaCartera.infomation.MotivoDesapuebraNull"))	;
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Anula la solicitud Cartera.
	 * @param solicitudCartera : class
	 * @throws Exception
	 */
	private void anulaSolicitudCartera(SolicitudCartera solicitudCartera)throws Exception{
		solicitudCartera.setEstadoRegistro(Constantes.VALUE_INACTIVO);
		solicitudCartera.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
		UtilData.auditarRegistro(solicitudCartera, true,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudCarteraManager().actualizar(solicitudCartera);
		//Inserta un nuevo registro con la desaprobacion de la Solicitud
		insertarSolicitudCartera(solicitudCartera);
	}

	/**
	 * Actualiza solicitud cartera
	 * @param solicitudCartera
	 * @throws Exception
	 */
	private void actualizaSolicitudCartera(SolicitudCartera solicitudCartera) throws Exception{
		Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		if (rbAnular_Contado.isChecked() || rbAnular_Credito.isChecked()){//Anulacion
			/*Solcitud cartera*/
			solicitudCartera.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
			solicitudCartera.setFechaAnulacion(fecha);
		}else{ //Aprueba
			/*solicid caretra*/
			solicitudCartera.setEstadoSolicitud(Constantes.ESTADOSOL_INACTIVA);
			solicitudCartera.setFechaAprobacion(fecha);
		}
		solicitudCartera.setNivelAprobacion(Constantes.NIVEL_UNO);
		solicitudCartera.setUsuarioAprobador(usuarioAprobador);
		UtilData.auditarRegistro(solicitudCartera, true,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudCarteraManager().actualizar(solicitudCartera);
	}


	/**
	 * Insertar CarteraCliente
	 * @param carteraCliente : class
	 * @throws Exception
	 */
	private CarteraCliente insertarCarteraCliente(SolicitudCartera solicitudCartera) throws Exception{
		long lDefault=Constantes.FORMAT_DATE_TIME_24H.parse(Constantes.FECHA_DEFAULT).getTime();
		Date fDefault= new Date(lDefault);
		Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());

		CarteraCliente carteraCliente=new CarteraCliente();
		carteraCliente.setUsuario(solicitudCartera.getUsuario());
		carteraCliente.setCliente(solicitudCartera.getCliente());
		carteraCliente.setSolicitudCartera(solicitudCartera);
		carteraCliente.setBaseHistorica(solicitudCartera.getBaseHistorica());
		carteraCliente.setFechaAsignacion(fecha);
		carteraCliente.setFechaSuspension(fDefault);
		carteraCliente.setEstadoCartera(Constantes.ESTADOSOL_ACTIVA);
		carteraCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);

		UtilData.auditarRegistro(carteraCliente,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getCarteraClienteManager().guardar(carteraCliente);

		return carteraCliente;
	}

	/**
	 * Inserta Linea Contado Cliente
	 * @param carteraCliente
	 * @throws Exception
	 */
	private void insertarLineaContadoCliente(CarteraCliente carteraCliente, SolicitudCartera solicitudCartera, String esComisionable) throws Exception{
		long lDefault=Constantes.FORMAT_DATE_TIME_24H.parse(Constantes.FECHA_DEFAULT).getTime();
		Date fDefault= new Date(lDefault);
		Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());

		LineaContadoCliente lineaContadoCliente=new LineaContadoCliente();
		lineaContadoCliente.setCarteraCliente(carteraCliente);
		lineaContadoCliente.setSolicitudCartera(solicitudCartera);
		lineaContadoCliente.setDescuentoAlta(solicitudCartera.getDescuentoAlta());
		lineaContadoCliente.setDescuentoBaja(solicitudCartera.getDescuentoBaja());
		lineaContadoCliente.setFechaActivacion(fecha);
		lineaContadoCliente.setFechaCaducidad(fDefault);
		lineaContadoCliente.setEstadoLineaContado(Constantes.ESTADOSOL_ACTIVA);
		lineaContadoCliente.setEsComisionable(esComisionable);
		lineaContadoCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		UtilData.auditarRegistro(lineaContadoCliente,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getLineaContadoClienteManager().guardar(lineaContadoCliente);
	}

	/**
	 * Inserta solicitud cliente credito
	 * @param solicitudCartera
	 * @param solicitudClienteCredito
	 * @throws Exception
	 */
	private void insertarSolicitudClienteCredito(SolicitudCartera solicitudCartera, SolicitudClienteCredito solicitudClienteCredito) throws Exception{
		Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());

		SolicitudClienteCredito clienteCredito= new SolicitudClienteCredito();
		clienteCredito.setUsuarioAprobador(usuarioAprobador);
		clienteCredito.setSolicitudCartera(solicitudCartera);
		if(solicitudClienteCredito.getTipoCobranza()!=null){ //Cliente Credito
			clienteCredito.setTipoCobranza(solicitudClienteCredito.getTipoCobranza());
//			clienteCredito.setLineaCreditoSolicitada(dbxCreditoAprobado.getValue());
			clienteCredito.setLineaCreditoSolicitada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
			clienteCredito.setLineaCreditoAprobada(dbxCreditoAprobado.getValue()!=null?dbxCreditoAprobado.getValue():.00);
			clienteCredito.setSobregiro(Util.parseNumberFormat(lblSobregiro.getValue(),2));

		}else{ //Cliente Contado
			clienteCredito.setLineaCreditoSolicitada(.00);
			clienteCredito.setLineaCreditoAprobada(.00);
			clienteCredito.setSobregiro(.00);
		}
		clienteCredito.setNumeroControl(solicitudClienteCredito.getNumeroControl());
		clienteCredito.setNivelAprobacion(Constantes.NIVEL_UNO);
		clienteCredito.setFechaSolicitud(solicitudCartera.getFechaSolicitud());
		clienteCredito.setEsCanje(solicitudClienteCredito.getEsCanje());
		clienteCredito.setEsComisionable(solicitudClienteCredito.getEsComisionable());
		clienteCredito.setEsAmpliacion(solicitudClienteCredito.getEsAmpliacion());
		clienteCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		/*Verifica si es una desaprobacion*/
		if (rbAnular_Contado.isChecked() || rbAnular_Credito.isChecked()){
			clienteCredito.setFechaAnulacion(fecha);
			clienteCredito.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
			if(!(txtMotivoAnulacion_Contado.getText().trim().isEmpty()))
				clienteCredito.setObservaciones(txtMotivoAnulacion_Contado.getText().trim().toUpperCase());
			else if(!(txtMotivoAnulacion_Credito.getText().trim().isEmpty()))
				clienteCredito.setObservaciones(txtMotivoAnulacion_Credito.getText().trim().toUpperCase());
		}else{
			clienteCredito.setFechaAprobacion(fecha);
			clienteCredito.setEstadoSolicitud(Constantes.ESTADOSOL_ACTIVA);
		}
		UtilData.auditarRegistro(clienteCredito, usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudClienteCreditoManager().guardar(clienteCredito);
	}

	/**
	 * Inserta solicitud Cartera
	 * @param solicitudCartera
	 * @throws Exception
	 */
	private void insertarSolicitudCartera(SolicitudCartera solicitudCartera) throws Exception{
		Date fecha= Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());

		SolicitudCartera cartera=new SolicitudCartera();
		cartera.setUsuario(solicitudCartera.getUsuario());
		cartera.setCliente(solicitudCartera.getCliente());
		cartera.setUsuarioAprobador(usuarioAprobador);
		cartera.setFechaSolicitud(solicitudCartera.getFechaSolicitud());
		/*Verifica si es una desaprobacion*/
		if (rbAnular_Contado.isChecked() || rbAnular_Credito.isChecked()){
			cartera.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
		}else{
			cartera.setEstadoSolicitud(Constantes.ESTADOSOL_INACTIVA);
		}
		cartera.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		cartera.setNivelAprobacion(Constantes.NIVEL_UNO);
		cartera.setFechaAnulacion(fecha);
		cartera.setDescuentoBaja(solicitudCartera.getDescuentoBaja());
		cartera.setDescuentoAlta(solicitudCartera.getDescuentoAlta());
		UtilData.auditarRegistro(cartera, usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudCarteraManager().guardar(cartera);
	}

	/**
	 * Limpia los controles de la solicitud
	 */
	private void clearControlSolicitud(){
		/*Cliente*/
		lblRuc.setValue("");
		lblRazonSocial.setValue("");
		lblOrigen.setValue("");
		lblBaseHistorica.setValue("");
		/*Solcitud contado*/
		txtFechaSolcitud_Contado.setText("");
		txtFuncionario_Contado.setText("");
		dbxDescuentoAlta_Contado.setText("");
		dbxDescuentoBaja_Contado.setText("");
		rbAprueba_Contado.setChecked(false);
		rbAnular_Contado.setChecked(false);
		txtMotivoAnulacion_Contado.setText("");
		txtMotivoAnulacion_Contado.setReadonly(true);
		/*Solicitud Credito*/
		lblFechaSolicitud.setValue("");
		lblFuncionario.setValue("");
//		dbxDescuentoAlta_Credito.setText("");
//		dbxDescuentoBaja_Credito.setText("");
		lblCreditoSolicitado.setValue("");
		lblSobregiro.setValue("");
		lblMontoSobregiro.setValue("");
		dbxCreditoAprobado.setText("");
		chbEsComisionable_Credito.setChecked(false);
		chbEsComisionable_Contado.setChecked(false);
		chbesCanje.setChecked(false);
		txtObservaciones.setText("");
		rbAprueba_Credito.setChecked(false);
		rbAnular_Credito.setChecked(false);
		txtMotivoAnulacion_Credito.setText("");
		txtMotivoAnulacion_Credito.setReadonly(true);
	}

	/**
	 * Calcula el monto del sobregiro, solo es referencial no se guarda en la DB
	 * solo se guarda el porcentaje.
	 */
	public void onChangeSobregiro(){
		if(dbxCreditoAprobado.getValue()!=null && lblSobregiro.getValue()!=null && Util.parseNumberFormat(lblSobregiro.getValue(),2)>0){
			Double montoSobregiro=dbxCreditoAprobado.getValue()*(Util.parseNumberFormat(lblSobregiro.getValue(),2)/100);
			lblMontoSobregiro.setValue("% |"+Util.toNumberFormat(montoSobregiro, 2));
		}
//		else{
//			lblMontoSobregiro.setValue("0.00");
//		}
	}

	private void DisableAprobarAnular(Boolean disabled){
		rbAnular_Contado.setDisabled(disabled);
		rbAnular_Credito.setDisabled(disabled);
		rbAprueba_Contado.setDisabled(disabled);
		rbAprueba_Credito.setDisabled(disabled);

	}

	/**
	 * Desaprueba la silicitud
	 */
	public void onCheckAnular(){
//		if(rbAnular_Credito.isChecked())
//			if(selectPendientes==true){
//				rbAprueba_Credito.setChecked(false);
//				txtMotivoAnulacion_Credito.setReadonly(false);
//				txtMotivoAnulacion_Credito.setFocus(true);
//				dbxCreditoAprobado.setReadonly(true);
//				dbxCreditoAprobado.setText("");
//			}

	}


	/** HISTORIAL DE SOLICITUDES **/
	/**
	 * Cargar el historial de solicitudes aprobadas y/o desaprobadas
	 */
	public void cargarSolicitudesAproDesapro(){
		String fechaInicio=Constantes.FORMAT_DATE.format(dtxFechaInicio.getValue());
		String fechaFin=Constantes.FORMAT_DATE.format(dtxFechaFinal.getValue());
		String estadoSolicitud="";Long idFuncionario=null; Long idCliente=null; Boolean recu_Historia=true;

		if(cmbEstadoSolicitud.getSelectedIndex()>0)
			estadoSolicitud=cmbEstadoSolicitud.getSelectedItem().getValue();
		if (cmbClienteHist.getSelectedItem().getValue() instanceof Cliente)
			idCliente=((Cliente)cmbClienteHist.getSelectedItem().getValue()).getId();

		List<SolicitudCartera>list=ServiceLocator.getSolicitudCarteraManager().BuscarSolicitudes(fechaInicio, fechaFin, estadoSolicitud, idFuncionario, usuarioAprobador,idCliente, recu_Historia);

		Listitem item=null;
		Listcell cell=null;
		int y=0;
		Util.limpiarListbox(listHistSolicitudes);

		for(SolicitudCartera solicitudCartera: list){
			Usuario funcionario = solicitudCartera.getUsuario();
			Cliente cliente=solicitudCartera.getCliente();
			String tipo="";
			y++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(y));
			item.appendChild(cell);
			cell=new Listcell(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
			cell.setStyle("font-size:8.5px !important");
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());cell.setStyle("font-size:8.5px !important");
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			if(solicitudCartera.getEstadoSolicitud().equals(Constantes.ESTADOSOL_INACTIVA))
				cell=new Listcell(Constantes.FORMAT_LONG.format(solicitudCartera.getFechaAprobacion()));
			else
				cell=new Listcell(Constantes.FORMAT_LONG.format(solicitudCartera.getFechaAnulacion()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			if(solicitudCartera.getEstadoSolicitud().equals(Constantes.ESTADOSOL_INACTIVA))
				cell=new Listcell(Constantes.APROBADO_DESC);
			else
				cell=new Listcell(Constantes.DESAPROBADO_DESC);
			item.appendChild(cell);

			/*Verifica si la solicitud es o no credito*/
			if(solicitudCartera.getSolicitudClienteCredito().getLineaCreditoSolicitada()==0)
				tipo=Constantes.TIPCON_CONTADO_DESC;
			else tipo=Constantes.TIPCON_CREDITO_DESC;
			cell=new Listcell(tipo);
			item.appendChild(cell);

			final String LABEL_MODIFICADAR="Modificar";
			final String LABEL_CONSULTAR="Consultar";

			final Toolbarbutton button= new Toolbarbutton();
			button.setZindex(y-1);
			button.setMode(tipo);
			button.setStyle("color:blue; font-style:inherit; font-size: 8.5px !important");
			if(tipo.equals(Constantes.TIPCON_CONTADO_DESC)){
				button.setLabel(LABEL_CONSULTAR);
			}else if (tipo.equals(Constantes.TIPCON_CREDITO_DESC)){
				if(solicitudCartera.getSolicitudClienteCredito().getNivelAprobacion().equals(Constantes.NIVEL_UNO))
					button.setLabel(LABEL_MODIFICADAR);
				else
					button.setLabel(LABEL_CONSULTAR);
			}else{
				button.setLabel("no definido");
				button.setDisabled(true);
			}

			if(!(accesoConsultar()))
				button.setDisabled(true);

			button.addEventListener(Events.ON_CLICK, new  EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					visibleListPendientes(false);
					grbCliente.setVisible(true);
					btnCancelar.setVisible(true);
					grbSolicitud.setVisible(true);
//					lblMontoSobregiro.setDisabled(true);
					tabPendientes.setLabel("Detalle Solicitud");
					tabHistSilicitudes.setVisible(false);
					tabPendientes.setSelected(true);
					selectPendientes=false;

					Listitem listitem=listHistSolicitudes.getItemAtIndex(button.getZindex());
					SolicitudCartera solicitudCartera=listitem.getValue();
					solicitudClienteCredito=new SolicitudClienteCredito();
					solicitudClienteCredito=solicitudCartera.getSolicitudClienteCredito();
					solicitudClienteCredito.setSolicitudCartera(solicitudCartera);
					showDetalleSolicitud(solicitudClienteCredito);

					btnAceptar.setVisible(true);

					/*Valdiación para la edicion de la solicitud*/
					Long id=solicitudClienteCredito.getId();
					solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(id);
					if (!(button.getLabel().equals(LABEL_CONSULTAR))){
//					if(solicitudClienteCredito.getNivelAprobacion().equals(Constantes.NIVEL_UNO) && solicitudClienteCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO) &&
//							button.getMode().equals(Constantes.TIPCON_CREDITO_DESC) ){
						/*Permiter editar*/
//						btnAceptar.setVisible(true);
						dbxCreditoAprobado.setReadonly(false);
						Util.disabledBtnAceptar(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_UNO, btnAceptar, accesoGrabar());
						DisableAprobarAnular(btnAceptar.isDisabled());
						action=Constantes.ACTION_MODIFY;
					}else{
						/*No permite editar*/
						DisableAprobarAnular(true);
//						btnAceptar.setVisible(true);
						Util.disabledBtnAceptar(true, btnAceptar, false);
						dbxCreditoAprobado.setReadonly(true);
					}
				}
			});
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);

			listHistSolicitudes.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					visibleListPendientes(false);
					grbCliente.setVisible(true);
					btnCancelar.setVisible(true);
					btnAceptar.setVisible(true);
					Util.disabledBtnAceptar(true, btnAceptar, true);
					grbSolicitud.setVisible(true);
//					lblMontoSobregiro.setDisabled(true);
					DisableAprobarAnular(true);
					tabPendientes.setLabel("Detalle Solicitud");
					tabHistSilicitudes.setVisible(false);
					tabPendientes.setSelected(true);
					selectPendientes=false;
					dbxCreditoAprobado.setReadonly(true);

					Listitem listitem=listHistSolicitudes.getItemAtIndex(listHistSolicitudes.getSelectedIndex());
					SolicitudCartera solicitudCartera=listitem.getValue();
					solicitudClienteCredito=new SolicitudClienteCredito();
					solicitudClienteCredito=solicitudCartera.getSolicitudClienteCredito();
					solicitudClienteCredito.setSolicitudCartera(solicitudCartera);
					showDetalleSolicitud(solicitudClienteCredito);
				}
			});
			item.setValue(solicitudCartera);
			listHistSolicitudes.appendChild(item);
		}
	}

}
