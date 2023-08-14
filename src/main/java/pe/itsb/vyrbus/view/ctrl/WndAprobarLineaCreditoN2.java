package pe.itsb.vyrbus.view.ctrl;


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
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import pe.itsb.vyrbus.model.bean.CarteraCliente;
import pe.itsb.vyrbus.model.bean.Cliente;
import pe.itsb.vyrbus.model.bean.LineaCreditoCliente;
import pe.itsb.vyrbus.model.bean.SolicitudCartera;
import pe.itsb.vyrbus.model.bean.SolicitudClienteCredito;
import pe.itsb.vyrbus.model.bean.TipoCobranza;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioAprobador;
import pe.itsb.vyrbus.service.exceptions.FormaCobranzaNullException;
import pe.itsb.vyrbus.service.exceptions.FuncionarioNullException;
import pe.itsb.vyrbus.service.exceptions.LineaCreditoAprobadoNullException;
import pe.itsb.vyrbus.service.exceptions.MotivoDesapruebaNullException;
import pe.itsb.vyrbus.service.exceptions.ObservacionesNullException;
import pe.itsb.vyrbus.service.exceptions.UsuarioAprobadorNullException;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.Messages;
import pe.itsb.vyrbus.service.util.MyTime;
import pe.itsb.vyrbus.service.util.Util;
import pe.itsb.vyrbus.service.util.UtilData;
import pe.itsb.vyrbus.view.ui.DlgMessage;
import pe.itsb.vyrbus.view.ui.WndBase;

/**
 *
 * @author JABANTO
 *
 */
public class WndAprobarLineaCreditoN2 extends WndBase {
	private static final long serialVersionUID = 1L;

	/*Tabs*/
	private Tab tabPendientes;
	private Tab tabHistorial;
	Boolean selectPendientes=false; //Tru(selecciona el tabHistorial solicitudes), (false)  pendientes.


	private Button btnBuscar;
	private Listbox listPendientes;
	private Combobox cmbCliente;
	private Grid grdAprobBusqCliente;

	/*Solicitud*/
	private Label lblRuc;
	private Label lblRazonSocial;
	private Row rowCreditoAnterior;
	private Label lblCreditoAnterior;
	private Groupbox grbSolicitud;
	private Label lblFechaSolicitud;
	private Label lblFuncionario;
	private Label lblCreditoSolicitado;
	private Label lblSobregiro;
	private Label lblMontoSobregiro;
//	private Doublebox dbxMontoSobregiro;
	private Checkbox chbesCanje;
	private Checkbox chbEsAmpliacion;
	private Textbox txtObservaciones;
	private Radio rbAprueba;
	private Doublebox dbxCreditoAprobado;
	private Radio rbDesaprueba;
	private Textbox txtMotivo;
	private Combobox cmbTipoCombranza;
	private Checkbox chbModificarCredito;
	private Button btnAceptar;
	private Label lblAmpliacion;
	/*Historial */
	private Datebox dtxFechaInicio;
	private Datebox dtxFechaFinal;
	private Combobox cmbEstadoSolicitud;
	private Listbox listHistSolicitudes;
	private Button btnBuscarSolApro;
	private Combobox cmbclienteHist;
	/*Class*/
	UsuarioAprobador usuarioAprobador=new UsuarioAprobador();
	CarteraCliente carteraCliente=null;
	SolicitudClienteCredito solicitudClienteCredito=null;

	/*Otras variables*/
	int action=0;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		/*Tabs*/
		tabPendientes=(Tab)this.getFellow("tabPendientes");
		tabHistorial=(Tab)this.getFellow("tabHistorial");

		btnBuscar=(Button)this.getFellow("btnBuscar");
		listPendientes=(Listbox)this.getFellow("listPendientes");
		grbSolicitud=(Groupbox)this.getFellow("grbSolicitud");
		cmbCliente=(Combobox)this.getFellow("cmbCliente");
		grdAprobBusqCliente=(Grid)this.getFellow("grdAprobBusqCliente");
		/*Solicitud*/
		lblFechaSolicitud=(Label)this.getFellow("lblFechaSolicitud");
		lblFuncionario=(Label)this.getFellow("lblFuncionario");
		lblCreditoSolicitado=(Label)this.getFellow("lblCreditoSolicitado");
		lblSobregiro=(Label)this.getFellow("lblSobregiro");
		lblMontoSobregiro=(Label)this.getFellow("lblMontoSobregiro");
//		dbxMontoSobregiro=(Doublebox)this.getFellow("dbxMontoSobregiro");
		chbesCanje=(Checkbox)this.getFellow("chbesCanje");
		chbEsAmpliacion=(Checkbox)this.getFellow("chbEsAmpliacion");
		txtObservaciones=(Textbox)this.getFellow("txtObservaciones");
		rbAprueba=(Radio)this.getFellow("rbAprueba");
		dbxCreditoAprobado=(Doublebox)this.getFellow("dbxCreditoAprobado");
		rbDesaprueba=(Radio)this.getFellow("rbDesaprueba");
		txtMotivo=(Textbox)this.getFellow("txtMotivo");
		lblRuc=(Label)this.getFellow("lblRuc");
		cmbTipoCombranza=(Combobox)this.getFellow("cmbTipoCombranza");
		lblRazonSocial=(Label)this.getFellow("lblRazonSocial");
		chbModificarCredito=(Checkbox)this.getFellow("chbModificarCredito");
		btnAceptar=(Button)this.getFellow("btnAceptar");
		rowCreditoAnterior=(Row)this.getFellow("rowCreditoAnterior");
		lblCreditoAnterior=(Label)this.getFellow("lblCreditoAnterior");
		lblAmpliacion=(Label)this.getFellow("lblAmpliacion");
		/*Historial */
		dtxFechaInicio=(Datebox)this.getFellow("dtxFechaInicio");
		dtxFechaFinal=(Datebox)this.getFellow("dtxFechaFinal");
		cmbEstadoSolicitud=(Combobox)this.getFellow("cmbEstadoSolicitud");
		listHistSolicitudes=(Listbox)this.getFellow("listHistSolicitudes");
		btnBuscarSolApro=(Button)this.getFellow("btnBuscarSolApro");
		cmbclienteHist=(Combobox)this.getFellow("cmbclienteHist");

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try{
			usuarioAprobador=(UsuarioAprobador)getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO_APROBADOR);

			if(usuarioAprobador==null)
				throw new UsuarioAprobadorNullException();

			Date date=new Date();
			date=Constantes.FORMAT_DATE.parse(new MyTime().dateServer());

			UtilData.cargarTipoCobranza(cmbTipoCombranza, false);

			visibleSolicitud(false);
			listPendientes();

//			lblSobregiro.setLocale(Locale.US);
			dbxCreditoAprobado.setLocale(Locale.US);
//			lblCreditoSolicitado.setLocale(Locale.US);
//			dbxMontoSobregiro.setLocale(Locale.US);
//			lblCreditoAnterior.setLocale(Locale.US);

			dtxFechaInicio.setValue(date);
			dtxFechaFinal.setValue(date);
			UtilData.cargarEstadoSolicitudLC(cmbEstadoSolicitud, true);
			cmbEstadoSolicitud.setSelectedIndex(1);

			cargarClientesSolicitudPendiente();
			UtilData.cargarClientesSolicitud(cmbclienteHist, true);

			Util.disabledBtnBuscar(false, btnBuscar, accesoConsultar());
			Util.disabledBtnBuscar(false, btnBuscarSolApro, accesoConsultar());

		}catch (UsuarioAprobadorNullException uanex){
			DlgMessage.information(Messages.getString("wndApruebaCartera.infomation.UsuarioAprobadorNull"));
			closeTabWindow();
		}

	}

	/**
	 * Lista pendienpes por aprobar LC.
	 */
	public void listPendientes(){
		String fechaInicio=null; String fechaFin=null; String estadoSolicitud=null; Long idCliente=null;
		Boolean recu_Historia=false;
		if(cmbCliente.getSelectedIndex()>0)
			idCliente=((Cliente)cmbCliente.getSelectedItem().getValue()).getId();
		List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN2(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);

		Listitem item=null;
		Listcell cell=null;
		int x=0;
		Util.limpiarListbox(listPendientes);

		for(LineaCreditoCliente lineaCreditoCliente: list){
			SolicitudCartera solicitudCartera=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera();
			Usuario funcionario = solicitudCartera.getUsuario();
			Cliente cliente=solicitudCartera.getCliente();
			x++;
			item=new Listitem();
			cell=new Listcell(String.valueOf(x));
			item.appendChild(cell);
			cell=new Listcell(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(funcionario.toString());
			item.appendChild(cell);
			cell=new Listcell(cliente.getNumeroDocumento());
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);
			cell=new Listcell(cliente.getRazonSocial());
			item.appendChild(cell);
			cell=new Listcell(Util.toNumberFormat(lineaCreditoCliente.getSolicitudClienteCredito().getLineaCreditoAprobada(), 2));
			cell.setStyle("font-size:11px !important");
			item.appendChild(cell);

			final Toolbarbutton button= new Toolbarbutton("VER SOLICITUD ");
			button.setId(String.valueOf(x-1));
			button.setStyle("color:blue; font-style:inherit; font-size:9px !important");
			cell=new Listcell();
			cell.appendChild(button);
			item.appendChild(cell);

			button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					selectPendientes=true;
					Listitem listitem=listPendientes.getItemAtIndex(Integer.valueOf(button.getId()));
					onLoadDetalleSolicitud(((LineaCreditoCliente)listitem.getValue()).getSolicitudClienteCredito());

					visibleSolicitud(true);
					visibleLisPendientes(false);
					tabHistorial.setDisabled(true);
					btnAceptar.setVisible(true);
					rbAprueba.setChecked(true);
//					Util.disabledBtnAceptar(false, btnAceptar, accesoGrabar());
					Util.disabledBtnAceptar(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_DOS, btnAceptar, accesoGrabar());
					disabledControlsSolciitud(btnAceptar.isDisabled());

					action=Constantes.ACTION_NEW;
				}
			});
			item.setValue(lineaCreditoCliente);
			listPendientes.appendChild(item);
		}
	}

	/**
	 * Carga los clientes con solicitud pendiente por aprobar
	 */
	private void cargarClientesSolicitudPendiente(){
		String fechaInicio=null; String fechaFin=null; String estadoSolicitud=null; Long idCliente=null;
		Boolean recu_Historia=false;

		List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN2(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);

		UtilData.cargarGenericData(cmbCliente, true);
		for (LineaCreditoCliente lineaCreditoCliente: list) {
			if(lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera().getCliente()!=null){
				Cliente cliente=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera().getCliente();
				Comboitem oComboitem = new Comboitem();
				oComboitem.setLabel(cliente.getRazonSocial());
				oComboitem.setValue(cliente);
				cmbCliente.appendChild(oComboitem);
			}
		}
	}

	/**
	 * Activa o desactiva controles de la solicitud
	 * @param disabled
	 */
	private void disabledControlsSolciitud(Boolean disabled){
		rbAprueba.setDisabled(disabled);
		rbDesaprueba.setDisabled(disabled);
		rbDesaprueba.setDisabled(disabled);
		txtObservaciones.setReadonly(disabled);
		dbxCreditoAprobado.setReadonly(true);
		chbModificarCredito.setDisabled(disabled);
		cmbTipoCombranza.setDisabled(disabled);
	}

	/**
	 * Muestra detalle de la solicitud
	 * @param solicitudClienteCredito : class
	 */
	private void onLoadDetalleSolicitud(SolicitudClienteCredito solicitudClienteCredito){
		//rowCreditoAnterior.setVisible(false);

		this.solicitudClienteCredito=solicitudClienteCredito;
		Cliente cliente=solicitudClienteCredito.getSolicitudCartera().getCliente();
		Usuario funcionario=solicitudClienteCredito.getSolicitudCartera().getUsuario();

		lblRuc.setValue(cliente.getNumeroDocumento());
		lblRazonSocial.setValue(cliente.getRazonSocial());
		lblFechaSolicitud.setValue(Constantes.FORMAT_DATE.format(solicitudClienteCredito.getSolicitudCartera().getFechaSolicitud()));
		lblFuncionario.setValue(funcionario.toString()); //.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());

		if(action==Constantes.ACTION_NEW){
			lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoAprobada(),2));
			dbxCreditoAprobado.setValue(solicitudClienteCredito.getLineaCreditoAprobada());
		}else{
			lblCreditoSolicitado.setValue(Util.toNumberFormat(solicitudClienteCredito.getLineaCreditoSolicitada(),2));
			dbxCreditoAprobado.setValue(solicitudClienteCredito.getLineaCreditoAprobada());
		}

		Util.seleccionarValorItemCombo(TipoCobranza.class, cmbTipoCombranza,solicitudClienteCredito.getTipoCobranza().getId());

		lblSobregiro.setValue(Util.toNumberFormat(solicitudClienteCredito.getSobregiro(),2));
		onChangeSobregiro();
		chbesCanje.setChecked(solicitudClienteCredito.getEsCanje().equals(Constantes.SI)? true: false);
		chbEsAmpliacion.setChecked(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI)? true: false);
		lblCreditoAnterior.setValue("0.00");

		if(chbEsAmpliacion.isChecked()){
			rowCreditoAnterior.setVisible(true);
			LineaCreditoCliente  lineaCreditoCliente=ServiceLocator.getLineaCreditoClienteManager().lineaCreditoCliente(solicitudClienteCredito.getSolicitudCartera().getCliente().getId());
			if(lineaCreditoCliente!=null)
				lblCreditoAnterior.setValue(Util.toNumberFormat(lineaCreditoCliente.getLineaCreditoAprobada(), 2));
		}

		if(!selectPendientes){//Recupera observaciones de las sicitud aprobadas o desaprobadas por finanzas
			if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA))
				txtObservaciones.setText(solicitudClienteCredito.getObservaciones());
			else if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA))
				txtMotivo.setText(solicitudClienteCredito.getObservaciones());
			if(solicitudClienteCredito.getLineaCreditoSolicitada() != solicitudClienteCredito.getLineaCreditoAprobada())
				chbModificarCredito.setChecked(true);
		}

		/*valida si es una extencion de canje publicitario*/
		if(chbesCanje.isChecked() && chbEsAmpliacion.isChecked()){
			lblAmpliacion.setValue("EXTENSIÓN DE CANJE PUBLICITARIO");
		}else{
			lblAmpliacion.setValue("AMPLIACIÓN");
		}
	}

	/**
	 * Muesttra o oculata listado de pendientes
	 * @param visible
	 */
	private void visibleLisPendientes(Boolean visible){
		listPendientes.setVisible(visible);
		btnBuscar.setVisible(visible);
		grdAprobBusqCliente.setVisible(visible);
	}

	/**
	 * muestar o oculta datos de la silicitud
	 * @param visible
	 */
	private void visibleSolicitud(Boolean visible){
		grbSolicitud.setVisible(visible);
	}

	/**
	 * Calcula el monto del sobregiro, solo es referencial no se guarda en la DB
	 * solo se guarda el porcentaje.
	 */
	public void onChangeSobregiro(){
		Double sobregiro=.00;
		Double montoSobregiro=.00;
		sobregiro=(lblSobregiro.getValue()!=null?Util.parseNumberFormat(lblSobregiro.getValue(),2):.00);
		if(sobregiro>0){
			if(chbModificarCredito.isChecked())
				montoSobregiro=dbxCreditoAprobado.getValue()*(sobregiro/100);
			else
				montoSobregiro=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)*(sobregiro/100);
		}

		lblMontoSobregiro.setValue("% |"+Util.toNumberFormat(montoSobregiro,2));

//		if(dbxCreditoAprobado.getValue()!=null && lblSobregiro.getValue()!=null && lblSobregiro.getValue()>0){
//			dbxMontoSobregiro.setValue(dbxCreditoAprobado.getValue()*(lblSobregiro.getValue()/100));
//		}else{
//			dbxMontoSobregiro.setValue(0);
//		}
	}

	/**
	 * cancela operación
	 */
	public void onClickCancelar(){
		clearControlSolcitud();
		visibleSolicitud(false);
		visibleLisPendientes(true);
		if (!(selectPendientes))
			tabHistorial.setSelected(true);
		tabHistorial.setDisabled(false);
	}

	/**
	 * Procesa aprobación o desaprobación.
	 * @throws Exception
	 */
	public void onClickAceptar() throws Exception{
		try{
			if(rbAprueba.isChecked()){
				if(dbxCreditoAprobado.getText().trim().isEmpty())
					throw new LineaCreditoAprobadoNullException();
				if(chbModificarCredito.isChecked() && txtObservaciones.getText().trim().isEmpty())
					throw new ObservacionesNullException();
			}else if (rbDesaprueba.isChecked()){
				if(txtMotivo.getText().trim().isEmpty())
					throw new MotivoDesapruebaNullException();
			}
			if(!(cmbTipoCombranza.getSelectedItem().getValue() instanceof TipoCobranza))
				throw new FormaCobranzaNullException();


			/*********************************************************************************/
			/** SE ADOPTO LA FUNCIONALIDAD DEL NIVEL 3 - 27/11/2015*/
			/*********************************************************************************/
			/*Buscar cartera cliente, por el idSolicitudCartera.*/
			TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
			criteriosBusqueda.put("cliente", solicitudClienteCredito.getSolicitudCartera().getCliente());
			criteriosBusqueda.put("estadoCartera", Constantes.ESTADOSOL_ACTIVA);
			List<CarteraCliente>list=ServiceLocator.getCarteraClienteManager().buscarPorX(criteriosBusqueda, null);
			if(list.size()>0){
				carteraCliente=new CarteraCliente();
				carteraCliente=list.get(0);
			}else
				throw new FuncionarioNullException();
			/** FIN*******************************************************************************/


			//Valiad si el usuario tiene el nivel requerido para la aprobacion
			if(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_DOS){
				DlgMessage.information(Messages.getString("wndAprobarCredito.information.noAccesoAprobar"));
				return;
			}


			Messagebox.show(Messages.getString(rbAprueba.isChecked()?"wndAprobarLineaCredito.question.Aprobar": "wndAprobarLineaCredito.question.Desaprobar"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event e) throws Exception {
					if(e.getName().equals("onYes")){
  						if(action==Constantes.ACTION_NEW) {
							/*Buscar solicitud cliente credito por ID*/
							Long idSolicitudCredito=solicitudClienteCredito.getId();
							solicitudClienteCredito = new SolicitudClienteCredito();
							solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(idSolicitudCredito);
						 }


						/*Inserta una nuevo registro con la solicitud cliente credito - Nivel 2*/
						insertarSolicitudClienteCredito(solicitudClienteCredito,Constantes.NIVEL_DOS);


						/*********************************************************************************/
						/** SE ADOPTO LA FUNCIONALIDAD DEL NIVEL 3 - 27/11/2015*/
						/*********************************************************************************/
						Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
						/*Recalcula el saldo para el credito del cliente*/
						Double sobregiro=.00; Double montoAprobado=.00;
						if (!(lblSobregiro.getValue()==null))
							sobregiro=Util.parseNumberFormat(lblSobregiro.getValue(),2);
						if(sobregiro>0){
							montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)+(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2)*(sobregiro/100));
						}else
							montoAprobado=Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2);

						Double saldo=montoAprobado;
						if(chbEsAmpliacion.isChecked()){
							/*Obtiene el nuevo saldo, cuando es una ampliacion*/
							if(solicitudClienteCredito.getEsAmpliacion().equals(Constantes.SI))
								saldo = ServiceLocator.getLineaCreditoClienteManager().saldo(montoAprobado, carteraCliente.getCliente().getId());

							/*Inactiva Linea de credito actual*/
							TreeMap<String, Object> criteriosBusqueda = new TreeMap<>();
							criteriosBusqueda.put("carteraCliente", carteraCliente);
							criteriosBusqueda.put("estadoLineaCredito", Constantes.ESTADOSOL_ACTIVA);
							criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
							List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarPorX(criteriosBusqueda, null);
							for(LineaCreditoCliente  lineaCreditoCliente: list){
								lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_INACTIVA);
								lineaCreditoCliente.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								lineaCreditoCliente.setFechaInactivacion(fecha);
								UtilData.auditarRegistro(lineaCreditoCliente, true, usuarioAprobador.getUsuario(), Executions.getCurrent());
								ServiceLocator.getLineaCreditoClienteManager().actualizar(lineaCreditoCliente);
							}
						}

						/*Inserta una nuevo registro con la solicitud cliente credito - Nivel 3*/
						solicitudClienteCredito=insertarSolicitudClienteCredito(solicitudClienteCredito,Constantes.NIVEL_TRES);

						if(rbAprueba.isChecked())
							insertarLineaCreditoAprobada(solicitudClienteCredito, carteraCliente,saldo);
						/** FIN***********************************************************************************************/


						onClickCancelar();
						listPendientes();
						listHistorial();
					}
				}
			});

//		}catch(CancelaGrabacionException cg){
		}catch (FuncionarioNullException fnex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCreditoN3.information.ClienteSinFuncionario"));
		}catch (FormaCobranzaNullException fcnex){
			DlgMessage.information(Messages.getString("wndSolicitudCartera.information.FormaCobranzaNull"),cmbTipoCombranza);
		}catch (ObservacionesNullException onex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCredito.information.ModificarCredito"),txtObservaciones);
		}catch (LineaCreditoAprobadoNullException lcanex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCredito.information.LicreditoAprobadaNull"),dbxCreditoAprobado);
		}catch (MotivoDesapruebaNullException mdnex){
			DlgMessage.information(Messages.getString("wndAprobarLineaCredito.information.MotivoDesapruebaNull"),txtMotivo);
		}catch (Exception e) {
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	private void insertarLineaCreditoAprobada(SolicitudClienteCredito solicitudClienteCredito, CarteraCliente carteraCliente, Double saldo) throws Exception{
		/*Inserta el nuevo registro de la linea de credito, con la aprobacion o desaprobacion*/
		long lDefault=Constantes.FORMAT_DATE_TIME_24H.parse(Constantes.FECHA_DEFAULT).getTime();
		Date fDefault= new Date(lDefault);
		Date fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());

		LineaCreditoCliente lineaCreditoCliente=new LineaCreditoCliente();
		lineaCreditoCliente.setCarteraCliente(carteraCliente);
		lineaCreditoCliente.setTipoCobranza(solicitudClienteCredito.getTipoCobranza());
		lineaCreditoCliente.setLineaCreditoSolicitada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
		lineaCreditoCliente.setLineaCreditoAprobada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
		lineaCreditoCliente.setFechaActivacion(fecha);
		lineaCreditoCliente.setEstadoLineaCredito(Constantes.ESTADOSOL_ACTIVA);
		lineaCreditoCliente.setSaldo(saldo);
		lineaCreditoCliente.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		lineaCreditoCliente.setSolicitudClienteCredito(solicitudClienteCredito);
		lineaCreditoCliente.setSobregiro(Util.parseNumberFormat(lblSobregiro.getValue(),2));
		lineaCreditoCliente.setFechaCaducidad(fDefault);
		lineaCreditoCliente.setEsCanje(solicitudClienteCredito.getEsCanje());
		lineaCreditoCliente.setEsComisionable(solicitudClienteCredito.getEsComisionable());
		UtilData.auditarRegistro(lineaCreditoCliente,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getLineaCreditoClienteManager().guardar(lineaCreditoCliente);
	}
	/**
	 * Inserta una nueva Solicitud cliente credito
	 * @param solicitudClienteCredito
	 * @return
	 * @throws Exception
	 */
	private SolicitudClienteCredito insertarSolicitudClienteCredito(SolicitudClienteCredito solicitudClienteCredito, Integer nivel)throws Exception{
		Date fecha = new Date();
		fecha=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		usuarioAprobador=ServiceLocator.getUsuarioAprobadorManager().buscarPorId(usuarioAprobador.getId().longValue());

		SolicitudClienteCredito osolicitudClienteCredito =new SolicitudClienteCredito();
		osolicitudClienteCredito.setUsuarioAprobador(usuarioAprobador);
		osolicitudClienteCredito.setSolicitudCartera(solicitudClienteCredito.getSolicitudCartera());
		osolicitudClienteCredito.setTipoCobranza(solicitudClienteCredito.getTipoCobranza());
//		osolicitudClienteCredito.setLineaCreditoSolicitada(solicitudClienteCredito.getLineaCreditoAprobada());
//		osolicitudClienteCredito.setLineaCreditoSolicitada(lblCreditoSolicitado.getValue());
		osolicitudClienteCredito.setLineaCreditoSolicitada(Util.parseNumberFormat(lblCreditoSolicitado.getValue(),2));
		osolicitudClienteCredito.setLineaCreditoAprobada(dbxCreditoAprobado.getValue()!=null?dbxCreditoAprobado.getValue():.00);
		osolicitudClienteCredito.setNumeroControl(solicitudClienteCredito.getNumeroControl());
		osolicitudClienteCredito.setSobregiro(Util.parseNumberFormat(lblSobregiro.getValue(),2));
		osolicitudClienteCredito.setNivelAprobacion(nivel);
		osolicitudClienteCredito.setFechaSolicitud(solicitudClienteCredito.getFechaSolicitud());
		osolicitudClienteCredito.setEsCanje(solicitudClienteCredito.getEsCanje());
		osolicitudClienteCredito.setEsComisionable(solicitudClienteCredito.getEsComisionable());
		osolicitudClienteCredito.setEsAmpliacion(solicitudClienteCredito.getEsAmpliacion());
		osolicitudClienteCredito.setTipoCobranza((TipoCobranza)cmbTipoCombranza.getSelectedItem().getValue());
		osolicitudClienteCredito.setEstadoRegistro(Constantes.VALUE_ACTIVO);
		if(rbAprueba.isChecked()){
			osolicitudClienteCredito.setFechaAprobacion(fecha);
			osolicitudClienteCredito.setEstadoSolicitud(Constantes.ESTADOSOL_ACTIVA);
			osolicitudClienteCredito.setObservaciones(txtObservaciones.getText().trim().toUpperCase());
		}else{
			osolicitudClienteCredito.setFechaAnulacion(fecha);
			osolicitudClienteCredito.setEstadoSolicitud(Constantes.ESTADOSOL_ANULADA);
			osolicitudClienteCredito.setObservaciones(txtMotivo.getText().trim().toUpperCase());
		}
		UtilData.auditarRegistro(osolicitudClienteCredito,usuarioAprobador.getUsuario(), Executions.getCurrent());
		ServiceLocator.getSolicitudClienteCreditoManager().guardar(osolicitudClienteCredito);

		return osolicitudClienteCredito;
	}

	/**
	 * Limpia controles de la solcitud
	 */
	private void clearControlSolcitud(){
		lblRuc.setValue("");
		lblRazonSocial.setValue("");
		lblCreditoAnterior.setValue("");
		lblFechaSolicitud.setValue("");
		lblFuncionario.setValue("");
		lblCreditoSolicitado.setValue("");
		lblSobregiro.setValue("");
		lblMontoSobregiro.setValue("");
//		dbxMontoSobregiro.setText("");
		chbesCanje.setChecked(false);
		chbEsAmpliacion.setChecked(false);
		rbAprueba.setChecked(false);
		rbDesaprueba.setChecked(false);
		dbxCreditoAprobado.setText("");
		txtMotivo.setText("");
		txtObservaciones.setText("");
		chbModificarCredito.setChecked(false);
	}


	/**
	 * historial
	 */
	public void listHistorial(){
		try{
			btnBuscarSolApro.setDisabled(true);
			String fechaInicio=Constantes.FORMAT_DATE.format(dtxFechaInicio.getValue());
			String fechaFin=Constantes.FORMAT_DATE.format(dtxFechaFinal.getValue());
			String estadoSolicitud=""; Long idCliente=null; Boolean recu_Historia=true;
			if(cmbEstadoSolicitud.getSelectedIndex()>0)
				estadoSolicitud=cmbEstadoSolicitud.getSelectedItem().getValue();
			if (cmbclienteHist.getSelectedItem().getValue() instanceof Cliente)
				idCliente=((Cliente)cmbclienteHist.getSelectedItem().getValue()).getId();

			List<LineaCreditoCliente>list=ServiceLocator.getLineaCreditoClienteManager().buscarSolicitudLineaCreditoN2(fechaInicio, fechaFin, estadoSolicitud, idCliente, usuarioAprobador, recu_Historia);

			String estado="";
			Listitem item=null;
			Listcell cell=null;
			int x=0;
			Util.limpiarListbox(listHistSolicitudes);

			for(LineaCreditoCliente lineaCreditoCliente: list){
				SolicitudCartera solicitudCartera=lineaCreditoCliente.getSolicitudClienteCredito().getSolicitudCartera();
				Usuario funcionario = solicitudCartera.getUsuario();
				Cliente cliente=solicitudCartera.getCliente();
				x++;
				item=new Listitem();
				cell=new Listcell(String.valueOf(x));
				item.appendChild(cell);
				cell=new Listcell(funcionario.getApellidoPaterno()+" "+funcionario.getApellidoMaterno()+", "+funcionario.getNombre());
				item.appendChild(cell);
				cell=new Listcell(cliente.getRazonSocial());
				item.appendChild(cell);
				cell=new Listcell(Constantes.FORMAT_DATE.format(solicitudCartera.getFechaSolicitud()));
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);
				if(lineaCreditoCliente.getSolicitudClienteCredito().getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)){
					cell=new Listcell(Constantes.FORMAT_LONG.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaAprobacion()));
					estado=Constantes.APROBADO_DESC;
				}else if (lineaCreditoCliente.getSolicitudClienteCredito().getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)){
					cell=new Listcell(Constantes.FORMAT_LONG.format(lineaCreditoCliente.getSolicitudClienteCredito().getFechaAnulacion()));
					estado=Constantes.DESAPROBADO_DESC;
				}else
					cell=new Listcell("");
				cell.setStyle("font-size:11px !important");
				item.appendChild(cell);

				cell=new Listcell(estado);
				item.appendChild(cell);

				final Toolbarbutton button= new Toolbarbutton();
				button.setId(lineaCreditoCliente.getSolicitudClienteCredito().getId().toString());
				button.setStyle("color:blue; font-style:inherit; font-size: 8.5px !important");
//				if(lineaCreditoCliente.getSolicitudClienteCredito().getNivelAprobacion().equals(Constantes.NIVEL_DOS))
//					button.setLabel("Modificar");
//				else
					button.setLabel("Consultar");

				button.addEventListener(Events.ON_CLICK,new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						selectPendientes=false;

						SolicitudClienteCredito solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(Long.valueOf(button.getId()));
						onLoadDetalleSolicitud(solicitudClienteCredito);

						visibleSolicitud(true);
						visibleLisPendientes(false);
						tabHistorial.setDisabled(true);
						if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)){
							rbAprueba.setChecked(true);
							rbDesaprueba.setChecked(false);
						}else if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)){
							rbAprueba.setChecked(false);
							rbDesaprueba.setChecked(true);
						}
						tabPendientes.setSelected(true);
						btnAceptar.setVisible(true);

						/*Valdiación para la edicion de la solicitud*/
						if(((Toolbarbutton)event.getTarget()).getLabel().equals("Modificar")){
//						if(solicitudClienteCredito.getNivelAprobacion().equals(Constantes.NIVEL_DOS) && solicitudClienteCredito.getEstadoRegistro().equals(Constantes.VALUE_ACTIVO)){
							/*Permiter editar*/
							Util.disabledBtnAceptar(usuarioAprobador.getNivelAprobacion().intValue()!=Constantes.NIVEL_DOS, btnAceptar, accesoGrabar());
							disabledControlsSolciitud(btnAceptar.isDisabled());
//							btnAceptar.setVisible(true);
							dbxCreditoAprobado.setReadonly(false);
							chbModificarCredito.setChecked(true);
							action=Constantes.ACTION_MODIFY;
						}else{
							/*No permite editar*/
							disabledControlsSolciitud(true);
//							btnAceptar.setVisible(false);
							Util.disabledBtnAceptar(true, btnAceptar, false);
							dbxCreditoAprobado.setReadonly(true);
						}
					}
				});
				cell=new Listcell();
				cell.appendChild(button);
				item.appendChild(cell);

				item.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						selectPendientes=false;

						Listitem listitem=listHistSolicitudes.getItemAtIndex(listHistSolicitudes.getSelectedIndex());
						SolicitudClienteCredito solicitudClienteCredito=ServiceLocator.getSolicitudClienteCreditoManager().buscarPorId(((LineaCreditoCliente)listitem.getValue()).getSolicitudClienteCredito().getId());
						onLoadDetalleSolicitud(solicitudClienteCredito);

						visibleSolicitud(true);
						visibleLisPendientes(false);
						tabHistorial.setDisabled(true);
						if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ACTIVA)){
							rbAprueba.setChecked(true);
							rbDesaprueba.setChecked(false);
						}else if(solicitudClienteCredito.getEstadoSolicitud().equals(Constantes.ESTADOSOL_ANULADA)){
							rbAprueba.setChecked(false);
							rbDesaprueba.setChecked(true);
						}
						tabPendientes.setSelected(true);

						Util.disabledBtnAceptar(true, btnAceptar, false);
						btnAceptar.setVisible(true);
						disabledControlsSolciitud(true);
					}
				});

				item.setValue(lineaCreditoCliente);
				listHistSolicitudes.appendChild(item);
			}
			btnBuscarSolApro.setDisabled(false);
		}catch (Exception e) {
			btnBuscarSolApro.setDisabled(false);
			DlgMessage.error(this.getClass().getName()+" "+e.getMessage());
			e.printStackTrace();
		}

	}

}
